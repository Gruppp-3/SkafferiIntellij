package com.example.first_restaurant.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.*;


@Service
public class DishService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Your existing method for à la carte menu
    public List<Map<String, Object>> getMenuByCategory() {
        // Your existing implementation for à la carte menu
        return jdbcTemplate.queryForList("SELECT d.dish_name, d.dish_description, d.dish_price, dt.dish_type_name " +
                "FROM dish d " +
                "JOIN dish_type dt ON d.dish_type_id = dt.dish_type_id " +
                "ORDER BY dt.dish_type_id, d.dish_name");
    }

    // New method for today's lunch
    public List<Map<String, Object>> getTodayLunch() {
        String sql = "SELECT ld.LUNCH_DISH_NAME as dish_name, " +
                "ld.LUNCH_DISH_PRICE as dish_price " +
                "FROM LUNCH_MENU lm " +
                "JOIN LUNCH_DISH ld ON lm.LUNCH_ID = ld.LUNCH_ID " +
                "WHERE lm.LUNCH_DATE = CURRENT_DATE";

        return jdbcTemplate.queryForList(sql);
    }

    // New method for weekly lunch menu
    public Map<String, List<Map<String, Object>>> getWeeklyLunchMenu() {
        String sql = "SELECT ld.LUNCH_DISH_NAME as dish_name, " +
                "ld.LUNCH_DISH_PRICE as dish_price, " +
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

    // Helper method for getting day names
    private String getDayName(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String[] days = {"Söndag", "Måndag", "Tisdag", "Onsdag", "Torsdag", "Fredag", "Lördag"};
        return days[calendar.get(Calendar.DAY_OF_WEEK) - 1];
    }
}