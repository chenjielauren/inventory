package com.silgan.inventory.dao;

import java.util.List;

import com.silgan.inventory.entity.Item;
import com.silgan.inventory.entity.LogItem;
import com.silgan.inventory.util.PageQueryUtil;

public interface ItemMapper {
    int deleteByPrimaryKey(Integer itemNumber);

    int insert(Item record);

    Item selectByPrimaryKey(Integer itemNumber);

    List<Item> selectAll();

    int updateByPrimaryKey(Item record);

	List<Item> findItemList(PageQueryUtil pageUtil);

	int getTotalItems(PageQueryUtil pageUtil);

	int deleteBatch(Integer[] itemNumbers);
	
	int insertLog(LogItem record);
}