
CREATE TABLE cart_items (
    game_id bigint NOT NULL,
    cart_id bigint NOT NULL
);


CREATE TABLE carts (
    id bigint NOT NULL,
    user_id bigint
);


CREATE TABLE developers (
    id bigint NOT NULL,
    name character varying(255),
    website character varying(255)
);


CREATE TABLE game_genres (
    game_id bigint NOT NULL,
    genre_id bigint NOT NULL
);


CREATE TABLE games (
    id bigint NOT NULL,
    average_rating double precision,
    description character varying(255),
    price numeric(38,2),
    release_date date,
    title character varying(255),
    developer_id bigint,
    publisher_id bigint
);


CREATE TABLE genres (
    id bigint NOT NULL,
    name character varying(255)
);


CREATE TABLE publishers (
    id bigint NOT NULL,
    name character varying(255),
    website character varying(255)
);


CREATE TABLE purchases (
    id bigint NOT NULL,
    comment character varying(255),
    price_paid numeric(38,2),
    purchase_date date,
    game_id bigint,
    receiver_id bigint,
    sender_id bigint
);


CREATE TABLE reviews (
    id bigint NOT NULL,
    comment character varying(255),
    created_at date,
    rating bigint,
    game_id bigint,
    user_id bigint
);


CREATE TABLE users (
    id bigint NOT NULL,
    email character varying(255),
    join_date date,
    password character varying(255),
    role character varying(255),
    username character varying(255)
);
