package com.silgan.inventory.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.silgan.inventory.dao.ItemMapper;
import com.silgan.inventory.dao.ItemVendorMapper;
import com.silgan.inventory.entity.Item;
import com.silgan.inventory.entity.ItemVendor;
import com.silgan.inventory.entity.LogItem;
import com.silgan.inventory.service.ItemVendorService;
import com.silgan.inventory.util.PageQueryUtil;
import com.silgan.inventory.util.PageResult;

@Service
public class ItemVendorServiceImpl implements ItemVendorService {
	
	@Autowired
    private ItemMapper itemMapper;
	

	@Autowired
    private ItemVendorMapper vendorMapper;

	@Override
	public List<ItemVendor> findVendorList() {
		return vendorMapper.selectAll();
	}

	@Override
	@Transactional
	public Boolean saveItem(Item item) {		
		return itemMapper.insert(item) >0 ;
	}

	@Override
	@Transactional
	public Boolean updateItem(Item item) {
		Item selectItem = itemMapper.selectByPrimaryKey(item.getItemNumber());
		item.setCreateUser(selectItem.getCreateUser());
		item.setCreateTime(selectItem.getCreateTime());
		LogItem logItem = new LogItem();
		BeanUtils.copyProperties(item, logItem);		
		Boolean result = itemMapper.updateByPrimaryKey(item) > 0;
		//更新item时记录log
		if(result) {
			itemMapper.insertLog(logItem);
			return result;
		}
		return false;
	}

	@Override
	public Item selectByItemNumber(Integer itemNumber) {
		return itemMapper.selectByPrimaryKey(itemNumber);
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
	

}
