var params = new Array();
var num = 0;
$(".checkBox li").click(function(){
    $(this).addClass("active").siblings().removeClass("active");
    $("#user_role").attr("value",$(this).attr("value"));
});
$(".labelListBox").on("click",".selected,.name",function(){
    $(this).parent().find(".selected").toggleClass("active");
    var param = $(this).parent().text();
    var search_param = $("#search_param").val();
    if (search_param != "" && num == 0) {
    	params.push(search_param);
	}
    if($.inArray(param,params) != -1){
    	removeByValue(params,param);
    }else {
    	params.push(param);
	}
    num = num + 1;
    $("#search_param").val(params.join(" "));
});
function removeByValue(arr, val) {
    for(var i=0; i<arr.length; i++) {
		if (arr[i] == val) {
			arr.splice(i, 1);
			break;
	    }
    }
}
$("#addLabel").click(function(){
    $("#labelCover").show();
});
$(".close,.cancel").click(function(){
    $(this).parents(".cover").hide();
})
$("#addFile").click(function(){
    $("#fileCover").show();
});
/*一键全选效果*/
$(".selectAll").click(function(){
    if($(this).hasClass("active")){
        $(this).removeClass("active");
        $(".musicName").removeClass("active");
    }else{
        $(this).addClass("active");
        $(".musicName").addClass("active");
    }
})
$(".tableWrapper").on("click",".musicName", function() {
    $(this).toggleClass("active");
})
/*新增员工*/
$(".addStaff").click(function(){
    $("#staffCover").show();
})
/*修改密码*/
$(".tableWrapper").on("click",".changePsw", function() {
    $("#changePsw").show();
    $("#newPsd").val($(this).siblings("span").text())
    $("#name").text($(this).parents("tr").find("td:first-child").text())
    $("#userName").val($(this).parents("tr").find("td:first-child").text());
})
/*工作详情页-备注修改*/
$(".tableWrapper").on("click",".modify", function() {
    $("#modifyTips").show();
    $("#modifyTips dd").removeClass("active");
})
$("#modifyTips").on("click","dd", function() {
    $(this).toggleClass("active");
})
/*工作详情修改弹窗点击效果*/
$(".labelList").on("click","li", function() {
    $(this).toggleClass("active");
})
$("#gridTable").on("click",".modifyWorkDetail", function() {
    $("#coverModifyWorkDetail").show();
    drag($(".addLabelBox"));
})
/*员工界面点击上传*/
$(".download").on("click", function() {
    $("#coverModifyWorkDetail1").show();
    drag($(".addLabelBox"));
})
/*员工界面-所属教材名下拉效果*/
$("#textBook").click(function(e){
	$(".zxl").hide();
	$(this).next("ul").show();
	e.stopPropagation();
})
$("#textBook_dl").click(function(e){
	$(".zxl").hide();
	$(this).next("ul").show();
	e.stopPropagation();
})
$("#pressVersion").click(function(e){
	$(".zxl").hide();
	$(this).next("ul").show();
	e.stopPropagation();
})
$(document).click(function(){
	$(".zxl").hide();
})
$("#pressVersion_dl").click(function(e){
	$(".zxl").hide();
	$(this).next("ul").show();
	e.stopPropagation();
})
$(".zxl").on("click","li",function(){
	$(this).parent().hide();
	$(this).parent().prev().val($(this).text());
})
/**
 * 显示文件名称
 * @returns
 */
function showFileName(){
	var fileName = $("#file").val();
	document.getElementById("fileShow").value = fileName.substring(fileName.lastIndexOf("\\") + 1);
}

/**
 * 显示文件名称
 * @returns
 */
function showFileNameDl(){
	var fileName = $("#file_dl").val();
	document.getElementById("fileShow_dl").value = fileName.substring(fileName.lastIndexOf("\\") + 1);
}