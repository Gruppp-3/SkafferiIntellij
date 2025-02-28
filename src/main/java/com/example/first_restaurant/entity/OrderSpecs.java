package com.example.first_restaurant.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.AbstractMap;

public class OrderSpecs {
    @JsonProperty("category")
    private String category;
    @JsonProperty("meal")
    private String meal;
    @JsonProperty("count")
    private Integer count;
    public String getCategory(){return category;}
    public String getMeal(){return meal;}
    public Integer getCount(){return count;}
    public void setCategory(String category){
        this.category=category;
    }
    public void setMeal(String meal){this.meal=meal;}
    public void setCount(Integer count){this.count=count;}
}
