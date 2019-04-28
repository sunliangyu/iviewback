package com.qust.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class FoodClassPk implements Serializable {

    @Column
    private Long restaurant;

    @Column
    private Long foodclass;

    public Long getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Long restaurant) {
        this.restaurant = restaurant;
    }

    public Long getFoodclass() {
        return foodclass;
    }

    public void setFoodclass(Long foodclass) {
        this.foodclass = foodclass;
    }
}
