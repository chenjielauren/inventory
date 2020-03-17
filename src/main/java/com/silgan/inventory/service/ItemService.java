package com.silgan.inventory.service;

import java.util.List;

import com.silgan.inventory.entity.Item;
import com.silgan.inventory.entity.Unit;
import com.silgan.inventory.entity.Vendor;
import com.silgan.inventory.util.PageQueryUtil;
import com.silgan.inventory.util.PageResult;

public interface ItemService {

	Boolean saveItem(Item item);

	Boolean updateItem(Item item);

	Item selectByItemNumber(Integer itemNumber);

	PageResult getItemsPage(PageQueryUtil pageUtil);

	Boolean deleteBatch(Integer[] itemNumbers);
	
	Integer getItemsCount();

	List<Item> findItemByKeyword(Item item);

	Boolean enableItem(Integer[] itemNumbers);

	String selectItems();
	
	List<Unit> findUnitList();
	
	List<Vendor> findVendorList(String userName);
	
	String getItemByVendId(String vendId);
}
