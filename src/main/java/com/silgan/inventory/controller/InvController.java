package com.silgan.inventory.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.silgan.inventory.entity.InvCode;
import com.silgan.inventory.entity.Location;
import com.silgan.inventory.entity.UserRole;
import com.silgan.inventory.service.InvService;
import com.silgan.inventory.service.ItemService;
import com.silgan.inventory.service.UserRoleService;
import com.silgan.inventory.util.Constant;
import com.silgan.inventory.util.PageQueryUtil;
import com.silgan.inventory.util.Result;
import com.silgan.inventory.util.ResultGenerator;
import com.silgan.inventory.vo.MpListVO;

@Controller
@RequestMapping("/inventory")
public class InvController {
	
	@Resource
    private InvService invService;

	@Resource
    private UserRoleService userRoleService;
	
	@Resource
    private ItemService itemService;
	/**
	 * 库存交易界面
	 */
	@GetMapping("/invs")
    public String invs(Model model) {
		model.addAttribute("path", "invs");
		model.addAttribute("title", "库存交易");
		UserRole u = userRoleService.getUser();
		model.addAttribute("fullName", u.getFullName());		
		model.addAttribute("itemList", itemService.selectItems());		
        model.addAttribute("invList", invService.selectInvs());
        model.addAttribute("locList", invService.selectLocs()); 
        UserRole userRole = userRoleService.selectByUserName(u.getUserName());
    	String roleName= userRole.getRoleName();       	
    	model.addAttribute("userRole", roleName);
    	if(null!=roleName  &&( roleName.equals(Constant.ROLE_ADMIN)|| roleName.equals(Constant.ROLE_INV_ADD))) 
    		return "inventory/inv";
    	return "inventory/accessDenied";
    }
	
	/**
	 * 库存查询界面
	 */
	@GetMapping("/invsearch")
    public String invSearch(Model model,@RequestParam(name="itemNumber",required = false) Integer itemNumber) {
		model.addAttribute("path", "invsearch");
		model.addAttribute("title", "库存交易查询");
		UserRole u = userRoleService.getUser();;
		model.addAttribute("fullName", u.getFullName());
		model.addAttribute("itemList", itemService.selectItems());
        model.addAttribute("codeList", invService.findInvCodeList());
        List<Location> locList = invService.findLocationList();
        model.addAttribute("locList", locList);   
        UserRole userRole = userRoleService.selectByUserName(u.getUserName());
    	String roleName= userRole.getRoleName();       	
    	model.addAttribute("userRole", roleName);
    	if(null!=roleName  &&( roleName.equals(Constant.ROLE_ADMIN)|| roleName.equals(Constant.ROLE_INV_SEARCH))) 
    		return "inventory/invsearch";
    	return "inventory/accessDenied";
    }
	
	/**
	 * VMI入库管理
	 */
	@GetMapping("/importinv")
    public String importinv(Model model) {
		model.addAttribute("path", "importinv");
		model.addAttribute("title", "VMI入库");
		UserRole u = userRoleService.getUser();;
		model.addAttribute("fullName", u.getFullName());		
        UserRole userRole = userRoleService.selectByUserName(u.getUserName());
    	String roleName= userRole.getRoleName();       	
    	model.addAttribute("userRole", roleName);
    	model.addAttribute("vendorList", itemService.findVendorList(u.getUserName()));
    	if(null!=roleName  &&( roleName.equals(Constant.ROLE_ADMIN)|| roleName.equals(Constant.ROLE_INV_ADD))) 
    		return "inventory/importinv";
    	return "inventory/accessDenied";
    }
	
	/**
	 * VMI入库记录查询
	 */
	@GetMapping("/vmisearch")
    public String vmisearch(Model model) {
		model.addAttribute("path", "vmisearch");
		model.addAttribute("title", "VMI入库记录查询");
		UserRole u = userRoleService.getUser();
		model.addAttribute("fullName", u.getFullName());		
        UserRole userRole = userRoleService.selectByUserName(u.getUserName());
    	String roleName= userRole.getRoleName();       	
    	model.addAttribute("userRole", roleName);
    	model.addAttribute("vendorList", itemService.findVendorList(u.getUserName()));
    	if(null!=roleName  &&( roleName.equals(Constant.ROLE_ADMIN)|| roleName.equals(Constant.ROLE_INV_ADD))) 
    		return "inventory/vmisearch";
    	return "inventory/accessDenied";
    }
	
	/**
	 * 领料计划
	 */
	@GetMapping("/mp")
    public String mp(Model model) {
		model.addAttribute("path", "mp");
		model.addAttribute("title", "领料计划");
		UserRole u = userRoleService.getUser();
		model.addAttribute("fullName", u.getFullName());		
        UserRole userRole = userRoleService.selectByUserName(u.getUserName());
    	String roleName= userRole.getRoleName();       	
    	model.addAttribute("userRole", roleName);    	
    	model.addAttribute("itemList", itemService.selectItems());
    	model.addAttribute("mpList", invService.selectMpList(u));
    	if(null!=roleName  &&( roleName.equals(Constant.ROLE_ADMIN)|| roleName.equals(Constant.ROLE_INV_ADD))) 
    		return "inventory/mp";
    	return "inventory/accessDenied";
    }
	
	/**
	 * 查看领料计划
	 */
	@GetMapping("/mpsearch")
    public String mpsearch(Model model) {
		model.addAttribute("path", "mpsearch");
		model.addAttribute("title", "查看领料计划");
		UserRole u = userRoleService.getUser();
		model.addAttribute("fullName", u.getFullName());		
        UserRole userRole = userRoleService.selectByUserName(u.getUserName());
    	String roleName= userRole.getRoleName();       	
    	model.addAttribute("userRole", roleName);
    	if(null!=roleName  &&( roleName.equals(Constant.ROLE_ADMIN)|| roleName.equals(Constant.ROLE_INV_ADD))) 
    		return "inventory/mpsearch";
    	return "inventory/accessDenied";
    }
	
	
	/**
	 * 库存查询界面
	 */
	@GetMapping("/invqtysearch")
    public String invqtysearch(Model model,@RequestParam(name="itemNumber",required = false) Integer itemNumber) {
		model.addAttribute("path", "invqtysearch");
		model.addAttribute("title", "库存查询");
		UserRole u = userRoleService.getUser();;
		model.addAttribute("fullName", u.getFullName());
		model.addAttribute("itemList", itemService.selectItems());
        model.addAttribute("codeList", invService.findInvCodeList());
        List<Location> locList = invService.findLocationList();
        model.addAttribute("locList", locList);   
        UserRole userRole = userRoleService.selectByUserName(u.getUserName());
    	String roleName= userRole.getRoleName();       	
    	model.addAttribute("userRole", roleName);
    	if(null!=roleName  &&( roleName.equals(Constant.ROLE_ADMIN)|| roleName.equals(Constant.ROLE_INV_SEARCH))) 
    		return "inventory/invqtysearch";
    	return "inventory/accessDenied";
    }
	
	/**
	 * 库存查询界面
	 */
	@GetMapping("/vmiqtysearch")
    public String vmiqtysearch(Model model,@RequestParam(name="itemNumber",required = false) Integer itemNumber) {
		model.addAttribute("path", "vmiqtysearch");
		model.addAttribute("title", "库存查询");
		UserRole u = userRoleService.getUser();;
		model.addAttribute("fullName", u.getFullName());
		model.addAttribute("itemList", itemService.selectItems());
        model.addAttribute("codeList", invService.findInvCodeList());
        List<Location> locList = invService.findLocationList();
        model.addAttribute("locList", locList);   
        UserRole userRole = userRoleService.selectByUserName(u.getUserName());
    	String roleName= userRole.getRoleName();       	
    	model.addAttribute("userRole", roleName);
    	if(null!=roleName  &&( roleName.equals(Constant.ROLE_ADMIN)|| roleName.equals(Constant.ROLE_INV_SEARCH))) 
    		return "inventory/vmiqtysearch";
    	return "inventory/accessDenied";
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
        String itemNumbers = (String) params.get("itemNumbers");
        if(itemNumbers!=null && !"".equals(itemNumbers)) {
        	String[] arr = itemNumbers.split(",");
        	params.put("itemNumbers", Arrays.asList(arr));        	
        }else {
        	params.put("itemNumbers", null);  
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);            
        return ResultGenerator.genSuccessResult(invService.getInvsPage(pageUtil));
    }
	/**
     * 根据ItemNumber查询库存交易记录
     */
    @PostMapping(value = "/invs/getInvs")
    @ResponseBody
    public Result getInvs(Model model,@RequestBody Integer[] itemNumbers) {           
        return ResultGenerator.genSuccessResult(invService.getInvsByItemNumbers(itemNumbers));
    }
    /**
     * 保存库存查询
     */
    @PostMapping(value = "/invs/save")
    @ResponseBody
    public Result save(@RequestBody String jsonStr) {
    	UserRole u = userRoleService.getUser();
    	if(invService.saveInv(jsonStr,u.getUserName())) {
    		return ResultGenerator.genSuccessResult(); 
    	}else {
    		return ResultGenerator.genFailResult("保存失败");  
    	} 	  		
    }
    
    /**
     * 获取Trans_code
     */
    @GetMapping(value = "/invs/getTransCode")
    @ResponseBody
    public List<InvCode> getTransCode(@RequestParam Map<String, Object> params) {    	
    	return invService.selectTranscodeByParam(params);
    }
    
    /**
     * 库存查询列表
     */
    @GetMapping(value = "/invs/vmilist")
    @ResponseBody
    public Result vmilist(Model model,@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        
        PageQueryUtil pageUtil = new PageQueryUtil(params);            
        return ResultGenerator.genSuccessResult(invService.getVmiInvsPage(pageUtil));
    }
   
    /**
     * VMI获取当前库存数
     */
    @PostMapping(value = "/vmi/getCurrentQty")
    @ResponseBody
    public Result getCurrentQty(@RequestBody String jsonStr) {
    	return ResultGenerator.genSuccessResult(invService.getVmiCurrentQty(jsonStr));
    }
    
    /**
     * 	根据供应商获取Item
     */
    @GetMapping(value = "/vmi/getItem")
    @ResponseBody
    public Result getItem(@RequestParam(name="vendId") String vendId) {
    	return ResultGenerator.genJsonResult(itemService.getItemByVendId(vendId));
    }
    
    /**
     * 领料计划获取当前库存数和已预约数
     */
    @GetMapping(value = "/mp/getMpQty")
    @ResponseBody
    public Result getMpQty(@RequestParam(name="itemNumber") String itemNumber) {
    	return ResultGenerator.genSuccessResult(invService.getMpQty(itemNumber));
    }
    
    /**
     * 保存库存查询
     */
    @PostMapping(value = "/mp/save")
    @ResponseBody
    public Result saveMp(@RequestBody String jsonStr) {
    	UserRole u = userRoleService.getUser();
    	Map<String,Object> retMap = invService.saveMp(jsonStr,u.getUserName());
    	if(retMap.get("message")!=null) {
    		return ResultGenerator.genFailResult(String.valueOf(retMap.get("message")));	
    	}
    	return ResultGenerator.genSuccessResult(); 
    		  		
    }
    
    /**
     * 查看领料计划
     */
    @GetMapping(value = "/invs/mplist")
    @ResponseBody
    public Result mplist(Model model,@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        
        PageQueryUtil pageUtil = new PageQueryUtil(params);  
        return ResultGenerator.genSuccessResult(invService.getMpInvsPage(pageUtil));
    }
    
    @PostMapping("/invs/checkDoneMp")
    @ResponseBody
    public Result checkDoneMp(@RequestBody String mpNumber) {       
        if (invService.checkDone(mpNumber)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("完成失败");
        }
    }
    
    @GetMapping("/invs/findItemByMp")
    @ResponseBody
    public Result findItemByMp(@RequestParam("mpNumber") String mpNumber) { 
    	UserRole u = userRoleService.getUser();
    	MpListVO vo = new MpListVO();
    	vo.setFullName(u.getFullName());
    	vo.setMpVoList(invService.findItemByMp(mpNumber));
        return ResultGenerator.genSuccessResult(vo);       
    }
    
    /**
     * 详情
     */
    @GetMapping("/inv/{mpNumber}")
    @ResponseBody
    public Result info(@PathVariable("mpNumber") String mpNumber) {
        return ResultGenerator.genSuccessResult(invService.selectByMp(mpNumber));
    }
    
    /**
     * 	取消预约
     */
    @PostMapping("/inv/cancelMp/{mpNumber}")
    @ResponseBody
    public Result cancelMp(@PathVariable("mpNumber") String mpNumber) {
        return ResultGenerator.genSuccessResult(invService.cancelMp(mpNumber));
    }
    
    /**
     * 库存查询
     */
    @GetMapping(value = "/invs/invqtylist")
    @ResponseBody
    public Result invqtylist(Model model,@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        
        PageQueryUtil pageUtil = new PageQueryUtil(params);  
        return ResultGenerator.genSuccessResult(invService.getInvQtyPage(pageUtil));
    }
    
}
