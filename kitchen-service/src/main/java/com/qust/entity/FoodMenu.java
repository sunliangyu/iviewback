package com.qust.entity;

import javax.jnlp.IntegrationService;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
@Entity
public class FoodMenu implements Serializable {

    @Id
    private Long id;
    @Column
    private Long restaurant;
    @Column
    private String name;
    @Column
    private Integer foodclass;
    @Column
    private int sellNumber;
    @Column
    private String createTime;
    @Column
    private int price;
    @Column
    private String desciption;
    @Column
    private int timeCost;
    @Column
    private boolean flag;

    public Long getId() {
        return id;
    }

    public Long getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Long restaurant) {
        this.restaurant = restaurant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFoodclass() {
        return foodclass;
    }

    public void setFoodclass(Integer foodclass) {
        this.foodclass = foodclass;
    }

    public int getSellNumber() {
        return sellNumber;
    }

    public void setSellNumber(int sellNumber) {
        this.sellNumber = sellNumber;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public int getTimeCost() {
        return timeCost;
    }

    public void setTimeCost(int timeCost) {
        this.timeCost = timeCost;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
