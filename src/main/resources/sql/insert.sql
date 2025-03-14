USE RestaurantDB;

-- 1. Employees
-- Infoga anställd med id '000216'
INSERT INTO EMPLOYEE (EMPLOYEE_ID, FIRST_NAME, LAST_NAME, PHONE_NUMBER, IS_ADMIN)
VALUES ('000216', 'John', 'Doe', '0701234567', false);

-- Infoga en andra anställd (t.ex. för work shift) med id '000217'
INSERT INTO EMPLOYEE (EMPLOYEE_ID, FIRST_NAME, LAST_NAME, PHONE_NUMBER, IS_ADMIN)
VALUES ('000217', 'Jane', 'Smith', '0701234568', false);

-- 2. Employee Availability
INSERT INTO EMPLOYEE_AVAILABILITY (EMPLOYEE_ID, DAY_OF_WEEK, START_TIME, END_TIME, PREFERENCE) VALUES
                                                                                                   ('000216', 'MONDAY', '08:00:00', '16:00:00', 'PREFERRED'),
                                                                                                   ('000216', 'TUESDAY', '08:00:00', '16:00:00', 'AVAILABLE'),
                                                                                                   ('000216', 'WEDNESDAY', '08:00:00', '16:00:00', 'AVAILABLE'),
                                                                                                   ('000216', 'THURSDAY', '08:00:00', '16:00:00', 'AVAILABLE'),
                                                                                                   ('000216', 'FRIDAY', '08:00:00', '16:00:00', 'AVAILABLE');

-- 3. Dish Types
INSERT INTO DISH_TYPE (DISH_TYPE_NAME) VALUES
                                           ('Förrätter'),      -- ID: 1
                                           ('Varmrätter'),     -- ID: 2
                                           ('Vegetariska'),    -- ID: 3
                                           ('Efterrätter'),    -- ID: 4
                                           ('Drycker');        -- ID: 5

-- 4. Events
INSERT INTO EVENT (EVENT_DATE, EVENT_DESCRIPTION) VALUES
                                                      ('2025-02-27', 'Live Music Night'),
                                                      ('2025-03-15', 'Wine Tasting Event');

-- 5. A La Carte Dishes
INSERT INTO DISH (DISH_NAME, DISH_DESCRIPTION, DISH_TYPE_ID, DISH_PRICE) VALUES
                                                                             -- Starters (Förrätter)
                                                                             ('Norrlands delikatesser', 'Torkad renfilé med enbärsgelé och frästa kantareller serveras med knäckebröd på rågsurdegsbröd.', 1, 85.00),
                                                                             ('Gravlax', 'Tunt skivad gravad lax med hovmästarsås och färskt bröd.', 1, 75.00),
                                                                             ('Västerbottenpaj', 'Västerbottenostpaj serverad med en klick gräddfil och lite gräslök.', 1, 70.00),

                                                                             -- Main Courses (Varmrätter)
                                                                             ('Ugnsstekt Piggvar med pepparrotsskräm', 'Ugnsstekt piggvar, krämigt potatismos och pepparrotsskräm.', 2, 220.00),
                                                                             ('Forellfilé med citronmousse', 'Stekta forellfiléer med citronmousse, serverad med potatispuré.', 2, 215.00),
                                                                             ('Lax med Dillstuvad Potatis', 'Ugnsbakad lax med dillstuvad potatis och en citronklyfta.', 2, 195.00),
                                                                             ('Älggryta med Rotfrukter', 'Långkokt älggryta med rotfrukter, serverad med nybakat bröd.', 2, 195.00),

                                                                             -- Vegetarian (Vegetariska)
                                                                             ('Grönsaksbiffar med Svampsås', 'Grönsaksbiffar med krämig svampsås och ugnsrostad potatis.', 3, 160.00),
                                                                             ('Svensk Rotfruktsgryta', 'Mustig gryta med rotfrukter och timjan, serverad med nybakat bröd.', 3, 160.00),

                                                                             -- Desserts (Efterrätter)
                                                                             ('Kladdkaka med Grädde', 'Svensk chokladkaka serverad med vispgrädde.', 4, 55.00),
                                                                             ('Blåbärspaj med Vaniljsås', 'Blåbärspaj serverad med varm vaniljsås.', 4, 65.00),
                                                                             ('Glass surprís', 'En riktig glassbomb! Välj dina smaker! Utmärkt att dela på.', 4, 130.00);

-- 6. Drinks
INSERT INTO DRINK (DRINK_NAME, DRINK_PRICE) VALUES
                                                ('Kaffe eller Te', 25.00),
                                                ('Lingondricka', 30.00),
                                                ('Lättöl eller Julmust', 35.00),
                                                ('Lokalt Bryggt Öl', 55.00);

-- 7. Clean up existing lunch menu data
DELETE FROM LUNCH_DISH
WHERE LUNCH_ID IN (
    SELECT LUNCH_ID
    FROM LUNCH_MENU
    WHERE LUNCH_DATE BETWEEN '2025-02-24' AND '2025-03-02'
);

DELETE FROM LUNCH_MENU
WHERE LUNCH_DATE BETWEEN '2025-02-24' AND '2025-03-02';

-- 8. Insert menu entries for current week
INSERT INTO LUNCH_MENU (LUNCH_DATE) VALUES
                                        ('2025-02-24'), -- Monday
                                        ('2025-02-25'), -- Tuesday
                                        ('2025-02-26'), -- Wednesday
                                        ('2025-02-27'), -- Thursday
                                        ('2025-02-28'), -- Friday
                                        ('2025-03-01'), -- Saturday
                                        ('2025-03-02'); -- Sunday

-- Insert Monday's dishes
INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Kycklingfilé med ugnsrostade grönsaker', 129.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-02-24';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Vegetarisk lasagne', 119.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-02-24';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Svampsoppa', 99.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-02-24';

-- Insert Tuesday's dishes
INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Pannbiff med löksås', 125.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-02-25';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Falukorv i ugn med tomat och ost', 115.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-02-25';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Halloumigryta', 119.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-02-25';

-- Insert Wednesday's dishes
INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Torskrygg med citronsås', 139.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-02-26';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Pasta Carbonara', 119.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-02-26';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Vegetarisk currygryta', 115.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-02-26';

-- Insert Thursday's dishes
INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Stekt fläsk med raggmunk', 129.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-02-27';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Kycklinggryta med dragon', 125.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-02-27';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Vegetarisk böngryta', 115.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-02-27';

-- Insert Friday's dishes
INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Grillad lax med hollandaise', 139.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-02-28';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Köttbullar med potatismos', 119.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-02-28';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Vegetarisk currygryta', 115.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-02-28';

-- Insert Saturday's dishes
INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Fläskfilé med potatisgratäng', 139.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-01';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Laxpasta med spenatsås', 119.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-01';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Vegetarisk quorngryta', 115.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-01';

-- Insert Sunday's dishes
INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Söndagsstek med sås', 145.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-02';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Pasta med räksås', 129.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-02';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Vegetarisk svamprisotto', 119.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-02';

-- 9. Sample Orders
INSERT INTO ORDERS (ORDER_DATE, TABLE_NUMBER, IS_FINISHED) VALUES
                                                               ('2025-02-24', 1, FALSE),
                                                               ('2025-02-24', 2, TRUE);

-- 10. Sample Order Items
INSERT INTO ORDER_DISH (ORDER_DISH_ID, ORDER_ID, CATEGORY, DISH_NAME, DISH_COUNT, IS_FINISHED)
VALUES (1, 1, 'FÖRRÄTT', 'DANEIL', 1, FALSE);

-- 11. Work Shifts (Updated to use the new status field)
INSERT INTO WORK_SHIFT (START_TIME, END_TIME, DESCRIPTION, EMPLOYEE_ID, SHIFT_STATUS) VALUES
                                                                                          ('2025-02-24 08:00:00', '2025-02-24 16:00:00', 'Morgonpass', '000216', 'ASSIGNED'),
                                                                                          ('2025-02-24 16:00:00', '2025-02-24 23:59:59', 'Kvällspass', '000217', 'ASSIGNED'),
                                                                                          ('2025-02-25 08:00:00', '2025-02-25 16:00:00', 'Morgonpass', NULL, 'OPEN'),
                                                                                          ('2025-02-25 16:00:00', '2025-02-25 23:59:59', 'Kvällspass', NULL, 'OPEN'),
                                                                                          ('2025-02-26 08:00:00', '2025-02-26 16:00:00', 'Morgonpass', NULL, 'OPEN'),
                                                                                          ('2025-02-26 16:00:00', '2025-02-26 23:59:59', 'Kvällspass', NULL, 'OPEN');

-- 12. Bookings
INSERT INTO BOOKING (NAME, EMAIL, PHONE, DATE, TIME, PEOPLE_COUNT)
VALUES
    ('Alice Johnson', 'alice@example.com', '0701234567', '2025-02-24', '18:00:00', 4),
    ('Bob Smith', 'bob@example.com', '0707654321', '2025-02-24', '18:00:00', 2),
    ('Carol Davis', 'carol@example.com', '0703334444', '2025-02-24', '19:30:00', 6),
    ('David Wilson', 'david@example.com', '0705556666', '2025-02-25', '18:30:00', 4),
    ('Eva Brown', 'eva@example.com', '0708889999', '2025-02-25', '18:30:00', 2),
    ('Frank Miller', 'frank@example.com', '0702223333', '2025-02-25', '20:00:00', 6);

-- Clean up existing lunch menu data for the new week
DELETE FROM LUNCH_DISH
WHERE LUNCH_ID IN (
    SELECT LUNCH_ID
    FROM LUNCH_MENU
    WHERE LUNCH_DATE BETWEEN '2025-03-03' AND '2025-03-09'
);

DELETE FROM LUNCH_MENU
WHERE LUNCH_DATE BETWEEN '2025-03-03' AND '2025-03-09';

-- Insert menu entries for new week
INSERT INTO LUNCH_MENU (LUNCH_DATE) VALUES
                                        ('2025-03-03'), -- Monday
                                        ('2025-03-04'), -- Tuesday
                                        ('2025-03-05'), -- Wednesday
                                        ('2025-03-06'), -- Thursday
                                        ('2025-03-07'), -- Friday
                                        ('2025-03-08'), -- Saturday
                                        ('2025-03-09'); -- Sunday

-- Insert Monday's dishes for new week
INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Fiskgratäng med dill och citron', 'Krämig fiskgratäng med färsk dill och citron, serveras med kokt potatis och gröna ärtor', 135.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-03';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Pulled pork med coleslaw', 'Långkokt och mör pulled pork serverad med hemlagad coleslaw och rostad potatis', 129.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-03';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Vegetarisk svamprisotto', 'Krämig risotto med säsongens svamp, parmesan och färska örter', 119.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-03';

-- Insert Tuesday's dishes for new week
INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Korv Stroganoff med ris', 'Klassisk korv Stroganoff med paprika och lök, serveras med fluffigt ris och inlagd gurka', 125.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-04';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Kycklingwraps med tzatziki', 'Mustiga kycklingwraps med fräscha grönsaker och hemlagad tzatziki', 119.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-04';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Grönsaksbiffar med rotsaksmos', 'Smakrika grönsaksbiffar på säsongens grönsaker, serveras med krämigt rotsaksmos och rödvinssås', 115.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-04';

-- Insert Wednesday's dishes for new week
INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Laxpudding med skirat smör', 'Traditionell svensk laxpudding med dill och ägg, serveras med skirat smör och citron', 139.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-05';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Wallenbergare med potatismos', 'Lyxig Wallenbergare på kalvfärs, serveras med potatismos, lingon och gröna ärtor', 135.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-05';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Vegetarisk linssoppa med bröd', 'Värmande linssoppa med tomat och färska örter, serveras med nybakat bröd och aioli', 109.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-05';

-- Insert Thursday's dishes for new week
INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Ärtsoppa med pannkakor', 'Klassisk svensk ärtsoppa med fläsk, serveras med pannkakor, sylt och vispad grädde', 119.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-06';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Fläskschnitzel med stekt potatis', 'Krispig fläskschnitzel med stekt potatis, kapris, ansjovis och citron', 129.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-06';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Vegetarisk paella', 'Smakrik spansk paella med saffran, paprika, ärtor och säsongens grönsaker', 119.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-06';

-- Insert Friday's dishes for new week
INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Fiskgryta med saffran och aioli', 'Smakrik medelhavsinspirerad fiskgryta med saffran, serveras med aioli och krutonger', 139.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-07';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Svenska köttbullar med lingon', 'Hemlagade svenska köttbullar med potatismos, gräddsås, lingon och pressgurka', 125.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-07';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Vegetarisk lasagne med sallad', 'Krämig vegetarisk lasagne med ricotta, spenat och tomat, serveras med grönsallad', 119.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-07';

-- Insert Saturday's dishes for new week
INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Helstekt entrecôte med rödvinssås', 'Mör helstekt entrecôte med krämig rödvinssås, serveras med ugnsrostad potatis och säsongens grönsaker', 149.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-08';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Krämig laxpasta med spenat', 'Krämig pasta med lax, spenat och gräslök, toppad med riven parmesan', 135.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-08';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Vegetarisk svamprisotto', 'Krämig risotto med Karl-Johan svamp, vitlök och timjan, toppad med parmesanchips', 125.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-08';

-- Insert Sunday's dishes for new week
INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Söndagsstek med gräddsås', 'Traditionell söndagsstek med gräddsås, kokt potatis, gelé och pressgurka', 145.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-09';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Pocherad torsk med äggsås', 'Varsamt pocherad torsk med klassisk äggsås, serveras med dillslungad potatis och smörglaserade morötter', 139.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-09';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Vegetariska biffar med svampsås', 'Saftiga vegetariska biffar med krämig svampsås, serveras med rostad potatis och lingon', 125.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-09';
