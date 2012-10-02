<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="utf-8"%>

<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%
	String path= request.getContextPath();
 %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />

	<link type="text/css" href="../_js/development-bundle/themes/base/ui.all.css" rel="stylesheet" />
	<script type="text/javascript" src="../_js/development-bundle/jquery-1.3.2.js"></script>
	<script type="text/javascript" src="../_js/development-bundle/ui/ui.core.js"></script>
	<script type="text/javascript" src="../_js/development-bundle/ui/ui.draggable.js"></script>
	<script type="text/javascript" src="../_js/development-bundle/ui/ui.resizable.js"></script>
	<script type="text/javascript" src="../_js/development-bundle/ui/ui.dialog.js"></script>
	<script type="text/javascript" src="../_js/development-bundle/ui/effects.core.js"></script>
	<script type="text/javascript" src="../_js/development-bundle/ui/effects.highlight.js"></script>
	<link rel="stylesheet" href="../_js/development-bundle/demos/demos.css" type="text/css"></link>
		
		<script type="text/javascript">
	$(function() {
		
		var ticketPrice = $("#ticketPrice"),
			documentPrice = $("#documentPrice"),
			insurancePrice = $("#insurancePrice"),
			//actualAmount = $("#actualAmount"),
			
			allFields = $([]).add(ticketPrice).add(documentPrice).add(insurancePrice),
			tips = $("#validateTips");

		function updateTips(t) {
			tips.text(t);
		}

		function checkLength(o,n,min,max) {

			if ( o.val().length > max || o.val().length < min ) {
				o.addClass('ui-state-error');
				updateTips("Length of " + n + " must be between "+min+" and "+max+".");
				return false;
			} else {
				return true;
			}

		}

		function checkRegexp(o,regexp,n) {

			if ( !( regexp.test( o.val() ) ) ) {
				o.addClass('ui-state-error');
				updateTips(n);
				return false;
			} else {
				return true;
			}

		}
		
		$("#dialog").dialog({
			bgiframe: true,
			autoOpen: false,
			//height: 500,
			//width:300,
			modal: true,
			buttons: {
				'确 定': function() {
					var bValid = true;
					allFields.removeClass('ui-state-error');

					bValid = bValid && checkLength(ticketPrice,"ticketPrice");
					bValid = bValid && checkLength(documentPrice,"documentPrice");
					bValid = bValid && checkLength(insurancePrice,"insurancePrice");
					//bValid = bValid && checkLength(actualAmount,"actualAmount");

				//	bValid = bValid && checkRegexp(name,/^[a-z]([0-9a-z_])+$/i,"ticketPrice may consist of a-z, 0-9, underscores, begin with a letter.");
					// From jquery.validate.js (by joern), contributed by Scott Gonzalez: http://projects.scottsplayground.com/documentPrice_address_validation/
				//	bValid = bValid && checkRegexp(documentPrice,/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i,"eg. ui@jquery.com");
				//	bValid = bValid && checkRegexp(insurancePrice,/^([0-9a-zA-Z])+$/,"insurancePrice field only allow : a-z 0-9");
					//bValid = bValid && checkRegexp(actualAmount,/^([0-9])+$/,"actualAmount field only allow : a-z 0-9");
				//	if (bValid) {
					//	alert('<tr>' +
					//		'<td>' + ticketPrice.val() + '</td>' + 
					//		'<td>' + documentPrice.val() + '</td>' + 
					//		'<td>' + insurancePrice.val() + '</td>' +
					//		'</tr>'); 
					//	$(this).dialog('close');						
					//}
					
					var actualAmount = $("#actualAmount").val();
					var statementId =$("#statementId").val();		
					var pan = /^[0-9]+$/;				
					if(!pan.test(actualAmount))
					{
						alert("请输入数字!");
						return false;
					}
					if(actualAmount != null && actualAmount != "")
					{						
						document.statement.action="<%=path %>/transaction/statement.do?thisAction=updatePlatform";
		   				document.statement.submit();
					}else
					{
						alert("收回金额不能为空!");
						return false;
					}	
					
					
				},
				'取 消': function() {
					$(this).dialog('close');
				}
			},
			close: function() {
				allFields.val('').removeClass('ui-state-error');
			}
		});
		
		
		
		$('#create-user').click(function() {
			$('#dialog').dialog('open');
		})
		
	});
	
	function showDiv(airticketOrderId,statementId,unsettledAccount,documentPrice,insurancePrice,groupMarkNo){
	 
	  $('#airticketOrderId').val(airticketOrderId);
	  $('#statementId').val(statementId);
	  $('#ticketPrice').val(unsettledAccount);//赋值给文件框
	  $('#documentPrice').val(documentPrice);
	  $('#insurancePrice').val(insurancePrice);
	  $('#groupMarkNo').val(groupMarkNo);
	  $('#dialog').dialog('open');
	
	}
	
	</script>
	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
				<html:form
					action="/airticket/listAirTicketOrder.do?thisAction=listWaitRecoveryTicketOrders">
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
								<c:import url="../_jsp/mainTitle.jsp?title1=票务管理&title2=全部订单"
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
												<html:text property="groupMarkNo"
													styleClass="colorblue2 p_5" style="width:150px;" />
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
												<html:text property="ticketNumber"
													styleClass="colorblue2 p_5" style="width:150px;" />
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
											<td style="display: none;">
												状态
												<html:select property="airticketOrder_status"
													styleClass="colorblue2 p_5" style="width:150px;">
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
										<th>
											<div>
												未结款
											</div>
										</th>
										<th>
											<div>
												操作
											</div>
										</th>
									</tr>
									<c:forEach var="info" items="${ulf.list}" varStatus="status">
										<tr>
											<td>
												<c:forEach var="flight1" items="${info.flights}">
													<c:out value="${flight1.cyr}" />
													</br>
												</c:forEach>
											</td>
											<td>
												<c:forEach var="flight2" items="${info.flights}">
													<c:out value="${flight2.flightCode}" />
													</br>
												</c:forEach>
											</td>
											<td>
												<c:forEach var="flight3" items="${info.flights}">
													<c:out value="${flight3.startPoint}" />-
                                             <c:out
														value="${flight3.endPoint}" />
													</br>
												</c:forEach>
											</td>

											<td>
												<c:forEach var="passenger1" items="${info.passengers}">
													<c:out value="${passenger1.name}" />
													</br>
												</c:forEach>
											</td>
											<td>
												<c:forEach var="passenger2" items="${info.passengers}">
													<c:out value="${passenger2.ticketNumber}" />
													</br>
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
												<c:out value="${info.statement.platComAccount.platform.name}" />			
											</td>
											<td>
												<c:out value="${info.subPnr}" />
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
												<c:out value="${info.statement.unsettledAccount}" />
											</td>
											<td>
												 <a   onclick="showDiv('<c:out value='${info.id}' />','<c:out value='${info.statement.id}' />','<c:out value='${info.statement.unsettledAccount}'/>','<c:out value='${info.documentPrice }'/>','<c:out value='${info.insurancePrice }'/>','<c:out value='${info.groupMarkNo }'/>')"  href="#">                    
		                       						 确认收回</a>
												<br>
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
		</div>
		<div id="dialog" title="确认回收">
	<p id="validateTips"></p>
	<form action="../transaction/statement.do"  name="statement" method="post">
	<fieldset>	   
	    <table>
	    <tr>
	     <td><label for="ticketPrice">订单金额</label></td>
	     <td><input type="text" name="ticketPrice" id="ticketPrice" class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
		    <tr>
	     <td><label for="documentPrice">行程单费用</label></td>
	     <td><input type="text" name="documentPrice" id="documentPrice" class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
		   
		 <tr>
	     <td><label for="insurancePrice">保险费</label></td>
	     <td><input type="text" name="insurancePrice" id="insurancePrice" class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
	    
	    <tr>
	     <td><label for="">收回金额</label></td>	     
	     <td>	     
	     <input type="hidden" name="statementId" id="statementId"/>
	     <input type="hidden" name="airticketOrderId" id="airticketOrderId">
	     <input type="hidden" name="groupMarkNo" id="groupMarkNo">
	     <input type="text" name="actualAmount" id="actualAmount"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
		</table>
	</fieldset>
	</form>
	
</div>
	</body>
</html>
