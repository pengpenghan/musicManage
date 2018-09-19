package com.hpp.utils;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * JSON模型
 * 
 * 用户后台向前台返回的JSON对象
 * 
 * 
 */
public class RespJson implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 日志记录器
	 */
	private static Logger Logger = LoggerFactory.getLogger(RespJson.class);

	/**
	 * 成功或者失败
	 */
	private boolean success = false;

	/**
	 * 结果消息
	 */
	private String msg = "";

	/**
	 * 响应对象
	 */
	private Object data = null;

	public RespJson() {

	}

	public RespJson(Boolean success, String msg, int code, Object obj) {
		this.success = success;
		this.msg = msg;
		this.code = code;
		this.data = obj;
	}

	/**
	 * 将传递过来的base64参数转jsonObject
	 * 
	 * @param req
	 * @return
	 */
	public static RespJson convertJson(HttpServletRequest req) {
		RespJson json = new RespJson();
		try {
			String sign = req.getParameter("sign");
			if (StringUtils.isEmpty(sign)) {
				json.setMsg("解析请求参数异常，请求参数为空");
				json.setSuccess(false);
				json.setCode(ResultCode.ERROR);
				Logger.error("解析请求参数异常，请求参数为空");
				return json;
			}
			JSONObject o = JSONObject.parseObject(sign);
			json.setData(o);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg("解析请求参数异常..." + e.getMessage());
			json.setSuccess(false);
			json.setCode(ResultCode.ERROR);
			Logger.error("解析请求参数异常", e);
		}
		return json;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}

	private Integer code;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RespJson [success=" + success + ", msg=" + msg + ", data=" + data + ", code=" + code + "]";
	}
}
