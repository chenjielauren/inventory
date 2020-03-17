package com.silgan.inventory.dao;

import java.util.List;

import com.silgan.inventory.entity.Item;
import com.silgan.inventory.entity.ItemVendor;

public interface ItemVendorMapper {
    int deleteByItemNumber(Integer itemNumber);

    int insert(Item item);

    ItemVendor selectByPrimaryKey(Integer vendId);

    List<ItemVendor> selectAll();

    int updateByPrimaryKey(ItemVendor record);
}