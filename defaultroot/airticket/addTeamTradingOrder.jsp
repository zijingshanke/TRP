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
		<script src="../_js/popcalendar.js" type="text/javascript"></script>
		<script src="../_js/popcalendar.js" type="text/javascript"></script>
		<script type='text/javascript' src='<%=path %>/dwr/interface/platComAccountStore.js'></script>
 		 <script type='text/javascript' src='<%=path %>/dwr/engine.js'></script>
		 <script type='text/javascript' src='<%=path %>/dwr/util.js'></script>
		<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
		
		<script type="text/javascript">
			$(document).ready(function(){
				platComAccountStore.getTempAgentListBytype(2,getData);
				function getData(data)
				{
					for(var i=0;i<data.length;i++)
					{
						//alert(data[i].name);						
						document.forms[0].agentNo.options[i] = new Option(data[i].name,data[i].id);
					}
				}
			});			
			
			
		</script>
		<script type="text/javascript">
		
		
			var num1=2;
			var num2=2;
			
			//键事件	(计算总数)		
			function totlePernsonCheck()//团队总人数
			{
				var adultCount =document.forms[0].adultCount.value;
				var childCount =document.forms[0].childCount.value;
				var babyCount =document.forms[0].babyCount.value;
				var totlep =1*adultCount + 1*childCount + 1*babyCount;
				$("input[name='totlePernson']").val(totlep);
				
				ticketPriceCheck();//总票面价
				airportPriceCheck();//总机建税 
				fuelPriceCheck();// 总燃油税
			}
			
			//生成订单信息表单
			function addAirticketOrder1()
			{		
				//alert($("#airOrderNo"+(1*num-1)).val());
				var str="";
				str+="<tr>";
				str+="<td><input name=\"airOrderNo"+"\" id=\"airOrderNo"+num1+"\" class=\"colorblue2 p_5"+"\" style=\"width: 150px"+"\"></td>";
				str+="<td><input name=\"totalAmount"+"\" id=\"totalAmount"+num1+"\" class=\"colorblue2 p_5"+"\" style=\"width: 100px"+"\"></td>";
				str+="<td><span onclick=\"return removet();\"><a>[编辑]</a> <a>[保存]</a> <a>[删除]</a></td>";	
				str+="</tr>"
				$("#table1").append(str);
				num1++;
			}
		
			//生成航程信息表单
			function addAirticketOrder2()
			{		
				var str="";
				str+="<tr id='tr1'>";
				str+="<td><input name=\"flightCode"+"\" id=\"flightCode"+num2+"\" class=\"colorblue2 p_5"+"\" style=\"width: 100px"+"\"></td>";
				str+="<td><input name=\"startPoint"+"\" id=\"startPoint"+num2+"\" class=\"colorblue2 p_5"+"\" style=\"width: 50px"+"\"> __ <input name=\"endPoint"+"\" id=\"endPoint"+num2+"\" class=\"colorblue2 p_5"+"\" style=\"width: 50px"+"\"></td>";
				str+="<td><input name=\"boardingTime"+"\" id=\"boardingTime"+num2+"\" class=\"colorblue2 p_5"+"\" style=\"width: 100px"+"\"  onclick=\"popUpCalendar(this, this);"+"\"  readonly=\"true"+"\"></td>";
				str+="<td><input name=\"flightClass"+"\" id=\"flightClass"+num2+"\" class=\"colorblue2 p_5"+"\" style=\"width: 50px"+"\"></td>";
				str+="<td><input name=\"discount"+"\" id=\"discount"+num2+"\"  value=\""+0+"\" class=\"colorblue2 p_5"+"\" style=\"width: 50px"+"\"></td>";
				str+="<td><input name=\"ticketPrice"+"\" id=\"ticketPrice"+num2+"\" value=\""+0+"\" onkeyup=\"ticketPriceCheck()"+"\" class=\"colorblue2 p_5"+"\" style=\"width: 60px"+"\"></td>";
				str+="<td><input name=\"adultAirportPrice"+"\" id=\"adultAirportPrice"+num2+"\" value=\""+0+"\" onkeyup=\"airportPriceCheck()"+"\" class=\"colorblue2 p_5"+"\" style=\"width: 60px"+"\"></td>";
				str+="<td><input name=\"adultFuelPrice"+"\" id=\"adultFuelPrice"+num2+"\" value=\""+0+"\" onkeyup=\"fuelPriceCheck()"+"\" class=\"colorblue2 p_5"+"\" style=\"width: 60px"+"\"></td>";
				str+="<td><input name=\"childAirportPrice"+"\" id=\"childAirportPrice"+num2+"\" value=\""+0+"\" onkeyup=\"airportPriceCheck()"+"\" class=\"colorblue2 p_5"+"\" style=\"width: 60px"+"\"></td>";
				str+="<td><input name=\"childfuelPrice"+"\" id=\"childfuelPrice"+num2+"\" value=\""+0+"\" onkeyup=\"fuelPriceCheck()"+"\" class=\"colorblue2 p_5"+"\" style=\"width: 60px"+"\"></td>";
				str+="<td><input name=\"babyAirportPrice"+"\" id=\"babyAirportPrice"+num2+"\" value=\""+0+"\" onkeyup=\"airportPriceCheck()"+"\" class=\"colorblue2 p_5"+"\" style=\"width: 60px"+"\"></td>";
				str+="<td><input name=\"babyfuelPrice"+"\" id=\"babyfuelPrice"+num2+"\" value=\""+0+"\" onkeyup=\"fuelPriceCheck()"+"\" class=\"colorblue2 p_5"+"\" style=\"width: 60px"+"\"></td>";				
				str+="<td><a href='#' onclick='del(this);'>[删除]</a></td>";	
				str+="</tr>"
				$("#table2").append(str);
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
				var totlePerson =$("input[name='totlePernson']").val();//总人数据
				var ticketPrice1 = $("#ticketPrice1").val();
				var totle=(1*ticketPrice1);
				for(var i=2;i<num2;i++)
				{			
					totle+= 1*($("#ticketPrice"+i).val());
				}
					totleTick=totle*(1*totlePerson)//总票面价*总人数
				$("input[name='totlePrice']").val(totleTick);
			}
			function airportPriceCheck()//总机建税 
			{
				var totlePerson =$("input[name='totlePernson']").val();//总人数据
				
				var adultCount =document.forms[0].adultCount.value;//成人总数
				var childCount =document.forms[0].childCount.value;//儿童总数
				var babyCount =document.forms[0].babyCount.value;//婴儿总数
				
				var adultAirportPrice1 = $("#adultAirportPrice1").val();
				var childAirportPrice1 = $("#childAirportPrice1").val();
				var babyAirportPrice1 = $("#babyAirportPrice1").val();
				var aPrice =1*adultAirportPrice1*(1*adultCount) + 1*childAirportPrice1*(1*childCount) + 1*babyAirportPrice1*(1*babyCount);//num2=2
				
				for(var i=2;i<num2;i++)//(先计算出新增加一行的数据)
				{
					aPrice+= 1*($("#adultAirportPrice"+i).val())*(1*adultCount) + 1*($("#childAirportPrice"+i).val())*(1*childCount) + 1*($("#babyAirportPrice"+i).val())*(1*babyCount);
				}
				totleAprice=aPrice;
				$("input[name='airportPrice']").val(totleAprice);
			}
			function fuelPriceCheck()// 总燃油税
			{
				var adultCount =document.forms[0].adultCount.value;//成人总数
				var childCount =document.forms[0].childCount.value;//儿童总数
				var babyCount =document.forms[0].babyCount.value;//婴儿总数
				
				var totlePerson =$("input[name='totlePernson']").val();//总人数据
				var adultFuelPrice1 = $("#adultFuelPrice1").val();
				var childfuelPrice1 = $("#childfuelPrice1").val();
				var babyfuelPrice1 = $("#babyfuelPrice1").val();
				var fPrice =1*adultFuelPrice1*(1*adultCount) + 1*childfuelPrice1*(1*childCount) + 1*babyfuelPrice1*(1*babyCount);
				
				for(var i=2;i<num2;i++)
				{
					fPrice+= 1*($("#adultFuelPrice"+i).val())*(1*adultCount) + 1*($("#childfuelPrice"+i).val())*(1*childCount) + 1*($("#babyfuelPrice"+i).val())*(1*babyCount);
				}
				totleFprice=fPrice;
				$("input[name='fuelPrice']").val(totleFprice);
			}
		
			//保存
			function addAirTicketOrder()
			{	
				var adultCount=document.forms[0].adultCount.value;
				var childCount=document.forms[0].childCount.value;
				var babyCount=document.forms[0].babyCount.value;
				var airOrderNo=document.forms[0].airOrderNo.value;
				var totalAmount=document.forms[0].totalAmount.value;
				
				var flightCode =document.forms[0].flightCode.value;
				var startPoint =document.forms[0].startPoint.value;
				var endPoint =document.forms[0].endPoint.value;
				var boardingTime =document.forms[0].boardingTime.value;
				var flightClass =document.forms[0].flightClass.value;
				var discount =document.forms[0].discount.value;
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
				if(airOrderNo == "")
				{
					alert("订单号不能为空!");
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
				
				
				document.forms[0].submit();
			}
			
		
		</script>
		
		
	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
				<html:form action="airticket/airticketOrder.do?thisAction=saveAirticketOrderTemp" method="post" >					
					<table width="100%" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td width="10" height="10" class="tblt"></td>
							<td height="10" class="tbtt"></td>
							<td width="10" height="10" class="tbrt"></td>
						</tr>
						<tr>
							<td width="10" class="tbll"></td>
							<td valign="top" class="body">
								<c:import url="../_jsp/mainTitle.jsp?title1=票务管理&title2=团队销售订单录入"
									charEncoding="UTF-8" />

								<div class="searchBar">
									<table cellpadding="0" cellspacing="0" border="0"
										class="searchPanel">
										<tr>
											<td>
												购票客户
												<html:select property="agentNo" name="airticketOrder" styleClass="colorblue2 p_5"
													style="width:150px;">
													<html:option value="">--请选择--</html:option>
												</html:select>
											</td>
											<td>
												出票人
												
												<html:select property="drawer" name="airticketOrder" styleClass="colorblue2 p_5"
													style="width:150px;">
													<html:option value="B2B网电">B2B网电</html:option>
													<html:option value="B2C散客">B2C散客</html:option>
													<html:option value="导票组">导票组</html:option>
												</html:select>
											</td>
											<td>
												<html:hidden property="tranType"  value="2" name="airticketOrder"/>
												成人数
												<html:text property="adultCount" name="airticketOrder" value="0" styleClass="colorblue2 p_5"
													style="width:50px;" onkeyup="totlePernsonCheck()"/>
												儿童数
												<html:text property="childCount" name="airticketOrder" value="0" styleClass="colorblue2 p_5"
													style="width:50px;" onkeyup="totlePernsonCheck()"/>
												婴儿数
												<html:text property="babyCount" name="airticketOrder" value="0" styleClass="colorblue2 p_5"
													style="width:50px;" onkeyup="totlePernsonCheck()"/>
											</td>
										</tr>
									</table>
									<hr />
								</div>
								订单信息：
								<input name="label" type="button" class="button1" value="添 加"
									onclick="addAirticketOrder1();" style="display: none;">
									
								<div class="searchBar">
									<table cellpadding="0" cellspacing="0" border="0"
										class="searchPanel">
										<tr>
											<td>订单号</td>
										<td>
											<html:text property="airOrderNo" styleId="airOrderNo1" name="airticketOrder" styleClass="colorblue2 p_5"
												style="width:150px;" />
										</td>
										<td>订单金额</td>
										<td>
											<html:text property="totalAmount" styleId="totalAmount1" name="airticketOrder" styleClass="colorblue2 p_5"
												style="width:100px;" />
										</td>
										<td>团队加价</td>
										<td>
											<html:text property="teamAddPrice" styleId="teamAddPrice" name="airticketOrder" styleClass="colorblue2 p_5"
												style="width:100px;" />
										</td>
										<td>客户加价</td>
										<td>
											<html:text property="agentAddPrice" styleId="agentAddPrice" name="airticketOrder" styleClass="colorblue2 p_5"
												style="width:100px;" />
										</td>
										<td style="display: none;">
											<a href="#">[编辑]</a>
											<a href="#">[保存]</a>
											<a href="#">[删除]</a>
										</td>
										</tr>
									</table>
								</div>
								<br />
								航程信息：
								<input name="label" type="button" class="button1" value="添 加"
									onclick="addAirticketOrder2();">
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
									<tr>
										<td>
											<html:text property="flightCode" name="airticketOrder" styleClass="colorblue2 p_5"
												style="width:100px;" />
										</td>
										<td>
											<html:text property="startPoint" name="airticketOrder" styleClass="colorblue2 p_5"
												style="width:50px;" /> __
											<html:text property="endPoint" name="airticketOrder" styleClass="colorblue2 p_5"
												style="width:50px;" />
										</td>
										<td>
											<html:text property="boardingTime" name="airticketOrder" styleClass="colorblue2 p_5"
												style="width:100px;"  onclick="popUpCalendar(this, this);"  readonly="true"/>
										</td>
										<td>
											<html:text property="flightClass" name="airticketOrder" styleClass="colorblue2 p_5"
												style="width:50px;" />
										</td>
										<td>
											<html:text property="discount" name="airticketOrder" styleClass="colorblue2 p_5" value="0"
												style="width:50px;" />
										</td>
										<td>
											<html:text property="ticketPrice" styleId="ticketPrice1" name="airticketOrder" styleClass="colorblue2 p_5"
												style="width:60px;" onkeyup="ticketPriceCheck()"/>
										</td>
										<td>
											<html:text property="adultAirportPrice" styleId="adultAirportPrice1" name="airticketOrder" styleClass="colorblue2 p_5"
												style="width:60px;" onkeyup="airportPriceCheck()"/>
										</td>
										<td>
											<html:text property="adultFuelPrice" styleId="adultFuelPrice1" name="airticketOrder" styleClass="colorblue2 p_5"
												style="width:60px;" onkeyup="fuelPriceCheck()"/>
										</td>
										<td>
											<html:text property="childAirportPrice" styleId="childAirportPrice1" name="airticketOrder" styleClass="colorblue2 p_5"
												style="width:60px;" onkeyup="airportPriceCheck()"/>
										</td>
										<td>
											<html:text property="childfuelPrice" styleId="childfuelPrice1" name="airticketOrder" styleClass="colorblue2 p_5"
												style="width:60px;" onkeyup="fuelPriceCheck()"/>
										</td>
										<td>
											<html:text property="babyAirportPrice" styleId="babyAirportPrice1" name="airticketOrder" styleClass="colorblue2 p_5"
												style="width:60px;" onkeyup="airportPriceCheck()"/>
										</td>
										<td>
											<html:text property="babyfuelPrice" styleId="babyfuelPrice1" name="airticketOrder" styleClass="colorblue2 p_5"
												style="width:60px;" onkeyup="fuelPriceCheck()"/>
										</td>
										<td>
											<a href="#">[删除]</a>
										</td>
									</tr>
								</table>
								<br />

								<table width="100%" cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td>
											团队总人数：
											<html:text property="totlePernson" styleClass="colorblue2 p_5"
												style="width:60px;" />
											总票面价
											<html:text property="totlePrice" styleClass="colorblue2 p_5"
												style="width:60px;" onmousedown="ticketPriceCheck()"/>
											总机建税
											<html:text property="airportPrice" styleClass="colorblue2 p_5"
												style="width:60px;" />
											总燃油税
											<html:text property="fuelPrice" styleClass="colorblue2 p_5"
												style="width:60px;"/>
											
											<input name="label" type="button" class="button1" value="保 存"
												onclick="addAirTicketOrder();">
										</td>

									</tr>
								</table>
							</td>
						</tr>
					</table>
				</html:form>
			</div>
		</div>
	</body>
</html>
