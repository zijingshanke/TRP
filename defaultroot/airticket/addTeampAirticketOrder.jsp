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
					document.forms[0].action="<%=path %>/airticket/airticketOrder.do?thisAction=updateAirticketOrder";
					document.forms[0].submit();
				}
			
		</script>
			
		
			<script type="text/javascript">
		$(function() {
		
			var ticketPrice = $("#ticketPrice"),
			documentPrice = $("#documentPrice"),
			insurancePrice = $("#insurancePrice"),
			
			
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
			height: 550,
			width:800,
			modal: true,
			buttons: {
				'确 定': function() {
					var bValid = true;
					allFields.removeClass('ui-state-error');

				
					
					
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
	
	
	function showDiv(){

	  $('#dialog').dialog('open');
	
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
									charEncoding="UTF-8" /><br /><br />
								
								主订单信息：
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
													<html:option value="导票组">导票组</html:option>
												</html:select>
											</td>
											<td>												
												<html:select property="tranType" name="airticketOrder"  styleClass="colorblue2 p_5"
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
								</table><br /><br /><br />
								
								
								<br />
								航程信息：
								<table width="100%" cellpadding="0" cellspacing="0" border="0"
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
												style="width:100px;"  onclick="popUpCalendar(this, this);"  readonly="true"/>
										</td>
										<td>
											<html:text property="flightClass" name="airticketOrder" value="${f.flightClass}" styleClass="colorblue2 p_5"
												style="width:50px;" />
										</td>

										<td>
											<html:text property="discount" name="airticketOrder" value="${f.discount}" styleClass="colorblue2 p_5"
												style="width:50px;" />
										</td>
										
										<td>
											<html:text property="ticketPrice" styleId="ticketPrice" name="airticketOrder" value="${airticketOrder.ticketPrice}" styleClass="colorblue2 p_5"
												style="width:60px;" />
										</td>
										<td>
											<html:text property="adultAirportPrice" styleId="adultAirportPrice" name="airticketOrder" value="${airticketOrder.adultAirportPrice}" styleClass="colorblue2 p_5"
												style="width:60px;" />
										</td>
										<td>
											<html:text property="adultFuelPrice" styleId="adultFuelPrice" name="airticketOrder" value="${airticketOrder.adultFuelPrice}" styleClass="colorblue2 p_5"
												style="width:60px;" />
										</td>
										<td>
											<html:text property="childAirportPrice" styleId="childAirportPrice" name="airticketOrder" value="${airticketOrder.childAirportPrice}" styleClass="colorblue2 p_5"
												style="width:60px;" />
										</td>
										<td>
											<html:text property="childfuelPrice" styleId="childfuelPrice" name="airticketOrder" value="${airticketOrder.childfuelPrice}" styleClass="colorblue2 p_5"
												style="width:60px;" />
										</td>
										<td>
											<html:text property="babyAirportPrice" styleId="babyAirportPrice" name="airticketOrder" value="${airticketOrder.babyAirportPrice}" styleClass="colorblue2 p_5"
												style="width:60px;" />
										</td>
										<td>
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
								<input name="label" type="button" class="button1"  onclick="showDiv();"  value="添 加">
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
												收票款
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
											&nbsp;
										</td>
										<td>
											&nbsp;
										</td>
										<td>
											&nbsp;
										</td>
										<td>
											&nbsp;
										</td>

										<td>
											&nbsp;
										</td>
										<td>
											&nbsp;
										</td>
										<td>
											&nbsp;
										</td>
										<td>
											&nbsp;
										</td>
										<td>
											&nbsp;
										</td>
										<td>
											&nbsp;
										</td>
										
										<td>
											<a href="#">[编辑]</a>
											<a href="#">[保存]</a>
											<a href="#">[删除]</a>
										</td>
									</tr>									
								</table>
								<table width="100%" cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td>
											<input name="label" type="button" class="button1" value="返 回"
												onclick="history.back();">
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
	
	<form action="../transaction/statement.do"  name="statement" method="post">
	   <table id="list">
                <tbody><tr><td colspan="3">___________________________________________对客户_____________________________________________</td></tr>
                <tr>
                    <td>应付出团代理费（现返）：</td>
                    <td align="left">
                        <input name="txtAgentFeeTeams" id="txtAgentFeeTeams" class="colorblue2 p_5" readonly="readonly" type="text">
                        =(<span>票面价</span>&nbsp;&nbsp;&nbsp;<input name="txtTicketPriceTotals" id="txtTicketPriceTotals" class="colorblue2 p_5" value="1100" type="text">
                        +<span style="color: Green;">多收票价<input name="txtAmountMore" id="txtAmountMore" class="colorblue2 p_5" style="width: 70px;" type="text"></span>
                        )*<span style="color: Green;">返点&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="txtAgents" id="txtAgents" class="colorblue2 p_5" style="width: 70px; color: Green;" type="text"></span></td>
                </tr>
                <tr>
                    <td><span style="color: Green;">应付出团代理费（未返）：</span></td>
                    <td align="left">
                        <input name="txtUnAgentFeeTeams" id="txtUnAgentFeeTeams" class="colorblue2 p_5" style="width: 70px; color: Green;" type="text">
                        <span style="color: Green;">&nbsp;&nbsp;备注：&nbsp;&nbsp;&nbsp;&nbsp;</span><input name="txtRemark" id="txtRemark" class="colorblue2 p_5" style="width: 300px; color: Green;" class="TextUnderLine" type="text">
                    </td>
                </tr>
                <tr>
                    <td>应收票款：</td>
                    <td align="left">
                        <input name="txtSAmount" id="txtSAmount"  class="colorblue2" type="text">
                        =(票面价&nbsp;&nbsp;&nbsp;<input name="txtTicketPriceTotal" id="txtTicketPriceTotal" class="colorblue2 p_5" readonly="readonly" value="1100" type="text">
                         +<span>多收票价<input name="txtAmountMores" id="txtAmountMores" style="width: 70px;" class="colorblue2 p_5" readonly="readonly" type="text"></span>
                        )-现返<input name="txtAgentFeeTeam" id="txtAgentFeeTeam" class="colorblue2 p_5" readonly="readonly" type="text">
                        +<span style="color: Green;">多收税<input name="txtTaxMore" id="txtTaxMore" class="colorblue2 p_5" style="width: 70px;" type="text"></span>
                       +<span style="color: Green;">收退票手续费：</span><input name="txtSourceTGQFee" id="txtSourceTGQFee" class="colorblue2 p_5" style="color: Green; width: 70px;" type="text">
                  </td>
                </tr>
                    <tr>
                    <td>实收票款：</td>
                    <td align="left"> 
                    <input name="txtTotalAmount" id="txtTotalAmount" class="colorblue2 p_5" type="text">
                    =应收票款<input name="txtSAmount2" id="txtSAmount2" class="colorblue2 p_5" readonly="readonly" type="text">
                    +机场税&nbsp;&nbsp;&nbsp;<input name="txtTax2" id="txtTax2" class="colorblue2 p_5" readonly="readonly" value="130" type="text">
                    </td>                
                </tr>
                <tr><td colspan="3" style="width: 750px;"></td></tr>
                <tr><td colspan="3">__________________________________________对航空公司___________________________________________</td></tr> 
                <tr>
                    <td>团毛利润：</td>
                    <td align="left">
                        <input name="txtProfits" id="txtProfits" class="colorblue2 p_5" readonly="readonly" type="text">
                        =票面价&nbsp;&nbsp;&nbsp;&nbsp;<input name="txtTicketPriceTotal3" id="txtTicketPriceTotal3" class="colorblue2 p_5" readonly="readonly" value="1100" type="text">
                        *<span style="color: Green;">返点</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="txtAgentT" id="txtAgentT" class="colorblue2 p_5" style="width: 70px; color: Green;" type="text">
                        -手续费<input name="txtCharge" id="txtCharge" class="colorblue2 p_5" style="color: Green; width: 70px;" type="text">
                    </td>
                </tr>
                <tr>
                    <td>月底返代理费：</td>
                    <td align="left">                  
                        <input name="txtAgentFeeCarrier" id="txtAgentFeeCarrier" class="colorblue2 p_5" readonly="readonly" type="text">
                        =<span>票面价</span>&nbsp;&nbsp;&nbsp;&nbsp;<input name="txtTicketPriceTotal1" id="txtTicketPriceTotal1" class="colorblue2 p_5" readonly="readonly" value="1100" type="text">
                        *<span style="color: Green;">月底返点</span><input name="txtAgent" id="txtAgent" tabindex="0" style="width: 70px; color: Green;" class="colorblue2 p_5" type="text">
                  </td>
                </tr>
                   <tr>
                    <td>应付票款：</td>
                    <td align="left">
                        <input name="txtTUnAmount" id="txtTUnAmount" class="colorblue2 p_5" readonly="readonly" type="text">
                        =票面价&nbsp;&nbsp;&nbsp;&nbsp;<input name="txtTicketPriceTotal2" id="txtTicketPriceTotal2" class="colorblue2 p_5" readonly="readonly" value="1100" type="text">
                        -团毛利润<input name="txtProfit" id="txtProfit" class="colorblue2 p_5" readonly="readonly" type="text">
                        +<span style="color: Green;">付退票手续费：</span><input name="txtTargetTGQFee" id="txtTargetTGQFee" style="color: Green; width: 70px;" class="colorblue2 p_5" type="text"></td>
                </tr>
                <tr>
                    <td>实付票款：</td>
                    <td align="left">
                        <input name="txtTAmount" id="txtTAmount" class="colorblue2 p_5" style="color: Red;" readonly="readonly" type="text">
                        =应付票款<input name="txtTUnAmount2" id="txtTUnAmount2" class="colorblue2 p_5" readonly="readonly" type="text">
                        +机场税&nbsp;&nbsp;&nbsp;<input name="txtTax" id="txtTax" class="colorblue2 p_5" value="130" type="text">
                        </td>
                </tr>
                <tr>
                    <td>订单金额：</td>
                    <td align="left">
                    <input name="txtAmountSum" id="txtAmountSum" class="colorblue2 p_5" readonly="readonly" style="color: Red;" value="300.00" type="text">
                    =(<input name="txtAmountItem" id="txtAmountItem" class="colorblue2 p_5" style="width: 200px;" readonly="readonly" value="100.00+200.00" type="text">)</td>
                </tr>
                <tr><td colspan="3"></td></tr>
                <tr><td colspan="3">___________________________________________利润_______________________________________________</td></tr>
                  <tr>
                    <td><span style="color: Green;">退票利润：</span></td>
                    <td align="left">
                    <input name="txtTProfit" id="txtTProfit" style="width: 70px; color: Green;" class="colorblue2 p_5" type="text">
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
                        <input name="btnAdd" value="提 交" id="btnAdd" type="submit"> &nbsp;&nbsp;&nbsp;&nbsp; 
                        <input value="重 置" id="btnReset" type="reset"> &nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                </tr>

          </tbody>
          </table>

	</form>
	
</div>
		
	</body>
</html>
