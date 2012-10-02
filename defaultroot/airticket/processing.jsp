<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%
	String path = request.getContextPath();
 %>
<html xmlns="http://www.w3.org/1999/xhtml">
	<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
	<link href="../_css/global.css" rel="stylesheet" type="text/css" />	
   	<script type='text/javascript' src='<%=path %>/dwr/interface/platComAccountStore.js'></script>
   	<script type='text/javascript' src='<%=path %>/dwr/interface/passengerBiz.js'></script>
   	<script type='text/javascript' src='<%=path %>/dwr/interface/tempPNRBizImp.js'></script>
   	<script type='text/javascript' src='<%=path %>/dwr/interface/parseBlackUtil.js'></script>
   	<script type='text/javascript' src='<%=path %>/dwr/interface/airticketOrderBiz.js'></script>
 	<script type='text/javascript' src='<%=path %>/dwr/engine.js'></script>
	<script type='text/javascript' src='<%=path %>/dwr/util.js'></script>
	<link type="text/css" href="../_js/development-bundle/themes/base/ui.all.css" rel="stylesheet" />
	<script type="text/javascript" src="../_js/development-bundle/jquery-1.3.2.js"></script>
	<script type="text/javascript" src="../_js/development-bundle/ui/ui.core.js"></script>
	<script type="text/javascript" src="../_js/development-bundle/ui/ui.draggable.js"></script>
	<script type="text/javascript" src="../_js/development-bundle/ui/ui.dialog.js"></script>	
	<script src="../_js/calendar/WdatePicker.js" type="text/javascript"></script>	
	<script src="../_js/common.js" type="text/javascript"></script>
	<script src="../_js/base/CalculateUtil.js" type="text/javascript"></script>
	<script src="../_js/tsms/loadAccount.js" type="text/javascript"></script>	
	<script src="../_js/tsms/loadManage.js" type="text/javascript"></script>
	<script src="../_js/tsms/orderOperate.js" type="text/javascript"></script>
	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
				<html:form action="/airticket/listAirTicketOrder.do?thisAction=listAirTicketOrder">
					<html:hidden property="thisAction" />
					<html:hidden property="lastAction" />
					<html:hidden property="intPage" />
					<html:hidden property="pageCount" />
					<input type="hidden" name="locate"
						value="<c:out value="${locate}"></c:out>" />

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
									<c:param name="title1" value="订单管理" />
									<c:param name="title2" value="关联订单" />																				
								</c:import>		
													
								<table width="100%" cellpadding="0" cellspacing="0" border="0" 
									class="dataList">
									<tr>
										<th>
											<div>
												承运人
											</div>
										</th>
										<th>
											<div>
												行程
											</div>
										</th>

										<th>
											<div>
												乘客
											</div>
										</th>
										<th>
											<div>
												票号
											</div>
										</th>
										<th>
											<div>
												票面价
											</div>
										</th>
										<th>
											<div>
												机建
											</div>
										</th>
										<th>
											<div>
												燃油
											</div>
										</th>

										<th>
											<div>
												平台
											</div>
										</th>
										<th>
											<div>
												预定PNR
											</div>
										</th>
										<th>
											<div>
												出票PNR
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
												交易金额
											</div>
										</th>
										<th>
											<div>
												交易类型
											</div>
										</th>
										<th>
											<div>
												订单状态
											</div>
										</th>
										<th>
											<div>
												操作人
											</div>
										</th>
										<th>
											<div>
												支付人
											</div>
										</th>
										<th>
											<div>
												订单时间
											</div>
										</th>
										<th>
											<div>
												流水号
											</div>
										</th>
										
										<th colspan="2">
											<div>
												操作
											</div>
										</th>
									</tr>
									<c:forEach var="groupInfo" items="${ulf.list}" varStatus="status">
										<tr>
											<td rowspan="<c:out value="${groupInfo.orderCount}" />" >
													<c:out value="${groupInfo.carrier}" escapeXml="false"/>
											</td>
											<td rowspan="<c:out value="${groupInfo.orderCount}" />">
													<c:out value="${groupInfo.flight}" escapeXml="false" />											
											</td>										
											<td rowspan="<c:out value="${groupInfo.orderCount}" />">
													<c:out value="${groupInfo.passenger}"  escapeXml="false"/>
											</td>
											<td rowspan="<c:out value="${groupInfo.orderCount}" />">
													<c:out value="${groupInfo.ticketNo}"  escapeXml="false" />
											</td>
											<td rowspan="<c:out value="${groupInfo.orderCount}" />">
													<c:out value="${groupInfo.ticketPrice}" />
											</td>
											<td rowspan="<c:out value="${groupInfo.orderCount}" />">
													<c:out value="${groupInfo.airportPrice}" />
											</td>
											<td rowspan="<c:out value="${groupInfo.orderCount}" />">
													<c:out value="${groupInfo.fuelPrice}" />
											</td>										
											<td>
												<c:if test="${!empty groupInfo.saleOrder.platform}">
													<c:out value="${groupInfo.saleOrder.platform.name}" />
												</c:if>
											</td>
											<td>
												<a href="<%=path%>/airticket/listAirTicketOrder.do?thisAction=view&id=<c:out value="${groupInfo.saleOrder.id}" />">
													<c:out value="${groupInfo.saleOrder.subPnr}" /> 
												</a>
											</td>
											<td>
												<a href="<%=path%>/airticket/listAirTicketOrder.do?thisAction=view&id=<c:out value="${groupInfo.saleOrder.id}" />">
													<c:out value="${groupInfo.saleOrder.drawPnr}" />
												</a>
											</td>
											<td>
												<a href="<%=path%>/airticket/listAirTicketOrder.do?thisAction=view&id=<c:out value="${groupInfo.saleOrder.id}" />">
													<c:out value="${groupInfo.saleOrder.bigPnr}" />
												</a>
											</td>
											<td>
												<c:out value="${groupInfo.saleOrder.rebate}" />
											</td>
											<td>
												<c:out value="${groupInfo.saleOrder.totalAmount}" />
											</td>
											<td>
												<c:out value="${groupInfo.saleOrder.tranTypeText}" />(<c:out value="${groupInfo.saleOrder.businessTypeText}" />)
											</td>
											<td>
												<c:out value="${groupInfo.saleOrder.statusText}" />
											</td>
											<td>
												 <c:out	value="${groupInfo.saleOrder.showEntryOperatorName}" />
											</td>
											<td>
												<c:out value="${groupInfo.saleOrder.showPayOperatorName}" />
											</td>	
											<td>
												<c:out value="${groupInfo.saleOrder.orderNo}" />
											</td>											
											<td>
												<c:out value='${groupInfo.saleOrder.tradeOperate}' escapeXml="false"/>
											</td>
											<td>
												<c:out value='${groupInfo.saleOrder.commonOperateTextNoRelate}' escapeXml="false"/>
											</td>
												<!-- 加载参数 -->		
										
										<!-- 退票手续费 -->	
								  <input id="cyr<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.cyr}'/>" type="hidden"/>
	                              <input id="flightClass<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.flightClass}'/>" type="hidden"/>
	                              <input id="tmpPlatformId<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.platform.id}'/>" type="hidden"/>
	                              <input id="tmpCompanyId<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.company.id}'/>" type="hidden"/>
	                              <input id="tmpAccountId<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.account.id}'/>" type="hidden"/>
	                              <input id="tmpGroupId<c:out value='${groupInfo.saleOrder.id}' />"  value="<c:out value='${groupInfo.saleOrder.orderGroup.id}' />"  type="hidden" />
								 	
							      <input id="memo<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.memo}' />" type="hidden" />  							
						
		                          <input id="tmpPlatformId<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.platform.id}'/>" type="hidden"/>
		                          <input id="tmpCompanyId<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.company.id}'/>" type="hidden"/>
		                          <input id="tmpAccountId<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.account.id}'/>" type="hidden"/>
		                          <input id="tmpGroupMarkNo<c:out value='${groupInfo.saleOrder.id}' />"  value="<c:out value='${groupInfo.saleOrder.groupMarkNo}' />"  type="hidden" />
								  <input id="tmpGroupMarkNo<c:out value='${groupInfo.saleOrder.id}' />"  value="<c:out value='${groupInfo.saleOrder.groupMarkNo}' />"  type="hidden" />
							
		                          <input id="tmpPlatformId9<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.platform.id}'/>" type="hidden"/>
	                              <input id="tmpCompanyId9<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.company.id}'/>" type="hidden"/>
	                              <input id="tmpAccountId9<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.account.id}'/>" type="hidden"/>
	                              <input id="tmpGroupId9<c:out value='${groupInfo.saleOrder.id}' />"  value="<c:out value='${groupInfo.saleOrder.orderGroup.id}' />"  type="hidden" />
								  <input id="tmpTranType9<c:out value='${groupInfo.saleOrder.id}' />"  value="<c:out value='${groupInfo.saleOrder.tranType}' />"  type="hidden" />								
									
								 <input type="hidden"  id="ticketPrice<c:out value='${groupInfo.saleOrder.id}' />"  value="<c:out value='${groupInfo.saleOrder.ticketPrice}' />"/>
							     <input type="hidden"  id="ticke"  value="<c:out value='${groupInfo.saleOrder.totalAmount}' />"/>
							     <input type="hidden" value="<c:out value='${groupInfo.saleOrder.adultCount}' />">
							     <input type="hidden"  id="totalPerson<c:out value='${groupInfo.saleOrder.id}' />"  value="<c:out value='${groupInfo.saleOrder.totalPerson}' />"/>
									
								<input id="ticketPrice<c:out value='${groupInfo.saleOrder.id}' />"  value="<c:out value='${groupInfo.saleOrder.ticketPrice}' />" type="hidden"  />
									  <input type="hidden"  id="ticke"  value="<c:out value='${groupInfo.saleOrder.totalAmount}' />"/>
									  <input type="hidden" value="<c:out value='${groupInfo.saleOrder.adultCount}' />">
									 
									  <input id="tmpPlatformId12<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.platform.id}'/>" type="hidden"/>
					                  <input id="tmpCompanyId12<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.company.id}'/>" type="hidden"/>
					                  <input id="tmpAccountId12<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.account.id}'/>" type="hidden"/>
						             
						             <input id="actualAmountTemp4<c:out value='${groupInfo.saleOrder.id}' />" type="hidden" value="<c:out value='${groupInfo.saleOrder.totalAmount}'/>"/>
						             <input type="hidden" id="TmpFromPCAccount5" value="<c:out value="${groupInfo.saleOrder.platform.name}" />"/>
					                 <input id="tmpPlatformId5<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.platform.id}'/>" type="hidden"/>
					                 <input id="tmpCompanyId5<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.company.id}'/>" type="hidden"/>
					                 <input id="tmpAccountId5<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.account.id}'/>" type="hidden"/>
					                    
					                    <input type="hidden" id="TmpFromPCAccount14" value="<c:out value="${groupInfo.saleOrder.platform.name}" />"/>
					                <input id="tmpPlatformId14<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.platform.id}'/>" type="hidden"/>
					                <input id="tmpCompanyId14<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.company.id}'/>" type="hidden"/>
					                <input id="tmpAccountId14<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.account.id}'/>" type="hidden"/>
					                <input type="hidden"  id="platformName<c:out value='${groupInfo.saleOrder.id}'/>" value="<c:out value="${groupInfo.saleOrder.platform.name}" />"/> 
									<input type="hidden"  id="companyName<c:out value='${groupInfo.saleOrder.id}'/>" value="<c:out value="${groupInfo.saleOrder.company.name}" />"/> 
									<input type="hidden"  id="accountName<c:out value='${groupInfo.saleOrder.id}'/>" value="<c:out value="${groupInfo.saleOrder.account.name}" />"/> 
											</tr>
										<c:if test="${groupInfo.orderCount>1}">
										<c:forEach var="info" begin="1" items="${groupInfo.orderList}" varStatus="status3">									
											<c:if test="${info.businessType==2}">
												<tr style="background-color: #CCCCCC">
											</c:if>		
											<c:if test="${info.businessType!=2}">
												<tr>
											</c:if>															
												<td>
													<c:if test="${!empty info.platform}">
															<c:out value="${info.platform.name}" />
													</c:if>
												</td>
												<td>
													<a href="<%=path%>/airticket/listAirTicketOrder.do?thisAction=view&id=<c:out value="${info.id}" />">
														<c:out value="${info.subPnr}" /> 
													</a>
												</td>
												<td>
													<a href="<%=path%>/airticket/listAirTicketOrder.do?thisAction=view&id=<c:out value="${info.id}" />">
														<c:out value="${info.drawPnr}" />
													</a>
												</td>
												<td>
													<a href="<%=path%>/airticket/listAirTicketOrder.do?thisAction=view&id=<c:out value="${info.id}" />">
														<c:out value="${info.bigPnr}" />
													</a>
												</td>
												<td>
													<c:out value="${info.rebate}" />
												</td>
												<td>
													<c:out value="${info.totalAmount}" />
												</td>
												<td>
													<c:out value="${info.tranTypeText}" />(<c:out value="${info.businessTypeText}" />)
												</td>
												<td>
													<c:out value="${info.statusText}" />
												</td>
												<td>
													<c:out value="${info.showEntryOperatorName}" />
												</td>
												<td>
													<c:out value="${info.showPayOperatorName}" />
												</td>	
												<td>
													<c:out value="${info.orderNo}" />
												</td>		
												<td>											
													<c:out value='${info.tradeOperate}' escapeXml="false"/>
												</td>
												<td>
												<c:out value='${info.commonOperateTextNoRelate}' escapeXml="false"/>
											</td>
											</tr>	
													<!-- 加载参数 -->
										<!-- 退票手续费 -->	
								    <input id="cyr<c:out value='${info.id}' />" value="<c:out value='${info.cyr}'/>" type="hidden"/>
		                         	<input id="flightClass<c:out value='${info.id}' />" value="<c:out value='${info.flightClass}'/>" type="hidden"/>  
								    <input id="tmpPlatformId<c:out value='${info.id}' />" value="<c:out value='${info.platform.id}'/>" type="hidden"/>
		                          <input id="tmpCompanyId<c:out value='${info.id}' />" value="<c:out value='${info.company.id}'/>" type="hidden"/>
		                          <input id="tmpAccountId<c:out value='${info.id}' />" value="<c:out value='${info.account.id}'/>" type="hidden"/>
		                          <input id="tmpGroupId<c:out value='${info.id}' />"  value="<c:out value='${info.orderGroup.id}' />"  type="hidden" />
								
								<input id="tmpPlatformId5<c:out value='${info.id}' />" value="<c:out value='${info.platform.id}'/>" type="hidden"/>
					            <input id="tmpCompanyId5<c:out value='${info.id}' />" value="<c:out value='${info.company.id}'/>" type="hidden"/>
					            <input id="tmpAccountId5<c:out value='${info.id}' />" value="<c:out value='${info.account.id}'/>" type="hidden"/>
					                  
													
								  <input id="tmpPlatformId9<c:out value='${info.id}' />" value="<c:out value='${info.platform.id}'/>" type="hidden"/>
	                              <input id="tmpCompanyId9<c:out value='${info.id}' />" value="<c:out value='${info.company.id}'/>" type="hidden"/>
	                              <input id="tmpAccountId9<c:out value='${info.id}' />" value="<c:out value='${info.account.id}'/>" type="hidden"/>
	                              <input id="tmpGroupId9<c:out value='${info.id}' />"  value="<c:out value='${info.orderGroup.id}' />"  type="hidden" />
								 	
								 <input id="tmpPlatformId12<c:out value='${info.id}' />" value="<c:out value='${info.platform.id}'/>" type="hidden"/>
					             <input id="tmpCompanyId12<c:out value='${info.id}' />" value="<c:out value='${info.company.id}'/>" type="hidden"/>
					             <input id="tmpAccountId12<c:out value='${info.id}' />" value="<c:out value='${info.account.id}'/>" type="hidden"/>
						         
						         <input id="tmpPlatformId14<c:out value='${info.id}' />" value="<c:out value='${info.platform.id}'/>" type="hidden"/>
					             <input id="tmpCompanyId14<c:out value='${info.id}' />" value="<c:out value='${info.company.id}'/>" type="hidden"/>
					             <input id="tmpAccountId14<c:out value='${info.id}' />" value="<c:out value='${info.account.id}'/>" type="hidden"/>
					          								 	
							      <input id="memo<c:out value='${info.id}'/>" value="<c:out value='${info.memo}' />" type="hidden"  />  							
						
		                          <input id="tmpGroupMarkNo<c:out value='${info.id}' />"  value="<c:out value='${info.orderGroup.no}' />"  type="hidden" />
								 
		                          <input id="tmpTranType9<c:out value='${info.id}' />"  value="<c:out value='${info.tranType}' />"  type="hidden" />								
									
								 <input id="ticketPrice<c:out value='${info.id}' />" value="<c:out value='${info.ticketPrice}'/>"  type="hidden"   />
							     <input id="ticke"  value="<c:out value='${info.totalAmount}' />" type="hidden"  />
							     <input type="hidden" value="<c:out value='${info.adultCount}' />">
							  
							     <input type="hidden"  id="totalPerson<c:out value='${info.id}' />"  value="<c:out value='${info.totalPerson}' />"/>
								
						             <input id="actualAmountTemp4<c:out value='${info.id}' />" type="hidden" value="<c:out value='${info.totalAmount}'/>"/>
						              
						             <input type="hidden" id="TmpFromPCAccount5" value="<c:out value="${info.platform.name}" />"/>	
					                <input type="hidden" id="TmpFromPCAccount14" value="<c:out value="${info.platform.name}" />"/>					                
					               
					                <input type="hidden"  id="platformName<c:out value='${info.id}'/>" value="<c:out value="${info.platform.name}" />"/> 
									<input type="hidden"  id="companyName<c:out value='${info.id}'/>" value="<c:out value="${info.company.name}" />"/> 
									<input type="hidden"  id="accountName<c:out value='${info.id}'/>" value="<c:out value="${info.account.name}" />"/> 
									</c:forEach>
									</c:if>									
							</c:forEach>
								</table>
							</td>
						</tr>
					</table>
				</html:form>
			</div>
		</div>				
	<jsp:include page="manageDiv.jsp"></jsp:include>
</body>
</html>
