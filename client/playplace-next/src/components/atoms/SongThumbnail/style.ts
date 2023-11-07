import Image from 'next/image';
import styled from 'styled-components';

interface SongThumbnailWrapperProps {
	$width: number;
	$height: number;
	$isFullSize: boolean;
}

const SongThumbnailWrapper = styled.div<SongThumbnailWrapperProps>`
	width: ${({ $isFullSize }) => ($isFullSize ? `100%` : `45px`)};
	height: ${({ $isFullSize }) => ($isFullSize ? `auto` : `45px`)};

	aspect-ratio: 1/1;
`;

export const SongThumbnailImage = styled(Image)`
	border-radius: var(--radius-s);
	width: 100%;
	height: 100%;
	aspect-ratio: 1/1;
`;

export default SongThumbnailWrapper;
