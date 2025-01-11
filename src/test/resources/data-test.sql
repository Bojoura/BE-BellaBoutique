DELETE FROM review;
DELETE FROM product_images;
DELETE FROM products;
DELETE FROM authorities;
DELETE FROM users;

INSERT INTO users (id, username, password, email)
VALUES 
    (1001, 'test_customer', '$2a$12$FxmIi7YZreokp0zhzWxIkOHvPGHxkBXFsBQJNxlsVcTMvDEtkQwGO', 'test_customer@test.nl'),
    (1002, 'test_admin', '$2a$12$FxmIi7YZreokp0zhzWxIkOHvPGHxkBXFsBQJNxlsVcTMvDEtkQwGO', 'test_admin@test.nl');

INSERT INTO authorities (user_id, authority) 
VALUES 
    (1001, 'ROLE_CUSTOMER'),
    (1002, 'ROLE_ADMIN');

INSERT INTO products (id, title, description, category, price, stock, sku)
VALUES 
    (1001,'Test Product 1', 'Test Description 1', 'beauty', 9.99, 10, 'TST001'),
    (1002,'Test Product 2', 'Test Description 2', 'kleding', 19.99, 5, 'TST002');

INSERT INTO product_images (product_id, images_url)
VALUES
    (1001, 'https://example.com/test1.jpg'),
    (1002, 'https://example.com/test2.jpg');

INSERT INTO review (product_id, rating, comment, reviewer_name, reviewer_email)
VALUES
    (1001, 5, 'Geweldig product!', 'Test User', 'test@test.com'),
    (1002, 4, 'Heerlijk product, maar iets te duur.', 'Test User', 'test@test.com');

