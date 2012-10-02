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
	<script type='text/javascript' src='<%=path %>/dwr/interface/platComAccountStore.js'></script>
	<script type='text/javascript' src='<%=path %>/dwr/interface/airticketOrderBiz.js'></script>
 	<script type='text/javascript' src='<%=path %>/dwr/engine.js'></script>
	<script type='text/javascript' src='<%=path %>/dwr/util.js'></script>
	<link type="text/css" href="../_js/development-bundle/themes/base/ui.all.css" rel="stylesheet" />
	<script type="text/javascript" src="../_js/development-bundle/jquery-1.3.2.js"></script>
	<script type="text/javascript" src="../_js/development-bundle/ui/ui.core.js"></script>
	<script type="text/javascript" src="../_js/development-bundle/ui/ui.draggable.js"></script>
	<script type="text/javascript" src="../_js/development-bundle/ui/ui.resizable.js"></script>
	<script type="text/javascript" src="../_js/development-bundle/ui/ui.dialog.js"></script>
	<script type="text/javascript" src="../_js/development-bundle/ui/effects.core.js"></script>
	<script type="text/javascript" src="../_js/development-bundle/ui/effects.highlight.js"></script>
		<script type="text/javascript" src="../_js/loadAccount.js"></script>
	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
				<html:form action="/airticket/listAirTicketOrder.do?thisAction=listWaitAgreeRetireOrder">
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
								<c:import url="../_jsp/mainTitle.jsp?title1=票务管理&title2=等待审核退废订单"
									charEncoding="UTF-8" />

								<div class="searchBar" style="display: none;">
									<table cellpadding="0" cellspacing="0" border="0"
										class="searchPanel">
										<tr>
											<td>
												承运人
												<html:select property="userNo" styleClass="colorblue2 p_5"
													style="width:120px;" />
											</td>
											<td>
												出票PNR
												<html:text property="drawPnr" styleClass="colorblue2 p_5"
													style="width:120px;" />
											</td>
											<td>
												预定PNR
												<html:text property="subPnr" styleClass="colorblue2 p_5"
													style="width:120px;" />
											</td>
											<td>
												大PNR
												<html:text property="bigPnr" styleClass="colorblue2 p_5"
													style="width:120px;" />
											</td>
											<td>
												订单号
												<html:text property="airOrderNo" styleClass="colorblue2 p_5"
													style="width:150px;" />
											</td>
											<td>
												操作人
												<html:text property="sysName" styleClass="colorblue2 p_5"
													style="width:100px;" />
											</td>
										</tr>
										<tr>
											<td>
												航班号
												<html:text property="flightCode" styleClass="colorblue2 p_5"
													style="width:120px;" />
											</td>
											<td>
												乘客
												<html:text property="agentName" styleClass="colorblue2 p_5"
													style="width:150px;" />
											</td>
											<td>
												票号
												<html:text property="ticketNumber" styleClass="colorblue2 p_5"
													style="width:150px;" />
											</td>
											<td>
												开始:
												<html:text property="userNo" styleClass="colorblue2 p_5"
													style="width:150px;" onclick="popUpCalendar(this, this);"
													readonly="true" />
											</td>
											<td>
												结束
												<html:text property="userNo" styleClass="colorblue2 p_5"
													style="width:150px;" onclick="popUpCalendar(this, this);"
													readonly="true" />
											</td>
											<td>
												最近
												<html:select property="userNo" styleClass="colorblue2 p_5"
													style="width:120px;" />
											</td>
										</tr>
										<tr>
											<td>
												买入
												<html:select property="userNo" styleClass="colorblue2 p_5"
													style="width:150px;" />
											</td>
											<td>
												付款
												<html:select property="userNo" styleClass="colorblue2 p_5"
													style="width:150px;" />
											</td>
											<td>
												卖出
												<html:select property="userNo" styleClass="colorblue2 p_5"
													style="width:150px;" />
											</td>
											<td>
												收款
												<html:select property="userNo" styleClass="colorblue2 p_5"
													style="width:150px;" />
											</td>
											<td>
												状态
												<html:select property="airticketOrder_status" styleClass="colorblue2 p_5"
													style="width:150px;">
													<html:option value="0">请选择</html:option>
													<html:option value="1">新订单</html:option>
													<html:option value="2">申请成功，等待支付</html:option>
													<html:option value="3">支付成功，等待出票</html:option>
													<html:option value="4">取消出票，等待退款</html:option>
													<html:option value="10">B2C订单，等待收款</html:option>
													<html:option value="20">退票订单，等待审核</html:option>
													<html:option value="21">退票审核通过，等待退款</html:option>
													<html:option value="30">废票订单，等待审核</html:option>
													<html:option value="31">废票审核通过，等待退款</html:option>
													<html:option value="41">改签订单，等待审核</html:option>
													<html:option value="42">改签审核通过，等待支付</html:option>
													<html:option value="43">改签已支付，等待确认</html:option>
													<html:option value="80">交易结束</html:option>
												</html:select>
											</td>
											<td>
												<input type="submit" name="button" id="button" value="提交"
													class="submit greenBtn" />
											</td>
										</tr>
									</table>
									<hr />
								</div>
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
												航班号
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
												折扣
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
										<th colspan="2">
											<div>
												操作
											</div>
										</th>
									</tr>
									 
                        		<c:forEach var="info" items="${ulf.list}" varStatus="status">
									<tr>
										<td>
                                         <c:forEach var="flight1" items="${info.flights}">                                         	
                                             <c:out value="${flight1.cyr}" /></br>
                                         </c:forEach>
										</td>
										<td>
										<c:forEach var="flight2" items="${info.flights}">
                                             <c:out value="${flight2.flightCode}" /></br>
                                         </c:forEach>
										</td>
										<td>
                                      <c:forEach var="flight3" items="${info.flights}">
                                             <c:out value="${flight3.startPoint}" />-
                                             <c:out value="${flight3.endPoint}" /></br>
                                         </c:forEach>
										</td>
										
										<td>
										 <c:forEach var="passenger1" items="${info.passengers}">
                                             <c:out value="${passenger1.name}" /></br>
                                         </c:forEach>
										</td>
										<td>
										 <c:forEach var="passenger2" items="${info.passengers}">
                                             <c:out value="${passenger2.ticketNumber}" /></br>
                                         </c:forEach>
										</td>
										<td>										
											<c:out value="${info.ticketPrice}" />										
										</td>
										<td>
										 <c:out value="${info.airportPrice}" />
										</td>
										<td>
										  <c:out value="${info.fuelPrice}" />
										</td>
									      
										<td>
										   <c:if test="${!empty info.statement.fromPCAccount}">
										    <c:out value="${info.statement.fromPCAccount.platform.name}" />
										    </c:if>
										    
										    <c:if test="${!empty info.statement.toPCAccount}">
										    <c:out value="${info.statement.toPCAccount.platform.name}" />
										    </c:if>
										</td>
										<td>
											<a href="<%=path%>/airticket/listAirTicketOrder.do?thisAction=viewAirticketOrderPage&tranType=<c:out value="${info.tranType}" />&groupMarkNo=<c:out value="${info.groupMarkNo}" />&aircketOrderId=<c:out value="${info.id}" />">
												<c:out value="${info.subPnr}" />
											</a>
										</td>
										<td>
												<a href="<%=path%>/airticket/listAirTicketOrder.do?thisAction=viewAirticketOrderPage&tranType=<c:out value="${info.tranType}" />&groupMarkNo=<c:out value="${info.groupMarkNo}" />&aircketOrderId=<c:out value="${info.id}" />">
													<c:out value="${info.drawPnr}" />
												</a>
											</td>
											<td>
												<a href="<%=path%>/airticket/listAirTicketOrder.do?thisAction=viewAirticketOrderPage&tranType=<c:out value="${info.tranType}" />&groupMarkNo=<c:out value="${info.groupMarkNo}" />&aircketOrderId=<c:out value="${info.id}" />">
													<c:out value="${info.bigPnr}" />
												</a>
											</td>
										<td>
										  <c:out value="${info.rebate}" />
										</td>
										<td>
											 <c:out value="${info.statement.totalAmount}" />
										</td>
										<td>
											<c:out value="${info.tranTypeText}" />(<c:out value="${info.businessTypeText}" />)
										</td>
										<td>
											 <c:out value="${info.statusText}" />
										</td>
										<td>
										
					     		<!-- 退票-->
					           		
								<c:if test="${info.tranType==3 && info.status==19}">
								
								<a href="#" onclick="showDiv11('<c:out value='${info.id}' />')">              
		                          <c:if test="${empty info.memo}">[备注]</c:if>
								  <c:if test="${!empty info.memo}"><font color="red">[备注]</font></c:if> 
		                        </a>
							       <input value="<c:out value='${info.memo}' />" type="hidden" id="memo<c:out value='${info.id}' />"/>  
		                        
		                   	   <td>
								 <c:check code="sb51">   <a   onclick="showDiv3('<c:out value='${info.id}' />','<c:out value='${info.tranType}'/>','<c:out value='${info.groupMarkNo}'/>')"  href="#">                    
		                        [通过申请]</a>
		                        </c:check>
		                        <input type="hidden"  id="ticketPrice<c:out value='${info.id}' />"  value="<c:out value='${info.ticketPrice}' />"/>
		                        <input type="hidden"  id="ticke"  value="<c:out value='${info.statement.totalAmount}' />"/>
		                        <input type="hidden" value="<c:out value='${info.adultCount}' />">
								   </td>
								</c:if>	
								
							<c:if test="${info.tranType==3 && info.status==20}">
								   <c:check code="sb51">
								 <a   onclick="showDiv7('<c:out value='${info.id}' />','<c:out value='${info.tranType}'/>','<c:out value='${info.groupMarkNo}'/>')"  href="#">
							    [通过申请]</a></c:check>
							    <input type="hidden"  id="ticketPrice<c:out value='${info.id}' />"  value="<c:out value='${info.ticketPrice}' />"/>
		                        <input type="hidden"  id="ticke"  value="<c:out value='${info.statement.totalAmount}' />"/>
		                        <input type="hidden" value="<c:out value='${info.adultCount}' />">                  
		                        
								   
								</c:if>	
								
												<!-- 退票外部-->
								<c:if test="${info.tranType==3 && info.status==24}">
								
								 <a href="#" onclick="showDiv11('<c:out value='${info.id}' />')">              
		                           <c:if test="${empty info.memo}">[备注]</c:if>
								  <c:if test="${!empty info.memo}"><font color="red">[备注]</font></c:if> 
		                         </a>
							       <input value="<c:out value='${info.memo}' />" type="hidden" id="memo<c:out value='${info.id}' />"/>  
		                        
		                   	   <td>
		                   	    <c:check code="sb51">
							   <a onclick="showDiv12('<c:out value='${info.id}' />','<c:out value='${info.tranType}'/>','<c:out value='${info.groupMarkNo}'/>')"  href="#">                    
		                        [通过申请]</a>
		                        </c:check>
		                        <input type="hidden"  id="ticketPrice<c:out value='${info.id}' />"  value="<c:out value='${info.ticketPrice}' />"/>
		                        <input type="hidden"  id="ticke"  value="<c:out value='${info.statement.totalAmount}' />"/>
		                        <input type="hidden" value="<c:out value='${info.adultCount}' />">
		                        
		                               <input id="tmpPlatformId12<c:out value='${info.id}' />" value="<c:out value='${info.statement.platComAccount.platform.id}'/>" type="hidden"/>
	                                  <input id="tmpCompanyId12<c:out value='${info.id}' />" value="<c:out value='${info.statement.platComAccount.company.id}'/>" type="hidden"/>
	                                  <input id="tmpAccountId12<c:out value='${info.id}' />" value="<c:out value='${info.statement.platComAccount.account.id}'/>" type="hidden"/>
		                        
								   </td>
								</c:if>	
								
			                    <c:if test="${info.tranType==3 && info.status==25}">
								   <c:check code="sb51">
								 <a   onclick="showDiv13('<c:out value='${info.id}' />','<c:out value='${info.tranType}'/>','<c:out value='${info.groupMarkNo}'/>')"  href="#">
							   
							     [通过申请]</a>
							     </c:check> <input type="hidden"  id="ticketPrice<c:out value='${info.id}' />"  value="<c:out value='${info.ticketPrice}' />"/>
		                        <input type="hidden"  id="ticke"  value="<c:out value='${info.statement.totalAmount}' />"/>
		                        <input type="hidden" value="<c:out value='${info.adultCount}' />">                  
		       	                        
								   
								</c:if>	
								
								
								
							    <!-- 废票-->
								<c:if test="${info.tranType==4 && info.status==29}">
								
		                        <a href="#" onclick="showDiv11('<c:out value='${info.id}' />')">              
		                            <c:if test="${empty info.memo}">[备注]</c:if>
								  <c:if test="${!empty info.memo}"><font color="red">[备注]</font></c:if> 
		                        
		                        </a>
							       <input value="<c:out value='${info.memo}' />" type="hidden" id="memo<c:out value='${info.id}' />"/> 
		                        <br>
								   <td>
								   <c:check code="sb51">
								   <a   onclick="showDiv3('<c:out value='${info.id}' />','<c:out value='${info.tranType}'/>','<c:out value='${info.groupMarkNo}'/>')"  href="#">                    
		                        [通过申请]</a></c:check>
								   </td>
							    <input type="hidden"  id="ticketPrice<c:out value='${info.id}' />"  value="<c:out value='${info.ticketPrice}' />"/>
		                        <input type="hidden"  id="ticke"  value="<c:out value='${info.statement.totalAmount}' />"/>
		                        <input type="hidden" value="<c:out value='${info.adultCount}' />">		
									
								</c:if>		
								
							<c:if test="${info.tranType==4 && info.status==30}">
								<c:check code="sb51">
								 <a   onclick="showDiv7('<c:out value='${info.id}' />','<c:out value='${info.tranType}'/>','<c:out value='${info.groupMarkNo}'/>')"  href="#">                 
		                        [通过申请]</a></c:check>
		                        <input type="hidden"  id="ticketPrice<c:out value='${info.id}' />"  value="<c:out value='${info.ticketPrice}' />"/>
		                        <input type="hidden"  id="ticke"  value="<c:out value='${info.statement.totalAmount}' />"/>
		                        <input type="hidden" value="<c:out value='${info.adultCount}' />">
								
								</c:if>	
								
								
									<!-- 废票- 外部 -->
								<c:if test="${info.tranType==4 && info.status==34}">
								
		                          <a href="#" onclick="showDiv11('<c:out value='${info.id}' />')">              
		                              <c:if test="${empty info.memo}">[备注]</c:if>
								  <c:if test="${!empty info.memo}"><font color="red">[备注]</font></c:if> 
		                        </a>
							       <input value="<c:out value='${info.memo}' />" type="hidden" id="memo<c:out value='${info.id}' />"/>  
		                        <br>
								   <td>
								   <c:check code="sb51">
								   <a   onclick="showDiv12('<c:out value='${info.id}' />','<c:out value='${info.tranType}'/>','<c:out value='${info.groupMarkNo}'/>')"  href="#">                    
		                        [通过申请]</a></c:check>
		                        <input type="hidden"  id="ticketPrice<c:out value='${info.id}' />"  value="<c:out value='${info.ticketPrice}' />"/>
		                        <input type="hidden"  id="ticke"  value="<c:out value='${info.statement.totalAmount}' />"/>
		                        <input type="hidden" value="<c:out value='${info.adultCount}' />">
								   </td>
									
								</c:if>		
								
							<c:if test="${info.tranType==4 && info.status==35}">
								<c:check code="sb51">
								 <a   onclick="showDiv13('<c:out value='${info.id}' />','<c:out value='${info.tranType}'/>','<c:out value='${info.groupMarkNo}'/>')"  href="#">                 
		                        [通过申请]</a></c:check>
								<input type="hidden"  id="ticketPrice<c:out value='${info.id}' />"  value="<c:out value='${info.ticketPrice}' />"/>
		                        <input type="hidden"  id="ticke"  value="<c:out value='${info.statement.totalAmount}' />"/>
		                        <input type="hidden" value="<c:out value='${info.adultCount}' />">
								</c:if>	
								
										
										</td>
									</tr>
                                 </c:forEach>
								</table>

								<table width="100%" style="margin-top: 5px;">
									<tr>
										<td></td>
										<td align="right">
											<div>
												共有记录&nbsp;
												<c:out value="${ulf.totalRowCount}"></c:out>
												&nbsp;条&nbsp;&nbsp;&nbsp;&nbsp; [
												<a href="JavaScript:turnToPage(document.forms[0],0)">首页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],1)">上一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],2)">下一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],3)"> 末页</a>]
												页数:
												<c:out value="${ulf.intPage}" />
												/
												<c:out value="${ulf.pageCount}" />
												]
											</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</html:form>
			</div>
			


<div id="dialog3" title="审核1">
	<p id="validateTips"></p>
<form action="../airticket/airticketOrder.do?thisAction=auditRetireTrading"  method="post" id="form3" >
	<fieldset>
	 <input id="oId3" name="id" type="hidden" />
	  <input id="tranType3" name="tranType" type="hidden" />
	  <input id="groupMarkNo3" name="groupMarkNo" type="hidden"/>
	  <input id="TmptotalAmount3"  type="hidden"/>
	   <input id="passengersCount3"  type="hidden"/>
	  
	  	    <table>
           <tr>
			<td>平台：</td>
			<td>
				<select name="platformId3" id="platform_Id3" class="text ui-widget-content ui-corner-all">		
							<option value="">请选择</option>															
				</select>
			</td>
			</tr>	
			<tr>
			<td>
				公司：
			</td>
			<td>
				<select name="companyId3" id="company_Id3"  class="text ui-widget-content ui-corner-all">		
					<option value="">请选择</option>								
				</select>
			</td>
				</tr>	
			<tr>
			<td>
				账号：
			</td>
			<td>
				<select name="accountId3" id="account_Id3"  class="text ui-widget-content ui-corner-all">		
					<option value="">请选择</option>								
				</select>
			</td>
			
			</tr>
		
	     <tr>
	     <td><label for="password">订单号</label></td>
	     <td><input type="text" name="airOrderNo" id="airOrderNo3"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
	    <tr>
	     <td><label for="password">应收金额</label></td>
	     <td><input type="text" name="totalAmount" id="totalAmount3" value="0"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
		 <tr>
	     <td><label for="password">手续费</label></td>
	     <td><input type="text" name="handlingCharge" id="handlingCharge3"   value="0"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
	    <tr>
	     <td><label for="password">客规</label></td>
	     <td>
	       <select name="selTuiPercent" id="selTuiPercent3"  onchange="selTuiPercent_onchange()">
			<option selected="selected" value="0">--请选择--</option>
			<option value="0">0%</option>
			<option value="5">5%</option>
			<option value="10">10%</option>
			<option value="20">20%</option>
			<option value="30">30%</option>
			<option value="50">50%</option>
			<option value="100">100%</option>
		</select>
	     </td>
	    </tr>
		<tr>
		<td>
		<input value="提交" type="submit" >
		</td>
		</tr>
		</table>
	</fieldset>
	</form>
</div>

			

			
<div id="dialog7" title="审核.">
	<p id="validateTips"></p>
<form action="../airticket/airticketOrder.do?thisAction=auditRetireTrading2"  method="post" id="form7" >
	<fieldset>
	 <input id="oId7" name="id" type="hidden" />
	  <input id="tranType7" name="tranType" type="hidden" />
	    <input id="groupMarkNo7" name="groupMarkNo" type="hidden"/>
	  <input id="TmptotalAmount7"  type="hidden"/>
	   <input id="passengersCount7"  type="hidden"/>
	  	    <table>
        <tr>
			<td>平台：</td>
			<td>
				<select name="platformId7" id="platform_Id7" class="text ui-widget-content ui-corner-all">		
							<option value="">请选择</option>															
				</select>
			</td>
			</tr>	
			<tr>
			<td>
				公司：
			</td>
			<td>
				<select name="companyId7" id="company_Id7"  class="text ui-widget-content ui-corner-all">		
					<option value="">请选择</option>								
				</select>
			</td>
				</tr>	
			<tr>
			<td>
				账号：
			</td>
			<td>
				<select name="accountId7" id="account_Id7"  class="text ui-widget-content ui-corner-all">		
					<option value="">请选择</option>								
				</select>
			</td>
			
			</tr>
		
	     <tr>
	     <td><label for="password">订单号</label></td>
	     <td><input type="text" name="airOrderNo" id="airOrderNo7"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
	    <tr>
	     <td><label for="password">应收金额</label></td>
	     <td><input type="text" name="totalAmount" id="totalAmount7" value="0"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
		 <tr>
	     <td><label for="password">手续费</label></td>
	     <td><input type="text" name="handlingCharge" id="handlingCharge7"   value="0"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
	    <tr>
	     <td><label for="password">客规</label></td>
	     <td>
	       <select name="selTuiPercent" id="selTuiPercent7" onchange="selTuiPercent_onchange2()">
			<option selected="selected" value="0">--请选择--</option>
			<option value="0">0%</option>
			<option value="5">5%</option>
			<option value="10">10%</option>
			<option value="20">20%</option>
			<option value="30">30%</option>
			<option value="50">50%</option>
			<option value="100">100%</option>
		</select>
	     </td>
	    </tr>
		<tr>
		<td>
		<input value="提交" type="submit" >
		</td>
		</tr>
		   
		</table>
	</fieldset>
	</form>
</div>


	<div id="dialog11" title="备注">
		<p id="validateTips"></p>
	<form action="../airticket/airticketOrder.do?thisAction=editRemark"  method="post" id="form11" >
		<fieldset>
		       <input id="oId11" name="id" type="hidden" />
		  	    <table>
		     <tr>
		    
		     <td>
		      <textarea rows="12" cols="60" name="memo" id="memo11"></textarea>
		     
		     </td>
		    </tr>
			<tr>
			<td>
			<input value="提交" type="submit" >
			</td>
			</tr>
			   
			</table>
		</fieldset>
		</form>
	</div>
	
	
	<div id="dialog12" title="审核12">
	<p id="validateTips"></p>
<form action="../airticket/airticketOrder.do?thisAction=auditOutRetireTrading"  method="post" id="form12" >
	<fieldset>
	 <input id="oId12" name="id" type="hidden" />
	  <input id="tranType12" name="tranType" type="hidden" />
	  <input id="groupMarkNo12" name="groupMarkNo" type="hidden"/>
	  <input id="TmptotalAmount12"  type="hidden"/>
	  	    <table>
     	   <tr>
			<td>平台：</td>
			<td>
				<select name="platformId12" id="platform_Id12" onchange="loadCompanyList('platform_Id12','company_Id12','account_Id12')" class="text ui-widget-content ui-corner-all">		
							<option value="">请选择</option>															
				</select>
			</td>
			</tr>	
			<tr>
			<td>
				公司：
			</td>
			<td>
				<select name="companyId12" id="company_Id12"  onchange="loadAccount('platform_Id12','company_Id12','account_Id12')" class="text ui-widget-content ui-corner-all">		
					<option value="">请选择</option>								
				</select>
			</td>
				</tr>	
			<tr>
			<td>
				账号：
			</td>
			<td>
				<select name="accountId12" id="account_Id12"  class="text ui-widget-content ui-corner-all">		
					<option value="">请选择</option>								
				</select>
			</td>
			
			</tr>
		
	     <tr>
	     <td><label for="password">订单号</label></td>
	     <td><input type="text" name="airOrderNo" id="airOrderNo"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
	    <tr>
	     <td><label for="password">应收金额</label></td>
	     <td><input type="text" name="totalAmount" id="totalAmount12" value="0"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
		 <tr>
	     <td><label for="password">手续费</label></td>
	     <td><input type="text" name="handlingCharge" id="handlingCharge12"   value="0"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
	
		<tr>
		<td>
		<input value="提交" type="submit" >
		</td>
		</tr>
		</table>
	</fieldset>
	</form>
</div>
	
	
	<div id="dialog13" title="审核13">
	<p id="validateTips"></p>
<form action="../airticket/airticketOrder.do?thisAction=auditOutRetireTrading2"  method="post" id="form7" >
	<fieldset>
	 <input id="oId13" name="id" type="hidden" />
	  <input id="tranType13" name="tranType" type="hidden" />
	    <input id="groupMarkNo13" name="groupMarkNo" type="hidden"/>
	  <input id="TmptotalAmount13"  type="hidden"/>
	  	    <table>
<!--  	  	 <tr>
	     <td><label for="password">平台：</label></td>
	     <td><input type="text" name="pnr" id="pnr"  class="text ui-widget-content ui-corner-all" disabled="disabled" /></td>
	    </tr> 
	     <tr>
	     <td><label for="password">公司：</label></td>
	     <td><input type="text" name="pnr" id="pnr"  class="text ui-widget-content ui-corner-all" disabled="disabled" /></td>
	    </tr>  
	        <tr>
	     <td><label for="password">账号：</label></td>
	     <td><input type="text" name="pnr" id="pnr"  class="text ui-widget-content ui-corner-all" disabled="disabled" /></td>
	    </tr>  -->
		
	     <tr>
	     <td><label for="password">订单号</label></td>
	     <td><input type="text" name="airOrderNo" id="airOrderNo"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
	    <tr>
	     <td><label for="password">应收金额</label></td>
	     <td><input type="text" name="totalAmount" id="totalAmount13" value="0"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
		 <tr>
	     <td><label for="password">手续费</label></td>
	     <td><input type="text" name="handlingCharge" id="handlingCharge13"   value="0"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
		<tr>
		<td>
		<input value="提交" type="submit" >
		</td>
		</tr>
		   
		</table>
	</fieldset>
	</form>
</div>	
	
			
</div>
<script type="text/javascript">
		   $(function(){
		   
		   $("#dialog3").dialog({
			bgiframe: true,
			autoOpen: false,
			//height: 500,
			//width:450,
			modal: true
		});	
		 $("#dialog7").dialog({
			bgiframe: true,
			autoOpen: false,
			//height: 500,
			//width:450,
			modal: true
		});	
		
		 $("#dialog11").dialog({
			bgiframe: true,
			autoOpen: false,
			height: 500,
			width:450,
			modal: true
			
		});		
		
		 $("#dialog12").dialog({
			bgiframe: true,
			autoOpen: false,
			height: 500,
			width:450,
			modal: true
			
		});	
		 $("#dialog13").dialog({
			bgiframe: true,
			autoOpen: false,
			height: 500,
			width:450,
			modal: true
			
		});			   
	});
	


 //审核
 function showDiv3(oId,tranType,groupMarkNo){

	  $('#oId3').val(oId);
	  $('#tranType3').val(tranType);
	  $('#groupMarkNo3').val(groupMarkNo);
	  $('#dialog3').dialog('open');
	  $('#airOrderNo3').val('');
	//var  TmptotalAmount3=$('#TmptotalAmount3');
	  $('#selTuiPercent3').attr('disabled','');
	 if(tranType==4){
	 $('#selTuiPercent3').attr('disabled','disabled');
	 }
	 airticketOrderBiz.getAirticketOrderByGroupMarkNor(groupMarkNo,1,function(ao){
	    var ta= ao.statement.totalAmount;
	   if(ta!=null){
	    $('#TmptotalAmount3').val(ta);
	    $('#passengersCount3').val(ao.passengersCount);
	    //alert(ta);
	    }
	     if(ao.airOrderNo!=null){
	     $('#airOrderNo3').val(ao.airOrderNo);
	    }
	    
	   //设置平台
	  if(ao.statement.platComAccount!=null){
	   
	   var  platform_Id= document.getElementById("platform_Id3");
	     platform_Id.options.length=0;
	     $('#platform_Id3').attr("disabled","disabled");
	     option = new Option(ao.statement.platComAccount.platform.name,ao.statement.platComAccount.platform.id);
		 platform_Id.options.add(option);
		 
		 var  company_Id= document.getElementById("company_Id3");
	     company_Id.options.length=0;
	     $('#company_Id3').attr("disabled","disabled");
	     option2 = new Option(ao.statement.platComAccount.company.name,ao.statement.platComAccount.company.id);
		 company_Id.options.add(option2);
		 
		  var  account_Id= document.getElementById("account_Id3");
	     account_Id.options.length=0;
	     $('#account_Id3').attr("disabled","disabled");
	     option3 = new Option(ao.statement.platComAccount.account.name,ao.statement.platComAccount.account.id);
		 account_Id.options.add(option3);
	  } 
	    
	 });
	}
	
	
	 //审核(外部)
 function showDiv12(oId,tranType,groupMarkNo){

	  $('#oId12').val(oId);
	  $('#tranType12').val(tranType);
	  $('#groupMarkNo12').val(groupMarkNo);
	  $('#dialog12').dialog('open');
	
	    //设置下拉框  平台初始值 默认选中
	    var tmpPlatformValue=$("#tmpPlatformId12"+oId).val();
	    var tmpCompanyValue=$("#tmpCompanyId12"+oId).val();  	
	     var tmpAccountValue=$("#tmpAccountId12"+oId).val(); 
	     if(tmpPlatformValue!=null&&tmpPlatformValue!=""){	
	     
	     loadPlatListSelected('platform_Id12','company_Id12','account_Id12',tmpPlatformValue,tmpCompanyValue,tmpAccountValue);
	     }else{
	     
	      loadPlatList('platform_Id12','company_Id12','account_Id12');
	     }
	
	}	
	

	 //审核(外部)
 function showDiv13(oId,tranType,groupMarkNo){

	  $('#oId13').val(oId);
	  $('#tranType13').val(tranType);
	  $('#groupMarkNo13').val(groupMarkNo);
	  $('#dialog13').dialog('open');

	}	

 //审核
 function showDiv7(oId,tranType,groupMarkNo){

	  $('#oId7').val(oId);
	  $('#tranType7').val(tranType);
	  $('#groupMarkNo7').val(groupMarkNo);
	  $('#dialog7').dialog('open');
	  $('#airOrderNo7').val('');
	  $('#selTuiPercent7').attr('disabled','');
	 if(tranType==4){
	 $('#selTuiPercent7').attr('disabled','disabled');
	 }
	 airticketOrderBiz.getAirticketOrderByGroupMarkNor(groupMarkNo,2,function(ao){
	    var ta= ao.statement.totalAmount;
	   if(ta!=null){
	    //alert(ta);
	    $('#TmptotalAmount7').val(ta);
	    $('#passengersCount7').val(ao.passengersCount);
	    
		    }
	   if(ao.airOrderNo!=null){
	     $('#airOrderNo7').val(ao.airOrderNo);
	    }
	    
	  	    //设置平台
	  if(ao.statement.platComAccount!=null){
	   
	   var  platform_Id= document.getElementById("platform_Id7");
	     platform_Id.options.length=0;
	     $('#platform_Id7').attr("disabled","disabled");
	     option = new Option(ao.statement.platComAccount.platform.name,ao.statement.platComAccount.platform.id);
		 platform_Id.options.add(option);
		 
		 var  company_Id= document.getElementById("company_Id7");
	     company_Id.options.length=0;
	     $('#company_Id7').attr("disabled","disabled");
	     option2 = new Option(ao.statement.platComAccount.company.name,ao.statement.platComAccount.company.id);
		 company_Id.options.add(option2);
		 
		  var  account_Id= document.getElementById("account_Id7");
	     account_Id.options.length=0;
	     $('#account_Id7').attr("disabled","disabled");
	     option3 = new Option(ao.statement.platComAccount.account.name,ao.statement.platComAccount.account.id);
		 account_Id.options.add(option3);
	  }   
	 });
	}

   	
	//客规	
	function selTuiPercent_onchange() {
	
	    var oIdValue=$('#oId3').val();
	    var ticketPrice=$('#ticketPrice'+oIdValue).val();//票面价
	    var handlingCharge=$('#handlingCharge3');//手续费
	    var selTuiPercent=$('#selTuiPercent3').val();//客规
	    var passengerNum=$('#passengersCount3').val();//人数
	    if(passengerNum==null){
	      passengerNum=1;
	    }
	    
	    // alert(ticketPrice+'---'+selTuiPercent);
	   if(ticketPrice!=null){ 
	    var tgqFee = ticketPrice * selTuiPercent / 100;
	   
	     tgqFee = tgqFee * Math.abs(passengerNum);
	     
	     handlingCharge.val(tgqFee);
	  
	  
	  var TmptotalAmount=$('#TmptotalAmount3').val();
      var totalTicketPrice=$('#totalAmount3');//
    if (TmptotalAmount * 1 > 0) {
        var receiveAmount = TmptotalAmount - tgqFee;
        // alert(TmptotalAmount+'---'+tgqFee);
        totalTicketPrice.val(Math.round(receiveAmount * 100) / 100);
      } 
      }
	}	
	
		//客规	
	function selTuiPercent_onchange2() {
	
	    var oIdValue=$('#oId7').val();
	    var ticketPrice=$('#ticketPrice'+oIdValue).val();//票面价
	    var handlingCharge=$('#handlingCharge7');//手续费
	    var selTuiPercent=$('#selTuiPercent7').val();//客规
	    var passengerNum=$('#passengersCount7').val();//人数
	    if(passengerNum==null){
	      passengerNum=1;
	    }	
	   if(ticketPrice!=null){   
	    // alert(ticketPrice+'---'+selTuiPercent);
	    var tgqFee = ticketPrice * selTuiPercent / 100;
	   
	     tgqFee = tgqFee * Math.abs(passengerNum);
	     
	     handlingCharge.val(tgqFee);
	     
	  var TmptotalAmount=$('#TmptotalAmount7').val();
      var totalTicketPrice=$('#totalAmount7');
    if (TmptotalAmount * 1 > 0) {
        var receiveAmount = TmptotalAmount - tgqFee;
        // alert(TmptotalAmount+'---'+tgqFee);
        totalTicketPrice.val(Math.round(receiveAmount * 100) / 100);
    } 
    }
	}	
	
	
	//备注
	function showDiv11(oId){
	  
	  $('#oId11').val(oId);
	  var  memo=$('#memo'+oId).val()
	  $('#memo11').val(memo);
	  $('#dialog11').dialog('open');
	 
	}
	</script>
	</body>
</html>
