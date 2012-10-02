<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>

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
		<script src="../_js/popcalendar.js" type="text/javascript"></script>
		<script src="../_js/common.js" type="text/javascript"></script>
	   	<script type='text/javascript' src='<%=path %>/dwr/interface/platComAccountStore.js'></script>
	   	<script type='text/javascript' src='<%=path %>/dwr/interface/passengerBiz.js'></script>
	 	<script type='text/javascript' src='<%=path %>/dwr/engine.js'></script>
		<script type='text/javascript' src='<%=path %>/dwr/util.js'></script>
		<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
		<script src="../_js/calendar/WdatePicker.js" type="text/javascript"></script>
		<script type="text/javascript" src="../_js/tsms/loadAccount.js"></script>	
		<script type="text/javascript" src="../_js/base/FormUtil.js"></script>	
		
		<script type="text/javascript">
		$(function() {		
		  var today = new Date();
	      var timeNow= showLocale(today);
		  $('#entryOrderDateObj').val(timeNow);
		  $('#boardingTimes').val(timeNow);
		  
		});
		
		
		function showHideObj(){
			var returnReasonSelect=document.getElementById("returnReasonSelect");
			var selectedValue=document.getElementsByName("tranType")[0].value;

			if(returnReasonSelect!=null){	
				if(selectedValue=="1"){
					returnReasonSelect.style.display="none";
					showDrawPnr(0);
					showUmbuchenPnr(0);
				}
				
				if(selectedValue=="3"){
					returnReasonSelect.style.display="";
					showDrawPnr(1);
				}else{
					returnReasonSelect.style.display="none";
				}
				
				if(selectedValue=="4"){
					showDrawPnr(1);
				}
				
				if(selectedValue=="5"){
					showUmbuchenPnr(1);
				}		
				
			}
		}
		
		
		function showDrawPnr(flag){
			var drawPnr=document.getElementsByName("drawPnr");	
			var drawPnrHead=document.getElementById("drawPnrHead");				
		    var drawPnrContent=document.getElementById("drawPnrContent");
		    if(flag==1){
			    drawPnrHead.style.display="";
				drawPnrContent.style.display="";
				for(var i=0;i<drawPnr.length;i++){
                  if(drawPnr[i].value==""||drawPnr[i].value==null){
                     drawPnr[i].style.display="";
                  }
                }
            }else if(flag==0){
              drawPnrHead.style.display="none";
				drawPnrContent.style.display="none";
				for(var i=0;i<drawPnr.length;i++){
                  if(drawPnr[i].value==""||drawPnr[i].value==null){
                     drawPnr[i].style.display="none";
                  }
            	}         
		    } 
		}
		
		function showUmbuchenPnr(flag){
		   var umbuchenPnr=document.getElementsByName("umbuchenPnr");
		   
		   var umbuchenPnrHead=document.getElementById("umbuchenPnrHead");	
		   var umbuchenPnrContent=document.getElementById("umbuchenPnrContent");	
		   			
		   if(flag==1){
		   		umbuchenPnrHead.style.display="";
				umbuchenPnrContent.style.display="";
		   
            	for(var j=0;j<umbuchenPnr.length;j++){
                   if(umbuchenPnr[j].value==""||umbuchenPnr[j].value==null){
                     umbuchenPnr[j].style.display="";
                  }
            	}          
		   }else if(flag==0){
		   		umbuchenPnrHead.style.display="none";
				umbuchenPnrContent.style.display="none";
		   
            	for(var j=0;j<umbuchenPnr.length;j++){
                   if(umbuchenPnr[j].value==""||umbuchenPnr[j].value==null){
                     umbuchenPnr[j].style.display="none";
                  }
            	}   
		   }			
			
		}
		</script>
	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
			<div>
				<html:form action="airticket/airticketOrder.do?thisAction=addOrderByHand" method="post" >
					航班信息：
								<input name="label" type="button" class="button1" value="添 加"
									onclick="addFlight()" >
							<table cellpadding="0" cellspacing="0" border="0" id="flightTable"
									class="dataList">
									<tr>		
									  <th><div>出发地 </div></th>
									  <th><div>目的地 </div></th>									  
									  <th><div>航班号 </div></th>
									  <th><div>舱位 </div></th>
									  <th><div>折扣 </div></th>
									  <th><div>出发日期 </div></th>
									  <th><div>操作 </div></th>
							       </tr>
							      <tr>		
									    <td><input type="text" name="startPoints" class="colorblue2 p_5"	style="width:65px;"/>  </td>
								        <td><input type="text" name="endPoints" class="colorblue2 p_5"	style="width:65px;"/></td>
								        <td><input type="text" name="flightCodes" class="colorblue2 p_5"	style="width:100px;"/></td>
								        <td><input type="text" name="flightClasss" class="colorblue2 p_5"	style="width:50px;"/></td>
								        <td><input type="text" name="discounts" class="colorblue2 p_5"	style="width:50px;"/></td> 
								        <td><input type="text" id="boardingTimes" name="boardingTimes" class="colorblue2 p_5"	style="width:150px;" onfocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" readonly="true" /></td>
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
									  <th><div>票号 </div></th>
									   <th style="display: none"><div>证件号 </div></th>
									   <th><div>类型 </div></th>									 
									  <th><div>操作 </div></th>								
							       </tr>
							        <tr id="s001">		
									    <td><input type="text" name="passNames" class="colorblue2 p_5"	style="width:80px;"/></td>	
								        <td><input type="text" name="passTicketNumbers" class="colorblue2 p_5"	style="width:120px;"/> </td>
								         <td style="display: none"><input type="text" name="passCardNos" class="colorblue2 p_5"	style="width:200px;"/> </td>
								         <td> 
								         <select name="passTypes">
								           <option value="1">成人</option>
								           <option value="2">儿童</option>
								           <option value="3">婴儿</option>
								         </select>
								        </td>
								         <td><a href="#"  onclick='delRow(this);'>删除</a></td>
							       </tr>
							</table>	
						<div id="opid2">	
							<br/>订单信息：<a href="#">《手工录单说明》</a>
								<input style="display:none" name="label" type="button" class="button1" value="添 加"	onclick="addop()" >
							<table cellpadding="0" cellspacing="0" border="0" id="table1" class="dataList">												
								<tr><td>类型<select name="ticketType" id="ticketType" onchange="showPNR()" ><option value="1">B2B</option><option value="3">B2C</option></select></td>							
								
								<td><input type="hidden" name="businessType" value="1" />
								<select name="tranType" id="tranType" onchange="showHideObj()">
										<option value="1">销售</option>
										<!-- 
										<option value="3">退票</option>
										<option value="4">废票</option>
										<option value="5">改签</option>
										 -->
									</select>
								</td>
								<td style="display: none" id="returnReasonSelect">原因<select name="returnReason">
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
								<td>预定PNR<html:text property="subPnr" name="airticketOrder" styleId="subPnr1" styleClass="colorblue2 p_5"	style="width:100px;" /></td>							
								<td>票面价<html:text property="ticketPrice" name="airticketOrder" styleId="ticketPrice1" styleClass="colorblue2 p_5"	style="width:80px;" /></td>
								<td>机建税<html:text property="airportPrice" name="airticketOrder" styleId="airportPrice1" styleClass="colorblue2 p_5"	style="width:50px;" /></td>
								<td>燃油税<html:text property="fuelPrice" name="airticketOrder" styleId="fuelPrice1" styleClass="colorblue2 p_5"	style="width:50px;" /></td>
								
							</tr>
								<th>平台</th><th>公司	</th><th>帐号</th>							
								<th>订单号</th><th>政策</th><th>金额</th><th>大PNR</th><th style="display: none">手续费</th>
								<th id="drawPnrHead" style="display: none">出票PNR</th><th id="umbuchenPnrHead" style="display: none">改签PNR</th><th>录单时间</th>	
							<tr>
								<td><select name="platformId" id="platformId" onchange="loadCompanyList('platformId','companyId','accountId')" style="width:140px"></select></td>
								<td><select name="companyId" id="companyId" onchange="loadAccount('platformId','companyId','accountId')" style="width:140px"><option value="">请选择</option></select></td>
						        <td><select name="accountId" id="accountId" style="width:140px" class="text ui-widget-content ui-corner-all"><option value="">请选择</option>	</select></td>
						      	<td><html:text property="airOrderNo" name="airticketOrder" styleClass="colorblue2 p_5"	style="width:180px;" /></td>
								<td><html:text property="rebate" name="airticketOrder" styleId="rebate1" styleClass="colorblue2 p_5"	style="width:50px;" /></td>								
								<td><input type="text" name="totalAmount" value="0" class="colorblue2 p_5"	style="width:50px;"/> </td>						     
								<td><html:text property="bigPnr" name="airticketOrder" styleId="bigPnr1" styleClass="colorblue2 p_5"	style="width:80px;" /></td>
								<td id="handlingChargeContent" style="display:none"><html:text property="handlingCharge" name="airticketOrder" styleId="handlingCharge1" value="0" styleClass="colorblue2 p_5" style="width:50px;" /></td>
								
								<td id="drawPnrContent" style="display:none"><html:text property="drawPnr" name="airticketOrder" styleId="drawPnr1" styleClass="colorblue2 p_5"	style="width:80px;" /></td>										
								<td id="umbuchenPnrContent" style="display:none"><html:text property="umbuchenPnr" name="airticketOrder" styleId="umbuchenPnr1"  styleClass="colorblue2 p_5"	style="width:80px;" /></td>	
																	
								<td><input type="text" id="entryOrderDateObj" name="entryOrderDate" onfocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" class="colorblue2 p_5"	style="width:120px;"/> </td>						       														
								
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
									    "<td><input type='text' name='startPoints' class='colorblue2 p_5' style='width:65px;'/></td>"
								        +"<td><input type='text' name='endPoints' class='colorblue2 p_5' style='width:65px;' /></td>"
								        +"<td><input type='text' name='flightCodes' class='colorblue2 p_5' style='width:100px;'/></td>"
								        +"<td><input type='text' name='flightClasss' class='colorblue2 p_5' style='width:50px;'/></td>"
								        +"<td><input type='text' name='discounts' class='colorblue2 p_5' style='width:50px;'/></td>" 
								        +"<td><input type='text' id='boardingTimes' name='boardingTimes' class='colorblue2 p_5'  style='width:150px;' onfocus=\"WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})\" readonly='true' /></td>"								        
								        +"<td><a href='#'   onclick='delRow(this);'>删除 </a></td>"
							       +"</tr>");
		       }
		       
		     //添加乘客
		    function addPassenger(){ 		    
		        $("#passengerTable").append("<tr>"+		
									   "<td><input type='text' name='passNames' class='colorblue2 p_5' style='width:80px;'/></td>"								      
								       +" <td><input type='text' name='passTicketNumbers' class='colorblue2 p_5' style='width:120px;'/> </td>"
								      +" <td style='display:none'><input type='text' name='passCardNos' class='colorblue2 p_5' style='width:200px;'/> </td>"
								        +"<td><select name='passTypes'>"
								       +"<option value='1'>成人</option>"
								       +"<option value='2'>儿童</option>"
								       +"<option value='3'>婴儿</option>"
								       +"</select></td>"
								       +" <td><a href='#'  onclick='delRow(this);'>删除</a></td>"   
							       +"</tr>");
			//$("#s001").clone().prependTo("#passengerTableBody"); 				       
		    }
		    
		    //添加订单
		    var  count=1;
		   function addop(){ 		      			
		      			count++;								
					 $("#opId").append("<div><br/>主订单信息：<a href='#'   onclick='delDiv(this);'>删除 </a></td><table cellpadding='0' cellspacing='0' border='0' id='table1' class='dataList'>"
									    +"<tr> "
								        +"<td> 机票类型<select name='ticketType' id='ticketType'>"		
										+"<option value='1'>普通</option>"	
										+"<option value='3'>B2C</option>"							
									    +"</select></td>"
									     +"<td><div>出票PNR  <input type='text' name='drawPnr'  Class='colorblue2 p_5'	style='width:50px;' id='drawPnr"+count+"' /> </div></td>"
									    +" <td><div>预定PNR	<input type='text' name='subPnr'   Class='colorblue2 p_5'	style='width:50px;' id='subPnr"+count+"' /> </div></td>	"	
										+"<td><div>大PNR	   <input type='text' name='bigPnr' Class='colorblue2 p_5'	style='width:50px;' id='bigPnr"+count+"' /> </div></td>	"
									    +" <td><div>票面价	<input type='text' value='0' name='ticketPrice' Class='colorblue2 p_5'	style='width:50px;' id='ticketPrice"+count+"' /> </div></td>"
									    +" <td><div>机建税	<input type='text' value='0' name='airportPrice'  Class='colorblue2 p_5'	style='width:50px;' id='airportPrice"+count+"' /> </div></td>"
									    +" <td><div>燃油税	<input type='text' value='0'  name='fuelPrice' Class='colorblue2 p_5'	style='width:50px;' id='fuelPrice"+count+"'/> </div></td>"
									    +" <td><div>手续费	<input type='text' value='0'  name='handlingCharge' Class='colorblue2 p_5'	style='width:50px;' id='handlingCharge"+count+"'/> </div></td>"
									    +" <td><div>政策	 <input type='text' value='0'  name='rebate' Class='colorblue2 p_5'	style='width:50px;' id='rebate"+count+"' /> </div></td></tr>"
									    +"<tr>"
										+"<td><div>订单号	<input type='text'  name='airOrderNo' Class='colorblue2 p_5'	style='width:120px;' /> </div>	</td>"
										+"<td>订单类型 "
										+"<select name='tranType' id='tranType"+count+"' onchange=checkType('tranType"+count+"','ktype"+count+"')>"
											+"<option value='2'>卖出机票</option>"
											+"<option value='1'>买入机票</option>"
											+"<option value='3'>退票</option>"
											+"<option value='4'>废票</option>"
											+"<option value='5'>改签</option>"
										+"</select>"
										+"</td>"
										+"<td><div>类型"
									+"<select id='ktype"+count+"' name='status'>"
									    	+"<option value='1'>X-新订单等待支付</option>"
											+"<option value='2'>S-申请成功，等待支付</option>"
											+"<option value='5'>Y-已经出票，交易结束</option>"
											+"<option value='4'>Q-取消出票，等待退款</option>"
											+"<option value='6'>Y-已退款，交易结束</option>"
											+"<option value='3'>Z-支付成功，等待出票</option>"
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
									 +"   <td></td>"
									 +" <td></td><td></td><td></td></tr>  "
							+" <tr>"
					        +"<td>类别"	
				            +"<select name='statement_type' id='sType"+count+"' onchange=checksType('platformId"+count+"','companyId"+count+"','accountId"+count+"','sType"+count+"')>"
				            +"<option value='1'>卖出</option>"
				            +"<option value='2'>买入</option>"
				            +"</select>"
				            +" </td>"
				            +"<td><div>平台<select name='platformId' id='platformId"+count+"' onclick=loadCompanyList('platformId"+count+"','companyId"+count+"','accountId"+count+"')></select> </div></td>"	
							+"<td>公司	<select name='companyId'  id='companyId"+count+"'  onclick=loadAccount('platformId"+count+"','companyId"+count+"','accountId"+count+"') >"
							+"<option value=''>请选择</option></select></td>"
						    +" <td> 帐号"
						   +"<select name='accountId' id='accountId"+count+"' class='text ui-widget-content ui-corner-all'>		"
						+"		<option value=''>请选择</option>								"
							+"</select>"
						   +"     </td>"
						   +"     <td>应收<input type='text' name='statement_totalAmount' value='0' class='colorblue2 p_5'	style='width:50px;'/> </td>"
						     +"   <td>实收<input type='text' name='statement_actualAmount' value='0' class='colorblue2 p_5'	style='width:50px;'/> </td>"
						      +" <td >交易时间<input type='text' name='statementtDate' onfocus=\"WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})\" + class='colorblue2 p_5'	style='width:120px;'/> </td>"
							 +"<td></td><td></td></tr>"
							+"</table></div>");
		      			 // loadPlatList("platformId"+count,"companyId"+count,"accountId"+count);	
		      			  loadPlatListByType("platformId"+count,"companyId"+count,"accountId"+count,'1');	
		      			 var aoCount=1;
		      			/* if(count>1){
		      			   var aoCount=count-1;  
		      			   }*/
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
		    //删除行
			function delRow(_this){
				//alert($(_this).get(0));
				$(_this).parent().parent().remove();
				
			}
			
			 //删除div
			function delDiv(_this){
				
				$(_this).parent().remove();
				count--;
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
							+"<option value='3'>Z-支付成功，等待出票</option>");
			    
			   }else{
			   
			      //  $("#ktype").append("<option value='0'>--请选择--</option>");
		
			   }
			
			}	
         //类型
         function checksType(platformId,companyId,accountId,sType){
         
           /*   var tranTypetext= $("#tranType").find("option:selected").text();
			   if(tranTypetext=='正常'){
			    var sType=$("#sType").val();
			    $("#tranType").find("option:selected").text("正常").val(sType);
			   }*/
			//  alert($("#tranType").val());
			   
			  
			  var sType= $("#"+sType).val();
			  //alert(sType);
			 if(sType=='1'){
			    loadPlatListByType(platformId,companyId,accountId,'1');
			  }else if(sType=='2'){
			    loadPlatListByType(platformId,companyId,accountId,'2');
			  }
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
              if(checkNum(discounts,"请正确填写折扣 ！")==false){
                 return false;
              } 
              
              var passNames=$("input[name='passNames']");   
              if(checkCount(passNames,"请正确填写乘客姓名  ！")==false){
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
               if(checkCount(accountId,"请正确选择账号 ！")==false){
                 return false;
              }     
            
       /*       var passTicketNumbers=$("input[name='passTicketNumbers']"); 
              if(checkCount(passTicketNumbers,"请正确填写证件号 ！")==false){
                 return false;
              } 
              
              var passAirorderIds=$("input[name='passAirorderIds']");   
              if(checkCount(passAirorderIds,"请正确填写票号 ！")==false){
                 return false;
              } 
              
            var drawPnr=$("input[name='drawPnr']");   
              if(checkCount(drawPnr,"请正确填写出票PNR ！")==false){
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
            //  var handlingCharge=$("input[name='handlingCharge']");   
               //if(checkNum(handlingCharge,"请正确填写手续费!")==false){
                //  return false;
            //  }   
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
              
              trim(document.forms[0]);
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
		   //loadPlatList('platformId','companyId','accountId');
		     loadPlatListByType('platformId','companyId','accountId','1');
		   
		});

		</script>	
	</body>
</html>
