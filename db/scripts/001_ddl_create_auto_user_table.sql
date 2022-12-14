create table auto_user (
                           id SERIAL PRIMARY KEY,
                           login varchar UNIQUE,
                           password varchar
);