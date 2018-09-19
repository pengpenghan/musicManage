<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>管理员-文件搜索</title>
    <link rel="stylesheet" href="/resources/css/public.css">
    <link rel="stylesheet" href="/resources/css/style.css">
    <script src="/resources/js/jquery-1.12.4.js"></script>
    <!-- dataTables -->
    <script src="/resources/js/dataTables/js/jquery.dataTables.js" type="text/javascript"></script>
    <link href="/resources/js/dataTables/css/jquery.dataTables-page.css" rel="stylesheet" type="text/css" />
</head>
<body>
    <div class="container container-manageIndex container-manageFileSearch">
        <header>
            <div class="marginBox">
                <div class="title">
                    <a href="/manager/manageIndex" class="back">返回</a>
                    <h2>管理员界面>文件搜索</h2>
                </div>
                <div class="quit">
                    <span class="identity">${userName}</span>
                    <a href="/manager/logout"><span>【</span>退出<span>】</span></a>
                </div>
            </div>
        </header>
        <div class="main">
            <div class="labelListBox">
                <h2>标签列表</h2>
                <div class="labelList">
                    <ul class="labelListUl" id="labelListUl">
                        <!-- <li>
                            <i class="selected"></i>
                            <span class="name">中文</span>
                            <i class="delete"></i>
                        </li>
                        <li>
                            <i class="selected"></i>
                            <span class="name">爱国</span>
                            <i class="delete"></i>
                        </li>
                        <li>
                            <i class="selected"></i>
                            <span class="name">四岁</span>
                            <i class="delete"></i>
                        </li>
                        <li>
                            <i class="selected"></i>
                            <span class="name">英文</span>
                            <i class="delete"></i>
                        </li> -->
                    </ul>
                    <div class="add" id="addLabel">添加</div>
                </div>
            </div>
            <!-- <div class="labelListBox">
                <h2>文件类别</h2>
                <div class="labelList">
                    <ul class="labelListUl" id="fileTypeListUl">
                        <li>
                            <i class="selected"></i>
                            <span class="name">中文</span>
                            <i class="delete"></i>
                        </li>
                        <li>
                            <i class="selected"></i>
                            <span class="name">爱国</span>
                            <i class="delete"></i>
                        </li>
                        <li>
                            <i class="selected"></i>
                            <span class="name">四岁</span>
                            <i class="delete"></i>
                        </li>
                        <li>
                            <i class="selected"></i>
                            <span class="name">英文</span>
                            <i class="delete"></i>
                        </li>
                    </ul>
                    <div class="add" id="addFile">添加</div>
                </div>
            </div> -->
            <div class="tableBox">
                <div class="searchBox">
                    <input type="text" class="searchIpt" id="search_param" placeholder="输入查询关键字">
                    <span class="searchBtn" onclick="getSearchList();">查询</span>
                </div>
                <div class="selectAllAndDownload">
                    <span class="selectAll">全选</span>
                    <span class="download" onclick="downloadFile();">下载</span>
                </div>
                <div class="tableWrapper">
                    <table id="fileInfoList">
                    	
                    </table>
                </div>
            </div>
        </div>
        <!--添加新的标签-->
        <div class="cover" id="labelCover">
            <div class="addLabelBox">
                <div class="head"><h2>添加新的标签</h2><i class="close"></i></div>
                <span class="title">请输入标签名字</span>
                <input type="text" id="labelName">
                <div class="btns">
                    <span class="cancel">取消</span>
                    <span class="complete" onclick="addLabel();">完成</span>
                </div>
            </div>
        </div>
        <!--添加新的文件-->
        <div class="cover" id="fileCover">
            <div class="addLabelBox">
                <div class="head"><h2>添加新的文件类别</h2><i class="close"></i></div>
                <span class="title">请输入文件类别名</span>
                <input type="text" id="typeName">
                <div class="btns">
                    <span class="cancel">取消</span>
                    <span class="complete" onclick="addFileType();">完成</span>
                </div>
            </div>
        </div>
    </div>
</body>
<script src="/resources/js/index.js"></script>
<script type="text/javascript"  src="/resources/js/aes.js"></script>
<script type="text/javascript"  src="/resources/js/encryptCode.js"></script>
<script src="/resources/js/artdialog/jquery.artDialog.js?skin=default" type="text/javascript"></script>
<script type="text/javascript" src="/resources/js/manager/fileSearch.js"></script>
</html>