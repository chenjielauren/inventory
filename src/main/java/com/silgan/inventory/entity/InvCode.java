package com.silgan.inventory.entity;

public class InvCode {
    private Integer id;

    private String invCode;
    
    private String invDesc;
    
    private Boolean vmi;
    
    private Integer invRank;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getInvCode() {
		return invCode;
	}

	public void setInvCode(String invCode) {
		this.invCode = invCode;
	}

	public String getInvDesc() {
		return invDesc;
	}

	public void setInvDesc(String invDesc) {
		this.invDesc = invDesc;
	}

	public Boolean getVmi() {
		return vmi;
	}

	public void setVmi(Boolean vmi) {
		this.vmi = vmi;
	}

	public Integer getInvRank() {
		return invRank;
	}

	public void setInvRank(Integer invRank) {
		this.invRank = invRank;
	}


}