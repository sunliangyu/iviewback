package com.qust.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


//订单表
@Entity
public class FoodOrder implements Serializable {

    @Id
    private Long indent;

    @Column
    private Long client;

    @Column
    private Timestamp createTime;

    @Column
    private char flow;

    @Column
    private boolean payFlag;

    @Column
    private boolean payMethod;

    @Column
    private String remark;

    @Column
    private int price;

    @Column
    private boolean flag;//waimai

    @Column
    private int people; //ren shu

    @Column
    private String location;

    @Column
    private int countFoods;
    @Column
    private Long restaurant;
    @Column
    private Timestamp accessTime;
    @Column
    private String reason;
    @Column
    private int finish;

    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Timestamp getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(Timestamp accessTime) {
        this.accessTime = accessTime;
    }

    public Long getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Long restaurant) {
        this.restaurant = restaurant;
    }

    public int getCountFoods() {
        return countFoods;
    }

    public void setCountFoods(int countFoods) {
        this.countFoods = countFoods;
    }

    public Long getIndent() {
        return indent;
    }

    public void setIndent(Long indent) {
        this.indent = indent;
    }

    public Long getClient() {
        return client;
    }

    public void setClient(Long client) {
        this.client = client;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public char getFlow() {
        return flow;
    }

    public void setFlow(char flow) {
        this.flow = flow;
    }

    public boolean isPayFlag() {
        return payFlag;
    }

    public void setPayFlag(boolean payFlag) {
        this.payFlag = payFlag;
    }

    public boolean isPayMethod() {
        return payMethod;
    }

    public void setPayMethod(boolean payMethod) {
        this.payMethod = payMethod;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
