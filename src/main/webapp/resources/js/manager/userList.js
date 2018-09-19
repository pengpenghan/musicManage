$(function() {
	userList();
});

/**
 * 用户列表
 */
function userList(){
	$("#gridTable").dataTable(
	{
		ajax : {
			type : 'post',
			url : '/user/list',
			data : {
				
			}
		},  
		autoWidth : false,
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
						return row.name;
					},
				},
				{
					render : function(data, type, row, meta) {
						var str = "<span>"+row.password +"</span><i class=\"changePsw\"></i>";
						return str;
					},
				},
				{
					render : function(data, type, row, meta) {
						return row.count;
					},
				},
				{
					render : function(data, type, row, meta) {
						var str = "<a href=\"/manager/manageWorkDetail?id=" + row.id + "\" class=\"workDetail\">工作详情</a>";
						return str;
					},
				}],
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

function addUser() {
	var userName = $("#userName").val();
	if (userName == '') {

		art.dialog({
			icon : 'warning',
			time : 2,
			content : "用户名不能为空"
		});
		setTimeout(function() {
			$("#userName").focus();
		}, 2000);
		return false;
	}
	var password = $("#password").val();
	if (password == '') {

		art.dialog({
			icon : 'warning',
			time : 2,
			content : "用户登录密码不能为空"
		});
		setTimeout(function() {
			$("#password").focus();
		}, 2000);
		return false;
	}
	var user = {
		userName : userName,
		password : password
	};
	$.ajax({
		url : "/user/add",
		type : 'post',
		data : user,
		dataType : "json",
		success : function(data) {
			if (data.success) {
				$("#staffCover").hide();
				art.dialog({
					icon : 'succeed',
					time : 3,
					content : data.msg
				});
				setTimeout(function(){
					$('#gridTable').DataTable().ajax.reload(); 
				},2000);
			} else {
				art.dialog({
					icon : 'warning',
					time : 1,
					content : data.msg
				});
			}
		}
	});
}

function updateUser() {
	var newPsd = $("#newPsd").val();
	if (newPsd == '') {
		art.dialog({
			icon : 'warning',
			time : 2,
			content : "用户名不能为空"
		});
		setTimeout(function() {
			$("#newPsd").focus();
		}, 2000);
		return false;
	}
	var name = $("#userName").val();
	var user = {
		userName : name,
		password : newPsd
	};
	$.ajax({
		url : "/user/update",
		type : 'post',
		data : user,
		dataType : "json",
		success : function(data) {
			if (data.success) {
				$("#changePsw").hide();
				art.dialog({
					icon : 'succeed',
					time : 2,
					content : data.msg
				});
				setTimeout(function(){
					$('#gridTable').DataTable().ajax.reload(); 
				},2000);

			} else {
				art.dialog({
					icon : 'warning',
					time : 3,
					content : data.msg
				});
			}
		}
	});

}