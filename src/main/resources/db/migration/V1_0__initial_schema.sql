CREATE SEQUENCE comments_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE feed_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE messages_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE reactions_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE comments
(
    created_at        TIMESTAMP(6) WITH TIME ZONE,
    id                BIGINT       NOT NULL,
    updated_at        TIMESTAMP(6) WITH TIME ZONE,
    author_id         UUID,
    post_id           UUID         NOT NULL,
    text_content      VARCHAR(255) NOT NULL,
    parent_comment_id BIGINT,
    PRIMARY KEY (id)
);

CREATE TABLE comment_images
(
    comment_id BIGINT NOT NULL,
    image_id   UUID   NOT NULL,
    PRIMARY KEY (comment_id, image_id)
);

CREATE TABLE comment_reactions
(
    comment_id  BIGINT NOT NULL,
    reaction_id BIGINT NOT NULL UNIQUE,
    PRIMARY KEY (comment_id, reaction_id)
);

CREATE TABLE feed
(
    id      BIGINT NOT NULL,
    post_id UUID   NOT NULL,
    user_id UUID   NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE followers
(
    participant_id UUID NOT NULL,
    users_id       UUID NOT NULL,
    PRIMARY KEY (participant_id, users_id)
);

CREATE TABLE friends
(
    user_1_id  UUID NOT NULL,
    users_2_id UUID NOT NULL,
    PRIMARY KEY (user_1_id, users_2_id)
);

CREATE TABLE groups
(
    is_private       BOOLEAN      NOT NULL,
    cover_photo_id   UUID,
    id               UUID         NOT NULL,
    profile_image_id UUID,
    handle           VARCHAR(255) NOT NULL UNIQUE,
    name             VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE groups_members
(
    groups_id  UUID NOT NULL,
    members_id UUID NOT NULL,
    PRIMARY KEY (groups_id, members_id)
);

CREATE TABLE images
(
    size       INTEGER      NOT NULL,
    created_at TIMESTAMP(6) WITH TIME ZONE,
    id         UUID         NOT NULL,
    owner_id   UUID         NOT NULL,
    mime_type  VARCHAR(255) NOT NULL,
    content    BYTEA,
    PRIMARY KEY (id)
);

CREATE TABLE image_reactions
(
    reaction_id BIGINT NOT NULL UNIQUE,
    image_id    UUID   NOT NULL,
    PRIMARY KEY (reaction_id, image_id)
);

CREATE TABLE messages
(
    created_at   TIMESTAMP(6) WITH TIME ZONE,
    id           BIGINT       NOT NULL,
    updated_at   TIMESTAMP(6) WITH TIME ZONE,
    receiver_id  UUID         NOT NULL,
    sender_id    UUID         NOT NULL,
    text_content VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE message_images
(
    message_id BIGINT NOT NULL,
    image_id   UUID   NOT NULL,
    PRIMARY KEY (message_id, image_id)
);

CREATE TABLE participant_request
(
    created_at  TIMESTAMP(6) WITH TIME ZONE,
    id          UUID         NOT NULL,
    receiver_id UUID         NOT NULL,
    sender_id   UUID         NOT NULL,
    type        VARCHAR(255) NOT NULL CHECK (type IN ('FOLLOW', 'FRIEND', 'JOIN')),
    PRIMARY KEY (id)
);

CREATE TABLE participants
(
    created_at TIMESTAMP(6) WITH TIME ZONE,
    updated_at TIMESTAMP(6) WITH TIME ZONE,
    id         UUID NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE password_change_requests
(
    id         UUID NOT NULL,
    request_id UUID,
    user_id    UUID NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE post
(
    created_at   TIMESTAMP(6) WITH TIME ZONE,
    updated_at   TIMESTAMP(6) WITH TIME ZONE,
    author_id    UUID,
    id           UUID         NOT NULL,
    space_id     UUID         NOT NULL,
    text_content VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE post_images
(
    image_id UUID NOT NULL,
    post_id  UUID NOT NULL,
    PRIMARY KEY (image_id, post_id)
);

CREATE TABLE post_reactions
(
    reaction_id BIGINT NOT NULL UNIQUE,
    post_id     UUID   NOT NULL,
    PRIMARY KEY (reaction_id, post_id)
);

CREATE TABLE message_reactions
(
    reaction_id BIGINT NOT NULL UNIQUE,
    message_id     BIGINT   NOT NULL,
    PRIMARY KEY (reaction_id, message_id)
);

CREATE TABLE reactions
(
    id      BIGINT NOT NULL,
    user_id UUID   NOT NULL,
    value   INTEGER DEFAULT 0,
    PRIMARY KEY (id)
);

CREATE TABLE system_config
(
    id    UUID         NOT NULL,
    key   VARCHAR(255) NOT NULL UNIQUE,
    value VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE users
(
    cover_photo_id   UUID,
    id               UUID         NOT NULL,
    profile_image_id UUID,
    bio              VARCHAR(255),
    city             VARCHAR(255),
    country          VARCHAR(255),
    email            VARCHAR(255) NOT NULL UNIQUE,
    full_name        VARCHAR(255) NOT NULL,
    handle           VARCHAR(255) NOT NULL UNIQUE,
    hometown         VARCHAR(255),
    password_hash    VARCHAR(255),
    phone_number     VARCHAR(255),
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS comments
    ADD CONSTRAINT comment_author_fk FOREIGN KEY (author_id) REFERENCES users;

ALTER TABLE IF EXISTS comments
    ADD CONSTRAINT comment_post_fk FOREIGN KEY (post_id) REFERENCES post;

ALTER TABLE IF EXISTS comment_images
    ADD CONSTRAINT comment_images_image_fk FOREIGN KEY (image_id) REFERENCES images;

ALTER TABLE IF EXISTS comment_images
    ADD CONSTRAINT comment_images_comment_fk FOREIGN KEY (comment_id) REFERENCES comments;

ALTER TABLE IF EXISTS comment_reactions
    ADD CONSTRAINT comment_reactions_reaction_fk FOREIGN KEY (reaction_id) REFERENCES reactions;

ALTER TABLE IF EXISTS comment_reactions
    ADD CONSTRAINT comment_reactions_comment_fk FOREIGN KEY (comment_id) REFERENCES comments;

ALTER TABLE IF EXISTS feed
    ADD CONSTRAINT feed_post_fk FOREIGN KEY (post_id) REFERENCES post;

ALTER TABLE IF EXISTS feed
    ADD CONSTRAINT feed_user_fk FOREIGN KEY (user_id) REFERENCES users;

ALTER TABLE IF EXISTS followers
    ADD CONSTRAINT followers_users_fk FOREIGN KEY (users_id) REFERENCES users;

ALTER TABLE IF EXISTS followers
    ADD CONSTRAINT followers_participant_fk FOREIGN KEY (participant_id) REFERENCES participants;

ALTER TABLE IF EXISTS friends
    ADD CONSTRAINT friends_users_2_fk FOREIGN KEY (users_2_id) REFERENCES users;

ALTER TABLE IF EXISTS friends
    ADD CONSTRAINT friends_user_1_fk FOREIGN KEY (user_1_id) REFERENCES users;

ALTER TABLE IF EXISTS groups
    ADD CONSTRAINT groups_cover_photo_fk FOREIGN KEY (cover_photo_id) REFERENCES images;

ALTER TABLE IF EXISTS groups
    ADD CONSTRAINT groups_profile_image_fk FOREIGN KEY (profile_image_id) REFERENCES images;

ALTER TABLE IF EXISTS groups
    ADD CONSTRAINT groups_participant_fk FOREIGN KEY (id) REFERENCES participants;

ALTER TABLE IF EXISTS groups_members
    ADD CONSTRAINT groups_members_member_fk FOREIGN KEY (members_id) REFERENCES users;

ALTER TABLE IF EXISTS groups_members
    ADD CONSTRAINT groups_members_group_fk FOREIGN KEY (groups_id) REFERENCES groups;

ALTER TABLE IF EXISTS image
    ADD CONSTRAINT image_owner_fk FOREIGN KEY (owner_id) REFERENCES participants;

ALTER TABLE IF EXISTS image_reactions
    ADD CONSTRAINT image_reactions_reaction_fk FOREIGN KEY (reaction_id) REFERENCES reactions;

ALTER TABLE IF EXISTS image_reactions
    ADD CONSTRAINT image_reactions_image_fk FOREIGN KEY (image_id) REFERENCES images;

ALTER TABLE IF EXISTS message
    ADD CONSTRAINT message_receiver_fk FOREIGN KEY (receiver_id) REFERENCES users;

ALTER TABLE IF EXISTS message
    ADD CONSTRAINT message_sender_fk FOREIGN KEY (sender_id) REFERENCES users;

ALTER TABLE IF EXISTS message_images
    ADD CONSTRAINT message_images_image_fk FOREIGN KEY (image_id) REFERENCES images;

ALTER TABLE IF EXISTS message_images
    ADD CONSTRAINT message_images_message_fk FOREIGN KEY (message_id) REFERENCES messages;

ALTER TABLE IF EXISTS participant_request
    ADD CONSTRAINT participant_request_receive_fk FOREIGN KEY (receiver_id) REFERENCES participants;

ALTER TABLE IF EXISTS participant_request
    ADD CONSTRAINT participant_request_send_fk FOREIGN KEY (sender_id) REFERENCES participants;

ALTER TABLE IF EXISTS password_change_requests
    ADD CONSTRAINT pass_change_requests_user_fk FOREIGN KEY (user_id) REFERENCES users;

ALTER TABLE IF EXISTS post
    ADD CONSTRAINT post_author_fk FOREIGN KEY (author_id) REFERENCES users;

ALTER TABLE IF EXISTS comments
    ADD CONSTRAINT post_parent_comment_fk FOREIGN KEY (parent_comment_id) REFERENCES comments ON DELETE CASCADE;

ALTER TABLE IF EXISTS post
    ADD CONSTRAINT post_space_fk FOREIGN KEY (space_id) REFERENCES participants ON DELETE CASCADE;

ALTER TABLE IF EXISTS post_images
    ADD CONSTRAINT post_images_image_fk FOREIGN KEY (image_id) REFERENCES images;

ALTER TABLE IF EXISTS post_images
    ADD CONSTRAINT post_images_post_fk FOREIGN KEY (post_id) REFERENCES post;

ALTER TABLE IF EXISTS post_reactions
    ADD CONSTRAINT post_reactions_reaction_fk FOREIGN KEY (reaction_id) REFERENCES reactions;

ALTER TABLE IF EXISTS post_reactions
    ADD CONSTRAINT post_reactions_post_fk FOREIGN KEY (post_id) REFERENCES post;

ALTER TABLE IF EXISTS message_reactions
    ADD CONSTRAINT message_reactions_reaction_fk FOREIGN KEY (reaction_id) REFERENCES reactions;

ALTER TABLE IF EXISTS message_reactions
    ADD CONSTRAINT message_reactions_message_fk FOREIGN KEY (message_id) REFERENCES messages;

ALTER TABLE IF EXISTS reactions
    ADD CONSTRAINT reaction_user_fk FOREIGN KEY (user_id) REFERENCES users;

ALTER TABLE IF EXISTS users
    ADD CONSTRAINT users_cover_photo_fk FOREIGN KEY (cover_photo_id) REFERENCES images;

ALTER TABLE IF EXISTS users
    ADD CONSTRAINT users_profile_image_fk FOREIGN KEY (profile_image_id) REFERENCES images;

ALTER TABLE IF EXISTS users
    ADD CONSTRAINT users_participant_fk FOREIGN KEY (id) REFERENCES participants;

-- DEFAULTS

INSERT INTO participants (created_at, updated_at, id)
VALUES ('2024-07-28 08:57:22.298105 +00:00', '2024-07-28 08:57:22.298159 +00:00',
        '94f450a6-b420-4288-9267-dce5f3b72bc5');

INSERT INTO users (cover_photo_id, id, profile_image_id, bio, city, country, email, full_name, handle, hometown,
                   password_hash, phone_number)
VALUES (null, '94f450a6-b420-4288-9267-dce5f3b72bc5', null, null, null, null, 'admin@cirkle.blog', 'admin', 'admin',
        null, '93a5bb80-2bbe-4953-a64b-7b1587612e13', null);

INSERT INTO system_config (id, key, value)
VALUES ('50a28842-af48-4275-b8d3-9f69707c181e', 'admin.user.id', '94f450a6-b420-4288-9267-dce5f3b72bc5'),
       ('cffd5999-4316-4bac-9d5c-0ec52153d2c4', 'default.image.profile', 'fe542c1e-bb03-4e45-9ee7-79668aac18c9'),
       ('a4816a07-fa07-4710-bb92-a28d68b12611', 'default.image.cover', '2faefd57-a40c-4a8e-8135-e62e0c2a5612');

INSERT INTO images (size, created_at, id, owner_id, mime_type, content)
VALUES (4330, '2024-07-28 08:57:22.307230 +00:00', 'fe542c1e-bb03-4e45-9ee7-79668aac18c9',
        '94f450a6-b420-4288-9267-dce5f3b72bc5', 'image/svg+xml',
        '<svg xmlns="http://www.w3.org/2000/svg" width="360" height="360" viewBox="0 0 360 360"><rect width="100%" height="100%" fill="#DDDDDD"/><path fill="#999999" d="m76.42 172.875-3.12-.55q.36-2.5 1.39-4.38 1.03-1.89 2.58-3.15t3.55-1.89q2.01-.64 4.31-.64 2.4 0 4.33.69 1.93.68 3.3 1.89 1.37 1.21 2.1 2.85.73 1.63.73 3.52 0 1.66-.37 2.92t-1.07 2.2q-.69.93-1.72 1.58-1.04.65-2.33 1.08 3.12.98 4.65 3 1.54 2.02 1.54 5.06 0 2.6-.96 4.59-.96 1.99-2.59 3.35-1.63 1.35-3.78 2.05-2.15.69-4.55.69-2.62 0-4.56-.6-1.94-.6-3.38-1.77-1.44-1.18-2.45-2.88-1.01-1.71-1.73-3.94l2.62-1.08q1.03-.43 1.93-.23.9.21 1.31.98.43.84.94 1.65.52.82 1.24 1.45.72.64 1.69 1.04.97.39 2.32.39 1.51 0 2.64-.49 1.13-.49 1.88-1.28.76-.8 1.13-1.77.37-.97.37-1.95 0-1.25-.26-2.27-.27-1.02-1.11-1.74-.84-.72-2.41-1.13-1.57-.41-4.21-.41v-4.22q2.18-.03 3.62-.41t2.3-1.07q.85-.68 1.18-1.64.34-.96.34-2.11 0-2.45-1.37-3.72-1.37-1.28-3.65-1.28-1.05 0-1.94.3-.89.3-1.6.84-.7.54-1.18 1.26t-.72 1.59q-.41 1.1-1.07 1.46-.66.36-1.86.17Zm44.95-10.22-10.3 12.6-1.2 1.48q1.01-.5 2.18-.78 1.16-.27 2.5-.27 1.97 0 3.83.65 1.86.64 3.28 1.95 1.41 1.31 2.28 3.25.86 1.95.86 4.54 0 2.42-.89 4.55-.88 2.12-2.49 3.71-1.61 1.58-3.88 2.49-2.27.91-5 .91-2.79 0-4.99-.88-2.21-.89-3.77-2.49-1.56-1.59-2.39-3.84-.83-2.24-.83-4.98 0-2.45 1-5.03.99-2.58 3.08-5.36l8.28-11.11q.43-.58 1.26-.99.83-.4 1.91-.4h5.28Zm-8.95 30.24q1.41 0 2.6-.48 1.19-.48 2.04-1.35.85-.86 1.33-2.03.48-1.16.48-2.53 0-1.49-.45-2.67-.46-1.19-1.3-2.02-.84-.83-2.01-1.26-1.18-.43-2.6-.43-1.41 0-2.56.48-1.16.48-1.97 1.33-.82.85-1.27 2.02-.46 1.16-.46 2.5 0 1.44.4 2.63.39 1.19 1.17 2.03.78.84 1.93 1.31 1.16.47 2.67.47Zm40.61-12.89q0 4.53-.98 7.88-.97 3.35-2.68 5.53-1.72 2.19-4.06 3.26-2.34 1.06-5.05 1.06t-5.03-1.06q-2.32-1.07-4.02-3.26-1.7-2.18-2.66-5.53-.96-3.35-.96-7.88 0-4.56.96-7.9.96-3.33 2.66-5.52 1.7-2.18 4.02-3.25 2.32-1.07 5.03-1.07 2.71 0 5.05 1.07t4.06 3.25q1.71 2.19 2.68 5.52.98 3.34.98 7.9Zm-6.12 0q0-3.77-.56-6.24-.55-2.47-1.47-3.94-.93-1.46-2.13-2.05-1.2-.59-2.49-.59-1.27 0-2.46.59t-2.1 2.05q-.91 1.47-1.45 3.94-.54 2.47-.54 6.24t.54 6.24q.54 2.47 1.45 3.93.91 1.47 2.1 2.06 1.19.58 2.46.58 1.29 0 2.49-.58 1.2-.59 2.13-2.06.92-1.46 1.47-3.93.56-2.47.56-6.24Zm44.23 9.41-3.15 3.09-8.13-8.13-8.21 8.18-3.14-3.1 8.2-8.23-7.82-7.82 3.12-3.12 7.82 7.82 7.78-7.77 3.17 3.12-7.8 7.8 8.16 8.16Zm19.97-16.54-3.12-.55q.36-2.5 1.39-4.38 1.03-1.89 2.58-3.15t3.55-1.89q2-.64 4.31-.64 2.4 0 4.33.69 1.93.68 3.3 1.89 1.37 1.21 2.1 2.85.73 1.63.73 3.52 0 1.66-.37 2.92t-1.07 2.2q-.69.93-1.73 1.58-1.03.65-2.32 1.08 3.12.98 4.65 3 1.54 2.02 1.54 5.06 0 2.6-.96 4.59-.96 1.99-2.59 3.35-1.64 1.35-3.78 2.05-2.15.69-4.55.69-2.62 0-4.56-.6-1.95-.6-3.39-1.77-1.44-1.18-2.44-2.88-1.01-1.71-1.73-3.94l2.61-1.08q1.04-.43 1.94-.23.9.21 1.3.98.44.84.95 1.65.52.82 1.24 1.45.72.64 1.69 1.04.97.39 2.32.39 1.51 0 2.64-.49 1.12-.49 1.88-1.28.76-.8 1.13-1.77.37-.97.37-1.95 0-1.25-.26-2.27-.27-1.02-1.11-1.74-.84-.72-2.41-1.13-1.57-.41-4.21-.41v-4.22q2.18-.03 3.62-.41t2.29-1.07q.86-.68 1.19-1.64.34-.96.34-2.11 0-2.45-1.37-3.72-1.37-1.28-3.65-1.28-1.05 0-1.94.3-.89.3-1.6.84-.71.54-1.19 1.26t-.72 1.59q-.4 1.1-1.06 1.46-.66.36-1.86.17Zm44.95-10.22-10.3 12.6-1.2 1.48q1.01-.5 2.17-.78 1.17-.27 2.51-.27 1.97 0 3.83.65 1.86.64 3.28 1.95 1.41 1.31 2.28 3.25.86 1.95.86 4.54 0 2.42-.89 4.55-.89 2.12-2.49 3.71-1.61 1.58-3.88 2.49-2.27.91-5 .91-2.79 0-5-.88-2.2-.89-3.76-2.49-1.56-1.59-2.39-3.84-.83-2.24-.83-4.98 0-2.45 1-5.03.99-2.58 3.08-5.36l8.28-11.11q.43-.58 1.26-.99.83-.4 1.91-.4h5.28Zm-8.95 30.24q1.41 0 2.6-.48 1.19-.48 2.04-1.35.85-.86 1.33-2.03.48-1.16.48-2.53 0-1.49-.45-2.67-.46-1.19-1.3-2.02-.84-.83-2.02-1.26-1.17-.43-2.59-.43-1.41 0-2.57.48-1.15.48-1.96 1.33-.82.85-1.28 2.02-.45 1.16-.45 2.5 0 1.44.39 2.63.4 1.19 1.18 2.03.78.84 1.93 1.31 1.15.47 2.67.47Zm40.6-12.89q0 4.53-.97 7.88-.97 3.35-2.69 5.53-1.71 2.19-4.05 3.26-2.34 1.06-5.05 1.06-2.72 0-5.03-1.06-2.32-1.07-4.02-3.26-1.71-2.18-2.67-5.53t-.96-7.88q0-4.56.96-7.9.96-3.33 2.67-5.52 1.7-2.18 4.02-3.25 2.31-1.07 5.03-1.07 2.71 0 5.05 1.07t4.05 3.25q1.72 2.19 2.69 5.52.97 3.34.97 7.9Zm-6.12 0q0-3.77-.55-6.24t-1.47-3.94q-.93-1.46-2.13-2.05-1.2-.59-2.49-.59-1.28 0-2.46.59-1.19.59-2.1 2.05-.92 1.47-1.46 3.94-.54 2.47-.54 6.24t.54 6.24q.54 2.47 1.46 3.93.91 1.47 2.1 2.06 1.18.58 2.46.58 1.29 0 2.49-.58 1.2-.59 2.13-2.06.92-1.46 1.47-3.93.55-2.47.55-6.24Z"/></svg>'),
       (3652, '2024-07-28 08:57:22.308824 +00:00', '2faefd57-a40c-4a8e-8135-e62e0c2a5612',
        '94f450a6-b420-4288-9267-dce5f3b72bc5', 'image/svg+xml',
        '<svg xmlns="http://www.w3.org/2000/svg" width="851" height="315" viewBox="0 0 851 315"><rect width="100%" height="100%" fill="#DDDDDD"/><path fill="#999999" d="M202.35 188.35q3.45 0 6.05-.99 2.6-.99 4.38-2.74 1.77-1.75 2.68-4.18.9-2.43.9-5.25 0-6.73-3.73-10.18-3.73-3.44-10.28-3.44-6.55 0-10.28 3.44-3.73 3.45-3.73 10.18 0 2.82.9 5.25.91 2.43 2.69 4.18 1.78 1.75 4.38 2.74 2.59.99 6.04.99Zm0-62.09q-3.11 0-5.37.96-2.26.96-3.75 2.57-1.5 1.61-2.21 3.7-.7 2.09-.7 4.41 0 2.42.59 4.71t2.01 4.07q1.41 1.78 3.72 2.85 2.32 1.08 5.71 1.08t5.71-1.08q2.31-1.07 3.73-2.85 1.41-1.78 2-4.07.6-2.29.6-4.71 0-2.32-.74-4.41-.73-2.09-2.2-3.7-1.47-1.61-3.73-2.57-2.26-.96-5.37-.96Zm13.79 29.49q7.45 2.43 11.01 7.43 3.56 5 3.56 12.23 0 5.42-2.06 9.83t-5.79 7.52q-3.73 3.1-8.95 4.8-5.23 1.69-11.56 1.69t-11.55-1.69q-5.23-1.7-8.96-4.8-3.73-3.11-5.79-7.52t-2.06-9.83q0-7.23 3.56-12.23t11.01-7.43q-5.93-2.49-8.89-7.06-2.97-4.58-2.97-11.02 0-4.63 1.89-8.65 1.9-4.01 5.29-6.97 3.39-2.97 8.1-4.64 4.72-1.66 10.37-1.66 5.65 0 10.37 1.66 4.72 1.67 8.11 4.64 3.39 2.96 5.28 6.97 1.89 4.02 1.89 8.65 0 6.44-2.96 11.02-2.97 4.57-8.9 7.06Zm44.8-26.44-2.88 17q2.71-.56 5.2-.82 2.48-.25 4.8-.25 6.44 0 11.36 1.92 4.91 1.92 8.25 5.31 3.33 3.39 5.02 7.94 1.7 4.55 1.7 9.8 0 6.5-2.29 11.87-2.29 5.37-6.36 9.18-4.06 3.81-9.63 5.9-5.56 2.09-12.12 2.09-3.84 0-7.29-.79-3.44-.79-6.47-2.12-3.02-1.32-5.59-3.05-2.57-1.72-4.6-3.64l4.29-5.93q1.36-1.92 3.56-1.92 1.41 0 2.88.9t3.36 1.98q1.9 1.07 4.47 1.97 2.57.91 6.18.91 3.85 0 6.78-1.24 2.94-1.25 4.89-3.48 1.95-2.23 2.94-5.31.99-3.08.99-6.7 0-6.66-3.87-10.42t-11.44-3.76q-5.82 0-11.93 2.15l-8.7-2.49 6.78-39.66h40.34v5.93q0 3-1.86 4.86-1.86 1.87-6.33 1.87h-22.43Zm83.51 58.59h14.69v10.45h-45.2V187.9h16.55v-47.63q0-2.83.17-5.82l-11.75 9.83q-1.02.79-2.01.99-.98.2-1.86.03-.88-.17-1.55-.6-.68-.42-1.02-.93l-4.41-6.04 24.92-21.19h11.47v71.36Zm108.65-8.25-7.4 7.29-19.16-19.16-19.32 19.27-7.4-7.29 19.32-19.38-18.42-18.42 7.35-7.34 18.42 18.42 18.3-18.31 7.46 7.35-18.36 18.36 19.21 19.21Zm47.01-38.93-7.35-1.3q.85-5.88 3.28-10.31 2.43-4.44 6.07-7.4 3.65-2.97 8.36-4.47 4.72-1.49 10.15-1.49 5.65 0 10.19 1.61 4.55 1.61 7.77 4.46 3.22 2.85 4.95 6.7 1.72 3.84 1.72 8.3 0 3.9-.88 6.87-.87 2.96-2.51 5.17-1.64 2.2-4.07 3.72-2.43 1.53-5.48 2.55 7.35 2.31 10.96 7.06 3.62 4.75 3.62 11.92 0 6.1-2.26 10.79-2.26 4.69-6.1 7.88-3.85 3.2-8.9 4.84-5.06 1.63-10.71 1.63-6.16 0-10.73-1.41-4.58-1.41-7.97-4.18-3.39-2.77-5.76-6.78-2.38-4.01-4.07-9.27l6.16-2.54q2.43-1.02 4.54-.53 2.12.48 3.08 2.28 1.02 1.98 2.24 3.9 1.21 1.92 2.91 3.42 1.69 1.5 3.98 2.43 2.29.93 5.45.93 3.56 0 6.22-1.16 2.65-1.15 4.43-3.02 1.78-1.86 2.66-4.15.87-2.29.87-4.61 0-2.93-.62-5.34-.62-2.4-2.6-4.09-1.98-1.7-5.68-2.66-3.7-.96-9.91-.96v-9.94q5.14-.06 8.53-.96 3.39-.91 5.4-2.52 2-1.61 2.79-3.87t.79-4.97q0-5.76-3.22-8.76-3.22-2.99-8.59-2.99-2.48 0-4.57.71-2.09.7-3.76 1.97t-2.8 2.97q-1.13 1.69-1.69 3.73-.96 2.6-2.52 3.44-1.55.85-4.37.4Zm95.88 47.18h14.69v10.45h-45.2V187.9h16.55v-47.63q0-2.83.17-5.82l-11.75 9.83q-1.02.79-2.01.99-.99.2-1.86.03-.88-.17-1.56-.6-.67-.42-1.01-.93l-4.41-6.04 24.92-21.19h11.47v71.36Zm47.57-58.59-2.88 17q2.71-.56 5.2-.82 2.48-.25 4.8-.25 6.44 0 11.36 1.92 4.91 1.92 8.24 5.31 3.34 3.39 5.03 7.94 1.7 4.55 1.7 9.8 0 6.5-2.29 11.87-2.29 5.37-6.36 9.18-4.06 3.81-9.63 5.9-5.57 2.09-12.12 2.09-3.84 0-7.29-.79-3.44-.79-6.47-2.12-3.02-1.32-5.59-3.05-2.57-1.72-4.61-3.64l4.3-5.93q1.35-1.92 3.56-1.92 1.41 0 2.88.9t3.36 1.98q1.89 1.07 4.46 1.97 2.58.91 6.19.91 3.84 0 6.78-1.24 2.94-1.25 4.89-3.48 1.95-2.23 2.94-5.31.99-3.08.99-6.7 0-6.66-3.87-10.42t-11.45-3.76q-5.82 0-11.92 2.15l-8.7-2.49 6.78-39.66h40.34v5.93q0 3-1.86 4.86-1.87 1.87-6.33 1.87h-22.43Z"/></svg>');
