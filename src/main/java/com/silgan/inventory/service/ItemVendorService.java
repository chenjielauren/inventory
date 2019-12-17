package com.silgan.inventory.service;

import java.util.List;

import com.silgan.inventory.entity.Item;
import com.silgan.inventory.entity.ItemVendor;
import com.silgan.inventory.util.PageQueryUtil;
import com.silgan.inventory.util.PageResult;

public interface ItemVendorService {

	List<ItemVendor> findVendorList();

	Boolean saveItem(Item item);

	Boolean updateItem(Item item);

	Item selectByItemNumber(Integer itemNumber);

	PageResult getItemsPage(PageQueryUtil pageUtil);

	Boolean deleteBatch(Integer[] itemNumbers);
	
	Integer getItemsCount();
}
