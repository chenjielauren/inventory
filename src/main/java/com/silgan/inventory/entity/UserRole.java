package com.silgan.inventory.entity;

public class UserRole {

    private String userName;

    private String roleName;
    
    private String fullName;
    
    private String vendId;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getVendId() {
		return vendId;
	}

	public void setVendId(String vendId) {
		this.vendId = vendId;
	}

}