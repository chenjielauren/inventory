package com.silgan.inventory.entity;

import java.util.Date;

public class ItemVendor {
    private Integer vendorId;

    private String vendorName;

    private String vendorPn;

    private Integer vendorRank;

    private Date createTime;

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorPn() {
        return vendorPn;
    }

    public void setVendorPn(String vendorPn) {
        this.vendorPn = vendorPn;
    }

    public Integer getVendorRank() {
        return vendorRank;
    }

    public void setVendorRank(Integer vendorRank) {
        this.vendorRank = vendorRank;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}