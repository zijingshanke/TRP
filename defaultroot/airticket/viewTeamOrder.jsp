<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="utf-8"%>

<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%
	String path = request.getContextPath();
 %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />

		<script src="../_js/common.js" type="text/javascript"></script>
		<script src="../_js/popcalendar.js" type="text/javascript"></script>
		<script src="../_js/popcalendar.js" type="text/javascript"></script>
		
		<script type='text/javascript' src='<%=path %>/dwr/interface/platComAccountStore.js'></script>
		<script type='text/javascript' src='<%=path %>/dwr/interface/airticketOrderBiz.js'></script>
 		 <script type='text/javascript' src='<%=path %>/dwr/engine.js'></script>
		 <script type='text/javascript' src='<%=path %>/dwr/util.js'></script>
		
		
		<link type="text/css" href="../_js/development-bundle/themes/base/ui.all.css" rel="stylesheet" />
		<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" src="../_js/development-bundle/ui/ui.core.js"></script>
		<script type="text/javascript" src="../_js/development-bundle/ui/ui.draggable.js"></script>
		<script type="text/javascript" src="../_js/development-bundle/ui/ui.dialog.js"></script>
			<script src="../_js/base/CalculateUtil.js" type="text/javascript"></script>
		<script type="text/javascript" src="../_js/tsms/loadTeamManage.js"></script>
		<script type="text/javascript" src="../_js/tsms/teamOrderOperate.js"></script>
		<script>
		 function editStatement(id)
		  {
		    var url="<%=path%>/transaction/listStatement.do?thisAction=editStatement&id="+id;
		     openWindow(400,340,url);  
		  }
  function editOrderMemo(id)
  {
	var url="<%=path%>/airticket/listAirTicketOrder.do?thisAction=editOrderMemo&id="+id;
	openWindow(400,340,url);  
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
								<c:param name="title2" value="查看团队订单" />																			
							</c:import>		
								<table width="100%" cellpadding="0" cellspacing="0" border="0" class="dataList">
										<th>订单号</th><th>订单金额</th><th>团队加价</th><th>客户加价</th>
										<th>购票客户</th><th>出票人</th><th>成人数</th><th>儿童数</th><th>婴儿数</th>
										<tr>
											<td>
												<c:out value="${buyerOrder.airOrderNo}"  />
											</td>
											<td>
												<c:out value="${buyerOrder.totalAmount}" />
											</td>
											<td>
												<c:out value="${buyerOrder.teamaddPrice}"  />
											</td>
											<td>
												<c:out value="${buyerOrder.agentaddPrice}" />
											</td>
											<td>
												<c:out value="${airticketOrder.agent.name}"></c:out>
											</td>
											<td>
												<c:out value="${airticketOrder.drawer}"></c:out>	
											</td>
											<td>
												<c:out value="${airticketOrder.adultCount}"></c:out>
											</td>
											<td>
												<c:out value="${airticketOrder.childCount}"></c:out>
											</td>
											<td>
												<c:out value="${airticketOrder.babyCount}"></c:out>	
											</td>
										</tr>
										 <tr>
										<td colspan="9">
											备注：<font color="red"><c:out value="${airticketOrder.memo}" /></font>&nbsp;&nbsp;&nbsp;&nbsp;<c:check code="sb70"><a href="#" onclick="editOrderMemo('<c:out value="${airticketOrder.id}"/>')">修改</a></c:check>
										</td>

									</tr>	
									</table>
									<hr />
								</div>	
								<br />
								航程信息：
								<table width="100%" cellpadding="0" cellspacing="0" border="0"
									class="dataList" id="table2">
									<tr>
										<th>
											<div>
												航班号
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
												票面价
											</div>
										</th>
										<th>
											<div>
												机建税(成人)
											</div>
										</th>
										<th>
											<div>
												燃油税(成人)
											</div>
										</th>
										<th>
											<div>
												机建税(儿童)
											</div>
										</th>
										<th>
											<div>
												燃油税(儿童)
											</div>
										</th>
										<th>
											<div>
												机建税(婴儿)
											</div>
										</th>
										<th>
											<div>
												燃油税(婴儿)
											</div>
										</th>
									</tr>	
								<c:forEach var="flight" items="${airticketOrder.flights}" varStatus="status">
									<tr>
										<td>
											<c:out  value="${flight.flightCode}" />
										</td>
										<td>
											<c:out  value="${flight.startPoint}" />--<c:out  value="${flight.endPoint}" />
										</td>
										<td>
											<c:out  value="${flight.boardingTime}" />
										</td>
										<td>
											<c:out  value="${flight.flightClass}" />
										</td>
										<td>
											<c:out  value="${flight.discount}" />
										</td>
										<td>
											<c:out  value="${flight.ticketPrice}" />
										</td>
										<td>
										 	<c:out value="${flight.airportPriceAdult}" />
										</td>
										<td>
											<c:out  value="${flight.fuelPriceAdult}" />
										</td>
										<td>
											<c:out  value="${flight.airportPriceChild}"/>
										</td>
										<td>
											<c:out  value="${flight.fuelPriceChild}" />
										</td>
										<td>
											<c:out  value="${flight.airportPriceBaby}" />
										</td>
										<td>
											<c:out value="${flight.fuelPriceBaby}" />
										</td>
									</tr>
								</c:forEach> 	
									<tr>
										<td colspan="3">
											团队总人数：<c:out value="${airticketOrder.totalPerson}" />
										</td>
										<td colspan="3">
											总票面价:<c:out value="${airticketOrder.totalTicketPrice}" />
										</td>
										<td colspan="3">
											总机建税:<c:out value="${airticketOrder.totalAirportPrice}" />
										</td>
										<td colspan="3">
											总燃油税:<c:out value="${airticketOrder.totalFuelPrice}" />
										</td>
									</tr>	
												 <tr>
										<td colspan="12">
											备注：<font color="red"><c:out value="${buyerOrder.memo}" /></font>&nbsp;&nbsp;&nbsp;&nbsp;
											<c:check code="sb70">
											<a href="#" onclick="editOrderMemo('<c:out value="${buyerOrder.id}"/>')">修改</a></c:check>
										</td>

									</tr>	
							</table>
							</td>
						</tr>
						<tr>
							<td colspan="5"><hr/></td>
						</tr>
					</table>
					<c:if test="${airticketOrder.id>0}">
						<table width="100%" cellpadding="0" cellspacing="0" border="0"
									class="dataList">
									<tr>
										<th>
											<div>
												应付出团代理费(现返)
											</div>
										</th>
										<th>
											<div>
												应收票款
											</div>
										</th>
										<th>
											<div>
												月底代理费
											</div>
										</th>
										<th>
											<div>
												应付票款
											</div>
										</th>
										<th>
											<div>
												实付票款
											</div>
										</th>										
										<th>
											<div>
												毛利润
											</div>
										</th>
										<th>
											<div>
												退票利润
											</div>
										</th>
										<th>
											<div>
												多收票款
											</div>
										</th>
										<th>
											<div>
												净利合计
											</div>
										</th>
										<th>
											<div>
												总金额
											</div>
										</th>
										<th>
											<div>
												操作
											</div>
										</th>
									</tr>
									<tr>
											<td>
												<c:out value="${teamProfit.commission}" />
											</td>
											<td>
												<c:out value="${teamProfit.saleTicketPrice}" />
											</td>
											<td>
												<c:out value="${teamProfit.buyRakeOff}" />
											</td>
											<td>
												<c:out value="${teamProfit.buyTicketPrice}" />
											</td>
											<td>	
												<c:out value="${teamProfit.buyTotalAmount}" />										
											</td>	
											<td>											
											   <c:out value="${teamProfit.grossProfit}" />
											</td>
											<td>	
												<c:out value="${teamProfit.refundProfit}" />											
											</td>
											<td>
												<c:out value="${teamProfit.saleOrder.overTicketPrice}" />											
											</td>
											<td>
												<c:out value="${teamProfit.totalProfit}" />												
											</td>
											<td>											
												<c:out value="${buyerOrder.totalAmount}" />	
											</td>
											<td>											
												<a href="#" onclick="showDiv('<c:out value="${buyerOrder.orderGroup.id}" />','<c:out value="${buyerOrder.subGroupMarkNo}"/>')">查看利润详情</a>
											</td>											
									</tr>
							</table>	
					</c:if>		
			</div>
			<br>
			<hr>结算单:</hr>
			<div>
			<table width="100%" cellpadding="0" cellspacing="0" border="0"
									class="dataList">
			<tr id="payId" >

			  <td>		 							  	
				<table width="100%"  cellpadding="0" cellspacing="0" border="0"
					class="dataList">
					<tr>
						    <th>序号</th>
						    <th width="80">
						    帐号
							</th>
							<th>
								收支
							</th>
								<th>
								票类型
							</th>
							
							<th width="120">
								结算单号
							</th>										
						    <th>
						       结算时间
								
							</th>
							<th>
								金额
							</th>
				<th>
								状态
							</th>
							<th>
								结算人
							</th>	
							<th>
							  备注
							</th>
							<th>
							  操作
							</th>																				
						</tr>
						 
					
					<c:forEach items="${statementList}" var="s" varStatus="sstatus">
					   
						<tr  bgcolor="#33FFCC">
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
						      <c:out value="${s.statementDate}"/>  
								
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
							<c:check code="sb80">
							<c:if test="${s.status!=8}">
							<a href="#" onclick="editStatement('<c:out value="${s.id}"/>')">修改</a>
							</c:if></c:check>
							</td>																				
						</tr>
						 
					</c:forEach>
				</table>
			  </td>									
			</tr>
</table>								
								
			</div>
			<hr>操作记录</hr>
			<div>
			
							
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
			</div>
			</div>
		<jsp:include page="../airticket/viewTeamProfit.jsp" />
	</body>
</html>
