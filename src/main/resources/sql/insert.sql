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
                                                      ('2025-02-20', 'Live Music Night'),
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

-- 6. Lunch Menu for the Week
INSERT INTO LUNCH_MENU (LUNCH_DATE) VALUES
                                        ('2025-02-17'), -- Monday (ID: 1)
                                        ('2025-02-18'), -- Tuesday (ID: 2)
                                        ('2025-02-19'), -- Wednesday (ID: 3)
                                        ('2025-02-20'), -- Thursday (ID: 4)
                                        ('2025-02-21'); -- Friday (ID: 5)

-- 7. Lunch Dishes
INSERT INTO LUNCH_DISH (LUNCH_DISH_NAME, LUNCH_DISH_DESCRIPTION, LUNCH_DISH_PRICE, LUNCH_ID) VALUES
                                                                                                 -- Monday
                                                                                                 ('Kycklingfilé', 'Serveras med ugnsrostade grönsaker, örtig vitlökssås och ris', 125.00, 1),
                                                                                                 ('Vegetarisk Lasagne', 'Gjord på linser, zucchini och morot', 115.00, 1),
                                                                                                 ('Svampsoppa', 'Krämig soppa med skogschampinjoner', 95.00, 1),

                                                                                                 -- Tuesday
                                                                                                 ('Pannbiff', 'Serveras med kokt potatis, löksås och lingonsylt', 125.00, 2),
                                                                                                 ('Halloumigryta', 'Smakrik gryta med ris och färska grönsaker', 115.00, 2),
                                                                                                 ('Tomatsoppa', 'Krämig tomatsoppa med basilika och vitlöksbröd', 95.00, 2),

                                                                                                 -- Wednesday
                                                                                                 ('Torskrygg', 'Serveras med potatispuré, citronsås och haricots verts', 125.00, 3),
                                                                                                 ('Vegetarisk Curry', 'Kryddig gryta med kokosmjölk, kikärtor och grönsaker', 115.00, 3),
                                                                                                 ('Gulaschsoppa', 'Traditionell gulaschsoppa med surdegsbröd', 95.00, 3),

                                                                                                 -- Thursday
                                                                                                 ('Fläsk med Raggmunk', 'Serveras med rårörda lingon', 125.00, 4),
                                                                                                 ('Bönbiffar', 'Serveras med rostad potatis och örtsås', 115.00, 4),
                                                                                                 ('Minestronesoppa', 'Italiensk grönsakssoppa med pasta', 95.00, 4),

                                                                                                 -- Friday
                                                                                                 ('Grillad Lax', 'Serveras med hollandaisesås, sparris och pressad potatis', 125.00, 5),
                                                                                                 ('Svamprisotto', 'Krämig risotto med skogssvamp och parmesan', 115.00, 5),
                                                                                                 ('Skaldjurssoppa', 'Krämig skaldjurssoppa med aioli', 95.00, 5);

-- 8. Sample Orders
INSERT INTO ORDERS (ORDER_DATE, TABLE_NUMBER, IS_FINISHED) VALUES
                                                               ('2025-02-17', 1, FALSE),
                                                               ('2025-02-17', 2, TRUE);

-- 9. Sample Order Items
INSERT INTO ORDER_DISH (ORDER_ID, DISH_ID, NOTE_TEXT, IS_DONE, IS_SERVED) VALUES
                                                                              (1, 1, 'Extra sås', FALSE, FALSE),
                                                                              (1, 2, 'Med extra ost', FALSE, FALSE),
                                                                              (2, 4, 'Utan is', TRUE, TRUE);

-- 10. Work Shifts
INSERT INTO WORK_SHIFT (START_TIME, END_TIME, DESCRIPTION, EMPLOYEE_ID) VALUES
                                                                            ('2025-02-17 08:00:00', '2025-02-17 16:00:00', 'Morgonpass', 1),
                                                                            ('2025-02-17 16:00:00', '2025-02-17 23:59:59', 'Kvällspass', 2);


-- 11. Bookings
INSERT INTO BOOKING (NAME, EMAIL, PHONE, DATE, TIME, PEOPLE_COUNT, TABLE_NUMBER)
VALUES
    ('Alice Johnson', 'alice@example.com', '0701234567', '2024-02-19', '18:00:00', 4, 2),
    ('Bob Smith', 'bob@example.com', '0707654321', '2024-02-19', '18:00:00', 2, 1),
    ('Carol Davis', 'carol@example.com', '0703334444', '2024-02-19', '19:30:00', 6, 3),
    ('David Wilson', 'david@example.com', '0705556666', '2024-02-20', '18:30:00', 4, 5),
    ('Eva Brown', 'eva@example.com', '0708889999', '2024-02-20', '18:30:00', 2, 4),
    ('Frank Miller', 'frank@example.com', '0702223333', '2024-02-20', '20:00:00', 6, 6);