package com.silgan.inventory.dao;

import com.silgan.inventory.entity.Dc;
import java.util.List;

public interface DcMapper {
    int deleteByPrimaryKey(Integer dcSeq);

    int insert(Dc record);

    Dc selectByPrimaryKey(Integer dcSeq);

    List<Dc> selectAll();

    int updateByPrimaryKey(Dc record);
    
    Integer selectMaxDcSeq(String dcCode);
}