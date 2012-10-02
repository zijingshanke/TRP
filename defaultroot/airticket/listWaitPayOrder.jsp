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
				<html:form action="/airticket/listAirTicketOrder.do?thisAction=listWaitPayOrder">
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
								<c:import url="../_jsp/mainTitle.jsp?title1=票务管理&title2=待确认支付订单"
									charEncoding="UTF-8" />

								<div class="searchBar">
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
											<c:out value="${info.tranTypeText}" />
										</td>
										<td>
											 <c:out value="${info.statusText}" />
										</td>
										<td>
										
								<c:if test="${ info.tranType==1 &&info.status==2||info.status==8}">
								<c:check code="sb43">
								     <a   onclick="showDiv8('<c:out value='${info.id}' />','<c:out value='${info.subPnr}'/>')"  href="#">                    
		                        [取消出票]</a>
		                        	</c:check>	
		                        <br>
								 <c:check code="sb44">
								<a href="<%=path %>/airticket/airticketOrder.do?thisAction=updateOrderStatus&status=7&id=<c:out value='${info.id}' />">                     
		                        [锁定]</a></c:check>
		                        <br>	
								</c:if>	
								
								
									
								<c:if test="${ info.tranType==1 &&info.status==7}">
								 <c:check code="sb43">
								     <a   onclick="showDiv8('<c:out value='${info.id}' />','<c:out value='${info.subPnr}'/>')"  href="#">                    
		                        [取消出票]</a>
		                        	</c:check>	
		                        <br>
		                        <c:check code="sb45">
		                        <a href="<%=path %>/airticket/airticketOrder.do?thisAction=updateOrderStatus&status=8&id=<c:out value='${info.id}' />">                     
		                        [解锁]</a>
		                        	</c:check>	<br>
								   <td>
								<c:check code="sb46">
								   <a   onclick="showDiv('<c:out value='${info.id}' />','<c:out value='${info.subPnr}'/>','<c:out value='${info.airOrderNo}'/>','<c:out value='${info.statement.totalAmount}'/>','<c:out value='${info.rebate}'/>')"  href="#">                       
		                        [确认支付]		                        	
		                        </a></c:check>	
                                  <input id="tmpPlatformId<c:out value='${info.id}' />" value="<c:out value='${info.statement.platComAccount.platform.id}'/>" type="hidden"/>
                                  <input id="tmpCompanyId<c:out value='${info.id}' />" value="<c:out value='${info.statement.platComAccount.company.id}'/>" type="hidden"/>
                                  <input id="tmpAccountId<c:out value='${info.id}' />" value="<c:out value='${info.statement.platComAccount.account.id}'/>" type="hidden"/>
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
			
<div id="dialog8" title="取消出票">
	<p id="validateTips"></p>
<form action="../airticket/airticketOrder.do?thisAction=quitTicket"  method="post" id="form8"  onsubmit="return submitForm8()">
	
	    <input id="oId8" name="id" type="hidden" />
	    <input id="groupMarkNo" name="groupMarkNo" type="hidden" />
	    
<table style="margin-left: 20px; margin-top: 20px; border: 1px solid black;" id="table1">
            <tbody><tr>
                <td style="width: 300px;">
  <table id="rbtnType" border="0">
	<tbody>
	<tr>
		<td><input id="rbtnType_0" name="rbtnType" value="自已取消出票" type="radio"><label for="rbtnType_0">自已取消出票</label></td>
		<td><input id="rbtnType_1" name="rbtnType" value="对方取消出票" checked="checked" type="radio" ">
		<label for="rbtnType_1">对方取消出票</label></td>
	</tr>
</tbody></table>                
                </td>
            </tr>
            <tr>
                <td style="border: 1px solid black;">
                &nbsp;&nbsp;取消原因：
                 <table id="rbtnReason" border="0">
	<tbody><tr>
		<td><input id="rbtnReason_0" name="rbtnReason" value="政策错误" type="radio"><label for="rbtnReason_0">政策错误</label></td>
	</tr><tr>
		<td><input id="rbtnReason_1" name="rbtnReason" value="位置不是KK或者HK" type="radio"><label for="rbtnReason_1">位置不是KK或者HK</label></td>
	</tr><tr>
		<td><input id="rbtnReason_2" name="rbtnReason" value="航段不连续" type="radio"><label for="rbtnReason_2">航段不连续</label></td>
	</tr><tr>
		<td><input id="rbtnReason_3" name="rbtnReason" value="该编码入库失败" type="radio"><label for="rbtnReason_3">该编码入库失败</label></td>
	</tr><tr>
		<td><input id="rbtnReason_4" name="rbtnReason" value="该航空网站上不去" type="radio"><label for="rbtnReason_4">该航空网站上不去</label></td>
	</tr><tr>
		<td><input id="rbtnReason_5" name="rbtnReason" value="价格不符" type="radio"><label for="rbtnReason_5">价格不符</label></td>
	</tr><tr>
		<td><input id="rbtnReason_6" name="rbtnReason" value="白金卡无此政策" type="radio"><label for="rbtnReason_6">白金卡无此政策</label></td>
	</tr>
</tbody></table>
                </td>
           </tr>
           <tr>
            <td style="border: 1px solid black;" align="left">
                &nbsp;&nbsp;其它原因： <textarea rows="5" cols="33"  id="cause"  class="text ui-widget-content ui-corner-all" ></textarea>
                <input type="hidden" name="memo"/>
            </td>
           </tr>
           <tr>
                <td align="center">
                                 
                </td>
            </tr>
        </tbody></table>	    
	    
	    
	   <br/>
		<input value="提交" type="submit" >
	</form>
</div>
		

<div id="dialog" title="确认支付">
	<p id="validateTips"></p>
<form action="../airticket/airticketOrder.do?thisAction=confirmTicket" id="form1"   method="post" >
	<fieldset>
	    <input id="oId" name="id" type="hidden" />
	    <table>
		   <jsp:include page="../transaction/plat.jsp"></jsp:include>
		<tr>
	     <td><label for="password">PNR</label></td>
	     <td><input type="text" name="pnr" id="pnr1"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
	     <tr>
	     <td><label for="password">订单号</label></td>
	     <td><input type="text" name="airOrderNo" id="airOrderNo1"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
	    <tr>
	     <td><label for="password">金额</label></td>
	     <td><input type="text" id="totalAmount2" value="0"  class="text ui-widget-content ui-corner-all"  disabled="disabled"/>
	      <input type="hidden" id="totalAmount1" name="totalAmount" >
	      <input type="hidden" id="tmpTotalAmount1">
	     </td>
	    </tr>
		
		  <tr>
	     <td><label for="password">政策</label></td>
	     <td><input type="text" name="rebate" id="rebate1"   value="0"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
	     <tr>
	     <td><label for="password" style="color: red">利润</label></td>
	     <td><input type="text" name="liruen" id="liruen1"   value="0"  class="text ui-widget-content ui-corner-all" style="color: red"/></td>
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
		   
		   $("#dialog8").dialog({
			bgiframe: true,
			autoOpen: false,
			//height: 500,
			//width:450,
			modal: true
		});	
		 $("#dialog").dialog({
			bgiframe: true,
			autoOpen: false,
			//height: 500,
			//width:450,
			modal: true
		});	
		   
	});
	
	//取消出票
 function showDiv8(oId,tranType,groupMarkNo){

	  $('#oId8').val(oId);
	  $('#tranType8').val(tranType);
	  $('#groupMarkNo').val(groupMarkNo);
	  $('#dialog8').dialog('open');
	 
	}	
	
		//确认支付
	function showDiv(oId,suPnr,airOrderNo,totalAmount,rebate){
	  $('#oId').val(oId);
	  $('#pnr1').val(suPnr);
	  $('#airOrderNo1').val(airOrderNo);
	  $('#totalAmount1').val(totalAmount);
	  $('#totalAmount2').val(totalAmount);
	  $('#tmpTotalAmount1').val(totalAmount);
	  $('#rebate1').val(rebate);
	   calculationLiren('tmpTotalAmount1','totalAmount1','liren1');
	  $('#dialog').dialog('open');
	  
	     	//设置下拉框  平台初始值 默认选中
    	var tmpPlatformValue=$("#tmpPlatformId"+oId).val();
    	var tmpCompanyValue=$("#tmpCompanyId"+oId).val();
    	var tmpAccountValue=$("#tmpAccountId"+oId).val();
    	if(tmpPlatformValue!=""){
    	 if(tmpPlatformValue!=null&&tmpPlatformValue!=""){	
	     
	     loadPlatListSelected('platform_Id','company_Id','account_Id',tmpPlatformValue,tmpCompanyValue,tmpAccountValue);
	     }else{
	     loadPlatList('platform_Id','company_Id','account_Id');	
	     }
	  
	 }
	}


   //利润计算
	function calculationLiren(tmpTotalAmount,totalAmount,liren){
	 
	var tmpTa=$("#"+tmpTotalAmount).val();
	var ta=$("#"+totalAmount).val();
	var lr=$("#"+liren);
	 if(tmpTa!=null&&ta!=null){
	 
	   var count=tmpTa-ta;
	   count= count.toString().replace(/^(\d+\.\d{2})\d*$/,"$1");
	   lr.val(count);
	 }
	
	}

		</script>
	</body>
</html>