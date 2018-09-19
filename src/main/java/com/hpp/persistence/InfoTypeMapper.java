package com.hpp.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hpp.model.InfoType;

public interface InfoTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InfoType record);

    int insertSelective(InfoType record);

    InfoType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InfoType record);

    int updateByPrimaryKey(InfoType record);
    
    int deleteInfoTypeByFileId(Integer fileId);
    
    List<InfoType> getInfoTypeList(@Param(value="fileId")Integer fileId);
}