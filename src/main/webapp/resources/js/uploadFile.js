//获取上传文件名称
function showFileName() {
	var fileName = $("#file").val();
	var ext = fileName.substring(fileName.lastIndexOf(".")+1);//获取后缀名称
	//将文件名称放入文本框中
	document.getElementById("fileShow").value = fileName.substring(fileName.lastIndexOf("\\")+1);
}

function uploadFile(){
	var file = $("#file").val();
	if(file == ""){
		art.dialog({icon:'error',time:1,content:'请选择上传文件'});
		$("#file").focus();
		return false;
	}
	var waitDialog = art.dialog({
		icon : 'succeed',
		content : '正在导入，请耐心等待！'
	});
	$("#uploadForm").ajaxSubmit({
		url : '/manager/uploadFile',
		type : 'post',
		dataType : 'json',
		success : function(data){
			waitDialog.close();
			if (data) {
				art.dialog({
					icon : 'succeed',
					time : 2,
					content : data.msg
				});
				setTimeout(function() {
					window.location.reload();
				},3000);
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