package com.silgan.inventory.controller;

import java.security.Principal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.silgan.inventory.service.InvService;
import com.silgan.inventory.service.ItemVendorService;

@Controller
public class LoginController {
	@Resource
    private ItemVendorService itemVendorService;
	@Resource
    private InvService invService;

	@GetMapping({"", "/", "/index", "/index.html"})
    public String login(Model model,Principal principal) {		
		model.addAttribute("path", "index");
	    model.addAttribute("itemCount", itemVendorService.getItemsCount());
	    model.addAttribute("invCount", invService.getInvCount());
//	    model.addAttribute("categoryCount", 9);
//	    model.addAttribute("linkCount", 3);
//	    model.addAttribute("tagCount", 7);	    
		String username = getUserName(principal);
        if(username!=null) {
        	model.addAttribute("username", getUserName(principal));
        	return "inventory/index";
        }        	
        return "inventory/login";
    }

    private String getUserName(Principal principal) {
        if (principal == null) {
            return "anonymous";
        } else {       	
            return principal.getName();
        }
    }
    @GetMapping({"/loginout"})
    public String loginout(Model model,Principal principal) {		
		return "/login";
    }
}
