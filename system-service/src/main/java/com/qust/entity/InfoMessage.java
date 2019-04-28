package com.qust.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

// 通知表
@Entity
public class InfoMessage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long restaurant;

    @Column
    private char state; // 状态 a 未读 b 已读 c 删除

    @Column
    private  String message;

    @Column
    private Timestamp createTime;

    @Column
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Long restaurant) {
        this.restaurant = restaurant;
    }

    public char getState() {
        return state;
    }

    public void setState(char state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTime() {
        return createTime;
    }

    public void setTime(Timestamp time) {
        this.createTime = time;
    }
}
