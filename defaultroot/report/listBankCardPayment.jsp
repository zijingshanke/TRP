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
		<script type='text/javascript'
			src='<%=path%>/dwr/interface/platComAccountStore.js'></script>
		<script type='text/javascript' src='<%=path%>/dwr/engine.js'></script>
		<script type='text/javascript' src='<%=path%>/dwr/util.js'></script>
		<script src="../_js/common.js" type="text/javascript"></script>
		<script src="../_js/popcalendar.js" type="text/javascript"></script>
		<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
		
		<script type="text/javascript">
			function download()
			{
				document.forms[0].action="<%=path %>/transaction/bankCardPaymentList.do?thisAction=downloadBankCardPayment";
				document.forms[0].submit();
			}
		</script>
		
	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
				<html:form action="/transaction/bankCardPaymentList.do?thisAction=list">
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
								<c:import url="../_jsp/mainTitle.jsp?title1=报表管理&title2=银行卡付款报表"
									charEncoding="UTF-8" />

								<div class="searchBar">
									<table cellpadding="0" cellspacing="0" border="0"
										class="searchPanel">
										<tr>
											<td>
												操作员
												<html:text property="sysName" styleClass="colorblue2 p_5"
													style="width:100px;" />
											</td>
											<td>
												选择日期:
												<html:text property="banlanceDate"
													styleClass="colorblue2 p_5" style="width:150px;"
													onclick="popUpCalendar(this, this);" readonly="true" />
												__
												<html:text property="banlanceDate"
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
										<th>
											<div>
												序号
											</div>
										</th>
										<th>
											<div>
												操作人
											</div>
										</th>
										<th>
											<div>
												团队收款(系统)
											</div>
										</th>
										<th>
											<div>
												团队付款(系统)
											</div>
										</th>
										<th>
											<div>
												支付宝5261
											</div>
										</th>
										<th>
											<div>
												小计
											</div>
										</th>
										<th>
											<div>
												总计
											</div>
										</th>
									</tr>

									<c:forEach var="ba" items="${bankCardPaymentListForm.list}"
										varStatus="sta">
										<tr>
											<td>
												<c:out
													value="${sta.count+(bankCardPaymentListForm.intPage-1)*bankCardPaymentListForm.perPageNum}" />
											</td>
											<td>
												<c:out value="${ba.userName}"></c:out>
											</td>
											<td>
												<c:out value="${ba.account1}"></c:out>
											</td>
											<td>
												<c:out value="${ba.account2}"></c:out>
											</td>
											<td>
												<c:out value="${ba.account3}"></c:out>
											</td>
											<td>
												<c:out value="${ba.subtotal}"></c:out>
											</td>
											<td>
												<c:out value="${ba.total}"></c:out>
											</td>

										</tr>
									</c:forEach>
									<tr>
										<td>
											<div align="center">
												<font>合计</font>
											</div>
										</td>
										<td>
											&nbsp;
										</td>
										<td>
											<c:out value="${bankCardPaymentListForm.totalValue1}" />
										</td>
										<td>
											<c:out value="${bankCardPaymentListForm.totalValue2}" />
										</td>
										<td>
											<c:out value="${bankCardPaymentListForm.totalValue3}" />
										</td>
										<td>
											<c:out value="${bankCardPaymentListForm.totalValue4}" />
										</td>
										<td>
											<c:out value="${bankCardPaymentListForm.totalValue5}" />
										</td>

									</tr>
								</table>

								<table width="100%" style="margin-top: 5px;">
									<tr>
										<td></td>
										<td align="right">
											<div>
												共有记录&nbsp;
												<c:out value="${bankCardPaymentListForm.totalRowCount}"></c:out>
												&nbsp;条&nbsp;&nbsp;&nbsp;&nbsp; [
												<a href="JavaScript:turnToPage(document.forms[0],0)">首页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],1)">上一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],2)">下一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],3)"> 末页</a>]
												页数:
												<c:out value="${bankCardPaymentListForm.intPage}" />
												/
												<c:out value="${bankCardPaymentListForm.pageCount}" />
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
