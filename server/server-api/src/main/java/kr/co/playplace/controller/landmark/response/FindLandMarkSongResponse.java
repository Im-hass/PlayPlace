package kr.co.playplace.controller.landmark.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@Data
public class FindLandMarkSongResponse {

    private String title;
    private String artist;
    private String albumImg;
    private String playTime;

    @Builder
    public FindLandMarkSongResponse(String title, String artist, String albumImg, String playTime) {
        this.title = title;
        this.artist = artist;
        this.albumImg = albumImg;
        this.playTime = playTime;
    }
}
