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
		<script type="text/javascript" src="../_js/base/FormUtil.js"></script>
		<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" src="../_js/tsms/loadAccount.js"></script>	
		<script type="text/javascript" src="../_js/base/FormUtil.js"></script>	
		<script src="../_js/calendar/WdatePicker.js" type="text/javascript"></script>
		<script type="text/javascript">
		function synTicketPrice(priceName){
			var priceNameObj=document.getElementsByName(priceName);	
			//alert(priceName+"0");
			var priceId=document.getElementById(priceName+"0");
			//alert(priceId.value);
			for(var i=0;i<priceNameObj.length;i++){
                if(priceNameObj[i]!=null){
                    priceNameObj[i].value=priceId.value;
                }
             }			
		}
		</script>
		
	</head>
	<body>		
       <c:import url="../_jsp/mainTitle.jsp?title1=票务管理&title2=编辑订单" charEncoding="UTF-8" />								
				<html:form action="airticket/airticketOrder.do?thisAction=updateOrder" method="post" >		
				<input type="hidden" name="tranType" value="<c:out value='${airticketOrder.tranType}'/>"/>
				航班信息： <input name="label" type="button" class="button1" value="添 加"onclick="addFlight()" >
				
					<table cellpadding="0" cellspacing="0" border="0" id="flightTable" class="dataList">
									<tr>		
									  <th><div style='width:50px;'/>出发地 </div></th>
									  <th><div style='width:50px;'/>目的地 </div></th>									  
									  <th><div style='width:70px;'/>航班号 </div></th>
									  <th><div style='width:50px;'/>舱位 </div></th>
									  <th><div style='width:50px;'/>折扣 </div></th>
									  <th><div style='width:120px;'/>出发日期 </div></th>
									  <th><div style='width:50px;'/>操作 </div></th>
							       </tr>
						
							       <c:forEach var="flight" items="${airticketOrder.flights}">
							      <tr>	
							            <input type="hidden" name="flightIds" value="<c:out value='${flight.id}'/>"/>
									    <td><input type="text" name="startPoints"  value="<c:out value='${flight.startPoint}'/>" class="colorblue2 p_5"	style="width:50px;"/>  </td>
								        <td><input type="text" name="endPoints"  value="<c:out value='${flight.endPoint}'/>" class="colorblue2 p_5"	style="width:50px;" /></td>
								       <td><input type="text" name="flightCodes"  value="<c:out value='${flight.flightCode}'/>" class="colorblue2 p_5"	style="width:70px;" /></td>
								        <td><input type="text" name="flightClasss" value="<c:out value='${flight.flightClass}'/>" class="colorblue2 p_5"	style="width:50px;"  /></td>
								         <c:if test="${!empty flight.discount}">
								        <td><input type="text" name="discounts" value="<c:out value='${flight.discount}'/>"  class="colorblue2 p_5"	style="width:50px;"/></td> 
								        </c:if>
								         <c:if test="${empty flight.discount}">
								        <td><input type="text" name="discounts" value="0"  class="colorblue2 p_5"	style="width:50px;"/></td> 
								        </c:if>
								         <td><input type="text" name="boardingTimes" value="<c:out value='${flight.boardingTime}'/>" class="colorblue2 p_5"	style="width:120px;" onfocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" /></td>
								         <td><a href="#"  onclick='delRow(this);'>删除</a></td>
							       </tr>
							      </c:forEach>
		
							</table>
							乘客信息：<input name="label" type="button" class="button1" value="添 加"
									onclick="addPassenger()" >
							<table cellpadding="0" cellspacing="0" border="0" id="passengerTable"
									class="dataList">
									<tr>		
									  <th><div>姓名 </div></th>									 
									  <th><div>票号 </div></th>
									  <th style="display: none"><div>证件号</div></th>
									   <th><div>类型 </div></th>
									  <th><div>操作 </div></th>								
							       </tr>
							      <c:forEach var="passenger" items="${airticketOrder.passengers}">
							        <tr id="s001"><input type="hidden" name="passids" value="<c:out value='${passenger.id}'/>"/>		
									    <td><input type="text" name="passNames" value="<c:out value='${passenger.name}'/>" class="colorblue2 p_5"	style="width:100px;"/></td>
								        <td><input type="text" name="passTicketNumbers" value="<c:out value='${passenger.ticketNumber}'/>" class="colorblue2 p_5"	style="width:160px;"/> </td>
								        <td style="display: none"><input type="text" name="passCardNos"  value="<c:out value='${passenger.cardno}'/>"  class="colorblue2 p_5"	style="width:160px;"/> </td>
								          <td><select name="passTypes">
								           <option value="1">成人</option>
								           <option value="2">儿童</option>
								           <option value="3">婴儿</option>
								         </select>
								        </td>
								        <td><a href="#"  onclick='delRow(this);'>删除</a></td>
							       </tr>
							      </c:forEach> 
							</table>	
							
						<div id="opid2">	
						<br/><input style="display: none" name="label" type="button" class="button1" value="添 加"	onclick="addop()" >
						</div>
							<td>票面价<c:if test="${!empty airticketOrderList.list[0].ticketPrice}"><html:text property="ticketPrice" name="airticketOrder"  styleId="ticketPrice0" value="${airticketOrderList.list[0].ticketPrice}"  onkeyup="synTicketPrice('ticketPrice')" onkeydown="synTicketPrice('ticketPrice')" styleClass="colorblue2 p_5"	style="width:50px;" /></c:if>
									<c:if test="${empty airticketOrderList.list[0].ticketPrice}">票面价<html:text property="ticketPrice" name="airticketOrder"  styleId="ticketPrice0" value="0" onkeyup="synTicketPrice('ticketPrice')" onkeydown="synTicketPrice('ticketPrice')"  styleClass="colorblue2 p_5"	style="width:50px;" /></c:if>
							</td>
							<td>机建税<c:if test="${!empty airticketOrderList.list[0].airportPrice}"><html:text property="airportPrice" name="airticketOrder" styleId="airportPrice0"  value="${airticketOrderList.list[0].airportPrice}" onkeyup="synTicketPrice('airportPrice')" onkeydown="synTicketPrice('airportPrice')" styleClass="colorblue2 p_5"	style="width:50px;" /></c:if>
										<c:if test="${empty airticketOrderList.list[0].airportPrice}"><html:text property="airportPrice" name="airticketOrder" styleId="airportPrice0" value="0" onkeyup="synTicketPrice('airportPrice')" onkeydown="synTicketPrice('airportPrice')"  styleClass="colorblue2 p_5"	style="width:50px;" /></c:if>
							</td>
							<td>燃油税<c:if test="${!empty airticketOrderList.list[0].fuelPrice}"><html:text property="fuelPrice" name="airticketOrder"  styleId="fuelPrice0" value="${airticketOrderList.list[0].fuelPrice}" onkeyup="synTicketPrice('fuelPrice')" onkeydown="synTicketPrice('fuelPrice')"  styleClass="colorblue2 p_5"	style="width:50px;" /></c:if>
										<c:if test="${empty airticketOrderList.list[0].fuelPrice}">燃油税<html:text property="fuelPrice" name="airticketOrder" styleId="fuelPrice0" value="0"  onkeyup="synTicketPrice('fuelPrice')" onkeydown="synTicketPrice('fuelPrice')" styleClass="colorblue2 p_5"	style="width:50px;" /></c:if>
							</td>
							<hr/>
						<table cellpadding="0" cellspacing="0" border="0" class="dataList2" >							
							<th>类型</th>
							<c:if test="${airticketOrder.tranType==3}">
							<th>退票原因</th>								
							</c:if>
							<th>预定PNR</th>
							<th>出票PNR</th>
							<th>大PNR</th>
							<c:if test="${airticketOrder.tranType==5}">
							<th>改签PNR</th>
							</c:if>
							<th>订单号</th>
							<th>政策</th>
							<th>金额</th>
							<c:if test="${airticketOrder.tranType==5}">
							<th>手续费</th>		
							</c:if>	
							<th>平台</th>
							<th>公司</th>
							<th>帐号</th>
							<th>录单时间</th>			
							<th>状态</th>				
						<c:forEach var="airticketOrder" items="${airticketOrderList.list}" varStatus="status">
							<tr>
								<td><input type="hidden" name="airticketOrderIds" value="<c:out value='${airticketOrder.id}'/>"/><c:out value="${airticketOrder.businessTypeText}"/></td>				 
								<c:if test="${airticketOrder.tranType==3}">
									<td><select name="returnReason" value="<c:out value='${airticketOrder.returnReason}'/>"><option value="0" selected="selected">--请选择--</option><option value="1">取消</option><option value="2">航班延误</option><option value="3">重复付款</option>	<option value="4">申请病退</option><option value="12">多收票款</option><option value="13">只退税</option><option value="14">升舱换开</option><option value="18">客规</option></select>	</td>	
								</c:if>
								<td><html:text property="subPnr" name="airticketOrder" value="${airticketOrder.subPnr}" styleId="subPnr<c:out value='${status.count}'/>" styleClass="colorblue2 p_5"	style="width:70px;" /></td>		
								<td><html:text property="drawPnr" name="airticketOrder" value="${airticketOrder.drawPnr}" styleId="drawPnr<c:out value='${status.count}'/>" styleClass="colorblue2 p_5"	style="width:70px;" /></td>
								<td><html:text property="bigPnr" name="airticketOrder" value="${airticketOrder.bigPnr}" styleId="bigPnr<c:out value='${status.count}'/>" styleClass="colorblue2 p_5"	style="width:70px;" /></td>										
								<c:if test="${airticketOrder.tranType==5}">
									<td><html:text property="umbuchenPnr" name="airticketOrder" value="${airticketOrder.umbuchenPnr}" styleId="umbuchenPnr<c:out value='${status.count}'/>" styleClass="colorblue2 p_5"	style="width:70px;" /></td>										
								</c:if>
								<td><html:text property="airOrderNo" name="airticketOrder" value="${airticketOrder.airOrderNo}"  styleId="airOrderNo<c:out value='${status.count}'/>" styleClass="colorblue2 p_5"	style="width:160px;" /></td>
								<td><html:text property="rebate" name="airticketOrder"  value="${airticketOrder.rebate}"  styleId="rebate<c:out value='${status.count}'/>" styleClass="colorblue2 p_5"	style="width:50px;" /></td>								
								<td><html:text property="totalAmount" value="${airticketOrder.totalAmount}" styleId="totalAmount<c:out value='${status.count}'/>" styleClass="colorblue2 p_5" style="width:80px"/></td>						     
								<c:if test="${airticketOrder.ticketType==3}">
								<td><html:text property="handlingCharge"  value="${airticketOrder.handlingCharge}" name="airticketOrder" styleId="handlingCharge<c:out value='${status.count}'/>" styleClass="colorblue2 p_5"	style="width:50px;" /></td>
								</c:if>										
								<td><input type="hidden" name="tmpPlatformId" value="<c:out value="${airticketOrder.platform.id}"/>"/>	
									<select name="platformId" id="platformId<c:out value='${status.count}'/>" onchange="loadCompanyList('platformId','companyId','accountId')" style="width: 120px"></select></div>
								</td>
								<td><input type="hidden" name="tmpCompanyId" value="<c:out value="${airticketOrder.company.id}"/>"/>									
									<select name="companyId" id="companyId<c:out value='${status.count}'/>" onchange="loadAccount('platformId<c:out value='${status.count}'/>','companyId<c:out value='${status.count}'/>','accountId<c:out value='${status.count}'/>')"  style="width: 120px">		
								</td>
							    <td><input type="hidden" name="tmpAccountId" value="<c:out value="${airticketOrder.account.id}"/>"/>
						  			<select name="accountId" id="accountId<c:out value='${status.count}'/>" class="text ui-widget-content ui-corner-all"><option value="">请选择</option></select>
						  		</td>
						  		<td><input type="text" name="entryOrderDate" value="<c:out value='${airticketOrder.entryOrderDate}'/>" id="entryOrderDate<c:out value='${status.count}'/>" onfocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" class="colorblue2 p_5"	style="width:140px;"/> </td>
							    <td>
						  			<font style="color:red"><c:out value="${airticketOrder.statusText}"></c:out></font>
						  		 </td>						  			
						    </tr>
						    <input type="hidden" name="ticketPrice"  id="ticketPrice<c:out value='${status.count}'/>" value="<c:out value='${airticketOrder.ticketPrice}'/>" />
							<input type="hidden" name="airportPrice"  id="airportPrice<c:out value='${status.count}'/>"  value="<c:out value='${airticketOrder.airportPrice}'/>"  />
							<input type="hidden" name="fuelPrice"   id="fuelPrice<c:out value='${status.count}'/>" value="<c:out value='${airticketOrder.fuelPrice}'/>"  /> 		
								
					</c:forEach>
					</table>			
			<input type="hidden" value="<c:out value="${airticketOrderList.listSize}"/>" id="aoCount"/>	
			<input name="label" type="button" class="button1" value="保 存"onclick="addOrder()" >                         
				</html:form>
			</div>
		</div>
		<script type="text/javascript">		
		    function addFlight(){		  
		          //添加航班
		       $("#flightTable").append("<tr><input type='hidden' name='flightIds' value='0'/>"+		
									    "<td><input type='text' name='startPoints' class='colorblue2 p_5' style='width:50px;'/></td>"
								        +"<td><input type='text' name='endPoints' class='colorblue2 p_5' style='width:50px;' /></td>"
								         +"<td><input type='text' name='flightCodes' class='colorblue2 p_5' style='width:50px;'/></td>"
								        +"<td><input type='text' name='flightClasss' class='colorblue2 p_5' style='width:50px;'/></td>"
								        +"<td><input type='text' name='discounts' class='colorblue2 p_5' style='width:50px;'/></td>" 
								        +"<td><input type='text' name='boardingTimes' class='colorblue2 p_5' style='width:120px;' onfocus=\"WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})\" +/></td>"
								        +"<td><a href='#'   onclick='delRow(this);'>删除 </a></td>"
							       +"</tr>");
		       }
		       
		     //添加乘客
		    function addPassenger(){ 		    
		        $("#passengerTable").append("<tr><input type='hidden' name='passids' value='0'/>"+		
									   "<td><input type='text' name='passNames' class='colorblue2 p_5' style='width:100px;'/></td>"								    
								       +" <td><input type='text' name='passTicketNumbers' class='colorblue2 p_5' style='width:160px;'/> </td>"
								       +" <td style='display:none'><input type='text' name='passCardNos' class='colorblue2 p_5' style='width:120px;'/> </td>"
								       +"<td><select name='passTypes'>"
								       +"<option value='1'>成人</option>"
								       +"<option value='2'>儿童</option>"
								       +"<option value='3'>婴儿</option>"
								       +"</select></td>"
								       +" <td><a href='#'  onclick='delRow(this);'>删除</a></td>"   
							       +"</tr>");
			//$("#s001").clone().prependTo("#passengerTableBody"); 				       
		    }
		    
		   		   
		   	var count= $("#aoCount").val()+1;	   
		    
		 
		    //删除行
			function delRow(_this){
				//alert($(_this).get(0));
				$(_this).parent().parent().remove();
			}
			
			 //删除div
			function delDiv(_this){
				
				$(_this).parent().remove();
			}
			

		</script>
		
<script type="text/javascript">
	$(function(){		
		 var aoCount= $("#aoCount").val();		    
		 var tmpPlatformId=$("input[name='tmpPlatformId']");  
		 var tmpCompanyId=$("input[name='tmpCompanyId']");  
		 var tmpAccountId=$("input[name='tmpAccountId']");   
		 
		 var tmpTicketType=$("input[name='tmpTicketType']");   
		 var tmpTranType=$("input[name='tmpTranType']");   
		 
		// alert("aoCount:"+aoCount);
		  for(var i=1;i<=aoCount;i++){		      
		       var j=i-1;		       
		           
		           //设置下拉框  平台初始值 默认选中
				  var tmpPlatformValue=tmpPlatformId[j];
				  var tmpCompanyValue=tmpCompanyId[j];  	
				  var tmpAccountValue=tmpAccountId[j]; 	 
				  		  
				 // alert("j:"+j);
				 // alert("tmpPlatformValue:"+tmpPlatformValue);
				  if(tmpPlatformValue!=null&&tmpPlatformValue!=""){					     
				        loadPlatListSelected('platformId'+i,'companyId'+i,'accountId'+i,tmpPlatformValue.value,tmpCompanyValue.value,tmpAccountValue.value);
				  }else{				     
				        loadPlatList('platformId'+i,'companyId'+i,'accountId'+i);
				  }	  	         
		   }
		   
		});		
		
		  
		  //点击交易平台名称
		function checkPlatform(platformId,companyId,accountId){			  
			var plId = document.getElementById(platformId).value;
				
			platComAccountStore.getPlatComAccountListByPlatformId(plId,function(data){
			     $("#"+companyId).empty();
				//option = new Option("请选择","");
			   	//document.getElementById(companyId).options.add(option);
				for(var i=0;i<data.length;i++){ 					
					 option2 = new Option(data[i].company.name,data[i].company.id);
			   	     document.getElementById(companyId).options.add(option2);
			   	     if(i==data.length-1){
			   	     checkCompany(platformId,companyId,accountId);
			   	     }
				}
			//	setTimeout(function(){checkCompany(platformId,companyId,accountId);},100);
			});
		}
		
		//点击公司名称		
		function checkCompany(platformId,companyId,accountId){			
			var comId =document.getElementById(companyId).value;
			var plaId = document.getElementById(platformId).value;
			
			platComAccountStore.getPlatComAccountListByCompanyId(comId,plaId,
			function(data1){	
				if(data1.length<1){
					  $("#"+accountId).empty();
					   option3 = new Option("请选择","");
			   	       document.getElementById(accountId).options.add(option3);
				}
			
				 $("#"+accountId).empty();
				 // option = new Option("请选择","");
			   	// document.getElementById(accountId).options.add(option);
				for(var i=0;i<data1.length;i++){
					if(data1[i].name != null || data1[i].name != ""){						
					   option3 = new Option(data1[i].account.name,data1[i].account.id);
			   	       document.getElementById(accountId).options.add(option3);
					}
				}
			});
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
            //  var handlingCharge=$("input[name='handlingCharge']");   
            //   if(checkNum(handlingCharge,"请正确填写手续费!")==false){
            //      return false;
            //  }   
              var rebate=$("input[name='rebate']");   
               if(checkNum(rebate,"请正确填写政策!")==false){
                  return false;
              }   
              
              var totalAmount=$("input[name='totalAmount']");   
               if(checkNum(totalAmount,"请正确填写订单金额!")==false){
                  return false;
              }            
              
              trim(document.forms[0]);
              document.forms[0].submit();   
         }
		
		function checkCount(arry,msg){
		
		if(arry && arry.length>0)
		{
		    for(var i=0;i<arry.length;i++){
                   if(arry[i].value==""||arry[i].value==null){
                     alert(msg);
                     return false;
                  }
            }
        }
        else
        {
           alert(msg);
          return false;        
        }    
		}
		
				//验证金额
		function checkNum(arry,msg){
		if(arry && arry.length>0)
		{
		    for(var i=0;i<arry.length;i++){
                   if(arry[i].value==""||arry[i].value==null){
                     alert(msg);
                     return false;
                  }else if(!isNum(arry[i].value)){
                     alert(msg);
                     return false;
                  }
            }
         }   else
        {
           alert(msg);
          return false;        
        }   
		}
		     //验证金额
	function isNum(b){
		var re=/^([1-9][0-9]*|0)(\.[0-9]{0,2})?$/;
		return(re.test(b));
	}
		
		 
		 
		</script>	
	</body>
</html>
