CREATE TABLE "users"
(
    "id"         UUID NOT NULL PRIMARY KEY,
    "email"      TEXT NOT NULL UNIQUE,
    "name"       TEXT NOT NULL,
    "avatarUrl"  TEXT,
    "coverUrl"   TEXT,
    "password"   TEXT NOT NULL,
    "number"     INTEGER,
    "country"    TEXT,
    "address"    TEXT,
    "bio"        TEXT NOT NULL,
    "created_at" TIMESTAMP(0) WITHOUT TIME ZONE,
    "updated_at" TIMESTAMP(0) WITHOUT TIME ZONE
);

CREATE TABLE "files_info"
(
    "id"            UUID                           NOT NULL PRIMARY KEY,
    "owner"         UUID                           NOT NULL REFERENCES "users" ("id"),
    "file_path"     TEXT                           NOT NULL,
    "original_name" TEXT                           NOT NULL,
    "media_type"    TEXT                           NOT NULL,
    "size"          INTEGER                        NOT NULL,
    "width"         INTEGER                        NOT NULL,
    "height"        INTEGER                        NOT NULL,
    "uploaded_at"   TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL
);

CREATE TABLE "posts"
(
    "id"         UUID                           NOT NULL PRIMARY KEY,
    "author"     UUID                           NOT NULL REFERENCES "users" ("id"),
    "text_data"  TEXT,
    "created_at" TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
    "updated_at" TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL
);

CREATE TABLE "comments"
(
    "id"         UUID                           NOT NULL PRIMARY KEY,
    "author"     UUID                           NOT NULL REFERENCES "users" ("id"),
    "post_ref"   UUID                           NOT NULL REFERENCES "posts" ("id"),
    "text_data"  TEXT                           NOT NULL,
    "created_at" TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
    "updated_at" TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL
);

CREATE TABLE "requests"
(
    "id"         UUID                           NOT NULL PRIMARY KEY,
    "sender"     UUID                           NOT NULL REFERENCES "users" ("id"),
    "receiver"   UUID                           NOT NULL REFERENCES "users" ("id"),
    "created_at" TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL
);

ALTER TABLE "requests"
    ADD CONSTRAINT "unique_sender_receiver" UNIQUE ("sender", "receiver");

CREATE TABLE "post_images"
(
    "id"            UUID NOT NULL PRIMARY KEY,
    "post_ref"      UUID NOT NULL REFERENCES "posts" ("id"),
    "file_info_ref" UUID NOT NULL REFERENCES "files_info" ("id")
);

CREATE TABLE "post_reactions"
(
    "id"         UUID                           NOT NULL PRIMARY KEY,
    "author"     UUID                           NOT NULL REFERENCES "users" ("id"),
    "post_ref"   UUID                           NOT NULL REFERENCES "posts" ("id"),
    "user_ref"   UUID                           NOT NULL REFERENCES "users" ("id"),
    "type"       INTEGER                        NOT NULL,
    "created_at" TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL
);

CREATE TABLE "messages"
(
    "id"                 UUID                           NOT NULL PRIMARY KEY,
    "sender"             UUID                           NOT NULL REFERENCES "users" ("id"),
    "receiver"           UUID                           NOT NULL REFERENCES "users" ("id"),
    "text"               TEXT                           NOT NULL,
    "viewed_by_receiver" BOOLEAN                        NOT NULL,
    "created_at"         TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
    "updated_at"         TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL
);

CREATE TABLE "comment_reactions"
(
    "id"          UUID                           NOT NULL PRIMARY KEY,
    "comment_ref" UUID                           NOT NULL REFERENCES "comments" ("id"),
    "author"      UUID                           NOT NULL REFERENCES "users" ("id"),
    "type"        INTEGER                        NOT NULL,
    "created_at"  TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL
);

CREATE TABLE "user_friends"
(
    "id"         UUID                        NOT NULL PRIMARY KEY,
    "user_id"    UUID                        NOT NULL REFERENCES "users" ("id"),
    "friend_id"  UUID                        NOT NULL REFERENCES "users" ("id"),
    "created_at" TIMESTAMP(0) WITH TIME ZONE NOT NULL
);

ALTER TABLE "user_friends"
    ADD CONSTRAINT check_user_not_friend CHECK ("user_id" != "friend_id");


INSERT INTO "users" ("id", "email", "name", "password", "bio", "created_at", "updated_at")
VALUES ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'user1@example.com', 'User One', 'password123', 'This is user one',
        '2023-09-21 12:00:00', '2023-09-21 12:00:00'),
       ('f47ac10b-58cc-4372-a567-0e02b2c3d479', 'user2@example.com', 'User Two', 'password456', 'This is user two',
        '2023-09-22 12:00:00', '2023-09-22 12:00:00');

INSERT INTO "files_info" ("id", "owner", "file_path", "original_name", "media_type", "size", "width", "height",
                          "uploaded_at")
VALUES ('e170d6ae-5a8a-4c3d-a0fa-0cfd283d2878', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', '/path/to/file1.jpg',
        'file1.jpg', 'image/jpeg', 2048, 800, 600, '2023-09-21 12:10:00'),
       ('9fa646dc-4c89-40c5-90db-25383d2b6f7a', 'f47ac10b-58cc-4372-a567-0e02b2c3d479', '/path/to/file2.jpg',
        'file2.jpg', 'image/jpeg', 2048, 800, 600, '2023-09-22 12:10:00');

INSERT INTO "posts" ("id", "author", "text_data", "created_at", "updated_at")
VALUES ('7a75c4d4-239d-494d-8f59-5c92b23b2ad4', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'This is post one',
        '2023-09-21 12:20:00', '2023-09-21 12:20:00'),
       ('8c74c309-08f5-4208-9c61-5c99a4f9db02', 'f47ac10b-58cc-4372-a567-0e02b2c3d479', 'This is post two',
        '2023-09-22 12:20:00', '2023-09-22 12:20:00');

INSERT INTO "comments" ("id", "author", "post_ref", "text_data", "created_at", "updated_at")
VALUES ('23a9341a-5efb-4d2a-a498-3ee810c2949b', 'f47ac10b-58cc-4372-a567-0e02b2c3d479',
        '7a75c4d4-239d-494d-8f59-5c92b23b2ad4', 'Comment by user two on post one', '2023-09-21 12:30:00',
        '2023-09-21 12:30:00'),
       ('3c2f44fa-c742-41e7-8d3e-2d2c2e2b8c87', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
        '8c74c309-08f5-4208-9c61-5c99a4f9db02', 'Comment by user one on post two', '2023-09-22 12:30:00',
        '2023-09-22 12:30:00');

INSERT INTO "requests" ("id", "sender", "receiver", "created_at")
VALUES ('2e4fd2c9-d454-411d-8c4a-74714c1a7049', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
        'f47ac10b-58cc-4372-a567-0e02b2c3d479', '2023-09-21 12:40:00'),
       ('0ccee8c9-d0a2-47d6-a4db-235017f28a57', 'f47ac10b-58cc-4372-a567-0e02b2c3d479',
        'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', '2023-09-22 12:40:00');

INSERT INTO "post_images" ("id", "post_ref", "file_info_ref")
VALUES ('e8dc4081-ef2a-464c-9f64-4137c5f3243c', '7a75c4d4-239d-494d-8f59-5c92b23b2ad4',
        'e170d6ae-5a8a-4c3d-a0fa-0cfd283d2878'),
       ('eb91d7c6-c305-45dd-91d0-2bdba5f4c258', '8c74c309-08f5-4208-9c61-5c99a4f9db02',
        '9fa646dc-4c89-40c5-90db-25383d2b6f7a');

INSERT INTO "post_reactions" ("id", "author", "post_ref", "user_ref", "type", "created_at")
VALUES ('a23be45e-1234-4e6a-b4db-5a8ef2389d8c', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
        '7a75c4d4-239d-494d-8f59-5c92b23b2ad4', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 1, '2023-09-21 12:50:00'),
       ('b23fd45e-2345-4b7a-c5db-5b8ef2389e8f', 'f47ac10b-58cc-4372-a567-0e02b2c3d479',
        '8c74c309-08f5-4208-9c61-5c99a4f9db02', 'f47ac10b-58cc-4372-a567-0e02b2c3d479', 2, '2023-09-22 12:50:00');

INSERT INTO "messages" ("id", "sender", "receiver", "text", "viewed_by_receiver", "created_at", "updated_at")
VALUES ('a8934bcd-5678-4c9d-d5eb-6a7df2389f9c', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
        'f47ac10b-58cc-4372-a567-0e02b2c3d479', 'Hey there!', true, '2023-09-21 13:00:00', '2023-09-21 13:00:00'),
       ('b8934cde-6789-4d0a-e5fc-7b8df2389a0d', 'f47ac10b-58cc-4372-a567-0e02b2c3d479',
        'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'Hello!', false, '2023-09-22 13:00:00', '2023-09-22 13:00:00');

INSERT INTO "comment_reactions" ("id", "comment_ref", "author", "type", "created_at")
VALUES ('c23ed45e-7890-4a1b-a6fd-8c7df2389d1a', '23a9341a-5efb-4d2a-a498-3ee810c2949b',
        'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 1, '2023-09-21 13:10:00'),
       ('d23fe45e-8901-4b2c-b7fe-9c7ef2389b2b', '3c2f44fa-c742-41e7-8d3e-2d2c2e2b8c87',
        'f47ac10b-58cc-4372-a567-0e02b2c3d479', 2, '2023-09-22 13:10:00');

INSERT INTO "user_friends" ("id", "user_id", "friend_id", "created_at")
VALUES ('e23df45e-9012-4c3d-a8bf-0c6df2389d3a', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
        'f47ac10b-58cc-4372-a567-0e02b2c3d479', '2023-09-21 13:20:00'),
       ('f23da45e-0123-4d4e-b9cd-1d7df2389d4c', 'f47ac10b-58cc-4372-a567-0e02b2c3d479',
        'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', '2023-09-22 13:20:00');
