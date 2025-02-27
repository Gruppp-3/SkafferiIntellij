package com.example.first_restaurant.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class DishService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Method for à la carte menu
    public List<Map<String, Object>> getMenuByCategory() {
        return jdbcTemplate.queryForList(
                "SELECT d.dish_name as DISH_NAME, " +
                        "d.dish_description as DISH_DESCRIPTION, " +
                        "d.dish_price as DISH_PRICE, " +
                        "dt.dish_type_name as DISH_TYPE_NAME " +
                        "FROM dish d " +
                        "JOIN dish_type dt ON d.dish_type_id = dt.dish_type_id " +
                        "ORDER BY dt.dish_type_id, d.dish_name"
        );
    }

    /**
     * Deletes a dish from the menu by ID
     * @param id The ID of the dish to delete
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteMenuItem(Long id) {
        try {
            String sql = "DELETE FROM dish WHERE dish_id = ?";
            int rowsAffected = jdbcTemplate.update(sql, id);

            // Return true if at least one row was affected (item was deleted)
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method for today's lunch
    public List<Map<String, Object>> getTodayLunch() {
        String sql =
                "SELECT ld.LUNCH_DISH_NAME as dish_name, " +
                        "ld.LUNCH_DISH_PRICE as dish_price, " +
                        "ld.LUNCH_DISH_DESCRIPTION as dish_description " +
                        "FROM LUNCH_MENU lm " +
                        "JOIN LUNCH_DISH ld ON lm.LUNCH_ID = ld.LUNCH_ID " +
                        "WHERE DATE(lm.LUNCH_DATE) = CURRENT_DATE";

        return jdbcTemplate.queryForList(sql);
    }

    // Method for weekly lunch menu
    public Map<String, List<Map<String, Object>>> getWeeklyLunchMenu() {
        String sql =
                "SELECT ld.LUNCH_DISH_NAME as dish_name, " +
                        "ld.LUNCH_DISH_PRICE as dish_price, " +
                        "'Veckans lunch' as dish_description, " +  // Adding a default description
                        "lm.LUNCH_DATE as menu_date " +
                        "FROM LUNCH_MENU lm " +
                        "JOIN LUNCH_DISH ld ON lm.LUNCH_ID = ld.LUNCH_ID " +
                        "ORDER BY lm.LUNCH_DATE, ld.LUNCH_DISH_PRICE DESC";

        List<Map<String, Object>> allLunchItems = jdbcTemplate.queryForList(sql);
        Map<String, List<Map<String, Object>>> weeklyMenu = new HashMap<>();

        for (Map<String, Object> item : allLunchItems) {
            Date menuDate = (Date) item.get("menu_date");
            String dayName = getDayName(menuDate);
            weeklyMenu.computeIfAbsent(dayName, k -> new ArrayList<>()).add(item);
        }

        return weeklyMenu;
    }

    private String getDayName(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String[] days = {"Söndag", "Måndag", "Tisdag", "Onsdag", "Torsdag", "Fredag", "Lördag"};
        return days[calendar.get(Calendar.DAY_OF_WEEK) - 1];
    }

    // Add this method to your DishService class

    public Map<String, Object> addMenuItem(Map<String, Object> menuItem) {
        try {
            // Extract values from the menuItem map
            String name = (String) menuItem.get("dish_name");
            String description = (String) menuItem.get("dish_description");
            String dishType = (String) menuItem.get("dish_type");
            Double price = Double.valueOf(menuItem.get("dish_price").toString());

            // Get the dish_type_id based on the dish_type
            Integer dishTypeId = getDishTypeIdFromType(dishType);

            // SQL to insert new dish
            String sql = "INSERT INTO dish (dish_name, dish_description, dish_type_id, dish_price) " +
                    "VALUES (?, ?, ?, ?)";

            // Execute update
            jdbcTemplate.update(sql, name, description, dishTypeId, price);

            // Create a map for the response
            Map<String, Object> savedItem = new HashMap<>(menuItem);
            savedItem.put("status", "success");

            return savedItem;
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // Re-throw to be handled by controller
        }
    }

    private Integer getDishTypeIdFromType(String dishType) {
        // Map API dish type values to database dish type IDs
        String dishTypeName;
        switch(dishType) {
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

        // Query to get dish type ID
        String sql = "SELECT dish_type_id FROM dish_type WHERE dish_type_name = ?";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, dishTypeName);
        } catch (Exception e) {
            // If the dish type doesn't exist, log and return a default
            System.err.println("Dish type not found: " + dishTypeName);
            // You could create the dish type here if needed
            return getDefaultDishTypeId();
        }
    }

    private Integer getDefaultDishTypeId() {
        // Get the first dish type ID or a default one
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT dish_type_id FROM dish_type LIMIT 1",
                    Integer.class
            );
        } catch (Exception e) {
            // If no dish types exist, return a reasonable default (may need adjustment)
            return 1;
        }
    }
}