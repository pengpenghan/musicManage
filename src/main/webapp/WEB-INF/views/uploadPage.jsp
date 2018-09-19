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
    <script type="text/javascript" src="/resources/js/jquery.form.js"></script>
    <script src="/resources/js/artdialog/jquery.artDialog.js?skin=default" type="text/javascript"></script>
</head>
<body>
	<form method="post" id="uploadForm" enctype="multipart/form-data">
		<!-- 类型<input type="text" id="type" name="type"><br>
		年龄<input type="text" id="type" name="type"><br>
		题目<input type="text" id="type" name="type"><br> -->
		<input style="width: 200px; display: inline; float: left;" id="fileShow" type="text"> 
								<a href="javascript:void(0);" class="fileChoose"> 选择文件
		<input type="file" name="file" id="file" placeholder="请选择要导入的文件"
		onchange="showFileName();">
		<input type="button" style="color:red;width:30px;height:20px;" onclick="uploadFile();" value="上传">
	</form>
    <script type="text/javascript"  src="/resources/js/aes.js"></script>
    <script type="text/javascript"  src="/resources/js/encryptCode.js"></script>
	<script src="/resources/js/uploadFile.js" type="text/javascript">
    </script>
</body>
</html>