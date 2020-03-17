package com.silgan.inventory.dao;

import java.util.List;
import java.util.Map;

import com.silgan.inventory.entity.MaterialPlan;
import com.silgan.inventory.util.PageQueryUtil;
import com.silgan.inventory.vo.MpListVO;

public interface MaterialPlanMapper {
    int deleteByMp(String mpNumber);

    int insert(MaterialPlan record);

    MaterialPlan selectByPrimaryKey(Integer id);

    List<MpListVO> selectByMap(Map<String, Object> map);

    int updateByPrimaryKey(MaterialPlan record);

    List<MaterialPlan> selectExistQty(Map<String, Object> map);

//	List<MaterialPlan> selectMpList(Map<String, String> map);

	List<MpListVO> findMpList(PageQueryUtil pageUtil);
	
	int getTotalMps(PageQueryUtil pageUtil);

	int checkDone(Map<String, String> map);
	
	void lockTable();
	
	void unlockTable();

	int cancelMp(String mpNumber);

	
}