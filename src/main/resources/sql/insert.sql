USE RestaurantDB;

-- 1. Employees
INSERT INTO EMPLOYEE (FIRST_NAME, LAST_NAME, PHONE_NUMBER, IS_ADMIN) VALUES
                                                                         ('John', 'Doe', '1234567890', TRUE),
                                                                         ('Jane', 'Smith', '0987654321', FALSE);

-- 2. Dish Types
INSERT INTO DISH_TYPE (DISH_TYPE_NAME) VALUES
                                           ('Förrätter'),      -- ID: 1
                                           ('Varmrätter'),     -- ID: 2
                                           ('Vegetariska'),    -- ID: 3
                                           ('Efterrätter'),    -- ID: 4
                                           ('Drycker');        -- ID: 5

-- 3. Events
INSERT INTO EVENT (EVENT_DATE, EVENT_DESCRIPTION) VALUES
                                                      ('2025-02-27', 'Live Music Night'),
                                                      ('2025-03-15', 'Wine Tasting Event');

-- 4. A La Carte Dishes
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

-- 5. Drinks
INSERT INTO DRINK (DRINK_NAME, DRINK_PRICE) VALUES
                                                ('Kaffe eller Te', 25.00),
                                                ('Lingondricka', 30.00),
                                                ('Lättöl eller Julmust', 35.00),
                                                ('Lokalt Bryggt Öl', 55.00);

-- 6. Clean up existing lunch menu data
DELETE FROM LUNCH_DISH
WHERE LUNCH_ID IN (
    SELECT LUNCH_ID
    FROM LUNCH_MENU
    WHERE LUNCH_DATE BETWEEN '2025-02-24' AND '2025-03-02'
);

DELETE FROM LUNCH_MENU
WHERE LUNCH_DATE BETWEEN '2025-02-24' AND '2025-03-02';

-- 7. Insert menu entries for current week
INSERT INTO LUNCH_MENU (LUNCH_DATE) VALUES
                                        ('2025-02-24'), -- Monday
                                        ('2025-02-25'), -- Tuesday
                                        ('2025-02-26'), -- Wednesday
                                        ('2025-02-27'), -- Thursday
                                        ('2025-02-28'), -- Friday
                                        ('2025-03-01'), -- Saturday
                                        ('2025-03-02'); -- Sunday

-- ===========================================================
-- Insert Monday's dishes
-- ===========================================================
INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID, LUNCH_DISH_DESCRIPTION)
SELECT 'Kycklingfilé med ugnsrostade grönsaker', 129.00, LUNCH_ID, 'Saftig kycklingfilé serverad med säsongens ugnsrostade grönsaker och rosmarin potatis.'
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-02-24';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID, LUNCH_DISH_DESCRIPTION)
SELECT 'Vegetarisk lasagne', 119.00, LUNCH_ID, 'Krämig lasagne med zucchini, aubergine, morot och spenat, toppad med mozzarella.'
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-02-24';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID, LUNCH_DISH_DESCRIPTION)
SELECT 'Svampsoppa', 99.00, LUNCH_ID, 'Smakrik soppa på skogschampinjoner och karljohansvamp, serveras med hembakat bröd.'
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-02-24';

-- ===========================================================
-- Insert Tuesday's dishes
-- ===========================================================
INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID, LUNCH_DISH_DESCRIPTION)
SELECT 'Pannbiff med löksås', 125.00, LUNCH_ID, 'Saftig pannbiff med krämig löksås, serveras med potatispuré och lingon.'
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-02-25';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID, LUNCH_DISH_DESCRIPTION)
SELECT 'Falukorv i ugn med tomat och ost', 115.00, LUNCH_ID, 'Klassisk falukorv i ugn med tomatsås och gratinerad ost, serveras med potatismos.'
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-02-25';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID, LUNCH_DISH_DESCRIPTION)
SELECT 'Halloumigryta', 119.00, LUNCH_ID, 'Smakrik gryta med halloumiost, zucchini, paprika och kikärtor i tomatsås, serveras med ris.'
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-02-25';

-- ===========================================================
-- Insert Wednesday's dishes
-- ===========================================================
INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID, LUNCH_DISH_DESCRIPTION)
SELECT 'Torskrygg med citronsås', 139.00, LUNCH_ID, 'Pocherad torskrygg med lätt citronsås, serveras med kokt potatis och ångade grönsaker.'
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-02-26';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID, LUNCH_DISH_DESCRIPTION)
SELECT 'Pasta Carbonara', 119.00, LUNCH_ID, 'Krämig pasta med bacon, äggula och svartpeppar, toppad med riven parmesan.'
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-02-26';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID, LUNCH_DISH_DESCRIPTION)
SELECT 'Vegetarisk currygryta', 115.00, LUNCH_ID, 'Aromatisk currygryta med säsongens grönsaker, kokos och linser, serveras med basmatiris.'
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-02-26';

-- ===========================================================
-- Insert Thursday's dishes
-- ===========================================================
INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID, LUNCH_DISH_DESCRIPTION)
SELECT 'Stekt fläsk med raggmunk', 129.00, LUNCH_ID, 'Knaperstekt rimmat fläsk med raggmunk och rårörda lingon.'
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-02-27';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID, LUNCH_DISH_DESCRIPTION)
SELECT 'Kycklinggryta med dragon', 125.00, LUNCH_ID, 'Krämig kycklinggryta smaksatt med dragon och vitvinsreduktion, serveras med ris.'
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-02-27';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID, LUNCH_DISH_DESCRIPTION)
SELECT 'Vegetarisk böngryta', 115.00, LUNCH_ID, 'Mustig gryta med blandade bönor, rotsaker och örter, serveras med gott bröd.'
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-02-27';

-- ===========================================================
-- Insert Friday's dishes
-- ===========================================================
INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID, LUNCH_DISH_DESCRIPTION)
SELECT 'Grillad lax med hollandaise', 139.00, LUNCH_ID, 'Grillad laxfilé med klassisk hollandaisesås, serveras med kokt potatis och sparris.'
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-02-28';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID, LUNCH_DISH_DESCRIPTION)
SELECT 'Köttbullar med potatismos', 119.00, LUNCH_ID, 'Hemlagade köttbullar med gräddsås, potatismos, lingon och pressgurka.'
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-02-28';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID, LUNCH_DISH_DESCRIPTION)
SELECT 'Vegetarisk currygryta', 115.00, LUNCH_ID, 'Aromatisk currygryta med säsongens grönsaker, kokos och linser, serveras med basmatiris.'
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-02-28';

-- ===========================================================
-- Insert Saturday's dishes
-- ===========================================================
INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID, LUNCH_DISH_DESCRIPTION)
SELECT 'Fläskfilé med potatisgratäng', 139.00, LUNCH_ID, 'Mör fläskfilé med rödvinssås, serveras med krämig potatisgratäng och säsongens grönsaker.'
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-01';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID, LUNCH_DISH_DESCRIPTION)
SELECT 'Laxpasta med spenatsås', 119.00, LUNCH_ID, 'Pasta med bitar av varmrökt lax i en krämig spenatsås, toppad med pinjenötter.'
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-01';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID, LUNCH_DISH_DESCRIPTION)
SELECT 'Vegetarisk quorngryta', 115.00, LUNCH_ID, 'Smakrik gryta med quorn, paprika, zucchini och champinjoner i tomatsås, serveras med ris.'
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-01';

-- ===========================================================
-- Insert Sunday's dishes
-- ===========================================================
INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID, LUNCH_DISH_DESCRIPTION)
SELECT 'Söndagsstek med sås', 145.00, LUNCH_ID, 'Långsamt tillagad oxstek med rödvinssås, ugnsrostade rotfrukter och potatisgratäng.'
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-02';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID, LUNCH_DISH_DESCRIPTION)
SELECT 'Pasta med räksås', 129.00, LUNCH_ID, 'Pasta med handskalade räkor i en krämig vitvinssås med dill och citron.'
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-02';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_PRICE, LUNCH_ID, LUNCH_DISH_DESCRIPTION)
SELECT 'Vegetarisk svamprisotto', 119.00, LUNCH_ID, 'Krämig risotto med skogschampinjoner, karljohansvamp och parmesan, toppad med rucola.'
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-02';

-- 8. Sample Orders
INSERT INTO ORDERS (ORDER_ID, TABLE_NUMBER, STATUS, ORDER_DATE)
VALUES
    (1, 5, 'ACTIVE', CURDATE()),  -- Table 5
    (2, 3, 'ACTIVE', CURDATE()),  -- Table 3
    (3, 7, 'ACTIVE', CURDATE());  -- Table 7

-- 9. Sample Order Items
INSERT INTO ORDER_DISH (ORDER_ID, DISH_ID, NOTE_TEXT, IS_DONE, IS_SERVED, STATUS)
VALUES
    (1, 1, 'Extra sås', FALSE, FALSE, 'PENDING'),  -- Norrlands delikatesser
    (1, 2, 'Ingen lök', FALSE, FALSE, 'PENDING'),  -- Gravlax
    (2, 3, 'Extra ost', FALSE, FALSE, 'PENDING'),  -- Västerbottenpaj
    (2, 4, 'Med citron', FALSE, FALSE, 'IN_PROGRESS'),  -- Ugnsstekt Piggvar med pepparrotsskräm
    (3, 5, 'Extra dill', TRUE, FALSE, 'READY'),  -- Forellfilé med citronmousse
    (3, 6, 'Ingen citron', TRUE, TRUE, 'SERVED'),  -- Lax med Dillstuvad Potatis
    (1, 7, 'Lägg till mer bröd', FALSE, FALSE, 'PENDING'),  -- Älggryta med Rotfrukter
    (2, 8, 'Ingen svampsås', FALSE, FALSE, 'IN_PROGRESS'),  -- Grönsaksbiffar med Svampsås
    (3, 9, 'Extra timjan', FALSE, FALSE, 'PENDING'),  -- Svensk Rotfruktsgryta
    (1, 10, 'Med extra grädde', FALSE, FALSE, 'PENDING'),  -- Kladdkaka med Grädde
    (2, 11, 'Servera varm', FALSE, FALSE, 'IN_PROGRESS'),  -- Blåbärspaj med Vaniljsås
    (3, 12, 'Dela på två personer', TRUE, TRUE, 'SERVED');  -- Glass surprís


-- 10. Work Shifts
INSERT INTO WORK_SHIFT (START_TIME, END_TIME, DESCRIPTION, EMPLOYEE_ID) VALUES
                                                                            ('2025-02-24 08:00:00', '2025-02-24 16:00:00', 'Morgonpass', 1),
                                                                            ('2025-02-24 16:00:00', '2025-02-24 23:59:59', 'Kvällspass', 2);

-- 11. Bookings
INSERT INTO BOOKING (NAME, EMAIL, PHONE, DATE, TIME, PEOPLE_COUNT, TABLE_NUMBER)
VALUES
    ('Alice Johnson', 'alice@example.com', '0701234567', '2025-02-24', '18:00:00', 4, 2),
    ('Bob Smith', 'bob@example.com', '0707654321', '2025-02-24', '18:00:00', 2, 1),
    ('Carol Davis', 'carol@example.com', '0703334444', '2025-02-24', '19:30:00', 6, 3),
    ('David Wilson', 'david@example.com', '0705556666', '2025-02-25', '18:30:00', 4, 5),
    ('Eva Brown', 'eva@example.com', '0708889999', '2025-02-25', '18:30:00', 2, 4),
    ('Frank Miller', 'frank@example.com', '0702223333', '2025-02-25', '20:00:00', 6, 6);

-- Verify the lunch menu
SELECT lm.LUNCH_DATE, ld.LUNCH_DISH_NAME, ld.LUNCH_DISH_PRICE, ld.LUNCH_DISH_DESCRIPTION
FROM LUNCH_MENU lm
         LEFT JOIN LUNCH_DISH ld ON lm.LUNCH_ID = ld.LUNCH_ID
ORDER BY lm.LUNCH_DATE;