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
		<script type='text/javascript' src='<%=path %>/dwr/interface/airticketOrderBiz.js'></script>
 		 <script type='text/javascript' src='<%=path %>/dwr/engine.js'></script>
		 <script type='text/javascript' src='<%=path %>/dwr/util.js'></script>	
		<link type="text/css" href="../_js/development-bundle/themes/base/ui.all.css" rel="stylesheet" />
		<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" src="../_js/development-bundle/ui/ui.core.js"></script>
		<script type="text/javascript" src="../_js/development-bundle/ui/ui.draggable.js"></script>
		<script type="text/javascript" src="../_js/development-bundle/ui/ui.dialog.js"></script>
		<script type="text/javascript" src="../_js/calendar/WdatePicker.js" ></script>
		<script type="text/javascript" src="../_js/popcalendar.js" ></script>	
		<script type="text/javascript" src="../_js/base/CalculateUtil.js"></script>
		<script type="text/javascript" src="../_js/tsms/loadTeamManage.js"></script>
		<script type="text/javascript" src="../_js/base/FormUtil.js"></script>	
		<script type="text/javascript" src="../_js/tsms/teamOrderOperate.js"></script>	
		<script type="text/javascript">
			$(document).ready(function(){
				platComAccountStore.getTempAgentListBytype(2,getData);
				function getData(data)
				{
					for(var i=0;i<data.length;i++)
					{
						//alert(data[i].name);						
						document.forms[0].agentId.options[i] = new Option(data[i].showName,data[i].id);
					}
					var agentIdValue='<c:out value="${airticketOrder.agent.id}"/>';
					//alert("value:"+agentIdValue);
				
					if(agentIdValue!=null&&agentIdValue!=""){
						var agentSelectObj=document.getElementById("agentId")
						var length=agentSelectObj.options.length;
						//alert("length:"+length);
						for(var i=0;i<length;i++){
							//alert("opt value:"+agentSelectObj.options[i].value);
							if(agentSelectObj.options[i]!=null){
								if(agentSelectObj.options[i].value==agentIdValue){
									agentSelectObj.options[i].selected=true;
								}
							}
						}					
					}
			  }			  
			  setTimeout("totalPersonCheck()",100);//			  	
			});	
		</script>
		<script type="text/javascript">		
			var num1=2;
			var num2=2;
			
			//键事件	(计算总数)		
			function totalPersonCheck()//团队总人数
			{
				var adultCount =document.forms[0].adultCount.value;
				var childCount =document.forms[0].childCount.value;
				var babyCount =document.forms[0].babyCount.value;
				var totlep =1*adultCount + 1*childCount + 1*babyCount;
				$("input[name='totalPerson']").val(totlep);
				
				ticketPriceCheck();//总票面价
				airportPriceCheck();//总机建税 
				fuelPriceCheck();// 总燃油税
			}

			//生成航程信息表单
			function addAirticketOrder2()
			{		
				var str="";
				str+="<tr id='tr1'>";
				str+="<td><input name=\"flightCodes"+"\" id=\"flightCodes"+"\"  class=\"colorblue2 p_5"+"\" style=\"width: 100px"+"\"></td>";
				str+="<td><input name=\"startPoints"+"\" id=\"startPoints"+"\"  class=\"colorblue2 p_5"+"\" style=\"width: 50px"+"\"> __ <input name=\"endPoints"+"\" id=\"endPoints"+"\" class=\"colorblue2 p_5"+"\" style=\"width: 50px"+"\"></td>";
				str+="<td><input name=\"boardingTimes"+"\" id=\"boardingTimes"+"\" class=\"colorblue2 p_5"+"\" style=\"width: 120px"+"\"  onclick=\"popUpCalendar(this, this);"+"\"  readonly=\"true"+"\"></td>";
				str+="<td><input name=\"flightClasss"+"\" id=\"flightClasss"+"\"  class=\"colorblue2 p_5"+"\" style=\"width: 50px"+"\"></td>";
				str+="<td><input name=\"discounts"+"\" id=\"discounts"+"\"  value=\""+0+"\" class=\"colorblue2 p_5"+"\" style=\"width: 50px"+"\"></td>";
				str+="<td><input name=\"ticketPrices"+"\" id=\"ticketPrices"+"\" value=\""+0+"\" onkeyup=\"ticketPriceCheck()"+"\" class=\"colorblue2 p_5"+"\" style=\"width: 60px"+"\"></td>";
				str+="<td><input name=\"adultAirportPrices"+"\" id=\"adultAirportPrices"+"\" value=\""+0+"\" onkeyup=\"airportPriceCheck()"+"\" class=\"colorblue2 p_5"+"\" style=\"width: 60px"+"\"></td>";
				str+="<td><input name=\"adultFuelPrices"+"\" id=\"adultFuelPrices"+"\" value=\""+0+"\" onkeyup=\"fuelPriceCheck()"+"\" class=\"colorblue2 p_5"+"\" style=\"width: 60px"+"\"></td>";
				str+="<td><input name=\"childAirportPrices"+"\" id=\"childAirportPrices"+"\" value=\""+0+"\" onkeyup=\"airportPriceCheck()"+"\" class=\"colorblue2 p_5"+"\" style=\"width: 60px"+"\"></td>";
				str+="<td><input name=\"childFuelPrices"+"\" id=\"childFuelPrices"+"\" value=\""+0+"\" onkeyup=\"fuelPriceCheck()"+"\" class=\"colorblue2 p_5"+"\" style=\"width: 60px"+"\"></td>";
				str+="<td><input name=\"babyAirportPrices"+"\" id=\"babyAirportPrices"+"\" value=\""+0+"\" onkeyup=\"airportPriceCheck()"+"\" class=\"colorblue2 p_5"+"\" style=\"width: 60px"+"\"></td>";
				str+="<td><input name=\"babyFuelPrices"+"\" id=\"babyFuelPrices"+"\" value=\""+0+"\" onkeyup=\"fuelPriceCheck()"+"\" class=\"colorblue2 p_5"+"\" style=\"width: 60px"+"\"></td>";				
				str+="<td><a href='#' onclick='del(this);'>[删除]</a></td>";	
				str+="</tr>"
				$("#table2").append(str);
				num2++;
			}
			//增加先机人
			function addPassenger()
			{		
				var str="";
				str+="<tr>";
				str+="<td>姓名：<input name=\"passengerNames"+"\" id=\"passengerNames"+"\"  class=\"colorblue2 p_5"+"\" style=\"width: 150px"+"\"></td>";
				str+="<td>&nbsp;&nbsp;&nbsp;&nbsp;证件号码：<input name=\"passengerCardnos"+"\" id=\"passengerCardnos"+"\"  class=\"colorblue2 p_5"+"\" style=\"width: 150px"+"\"></td>";
				str+="<td>&nbsp;&nbsp;&nbsp;&nbsp;票号：<input name=\"passengerTicketNumbers"+"\" id=\"passengerTicketNumbers"+"\"  class=\"colorblue2 p_5"+"\" style=\"width: 150px"+"\">";
				str+="<a href='#' onclick='del(this);'>[删除]</a></td>";	
				str+="</tr>"
				$("#passengerTable").append(str);
				num2++;
			}
			
			function displayPassenger()
			{		
			  $("#passengerDiv").slideToggle();//	

			}
			//添加订单号
			function addOrderNo()
			{		
				var str="";
				str+="<tr><td>订单号：</td>";
				str+="<td colspan=\"6\"><input name=\"airOrderNos"+"\" id=\"airOrderNos"+"\"  class=\"colorblue2 p_5"+"\" style=\"width: 150px"+"\">";
				str+="<a href='#' onclick='del(this);'>[删除]</a></td>";	
				str+="</tr>"
				$("#orderNoTable").append(str);
				num2++;
			}
			
			//删除行
			function del(_this)
			{
				$(_this).parent().parent().remove();
				num2--;
				ticketPriceCheck();//总票面价
				airportPriceCheck();//总机建税 
				fuelPriceCheck();// 总燃油税
			}
		 
			
			function ticketPriceCheck()//总票面价
			{
				var totalPerson =$("input[name='totalPerson']").val();//总人数据		
				var ticketPrices=0;				
				if(document.forms[0].flightCodes.length)
				{
				  for(i=0;i<document.forms[0].flightCodes.length;i++)
				  {				   
					var ticketPrice =document.forms[0].ticketPrices[i].value;											
					ticketPrices+= 1*ticketPrice;					
				  }
				}
				else
				{
			      var ticketPrice =document.forms[0].ticketPrices.value;	
				  ticketPrices+= 1*ticketPrice;	
				}
				
				ticketPrices=ticketPrices*(1*totalPerson)//总票面价*总人数					
				$("input[name='totalTicketPrice']").val(ticketPrices);
			}
			function airportPriceCheck()//总机建税 
			{
				var totalPerson =$("input[name='totalPerson']").val();//总人数据
				
				var adultCount =document.forms[0].adultCount.value;//成人总数
				var childCount =document.forms[0].childCount.value;//儿童总数
				var babyCount =document.forms[0].babyCount.value;//婴儿总数
				var aPrices=0;
				
				if(document.forms[0].flightCodes.length)
				{
				  for(i=0;i<document.forms[0].flightCodes.length;i++)
				  {				   
					var adultAirportPrice = document.forms[0].adultAirportPrices[i].value;	
				    var childAirportPrice = document.forms[0].childAirportPrices[i].value;	
				    var babyAirportPrice =document.forms[0].babyAirportPrices[i].value;					   
				    aPrices +=1*adultAirportPrice*(1*adultCount) + 1*childAirportPrice*(1*childCount) + 1*babyAirportPrice*(1*babyCount);//num2=2
				  }
				}
				else
				{
			      	var adultAirportPrice = document.forms[0].adultAirportPrices.value;	
				    var childAirportPrice = document.forms[0].childAirportPrices.value;	
				    var babyAirportPrice =document.forms[0].babyAirportPrices.value;
				    aPrices +=1*adultAirportPrice*(1*adultCount) + 1*childAirportPrice*(1*childCount) + 1*babyAirportPrice*(1*babyCount);//num2=2
				} 
				
				totleAprice=aPrices;
				$("input[name='totalAirportPrice']").val(totleAprice);
			}
			function fuelPriceCheck()// 总燃油税
			{
				var adultCount =document.forms[0].adultCount.value;//成人总数
				var childCount =document.forms[0].childCount.value;//儿童总数
				var babyCount =document.forms[0].babyCount.value;//婴儿总数				
				var totalPerson =$("input[name='totalPerson']").val();//总人数据
				
				var fPrices=0;
				if(document.forms[0].flightCodes.length)
				{
				  for(i=0;i<document.forms[0].flightCodes.length;i++)
				  {				   
					var adultFuelPrice =document.forms[0].adultFuelPrices[i].value;	
				    var childFuelPrice =document.forms[0].childFuelPrices[i].value;	
				    var babyFuelPrice =document.forms[0].babyFuelPrices[i].value;					 		   
				    fPrices+=1*adultFuelPrice*(1*adultCount) + 1*childFuelPrice*(1*childCount) + 1*babyFuelPrice*(1*babyCount);		
				  }
				}
				else
				{
			      var adultFuelPrice =document.forms[0].adultFuelPrices.value;	
				  var childFuelPrice =document.forms[0].childFuelPrices.value;	
				  var babyFuelPrice =document.forms[0].babyFuelPrices.value;					 		   
				  fPrices+=1*adultFuelPrice*(1*adultCount) + 1*childFuelPrice*(1*childCount) + 1*babyFuelPrice*(1*babyCount);		
				} 

				totleFprice=fPrices
				$("input[name='totalFuelPrice']").val(totleFprice);
			}
		
			//保存
			function addAirTicketOrder()
			{	
			  	var adultCount=document.forms[0].adultCount.value;
				var childCount=document.forms[0].childCount.value;
				var babyCount=document.forms[0].babyCount.value;				
				var totalAmount=document.forms[0].totalAmount.value;
			if(document.forms[0].flightCodes.length)
			{	
			  for(i=0;i<document.forms[0].flightCodes.length;i++)
			  {
				var flightCode =document.forms[0].flightCodes[i].value;
				var startPoint =document.forms[0].startPoints[i].value;
				var endPoint =  document.forms[0].endPoints[i].value;
				var boardingTime =document.forms[0].boardingTimes[i].value;
				var flightClass =document.forms[0].flightClasss[i].value;
				var discount =document.forms[0].discounts[i].value;
				var pan = /^[0-9]+$/;
				
				if(!pan.test(adultCount))
				{
					alert("成人数输入格式不符合!");
					return false;
				}
				if(!pan.test(childCount))
				{
					alert("儿童数输入格式不符合!");
					return false;
				}
				if(!pan.test(babyCount))
				{
					alert("婴儿数输入格式不符合!");
					return false;
				}
				if(flightCode == "")
				{
					alert("航班号不能为空!");
					return false;
				}
				if(startPoint == "")
				{
					alert("出发地不能为空!");
					return false;
				}
				if(endPoint == "")
				{
					alert("目的地不能为空!");
					return false;
				}
				if(boardingTime == "")
				{
					alert("出发时间不能为空!");
					return false;
				}
				if(flightClass == "")
				{
					alert("航位不能为空!");
					return false;
				}
				if(discount == "")
				{
					alert("折扣不能为空!");
					return false;
				}
		      }
			}
			else
			{			
				var flightCode =document.forms[0].flightCodes.value;
				var startPoint =document.forms[0].startPoints.value;
				var endPoint =  document.forms[0].endPoints.value;
				var boardingTime =document.forms[0].boardingTimes.value;
				var flightClass =document.forms[0].flightClasss.value;
				var discount =document.forms[0].discounts.value;
				var pan = /^[0-9]+$/;
				
				if(!pan.test(adultCount))
				{
					alert("成人数输入格式不符合!");
					return false;
				}
				if(!pan.test(childCount))
				{
					alert("儿童数输入格式不符合!");
					return false;
				}
				if(!pan.test(babyCount))
				{
					alert("婴儿数输入格式不符合!");
					return false;
				}
				if(flightCode == "")
				{
					alert("航班号不能为空!");
					return false;
				}
				if(startPoint == "")
				{
					alert("出发地不能为空!");
					return false;
				}
				if(endPoint == "")
				{
					alert("目的地不能为空!");
					return false;
				}
				if(boardingTime == "")
				{
					alert("出发时间不能为空!");
					return false;
				}
				if(flightClass == "")
				{
					alert("航位不能为空!");
					return false;
				}
				if(discount == "")
				{
					alert("折扣不能为空!");
					return false;
				}
		      }
		      
		      var airOrderNos=document.getElementById("airOrderNos").value;
		      if(airOrderNos!=null){
		      	if(airOrderNos==""){
		      		alert("请填写订单号");
					return false;
		      	}
		      }
		      
		      trim(document.forms[0]);
		      
              document.forms[0].submit();
			}	
		</script>
	</head>
	<body>

	 <c:set var="title1" value="团队订单管理"/>
	<c:choose>
	    <c:when test="${airticketOrder.tranType==1 and airticketOrder.id==0}">
	      <c:set var="title2" value="录入团队销售订单"/>
	    </c:when> 
	    <c:when test="${(airticketOrder.tranType==1 or airticketOrder.tranType==2) and airticketOrder.id>0}">
	     <c:set var="title2" value="编辑团队销售订单"/>
	    </c:when> 
	    <c:when test="${airticketOrder.tranType==3 and airticketOrder.id==0}">
	     <c:set var="title2" value="录入团队退票订单"/>
	    </c:when> 
	    <c:when test="${airticketOrder.tranType==3 and airticketOrder.id>0}">
	     	<c:set var="title2" value="编辑团队退票订单"/>
	    </c:when>
	</c:choose>		   	
		<div id="mainContainer">
			<div id="container">			
				<html:form action="airticket/airticketOrder.do?thisAction=updateTeamAirticketOrder" method="post" >					
					<html:hidden property="id"  name="airticketOrder"/>
					<html:hidden property="tranType" value="${airticketOrder.tranType}" name="airticketOrder"/>
					<html:hidden property="businessType" value="${airticketOrder.businessType}" name="airticketOrder"/>
					
					
					<table width="100%" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td width="10" height="10" class="tblt"></td>
							<td height="10" class="tbtt"></td>
							<td width="10" height="10" class="tbrt"></td>
						</tr>
						<tr>
							<td width="10" class="tbll"></td>
							<td valign="top" class="body">
							   <c:import url="../_jsp/mainTitle.jsp" charEncoding="UTF-8">							
									<c:param name="title1" value="${title1}" />
									<c:param name="title2" value="${title2}" />																				
								</c:import>		
								<div class="searchBar">
									<table cellpadding="0" cellspacing="0" border="0"
										class="searchPanel">
										<tr>
											<td>
												购票客户
												<html:select property="agentId"  name="airticketOrder" styleId="agentId" styleClass="colorblue2 p_5"
													style="width:200px;">
													<html:option value="">--请选择--</html:option>
													
												</html:select>
											</td>
											<td>
												订单类型												
												<html:select property="drawer" name="airticketOrder" styleClass="colorblue2 p_5"
													style="width:150px;">
													<html:option value="B2B网电">B2B网电</html:option>
													<html:option value="B2C散客">B2C散客</html:option>
													<html:option value="倒票组">倒票组</html:option>
												</html:select>
											</td>
											<td>
												<html:hidden property="tranType"  name="airticketOrder"/>
												成人数
												<html:text property="adultCount" name="airticketOrder"  styleClass="colorblue2 p_5"
													style="width:50px;" onkeyup="totalPersonCheck()"/>
												儿童数
												<html:text property="childCount" name="airticketOrder"  styleClass="colorblue2 p_5"
													style="width:50px;" onkeyup="totalPersonCheck()"/>
												婴儿数
												<html:text property="babyCount" name="airticketOrder"  styleClass="colorblue2 p_5"
													style="width:50px;" onkeyup="totalPersonCheck()"/>
											</td>
											<td><input name="label" type="button" class="button1" value="乘机人"
												onclick="displayPassenger();"></td>
										</tr>
									</table>
									
								</div>	
								<div id="passengerDiv" style="display:none">
								    乘机人信息：
									<table id="passengerTable" cellpadding="0" cellspacing="0" border="0"
										class="searchPanel">
										<c:choose>
									    <c:when test="${(airticketOrder.id>0 or buyerOrder.id>0) && airticketOrder.passengerSize>0}">
									    <c:forEach var="passenger" items="${airticketOrder.passengers}" varStatus="statusx">
										<tr>
											<td>
											   姓名：<html:text property="passengerNames" value="${passenger.name}"  styleClass="colorblue2 p_5"
													style="width:150px;"/><html:hidden property="passengerIds"  value="${passenger.id}" />
											</td>	
											<td>
											   &nbsp;&nbsp;&nbsp;&nbsp;证件号码：<html:text property="passengerCardnos" value="${passenger.cardno}"  styleClass="colorblue2 p_5"
													style="width:150px;"/>
											</td>
											<td>
												&nbsp;&nbsp;&nbsp;&nbsp;票号：<html:text property="passengerTicketNumbers" value="${passenger.ticketNumber}"  styleClass="colorblue2 p_5"
													style="width:150px;"/>
												<c:if test="${statusx.count==1}">
													<input name="label" type="button" class="button2" value="添加乘机人"
									onclick="addPassenger();">&nbsp;&nbsp; &nbsp;&nbsp;<input name="label" type="button" class="button1" value="保 存"
												onclick="addAirTicketOrder();">
												</c:if>
												<c:if test="${statusx.count>1}">
												<a href='#' onclick='del(this);'>[删除]</a>
												</c:if>
												
												
											</td>
																				
										</tr>
									    </c:forEach>
									    </c:when>
									    <c:when test="${airticketOrder.id<1 && buyerOrder.id<1 || airticketOrder.passengerSize<1}">									    
									    <tr>
											<td>
												姓名：<html:text property="passengerNames" styleClass="colorblue2 p_5"
													style="width:150px;"/>
											</td>	
											<td>
												&nbsp;&nbsp;&nbsp;&nbsp;证件号码：<html:text property="passengerCardnos"   styleClass="colorblue2 p_5"
													style="width:150px;"/>
											</td>
											<td>
												&nbsp;&nbsp;&nbsp;&nbsp;票号：<html:text property="passengerTicketNumbers"  styleClass="colorblue2 p_5"
													style="width:150px;"/>&nbsp;&nbsp;<input name="label" type="button" class="button2" value="添加乘机人"
									onclick="addPassenger();">&nbsp;&nbsp;<input name="label" type="button" class="button1" value="保 存"
												onclick="addAirTicketOrder();">
											</td>													
										</tr>										
										</c:when>
								       </c:choose>										
									</table>
									
								</div>	
								<hr />
								<div>
								 订单信息：
									<table id="orderNoTable" cellpadding="0" cellspacing="0" border="0"
										class="searchPanel">										
									    <tr>
											<td>订单号：</td>
										<td>
											<html:text property="airOrderNos" styleId="airOrderNos" value="${buyerOrder.airOrderNos[0]}" styleClass="colorblue2 p_5"
												style="width:150px;" />
										</td>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;订单金额：</td>
										<td>
											<html:text property="totalAmount" styleId="totalAmount"  value="${buyerOrder.totalAmount}" styleClass="colorblue2 p_5"
												style="width:100px;" />
										</td>										
										<td>&nbsp;&nbsp;&nbsp;&nbsp;团队加价：</td>
										<td>
											<html:text property="teamaddPrice" styleId="teamAddPrice" value="${airticketOrder.teamaddPrice}" styleClass="colorblue2 p_5"
												style="width:100px;" />
										</td>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;客户加价：</td>
										<td>
											<html:text property="agentaddPrice" styleId="agentAddPrice" value="${airticketOrder.agentaddPrice}" styleClass="colorblue2 p_5"	style="width:100px;" />
										</td>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;多收税：</td>
										<td>
											<html:text property="saleOverAirportfulePrice" styleId="saleOverAirportfulePrice" value="${airticketOrder.overAirportfulePrice}" styleClass="colorblue2 p_5"
												style="width:100px;" />
										</td>
										<td>
											<input name="label" type="button" class="button2" value="添加订单号"	onclick="addOrderNo();">
										</td>										
										</tr>
									    <c:if test="${airticketOrder.id>0 or buyerOrder.id>0}">
									    <c:forEach var="no" begin="1" items="${buyerOrder.airOrderNos}" varStatus="status">
									     <tr>
											<td>订单号：</td>
										<td>
											<html:text property="airOrderNos" styleId="airOrderNos" value="${no}" styleClass="colorblue2 p_5"
												style="width:150px;" /><a href='#' onclick='del(this);'>[删除]</a>
										</td>
										</tr>
										</c:forEach>
										</c:if>
										
									</table>
								</div>
								<hr />
								<br/>
								<div>
								航程信息：
								<input name="label" type="button" class="button1" value="添 加"
									onclick="addAirticketOrder2();">
								<br/>	<br/>	
								<table width="100%" cellpadding="0" cellspacing="0" border="0"
									class="dataList" id="table2">
									<tr>
										<th>
											<div>
												航班号
											</div>
										</th>
										<th>
											<div>
												航段
											</div>
										</th>
										<th>
											<div>
												起飞日期
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
												票面价
											</div>
										</th>
										<th>
											<div>
												机建税(成人)
											</div>
										</th>
										<th>
											<div>
												燃油税(成人)
											</div>
										</th>
										<th>
											<div>
												机建税(儿童)
											</div>
										</th>
										<th>
											<div>
												燃油税(儿童)
											</div>
										</th>
										<th>
											<div>
												机建税(婴儿)
											</div>
										</th>
										<th>
											<div>
												燃油税(婴儿)
											</div>
										</th>
										<th>
											<div>
												操作
											</div> 
										</th>
									</tr>									
									<c:choose>
									<c:when test="${airticketOrder.id>0 or buyerOrder.id>0}">
									<c:forEach var="flight" items="${airticketOrder.flights}" varStatus="status">
									<tr>
										<td>
											<html:text property="flightCodes"  styleId="flightCodes" styleClass="colorblue2 p_5" value="${flight.flightCode}"
												style="width:100px;" /><html:hidden property="flightIds"  />
										</td>
										<td>
											<html:text property="startPoints"  styleId="startPoints" styleClass="colorblue2 p_5" value="${flight.startPoint}"
												style="width:50px;" /> __
											<html:text property="endPoints"  styleId="endPoints" styleClass="colorblue2 p_5" value="${flight.endPoint}"
												style="width:50px;" />
										</td>
										<td>
											<html:text property="boardingTimes"  styleId="boardingTimes" styleClass="colorblue2 p_5" value="${flight.boardingTime}"
												style="width:120px;"  onclick="popUpCalendar(this, this);"  readonly="true"/>
										</td>
										<td>
											<html:text property="flightClasss"  styleId="flightClasss" styleClass="colorblue2 p_5" value="${flight.flightClass}"
												style="width:50px;" />
										</td>
										<td>
											<html:text property="discounts"  styleId="discounts" styleClass="colorblue2 p_5" value="${flight.discount}"
												style="width:50px;" />
										</td>
										<td>
											<html:text property="ticketPrices" styleId="ticketPrices"  styleClass="colorblue2 p_5" value="${flight.ticketPrice}"
												style="width:60px;" onkeyup="ticketPriceCheck()"/>
										</td>
										<td>
											<html:text property="adultAirportPrices" styleId="adultAirportPrices"  styleClass="colorblue2 p_5" value="${flight.airportPriceAdult}"
												style="width:60px;" onkeyup="airportPriceCheck()"/>
										</td>
										<td>
											<html:text property="adultFuelPrices" styleId="adultFuelPrices"  styleClass="colorblue2 p_5" value="${flight.fuelPriceAdult}"
												style="width:60px;" onkeyup="fuelPriceCheck()"/>
										</td>
										<td>
											<html:text property="childAirportPrices" styleId="childAirportPrices"  styleClass="colorblue2 p_5" value="${flight.airportPriceChild}"
												style="width:60px;" onkeyup="airportPriceCheck()"/>
										</td>
										<td>
											<html:text property="childFuelPrices" styleId="childFuelPrices"  styleClass="colorblue2 p_5" value="${flight.fuelPriceChild}"
												style="width:60px;" onkeyup="fuelPriceCheck()"/>
										</td>
										<td>
											<html:text property="babyAirportPrices" styleId="babyAirportPrices"  styleClass="colorblue2 p_5" value="${flight.airportPriceBaby}"
												style="width:60px;" onkeyup="airportPriceCheck()"/>
										</td>
										<td>
											<html:text property="babyFuelPrices" styleId="babyFuelPrices"  styleClass="colorblue2 p_5" value="${flight.fuelPriceBaby}"
												style="width:60px;" onkeyup="fuelPriceCheck()"/>
										</td>
										<td>
											<a href='#' onclick='del(this);'>[删除]</a>
										</td>
									</tr>
									</c:forEach>
									</c:when>
									<c:when test="${airticketOrder.id<1 && buyerOrder.id<1}">
									<tr>
										<td>
											<html:text property="flightCodes" styleId="flightCodes" styleClass="colorblue2 p_5"
												style="width:100px;" /><html:hidden property="flightIds" />
										</td>
										<td>
											<html:text property="startPoints" styleId="startPoints" styleClass="colorblue2 p_5"
												style="width:50px;" /> __
											<html:text property="endPoints" styleId="endPoints" styleClass="colorblue2 p_5"
												style="width:50px;" />
										</td>
										<td>
											<html:text property="boardingTimes" styleId="boardingTimes"  styleClass="colorblue2 p_5"
												style="width:120px;"  onclick="popUpCalendar(this, this);"  readonly="true"/>
										</td>
										<td>
											<html:text property="flightClasss" styleId="flightClasss"  styleClass="colorblue2 p_5"
												style="width:50px;" />
										</td>
										<td>
											<html:text property="discounts" styleId="discounts" styleClass="colorblue2 p_5" value="0"
												style="width:50px;" />
										</td>
										<td>
											<html:text property="ticketPrices" styleId="ticketPrices"  value="0" styleClass="colorblue2 p_5"
												style="width:60px;" onkeyup="ticketPriceCheck()"/>
										</td>
										<td>
											<html:text property="adultAirportPrices" styleId="adultAirportPrices"  value="0"  styleClass="colorblue2 p_5"
												style="width:60px;" onkeyup="airportPriceCheck()"/>
										</td>
										<td>
											<html:text property="adultFuelPrices" styleId="adultFuelPrices"  value="0"styleClass="colorblue2 p_5"
												style="width:60px;" onkeyup="fuelPriceCheck()"/>
										</td>
										<td>
											<html:text property="childAirportPrices" styleId="childAirportPrices"  value="0"styleClass="colorblue2 p_5"
												style="width:60px;" onkeyup="airportPriceCheck()"/>
										</td>
										<td>
											<html:text property="childFuelPrices" styleId="childFuelPrices"  value="0"styleClass="colorblue2 p_5"
												style="width:60px;" onkeyup="fuelPriceCheck()"/>
										</td>
										<td>
											<html:text property="babyAirportPrices" styleId="babyAirportPrices" value="0" styleClass="colorblue2 p_5"
												style="width:60px;" onkeyup="airportPriceCheck()"/>
										</td>
										<td>
											<html:text property="babyFuelPrices" styleId="babyFuelPrices"  value="0" styleClass="colorblue2 p_5"
												style="width:60px;" onkeyup="fuelPriceCheck()"/>
										</td>
										<td>
											<a href='#' onclick='del(this);'>[删除]</a>
										</td>
									</tr>
									</c:when>
								    </c:choose>
								</table>
								</div>								
								<br />
								<table width="100%" cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td>
											团队总人数：
											<html:text property="totalPerson"  styleClass="colorblue2 p_5"
												style="width:60px;" />
											总票面价
											<html:text property="totalTicketPrice"  styleClass="colorblue2 p_5"
												style="width:60px;" onmousedown="ticketPriceCheck()"/>
											总机建税
											<html:text property="totalAirportPrice"  styleClass="colorblue2 p_5"
												style="width:60px;" />
											总燃油税
											<html:text property="totalFuelPrice"  styleClass="colorblue2 p_5"
												style="width:60px;"/>
											
											<input name="label" type="button" class="button1" value="保 存"
												onclick="addAirTicketOrder();">
										</td>
										<td>	
										<c:if test="${airticketOrder.id>0 and airticketOrder.status==101}">								
												<a href="#" onclick="showDiv('<c:out value="${buyerOrder.orderGroup.id}" />','<c:out value="${buyerOrder.subGroupMarkNo}" />')">利润统计</a>
										</c:if>
										</td>									
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan="5"><hr/></td>
						</tr>
					</table>
					<c:if test="${airticketOrder.id>0}">
						<table width="100%" cellpadding="0" cellspacing="0" border="0"
									class="dataList">
									<tr>
										<th>
											<div>
												应付出团代理费(现返)
											</div>
										</th>
										<th>
											<div>
												应收票款
											</div>
										</th>
										<th>
											<div>
												月底代理费
											</div>
										</th>
										<th>
											<div>
												应付票款
											</div>
										</th>
										<th>
											<div>
												实付票款
											</div>
										</th>										
										<th>
											<div>
												毛利润
											</div>
										</th>
										<th>
											<div>
												退票利润
											</div>
										</th>
										<th>
											<div>
												多收票款
											</div>
										</th>
										<th>
											<div>
												净利合计
											</div>
										</th>
										<th>
											<div>
												总金额
											</div>
										</th>
										<th>
											<div>
												操作
											</div>
										</th>
									</tr>
									<tr>
											<td>
												<c:out value="${teamProfit.commission}" />
											</td>
											<td>
												<c:out value="${teamProfit.saleTicketPrice}" />
											</td>
											<td>
												<c:out value="${teamProfit.buyRakeOff}" />
											</td>
											<td>
												<c:out value="${teamProfit.buyTicketPrice}" />
											</td>
											<td>	
												<c:out value="${teamProfit.buyTotalAmount}" />										
											</td>	
											<td>											
											   <c:out value="${teamProfit.grossProfit}" />
											</td>
											<td>	
												<c:out value="${teamProfit.refundProfit}" />											
											</td>
											<td>
												<c:out value="${teamProfit.saleOverPrice}" />											
											</td>
											<td>
												<c:out value="${teamProfit.totalProfit}" />											
											</td>
											<td>											
												<c:out value="${buyerOrder.totalAmount}" />	
											</td>
											<td>											
												<a href="#" onclick="showDiv('<c:out value="${buyerOrder.orderGroup.id}" />','<c:out value="${buyerOrder.subGroupMarkNo}" />')">编辑</a>
											</td>											
									</tr>
							</table>	
					</c:if>					
				</html:form>
			</div>
		</div>
		 <c:import url="../airticket/teamProfitStatistic.jsp" charEncoding="UTF-8"/>		
	</body>
</html>
