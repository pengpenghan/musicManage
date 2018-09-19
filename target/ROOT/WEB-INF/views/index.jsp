<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0"> 
	<title>登录界面</title>
    <link href="/resources/login/css/default.css" rel="stylesheet" type="text/css" />
	<!--必要样式-->
    <link href="/resources/login/css/styles.css" rel="stylesheet" type="text/css" />
    <link href="/resources/login/css/demo.css" rel="stylesheet" type="text/css" />
    <link href="/resources/login/css/loaders.css" rel="stylesheet" type="text/css" />
    <link href="/resources/login/layui/css/layui.css" rel="stylesheet" type="text/css" />
	<script src="/resources/login/js/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="/resources/js/jquery.cookie.js"></script>
    <script src="/resources/js/artdialog/jquery.artDialog.js?skin=default" type="text/javascript"></script>
</head>
<body>
	<div class='login'>
	  <div class='login_title'>
	    <span>管理员登录</span>
	  </div>
	  <div class='login_fields'>
	    <div class='login_fields__user'>
	      <div class='icon'>
	        <img alt="" src='/resources/login/img/user_icon_copy.png'>
	      </div>
	      <input name="name" placeholder='用户名' maxlength="16" type='text' autocomplete="off" value="admin"/>
	        <div class='validation'>
	          <img alt="" src='/resources/login/img/tick.png'>
	        </div>
	    </div>
	    <div class='login_fields__password'>
	      <div class='icon'>
	        <img alt="" src='/resources/login/img/lock_icon_copy.png'>
	      </div>
	      <input name="pwd" placeholder='密码' maxlength="16" type='text' autocomplete="off">
	      <div class='validation'>
	        <img alt="" src='/resources/login/img/tick.png'>
	      </div>
	    </div>
	    <div class='login_fields__password'>
	      <div class='icon'>
	        <img alt="" src='/resources/login/img/key.png'>
	      </div>
	      <input name="code" placeholder='验证码' maxlength="4" type='text' name="ValidateNum" autocomplete="off">
	      <div class='validation' style="opacity: 1; right: -5px;top: -3px;">
          <canvas class="J_codeimg" id="myCanvas" onclick="Code();">对不起，您的浏览器不支持canvas，请下载最新版浏览器!</canvas>
	      </div>
	    </div>
	    <div class='login_fields__submit'>
	      <input type='button' value='登录'>
	    </div>
	  </div>
	  <div class='success'>
	  </div>
	  <div class='disclaimer'>
	    <p>欢迎登陆后台管理系统</p>
	  </div>
	</div>
	<div class='authent'>
	  <div class="loader" style="height: 44px;width: 44px;margin-left: 28px;">
        <div class="loader-inner ball-clip-rotate-multiple">
            <div></div>
            <div></div>
            <div></div>
        </div>
        </div>
	  <p>认证中...</p>
	</div>
	<div class="OverWindows"></div>
	<script type="text/javascript" src="/resources/login/js/jquery-ui.min.js"></script>
	<script type="text/javascript" src='/resources/login/js/stopExecutionOnTimeout.js?t=1'></script>
    <script src="/resources/login/layui/layui.js" type="text/javascript"></script>
    <script src="/resources/login/js/Particleground.js" type="text/javascript"></script>
    <script src="/resources/login/js/Treatment.js" type="text/javascript"></script>
    <script src="/resources/login/js/jquery.mockjax.js" type="text/javascript"></script>
    <script type="text/javascript"  src="/resources/js/aes.js"></script>
    <script type="text/javascript"  src="/resources/js/encryptCode.js"></script>
	<script src="/resources/login/js/login.js" type="text/javascript">
    </script>
</body>
</html>
