package com.qust.entity;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class FoodNeedPk implements Serializable {
    @Column(name="food")
    private  Long food;

    @Column(name="material")
    private Long  material;

    public Long getFood() {
        return food;
    }

    public Long getMaterial() {
        return material;
    }

    public void setMaterial(Long material) {
        this.material = material;
    }

    public void setFood(Long food) {
        this.food = food;
    }
}
