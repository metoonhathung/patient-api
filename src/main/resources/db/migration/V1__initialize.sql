CREATE TABLE IF NOT EXISTS patient (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR (255) NOT NULL,
    gender VARCHAR (50) NOT NULL,
    age INT NOT NULL,
    email VARCHAR (50),
    phone VARCHAR (50) NOT NULL,
    created TIMESTAMP NOT NULL,
    updated TIMESTAMP NOT NULL
);