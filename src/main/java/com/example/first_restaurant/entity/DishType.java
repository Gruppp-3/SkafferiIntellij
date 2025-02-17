package com.example.first_restaurant.entity;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "DISH_TYPE")
public class DishType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DISH_TYPE_ID")
    private Long id;

    @Column(name = "DISH_TYPE_NAME")
    private String name;

    @OneToMany(mappedBy = "dishType")
    private List<Dish> dishes;

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
