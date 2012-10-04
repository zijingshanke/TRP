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
<title>票务订单管理</title>
<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
<link href="../_css/global.css" rel="stylesheet" type="text/css" />
<script type='text/javascript' src='<%=path %>/dwr/interface/platComAccountStore.js'></script>
<script type='text/javascript' src='<%=path %>/dwr/interface/passengerBiz.js'></script>
<script type='text/javascript' src='<%=path %>/dwr/interface/tempPNRBizImp.js'></script>
<script type='text/javascript' src='<%=path %>/dwr/interface/parseBlackUtil.js'></script>
<script type='text/javascript' src='<%=path %>/dwr/interface/airticketOrderBiz.js'></script>
<script type='text/javascript' src='<%=path %>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=path %>/dwr/util.js'></script>
<link type="text/css" href="../_js/development-bundle/themes/base/ui.all.css" rel="stylesheet" />
<script type="text/javascript" src="../_js/development-bundle/jquery-1.3.2.js"></script>
<script type="text/javascript" src="../_js/development-bundle/ui/ui.core.js"></script>
<script type="text/javascript" src="../_js/development-bundle/ui/ui.draggable.js"></script>
<script type="text/javascript" src="../_js/development-bundle/ui/ui.dialog.js"></script>
<script src="../_js/common.js" type="text/javascript"></script>
<script src="../_js/calendar/WdatePicker.js" type="text/javascript"></script>
<script src="../_js/base/CalculateUtil.js" type="text/javascript"></script>
<script src="../_js/base/FormUtil.js" type="text/javascript"></script>
<script src="../_js/tsms/loadAccount.js" type="text/javascript"></script>
<script src="../_js/tsms/loadManage.js" type="text/javascript"></script>
<script src="../_js/tsms/orderOperate.js" type="text/javascript"></script>
<script src="../_js/menu.js" type="text/javascript"></script>
<script>
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
      	   var tempDay="<c:out value='${ulf.recentlyDay}'/>";
        	if(tempDay==null||tempDay==""){
        		tempDay="1";
        	}
        	
		  var recentlyDayValue=$("#recentlyDayId").val();		  
		  
		  if(recentlyDayValue==0){
		   $("#recentlyDayId").val('');
		   $("#ifRecentlyObj").attr("checked","");
		  }
		  else
		  {
		    $("#recentlyDayId").val(tempDay);
		  }
		});
		
		function goMyIntPage(form)
		{
		  
		  var _key;
	     document.onkeyup = function(e){
		if (e == null) { // ie
			_key = event.keyCode;
		} else { // firefox
			_key = e.which;
		} 
 
		if(_key == 13){
		   if(form.myIntPage.value>0)
		     form.myIntPage.value=form.myIntPage.value-1
		  form.intPage.value=form.myIntPage.value;		  
		  turnToPage(document.forms[0],2);
		}
	};
		  
		 
		}
		  
      </script>
<style type="text/css">
.dataList th {
	padding:0;
	margin:0;
	text-align:center;
}
.dataList td {
	text-align: center;
	border: 1px solid #dedfe1;
	font-size: 12px;
	empty-cells:show;
	padding:0;
	margin:0;
	line-height:22px;
}
.dataList .operationArea a {
	color: #005c9c;
	text-decoration: underline;
}
.wordWrap {
	word-wrap: break-word;
	word-break: break-all;
	overflow: hidden;
	-moz-binding: url('../_css/wordwrap.xml#wordwrap');
}
</style>
</head>
<body>
<c:choose>
  <c:when test="${param.orderType==91}">
    <c:set var="title" value="正常订单管理"/>
    <c:choose>
      <c:when test="${param.moreStatus=='1'}">
        <c:set var="subtitle" value="待处理新订单"/>
        <script>setSelectedMenu("ulEditNormal",1);</script> 
      </c:when>
      <c:when test="${param.moreStatus=='2,7,8'}">
        <c:set var="subtitle" value="待确认支付订单"/>
        <script>setSelectedMenu("ulEditNormal",2);</script> 
      </c:when>
      <c:when test="${param.moreStatus=='3'}">
        <c:set var="subtitle" value="等待出票订单"/>
        <script>setSelectedMenu("ulEditNormal",3);</script> 
      </c:when>
      <c:when test="${param.moreStatus=='4,14,9,10,6,16'}">
        <c:set var="subtitle" value="取消出票订单"/>
        <script>setSelectedMenu("ulEditNormal",4);</script> 
      </c:when>      
      <c:when test="${param.moreStatus=='5'}">
        <c:set var="subtitle" value="完成出票订单"/>
        <script>setSelectedMenu("ulEditNormal",5);</script> 
      </c:when>
      <c:when test="${param.moreStatus=='4'}">
        <c:set var="subtitle" value="取消待退款订单"/>
        <script>setSelectedMenu("ulEditNormal",6);</script> 
      </c:when>
      <c:when test="${param.moreStatus=='6'}">
        <c:set var="subtitle" value="取消已退款订单"/>
        <script>setSelectedMenu("ulEditNormal",7);</script> 
      </c:when>
      <c:otherwise>
        <c:set var="subtitle" value="正常订单管理"/>
      </c:otherwise>
    </c:choose>
  </c:when>
  <c:when test="${param.orderType==92}">
    <c:set var="title" value="改签订单管理"/>
    <c:choose>
      <c:when test="${param.moreStatus=='39,46'}">
        <c:set var="subtitle" value="待审核新订单"/>
        <script>setSelectedMenu("ulEditUmbuchen",1);</script> 
      </c:when>
      <c:when test="${param.moreStatus=='40,41,42'}">
        <c:set var="subtitle" value="已审待支付订单"/>
        <script>setSelectedMenu("ulEditUmbuchen",2);</script> 
      </c:when>
      <c:when test="${param.moreStatus=='43'}">
        <c:set var="subtitle" value="已付待确认订单"/>
        <script>setSelectedMenu("ulEditUmbuchen",3);</script> 
      </c:when>
      <c:when test="${param.moreStatus=='45'}">
        <c:set var="subtitle" value="完成改签订单"/>
        <script>setSelectedMenu("ulEditUmbuchen",4);</script> 
      </c:when>
      <c:when test="${param.moreStatus=='44'}">
        <c:set var="subtitle" value="改签不通过订单"/>
        <script>setSelectedMenu("ulEditUmbuchen",5);</script> 
      </c:when>
      <c:otherwise>
        <c:set var="subtitle" value="改签订单管理"/>
      </c:otherwise>
    </c:choose>
  </c:when>
  <c:when test="${param.orderType==93}">
    <c:set var="title" value="退废订单管理"/>
    <c:choose>
      <c:when test="${param.moreStatus=='19,29,20,30,24,25,34,35'}">
        <c:set var="subtitle" value="待审核新订单"/>
        <script>setSelectedMenu("ulEditRetire",1);</script> 
      </c:when>
      <c:when test="${param.moreStatus=='21,31'}">
        <c:set var="subtitle" value="已审待退款订单"/>
        <script>setSelectedMenu("ulEditRetire",2);</script> 
      </c:when>
      <c:when test="${param.moreStatus=='22,32'}">
        <c:set var="subtitle" value="完成退款订单"/>
        <script>setSelectedMenu("ulEditRetire",3);</script> 
      </c:when>
      <c:when test="${param.moreStatus=='23,33'}">
        <c:set var="subtitle" value="审核不通过订单"/>
        <script>setSelectedMenu("ulEditRetire",4);</script> 
      </c:when>
      <c:otherwise>
        <c:set var="subtitle" value="退废订单管理"/>
      </c:otherwise>
    </c:choose>
  </c:when>
  <c:otherwise> </c:otherwise>
</c:choose>
<div id="mainContainer">
  <div id="container">
    <html:form action="/airticket/listAirTicketOrder.do?thisAction=listAirTicketOrder"> 
          <html:hidden property="thisAction" />
          <html:hidden property="lastAction" />
          <html:hidden property="intPage" />
          <html:hidden property="pageCount" />
          
          <html:hidden property="moreStatus"/>
          <input type="hidden" name="locate"/>

      <table width="100%" cellpadding="0" cellspacing="0" border="0">
        <tr>
          <td width="10" height="10" class="tblt"></td>
          <td height="10" class="tbtt"></td>
          <td width="10" height="10" class="tbrt"></td>
        </tr>
        <tr>
          <td width="10" class="tbll"></td>
            <td valign="top" class="body">
          <c:import url="../_jsp/mainTitle.jsp" charEncoding="UTF-8" >
            <c:param name="title1" value="票务管理"/>
            <c:param name="title2" value="${title}"/>
            <c:param name="title3" value="${subtitle}"/>
          </c:import>
          <jsp:include page="searchToolBar.jsp"></jsp:include>
          <table id="myTable" cellpadding="0" cellspacing="0" border="0" class="dataList" width="99%">
            <tr>              
              <th class="tdTravel wordWrap"> <div> 行程 </div>
              </th>
              <th class="wordWrap" style="width:80px;"> <div> 乘客 </div>
              </th>
              <th class="wordWrap" style="width:90px;"> <div> 票号 </div>
              </th>
              <th class="tdTicketPrice wordWrap"> <div> 票面价 </div>
              </th>
              <th class="tdMachinebuilding wordWrap"> <div> 机建 </div>
              </th>
              <th class="tdFuel wordWrap"> <div> 燃油 </div>
              </th>
              <th class="tdPlatform wordWrap"> <div> 平台 </div>
              </th>
              <th> <div> 预定/<font color='red'>出票</font>/大PNR</div>
              </th>
              <th class="tdPolicy wordWrap"> <div> 政策 </div>
              </th>
              <th class="tdAmount wordWrap"> <div> 金额 </div>
              </th>
              <th  class="tdType wordWrap"> <div> 类型 </div>
              </th>
              <th class="tdStatus wordWrap" style="width:8%;"> <div> 订单状态 </div>
              </th>
              <th class="tdOperator wordWrap"> <div> 操作人 </div>
              </th>
              
              <c:if test="${param.orderType==91}" >
              <th class="tdPayer wordWrap"> <div>支付人</div>
              </c:if>
              <c:if test="${param.orderType==93}" >
              <th class="tdPayer wordWrap"> <div>退款人</div>
              </c:if>              
              </th>
              <th class="tdOrderTime wordWrap" style="width:60px;"> <div> 流水号 </div>
              </th>
              <th  class="tdAction wordWrap" colspan="2" style="width:160px;"> <div> 操作 </div>
              </th>
              
            </tr>
            <c:forEach var="groupInfo" items="${ulf.list}" varStatus="status">
                <tr>
                  <td class="tdTravel wordWrap" rowspan="<c:out value="${groupInfo.orderCount}" />
                ">
                <div>
                   <c:out value="${groupInfo.carrier}" escapeXml="false"/><br/>
                  <c:if test="${groupInfo.saleOrder.todayFlight}"> <font style="color: red">
                    <c:out value="${groupInfo.flight}" escapeXml="false"/>
                    </font> </c:if>
                  <c:if test="${groupInfo.saleOrder.todayFlight==false}">
                    <c:out value="${groupInfo.flight}" escapeXml="false" />
                  </c:if>
                </div>
                  </td>
                <td class="tdPassengers wordWrap" rowspan="<c:out value="${groupInfo.orderCount}" />
                ">
                <div>
                  <c:out value="${groupInfo.passenger}"  escapeXml="false"/>
                </div>
                  </td>
                <td class="tdTicketNumber wordWrap" rowspan="<c:out value="${groupInfo.orderCount}" />
                ">
                <div>
                  <c:out value="${groupInfo.ticketNo}"  escapeXml="false" />
                </div>
                  </td>
                <td class="tdTicketPrice wordWrap" rowspan="<c:out value="${groupInfo.orderCount}" />            ">
                <div>
                  <c:out value="${groupInfo.ticketPrice}" />
                </div>
                  </td>
                <td class="tdMachinebuilding wordWrap" rowspan="<c:out value="${groupInfo.orderCount}" />       ">
                <div>
                  <c:out value="${groupInfo.airportPrice}" />
                </div>
                  </td>
                <td class="tdFuel wordWrap" rowspan="<c:out value="${groupInfo.orderCount}" />    ">
                <div>
                  <c:out value="${groupInfo.fuelPrice}" />
                </div>
                  </td>
                <td class="tdPlatform wordWrap"><div>
                    <c:if test="${!empty groupInfo.saleOrder.platform}">
                      <c:out value="${groupInfo.saleOrder.platform.name}" />
                    </c:if>
                  </div></td>
                <td width="140">
                    <c:out value="${groupInfo.saleOrder.showPNR}" escapeXml="false"/>
                  </td>
                <td class="tdPolicy wordWrap"><div>
                    <c:out value="${groupInfo.saleOrder.rebate}" />
                  </div></td>
                <td class="tdAmount wordWrap"><div>
                    <c:out value="${groupInfo.saleOrder.showTotalAmount}" />
                  </div></td>
                <td class="tdType wordWrap"><div>
                    <c:out value="${groupInfo.saleOrder.tranTypeText}" />
                  </div></td>
                <td class="tdStatus wordWrap"><div>
                    <c:out value="${groupInfo.saleOrder.statusText}"  escapeXml="false"/>
                    </div></td>
                <td class="tdOperator wordWrap"><div> 
                    <c:out value="${groupInfo.saleOrder.showEntryOperatorName}" /> </div></td>
                
                <c:if test="${param.orderType==91}">
                <td class="tdPayer wordWrap"><div>
                    <c:if test="${!empty groupInfo.saleOrder.showPayOperator}"> <c:out value="${groupInfo.saleOrder.showPayOperatorName}" />
                       </c:if>
                  </div></td>
                  </c:if>
                <c:if test="${param.orderType==93}">
                <td class="tdPayer wordWrap"><div>
                    <c:if test="${!empty groupInfo.saleOrder.showRefundOperator}"> <c:out value="${groupInfo.saleOrder.showRefundOperatorName}" />
                       </c:if>
                  </div></td>
                  </c:if>
                    
                <td class="tdOrderTime wordWrap" style="width:60px;"><div>
                    <a href="<%=path%>/airticket/listAirTicketOrder.do?thisAction=view&id=<c:out value='${groupInfo.saleOrder.id}' />"><c:out value="${groupInfo.saleOrder.orderNo}" /></a>
                  </div></td>
                <td class="tdAction0 wordWrap" style="width:90px;"><div>
                    <c:out value='${groupInfo.saleOrder.tradeOperate}' escapeXml="false"/>
                  </div></td>
                <td class="tdAction1 wordWrap" style="width:70px;"><div>
                    <c:out value='${groupInfo.saleOrder.commonOperateText}' escapeXml="false"/>
                  </div></td>
                <!-- 加载参数 --> 
                
                <!-- 退票手续费 -->
                <input id="cyr<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.cyr}'/>" type="hidden"/>
                <input id="flightClass<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.flightClass}'/>" type="hidden"/>
                
                <input id="tmpPlatformId<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.platform.id}'/>" type="hidden"/>
                <input id="tmpCompanyId<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.company.id}'/>" type="hidden"/>
                <input id="tmpAccountId<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.account.id}'/>" type="hidden"/>
                <input id="tmpGroupId<c:out value='${groupInfo.saleOrder.id}' />"  value="<c:out value='${groupInfo.saleOrder.orderGroup.id}' />"  type="hidden" />
                <input id="memo<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.memo}' />" type="hidden" />
                <input id="tmpPlatformId<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.platform.id}'/>" type="hidden"/>
                <input id="tmpCompanyId<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.company.id}'/>" type="hidden"/>
                <input id="tmpAccountId<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.account.id}'/>" type="hidden"/>
                <input id="tmpGroupMarkNo<c:out value='${groupInfo.saleOrder.id}' />"  value="<c:out value='${groupInfo.saleOrder.groupMarkNo}' />"  type="hidden" />
                <input id="tmpGroupMarkNo<c:out value='${groupInfo.saleOrder.id}' />"  value="<c:out value='${groupInfo.saleOrder.groupMarkNo}' />"  type="hidden" />
                <input id="tmpPlatformId9<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.platform.id}'/>" type="hidden"/>
                <input id="tmpCompanyId9<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.company.id}'/>" type="hidden"/>
                <input id="tmpAccountId9<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.account.id}'/>" type="hidden"/>
                <input id="tmpGroupId9<c:out value='${groupInfo.saleOrder.id}' />"  value="<c:out value='${groupInfo.saleOrder.orderGroup.id}' />"  type="hidden" />
                <input id="tmpTranType9<c:out value='${groupInfo.saleOrder.id}' />"  value="<c:out value='${groupInfo.saleOrder.tranType}' />"  type="hidden" />
                <input type="hidden"  id="ticketPrice<c:out value='${groupInfo.saleOrder.id}' />"  value="<c:out value='${groupInfo.saleOrder.ticketPrice}' />"/>
                <input type="hidden"  id="ticke"  value="<c:out value='${groupInfo.saleOrder.totalAmount}' />"/>
                <input type="hidden" value="<c:out value='${groupInfo.saleOrder.adultCount}' />">
                <input type="hidden"  id="totalPerson<c:out value='${groupInfo.saleOrder.id}' />"  value="<c:out value='${groupInfo.saleOrder.totalPerson}' />"/>
                <input id="ticketPrice<c:out value='${groupInfo.saleOrder.id}' />"  value="<c:out value='${groupInfo.saleOrder.ticketPrice}' />" type="hidden"  />
                <input type="hidden"  id="ticke"  value="<c:out value='${groupInfo.saleOrder.totalAmount}' />"/>
                <input type="hidden" value="<c:out value='${groupInfo.saleOrder.adultCount}' />">
                <input id="tmpPlatformId12<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.platform.id}'/>" type="hidden"/>
                <input id="tmpCompanyId12<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.company.id}'/>" type="hidden"/>
                <input id="tmpAccountId12<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.account.id}'/>" type="hidden"/>
                <input id="actualAmountTemp4<c:out value='${groupInfo.saleOrder.id}' />" type="hidden" value="<c:out value='${groupInfo.saleOrder.totalAmount}'/>"/>
                <input type="hidden" id="TmpFromPCAccount5" value="<c:out value="${groupInfo.saleOrder.platform.name}" />
                "/>
                <input id="tmpPlatformId5<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.platform.id}'/>" type="hidden"/>
                <input id="tmpCompanyId5<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.company.id}'/>" type="hidden"/>
                <input id="tmpAccountId5<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.account.id}'/>" type="hidden"/>
                <input type="hidden" id="TmpFromPCAccount14" value="<c:out value="${groupInfo.saleOrder.platform.name}" />
                "/>
                <input id="tmpPlatformId14<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.platform.id}'/>" type="hidden"/>
                <input id="tmpCompanyId14<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.company.id}'/>" type="hidden"/>
                <input id="tmpAccountId14<c:out value='${groupInfo.saleOrder.id}' />" value="<c:out value='${groupInfo.saleOrder.account.id}'/>" type="hidden"/>
                <input type="hidden"  id="platformName<c:out value='${groupInfo.saleOrder.id}'/>" value="<c:out value="${groupInfo.saleOrder.platform.name}" />
                "/>
                <input type="hidden"  id="companyName<c:out value='${groupInfo.saleOrder.id}'/>" value="<c:out value="${groupInfo.saleOrder.company.name}" />
                "/>
                <input type="hidden"  id="accountName<c:out value='${groupInfo.saleOrder.id}'/>" value="<c:out value="${groupInfo.saleOrder.account.name}" />
                "/> </tr>
              <c:if test="${groupInfo.orderCount>1}">
                <c:forEach var="info" begin="1" items="${groupInfo.orderList}" varStatus="status3">
                  <c:if test="${info.businessType==2}">
                    <tr style="background-color: #CCCCCC"> 
                  </c:if>
                  <c:if test="${info.businessType!=2}">
                    <tr> 
                  </c:if>
                  
                    <td class="tdPlatform wordWrap"><div>
                        <c:if test="${!empty info.platform}">
                          <c:out value="${info.platform.name}" />
                        </c:if>
                      </div></td>
                    <td>
                        <c:out value="${info.showPNR}"  escapeXml="false"/>
                      </td>
                    <td class="tdPolicy wordWrap"><div>
                        <c:out value="${info.rebate}" />
                      </div></td>
                    <td class="tdAmount wordWrap"><div>
                        <c:out value="${info.showTotalAmount}" />
                      </div></td>
                    <td class="tdType wordWrap"><div>
                        <c:out value="${info.tranTypeText}" />
                      </div></td>
                    <td class="tdStatus wordWrap"><div>
                        <c:out value="${info.statusText}" />
                        </div></td>
                    <td class="tdOperator wordWrap"><div> 
                        <c:out value="${info.showEntryOperatorName}" />
                         </div></td>
                     <c:if test="${param.orderType==91}">
                    <td class="tdPayer wordWrap"><div>
                        <c:if test="${!empty info.showPayOperator}"> 
                          <c:out value="${info.showPayOperatorName}" /></c:if>
                      </div></td>
                      </c:if>
                        <c:if test="${param.orderType==93}">
                    <td class="tdPayer wordWrap"><div>
                        <c:if test="${!empty info.showRefundOperator}"> 
                          <c:out value="${info.showRefundOperatorName}" /></c:if>
                      </div></td>
                      </c:if>
                    <td class="tdOrderTime wordWrap" style="width:60px;"><div>
                          <a href="<%=path%>/airticket/listAirTicketOrder.do?thisAction=view&id=<c:out value='${info.id}' />"><c:out value="${info.orderNo}" /></a>  
                      </div></td>
                    <td class="tdAction0 wordWrap" style="width:90px;"><div>
                        <c:out value='${info.tradeOperate}' escapeXml="false"/>
                      </div></td>
                    <td class="tdAction1 wordWrap" style="width:70px;"><div>
                        <c:out value='${info.commonOperateText}' escapeXml="false"/>
                      </div></td>
                  </tr>
                  <!-- 加载参数 --> 
                  <!-- 退票手续费 -->
                  <input id="cyr<c:out value='${info.id}' />" value="<c:out value='${info.cyr}'/>" type="hidden"/>
                  <input id="flightClass<c:out value='${info.id}' />" value="<c:out value='${info.flightClass}'/>" type="hidden"/>  
                  <input id="tmpPlatformId<c:out value='${info.id}' />" value="<c:out value='${info.platform.id}'/>" type="hidden"/>
                  <input id="tmpCompanyId<c:out value='${info.id}' />" value="<c:out value='${info.company.id}'/>" type="hidden"/>
                  <input id="tmpAccountId<c:out value='${info.id}' />" value="<c:out value='${info.account.id}'/>" type="hidden"/>
                  <input id="tmpGroupId<c:out value='${info.id}' />"  value="<c:out value='${info.orderGroup.id}' />"  type="hidden" />
                  <input id="tmpPlatformId5<c:out value='${info.id}' />" value="<c:out value='${info.platform.id}'/>" type="hidden"/>
                  <input id="tmpCompanyId5<c:out value='${info.id}' />" value="<c:out value='${info.company.id}'/>" type="hidden"/>
                  <input id="tmpAccountId5<c:out value='${info.id}' />" value="<c:out value='${info.account.id}'/>" type="hidden"/>
                  <input id="tmpPlatformId9<c:out value='${info.id}' />" value="<c:out value='${info.platform.id}'/>" type="hidden"/>
                  <input id="tmpCompanyId9<c:out value='${info.id}' />" value="<c:out value='${info.company.id}'/>" type="hidden"/>
                  <input id="tmpAccountId9<c:out value='${info.id}' />" value="<c:out value='${info.account.id}'/>" type="hidden"/>
                  <input id="tmpGroupId9<c:out value='${info.id}' />"  value="<c:out value='${info.orderGroup.id}' />"  type="hidden" />
                  <input id="tmpPlatformId12<c:out value='${info.id}' />" value="<c:out value='${info.platform.id}'/>" type="hidden"/>
                  <input id="tmpCompanyId12<c:out value='${info.id}' />" value="<c:out value='${info.company.id}'/>" type="hidden"/>
                  <input id="tmpAccountId12<c:out value='${info.id}' />" value="<c:out value='${info.account.id}'/>" type="hidden"/>
                  <input id="tmpPlatformId14<c:out value='${info.id}' />" value="<c:out value='${info.platform.id}'/>" type="hidden"/>
                  <input id="tmpCompanyId14<c:out value='${info.id}' />" value="<c:out value='${info.company.id}'/>" type="hidden"/>
                  <input id="tmpAccountId14<c:out value='${info.id}' />" value="<c:out value='${info.account.id}'/>" type="hidden"/>
                  <input id="memo<c:out value='${info.id}'/>" value="<c:out value='${info.memo}' />" type="hidden"  />
                  <input id="tmpGroupMarkNo<c:out value='${info.id}' />"  value="<c:out value='${info.orderGroup.no}' />"  type="hidden" />
                  <input id="tmpTranType9<c:out value='${info.id}' />"  value="<c:out value='${info.tranType}' />"  type="hidden" />
                  <input id="ticketPrice<c:out value='${info.id}' />" value="<c:out value='${info.ticketPrice}'/>"  type="hidden"   />
                  <input id="ticke"  value="<c:out value='${info.totalAmount}' />" type="hidden"  />
                  <input type="hidden" value="<c:out value='${info.adultCount}' />">
                  <input type="hidden"  id="totalPerson<c:out value='${info.id}' />"  value="<c:out value='${info.totalPerson}' />"/>
                  <input id="actualAmountTemp4<c:out value='${info.id}' />" type="hidden" value="<c:out value='${info.totalAmount}'/>"/>
                  <input type="hidden" id="TmpFromPCAccount5" value="<c:out value="${info.platform.name}" />
                  "/>
                  <input type="hidden" id="TmpFromPCAccount14" value="<c:out value="${info.platform.name}" />
                  "/>
                  <input type="hidden"  id="platformName<c:out value='${info.id}'/>" value="<c:out value="${info.platform.name}" />
                  "/>
                  <input type="hidden"  id="companyName<c:out value='${info.id}'/>" value="<c:out value="${info.company.name}" />
                  "/>
                  <input type="hidden"  id="accountName<c:out value='${info.id}'/>" value="<c:out value="${info.account.name}" />
                  "/> </c:forEach>
              </c:if>
            </c:forEach>
          </table>
          <table width="100%" style="margin-top: 5px;">
            <tr>
              <td></td>
              <td align="right"><div> 共有订单&nbsp;
                  <c:out value="${ulf.groupCount}" />
                  &nbsp;条
                  明细&nbsp;
                  <c:out value="${ulf.totalRowCount}"></c:out>
                  &nbsp;条&nbsp;&nbsp;&nbsp;&nbsp; [ <a href="JavaScript:turnToPage(document.forms[0],0)">首页</a> | <a href="JavaScript:turnToPage(document.forms[0],1)">上一页</a> | <a href="JavaScript:turnToPage(document.forms[0],2)">下一页</a> | <a href="JavaScript:turnToPage(document.forms[0],3)"> 末页</a>]
                  页数:
                  <c:out value="${ulf.intPage}" />
                  /
                  <c:out value="${ulf.pageCount}" />
                  ] 第<input type="text" name="myIntPage" value="<c:out value='${ulf.intPage}'/>"  style="width:20px;" onkeyup="goMyIntPage(this.form)">页</div></td>
            </tr>
          </table>
            </td>
        </tr>
      </table>
    </html:form>
  </div>
</div>
<jsp:include page="manageDiv.jsp"></jsp:include>

</body>
</html>
