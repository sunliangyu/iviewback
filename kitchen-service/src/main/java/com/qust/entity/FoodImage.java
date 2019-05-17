package com.qust.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class FoodImage implements Serializable {

    @EmbeddedId
    private FoodImagePk id;

    public FoodImagePk getId() {
        return id;
    }

    public void setId(FoodImagePk id) {
        this.id = id;
    }



}
