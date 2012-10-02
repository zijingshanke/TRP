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
	text-indent: 15px;
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
      
      function selectRecent(){
      	var ifRecentlyObj=document.getElementById("ifRecentlyObj");
      	if(ifRecentlyObj.checked){
      		ifRecentlyObj.Checked=false;
      		ifRecentlyObj.value="0";
      	}else{
      		ifRecentlyObj.Checked=true;
      		ifRecentlyObj.value="1";
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
													<html:option value="3U">3U-四川航空</html:option>
													<html:option value="8C">8C-东星航空</html:option>
													<html:option value="8L">8L-翔鹏航空</html:option>
													<html:option value="9C">9C-春秋航空</html:option>
													<html:option value="BK">BK-奥凯航空</html:option>
													<html:option value="CA">CA-国际航空</html:option>
													<html:option value="CN">CN-大新华航空</html:option>
													<html:option value="CZ">CZ-南方航空</html:option>
													<html:option value="EU">EU-鹰联航空</html:option>
													<html:option value="FM">FM-上海航空</html:option>
													<html:option value="G5">GS-华夏航空</html:option>
													<html:option value="GS">GS-大新华快运航空</html:option>
													<html:option value="HO">HO-吉祥航空</html:option>
													<html:option value="HU">HU-海南航空</html:option>
													<html:option value="JD">JD-金鹿航空</html:option>
													<html:option value="JR">JR-幸福航空</html:option>
													<html:option value="KY">KY-昆明航空</html:option>
													<html:option value="KN">KN-联合航空</html:option>
													<html:option value="MF">MF-厦门航空</html:option>
													<html:option value="MU">MU_东方航空</html:option>
													<html:option value="NS">NS-东北航空</html:option>
													<html:option value="OQ">OQ-重庆航空</html:option>
													<html:option value="PN">PN-西部航空</html:option>
													<html:option value="SC">SC-山东航空</html:option>
													<html:option value="VD">VD-鲲鹏航空</html:option>
													<html:option value="ZH">ZH-深圳航空</html:option>
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
												<input type="checkbox" name="ifRecently" checked="checked" id="ifRecentlyObj" value="1" onclick="selectRecent()">最近<html:text property="userNo"  style="width:30px" maxlength="3" value="1" />天
											</td>
										</tr>
										<tr>
											<td>
												买入
												<html:select property="fromPlatformId" styleClass="colorblue2 p_5"
													style="width:150px;">
													<html:option value="">---请选择---</html:option>
													<c:forEach items="${formPlatFormList}" var="foplat">
														<html:option value="${foplat.id}"><c:out value="${foplat.name }"/></html:option>
													</c:forEach>
												</html:select>
											</td>
											<td>
												付款
												<html:select property="fromAccountId" styleClass="colorblue2 p_5"
													style="width:150px;" >
													<html:option value="">---请选择---</html:option>
													<c:forEach items="${formAccountList}" var="flac">
														<html:option value="${flac.id }"><c:out value="${flac.name }"/></html:option>
													</c:forEach>
												</html:select>
											</td>
											<td>
												卖出
												<html:select property="toPlatformId" styleClass="colorblue2 p_5"
													style="width:150px;" >
													<html:option value="">---请选择---</html:option>
													<c:forEach items="${toPlatFormList}" var="toplat">
														<html:option value="${toplat.id }"><c:out value="${toplat.name }"/></html:option>
													</c:forEach>
												</html:select>
											</td>
											<td>
												收款
												<html:select property="toAccountId" styleClass="colorblue2 p_5"
													style="width:150px;" >
													<html:option value="">---请选择---</html:option>
													<c:forEach items="${toAccountList}" var="toac">
														<html:option value="${toac.id }"><c:out value="${toac.name }"/></html:option>
													</c:forEach>
												</html:select>
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
													<html:option value="5">出票成功，交易结束</html:option>		
													<html:option value="6">取消出票,已经退款</html:option>																										
													<html:option value="19">退票订单，等待审核</html:option>
													<html:option value="21">退票审核通过，等待退款</html:option>
													<html:option value="21">退票,已经退款</html:option>													
													<html:option value="29">废票订单，等待审核</html:option>
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
												订单时间
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
													<c:out value="${groupInfo.flight}" />											
											</td>										
											<td rowspan="<c:out value="${groupInfo.orderCount}" />">
													<c:out value="${groupInfo.passenger}"  escapeXml="false"/>
											</td>
											<td rowspan="<c:out value="${groupInfo.orderCount}" />">
													<c:out value="${groupInfo.ticketNo}"  escapeXml="false" />
											</td>
											<td rowspan="<c:out value="${groupInfo.orderCount}" />">
													<c:out value="${groupInfo.ticketPrice}" />
											</td>
											<td rowspan="<c:out value="${groupInfo.orderCount}" />">
													<c:out value="${groupInfo.airportPrice}" />
											</td>
											<td rowspan="<c:out value="${groupInfo.orderCount}" />">
													<c:out value="${groupInfo.fuelPrice}" />
											</td>										
											<td>
												<c:if test="${!empty info.platform}">
													<c:out value="${groupInfo.saleOrder.platform.showName}" />
												</c:if>
											</td>
											<td>
												<a href="<%=path%>/airticket/listAirTicketOrder.do?thisAction=viewAirticketOrderPage&tranType=<c:out value="${groupInfo.saleOrder.tranType}" />&groupMarkNo=<c:out value="${groupInfo.saleOrder.groupMarkNo}" />&aircketOrderId=<c:out value="${groupInfo.saleOrder.id}" />">
													<c:out value="${groupInfo.saleOrder.subPnr}" /> 
												</a>
											</td>
											<td>
												<a href="<%=path%>/airticket/listAirTicketOrder.do?thisAction=viewAirticketOrderPage&tranType=<c:out value="${groupInfo.saleOrder.tranType}" />&groupMarkNo=<c:out value="${groupInfo.saleOrder.groupMarkNo}" />&aircketOrderId=<c:out value="${groupInfo.saleOrder.id}" />">
													<c:out value="${groupInfo.saleOrder.drawPnr}" />
												</a>
											</td>
											<td>
												<a href="<%=path%>/airticket/listAirTicketOrder.do?thisAction=viewAirticketOrderPage&tranType=<c:out value="${groupInfo.saleOrder.tranType}" />&groupMarkNo=<c:out value="${groupInfo.saleOrder.groupMarkNo}" />&aircketOrderId=<c:out value="${groupInfo.saleOrder.id}" />">
													<c:out value="${groupInfo.saleOrder.bigPnr}" />
												</a>
											</td>
											<td>
												<c:out value="${groupInfo.saleOrder.rebate}" />
											</td>
											<td>
												<c:out value="${groupInfo.saleOrder.totalAmount}" />
											</td>
											<td>
												<c:out value="${groupInfo.saleOrder.tranTypeText}" />(<c:out value="${groupInfo.saleOrder.businessTypeText}" />)
											</td>
											<td>
												<c:out value="${groupInfo.saleOrder.statusText}" />
											</td>
											<td>
												<a class="BBJ_LOGO"
													href="javascript:startTalking('<c:out value="${groupInfo.saleOrder.entryOperator}" />')"><c:out
														value="${groupInfo.saleOrder.entryOperatorName}" /> </a>
											</td>
											<td>
												<c:if test="${!empty groupInfo.saleOrder.payOperatorName}">
													<a class="BBJ_LOGO"
														href="javascript:startTalking('<c:out value="${groupInfo.saleOrder.payOperatorName}" />')"><c:out
															value="${groupInfo.saleOrder.payOperatorName}" /> </a>
												</c:if>
											</td>											
											<td>
												<c:out value="${groupInfo.saleOrder.entryOrderDate}" />
											</td>
											<td>
												<a
													href="<%=path%>/airticket/listAirTicketOrder.do?thisAction=tradingOrderProcessing&groupMarkNo=<c:out value="${groupInfo.saleOrder.groupMarkNo}" />">
													关联订单</a>
												<br />
												<c:check code="sb81">
													<a
														href="<%=path%>/airticket/listAirTicketOrder.do?thisAction=forwardEditTradingOrder&id=<c:out value='${groupInfo.saleOrder.id}'/>&groupMarkNo=<c:out value="${groupInfo.saleOrder.groupMarkNo}" />">
														编辑</a>
												</c:check>
												<br />
												<c:check code="sb82">
													<a onclick="return confirm('确定删除吗?');"
														href="<%=path%>/airticket/listAirTicketOrder.do?thisAction=deleteAirticketOrder&num=1&airticketOrderId=<c:out value='${groupInfo.saleOrder.id}' />">
														删除 </a>
												</c:check>
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
														<c:if test="${!empty info.platform}">
															<c:out value="${info.platform.showName}" />
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
												<c:out value="${info.totalAmount}" />
											</td>
											<td>
												<c:out value="${info.tranTypeText}" />(<c:out value="${info.businessTypeText}" />)
											</td>
											<td>
												<c:out value="${info.statusText}" />
											</td>
											<td>
												<a class="BBJ_LOGO"
													href="javascript:startTalking('<c:out value="${info.entryOperator}" />')"><c:out
														value="${info.entryOperatorName}" /> </a>
											</td>
											<td>
												<c:if test="${!empty groupInfo.saleOrder.payOperatorName}">
													<a class="BBJ_LOGO"
														href="javascript:startTalking('<c:out value="${info.payOperatorName}" />')"><c:out
															value="${info.payOperatorName}" /> </a>
												</c:if>
											</td>											
											<td>
												<c:out	value="${info.entryOrderDate}" />
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
														href="<%=path%>/airticket/listAirTicketOrder.do?thisAction=deleteAirticketOrder&num=1&airticketOrderId=<c:out value='${info.id}' />">
														删除 </a>
												</c:check>
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
	</body>
</html>
