package com.silgan.inventory.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.stereotype.Service;

import com.silgan.inventory.dao.UserRoleMapper;
import com.silgan.inventory.entity.UserRole;
import com.silgan.inventory.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService {
	
	@Autowired
    private UserRoleMapper userRolenMapper;

	@Override
	public UserRole selectByUserName(String userName) {
		return userRolenMapper.selectByUserName(userName);
	}

	@Override
	public UserRole getUser() {
		LdapUserDetailsImpl ldapDetails = (LdapUserDetailsImpl) SecurityContextHolder
	            .getContext().getAuthentication().getPrincipal();
    	String dn = ldapDetails.getDn();
    	String userName = ldapDetails.getUsername();
    	UserRole u = new UserRole();
    	u.setUserName(userName);
    	int beginIndex = dn.indexOf("cn=") + 3;
    	int endIndex = dn.indexOf(",");
    	u.setFullName(dn.substring(beginIndex, endIndex));
    	return u;
	}
	
//	@Override
//	public UserRole getUser() {
//    	String userName = "lchen2";
//    	UserRole u = new UserRole();
//    	u.setUserName(userName);
//    	u.setFullName("Lauren Chen");
//    	return u;
//	}

}
