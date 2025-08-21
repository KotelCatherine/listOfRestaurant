CREATE TABLE IF NOT EXISTS restaurants
(

    id           uuid PRIMARY KEY,
    version_id   bigint    NOT NULL DEFAULT 1,
    name         text      NOT NULL,
    description  text,
    phone        text,
    email        text,
    website      text,
    status       text      NOT NULL,
    opening_date date,
    created_at   timestamp NOT NULL,
    created_by   text      NOT NULL,
    updated_at   timestamp,
    updated_by   text

);

CREATE TABLE IF NOT EXISTS cuisines
(

    id          uuid PRIMARY KEY,
    version_id  bigint NOT NULL DEFAULT 1,
    name        text   NOT NULL,
    description text

);

CREATE TABLE IF NOT EXISTS restaurant_cuisines
(

    id            uuid PRIMARY KEY,
    restaurant_id uuid NOT NULL,
    cuisine_id    uuid NOT NULL

);

CREATE TABLE IF NOT EXISTS addresses
(

    id            uuid PRIMARY KEY,
    restaurant_id uuid NOT NULL,
    country       text NOT NULL,
    city          text NOT NULL,
    street        text NOT NULL,
    building      text NOT NULL,
    apartment     text,
    postal_code   text,
    latitude      double precision,
    longitude     double precision

);

CREATE TABLE IF NOT EXISTS restaurant_schedules
(
    id            uuid PRIMARY KEY,
    restaurant_id uuid     NOT NULL REFERENCES restaurants (id) ON DELETE CASCADE,
    day_of_week   smallint NOT NULL CHECK (day_of_week BETWEEN 1 AND 7), -- 1=Понедельник, 7=Воскресенье
    open_time     time     NOT NULL,
    close_time    time     NOT NULL,
    is_closed     boolean DEFAULT FALSE,
    CONSTRAINT valid_times CHECK (close_time > open_time OR is_closed)
);


CREATE TABLE IF NOT EXISTS menu_categories
(

    id            uuid PRIMARY KEY,
    restaurant_id uuid NOT NULL,
    name          text NOT NULL,
    description   text,
    position      integer

);

CREATE TABLE IF NOT EXISTS menu_items
(

    id               uuid PRIMARY KEY,
    menu_category_id uuid           NOT NULL,
    name             text           NOT NULL,
    description      text,
    price            numeric(10, 2) NOT NULL,
    position         integer,
    is_available     boolean DEFAULT TRUE,
    image_url        text

);

CREATE TABLE IF NOT EXISTS reviews
(
    id            uuid PRIMARY KEY,
    restaurant_id uuid NOT NULL,
    user_id       uuid NOT NULL,
    rating        smallint CHECK (rating BETWEEN 1 AND 5),
    comment       text
);


