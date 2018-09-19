<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>管理员-员工管理-工作详情</title>
    <link rel="stylesheet" href="/resources/css/public.css">
    <link rel="stylesheet" href="/resources/css/style.css">
    <script src="/resources/js/jquery-1.12.4.js"></script>
    <!-- dataTables -->
    <script src="/resources/js/dataTables/js/jquery.dataTables.js" type="text/javascript"></script>
    <link href="/resources/js/dataTables/css/jquery.dataTables-page.css" rel="stylesheet" type="text/css" />
</head>
<body>
    <div class="container container-manageIndex container-manageFileSearch container-staff container-workDetail">
        <header>
            <div class="marginBox">
                <div class="title">
                    <a href="/manager/manageStaff" class="back">返回</a>
                    <h2>管理员界面>员工管理>工作详情</h2>
                </div>
                <div class="quit">
                    <span class="identity">${userNameLogin}</span>
                    <a href="/manager/logout"><span>【</span>退出<span>】</span></a>
                </div>
            </div>
        </header>
        <div class="main">
            <div class="tableBox">
                <div class="selectAllAndDownload">
                    <span class="staffList">${userName}</span>
                </div>
                <input type="hidden" id="userId" value="${userId}">
                <div class="tableWrapper">
                    <table id="gridTable">
                    	<thead>
                        <tr>
                            <th width="">曲目名</th>
                            <th width="">所属教材名</th>
                            <th width="">出版社及版本</th>
                            <th width="">页码</th>
                            <th width="">标签</th>
                            <th width="">其他信息</th>
                            <th width="">附件</th>
                            <!-- <th width="">标记</th> -->
                            <!-- <th width="">更新时间</th> -->
                            <th>备注</th>
                            <th width="200">操作</th>
                        </tr>
                        </thead>
                        <!-- <tr>
                            <td>我爱北京天安门.mp3</td>
                            <td>中文、搞笑、4岁儿童</td>
                            <td>2018-02-06 20：30</td>
                            <td><span class="tips"></span><i class="modify"></i></td>
                        </tr>
                        <tr>
                            <td>我爱北京天安门.mp3</td>
                            <td>中文、搞笑、4岁儿童</td>
                            <td>2018-02-06 20：30</td>
                            <td><span class="tips"></span><i class="modify"></i></td>
                        </tr>
                        <tr>
                            <td>我爱北京天安门.mp3</td>
                            <td>中文、搞笑、4岁儿童</td>
                            <td>2018-02-06 20：30</td>
                            <td><span class="tips"></span><i class="modify"></i></td>
                        </tr>
                        <tr>
                            <td>我爱北京天安门.mp3</td>
                            <td>中文、搞笑、4岁儿童</td>
                            <td>2018-02-06 20：30</td>
                            <td><span class="tips"></span><i class="modify"></i></td>
                        </tr>
                        <tr>
                            <td>我爱北京天安门.mp3</td>
                            <td>中文、搞笑、4岁儿童</td>
                            <td>2018-02-06 20：30</td>
                            <td><span class="tips"></span><i class="modify"></i></td>
                        </tr>
                        <tr>
                            <td>我爱北京天安门.mp3</td>
                            <td>中文、搞笑、4岁儿童</td>
                            <td>2018-02-06 20：30</td>
                            <td><span class="tips"></span><i class="modify"></i></td>
                        </tr>
                        <tr>
                            <td>我爱北京天安门.mp3</td>
                            <td>中文、搞笑、4岁儿童</td>
                            <td>2018-02-06 20：30</td>
                            <td><span class="tips"></span><i class="modify"></i></td>
                        </tr>
                        <tr>
                            <td>我爱北京天安门.mp3</td>
                            <td>中文、搞笑、4岁儿童</td>
                            <td>2018-02-06 20：30</td>
                            <td><span class="tips"></span><i class="modify"></i></td>
                        </tr> -->
                    </table>
                </div>
            </div>
        </div>
        <!--修改备注-->
        <div class="cover" id="modifyTips">
            <div class="addLabelBox">
                <div class="head"><h2>修改备注</h2><i class="close"></i></div>
                <span class="title">请输入备注内容</span>
                <input type="text" id="newPsd" value="">
                <input type="hidden" id="fileId" value="">
                <div class="btns" style="margin-top:0;">
                    <span class="cancel">取消</span>
                    <span class="complete" onclick="addRemark();">完成</span>
                </div>
            </div>
        </div>
        <!--修改-->
        <div class="cover coverModifyWorkDetail" id="coverModifyWorkDetail">
        <form method="post" id="updateWorkInfo" enctype="multipart/form-data">
        <input type="hidden" id="id_dl" name="id_dl">
            <div class="addLabelBox">
                <div class="head"><h2>修改文件</h2><i class="close"></i></div>
				<table>
					<tr>
						<td><span style="color:red;">*</span>曲目名</td>
						<td><input class="modifyipt" type="text" id="musicName_dl" name="musicName_dl"></td>
					</tr>
					<tr>
						<td><span style="color:red;">*</span>所属教材名</td>
						<td class="xl">
							<input class="modifyipt" type="text" id="textBook_dl" name="textBook_dl">
							<ul class="zxl" id="textBookType_dl">
							<c:forEach var="item" items="${textBookTypes}" varStatus="index">
								<li>${item }</li>
							</c:forEach>
							</ul>
						</td>
					</tr>
					<tr>
						<td><span style="color:red;">*</span>出版社及版本</td>
						<td class="xl">
							<input class="modifyipt" type="text" id="pressVersion_dl" name="pressVersion_dl">
							<ul class="zxl" id="pressVersionType_dl">
							<c:forEach var="item" items="${pressVersons}" varStatus="index">
								<li>${item }</li>
							</c:forEach>
							</ul>
						</td>
					</tr>
					<tr>
						<td><span style="color:red;">*</span>页码</td>
						<td><input class="modifyipt" type="text" id="pageNum_dl" name="pageNum_dl" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"></td>
					</tr>
					<tr>
						<td><span style="color:red;">*</span>标签</td>
						<td><input class="modifyipt" type="text" id="labels_dl" name="labels_dl" disabled="disabled"></td>
					</tr>
					<tr>
						<td>标签列表</td>
						<td>
							<ul class="labelList" id="labelList_dl">
							</ul>
						</td>
					</tr>
					<tr>
						<td>其他信息</td>
						<td><textarea id="otherInfo_dl" name="otherInfo_dl"></textarea></td>
					</tr>
					<tr>
						<td>添加附件</td>
						<td><input class="modifyipt" id="fileShow_dl" style="width:100px;float:left;margin-right:20px;" type="text">
						<a class="uploadFileBtn" href="javascript:;">请选择
						<input class="uploadFile" type="file" name="file_dl" id="file_dl" onchange="showFileNameDl();"></a></td>
					</tr>
				</table>            
                <div class="btns" style="margin-top:0;">
                    <span class="cancel">取消</span>
                    <span class="complete" onclick="updateWorkInfo();">完成</span>
                </div>
            </div>
            </form>
        </div>
    </div>
</body>
<script type="text/javascript"  src="/resources/js/drag.js"></script>
<script src="/resources/js/index.js"></script>
<script type="text/javascript"  src="/resources/js/aes.js"></script>
<script type="text/javascript"  src="/resources/js/encryptCode.js"></script>
<script src="/resources/js/artdialog/jquery.artDialog.js?skin=default" type="text/javascript"></script>
<script type="text/javascript" src="/resources/js/manager/fileInfoList.js"></script>
<script type="text/javascript">
	$(function(){
		fileInfoList();
	});
</script>
</html>