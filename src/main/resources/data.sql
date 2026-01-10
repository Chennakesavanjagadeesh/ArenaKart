-- Insert Users
INSERT INTO users (email, password, first_name, last_name, phone, role, created_at) VALUES
('admin@ecommerce.com', '$2a$10$xYQYBqKXZ9KrN8L5TqKqseCEWxBPzXVKp5IZj6XQZYYCQwZXZmCQy', 'Admin', 'User', '+1234567890', 'ADMIN', CURRENT_TIMESTAMP),
('john.doe@email.com', '$2a$10$xYQYBqKXZ9KrN8L5TqKqseCEWxBPzXVKp5IZj6XQZYYCQwZXZmCQy', 'John', 'Doe', '+1234567891', 'CUSTOMER', CURRENT_TIMESTAMP),
('jane.smith@email.com', '$2a$10$xYQYBqKXZ9KrN8L5TqKqseCEWxBPzXVKp5IZj6XQZYYCQwZXZmCQy', 'Jane', 'Smith', '+1234567892', 'CUSTOMER', CURRENT_TIMESTAMP),
('bob.wilson@email.com', '$2a$10$xYQYBqKXZ9KrN8L5TqKqseCEWxBPzXVKp5IZj6XQZYYCQwZXZmCQy', 'Bob', 'Wilson', '+1234567893', 'CUSTOMER', CURRENT_TIMESTAMP);

-- Insert Addresses
INSERT INTO addresses (street, city, state, zip_code, country, default_address, user_id) VALUES
('123 Admin St', 'New York', 'NY', '10001', 'USA', true, 1),
('456 Oak Avenue', 'Los Angeles', 'CA', '90001', 'USA', true, 2),
('789 Maple Drive', 'Los Angeles', 'CA', '90002', 'USA', false, 2),
('321 Pine Road', 'Chicago', 'IL', '60601', 'USA', true, 3),
('654 Elm Street', 'Houston', 'TX', '77001', 'USA', true, 4);

-- Insert Categories
INSERT INTO categories (name, description) VALUES
('Electronics', 'Electronic devices and accessories'),
('Clothing', 'Mens and womens clothing'),
('Books', 'Physical and digital books'),
('Home & Kitchen', 'Home appliances and kitchen items'),
('Sports & Outdoors', 'Sports equipment and outdoor gear'),
('Toys & Games', 'Toys, games, and puzzles');

-- Insert Products
INSERT INTO products (name, description, price, stock_quantity, image_url, sku, active, created_at, category_id) VALUES
-- Electronics
('Laptop Pro 15', 'High-performance laptop with 16GB RAM and 512GB SSD', 1299.99, 50, 'https://example.com/laptop.jpg', 'ELEC-LAP-001', true, CURRENT_TIMESTAMP, 1),
('Wireless Mouse', 'Ergonomic wireless mouse with precision tracking', 29.99, 200, 'https://example.com/mouse.jpg', 'ELEC-MOU-001', true, CURRENT_TIMESTAMP, 1),
('USB-C Hub', '7-in-1 USB-C hub with HDMI and card reader', 49.99, 150, 'https://example.com/hub.jpg', 'ELEC-HUB-001', true, CURRENT_TIMESTAMP, 1),
('Wireless Headphones', 'Noise-canceling Bluetooth headphones', 199.99, 100, 'https://example.com/headphones.jpg', 'ELEC-HEAD-001', true, CURRENT_TIMESTAMP, 1),
('Smartphone 12', 'Latest smartphone with 128GB storage', 899.99, 75, 'https://example.com/phone.jpg', 'ELEC-PHO-001', true, CURRENT_TIMESTAMP, 1),

-- Clothing
('Cotton T-Shirt', 'Comfortable 100% cotton t-shirt', 19.99, 300, 'https://example.com/tshirt.jpg', 'CLOT-TSH-001', true, CURRENT_TIMESTAMP, 2),
('Denim Jeans', 'Classic fit blue denim jeans', 59.99, 150, 'https://example.com/jeans.jpg', 'CLOT-JEA-001', true, CURRENT_TIMESTAMP, 2),
('Running Shoes', 'Lightweight running shoes with cushioned sole', 89.99, 100, 'https://example.com/shoes.jpg', 'CLOT-SHO-001', true, CURRENT_TIMESTAMP, 2),
('Winter Jacket', 'Warm waterproof winter jacket', 149.99, 80, 'https://example.com/jacket.jpg', 'CLOT-JAC-001', true, CURRENT_TIMESTAMP, 2),

-- Books
('The Complete Guide to Programming', 'Comprehensive programming textbook', 49.99, 120, 'https://example.com/progbook.jpg', 'BOOK-PRO-001', true, CURRENT_TIMESTAMP, 3),
('Mystery Novel Collection', 'Set of 3 bestselling mystery novels', 34.99, 90, 'https://example.com/mystery.jpg', 'BOOK-MYS-001', true, CURRENT_TIMESTAMP, 3),
('Cookbook: Easy Recipes', '200+ easy and delicious recipes', 24.99, 150, 'https://example.com/cookbook.jpg', 'BOOK-COO-001', true, CURRENT_TIMESTAMP, 3),

-- Home & Kitchen
('Coffee Maker', 'Programmable 12-cup coffee maker', 79.99, 60, 'https://example.com/coffee.jpg', 'HOME-COF-001', true, CURRENT_TIMESTAMP, 4),
('Blender Pro', 'High-speed blender with multiple settings', 99.99, 45, 'https://example.com/blender.jpg', 'HOME-BLE-001', true, CURRENT_TIMESTAMP, 4),
('Non-Stick Pan Set', 'Set of 3 non-stick frying pans', 69.99, 70, 'https://example.com/pans.jpg', 'HOME-PAN-001', true, CURRENT_TIMESTAMP, 4),
('Vacuum Cleaner', 'Powerful bagless vacuum cleaner', 179.99, 40, 'https://example.com/vacuum.jpg', 'HOME-VAC-001', true, CURRENT_TIMESTAMP, 4),

-- Sports & Outdoors
('Yoga Mat', 'Premium non-slip yoga mat', 29.99, 200, 'https://example.com/yogamat.jpg', 'SPOR-YOG-001', true, CURRENT_TIMESTAMP, 5),
('Camping Tent', '4-person waterproof camping tent', 159.99, 35, 'https://example.com/tent.jpg', 'SPOR-TEN-001', true, CURRENT_TIMESTAMP, 5),
('Basketball', 'Official size and weight basketball', 24.99, 100, 'https://example.com/basketball.jpg', 'SPOR-BAS-001', true, CURRENT_TIMESTAMP, 5),
('Fitness Tracker', 'Waterproof fitness tracker with heart rate monitor', 79.99, 85, 'https://example.com/tracker.jpg', 'SPOR-FIT-001', true, CURRENT_TIMESTAMP, 5),

-- Toys & Games
('Building Blocks Set', '500-piece creative building blocks', 39.99, 120, 'https://example.com/blocks.jpg', 'TOY-BLO-001', true, CURRENT_TIMESTAMP, 6),
('Board Game Classic', 'Family board game for 2-6 players', 29.99, 90, 'https://example.com/boardgame.jpg', 'TOY-BOA-001', true, CURRENT_TIMESTAMP, 6),
('RC Car', 'Remote control racing car', 49.99, 60, 'https://example.com/rccar.jpg', 'TOY-RC-001', true, CURRENT_TIMESTAMP, 6);

-- Insert Carts
INSERT INTO carts (user_id) VALUES
(2), (3), (4);

-- Insert Cart Items
INSERT INTO cart_items (cart_id, product_id, quantity) VALUES
(1, 1, 1),  -- John has Laptop in cart
(1, 2, 2),  -- John has 2 Wireless Mice
(2, 6, 3),  -- Jane has 3 T-Shirts
(2, 13, 1), -- Jane has Coffee Maker
(3, 17, 1), -- Bob has Yoga Mat
(3, 21, 2); -- Bob has 2 Building Blocks Sets

-- Insert Orders
INSERT INTO orders (user_id, total_amount, status, order_date, shipping_address) VALUES
(2, 1359.97, 'DELIVERED', DATEADD('DAY', -30, CURRENT_TIMESTAMP), '456 Oak Avenue, Los Angeles, CA, 90001, USA'),
(2, 89.99, 'SHIPPED', DATEADD('DAY', -5, CURRENT_TIMESTAMP), '456 Oak Avenue, Los Angeles, CA, 90001, USA'),
(3, 279.96, 'PROCESSING', DATEADD('DAY', -2, CURRENT_TIMESTAMP), '321 Pine Road, Chicago, IL, 60601, USA'),
(4, 99.98, 'CONFIRMED', DATEADD('DAY', -1, CURRENT_TIMESTAMP), '654 Elm Street, Houston, TX, 77001, USA');

-- Insert Order Items
INSERT INTO order_items (order_id, product_id, quantity, price) VALUES
-- Order 1 (John - Delivered)
(1, 1, 1, 1299.99),
(1, 2, 2, 29.99),
-- Order 2 (John - Shipped)
(2, 8, 1, 89.99),
-- Order 3 (Jane - Processing)
(3, 13, 1, 79.99),
(3, 14, 2, 99.99),
-- Order 4 (Bob - Confirmed)
(4, 19, 4, 24.99);

-- Insert Payments
INSERT INTO payments (order_id, amount, payment_method, status, transaction_id, payment_date) VALUES
(1, 1359.97, 'CREDIT_CARD', 'COMPLETED', 'TXN-20240101-001', DATEADD('DAY', -30, CURRENT_TIMESTAMP)),
(2, 89.99, 'PAYPAL', 'COMPLETED', 'TXN-20240201-002', DATEADD('DAY', -5, CURRENT_TIMESTAMP)),
(3, 279.96, 'DEBIT_CARD', 'COMPLETED', 'TXN-20240228-003', DATEADD('DAY', -2, CURRENT_TIMESTAMP)),
(4, 99.98, 'CASH_ON_DELIVERY', 'PENDING', NULL, DATEADD('DAY', -1, CURRENT_TIMESTAMP));