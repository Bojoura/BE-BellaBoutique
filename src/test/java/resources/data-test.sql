INSERT INTO users (username, password, email)
VALUES
    ('test_customer', '$2a$12$FxmIi7YZreokp0zhzWxIkOHvPGHxkBXFsBQJNxlsVcTMvDEtkQwGO', 'test_customer@test.nl'),
    ('test_employee', '$2a$12$FxmIi7YZreokp0zhzWxIkOHvPGHxkBXFsBQJNxlsVcTMvDEtkQwGO', 'test_employee@test.nl'),
    ('test_admin', '$2a$12$FxmIi7YZreokp0zhzWxIkOHvPGHxkBXFsBQJNxlsVcTMvDEtkQwGO', 'test_admin@test.nl');

INSERT INTO authorities (user_id, authority)
VALUES
    (1, 'ROLE_CUSTOMER'),
    (2, 'ROLE_EMPLOYEE'),
    (3, 'ROLE_ADMIN');

INSERT INTO products (id, title, description, category, price, discount_percentage, rating, stock, sku, weight, width, height, depth, warranty_information, shipping_information, availability_status, return_policy, minimum_order_quantity, created_at, updated_at, barcode, qr_code, thumbnail)
VALUES
    (1, 'Test Product 1', 'Lipstick Bruin', 'beauty', 5.99, 5.0, 4.5, 10, 'SKU12345', 1.5, 15.0, 10.0, 5.0, '6 months warranty', 'Ships in 2 weeks', 'In Stock', '14 days return policy', 1, '2024-05-23 08:56:21', '2024-05-23 08:56:21', '1234567890123', 'https://assets.dummyjson.com/public/qr-code.png', 'https://cdn.dummyjson.com/products/images/test-product-1.png'),
    (2, 'Test Product 2', 'Lipstick Bordeaux', 'beauty', 5.99, 10.0, 3.8, 20, 'SKU67890', 1.0, 10.0, 8.0, 3.0, '1 year warranty', 'Ships in 1 week', 'Low Stock', '30 days return policy', 2, '2024-05-23 08:56:21', '2024-05-23 08:56:21', '9876543210987', 'https://assets.dummyjson.com/public/qr-code.png', 'https://cdn.dummyjson.com/products/images/test-product-2.png');

INSERT INTO product_tags (product_id, tags)
VALUES
    (1, 'beauty'),
    (1, 'test_tag_1'),
    (2, 'beauty'),
    (2, 'test_tag_2');

INSERT INTO product_images (product_id, images_url)
VALUES
    (1, 'https://cdn.dummyjson.com/products/images/test-product-1/1.png'),
    (2, 'https://cdn.dummyjson.com/products/images/test-product-2/1.png');

INSERT INTO review (product_id, rating, comment, review_date, reviewer_name, reviewer_email)
VALUES
    (1, 4, 'Good quality for the price.', '2024-05-23 08:56:21', 'Test User 1', 'test.user1@test.com'),
    (1, 3, 'Average product.', '2024-05-23 08:56:21', 'Test User 2', 'test.user2@test.com'),
    (2, 5, 'Excellent!', '2024-05-23 08:56:21', 'Test User 3', 'test.user3@test.com'),
    (2, 2, 'Not very happy with the purchase.', '2024-05-23 08:56:21', 'Test User 4', 'test.user4@test.com');
