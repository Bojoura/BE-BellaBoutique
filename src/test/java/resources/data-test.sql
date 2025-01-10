DELETE FROM review;
DELETE FROM product_images;
DELETE FROM products;
DELETE FROM authorities;
DELETE FROM users;

INSERT INTO users (username, password, email) 
VALUES 
    ('test_customer', '$2a$12$FxmIi7YZreokp0zhzWxIkOHvPGHxkBXFsBQJNxlsVcTMvDEtkQwGO', 'test_customer@test.nl'),
    ('test_admin', '$2a$12$FxmIi7YZreokp0zhzWxIkOHvPGHxkBXFsBQJNxlsVcTMvDEtkQwGO', 'test_admin@test.nl');

INSERT INTO authorities (user_id, authority) 
VALUES 
    (1, 'ROLE_CUSTOMER'),
    (2, 'ROLE_ADMIN');

INSERT INTO products (title, description, category, price, stock, sku) 
VALUES 
    ('Test Product 1', 'Test Description 1', 'beauty', 9.99, 10, 'TST001'),
    ('Test Product 2', 'Test Description 2', 'kleding', 19.99, 5, 'TST002');

INSERT INTO product_images (product_id, images_url)
VALUES
    (1, 'https://example.com/test1.jpg'),
    (2, 'https://example.com/test2.jpg');

INSERT INTO review (product_id, rating, comment, reviewer_name, reviewer_email)
VALUES
    (1, 5, 'Geweldig product!', 'Test User', 'test@test.com');
    (2, 4, 'Heerlijk product, maar iets te duur.', 'Test User', 'test@test.com');
    (3, 3, 'Goede kwaliteit, maar had meer verwacht voor de prijs.', 'Test User', 'test@test.com');
    (4, 5, 'Dit product heeft mijn verwachtingen helemaal overtroffen!', 'Test User', 'test@test.com');
    (5, 2, 'Helaas viel het tegen, niet zoals beschreven.', 'Test User', 'test@test.com');

