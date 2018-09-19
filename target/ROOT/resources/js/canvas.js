//runLine(857.5,371,1050,0,1918,724,"#6ab112",1);
//runLine(939.5,554,1100,390,1790,700,"#6ab112",2);
//runLine(1151.5,786,1186,562,1870,816,"#6ab112",3);
//runLine(1205.5,350,1700,208,1842,662,"#6ab112",4);
//runLine(1181.5,628,1554,463,1850,908,"#6ab112",5);
//runLine(1349.5,624,1570,463,1860,700,"#6ab112",6);
//runLine(1427.5,768,1622,682,1848,942,"#6ab112",7);
//runLine(1527.5,424,1688,208,1880,708,"#6ab112",8);
//runLine(1661.5,444,1796,370,1820,764,"#6ab112",9);
//runLine(1733.5,256,1962,258,1894,820,"#ff0000",10);
var t = 0;
var deg;
function runLine(x0,y0,x1,y1,x2,y2,color,id){

	var c=document.getElementById("myCanvas");
	var ctx=c.getContext("2d");
	ctx.beginPath();
	ctx.strokeStyle = color;
	ctx.lineWidth = 4;
	ctx.moveTo(x0,y0);
	ctx.quadraticCurveTo(x1,y1,x2,y2);
	ctx.stroke();
	
	
	switch(id){
		case 1:
			setInterval(function(){
				move1(x0,y0,x1,y1,x2,y2);
			},10);
		break;
		case 2:
			setInterval(function(){
				move2(x0,y0,x1,y1,x2,y2);
			},10);
		break;
		case 3:
			setInterval(function(){
				move3(x0,y0,x1,y1,x2,y2);
			},10);
		break;
		case 4:
			setInterval(function(){
				move4(x0,y0,x1,y1,x2,y2);
			},10);
		break;
		case 5:
			setInterval(function(){
				move5(x0,y0,x1,y1,x2,y2);
			},10);
		break;
		case 6:
			setInterval(function(){
				move6(x0,y0,x1,y1,x2,y2);
			},10);
		break;
		case 7:
			setInterval(function(){
				move7(x0,y0,x1,y1,x2,y2);
			},10);
		break;
		case 8:
			setInterval(function(){
				move8(x0,y0,x1,y1,x2,y2);
			},300);
		break;
		case 9:
			setInterval(function(){
				move9(x0,y0,x1,y1,x2,y2);
			},10);
		break;
		case 10:
			setInterval(function(){
				move10(x0,y0,x1,y1,x2,y2);
			},10);
		break;
	}
}

function move1(x0,y0,x1,y1,x2,y2){
	t+= 0.001;
	if(t>1){
		t =0;
	}
	var x = Math.pow(1-t,2)*(x0-10.5) + 2*t*(1-t)*(x1-10.5) + Math.pow(t,2)*(x2-10.5)
	var y = Math.pow(1-t,2)*(y0-56) + 2*t*(1-t)*(y1-56) + Math.pow(t,2)*(y2-56)
	
	$("#one").get(0).style.left = x + "px";
	$("#one").get(0).style.top = y + "px";
	
	deg = (y0-y)/(x-x0) 
	
	var result = 90- (Math.atan(deg)*180/Math.PI)+30;
	$("#one").css("transform","rotate("+result+"deg)");
}
function move2(x0,y0,x1,y1,x2,y2){
	t+= 0.005;
	if(t>1){
		t =0;
	}
	var x = Math.pow(1-t,2)*(x0-10.5) + 2*t*(1-t)*(x1-10.5) + Math.pow(t,2)*(x2-10.5)
	var y = Math.pow(1-t,2)*(y0-56) + 2*t*(1-t)*(y1-56) + Math.pow(t,2)*(y2-56)
	
	$("#two").get(0).style.left = x + "px";
	$("#two").get(0).style.top = y + "px";
	
	deg = (y0-y)/(x-x0); 
	
	var result = 90- (Math.atan(deg)*180/Math.PI)+30;
	$("#two").css("transform","rotate("+result+"deg)");
}
function move3(x0,y0,x1,y1,x2,y2){
	t+= 0.005;
	if(t>1){
		t =0;
	}
	var x = Math.pow(1-t,2)*(x0-10.5) + 2*t*(1-t)*(x1-10.5) + Math.pow(t,2)*(x2-10.5)
	var y = Math.pow(1-t,2)*(y0-56) + 2*t*(1-t)*(y1-56) + Math.pow(t,2)*(y2-56)
	
	$("#three").get(0).style.left = x + "px";
	$("#three").get(0).style.top = y + "px";
	
	deg = (y0-y)/(x-x0); 
	
	var result = 90- (Math.atan(deg)*180/Math.PI)+30;
	$("#three").css("transform","rotate("+result+"deg)");
}
function move4(x0,y0,x1,y1,x2,y2){
	t+= 0.005;
	if(t>1){
		t =0;
	}
	var x = Math.pow(1-t,2)*(x0-10.5) + 2*t*(1-t)*(x1-10.5) + Math.pow(t,2)*(x2-10.5)
	var y = Math.pow(1-t,2)*(y0-56) + 2*t*(1-t)*(y1-56) + Math.pow(t,2)*(y2-56)
	
	$("#four").get(0).style.left = x + "px";
	$("#four").get(0).style.top = y + "px";
	
	deg = (y0-y)/(x-x0); 
	
	var result = 90- (Math.atan(deg)*180/Math.PI)+30;
	$("#four").css("transform","rotate("+result+"deg)");
}
function move5(x0,y0,x1,y1,x2,y2){
	t+= 0.005;
	if(t>1){
		t =0;
	}
	var x = Math.pow(1-t,2)*(x0-10.5) + 2*t*(1-t)*(x1-10.5) + Math.pow(t,2)*(x2-10.5)
	var y = Math.pow(1-t,2)*(y0-56) + 2*t*(1-t)*(y1-56) + Math.pow(t,2)*(y2-56)
	
	$("#five").get(0).style.left = x + "px";
	$("#five").get(0).style.top = y + "px";
	
	deg = (y0-y)/(x-x0); 
	
	var result = 90- (Math.atan(deg)*180/Math.PI)+30;
	$("#five").css("transform","rotate("+result+"deg)");
}
function move6(x0,y0,x1,y1,x2,y2){
	t+= 0.005;
	if(t>1){
		t =0;
	}
	var x = Math.pow(1-t,2)*(x0-10.5) + 2*t*(1-t)*(x1-10.5) + Math.pow(t,2)*(x2-10.5)
	var y = Math.pow(1-t,2)*(y0-56) + 2*t*(1-t)*(y1-56) + Math.pow(t,2)*(y2-56)
	
	$("#six").get(0).style.left = x + "px";
	$("#six").get(0).style.top = y + "px";
	
	deg = (y0-y)/(x-x0); 
	
	var result = 90- (Math.atan(deg)*180/Math.PI)+30;
	$("#six").css("transform","rotate("+result+"deg)");
}
function move7(x0,y0,x1,y1,x2,y2){
	t+= 0.005;
	if(t>1){
		t =0;
	}
	var x = Math.pow(1-t,2)*(x0-10.5) + 2*t*(1-t)*(x1-10.5) + Math.pow(t,2)*(x2-10.5)
	var y = Math.pow(1-t,2)*(y0-56) + 2*t*(1-t)*(y1-56) + Math.pow(t,2)*(y2-56)
	
	$("#seven").get(0).style.left = x + "px";
	$("#seven").get(0).style.top = y + "px";
	
	deg = (y0-y)/(x-x0); 
	
	var result = 90- (Math.atan(deg)*180/Math.PI)+30;
	$("#seven").css("transform","rotate("+result+"deg)");
}
function move8(x0,y0,x1,y1,x2,y2){
	t+= 0.005;
	if(t>1){
		t =0;
	}
	var x = Math.pow(1-t,2)*(x0-10.5) + 2*t*(1-t)*(x1-10.5) + Math.pow(t,2)*(x2-10.5)
	var y = Math.pow(1-t,2)*(y0-56) + 2*t*(1-t)*(y1-56) + Math.pow(t,2)*(y2-56)
	
	$("#eight").get(0).style.left = x + "px";
	$("#eight").get(0).style.top = y + "px";
	
	deg = (y0-y)/(x-x0); 
	
	var result = 90- (Math.atan(deg)*180/Math.PI)+30;
	$("#eight").css("transform","rotate("+result+"deg)");
}
function move9(x0,y0,x1,y1,x2,y2){
	t+= 0.005;
	if(t>1){
		t =0;
	}
	var x = Math.pow(1-t,2)*(x0-10.5) + 2*t*(1-t)*(x1-10.5) + Math.pow(t,2)*(x2-10.5)
	var y = Math.pow(1-t,2)*(y0-56) + 2*t*(1-t)*(y1-56) + Math.pow(t,2)*(y2-56)
	
	$("#nine").get(0).style.left = x + "px";
	$("#nine").get(0).style.top = y + "px";
	
	deg = (y0-y)/(x-x0); 
	
	var result = 90- (Math.atan(deg)*180/Math.PI)+30;
	$("#nine").css("transform","rotate("+result+"deg)");
}
function move10(x0,y0,x1,y1,x2,y2){
	t+= 0.005;
	if(t>1){
		t =0;
	}
	var x = Math.pow(1-t,2)*(x0-10.5) + 2*t*(1-t)*(x1-10.5) + Math.pow(t,2)*(x2-10.5)
	var y = Math.pow(1-t,2)*(y0-56) + 2*t*(1-t)*(y1-56) + Math.pow(t,2)*(y2-56)
	
	$("#ten").get(0).style.left = x + "px";
	$("#ten").get(0).style.top = y + "px";
	
	deg = (y0-y)/(x-x0); 
	
	var result = 90- (Math.atan(deg)*180/Math.PI)+30;
	$("#ten").css("transform","rotate("+result+"deg)");
}



