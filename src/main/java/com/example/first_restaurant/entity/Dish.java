package com.example.first_restaurant.entity;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "ORDER_DISH")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_DISH_ID")
    private Long id;

    @Column(name = "ORDER_ID")
    private Long orderID;
    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "DISH_NAME")
    private String dish_name;

    @Column(name = "DISH_COUNT")
    private Integer dish_count;

    @Column(name = "IS_FINISHED")
    private Boolean isFinished;

    public Long getId(){return id;}
    public Long getOrderID(){return orderID;}
    public String getCategory(){return category;}
    public String getDish_name(){return dish_name;}
    public Integer getDish_count(){return dish_count;}
    public Boolean getIsFinished(){return isFinished;}

    public void setOrderID(Long orderID){
        this.orderID=orderID;
    }
    public void setCategory(String category){
        this.category=category;
    }
    public void setDish_name(String dish_name){
        this.dish_name=dish_name;
    }
    public void setDish_count(Integer dish_count){
        this.dish_count=dish_count;
    }
    public void setIsFinished(Boolean isFinished){this.isFinished=isFinished;}
}