package com.silgan.inventory.service;

import com.silgan.inventory.entity.UserRole;

public interface UserRoleService {
	UserRole selectByUserName(String userName);
	UserRole getUser();
}
