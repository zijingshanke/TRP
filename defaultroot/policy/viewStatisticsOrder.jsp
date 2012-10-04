<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<% String path = request.getContextPath(); %>
<html>
	<head>
		<title>后返报表记录详细信息</title>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />

<style>
.divstyle {
	padding: 5px;
	font-family: "MS Serif", "New York", serif;
	background: #e5e5e5;
}
</style>
<script type="text/javascript">
	function back(){
		var saleStatisticsId = document.getElementById("saleStatisticsId").value;
		document.location.href="<%=path%>/airticket/listStatisticsOrder.do?thisAction=listStatisticsOrder&saleStatisticsId="+saleStatisticsId;
	}
</script>
</head>
	<body>
		<input id="saleStatisticsId" type="hidden" value="<c:out value="${statisticsOrder.saleStatistics.id}" />"> 
		
		<div id="mainContainer">
			<div id="container">
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
									<c:param name="title1" value="后返政策管理" />
									<c:param name="title2" value="查看后返报表信息" />																						
						</c:import>
							<hr>
							<table width="100%" cellpadding="0" cellspacing="0" border="0"
								class="dataList">
								<tr>
									<td class="lef">
										流水号
									</td>
									<td style="text-align: left">
										<c:out value="${statisticsOrder.orderNo}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										航班号
									</td>
									<td style="text-align: left">
										<c:out value="${statisticsOrder.flightCode}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										起止城市
									</td>
									<td style="text-align: left">
										<c:out value="${statisticsOrder.startEnd}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										起飞时间
									</td>
									<td style="text-align: left">
										<c:out value="${statisticsOrder.boardingTime}" />
									</td>
								</tr>	
								<tr>
									<td class="lef">
										乘客姓名
									</td>
									<td style="text-align: left">
										<c:out value="${statisticsOrder.passengerName}" />
									</td>
								</tr>	
								<tr>
									<td class="lef">
										票号
									</td>
									<td style="text-align: left">
										<c:out value="${statisticsOrder.ticketNumber}" />
									</td>
								</tr>							
								<tr>
									<td class="lef">
										订单金额
									</td>
									<td style="text-align: left">
										<c:out value="${statisticsOrder.totalAmount}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										利润
									</td>
									<td style="text-align: left">
										<c:out value="${statisticsOrder.profit}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										后返政策
									</td>
									<td style="text-align: left">
										<c:out value="${statisticsOrder.rate}" />%
									</td>
								</tr>
								<tr>
									<td class="lef">
										后返利润
									</td>
									<td style="text-align: left">
										<c:out value="${statisticsOrder.profitAfter}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										tranType
									</td>
									<td style="text-align: left">
										<c:out value="${statisticsOrder.tranType}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										orderGroupId
									</td>
									<td style="text-align: left">
										<c:out value="${statisticsOrder.groupId}" />
									</td>
								</tr>
							</table>
							<table width="100%" style="margin-top: 5px;">
								<tr>
									<td>
										<input name="label" type="button" class="button1" value="返 回"
											onclick="back();">
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
			</div>
		</div>
	</body>
</html>
