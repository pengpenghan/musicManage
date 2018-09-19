package com.hpp.web;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hpp.model.SysUser;
import com.hpp.service.IUserService;
import com.hpp.utils.AESUtil;
import com.hpp.utils.Constant;
import com.hpp.utils.DataTableModel;
import com.hpp.utils.MyLogger;
import com.hpp.utils.RespJson;
import com.hpp.utils.ResultCode;

@Controller
@RequestMapping(value="/user")
public class UserController {

	private static final MyLogger LOGGER = new MyLogger(UserController.class);
	
	@Autowired
	IUserService userService;
	
	/**
	 * 获取上传文件列表
	 * 
	 * @Description
	 * @author 韩鹏鹏
	 * @param session
	 * @param model
	 * @param draw
	 * @param start
	 * @param length
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list")
	public DataTableModel<?> getFileInfoList(HttpSession session, Model model, int draw, int start, int length,
			HttpServletRequest request) {
		HashMap<String, Object> search = new HashMap<String, Object>();
		search.put("draw", draw);
		search.put("pageNo", start == 0 ? 0 : start / length + 1);
		search.put("pageSize", length);
		search.put("roleId", 1);
		search.put("status", 1);//正常
		DataTableModel<SysUser> result = userService.getSysUSerByCondition(search);
		result.setDraw(draw);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public RespJson addUser(String userName,String password) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userName", userName);
		SysUser user = userService.selectUserByName(userName);
		if (user != null) {
			return new RespJson(false, "用户名已经存在,请更换其它用户名！", ResultCode.ERROR,
					null);
		} else {
			try {
				user = new SysUser();
				user.setCtime(System.currentTimeMillis());
				user.setName(userName);
				user.setPassword(AESUtil.Encrypt(password));
				user.setRoleId(1);//普通员工
				user.setStatus((byte)Constant.Status.usernormal.getCode());
				userService.insertSelective(user);
				return new RespJson(true, "用户添加成功", ResultCode.SUCCESS, null);
			} catch (Exception ex) {
				ex.printStackTrace();
				return new RespJson(false, "添加异常！", ResultCode.ERROR, null);
			}
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/user/info")
	public Object userUpdatePage(
			@RequestParam(value = "userId", required = true) Integer userId) {
		SysUser user = userService.selectByPrimaryKey(userId);
		return user;
	}
	
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public RespJson updateUser(String userName,String password) {
		SysUser user = userService.selectUserByName(userName);
		if (user != null) {
			try {
				if ("".equals(user.getPassword())) {// 密码为空时不许改密码
					return new RespJson(true, "密码不能为空", ResultCode.ERROR, null);
				}
				user.setPassword(AESUtil.Encrypt(password));
				userService.updateByPrimaryKeySelective(user);
				return new RespJson(true, "用户信息更新成功", ResultCode.SUCCESS, null);
			} catch (Exception ex) {
				ex.printStackTrace();
				return new RespJson(false, "修改异常！", ResultCode.ERROR, null);
			}
		} else {
			return new RespJson(false, "该用户不存在！", ResultCode.ERROR,
					null);
		}

	}
}
