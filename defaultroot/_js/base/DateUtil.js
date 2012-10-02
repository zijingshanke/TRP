//////////////////////////////////获取系统时间///////////////////////////////////
	
	function showLocale(objD)
   {
	var str,colorhead,colorfoot;
	var yy = objD.getYear();
	if(yy<1900) yy = yy+1900;
	var MM = objD.getMonth()+1;
	if(MM<10) MM = '0' + MM;
	var dd = objD.getDate();
	if(dd<10) dd = '0' + dd;
	var hh = objD.getHours();
	if(hh<10) hh = '0' + hh;
	var mm = objD.getMinutes();
	if(mm<10) mm = '0' + mm;
	var ss = objD.getSeconds();
	if(ss<10) ss = '0' + ss;
	
	var ww = objD.getDay();
	if  ( ww==0 )  colorhead="<font color=\"#FF0000\">";
	if  ( ww > 0 && ww < 6 )  colorhead="<font color=\"#373737\">";
	if  ( ww==6 )  colorhead="<font color=\"#008000\">";
	if  (ww==0)  ww="星期日";
	if  (ww==1)  ww="星期一";
	if  (ww==2)  ww="星期二";
	if  (ww==3)  ww="星期三";
	if  (ww==4)  ww="星期四";
	if  (ww==5)  ww="星期五";
	if  (ww==6)  ww="星期六";
	colorfoot="</font>"
///	str = colorhead + yy + "-" + MM + "-" + dd + " " + hh + ":" + mm + ":" + ss + "  " + ww + colorfoot;
    str = yy + "-" + MM + "-" + dd + " " + hh + ":" + mm + ":" + ss;
	return(str);
}  

//当前日期
function showLocaleDate(objDate){
	var str,colorhead,colorfoot;
	var yy = objDate.getYear();
	if(yy<1900) yy = yy+1900;
	var MM = objDate.getMonth()+1;
	if(MM<10) MM = '0' + MM;
	var dd = objDate.getDate();
	if(dd<10) dd = '0' + dd;
    str = yy + "-" + MM + "-" + dd;
	return(str);
}  

//前一天
function showLocaleYesterday(){
	var curDate=new Date();
	
	var preDate = new Date(curDate.getTime() - 24*60*60*1000);  //前一天
	//var nextDate = new Date(curDate.getTime() + 24*60*60*1000);  //后一天 	

	var str,colorhead,colorfoot;
	var yy = preDate.getYear();
	if(yy<1900) yy = yy+1900;
	var MM = preDate.getMonth()+1;
	if(MM<10) MM = '0' + MM;
	var dd = preDate.getDate();
	if(dd<10) dd = '0' + dd;
    str = yy + "-" + MM + "-" + dd;
	return(str);
}









