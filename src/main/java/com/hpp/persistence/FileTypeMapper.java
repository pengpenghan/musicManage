package com.hpp.persistence;

import java.util.List;
import java.util.Map;

import com.hpp.model.FileType;

public interface FileTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FileType record);

    int insertSelective(FileType record);

    FileType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FileType record);

    int updateByPrimaryKey(FileType record);
    
    List<FileType> selectFileTypeList(Map<String, Object> map);
}