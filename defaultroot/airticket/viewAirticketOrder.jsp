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
<script>
  var buyStatus=false;
  function displayStatement(id)
  {
    var _tr=document.getElementById(id);
    if(_tr)
    {
      if(buyStatus)
        _tr.style.display="";
      else
        _tr.style.display="none";
      buyStatus=!buyStatus;
    } 
  }
  
  function editStatement(id)
  {
    var url="<%=path%>/transaction/listStatement.do?thisAction=editStatement&id="+id;
    openWindow(400,340,url);  
  }
  
   function editOrder(id)
  {
    var url="<%=path%>/airticket/listAirTicketOrder.do?thisAction=editOrder&id="+id;
     window.location.href=url;
  }
  function editOrderMemo(id)
  {
	var url="<%=path%>/airticket/listAirTicketOrder.do?thisAction=editOrderMemo&id="+id;
	openWindow(400,340,url);  
  }
   function updateOrderProfitAfter(id)
  {
	var url="<%=path%>/airticket/airticketOrder.do?thisAction=updateOrderProfitAfter&id="+id;
	openWindow(400,340,url);  
  }
  
  function processing(id){
  	var url="<%=path%>/airticket/listAirTicketOrder.do?thisAction=processing&id="+id;
     window.location.href=url;
  }

</script>	
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
							<c:param name="title1" value="票务管理" />
							<c:param name="title2" value="查看订单详细信息" />																			
						</c:import>		
							操作记录
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
										<c:out value="${airticketOrder.totalPerson}"></c:out>
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
											客规率
										</div>
									</th>
									<th>
										<div>
											后返
										</div>
									</th>
											<th>
										<div>
											备注
										</div>
									</th>
									<th>
										<div>
											 
										</div>
									</th>									
								</tr>
								<c:forEach items="${airticketOrderList}" var="a" varStatus="status">
									<tr>
										<td>
											<c:out value="${a.tranTypeText}" />(<c:out value="${a.businessTypeText}" />)
										</td>
										<td>
											<c:out
												value="${a.platform.name}" />
										</td>
										<td>
											<c:out
												value="${a.company.name}" />
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
											<c:if test="${a.transRule>0}"><c:out value="${a.transRule}" />%</c:if>
										</td>
										<td>
										    <c:if test="${a.tranType==2}">
											<font color="red"><c:out value="${a.profitAfter}" /></font>&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="updateOrderProfitAfter('<c:out value="${a.id}"/>')">计算后返</a>
										   </c:if>
										</td>
											<td>
											<font color="red"><c:out value="${a.memo}" /></font>&nbsp;&nbsp;&nbsp;&nbsp;<c:check code="sb30"><a href="#" onclick="editOrderMemo('<c:out value="${a.id}"/>')">修改备注</a></c:check>
										</td>
										<td>
											<a href="#" onclick="displayStatement('payId<c:out value="${status.count}"/>')">查看结算单</a>
										</td>										
									</tr>
									
									<tr id="payId<c:out value='${status.count}'/>" >
									  <td colspan="3">流水号：<c:out value="${a.orderNo}"  /></td>
									  <td colspan="10">												  	
										<table width="100%"  bgcolor="#CCCCCC" cellpadding="0" cellspacing="0" border="0"
											class="dataList">
											<c:forEach items="${statementList}" var="s" varStatus="sstatus">
											   <c:if test="${s.orderId==a.id}">
												<tr>
												    <td><c:out value='${sstatus.count}'/></td>
												    <td width="80">
												     <c:if test="${s.type==1}"><c:out value="${s.toAccount.name}" /></c:if>
													 <c:if test="${s.type==2}"><c:out value="${s.fromAccount.name}" /></c:if>	
													</td>
													<td>
														<c:out value="${s.typeInfo}" />
													</td>
														<td>
														<c:out value="${s.orderSubtypeText}" />
													</td>
													
													<td width="120">
														<a href="<%=path%>/transaction/listStatement.do?thisAction=viewStatement&statementId=<c:out value="${s.id}" />">
															<c:out value="${s.statementNo}" /></a>
													</td>										
												    <td>
												       <c:out value="${s.statementDate}" />														
													</td>
													<td>
														<c:out value="${s.totalAmount}" />
													</td>
										<td>
														<c:out value="${s.statusInfo}" />
													</td>
													<td>
														<c:out value="${s.sysUser.userName}" />
													</td>	
													<td>
														<c:out value="${s.memo}" />
													</td>
													<td id="<c:out value='${s.orderId}'/>">
													<c:check code="sb50"><c:if test="${s.status!=8}"><a href="#" onclick="editStatement('<c:out value="${s.id}"/>')">修改</a></c:if></c:check>
													</td>																				
												</tr>
												</c:if>
											</c:forEach>
										</table>
									  </td>									
									</tr>									
								</c:forEach>
							</table>
							<br />
						
							<table width="100%" style="margin-top: 5px;">
								<tr>
									<td align="right">
										<c:check code="sb81">
											<input name="label" type="button" class="button1" value="编辑订单" onclick="editOrder('<c:out value="${airticketOrder.id}"/>')">
										</c:check>
										<input name="label" type="button" class="button1" value="关联订单" onclick="processing('<c:out value="${airticketOrder.id}"/>')">
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
									<th>
										<div>
											内容
										</div>
									</th>															
								</tr>
								<c:forEach items="${ticketLogList}" var="t">
									<tr>
										<td width="140">
											<c:out value="${t.formatOptTime}" />
										</td>
										<td width="60">
											<c:out value="${t.sysUser.userName}" />
										</td>
										<td width="120">
											<c:out value="${t.typeInfo}" />
										</td>		
										<td><div align="left">
											<c:out value="${t.content}" /></div>
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
