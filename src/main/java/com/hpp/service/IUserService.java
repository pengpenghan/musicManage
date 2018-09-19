package com.hpp.service;

import java.util.Map;

import com.hpp.model.SysUser;
import com.hpp.utils.DataTableModel;

public interface IUserService {

	/**
	 * 获取用户列表
	 * @Description 
	 * @author 韩鹏鹏
	 * @param search
	 * @return
	 */
	DataTableModel<SysUser> getSysUSerByCondition(Map<String, Object> search);
	
	SysUser selectUserByName(String name);
	
	int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);
}
