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

    // Method for today's lunch
    public List<Map<String, Object>> getTodayLunch() {
        String sql =
                "SELECT ld.LUNCH_DISH_NAME as dish_name, " +
                        "ld.LUNCH_DISH_PRICE as dish_price, " +
                        "'Dagens lunch' as dish_description " +  // Adding a default description
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
}