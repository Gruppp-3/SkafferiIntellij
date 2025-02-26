DROP DATABASE IF EXISTS RestaurantDB;
CREATE DATABASE RestaurantDB;
USE RestaurantDB;

-- 1. Tabell för anställda
CREATE TABLE EMPLOYEE (
                          EMPLOYEE_ID INT AUTO_INCREMENT PRIMARY KEY,
                          FIRST_NAME VARCHAR(25),
                          LAST_NAME VARCHAR(25),
                          PHONE_NUMBER VARCHAR(15),
                          IS_ADMIN BOOLEAN
);

-- 2. Tabell för maträttstyper (förrätter, varmrätter, efterrätter, vegetariska, drycker)
CREATE TABLE DISH_TYPE (
                           DISH_TYPE_ID INT AUTO_INCREMENT PRIMARY KEY,
                           DISH_TYPE_NAME VARCHAR(50) UNIQUE
);

-- 3. Tabell för event
CREATE TABLE EVENT (
                       EVENT_ID INT AUTO_INCREMENT PRIMARY KEY,
                       EVENT_DATE DATE,
                       EVENT_DESCRIPTION VARCHAR(255)
);

-- 4. Tabell för lunchmeny (flyttad upp!)
CREATE TABLE LUNCH_MENU (
                            LUNCH_ID INT AUTO_INCREMENT PRIMARY KEY,
                            LUNCH_DATE DATE
);

-- 5. Tabell för lunchrätter (nu funkar FOREIGN KEY)
CREATE TABLE LUNCH_DISH (
                            LUNCH_DISH_ID INT AUTO_INCREMENT PRIMARY KEY,
                            LUNCH_DISH_NAME VARCHAR(50),
                            LUNCH_DISH_DESCRIPTION VARCHAR(255),
                            LUNCH_DISH_PRICE DECIMAL(5,2),
                            LUNCH_ID INT,
                            FOREIGN KEY (LUNCH_ID) REFERENCES LUNCH_MENU(LUNCH_ID)
);

-- 6. Tabell för maträtter
CREATE TABLE DISH (
                      DISH_ID INT AUTO_INCREMENT PRIMARY KEY,
                      DISH_NAME VARCHAR(100),
                      DISH_DESCRIPTION VARCHAR(255),
                      DISH_TYPE_ID INT,
                      DISH_PRICE DECIMAL(5,2),
                      FOREIGN KEY (DISH_TYPE_ID) REFERENCES DISH_TYPE(DISH_TYPE_ID)
);

-- 7. Tabell för drycker
CREATE TABLE DRINK (
                       DRINK_ID INT AUTO_INCREMENT PRIMARY KEY,
                       DRINK_NAME VARCHAR(50),
                       DRINK_PRICE DECIMAL(5,2)
);

-- 8. Tabell för beställda ordrar
CREATE TABLE ORDERS (
                        ORDER_ID INT AUTO_INCREMENT PRIMARY KEY,
                        ORDER_DATE DATE,
                        TABLE_NUMBER INT,
                        IS_FINISHED BOOLEAN,
                        STATUS VARCHAR(50) DEFAULT 'ACTIVE'  -- ✅Ny status kolumn för order
);

-- 9. Tabell för beställda rätter
CREATE TABLE ORDER_DISH (
                            ORDER_DISH_ID INT AUTO_INCREMENT PRIMARY KEY,
                            ORDER_ID INT,
                            DISH_ID INT,
                            NOTE_TEXT VARCHAR(100),
                            IS_DONE BOOLEAN,
                            IS_SERVED BOOLEAN,
                            STATUS VARCHAR(50) DEFAULT 'PENDING',  -- Ny kolumn för individuell rätt, PENDING eller DONE
                            FOREIGN KEY (ORDER_ID) REFERENCES ORDERS(ORDER_ID) ON DELETE CASCADE,
                            FOREIGN KEY (DISH_ID) REFERENCES DISH(DISH_ID)
);



-- 10. Tabell för arbetspass
CREATE TABLE WORK_SHIFT (
                            WORK_SHIFT_ID INT AUTO_INCREMENT PRIMARY KEY,
                            START_TIME TIMESTAMP,
                            END_TIME TIMESTAMP,
                            DESCRIPTION VARCHAR(100),
                            EMPLOYEE_ID INT,
                            FOREIGN KEY (EMPLOYEE_ID) REFERENCES EMPLOYEE(EMPLOYEE_ID)
);

-- 11. Tabell för bokningar
CREATE TABLE BOOKING (
                         BOOKING_ID INT AUTO_INCREMENT PRIMARY KEY,
                         NAME VARCHAR(255) NOT NULL,
    EMAIL VARCHAR(255) NOT NULL,
    PHONE VARCHAR(20) NOT NULL,
    DATE DATE NOT NULL,
    TIME TIME NOT NULL,
    PEOPLE_COUNT INT NOT NULL,
    TABLE_NUMBER INT NOT NULL,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );