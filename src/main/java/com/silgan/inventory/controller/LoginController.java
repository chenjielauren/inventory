package com.silgan.inventory.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.silgan.inventory.entity.UserRole;
import com.silgan.inventory.service.InvService;
import com.silgan.inventory.service.ItemService;
import com.silgan.inventory.service.UserRoleService;

@Controller
public class LoginController {
	@Resource
    private ItemService itemService;
	@Resource
    private InvService invService;
	@Resource
    private UserRoleService userRoleService;

	@GetMapping({"/","/login", "/index", "/index.html"})
    public String login(Model model) {		  
		UserRole u = userRoleService.getUser();
    	String username = String.valueOf(u.getUserName());
        if(username!=null) {
        	model.addAttribute("username", username);
        	model.addAttribute("fullName", u.getFullName());
        	//show menu according to userRole
        	UserRole userRole = userRoleService.selectByUserName(username);
        	String roleName= userRole.getRoleName();       	
        	model.addAttribute("userRole", roleName);
        	model.addAttribute("path", "index");
        	return "inventory/index";
        }        	
        return "inventory/login";
    }
}
