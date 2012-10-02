<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<html>
	<head>
		<title>指标计算政策详细信息</title>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />

<style>
.divstyle {
	padding: 5px;
	font-family: "MS Serif", "New York", serif;
	background: #e5e5e5;
}
</style>
	</head>
	<body>
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
									<c:param name="title1" value="指标计算政策管理" />
									<c:param name="title2" value="查看指标计算政策信息" />																						
						</c:import>
							<hr>
							<table width="100%" cellpadding="0" cellspacing="0" border="0"
								class="dataList">
								<tr>
									<td class="lef">
										承运人
									</td>
									<td style="text-align: left">
										<c:out value="${indicatorStatistics.carrier}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										航班号
									</td>
									<td style="text-align: left">
										<c:out value="${indicatorStatistics.flightCode}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										不适用航班
									</td>
									<td style="text-align: left">
										<c:out value="${indicatorStatistics.flightCodeExcept}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										航段
									</td>
									<td style="text-align: left">
										<c:out value="${indicatorStatistics.flightPoint}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										不适航段
									</td>
									<td style="text-align: left">
										<c:out value="${indicatorStatistics.flightPointExcept}" />
									</td>
								</tr>	
								<tr>
									<td class="lef">
										舱位
									</td>
									<td style="text-align: left">
										<c:out value="${indicatorStatistics.flightClass}" />
									</td>
								</tr>							
								<tr>
									<td class="lef">
										不适用舱位
									</td>
									<td style="text-align: left">
										<c:out value="${indicatorStatistics.flightClassExcept}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										起始日期
									</td>
									<td style="text-align: left">
										<c:out value="${indicatorStatistics.beginDate}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										结束日期
									</td>
									<td style="text-align: left">
										<c:out value="${indicatorStatistics.endDate}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										是否计量额
									</td>
									<td style="text-align: left">
										<c:out value="${indicatorStatistics.isAmountValue}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										是否计奖额
									</td>
									<td style="text-align: left">
										<c:out value="${indicatorStatistics.isAwardValue}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										是否高舱
									</td>
									<td style="text-align: left">
										<c:out value="${indicatorStatistics.isHighClassValue}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										行程类型
									</td>
									<td style="text-align: left">
										<c:out value="${indicatorStatistics.travelTypeValue}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										客票类型
									</td>
									<td style="text-align: left">
										<c:out value="${indicatorStatistics.ticketTypeValue}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										状态
									</td>
									<td style="text-align: left">
										<c:out value="${policyAfter.statusInfo}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										备注
									</td>
									<td style="text-align: left">
										<c:out value="${indicatorStatistics.remark}" />
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
				</table>
			</div>
		</div>
	</body>
</html>
