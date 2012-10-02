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
		<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
		
		<script type="text/javascript">
			function download()
			{
				document.forms[0].action="<%=path %>/transaction/platformbankCardPaymentList.do?thisAction=downloadPlatformbankCardPayment";
				document.forms[0].submit();
			}
		</script>
		
	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
				<html:form action="/transaction/platformbankCardPaymentList.do?thisAction=list">
					<html:hidden property="thisAction" value="list"/>
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
								<c:import url="../_jsp/mainTitle.jsp?title1=付款管理&title2=平台银行卡付款报表"
									charEncoding="UTF-8" />

								<div class="searchBar">
									<table cellpadding="0" cellspacing="0" border="0"
										class="searchPanel">
										<tr>
											
											<td>
												选择日期:
												<html:text property="startDate"
													styleClass="colorblue2 p_5" style="width:150px;"
													onclick="popUpCalendar(this, this);" readonly="true" />
												__
												<html:text property="endDate"
													styleClass="colorblue2 p_5" style="width:150px;"
													onclick="popUpCalendar(this, this);" readonly="true" />
											</td>
											<td>
												<input type="submit" name="button" id="button" value="提交"
													class="submit greenBtn" />

												<input type="button" name="button" id="button" value="导出"
													onclick="download()" class="submit greenBtn" />
											</td>
										</tr>
									</table>
									<hr />
								</div>
								<table width="100%" cellpadding="0" cellspacing="0" border="0"
									class="dataList">
									<tr>
										<th  rowspan="2"><div>平台</div></th>
										<th colspan="2"><div>支付宝taishen</div></th>
										<th colspan="2"><div>支付宝9679</div></th>
										<th colspan="2"><div>支付宝SPC</div></th>
										<th colspan="2"><div>支付宝6511</div></th>
										<th colspan="2"><div>支付宝信用支付</div></th>
										<th colspan="2"><div>支付宝5262</div></th>
										<th colspan="2"><div>支付宝5261</div></th>
										<th colspan="2"><div>支付宝5250</div></th>
										<th colspan="2"><div>快钱5261</div></th>
										<th colspan="2"><div>快钱5262</div></th>
										<th colspan="2"><div>B2C代出</div></th>
										<th colspan="2"><div>欠款</div></th>
										<th colspan="2"><div>现金</div></th>
										<th colspan="2"><div>MU白金卡</div></th>
										<th colspan="2"><div>汇付现金支付</div></th>
										<th colspan="2"><div>汇付Gold</div></th>
										<th colspan="2"><div>汇付信用支付</div></th>
										<th colspan="2"><div>汇付zuhts</div></th>
										<th colspan="2"><div>汇付920</div></th>
										<th colspan="2"><div>汇付10086</div></th>
										<th colspan="2"><div>汇付现金池</div></th>
										<th colspan="2"><div>etszx132</div></th>
										<th colspan="2"><div>et11586755826</div></th>
										<th colspan="2"><div>etzuh193</div></th>
										<th colspan="2"><div>ettaishengspc</div></th>
										<th colspan="2"><div>ettaishenspc</div></th>
										<th colspan="2"><div>工行北岭支行</div></th>
										<th colspan="2"><div>工妙</div></th>
										<th colspan="2"><div>工苏</div></th>
										<th colspan="2"><div>工何</div></th>
										<th colspan="2"><div>工总</div></th>
										<th colspan="2"><div>工关</div></th>
										<th colspan="2"><div>工张</div></th>
										<th colspan="2"><div>工庄</div></th>
										<th colspan="2"><div>工俊</div></th>
										<th colspan="2"><div>工毛</div></th>
										<th colspan="2"><div>工行拱北支行</div></th>
										<th colspan="2"><div>虚拟</div></th>
										<th colspan="2"><div>虚拟币</div></th>
										<th colspan="2"><div>记帐式服务</div></th>
										<th colspan="2"><div>钱门tsb2c</div></th>
										<th colspan="2"><div>钱门CHEN</div></th>
										<th colspan="2"><div>钱门9679</div></th>
										<th colspan="2"><div>钱门6511</div></th>
										<th colspan="2"><div>钱门记账式服务</div></th>
										<th colspan="2"><div>钱门SPC</div></th>
										<th colspan="2"><div>财付通6511</div></th>
										<th colspan="2"><div>财付通SPC</div></th>
										<th colspan="2"><div>财付通9679</div></th>
										<th colspan="2"><div>财付通CHEN</div></th>
										<th colspan="2"><div>易宝信用5838</div></th>
										<th  colspan="2">合计</th>
									
									</tr>
                                   	<tr>
									
										<th><div>支出</div></th>
										<th><div>收入</div></th>
										
										<th><div>支出</div></th>
										<th><div>收入</div></th>
										
										<th><div>支出</div></th>
										<th><div>收入</div></th>
										
										<th><div>支出</div></th>
										<th><div>收入</div></th>
								
										<th><div>收入</div></th>
										<th><div>支出</div></th>
										
										<th><div>支出</div></th>
										<th><div>收入</div></th>
										
										<th><div>支出</div></th>
										<th><div>收入</div></th>
										
										<th><div>支出</div></th>
										<th><div>收入</div></th>
										
										<th><div>支出</div></th>
										<th><div>收入</div></th>
								
										<th><div>收入</div></th>
										<th><div>支出</div></th>
										
										<th><div>支出</div></th>
										<th><div>收入</div></th>
										
										<th><div>支出</div></th>
										<th><div>收入</div></th>
										
										<th><div>支出</div></th>
										<th><div>收入</div></th>
										
										<th><div>支出</div></th>
										<th><div>收入</div></th>
								
										<th><div>收入</div></th>
										<th><div>支出</div></th>
										
										<th><div>支出</div></th>
										<th><div>收入</div></th>
										
										<th><div>支出</div></th>
										<th><div>收入</div></th>
										
										<th><div>支出</div></th>
										<th><div>收入</div></th>
										
										<th><div>支出</div></th>
										<th><div>收入</div></th>
								
										<th><div>收入</div></th>
										<th><div>支出</div></th>
										<th><div>支出</div></th>
										<th><div>收入</div></th>
										
										<th><div>支出</div></th>
										<th><div>收入</div></th>
										
										<th><div>支出</div></th>
										<th><div>收入</div></th>
										
										<th><div>支出</div></th>
										<th><div>收入</div></th>
								
										<th><div>收入</div></th>
										<th><div>支出</div></th>
										
										<th><div>支出</div></th>
										<th><div>收入</div></th>
										
										<th><div>支出</div></th>
										<th><div>收入</div></th>
										
										<th><div>支出</div></th>
										<th><div>收入</div></th>
										
										<th><div>支出</div></th>
										<th><div>收入</div></th>
								
										<th><div>收入</div></th>
										<th><div>支出</div></th>
										<th><div>支出</div></th>
										<th><div>收入</div></th>
										
										<th><div>支出</div></th>
										<th><div>收入</div></th>
										
										<th><div>支出</div></th>
										<th><div>收入</div></th>
										
										<th><div>支出</div></th>
										<th><div>收入</div></th>
								
										<th><div>收入</div></th>
										<th><div>支出</div></th>
										
										<th><div>支出</div></th>
										<th><div>收入</div></th>
										
										<th><div>支出</div></th>
										<th><div>收入</div></th>
										
										<th><div>支出</div></th>
										<th><div>收入</div></th>
										
										<th><div>支出</div></th>
										<th><div>收入</div></th>
								
										<th><div>收入</div></th>
										<th><div>支出</div></th>
										<th><div>支出</div></th>
										<th><div>收入</div></th>
										
										<th><div>支出</div></th>
										<th><div>收入</div></th>
										
										<th><div>支出</div></th>
										<th><div>收入</div></th>
										
										<th><div>支出</div></th>
										<th><div>收入</div></th>
								
										<th><div>收入</div></th>
										<th><div>支出</div></th>
										
										<th><div>支出</div></th>
										<th><div>收入</div></th>
										
										<th><div>支出</div></th>
										<th><div>收入</div></th>
										
										<th><div>支出</div></th>
										<th><div>收入</div></th>
										
										<th><div>支出</div></th>
										<th><div>收入</div></th>
								
										<th><div>收入</div></th>
										<th><div>支出</div></th>
										
										<th><div>收入</div></th>
										<th><div>支出</div></th>
										
										<th><div>总收入</div></th>
										<th><div>总支出</div></th>
										
									</tr>
									<c:forEach var="ba" items="${pbplistForm.list}" 	varStatus="sta">
										<tr>
											
											<td>
												<c:out value="${ba.platform}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount1}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount1}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount2}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount2}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount3}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount3}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount4}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount4}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount5}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount5}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount6}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount6}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount7}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount7}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount8}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount8}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount9}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount9}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount10}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount10}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount11}"></c:out>
									 		</td>
									 		<td>
												<c:out value="${ba.toAccount11}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount12}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount12}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount13}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount13}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount14}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount14}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount15}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount15}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount16}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount16}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount17}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount17}"></c:out>
									 		</td>
									 		<td>
												<c:out value="${ba.fromAccount18}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount18}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount19}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount19}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount20}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount20}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount21}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount21}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount22}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount22}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount23}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount23}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount24}"></c:out>
									 		</td>
									 		<td>
												<c:out value="${ba.toAccount24}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount25}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount25}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount26}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount26}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount27}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount27}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount28}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount28}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount29}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount29}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount30}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount30}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount31}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount31}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount32}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount32}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount33}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount33}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount34}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount34}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount35}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount35}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount36}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount36}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount37}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount37}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount38}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount38}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount39}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount39}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount40}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount40}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount41}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount41}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount42}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount42}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount43}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount43}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount44}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount44}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount45}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount45}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount46}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount46}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount47}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount47}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount48}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount48}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount49}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount49}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount50}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount50}"></c:out>
											</td>
											<td>
												<c:out value="${ba.fromAccount51}"></c:out>
											</td>
											<td>
												<c:out value="${ba.toAccount51}"></c:out>
											</td>
											<td>
												<c:out value="${ba.allToAccount}"></c:out>
											</td>
											<td>
												<c:out value="${ba.allFromAccount}"></c:out>
											</td>
									 		
										</tr>
									</c:forEach>
							<c:if test="${!empty pbplistForm.list}">
							  <tr>
							    <td>合计：</td>
							    <td><c:out value="${pbplistForm.totalValues[0]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[1]}" /></td>
						        <td><c:out value="${pbplistForm.totalValues[2]}" /></td>
						        <td><c:out value="${pbplistForm.totalValues[3]}" /></td>
						        <td><c:out value="${pbplistForm.totalValues[4]}" /></td>
						        <td><c:out value="${pbplistForm.totalValues[5]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[6]}" /></td>   
							    <td><c:out value="${pbplistForm.totalValues[7]}" /></td> 
							    <td><c:out value="${pbplistForm.totalValues[8]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[9]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[10]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[11]}" /></td>
						        <td><c:out value="${pbplistForm.totalValues[12]}" /></td>
						        <td><c:out value="${pbplistForm.totalValues[13]}" /></td>
						        <td><c:out value="${pbplistForm.totalValues[14]}" /></td>
						        <td><c:out value="${pbplistForm.totalValues[15]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[16]}" /></td>   
							    <td><c:out value="${pbplistForm.totalValues[17]}" /></td> 
							    <td><c:out value="${pbplistForm.totalValues[18]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[19]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[20]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[21]}" /></td>
						        <td><c:out value="${pbplistForm.totalValues[22]}" /></td>
						        <td><c:out value="${pbplistForm.totalValues[23]}" /></td>
						        <td><c:out value="${pbplistForm.totalValues[24]}" /></td>
						        <td><c:out value="${pbplistForm.totalValues[25]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[26]}" /></td>   
							    <td><c:out value="${pbplistForm.totalValues[27]}" /></td> 
							    <td><c:out value="${pbplistForm.totalValues[28]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[29]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[30]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[31]}" /></td>
						        <td><c:out value="${pbplistForm.totalValues[32]}" /></td>
						        <td><c:out value="${pbplistForm.totalValues[33]}" /></td>
						        <td><c:out value="${pbplistForm.totalValues[34]}" /></td>
						        <td><c:out value="${pbplistForm.totalValues[35]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[36]}" /></td>   
							    <td><c:out value="${pbplistForm.totalValues[37]}" /></td> 
							    <td><c:out value="${pbplistForm.totalValues[38]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[39]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[40]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[41]}" /></td>
						        <td><c:out value="${pbplistForm.totalValues[42]}" /></td>
						        <td><c:out value="${pbplistForm.totalValues[43]}" /></td>
						        <td><c:out value="${pbplistForm.totalValues[44]}" /></td>
						        <td><c:out value="${pbplistForm.totalValues[45]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[46]}" /></td>   
							    <td><c:out value="${pbplistForm.totalValues[47]}" /></td> 
							    <td><c:out value="${pbplistForm.totalValues[48]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[49]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[50]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[51]}" /></td>
						        <td><c:out value="${pbplistForm.totalValues[52]}" /></td>
						        <td><c:out value="${pbplistForm.totalValues[53]}" /></td>
						        <td><c:out value="${pbplistForm.totalValues[54]}" /></td>
						        <td><c:out value="${pbplistForm.totalValues[55]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[56]}" /></td>   
							    <td><c:out value="${pbplistForm.totalValues[57]}" /></td> 
							    <td><c:out value="${pbplistForm.totalValues[58]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[59]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[60]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[61]}" /></td>
						        <td><c:out value="${pbplistForm.totalValues[62]}" /></td>
						        <td><c:out value="${pbplistForm.totalValues[63]}" /></td>
						        <td><c:out value="${pbplistForm.totalValues[64]}" /></td>
						        <td><c:out value="${pbplistForm.totalValues[65]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[66]}" /></td>   
							    <td><c:out value="${pbplistForm.totalValues[67]}" /></td> 
							    <td><c:out value="${pbplistForm.totalValues[68]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[69]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[70]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[71]}" /></td>
						        <td><c:out value="${pbplistForm.totalValues[72]}" /></td>
						        <td><c:out value="${pbplistForm.totalValues[73]}" /></td>
						        <td><c:out value="${pbplistForm.totalValues[74]}" /></td>
						        <td><c:out value="${pbplistForm.totalValues[75]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[76]}" /></td>   
							    <td><c:out value="${pbplistForm.totalValues[77]}" /></td> 
							    <td><c:out value="${pbplistForm.totalValues[78]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[79]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[80]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[81]}" /></td>
						        <td><c:out value="${pbplistForm.totalValues[82]}" /></td>
						        <td><c:out value="${pbplistForm.totalValues[83]}" /></td>
						        <td><c:out value="${pbplistForm.totalValues[84]}" /></td>
						        <td><c:out value="${pbplistForm.totalValues[85]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[86]}" /></td>   
							    <td><c:out value="${pbplistForm.totalValues[87]}" /></td> 
							    <td><c:out value="${pbplistForm.totalValues[88]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[89]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[90]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[91]}" /></td>
						        <td><c:out value="${pbplistForm.totalValues[92]}" /></td>
						        <td><c:out value="${pbplistForm.totalValues[93]}" /></td>
						        <td><c:out value="${pbplistForm.totalValues[94]}" /></td>
						        <td><c:out value="${pbplistForm.totalValues[95]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[96]}" /></td>   
							    <td><c:out value="${pbplistForm.totalValues[97]}" /></td> 
							    <td><c:out value="${pbplistForm.totalValues[98]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[99]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[100]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[101]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[102]}" /></td>
							    <td><c:out value="${pbplistForm.totalValues[103]}" /></td>
							  </tr>
							</c:if>		
								</table>

								<table width="100%" style="margin-top: 5px;">
									<tr>
										<td></td>
										<td align="right">
											<div>
												共有记录&nbsp;
												<c:out value="${pbplistForm.totalRowCount}"></c:out>
												&nbsp;条&nbsp;&nbsp;&nbsp;&nbsp; [
												<a href="JavaScript:turnToPage(document.forms[0],0)">首页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],1)">上一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],2)">下一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],3)"> 末页</a>]
												页数:
												<c:out value="${pbplistForm.intPage}" />
												/
												<c:out value="${pbplistForm.pageCount}" />
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

	</body>
</html>
