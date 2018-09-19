package com.hpp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hpp.model.FileInfo;
import com.hpp.model.SysUser;
import com.hpp.persistence.FileInfoMapper;
import com.hpp.persistence.SysUserMapper;
import com.hpp.service.IUserService;
import com.hpp.utils.AESUtil;
import com.hpp.utils.DataTableModel;

@Service
public class UserServiceImpl implements IUserService{
	
	@Autowired
	SysUserMapper sysUserMapper;
	
	@Autowired
	FileInfoMapper fileInfoMapper;

	@Override
	public DataTableModel<SysUser> getSysUSerByCondition(Map<String, Object> search) {
		List<SysUser> list = sysUserMapper.selectUserList(search);
		List<SysUser> users = new ArrayList<SysUser>();
		for(SysUser user : list){
			try {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userId", user.getId());
				user.setPassword(AESUtil.Decrypt(user.getPassword()));
				users.add(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		int count = sysUserMapper.selectUserCount(search);
		return new DataTableModel<>(users, count, count);
	}

	@Override
	public SysUser selectUserByName(String name) {
		return sysUserMapper.selectUserByName(name);
	}

	@Override
	public int insertSelective(SysUser record) {
		return sysUserMapper.insertSelective(record);
	}

	@Override
	public SysUser selectByPrimaryKey(Integer id) {
		return sysUserMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(SysUser record) {
		return sysUserMapper.updateByPrimaryKeySelective(record);
	}

}
