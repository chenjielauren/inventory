package com.silgan.inventory.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.silgan.inventory.entity.Unit;
import com.silgan.inventory.entity.UserRole;
import com.silgan.inventory.entity.Vendor;
import com.silgan.inventory.service.InvService;
import com.silgan.inventory.service.ItemService;
import com.silgan.inventory.service.UserRoleService;
import com.silgan.inventory.util.CommonUtil;
import com.silgan.inventory.util.Constant;
import com.silgan.inventory.util.PageQueryUtil;
import com.silgan.inventory.util.Result;
import com.silgan.inventory.util.ResultGenerator;

@Controller
@RequestMapping("/inventory")
public class ItemController {
	
	@Resource
    private ItemService itemService;
	
	@Resource
    private InvService invService;
	
	@Resource
    private UserRoleService userRoleService;
	
	@GetMapping("/items")
    public String itemPage(Model model,@RequestParam(name="itemNumber",required = false) Integer itemNumber) {
		model.addAttribute("path", "items");
		model.addAttribute("title", "物料管理");		
		model.addAttribute("item", new Item());
		UserRole u = userRoleService.getUser();
		model.addAttribute("fullName", u.getFullName());		
        List<Unit> unitList = itemService.findUnitList();
        model.addAttribute("unitList", unitList);
        List<Vendor> vendorList = itemService.findVendorList(u.getUserName());
        model.addAttribute("vendorList", vendorList);
    	String username = u.getUserName();
        UserRole userRole = userRoleService.selectByUserName(username);
        String roleName= userRole.getRoleName();       	
    	model.addAttribute("userRole", roleName);
    	if(null!=roleName  &&( roleName.equals(Constant.ROLE_ADMIN)|| roleName.equals(Constant.ROLE_ITEM))) {
    		return "inventory/item";
    	}
    	return "inventory/accessDenied";
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
        return ResultGenerator.genSuccessResult(itemService.getItemsPage(pageUtil));
    }
    
    @PostMapping(value = "/items/checkExistItems")
    @ResponseBody
    public Result checkExistItems(@RequestBody Item item) {
    	List<Item> existItems = itemService.findItemByKeyword(item);
    	if(existItems!=null && existItems.size()>0) {
    		return ResultGenerator.genSuccessResult(true);
    	}else {
    		return ResultGenerator.genSuccessResult(false);
    	}
    	 		
    }
    
    @PostMapping(value = "/items/save")
    @ResponseBody
    public Result save(@ModelAttribute Item item) {
    	UserRole u = userRoleService.getUser();
    	String username = u.getUserName();
    	item.setCreateUser(username);
    	item.setCreateTime(new Date());
    	//默认启用
    	item.setState(true);
    	List<Item> existItems = itemService.findItemByKeyword(item);
    	if(existItems!=null && existItems.size()>0) {
    		return ResultGenerator.genSuccessResult(existItems);
    	}
    	else if(itemService.saveItem(item)) {
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
    	Item item = itemService.selectByItemNumber(itemNumber);
        return ResultGenerator.genSuccessResult(item);
    }
	
    @PostMapping(value = "/items/update")
    @ResponseBody
    public Result update(@ModelAttribute Item item) {
    	UserRole u = userRoleService.getUser();
    	String username = u.getUserName();
    	item.setModifyUser(username);
    	item.setModifyTime(new Date());
    	List<Item> existItems = itemService.findItemByKeyword(item);
    	if(existItems!=null && existItems.size()>0) {
    		return ResultGenerator.genSuccessResult(existItems);
    	}else if(itemService.updateItem(item)) {
    		return ResultGenerator.genSuccessResult();
    	}else {
    		return ResultGenerator.genFailResult("修改失败");
    	}   		
    }
    
    @PostMapping(value = "/items/enableItem")
    @ResponseBody
    public Result enableItem(@RequestBody Integer[] itemNumbers) {
        if (null==itemNumbers || itemNumbers.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }

        if (itemService.enableItem(itemNumbers)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }
    
    @PostMapping(value = "/items/disableItem")
    @ResponseBody
    public Result disableItem(@RequestBody Integer[] itemNumbers) {
        if (null==itemNumbers || itemNumbers.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        else if (itemService.deleteBatch(itemNumbers)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }
    
}
