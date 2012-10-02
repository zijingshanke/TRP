<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<html>
	<head>
		<title>后返政策详细信息</title>
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
									<c:param name="title1" value="后返政策管理" />
									<c:param name="title2" value="查看后返政策信息" />																						
						</c:import>
							<hr>
							<table width="100%" cellpadding="0" cellspacing="0" border="0"
								class="dataList">
								<tr>
									<td class="lef">
										航班号
									</td>
									<td style="text-align: left">
										<c:out value="${policyAfter.flightCode}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										不适用航班
									</td>
									<td style="text-align: left">
										<c:out value="${policyAfter.flightCodeExcept}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										航段
									</td>
									<td style="text-align: left">
										<c:out value="${policyAfter.startEnd}" />
									</td>
								</tr>	
								<tr>
									<td class="lef">
										舱位
									</td>
									<td style="text-align: left">
										<c:out value="${policyAfter.flightClass}" />
									</td>
								</tr>							
								<tr>
									<td class="lef">
										不适用舱位
									</td>
									<td style="text-align: left">
										<c:out value="${policyAfter.flightClassExcept}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										折扣
									</td>
									<td style="text-align: left">
										<c:out value="${policyAfter.discount}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										后返佣金
									</td>
									<td style="text-align: left">
										<c:out value="${policyAfter.rate}" />%
									</td>
								</tr>
								<tr>
									<td class="lef">
										行程类型
									</td>
									<td style="text-align: left">
										<c:out value="${policyAfter.travelTypeInfo}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										客票类型
									</td>
									<td style="text-align: left">
										<c:out value="${policyAfter.ticketType}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										任务额度
									</td>
									<td style="text-align: left">
										<c:out value="${policyAfter.quota}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										备注
									</td>
									<td style="text-align: left">
										<c:out value="${policyAfter.memo}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										操作人
									</td>
									<td style="text-align: left">
										<c:out value="${policyAfter.userName}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										操作时间
									</td>
									<td style="text-align: left">
										<c:out value="${policyAfter.updateDate}" />
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
