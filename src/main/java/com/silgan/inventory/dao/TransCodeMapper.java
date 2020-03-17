package com.silgan.inventory.dao;

import java.util.List;

import com.silgan.inventory.vo.TransCodeListVO;

public interface TransCodeMapper {
	
	List<TransCodeListVO> selectTransCodeKey();

}
