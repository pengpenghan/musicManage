<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>管理员-员工管理</title>
    <link rel="stylesheet" href="/resources/css/public.css">
    <link rel="stylesheet" href="/resources/css/style.css">
    <script src="/resources/js/jquery-1.12.4.js"></script>
    <!-- dataTables -->
    <script src="/resources/js/dataTables/js/jquery.dataTables.js" type="text/javascript"></script>
    <link href="/resources/js/dataTables/css/jquery.dataTables-page.css" rel="stylesheet" type="text/css" />
</head>
<body>
    <div class="container container-manageIndex container-manageFileSearch container-staff">
        <header>
            <div class="marginBox">
                <div class="title">
                    <a href="/manager/manageIndex" class="back">返回</a>
                    <h2>管理员界面>员工管理</h2>
                </div>
                <div class="quit">
                    <span class="identity">${userName}</span>
                    <a href="/manager/logout"><span>【</span>退出<span>】</span></a>
                </div>
            </div>
        </header>
        <div class="main">
            <div class="tableBox">
                <div class="selectAllAndDownload">
                    <span class="staffList">员工列表</span>
                    <span class="addStaff">新增员工</span>
                </div>
                <div class="tableWrapper">
                    <table id="gridTable">
                    	<thead>
	                        <tr>
	                            <th width="25%">用户名</th>
	                            <th width="25%">密码</th>
	                            <th width="25%">标记数量</th>
	                            <th>操作</th>
	                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
        <!--添加用户-->
        <div class="cover" id="staffCover">
            <div class="addLabelBox">
                <div class="head"><h2>添加用户</h2><i class="close"></i></div>
                <div class="iptWrapper"><span>用户名</span> <input type="text" id="userName"></div>
                <div class="iptWrapper"><span>密码</span><input type="text" id="password"></div>
                <div class="btns">
                    <span class="cancel">取消</span>
                    <span class="complete" onclick="addUser();">完成</span>
                </div>
            </div>
        </div>
        <!--修改密码-->
        <div class="cover" id="changePsw">
            <div class="addLabelBox">
                <div class="head"><h2>修改用户“<span id="name">张三</span>”的密码</h2><i class="close"></i></div>
                <input type="hidden" id="userName">
                <span class="title">请输入新密码</span>
                <input type="text" id="newPsd">
                <div class="btns" style="margin-top:0;">
                    <span class="cancel">取消</span>
                    <span class="complete" onclick="updateUser();">完成</span>
                </div>
            </div>
        </div>
    </div>
</body>
<script src="/resources/js/index.js"></script>
<script type="text/javascript"  src="/resources/js/aes.js"></script>
<script type="text/javascript"  src="/resources/js/encryptCode.js"></script>
<script src="/resources/js/artdialog/jquery.artDialog.js?skin=default" type="text/javascript"></script>
<script type="text/javascript" src="/resources/js/manager/userList.js"></script>
</html>