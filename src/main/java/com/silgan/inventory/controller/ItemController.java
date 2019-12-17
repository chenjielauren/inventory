package com.silgan.inventory.controller;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.silgan.inventory.entity.Item;
import com.silgan.inventory.entity.ItemVendor;
import com.silgan.inventory.service.ItemVendorService;
import com.silgan.inventory.util.PageQueryUtil;
import com.silgan.inventory.util.Result;
import com.silgan.inventory.util.ResultGenerator;

@Controller
@RequestMapping("/inventory")
public class ItemController {
	
	@Resource
    private ItemVendorService itemVendorService;
	
	@GetMapping("/items")
    public String itemPage(Model model) {
		model.addAttribute("path", "items");
		model.addAttribute("item", new Item());
		List<ItemVendor> list = itemVendorService.findVendorList();
        model.addAttribute("vendorList", list); 
        return "inventory/item";
    }
	/**
     * 分类列表
     */
    @GetMapping(value = "/items/list")
    @ResponseBody
    public Result list(Model model,@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);            
        return ResultGenerator.genSuccessResult(itemVendorService.getItemsPage(pageUtil));
    }
    
    @PostMapping(value = "/items/save")
    @ResponseBody
    public Result save(@ModelAttribute Item item,Principal principal) {
    	item.setCreateUser(getUserName(principal));
    	item.setCreateTime(new Date());
    	if(itemVendorService.saveItem(item)) {
    		return ResultGenerator.genSuccessResult();
    	}else {
    		return ResultGenerator.genFailResult("保存失败");
    	}   		
    }
    /**
     * 详情
     */
    @GetMapping("/items/edit/{itemNumber}")
    @ResponseBody
    public Result info(@PathVariable("itemNumber") Integer itemNumber) {
        Item item = itemVendorService.selectByItemNumber(itemNumber);
        return ResultGenerator.genSuccessResult(item);
    }
	
    @PostMapping(value = "/items/update")
    @ResponseBody
    public Result update(@ModelAttribute Item item,Principal principal) {
    	item.setModifyUser(getUserName(principal));
    	item.setModifyTime(new Date());
    	if(itemVendorService.updateItem(item)) {
    		return ResultGenerator.genSuccessResult();
    	}else {
    		return ResultGenerator.genFailResult("修改失败");
    	}   		
    }  
    
    @PostMapping(value = "/items/delete")
    @ResponseBody
    public Result delete(@RequestBody Integer[] itemNumbers) {
        if (null==itemNumbers || itemNumbers.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (itemVendorService.deleteBatch(itemNumbers)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("删除失败");
        }
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
