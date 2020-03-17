package com.silgan.inventory.dao;

import java.util.List;
import java.util.Map;

import com.silgan.inventory.entity.Inv;
import com.silgan.inventory.util.PageQueryUtil;
import com.silgan.inventory.vo.InvListVO;

public interface InvMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Inv record);

    Inv selectByPrimaryKey(Integer id);

    List<Inv> selectLocByMap(Map<String, Object> map);

    int updateByPrimaryKey(Inv record);

    List<InvListVO> findInvList(PageQueryUtil pageUtil);

	int getTotalInvs(PageQueryUtil pageUtil);

	int deleteBatch(Integer[] ids);

	List<InvListVO> findInvListByItemNumbers(Integer[] itemNumbers);

	int getTotalInvByItemNumbers(PageQueryUtil pageUtil);

	List<InvListVO> getCurrentQty(Map<String, String> map);

	List<InvListVO> findVmiInvList(PageQueryUtil pageUtil);

	int getTotalVmiInvs(PageQueryUtil pageUtil);
}