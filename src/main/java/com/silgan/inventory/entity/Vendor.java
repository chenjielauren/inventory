package com.silgan.inventory.entity;

public class Vendor {

    private String vendId;
    
    private String vendName;
    
    private Boolean vmi;
    
    private Boolean status;
    
    private String vmiInv;

	public String getVendId() {
		return vendId;
	}

	public void setVendId(String vendId) {
		this.vendId = vendId;
	}

	public String getVendName() {
		return vendName;
	}

	public void setVendName(String vendName) {
		this.vendName = vendName;
	}

	public Boolean getVmi() {
		return vmi;
	}

	public void setVmi(Boolean vmi) {
		this.vmi = vmi;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getVmiInv() {
		return vmiInv;
	}

	public void setVmiInv(String vmiInv) {
		this.vmiInv = vmiInv;
	}

}