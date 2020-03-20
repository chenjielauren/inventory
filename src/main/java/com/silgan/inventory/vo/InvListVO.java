package com.silgan.inventory.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class InvListVO {

	private Integer id;

    private String itemNumber;
    
    private String itemDesc;
    
    private String itemModel;
    
    private String itemBrand;
    
    private Boolean vmi;

    private Integer safeVmi;

    private Integer expireDate;
    
    private String itemUnit;

    private String fromInv;

    private String toInv;

    private String fromLoc;

    private String toLoc;

    private Integer qty;
    
    private Integer mpQty;

    private Integer allowQty;
    
    private String lotNumber;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date invTime;
    
    private String createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String modifyUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifyTime;
    
    private Boolean state;
    
    private String transKey;
    
    private String icNumber;
    
    private String note;
    
    private String  vendorName;

    private String  vmiInv;
    
    private String invName;
    
    private String locName;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date icTime;
    
    private String  fullName;
    
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

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public String getItemModel() {
		return itemModel;
	}

	public void setItemModel(String itemModel) {
		this.itemModel = itemModel;
	}

	public String getItemBrand() {
		return itemBrand;
	}

	public void setItemBrand(String itemBrand) {
		this.itemBrand = itemBrand;
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
	
	public Integer getMpQty() {
		return mpQty;
	}

	public void setMpQty(Integer mpQty) {
		this.mpQty = mpQty;
	}
	
	public Integer getAllowQty() {
		return allowQty;
	}

	public void setAllowQty(Integer allowQty) {
		this.allowQty = allowQty;
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

	public Boolean getState() {
		return state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	public String getTransKey() {
		return transKey;
	}

	public void setTransKey(String transKey) {
		this.transKey = transKey;
	}

	public String getIcNumber() {
		return icNumber;
	}

	public void setIcNumber(String icNumber) {
		this.icNumber = icNumber;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVmiInv() {
		return vmiInv;
	}

	public void setVmiInv(String vmiInv) {
		this.vmiInv = vmiInv;
	}

	public Date getIcTime() {
		return icTime;
	}

	public void setIcTime(Date icTime) {
		this.icTime = icTime;
	}

	public String getLocName() {
		return locName;
	}

	public void setLocName(String locName) {
		this.locName = locName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getInvName() {
		return invName;
	}

	public void setInvName(String invName) {
		this.invName = invName;
	}
	
}
