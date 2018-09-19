package com.hpp.persistence;

import java.util.List;
import java.util.Map;

import com.hpp.model.FileInfo;

public interface FileInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FileInfo record);

    int insertSelective(FileInfo record);

    FileInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FileInfo record);

    int updateByPrimaryKey(FileInfo record);
    
    //单个对象获取
    FileInfo selectFileInfo(Map<String, Object> map);
    //获取文件列表
    List<FileInfo> selectFileInfoList(Map<String, Object> map);
    //获取文件列表条目数
    int selectFileInfoListCount(Map<String, Object> map);
    //获取所属教材名列表
    List<String> getTextBookType(Map<String, Object> map);
    //获取出版社及版本
    List<String> getPressVersionType(Map<String, Object> map);
}