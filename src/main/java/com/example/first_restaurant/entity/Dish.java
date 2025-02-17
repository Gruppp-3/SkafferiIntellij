package com.example.first_restaurant.entity;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "DISH")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DISH_ID")
    private Long id;

    @Column(name = "DISH_NAME")
    private String name;

    @Column(name = "DISH_DESCRIPTION")
    private String description;

    @ManyToOne
    @JoinColumn(name = "DISH_TYPE_ID")
    private DishType dishType;

    @Column(name = "DISH_PRICE")
    private BigDecimal price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DishType getDishType() {
        return dishType;
    }

    public void setDishType(DishType dishType) {
        this.dishType = dishType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}