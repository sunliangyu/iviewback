package com.qust.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;
import java.sql.Date;

@Entity
public class OrderFinsh implements Serializable {

    @EmbeddedId
    private CodeOrderPk id;

    @Column
    private int count;

    @Column
    private Date createTime;

    @Column
    private String remark;

    @Column
    private Long slectStaff;
    @Column
    private Long codeStaff;
    @Column
    private char flag;

    public CodeOrderPk getId() {
        return id;
    }

    public void setId(CodeOrderPk id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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

    public Long getSlectStaff() {
        return slectStaff;
    }

    public void setSlectStaff(Long slectStaff) {
        this.slectStaff = slectStaff;
    }

    public Long getCodeStaff() {
        return codeStaff;
    }

    public void setCodeStaff(Long codeStaff) {
        this.codeStaff = codeStaff;
    }

    public char getFlag() {
        return flag;
    }

    public void setFlag(char flag) {
        this.flag = flag;
    }
}
