-- Check what's in your database
SELECT lm.LUNCH_DATE, ld.LUNCH_DISH_NAME
FROM LUNCH_MENU lm
         JOIN LUNCH_DISH ld ON lm.LUNCH_ID = ld.LUNCH_ID
ORDER BY lm.LUNCH_DATE;

-- Check lunch menu entries
SELECT * FROM LUNCH_MENU ORDER BY LUNCH_DATE;

-- Check lunch dishes with their dates
SELECT lm.LUNCH_DATE, ld.LUNCH_DISH_NAME
FROM LUNCH_MENU lm
         JOIN LUNCH_DISH ld ON lm.LUNCH_ID = ld.LUNCH_ID
ORDER BY lm.LUNCH_DATE;