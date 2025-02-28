-- Sample data to test the system
-- Insert dish types
INSERT INTO DISH_TYPE (DISH_TYPE_NAME)
VALUES
    ('Appetizer'),
    ('Main Course'),
    ('Dessert'),
    ('Beverage');

-- Insert sample dishes
INSERT INTO DISH (DISH_NAME, DISH_DESCRIPTION, DISH_TYPE_ID, DISH_PRICE)
VALUES
    ('Caesar Salad', 'Fresh romaine lettuce with croutons and caesar dressing', 1, 89.00),
    ('Swedish Meatballs', 'Traditional meatballs with mashed potatoes and lingonberry jam', 2, 149.00),
    ('Chocolate Fondant', 'Warm chocolate cake with a melting center', 3, 79.00),
    ('Sparkling Water', 'Refreshing sparkling mineral water', 4, 29.00);

-- Insert test orders (These should match your IDs 4 and 5)
-- If your orders already exist, skip this part
INSERT INTO ORDERS (ORDER_ID, ORDER_DATE, TABLE_NUMBER, IS_FINISHED, STATUS)
VALUES
    (4, CURDATE(), 1, FALSE, 'ACTIVE'),
    (5, CURDATE(), 2, FALSE, 'ACTIVE');

-- Insert order dishes for your test orders
INSERT INTO ORDER_DISH (ORDER_ID, DISH_ID, NOTE_TEXT, IS_DONE, IS_SERVED, STATUS)
VALUES
    (4, 1, 'No croutons', FALSE, FALSE, 'PENDING'),
    (4, 2, 'Extra sauce', FALSE, FALSE, 'PENDING'),
    (4, 4, '', FALSE, FALSE, 'PENDING'),
    (5, 2, 'No lingonberry jam', FALSE, FALSE, 'PENDING'),
    (5, 3, 'Extra ice cream', FALSE, FALSE, 'PENDING');