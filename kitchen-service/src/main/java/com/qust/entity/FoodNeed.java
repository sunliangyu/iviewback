package com.qust.entity;

import org.hibernate.annotations.CollectionId;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class FoodNeed implements Serializable {


    @EmbeddedId
    private FoodNeedPk id;

    @Column
    private Long restaurant;

    @Column
    private int count;

    public FoodNeedPk getId() {
        return id;
    }

    public void setId(FoodNeedPk id) {
        this.id = id;
    }

    public Long getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Long restaurant) {
        this.restaurant = restaurant;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
