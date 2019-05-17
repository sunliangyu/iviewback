package com.qust.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class FoodImagePk implements Serializable {

    @Column(name = "restaurant")
    private Long restaurant;

    @Column(name = "food")
    private Long food;

    @Column(name = "url")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Long restaurant) {
        this.restaurant = restaurant;
    }

    public Long getFood() {
        return food;
    }

    public void setFood(Long food) {
        this.food = food;
    }
}
