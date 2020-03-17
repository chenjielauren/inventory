package com.silgan.inventory.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Item {
    private Integer itemNumber;

    private String itemDesc;

    private String itemBrand;

    private String itemModel;

    private Boolean vmi;

    private Integer safeVmi;

    private Integer expireDate;
    
    private String itemUnit;

    private String createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")   
    private Date createTime;

    private String modifyUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifyTime;
      
    List<String> vendIds;
    
    private Boolean state;
    
    public Item() {
    	
    }
    
	public Item(Integer itemNumber, String itemDesc, String itemBrand, String itemModel) {
		super();
		this.itemNumber = itemNumber;
		this.itemDesc = itemDesc;
		this.itemBrand = itemBrand;
		this.itemModel = itemModel;
	}

	public Integer getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(Integer itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public String getItemBrand() {
		return itemBrand;
	}

	public void setItemBrand(String itemBrand) {
		this.itemBrand = itemBrand;
	}

	public String getItemModel() {
		return itemModel;
	}

	public void setItemModel(String itemModel) {
		this.itemModel = itemModel;
	}

	public Boolean getVmi() {
		return vmi;
	}

	public void setVmi(Boolean vmi) {
		this.vmi = vmi;
	}

	public Integer getSafeVmi() {
		return safeVmi;
	}

	public void setSafeVmi(Integer safeVmi) {
		this.safeVmi = safeVmi;
	}
	
	public Integer getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Integer expireDate) {
		this.expireDate = expireDate;
	}

	public String getItemUnit() {
		return itemUnit;
	}

	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
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

	public Boolean getState() {
		return state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	public List<String> getVendIds() {
		return vendIds;
	}

	public void setVendIds(List<String> vendIds) {
		this.vendIds = vendIds;
	}

	@Override
    public String toString() {
        return "{" +
                "itemNumber:" + itemNumber +
                ", itemDesc:" + itemDesc +
                ", itemBrand:" + itemBrand +
                ", itemModel:" + itemModel +
                '}';
    }
	
}