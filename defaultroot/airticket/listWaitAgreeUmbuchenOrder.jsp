﻿<%@ page contentType="text/html;charset=UTF-8" language="java"
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
				<html:form action="/airticket/listAirTicketOrder.do?thisAction=listWaitAgreeUmbuchenOrder">
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
								<c:import url="../_jsp/mainTitle.jsp?title1=票务管理&title2=改签待审核新订单"
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
											<a href="<%=path %>/airticket/listAirTicketOrder.do?thisAction=viewAirticketOrderPage&aircketOrderId=<c:out value="${info.id}" />">
												<c:out value="${info.subPnr}" />
											</a>
										</td>
										<td>
											 <c:out value="${info.drawPnr}" />
										</td>
										<td>
									    <c:out value="${info.bigPnr}" />
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
										
					        	
							  <!-- 申请改签 -->
									
					         <c:if test="${info.statement.type==1 && info.status==39}">
								 <c:check code="sb52">
									 <a href="<%=path %>/airticket/airticketOrder.do?thisAction=updateOrderStatus&status=44&id=<c:out value='${info.id}' />">                    
		                           [拒绝申请]</a>
		                           </c:check><br/>
		                     
									 <td>
									  <c:check code="sb51">
								   <a   onclick="showDiv5('<c:out value='${info.id}' />','<c:out value='${info.tranType}'/>','<c:out value='${info.groupMarkNo}'/>')"  href="#">                    
		                        [通过申请]</a>
		                        </c:check>
		                        <input type="hidden" id="TmpFromPCAccount5" value="<c:out value="${info.statement.toPCAccount.platform.name}" />"/>
								   </td>
									</c:if>		
									
								<c:if test="${info.statement.type==1 && info.status==40}">
									  <c:check code="sb51">
								  <a href="<%=path %>/airticket/airticketOrder.do?thisAction=updateOrderStatus&status=41&id=<c:out value='${info.id}' />">                     
		                        [通过申请]</a>
								 </c:check>
									</c:if>	
									
						   		 <!-- 申请改签 外部-->		
							<c:if test="${info.statement.type==1 && info.status==46}">
									 <a href="<%=path %>/airticket/airticketOrder.do?thisAction=updateOrderStatus&status=44&id=<c:out value='${info.id}' />">                    
		                           [拒绝申请]</a><br/>
		                     
									 <td>
								  <c:check code="sb51">
								    <a   onclick="showDiv14('<c:out value='${info.id}' />','<c:out value='${info.tranType}'/>','<c:out value='${info.groupMarkNo}'/>')"  href="#">                    
		                        [通过申请]</a>
		                        </c:check>
		                        <input type="hidden" id="TmpFromPCAccount14" value="<c:out value="${info.statement.toPCAccount.platform.name}" />"/>
		                          <input id="tmpPlatformId14<c:out value='${info.id}' />" value="<c:out value='${info.statement.platComAccount.platform.id}'/>" type="hidden"/>
	                              <input id="tmpCompanyId14<c:out value='${info.id}' />" value="<c:out value='${info.statement.platComAccount.company.id}'/>" type="hidden"/>
	                              <input id="tmpAccountId14<c:out value='${info.id}' />" value="<c:out value='${info.statement.platComAccount.account.id}'/>" type="hidden"/>
								   </td>
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
			

<div id="dialog5" title="改签审核">
	<p id="validateTips"></p>
<form action="../airticket/airticketOrder.do?thisAction=auditWaitAgreeUmbuchenOrder"  method="post" id="form3" >
	<fieldset>
	 <input id="oId5" name="id" type="hidden" />
	 <input id="tranType5" name="tranType" type="hidden" />
	 <input id="groupMarkNo5" name="groupMarkNo" type="hidden"/>
	  	    <table>
	    <tr>
	    <td>平台</td>
		<td>
	<select name="platformId2" id="platformId5" disabled="disabled"  class="text ui-widget-content ui-corner-all">		
		<option value="">请选择</option>															
		</select>
		</td>
		</tr>
		<tr>
	     <td><label for="password">订单号</label></td>
	     <td><input type="text" name="airOrderNo"   class="text ui-widget-content ui-corner-all" /></td>
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


<div id="dialog14" title="改签审核(外部)">
	<p id="validateTips"></p>
<form action="../airticket/airticketOrder.do?thisAction=auditOutWaitAgreeUmbuchenOrder"  method="post" id="form14" >
	<fieldset>
	 <input id="oId14" name="id" type="hidden" />
	 <input id="tranType14" name="tranType" type="hidden" />
	 <input id="groupMarkNo14" name="groupMarkNo" type="hidden"/>
	  	    <table>
	 
	      <tr>
			<td>平台：</td>
			<td>
				<select name="platformId14" id="platform_Id14" onchange="loadCompanyList('platform_Id14','company_Id14','account_Id14')" class="text ui-widget-content ui-corner-all">		
							<option value="">请选择</option>															
				</select>
			</td>
			</tr>	
			<tr>
			<td>
				公司：
			</td>
			<td>
				<select name="companyId14" id="company_Id14"  onchange="loadAccount('platform_Id14','company_Id14','account_Id14')" class="text ui-widget-content ui-corner-all">		
					<option value="">请选择</option>								
				</select>
			</td>
				</tr>	
			<tr>
			<td>
				账号：
			</td>
			<td>
				<select name="accountId14" id="account_Id14"  class="text ui-widget-content ui-corner-all">		
					<option value="">请选择</option>								
				</select>
			</td>
			
			</tr>
		<tr>
	     <td><label for="password">订单号</label></td>
	     <td><input type="text" name="airOrderNo"   class="text ui-widget-content ui-corner-all" /></td>
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
		   
		   $("#dialog5").dialog({
			bgiframe: true,
			autoOpen: false,
			//height: 500,
			//width:450,
			modal: true
		});	
		
		$("#dialog14").dialog({
			bgiframe: true,
			autoOpen: false,
			//height: 500,
			//width:450,
			modal: true
		});	   
	});
	

//申请改签
 function showDiv5(oId,suPnr,groupMarkNo){

	  $('#oId5').val(oId);
	  $('#tranType5').val(suPnr);
	  $('#groupMarkNo5').val(groupMarkNo);
	  $('#dialog5').dialog('open');
	  var TmpFromPCAccount5=$('#TmpFromPCAccount5').val();
	  
	  
	  var platformId5= document.getElementById('platformId5');
	  platformId5.options.length=0;
	  option = new Option(TmpFromPCAccount5,'');
	  platformId5.options.add(option);
	   
	   
/*	 platComAccountStore.getPlatFormList(function(data){
			   		for(var i=0;i<data.length;i++)
			   		{		
			   			
			   			document.all.platformId2.options[i] = new Option(data[i].name,data[i].id);
			   			
			   		}
			   		
			   });*/
	 
	}
	
	//申请改签(外部)
 function showDiv14(oId,suPnr,groupMarkNo){

	  $('#oId14').val(oId);
	  $('#tranType14').val(suPnr);
	  $('#groupMarkNo14').val(groupMarkNo);
	  $('#dialog14').dialog('open');
       //  loadPlatList('platform_Id14','company_Id14','account_Id14');
	    //设置下拉框  平台初始值 默认选中
	    var tmpPlatformValue=$("#tmpPlatformId14"+oId).val();
	    var tmpCompanyValue=$("#tmpCompanyId14"+oId).val();  	
	     var tmpAccountValue=$("#tmpAccountId14"+oId).val(); 
	     if(tmpPlatformValue!=null&&tmpPlatformValue!=""){	
	     
	     loadPlatListSelected('platform_Id14','company_Id14','account_Id14',tmpPlatformValue,tmpCompanyValue,tmpAccountValue);
	     }else{
	     
	      loadPlatList('platform_Id14','company_Id14','account_Id14');
	     }
	 
	}	
	</script>
	</body>
</html>
