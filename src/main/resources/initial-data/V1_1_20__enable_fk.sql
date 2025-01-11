SELECT SETVAL('reactions_seq', (SELECT COALESCE(MAX(id) + 50, 1) FROM reactions));
SELECT SETVAL('messages_seq', (SELECT COALESCE(MAX(id) + 50, 1) FROM messages));
SELECT SETVAL('feed_seq', (SELECT COALESCE(MAX(id) + 50, 1) FROM feed));
SELECT SETVAL('comments_seq', (SELECT COALESCE(MAX(id) + 50, 1) FROM comments));

ALTER TABLE IF EXISTS users
    ADD CONSTRAINT users_profile_image_fk FOREIGN KEY (profile_image_id) REFERENCES images ON
DELETE
SET null;