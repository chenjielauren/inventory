package com.silgan.inventory.dao;

import java.util.List;
import java.util.Map;

import com.silgan.inventory.entity.Item;
import com.silgan.inventory.entity.LogItem;
import com.silgan.inventory.util.PageQueryUtil;

public interface ItemMapper {
    int deleteByPrimaryKey(Integer itemNumber);

    int insert(Item record);

    Item selectByPrimaryKey(Integer itemNumber);

    int updateByPrimaryKey(Item record);

	List<Item> findItemList(PageQueryUtil pageUtil);

	int getTotalItems(PageQueryUtil pageUtil);

	int deleteBatch(Integer[] itemNumbers);
	
	int insertLog(LogItem record);

	List<Item> findItemByKeyword(Item item);

	int enableItem(Integer[] itemNumbers);
	
	List<Item> selectItems(Map<String, Object> map);

	List<Item> getItemByVendId(String vendId);
	
}