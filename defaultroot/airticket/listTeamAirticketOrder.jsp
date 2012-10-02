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
		<script src="../_js/calendar/WdatePicker.js" type="text/javascript"></script>
		
		<script type='text/javascript' src='<%=path %>/dwr/interface/platComAccountStore.js'></script>
		<script type='text/javascript' src='/tsms/dwr/interface/airticketOrderBiz.js'></script>
 		 <script type='text/javascript' src='<%=path %>/dwr/engine.js'></script>
		 <script type='text/javascript' src='<%=path %>/dwr/util.js'></script>
		
		<link type="text/css" href="../_js/development-bundle/themes/base/ui.all.css" rel="stylesheet" />
		<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" src="../_js/development-bundle/ui/ui.core.js"></script>
		<script type="text/javascript" src="../_js/development-bundle/ui/ui.draggable.js"></script>
		<script type="text/javascript" src="../_js/development-bundle/ui/ui.resizable.js"></script>
		<script type="text/javascript" src="../_js/development-bundle/ui/ui.dialog.js"></script>
		<script type="text/javascript" src="../_js/development-bundle/ui/effects.core.js"></script>
		<script type="text/javascript" src="../_js/development-bundle/ui/effects.highlight.js"></script>
		<link rel="stylesheet" href="../_js/development-bundle/demos/demos.css" type="text/css"></link>

		 <script type="text/javascript" src="../_js/teamAirticket.js"></script>
		
		
		<script type="text/javascript">
			 function selectRecent(){
      	var ifRecentlyObj=document.getElementById("ifRecentlyObj");
      	if(ifRecentlyObj.checked){
      		ifRecentlyObj.Checked=false;
      		ifRecentlyObj.value="1";
      		document.getElementById("recentlyDayId").value=1; 
      	}else{
      		ifRecentlyObj.Checked=true;
      		ifRecentlyObj.value="0";
      		document.getElementById("recentlyDayId").value='';
      	}      	
      }
      
      	$(function() {		
		  var recentlyDayId=$("#recentlyDayId").val();
		  if(recentlyDayId==0){
		  $("#recentlyDayId").val('');
		   $("#ifRecentlyObj").attr("checked","");
		  }
		});
		</script>
		
		<script type="text/javascript">
			function del(airticketOrderId){
				if(confirm("您真的要删除选择的这条数据吗？"))	{
					 document.forms[0].action="<%=path %>/airticket/listAirTicketOrder.do?thisAction=deleteAirticketOrder&num=2&airticketOrderId="+airticketOrderId;
					 document.forms[0].submit();
				}
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
		
		//添加利润
		$("#dialog").dialog({
			bgiframe: true,
			autoOpen: false,
			height: 480,
			width:800,
			modal: true
		});
		
		//确认支付
		$("#dialogOk").dialog({
			bgiframe: true,
			autoOpen: false,
			height: 450,
			width:750,
			modal: true
		});
		
		//退款
		$("#dialogFo").dialog({
			bgiframe: true,
			autoOpen: false,
			height: 360,
			width: 330,
			modal: true
		});
		//卖出
		$("#dialogTo").dialog({
			bgiframe: true,
			autoOpen: false,
			height: 360,
			width: 330,
			modal: true
		});
		
		//添加利润
		$('#create-user').click(function() {
			$('#dialog').dialog('open');
		})
		
		//确认支付
		$('#create-user').click(function() {
			$('#dialogOk').dialog('open');
		})
		
		//退款
		$('#create-user').click(function() {
			$('#dialogFo').dialog('open');
		})
		
		//卖出
		$('#create-user').click(function() {
			$('#dialogTo').dialog('open');
		})
		
	});
	</script>
		
	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
				<html:form action="/airticket/listAirTicketOrder.do?thisAction=listTeamAirticketOrder">
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
								<c:import url="../_jsp/mainTitle.jsp?title1=票务管理&title2=团队订单管理"
									charEncoding="UTF-8" />

								<div class="searchBar">
									<table cellpadding="0" cellspacing="0" border="0"
										class="searchPanel">
										<tr>
											<td>
												承运人/航班号
												<html:text property="cyr" styleClass="colorblue2 p_5"
													style="width:120px;" />
											</td>	
											<td>
												航班号
												<html:text property="flightCode" styleClass="colorblue2 p_5"
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
											<td style="display: none">
												状态
												<html:select property="airticketOrder_status" styleClass="colorblue2 p_5"
													style="width:150px;">
													<html:option value="0">请选择</html:option>
													<html:option value="101">新订单,待统计利润</html:option>
													<html:option value="111">新订单,等待申请</html:option>
													<html:option value="102">申请成功，等待支付</html:option>
													<html:option value="103">支付成功，等待出票</html:option>
													<html:option value="104">取消出票，等待退款</html:option>
													<html:option value="105">出票成功，交易结束</html:option>
													<html:option value="107">退票订单，等待审核</html:option>
													<html:option value="108">退票审核通过，等待退款</html:option>
													<html:option value="109">已经退款，交易结束</html:option>
													<html:option value="110">退票未通过，交易结束</html:option>
												</html:select>
											</td>
										</tr>
										<tr>
											<td>
												购票客户
												<html:select property="agentNo"  styleClass="colorblue2 p_5"
													style="width:200px;">
													<html:option value="">--请选择--</html:option>
													<c:forEach items="${agentList}" var="ag">
														<html:option value="${ag.id}"><c:out value="${ag.name}"/></html:option>
													</c:forEach>
												</html:select>
											</td>
											<td>
												出票人
												<html:select property="drawer"  styleClass="colorblue2 p_5"
													style="width:150px;">
													<html:option value="">---请选择---</html:option>
													<html:option value="B2B网电">B2B网电</html:option>
													<html:option value="B2C散客">B2C散客</html:option>
													<html:option value="导票组">导票组</html:option>
												</html:select>
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
												<input type="checkbox" name="ifRecently" checked="checked" id="ifRecentlyObj" value="1" onclick="selectRecent()">最近<html:text property="recentlyDay" styleId="recentlyDayId" style="width:30px" maxlength="3"  />天
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
												订单号
											</div>
										</th>
										<th>
											<div>
												购票客户
											</div>
										</th>
										<th>
											<div>
												总票面价
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
												出团总人数
											</div>
										</th>
										<th>
											<div>
												交易金额
											</div>
										</th>
										<th>
											<div>
												业务类型
											</div>
										</th>
										<th>
											<div>
												交易类型
											</div>
										</th>
										<th>
											<div>
												客票类型
											</div>
										</th>
										<th>
											<div>
												添加时间
											</div>
										</th>
										<th>
											<div>
												订单状态
											</div>
										</th>
										<th>
											<div>
												支付人
											</div>
										</th>
										<th>
											<div>
												操作人
											</div>
										</th>
										<th>
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
													<c:out value="${groupInfo.flightCode}" escapeXml="false"/>											
											</td>
										<td rowspan="<c:out value="${groupInfo.orderCount}" />">
													<c:out value="${groupInfo.flight}" escapeXml="false"/>											
											</td>
										<td rowspan="<c:out value="${groupInfo.orderCount}" />">
													<c:out value="${groupInfo.airorderNo}"/>										
											</td>
										<td rowspan="<c:out value="${groupInfo.orderCount}" />">
													<c:out value="${groupInfo.buyAgent.name}" />										
											</td>
										<td rowspan="<c:out value="${groupInfo.orderCount}" />">
													<c:out value="${groupInfo.totalTicketPrice}" />										
											</td>
										<td rowspan="<c:out value="${groupInfo.orderCount}" />">
													<c:out value="${groupInfo.totalAirportPrice}" />										
										</td>
										<td rowspan="<c:out value="${groupInfo.orderCount}" />">
													<c:out value="${groupInfo.totalFuelPrice}" />										
											</td>
										<td rowspan="<c:out value="${groupInfo.orderCount}" />">
											<c:out value="${groupInfo.totalPassenger}"/>										
											</td>
										<td>
											 <c:out value="${groupInfo.saleOrder.totalAmount}" />
										</td>
										<td>
											<c:out value="${groupInfo.saleOrder.businessTypeText}" />
										</td>
										<td>
											<c:out value="${groupInfo.saleOrder.tranTypeText}" />
										</td>
										<td>
											<c:out value="${groupInfo.saleOrder.drawer}" />
										</td>
										<td>
											<c:out value="${groupInfo.saleOrder.optTime}" />
										</td>
										<td>
											 <c:out value="${groupInfo.saleOrder.statusText}" />
										</td>
										<td>
											<c:out value="${groupInfo.saleOrder.payOperatorName}" />
										</td>
										<td>
											<c:out value="${groupInfo.saleOrder.entryOperatorName}" />
										</td>
										<td>
											<c:out value="${groupInfo.saleOrder.tradeOperate}" escapeXml="false"/>
										</td>
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
												<c:out value="${info.totalAmount}" />
											</td>
											<td>
												<c:out value="${info.businessTypeText}" />
											</td>
											<td>
												<c:out value="${info.tranTypeText}" />
											</td>
											<td>
												<c:out value="${info.drawer}" />
											</td>
											<td>
												<c:out value="${info.optTime}" />
											</td>
											<td>
												<c:out value="${info.statusText}" />
											</td>
											<td>
												<c:out value="${info.payOperatorName}" />
											</td>
											<td>
												<c:out value="${info.entryOperatorName}" />
											</td>
											<td>
													<c:out value="${info.tradeOperate}" escapeXml="false"/>										
											</td>
											</tr>												
										</c:forEach>
										</c:if>					
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
		<jsp:include page="../airticket/teamProfit.jsp" />
	</body>
</html>
