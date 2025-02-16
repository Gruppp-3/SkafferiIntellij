package com.example.first_restaurant.controller;
import com.example.first_restaurant.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MenuController {
    @Autowired
    private MenuItemRepository menuRepo;

    @GetMapping("/menu")
    public String showMenu(Model model) {
        model.addAttribute("menuItems", menuRepo.findAll());
        return "menu";  // This refers to menu.html
    }
}