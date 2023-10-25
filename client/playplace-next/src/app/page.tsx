'use client';

import Link from 'next/link';
import TextTest from './../components/molecules/TextTest';

export default function Home() {
	return (
		<main>
			<h1>PlayPlace Home</h1>
			<h2>함께 만드는 위치 기반 공유 플레이리스트</h2>
			<h3>C109</h3>
			<TextTest />
			<ul>
				<li>
					<Link href="/">메인페이지(여기)</Link>
				</li>
				<li>
					<Link href="/login">로그인</Link>
				</li>
				<li>
					<Link href="/signup">회원가입</Link>
				</li>
				<li>
					<Link href="/map">플레이맵</Link>
				</li>
				<li>
					<Link href="/radar">플레이더</Link>
				</li>
				<li>
					<Link href="/search">검색</Link>
				</li>
			</ul>
		</main>
	);
}
