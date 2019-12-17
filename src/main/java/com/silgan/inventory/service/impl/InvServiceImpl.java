package com.silgan.inventory.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.silgan.inventory.dao.InvCodeMapper;
import com.silgan.inventory.dao.InvMapper;
import com.silgan.inventory.dao.LocationMapper;
import com.silgan.inventory.entity.Inv;
import com.silgan.inventory.entity.InvCode;
import com.silgan.inventory.entity.Location;
import com.silgan.inventory.service.InvService;
import com.silgan.inventory.util.PageQueryUtil;
import com.silgan.inventory.util.PageResult;

@Service
public class InvServiceImpl implements InvService{

	@Autowired
    private InvMapper invMapper;
	
	@Autowired
    private InvCodeMapper invCodeMapper;
	
	@Autowired
    private LocationMapper locationMapper;

	@Override
	public List<InvCode> findInvCodeList() {
		return invCodeMapper.selectInvCode();
	}
	
	@Override
	public List<Location> findLocationList() {
		return locationMapper.selectLocation();
	}

	@Override
	@Transactional
	public Boolean saveInv(Inv inv) {
		return invMapper.insert(inv) > 0;
	}

	@Override
	public PageResult getInvsPage(PageQueryUtil pageUtil) {
		List<Inv> itemList = invMapper.findInvList(pageUtil);
        int total = invMapper.getTotalInvs(pageUtil);
        PageResult pageResult = new PageResult(itemList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
	}

	@Override
	public Integer getInvCount() {
		return invMapper.getTotalInvs(null);
	}

}
