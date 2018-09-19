$(function() {
	getSearchList();//文件列表
	getLabelList();//标签列表
	getFileTypeList();//文件类型
	//fileInfoList();
});

function getSearchList() {
	var param = $("#search_param").val();
	$.ajax({
		url : '/manager/search/list',
		data : {
			param : param
		},
		type : 'post',
		success:function(data){    
			console.log(data);
			if(data.success){
				$("#fileInfoList").html('');
				var str = "<th style=\"padding-left: 43px;padding-left: 43px;padding-bottom: 10px;\">曲目名</th><th>所属教材名</th><th>出版社及版本</th><th>页码</th><th>标签</th><th>其他信息</th><th>附件</th><th>标记</th>";
				$("#fileInfoList").append(str);
				$.each(data.data,function(i,datas){
					var str = "<tr style=\"text-align: center;\"><td class=\"musicName\" id=\"file_"+datas.id+"\" value=\""+datas.id+"\">"+datas.musicName+"</td>";
					str += "<td>"+datas.textBook+"</td>";
					str += "<td>"+datas.pressVersion+"</td>";
					str += "<td>"+datas.pageNum+"</td>";
					str += "<td>"+datas.labels+"</td>";
					if(!datas.otherInfo){
						str += "<td>-</td>";
					}else{
						str += "<td>"+datas.otherInfo+"</td>";
					}
					if(!datas.fileName){
						str += "<td>-</td>";
					}else{
						str += "<td>"+datas.fileName+"</td>";
					}
					if(!datas.remark){
						str += "<td>-</td>";
					}else{
						str += "<td style=\"color:red;\">"+datas.remark+"</td>";
					}
					$("#fileInfoList").append(str);
				});
			}
		}
	});
}

function downloadFile(){
	var fileIds = new Array();
	$("#fileInfoList tr td").each(function() {
		if($(this).attr("class") != undefined && $(this).hasClass("active")){
			fileIds.push($(this).attr("value"));
		}
	});
	if(fileIds.length == 0){
		art.dialog({
			icon : 'error',
			time : 2,
			content : '请选择一条或多条数据！'
		});
		return;
	}
	art.dialog.confirm("确定下载？",function(){
		window.location.href = "/manager/downloadZip?fileIds=" + fileIds.join(",");
	});
}

function download(id){
	art.dialog.confirm("确定下载？",function(){
		window.location.href = "/manager/download?id=" + id;
	});
}

function getLabelList(){
	$.ajax({
		url : '/manager/label/list',
		data : {},
		type : 'post',
		success:function(data){
			if(data.success){
				$("#labelListUl").html('');
				$.each(data.data,function(i,datas){
					var str = "<li><i class=\"selected\"></i>";
					str += "<span class=\"name\">"+datas.labelName+"</span>";
					str += "<i class=\"delete\" onclick=\"deleteLabel("+datas.id+");\"></i></li>";
					$("#labelListUl").append(str);
				});
			}
		}
	});
}

function getFileTypeList(){
	$.ajax({
		url : '/manager/fileType/list',
		data : {},
		type : 'post',
		success:function(data){
			if(data.success){
				$("#fileTypeListUl").html('');
				$.each(data.data,function(i,datas){
					var str = "<li><i class=\"selected\"></i>";
					str += "<span class=\"name\">"+datas.typeName+"</span>";
					str += "<i class=\"delete\" onclick=\"deleteFileType("+datas.id+");\"></i></li>";
					$("#fileTypeListUl").append(str);
				});
			}
		}
	});
}

/**
 * 添加标签列表
 */
function addLabel(){
	$.ajax({
		url : '/manager/label/add',
		data : {
			labelName : $("#labelName").val()
		},
		type : 'post',
		success:function(data){
			if(data.success){
				art.dialog({
					icon : 'succeed',
					time : 2,
					content : data.msg
				});
				$(".cover").hide();
				$("#labelName").val("");
				getLabelList();
			}else {
				art.dialog({
					icon : 'error',
					time : 2,
					content : data.msg
				});
			}
		}
	});
}

/**
 * 删除标签列表
 */
function deleteLabel(id){
	art.dialog.confirm("确认删除？",function(){
		$.ajax({
			url : '/manager/label/delete',
			data : {
				id : id
			},
			type : 'post',
			success:function(data){
				if(data.success){
					art.dialog({
						icon : 'succeed',
						time : 2,
						content : data.msg
					});
					getLabelList();
				}else {
					art.dialog({
						icon : 'error',
						time : 2,
						content : data.msg
					});
				}
			}
		});
	})
}

/**
 * 添加文件类型
 */
function addFileType(){
	$.ajax({
		url : '/manager/fileType/add',
		data : {
			typeName : $("#typeName").val()
		},
		type : 'post',
		success:function(data){
			if(data.success){
				art.dialog({
					icon : 'succeed',
					time : 2,
					content : data.msg
				});
				$(".cover").hide();
				$("#typeName").val("");
				getFileTypeList();
			}else {
				art.dialog({
					icon : 'error',
					time : 2,
					content : data.msg
				});
			}
		}
	});
}

/**
 * 删除文件类型
 */
function deleteFileType(id){
	art.dialog.confirm("确认删除？",function(){
		$.ajax({
			url : '/manager/fileType/delete',
			data : {
				id : id
			},
			type : 'post',
			success:function(data){
				if(data.success){
					art.dialog({
						icon : 'succeed',
						time : 2,
						content : data.msg
					});
					$(".cover").hide();
					$("#typeName").val("");
					getFileTypeList();
				}else {
					art.dialog({
						icon : 'error',
						time : 2,
						content : data.msg
					});
				}
			}
		});
	})
}