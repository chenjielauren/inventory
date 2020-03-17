package com.silgan.inventory.dao;

import java.util.List;
import java.util.Map;

import com.silgan.inventory.entity.InvCode;

public interface InvCodeMapper {

	List<InvCode> selectInvCode();
	
	List<InvCode> selectInvCodeByVmi(Map<String, Object> params);
	
	List<InvCode> selectTransCode(Map<String, Object> params);
}
