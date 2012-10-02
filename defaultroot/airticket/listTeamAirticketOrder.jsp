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
		<script src="../_js/calendar/WdatePicker.js" type="text/javascript"></script>		
		<script type='text/javascript' src='<%=path %>/dwr/interface/platComAccountStore.js'></script>
		<script type='text/javascript' src='<%=path %>/dwr/interface/airticketOrderBiz.js'></script>
 		 <script type='text/javascript' src='<%=path %>/dwr/engine.js'></script>
		 <script type='text/javascript' src='<%=path %>/dwr/util.js'></script>
		
		<link type="text/css" href="../_js/development-bundle/themes/base/ui.all.css" rel="stylesheet" />
		<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" src="../_js/development-bundle/ui/ui.core.js"></script>
		<script type="text/javascript" src="../_js/development-bundle/ui/ui.draggable.js"></script>
		<script type="text/javascript" src="../_js/development-bundle/ui/ui.dialog.js"></script>
			<script src="../_js/base/CalculateUtil.js" type="text/javascript"></script>
			<script src="../_js/base/FormUtil.js" type="text/javascript"></script>	
		<script type="text/javascript" src="../_js/tsms/loadTeamManage.js"></script>
		<script type="text/javascript" src="../_js/tsms/teamOrderOperate.js"></script>
		 
		<script type="text/javascript">
		function selectRecent(){
        var tempDay="<c:out value='${param.recentlyDay}'/>";
        if(tempDay==null||tempDay==""){
        	tempDay="1";
        }
       	var ifRecentlyObj=document.getElementById("ifRecentlyObj");
      	if(ifRecentlyObj.checked){
      		ifRecentlyObj.Checked=false;
      		ifRecentlyObj.value="0";      		
      		document.getElementById("recentlyDayId").value=tempDay;
      	}else{
      		ifRecentlyObj.Checked=true;
      		ifRecentlyObj.value="1";
      		document.getElementById("recentlyDayId").value="";
      	}      	
      }
      
      	$(function() {		
      	   var tempDay="<c:out value='${param.recentlyDay}'/>";
        	if(tempDay==null||tempDay==""){
        		tempDay="1";
        	}
        	
		  var recentlyDayValue=$("#recentlyDayId").val();
		  
		  if(tempDay>1){
		  	recentlyDayValue=tempDay;
		  	 $("#recentlyDayId").val(recentlyDayValue);
		  }else{
		  	 recentlyDayValue="1";
		  	 $("#recentlyDayId").val("1");
		  }
		  
		  if(recentlyDayValue==0){
		   $("#recentlyDayId").val('');
		   $("#ifRecentlyObj").attr("checked","");
		  }
		  
		  
		});
		</script>		
	</head>
	<body>
	   <c:choose>
	    <c:when test="${param.moreStatus=='101'}">
	      <c:set var="subtitle" value="待处理新订单"/>
	    </c:when> 
	    <c:when test="${param.moreStatus=='111'}">
	     <c:set var="subtitle" value="待申请支付订单"/>
	    </c:when> 
	    <c:when test="${param.moreStatus=='102'}">
	     <c:set var="subtitle" value="待确认支付订单"/>
	    </c:when> 
	    <c:when test="${param.moreStatus=='103'}">
	     <c:set var="subtitle" value="等待出票订单"/>
	    </c:when> 
	    <c:when test="${param.moreStatus=='105'}">
	     <c:set var="subtitle" value="完成出票订单"/>
	    </c:when> 
	    	    <c:when test="${param.moreStatus=='107'}">
	     <c:set var="subtitle" value="待审核退废订单"/>
	    </c:when> 
	    
	    	    <c:when test="${param.moreStatus=='108'}">
	     <c:set var="subtitle" value="已审待退款订单"/>
	    </c:when> 
	    
	   	    	    <c:when test="${param.moreStatus=='109'}">
	     <c:set var="subtitle" value="完成退款订单"/>
	    </c:when>  	    
       	<c:otherwise>
	      <c:set var="subtitle" value="团队订单管理"/>
	    </c:otherwise>
	    </c:choose>	

		<div id="mainContainer">
			<div id="container">
				<html:form action="/airticket/listAirTicketOrder.do?thisAction=listTeamAirticketOrder">
					<html:hidden property="thisAction" />
					<html:hidden property="lastAction" />
					<html:hidden property="intPage" />
					<html:hidden property="pageCount" />
					<html:hidden property="orderType" value="${param.orderType}"/>
					<html:hidden property="moreStatus" value="${param.moreStatus}"/>
						 
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
								   <c:import url="../_jsp/mainTitle.jsp" charEncoding="UTF-8">
										<c:param name="title1" value="票务管理" />
										<c:param name="title2" value="团队订单管理" />		
										<c:param name="title3" value="${subtitle}" />																		
								</c:import>		
							<jsp:include page="teamSearchToolBar.jsp"></jsp:include>
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
												实收
											</div>
										</th>
										<th>
											<div>
												实付
											</div>
										</th>
										<th>
											<div>
												交易类型
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
											<td>
													<c:out value="${groupInfo.carrier}" escapeXml="false"/>
											</td>
											<td>
													<c:out value="${groupInfo.flightCode}" escapeXml="false"/>											
											</td>
											<td>
													<c:out value="${groupInfo.flight}" escapeXml="false"/>											
											</td>
											<td>
													<a href="<%=path%>/airticket/listAirTicketOrder.do?thisAction=viewTeam&id=<c:out value="${groupInfo.saleOrder.id}"/>"><c:out value="${groupInfo.saleOrder.airOrderNo}"/>		</a>								
											</td>
											<td>
													<c:out value="${groupInfo.buyAgent.name}" />										
											</td>
											<td>
													<c:out value="${groupInfo.totalTicketPrice}" />										
											</td>
											<td>
													<c:out value="${groupInfo.totalAirportPrice}" />										
											</td>
											<td>
													<c:out value="${groupInfo.totalFuelPrice}" />										
											</td>
											<td>
													<c:out value="${groupInfo.totalPassenger}"/>										
											</td>
											<td>
												 <c:out value="${groupInfo.saleAmount}" />
											</td>
											<td>
												 <c:out value="${groupInfo.buyAmount}" />
											</td>
											<td>
												<c:out value="${groupInfo.orderList[0].tranTypeText}" />
											</td>
											<td>
												<c:out value="${groupInfo.orderList[0].entryOrderDate}" />
											</td>
											<td>
												 <c:out value="${groupInfo.orderList[0].statusText}" />
											</td>
											<td>
												<c:out value="${groupInfo.orderList[0].payOperatorName}" />
											</td>
											<td>
												<c:out value="${groupInfo.orderList[0].entryOperatorName}" />
											</td>
											<td>
												<c:out value="${groupInfo.orderList[0].tradeOperate}" escapeXml="false"/>
											</td>
									</tr>	
									</c:forEach>
								</table>
								<table width="100%" style="margin-top: 5px;">
									<tr>
										<td></td>
										<td align="right">
											<div>
											 共有订单&nbsp;
											<c:out value="${ulf.groupCount}" />
											&nbsp;条
												明细&nbsp;
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
		<jsp:include page="../airticket/teamManageDiv.jsp" />
	</body>
</html>
