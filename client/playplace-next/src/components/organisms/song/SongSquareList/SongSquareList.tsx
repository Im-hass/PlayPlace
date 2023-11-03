import SongSubtitle from '@/components/molecules/song/SongSubtitle/SongSubtitle';
import WEATHER_TITLE from '@/constants/home';
import { WeatherSong } from '@/types/songs';
import React, { WheelEvent, useRef } from 'react';
import SongSquareItems from '@/components/molecules/song/SongSquareItems/SongSquareItems';
import Text from '@/components/atoms/Text/Text';
import SongSquareListContainer, { SongSquareListContent, SongSquareListScroll } from './style';

interface SongSquareListProps {
	WeatherSongList: WeatherSong;
}

function SongSquareList(props: SongSquareListProps) {
	const { WeatherSongList } = props;

	const containerRef = useRef<HTMLUListElement | null>(null);

	const handleScroll = (e: WheelEvent<HTMLUListElement>) => {
		const container = containerRef.current;
		if (container) {
			container.scrollLeft += e.deltaY;
		}
	};

	const test = () => {
		console.log(1);
	};

	return (
		<SongSquareListContainer>
			<SongSubtitle colorSubtitle={WEATHER_TITLE[WeatherSongList.weather]} normalSubtitle="듣기 좋은 음악" />
			<SongSquareListScroll onWheel={handleScroll} ref={containerRef}>
				{WeatherSongList.songs.map((v) => (
					<SongSquareListContent key={v.youtubeId}>
						<SongSquareItems imgSrc={v.albumImg || ''} onClick={test} />
						<Text text={v.title} color="default" fontSize={14} />
						<Text text={v.artist} color="gray" fontSize={10} />
					</SongSquareListContent>
				))}
			</SongSquareListScroll>
		</SongSquareListContainer>
	);
}

export default SongSquareList;
