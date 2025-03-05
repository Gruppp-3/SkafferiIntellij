package com.example.first_restaurant.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class DishService {

    private static final Logger logger = LoggerFactory.getLogger(DishService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Ny funktion för WaiterApplication
    public List<Map<String, Object>> getMenuByCategoryAndType(String category) {
        String sql = "SELECT d.dish_name as DISH_NAME, " +
                "d.dish_description as DISH_DESCRIPTION, " +
                "d.dish_price as DISH_PRICE, " +
                "dt.dish_type_name as DISH_TYPE_NAME " +
                "FROM dish d " +
                "JOIN dish_type dt ON d.dish_type_id = dt.dish_type_id " +
                "WHERE dt.dish_type_name = ? " +
                "ORDER BY d.dish_name";
        return jdbcTemplate.queryForList(sql, category);
    }

    // Method for à la carte menu
    public List<Map<String, Object>> getMenuByCategory() {
        logger.debug("Fetching menu by category...");
        List<Map<String, Object>> menu = jdbcTemplate.queryForList(
                "SELECT d.dish_id as DISH_ID, " +
                        "d.dish_name as DISH_NAME, " +
                        "d.dish_description as DISH_DESCRIPTION, " +
                        "d.dish_price as DISH_PRICE, " +
                        "dt.dish_type_name as DISH_TYPE_NAME " +
                        "FROM dish d " +
                        "JOIN dish_type dt ON d.dish_type_id = dt.dish_type_id " +
                        "ORDER BY dt.dish_type_id, d.dish_name"
        );
        logger.debug("Menu fetched: {} items", menu.size());
        return menu;
    }

    public Map<String, Object> updateMenuItem(Long id, Map<String, Object> menuItem) {
        logger.debug("Updating dish with ID: {}", id);
        try {
            // Extract the new values
            String name = (String) menuItem.get("DISH_NAME");
            String description = (String) menuItem.get("DISH_DESCRIPTION");
            Double price = Double.valueOf(menuItem.get("DISH_PRICE").toString());
            logger.debug("New values - name: {}, description: {}, price: {}", name, description, price);

            // Determine dish type only if provided; otherwise, leave it unchanged
            String dishType = (String) menuItem.get("DISH_TYPE_NAME");
            Integer dishTypeId = null;
            if (dishType != null) {
                logger.debug("Dish type provided: {}", dishType);
                dishTypeId = getDishTypeIdFromType(dishType);
                logger.debug("Mapped dish type '{}' to dish_type_id: {}", dishType, dishTypeId);
            } else {
                logger.debug("No dish type provided; dish_type_id will not be updated.");
            }

            String sql;
            int rowsAffected;
            if (dishTypeId != null) {
                // Update all fields including dish type
                sql = "UPDATE dish SET dish_name = ?, dish_description = ?, dish_type_id = ?, dish_price = ? WHERE dish_id = ?";
                logger.debug("Executing SQL: {} with parameters: {}, {}, {}, {}, {}", sql, name, description, dishTypeId, price, id);
                rowsAffected = jdbcTemplate.update(sql, name, description, dishTypeId, price, id);
            } else {
                // Update only name, description, and price
                sql = "UPDATE dish SET dish_name = ?, dish_description = ?, dish_price = ? WHERE dish_id = ?";
                logger.debug("Executing SQL: {} with parameters: {}, {}, {}, {}", sql, name, description, price, id);
                rowsAffected = jdbcTemplate.update(sql, name, description, price, id);
            }

            logger.debug("Rows affected: {}", rowsAffected);
            if (rowsAffected > 0) {
                Map<String, Object> updatedItem = new HashMap<>(menuItem);
                updatedItem.put("dish_id", id);
                updatedItem.put("status", "success");
                logger.info("Dish with ID {} updated successfully.", id);
                return updatedItem;
            } else {
                String errMsg = "No rows updated; check if the ID " + id + " exists.";
                logger.error(errMsg);
                throw new Exception(errMsg);
            }
        } catch (Exception e) {
            logger.error("Error updating dish with ID " + id, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes a dish from the menu by ID
     * @param id The ID of the dish to delete
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteMenuItem(Long id) {
        logger.debug("Deleting dish with ID: {}", id);
        try {
            String sql = "DELETE FROM dish WHERE dish_id = ?";
            int rowsAffected = jdbcTemplate.update(sql, id);
            logger.debug("Rows affected by delete: {}", rowsAffected);
            return rowsAffected > 0;
        } catch (Exception e) {
            logger.error("Error deleting dish with ID " + id, e);
            return false;
        }
    }

    // Method for today's lunch
    public List<Map<String, Object>> getTodayLunch() {
        logger.debug("Fetching today's lunch items...");
        String sql =
                "SELECT ld.LUNCH_DISH_NAME as dish_name, " +
                        "ld.LUNCH_DISH_PRICE as dish_price, " +
                        "ld.LUNCH_DISH_DESCRIPTION as dish_description " +
                        "FROM LUNCH_MENU lm " +
                        "JOIN LUNCH_DISH ld ON lm.LUNCH_ID = ld.LUNCH_ID " +
                        "WHERE DATE(lm.LUNCH_DATE) = CURRENT_DATE";
        List<Map<String, Object>> todayLunch = jdbcTemplate.queryForList(sql);
        logger.debug("Today's lunch items fetched: {} items", todayLunch.size());
        return todayLunch;
    }

    // Method for weekly lunch menu
    public Map<String, List<Map<String, Object>>> getWeeklyLunchMenu() {
        logger.debug("Fetching weekly lunch menu...");
        String sql =
                "SELECT ld.LUNCH_DISH_NAME as dish_name, " +
                        "ld.LUNCH_DISH_PRICE as dish_price, " +
                        "'Veckans lunch' as dish_description, " +  // Adding a default description
                        "lm.LUNCH_DATE as menu_date " +
                        "FROM LUNCH_MENU lm " +
                        "JOIN LUNCH_DISH ld ON lm.LUNCH_ID = ld.LUNCH_ID " +
                        "ORDER BY lm.LUNCH_DATE, ld.LUNCH_DISH_PRICE DESC";
        List<Map<String, Object>> allLunchItems = jdbcTemplate.queryForList(sql);
        logger.debug("Total lunch items for week: {}", allLunchItems.size());
        Map<String, List<Map<String, Object>>> weeklyMenu = new HashMap<>();

        for (Map<String, Object> item : allLunchItems) {
            Date menuDate = (Date) item.get("menu_date");
            String dayName = getDayName(menuDate);
            weeklyMenu.computeIfAbsent(dayName, k -> new ArrayList<>()).add(item);
        }
        logger.debug("Weekly menu constructed with {} days.", weeklyMenu.size());
        return weeklyMenu;
    }

    private String getDayName(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String[] days = {"Söndag", "Måndag", "Tisdag", "Onsdag", "Torsdag", "Fredag", "Lördag"};
        String dayName = days[calendar.get(Calendar.DAY_OF_WEEK) - 1];
        logger.debug("Converted date {} to day name: {}", date, dayName);
        return dayName;
    }

    public Map<String, Object> addMenuItem(Map<String, Object> menuItem) {
        logger.debug("Adding new menu item with data: {}", menuItem);
        try {
            // Extract values from the menuItem map
            String name = (String) menuItem.get("dish_name");
            String description = (String) menuItem.get("dish_description");
            String dishType = (String) menuItem.get("dish_type");
            Double price = Double.valueOf(menuItem.get("dish_price").toString());
            logger.debug("Parsed values - name: {}, description: {}, dish_type: {}, price: {}", name, description, dishType, price);

            // Get the dish_type_id based on the dish_type
            Integer dishTypeId = getDishTypeIdFromType(dishType);
            logger.debug("Mapped dish type '{}' to dish_type_id: {}", dishType, dishTypeId);

            // SQL to insert new dish
            String sql = "INSERT INTO dish (dish_name, dish_description, dish_type_id, dish_price) " +
                    "VALUES (?, ?, ?, ?)";
            logger.debug("Executing SQL: {} with parameters: {}, {}, {}, {}", sql, name, description, dishTypeId, price);
            jdbcTemplate.update(sql, name, description, dishTypeId, price);

            // Create a map for the response
            Map<String, Object> savedItem = new HashMap<>(menuItem);
            savedItem.put("status", "success");
            logger.info("New menu item added successfully: {}", savedItem);
            return savedItem;
        } catch (Exception e) {
            logger.error("Error adding new menu item with data: " + menuItem, e);
            e.printStackTrace();
            throw e; // Re-throw to be handled by controller
        }
    }

    private Integer getDishTypeIdFromType(String dishType) {
        // Map API dish type values to database dish type IDs
        String dishTypeName;
        switch (dishType) {
            case "APPETIZER":
                dishTypeName = "Förrätter";
                break;
            case "MAIN":
                dishTypeName = "Varmrätter";
                break;
            case "DESSERT":
                dishTypeName = "Efterrätter";
                break;
            case "DRINK":
                dishTypeName = "Drycker";
                break;
            case "VEGETARIAN":
                dishTypeName = "Vegetariska";
                break;
            default:
                dishTypeName = "Övrigt";
        }
        logger.debug("Mapping dish type '{}' to dish type name '{}'", dishType, dishTypeName);

        // Query to get dish type ID
        String sql = "SELECT dish_type_id FROM dish_type WHERE dish_type_name = ?";
        try {
            Integer dishTypeId = jdbcTemplate.queryForObject(sql, Integer.class, dishTypeName);
            logger.debug("Found dish_type_id {} for dish type name '{}'", dishTypeId, dishTypeName);
            return dishTypeId;
        } catch (Exception e) {
            logger.error("Dish type not found: " + dishTypeName, e);
            // You could create the dish type here if needed
            return getDefaultDishTypeId();
        }
    }

    private Integer getDefaultDishTypeId() {
        try {
            Integer defaultId = jdbcTemplate.queryForObject("SELECT dish_type_id FROM dish_type LIMIT 1", Integer.class);
            logger.debug("Default dish_type_id fetched: {}", defaultId);
            return defaultId;
        } catch (Exception e) {
            logger.error("Error fetching default dish_type_id", e);
            return 1;
        }
    }
}
