<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>管理员首页</title>
    <link rel="stylesheet" href="/resources/css/public.css">
    <link rel="stylesheet" href="/resources/css/style.css">
    <script src="/resources/js/jquery-1.12.4.js"></script>
</head>
<body>
    <div class="container container-manageIndex">
        <header>
            <div class="marginBox">
                <h2>管理员界面</h2>
                <div class="quit">
                    <span class="identity">${userName}</span>
                    <a href="/manager/logout"><span>【</span>退出<span>】</span></a>
                </div>
            </div>
        </header>
        <div class="nav">
            <a href="/manager/manageFileSearch">
                <div class="imgWrapper"><img src="/resources/images/pic07.png" alt=""></div>
                <span>文件搜索</span>
            </a>
            <a href="/manager/manageStaff">
                <div class="imgWrapper"><img src="/resources/images/pic08.png" alt=""></div>
                <span>员工管理</span>
            </a>
        </div>
    </div>
</body>
<script src="/resources/js/index.js"></script>
</html>