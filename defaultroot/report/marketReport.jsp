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
   	<script type='text/javascript' src='<%=path %>/dwr/interface/platComAccountStore.js'></script>
 	<script type='text/javascript' src='<%=path %>/dwr/engine.js'></script>
	<script type='text/javascript' src='<%=path %>/dwr/util.js'></script>
	<script src="../_js/common.js" type="text/javascript"></script>
	<script src="../_js/calendar/WdatePicker.js" type="text/javascript"></script>
	<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
	</head>
	<body>	
		<div id="mainContainer">
			<div id="container">
				<html:form action="/airticket/report.do?thisAction=marketReport">
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
								<c:import url="../_jsp/mainTitle.jsp?title1=票务管理&title2=原始销售报表"
									charEncoding="UTF-8" />

								<div class="searchBar">
									<table cellpadding="0" cellspacing="0" border="0"
										class="searchPanel">
										<tr>
											<td>
												  报表类型：
												<html:select property="businessType" styleClass="colorblue2 p_5"
													style="width:120px;" >
													<html:option value="">--请选择--</html:option>
													<html:option value="2">销售报表(卖出)</html:option>
													<html:option value="1">销售报表(买入)</html:option>
													</html:select>
											</td>
											
											<td>
												  平台：
											
									        <html:select property="platformId" styleId="platformId">
									            <html:option value="">请选择</html:option>
									        </html:select>
									        <input id="platformValue" type="hidden" value="<c:out value="${ulf.platformId}"/>"/>
											</td>
											<td>
												出票PNR
												<html:text property="drawPnr" styleClass="colorblue2 p_5"
													style="width:120px;" />
											</td>
						
											
											<td>
												票号
												<html:text property="ticketNumber" styleClass="colorblue2 p_5"
													style="width:150px;" />
											</td>
										</tr>
										<tr>
									       <td>
												操作人
												<html:text property="sysName" styleClass="colorblue2 p_5"
													style="width:100px;" />
											</td>
									      
									       <td>
												开始:
												<html:text property="userNo" styleClass="colorblue2 p_5"
													style="width:150px;" onfocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
													readonly="true" />
											</td>
											<td>
												结束
												<html:text property="userNo" styleClass="colorblue2 p_5"
													style="width:150px;" onfocus="WdatePicker({startDate:'%y-%M-01 23:59:59',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
													readonly="true" />
											</td>
											<td>
												订单编号
												<html:text property="airOrderNo" styleClass="colorblue2 p_5"
													style="width:150px;" />
											</td>
											
											<td>
												<input type="submit" name="button" id="button" value="提交"
													class="submit greenBtn" />
													
													<input type="button" name="button" id="button" value="导出" onclick="download()"
													class="submit greenBtn" />
											</td>
										</tr>
									</table>
									<hr />
								</div>
								<table width="100%" cellpadding="0" cellspacing="0" border="0"
									class="dataList">
									<tr>
										<th><div>订单时间</div></th>
										<th><div>平台</div></th>
										<th><div>返点</div></th>
										<th><div>预定PNR</div></th>
										<th><div>出票PNR</div></th>
										<th><div>大PNR</div></th>
										<th><div>乘客</div></th>
										<th><div>人数</div></th>
										<th><div>航段</div></th>
										<th><div>航段三字码</div></th>
										<th><div>承运人</div></th>
										<th><div>航班号</div></th>
										<th><div>舱位</div></th>
										<th><div>折扣</div></th>
										<th><div>单票面价</div></th>
										<th><div>单机建税</div></th>
										<th><div>单燃油税</div></th>
										<th><div>总票面价</div></th>
										<th><div>总机建税</div></th>
										<th><div>总燃油税</div></th>
										<th><div>起飞日期</div></th>
										<th><div>票号</div></th>
										<th><div>订单编号</div></th>
										<th><div>实收/付票款</div></th>
										<th><div>支付方式/操作人</div></th>
									</tr>
									 
                        		<c:forEach var="info" items="${ulf.list}" varStatus="status">
									<tr>
									<td></td>
									<td> <c:out value="${info.platform.showName}" /></td>
									<td> <c:out value="${info.statement.commission}" /></td>
									<td>
									<a href="<%=path %>/airticket/listAirTicketOrder.do?thisAction=view&id=<c:out value="${info.id}" />">
										<c:out value="${info.subPnr}" />
									</a>
									</td>
									<td>
									 <c:out value="${info.drawPnr}" />
									</td>
										<td>
									    <c:out value="${info.bigPnr}" />
										</td>
										<td>
									 <c:forEach var="passenger" items="${info.passengers}">
                                             <c:out value="${passenger.name}" /></br>
                                      </c:forEach>
										</td>
								    <td> 
								   <c:out value="${info.allPeople}" />
								    </td>
								    <td>
                                      <c:forEach var="flight" items="${info.flights}">
                                             <c:out value="${flight.startPointText}" />-
                                             <c:out value="${flight.endPointText}" /></br>
                                         </c:forEach>
									</td>
									<td>
								   <c:forEach var="flight" items="${info.flights}">
                                             <c:out value="${flight.startPoint}" />-
                                             <c:out value="${flight.endPoint}" /></br>
                                         </c:forEach>
									</td>
								
										<td>
                                         <c:forEach var="flight" items="${info.flights}">                                         	
                                             <c:out value="${flight.cyr}" /></br>
                                         </c:forEach>
										</td>
										
										<td>
                                         <c:forEach var="flight" items="${info.flights}">                                         	
                                             <c:out value="${flight.flightCode}" /></br>
                                         </c:forEach>
										</td>
										
										<td>
										<c:forEach var="flight" items="${info.flights}">
                                             <c:out value="${flight.flightClass}" /></br>
                                         </c:forEach>
										</td>
										
										<td>
										<c:forEach var="flight" items="${info.flights}">
                                             <c:out value="${flight.discount}" /></br>
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
									      
									   <td><c:out value="${info.allTotlePrice}" /></td>
										
										<td>
										 <c:out value="${info.allAirportPrice}" />
										</td>
										<td>
											 <c:out value="${info.allFuelPrice}" />
										</td>
										<td>
									   <c:forEach var="flight" items="${info.flights}">                                         	
                                             <c:out value="${flight.boardingTime}" /></br>
                                         </c:forEach>
										</td>
										
									   	<td>
										 <c:forEach var="passenger2" items="${info.passengers}">
                                             <c:out value="${passenger2.ticketNumber}" /></br>
                                         </c:forEach>
										</td>
										<td>
											 <c:out value="${info.airOrderNo}" />
										</td>
										<td><c:out value="${info.totalAmount}" />/
										0</td>
										<td>
										<c:out value="${info.statement.platComAccount.account.name}" />
										/<c:out value="${info.statement.sysUser.userName}" />
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
		
		<script type="text/javascript">
				$(function(){
				
					platComAccountStore.getPlatFormList(getData2);		   
		    
		   	  function getData2(data2)
			   {
			      
			   		for(var i=0;i<data2.length;i++)
			   		{		
			   			var j=i+1;
			   			document.all.platformId.options[j] = new Option(data2[i].name,data2[i].id);
			   			
			   		}
			   		
			   		//设置默认选中
			   		var platform=$("#platformValue").val();
			     	$("#platformId option[value='"+platform+"']").attr("selected", true);
			   		
			   		
			   }
			   
				
				});
		     function download(){
		     
		        document.forms[0].action="report.do?thisAction=downloadMarketReport";
		        document.forms[0].submit();
		     
		     }
		</script>
	</body>
</html>
