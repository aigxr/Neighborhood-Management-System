-- DEVELOPER --!>

INSERT INTO
    `developer`(first_name, last_name)
VALUES
    ('Igor', 'Siotor'),
    ('Jan', 'Kowalski');


-- OWNER --!>

INSERT INTO
    `owner`(first_name, last_name)
VALUES
    ('Jan', 'Kowalski'),
    ('Igor', 'Siotor');

-- NEIGHBORHOOD --!>

INSERT INTO
    `neighborhood`(developer, name, city, address)
VALUES
    (1, 'SOLID', 'Łódź', 'ul. Potrzebna 23 95-040');

-- BLOCK --!>

INSERT INTO
    `block`(name, neighborhood_id)
VALUES
    ('A', 1);
--     ('B', 1);

-- FLAT --!>

INSERT INTO
    `flat`(a_length, b_length, owner_id, block_id)
VALUES
    (30, 40 ,1, 1),
    (20, 30, 2, 1);

-- PERSON --!>

INSERT INTO
    `person`(first_name, last_name, pesel, address, birth_date)
VALUES
    ('John', 'Kowalski', 1234321234, 'Poland, Łódź itp.', '2008-01-01');