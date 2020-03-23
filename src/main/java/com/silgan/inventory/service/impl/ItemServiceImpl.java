package com.silgan.inventory.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.silgan.inventory.dao.ItemMapper;
import com.silgan.inventory.dao.ItemVendorMapper;
import com.silgan.inventory.dao.UnitMapper;
import com.silgan.inventory.dao.UserRoleMapper;
import com.silgan.inventory.dao.VendorMapper;
import com.silgan.inventory.entity.Item;
import com.silgan.inventory.entity.LogItem;
import com.silgan.inventory.entity.Unit;
import com.silgan.inventory.entity.Vendor;
import com.silgan.inventory.service.ItemService;
import com.silgan.inventory.util.CommonUtil;
import com.silgan.inventory.util.PageQueryUtil;
import com.silgan.inventory.util.PageResult;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
    private ItemMapper itemMapper;

	@Autowired
    private VendorMapper vendorMapper;
	
	@Autowired
    private UnitMapper unitMapper;
	
	@Autowired
    private ItemVendorMapper itemVendorMapper;

	@Autowired
    private UserRoleMapper userMapper;
	@Override
	public List<Vendor> findVendorList(String userName) {
		List<String> vendIds = userMapper.selectVendorByUserName(userName);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("vendIds", vendIds);
		return vendorMapper.selectVendors(params);
	}

	@Override
	@Transactional
	public Boolean saveItem(Item item) {
		int rows = itemMapper.insert(item);
		itemVendorMapper.deleteByItemNumber(item.getItemNumber());
		if(item.getVmi() && !CollectionUtils.isEmpty(item.getVendIds())) {			
			itemVendorMapper.insert(item);
		}
		return  rows>0 ;
	}

	@Override
	@Transactional
	public Boolean updateItem(Item item) {
		Item selectItem = itemMapper.selectByPrimaryKey(item.getItemNumber());
		item.setCreateUser(selectItem.getCreateUser());
		item.setCreateTime(selectItem.getCreateTime());
		Boolean result = itemMapper.updateByPrimaryKey(item) > 0;
		itemVendorMapper.deleteByItemNumber(item.getItemNumber());
		if(item.getVmi() && !CollectionUtils.isEmpty(item.getVendIds())) {			
			itemVendorMapper.insert(item);
		}
		//更新item时记录log
		if(result) {
			LogItem logItem = new LogItem();
			BeanUtils.copyProperties(item, logItem);
			itemMapper.insertLog(logItem);
			return result;
		}
		return false;
	}

	@Override
	public Item selectByItemNumber(Integer itemNumber) {
		Item item = itemMapper.selectByPrimaryKey(itemNumber);
		return item;
	}

	@Override
	public PageResult getItemsPage(PageQueryUtil pageUtil) {
		List<Item> itemList = itemMapper.findItemList(pageUtil);
        int total = itemMapper.getTotalItems(pageUtil);
        PageResult pageResult = new PageResult(itemList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
	}

	@Override
	public Boolean deleteBatch(Integer[] itemNumbers) {
		return itemMapper.deleteBatch(itemNumbers) > 0;
	}

	@Override
	public Integer getItemsCount() {
		return itemMapper.getTotalItems(null);
	}

	@Override
	public List<Item> findItemByKeyword(Item item) {
		return itemMapper.findItemByKeyword(item);
	}

	@Override
	public Boolean enableItem(Integer[] itemNumbers) {
		return itemMapper.enableItem(itemNumbers) > 0;
	}

	@Override
	public String selectItems(Integer vmi) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("state", 1);
		
		map.put("vmi", vmi);//不需要VMIList
		List<Item> itemList = itemMapper.selectItems(map);
		return CommonUtil.convertListToJson(itemList);
	}

	@Override
	public List<Unit> findUnitList() {
		return unitMapper.selectUnits();
	}

	@Override
	public String getItemByVendId(String vendId) {
		List<Item> itemList = itemMapper.getItemByVendId(vendId);
		return CommonUtil.convertListToJson(itemList);
	}
	

}
