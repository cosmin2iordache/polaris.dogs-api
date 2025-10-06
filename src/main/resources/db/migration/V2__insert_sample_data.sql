-- Sample supplier data
INSERT INTO supplier (id, name, contact_info)
SELECT 1, 'Paws Training Co.', 'paws@example.com'
WHERE NOT EXISTS (SELECT 1 FROM supplier WHERE id = 1);

INSERT INTO supplier (id, name, contact_info)
SELECT 2, 'Guardian K9 Ltd.', 'guardian@example.com'
WHERE NOT EXISTS (SELECT 1 FROM supplier WHERE id = 2);

INSERT INTO supplier (id, name, contact_info)
SELECT 3, 'Elite Dog Services', 'elite@example.com'
WHERE NOT EXISTS (SELECT 1 FROM supplier WHERE id = 3);

-- Sample dogs
INSERT INTO dog (name, breed, badge_id, gender, current_status, supplier_id, birth_date, date_acquired)
VALUES
    ('Max', 'Labrador', 'B123', 'MALE', 'IN_SERVICE', 1, '2020-03-15', '2021-01-10'),
    ('Bella', 'German Shepherd', 'B124', 'FEMALE', 'IN_TRAINING', 2, '2021-05-20', '2022-01-05'),
    ('Charlie', 'Belgian Malinois', 'B125', 'MALE', 'IN_SERVICE', 3, '2019-11-02', '2020-06-15'),
    ('Lucy', 'Golden Retriever', 'B126', 'FEMALE', 'LEFT', 1, '2022-02-10', '2023-04-12'),
    ('Rocky', 'Rottweiler', 'B127', 'MALE', 'RETIRED', 2, '2018-08-30', '2019-03-18'),
    ('Daisy', 'Springer Spaniel', 'B128', 'FEMALE', 'IN_TRAINING', 1, '2023-01-22', '2023-07-01'),
    ('Cooper', 'Border Collie', 'B129', 'MALE', 'IN_SERVICE', 3, '2020-06-10', '2021-02-11'),
    ('Molly', 'Doberman', 'B130', 'FEMALE', 'LEFT', 2, '2021-09-05', '2022-06-01'),
    ('Buddy', 'Labrador', 'B131', 'MALE', 'IN_SERVICE', 1, '2020-04-15', '2021-03-20'),
    ('Sadie', 'German Shepherd', 'B132', 'FEMALE', 'RETIRED', 3, '2017-12-09', '2018-07-07');
