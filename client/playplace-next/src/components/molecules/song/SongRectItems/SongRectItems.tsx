import React from 'react';
import SongThumbnail from '@/components/atoms/SongThumbnail/SongThumbnail';
import SongRectItemsContainer from './style';

interface ISongRectItemsProps {
	imgSrc: string;
}

function SongRectItems(props: ISongRectItemsProps) {
	const { imgSrc } = props;

	return (
		<SongRectItemsContainer>
			{/* <SongThumbnail src={HypeBoy} $height={180} $width={130} /> */}
			<SongThumbnail src={imgSrc || ''} $height={180} $width={130} />
		</SongRectItemsContainer>
	);
}

export default SongRectItems;
