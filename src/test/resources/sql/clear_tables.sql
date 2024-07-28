DELETE FROM system_config;

DELETE FROM image_reactions CASCADE;

DELETE FROM comment_images CASCADE;
DELETE FROM comment_reactions CASCADE;
DELETE FROM comments CASCADE;

DELETE FROM post_images CASCADE;

DELETE FROM feed CASCADE;

DELETE FROM post_reactions CASCADE;
DELETE FROM post CASCADE;

DELETE FROM message_images CASCADE;
DELETE FROM message_reactions  CASCADE;
DELETE FROM messages CASCADE;

DELETE FROM image_reactions CASCADE;
DELETE FROM images CASCADE;

DELETE FROM reactions CASCADE;

DELETE FROM participant_request CASCADE;

DELETE FROM password_change_requests CASCADE;

DELETE FROM followers CASCADE;
DELETE FROM friends CASCADE;

DELETE FROM groups_members CASCADE;
DELETE FROM groups CASCADE;

DELETE FROM users CASCADE;
DELETE FROM participants CASCADE;
