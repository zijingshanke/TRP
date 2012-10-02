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

		<style>
.BBJ_LOGO {
	color: #195EDC;
	font-weight: 400;
	display: inline-block;
	height: 20px;
	text-indent: 20px;
	background: url(../_img_jajabi/spr_icon.png) no-repeat -6px -36px;
	font-size: 12px;
}
</style>
		<script type="text/javascript" src="../_js_jajabi/JajabiProgress.js"></script>
		<script>
		function startTalking(agentjjbno) {
        var obj = document.getElementById("jjbTalkCab");      
        var  myjjb="";

   		<c:if test="${URI!=null}">
   			 myjjb=<c:out value="${URI.user.userNo}" />; 	
		 </c:if>   
		            
         if (myjjb == "") {
            obj.SetTalkInfo("", agentjjbno, "", "");
          } else{
          	alert('请登录家家比');
          }  
      }
      </script>
	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
				<html:form action="/airticket/listAirTicketOrder.do?thisAction=list">
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
								<c:import url="../_jsp/mainTitle.jsp?title1=票务管理&title2=全部散票订单"
									charEncoding="UTF-8" />

								<div class="searchBar">
									<table cellpadding="0" cellspacing="0" border="0"
										class="searchPanel">
										<tr>
											<td>
												承运人
												<html:select property="cyr" styleClass="colorblue2 p_5"
													style="width:120px;">
													<html:option value="">--请选择--</html:option>
													<html:option value="3U">四川航空</html:option>
													<html:option value="8C">东星航空</html:option>
													<html:option value="8L">翔鹏航空</html:option>
													<html:option value="9C">春秋航空</html:option>
													<html:option value="BK">奥凯航空</html:option>
													<html:option value="CA">国际航空</html:option>
													<html:option value="CN">大新华航空</html:option>
													<html:option value="CZ">南方航空</html:option>
													<html:option value="EU">鹰联航空</html:option>
													<html:option value="FM">上海航空</html:option>
													<html:option value="G5">华夏航空</html:option>
													<html:option value="GS">大新华快运航空</html:option>
													<html:option value="HO">吉祥航空</html:option>
													<html:option value="HU">海南航空</html:option>
													<html:option value="JD">金鹿航空</html:option>
													<html:option value="JR">幸福航空</html:option>
													<html:option value="KN">联合航空</html:option>
													<html:option value="KY">昆明航空</html:option>
													<html:option value="MF">厦门航空</html:option>
													<html:option value="MU">东方航空</html:option>
													<html:option value="KN">联合航空</html:option>
													<html:option value="KY">昆明航空</html:option>
													<html:option value="MF">厦门航空</html:option>
													<html:option value="MU">东方航空</html:option>
													<html:option value="NS">东北航空</html:option>
													<html:option value="OQ">重庆航空</html:option>
													<html:option value="PN">西部航空</html:option>
													<html:option value="SC">山东航空</html:option>
													<html:option value="VD">鲲鹏航空</html:option>
													<html:option value="ZH">深圳航空</html:option>

												</html:select>
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
												<html:text property="ticketNumber"
													styleClass="colorblue2 p_5" style="width:150px;" />
											</td>
											<td>
												开始:
												<html:text property="startDate" styleClass="colorblue2 p_5"
													style="width:150px;" onclick="popUpCalendar(this, this);"
													readonly="true" />
											</td>
											<td>
												结束
												<html:text property="endDate" styleClass="colorblue2 p_5"
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
												订单操作时间
											</div>
										</th>
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
												操作
											</div>
										</th>
									</tr>

									<c:forEach var="info" items="${ulf.list}" varStatus="status">
										<tr>
											<td>
												<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
													value="${info.optTime}" />
											</td>
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
												<c:if test="${!empty info.statement.fromPCAccount}">
													<c:out
														value="${info.statement.fromPCAccount.platform.name}" />
												</c:if>

												<c:if test="${!empty info.statement.toPCAccount}">
													<c:out value="${info.statement.toPCAccount.platform.name}" />
												</c:if>
											</td>
											<td>
												<a
													href="<%=path%>/airticket/listAirTicketOrder.do?thisAction=viewAirticketOrderPage&aircketOrderId=<c:out value="${info.id}" />">
													<c:out value="${info.subPnr}" /> </a>
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
												<a class="BBJ_LOGO"
													href="javascript:startTalking('<c:out value="${info.entryOperator}" />')">联系</a>
											</td>
											<td>
												<c:if test="${!empty info.orderPayerNo}">
													<a class="BBJ_LOGO"
														href="javascript:startTalking('<c:out value="${info.orderPayerNo}" />')">联系</a>
												</c:if>
											</td>
											<td>
												<a
													href="<%=path%>/airticket/listAirTicketOrder.do?thisAction=tradingOrderProcessing&groupMarkNo=<c:out value="${info.groupMarkNo}" />">
													关联订单</a>
												<br />
												<c:check code="sb81">
													<a
														href="<%=path%>/airticket/listAirTicketOrder.do?thisAction=forwardEditTradingOrder&id=<c:out value='${info.id}'/>&groupMarkNo=<c:out value="${info.groupMarkNo}" />">
														编辑</a>
												</c:check>
												<br />
												<c:check code="sb82">
													<a onclick="return confirm('确定删除吗?');"
														href="<%=path%>/airticket/airticketOrder.do?thisAction=updateOrderStatus&status=88&id=<c:out value='${info.id}' />">
														删除 </a>
												</c:check>
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
	</body>
</html>
