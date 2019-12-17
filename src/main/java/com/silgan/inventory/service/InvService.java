package com.silgan.inventory.service;

import java.util.List;

import com.silgan.inventory.entity.Inv;
import com.silgan.inventory.entity.InvCode;
import com.silgan.inventory.entity.Location;
import com.silgan.inventory.util.PageQueryUtil;
import com.silgan.inventory.util.PageResult;

public interface InvService {

	List<InvCode> findInvCodeList();
	
	List<Location> findLocationList();

	Boolean saveInv(Inv inv);

	PageResult getInvsPage(PageQueryUtil pageUtil);

	Integer getInvCount();
}
