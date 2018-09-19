package com.hpp.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 请求包装类
 * 
 * @author NP-HEHU
 * @date 2015-8-28 17:47:23
 */
public class XSSRequestWrapper extends HttpServletRequestWrapper {

	/**
	 * 密钥
	 */
	private static final String sKey = "abcdefgabcdefg13";

	/**
	 * 构照方法
	 * */
	public XSSRequestWrapper(HttpServletRequest servletRequest) {
		super(servletRequest);
	}

	/**
	 * 重写父类方法
	 * */
	@Override
	public String[] getParameterValues(String parameter) {

		String[] values = super.getParameterValues(parameter);
		if (values == null) {
			return values;
		}
		int count = values.length;
		String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {
			String value = values[i];
			// 解密
			if (isEncrypt() && value != null) {
				try {
					value = Decrypt(value);
					if (value == null || value.equals("null")) {
						value = "";
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("getParameterValues Decrypt" + e);
				}
			}
			Matcher mat = SqlInjection.HasInjectionData(value);
			if (!mat.find()) {
				encodedValues[i] = stripXSS(value, parameter);
			}
		}
		return encodedValues;
	}

	/**
	 * 是否是加密请求
	 * 
	 * @return
	 */
	public boolean isEncrypt() {
		boolean isEncrypt = false;
		Map map = this.getParameterMap();
		if (map != null && map.size() > 0) {
			Set set = map.keySet();
			if (set.contains("encryptFlag")) {
				isEncrypt = true;
			}
		}
		return isEncrypt;
	}

	/**
	 * 重写父类方法
	 * */
	@Override
	public String getParameter(String parameter) {
		String value = super.getParameter(parameter);
		// 解密
		if (isEncrypt() && value != null) {
			try {
				value = Decrypt(value);
				if (value == null || value.equals("null")) {
					value = "";
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("getParameter Decrypt" + e);
			}
		}
		return stripXSS(value, parameter);
	}

	/**
	 * 重写父类方法
	 * */
	@Override
	public String getHeader(String name) {
		String value = super.getHeader(name);
		return stripXSS(value, name);
	}

	/**
	 * 过滤参数
	 * 
	 * @param value
	 *            参数值
	 * @param parameter
	 *            参数name名
	 * @return
	 */
	private String stripXSS(String value, String parameter) {
		String valueNew = value;

		if ("keyWords".equals(parameter)) {
			System.out.println("==========================");
		}
		if (getNoCheckParameter(parameter) && valueNew != null) {

			// NOTE: It's highly recommended to use the ESAPI library and
			// uncomment the following line to
			// avoid encoded attacks.
			// value = ESAPI.encoder().canonicalize(value);
			// Avoid null characters
			valueNew = valueNew.replaceAll("", "");
			// Avoid anything between script tags

			Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
			valueNew = scriptPattern.matcher(valueNew).replaceAll("");

			scriptPattern = Pattern.compile("[%<>\"]+");
			valueNew = scriptPattern.matcher(valueNew).replaceAll("");

			// Avoid anything in a src='...' type of e­xpression
			scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL);
			valueNew = scriptPattern.matcher(valueNew).replaceAll("");

			scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL);
			valueNew = scriptPattern.matcher(valueNew).replaceAll("");

			// Remove any lonesome </script> tag
			scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
			valueNew = scriptPattern.matcher(valueNew).replaceAll("");

			// Remove any lonesome <script ...> tag
			scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
					| Pattern.DOTALL);
			valueNew = scriptPattern.matcher(valueNew).replaceAll("");

			// Avoid eval(...) e­xpressions
			scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
					| Pattern.DOTALL);
			valueNew = scriptPattern.matcher(valueNew).replaceAll("");

			// Avoid e­xpression(...) e­xpressions
			scriptPattern = Pattern.compile("e­xpression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
					| Pattern.DOTALL);
			valueNew = scriptPattern.matcher(valueNew).replaceAll("");

			// Avoid javascript:... e­xpressions
			scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
			valueNew = scriptPattern.matcher(valueNew).replaceAll("");

			// Avoid vbscript:... e­xpressions
			scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
			valueNew = scriptPattern.matcher(valueNew).replaceAll("");

			// Avoid onload= e­xpressions
			scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
					| Pattern.DOTALL);
			valueNew = scriptPattern.matcher(valueNew).replaceAll("");

			// 采用正则表达式将包含有 单引号(')，分号(;) 和 注释符号(--)的语句给替换掉来防止SQL注入
			scriptPattern = Pattern.compile(".*([';]+|(--)+).*");
			valueNew = scriptPattern.matcher(valueNew).replaceAll("");
		}

		return valueNew;
	}

	// 加密
	public static String Encrypt(String sSrc) throws Exception {
		if (sKey == null) {
			System.out.print("Key为空null");
			return null;
		}
		// 判断Key是否为16位
		if (sKey.length() != 16) {
			System.out.print("Key长度不是16位");
			return null;
		}
		byte[] raw = sKey.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// "算法/模式/补码方式"
		IvParameterSpec iv = new IvParameterSpec("abcdefgabcdefg11".getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		byte[] encrypted = cipher.doFinal(sSrc.getBytes());

		String str = new String(Base64.encodeBase64(encrypted));// 此处使用BAES64做转码功能，同时能起到2次加密的作用。
		return str;
	}

	// 解密
	public static String Decrypt(String sSrc) throws Exception {
		try {
			// 判断Key是否正确
			if (sKey == null) {
				System.out.print("Key为空null");
				return null;
			}
			// 判断Key是否为16位
			if (sKey.length() != 16) {
				System.out.print("Key长度不是16位");
				return null;
			}
			byte[] raw = sKey.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec("abcdefgabcdefg11".getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] encrypted1 = Base64.decodeBase64(sSrc.getBytes());// 先用bAES64解密
			try {
				byte[] original = cipher.doFinal(encrypted1);
				String originalString = new String(original, "utf-8");
				return originalString;
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	/**
	 * 判断name是否应该拦截
	 * 
	 * @param parameter
	 *            参数名
	 * @return 不拦截返回true，拦截返回false
	 */

	private boolean getNoCheckParameter(String parameter) {
		String[] noFilterURLs = new String[] { "goodsDetailDesc", "goodsMobileDesc", "bsetUseragreement", "mobileDesc",
				"bsetUseragreementuser", "bsetCopyright", "content", "thirdUserment", "helpContent", "marketingDes",
				"giftDesc", "ipCont", "str", "pageDes", "title", "thirdProjectContext", "backInfoRemark",
				"backPriceRemark", "payHelp", "giftText", "Referer", "brandName"// ,"chProvince","ch_city","ch_distinct"
		};

		for (String parameters : noFilterURLs) {
			if (parameter.equals(parameters)) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) throws Exception {

		String content = "我爱你";
		System.out.println("加密前：" + content);

		System.out.println("加密密钥和解密密钥：" + sKey);

		String encrypt = Encrypt(content);
		System.out.println("加密后：" + encrypt);

		String decrypt = Decrypt("BOP2YsnrC8xTZe0k5QLaOQ==");
		System.out.println("解密后：" + decrypt);

		encrypt = Encrypt(content);
		System.out.println("加密后：" + encrypt);
	}

}