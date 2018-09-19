package com.hpp.utils;

public class ResultCode {

	public static String USER_LOGIN_INFO = "USER_LOGIN_INFO";
 
	public static String ADMIN_LOGIN_INFO = "user";

	
	public static String ACCESS_TOKEN = "access-token";
	
	
/*	1000	ERROR	错误
	1001	USER_NOT_EXIST	用户不存在
	1002	USER_NAME_EXIST	登录用户名已经被使用
	1003	USER_PWD_ERROR	密码错误！
	1004	USER_ALREADY_DEL	用户已被删除！
	1005	CHECK_NO_RECORD	查询无此记录
	1006	TOKEN_NOT_EXIST	用户TOKEN不存在
	1007	TOKEN_ALREADY_EXPIRE	
	1008    NICK_NAME_EXIST 用户昵称已经被使用
	1009    USER_NAME_LENGTH 用户名称长度
	1010    ERROR_PARAM 参数错误*/
	
	public static Integer SUCCESS = 2000;
	public static Integer ERROR = 1000;	
	
	public static Integer USER_NOT_EXIST = 1001;
	public static Integer USER_NAME_EXIST = 1002;
	public static Integer USER_PWD_ERROR = 1003;
	public static Integer USER_ALREADY_DEL = 1004;
	public static Integer CHECK_NO_RECORD = 1005;
	public static Integer TOKEN_NOT_EXIST = 1006;
	public static Integer TOKEN_ALREADY_EXPIRE = 1007;
	public static Integer NICK_NAME_EXIST = 1008;
	public static Integer USER_NAME_LENGTH = 1009;
	public static Integer ERROR_PARAM = 1010;
 
	public static final Integer USER_NO_AUTH = 1011;
	public static final Integer USER_OLD_PWD_ERROR = 1012;
	
	public static final Integer RESP_LOGINERROR_AUTHCODE = 2003;
	public static final Integer RESP_ERROR_SESSIONILLEGAL = 2004;
}
