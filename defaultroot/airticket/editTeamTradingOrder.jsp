<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="utf-8"%>

<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
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
					
				var num2 ='<c:out value="${flightSize}"/>';
				
			function totlePernsonCheck()//团队总人数
			{
				
				var teamAdultCount =$("#teamAdultCount").val();
				var teamchildCount =$("#teamChildCount").val();
				var teambabyCount =$("#teamBabyCount").val();
				var teamTotle =1*teamAdultCount + 1*teamchildCount + 1*teambabyCount;
				document.getElementById("totlePernson").innerHTML=teamTotle;
				
				
				ticketPriceCheck();//总票面价
				airportPriceCheck();//总机建税 
				fuelPriceCheck();// 总燃油税
			}
			
			
			function ticketPriceCheck()//总票面价
			{
				var teamAdultCount =$("#teamAdultCount").val();
				var teamchildCount =$("#teamChildCount").val();
				var teambabyCount =$("#teamBabyCount").val();
				var totlePerson =1*teamAdultCount + 1*teamchildCount + 1*teambabyCount;
				var ticketPrice1 = $("#ticketPrice1").val();
				var totle=1*ticketPrice1;
				for(var i=2;i<=num2;i++)
				{			
					totle+= 1*($("#ticketPrice"+i).val());
				}
					totleTick=totle*(1*totlePerson)//总票面价*总人数
				$("#totlePrice").val(totleTick);
			}
			
			function airportPriceCheck()//总机建税 
			{
				var teamAdultCount =$("#teamAdultCount").val();
				var teamchildCount =$("#teamChildCount").val();
				var teambabyCount =$("#teamBabyCount").val();
				
				var adultAirportPrice1 = $("#adultAirportPrice1").val();
				var childAirportPrice1 = $("#childAirportPrice1").val();
				var babyAirportPrice1 = $("#babyAirportPrice1").val();
				var aPrice =1*adultAirportPrice1*(1*teamAdultCount) + 1*childAirportPrice1*(1*teamchildCount) + 1*babyAirportPrice1*(1*teambabyCount);
				
				for(var i=2;i<=num2;i++)//(先计算出新增加一行的数据)
				{
					aPrice+= 1*($("#adultAirportPrice"+i).val())*(1*teamAdultCount) + 1*($("#childAirportPrice"+i).val())*(1*teamchildCount) + 1*($("#babyAirportPrice"+i).val())*(1*teambabyCount);
				}
				totleAprice=aPrice;
				$("input[name='airportPrice']").val(totleAprice);
			}
			
			function fuelPriceCheck()// 总燃油税
			{
				var teamAdultCount =$("#teamAdultCount").val();
				var teamchildCount =$("#teamChildCount").val();
				var teambabyCount =$("#teamBabyCount").val();
				
				var adultFuelPrice1 = $("#adultFuelPrice1").val();
				var childfuelPrice1 = $("#childfuelPrice1").val();
				var babyfuelPrice1 = $("#babyfuelPrice1").val();
				var fPrice =1*adultFuelPrice1*(1*teamAdultCount) + 1*childfuelPrice1*(1*teamchildCount) + 1*babyfuelPrice1*(1*teambabyCount);
				
				for(var i=2;i<=num2;i++)
				{
					fPrice+= 1*($("#adultFuelPrice"+i).val())*(1*teamAdultCount) + 1*($("#childfuelPrice"+i).val())*(1*teamchildCount) + 1*($("#babyfuelPrice"+i).val())*(1*teambabyCount);
				}
				totleFprice=fPrice;
				$("input[name='fuelPrice']").val(totleFprice);
			}
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
					
					document.forms[0].action="<%=path %>/airticket/airticketOrder.do?thisAction=updateAirticketOrder";
					document.forms[0].submit();
				}
			
		</script>
		
		
		<script type="text/javascript">//返回
			function returnPage()
			{
				document.forms[0].action="<%=path %>/airticket/listAirTicketOrder.do?thisAction=getTempAirticketOrderByticketType";
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
			height: 480,
			width:800,
			modal: true
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
	
	//添加(利润统计)
	function showDiv(airticketOrderId,totalTicketPrice,totalAirportPrice,totalFuelPrice,totalAmount,teamaddPrice,agentaddPrice){ 
	 
	  totalTickAirPrice= 1*totalAirportPrice + 1*totalFuelPrice;//机建燃油税＝机建税+燃油税
	  $("#txtTax2").val(totalTickAirPrice);//机建燃油税
	  $("#txtTax").val(totalTickAirPrice);//机建燃油税
	  
	  $("#airticketOrderId").val(airticketOrderId);
	  //-----客户------
	  $("#txtTicketPriceTotals").val(totalTicketPrice);//票面价
	  $("#txtTicketPriceTotal").val(totalTicketPrice);//票面价
	  $("#txtAmountMore").val(1*teamaddPrice + 1*agentaddPrice);//多收票价
	  //-----航空-------
	  $("#txtAmountSum").val(totalAmount);//订单金额
	  $("#txtTicketPriceTotal3").val(totalTicketPrice);//票面价
	  $("#txtTicketPriceTotal1").val(totalTicketPrice);//票面价
	  $("#txtTicketPriceTotal2").val(totalTicketPrice);//票面价
	  
	  $('#dialog').dialog('open');
	
	}  
	
	
	//编辑(利润统计)
	function showDivUpdate(airticketOrderId,overTicketPrice,overAirportfulePrice,incomeRetreatcharge,totalTicketPriceAgent,totalAirportPriceAgent,
		totalFuelPriceAgent,totalAmount,incomeRetreatchargeAvia,commissonCountAgent,commissonCountAvia,rakeoffCount,totalAirportPriceAvia,totalFuelPriceAvia,
		totalTicketPriceAvia,actualAmountAgent,actualAmountAvia,handlingChargeAvia,memo,proxyPrice,tranType,tranTypeRufund,
		totalRufundTicket,commissonRufundCount,proxyRufundPrice,incomeRufundretreatCharge,
		totalRufundAirportPrice, totalRufundFuelPrice,rufundHandlingCharge,rufundRakeoffCount) 
		
	{   
			setTimeout("amountMoreCheck()",100);//触发点
			setTimeout("agentTCheck()",100);
			setTimeout("agentCheck()",100);
			setTimeout("targetTGQFee()",100);
		
		 $("#airticketOrderId").val(airticketOrderId);
	   
		
		 if(tranType ==2){//卖出
			  //-----客户------ 
			totalTickAirPriceAgent=1*totalAirportPriceAgent + 1*totalFuelPriceAgent;//机建燃油税＝机建税+燃油税
			 $("#txtTax2").val(totalTickAirPriceAgent);//机建燃油税
			$("#txtTicketPriceTotals").val(totalTicketPriceAgent);//票面价
			$("#txtTicketPriceTotal").val(totalTicketPriceAgent);//票面价
			$("#txtAmountMore").val(overTicketPrice);//多收票价
			$("#txtAmountMores").val(overTicketPrice);//多收票价
			$("#txtTaxMore").val(overAirportfulePrice);//多收税
			$("#txtSourceTGQFee").val(incomeRetreatcharge);//收退票手续费  
			$("#txtAgents").val(commissonCountAgent);//返点
			$("#txtTotalAmount").val(actualAmountAgent);//实收票款
			$("#txtRemark").val(memo);//备注
			$("#txtUnAgentFeeTeams").val(proxyPrice);//应付出团代理费(未返)
			
			 //-----航空-------
		 totalTickAirPriceAvia=1*totalAirportPriceAvia + 1*totalFuelPriceAvia;//机建燃油税＝机建税+燃油税
		  $("#txtTax").val(totalTickAirPriceAvia);//机建燃油税
		  
		$("#txtTicketPriceTotal3").val(totalTicketPriceAvia);//票面价
		 $("#txtTicketPriceTotal1").val(totalTicketPriceAvia);//票面价
		$("#txtTicketPriceTotal2").val(totalTicketPriceAvia);//票面价
		$("#txtAmountSum").val(totalAmount);//订单总金额
		$("#txtTargetTGQFee").val(incomeRetreatchargeAvia);//付退票手续费
		$("#txtAgentT").val(commissonCountAvia);//返点
		$("#txtAgent").val(rakeoffCount);//月底返点
		$("#txtTAmount").val(actualAmountAvia);//实付票款
		$("#txtCharge").val(handlingChargeAvia);//手续费
		}
		
		if(tranTypeRufund ==3)//退票
		{
			 totalTickAirPriceAgent=1*totalRufundAirportPrice + 1*totalRufundFuelPrice;//机建燃油税＝机建税+燃油税
			 $("#txtTax").val(totalTickAirPriceAgent);//机建燃油税
			$("#txtTicketPriceTotal3").val(totalRufundTicket);//票面价
			$("#txtTicketPriceTotal1").val(totalRufundTicket);//票面价
			$("#txtTicketPriceTotal2").val(totalRufundTicket);//票面价
			$("#txtAgentT").val(commissonRufundCount);//返点
			$("#txtAgent").val(rufundRakeoffCount);//月底返点
			$("#txtCharge").val(rufundHandlingCharge);//手续费
			$("#txtTargetTGQFee").val(incomeRufundretreatCharge);//付退票手续费
			
		}
		
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
												成人数
											</div>
										</th>
										<th>
											<div>
												儿童数
											</div>
										</th>
										<th>
											<div>
												婴儿数
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
													<html:option value="导票组">导票组</html:option>
												</html:select>
											</td>
											<td>											
												<html:select property="tranType" name="airticketOrder"  styleClass="colorblue2 p_5" value="${airticketOrder.tranType}"
													style="width:150px;">
													<html:option value="2">销售</html:option>
													<html:option value="3">退票</html:option>
												</html:select>
											</td>
											<td>
													<span id="totlePernson"><c:out value="${airticketOrder.totlePerson}"/></span>
											</td>
											<td>
												<html:text property="teamAdultCount" styleId="teamAdultCount" name="airticketOrder" value="${airticketOrder.adultCount}" styleClass="colorblue2 p_5"
													style="width:50px;" onkeyup="totlePernsonCheck()"/>
											</td>
											<td>
												<html:text property="teamChildCount" styleId="teamChildCount" name="airticketOrder" value="${airticketOrder.childCount}" styleClass="colorblue2 p_5"
													style="width:50px;" onkeyup="totlePernsonCheck()"/>
											</td>
											<td>
												<html:text property="teamBabyCount" styleId="teamBabyCount" name="airticketOrder" value="${airticketOrder.babyCount}" styleClass="colorblue2 p_5"
													style="width:50px;" onkeyup="totlePernsonCheck()"/>
											</td>
											<td>
												<html:text property="totlePrice" styleId="totlePrice" name="airticketOrder" value="${airticketOrder.totalTicketPrice}" styleClass="colorblue2 p_5"
													style="width:50px;" />
											</td>
											<td>
												<html:text property="airportPrice" name="airticketOrder" value="${airticketOrder.totalAirportPrice}" styleClass="colorblue2 p_5"
													style="width:50px;" />
											</td>
											<td>
												<html:text property="fuelPrice" name="airticketOrder" value="${airticketOrder.totalFuelPrice}" styleClass="colorblue2 p_5"
													style="width:50px;" />
											</td>
											<td>
												<input type="button" value="保存修改" class="submit greenBtn" onclick="updateAirticketOrderCheck()"/>
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
										<th>
											<div>
												操作
											</div> 
										</th>
									</tr>
									<c:forEach items="${flightList}" var="f" varStatus="status">
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
										
											<html:text property="boardingTime" name="airticketOrder"  value="${f.boardingDate}" styleClass="colorblue2 p_5"
												style="width:150px;"  onclick="popUpCalendar(this, this);"  readonly="true"/>
											
										</td>
										<td>
											<html:hidden property="discount" name="airticketOrder" value="${f.discount}"/>
											<html:text property="flightClass" name="airticketOrder" value="${f.flightClass}" styleClass="colorblue2 p_5"
												style="width:50px;" />
										</td>
										<td>
											<html:text property="ticketPrice" styleId="ticketPrice${status.count}" name="airticketOrder" value="${f.ticketPrice}" styleClass="colorblue2 p_5"
												style="width:60px;" onkeyup="ticketPriceCheck()"/>
										</td>
										<td>
											<html:text property="adultAirportPrice" styleId="adultAirportPrice${status.count}" name="airticketOrder" value="${f.airportPriceAdult}" styleClass="colorblue2 p_5"
												style="width:60px;" onkeyup="airportPriceCheck()"/>
										</td>
										<td>
											<html:text property="adultFuelPrice" styleId="adultFuelPrice${status.count}" name="airticketOrder" value="${f.fuelPriceAdult}" styleClass="colorblue2 p_5"
												style="width:60px;" onkeyup="fuelPriceCheck()"/>
										</td>
										<td>
											<html:text property="childAirportPrice" styleId="childAirportPrice${status.count}" name="airticketOrder" value="${f.airportPriceChild}" styleClass="colorblue2 p_5"
												style="width:60px;" onkeyup="airportPriceCheck()"/>
										</td>
										<td>
											<html:text property="childfuelPrice" styleId="childfuelPrice${status.count}" name="airticketOrder" value="${f.fuelPriceChild}" styleClass="colorblue2 p_5"
												style="width:60px;" onkeyup="fuelPriceCheck()"/>
										</td>
										<td>
											<html:text property="babyAirportPrice" styleId="babyAirportPrice${status.count}" name="airticketOrder" value="${f.airportPriceBaby}" styleClass="colorblue2 p_5"
												style="width:60px;" onkeyup="airportPriceCheck()"/>
										</td>
										<td>
											<html:text property="babyfuelPrice" styleId="babyfuelPrice${status.count}" name="airticketOrder" value="${f.fuelPriceBaby}" styleClass="colorblue2 p_5"
												style="width:60px;" onkeyup="fuelPriceCheck()"/>
										</td>
										<td>
											<input type="button" value="保存修改" class="submit greenBtn" onclick="updateAirticketOrderCheck()"/>
										</td>
									</tr>	
									</c:forEach>	
								</table>
								<br /><br />
								订单信息:
								<input name="label" type="button" class="button1" value="添 加"
									onclick="addUser();" style="display: none;">
								<div class="searchBar">
									<table cellpadding="0" cellspacing="0" border="0"
										class="searchPanel">
										<tr>
											<td>订单号</td>
										<td>
											<html:text property="airOrderNo" styleId="airOrderNo" name="airticketOrder" value="${airticketOrder.airOrderNo}" styleClass="colorblue2 p_5"
												style="width:150px;" />
										</td>
										<td>订单金额</td>
										<td>
											<html:text property="totalAmount" styleId="totalAmount" name="airticketOrder" value="${airticketOrder.statement.totalAmount}" styleClass="colorblue2 p_5"
												style="width:100px;" />
										</td>
										<td>团队加价</td>
										<td>
											<html:text property="teamAddPrice" styleId="teamAddPrice" name="airticketOrder" value="${airticketOrder.teamaddPrice}" styleClass="colorblue2 p_5"
												style="width:100px;" />
										</td>
										<td>客户加价</td>
										<td>
											<html:text property="agentAddPrice" styleId="agentAddPrice" name="airticketOrder" value="${airticketOrder.agentaddPrice}" styleClass="colorblue2 p_5"
												style="width:100px;" />
										</td>
										<td>
											<input type="button" value="保存修改" class="submit greenBtn" onclick="updateAirticketOrderCheck()"/>
										</td>
										</tr>
									</table>
								</div>
								<br /><br />
								
								利润统计
								<c:if test="${statementSize>=2}">
									<input name="btnTotal" type="button" disabled="disabled" onclick="showDiv('<c:out value="${airticketOrder.id}"/>');"  value="添 加" />
									<input name="btnTota2" type="button" onclick="showDivUpdate('<c:out value="${airticketOrder.id}"/>','<c:out value="${airticketOrderTeamAgent.overTicketPrice}"/>',
										'<c:out value="${airticketOrderTeamAgent.overAirportfulePrice}"/>','<c:out value="${airticketOrderTeamAgent.incomeretreatCharge}"/>','<c:out value="${airticketOrderTeamAgent.totalTicketPrice}"/>',
										'<c:out value="${airticketOrderTeamAgent.totalAirportPrice}"/>','<c:out value="${airticketOrderTeamAgent.totalFuelPrice}"/>','<c:out value="${airticketOrder.statement.totalAmount}"/>'
										,'<c:out value="${airticketOrderTeamAvia.incomeretreatCharge}"/>','<c:out value="${airticketOrderTeamAgent.commissonCount}"/>','<c:out value="${airticketOrderTeamAvia.commissonCount}"/>'
										,'<c:out value="${airticketOrderTeamAvia.rakeoffCount}"/>','<c:out value="${airticketOrderTeamAvia.totalAirportPrice}"/>','<c:out value="${airticketOrderTeamAvia.totalFuelPrice}"/>'
										,'<c:out value="${airticketOrderTeamAvia.totalTicketPrice}"/>','<c:out value="${statementTeamAgent.actualAmount}"/>','<c:out value="${statementTeamAvia.actualAmount}"/>'
										,'<c:out value="${airticketOrderTeamAvia.handlingCharge}"/>','<c:out value="${airticketOrderTeamAgent.memo}"/>','<c:out value="${airticketOrderTeamAgent.proxyPrice}"/>'
										,'<c:out value="${airticketOrderTeamAgent.tranType}"/>','<c:out value="${airticketOrderRefundTeamAgent.tranType}"/>' 
										,'<c:out value="${airticketOrderRefundTeamAgent.totalTicketPrice}"/>'
										,'<c:out value="${airticketOrderRefundTeamAgent.commissonCount}"/>','<c:out value="${airticketOrderRefundTeamAgent.proxyPrice}"/>','<c:out value="${airticketOrderRefundTeamAgent.incomeretreatCharge}"/>'
										,'<c:out value="${airticketOrderRefundTeamAgent.totalAirportPrice}"/>','<c:out value="${airticketOrderRefundTeamAgent.totalFuelPrice}"/>'
										,'<c:out value="${airticketOrderRefundTeamAgent.handlingCharge}"/>','<c:out value="${airticketOrderRefundTeamAgent.rakeoffCount}"/>')"  value="编 辑" />
								</c:if>
								<c:if test="${statementSize==0 || statementSize==1}">
									<input name="btnTotal" type="button" onclick="showDiv('<c:out value="${airticketOrder.id}"/>',<c:out value="${airticketOrder.totalTicketPrice}"/>,<c:out value="${airticketOrder.totalAirportPrice}"/>,<c:out value="${airticketOrder.totalFuelPrice}"/>,<c:out value="${airticketOrder.statement.totalAmount}"/>,<c:out value="${airticketOrder.teamaddPrice}"/>,<c:out value="${airticketOrder.agentaddPrice}"/>);"  value="添 加" />
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
	
	<form id="form1" action="../airticket/airticketOrder.do?thisAction=insertTeamTradingOrder" name="airticketOrder" method="post">
	   <table id="list">
                <tbody><tr><td colspan="3">___________________________________________对客户_____________________________________________</td></tr>
                <tr>
                    <td>应付出团代理费（现返）：</td>
                    <td align="left">
                    	<input type="hidden" name="airticketOrderId" id="airticketOrderId" />
                        <input name="txtAgentFeeTeams" id="txtAgentFeeTeams" class="colorblue2 p_5" readonly="readonly" type="text" style="width: 70px;">
                        =(票面价&nbsp;&nbsp;&nbsp;<input name="txtTicketPriceTotals" id="txtTicketPriceTotals" class="colorblue2 p_5" readonly="readonly"  type="text" style="width: 70px;">
                        +<span style="color: Green;">多收票价<input name="txtAmountMore" id="txtAmountMore" class="colorblue2 p_5" onkeyup="amountMoreCheck();" value="0" style="width: 70px; color: Green;" type="text"></span>
                        )*<span style="color: Green;">返点&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="txtAgents" id="txtAgents" value="0" onkeyup="agentsCheck();" class="colorblue2 p_5" style="width: 70px; color: Green;" type="text"></span></td>
                </tr>
                <tr>
                    <td><span style="color: Green;">应付出团代理费（未返）：</span></td>
                    <td align="left">
                        <input name="txtUnAgentFeeTeams" id="txtUnAgentFeeTeams" value="0" onkeyup="unAgentFeeTeamsCheck();" class="colorblue2 p_5" style="width: 70px; color: Green;" type="text">
                        <span style="color: Green;">&nbsp;&nbsp;备注：&nbsp;&nbsp;&nbsp;&nbsp;</span><input name="txtRemark" id="txtRemark" class="colorblue2 p_5" style="width: 300px; color: Green;" class="TextUnderLine" type="text">
                    </td>
                </tr>
                <tr>
                    <td>应收票款：</td>
                    <td align="left">
                        <input name="txtSAmount" id="txtSAmount"  class="colorblue2" type="text" style="width: 70px;">
                        =(票面价&nbsp;&nbsp;&nbsp;<input name="txtTicketPriceTotal" id="txtTicketPriceTotal" style="width: 70px;" class="colorblue2 p_5" readonly="readonly" type="text">
                         +<span>多收票价<input name="txtAmountMores" id="txtAmountMores" style="width: 70px;" class="colorblue2 p_5" readonly="readonly" type="text"></span>
                        )-现返<input name="txtAgentFeeTeam" id="txtAgentFeeTeam" class="colorblue2 p_5" readonly="readonly" style="width: 70px;" type="text">
                        +<span style="color: Green;">多收税<input name="txtTaxMore" id="txtTaxMore" value="0" onkeyup="taxMoreCheck();" class="colorblue2 p_5" style="width: 70px;" type="text"></span>
                       +<span style="color: Green;">收退票手续费：</span><input name="txtSourceTGQFee" id="txtSourceTGQFee" value="0" onkeyup="sourceTGQFeeCheck();" class="colorblue2 p_5" style="color: Green; width: 70px;" type="text">
                  </td>
                </tr>
                    <tr>
                    <td>实收票款：</td>
                    <td align="left"> 
                    <input name="txtTotalAmount" id="txtTotalAmount" class="colorblue2 p_5" type="text" style="width: 70px;">
                    =应收票款<input name="txtSAmount2" id="txtSAmount2" class="colorblue2 p_5" readonly="readonly" type="text" style="width: 70px;">
                    +机建燃油税&nbsp;&nbsp;&nbsp;<input name="txtTax2" id="txtTax2" class="colorblue2 p_5" readonly="readonly" type="text" style="width: 70px;">
                    </td>                
                </tr>
                <tr><td colspan="3" style="width: 750px;"></td></tr>
                <tr><td colspan="3">__________________________________________对航空公司___________________________________________</td></tr>
                <tr>
                    <td>团毛利润：</td>
                    <td align="left">
                        <input name="txtProfits" id="txtProfits" class="colorblue2 p_5" readonly="readonly" type="text" style="width: 70px;">
                        =票面价&nbsp;&nbsp;&nbsp;&nbsp;<input name="txtTicketPriceTotal3" id="txtTicketPriceTotal3" class="colorblue2 p_5" readonly="readonly" type="text" style="width: 70px;">
                        *<span style="color: Green;">返点</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="txtAgentT" value="0" id="txtAgentT" onkeyup="agentTCheck();" class="colorblue2 p_5" style="width: 70px; color: Green;" type="text">
                        -手续费<input name="txtCharge" id="txtCharge" value="0" onkeyup="chargeCheck();" class="colorblue2 p_5" style="color: Green; width: 70px;" type="text">
                    </td>
                </tr>
                <tr>
                    <td>月底返代理费：</td>
                    <td align="left"> 
                        <input name="txtAgentFeeCarrier" id="txtAgentFeeCarrier" class="colorblue2 p_5" readonly="readonly" type="text" style="width: 70px;">
                        =<span>票面价</span>&nbsp;&nbsp;&nbsp;&nbsp;<input name="txtTicketPriceTotal1" id="txtTicketPriceTotal1" class="colorblue2 p_5" readonly="readonly" type="text" style="width: 70px;">
                        *<span style="color: Green;">月底返点</span><input name="txtAgent" id="txtAgent" value="0" onkeyup="agentCheck();" tabindex="0" style="width: 70px; color: Green;" class="colorblue2 p_5" type="text">
                  </td>
                </tr>
                   <tr>
                    <td>应付票款：</td>
                    <td align="left">
                        <input name="txtTUnAmount" id="txtTUnAmount" class="colorblue2 p_5" readonly="readonly" type="text" style="width: 70px;">
                        =票面价&nbsp;&nbsp;&nbsp;&nbsp;<input name="txtTicketPriceTotal2" id="txtTicketPriceTotal2" class="colorblue2 p_5" readonly="readonly"  type="text" style="width: 70px;">
                        -团毛利润<input name="txtProfit" id="txtProfit" class="colorblue2 p_5" readonly="readonly" type="text" style="width: 70px;">
                        +<span style="color: Green;">付退票手续费：</span><input name="txtTargetTGQFee" id="txtTargetTGQFee" value="0" onkeyup="targetTGQFee();" style="color: Green; width: 70px;" class="colorblue2 p_5" type="text"></td>
                </tr>
                <tr>
                    <td>实付票款：</td>
                    <td align="left">
                        <input name="txtTAmount" id="txtTAmount" class="colorblue2 p_5" style="width: 70px; color: Red;" readonly="readonly" type="text" />
                        =应付票款<input name="txtTUnAmount2" id="txtTUnAmount2" class="colorblue2 p_5" readonly="readonly" type="text" style="width: 70px;" />
                        +机建燃油税&nbsp;&nbsp;&nbsp;<input name="txtTax" id="txtTax" class="colorblue2 p_5" type="text" style="width: 70px;" />
                        </td>
                </tr>
                <tr>
                    <td>订单金额：</td>
                    <td align="left">
                    <input name="txtAmountSum" id="txtAmountSum" class="colorblue2 p_5" readonly="readonly" style="color: Red;" type="text" style="width: 70px;" />
                   
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
                        <input name="btnAdd" value="提 交" type="button" onclick="btnair()"/> &nbsp;&nbsp;&nbsp;&nbsp; 
                        <input value="重 置" id="btnReset" type="reset"> &nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                </tr>

          </tbody>
          </table>

	</form>
	
</div>

<div id="dialogStatement" title="修改结算金额" >
<form id="form2" action="../airticket/airticketOrder.do?thisAction=updateTeamStatement" name="airticketOrder" method="post">
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
                 <input name="btnUpdate" value="提 交" class="text ui-widget-content ui-corner-all"  type="button" onclick="btnUpdata()"/> &nbsp;&nbsp;&nbsp;&nbsp; 
                 <input value="重 置" id="btnReset" class="text ui-widget-content ui-corner-all" type="reset"> &nbsp;&nbsp;&nbsp;&nbsp;
             </td>
         </tr>
	</table>
</form>

</div>

<script type="text/javascript">
	function btnUpdata()//修改结算金额
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
		document.forms.form2.submit();
	}
</script>

<script type="text/javascript">
	function btnair()//添加利润统计 
	{
		var pan = /^[0-9]+$/;
		var txtAmountMores =$("#txtAmountMores").val();
		//var txtAmountSum =$("#txtAmountSum").val();//订单金额
		//var txtTAmount =$("#txtTAmount").val();//实付票款
		//if(!pan.test(txtAmountMores))
		//{
		//	alert("多收票价输入格式不正确!");
		//	return false;
	//	}
		//if(txtAmountSum != txtTAmount)
	//	{
		//	alert("实付票款与订单金额不一至，请核对！");
		//	return false;
		//}
		document.forms.form1.submit();
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
		
		amountMoreCheck();//多收票价(对客户)
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
		$("#txtTProfit").val(1*($("#txtTargetTGQFee").val()) -1*($("#txtSourceTGQFee").val()));
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
		$("#txtTProfit").val(1*($("#txtTargetTGQFee").val()) -1*($("#txtSourceTGQFee").val()));
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
