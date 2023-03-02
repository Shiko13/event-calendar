DROP TABLE IF EXISTS users cascade;
DROP TABLE IF EXISTS categories cascade;
DROP TABLE IF EXISTS events cascade;
DROP TABLE IF EXISTS requests cascade;
DROP TABLE IF EXISTS compilations cascade;
DROP TABLE IF EXISTS event_compilations cascade;

CREATE TABLE users
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    name    VARCHAR(64)                             NOT NULL CHECK (name <> ''),
    email   VARCHAR(64)                             NOT NULL UNIQUE CHECK (email <> ''),
    password   VARCHAR(64),
    role VARCHAR(64)
);

CREATE TABLE categories
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    name        VARCHAR(255)                            NOT NULL CHECK (name <> '')
);

CREATE TABLE events
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY    NOT NULL PRIMARY KEY,
    annotation         VARCHAR(2000)                              NOT NULL CHECK (annotation <> ''),
    category_id        BIGINT REFERENCES categories (id),
    description        VARCHAR(7000) CHECK (description <> ''),
    event_date         TIMESTAMP WITHOUT TIME ZONE                NOT NULL,
    lat                FLOAT                                      NOT NULL,
    lon                FLOAT                                      NOT NULL,
    initiator_id       BIGINT REFERENCES users (id)          ,
    paid               BOOLEAN                                    NOT NULL,
    participant_limit  BIGINT                                     NOT NULL,
    request_moderation BOOLEAN                                    NOT NULL,
    title              VARCHAR(120)                               NOT NULL CHECK (title <> ''),
    created            TIMESTAMP WITHOUT TIME ZONE                NOT NULL,
    published            TIMESTAMP WITHOUT TIME ZONE                ,
    state              VARCHAR                                    NOT NULL
);

CREATE TABLE requests
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    requester_id             BIGINT REFERENCES users (id)   ,
    event_id                 BIGINT REFERENCES events (id) ,
    status                   VARCHAR ,
    created                  TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE compilations
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    pinned         BOOLEAN      NOT NULL,
    title          VARCHAR(120) NOT NULL CHECK (title <> '')
);

CREATE TABLE event_compilations
(
    compilation_id BIGINT REFERENCES compilations (id) ,
    event_id       BIGINT REFERENCES events (id)             ,
    UNIQUE (compilation_id, event_id)
);