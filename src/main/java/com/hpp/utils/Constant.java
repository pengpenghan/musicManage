package com.hpp.utils;

import java.util.HashMap;
import java.util.Map;

/***
 * 常量定义类
 * 
 * @Description
 */
public class Constant {

	/***
	 * 地区
	 * 
	 * @Description
	 */
	public static enum Status {
		delete(-1), // 删除
		normal(0),//正常
		usernormal(1); //用户正常

		Status(int code) {
			this.code = code;
		}

		private int code;

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}
	}

	/***
	 * 地区
	 * 
	 * @Description
	 */
	public static enum CityType {
		cityName(0), // 全省
		district(1), // 市区
		stationName(3); // 站点

		CityType(int code) {
			this.code = code;
		}

		private int code;

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}
	}

	/***
	 * 地区
	 * 
	 * @Description
	 */
	public static enum AttachType {
		tyhpoon(0);

		AttachType(int code) {
			this.code = code;
		}

		private int code;

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}
	}
}
