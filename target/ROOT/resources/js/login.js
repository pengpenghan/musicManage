$(function(){
	//登录url
	var LOGIN_URL = "/manager/login";
	
	$('#loginBtn').bind('click',function(){
 
		var username = $("#username").val();
        var password = $("#password").val(); 
        
		if(username == ''){
			$("#username").focus();
			art.dialog({icon: 'warning',time:1,content: "用户名不能为空"}); 
			//$("#errorInfo").text("错误提示:用户名不能为空");
			return false;
		}
	 
		if(password == ''){
			$("#password").focus();
			art.dialog({icon: 'warning',time:1,content: "密码不能为空"}); 
			//$("#errorInfo").text("错误提示:密码不能为空");
			return false;
		}
	    var userRole = $("#user_role").val();
		$.post(LOGIN_URL,{
			username: username,
			password: password,//对数据加密  
			userRole:userRole
		},function(data){
				if(!data.success){
					//登录失败 TODO
					art.dialog({icon: 'warning',time:2,content: data.msg}); 
					//$("#errorInfo").text("错误提示:"+data.msg);
					
				}else{
					//登录成功 TODO
					$.cookie("rmbUser", "true", { expires: 7 }); // 存储一个带7天期限的 cookie
			        $.cookie("username", username, { expires: 7 }); // 存储一个带7天期限的 cookie
			        $.cookie("password", password, { expires: 7 }); // 存储一个带7天期限的 cookie
			        $.cookie("userRole", userRole, { expires: 7 }); // 存储一个带7天期限的 cookie
					/*if(ischeck == 'on'){
						 
				        $.cookie("rmbUser", "true", { expires: 7 }); // 存储一个带7天期限的 cookie
				        $.cookie("username", username, { expires: 7 }); // 存储一个带7天期限的 cookie
				        $.cookie("password", password, { expires: 7 }); // 存储一个带7天期限的 cookie
				 
					}else {
				        $.cookie("rmbUser", "false", { expires: -1 });        // 删除 cookie
				        $.cookie("username", '', { expires: -1 });
				        $.cookie("password", '', { expires: -1 });
				    }*/
		 
			        window.location.href=data.data;
				}
		 
		},'json');
	});
});

//获取input输入框的值
function getInputVal($elem){
	return $.trim($elem.val());
}

//判断必填项是否已经全部填写
function isRequiredFilled(){
	var username = getInputVal($('#username')),
		password = getInputVal($('#password'));
	
	return username != '' && password != '' && username !='用户名' && password !='用户密码';
}

//回车键登陆
$(document).keyup(function(event){
	if(event.keyCode == 13){
		$('#loginBtn').click();
	}
});

//base64加密
var keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="; 
function encode64(input) {  
    var output = "";  
    var chr1, chr2, chr3 = "";  
    var enc1, enc2, enc3, enc4 = "";  
    var i = 0;  
    do {  
        chr1 = input.charCodeAt(i++);  
        chr2 = input.charCodeAt(i++);  
        chr3 = input.charCodeAt(i++);  
        enc1 = chr1 >> 2;  
        enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);  
        enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);  
        enc4 = chr3 & 63;  
        if (isNaN(chr2)) {  
            enc3 = enc4 = 64;  
        } else if (isNaN(chr3)) {  
            enc4 = 64;  
        }  
        output = output + keyStr.charAt(enc1) + keyStr.charAt(enc2)  
                + keyStr.charAt(enc3) + keyStr.charAt(enc4);  
        chr1 = chr2 = chr3 = "";  
        enc1 = enc2 = enc3 = enc4 = "";  
    } while (i < input.length);  

    return output;  
}  

