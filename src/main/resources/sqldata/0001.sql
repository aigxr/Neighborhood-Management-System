

-- NEIGHBORHOOD --!>

INSERT INTO
    `developer`(first_name, last_name, pesel, address, birth_date)
VALUES
    ('John', 'Kowalski', 12343212341, 'Poland, Łódź itp.', '2008-01-01');

INSERT INTO
    `owner`(first_name, last_name, pesel, address, birth_date)
VALUES
    ('John', 'Kowalski', 12343212341, 'Poland, Łódź itp.', '2008-01-01');

INSERT INTO
    `tenant`(first_name, last_name, pesel, address, birth_date)
VALUES
    ('John', 'Kowalski', 12343212341, 'Poland, Łódź itp.', '2008-01-01');

INSERT INTO
    `neighborhood`(developer_id, name, city, address)
VALUES
    (1, 'SOLID', 'Łódź', 'ul. Potrzebna 23 95-040');
--
-- -- BLOCK --!>
--
INSERT INTO
    `block`(name, neighborhood_id)
VALUES
    ('A', 1),
    ('B', 1);

-- FLAT --!>

INSERT INTO
    `flat`(block_id, owner_id, tenant_id, room_id)
VALUES
    (1, 1, 1, 1),
    (1, 1, 1, 1);

-- PERSON --!>

