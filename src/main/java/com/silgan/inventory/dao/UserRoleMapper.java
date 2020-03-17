package com.silgan.inventory.dao;

import java.util.List;

import com.silgan.inventory.entity.UserRole;

public interface UserRoleMapper {

    UserRole selectByUserName(String userName);
    
    List<String> selectVendorByUserName(String userName);
}