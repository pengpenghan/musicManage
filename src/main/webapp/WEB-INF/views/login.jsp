<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>音乐管理</title>
    <link rel="stylesheet" href="/resources/css/public.css">
    <link rel="stylesheet" href="/resources/css/style.css">
    <script src="/resources/js/jquery-1.12.4.js"></script>
    <script type="text/javascript" src="/resources/js/jquery.cookie.js"></script>
    <script src="/resources/js/artdialog/jquery.artDialog.js?skin=default" type="text/javascript"></script>
    <script>
	$(document).ready(function(){
	  $("#rember_pass").click(function(){
	  	if($('#rember_pass').is('.cur')){
         	$('#rember_pass').removeClass('cur');
        }
		else{
			$('#rember_pass').addClass('cur');
		}
	  });
	});


	$(document).ready(function() { 
		if ($.cookie("rmbUser") == "true") { 
		    $('#rember_pass').addClass('cur');
			$("#username").val($.cookie("username")); 
			$("#password").val($.cookie("password")); 
			var userRole = $.cookie("userRole");
			$("#user_role").val(userRole)
			if(userRole == 0){
				$("ul li").eq(0).attr("class","active");
			}else{
				$("ul li").eq(1).attr("class","active");
			}
		} 
	}); 
	</script>
</head>
<body>
    <div class="container container-login">
        <div class="loginBox">
            <h2>账户登录</h2>
            <div class="login">
                <div class="iptWrapper"><input type="text" class="username" placeholder="用户名" id="username"><i class="usernameIcon"></i></div>
                <div class="iptWrapper"><input type="password" class="password" placeholder="密码" id="password"><i class="passwordIcon"></i></div>
                <ul class="checkBox">
                    <li value="0">管理员</li>
                    <li value="1">员工</li>
                </ul>
                <input type="hidden" id="user_role" value="0">
                <div class="btn" id="loginBtn">登&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp录</div>
            </div>
        </div>
    </div>
</body>
<script type="text/javascript"  src="/resources/js/aes.js"></script>
<script type="text/javascript"  src="/resources/js/encryptCode.js"></script>
<script src="/resources/js/index.js"></script>
<script src="/resources/js/login.js"></script>
</html>