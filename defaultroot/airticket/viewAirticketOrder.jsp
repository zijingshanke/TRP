<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%
String path = request.getContextPath();
%>
<html>
	<head>
		<title>main</title>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" language="javascript"
			src="../_js/jquery-1.3.1.min.js"></script>
		<script src="../_js/common.js" type="text/javascript"></script>
		<script src="../_js/popcalendar.js" type="text/javascript"></script>
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

							<c:import url="../_jsp/mainTitle.jsp?title1=用户管理&title2=查看订单详细信息"
								charEncoding="UTF-8" />
							<hr>
							<table width="100%" cellpadding="0" cellspacing="0" border="0"
								class="dataList">
								<tr>
									<th>
										<div>
											预定PNR
										</div>
									</th>
									<th>
										<div>
											大PNR
										</div>
									</th>
									<th>
										<div>
											出票PNR
										</div>
									</th>
									<th>
										<div>
											改签PNR
										</div>
									</th>									
									<th>
										<div>
											承运人
										</div>
									</th>
									<th>
										<div>
											票面价
										</div>
									</th>
									<th>
										<div>
											机建税
										</div>
									</th>
									<th>
										<div>
											燃油税
										</div>
									</th>
									<th>
										<div>
											航段
										</div>
									</th>
									<th>
										<div>
											起飞日期
										</div>
									</th>
									<th>
										<div>
											航班号
										</div>
									</th>
									<th>
										<div>
											舱位
										</div>
									</th>
									<th>
										<div>
											折扣
										</div>
									</th>
									<th>
										<div>
											姓名
										</div>
									</th>
									<th>
										<div>
											人数
										</div>
									</th>
									<th>
										<div>
											票号
										</div>
									</th>
								</tr>
								<tr>
									<td>
										<c:out value="${airticketOrder.subPnr}" />
									</td>
									<td>
										<c:out value="${airticketOrder.bigPnr}" />
									</td>
									<td>
										<c:out value="${airticketOrder.drawPnr}" />
									</td>
									<td>
										<c:out value="${airticketOrder.umbuchenPnr}" />
									</td>
									<td>
										<c:forEach var="f" items="${flightList}">
											<c:out value="${f.cyr}" />
										</c:forEach>
									</td>
									<td>
										<c:out value="${airticketOrder.ticketPrice}" />
									</td>
									<td>
										<c:out value="${airticketOrder.airportPrice}" />
									</td>
									<td>
										<c:out value="${airticketOrder.fuelPrice}" />
									</td>
									<td>
										<c:forEach var="f" items="${flightList}">
											<c:out value="${f.startPoint}" /> -
	                                             	<c:out
												value="${f.endPoint}" />
											<br />
										</c:forEach>
									</td>
									<td>
										<c:forEach var="f" items="${flightList}">
											<fmt:formatDate pattern="yyyy-MM-dd"
												value="${f.boardingTime}" />
											<br />
										</c:forEach>
									</td>
									<td>
										<c:forEach var="f" items="${flightList}">
											<c:out value="${f.flightCode}" />
											<br />
										</c:forEach>
									</td>
									<td>
										<c:forEach var="f" items="${flightList}">
											<c:out value="${f.flightClass}" />
											<br />
										</c:forEach>
									</td>
									<td>
										<c:forEach var="f" items="${flightList}">
											<c:out value="${f.discount}" />
											<br />
										</c:forEach>
									</td>
									<td>
										<c:forEach var="pa" items="${passengerList}">
											<c:out value="${pa.name}" />
											<br />
										</c:forEach>
									</td>
									<td>
										<c:out value="${airticketOrder.totlePerson}"></c:out>
									</td>
									<td>
										<c:forEach var="pa" items="${passengerList}">
											<c:out value="${pa.ticketNumber}" />
											<br />
										</c:forEach>
									</td>
								</tr>
							</table>
							<br />
							<table width="100%" cellpadding="0" cellspacing="0" border="0"
								class="dataList">
								<tr>
									<th>
										<div>
											类别
										</div>
									</th>
									<th>
										<div>
											平台
										</div>
									</th>
									<th>
										<div>
											公司
										</div>
									</th>
									<th>
										<div>
											机票订单号
										</div>
									</th>
									<th>
										<div>
											订单类型
										</div>
									</th>
									<th>
										<div>
											状态
										</div>
									</th>
									<th>
										<div>
											预定PNR
										</div>
									</th>
									<th>
										<div>
											大PNR
										</div>
									</th>
									<th>
										<div>
											政策
										</div>
									</th>
									<th>
										<div>
											手续费
										</div>
									</th>
									<th>
										<div>
											备注
										</div>
									</th>
								</tr>
								<c:forEach items="${airticketOrderList}" var="a">
									<tr>
										<td>
											<c:out value="${a.tranTypeText}" />（	<c:out value="${a.businessTypeText}" />）
										</td>
										<td>
											<c:out
												value="${a.platform.showName}" />
										</td>
										<td>
											<c:out
												value="${a.company.showName}" />
										</td>
										<td>
											<c:out value="${a.airOrderNo}" />
										</td>
										<td>
											<c:out value="${a.ticketTypeText}" />
										</td>
										<td>
											<c:out value="${a.statusText}" />
										</td>
										<td>
											<c:out value="${a.subPnr}" />
										</td>
										<td>
											<c:out value="${a.bigPnr}" />
										</td>
										<td>
											<c:out value="${a.rebate}" />
										</td>
										<td>
											<c:out value="${a.handlingCharge}" />
										</td>
										<td>
											<c:out value="${a.memo}" />
										</td>
									</tr>
								</c:forEach>
							</table>
							<br />
							支付信息
							<table width="100%" cellpadding="0" cellspacing="0" border="0"
								class="dataList">
								<tr>
									<th>
										<div>
											交易时间
										</div>
									</th>
									<th>
										<div>
											结算单号
										</div>
									</th>									
									<th>
										<div>
											收款帐号
										</div>
									</th>
									<th>
										<div>
											付款帐号
										</div>
									</th>									
									<th>
										<div>
											金额
										</div>
									</th>
									<th>
										<div>
											状态
										</div>
									</th>									
								</tr>
								<c:forEach items="${statementList}" var="s">
									<tr>
										<td>
											<c:out value="${s.formatOptTime}" />
										</td>
										<td>
											<a
												href="<%=path%>/transaction/listStatement.do?thisAction=viewStatement&statementId=<c:out value="${s.id}" />">
												<c:out value="${s.statementNo}" /> </a>
										</td>										
										<td>
											<c:out
												value="${s.toAccount.name}" />
										</td>
										<td>
											<c:out
												value="${s.fromAccount.name}" />
										</td>										
										<td>
											<c:out value="${s.totalAmount}" />
										</td>
										<td>
											<c:out value="${s.statusInfo}" />
										</td>										
									</tr>
								</c:forEach>
							</table>
							<table width="100%" style="margin-top: 5px;">
								<tr>
									<td>
										<input name="label" type="button" class="button1" value="返 回"
											onclick="window.history.back();">
									</td>
								</tr>
							</table>
							<br />
							<br />
							操作记录
							<table width="100%" cellpadding="0" cellspacing="0" border="0"
								class="dataList">
								<tr>
									<th>
										<div>
											操作时间
										</div>
									</th>
									<th>
										<div>
											操作员
										</div>
									</th>	
									<th>
										<div>
											类型
										</div>
									</th>								
								</tr>
								<c:forEach items="${ticketLogList}" var="t">
									<tr>
										<td>
											<c:out value="${t.formatOptTime}" />
										</td>
										<td>
											<c:out value="${t.sysUser.userName}" />
										</td>
										<td>
											<c:out value="${t.typeInfo}" />
										</td>									
									</tr>
								</c:forEach>
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
