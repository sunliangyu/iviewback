package com.qust.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Staff implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private  Long  restaurant;

    @Column
    private String name;

    @Column
    private  String tentryTime;

    @Column
    private int salary;

    @Column
    private String lastTime;

    @Column
    private String position;

    @Column
    private  char state;

    @Column
    private char age;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTentryTime() {
        return tentryTime;
    }

    public void setTentryTime(String tentryTime) {
        this.tentryTime = tentryTime;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public char getState() {
        return state;
    }

    public void setState(char state) {
        this.state = state;
    }

    public char getAge() {
        return age;
    }

    public void setAge(char age) {
        this.age = age;
    }
}
