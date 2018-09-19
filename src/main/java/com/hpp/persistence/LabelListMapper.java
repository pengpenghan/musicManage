package com.hpp.persistence;

import java.util.List;
import java.util.Map;

import com.hpp.model.LabelList;

public interface LabelListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LabelList record);

    int insertSelective(LabelList record);

    LabelList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LabelList record);

    int updateByPrimaryKey(LabelList record);
    
    List<LabelList> selectLabelList(Map<String, Object> map);
}