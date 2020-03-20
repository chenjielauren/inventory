package com.silgan.inventory.service;

import java.util.List;
import java.util.Map;

import com.silgan.inventory.entity.InvCode;
import com.silgan.inventory.entity.Item;
import com.silgan.inventory.entity.Location;
import com.silgan.inventory.entity.MaterialPlan;
import com.silgan.inventory.entity.UserRole;
import com.silgan.inventory.util.PageQueryUtil;
import com.silgan.inventory.util.PageResult;
import com.silgan.inventory.vo.InvListVO;
import com.silgan.inventory.vo.MpListVO;

public interface InvService {

	List<InvCode> findInvCodeList();
	
	List<Location> findLocationList();

	Boolean saveInv(String jsonStr,String username);

	PageResult getInvsPage(PageQueryUtil pageUtil);

	Integer getInvCount();
	
	String selectInvs();

	String selectLocs();
	
	Map<String,String> getTransCodeMap();
	
	Map<String,Item> getItemMap();
	
	List<InvListVO> getInvsByItemNumbers(Integer[] itemNumbers);

	List<InvCode> selectTranscodeByParam(Map<String, Object> params);

	Integer getVmiCurrentQty(String jsonStr);
	
	Map<String,Integer> getCurrentQtyByItemNumbers(Map<String, Object> map);

	PageResult getVmiInvsPage(PageQueryUtil pageUtil);

	MpListVO getMpQty(String itemNumber);
	
	Map<String,Integer> getMpQtyByItemNumbers(Map<String, Object> map);

	Map<String,Object> saveMp(String jsonStr, String username);

	List<MpListVO> selectMpList(UserRole u);

	PageResult getMpInvsPage(PageQueryUtil pageUtil);

	Boolean checkDone(String mpNumber);

	List<MpListVO> findItemByMp(String mpNumber);
	
	Map<String,List<InvListVO>> selectLocByMap(Map<String, Object> map);

	List<MpListVO> selectByMp(String mpNumber);

	Boolean cancelMp(String mpNumber);

	PageResult getInvQtyPage(PageQueryUtil pageUtil);
	
}
