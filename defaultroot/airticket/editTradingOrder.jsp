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
       <c:import url="../_jsp/mainTitle.jsp?title1=票务管理&title2=编辑订单" charEncoding="UTF-8" />								
				<html:form action="airticket/airticketOrder.do?thisAction=editTradingOrder" method="post" >
				<input type="hidden" name="groupMarkNo" value="<c:out value='${airticketOrder.groupMarkNo}'/>"/>
				航班信息：  
				<input name="label" type="button" class="button1" value="添 加"onclick="addFlight()" >
					<table cellpadding="0" cellspacing="0" border="0" id="flightTable"class="dataList">
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
							<br/><P>乘客信息：
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
							        <tr id="s001"><input type="hidden" name="passids" value="<c:out value='${passenger.id}'/>"/>		
									    <td><input type="text" name="passNames" value="<c:out value='${passenger.name}'/>" class="colorblue2 p_5"	style="width:80px;"/></td>
								        <td><select name="passTypes">
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
						<br/><hr/><input style="display: none" name="label" type="button" class="button1" value="添 加"	onclick="addop()" >
						<c:forEach var="airticketOrder" items="${airticketOrderList.list}" varStatus="status">								
							<table cellpadding="0" cellspacing="0" border="0" id="table<c:out value='${status.count}'/>" >									
									<tr>
										<td><input type="hidden" name="airticketOrderIds" value="<c:out value='${airticketOrder.id}'/>"/><c:out value="${airticketOrder.ticketTypeText}"/>	<c:out value="${airticketOrder.businessTypeText}"/>	<c:out value="${airticketOrder.tranTypeText}"/>				</td>				 
										<td>原因<select name="returnReason" value="<c:out value='${airticketOrder.returnReason}'/>"><option value="0" selected="selected">--请选择--</option><option value="1">取消</option><option value="2">航班延误</option><option value="3">重复付款</option>	<option value="4">申请病退</option><option value="12">多收票款</option><option value="13">只退税</option><option value="14">升舱换开</option><option value="18">客规</option></select>	</td>	
										<td><div>订单号<html:text property="airOrderNo" name="airticketOrder" value="${airticketOrder.airOrderNo}"  styleId="airOrderNo<c:out value='${status.count}'/>" styleClass="colorblue2 p_5"	style="width:120px;" /> </div>	</td>
										<td><div>政策<html:text property="rebate" name="airticketOrder"  value="${airticketOrder.rebate}"  styleId="rebate<c:out value='${status.count}'/>" styleClass="colorblue2 p_5"	style="width:50px;" /> </div></td>								
										<td>金额<html:text property="totalAmount" value="${airticketOrder.totalAmount}" styleId="totalAmount<c:out value='${status.count}'/>" styleClass="colorblue2 p_5" style="width:50px"/></td>						     
										<td><div>手续费<html:text property="handlingCharge"  value="${airticketOrder.handlingCharge}" name="airticketOrder" styleId="handlingCharge<c:out value='${status.count}'/>" styleClass="colorblue2 p_5"	style="width:50px;" /> </div></td>
										<td>录单时间<input type="text" name="entryOrderDate" value="<c:out value='${airticketOrder.entryOrderDate}'/>" id="entryOrderDate<c:out value='${status.count}'/>" onfocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" class="colorblue2 p_5"	style="width:100px;"/> </td>
							       </tr>
									<tr>
								        <td><div>出票PNR<html:text property="drawPnr" name="airticketOrder" value="${airticketOrder.drawPnr}" styleId="drawPnr<c:out value='${status.count}'/>" styleClass="colorblue2 p_5"	style="width:50px;" /> </div></td>
										<td><div>预定PNR<html:text property="subPnr" name="airticketOrder" value="${airticketOrder.subPnr}" styleId="subPnr<c:out value='${status.count}'/>" styleClass="colorblue2 p_5"	style="width:50px;" /> </div></td>		
										<td><div>大PNR<html:text property="bigPnr" name="airticketOrder" value="${airticketOrder.bigPnr}" styleId="bigPnr<c:out value='${status.count}'/>" styleClass="colorblue2 p_5"	style="width:50px;" /> </div></td>										
										<td><div>改签PNR<html:text property="umbuchenPnr" name="airticketOrder" value="${airticketOrder.umbuchenPnr}" styleId="umbuchenPnr<c:out value='${status.count}'/>" styleClass="colorblue2 p_5"	style="width:50px;" /> </div></td>										
										<c:if test="${!empty airticketOrder.ticketPrice}"><td><div>票面价<html:text property="ticketPrice" name="airticketOrder"  styleId="ticketPrice${status.count}" value="${airticketOrder.ticketPrice}"  styleClass="colorblue2 p_5"	style="width:50px;" /></div></td></c:if>
										<c:if test="${empty airticketOrder.ticketPrice}"><td><div>票面价<html:text property="ticketPrice" name="airticketOrder"  styleId="ticketPrice${status.count}" value="0"  styleClass="colorblue2 p_5"	style="width:50px;" /></div></td></c:if>
									    <c:if test="${!empty airticketOrder.airportPrice}"><td><div>机建税<html:text property="airportPrice" name="airticketOrder" styleId="airportPrice${status.count}"  value="${airticketOrder.airportPrice}"  styleClass="colorblue2 p_5"	style="width:50px;" /> </div></td></c:if>
										<c:if test="${empty airticketOrder.airportPrice}"><td><div>机建税	<html:text property="airportPrice" name="airticketOrder" styleId="airportPrice${status.count}" value="0"  styleClass="colorblue2 p_5"	style="width:50px;" /> </div></td> </c:if>
										<c:if test="${!empty airticketOrder.fuelPrice}"><td><div>燃油税<html:text property="fuelPrice" name="airticketOrder"  styleId="fuelPrice${status.count}" value="${airticketOrder.fuelPrice}"  styleClass="colorblue2 p_5"	style="width:50px;" /> </div></td></c:if>
										<c:if test="${empty airticketOrder.fuelPrice}"><td><div>燃油税<html:text property="fuelPrice" name="airticketOrder" styleId="fuelPrice${status.count}" value="0"  styleClass="colorblue2 p_5"	style="width:50px;" /> </div></td></c:if>
									</tr>
								<tr>
									<td colspan="2"><div>平台<input type="hidden" name="tmpPlatformId" value="<c:out value="${airticketOrder.platform.id}"/>"/>	
									   	<select name="platformId" id="platformId<c:out value='${status.count}'/>" onchange="loadCompanyList('platformId','companyId','accountId')"></select></div>
									</td>
									<td colspan="2">公司<input type="hidden" name="tmpCompanyId" value="<c:out value="${airticketOrder.company.id}"/>"/>									
										<select name="companyId" id="companyId<c:out value='${status.count}'/>" onchange="loadAccount('platformId<c:out value='${status.count}'/>','companyId<c:out value='${status.count}'/>','accountId<c:out value='${status.count}'/>')" >		
									</td>
							        <td>帐号<input type="hidden" name="tmpAccountId" value="<c:out value="${airticketOrder.account.id}"/>"/>
						  				 <select name="accountId" id="accountId<c:out value='${status.count}'/>" class="text ui-widget-content ui-corner-all"><option value="">请选择</option></select>
						  			</td>
						  			<td>
						  				<font style="color:red"><c:out value="${airticketOrder.statusText}"></c:out></font>
						  			</td>
						       </tr>
					</table>	
					 <tr><hr/></tr>						
			</c:forEach>	
			<input type="hidden" value="<c:out value="${airticketOrderList.totalRowCount}"/>" id="aoCount"/>	
		</div><div id="opId"></div><input name="label" type="button" class="button1" value="保 存"onclick="addOrder()" >                         
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
		 
		 //alert("aoCount:"+aoCount);
		  for(var i=1;i<=aoCount;i++){		      
		       var j=i-1;		       
		           
		           //设置下拉框  平台初始值 默认选中
				  var tmpPlatformValue=tmpPlatformId[j];
				  var tmpCompanyValue=tmpCompanyId[j];  	
				  var tmpAccountValue=tmpAccountId[j]; 	 
				  		  
				 // alert("j:"+j);
				  if(tmpPlatformValue!=null&&tmpPlatformValue!=""){					     
				        loadPlatListSelected('platformId'+i,'companyId'+i,'accountId'+i,tmpPlatformValue.value,tmpCompanyValue.value,tmpAccountValue.value);
				  }else{				     
				        loadPlatList('platformId'+i,'companyId'+i,'accountId'+i);
				  }	
				  setTablesColor(j);	  	         
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
		 
		function setTablesColor(index){
		 	 var table = document.getElementById("table"+index);
		 	 if(table!=null){	
		 	 	//alert(index);
		 	 	//alert(index%2);
		 	 	if(index%2==0){	
		  		 	table.style.backgroundColor='#CCCCCC';		  		 	
		  		}
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
              
              var totalAmount=$("input[name='totalAmount']");   
               if(checkNum(totalAmount,"请正确填写订单金额!")==false){
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
	</body>
</html>
