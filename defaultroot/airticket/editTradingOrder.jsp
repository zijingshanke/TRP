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
	   	<script type='text/javascript' src='<%=path %>/dwr/interface/platComAccountStore.js'></script>
	   	<script type='text/javascript' src='<%=path %>/dwr/interface/passengerBiz.js'></script>
	 	<script type='text/javascript' src='<%=path %>/dwr/engine.js'></script>
		<script type='text/javascript' src='<%=path %>/dwr/util.js'></script>
		<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" src="../_js/loadAccount.js"></script>
		<script src="../_js/calendar/WdatePicker.js" type="text/javascript"></script>
	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
			<div>
					
       <c:import url="../_jsp/mainTitle.jsp?title1=票务管理&title2=编辑订单"	 charEncoding="UTF-8" />
								
				<html:form action="airticket/airticketOrder.do?thisAction=editTradingOrder" method="post" >
				<input type="hidden" name="groupMarkNo" value="<c:out value='${airticketOrder.groupMarkNo}'/>"/>
				<input type="hidden" name="airticketOrder.statement.statementNo" value="<c:out value='${airticketOrder.statement.statementNo}'/>"/>
				航班信息：  
								<input name="label" type="button" class="button1" value="添 加"
									onclick="addFlight()" >
							<table cellpadding="0" cellspacing="0" border="0" id="flightTable"
									class="dataList">
									<tr>		
									  <th><div>出发地 </div></th>
									  <th><div>目的地 </div></th>
									  <th><div>出发日期 </div></th>
									  <th><div>航班号 </div></th>
									  <th><div>舱位 </div></th>
									  <th><div>折扣 </div></th>
									  <th><div>操作 </div></th>
							       </tr>
							       <c:forEach var="flight" items="${airticketOrder.flights}">
							      <tr>	
							            <input type="hidden" name="flightIds" value="<c:out value='${flight.id}'/>"/>
									    <td><input type="text" name="startPoints"  value="<c:out value='${flight.startPoint}'/>" class="colorblue2 p_5"	style="width:50px;"/>  </td>
								        <td><input type="text" name="endPoints"  value="<c:out value='${flight.endPoint}'/>" class="colorblue2 p_5"	style="width:50px;" /></td>
								        <td><input type="text" name="boardingTimes" value="<c:out value='${flight.boardingTime}'/>" class="colorblue2 p_5"	style="width:120px;" onfocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" /></td>
								        <td><input type="text" name="flightCodes"  value="<c:out value='${flight.flightCode}'/>" class="colorblue2 p_5"	style="width:50px;" /></td>
								        <td><input type="text" name="flightClasss" value="<c:out value='${flight.flightClass}'/>" class="colorblue2 p_5"	style="width:50px;"  /></td>
								         <c:if test="${!empty flight.discount}">
								        <td><input type="text" name="discounts" value="<c:out value='${flight.discount}'/>"  class="colorblue2 p_5"	style="width:50px;"/></td> 
								        </c:if>
								         <c:if test="${empty flight.discount}">
								        <td><input type="text" name="discounts" value="0"  class="colorblue2 p_5"	style="width:50px;"/></td> 
								        </c:if>
								        <td><a href="#"  onclick='delRow(this);'>删除</a></td>
							       </tr>
							      </c:forEach> 
							</table>
								
		        								<br/><br/><P>乘客信息：
								<input name="label" type="button" class="button1" value="添 加"
									onclick="addPassenger()" >
							<table cellpadding="0" cellspacing="0" border="0" id="passengerTable"
									class="dataList">
									<tr>		
									  <th><div>姓名 </div></th>
									  <th><div>类型 </div></th>
									  <th><div>票号 </div></th>
									  <th><div>证件号</div></th>
									  <th><div>操作 </div></th>
								
							       </tr>
							        <c:forEach var="passenger" items="${airticketOrder.passengers}">
							        <tr id="s001">	
							            <input type="hidden" name="passids" value="<c:out value='${passenger.id}'/>"/>		
									    <td><input type="text" name="passNames" value="<c:out value='${passenger.name}'/>" class="colorblue2 p_5"	style="width:80px;"/></td>
								        <td> 
								         <select name="passTypes">
								           <option value="1">成人</option>
								           <option value="2">儿童</option>
								           <option value="3">婴儿</option>
								         </select>
								        </td>
								        <td><input type="text" name="passTicketNumbers" value="<c:out value='${passenger.ticketNumber}'/>" class="colorblue2 p_5"	style="width:120px;"/> </td>
								        <td><input type="text" name="passAirorderIds"  value="<c:out value='${passenger.cardno}'/>"  class="colorblue2 p_5"	style="width:120px;"/> </td>
								         <td><a href="#"  onclick='delRow(this);'>删除</a></td>
							       </tr>
							      </c:forEach> 
							</table>	
							
							
						<div id="opid2">	
						<br/><input name="label" type="button" class="button1" value="添 加"	onclick="addop()" >
						<c:forEach var="airticketOrder" items="${airticketOrderList.list}" varStatus="status">
								<br/>主订单信息：
								 <input type="hidden" name="airticketOrderIds" value="<c:out value='${airticketOrder.id}'/>"/>
								<table cellpadding="0" cellspacing="0" border="0" id="table1" class="dataList">
									<tr>
									 <td> 机票类型<select name="ticketType" id="ticketType" class="text ui-widget-content ui-corner-all">		
										<option value="1">普通</option>	
										<option value="3">B2C</option>							
									</select></td>
									     <td><div>出票PNR 	<html:text property="drawPnr"  name="airticketOrder" value="${airticketOrder.drawPnr}"  styleClass="colorblue2 p_5"	style="width:50px;" styleId="drawPnr${status.count}" /> </div></td>
									     <td><div>预定PNR	<html:text property="subPnr" name="airticketOrder" value="${airticketOrder.drawPnr}"  styleClass="colorblue2 p_5"	style="width:50px;" styleId="subPnr${status.count}"/> </div></td>		
										<td><div>大PNR	<html:text property="bigPnr" name="airticketOrder"  styleId="bigPnr${status.count}" value="${airticketOrder.bigPnr}"  styleClass="colorblue2 p_5"	style="width:50px;" /> </div></td>	
										 <c:if test="${!empty airticketOrder.ticketPrice}">
									     <td><div>票面价	<html:text property="ticketPrice" name="airticketOrder"  styleId="ticketPrice${status.count}" value="${airticketOrder.ticketPrice}"  styleClass="colorblue2 p_5"	style="width:50px;" /> </div></td>
									     </c:if>
									      <c:if test="${empty airticketOrder.ticketPrice}">
									     <td><div>票面价	<html:text property="ticketPrice" name="airticketOrder"  styleId="ticketPrice${status.count}" value="0"  styleClass="colorblue2 p_5"	style="width:50px;" /> </div></td>
									     </c:if>
									     <c:if test="${!empty airticketOrder.airportPrice}">
									     <td><div>机建税	<html:text property="airportPrice" name="airticketOrder" styleId="airportPrice${status.count}"  value="${airticketOrder.airportPrice}"  styleClass="colorblue2 p_5"	style="width:50px;" /> </div></td>
									     </c:if>
									       <c:if test="${empty airticketOrder.airportPrice}">
									     <td><div>机建税	<html:text property="airportPrice" name="airticketOrder" styleId="airportPrice${status.count}" value="0"  styleClass="colorblue2 p_5"	style="width:50px;" /> </div></td>
									     </c:if>
									     <c:if test="${!empty airticketOrder.fuelPrice}">
									     <td><div>燃油税	<html:text property="fuelPrice" name="airticketOrder"  styleId="fuelPrice${status.count}" value="${airticketOrder.fuelPrice}"  styleClass="colorblue2 p_5"	style="width:50px;" /> </div></td>
									     </c:if>
									      <c:if test="${empty airticketOrder.fuelPrice}">
									     <td><div>燃油税	<html:text property="fuelPrice" name="airticketOrder" styleId="fuelPrice${status.count}" value="0"  styleClass="colorblue2 p_5"	style="width:50px;" /> </div></td>
									     </c:if>
									      <c:if test="${!empty airticketOrder.handlingCharge}">
									     <td><div>手续费	<html:text property="handlingCharge" name="airticketOrder" styleId="handlingCharge${status.count}"  value="${airticketOrder.handlingCharge}"  styleClass="colorblue2 p_5"	style="width:50px;" /> </div></td>
									     </c:if>
									      <c:if test="${empty airticketOrder.handlingCharge}">
									     <td><div>手续费	<html:text property="handlingCharge" name="airticketOrder" styleId="handlingCharge${status.count}"  value="0"  styleClass="colorblue2 p_5"	style="width:50px;" /> </div></td>
									     </c:if>
									     <c:if test="${!empty airticketOrder.rebate}">
									     <td><div>政策	<html:text property="rebate" name="airticketOrder" styleId="rebate${status.count}"  value="${airticketOrder.rebate}" styleClass="colorblue2 p_5"	style="width:50px;" /> </div></td>
									     </c:if>
									      <c:if test="${empty airticketOrder.rebate}">
									     <td><div>政策	<html:text property="rebate" name="airticketOrder" styleId="rebate${status.count}"  value="0" styleClass="colorblue2 p_5"	style="width:50px;" /> </div></td>
									     </c:if>
									</tr>
									<tr>
									
										<td><div>订单号	<html:text property="airOrderNo" name="airticketOrder" styleClass="colorblue2 p_5"	style="width:120px;" /> </div>	</td>
										<td>订单类型 
									
										
										<html:select property="tranType" name="airticketOrder" styleId="tranType${status.count}"  onchange="checkType('tranType${status.count}','ktype${status.count}')">
										   <html:option value=""></html:option>
										   
										   <html:option value="">--请选择--</html:option>
											<html:option value="2">卖出机票</html:option>
											<html:option value="1">买入机票</html:option>
											<html:option value="3">退票</html:option>
											<html:option value="4">废票</html:option>
											<html:option value="5">改签</html:option>
										</html:select>
										</td>
										<td><div>类型
										<input type="hidden"  name="ktypeValue" value="<c:out value="${airticketOrder.status}"/>"/>
									<select id="ktype<c:out value='${status.count}'/>" name="status" onfocus="checkType('tranType<c:out value='${status.count}'/>','ktype<c:out value='${status.count}'/>')">
									    <option value="<c:out value="${airticketOrder.status}"/>"><c:out value="${airticketOrder.statusText}"/></option>
									
									</select>
									 </div></td>
									    	
									    <td>原因
									    <select>
									    <option value="0" selected="selected">--请选择--</option>
											<option value="1">取消</option>
											<option value="2">航班延误</option>
											<option value="3">重复付款</option>
											<option value="4">申请病退</option>
											<option value="12">多收票款</option>
											<option value="13">只退税</option>
											<option value="14">升舱换开</option>
											<option value="18">客规</option>
									    </select>
									    </td>
									     <td><div>客规	
									     <select>
									     <option value="0" selected="selected">--请选择--</option>
											<option value="0">0%</option>
											<option value="5">5%</option>
											<option value="10">10%</option>
											<option value="20">20%</option>
											<option value="30">30%</option>
											<option value="50">50%</option>
											<option value="100">100%</option>
									     </select>
									     </div></td>
									     <td></td><td></td><td></td><td></td>
									    </tr>  
							 <tr>		
					            <td>类别	
						             <html:select property="statement.type" name="airticketOrder" value="${airticketOrder.statement.type}">
						                <html:option value="2">卖出</html:option>
						                <html:option value="1">买入</html:option>
						             </html:select>
						             </td>
						       	<td><div>平台
									<input type="hidden" name="tmpPlatformId" value="<c:out value="${airticketOrder.statement.platComAccount.platform.id}"/>"/>	
								   <select name="platformId"  id="platformId<c:out value='${status.count}'/>" onchange="loadCompanyList('platformId<c:out value='${status.count}'/>','companyId<c:out value='${status.count}'/>','accountId<c:out value='${status.count}'/>')">		
																									
								   </select>
									</div></td>
									<td>公司	
									<input type="hidden" name="tmpCompanyId" value="<c:out value="${airticketOrder.statement.platComAccount.company.id}"/>"/>	
									<select name="companyId" id="companyId<c:out value='${status.count}'/>" onchange="loadAccount('platformId<c:out value='${status.count}'/>','companyId<c:out value='${status.count}'/>','accountId<c:out value='${status.count}'/>')" >		
									<option value="">请选择</option>								
								     </select>
										</td>      
						        <td> 帐号
						     <input type="hidden" name="tmpAccountId" value="<c:out value="${airticketOrder.statement.platComAccount.account.id}"/>"/>
						   <select name="accountId" id="accountId<c:out value='${status.count}'/>" class="text ui-widget-content ui-corner-all">		
								<option value="">请选择</option>								
							</select>
						        </td>
						         <c:if test="${!empty airticketOrder.statement.totalAmount}">
						        <td>应收<input type="text" name="statement_totalAmount" value="<c:out value='${airticketOrder.statement.totalAmount}'/>" class="colorblue2 p_5"	style="width:50px;"/> </td>
						        </c:if>
						        <c:if test="${empty airticketOrder.statement.totalAmount}">
						        <td>应收<input type="text" name="statement_totalAmount" value="0" class="colorblue2 p_5"	style="width:50px;"/> </td>
						        </c:if>
						        <c:if test="${!empty airticketOrder.statement.actualAmount}">
						        <td>实收<input type="text" name="statement_actualAmount" value="<c:out value='${airticketOrder.statement.actualAmount}'/>" class="colorblue2 p_5"	style="width:50px;"/> </td>
						        </c:if>
						        <c:if test="${empty airticketOrder.statement.actualAmount}">
						        <td>实收<input type="text" name="statement_actualAmount" value="0" class="colorblue2 p_5"	style="width:50px;"/> </td>
						        </c:if>
						        <td style="display: none;">交易时间<input type="text" name="statementtDate" class="colorblue2 p_5"	style="width:120px;" value="<c:out value='${airticketOrder.statement.optTime}'/>" disabled="disabled"/> </td>
						        <td></td><td></td><td></td>
							 </tr>
							</table>
							
					</c:forEach>	
				   <input type="hidden" value="<c:out value="${airticketOrderList.totalRowCount}"/>" id="aoCount"/>	
							</div> <div id="opId"></div>
							<input name="label" type="button" class="button1" value="保 存"
									onclick="addOrder()" >
                          
				</html:form>
			</div>
		</div>
		<script type="text/javascript">
		
		    function addFlight(){
		  
		          //添加航班
		       $("#flightTable").append("<tr><input type='hidden' name='flightIds' value='0'/>"+		
									    "<td><input type='text' name='startPoints' class='colorblue2 p_5' style='width:50px;'/></td>"
								        +"<td><input type='text' name='endPoints' class='colorblue2 p_5' style='width:50px;' /></td>"
								        +"<td><input type='text' name='boardingTimes' class='colorblue2 p_5' style='width:120px;' onfocus=\"WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})\" +/></td>"
								        +"<td><input type='text' name='flightCodes' class='colorblue2 p_5' style='width:50px;'/></td>"
								        +"<td><input type='text' name='flightClasss' class='colorblue2 p_5' style='width:50px;'/></td>"
								        +"<td><input type='text' name='discounts' class='colorblue2 p_5' style='width:50px;'/></td>" 
								        +"<td><a href='#'   onclick='delRow(this);'>删除 </a></td>"
							       +"</tr>");
		       }
		       
		     //添加乘客
		    function addPassenger(){ 
		    
		        $("#passengerTable").append("<tr><input type='hidden' name='passids' value='0'/>"+		
									   "<td><input type='text' name='passNames' class='colorblue2 p_5' style='width:80px;'/></td>"
								       +"<td><select name='passTypes'>"
								       +"<option value='1'>成人</option>"
								       +"<option value='2'>儿童</option>"
								       +"<option value='3'>婴儿</option>"
								       +"</select></td>"
								       +" <td><input type='text' name='passTicketNumbers' class='colorblue2 p_5' style='width:120px;'/> </td>"
								       +" <td><input type='text' name='passAirorderIds' class='colorblue2 p_5' style='width:120px;'/> </td>"
								       +" <td><a href='#'  onclick='delRow(this);'>删除</a></td>"   
							       +"</tr>");
			//$("#s001").clone().prependTo("#passengerTableBody"); 				       
		    }
		    
		    //添加订单
		   
		    var  count= $("#aoCount").val()+1;
		   function addop(){ 
		      			
		      			count++;								
					 $("#opId").append("<div><br/>主订单信息：<input type='hidden' name='airticketOrderIds' value='0'/>"
					                 +"<a href='#' onclick='delDiv(this);'>删除 </a><table cellpadding='0' cellspacing='0' border='0' id='table1' class='dataList'>"
									  +"<tr> "
									    +"<tr><td> 机票类型<select name='ticketType' id='ticketType'>"		
										+"<option value='1'>普通</option>"	
										+"<option value='3'>B2C</option>"							
									    +"</select></td>"
									    +"<td><div>出票PNR  <input type='text' name='drawPnr'  Class='colorblue2 p_5'	style='width:50px;' id='drawPnr"+count+"'/> </div></td>"
									    +" <td><div>预定PNR	<input type='text' name='subPnr'   Class='colorblue2 p_5'	style='width:50px;' id='subPnr"+count+"' /> </div></td>	"	
										+"<td><div>大PNR	   <input type='text' name='bigPnr' Class='colorblue2 p_5'	style='width:50px;' id='bigPnr"+count+"' /> </div></td>	"
									    +" <td><div>票面价	<input type='text' value='0' name='ticketPrice' Class='colorblue2 p_5'	style='width:50px;' id='ticketPrice"+count+"' /> </div></td>"
									    +" <td><div>机建税	<input type='text' value='0' name='airportPrice'  Class='colorblue2 p_5'	style='width:50px;' id='airportPrice"+count+"' /> </div></td>"
									    +" <td><div>燃油税	<input type='text' value='0'  name='fuelPrice' Class='colorblue2 p_5'	style='width:50px;' id='fuelPrice"+count+"' /> </div></td>"
									    +" <td><div>手续费	<input type='text' value='0'  name='handlingCharge' Class='colorblue2 p_5'	style='width:50px;' id='handlingCharge"+count+"' /> </div></td>"
									    +" <td><div>政策	 <input type='text' value='0'  name='rebate' Class='colorblue2 p_5'	style='width:50px;' id='rebate"+count+"' /> </div></td></tr>"
										+"<td><div>订单号	<input type='text'  name='airOrderNo' Class='colorblue2 p_5'	style='width:120px;' /> </div>	</td>"
										+"<td>订单类型 "
										+"<select name='tranType' id='tranType"+count+"' onchange=checkType('tranType"+count+"','ktype"+count+"')>"
										    +"<option value=''>--请选择--</option>"
											+"<option value='2'>卖出机票</option>"
											+"<option value='1'>买入机票</option>"
											+"<option value='3'>退票</option>"
											+"<option value='4'>废票</option>"
											+"<option value='5'>改签</option>"
										+"</select>"
										+"</td>"
										+"<td><div>类型"
									+"<select id='ktype"+count+"' name='status'>"
									   +" <option value=''>--请选择--</option>"
									+"</select>"
									+" </div></td>"
									
									   +" <td>原因"
									    +"<select>"
									    +"<option value='0' selected='selected'>--请选择--</option>"
											+"<option value='1'>取消</option>"
											+"<option value='2'>航班延误</option>"
											+"<option value='3'>重复付款</option>"
											+"<option value='4'>申请病退</option>"
											+"<option value='12'>多收票款</option>"
											+"<option value='13'>只退税</option>"
											+"<option value='14'>升舱换开</option>"
											+"<option value='18'>客规</option>"
									    +"</select>"
									   +" </td>"
									    +" <td><div>客规	"
									    +" <select>"
									    +" <option value='0' selected='selected'>--请选择--</option>"
										+"	<option value='0'>0%</option>"
										+"	<option value='5'>5%</option>"
										+"	<option value='10'>10%</option>"
										+"	<option value='20'>20%</option>"
										+"	<option value='30'>30%</option>"
										+"	<option value='50'>50%</option>"
										+"	<option value='100'>100%</option>"
									   +"  </select>"
									  +"   </div></td>"
									 +"   <td></td><td></td><td></td><td></td>"
									 +"   </tr>  "
							+" <tr>		"
					        +"<td>类别"	
				            +"<select name='statement.type' id='sType' onchange='checksType()'>"
				            +"  <option value='2'>卖出</option>"
				            +"<option value='1'>买入</option>"
				            +"</select>"
				            +" </td>"
				            +"<td><div>平台<select name='platformId' id='platformId"+count+"' onclick=loadCompanyList('platformId"+count+"','companyId"+count+"','accountId"+count+"')></select> </div></td>"	
							+"<td>公司	<select name='companyId'  id='companyId"+count+"'  onclick=loadAccount('platformId"+count+"','companyId"+count+"','accountId"+count+"') >"
							+"<option value=''>请选择</option></select></td>"
						    +"    <td> 帐号"
						   +"<select name='accountId' id='accountId"+count+"' class='text ui-widget-content ui-corner-all'>		"
						+"		<option value=''>请选择</option>								"
							+"</select>"
						   +"     </td>"
						   +"     <td>应收<input type='text' name='statement_totalAmount' value='0' class='colorblue2 p_5'	style='width:50px;'/> </td>"
						     +"   <td>实收<input type='text' name='statement_actualAmount' value='0' class='colorblue2 p_5'	style='width:50px;'/> </td>"
							 +"<td></td><td></td><td></td></tr>"
							+"</table></div>");
		      			 loadDate("platformId"+count,"companyId"+count,"accountId"+count);			
		      			 
		      		var aoCount=$("#aoCount").val();	
		      		if(aoCount!=""){
		      		   var drawPnrValue=$("#drawPnr"+aoCount).val();
		      		   var subPnrValue=$("#subPnr"+aoCount).val();
		      		   var bigPnrrValue=$("#bigPnr"+aoCount).val();
		      		   var ticketPriceValue=$("#ticketPrice"+aoCount).val();
		      		   var airportPriceValue=$("#airportPrice"+aoCount).val();
		      		   var fuelPriceValue=$("#fuelPrice"+aoCount).val();
		      		   var handlingChargeValue=$("#handlingCharge"+aoCount).val();
		      		   var rebateValue=$("#rebate"+aoCount).val();
		      		   
		      		   $("#drawPnr"+count).val(drawPnrValue);
		      		   $("#subPnr"+count).val(subPnrValue);
		      		   $("#bigPnr"+count).val(bigPnrrValue);
		      		   $("#ticketPrice"+count).val(ticketPriceValue);    
		      		   $("#airportPrice"+count).val(airportPriceValue);
		      		   $("#fuelPrice"+count).val(fuelPriceValue);
		      		   $("#handlingCharge"+count).val(handlingChargeValue);
		      		   $("#rebate"+count).val(rebateValue);
		      		    
		      		} 	       
		    }
		    //删除行
			function delRow(_this){
				//alert($(_this).get(0));
				$(_this).parent().parent().remove();
			}
			
			 //删除div
			function delDiv(_this){
				
				$(_this).parent().remove();
			}
			//订单类型	
			function checkType(tranType,ktype){
			   
			   var tranType= $("#"+tranType).val();
		      if(tranType==3){
		         $("#"+ktype).empty();
		        $("#"+ktype).append(
		                "<option value='19'>T-退票订单，等待审核</option>"
						+"<option value='21'>T-退票审核通过，等待退款</option>"
						+"<option value='22'>T-退票已退款，交易结束</option>"
						+"<option value='23'>T-退票订单审核未通过</option>"
						+"<option value='20'>Z-支付成功，等待退票</option>"
		          );
			    
			   }else if(tranType==4){
			       $("#"+ktype).empty();
			       $("#"+ktype).append(
					            "<option value='29'>F-废票订单，等待审核</option>"
								+"<option value='31'>F-废票审核通过，等待退款</option>"
								+"<option value='32'>F-废票已退款，交易结束</option>"
								+"<option value='33'>F-废票订单审核未通过</option>"
		          );
			    
			   }else  if(tranType==5){
			      $("#"+ktype).empty();
			      $("#"+ktype).append(
			                    "<option value='40'>G-改签订单，等待审核</option>"
								+"<option value='41'>G-改签通过，等待支付</option>"
								+"<option value='43'>G-改签已支付，等待确认</option>"
								+"<option value='45'>G-改签完成，交易结束</option>"
								+"<option value='44'>G-改签未通过，交易结束</option>"
			      );
			     
			   }else  if(tranType==1||tranType==2){
		     
			    $("#"+ktype).empty();
			    $("#"+ktype).append(
							"<option value='1'>X-新订单等待支付</option>"
							+"<option value='2'>S-申请成功，等待支付</option>"
							+"<option value='5'>Y-已经出票，交易结束</option>"
							+"<option value='4'>Q-取消出票，等待退款</option>"
							+"<option value='6'>Y-已退款，交易结束</option>"
							+"<option value='3'>Z-支付成功，等待出票</option>"
							+"<option value='7'>S-锁定订单</option>"
							+"<option value='8'>J-解锁订单</option>"
							);
							
			    
			   }else{
			  
			      //  $("#ktype").append("<option value='0'>--请选择--</option>");
		
			   }
			
			}	
         //类型
         function checksType(){
         
              var tranTypetext= $("#tranType").find("option:selected").text();
			   if(tranTypetext=='正常'){
			    var sType=$("#sType").val();
			    $("#tranType").find("option:selected").text("正常").val(sType);
			   }
			   // alert($("#tranType").val());
         }
         
         function addOrder(){
             
              var startPoints=$("input[name='startPoints']");
              if( checkCount(startPoints,"请正确填写出发地 ！")==false){
                 return false;
              }
              
              var endPoints=$("input[name='endPoints']");
              if(checkCount(endPoints,"请正确填写目的地 ！")==false){
                 return false;
              } 
              
              var boardingTimes=$("input[name='boardingTimes']");   
              if(checkCount(boardingTimes,"请正确填写出发日期  ！")==false){
                 return false;
              }   
              var flightCodes=$("input[name='flightCodes']");   
              if(checkCount(flightCodes,"请正确填写航班号  ！")==false){
                 return false;
              } 
               var flightClasss=$("input[name='flightClasss']");   
              if(checkCount(flightClasss,"请正确填写舱位  ！")==false){
                 return false;
              } 
               var discounts=$("input[name='discounts']");   
              if(checkNum(discounts,"请正确填写折扣   ！")==false){
                 return false;
              } 
              
              var passNames=$("input[name='passNames']");   
              if(checkCount(passNames,"请正确填写乘客姓名  ！")==false){
                 return false;
              } 
              
         
              
              
                var tranType=$("select[name='tranType']");
               if(checkCount(tranType,"请正确选择订单类型 ！")==false){
                 return false;
              } 
                 var status=$("select[name='status']");
               if(checkCount(status,"请正确选择类型 ！")==false){
                 return false;
              } 
            
         
          var platformId=$("select[name='platformId']");
               if(checkCount(platformId,"请正确选择平台 ！")==false){
                 return false;
              } 
            var companyId=$("select[name='companyId']");
               if(checkCount(companyId,"请正确选择公司 ！")==false){
                 return false;
              } 
             var accountId=$("select[name='accountId']");
               if(checkCount(accountId,"请正确选择付款账号 ！")==false){
                 return false;
              }     
            
              
              
       /*      var drawPnr=$("input[name='drawPnr']");   
              if(checkCount(drawPnr,"请正确填写出票PNR ！")==false){
                 return false;
              } 
            
       /*    var passTicketNumbers=$("input[name='passTicketNumbers']"); 
              if(checkCount(passTicketNumbers,"请正确填写票号 ！")==false){
                 return false;
              } 
              
              var passAirorderIds=$("input[name='passAirorderIds']");   
              if(checkCount(passAirorderIds,"请正确填写证件号 ！")==false){
                 return false;
              } */
         
             
                      var ticketPrice=$("input[name='ticketPrice']");   
               if(checkNum(ticketPrice,"请正确填写票面价!")==false){
                  return false;
              }  
               var airportPrice=$("input[name='airportPrice']");   
               if(checkNum(airportPrice,"请正确填写机建税!")==false){
                  return false;
              }  
              
               var fuelPrice=$("input[name='fuelPrice']");   
               if(checkNum(fuelPrice,"请正确填写燃油税!")==false){
                  return false;
              }  
              var handlingCharge=$("input[name='handlingCharge']");   
               if(checkNum(handlingCharge,"请正确填写手续费!")==false){
                  return false;
              }   
              var rebate=$("input[name='rebate']");   
               if(checkNum(rebate,"请正确填写政策!")==false){
                  return false;
              }   
              
               var stotalAmount=$("input[name='statement_totalAmount']");   
               if(checkNum(stotalAmount,"请正确填写应收金额!")==false){
                  return false;
              }   
                  
              
              var sactualAmount=$("input[name='statement_actualAmount']");   
               if(checkNum(sactualAmount,"请正确填写实收金额!")==false){
                  return false;
              }   
         
              document.forms[0].submit();   
         }
		
		function checkCount(arry,msg){
		    for(var i=0;i<arry.length;i++){
                   if(arry[i].value==""||arry[i].value==null){
                     alert(msg);
                     return false;
                  }
            }
		}
		
				//验证金额
		function checkNum(arry,msg){
		
		    for(var i=0;i<arry.length;i++){
                   if(arry[i].value==""||arry[i].value==null){
                     alert(msg);
                     return false;
                  }else if(!isNum(arry[i].value)){
                     alert(msg);
                     return false;
                  }
            }
		}
		     //验证金额
				function isNum(b){
				   		var re=/^([1-9][0-9]*|0)(\.[0-9]{0,2})?$/;
				   		return(re.test(b));
				}
		</script>
		
		<script type="text/javascript">
		$(function(){
		
		   var aoCount= $("#aoCount").val();
		    
		 var tmpPlatformId=$("input[name='tmpPlatformId']");  
		 var tmpCompanyId=$("input[name='tmpCompanyId']");  
		 var tmpAccountId=$("input[name='tmpAccountId']");   
		  for(var i=1;i<=aoCount;i++){
		      
		       var j=i-1;
		       
		         //  loadDate("platformId"+i,"companyId"+i,"accountId"+i);
		           
		           //设置下拉框  平台初始值 默认选中
				  var tmpPlatformValue=tmpPlatformId[j];
				  var tmpCompanyValue=tmpCompanyId[j];  	
				  var tmpAccountValue=tmpAccountId[j]; 
				  if(tmpPlatformValue!=null&&tmpPlatformValue!=""){	
				     
				        loadPlatListSelected('platformId'+i,'companyId'+i,'accountId'+i,tmpPlatformValue.value,tmpCompanyValue.value,tmpAccountValue.value);
				   }else{
				     
				       loadPlatList('platformId'+i,'companyId'+i,'accountId'+i);
				     }
		           
		         //  checkType("tranType"+i,"ktype"+i);
		   }
		   
		  
		   
		//   	setTimeout("loaddef()",100);
		  
		});
		
		//选中下拉框
		function loaddef(){
		
		 var aoCount= $("#aoCount").val();
		 var tmpPlatformId=$("input[name='tmpPlatformId']");  
		 var tmpCompanyId=$("input[name='tmpCompanyId']");  
		 var tmpAccountId=$("input[name='tmpAccountId']");  
		 var ktypeValue=$("input[name='ktypeValue']");
		  
		  for(var i=1;i<=aoCount;i++){
		       
		       var j=i-1;
		         
		           //设置下拉框  平台初始值 默认选中
			      
			      	var tmpPlatformValue=tmpPlatformId[j];
			      	if(tmpPlatformValue!=""){
			   		$("#platformId"+i+" option[value='"+tmpPlatformValue.value+"']").attr("selected", true);
			   		}
			   		
		
		   }
		   	setTimeout("loaddef2()",500);
		}
		
		
				
		//选中下拉框
		function loaddef2(){
		
		 var aoCount= $("#aoCount").val();
		 var tmpPlatformId=$("input[name='tmpPlatformId']");  
		 var tmpCompanyId=$("input[name='tmpCompanyId']");  
		 var tmpAccountId=$("input[name='tmpAccountId']");  
		 var ktypeValue=$("input[name='ktypeValue']");
		  
		  for(var i=1;i<=aoCount;i++){
		       
		       var j=i-1;
		         
			   		//设置下拉框  公司初始值 默认选中
			      	var tmpCompanyValue=tmpCompanyId[j];
			      	//alert(tmpCompanyValue.value);
			      
			      	if(tmpCompanyValue!=""){
			   		$("#companyId"+i+" option[value='"+tmpCompanyValue.value+"']").attr("selected", true);
			   		}
			   		
			   		//设置下拉框  账户初始值 默认选中
			      	var tmpAccountValue=tmpAccountId[j];
			      	if(tmpAccountValue!=""){
			   		$("#accountId"+i+" option[value='"+tmpAccountValue.value+"']").attr("selected", true);
			   		}
			   		
			   		
			   		//设置下拉框  订单状态  初始值 默认选中
			      	var kv=ktypeValue[j];
			      	if(kv!=""){
			   		$("#ktype"+i+" option[value='"+kv.value+"']").attr("selected", true);
			   		}
		   }
		 
		 
		 }
		
		function loadDate(platformId,companyId,accountId){
				 platComAccountStore.getPlatFormList(function(data){
			   		for(var i=0;i<data.length;i++)
			   		{	
			   			    option = new Option(data[i].name,data[i].id);
			   			    document.getElementById(platformId).options.add(option);
			   			if(i==data.length-1){
			   			checkPlatform(platformId,companyId,accountId);
			   			}
			   		}
			   	//	setTimeout(function(){checkPlatform(platformId,companyId,accountId);},100);
			   });
			   
			}
		  
			function checkPlatform(platformId,companyId,accountId)//点击交易平台名称
			{
			  
				var plId = document.getElementById(platformId).value;
				
			platComAccountStore.getPlatComAccountListByPlatformId(plId,function(data)
			  {
			     $("#"+companyId).empty();
				//option = new Option("请选择","");
			   	//document.getElementById(companyId).options.add(option);
				for(var i=0;i<data.length;i++)
				{ 
					
					 option2 = new Option(data[i].company.name,data[i].company.id);
			   	     document.getElementById(companyId).options.add(option2);
			   	     if(i==data.length-1){
			   	     checkCompany(platformId,companyId,accountId);
			   	     }
				}
			//	setTimeout(function(){checkCompany(platformId,companyId,accountId);},100);
			});
			}
			
			function checkCompany(platformId,companyId,accountId) //点击公司名称
			{
			
				var comId =document.getElementById(companyId).value;
				var plaId = document.getElementById(platformId).value;
			
				platComAccountStore.getPlatComAccountListByCompanyId(comId,plaId,
			function(data1)
			{	
				if(data1.length<1)
				{
					  $("#"+accountId).empty();
					   option3 = new Option("请选择","");
			   	       document.getElementById(accountId).options.add(option3);
				}
			
				     $("#"+accountId).empty();
				     // option = new Option("请选择","");
			   	     // document.getElementById(accountId).options.add(option);
				for(var i=0;i<data1.length;i++)
				{
					if(data1[i].name != null || data1[i].name != "")
					{
						
					   option3 = new Option(data1[i].account.name,data1[i].account.id);
			   	       document.getElementById(accountId).options.add(option3);
					}
				}
			});
		 }
		</script>	
	</body>
</html>
