package com.silgan.inventory.dao;

import java.util.List;

import com.silgan.inventory.entity.Inv;
import com.silgan.inventory.entity.InvCode;
import com.silgan.inventory.util.PageQueryUtil;

public interface InvMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Inv record);

    Inv selectByPrimaryKey(Integer id);

    List<Inv> selectAll();

    int updateByPrimaryKey(Inv record);

    List<Inv> findInvList(PageQueryUtil pageUtil);

	int getTotalInvs(PageQueryUtil pageUtil);

	int deleteBatch(Integer[] ids);
}