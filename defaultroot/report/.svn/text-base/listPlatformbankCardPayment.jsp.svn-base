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
				<html:form action="/transaction/platformbankCardPaymentList.do?thisAction=getPlatformbankCardPaymentlist">
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
										<th colspan="2"><div>散客-B2C</div></th>
										
										<th colspan="2"><div>珠海品尚物流平台</div></th>
										
										
										<th colspan="2"><div>珠海品尚物流平台</div></th>
										
										<th colspan="2"><div>散客-B2C</div></th>
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
							    <td><c:out value="${pbplistForm.totalValue1}" /></td>
						        <td><c:out value="${pbplistForm.totalValue2}" /></td>
						        <td><c:out value="${pbplistForm.totalValue3}" /></td>
						        <td><c:out value="${pbplistForm.totalValue4}" /></td>
						        <td><c:out value="${pbplistForm.totalValue5}" /></td>
							    <td><c:out value="${pbplistForm.totalValue6}" /></td>   
							    <td><c:out value="${pbplistForm.totalValue7}" /></td> 
							    <td><c:out value="${pbplistForm.totalValue8}" /></td>
							    <td></td>
							    <td></td>
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
