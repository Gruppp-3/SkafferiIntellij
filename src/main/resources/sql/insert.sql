USE RestaurantDB;

-- =========================================
-- SECTION 1: DISH TYPES
-- =========================================

-- Clean up existing dish types if needed
DELETE FROM DISH_TYPE;
ALTER TABLE DISH_TYPE AUTO_INCREMENT = 1;

-- Insert dish types
INSERT INTO DISH_TYPE (DISH_TYPE_NAME) VALUES
                                           ('Förrätter'),      -- ID: 1
                                           ('Varmrätter'),     -- ID: 2
                                           ('Vegetariska'),    -- ID: 3
                                           ('Efterrätter'),    -- ID: 4
                                           ('Drycker');        -- ID: 5

-- =========================================
-- SECTION 2: EMPLOYEES
-- =========================================

-- Clean up existing employees
DELETE FROM WORK_SHIFT WHERE EMPLOYEE_ID IS NOT NULL;
DELETE FROM EMPLOYEE_AVAILABILITY;
DELETE FROM EMPLOYEE;

-- Insert new employees (note: EMPLOYEE_ID is INT in your schema, not VARCHAR)
INSERT INTO EMPLOYEE (EMPLOYEE_ID, FIRST_NAME, LAST_NAME, PHONE_NUMBER, IS_ADMIN) VALUES
                                                                                      (1001, 'Erik', 'Johansson', '0701122334', false),
                                                                                      (1002, 'Maria', 'Andersson', '0702233445', false),
                                                                                      (1003, 'Anders', 'Lindgren', '0703344556', false),
                                                                                      (1004, 'Sofia', 'Bergman', '0704455667', false),
                                                                                      (1005, 'Lars', 'Nyström', '0705566778', true),  -- Admin
                                                                                      (1006, 'Emma', 'Ekström', '0706677889', false),
                                                                                      (1007, 'Johan', 'Karlsson', '0707788990', false),
                                                                                      (1008, 'Anna', 'Nordström', '0708899001', false);

-- Employee Availability
INSERT INTO EMPLOYEE_AVAILABILITY (EMPLOYEE_ID, DAY_OF_WEEK, START_TIME, END_TIME, PREFERENCE) VALUES
-- Erik Johansson
(1001, 'MONDAY', '08:00:00', '16:00:00', 'PREFERRED'),
(1001, 'TUESDAY', '08:00:00', '16:00:00', 'PREFERRED'),
(1001, 'WEDNESDAY', '08:00:00', '16:00:00', 'AVAILABLE'),
(1001, 'THURSDAY', '08:00:00', '16:00:00', 'AVAILABLE'),
(1001, 'FRIDAY', '08:00:00', '16:00:00', 'UNAVAILABLE'),

-- Maria Andersson
(1002, 'MONDAY', '16:00:00', '23:59:59', 'AVAILABLE'),
(1002, 'TUESDAY', '16:00:00', '23:59:59', 'PREFERRED'),
(1002, 'WEDNESDAY', '16:00:00', '23:59:59', 'PREFERRED'),
(1002, 'THURSDAY', '16:00:00', '23:59:59', 'AVAILABLE'),
(1002, 'FRIDAY', '16:00:00', '23:59:59', 'AVAILABLE'),
(1002, 'SATURDAY', '16:00:00', '23:59:59', 'AVAILABLE'),

-- Anders Lindgren
(1003, 'WEDNESDAY', '08:00:00', '16:00:00', 'PREFERRED'),
(1003, 'THURSDAY', '08:00:00', '16:00:00', 'PREFERRED'),
(1003, 'FRIDAY', '08:00:00', '16:00:00', 'AVAILABLE'),
(1003, 'SATURDAY', '08:00:00', '16:00:00', 'AVAILABLE'),
(1003, 'SUNDAY', '08:00:00', '16:00:00', 'AVAILABLE'),

-- Sofia Bergman
(1004, 'MONDAY', '08:00:00', '16:00:00', 'AVAILABLE'),
(1004, 'TUESDAY', '08:00:00', '16:00:00', 'AVAILABLE'),
(1004, 'FRIDAY', '16:00:00', '23:59:59', 'PREFERRED'),
(1004, 'SATURDAY', '16:00:00', '23:59:59', 'PREFERRED'),
(1004, 'SUNDAY', '16:00:00', '23:59:59', 'AVAILABLE'),

-- Lars Nyström
(1005, 'MONDAY', '08:00:00', '16:00:00', 'PREFERRED'),
(1005, 'TUESDAY', '08:00:00', '16:00:00', 'PREFERRED'),
(1005, 'WEDNESDAY', '08:00:00', '16:00:00', 'PREFERRED'),
(1005, 'THURSDAY', '08:00:00', '16:00:00', 'PREFERRED'),
(1005, 'FRIDAY', '08:00:00', '16:00:00', 'PREFERRED'),

-- Emma Ekström
(1006, 'THURSDAY', '16:00:00', '23:59:59', 'AVAILABLE'),
(1006, 'FRIDAY', '16:00:00', '23:59:59', 'PREFERRED'),
(1006, 'SATURDAY', '16:00:00', '23:59:59', 'PREFERRED'),
(1006, 'SUNDAY', '12:00:00', '20:00:00', 'AVAILABLE'),

-- Johan Karlsson
(1007, 'MONDAY', '16:00:00', '23:59:59', 'AVAILABLE'),
(1007, 'TUESDAY', '16:00:00', '23:59:59', 'AVAILABLE'),
(1007, 'WEDNESDAY', '16:00:00', '23:59:59', 'PREFERRED'),
(1007, 'THURSDAY', '16:00:00', '23:59:59', 'PREFERRED'),
(1007, 'SUNDAY', '12:00:00', '20:00:00', 'AVAILABLE'),

-- Anna Nordström
(1008, 'WEDNESDAY', '08:00:00', '16:00:00', 'AVAILABLE'),
(1008, 'THURSDAY', '08:00:00', '16:00:00', 'AVAILABLE'),
(1008, 'SATURDAY', '08:00:00', '16:00:00', 'PREFERRED'),
(1008, 'SUNDAY', '08:00:00', '16:00:00', 'PREFERRED');

-- =========================================
-- SECTION 3: A LA CARTE MENU
-- =========================================

-- Insert new A La Carte Dishes for Spring 2025
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


-- =========================================
-- SECTION 4: LUNCH MENU (March 17-23, 2025)
-- =========================================

-- Clean up existing lunch menu data for the upcoming week
DELETE FROM LUNCH_DISH
WHERE LUNCH_ID IN (
    SELECT LUNCH_ID
    FROM LUNCH_MENU
    WHERE LUNCH_DATE BETWEEN '2025-03-17' AND '2025-03-23'
);

DELETE FROM LUNCH_MENU
WHERE LUNCH_DATE BETWEEN '2025-03-17' AND '2025-03-23';

-- Insert menu entries for upcoming week (March 17-23, 2025)
INSERT INTO LUNCH_MENU (LUNCH_DATE) VALUES
                                        ('2025-03-17'), -- Monday
                                        ('2025-03-18'), -- Tuesday
                                        ('2025-03-19'), -- Wednesday
                                        ('2025-03-20'), -- Thursday
                                        ('2025-03-21'), -- Friday
                                        ('2025-03-22'), -- Saturday
                                        ('2025-03-23'); -- Sunday

-- Insert Monday's dishes (March 17)
INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Stekt strömming med potatismos', 'Traditionell stekt strömming med hemlagat potatismos, lingon och smält smör', 129.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-17';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Kyckling Tikka Masala med ris', 'Krämig indisk kycklinggryta med basmatiris, raita och naanbröd', 135.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-17';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Vårprimörer med quinoa och fetaost', 'Säsongens primörer med krispig quinoa, fetaost och citrondressing', 119.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-17';

-- Insert Tuesday's dishes (March 18)
INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Biff Rydberg', 'Klassisk Biff Rydberg med tärnad oxfilé, potatis och lök, serveras med senapskräm och äggula', 145.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-18';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Ugnsbakad lax med skaldjurssås', 'Saftig ugnsbakad lax med skaldjurssås, serveras med färskpotatis och sparris', 139.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-18';


INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Vegetarisk moussaka', 'Smakrik vegetarisk moussaka med aubergine, linser och bechamelsås, serveras med grekisk sallad', 125.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-18';

-- Insert Wednesday's dishes (March 19)
INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Skaldjurspasta med vitlök och chili', 'Lyxig pasta med räkor, musslor och bläckfisk, smaksatt med vitlök, chili och vitt vin', 149.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-19';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Kalvlever med bacon och löksås', 'Klassisk kalvlever med knaperstekt bacon, löksås och potatispuré', 135.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-19';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Kikärtsgryta med färska örter', 'Värmande kikärtsgryta med morötter och färska örter, serveras med gräddfil och nybakat bröd', 115.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-19';

-- Insert Thursday's dishes (March 20)
INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Ärtsoppa och pannkakor', 'Traditionell svensk ärtsoppa med fläsk, serveras med pannkakor, jordgubbssylt och vispad grädde', 125.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-20';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Kycklingpaella med saffran', 'Spansk saffranspaella med kyckling, paprika och ärtor', 135.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-20';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Vårsallad med halloumi', 'Fräsch vårsallad med grillad halloumi, sparris, jordgubbar och balsamicodressing', 125.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-20';

-- Insert Friday's dishes (March 21)
INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Panerad fisk med remouladsås', 'Krispigt panerad torsk med hemlagad remouladsås, kokt potatis och citron', 135.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-21';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Älgfärsbiffar med gräddsås', 'Saftiga älgfärsbiffar med gräddsås, rårörda lingon och potatismos', 145.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-21';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Vårprimörpaj med getost', 'Krämig paj med säsongens primörer och getost, serveras med en sallad på ruccola', 119.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-21';

-- Insert Saturday's dishes (March 22)
INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Helgspecial: Oxfilé med potatisgratäng', 'Mör oxfilé med rödvinssås, potatisgratäng och säsongens grönsaker', 169.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-22';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Ugnsbakad röding med hollandaisesås', 'Ugnsbakad röding med hollandaisesås, sparris och dillslungad potatis', 155.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-22';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Vårrisotto med sparris och parmesan', 'Krämig risotto med färsk sparris, gröna ärtor och parmesanchips', 139.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-22';

-- Insert Sunday's dishes (March 23)
INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Helgstekt lammstek med rosmarinsky', 'Mör lammstek med rosmarinsky, ugnsrostade primörer och potatisgratäng', 169.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-23';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Ankbröst med apelsinglaze', 'Rosastekt ankbröst med apelsinglaze, serveras med palsternackspuré och rödvinssås', 159.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-23';

INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID)
SELECT 'Svamp- och tryffelrisotto', 'Lyxig risotto med säsongens svamp, tryffel och hyvlad parmesan', 145.00, LUNCH_ID
FROM LUNCH_MENU WHERE LUNCH_DATE = '2025-03-23';

-- =========================================
-- SECTION 5: WORK SHIFTS with EMPLOYEE ASSIGNMENTS
-- =========================================

-- Clear any existing work shifts for the upcoming period to avoid conflicts
DELETE FROM WORK_SHIFT
WHERE START_TIME >= '2025-03-20 00:00:00' AND START_TIME <= '2025-03-31 23:59:59';

-- Thursday, March 20, 2025
INSERT INTO WORK_SHIFT (START_TIME, END_TIME, DESCRIPTION, EMPLOYEE_ID, SHIFT_STATUS) VALUES
                                                                                          ('2025-03-20 06:00:00', '2025-03-20 14:00:00', 'Frukost- och lunchpass', 1008, 'ASSIGNED'),
                                                                                          ('2025-03-20 08:00:00', '2025-03-20 16:00:00', 'Dagpass kök', 1001, 'ASSIGNED'),
                                                                                          ('2025-03-20 10:00:00', '2025-03-20 18:00:00', 'Lunch- och eftermiddagspass', 1003, 'ASSIGNED'),
                                                                                          ('2025-03-20 14:00:00', '2025-03-20 22:00:00', 'Eftermiddags- och kvällspass', 1007, 'ASSIGNED'),
                                                                                          ('2025-03-20 16:00:00', '2025-03-20 23:59:59', 'Kvällspass kök', 1002, 'ASSIGNED');

-- Friday, March 21, 2025
INSERT INTO WORK_SHIFT (START_TIME, END_TIME, DESCRIPTION, EMPLOYEE_ID, SHIFT_STATUS) VALUES
                                                                                          ('2025-03-21 06:00:00', '2025-03-21 14:00:00', 'Frukost- och lunchpass', 1005, 'ASSIGNED'),
                                                                                          ('2025-03-21 08:00:00', '2025-03-21 16:00:00', 'Dagpass kök', 1003, 'ASSIGNED'),
                                                                                          ('2025-03-21 10:00:00', '2025-03-21 18:00:00', 'Lunch- och eftermiddagspass', NULL, 'OPEN'),
                                                                                          ('2025-03-21 14:00:00', '2025-03-21 22:00:00', 'Eftermiddags- och kvällspass', 1002, 'ASSIGNED'),
                                                                                          ('2025-03-21 16:00:00', '2025-03-21 23:59:59', 'Kvällspass kök', 1006, 'ASSIGNED'),
                                                                                          ('2025-03-21 18:00:00', '2025-03-22 02:00:00', 'Fredagskväll extra', 1004, 'ASSIGNED');

-- Saturday, March 22, 2025
INSERT INTO WORK_SHIFT (START_TIME, END_TIME, DESCRIPTION, EMPLOYEE_ID, SHIFT_STATUS) VALUES
                                                                                          ('2025-03-22 06:00:00', '2025-03-22 14:00:00', 'Helgfrukost- och lunchpass', 1008, 'ASSIGNED'),
                                                                                          ('2025-03-22 08:00:00', '2025-03-22 16:00:00', 'Helgdagpass kök', 1003, 'ASSIGNED'),
                                                                                          ('2025-03-22 10:00:00', '2025-03-22 18:00:00', 'Helglunch- och eftermiddagspass', NULL, 'OPEN'),
                                                                                          ('2025-03-22 14:00:00', '2025-03-22 22:00:00', 'Helgeftermiddags- och kvällspass', NULL, 'OPEN'),
                                                                                          ('2025-03-22 16:00:00', '2025-03-22 23:59:59', 'Helgkvällspass kök', 1006, 'ASSIGNED'),
                                                                                          ('2025-03-22 18:00:00', '2025-03-23 02:00:00', 'Lördagskväll extra', 1004, 'ASSIGNED');

-- Sunday, March 23, 2025
INSERT INTO WORK_SHIFT (START_TIME, END_TIME, DESCRIPTION, EMPLOYEE_ID, SHIFT_STATUS) VALUES
                                                                                          ('2025-03-23 08:00:00', '2025-03-23 16:00:00', 'Söndagsdagpass servering', 1008, 'ASSIGNED'),
                                                                                          ('2025-03-23 10:00:00', '2025-03-23 18:00:00', 'Söndagslunch- och eftermiddagspass', 1003, 'ASSIGNED'),
                                                                                          ('2025-03-23 12:00:00', '2025-03-23 20:00:00', 'Söndagsmiddag servering', 1007, 'ASSIGNED'),
                                                                                          ('2025-03-23 14:00:00', '2025-03-23 22:00:00', 'Söndagseftermiddag kök', 1004, 'ASSIGNED');

-- Monday, March 24, 2025
INSERT INTO WORK_SHIFT (START_TIME, END_TIME, DESCRIPTION, EMPLOYEE_ID, SHIFT_STATUS) VALUES
                                                                                          ('2025-03-24 06:00:00', '2025-03-24 14:00:00', 'Frukost- och lunchpass', 1001, 'ASSIGNED'),
                                                                                          ('2025-03-24 08:00:00', '2025-03-24 16:00:00', 'Dagpass kök', 1005, 'ASSIGNED'),
                                                                                          ('2025-03-24 10:00:00', '2025-03-24 18:00:00', 'Lunch- och eftermiddagspass', 1004, 'ASSIGNED'),
                                                                                          ('2025-03-24 14:00:00', '2025-03-24 22:00:00', 'Eftermiddags- och kvällspass', 1007, 'ASSIGNED'),
                                                                                          ('2025-03-24 16:00:00', '2025-03-24 23:59:59', 'Kvällspass kök', NULL, 'OPEN');

-- Tuesday, March 25, 2025
INSERT INTO WORK_SHIFT (START_TIME, END_TIME, DESCRIPTION, EMPLOYEE_ID, SHIFT_STATUS) VALUES
                                                                                          ('2025-03-25 06:00:00', '2025-03-25 14:00:00', 'Frukost- och lunchpass', 1001, 'ASSIGNED'),
                                                                                          ('2025-03-25 08:00:00', '2025-03-25 16:00:00', 'Dagpass kök', 1005, 'ASSIGNED'),
                                                                                          ('2025-03-25 10:00:00', '2025-03-25 18:00:00', 'Lunch- och eftermiddagspass', 1004, 'ASSIGNED'),
                                                                                          ('2025-03-25 14:00:00', '2025-03-25 22:00:00', 'Eftermiddags- och kvällspass', NULL, 'OPEN'),
                                                                                          ('2025-03-25 16:00:00', '2025-03-25 23:59:59', 'Kvällspass kök', 1002, 'ASSIGNED');

-- Wednesday, March 26, 2025
INSERT INTO WORK_SHIFT (START_TIME, END_TIME, DESCRIPTION, EMPLOYEE_ID, SHIFT_STATUS) VALUES
                                                                                          ('2025-03-26 06:00:00', '2025-03-26 14:00:00', 'Frukost- och lunchpass', 1008, 'ASSIGNED'),
                                                                                          ('2025-03-26 08:00:00', '2025-03-26 16:00:00', 'Dagpass kök', 1003, 'ASSIGNED'),
                                                                                          ('2025-03-26 10:00:00', '2025-03-26 18:00:00', 'Lunch- och eftermiddagspass', NULL, 'OPEN'),
                                                                                          ('2025-03-26 14:00:00', '2025-03-26 22:00:00', 'Eftermiddags- och kvällspass', 1007, 'ASSIGNED'),
                                                                                          ('2025-03-26 16:00:00', '2025-03-26 23:59:59', 'Kvällspass kök', 1002, 'ASSIGNED');