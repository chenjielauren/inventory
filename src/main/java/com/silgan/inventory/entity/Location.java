package com.silgan.inventory.entity;

public class Location {
	private Integer id;

    private String locCode;
    
    private String locDesc;
        
    private Integer locRank;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLocCode() {
		return locCode;
	}

	public void setLocCode(String locCode) {
		this.locCode = locCode;
	}

	public String getLocDesc() {
		return locDesc;
	}

	public void setLocDesc(String locDesc) {
		this.locDesc = locDesc;
	}

	public Integer getLocRank() {
		return locRank;
	}

	public void setLocRank(Integer locRank) {
		this.locRank = locRank;
	}

	

}