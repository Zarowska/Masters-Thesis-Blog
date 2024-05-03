CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE objects
(
    id         UUID                        NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
    created_at TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    created_by UUID,
    updated_by UUID
);

CREATE TABLE participants
(
    id         UUID NOT NULL PRIMARY KEY REFERENCES objects (id) ON DELETE CASCADE,
    avatar_url TEXT NOT NULL,
    slug       TEXT NOT NULL UNIQUE
);

CREATE INDEX participants_slug_index ON participants (slug);

CREATE TABLE groups
(
    id    UUID NOT NULL PRIMARY KEY REFERENCES participants (id) ON DELETE CASCADE,
    title TEXT NOT NULL
);

CREATE TABLE users
(
    id              UUID    NOT NULL PRIMARY KEY REFERENCES participants (id) ON DELETE CASCADE,
    email_validated BOOLEAN NOT NULL,
    auth_id         TEXT,
    email           TEXT    NOT NULL UNIQUE,
    first_name      TEXT    NOT NULL,
    last_name       TEXT    NOT NULL,
    password_hash   TEXT    NOT NULL,
    role            TEXT    NOT NULL CHECK (role = ANY (ARRAY ['SYSTEM', 'ADMIN', 'USER']))
);

CREATE TABLE email_validations
(
    id       UUID NOT NULL PRIMARY KEY REFERENCES objects (id) ON DELETE CASCADE,
    code     UUID NOT NULL,
    user_ref UUID UNIQUE REFERENCES users on delete cascade
);

CREATE TABLE relations
(
    id         UUID NOT NULL PRIMARY KEY REFERENCES objects (id) ON DELETE CASCADE,
    owner_id   UUID NOT NULL REFERENCES participants on DELETE cascade,
    related_id UUID NOT NULL REFERENCES participants on delete cascade,
    type       TEXT NOT NULL
        CHECK (type = ANY (ARRAY ['BLOCKED', 'OWNER', 'ADMIN', 'MEMBER', 'FRIEND', 'FOLLOWER']))
);

CREATE TABLE relation_requests
(
    id           UUID NOT NULL PRIMARY KEY REFERENCES objects (id) ON DELETE CASCADE,
    initiator_id UUID NOT NULL REFERENCES participants on delete cascade,
    target_id    UUID NOT NULL REFERENCES participants on delete cascade,
    type         TEXT NOT NULL
        CHECK (type = ANY (ARRAY ['BLOCKED', 'OWNER', 'ADMIN', 'MEMBER', 'FRIEND', 'FOLLOWER']))
);

CREATE TABLE resources
(
    id        UUID NOT NULL PRIMARY KEY REFERENCES objects (id) ON DELETE CASCADE,
    author_id UUID NOT NULL REFERENCES users on delete cascade
);


CREATE TABLE image_files
(
    id            UUID  NOT NULL PRIMARY KEY REFERENCES objects (id) ON DELETE CASCADE,
    original_name TEXT  NOT NULL,
    media_type    TEXT  NOT NULL,
    content       bytea NOT NULL
);

CREATE TABLE images
(
    id            UUID NOT NULL PRIMARY KEY REFERENCES resources (id) ON DELETE CASCADE,
    image_file_id UUID references image_files (id) ON DELETE CASCADE
);

CREATE TABLE posts
(
    id     UUID    NOT NULL PRIMARY KEY REFERENCES resources (id) ON DELETE CASCADE,
    is_new BOOLEAN NOT NULL default false,
    slug   TEXT    NOT NULL UNIQUE
);

CREATE INDEX resources_slug_index ON posts (slug);

CREATE TABLE comments
(
    id        UUID NOT NULL PRIMARY KEY REFERENCES resources (id) ON DELETE CASCADE,
    parent_id UUID REFERENCES comments on delete cascade,
    post_id   UUID NOT NULL REFERENCES posts on delete cascade
);

CREATE INDEX comments_post_id_index ON comments (post_id);

CREATE TABLE texts
(
    id   UUID NOT NULL PRIMARY KEY REFERENCES resources (id) ON DELETE CASCADE,
    text TEXT NOT NULL
);

CREATE TABLE feed
(
    id             UUID NOT NULL PRIMARY KEY REFERENCES objects (id) ON DELETE CASCADE,
    participant_id UUID NOT NULL REFERENCES participants on delete cascade,
    post_id        UUID NOT NULL references posts on DELETE cascade
);

CREATE INDEX feed_participant_id_index ON feed (participant_id);

CREATE TABLE resources_resources
(
    resource_id           UUID NOT NULL REFERENCES resources on delete cascade,
    referred_resources_id UUID NOT NULL REFERENCES resources on DELETE cascade,
    PRIMARY KEY (resource_id, referred_resources_id)
);

CREATE TABLE reactions
(
    id          UUID    NOT NULL PRIMARY KEY REFERENCES objects (id) ON DELETE CASCADE,
    resource_id UUID    NOT NULL REFERENCES resources on delete cascade,
    author_id   UUID    NOT NULL REFERENCES users on delete cascade,
    value       INTEGER NOT NULL
);

CREATE INDEX reactions_resource_id_index ON reactions (resource_id);

CREATE TABLE actor
(
    id                            UUID                        NOT NULL PRIMARY KEY REFERENCES objects (id) ON DELETE CASCADE,
    accept_friendship_probability INTEGER                     NOT NULL,
    comment_probability           INTEGER                     NOT NULL,
    follow_probability            INTEGER                     NOT NULL,
    friend_probability            INTEGER                     NOT NULL,
    like_probability              INTEGER                     NOT NULL,
    "new post_probability"        INTEGER                     NOT NULL,
    repost_probability            INTEGER                     NOT NULL,
    last_active                   TIMESTAMP(6) WITH TIME ZONE,
    registration_timestamp        TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    user_id                       UUID UNIQUE REFERENCES users on delete cascade,
    avatar_url                    TEXT                        NOT NULL,
    email                         TEXT                        NOT NULL UNIQUE,
    first_name                    TEXT                        NOT NULL,
    last_name                     TEXT                        NOT NULL,
    password                      TEXT                        NOT NULL,
    time_zone                     TEXT                        NOT NULL
);

CREATE TABLE actor_topics
(
    owner_id UUID NOT NULL REFERENCES actor on delete cascade,
    topic    TEXT
);

INSERT INTO objects(id, created_at, created_by, updated_at, updated_by)
values ('00000000-0000-0000-0000-000000000000', now(), null, now(), null);

update objects
set created_by = '00000000-0000-0000-0000-000000000000',
    updated_by = '00000000-0000-0000-0000-000000000000'
where id = '00000000-0000-0000-0000-000000000000';

ALTER TABLE objects
    ALTER COLUMN created_by SET NOT NULL;
ALTER TABLE objects
    ALTER COLUMN updated_by SET NOT NULL;

INSERT INTO participants (id, slug, avatar_url)
values ('00000000-0000-0000-0000-000000000000',
        'system',
        'https://cdn.cloudflare.steamstatic.com/steamcommunity/public/images/avatars/f8/f8f2658c4fffd9f24da4183ad1e293d0ca156c6b.jpg');

INSERT INTO users (id, email_validated, auth_id, email, first_name, last_name, password_hash, role)
values ('00000000-0000-0000-0000-000000000000', true, null,
        'system@circle.blog', 'System', '', '$2a$12$mfxXeMl0ufV2CDmWur19D.vNe/qUGESm.DUPaGL4breVVwUVhhTd2', 'SYSTEM');


