package com.qust.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

//购物车
@Entity
public class ShoppingCard implements Serializable {

    @EmbeddedId
    private ShoppingCardPk shoppingCardPk;

    @Column
    private int count;

    public ShoppingCardPk getShoppingCardPk() {
        return shoppingCardPk;
    }

    public void setShoppingCardPk(ShoppingCardPk shoppingCardPk) {
        this.shoppingCardPk = shoppingCardPk;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
