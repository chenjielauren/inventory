package com.silgan.inventory.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Inv {
    private Integer id;

    private String itemNumber;

    private String fromInv;

    private String toInv;

    private String fromLoc;

    private String toLoc;

    private Integer qty;

    private String lotNumber;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date invTime;

    private String createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String modifyUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifyTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getFromInv() {
        return fromInv;
    }

    public void setFromInv(String fromInv) {
        this.fromInv = fromInv;
    }

    public String getToInv() {
        return toInv;
    }

    public void setToInv(String toInv) {
        this.toInv = toInv;
    }

    public String getFromLoc() {
        return fromLoc;
    }

    public void setFromLoc(String fromLoc) {
        this.fromLoc = fromLoc;
    }

    public String getToLoc() {
        return toLoc;
    }

    public void setToLoc(String toLoc) {
        this.toLoc = toLoc;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public Date getInvTime() {
        return invTime;
    }

    public void setInvTime(Date invTime) {
        this.invTime = invTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}