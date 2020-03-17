package com.silgan.inventory.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.silgan.inventory.dao.DcMapper;
import com.silgan.inventory.dao.InvCodeMapper;
import com.silgan.inventory.dao.InvMapper;
import com.silgan.inventory.dao.ItemMapper;
import com.silgan.inventory.dao.LocationMapper;
import com.silgan.inventory.dao.MaterialPlanMapper;
import com.silgan.inventory.dao.TransCodeMapper;
import com.silgan.inventory.dao.VendorMapper;
import com.silgan.inventory.entity.Dc;
import com.silgan.inventory.entity.Inv;
import com.silgan.inventory.entity.InvCode;
import com.silgan.inventory.entity.Item;
import com.silgan.inventory.entity.Location;
import com.silgan.inventory.entity.MaterialPlan;
import com.silgan.inventory.entity.UserRole;
import com.silgan.inventory.service.InvService;
import com.silgan.inventory.util.CommonUtil;
import com.silgan.inventory.util.Constant;
import com.silgan.inventory.util.PageQueryUtil;
import com.silgan.inventory.util.PageResult;
import com.silgan.inventory.vo.InvListVO;
import com.silgan.inventory.vo.MpListVO;
import com.silgan.inventory.vo.TransCodeListVO;

@Service
public class InvServiceImpl implements InvService{

	@Autowired
    private InvMapper invMapper;
	
	@Autowired
    private InvCodeMapper invCodeMapper;
	
	@Autowired
    private LocationMapper locationMapper;

	@Autowired
    private ItemMapper itemMapper;
	
	@Autowired
    private TransCodeMapper transCodeMapper;
	
	@Autowired
    private DcMapper dcMapper;
	
	@Autowired
    private VendorMapper vendorMapper;
	
	@Autowired
    private MaterialPlanMapper mpMapper;
	
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
	public Boolean saveInv(String jsonStr,String username) {
		if(null!=jsonStr) {
			try {
				ObjectMapper mapper = new ObjectMapper();
	    		List<Inv> invs = mapper.readValue(jsonStr, new TypeReference<List<Inv>>(){});
			    if(null!=invs && invs.size()>0) {
			    	Dc dc = new Dc();
	    	    	dc.setDcCode(Constant.DC_INV);
	    	    	Integer maxDcSeq = dcMapper.selectMaxDcSeq(dc.getDcCode());
	    	    	if(null != maxDcSeq) {
	    	    		dc.setDcSeq(maxDcSeq + 1);
	    	    	}else {
	    	    		dc.setDcSeq(1);
	    	    	}
	    	    	dcMapper.insert(dc);
	    	    	Date createDate = new Date();
		    		for(Inv inv:invs) {
		    			inv.setCreateUser(username);
		    	    	inv.setCreateTime(createDate);		    	    	
		    	    	inv.setIcNumber(CommonUtil.generateSerialNum(dc.getDcCode(), dc.getDcSeq()));
		    	    	//预留price和amount
//		    	    	inv.setPrice(new BigDecimal(""));
//		    	    	inv.setAmount(new BigDecimal(""));
		    	    	invMapper.insert(inv);  	    	
		    		}
		    		return true;	
			    }
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public PageResult getInvsPage(PageQueryUtil pageUtil) {		
		List<InvListVO> voList = invMapper.findInvList(pageUtil);
		if(!CollectionUtils.isEmpty(voList)) {
			Map<String,Item> itemMap = this.getItemMap();
			for(InvListVO vo:voList) {
				Item item = itemMap.get(vo.getItemNumber());				
				vo.setItemDesc(item.getItemDesc());
				vo.setItemBrand(item.getItemBrand());
				vo.setItemModel(item.getItemModel());
				vo.setVmi(item.getVmi());
				vo.setSafeVmi(item.getSafeVmi());
				vo.setExpireDate(item.getExpireDate());
				vo.setItemUnit(item.getItemUnit());
			}
		}
        int total = invMapper.getTotalInvs(pageUtil);
        PageResult pageResult = new PageResult(voList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
	}

	@Override
	public Integer getInvCount() {
		return invMapper.getTotalInvs(null);
	}

	@Override
	public String selectInvs() {
		List<InvCode> invCodes = invCodeMapper.selectInvCode();		
		return CommonUtil.convertListToJson(invCodes);
	}

	@Override
	public String selectLocs() {
		List<Location> locCodes = locationMapper.selectLocation();
		return CommonUtil.convertListToJson(locCodes);
	}
	
	@Override
	public Map<String,Item> getItemMap() {
		//获取逻辑关系
		List<Item> itemList = itemMapper.selectItems(null);
		Map<String,Item> itemMap = new HashMap<String,Item>();
		if(!CollectionUtils.isEmpty(itemList)) {
			for(Item item:itemList) {
				itemMap.put(String.valueOf(item.getItemNumber()), item);
			}
		}
		return itemMap;
	}
	
	@Override
	public Map<String,String> getTransCodeMap() {
		List<TransCodeListVO> transList = transCodeMapper.selectTransCodeKey();
		Map<String,String> transCodeMap = new HashMap<String,String>();
		if (!CollectionUtils.isEmpty(transList)) {
			transCodeMap = transList.stream().collect(Collectors.toMap(TransCodeListVO::getTransKey, TransCodeListVO::getOperator, (key1, key2) -> key2));
        }
		return transCodeMap;
	}
	
	/**
	 * 根据传入的list计算当前库存数
	 * @param list
	 * @param transCodeMap
	 * @return
	 */
	public Integer calSumQty(List<InvListVO> list,Map<String,String> transCodeMap) {
		Integer sumQty = 0;
		if(!CollectionUtils.isEmpty(list)) {			
			for(int i=0;i<list.size();i++) {
				InvListVO vo = list.get(i);
				if(vo.getTransKey()!=null) {
					//根据tansCode的operator计算qty
					String operator = transCodeMap.get(vo.getTransKey());
					Integer qty = vo.getQty();
					if((null!=operator && operator.equals("+")) || operator == null) {
						sumQty+=qty;
					}
					if(null!=operator && operator.equals("-")) {
						sumQty-=qty;
					}								
				}
			}				
		}	
		return sumQty;
	}

	@Override
	public List<InvListVO> getInvsByItemNumbers(Integer[] itemNumbers) {		
		List<InvListVO> invListVOS = invMapper.findInvListByItemNumbers(itemNumbers);
//		if(!CollectionUtils.isEmpty(invListVOS)) {
//			//获取逻辑关系
//			Map<String,String> transCodeMap = this.getTransCodeMap();
//			Map<String,Item> itemMap = this.getItemMap();
//			//将list转换为Map
//			Map<String,List<InvListVO>> invMap = new HashMap<String,List<InvListVO>>();
//			for(int i=0;i<invListVOS.size();i++) {
//				InvListVO inv = invListVOS.get(i);
//				Item item = itemMap.get(inv.getItemNumber());
//				String key = item.getItemNumber()+","+item.getItemDesc();
//				if(invMap.get(key) == null) {
//					List<InvListVO> list = new ArrayList<InvListVO>();
//					list.add(inv);
//					invMap.put(key,list);
//				}else {
//					List<InvListVO> list = invMap.get(key);
//					list.add(inv);
//				}			
//			}
//			//遍历Map
//			for (Map.Entry<String, List<InvListVO>> entry : invMap.entrySet()) {
//				InvListVO retVO = new InvListVO();
//				String key = entry.getKey();				
//				retVO.setItemNumber(key.split(",")[0]);
//				retVO.setItemDesc(key.split(",")[1]);
//				List<InvListVO> list = entry.getValue();
//				Integer sumQty = this.calSumQty(list, transCodeMap);
//				retVO.setQty(sumQty);					
//				retVO.setState(sumQty == 0?false:true);//可禁用		
//				retList.add(retVO);				
//			}				
//		}
		List<InvListVO> retList = this.convertShowInvListVO(invListVOS);
		//排序
		Collections.sort(retList, new Comparator<InvListVO>() {
			@Override
			public int compare(final InvListVO record1, final InvListVO record2) {
				int c;
			    c = record2.getState().compareTo(record1.getState());
			    if (c == 0)
			       c = record1.getQty().compareTo(record2.getQty());
			    if (c == 0)
			       c = Integer.valueOf(record1.getItemNumber()).compareTo(Integer.valueOf(record2.getItemNumber()));
			    return c;
			}
		});
		
	    return retList;
	}
	
	/**
	 * 转换显示的InvList
	 * @param invList
	 * @return
	 */
	private List<InvListVO> convertShowInvListVO(List<InvListVO> invListVOS) {
		List<InvListVO> retList = new ArrayList<InvListVO>();
		if(!CollectionUtils.isEmpty(invListVOS)) {
			//获取逻辑关系
			Map<String,String> transCodeMap = this.getTransCodeMap();			
			//将list转换为Map
			Map<String,List<InvListVO>> invMap = new HashMap<String,List<InvListVO>>();
			for(int i=0;i<invListVOS.size();i++) {
				InvListVO inv = invListVOS.get(i);
				String key = inv.getItemNumber();
				if(invMap.get(key) == null) {
					List<InvListVO> list = new ArrayList<InvListVO>();
					list.add(inv);
					invMap.put(key,list);
				}else {
					List<InvListVO> list = invMap.get(key);
					list.add(inv);
				}			
			}
			//遍历Map
			Map<String,Item> itemMap = this.getItemMap();
			for (Map.Entry<String, List<InvListVO>> entry : invMap.entrySet()) {
				InvListVO retVO = new InvListVO();
				String key = entry.getKey();	
				Item item = itemMap.get(key);
				retVO.setItemNumber(String.valueOf(item.getItemNumber()));
				retVO.setItemDesc(item.getItemDesc());
				retVO.setItemModel(item.getItemModel());
				retVO.setItemBrand(item.getItemBrand());
				List<InvListVO> list = entry.getValue();
				Integer sumQty = this.calSumQty(list, transCodeMap);
				retVO.setQty(sumQty);					
				retVO.setState(sumQty == 0?false:true);//可禁用		
				retList.add(retVO);				
			}				
		}
		return retList;
	}

	@Override
	public List<InvCode> selectTranscodeByParam(Map<String, Object> params) {
		if(String.valueOf(params.get("fromInv")).equals("VG") || String.valueOf(params.get("fromInv")).equals("VM")) {
			params.put("vmi", 1);
			return invCodeMapper.selectInvCodeByVmi(params);
		}else {
			return invCodeMapper.selectTransCode(params);
		}
		
	}

	@Override
	public Integer getVmiCurrentQty(String jsonStr) {
		Integer sumQty = 0;
		if(null!=jsonStr) {			
			Map map = CommonUtil.convertJsonMap(jsonStr);
		    List<InvListVO> list = invMapper.getCurrentQty(map);
		    if(!CollectionUtils.isEmpty(list)) {
		    	//获取逻辑关系
		    	Map<String,String> transCodeMap = this.getTransCodeMap();
		    	sumQty= this.calSumQty(list, transCodeMap);										
		    }			
		}
		return sumQty;
	}
	
	/**
	 *根据多个ItemNumber获取库存数
	 */
	@Override
	public Map<String,Integer> getCurrentQtyByItemNumbers(Map map) {
//		Map<String,Integer> qtyMap = new HashMap<String,Integer>();
//		List<InvListVO> list = invMapper.getCurrentQty(map);
//		if(!CollectionUtils.isEmpty(list)) {
//			Map<String,List<InvListVO>> retMap = new HashMap<String,List<InvListVO>>();
//			for(InvListVO vo:list) {
//				if(retMap.get(vo.getItemNumber()) == null) {
//					List<InvListVO> volist = new ArrayList<InvListVO>();
//					volist.add(vo);
//					retMap.put(vo.getItemNumber(), volist);
//				}else {
//					List<InvListVO> volist = retMap.get(vo.getItemNumber());
//					volist.add(vo);
//				}
//			}
//			Map<String,String> transCodeMap = this.getTransCodeMap();
//			for (Map.Entry<String, List<InvListVO>> entry : retMap.entrySet()) {
//				List<InvListVO> invlist= entry.getValue();				
//		    	Integer sumQty= this.calSumQty(invlist, transCodeMap);	
//		    	qtyMap.put(entry.getKey(), sumQty);
//			}
//		}
//		return qtyMap;
		Map<String,Integer> qtyMap = new HashMap<String,Integer>();
		Map<String,List<InvListVO>> retMap = this.selectLocByMap(map);		
		for (Map.Entry<String, List<InvListVO>> entry : retMap.entrySet()) {
			List<InvListVO> invlist= entry.getValue();				
	    	Integer sumQty= 0;
	    	for(InvListVO vo:invlist) {
	    		sumQty+=vo.getQty();
	    	}
	    	qtyMap.put(entry.getKey(), sumQty);
		}
		
		return qtyMap;
	}

	@Override
	public PageResult getVmiInvsPage(PageQueryUtil pageUtil) {
		List<InvListVO> invList = invMapper.findVmiInvList(pageUtil);
        int total = invMapper.getTotalVmiInvs(pageUtil);
        PageResult pageResult = new PageResult(invList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
	}
	
//	/**
//	 * 转换显示的VMIList
//	 * @param invList
//	 * @return
//	 */
//	private List<InvListVO> convertVmiToInvListVO(List<InvListVO> invList) {
//		if(!CollectionUtils.isEmpty(invList)) {
//			//获取逻辑关系
//			List<Vendor> vendorList = vendorMapper.selectVendors(null);
//			Map<String,Vendor> vendorMap = new HashMap<String,Vendor>();
//			if(!CollectionUtils.isEmpty(vendorList)) {
//				for(Vendor v:vendorList) {
//					vendorMap.put(v.getVmiInv(), v);
//				}
//			}			
//			for(InvListVO vo:invList) {
//				Vendor v = vendorMap.get(vo.getToInv());				
//				vo.setVendorName(v.getVendName());
//				vo.setVmiInv(v.getVmiInv());
//				retList.add(vo);
//			}
//		}
//	}

	@Override
	public MpListVO getMpQty(String itemNumber) {
		MpListVO vo = new MpListVO();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("itemNumber", itemNumber);
		Map<String,Integer> invQtyMap = this.getCurrentQtyByItemNumbers(params);
		vo.setCurrentQty(invQtyMap.get(itemNumber));
		Map<String,Integer> mpQtyMap =this.getMpQtyByItemNumbers(params);
		vo.setExistQty(mpQtyMap.get(itemNumber));
		//show qty list
		params.put("states", new Integer[]{0,1});
		vo.setMpVoList(mpMapper.selectByMap(params));
		return vo;
	}
	
	
	@Override
	public Map<String,Integer> getMpQtyByItemNumbers(Map map) {
		Map<String,Integer> qtyMap = new HashMap<String,Integer>();
		List<MaterialPlan> list = mpMapper.selectExistQty(map);
		if(!CollectionUtils.isEmpty(list)) {
			Map<String,List<MaterialPlan>> retMap = new HashMap<String,List<MaterialPlan>>();
			Integer qty = 0;
			for(MaterialPlan mp:list) {
				qty = mp.getQty()!=null?mp.getQty():0;
				qtyMap.put(mp.getItemNumber(), qty);
			}
		}
		return qtyMap;
	}

	@Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
	public Map<String,Object> saveMp(String jsonStr, String username) {
		Map<String,Object> retMap = new HashMap<String,Object>();
		// list = new ArrayList<MaterialPlan>();
		if(null!=jsonStr) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				MpListVO vo = mapper.readValue(jsonStr, MpListVO.class);
				String mpNumber = vo.getMpNumber();
				Date createDate = new Date();
				if(StringUtils.isEmpty(mpNumber)) {
			    	Dc dc = new Dc();
	    	    	dc.setDcCode(Constant.DC_MP);
	    	    	Integer maxDcSeq = dcMapper.selectMaxDcSeq(dc.getDcCode());
	    	    	if(null != maxDcSeq) {
	    	    		dc.setDcSeq(maxDcSeq + 1);
	    	    	}else {
	    	    		dc.setDcSeq(1);
	    	    	}
	    	    	dcMapper.insert(dc);
	    	    	mpNumber = CommonUtil.generateSerialNum(dc.getDcCode(), dc.getDcSeq());
				}else {
					//先删除再新增预约
					mpMapper.deleteByMp(mpNumber);
				}
	    		List<MaterialPlan> mps = vo.getMpList();
	    		if(!CollectionUtils.isEmpty(mps)) {
	    			mpMapper.lockTable();
	    			Map<String,Object> params = new HashMap<String,Object>();
	    			List<String> itemNumbers = mps.stream().map(mpVo->mpVo.getItemNumber()).distinct().collect(Collectors.toList());
	    			params.put("itemNumbers", itemNumbers);
	    			Map<String,Integer> invQtyMap = this.getCurrentQtyByItemNumbers(params);
	    			Map<String,Integer> mpQtyMap = this.getMpQtyByItemNumbers(params);
	    			boolean flag = true;
	    			for(MaterialPlan mp : mps) {
	    				String itemNumber = mp.getItemNumber();
	    				Integer currentQty = (invQtyMap!=null && !invQtyMap.isEmpty())?invQtyMap.get(itemNumber):0;
	    				Integer mpQty = (mpQtyMap!=null && !mpQtyMap.isEmpty())?mpQtyMap.get(itemNumber):0;
	    				Integer minusQty = (currentQty - mpQty);
	    				//保存时验证预约值是否大于可预约数
	    				if(mp.getQty() > minusQty) {
	    					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	    					flag = false;
	    					retMap.put("message", itemNumber+"预约值太大,最大值为"+minusQty);
	    					break;
	    				}
	    				if(mp.getId() == null) {
	    					mp.setCreateUser(username);
			    			mp.setCreateTime(createDate);		    	    	
			    			mp.setMpNumber(mpNumber);
			    			mp.setState(Constant.MP_STATE_UNFINISHED);
	    				}
	    				mpMapper.insert(mp);
	    				invQtyMap.put(itemNumber, currentQty-mp.getQty());
	    			}
	    			if(flag) {
	    				params.put("userName", username);
//	    				List<MaterialPlan> list =this.selectMpList(params);
//			    		retMap.put("list", list);
			    		
	    			}
	    			mpMapper.unlockTable();
		    		
			    }
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		return retMap;
	}

	@Override
	public List<MpListVO> selectMpList(UserRole u) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("state", Constant.MP_STATE_UNFINISHED);
		params.put("userName", u.getUserName());
		return  mpMapper.selectByMap(params);
	}

	@Override
	public PageResult getMpInvsPage(PageQueryUtil pageUtil) {
//		List<MaterialPlan> mpList = mpMapper.findMpList(pageUtil);
//		List<MpListVO> volist = new ArrayList<MpListVO>();
//		if(!CollectionUtils.isEmpty(mpList)) {
//			for(MaterialPlan mp :mpList) {
//				MpListVO vo = new MpListVO();
//				BeanUtils.copyProperties(mp, vo);
//				volist.add(vo);
//			}
//		}
//        int total = volist.size();
//        PageResult pageResult = new PageResult(volist, total, pageUtil.getLimit(), pageUtil.getPage());
//        return pageResult;
		List<MpListVO> mpList = mpMapper.findMpList(pageUtil);	
		int total = mpMapper.getTotalMps(pageUtil);
        PageResult pageResult = new PageResult(mpList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
	}

	@Override
	public Boolean checkDone(String mpNumber) {
		return mpMapper.checkDone(CommonUtil.convertJsonMap(mpNumber)) > 0;
	}

	@Override
	public List<MpListVO> findItemByMp(String mpNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mpNumber", mpNumber);
		List<MpListVO> mpList = mpMapper.selectByMap(map);
		List<String> itemNumbers = mpList.stream().map(mpVo->mpVo.getItemNumber()).distinct().collect(Collectors.toList());
		map.put("itemNumbers", itemNumbers);
		Map<String,List<InvListVO>> invMap = this.selectLocByMap(map);
		Map<String,Item> itemMap = this.getItemMap();
		for(MpListVO vo:mpList) {
			vo.setItem(itemMap.get(vo.getItemNumber()));
			vo.setInvList(invMap.get(vo.getItemNumber()));
		}
		return mpList;
	}

	/**
	 *根据ItemNumber查询库位信息
	 */
	@Override
	public Map<String,List<InvListVO>> selectLocByMap(Map<String, Object> map) {		
		Map<String,List<Inv>> invMap = new HashMap<String,List<Inv>>();
		List<Inv> list = invMapper.selectLocByMap(map);
		//Key:itemNumber value:交易记录
		for(Inv inv:list) {
			if(invMap.get(inv.getItemNumber()) == null) {
				List<Inv> invList = new ArrayList<Inv>();
				invList.add(inv);
				invMap.put(inv.getItemNumber(), invList);
			}else {
				List<Inv> invList = invMap.get(inv.getItemNumber());
				invList.add(inv);
			}
		}
		//返回值Map
		Map<String,List<InvListVO>> retMap = new HashMap<String,List<InvListVO>>();
		//分别获取fromLoc的qty总和与toLoc的qty总和,两者相减，获取数量不为0的库位
		for (Map.Entry<String, List<Inv>> invEntry : invMap.entrySet()) {
			List<InvListVO> retList = new ArrayList<InvListVO>();
			//key:fromLoc value:qty累加
			Map<String,Integer> fromMap = new HashMap<String,Integer>();
			//key:toLoc value:qty累加
			Map<String,Integer> toMap = new HashMap<String,Integer>();
			//key:invTime value:invTime
			Map<String,Date> invTimeMap = new HashMap<String,Date>();
			//transCode 决定数量正负 交易代码
			Map<String,String> transCodeMap = this.getTransCodeMap();
			for(Inv inv :invEntry.getValue()) {
				String operator = transCodeMap.get(inv.getFromInv()+inv.getToInv());
				if(fromMap.get(inv.getFromLoc()) == null) {
					fromMap.put(inv.getFromLoc(), (null!=operator && operator.equals("-")) ? 0-inv.getQty() : inv.getQty());//qty的相反数
				}else {
					Integer qty = fromMap.get(inv.getFromLoc());
					qty += inv.getQty();
					fromMap.put(inv.getFromLoc(), qty);
				}
				if(toMap.get(inv.getToLoc()) == null) {
					toMap.put(inv.getToLoc(), (null!=operator && operator.equals("-")) ? 0-inv.getQty() : inv.getQty());
					invTimeMap.put(inv.getToLoc(), inv.getInvTime());
				}else {
					Integer qty = toMap.get(inv.getToLoc());
					qty += inv.getQty();
					toMap.put(inv.getToLoc(), qty);
				}
			}
			//遍历Map，将toMap的qty总和减去fromMap的qty总和
			for (Map.Entry<String, Integer> entry : toMap.entrySet()) {
				String key = entry.getKey();				
				Integer minusQty = (toMap.get(key)!=null?toMap.get(key):0)-(fromMap.get(key)!=null?fromMap.get(key):0);
				if(minusQty !=0) {
					InvListVO retVO = new InvListVO();
					retVO.setLocName(key);
					retVO.setQty(minusQty);
					retVO.setInvTime(invTimeMap.get(key));
					retList.add(retVO);	
				}			
			}
			retMap.put(invEntry.getKey(), retList);
		}
		return retMap;
	}

	@Override
	public List<MpListVO> selectByMp(String mpNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mpNumber", mpNumber);
		List<MpListVO> mpList =  mpMapper.selectByMap(map);
		List<String> itemNumbers = mpList.stream().map(mpVo->mpVo.getItemNumber()).distinct().collect(Collectors.toList());
		map.put("itemNumbers", itemNumbers);
		if(!CollectionUtils.isEmpty(mpList)) {
			Map<String,Integer> invQtyMap = this.getCurrentQtyByItemNumbers(map);
			Map<String,Integer> mpQtyMap = this.getMpQtyByItemNumbers(map);
			for(MpListVO vo:mpList) {
				Integer currentQty = invQtyMap.get(vo.getItemNumber());
				Integer mpQty = mpQtyMap.get(vo.getItemNumber());
				vo.setMinusQty(currentQty - mpQty);
			}
		}
		return mpList;
	}
	
	@Override
	public Boolean cancelMp(String mpNumber) {
		return mpMapper.cancelMp(mpNumber) > 0;
	}



}
