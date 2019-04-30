package com.qust.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Date;


// 物品流水表
@Entity
public class RawMarerialOrder implements Serializable {
    @Id
    private Long id;
    @Column
    private Long restaurant;
    @Column
    private Long material;
    @Column
    private int count;
    @Column
    private int output;
    @Column
    private Date createTime;
    @Column
    private String remark;

    public Long getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Long restaurant) {
        this.restaurant = restaurant;
    }

    public Long getMaterial() {
        return material;
    }

    public void setMaterial(Long material) {
        this.material = material;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getOutput() {
        return output;
    }

    public void setOutput(int output) {
        this.output = output;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
