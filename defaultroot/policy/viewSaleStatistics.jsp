<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<% String path = request.getContextPath(); %>
<html>
<head>
	<title>订单后返详细信息</title>
	<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
	<link href="../_css/global.css" rel="stylesheet" type="text/css" />
	<script src="../_js/common.js" type="text/javascript"></script>
	<script src="../_js/progressBar.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=path%>/_js/jquery-1.3.2.min.js"></script>
	<style type="text/css">
		.progressBar {
			width: 200px;
			height: 20px;
			border: solid 1px #B3B3DC;
			position: relative;
		}
	</style>
<script>
function statisticsTicketNum()
{
  if(confirm("您要重新计算票数吗？这大约要花费几分钟，会占用系统资源，尽量少用。请等候..."))
  {
    document.forms[0].labelTicketNum.disabled=true;
    var url="saleStatistics.do?thisAction=statisticsTicketNum&id="+document.forms[0].id.value;
    openWindow(400,340,url); 
  }

}

function statisticsSalwAmount()
{
  if(confirm("您要重新计算销售量吗？这大约要花费几分钟，会占用系统资源，尽量少用。请等候..."))
  {
    document.forms[0].label2.disabled=true;
    var url="saleStatistics.do?thisAction=statisticsSaleAmount&id="+document.forms[0].id.value;
    openWindow(400,340,url); 
  }

}


function statisticsIndicators(){
	var indicatorStatisticsSize = <c:out value="${indicatorStatisticsSize}" />;
	if(indicatorStatisticsSize == 0){
		alert("请先在后返政策列表里设定指标计算政策");
		return;
	}
	if(confirm("您要重新计算指标完成度吗？这大约要花费几分钟，会占用系统资源，尽量少用。请等候...")){
	 	document.forms[0].thisAction.value="computeIndicator";
	    //var url="saleStatistics.do?thisAction=computeIndicator&id="+document.forms[0].id.value;
	   // openWindow(400,340,url); 
	    document.forms[0].submit();
	    sendRequest();
    }
}

function statisticsProfitAfter()
{
  if(confirm("您要重新计算后返吗？这大约要花费几分钟，会占用系统资源，尽量少用。请等候..."))
  {
    document.forms[0].label3.disabled=true;
    var url="saleStatistics.do?thisAction=statisticsProfitAfter&id="+document.forms[0].id.value;
    openWindow(400,340,url); 
  }

}

function statisticsAllProfitAfter()
{
	var policyAfterSize = <c:out value="${policyAfterSize}" />;
	if(policyAfterSize == 0){
		alert("请先在后返政策列表里设定后返计算政策");
		return;
	}
  if(confirm("您要重新执行后返吗？这大约要花费几分钟，会占用系统资源，尽量少用。请等候..."))
  {
    document.forms[0].label4.disabled=true;
    document.forms[0].thisAction.value="statisticsAllProfitAfter";
    if(document.forms[0].label4.value == "忽略任务执行后返"){
     	document.forms[0].quotaByStatistics.value=0;
    }else{
   		document.forms[0].quotaByStatistics.value=1;
    }
    document.forms[0].submit();
    sendRequest();
  }

}

function listStatisticsOrder(){
   var url="<%=path%>/airticket/listStatisticsOrder.do?saleStatisticsId="+document.forms[0].id.value;
   document.forms[0].action=url;
   document.forms[0].thisAction.value="listStatisticsOrder";
   document.forms[0].submit();
}
function setButtonValue(){
	var quot1 = <c:out value="${saleStatistics.airlinePolicyAfter.quota}" />;
	var quot2 =  <c:out value="${saleStatistics.saleAmount}" />;
	var executeButton = document.forms[0].label4;
	if(quot1>quot2){
		executeButton.value = "忽略任务执行后返";
	}else{
		executeButton.value = "执行后返";
	}
}

function updateHighClassAward(){
	var highClassAward = document.forms[0].highClassAward;
	highClassAward.disabled = false;
	var hcaButton = document.getElementById("hcaButton");
	if( hcaButton.value == "点击提交" ){
		var highClassAward =  document.forms[0].highClassAward.value;
		if(!isMoney(highClassAward) && !isInteger(highClassAward)){
			alert("高舱奖励只能是金额格式");
			return;
		}
		hcaButton.value = "点击修改";
	    document.forms[0].thisAction.value="updateHighClassAward";
	    document.forms[0].submit();
	}
	hcaButton.value = "点击提交";
}

//检查是否金额
function isMoney( s ){ 
	var regu = "^[0-9]+[\.][0-9]{0,}$"; 
	var re = new RegExp(regu); 
	if (re.test(s)) { 
		return true; 
	} else { 
		return false; 
	} 
}
//检查是否整数
function isInteger( str ){ 
	var regu = /^[0-9]{1,}$/; 
	return regu.test(str); 
} 

</script>
<script type="text/javascript"> 
	function sendRequest(){
		jQuery.get("<%=path%>/policy/saleStatistics.do?thisAction=showProgressBar",
					function(backJson){
						var backInf = eval("("+backJson+")");
						var totalCount = backInf["total"];
						var currentCount = backInf["current"];
						backHandle(totalCount,currentCount);
					},
					"json");
	}
	function backHandle(total,current){
		var totalCount =  parseInt(total);
		var currentCount = parseInt(current);
		var comPercent = $("#compPercent");
		var display = $("#display");
		if(totalCount == 0){
			comple();
		}else{
			display.show();
			comPercent.show();
			comPercent.text("已完成："+(currentCount/totalCount*100).toFixed(2)+"%");
			showProgress("display",totalCount,currentCount);
			if(currentCount < totalCount){
				setTimeout("sendRequest()",500);//定时调用
			}else{
				comple();
			}
		}
	}
	
	function comple(){
		jQuery.get("<%=path%>/policy/saleStatistics.do?thisAction=comple");
	}
		</script>
</head>
<body onload="showProgressPercent('progressBar',<c:out value='${saleStatistics.saleAmountPercent}' />);setButtonValue();">
	<div id="mainContainer">
		<div id="container">
			<html:form action="/policy/saleStatistics.do">
				<html:hidden property="id" name="saleStatistics" />
				<html:hidden property="thisAction" name="saleStatistics" />
				<html:hidden property="quotaByStatistics" name="saleStatistics" />
				<table width="100%" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td width="10" height="10" class="tblt"></td>
						<td height="10" class="tbtt"></td>
						<td width="10" height="10" class="tbrt"></td>
					</tr>
					<tr>
						<td width="10" class="tbll"></td>
						<td valign="top" class="body">
						<c:import url="../_jsp/mainTitle.jsp" charEncoding="UTF-8">
									<c:param name="title1" value="订单后返管理" />
									<c:param name="title2" value="查看订单后返信息" />																						
						</c:import>
							<hr>
							<table width="100%" cellpadding="0" cellspacing="0" border="0"
								class="dataList">
								<tr>
									<td class="lef">
										航空公司
									</td>
									<td style="text-align: left">
										<c:out value="${saleStatistics.carrier}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										政策
									</td>
									<td style="text-align: left">
										<c:out value="${saleStatistics.airlinePolicyAfter.name}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										开始时间
									</td>
									<td style="text-align: left">
										<c:out value="${saleStatistics.beginDate}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										结束时间
									</td>
									<td style="text-align: left">
										<c:out value="${saleStatistics.endDate}" />
									</td>
								</tr>	
								<tr>
									<td class="lef"> 
										任务指标 
									</td>
									<td style="text-align: left">
										<c:out value="${saleStatistics.airlinePolicyAfter.quota}" />&nbsp;&nbsp;元
									</td>
								</tr>
								<tr>
									<td class="lef">完成任务指标	<br></td>
									<td style="text-align: left">							    
										已完成任务量：<c:out value="${saleStatistics.saleAmount}" />&nbsp;&nbsp;元 <br>
										进度：<span id="progressBar" class="progressBar"></span> &nbsp;<span><c:out value="${saleStatistics.saleAmountPercent}" />%</span><br>
										
									</td>
								</tr>
								<tr>
									<td class="lef"> 
										高舱票数指标 
									</td>
									<td style="text-align: left">
										<c:out value="${saleStatistics.airlinePolicyAfter.highClassQuota}" />&nbsp;&nbsp;张
									</td>
								</tr>
								<tr>
									<td class="lef">完成高舱票数指标	<br></td>
									<td style="text-align: left">							    
										<c:out value="${saleStatistics.highClassTicketNum}" />&nbsp;&nbsp;张<br>
									</td>
								</tr>
								<tr>
									<td class="lef">高舱票超过指标奖励</td>
									<td style="text-align:left">
										<html:text property="highClassAward" name="saleStatistics" style="width:40px" disabled="true" />
										元/张 &nbsp; &nbsp; <input id="hcaButton" type="button" class="button2" value="点击修改" onclick="updateHighClassAward();">
									</td>
								</tr>
								<tr>
									<td class="lef">
										正常利润
									</td>
									<td style="text-align: left">
									<c:out value="${saleStatistics.profit}" />&nbsp;&nbsp;元<br>
									</td>
								</tr>
								<tr>
									<td class="lef">
										后返佣金
									</td>
									<td style="text-align: left">
									<c:out value="${saleStatistics.profitAfter}" />&nbsp;&nbsp;元
									</td>
								</tr>
								<tr>
									<td class="lef">
										后返总金额
									</td>
									<td style="text-align: left">
									<c:out value="${saleStatistics.afterAmount}" />&nbsp;&nbsp;元
									</td>
								</tr>
								<tr>
									<td class="lef">票数<br></td>
									<td style="text-align: left">							    
										<c:out value="${saleStatistics.ticketNum}" />&nbsp;&nbsp;张<br>
									</td>
								</tr>
								<tr>
									<td class="lef">
										状态
									</td>
									<td style="text-align: left">
										<c:out value="${saleStatistics.statusInfo}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										计算
									</td>
									<td style="text-align: left">
										<input name="label2" type="button" class="button3" value="计算指标完成情况" onclick="statisticsIndicators();">
										<c:check code="sc03"><input name="label4" type="button" class="button3" value="执行后返" onclick="statisticsAllProfitAfter();"></c:check>
										<input name="label3" type="button" class="button2" value="计算后返" onclick="statisticsProfitAfter();">
										<input name="labelTicketNum" type="button" class="button2" value="计算票数" onclick="statisticsTicketNum();">
										
									</td>
								</tr>
	
							</table>
							<table width="1043" style="margin-top: 5px;" height="34">
								<tr>
									<td>
										<input name="label" type="button" class="button1" value="返 回"
											onclick="window.history.back();">
											<c:check code="sc04">
										
										<input name="labeList" type="button" class="button2"  value="后返报表列表"
											onclick="listStatisticsOrder();"></c:check>
									</td>
								</tr>
							</table>
							<div class="clear"></div>
	
						</td>
						<td width="10" class="tbrr"></td>
					</tr>
					<tr>
						<td width="10" class="tblb"></td>
						<td class="tbbb"></td>
						<td width="10" class="tbrb"></td>
					</tr>
				</table>
			</html:form>
			<table align="center">
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align="left">
						<span id="display" class="progressBar" style="display:none;"></span>
					</td>
				</tr>
				<tr>
					<td align="center"><span id="compPercent"></span></td>
				</tr>
			</table>
		</div>
	</div>
		
</body>
</html>
