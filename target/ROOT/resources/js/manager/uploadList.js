var labels = new Array();//选中标签
var labelIds = new Array();//选中标签id
var num = 0;//选中数

var labels_dl = new Array();//选中标签
var labelIds_dl = new Array();//选中标签id

$(function(){
	fileInfoList();
	getLabels();//获取标签列表
	$("#labelList").on("click","li",function(){
		var labelId = $(this).attr("value");
		var labelName = $(this).text();
		if($.inArray(labelName,labels) != -1){
			removeByValue(labels,labelName);
			removeByValue(labelIds,labelId);
		}else{
			labels.push(labelName);
			labelIds.push(labelId);
		}
		$("#labels").val(labels.join(","));
	});
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
	//列表点击设置
	var tabel = $("#gridTable").DataTable();
	$("#gridTable tbody").on("click","tr",function(){
		$("#coverModifyWorkDetail2").show();
		drag($(".addLabelBox"));
		getTableInfo(tabel.row(this).data());
	});
});

//删除数值中已有标签
function removeByValue(arr, val) {
	for(var i=0; i<arr.length; i++) {
		if (arr[i] == val) {
			arr.splice(i, 1);
			break;
		}
	}
}

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
				type : 0 //取无标记 即全部的数据
			}
		}, 
		/**
		 * 设定某一列的宽度
		 */
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
					var r = "-";
					if (null == row.remark
							|| "" == row.remark) {
						r = "-";
					} else {
						r = row.remark;
					}
					return r;
				},
			},
			{
				render : function(data, type, row, meta) {
					var r = "";
					r = "<ul id=\"uploadList"+row.id+"\"></ul>";
					if(null == row.fileName
							|| "" == row.fileName){
						r += "";
					}else{
						r += "100%";
					}
					return r;
				},
			}
			/*{
				render : function(data, type, row, meta) {
					var str = "<i class=\"modify\" onclick=\"getFileId("+row.id+");\"></i>";
					return str;
				},
			}*/
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

/**
 * 获取列表点击框的详情
 */
function getTableInfo(obj){
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

function getFileId(id) {
	$("#file_id").val(id);
	$.ajax({
		url : '/manager/label/type',
		data : {
			id : id
		},
		type : 'post',
		success:function(data){
			$.each(data.infoLabels,function(i,datas){
				$("#label_"+datas.labelListId).attr("class","active");
			});
			$.each(data.infoTypes,function(i,datas){
				$("#type_"+datas.fileTypeId).attr("class","active");
			})
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
				$("#labelList").html('');
				$("#labelList_dl").html('');
				var str = "";
				$.each(data.data,function(i,datas){
					str += "<li value=" + datas.id + ">" + datas.labelName + "</li>";
				});
				$("#labelList").append(str);
				$("#labelList_dl").append(str);
 			}
		}
	});
}

function addWorkInfo(){
	var musicName = $("#musicName").val();
	if(musicName == ""){
		art.dialog({icon:'error',time:1,content:'请输入曲目名'});
		$("#musicName").focus();
		return false;
	}
	var textBook = $("#textBook").val();
	if(textBook == ""){
		art.dialog({icon:'error',time:1,content:'请输入所属教材名'});
		$("#textBook").focus();
		return false;
	}
	var pressVersion = $("#pressVersion").val();
	if(pressVersion == ""){
		art.dialog({icon:'error',time:1,content:'请输入出版社及版本'});
		$("#pressVersion").focus();
		return false;
	}
	var pageNum = $("#pageNum").val();
	if(pageNum == ""){
		art.dialog({icon:'error',time:1,content:'请输入页码'});
		$("#pageNum").focus();
		return false;
	}
	var labels = $("#labels").val();
	if(labels == ""){
		art.dialog({icon:'error',time:1,content:'请选择标签'});
		$("#labels").focus();
		return false;
	}
	var otherInfo = $("#otherInfo").val();
	var file = $("#file").val();
	$.ajax({
		url : '/manager/fileInfo/add',
		type : 'post',
		data : {
			musicName : musicName,
			textBook : textBook,
			pressVersion : pressVersion,
			pageNum : pageNum,
			labels : labels,
			labelIds : labelIds.join(","),
			otherInfo : otherInfo
		},
		success : function(data){
			if (data.success) {
				$("#coverModifyWorkDetail1").hide();
				$("#gridTable").dataTable().fnDraw(false);
				setTimeout(function(){
					if(file != ""){
						uploadFile(data.data,1);//上传文件
					}
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
 * 获取标签及文件类型
 */
function addFileLabel() {
	var labelIds = new Array();
	var labelNames = new Array();//标签及文件类型数组
	var fileTypes = new Array();
	$("#labelChecke dd").each(function(){
		var flag = $(this).hasClass("active");
		if(flag){
			labelIds.push($(this).attr("value"));
			labelNames.push($(this).text());
		}
	});
	$("#typeChecke dd").each(function(){
		var flag = $(this).hasClass("active");
		if(flag){
			fileTypes.push($(this).attr("value"));
			labelNames.push($(this).text());
		}
	});
	if(labelIds.length == 0){
		art.dialog({
			icon : 'error',
			time : 2,
			content : '请选择标签！'
		});
		return;
	}
	if(labelIds.length == 0){
		art.dialog({
			icon : 'error',
			time : 2,
			content : '请选择类别！'
		});
		return;
	}
	var id = $("#file_id").val();
	$.ajax({
		url : '/manager/fileInfo/labelType',
		data :{
			id : id,
			labelType : labelNames.join("、"),
			labelIds : labelIds.join(","),
			fileTypes : fileTypes.join(",")
		},
		type : 'post',
		success:function(data){
			if (data.success) {
				/*art.dialog({
					icon : 'succeed',
					time : 2,
					content : data.msg
				});*/
				$(".cover").hide();
				$("#gridTable").dataTable().fnDraw(false); 
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

/*操作类型 1新增文件 2更新上传文件*/
function uploadFile(id,type) { 
	$("#id").val(id);
	var fileObj = "";// js 获取文件对象  
	var FileController = "";// 接收上传文件的后台地址   
	if(type == 1){
		fileObj = document.getElementById("file").files[0]; 
	    FileController = "/manager/uploadFile"; 
	}else if(type == 2){
		fileObj = document.getElementById("file_dl").files[0]; 
	    FileController = "/manager/updateFile"; 
	}
    
    if (fileObj.size > 50000000) {
		art.dialog({
			icon : 'error',
			time : 2,
			content: '上传文件大小不能超过50M！'
		});
		return;
	}
  
    // FormData 对象  
    var form = new FormData();  
    form.append("author", "hooyes"); // 可以增加表单数据  
    form.append("file", fileObj); // 文件对象  
    form.append("id",id);
  
    // XMLHttpRequest 对象  
    var xhr = new XMLHttpRequest();  
    xhr.open("post", FileController, true);  
    xhr.onload = function() {  
        // alert("上传完成!");  
    };  
    var name = fileObj.name;
    if (name.length > 6) {
		name = name.substring(0,6);
	}
    var html = "<li><span class=\"transportName\" id=\"fileName\" style=\"width:110px;\">"+name+"</span>";
    html += "<progress id=\"progressBar_"+id+"\" value=\"0\" max=\"100\" style=\"margin-left:18px;width: 90px;\"></progress>";
    html += "<span id=\"percentage_"+id+"\" style=\"display: inline-block;width:20px;text-align: center;\"></span></li>";
    $("#uploadList"+id).append(html);
    xhr.upload.addEventListener("progress", progressFunction, false); 
    xhr.addEventListener("load", uploadComplete, false);
    xhr.send(form);
}  
  
function clearProgressInfo()  
{  
	var id = $("#id").val();
    document.getElementById("progressBar_"+id+"").value = 0;  
    document.getElementById("percentage_"+id+"").innerHTML = "";  
}  

function progressFunction(evt) {  
	var id = $("#id").val();
    var progressBar = document.getElementById("progressBar_"+id+"");  
    var percentageDiv = document.getElementById("percentage_"+id+"");  
    if (evt.lengthComputable) {
        progressBar.max = evt.total;  
        progressBar.value = evt.loaded;  
        percentageDiv.innerHTML = Math.round(evt.loaded / evt.total * 100) + "%";  
    }  
}  

function uploadComplete(evt) {
    /* 服务器端返回响应时候触发event事件*/
	$("#gridTable").dataTable().fnDraw(false); 
    //alert(evt.target.responseText);
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