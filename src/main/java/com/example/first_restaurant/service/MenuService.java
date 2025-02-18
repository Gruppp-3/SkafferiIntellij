package com.example.first_restaurant.service;

import com.example.first_restaurant.entity.MenuItem;
import com.example.first_restaurant.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MenuService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    public List<MenuItem> getDailyLunchMenu() {
        // Assuming "LUNCH" is a category in your database
        return menuItemRepository.findByCategory("LUNCH");
    }

    public List<MenuItem> getWeeklyLunchMenu() {
        // Assuming "WEEKLY_LUNCH" is a category in your database
        return menuItemRepository.findByCategory("WEEKLY_LUNCH");
    }

    public Map<String, List<MenuItem>> getALaCarteMenu() {
        // Get all menu items and group them by category
        List<MenuItem> allItems = menuItemRepository.findAll();

        return allItems.stream()
                .filter(item -> !"LUNCH".equals(item.getCategory())
                        && !"WEEKLY_LUNCH".equals(item.getCategory()))
                .collect(Collectors.groupingBy(MenuItem::getCategory));
    }

    // Get menu items by course
    public List<MenuItem> getMenuItemsByCourse(Long courseId) {
        return menuItemRepository.findByCourseId(courseId);
    }

    // Get menu items by category and course
    public List<MenuItem> getMenuItemsByCategoryAndCourse(String category, Long courseId) {
        return menuItemRepository.findByCategoryAndCourseId(category, courseId);
    }
}