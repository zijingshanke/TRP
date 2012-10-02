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
	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
			<div>
				<html:form action="airticket/airticketOrder.do?thisAction=handworkAddTradingOrder" method="post" >
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
							      <tr>		
									    <td><input type="text" name="startPoints" class="colorblue2 p_5"	style="width:50px;"/>  </td>
								        <td><input type="text" name="endPoints" class="colorblue2 p_5"	style="width:50px;"/></td>
								        <td><input type="text" name="boardingTimes" class="colorblue2 p_5"	style="width:120px;"/></td>
								        <td><input type="text" name="flightCodes" class="colorblue2 p_5"	style="width:50px;"/></td>
								        <td><input type="text" name="flightClasss" class="colorblue2 p_5"	style="width:50px;"/></td>
								        <td><input type="text" name="discounts" class="colorblue2 p_5"	style="width:50px;"/></td> 
								        <td><a href="#"  onclick='delRow(this);'>删除</a></td>
							       </tr>
							</table>	
								
								<br/>乘客信息：
								<input name="label" type="button" class="button1" value="添 加"
									onclick="addPassenger()" >
							<table cellpadding="0" cellspacing="0" border="0" id="passengerTable"
									class="dataList">
									<tr>		
									  <th><div>姓名 </div></th>
									  <th><div>类型 </div></th>
									  <th><div>证件号 </div></th>
									  <th><div>票号 </div></th>
									  <th><div>操作 </div></th>
								
							       </tr>
							        <tr id="s001">		
									    <td><input type="text" name="passNames" class="colorblue2 p_5"	style="width:80px;"/></td>
								        <td> 
								         <select name="passTypes">
								           <option value="1">成人</option>
								           <option value="2">儿童</option>
								           <option value="3">婴儿</option>
								         </select>
								        </td>
								        <td><input type="text" name="passTicketNumbers" class="colorblue2 p_5"	style="width:120px;"/> </td>
								        <td><input type="text" name="passAirorderIds" class="colorblue2 p_5"	style="width:120px;"/> </td>
								         <td><a href="#"  onclick='delRow(this);'>删除</a></td>
							       </tr>
							</table>
								
							
						<div id="opid2">	
								<br/>主订单信息：<input name="label" type="button" class="button1" value="添 加"	onclick="addop()" >
								<table cellpadding="0" cellspacing="0" border="0" id="table1" class="dataList">
									<tr>
									    <td>类别	
							             <select name="statement_type" id="sType" onchange="checksType()">
							              <option value="2">卖出</option>
							               <option value="1">买入</option>
							             </select>
							             </td>
									     <td><div>出票PNR 	<html:text property="drawPnr" name="airticketOrder" styleClass="colorblue2 p_5"	style="width:50px;" /> </div></td>
									     <td><div>预定PNR	<html:text property="subPnr" name="airticketOrder" styleClass="colorblue2 p_5"	style="width:50px;" /> </div></td>		
										<td><div>大PNR	<html:text property="bigPnr" name="airticketOrder" styleClass="colorblue2 p_5"	style="width:50px;" /> </div></td>	
									     <td><div>票面价	<html:text property="ticketPrice" name="airticketOrder" styleClass="colorblue2 p_5"	style="width:50px;" /> </div></td>
									     <td><div>机建税	<html:text property="airportPrice" name="airticketOrder" styleClass="colorblue2 p_5"	style="width:50px;" /> </div></td>
									     <td><div>燃油税	<html:text property="fuelPrice" name="airticketOrder" styleClass="colorblue2 p_5"	style="width:50px;" /> </div></td>
									     <td><div>手续费	<html:text property="handlingCharge" name="airticketOrder" styleClass="colorblue2 p_5"	style="width:50px;" /> </div></td>
									     <td><div>政策	<html:text property="rebate" name="airticketOrder" styleClass="colorblue2 p_5"	style="width:50px;" /> </div></td>
									</tr>
									<tr>
									
								   <td> 机票类型<select name="ticketType" id="ticketType" class="text ui-widget-content ui-corner-all">		
										<option value="1">普通</option>	
										<option value="3">B2C</option>							
									</select></td>
								        
										<td><div>平台	
									   <select name="platformId" id="platformId" onclick="checkPlatform('platformId','companyId','accountId')">		
																										
									   </select>
										</div></td>
										<td>公司	
										<select name="companyId" id="companyId" onclick="checkCompany('platformId','companyId','accountId')" >		
										<option value="">请选择</option>								
									     </select>
										</td>
										<td><div>订单号	<html:text property="airOrderNo" name="airticketOrder" styleClass="colorblue2 p_5"	style="width:120px;" /> </div>	</td>
										<td>订单类型 
										<select name="tranType" id="tranType" onchange="checkType('tranType','ktype')">
										    <option value="">--请选择--</option>
											<option value="2">卖出机票</option>
											<option value="1">买入机票</option>
											<option value="3">退票</option>
											<option value="4">废票</option>
											<option value="5">改签</option>
										</select>
										</td>
										<td><div>类型
									<select id="ktype" name="status">
									    <option value="0">--请选择--</option>
									
									</select>
									 </div></td>
									    <td><div>是否有效	
									    <select>
									    <option value="1" >有效</option>
                                        <option value="0">失效</option>
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
									    <td></td>
									    </tr>  
							 <tr>		
							<td>
							    支付状态
							    <select name="statement_status">
							      <option value="0">未结算</option>
							      <option value="1">已结算</option>
							      <option value="2">部分结算</option>
							    </select>
							    </td>
						        <td> 帐号
						   <select name="accountId" id="accountId" class="text ui-widget-content ui-corner-all">		
								<option value="">请选择</option>								
							</select>
						        </td>
						        <td>应收<input type="text" name="statement_totalAmount" value="0" class="colorblue2 p_5"	style="width:50px;"/> </td>
						        <td>实收<input type="text" name="statement_actualAmount" value="0" class="colorblue2 p_5"	style="width:50px;"/> </td>
						        <td>交易时间<input type="text" name="statementtDate" class="colorblue2 p_5"	style="width:120px;"/> </td>
							 </tr>
							</table>
							</div> <div id="opId"></div>
							<input name="label" type="button" class="button1" value="保 存"
									onclick="addOrder()" >
                          
				</html:form>
			</div>
		</div>
		<script type="text/javascript">
		
		    function addFlight(){
		  
		          //添加航班
		       $("#flightTable").append("<tr>"+		
									    "<td><input type='text' name='startPoints' class='colorblue2 p_5' style='width:50px;'/></td>"
								        +"<td><input type='text' name='endPoints' class='colorblue2 p_5' style='width:50px;' /></td>"
								        +"<td><input type='text' name='boardingTimes' class='colorblue2 p_5' style='width:120px;'/></td>"
								        +"<td><input type='text' name='flightCodes' class='colorblue2 p_5' style='width:50px;'/></td>"
								        +"<td><input type='text' name='flightClasss' class='colorblue2 p_5' style='width:50px;'/></td>"
								        +"<td><input type='text' name='discounts' class='colorblue2 p_5' style='width:50px;'/></td>" 
								        +"<td><a href='#'   onclick='delRow(this);'>删除 </a></td>"
							       +"</tr>");
		       }
		       
		     //添加乘客
		    function addPassenger(){ 
		    
		        $("#passengerTable").append("<tr>"+		
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
		    var  count=1;
		   function addop(){ 
		      			
		      			count++;								
					 $("#opId").append("<div><br/>主订单信息：<a href='#'   onclick='delDiv(this);'>删除 </a></td><table cellpadding='0' cellspacing='0' border='0' id='table1' class='dataList'>"
									  +"<tr> <td>类别"	
							             +"<select name='statement_type' id='sType' onchange='checksType()'>"
							            +"  <option value='2'>卖出</option>"
							               +"<option value='1'>买入</option>"
							             +"</select>"
							            +" </td>"
									     +"<td><div>出票PNR  <input type='text' name='drawPnr'  Class='colorblue2 p_5'	style='width:50px;' /> </div></td>"
									    +" <td><div>预定PNR	<input type='text' name='subPnr'   Class='colorblue2 p_5'	style='width:50px;' /> </div></td>	"	
										+"<td><div>大PNR	   <input type='text' name='bigPnr' Class='colorblue2 p_5'	style='width:50px;' /> </div></td>	"
									    +" <td><div>票面价	<input type='text' value='0' name='ticketPrice' Class='colorblue2 p_5'	style='width:50px;' /> </div></td>"
									    +" <td><div>机建税	<input type='text' value='0' name='airportPrice'  Class='colorblue2 p_5'	style='width:50px;' /> </div></td>"
									    +" <td><div>燃油税	<input type='text' value='0'  name='fuelPrice' Class='colorblue2 p_5'	style='width:50px;' /> </div></td>"
									    +" <td><div>手续费	<input type='text' value='0'  name='handlingCharge' Class='colorblue2 p_5'	style='width:50px;' /> </div></td>"
									    +" <td><div>政策	 <input type='text' value='0'  name='rebate' Class='colorblue2 p_5'	style='width:50px;' /> </div></td></tr>"
									    +"<tr><td> 机票类型<select name='ticketType' id='ticketType'>"		
										+"<option value='1'>普通</option>"	
										+"<option value='3'>B2C</option>"							
									    +"</select></td>"
									    +"<td><div>平台<select name='platformId' id='platformId"+count+"' onclick=checkPlatform('platformId"+count+"','companyId"+count+"','accountId"+count+"')></select> </div></td>"	
										+"<td>公司	<select name='companyId'  id='companyId"+count+"'  onclick=checkCompany('platformId"+count+"','companyId"+count+"','accountId"+count+"') >"
										+"<option value=''>请选择</option></select></td>						"
										+"<td><div>订单号	<input type='text'  name='airOrderNo' Class='colorblue2 p_5'	style='width:120px;' /> </div>	</td>"
										+"<td>订单类型 "
										+"<select name='tranType' id='tranType"+count+"' onchange=checkType('tranType"+count+"','ktype"+count+"')>"
										    +"<option value=''>--请选择--</option>"
											+"<option >正常</option>"
											+"<option value='2'>卖出机票</option>"
											+"<option value='1'>买入机票</option>"
											+"<option value='3'>退票</option>"
											+"<option value='4'>废票</option>"
											+"<option value='5'>改签</option>"
										+"</select>"
										+"</td>"
										+"<td><div>类型"
									+"<select id='ktype"+count+"' name='status'>"
									   +" <option value='0'>--请选择--</option>"
									+"</select>"
									+" </div></td>"
									  +"<td><div>是否有效	"
									   +" <select>"
									    +"<option value='1' >有效</option>"
                                       +" <option value='0'>失效</option>"
                                        +"</select>"
									   +"  </div></td>"
									    	
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
									 +"   <td></td>"
									 +"   </tr>  "
							+" <tr>		"
							+"<td>"
							 +"   支付状态"
							+"    <select name='statement_status'>"
							+"      <option value='0'>未结算</option>"
							+"      <option value='1'>已结算</option>"
							+"      <option value='2'>部分结算</option>"
							+"    </select>"
							+"    </td>"
						    +"    <td> 帐号"
						   +"<select name='accountId' id='accountId"+count+"' class='text ui-widget-content ui-corner-all'>		"
						+"		<option value=''>请选择</option>								"
							+"</select>"
						   +"     </td>"
						   +"     <td>应收<input type='text' name='statement_totalAmount' value='0' class='colorblue2 p_5'	style='width:50px;'/> </td>"
						     +"   <td>实收<input type='text' name='statement_actualAmount' value='0' class='colorblue2 p_5'	style='width:50px;'/> </td>"
						   +"     <td>交易时间<input type='text' name='statementtDate' class='colorblue2 p_5'	style='width:120px;'/> </td>"
							 +"</tr>"
							+"</table></div>");
		      			 loadDate("platformId"+count,"companyId"+count,"accountId"+count);				       
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
		        $("#"+ktype).append("<option value='0'>--请选择--</option>"
		                +"<option value='19'>T-退票订单，等待审核</option>"
						+"<option value='21'>T-退票审核通过，等待退款</option>"
						+"<option value='22'>T-退票已退款，交易结束</option>"
						+"<option value='23'>T-退票订单审核未通过</option>"
						+"<option value='20'>Z-支付成功，等待退票</option>"
		          );
			    
			   }else if(tranType==4){
			       $("#"+ktype).empty();
			       $("#"+ktype).append("<option value='0'>--请选择--</option>"
					            +"<option value='29'>F-废票订单，等待审核</option>"
								+"<option value='31'>F-废票审核通过，等待退款</option>"
								+"<option value='32'>F-废票已退款，交易结束</option>"
								+"<option value='33'>F-废票订单审核未通过</option>"
		          );
			    
			   }else  if(tranType==5){
			      $("#"+ktype).empty();
			      $("#"+ktype).append("<option value='0'>--请选择--</option>"
			                    +"<option value='40'>G-改签订单，等待审核</option>"
								+"<option value='41'>G-改签通过，等待支付</option>"
								+"<option value='43'>G-改签已支付，等待确认</option>"
								+"<option value='45'>G-改签完成，交易结束</option>"
								+"<option value='44'>G-改签未通过，交易结束</option>"
			      );
			     
			   }else  if(tranType==1||tranType==2){
		
			    $("#"+ktype).empty();
			    $("#"+ktype).append("<option value='0'>--请选择--</option>"
							+"<option value='1'>X-新订单等待支付</option>"
							+"<option value='2'>S-申请成功，等待支付</option>"
							+"<option value='5'>Y-已经出票，交易结束</option>"
							+"<option value='4'>Q-取消出票，等待退款</option>"
							+"<option value='6'>Y-已退款，交易结束</option>"
							+"<option value='3'>Z-支付成功，等待出票</option>");
			    
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
              if(checkCount(discounts,"请正确填写折扣   ！")==false){
                 return false;
              } 
              
              var passNames=$("input[name='passNames']");   
              if(checkCount(passNames,"请正确填写乘客姓名  ！")==false){
                 return false;
              } 
              
              var passTicketNumbers=$("input[name='passTicketNumbers']"); 
              if(checkCount(passTicketNumbers,"请正确填写证件号 ！")==false){
                 return false;
              } 
              
              var passAirorderIds=$("input[name='passAirorderIds']");   
              if(checkCount(passAirorderIds,"请正确填写折扣票号 ！")==false){
                 return false;
              } 
              
       /*      var drawPnr=$("input[name='drawPnr']");   
              if(checkCount(drawPnr,"请正确填写出票PNR ！")==false){
                 return false;
              } 
            
               var ticketPrice=$("input[name='ticketPrice']").val();   
               if(!isNum(ticketPrice)||ticketPrice==""){
                  alert("请正确填写票面价 !");
                  return false;
              } 
              var airportPrice=$("input[name='airportPrice']").val();   
               if(!isNum(airportPrice)||airportPrice==""){
                  alert("请正确填写机建税 !");
                  return false;
              }   
              var fuelPrice=$("input[name='fuelPrice']").val();   
               if(!isNum(fuelPrice)||fuelPrice==""){
                  alert("请正确填写燃油税!");
                  return false;
              }   
              
              var platformId=$("#platformId").val();   
              if(platformId==""||platformId==0){
                 alert("请正确选择平台 ！");
                 return false;
              } 
              
             var companyId=$("#companyId").val(); 
             if(companyId==""||companyId==0){
                 alert("请正确选择公司 ！");
                 return false;
              } 
             var airOrderNo=$("input[name='airOrderNo']");   
             if(checkCount(airOrderNo,"请正确填写订单号 ！")==false){
                 return false;
              }     
              
             var tranType=$("#tranType").val(); 
             if(tranType==""||tranType==0){
                 alert("请正确选择订单类型 ！");
                 return false;
              }
            var ktype=$("#ktype").val(); 
             if(ktype==""||ktype==0){
                 alert("请正确选择类型. ！");
                 return false;
              }
              
             var subPnr=$("input[name='subPnr']");   
             if(checkCount(subPnr,"请正确填写预定PNR ！")==false){
                 return false;
              }
             var bigPnr=$("input[name='bigPnr']");   
             if(checkCount(bigPnr,"请正确填写大PNR ！")==false){
                 return false;
              } 
                        
              var handlingCharge=$("input[name='handlingCharge']").val();   
               if(!isNum(handlingCharge)||handlingCharge==""){
                  alert("请正确填写手续费!");
                  return false;
              }   
              
              
              var accountId=$("#accountId").val(); 
             if(accountId==""||accountId==0){
                 alert("请正确选择帐号. ！");
                 return false;
              }
             
              
               var stotalAmount=$("input[name='statement.totalAmount']").val();   
               if(!isNum(stotalAmount)||stotalAmount==""){
                  alert("请正确填写应收金额!");
                  return false;
              }   
              
              
              var sactualAmount=$("input[name='statement.actualAmount']").val();   
               if(!isNum(sactualAmount)||sactualAmount==""){
                  alert("请正确填写实收金额!");
                  return false;
              }   */
              
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
				function isNum(b){
				   		var re=/^([1-9][0-9]*|0)(\.[0-9]{0,2})?$/;
				   		return(re.test(b));
				}
		</script>
		
		<script type="text/javascript">
		$(function(){
		   loadDate("platformId","companyId","accountId");
		});
		function loadDate(platformId,companyId,accountId){
				 platComAccountStore.getPlatFormList(function(data){
			   		for(var i=0;i<data.length;i++)
			   		{	
			   			    option = new Option(data[i].name,data[i].id);
			   			    document.getElementById(platformId).options.add(option);
			   			
			   		}
			   		setTimeout(function(){checkPlatform(platformId,companyId,accountId);},100);
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
				}
				setTimeout(function(){checkCompany(platformId,companyId,accountId);},100);
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
