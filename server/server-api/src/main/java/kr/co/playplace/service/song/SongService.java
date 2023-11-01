package kr.co.playplace.service.song;

import kr.co.playplace.common.util.Geocoder;
import kr.co.playplace.common.util.GetWeather;
import kr.co.playplace.common.util.S3Uploader;
import kr.co.playplace.common.util.SecurityUtils;
import kr.co.playplace.controller.song.request.SavePlaySongRequest;
import kr.co.playplace.controller.song.request.SaveSongHistoryRequest;
import kr.co.playplace.controller.song.request.SaveSongRequest;
import kr.co.playplace.entity.Weather;
import kr.co.playplace.entity.location.Village;
import kr.co.playplace.entity.song.Song;
import kr.co.playplace.entity.song.SongHistory;
import kr.co.playplace.entity.user.UserSong;
import kr.co.playplace.entity.user.Users;
import kr.co.playplace.repository.user.UserRepository;
import kr.co.playplace.repository.location.VillageRepository;
import kr.co.playplace.repository.song.SongHistoryRepository;
import kr.co.playplace.repository.song.SongRepository;
import kr.co.playplace.repository.user.UserSongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;
    private final UserSongRepository userSongRepository;
    private final UserRepository userRepository;
    private final VillageRepository villageRepository;
    private final SongHistoryRepository songHistoryRepository;

    private final S3Uploader s3Uploader;
    private final Geocoder geocoder;
    private final GetWeather getWeather;
    private final RedisTemplate redisTemplate;

    public void saveSong(SaveSongRequest saveSongRequest){
        boolean alreadySaved = songRepository.existsByYoutubeId(saveSongRequest.getYoutubeId());
        if(!alreadySaved){ // db에 없는 곡이라면 저장
//            String imgUrl = "";
//            try {
//                imgUrl = s3Uploader.upload(saveSongRequest.getAlbumImg(), "album");
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            Song song = saveSongRequest.toEntity(imgUrl);
            Song song = saveSongRequest.toEntity();
            songRepository.save(song);

            saveSongInPlayList(song);
        }else{ // db에 있다면 찾아서 재생목록에 추가
            Optional<Song> song = songRepository.findByYoutubeId(saveSongRequest.getYoutubeId());
            saveSongInPlayList(song.get());
        }
    }

    private void saveSongInPlayList(Song song){ // user 확인해서 곡을 재생목록에 추가
        Optional<Users> user = userRepository.findById(SecurityUtils.getUser().getUserId());

//        deleteSongInPlayList(user.get());

        UserSong userSong = UserSong.builder()
                .user(user.get())
                .song(song)
                .build();
        userSongRepository.save(userSong);
    }

    private void deleteSongInPlayList(Users user){
        int cnt = userSongRepository.countUserSongByUser_Id(user.getId());
        if(cnt < 99) return;
        userSongRepository.deleteUserSongByUser_Id(user.getId());
    }

    public void saveSongHistory(SaveSongHistoryRequest saveSongHistoryRequest){
        // 로그인한 사용자
        Optional<Users> user = userRepository.findById(SecurityUtils.getUser().getUserId());

        // 재생한 곡
        Optional<Song> song = songRepository.findById(saveSongHistoryRequest.getSongId());

        // 1. 위도 경도로 api 호출해서 지역 코드 받아오기
        log.info("lat{} lon{}", saveSongHistoryRequest.getLat(), saveSongHistoryRequest.getLon());
        int code = geocoder.getGeoCode(saveSongHistoryRequest.getLat(), saveSongHistoryRequest.getLon());
        Optional<Village> village = villageRepository.findByCode(code);

        // 2. 위도 경도로 날씨 받아오기
        Weather weather = getWeather.getWeatherCode(saveSongHistoryRequest.getLat(), saveSongHistoryRequest.getLon());

        // 3. 곡 기록에 저장
        SongHistory songHistory = SongHistory.builder()
                .user(user.get())
                .song(song.get())
                .village(village.get())
                .weather(weather)
                .build();
        songHistoryRepository.save(songHistory);
    }

    public void playSong(SavePlaySongRequest savePlaySongRequest){
        // redis에 저장
        long userId = SecurityUtils.getUser().getUserId();
        String key = "play:"+userId;
        if(redisTemplate.hasKey(key)){
            redisTemplate.delete(key);
        }
        if(savePlaySongRequest.isLandmark()){
            redisTemplate.opsForHash().put(key, savePlaySongRequest.getPlaylistSongId(),"true");
        }else{
            redisTemplate.opsForHash().put(key, savePlaySongRequest.getPlaylistSongId(),"false");
        }
    }

    @Scheduled(cron = "0 0/30 * * * ?") // Redis -> MySQL 30분 마다 동기화
    public void syncPlaySong(){
        Set<String> changeUserKeys = redisTemplate.keys("play:*");
        if (changeUserKeys.isEmpty()) return;

        for (String key : changeUserKeys) {
            long userId = Long.parseLong(key.split(":")[1]);
            Users user = userRepository.findById(userId).orElse(null);
            if (user != null) syncSongForNowplay(user); // mysql update
            redisTemplate.delete(key); // Redis 데이터 삭제
        }
    }

    private void syncSongForNowplay(Users user){
        Set<Object> companyIdsObjects = redisTemplate.opsForHash().keys("play:" + user.getId());
        Set<Long> playlistSongIds = companyIdsObjects.stream()
                .map(objectId -> (Long) objectId)
                .collect(Collectors.toSet());

        log.info(playlistSongIds.iterator().next().toString()); // playlistSongId
        log.info(redisTemplate.opsForHash().get("play:" + user.getId(), playlistSongIds.iterator().next()).toString()); // isLandmark

        Object check = redisTemplate.opsForHash().get("play:" + user.getId(), playlistSongIds.iterator().next());
        if (check == null) return;
        if (check.equals("true")) {
//            if(!interestRepository.existsByMember_IdAndCompany_Id(member.getId(), com.getId())) saveCompany.add(com);
        } else {
//            Interest interest = interestRepository.findByMember_IdAndCompany_Id(member.getId(), com.getId());
//            if (interest != null) interestRepository.delete(interest);
        }
    }
}