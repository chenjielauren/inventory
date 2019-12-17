package com.silgan.inventory.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.silgan.inventory.entity.Inv;
import com.silgan.inventory.entity.InvCode;
import com.silgan.inventory.entity.Location;
import com.silgan.inventory.service.InvService;
import com.silgan.inventory.util.PageQueryUtil;
import com.silgan.inventory.util.Result;
import com.silgan.inventory.util.ResultGenerator;

@Controller
@RequestMapping("/inventory")
public class InvController {
	
	@Resource
    private InvService invService;
	/**
	 * 库存交易界面
	 */
	@GetMapping("/invs")
    public String addInv(Model model) {
		model.addAttribute("path", "invs");
		List<InvCode> list = invService.findInvCodeList();
        model.addAttribute("codeList", list);
        List<Location> locList = invService.findLocationList();
        model.addAttribute("locList", locList);        
        return "inventory/inv";
    }
	
	/**
	 * 库存查询界面
	 */
	@GetMapping("/invsearch")
    public String invSearch(Model model) {
		model.addAttribute("path", "invsearch");
		List<InvCode> list = invService.findInvCodeList();
        model.addAttribute("codeList", list);
        List<Location> locList = invService.findLocationList();
        model.addAttribute("locList", locList);        
        return "inventory/invsearch";
    }
	/**
     * 库存查询列表
     */
    @GetMapping(value = "/invs/list")
    @ResponseBody
    public Result list(Model model,@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);            
        return ResultGenerator.genSuccessResult(invService.getInvsPage(pageUtil));
    }
    /**
     * 保存库存查询
     */
    @PostMapping(value = "/invs/save")
    @ResponseBody
    public Result save(@RequestBody String jsonStr,Principal principal) {
    	ObjectMapper mapper = new ObjectMapper();
    	try {
			Inv[] invs = mapper.readValue(jsonStr, Inv[].class);
		    if(null!=invs && invs.length>0) {
	    		for(Inv inv:invs) {
	    			inv.setCreateUser(getUserName(principal));
	    	    	inv.setCreateTime(new Date());
	    	    	invService.saveInv(inv);	    	    	
	    		}
	    		return ResultGenerator.genSuccessResult();
	    	}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return ResultGenerator.genFailResult("保存失败");   	  		
    }
  
    //get login username
    private String getUserName(Principal principal) {
        if (principal == null) {
            return ""
            		+ "anonymous";
        } else {

            final UserDetails currentUser = (UserDetails) ((Authentication) principal).getPrincipal();
            Collection<? extends GrantedAuthority> authorities = currentUser.getAuthorities();
            for (GrantedAuthority grantedAuthority : authorities) {
                System.out.println(grantedAuthority.getAuthority());
            }
            return principal.getName();
        }
    }

	
  

}
