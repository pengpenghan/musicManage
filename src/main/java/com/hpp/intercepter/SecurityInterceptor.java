package com.hpp.intercepter;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hpp.model.SysUser;
import com.hpp.utils.ResultCode;

/**
 * 权限拦截器
 *  
 * 
 */

public class SecurityInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(SecurityInterceptor.class);
	 

	private List<String> excludeUrls;// 不需要拦截的资源

	public List<String> getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	/**
	 * 完成页面的render后调用
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception) throws Exception {
	}

	/**
	 * 在调用controller具体方法后拦截
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView) throws Exception {
		 String newLine =" ==== ";// System.getProperty("line.separator");
         StringBuilder stringBuilder = new StringBuilder();
         stringBuilder.append(newLine);

         stringBuilder.append(request.getMethod()+" "+request.getRequestURI()).append(newLine);
         stringBuilder.append("thread id : "+Thread.currentThread().getName()+"|"+request.getMethod()).append("");

         Enumeration enumeration = request.getHeaderNames();
         stringBuilder.append("Header:{");
         while(enumeration.hasMoreElements()){
       	      Object obj = enumeration.nextElement();
             stringBuilder.append(obj+" = "+request.getHeader(obj.toString())+",");
         }
         stringBuilder.append("}").append(newLine);
         stringBuilder.append("Parameter:{");
         for(Map.Entry<String, String[]> sub : ((Map<String, String[]>)request.getParameterMap()).entrySet()){
             stringBuilder.append(sub.getKey()+" = "+Arrays.toString(sub.getValue())+",");
         }
         stringBuilder.append("}").append(newLine);
         logger.debug(stringBuilder.toString());
  
	}

	/**
	 * 在调用controller具体方法前拦截
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = requestUri.substring(contextPath.length());
 
		if ( excludeUrls.contains(url)||url.indexOf("/api/") > - 1 ) {// 如果要访问的资源是不需要验证的
			return true;
		}
		
		SysUser user = (SysUser) request.getSession().getAttribute(ResultCode.USER_LOGIN_INFO);
		if (user == null) {// 如果没有登录或登录超时
			String gourl = "http://"+request.getServerName()+":"+request.getServerPort() + "/index";
			response.sendRedirect(gourl);
			return false;
		}
       return true;
	}
}
