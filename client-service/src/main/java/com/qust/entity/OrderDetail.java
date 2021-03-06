package com.qust.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

//订单详细表
@Entity
public class OrderDetail implements Serializable {

    @EmbeddedId
    private OrderDetailPk orderDetailPk;
    @Column
    private Long client;
    @Column
    private String remark;
    @Column
    private  int counts;
    @Column
    private String foodName;
    @Column
    private Long restaurant;
    @Column
    private int price;

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Long getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Long restaurant) {
        this.restaurant = restaurant;
    }

    public OrderDetailPk getOrderDetailPk() {
        return orderDetailPk;
    }

    public void setOrderDetailPk(OrderDetailPk orderDetailPk) {
        this.orderDetailPk = orderDetailPk;
    }

    public Long getClient() {
        return client;
    }

    public void setClient(Long client) {
        this.client = client;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
