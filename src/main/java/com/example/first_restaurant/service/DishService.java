package com.example.first_restaurant.service;
import com.example.first_restaurant.entity.Dish;
import com.example.first_restaurant.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class DishService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getMenuByCategory() {
        String sql = """
            SELECT dt.DISH_TYPE_NAME, d.DISH_NAME, d.DISH_DESCRIPTION, d.DISH_PRICE 
            FROM DISH d 
            JOIN DISH_TYPE dt ON d.DISH_TYPE_ID = dt.DISH_TYPE_ID 
            ORDER BY dt.DISH_TYPE_ID, d.DISH_NAME
        """;

        return jdbcTemplate.queryForList(sql);
    }

    public void printMenu() {
        List<Map<String, Object>> menu = getMenuByCategory();
        String currentCategory = "";

        for (Map<String, Object> item : menu) {
            String category = (String) item.get("DISH_TYPE_NAME");
            if (!category.equals(currentCategory)) {
                System.out.println("\n=== " + category + " ===");
                currentCategory = category;
            }

            System.out.printf("%-30s %5.2f kr\n",
                    item.get("DISH_NAME"),
                    ((Number) item.get("DISH_PRICE")).doubleValue());
            System.out.println("    " + item.get("DISH_DESCRIPTION") + "\n");
        }
    }
}