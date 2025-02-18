-- Use the RestaurantDB database
USE RestaurantDB;
SHOW VARIABLES LIKE 'log_error';

-- ===========================================================
-- Delete existing lunch dishes and menus for the given dates
-- ===========================================================
DELETE FROM LUNCH_DISH
WHERE LUNCH_ID IN (
    SELECT LUNCH_ID
    FROM LUNCH_MENU
    WHERE LUNCH_DATE BETWEEN '2025-02-17' AND '2025-02-23'
);

DELETE FROM LUNCH_MENU
WHERE LUNCH_DATE BETWEEN '2025-02-17' AND '2025-02-23';

-- ===========================================================
-- Insert menu entries for each day (one row per date)
-- ===========================================================
INSERT INTO LUNCH_MENU (LUNCH_DATE) VALUES
    ('2025-02-17'), -- Monday
    ('2025-02-18'), -- Tuesday
    ('2025-02-19'), -- Wednesday
    ('2025-02-20'), -- Thursday
    ('2025-02-21'), -- Friday
    ('2025-02-22'), -- Saturday
    ('2025-02-23'); -- Sunday

-- ===========================================================
-- Insert Monday's dishes
-- ===========================================================
INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Kycklingfilé med ugnsrostade grönsaker', 129.00, LUNCH_ID
FROM LUNCH_MENU
WHERE LUNCH_DATE = '2025-02-17';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Vegetarisk lasagne', 119.00, LUNCH_ID
FROM LUNCH_MENU
WHERE LUNCH_DATE = '2025-02-17';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Svampsoppa', 99.00, LUNCH_ID
FROM LUNCH_MENU
WHERE LUNCH_DATE = '2025-02-17';

-- ===========================================================
-- Insert Tuesday's dishes
-- ===========================================================
INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Pannbiff med löksås', 125.00, LUNCH_ID
FROM LUNCH_MENU
WHERE LUNCH_DATE = '2025-02-18';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Falukorv i ugn med tomat och ost', 115.00, LUNCH_ID
FROM LUNCH_MENU
WHERE LUNCH_DATE = '2025-02-18';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Halloumigryta', 119.00, LUNCH_ID
FROM LUNCH_MENU
WHERE LUNCH_DATE = '2025-02-18';

-- ===========================================================
-- Insert Wednesday's dishes
-- ===========================================================
INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Torskrygg med citronsås', 139.00, LUNCH_ID
FROM LUNCH_MENU
WHERE LUNCH_DATE = '2025-02-19';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Pasta Carbonara', 119.00, LUNCH_ID
FROM LUNCH_MENU
WHERE LUNCH_DATE = '2025-02-19';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Vegetarisk currygryta', 115.00, LUNCH_ID
FROM LUNCH_MENU
WHERE LUNCH_DATE = '2025-02-19';

-- ===========================================================
-- Insert Thursday's dishes
-- ===========================================================
INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Stekt fläsk med raggmunk', 129.00, LUNCH_ID
FROM LUNCH_MENU
WHERE LUNCH_DATE = '2025-02-20';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Kycklinggryta med dragon', 125.00, LUNCH_ID
FROM LUNCH_MENU
WHERE LUNCH_DATE = '2025-02-20';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Vegetarisk böngryta', 115.00, LUNCH_ID
FROM LUNCH_MENU
WHERE LUNCH_DATE = '2025-02-20';

-- ===========================================================
-- Insert Friday's dishes
-- ===========================================================
INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Grillad lax med hollandaise', 139.00, LUNCH_ID
FROM LUNCH_MENU
WHERE LUNCH_DATE = '2025-02-21';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Pasta Carbonara', 119.00, LUNCH_ID
FROM LUNCH_MENU
WHERE LUNCH_DATE = '2025-02-21';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Vegetarisk currygryta', 115.00, LUNCH_ID
FROM LUNCH_MENU
WHERE LUNCH_DATE = '2025-02-21';

-- ===========================================================
-- Insert Saturday's dishes
-- ===========================================================
INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Torskrygg med citronsås', 139.00, LUNCH_ID
FROM LUNCH_MENU
WHERE LUNCH_DATE = '2025-02-22';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Pasta Carbonara', 119.00, LUNCH_ID
FROM LUNCH_MENU
WHERE LUNCH_DATE = '2025-02-22';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Vegetarisk currygryta', 115.00, LUNCH_ID
FROM LUNCH_MENU
WHERE LUNCH_DATE = '2025-02-22';

-- ===========================================================
-- Insert Sunday's dishes
-- ===========================================================
INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Pannbiff med löksås', 125.00, LUNCH_ID
FROM LUNCH_MENU
WHERE LUNCH_DATE = '2025-02-23';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Pasta Carbonara', 119.00, LUNCH_ID
FROM LUNCH_MENU
WHERE LUNCH_DATE = '2025-02-23';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Halloumigryta', 119.00, LUNCH_ID
FROM LUNCH_MENU
WHERE LUNCH_DATE = '2025-02-23';

-- ===========================================================
-- Display the lunch menu
-- ===========================================================
SELECT lm.LUNCH_DATE, ld.LUNCH_DISH_NAME
FROM LUNCH_MENU lm
LEFT JOIN LUNCH_DISH ld ON lm.LUNCH_ID = ld.LUNCH_ID
ORDER BY lm.LUNCH_DATE;
