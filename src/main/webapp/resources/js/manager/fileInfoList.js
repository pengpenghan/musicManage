var labels_dl = new Array();//选中标签
var labelIds_dl = new Array();//选中标签id

$(function(){
	getLabels();//获取标签列表
	//修改标签列表
	$("#labelList_dl").on("click","li",function(){
		var labelId = $(this).attr("value");
		var labelName = $(this).text();
		if($.inArray(labelName,labels_dl) != -1){
			removeByValue(labels_dl,labelName);
			removeByValue(labelIds_dl,labelId);
		}else{
			labels_dl.push(labelName);
			labelIds_dl.push(labelId);
		}
		$("#labels_dl").val(labels_dl.join(","));
	});
});

/**
 * 上传文件列表
 */
function fileInfoList(){
	$("#gridTable").dataTable(
	{
		ajax : {
			type : 'post',
			url : '/manager/fileInfo/list',
			data : {
				userId : $("#userId").val(),
				type : 1 //取有标记的数据
			}
		}, 
		aoColumnDefs: [
		               	  { "sWidth": "170px", "aTargets": [0] },
		                  { "sWidth": "60px", "aTargets": [3] }
		               ],   
		destroy:true,
		bAutoWidth : false,
		bServerSide : true,
		bPaginate : true,
		bLengthChange : false,
		iDisplayLength : 10,
		processing : true,
		searching : false,
		info : false,
		ordering : false,
		columns : [
				{
					render : function(data, type, row, meta) {
						var r = "-";
						if (null == row.musicName
								|| "" == row.musicName) {
							r = "-";
						} else {
							r = row.musicName;
						}
						return r;
					},
				},
				{
					render : function(data, type, row, meta) {
						var r = "-";
						if (null == row.textBook
								|| "" == row.textBook) {
							r = "-";
						} else {
							r = row.textBook;
						}
						return r;
					},
				},
				{
					render : function(data, type, row, meta) {
						var r = "-";
						if (null == row.pressVersion
								|| "" == row.pressVersion) {
							r = "-";
						} else {
							r = row.pressVersion;
						}
						return r;
					},
				},
				{
					render : function(data, type, row, meta) {
						var r = "-";
						if (null == row.pageNum
								|| "" == row.pageNum) {
							r = "-";
						} else {
							r = row.pageNum;
						}
						return r;
					},
				},
				{
					render : function(data, type, row, meta) {
						var r = "-";
						if (null == row.labels
								|| "" == row.labels) {
							r = "-";
						} else {
							r = row.labels;
						}
						return r;
					},
				},
				{
					render : function(data, type, row, meta) {
						var r = "-";
						if (null == row.otherInfo
								|| "" == row.otherInfo) {
							r = "-";
						} else {
							r = row.otherInfo;
						}
						return r;
					},
				},
				{
					render : function(data, type, row, meta) {
						var r = "-";
						if (null == row.fileName
								|| "" == row.fileName) {
							r = "-";
						} else {
							r = row.fileName;
						}
						return r;
					},
				},
				{
					render : function(data, type, row, meta) {
						var str = "<span class=\"tips\"></span><i class=\"modify\" onclick=\"getRemark("+row.id+");\"></i>";
						return str;
					},
				},
				{
					render : function(data, type, row, meta) {
						var str = "<span class=\"addStaff\" onclick=\"deleteFileInfo('"+row.id+"');\">删除</span><span class=\"addStaff modifyWorkDetail\" onclick=\"getTableInfo('"+row.id+"');\">修改</span>";
						return str;
					},
				}
				],
		language : {
			emptyTable : '暂无数据',
			processing : '查询中',
			paginate : {
				previous : '上一页',
				next : '下一页'
			}
		}
	});
}

function getRemark(id){
	$.ajax({
		url : '/manager/fileInfo/info',
		type : 'post',
		data : {
			id : id
		},
		success:function(data){
			if(data.success){
				$("#fileId").val(data.data.id);
				if(data.data.remark != ""){
					$("#newPsd").val(data.data.remark);
				}else {
					$("#newPsd").val("标记有误");
				}
			}
		}
	});
}

function addRemark(){
	var fileId = $("#fileId").val();
	var remark = $("#newPsd").val();
	var datas = {
		id : fileId,
		remark : remark
	}
	$.ajax({
		url : '/manager/remark/update',
		type : 'post',
		data : datas,
		success:function(data){
			if(data){
				art.dialog({
					icon : 'succeed',
					time : 2,
					content : data.msg
				});
				$(".cover").hide();
				$("#newPsd").val();
				$("#gridTable").dataTable().fnDraw(false); 
			}else {
				art.dialog({
					icon : 'succeed',
					time : 2,
					content : data.msg
				});
			}
		}
	});
}

function deleteFileInfo(id) {
	var datas = {
		id : id
	};
	art.dialog("确认删除？",function(){
		$.ajax({
			url : '/manager/fileInfo/delete',
			type : 'post',
			data : datas,
			success:function(data){
				if(data){
					art.dialog({
						icon : 'succeed',
						time : 2,
						content : data.msg
					});
					$("#gridTable").dataTable().fnDraw(false); 
				}else {
					art.dialog({
						icon : 'succeed',
						time : 2,
						content : data.msg
					});
				}
			}
		});
	});
}

function searchByUserId() {
	var oSettings = $("#gridTable").dataTable().fnSettings();
	oSettings.ajax.data.userId = $("#userId").val();
	$("#gridTable").dataTable().fnDraw(oSettings);
}

/**
 * 获取列表点击框的详情
 */
function getTableInfo(id){
	$.ajax({
		url : '/manager/fileInfo/info',
		data : {
			id : id
		},
		type : 'post',
		success : function(data){
			if(data.success){
				var obj = data.data;
				$("#id_dl").val(obj.id);
				$("#musicName_dl").val(obj.musicName);
				$("#textBook_dl").val(obj.textBook);
				$("#pressVersion_dl").val(obj.pressVersion);
				$("#pageNum_dl").val(obj.pageNum);
				$("#labels_dl").val(obj.labels);
				$("#otherInfo_dl").val(obj.otherInfo);
				$("#fileShow_dl").val(obj.fileName);
				labels_dl = clear_arr_trim(obj.labels.split(","));
				labelIds_dl = clear_arr_trim(obj.labelIds.split(","));
				$("#labelList_dl li").removeClass("active");
				$.each(labels_dl,function(i,data){
					$("#labelList_dl li").each(function(){
						if($(this).text().indexOf(data) >= 0){
							$(this).addClass("active");
						}
					});
				})
			}
		}
	});
}

/**
 * 获取标签列表
 * @param id
 * @returns
 */
function getLabels() {
	$.ajax({
		url : '/manager/label/list',
		data : {},
		type : 'post',
		success:function(data){
			if(data.success){
				$("#labelList_dl").html('');
				var str = "";
				$.each(data.data,function(i,datas){
					str += "<li value=" + datas.id + ">" + datas.labelName + "</li>";
				});
				$("#labelList_dl").append(str);
 			}
		}
	});
}

function updateWorkInfo(){
	var id = $("#id_dl").val();
	var musicName = $("#musicName_dl").val();
	if(musicName == ""){
		art.dialog({icon:'error',time:1,content:'请输入曲目名'});
		$("#musicName").focus();
		return false;
	}
	var textBook = $("#textBook_dl").val();
	if(textBook == ""){
		art.dialog({icon:'error',time:1,content:'请输入所属教材名'});
		$("#textBook").focus();
		return false;
	}
	var pressVersion = $("#pressVersion_dl").val();
	if(pressVersion == ""){
		art.dialog({icon:'error',time:1,content:'请输入出版社及版本'});
		$("#pressVersion").focus();
		return false;
	}
	var pageNum = $("#pageNum_dl").val();
	if(pageNum == ""){
		art.dialog({icon:'error',time:1,content:'请输入页码'});
		$("#pageNum").focus();
		return false;
	}
	var labels = $("#labels_dl").val();
	if(labels == ""){
		art.dialog({icon:'error',time:1,content:'请选择标签'});
		$("#labels").focus();
		return false;
	}
	var otherInfo = $("#otherInfo_dl").val();
	$.ajax({
		url : '/manager/fileInfo/update',
		type : 'post',
		data : {
			id : id,
			musicName : musicName,
			textBook : textBook,
			pressVersion : pressVersion,
			pageNum : pageNum,
			labels : labels,
			labelIds : labelIds_dl.join(","),
			otherInfo : otherInfo
		},
		success : function(data){
			if (data.success) {
				$("#coverModifyWorkDetail2").hide();
				$("#gridTable").dataTable().fnDraw(false);
				setTimeout(function(){
					uploadFile(data.data,2);//上传文件
				},2000);
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
 * 过滤JS数组中的空值，返回新的数组 
 * @param array 需要过滤的数组 
 * @returns {Array} [] 
 */  
function clear_arr_trim(array) {  
    for(var i = 0 ;i<array.length;i++)  
    {  
        if(array[i] == "" || typeof(array[i]) == "undefined")  
        {  
            array.splice(i,1);  
            i= i-1;  
        }  
    }  
    return array;  
}