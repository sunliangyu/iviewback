package com.qust.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
public class SalaryFlow  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long staff;

    @Column
    private Long restaurant;

    @Column
    private float output;

    @Column
    private Date time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStaff() {
        return staff;
    }

    public void setStaff(Long staff) {
        this.staff = staff;
    }

    public Long getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Long restaurant) {
        this.restaurant = restaurant;
    }

    public float getOutput() {
        return output;
    }

    public void setOutput(float output) {
        this.output = output;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column
    private String remark;



}
