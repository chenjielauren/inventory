package com.silgan.inventory.vo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.silgan.inventory.entity.Item;
import com.silgan.inventory.entity.MaterialPlan;

public class MpListVO {

	private Integer id;

    private String itemNumber;

    private Integer qty;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date mpTime;

    private String createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String note;
    
    private Integer state;
    
    private Integer currentQty;//当前库存
    
    private Integer existQty;//已预约数

    private String mpNumber;//预约号
    
    private List<MaterialPlan> mpList;
    
    private Item item;
    
    private List<InvListVO> invList;
    
    private Integer minusQty;
    
    private String fullName;
    
    private List<MpListVO> mpVoList;
    
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

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Date getMpTime() {
		return mpTime;
	}

	public void setMpTime(Date mpTime) {
		this.mpTime = mpTime;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getCurrentQty() {
		return currentQty;
	}

	public void setCurrentQty(Integer currentQty) {
		this.currentQty = currentQty;
	}

	public Integer getExistQty() {
		return existQty;
	}

	public void setExistQty(Integer existQty) {
		this.existQty = existQty;
	}

	public String getMpNumber() {
		return mpNumber;
	}

	public void setMpNumber(String mpNumber) {
		this.mpNumber = mpNumber;
	}

	public List<MaterialPlan> getMpList() {
		return mpList;
	}

	public void setMpList(List<MaterialPlan> mpList) {
		this.mpList = mpList;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public List<InvListVO> getInvList() {
		return invList;
	}

	public void setInvList(List<InvListVO> invList) {
		this.invList = invList;
	}

	public Integer getMinusQty() {
		return minusQty;
	}

	public void setMinusQty(Integer minusQty) {
		this.minusQty = minusQty;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public List<MpListVO> getMpVoList() {
		return mpVoList;
	}

	public void setMpVoList(List<MpListVO> mpVoList) {
		this.mpVoList = mpVoList;
	}

}
