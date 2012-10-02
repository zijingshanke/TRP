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
		<link rel="stylesheet" href="../_js/development-bundle/demos/demos.css" type="text/css"></link>

		
		<script type="text/javascript">
			$(function(){
				var totlePrice =document.forms[0].totlePrice.value;
				var tPrice = 1*totlePrice/2;
				$("#ticketPrice").val(tPrice);
				 
				//出团总人数
				var adultCount =document.forms[0].adultCount.value;
				var childCount =document.forms[0].childCount.value;
				var babyCount =document.forms[0].babyCount.value;
				var totleCount = 1*adultCount + 1*childCount + 1*babyCount;
				$("#totlePernson").val(totleCount);
				
			});
		</script>
		
		<script type="text/javascript">
			function updateAirticketOrderCheck()
				{
					
					var startPoint =document.forms[0].startPoint.value;
					var endPoint =document.forms[0].endPoint.value;
					var flightCode =document.forms[0].flightCode.value;
					var boardingTime =document.forms[0].boardingTime.value;
					var discount =document.forms[0].discount.value;
					
					if(startPoint == "")
					{
						alert("航段出发地不能为空!");
						return false;
					} 
					if(endPoint == "")
					{
						alert("航段目的地不能为空!");
						return false;
					} 
					if(flightCode == "")
					{
						alert("航班号不能为空!");
						return false;
					}
					if(boardingTime == "")
					{
						alert("出发时间不能为空!");
						return false;
					}
					if(discount == "")
					{
						alert("折扣不能为空!");
						return false;
					}
					
					document.forms[0].action="<%=path %>/airticket/airticketOrder.do?thisAction=updateAirticketOrder";
					document.forms[0].submit();
				}
			
		</script>
			
		
			<script type="text/javascript">
		$(function() {
		
			var insurancePrice = $("#insurancePrice"),
			documentPrice = $("#documentPrice"),
			
			
			
			allFields = $([]).add(insurancePrice).add(documentPrice),
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
			height: 500,
			width:800,
			modal: true,
		
			close: function() {
				allFields.val('').removeClass('ui-state-error');
			}
		});
		
		$("#dialogStatement").dialog({
			bgiframe: true,
			autoOpen: false,
			//height: 500,
			//width:800,
			modal: true,
		
			close: function() {
				allFields.val('').removeClass('ui-state-error');
			}
		});
		
		$("#dialogStatement").dialog({//结算修改
			bgiframe: true,
			autoOpen: false,
			height: 500,
			width:800,
			modal: true,
		
			close: function() {
				allFields.val('').removeClass('ui-state-error');
			}
		});
		
		
		$('#create-user').click(function() {
			$('#dialog').dialog('open');
		})
		
		$('#create-user').click(function() {//结算修改
			$('#dialogStatement').dialog('open');
		})
		
	});
	
	
	function showDiv(airticketOrderId){
	 
	  $("#airticketOrderId").val(airticketOrderId);//利润
	  $('#dialog').dialog('open');
	
	}
	 
	function showStaDiv(statementNo,platComAccount,type,status,actualAmount,unsettledAccount,commission,rakeOff,totalAmount,id,airticketOrderId){//结算修改
	 
	  $("#txt_StatementNo").val(statementNo);
	  $("#txt_platComAccount").val(platComAccount);
	  $("#txt_Type").val(type);
	  $("#txt_Status").val(status);
	  $("#txt_ActualAmount").val(actualAmount);
	  $("#txt_UnsettledAccount").val(unsettledAccount);
	  $("#txt_Commission").val(commission);
	  $("#txt_RakeOff").val(rakeOff);
	  $("#txt_TotalAmount").val(totalAmount);
	  $("#statementId").val(id);
	   $("#airOrderId").val(airticketOrderId);
	  $('#dialogStatement').dialog('open');
	
	}
	
	</script>
		
	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
				<html:form action="/airticket/airticketOrder.do" method="post" >					
					<table width="100%" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td width="10" height="10" class="tblt"></td>
							<td height="10" class="tbtt"></td>
							<td width="10" height="10" class="tbrt"></td>
						</tr>
						<tr>
							<td width="10" class="tbll"></td>
							<td valign="top" class="body">
								<c:import url="../_jsp/mainTitle.jsp?title1=票务管理&title2=团队订单管理"
									charEncoding="UTF-8" />
								<table cellpadding="0" cellspacing="0" border="0" id="table1"
									class="dataList">
									<tr>
										<th>
											<div>
												购票客户
											</div>
										</th>
										<th>
											<div>
												客票类型
											</div>
										</th>
										<th>
											<div>
												交易类型
											</div>
										</th>
										<th>
											<div>
												出团总人数
											</div>
										</th>
										<th>
											<div>
												总票面价
											</div>
										</th>
										<th>
											<div>
												总机建税
											</div>
										</th>
										<th>
											<div>
												总燃油税
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
												<html:hidden property="id"  value="${airticketOrder.id}" />
												<html:hidden property="adultCount" value="${airticketOrder.adultCount}" />
												<html:hidden property="childCount" value="${airticketOrder.childCount}" />
												<html:hidden property="babyCount" value="${airticketOrder.babyCount}" />
												<html:select property="agentNo" name="airticketOrder" value="${airticketOrder.agent.id}" styleClass="colorblue2 p_5"
													style="width:150px;">
													<c:forEach items="${agentList}" var="a">
														<html:option value="${a.id}"><c:out value="${a.name}"/></html:option>
													</c:forEach>
												</html:select>
											</td>
											<td>											
												<html:select property="drawer" name="airticketOrder" value="${airticketOrder.drawer}"  styleClass="colorblue2 p_5"
													style="width:150px;">
													<html:option value="B2B网电">B2B网电</html:option>
													<html:option value="B2C散客">B2C散客</html:option>
													<html:option value="倒票组">倒票组</html:option>
												</html:select>
											</td>
											<td>												
												<html:select property="tranType" name="airticketOrder"  styleClass="colorblue2 p_5" value="${airticketOrder.tranType}"
													style="width:150px;">
													<html:option value="1">销售</html:option>
													<html:option value="2">退票</html:option>
												</html:select>
											</td>
											<td>
												<html:text property="totlePernson" styleId="totlePernson" name="airticketOrder" value="${airticketOrder.totlePernson}" styleClass="colorblue2 p_5"
													style="width:50px;" />
											</td>
											<td>
												<html:text property="totlePrice" name="airticketOrder" value="${airticketOrder.ticketPrice}" styleClass="colorblue2 p_5"
													style="width:50px;" />
											</td>
											<td>
												<html:text property="airportPrice" name="airticketOrder" value="${airticketOrder.airportPrice}" styleClass="colorblue2 p_5"
													style="width:50px;" />
											</td>
											<td>
												<html:text property="fuelPrice" name="airticketOrder" value="${airticketOrder.fuelPrice}" styleClass="colorblue2 p_5"
													style="width:50px;" />
											</td>
											<td>
												<input type="button" value="保存修改" onclick="updateAirticketOrderCheck()"/>
											</td>
										
									</tr>									
								</table><br />
								
								
								<br />
								航程信息：
								<table cellpadding="0" cellspacing="0" border="0"
									class="dataList">
									<tr>
										<th>
											<div>
												航段
											</div>
										</th>
										<th>
											<div>
												航班号
											</div>
										</th>
										<th>
											<div>
												起飞时间
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
										<th style="display: none;">
											<div>
												票面价
											</div>
										</th>
										<th style="display: none;">
											<div>
												机建税(成人)
											</div>
										</th>
										<th style="display: none;">
											<div>
												燃油税(成人)
											</div>
										</th>
										<th style="display: none;">
											<div>
												机建税(儿童)
											</div>
										</th>
										<th style="display: none;">
											<div>
												燃油税(儿童)
											</div>
										</th>
										<th style="display: none;">
											<div>
												机建税(婴儿)
											</div>
										</th>
										<th style="display: none;">
											<div>
												燃油税(婴儿)
											</div>
										</th>
										<th>
											<div>
												操作
											</div> 
										</th>
									</tr>
									<c:forEach items="${flightList}" var="f">
									<tr>
										<td>
											<html:hidden property="flightId" name="airticketOrder" value="${f.id}"/>
											<html:text property="startPoint" name="airticketOrder" value="${f.startPoint}" styleClass="colorblue2 p_5"
												style="width:50px;" />
											<html:text property="endPoint" name="airticketOrder" value="${f.endPoint}" styleClass="colorblue2 p_5"
												style="width:50px;" />
										</td>
										<td>
											<html:text property="flightCode" name="airticketOrder" value="${f.flightCode}" styleClass="colorblue2 p_5"
												style="width:100px;" />
										</td>
										<td>
											<html:text property="boardingTime" name="airticketOrder"  value="${f.boardingTime}" styleClass="colorblue2 p_5"
												style="width:150px;"  onclick="popUpCalendar(this, this);"  readonly="true"/>
										</td>
										<td>
											<html:text property="flightClass" name="airticketOrder" value="${f.flightClass}" styleClass="colorblue2 p_5"
												style="width:50px;" />
										</td>

										<td>
											<html:text property="discount" name="airticketOrder" value="${f.discount}" styleClass="colorblue2 p_5"
												style="width:50px;" />
										</td>
										
										<td style="display: none;">
											<html:text property="ticketPrice" styleId="ticketPrice" name="airticketOrder" value="${airticketOrder.ticketPrice}" styleClass="colorblue2 p_5"
												style="width:60px;" />
										</td>
										<td style="display: none;">
											<html:text property="adultAirportPrice" styleId="adultAirportPrice" name="airticketOrder" value="${airticketOrder.adultAirportPrice}" styleClass="colorblue2 p_5"
												style="width:60px;" />
										</td>
										<td style="display: none;">
											<html:text property="adultFuelPrice" styleId="adultFuelPrice" name="airticketOrder" value="${airticketOrder.adultFuelPrice}" styleClass="colorblue2 p_5"
												style="width:60px;" />
										</td>
										<td style="display: none;">
											<html:text property="childAirportPrice" styleId="childAirportPrice" name="airticketOrder" value="${airticketOrder.childAirportPrice}" styleClass="colorblue2 p_5"
												style="width:60px;" />
										</td>
										<td style="display: none;">
											<html:text property="childfuelPrice" styleId="childfuelPrice" name="airticketOrder" value="${airticketOrder.childfuelPrice}" styleClass="colorblue2 p_5"
												style="width:60px;" />
										</td>
										<td style="display: none;">
											<html:text property="babyAirportPrice" styleId="babyAirportPrice" name="airticketOrder" value="${airticketOrder.babyAirportPrice}" styleClass="colorblue2 p_5"
												style="width:60px;" />
										</td>
										<td style="display: none;">
											<html:text property="babyfuelPrice" styleId="babyfuelPrice" name="airticketOrder" value="${airticketOrder.babyfuelPrice}" styleClass="colorblue2 p_5"
												style="width:60px;" />
										</td>
										<td>
											<input type="button" value="保存修改" onclick="updateAirticketOrderCheck()"/>
										</td>
									</tr>	
									</c:forEach>	
								</table>
								<br /><br />
								订单信息:
								<input name="label" type="button" class="button1" value="添 加"
									onclick="addUser();">
								<table cellpadding="0" cellspacing="0" border="0" id="table1"
									class="dataList">
									<tr>
										<th>
											<div>
												订单号
											</div>
										</th>
										<th>
											<div>
												订单金额
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
											<html:text property="airOrderNo" styleId="airOrderNo" name="airticketOrder" value="${airticketOrder.airOrderNo}" styleClass="colorblue2 p_5"
												style="width:150px;" />
										</td>
										<td>
											<html:text property="totalAmount" styleId="totalAmount" name="airticketOrder" value="${airticketOrder.statement.totalAmount}" styleClass="colorblue2 p_5"
												style="width:100px;" />
										</td>
										<td>
											<input type="button" value="保存修改" onclick="updateAirticketOrderCheck()"/>
										</td>
									</tr>
								</table>
								<br /><br />
								
								利润统计
								<c:if test="${statementSize==2}">
									<input name="btnTotal" type="button" disabled="disabled" onclick="showDiv('<c:out value="${airticketOrder.id}"/>');"  value="添 加" />
								</c:if>
								<c:if test="${statementSize==0 || statementSize==1}">
									<input name="btnTotal" type="button" onclick="showDiv('<c:out value="${airticketOrder.id}"/>');"  value="添 加" />
								</c:if>
								<table width="100%" cellpadding="0" cellspacing="0" border="0"
									class="dataList">
									<tr>
										<th>
											<div>
												结算单号
											</div>
										</th>
										<th>
											<div>
												类型
											</div>
										</th>
										<th>
											<div>
												平台
											</div>
										</th>
										<th>
											<div>
												帐号类型
											</div>
										</th>
										<th>
											<div>
												结算状态
											</div>
										</th>										
										<th>
											<div>
												实收款
											</div>
										</th>
										<th>
											<div>
												未结款
											</div>
										</th>
										<th>
											<div>
												现返佣金
											</div>
										</th>
										<th>
											<div>
												后返佣金
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
								<c:forEach items="${statementList}" var="s">
									<tr>
										<td>
											<c:out value="${s.statementNo}" />
										</td>
										<td>
											<c:out value="${s.typeInfo}" />
										</td>
										<td>
											<c:out value="${s.platComAccount.platform.name}"></c:out>
										</td>
										<td>
											<c:if test="${!empty s.platComAccount.account.name}">
												<c:out value="${s.platComAccount.account.name}" />
											</c:if>
										</td>
										<td>
											<c:out value="${s.statusInfo}" />
										</td>	
										<td>
											<c:out value="${s.actualAmount}" />
										</td>
										<td>
											<c:out value="${s.unsettledAccount}" />
										</td>
										<td>
											<c:out value="${s.commission}" />
										</td>
										<td>
											<c:out value="${s.rakeOff}" />
										</td>
										<td>
											<c:out value="${s.totalAmount}" />
										</td>
										
										<td>
											<a href="#" onclick="showStaDiv('<c:out value="${s.statementNo}" />','<c:out value="${s.platComAccount.account.name}" />',
											'<c:out value="${s.typeInfo}" />','<c:out value="${s.statusInfo}" />','<c:out value="${s.actualAmount}" />',
											'<c:out value="${s.unsettledAccount}" />','<c:out value="${s.commission}" />','<c:out value="${s.rakeOff}" />',
											'<c:out value="${s.totalAmount}" />','<c:out value="${s.id}"/>','<c:out value="${airticketOrder.id}"/>')">[编辑]</a>
										</td>
									</tr>	
								  </c:forEach>								
								</table>
								<table width="100%" cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td>
											<input name="label" type="button" class="button1" value="返 回"
												onclick="returnPage();">
										</td>

									</tr>
								</table>
							</td>
						</tr>
					</table>
				</html:form>
			</div>
		</div>
		
<div id="dialog" title="利润汇总统计" >
	
	<form action="../airticket/airticketOrder.do?thisAction=insertTeamTradingOrder" name="airticketOrder" method="post" onsubmit="return btn();">
	   <table id="list">
                <tbody><tr><td colspan="3">___________________________________________对客户_____________________________________________</td></tr>
                <tr>
                    <td>应付出团代理费（现返）：</td>
                    <td align="left">
                    	<input type="hidden" name="airticketOrderId" id="airticketOrderId" />
                        <input name="txtAgentFeeTeams" id="txtAgentFeeTeams" class="colorblue2 p_5" readonly="readonly" type="text" style="width: 70px;">
                        =(票面价&nbsp;&nbsp;&nbsp;<input name="txtTicketPriceTotals" id="txtTicketPriceTotals" class="colorblue2 p_5" value="1100" type="text" style="width: 70px;">
                        +<span style="color: Green;">多收票价<input name="txtAmountMore" id="txtAmountMore" class="colorblue2 p_5" onkeyup="amountMoreCheck();" style="width: 70px; color: Green;" type="text"></span>
                        )*<span style="color: Green;">返点&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="txtAgents" id="txtAgents" onkeyup="agentsCheck();" class="colorblue2 p_5" style="width: 70px; color: Green;" type="text"></span></td>
                </tr>
                <tr>
                    <td><span style="color: Green;">应付出团代理费（未返）：</span></td>
                    <td align="left">
                        <input name="txtUnAgentFeeTeams" id="txtUnAgentFeeTeams" onkeyup="unAgentFeeTeamsCheck();" class="colorblue2 p_5" style="width: 70px; color: Green;" type="text">
                        <span style="color: Green;">&nbsp;&nbsp;备注：&nbsp;&nbsp;&nbsp;&nbsp;</span><input name="txtRemark" id="txtRemark" class="colorblue2 p_5" style="width: 300px; color: Green;" class="TextUnderLine" type="text">
                    </td>
                </tr>
                <tr>
                    <td>应收票款：</td>
                    <td align="left">
                        <input name="txtSAmount" id="txtSAmount"  class="colorblue2" type="text" style="width: 70px;">
                        =(票面价&nbsp;&nbsp;&nbsp;<input name="txtTicketPriceTotal" id="txtTicketPriceTotal" style="width: 70px;" class="colorblue2 p_5" readonly="readonly" value="1100" type="text">
                         +<span>多收票价<input name="txtAmountMores" id="txtAmountMores" style="width: 70px;" class="colorblue2 p_5" readonly="readonly" type="text"></span>
                        )-现返<input name="txtAgentFeeTeam" id="txtAgentFeeTeam" class="colorblue2 p_5" readonly="readonly" style="width: 70px;" type="text">
                        +<span style="color: Green;">多收税<input name="txtTaxMore" id="txtTaxMore" onkeyup="taxMoreCheck();" class="colorblue2 p_5" style="width: 70px;" type="text"></span>
                       +<span style="color: Green;">收退票手续费：</span><input name="txtSourceTGQFee" id="txtSourceTGQFee" onkeyup="sourceTGQFeeCheck();" class="colorblue2 p_5" style="color: Green; width: 70px;" type="text">
                  </td>
                </tr>
                    <tr>
                    <td>实收票款：</td>
                    <td align="left"> 
                    <input name="txtTotalAmount" id="txtTotalAmount" class="colorblue2 p_5" type="text" style="width: 70px;">
                    =应收票款<input name="txtSAmount2" id="txtSAmount2" class="colorblue2 p_5" readonly="readonly" type="text" style="width: 70px;">
                    +机场税&nbsp;&nbsp;&nbsp;<input name="txtTax2" id="txtTax2" class="colorblue2 p_5" readonly="readonly" value="130" type="text" style="width: 70px;">
                    </td>                
                </tr>
                <tr><td colspan="3" style="width: 750px;"></td></tr>
                <tr><td colspan="3">__________________________________________对航空公司___________________________________________</td></tr> 
                <tr>
                    <td>团毛利润：</td>
                    <td align="left">
                        <input name="txtProfits" id="txtProfits" class="colorblue2 p_5" readonly="readonly" type="text" style="width: 70px;">
                        =票面价&nbsp;&nbsp;&nbsp;&nbsp;<input name="txtTicketPriceTotal3" id="txtTicketPriceTotal3" class="colorblue2 p_5" readonly="readonly" value="1100" type="text" style="width: 70px;">
                        *<span style="color: Green;">返点</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="txtAgentT" id="txtAgentT" onkeyup="agentTCheck();" class="colorblue2 p_5" style="width: 70px; color: Green;" type="text">
                        -手续费<input name="txtCharge" id="txtCharge" onkeyup="chargeCheck();" class="colorblue2 p_5" style="color: Green; width: 70px;" type="text">
                    </td>
                </tr>
                <tr>
                    <td>月底返代理费：</td>
                    <td align="left"> 
                        <input name="txtAgentFeeCarrier" id="txtAgentFeeCarrier" class="colorblue2 p_5" readonly="readonly" type="text" style="width: 70px;">
                        =<span>票面价</span>&nbsp;&nbsp;&nbsp;&nbsp;<input name="txtTicketPriceTotal1" id="txtTicketPriceTotal1" class="colorblue2 p_5" readonly="readonly" value="1100" type="text" style="width: 70px;">
                        *<span style="color: Green;">月底返点</span><input name="txtAgent" id="txtAgent" onkeyup="agentCheck();" tabindex="0" style="width: 70px; color: Green;" class="colorblue2 p_5" type="text">
                  </td>
                </tr>
                   <tr>
                    <td>应付票款：</td>
                    <td align="left">
                        <input name="txtTUnAmount" id="txtTUnAmount" class="colorblue2 p_5" readonly="readonly" type="text" style="width: 70px;">
                        =票面价&nbsp;&nbsp;&nbsp;&nbsp;<input name="txtTicketPriceTotal2" id="txtTicketPriceTotal2" class="colorblue2 p_5" readonly="readonly" value="1100" type="text" style="width: 70px;">
                        -团毛利润<input name="txtProfit" id="txtProfit" class="colorblue2 p_5" readonly="readonly" type="text" style="width: 70px;">
                        +<span style="color: Green;">付退票手续费：</span><input name="txtTargetTGQFee" id="txtTargetTGQFee" onkeyup="targetTGQFee();" style="color: Green; width: 70px;" class="colorblue2 p_5" type="text"></td>
                </tr>
                <tr>
                    <td>实付票款：</td>
                    <td align="left">
                        <input name="txtTAmount" id="txtTAmount" class="colorblue2 p_5" style="width: 70px; color: Red;" readonly="readonly" type="text" />
                        =应付票款<input name="txtTUnAmount2" id="txtTUnAmount2" class="colorblue2 p_5" readonly="readonly" type="text" style="width: 70px;" />
                        +机场税&nbsp;&nbsp;&nbsp;<input name="txtTax" id="txtTax" class="colorblue2 p_5" value="130" type="text" style="width: 70px;" />
                        </td>
                </tr>
                <tr>
                    <td>订单金额：</td>
                    <td align="left">
                    <input name="txtAmountSum" id="txtAmountSum" class="colorblue2 p_5" readonly="readonly" style="color: Red;" value="300.00" type="text" style="width: 70px;" />
                    =(<input name="txtAmountItem" id="txtAmountItem" class="colorblue2 p_5" style="width: 200px;" readonly="readonly" value="100.00+200.00" type="text" />)</td>
                </tr>
                <tr><td colspan="3"></td></tr>
                <tr><td colspan="3">___________________________________________利润_______________________________________________</td></tr>
                  <tr>
                    <td><span style="color: Green;">退票利润：</span></td>
                    <td align="left">
                    <input name="txtTProfit" id="txtTProfit" onkeyup="tProfitCheck();" style="width: 70px; color: Green;" class="colorblue2 p_5" type="text">
                  </td>
                </tr>
                <tr>
                    <td>净利合计：</td>
                    <td align="left">  
                    <input name="txtTotalProfit" id="txtTotalProfit" class="colorblue2 p_5" readonly="readonly" type="text">
                    =团毛利润+退票利润+多收票款+多收税款-应付出团代理费</td>
                     
                </tr>  
                <tr>
                    <td></td>
                    
                    <td><br>注：只用填写绿色字体部分，其他数据自动计算得到！<br></td>
                </tr>
                 <tr>
                    <td colspan="2" align="center"> 
                        <input name="btnAdd" value="提 交" type="submit" onclick="btnair()"/> &nbsp;&nbsp;&nbsp;&nbsp; 
                        <input value="重 置" id="btnReset" type="reset"> &nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                </tr>

          </tbody>
          </table>

	</form>
	
</div>

<div id="dialogStatement" title="修改结算金额" >
<form action="../airticket/airticketOrder.do?thisAction=updateTeamStatement" name="airticketOrder" method="post" onsubmit="return updateStatement();">
	<table align="center">
		<tr style="display: none;">
			<td>结算单号</td>
			<td>
				<input type="hidden" name="airticketOrderId" id="airOrderId" />
				<input type="hidden" name="statementId" id="statementId"/>
				<input type="text" name="txt_StatementNo" id="txt_StatementNo" disabled="disabled"  class="text ui-widget-content ui-corner-all"/>
			</td>
		</tr>
		<tr>
			<td>帐号类型</td>
			<td>
				<input type="text" name="txt_platComAccount" id="txt_platComAccount" disabled="disabled" class="text ui-widget-content ui-corner-all"/>
			</td>
		</tr>
		<tr style="display: none;">
			<td>类型</td>
			<td>
				<select id="txt_Type" disabled="disabled" class="text ui-widget-content ui-corner-all">
					<option value="1">支出</option>
					<option value="2">收入</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>结算状态</td>
			<td>
				<select id="txt_Status" disabled="disabled" class="text ui-widget-content ui-corner-all">
					<option value="0">未结算</option>
					<option value="1">已结算</option>
					<option value="2">部分结算</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>总金额</td>
			<td>
				<input type="text" name="txt_TotalAmount" id="txt_TotalAmount" onkeyup="totalAmountCheck();" class="text ui-widget-content ui-corner-all"/>
			</td>
		</tr>
		<tr>
			<td>实收款</td>
			<td>
				<input type="text" name="txt_ActualAmount" id="txt_ActualAmount" onkeyup="actualAmountCheck();"  class="text ui-widget-content ui-corner-all"/>
			</td>
		</tr>
		<tr>
			<td>未结款</td>
			<td>
				<input type="text" name="txt_UnsettledAccount" id="txt_UnsettledAccount" disabled="disabled" class="text ui-widget-content ui-corner-all"/>
			</td>
		</tr>
		<tr>
			<td>现返佣金</td>
			<td>
				<input type="text" name="txt_Commission" id="txt_Commission" class="text ui-widget-content ui-corner-all"/>
			</td>
		</tr>
		<tr>
			<td>后返佣金</td>
			<td>
				<input type="text" name="txt_RakeOff" id="txt_RakeOff" class="text ui-widget-content ui-corner-all"/>
			</td>
		</tr>
		<tr>
            <td colspan="2" align="center"> 
                 <input name="btnUpdate" value="提 交" class="text ui-widget-content ui-corner-all"  type="submit" onclick="btnUpdate()"/> &nbsp;&nbsp;&nbsp;&nbsp; 
                 <input value="重 置" id="btnReset" class="text ui-widget-content ui-corner-all" type="reset"> &nbsp;&nbsp;&nbsp;&nbsp;
             </td>
         </tr>
	</table>
</form>

</div>

<script type="text/javascript">
	function updateStatement()//修改结算金额
	{
		var pan = /^[0-9]+$/;
		var txt_TotalAmount = $("#txt_TotalAmount").val();
		var txt_ActualAmount =$("#txt_ActualAmount").val();
		
		if(!pan.test(txt_TotalAmount))
		{
			alert("总金额输入格式不正确!");
			return false;
		}
		if(!pan.test(txt_ActualAmount))
		{
			alert("实书款金额输入格式不正确!");
			return false;
		}
	}
</script>

<script type="text/javascript">
	function btn()//添加利润统计
	{
		var pan = /^[0-9]+$/;
		var txtAmountMores =$("#txtAmountMores").val();
		if(!pan.test(txtAmountMores))
		{
			alert("多收票价输入格式不正确!");
			return false;
		}
	}
</script>


<script type="text/javascript">
	function totalAmountCheck()//总金额
	{
		$("#txt_UnsettledAccount").val(1*($("#txt_TotalAmount").val()) - 1*($("#txt_ActualAmount").val()));//未结款=总金额-实收款
		if(1*($("#txt_TotalAmount").val())> (1*($("#txt_ActualAmount").val())) && 1*($("#txt_ActualAmount").val())>0)//总金额>实收款
		{
			$("#txt_Status").val(2);//部分结算
		}
		if(1*($("#txt_TotalAmount").val())<= 1*($("#txt_ActualAmount").val()))//总金额<=实收款
		{
			$("#txt_Status").val(1);//已结算
		}
		if(1*($("#txt_ActualAmount").val()) == 0)
		{
			$("#txt_Status").val(0);//未结算
		}
	}
	function actualAmountCheck()//实收款
	{
		$("#txt_UnsettledAccount").val(1*($("#txt_TotalAmount").val()) - 1*($("#txt_ActualAmount").val()));//未结款=总金额-实收款
		if(1*($("#txt_TotalAmount").val())> (1*($("#txt_ActualAmount").val())) && 1*($("#txt_ActualAmount").val())>0)//总金额>实收款
		{
			$("#txt_Status").val(2);//部分结算
		}
		if(1*($("#txt_TotalAmount").val())<= 1*($("#txt_ActualAmount").val()))//总金额<=实收款
		{
			$("#txt_Status").val(1);//已结算
		}
		if(1*($("#txt_ActualAmount").val()) == 0)
		{
			$("#txt_Status").val(0);//未结算
		}
	}
</script>

<script type="text/javascript">

	function amountMoreCheck()//多收票价(对客户)
	{
		///////////对客户//////////////
		
		$("#txtAgentFeeTeams").val(Math.round((1*($("#txtTicketPriceTotals").val()) + 1*($("#txtAmountMore").val())) * (1*($("#txtAgents").val()))));//应付出团代理费（现返）=(票面价+票面价)*返点
		$("#txtAmountMores").val($("#txtAmountMore").val());//多收票价2
		$("#txtAgentFeeTeam").val($("#txtAgentFeeTeams").val());//现返
		$("#txtSAmount").val(1*($("#txtTicketPriceTotal2").val()) + 1*($("#txtAmountMores").val()) -1*($("#txtAgentFeeTeam").val()) + 1*($("#txtTaxMore").val()) + 1*($("#txtSourceTGQFee").val()));//应收票价 =票面价+多收票价-现返+多收税 +收退票手续费：
		$("#txtSAmount2").val($("#txtSAmount").val());//应收票价2
		$("#txtTotalAmount").val(1*($("#txtSAmount2").val()) +1*($("#txtTax2").val()) + 1*($("#txtTaxMore").val()) + 1*($("#txtSourceTGQFee").val()))//实收票款=应收票款 + 机场税
		
		//////////对航空公司//////////
		$("#txtProfits").val(Math.round(1*($("#txtTicketPriceTotal3").val()) * (1*($("#txtAgentT").val())) - 1*($("#txtCharge").val())));//团毛利润=票面价*返点-手续费
		$("#txtAgentFeeCarrier").val(Math.round(1*($("#txtTicketPriceTotal1").val()) * (1*($("#txtAgent").val()))));//月底返代理费=票面价*月底返点
		$("#txtProfit").val($("#txtProfits").val());//团毛利润2
		$("#txtTUnAmount").val(1*($("#txtTicketPriceTotal2").val()) - 1*($("#txtProfit").val()) + 1*($("#txtTargetTGQFee").val()));//应付票款=票面价-团毛利润2+付退票手续费
		$("#txtTUnAmount2").val($("#txtTUnAmount").val());//应付票款2
		$("#txtTAmount").val(1*($("#txtTUnAmount2").val()) + 1*($("#txtTax").val()))//实付票款2=应付票款2  + 机场税2
		
		/////////利润///////////
		$("#txtTotalProfit").val(Math.round(1*($("#txtProfits").val()) + 1*($("#txtTProfit").val())  + 1*($("#txtTaxMore").val()) + 1*($("#txtAmountMore").val()) -1*($("#txtAgentFeeTeams").val()) - 1*($("#txtUnAgentFeeTeams").val()))); //净利合计=团毛利润+退票利润+多收票款+多收税款-应付出团代理费
	}
	
	function agentsCheck()//返点(客户)
	{
		///////////对客户//////////////
		$("#txtAgentFeeTeams").val(Math.round((1*($("#txtTicketPriceTotals").val()) + 1*($("#txtAmountMore").val())) * 1*($("#txtAgents").val())));
		$("#txtAgentFeeTeam").val($("#txtAgentFeeTeams").val());//现返 
		$("#txtSAmount").val(1*($("#txtTicketPriceTotal2").val()) + 1*($("#txtAmountMore").val()) -1*($("#txtAgentFeeTeam").val()) + 1*($("#txtTaxMore").val()) + 1*($("#txtSourceTGQFee").val()));//应收票价 =票面价+多收票价-现返+多收税 +收退票手续费：
		$("#txtSAmount2").val($("#txtSAmount").val());//应收票款2
		$("#txtTotalAmount").val(1*($("#txtSAmount2").val()) +1*($("#txtTax2").val()))//实收票款=应收票款 + 机场税
		
		//////////对航空公司//////////
		
		/////////利润///////////
		$("#txtTotalProfit").val(Math.round(1*($("#txtProfits").val()) + 1*($("#txtTProfit").val())  + 1*($("#txtTaxMore").val()) + 1*($("#txtAmountMore").val()) - 1*($("#txtAgentFeeTeams").val()) - 1*($("#txtUnAgentFeeTeams").val())));	//净利合计=团毛利润+退票利润+多收票款+多收税款-应付出团代理费(金额为0略)
	}
	
	function unAgentFeeTeamsCheck() //应付出团代理费（未返)客户
	{
		/////////利润///////////
		$("#txtTotalProfit").val(Math.round(1*($("#txtProfits").val()) + 1*($("#txtTProfit").val())  + 1*($("#txtTaxMore").val()) + 1*($("#txtAmountMore").val()) - 1*($("#txtAgentFeeTeams").val()) - 1*($("#txtUnAgentFeeTeams").val())));	//净利合计=团毛利润+退票利润+多收票款+多收税款-应付出团代理费(金额为0略)
	}
	
	function taxMoreCheck()//多收税(客户)
	{
		///////////对客户//////////////
		$("#txtSAmount").val(1*($("#txtTicketPriceTotal2").val()) + 1*($("#txtAmountMore").val()) -1*($("#txtAgentFeeTeam").val()) + 1*($("#txtTaxMore").val()) + 1*($("#txtSourceTGQFee").val()));//应收票价 =票面价+多收票价-现返+多收税 +收退票手续费：
		$("#txtSAmount2").val($("#txtSAmount").val());
		$("#txtTotalAmount").val(1*($("#txtSAmount2").val()) +1*($("#txtTax2").val()))//实收票款=应收票款 + 机场税
		
		/////////利润///////////
		$("#txtTotalProfit").val(Math.round(1*($("#txtProfits").val()) + 1*($("#txtTProfit").val())  + 1*($("#txtTaxMore").val()) + 1*($("#txtAmountMore").val()) - 1*($("#txtAgentFeeTeams").val()) - 1*($("#txtUnAgentFeeTeams").val())));	//净利合计=团毛利润+退票利润+多收票款+多收税款-应付出团代理费(金额为0略)
	}
	
	function sourceTGQFeeCheck()//收退票手续费(客户)
	{
		taxMoreCheck();//多收税(客户)
	}
	
	function agentTCheck() //返点(对航空公司)
	{
		$("#txtProfits").val(Math.round(1*($("#txtTicketPriceTotal3").val()) * (1*($("#txtAgentT").val())) -1*($("#txtCharge").val())));//团毛利润=票面价*返点-手续费
		 $("#txtAgentFeeCarrier").val(Math.round(1*($("#txtTicketPriceTotal1").val()) * 1*($("#txtAgent").val())));//月底返代理费=票面价*月底返点
		$("#txtProfit").val($("#txtProfits").val());//团毛利润2
		$("#txtTUnAmount").val(1*($("#txtTicketPriceTotal2").val()) - 1*($("#txtProfits").val()) + 1*($("#txtTargetTGQFee").val()));//应付票款=票面价-团毛利润+付退票手续费
		$("#txtTUnAmount2").val($("#txtTUnAmount").val());//应付票款2
		$("#txtTAmount").val(1*($("#txtTUnAmount").val()) + 1*($("#txtTax").val()));//实付票款=应付票款+机场税 
		/////////利润///////////
		$("#txtTotalProfit").val(1*($("#txtProfits").val()) + 1*($("#txtTProfit").val())  + 1*($("#txtTaxMore").val()) + 1*($("#txtAmountMore").val()) - 1*($("#txtAgentFeeTeams").val()) - 1*($("#txtUnAgentFeeTeams").val()))	//净利合计=团毛利润+退票利润+多收票款+多收税款-应付出团代理费(金额为0略)
	}  
	
	function chargeCheck() //手续费(对航空公司)
	{
		agentTCheck();
	}
	
	function agentCheck() //月底返点(对航空公司)
	{
		 $("#txtAgentFeeCarrier").val(Math.round(1*($("#txtTicketPriceTotal1").val()) * 1*($("#txtAgent").val())));//月底返代理费=票面价*月底返点
	}
	
	function targetTGQFee() //付退票手续费(对航空公司)
	{
		agentTCheck();
	}
	
	function tProfitCheck() //退票利润
	{
		/////////利润///////////
		$("#txtTotalProfit").val(Math.round(1*($("#txtProfits").val()) + 1*($("#txtTProfit").val())  + 1*($("#txtTaxMore").val()) + 1*($("#txtAmountMore").val()) - 1*($("#txtAgentFeeTeams").val()) - 1*($("#txtUnAgentFeeTeams").val())));	//净利合计=团毛利润+退票利润+多收票款+多收税款-应付出团代理费(金额为0略)
	}
</script>
		
	</body>
</html>
