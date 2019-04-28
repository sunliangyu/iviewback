package com.qust.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class FoodClass implements Serializable {
    @EmbeddedId
    private FoodClassPk id;

    @Column
    private  String description;

    public FoodClassPk getId() {
        return id;
    }

    public void setId(FoodClassPk id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
