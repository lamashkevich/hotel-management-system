DELETE FROM hotel_amenities;
DELETE FROM hotels;
DELETE FROM arrival_times;
DELETE FROM contacts;
DELETE FROM addresses;

ALTER SEQUENCE addresses_seq RESTART WITH 1;
ALTER SEQUENCE contacts_seq RESTART WITH 1;
ALTER SEQUENCE arrival_times_seq RESTART WITH 1;
ALTER SEQUENCE hotels_seq RESTART WITH 1;
ALTER SEQUENCE hotel_amenity_seq RESTART WITH 1;

INSERT INTO addresses (id, house_number, street, city, country, post_code)
VALUES
    (1, 10, 'Street1', 'Minsk', 'Belarus', '100000'),
    (2, 20, 'Street2', 'Grodno', 'BELARUS', '200000'),
    (3, 20, 'Street3', 'grodno', 'BLR', '200000'),
    (4, 20, 'Street4', 'Minsk', 'BELARUS', '200000'),
    (5, 30, 'Street5', 'Brest', 'BLR', '300000'),
    (6, 30, 'Street6', 'minsk', 'Belarus', '300000');

INSERT INTO contacts (id, phone, email)
VALUES
    (1, '+375-17-123-45-67', 'info@hotel1.com'),
    (2, '+375-29-123-45-67', 'info@hotel2.com'),
    (3, '+375-33-123-45-67', 'info@hotel3.com'),
    (4, '+375-33-123-45-67', 'info@hotel4.com'),
    (5, '+375-33-123-45-67', 'info@hotel5.com'),
    (6, '+375-33-123-45-67', 'info@hotel6.com');

INSERT INTO arrival_times (id, check_in, check_out)
VALUES
    (1, '11:00', '10:10'),
    (2, '12:00', '10:20'),
    (3, '13:00', '10:30'),
    (4, '13:00', '10:30'),
    (5, '13:00', '10:30'),
    (6, '13:00', '10:30');

INSERT INTO hotels (id, name, description, brand, address_id, contacts_id, arrival_time_id)
VALUES
    (1, 'Hotel #1', 'Hotel #1 in Belarus', 'Brand1', 1, 1, 1),
    (2, 'Hotel #2', 'Hotel #2 in Belarus', 'Brand2', 2, 2, 2),
    (3, 'Hotel #3', 'Hotel #3 in Belarus', 'Brand3', 3, 3, 3),
    (4, 'Hotel #4', 'Hotel #4 in Belarus', 'Brand1', 4, 4, 4),
    (5, 'Hotel #5', 'Hotel #5 in Belarus', 'Brand1', 5, 5, 5),
    (6, 'Hotel #6', 'Hotel #6 in Belarus', 'Brand2', 6, 6, 6);

INSERT INTO hotel_amenities (id, name, hotel_id)
VALUES
    (1, 'Free parking', 1),
    (2, 'Free WiFi', 1),
    (3, 'concierge', 1),
    (4, 'free WiFi', 2),
    (5, 'Concierge', 2),
    (6, 'On-site restaurant', 2),
    (7, 'Free WiFi', 3),
    (8, 'Fitness center', 3),
    (9, 'Non-smoking rooms', 3),
    (10, 'Non-smoking rooms', 4),
    (11, 'Non-smoking rooms', 5),
    (12, 'non-smoking rooms', 6);

ALTER SEQUENCE addresses_seq RESTART WITH 7;
ALTER SEQUENCE contacts_seq RESTART WITH 7;
ALTER SEQUENCE arrival_times_seq RESTART WITH 7;
ALTER SEQUENCE hotels_seq RESTART WITH 7;
ALTER SEQUENCE hotel_amenity_seq RESTART WITH 13;
