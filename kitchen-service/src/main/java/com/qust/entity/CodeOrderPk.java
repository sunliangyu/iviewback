package com.qust.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CodeOrderPk  implements Serializable {

    @Column
    private Long  indent;

    @Column
    private Long restayrant;

    @Column
    private long food;

    public long getFood() {
        return food;
    }

    public void setFood(long food) {
        this.food = food;
    }

    public Long getOrder() {
        return indent;
    }

    public void setOrder(Long indent) {
        this.indent = indent;
    }

    public Long getRestayrant() {
        return restayrant;
    }

    public void setRestayrant(Long restayrant) {
        this.restayrant = restayrant;
    }
}
