package com.hpp.utils;

import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.druid.pool.DruidDataSource;

@SuppressWarnings("all")
public class ConfigEncrypt extends DruidDataSource{
	
	@Override
	public void setUsername(String username) {
		try {
			username = ConfigTools.decrypt(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
			super.setUsername(username);
	}
	
	@Override
	public void setPassword(String password) {
		try {
			password = ConfigTools.decrypt(password);
		} catch (Exception e) {
			e.printStackTrace();
		}
			super.setPassword(password);
	}

}
