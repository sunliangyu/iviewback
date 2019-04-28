package com.qust.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class FoodImage implements Serializable {

    @EmbeddedId
    private FoodImagePk id;

    @Column
    private String url;

    @Column
    private char flag;

    public FoodImagePk getId() {
        return id;
    }

    public void setId(FoodImagePk id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public char getFlag() {
        return flag;
    }

    public void setFlag(char flag) {
        this.flag = flag;
    }

}
