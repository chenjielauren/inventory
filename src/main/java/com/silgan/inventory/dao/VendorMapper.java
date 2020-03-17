package com.silgan.inventory.dao;

import java.util.List;
import java.util.Map;

import com.silgan.inventory.entity.Vendor;

public interface VendorMapper {

	List<Vendor> selectVendors(Map<String, Object> params);

}
