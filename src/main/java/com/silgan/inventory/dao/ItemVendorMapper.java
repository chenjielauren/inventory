package com.silgan.inventory.dao;

import com.silgan.inventory.entity.ItemVendor;
import java.util.List;

public interface ItemVendorMapper {
    int deleteByPrimaryKey(Integer vendorId);

    int insert(ItemVendor record);

    ItemVendor selectByPrimaryKey(Integer vendorId);

    List<ItemVendor> selectAll();

    int updateByPrimaryKey(ItemVendor record);
}