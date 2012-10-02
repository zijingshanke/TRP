<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<html>
<head>
	<title>订单后返详细信息</title>
	<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
	<link href="../_css/global.css" rel="stylesheet" type="text/css" />
	<script src="../_js/common.js" type="text/javascript"></script>
	<script src="../_js/progressBar.js" type="text/javascript"></script>
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
    document.forms[0].label1.disabled=true;
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
function statisticsProfitAfter()
{
  if(confirm("您要重新后返吗？这大约要花费几分钟，会占用系统资源，尽量少用。请等候..."))
  {
    document.forms[0].label3.disabled=true;
    var url="saleStatistics.do?thisAction=statisticsProfitAfter&id="+document.forms[0].id.value;
    openWindow(400,340,url); 
  }

}
</script>
</head>
<body onload="showProgressPercent('progressBar',<c:out value='${saleStatistics.saleAmountPercent}' />);">
	<div id="mainContainer">
		<div id="container">
		<html:form action="/policy/saleStatistics.do">
			<html:hidden property="id" name="saleStatistics" />
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
									任务量
								</td>
								<td style="text-align: left">
									<c:out value="${saleStatistics.airlinePolicyAfter.quota}" />&nbsp;&nbsp;元
								</td>
							</tr>
							<tr>
								<td class="lef">
									销售量
								</td>
								<td style="text-align: left">							    
									已销售量：<c:out value="${saleStatistics.saleAmount}" />&nbsp;&nbsp;元 <br>
									任务进度：<span id="progressBar" class="progressBar"></span> &nbsp;<span><c:out value="${saleStatistics.saleAmountPercent}" />%</span><br>
									<input name="label2" type="button" class="button2" value="计算销售量" onclick="statisticsSalwAmount();">
								</td>
							</tr>
							<tr>
								<td class="lef">
									后返佣金
								</td>
								<td style="text-align: left">
									<c:out value="${saleStatistics.afterAmount}" />&nbsp;&nbsp;元<br>
									<input name="label3" type="button" class="button1" value="计算后返" onclick="statisticsProfitAfter();">
								</td>
							</tr>
							<tr>
								<td class="lef">
									票数
								</td>
								<td style="text-align: left">
									<c:out value="${saleStatistics.ticketNum}" />&nbsp;&nbsp;张<br>
									<input name="label1" type="button" class="button1" value="计算票数" onclick="statisticsTicketNum();">
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

						</table>
						<table width="100%" style="margin-top: 5px;">
							<tr>
								<td>
									<input name="label" type="button" class="button1" value="返 回"
										onclick="window.history.back();">
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
			</table></html:form>
		</div>
	</div>
</body>
</html>
