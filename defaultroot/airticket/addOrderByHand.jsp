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
				<html:form action="airticket/airticketOrder.do?thisAction=addOrderByHand" method="post">
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
								        <td><input type="text" id="boardingTimes" name="boardingTimes" class="colorblue2 p_5"	style="width:150px;" onfocus="WdatePicker({startDate:'%y-%M-%D 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" readonly="true" /></td>
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
							<br/>订单信息：
							<table cellpadding="0" cellspacing="0" border="0" id="table1" class="dataList">												
								<tr><td>类型<select name="ticketType" id="ticketType" onchange="showPNR()" ><option value="1">B2B</option><option value="3">B2C</option></select></td>							
								
								<td><input type="hidden" name="businessType" value="1" />
								<select name="tranType" id="tranType" onchange="showHideObj()">
										<option value="1">销售</option>
									</select>
								</td>
								<td style="display: none" id="returnReasonSelect">原因<select name="returnReason">
									    <option value="0" selected="selected">--请选择--</option>
									    	<option value="18">客规</option>
											<option value="1">取消</option>
											<option value="2">航班延误</option>
											<option value="3">重复付款</option>
											<option value="4">申请病退</option>
											<option value="12">多收票款</option>
											<option value="13">只退税</option>
											<option value="14">升舱换开</option>											
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
																	
								<td><input type="text" id="entryOrderDateObj" name="entryOrderDate" onfocus="WdatePicker({startDate:'%y-%M-%D 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" class="colorblue2 p_5"	style="width:120px;"/> </td>						       														
								
							 </tr>
							 <tr>
							 	<td colspan="8" align="right">
							 		<input name="label" type="button" class="button1" value="保 存"
									id="submitButtonHand"	onclick="addOrder()" >            
							 	</td>
							 </tr>
							</table>
							</div> <div id="opId"></div>
							              
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
								        +"<td><input type='text' id='boardingTimes' name='boardingTimes' class='colorblue2 p_5'  style='width:150px;' onfocus=\"WdatePicker({startDate:'%y-%M-%D 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})\" readonly='true' /></td>"								        
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
              
              if(setSubmitButtonDisable('submitButtonHand')){
	              trim(document.forms[0]);
	              document.forms[0].submit();   
              }else{
             	alert("页面错误，请联系管理员");
             	return false;
             }
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

		$(function(){
		     loadPlatListByType('platformId','companyId','accountId','1');		   
		});
		</script>	
	</body>
</html>
