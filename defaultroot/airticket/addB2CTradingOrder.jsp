<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
 %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />
		<script src="../_js/common.js" type="text/javascript"></script>
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
			
		<script type="text/javascript">
		
		   $(function(){
			   
		      platComAccountStore.getB2CAgentList(loadAgent);	
			  function loadAgent(loadAgentList)
			   {
			  
			   		for(var i=0;i<loadAgentList.length;i++)
			   		{
			   			document.forms[0].agentId.options[i] = new Option(loadAgentList[i].name,loadAgentList[i].id);
			   		}
			   }
		   });
		  
		
			
		</script>
		
	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
					<html:form action="/airticket/airticketOrder.do">
						<html:hidden property="forwardPage" value="addB2CTradingOrder"/>
						<input type="hidden" id="pnrNo" value="<c:out value="${tempPNR.pnr}" />"/>
						<html:hidden property="status" value="10"/>						
					<table width="100%" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td width="10" height="10" class="tblt"></td>
							<td height="10" class="tbtt"></td>
							<td width="10" height="10" class="tbrt"></td>
						</tr>
						<tr>
							<td width="10" class="tbll"></td>
							<td valign="top" class="body">
								<c:import url="../_jsp/mainTitle.jsp?title1=票务管理&title2=B2C订单录入"
									charEncoding="UTF-8" />

								<div class="searchBar">
									<table cellpadding="0" cellspacing="0" border="0"
										class="searchPanel">
										<tr>
											<td>
												PNR:
												<html:text property="pnr" styleClass="colorblue2 p_5"
													style="width:120px;" />
											</td>
											<td>
												<input type="button" name="button" id="button" value="导入"
													class="submit greenBtn" onclick=" getPNRinfo()" style="display: none;"/>
													<a href="#" onclick="showDiv()">  [黑屏信息解析]  </a>
												<a href="../airticket/handworkAddTradingOrder.jsp">	[手工录入]</a>
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
												PNR
											</div>
										</th>
										
										<th>
											<div>
												大PNR
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
												航班日期
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
												航段
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
									</tr>

									<tr>
										<td>
											<c:out value="${tempPNR.pnr}" />
										</td>
										<td>
                                            <c:out value="${tempPNR.b_pnr}" />
										</td>
										<td>
									   <c:forEach var="flight" items="${tempPNR.tempFlightList}">
                                              <c:out value="${flight.cyr}" /></br>
                                              </c:forEach>   
										</td>
										<td>
                                        	<c:forEach var="flight1" items="${tempPNR.tempFlightList}">
                                              <c:out value="${flight1.airline}" /></br>
                                              </c:forEach>
										</td>

									
										<td>
										<c:forEach var="flight3" items="${tempPNR.tempFlightList}">
                                              <c:out value="${flight3.starttime}" /></br>
                                              </c:forEach>
										</td>
										<td>
										<c:forEach var="flight4" items="${tempPNR.tempFlightList}">
										 <c:out value="${flight4.cabin}" /></br>
                                             
                                         </c:forEach>
										</td>
										<td>
										<c:forEach var="flight5" items="${tempPNR.tempFlightList}">
                                              <c:out value="${flight5.discount}" /></br>
                                         </c:forEach>
										</td>
										<td>
										 	<c:forEach var="flight2" items="${tempPNR.tempFlightList}">
                                              <c:out value="${flight2.departureCity}" />-
                                               <c:out value="${flight2.destineationCity}" /></br>
                                              </c:forEach>
										</td>
										<td>
											<c:forEach var="tempPassenger1" items="${tempPNR.tempPassengerList}">
                                              <c:out value="${tempPassenger1.name}" /></br>
                                         </c:forEach>
										</td>
										<td>
										<c:forEach var="tempTickets" items="${tempPNR.tempTicketsList}" varStatus="sta">
                                              <c:out value="${tempTickets}" /></br>
                                         </c:forEach>
										</td>
									
									</tr>
								</table>
								<table>
									<tr>
									<td>
									类型																				
									<select Class="colorblue2 p_5" disabled="disabled" >												
										<option value="2">卖出</option>							
									</select>
										<html:hidden property="statement_type" value="2"/>
									</td>
										<td>
											平台
											<select Class="colorblue2 p_5" disabled="disabled"
										style="width:150px;" >		
												
												<option value="3">散客－B2C</option>														
									</select>
										</td>
										<td>
											公司
											<select Class="colorblue2 p_5" disabled="disabled"
										style="width:150px;">		
										<option value="2">珠海泰申发展有限公司</option>							
									</select>
										</td>
										<td>
											收款方式
											<select  Class="colorblue2 p_5" disabled="disabled"
												style="width:150px;" >
												<option value="2">欠款(系统)</option>	
											</select>
											<html:hidden property="platformId" value="88888"/>
											<html:hidden property="companyId" value="88888"/>
											<html:hidden property="accountId" value="88888"/>
										</td>
										<td>
											客户
											<html:select property="agentId" styleClass="colorblue2 p_5"
										style="width:150px;" >		
										<option value="">请选择</option>								
									</html:select>
										</td>
									</tr>
									<tr>
										<td>
											政策
											<html:text property="rebate" styleClass="colorblue2 p_5"
												style="width:150px;" />
										</td>
										<td>
											金额
											<html:text property="totalAmount" styleClass="colorblue2 p_5"
												style="width:150px;" />
										</td>
										<td>
											大PNR
											<html:text property="bigPnr" styleClass="colorblue2 p_5"
												style="width:150px;" />
										</td><td></td><td></td>
									</tr>
									<tr>
										<td>
											是否打印行程单
											<html:multibox property="id"
												value="${info.userId}"></html:multibox>
										</td>
										<td>
											行程单费用
											<html:text property="documentPrice" styleClass="colorblue2 p_5"
												style="width:150px;" />
										</td>
										<td>
											保险费
											<html:text property="insurancePrice" styleClass="colorblue2 p_5"
												style="width:150px;" />
										</td>
										<td>
											<input name="label" type="button" class="button1" value="创 建"
												onclick="add()">
										</td>
                                      <td></td>
									</tr>
								</table>

							</td>
						</tr>
					</table>
			
				</html:form>
			</div>
			
				<div id="dialog" title="PNR信息导入">
		<p id="validateTips"></p>
	<form action="../airticket/airticketOrder.do?thisAction=airticketOrderByBlackPNR"  method="post" id="form3" >
		<fieldset>
		      <html:hidden property="forwardPage" value="addB2CTradingOrder"/>
		  	    <table>
		     <tr>
		    
		     <td>
		      <textarea rows="15" cols="90" name="pnrInfo"></textarea>
		     
		     </td>
		    </tr>
			<tr>
			<td>
			<input value="提交" type="submit" >
			</td>
			</tr>
			   
			</table>
		</fieldset>
		</form>
	</div>
		</div>
		<script type="text/javascript">
		      function getPNRinfo(){
		      
		         var pnr = document.forms[0].pnr.value;
		         if(pnr==""){
		              alert("请正确填写PNR");
		              return false;
		         }
		         document.forms[0].action="airticketOrder.do?thisAction=airticketOrderByPNR";
                 document.forms[0].submit();
                 
		      }
		      
		      
		       //是否只包含数字
				function isNum(b){
				   		var re=/^([1-9][0-9]*|0)(\.[0-9]{0,2})?$/;
				   		return(re.test(b));
				}
		       function add(){
		      
		         var pnr = document.forms[0].pnrNo.value;
		         var bigPnr = document.forms[0].bigPnr.value;
		         var rebate = document.forms[0].rebate.value;
		         var totalAmount = document.forms[0].totalAmount.value;
		         
		         var documentPrice = document.forms[0].documentPrice.value;//行程单费用
		         var insurancePrice = document.forms[0].insurancePrice.value;//保险费
		         if(pnr==""){
		              alert("请先导入PNR!");
		              return false;
		         }
		         
		         if(bigPnr==""){
		             alert("请正确填写大PNR!");
		              return false;
		         }
		          if(!isNum(rebate)||rebate==""){
				      alert("请正确填写政策!");
				      return false;
				   }  
				  
				  if(!isNum(totalAmount)||totalAmount==""){
				      alert("请正确填写金额!");
				      return false;
				   }  
				   
				   
			     if(!isNum(documentPrice)||documentPrice==""){
				      alert("请正确填写行程单费用!");
				      return false;
				   }  
				  
				  if(!isNum(insurancePrice)||insurancePrice==""){
				      alert("请正确填写保险费!");
				      return false;
				   }  
				    
		         document.forms[0].action="airticketOrder.do?thisAction=createB2CTradingOrder";
                 document.forms[0].submit();
                 
		      }
		      
		$(function(){
		        
			$("#dialog").dialog({
				bgiframe: true,
				autoOpen: false,
				height: 550,
				width:650,
				modal: true
		    });
		    });
	
		 //黑屏导入
		 function showDiv(){

			  $('#dialog').dialog('open');
			 
			}
		
		</script>
	</body>
</html>
