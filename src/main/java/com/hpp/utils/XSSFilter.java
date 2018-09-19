package com.hpp.utils;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.*;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 拦截器
 * */
public class XSSFilter implements Filter {
    private static final MyLogger LOGGER=new MyLogger(XSSFilter.class);
    /**
     * 重写父类方法
     * */
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    /**
     * 重写父类方法
     * */
    public void destroy() {
    }
    /**
     * 重写父类方法
     * */
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String method = httpRequest.getMethod();//请求方式
        String path = httpRequest.getServletPath();
        //利用referer请求头实现防盗链
        /*if (!checkReferer(httpRequest, httpResponse)) {
            *//**
             * 如果 链接地址来自其他网站，则返回错误页面 不进行请求响应
             *//*
            request.getRequestDispatcher("/errorReferer.htm").forward(request,
                    response);
            return;
        }*/

        //拦截除POST及GET外的请求
       /* if(!StringUtils.isEmpty(method) && ("post".equals(method.toLowerCase()) || "get".equals(method.toLowerCase()))) {
        
         * //设置request字符编码 request.setCharacterEncoding("UTF-8");
         * //设置response字符编码 response.setContentType("text/html;charset=UTF-8");
         
            try {
                if (checkChange(request) || isAgain(request)) {
                    toError(request, response, httpRequest, httpResponse);
                    return;
                }
            } catch (Exception e) {
                toError(request, response, httpRequest, httpResponse);
                return;
            }
           
        }*/
    chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request),
            response);
    }

    private void toError(ServletRequest request, ServletResponse response, HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException, IOException {
        String basePath = httpRequest.getScheme() + "://" + httpRequest.getServerName() + ":" + httpRequest.getServerPort() + httpRequest.getContextPath() + "/error.htm";
        if (httpRequest.getHeader("x-requested-with") != null
                && "XMLHttpRequest".equalsIgnoreCase(httpRequest.getHeader("x-requested-with"))) {
            //向http头添加 状态 sessionstatus
            httpResponse.setHeader("SESSIONSTATUS", "TIMEOUT");
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            //向http头添加登录的url
            httpResponse.addHeader("CONTEXTPATH", basePath);
        } else {
            request.getRequestDispatcher("/error.htm").forward(request, response);
           // httpResponse.sendRedirect(basePath);
        }
    }

    //防篡改
    private boolean checkChange(ServletRequest request) throws Exception {
        boolean isChanged = false;
        Map<String, String> map = new TreeMap<String, String>();
        if (request.getParameter("encryptFlag") != null && request.getParameter("sign") != null) {
            String sign =request.getParameter("sign");
            Enumeration enu=request.getParameterNames();
            while(enu.hasMoreElements()){
                String paraName=(String)enu.nextElement();
                if(!paraName.equals("sign") &&!paraName.equals("_")){
                    if(request.getParameterValues(paraName).length>1){
//                        String str="";
                        StringBuilder sb=new StringBuilder();
                        for(int i=0;i<request.getParameterValues(paraName).length;i++){
//                            str+=AESUtil.Decrypt(request.getParameterValues(paraName)[i]);
                            sb.append(AESUtil.Decrypt(request.getParameterValues(paraName)[i]));
                        }
                        map.put(paraName,sb.toString());
                        continue;
                    }
//                    else if(request.getParameterValues(paraName+"[]")!=null){
//                        StringBuilder sb=new StringBuilder();
//                        for(int i=0;i<request.getParameterValues(paraName+"[]").length;i++){
////                            str+=AESUtil.Decrypt(request.getParameterValues(paraName)[i]);
//                            sb.append(AESUtil.Decrypt(request.getParameterValues(paraName+"[]")[i]));
//                        }
//                        map.put(paraName,sb.toString());
//                        continue;
//                    }
                    String paraValue = AESUtil.Decrypt(request.getParameter(paraName));
                    map.put(paraName,paraValue);
                }
            }
//            String chekSign="";
            StringBuilder sb=new StringBuilder();
            for (String key : map.keySet()) {
//                String str=(key+"="+map.get(key)+"&");
//                chekSign+=str;
                sb.append(key);
                sb.append("=");
                sb.append(map.get(key));
                sb.append("&");
            }
            String newSign=sb.substring(0,sb.length()-1);
            LOGGER.info(newSign);
            String md5Sign = MD5Util.md5Hex(newSign,"utf-8");
            LOGGER.info("newSign:"+newSign);
            LOGGER.info("sign:"+request.getParameter("sign"));
            LOGGER.info("chekSign:="+md5Sign);
            if(!sign.equals(md5Sign)){
                isChanged=true;
            }
        }
        return isChanged;
    }
    //重放攻击
    private boolean isAgain(ServletRequest request){
        boolean isAgain = false;
        if (request.getParameter("timestamp") != null){
            try {
                String paraValue = AESUtil.Decrypt(request.getParameter("timestamp"));
                Long preTime = Long.valueOf(paraValue.substring(0,13));
                Long backTime = new Date().getTime();
                //请求时间与服务器时间相差超过2分钟算篡改
                if(backTime>(preTime+120000)){
                    return true;
                }
                HttpServletRequest httpRequest = (HttpServletRequest) request;
                HashSet usedSigns = (HashSet) httpRequest.getSession().getAttribute("timestampIsAgain");
                if(usedSigns!=null){
                    Integer signNums = usedSigns.size();
                    usedSigns.add(paraValue);
                    if(usedSigns.size()==signNums){
                        isAgain = true;
                    }
                }else {
                    putSignsToSession(paraValue, httpRequest);
                }
                //单session中存储标签超过200个时清零
                if(usedSigns!=null&&usedSigns.size()>200){
                    putSignsToSession(paraValue, httpRequest);
                    usedSigns=null;
                }
            } catch (Exception e) {
                isAgain = true;
            }
        }
        return isAgain;
    }

    private void putSignsToSession(String paraValue, HttpServletRequest httpRequest) {
        HashSet newUsedSigns = new HashSet(205);
        newUsedSigns.add(paraValue);
        httpRequest.getSession().setAttribute("timestampIsAgain",newUsedSigns);
    }

    //校验头部信息referer
    private boolean checkReferer(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException, IOException {
        String requestUri = httpRequest.getRequestURI();
        if(requestUri.equals("/goenterpage.html")){
            return true;
        }
        String serverName = httpRequest.getServerName();

        // 禁止缓存
        httpResponse.setHeader("Cache-Control", "no-store");
        httpResponse.setHeader("Pragrma", "no-cache");
        httpResponse.setDateHeader("Expires", 0);
        // 链接来源地址
        String referer = httpRequest.getHeader("referer");
        //checkHtm ：过滤不需要判断referer 请求
        if ((referer == null || !referer.contains(serverName)) && !checkHtm(requestUri)) {
            return false;
        }
        return true;
    }

    //校验请求路径
    public boolean checkHtm(String urlHtm) {
        // 判断请求路径是否需要校验referer
        String[] urlHtms = {
            "index.htm","login","register.html","/error.htm","/errorReferer.htm"
        };
        boolean flag = false;
        for(int i = 0;i<urlHtms.length;i++){
            if(-1 != urlHtm.indexOf(urlHtms[i]) || urlHtm.equals("/")){
                return flag = true;
            }
        }
        return flag;
    }

}