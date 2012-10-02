<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
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
		
		<script type='text/javascript' src='<%=path %>/dwr/interface/platComAccountStore.js'></script>
 		 <script type='text/javascript' src='<%=path %>/dwr/engine.js'></script>
		<script type='text/javascript' src='<%=path %>/dwr/util.js'></script>
	<link type="text/css" href="../_js/development-bundle/themes/base/ui.all.css" rel="stylesheet" />
	<script type="text/javascript" src="../_js/development-bundle/jquery-1.3.2.js"></script>
	<script type="text/javascript" src="../_js/development-bundle/ui/ui.core.js"></script>
	<script type="text/javascript" src="../_js/development-bundle/ui/ui.draggable.js"></script>
	<script type="text/javascript" src="../_js/base/FormUtil.js"></script>	
	<script type="text/javascript" src="../_js/development-bundle/ui/ui.dialog.js"></script>
	<script type="text/javascript" src="../_js/tsms/loadAccount.js"></script>	
	<script type="text/javascript" src="../_js/base/FormUtil.js"></script>	
	<script type="text/javascript" src="../_js/menu.js"></script>	
	
	<script type="text/javascript">		
		   $(function(){				
			//  loadPlatList('platform_Id','company_Id','account_Id');
			  loadPlatListByType('platform_Id','company_Id','account_Id','1');			  
			  var onfoucsObj=document.getElementById("platform_Id");
			  onfoucsObj.focus();
			});
	</script>		
	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
				<html:form action="/airticket/airticketOrder.do">
						<html:hidden property="forwardPage" value="addOrder"/>
						<input type="hidden" id="pnrNo" value="<c:out value="${tempPNR.pnr}" />"/>
					<table width="100%" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td width="10" height="10" class="tblt"></td>
							<td height="10" class="tbtt"></td>
							<td width="10" height="10" class="tbrt"></td>
						</tr>
						<tr>
							<td width="10" class="tbll"></td>
							<td valign="top" class="body">
								<c:import url="../_jsp/mainTitle.jsp"
									charEncoding="UTF-8">
									<c:param name="title1" value="票务管理" />
									<c:param name="title2" value="正常订单录入" />									
								</c:import>
								<div class="searchBar">
									<table cellpadding="0" cellspacing="0" border="0"
										class="searchPanel">
										<tr>
											<td style="display: none">
												PNR:
												<html:text property="pnr" styleClass="colorblue2 p_5"
													style="width:120px;" />
												<input  type="button" name="button" id="button" value="导入" class="submit greenBtn" onclick=" getPNRinfo()" />
											</td>	
											<td></td>
											<td></td>
											<td></td>
											<td></td>																					
											<td>	
												<a href="#" onclick="showDiv()" style="font-size: 20">[黑屏信息解析]</a>
												<a href="../airticket/addOrderByHand.jsp" style="font-size: 20">[手工录入]</a>
												<font color="red"><c:out value="${msg}"/></font>
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
                                              <c:out value="${flight1.flightNo}" /></br>
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
									<td>类型</td>
										<td>											
									<select  Class="colorblue2 p_5" disabled="disabled">												
										<option value="1">卖出</option>							
									</select>
									<html:hidden property="statement_type" value="1"/>
										</td>
										<td>
											平台</td><td>	
										<html:select property="platformId" styleClass="colorblue2 p_5" styleId="platform_Id"
										style="width:150px;" onchange="loadCompanyListByType('platform_Id','company_Id','account_Id','1')">		
												<option value="">请选择</option>															
									</html:select>
										</td>
										<td>
											公司</td><td>
											<html:select property="companyId" styleClass="colorblue2 p_5" styleId="company_Id"
										style="width:150px;" onchange="loadAccountByType('platform_Id','company_Id','account_Id','1')">		
										<option value="">请选择</option>								
									</html:select>
										</td>
										<td>帐号</td>
										<td>											
											<html:select property="accountId" styleClass="colorblue2 p_5" styleId="account_Id"
										style="width:100px;" >		
										<option value="">请选择</option>								
									</html:select>
										</td>										
										<td>
											订单号
											<html:text property="airOrderNo" styleClass="colorblue2 p_5"
												style="width:150px;" />
										</td>										
										<td>
											政策
											<html:text property="rebate" styleClass="colorblue2 p_5"
												style="width:50px;" />
										</td>
										<td>
											金额
											<html:text property="totalAmount" styleClass="colorblue2 p_5"
												style="width:100px;" />
										</td>
										<td>
											大PNR
											<html:text property="bigPnr" value="${tempPNR.b_pnr}" styleClass="colorblue2 p_5"
												style="width:80px;" />
										</td>
										<td>
											<input name="label" type="button" class="button1" value="创 建"
												onclick="add();">
										</td>

									</tr>
								</table>
							</td>
						</tr>
					</table>
				</html:form>
			</div>
		</div>
<div id="dialog" title="PNR信息导入">
	<form action="../airticket/airticketOrder.do?thisAction=airticketOrderByBlackPNR"  method="post" id="form3" >		  
		  	<table>
			     <tr>		    
				     <td>
				     	<html:hidden property="forwardPage" value="addOrder"/>
				      	<textarea rows="15" cols="100" name="pnrInfo" style="overflow: auto"></textarea>		     
				     </td>
			    </tr>
				<tr>
					<td align="center">
						<input  value="提交" type="submit" class="button1">
					</td>
				</tr>			   
			</table>
		</form>
</div>
		<script type="text/javascript">
		      function getPNRinfo(){
		      
		         var pnr = document.forms[0].pnr.value;
		         if(pnr==""){
		              alert("请正确填写PNR!");
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
		         var airOrderNo = document.forms[0].airOrderNo.value;
		         var bigPnr = document.forms[0].bigPnr.value;
		         var rebate = document.forms[0].rebate.value;
		         
		          var totalAmount = document.forms[0].totalAmount.value;
		          totalAmount=$.trim(totalAmount);
		          totalAmount=totalAmount.replace(/\，/g,""); //去除 ，
		       
		         if(pnr==""){
		              alert("请先导入PNR!");
		              return false;
		         }
		         
		         var account_IdVal=$('#account_Id').val();
		        if(account_IdVal==""){
		            alert("请正确选择平台/公司/账户！");
		            return false;
		        } 
		         if(airOrderNo==""){
		             alert("请正确填写订单号!");
		              return false;
		         }
		         
		          if(!isNum(rebate)||rebate==""){
				      alert("请正确填写政策!");
				      return false;
				   }  
				  
				 //alert("totalAmount:"+totalAmount+"--length:"+totalAmount.length);
				  if(!isNum(totalAmount)||totalAmount==""){
				      alert("请正确填写金额!");
				      return false;
				   } 
				    
		         document.forms[0].action="airticketOrder.do?thisAction=createOrder";
		         trim(document.forms[0]);
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
			  $('#dialog').draggable({cancle: 'form'}); 
			}	
		</script>
	</body>
</html>
