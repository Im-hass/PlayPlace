INSERT INTO users (user_id, nickname, profile_img, outh_id, is_removed, is_push, is_shake, created_date, modified_date)
VALUES (1, 'nickname1', 1, 'outh_id1', 0, 0, 1, NOW(), NOW()),
       (2, 'nickname2', 2, 'outh_id2', 0, 0, 1, NOW(), NOW()),
       (3, 'nickname3', 3, 'outh_id3', 1, 0, 1, NOW(), NOW());

INSERT INTO song (song_id, youtube_id, title, artist, album_img, play_time, created_date, modified_date)
VALUES (1, 'yId1', 'Title1', 'Artist1', 'AlbumImg1', 210, NOW(), NOW()),
       (2, 'yId2', 'Title2', 'Artist2', 'AlbumImg2', 240, NOW(), NOW()),
       (3, 'yId3', 'Title3', 'Artist3', 'AlbumImg3', 216, NOW(), NOW());

INSERT INTO landmark (landmark_id, title, latitude, longitude, representative_img)
VALUES (1, 'landmark1', 37.566535, 126.977969, "test1"),
       (2, 'landmark2', 37.551229, 126.988205, "test2"),
       (3, 'landmark3', 37.579617, 126.977041, "test3"),
       (4, 'landmark4', 37.579617, 126.977041, "test4"),
       (5, 'landmark5', 37.566535, 126.977969, "test5"),
       (6, 'landmark6', 37.551229, 126.988205, "test6"),
       (7, 'landmark7', 37.579617, 126.977041, "test7"),
       (8, 'landmark8', 37.579617, 126.977041, "test8"),
       (9, 'landmark9', 37.566535, 126.977969, "test9"),
       (10, 'landmark10', 37.551229, 126.988205, "test10");


INSERT INTO landmark_song (landmark_song_id, landmark_id, song_id, user_id, created_date,
                           modified_date)
VALUES (1, 1, 1, 1, NOW(), NOW()),
       (2, 1, 2, 2, NOW(), NOW()),
       (3, 2, 1, 2, NOW(), NOW()),
       (4, 3, 1, 1, NOW(), NOW()),
       (5, 3, 3, 1, NOW(), NOW());

INSERT INTO user_song (user_song_id, user_id, song_id, created_date, modified_date)
VALUES (1, 1, 1, NOW(), NOW());
INSERT INTO user_song (user_song_id, user_id, song_id, created_date, modified_date)
VALUES (2, 1, 2, NOW(), NOW());
INSERT INTO user_song (user_song_id, user_id, song_id, created_date, modified_date)
VALUES (3, 1, 3, NOW(), NOW());

INSERT INTO user_landmark_group (user_landmark_group_id, user_id, landmark_id, created_date, modified_date)
VALUES (1, 1, 1, NOW(), NOW()),
       (2, 1, 2, NOW(), NOW()),
       (3, 2, 2, NOW(), NOW());
