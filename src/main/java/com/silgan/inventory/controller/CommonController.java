package com.silgan.inventory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.silgan.inventory.util.Result;
import com.silgan.inventory.util.ResultGenerator;

@Controller
@RequestMapping("/common")
public class CommonController {

	 /**
     * 	根据供应商获取Item
     */
    @GetMapping(value = "/print/printMp")
    @ResponseBody
    public Result getItem(@RequestParam(name="vendId") String vendId) {
//    	return ResultGenerator.genJsonResult(itemService.getItemByVendId(vendId));
    	return ResultGenerator.genSuccessResult();
    }
}
