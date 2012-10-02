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
		<script src="../_js/calendar/WdatePicker.js" type="text/javascript"></script>
		
		<script type='text/javascript' src='/tsms/dwr/interface/airticketOrderBiz.js'></script>
  		<script type='text/javascript' src='/tsms/dwr/engine.js'></script>
  		<script type='text/javascript' src='/tsms/dwr/util.js'></script>
		
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
			function del(airticketOrderId)
			{
				if(confirm("您真的要删除这条数据吗？"))
				{
					 document.forms[0].action="<%=path %>/airticket/listAirTicketOrder.do?thisAction=updateAirticketOrderByStatus&airticketOrderId="+airticketOrderId;
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
		
		$("#dialog").dialog({
			bgiframe: true,
			autoOpen: false,
			height: 450,
			width:750,
			modal: true
		});
		
		$('#create-user').click(function() {
			$('#dialog').dialog('open');
		})
	
		
	});
		
		function showDiv(airOrderNo,totalAmount,agentId,cyrs,airticketOrderId,platComAccountId,sys_userName,groupMarkNo)    
		{
			airticketOrderBiz.getAirticketOrderByMarkNo(groupMarkNo,'1',getdata);
			
			$('#txtConfirmUser').val(sys_userName);//操作人
			$('#txtOrderNo').val(airOrderNo);//订单金额
			$('#txtOrderAmount').val(totalAmount);//订单金额
			$('#selTeamCmp').val(agentId);//购票客户
			$('#txtTAmount').val(totalAmount);//实付
			$('#txtCarrier').val(cyrs);//航空公司
			$('#airticketOrderId').val(airticketOrderId);//订单ID
			if(platComAccountId <90000)
			{
				$("#rptOrderItem_ctl01_selAccount").val(platComAccountId);
			}
			
			$('#dialog').dialog('open');
		}
		
		function getdata(date)//回调
		{
			if(date !=null)
			{
				$("#txtSAmount").val(date.totalAmount);
			}
		}
</script>

	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
				<html:form action="/airticket/listAirTicketOrder.do?thisAction=listTeamAppAirticketOrder">
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
												出团总人数
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
												实付
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
											<c:out value="${info.airOrderNo}" />
										</td>
										<td>
										 	<c:out value="${info.agent.name}" />
										</td>
										<td>										
											<c:out value="${info.totalTicketPrice}" />										
										</td>
										<td>
										 <c:out value="${info.totalAirportPrice}" />
										</td>
										<td>
										  <c:out value="${info.totalFuelPrice}" />
										</td>
									      
										<td>
										  	 <html:hidden property="adultCount" value="${info.adultCount}" />
										    <html:hidden property="childCount" value="${info.childCount}" />
										    <html:hidden property="babyCount" value="${info.babyCount}" />
										     <c:out value="${info.totlePerson}" />
										</td>
										<td>
										  <c:out value="${info.rebate}" />
										</td>
										<td>
											<c:forEach var="flight4" items="${info.flights}">
	                                             <c:out value="${flight4.discount}" /></br>
	                                         </c:forEach>
										</td>
										<td>
											 <c:out value="${info.totalAmount}" />
										</td>
										<td>
											<c:out value="${info.businessTypeText}" />
										</td>
										<td>
											<c:out value="${info.tranTypeText}" />(<c:out value="${info.businessTypeText}" />)
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
											<c:out value="${info.totalAmount}" />
										</td>
										<td>
											<c:out value="${info.payOperatorName}" />
										</td>
										<td>
											<c:out value="${info.entryOperatorName}" />
										</td>
										<td style="display: none;">
											<a href="<%=path %>/airticket/listAirTicketOrder.do?thisAction=updaTempAirticketOrderPage&airticketOrderId=<c:out value="${info.id}" />">编辑</a><br />
											<a href="#" onclick="del('<c:out value="${info.id}" />')">删除</a><br />
										</td>
										<td>
											<c:if test="${info.tranType ==2}"><!-- 买入 -->
												<c:check code="sb73">
													<a href="#" onclick="showDiv('<c:out value="${info.airOrderNo}" />','<c:out value="${info.totalAmount}" />','<c:out value="${info.agent.id}" />',
													'<c:out value="${info.cyrs}" />','<c:out value="${info.id }" />','<c:out value="${info.account.id }" />','<c:out value="${sys_userName }"/>'
													,'<c:out value="${info.groupMarkNo }" />')")">确认支付</a>
												</c:check>
												
												<br />
										
											</c:if>
											<c:if test="${info.tranType ==1}"><!-- 卖出 -->
												<a href="#" onclick="showDiv('<c:out value="${info.optTime }" />','<c:out value="${info.statement.sysUser.userName }" />','<c:out value="${info.airOrderNo}" />',
												'<c:out value="${info.statement.totalAmount}" />','<c:out value="${info.agent.id}" />','<c:out value="${info.statement.actualAmount}" />',
												'<c:out value="${info.cyrs}" />','<c:out value="${info.id }" />','<c:out value="${info.memo }" />')">确认收款</a><br />
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
		</div>
		
<div id="dialog" title="确认支付" >
 	<form id="form2" action="../airticket/airticketOrder.do?thisAction=teamAirticketOrderupdateAccount" name="airticketOrder" method="post">
		<div class="Panel">
        <br>
        <table width="70%">
            <tbody><tr>
                <td>
                <input type="hidden" id="airticketOrderId"  name="airticketOrderId"/>
                <input type="hidden" id="platComAccountId" name="platComAccountId"/>
                    购票客户：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <select  style="width: 150px;" id="selTeamCmp" name="selTeamCmp"  disabled="disabled" class="text ui-widget-content ui-corner-all">
						<c:forEach items="${agentList}" var="a">
							<option value="<c:out value="${a.id}"/>" ><c:out value="${a.name}"/></option>
						</c:forEach>
						
					</select>
                </td>
                <td>
                   实收：<input type="text"  readonly="readonly"  value="0"style="width: 100px;" id="txtSAmount" name="txtSAmount" class="text ui-widget-content ui-corner-all">
                </td>
            </tr>
            <tr>
                <td>
                    航空公司：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text"  disabled="disabled" style="width: 150px;" id="txtCarrier" name="txtCarrier" class="text ui-widget-content ui-corner-all">
                </td>
                <td>
                   实付：<input type="text"  readonly="readonly"  style="width: 100px;" id="txtTAmount" name="txtTAmount" class="text ui-widget-content ui-corner-all">
                </td>
            </tr>
        </tbody></table>
        <table width="100%">
            <tbody><tr>
                <td>
                    订单金额：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                </td>
                <td>
                    <div class="dataList" id="divOrderItem">
                                <table style="margin-left: 5px;" class="dataList">
                                    <tbody><tr>
                                        <th>
                                            订单号
                                        </th>
                                        <th>
                                            订单金额
                                        </th>
                                        <th>
                                            支付账号
                                        </th>
                                        <th>
                                            备注
                                        </th>
                                    </tr>
                            
                                <tr>
                                    <td>
                                        <input type="text" readonly="readonly" style="width: 180px;" id="txtOrderNo" name="txtOrderNo" class="text ui-widget-content ui-corner-all">
                                    </td>
                                    <td>
                                        <input type="text"  readonly="readonly" style="width: 80px;" id="txtOrderAmount" name="txtOrderAmount" class="text ui-widget-content ui-corner-all">
                                    </td>
                                    <td>
                                        <select style="width: 150px;" id="selAccount" name="selAccount" class="text ui-widget-content ui-corner-all">
											<c:forEach items="${platComAccountList}" var="p">
												<option value="<c:out value="${p.account.id}"/>" ><c:out value="${p.account.name}"/></option>
											</c:forEach>
										</select>
                                    </td>
                                    <td>
                                        <input type="text" style="width: 200px;" id="txtOrderRemark" name="txtOrderRemark" class="text ui-widget-content ui-corner-all">
                                    </td>
                                </tr>
                            </tbody></table>
                    </div>
                </td>
            </tr>
        </tbody></table>
        <table>
            <tbody><tr>
                <td>确认支付时间：
                	<input type="text"  readonly="readonly"  style="width: 180px;" value="<c:out value="${thisTime }"/>"   id="txtConfirmTime" name="txtConfirmTime" onfocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" class="text ui-widget-content ui-corner-all">
                </td>
            </tr>
            <tr>
                <td>
                    确认支付人&nbsp;&nbsp;&nbsp;&nbsp;：<input type="text"  readonly="readonly" style="width: 150px;" id="txtConfirmUser" name="txtConfirmUser" class="text ui-widget-content ui-corner-all">
                </td>
            </tr>
            <tr>
                <td align="center">
                    &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
                    &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="button" id="btnAdd" value="提 交" onclick="readonlyOK()" name="btnAdd" class="text ui-widget-content ui-corner-all">
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="reset" id="btnReset" value="重 置" class="text ui-widget-content ui-corner-all">
                    &nbsp;&nbsp;&nbsp;&nbsp;
                </td>
            </tr>
        </tbody></table>
    </div>
	</form>

</div>
		
	</body>
	<script type="text/javascript">
		function readonlyOK()
		{
			document.forms.form2.submit();
		}		
	</script>
	
</html>
