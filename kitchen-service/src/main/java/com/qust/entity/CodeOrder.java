package com.qust.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
public class CodeOrder implements Serializable {

    @EmbeddedId
    private CodeOrderPk id;
    @Column
    private  String remark;
    @Column
    private  int count;
    @Column
    private char state;// A准备B制作中C完成
    @Column
    private char flag;//A 外卖 b 非外卖
    @Column
    private Timestamp createTime;
    @Column
    private Timestamp updateTime;
    @Column
    private Long selectStaff;
    @Column
    private Long codeStaff;

    @Column
    private String evaluation;

    public CodeOrderPk getId() {
        return id;
    }

    public void setId(CodeOrderPk id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public char getState() {
        return state;
    }

    public void setState(char state) {
        this.state = state;
    }

    public char getFlag() {
        return flag;
    }

    public void setFlag(char flag) {
        this.flag = flag;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Long getSelectStaff() {
        return selectStaff;
    }

    public void setSelectStaff(Long selectStaff) {
        this.selectStaff = selectStaff;
    }

    public Long getCodeStaff() {
        return codeStaff;
    }

    public void setCodeStaff(Long codeStaff) {
        this.codeStaff = codeStaff;
    }

    public String getComment() {
        return evaluation;
    }

    public void setComment(String evaluation) {
        this.evaluation = evaluation;
    }



}
