--liquibase formatted sql
--changeset lamashkevich:1
CREATE SEQUENCE addresses_seq START WITH 1 INCREMENT BY 50;
CREATE TABLE IF NOT EXISTS addresses (
    id BIGINT NOT NULL DEFAULT nextval('addresses_seq'),
    house_number INT NOT NULL,
    street VARCHAR(255) NOT NULL,
    city VARCHAR(128) NOT NULL,
    country VARCHAR(128) NOT NULL,
    post_code VARCHAR(16) NOT NULL,
    CONSTRAINT addresses_pkey PRIMARY KEY (id)
);

--changeset lamashkevich:2
CREATE SEQUENCE contacts_seq START WITH 1 INCREMENT BY 50;
CREATE TABLE IF NOT EXISTS contacts (
    id BIGINT NOT NULL DEFAULT nextval('contacts_seq'),
    phone VARCHAR(32) NOT NULL,
    email VARCHAR(255) NOT NULL,
    CONSTRAINT contacts_pkey PRIMARY KEY (id)
);

--changeset lamashkevich:3
CREATE SEQUENCE arrival_times_seq START WITH 1 INCREMENT BY 50;
CREATE TABLE IF NOT EXISTS arrival_times (
    id BIGINT NOT NULL DEFAULT nextval('arrival_times_seq'),
    check_in VARCHAR(5) NOT NULL,
    check_out VARCHAR(5),
    CONSTRAINT arrival_times_pkey PRIMARY KEY (id)
);

--changeset lamashkevich:4
CREATE SEQUENCE hotels_seq START WITH 1 INCREMENT BY 50;
CREATE TABLE IF NOT EXISTS hotels (
    id BIGINT NOT NULL DEFAULT nextval('hotels_seq'),
    name VARCHAR(255) NOT NULL,
    description TEXT,
    brand VARCHAR(64) NOT NULL,
    address_id BIGINT REFERENCES addresses(id),
    contacts_id BIGINT REFERENCES contacts(id),
    arrival_time_id BIGINT REFERENCES arrival_times(id),
    CONSTRAINT hotels_pkey PRIMARY KEY (id)
);

--changeset lamashkevich:5
CREATE SEQUENCE hotel_amenity_seq START WITH 1 INCREMENT BY 50;
CREATE TABLE IF NOT EXISTS hotel_amenities (
    id BIGINT NOT NULL DEFAULT nextval('hotel_amenity_seq'),
    name VARCHAR(128) NOT NULL,
    hotel_id BIGINT REFERENCES hotels(id),
    CONSTRAINT hotel_amenities_pkey PRIMARY KEY (id)
);