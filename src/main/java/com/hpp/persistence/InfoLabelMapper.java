package com.hpp.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hpp.model.InfoLabel;

public interface InfoLabelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InfoLabel record);

    int insertSelective(InfoLabel record);

    InfoLabel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InfoLabel record);

    int updateByPrimaryKey(InfoLabel record);
    
    int deleteInfoLabelByFileId(Integer fileId);
    
    List<InfoLabel> getInfoLabelList(@Param(value="fileId")Integer fileId);
}