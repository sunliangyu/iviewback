package com.qust.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

//购物车
@Entity
public class ShoppingCart implements Serializable {

    @EmbeddedId
    private ShoppingCartPk shoppingCartPk;

    @Column
    private int count;

    public ShoppingCartPk getShoppingCardPk() {
        return shoppingCartPk;
    }

    public void setShoppingCardPk(ShoppingCartPk shoppingCardPk) {
        this.shoppingCartPk = shoppingCardPk;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
