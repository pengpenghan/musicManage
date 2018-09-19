package com.hpp.persistence;

import java.util.List;
import java.util.Map;

import com.hpp.model.SysUser;

public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);
    
    SysUser selectUserByName(String name);
    
    List<SysUser> selectUserList(Map<String, Object> map);
    
    int selectUserCount(Map<String, Object> map);
}