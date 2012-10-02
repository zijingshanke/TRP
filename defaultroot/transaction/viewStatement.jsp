﻿<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<html>
	<head>
		<title>main</title>
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

							<c:import url="../_jsp/mainTitle.jsp?title1=结算管理&title2=查看结算详细信息"
								charEncoding="UTF-8" />
							<hr>
							<table width="100%" cellpadding="0" cellspacing="0" border="0"
								class="dataList">
								<tr>
									<td class="lef">
										结算单号
									</td>
									<td style="text-align: left">
										<c:out value="${statement.statementNo}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										类型
									</td>
									<td style="text-align: left">
										<c:out value="${statement.orderTypeInfo}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										账号
									</td>
									<td style="text-align: left">
										<c:out value="${statement.platComAccount.account.name}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										 结算状态
									</td>
									<td style="text-align: left">
										<c:out value="${statement.statusInfo}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										实收款
									</td>
									<td style="text-align: left">
										<c:out value="${statement.actualAmount}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										未结款
									</td>
									<td style="text-align: left">
										<c:out value="${statement.unsettledAccount}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										现返佣金
									</td>
									<td style="text-align: left">
										<c:out value="${statement.commission}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										后返佣金
									</td>
									<td style="text-align: left">
										<c:out value="${statement.rakeOff}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										总金额
									</td>
									<td style="text-align: left">
										<c:out value="${statement.totalAmount}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										操作员
									</td>
									<td style="text-align: left">
										<c:out value="${statement.sysUser.userName}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										操作时间 
									</td>
									<td style="text-align: left">
										<c:out value="${statement.optTime}" />
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
