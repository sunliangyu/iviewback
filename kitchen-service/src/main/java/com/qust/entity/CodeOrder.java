package com.qust.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;
import java.sql.Date;

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
    private Boolean flag;//y 外卖 b// 非外卖
    @Column
    private Date createTime;
    @Column
    private Date updateTime;
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

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
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
