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
				document.forms[0].action="<%=path %>/transaction/optTransactionList.do?thisAction=downloadBankCardPayment";
				document.forms[0].submit();
			}
		</script>
		
	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
				<html:form action="/transaction/optTransactionList.do?thisAction=list">
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
								<c:import url="../_jsp/mainTitle.jsp?title1=报表管理&title2=操作员收付款统计报表"
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
												卖出订单总数
											</div>
										</th>
										<th>
											<div>
												正常订单
											</div>
										</th>
										<th>
											<div>
												改签订单
											</div>
										</th>
										<th>
											<div>
												退票订单
											</div>
										</th>
										<th>
											<div>
												废票订单
											</div>
										</th>
										<th>
											<div>
												取消订单
											</div>
										</th>
										<th>
											<div>
												卖出机票数量
											</div>
										</th>
										<th>
											<div>
												收款金额
											</div>
										</th>
										<th>
											<div>
												付款金额
											</div>
										</th>
										<th>
											<div>
												利润
											</div>
										</th>
										<th>
											<div>
												收退款金额
											</div>
										</th>
										<th>
											<div>
												付退款金额
											</div>
										</th>
										<th>
											<div>
												取消出票收款
											</div>
										</th>
										<th>
											<div>
												取消出票退款
											</div>
										</th>
										
									</tr>

									<c:forEach var="o" items="${otf.list}"
										varStatus="sta">
										<tr>
											<td>
												<c:out
													value="${sta.count+(otf.intPage-1)*otf.perPageNum}" />
											</td>
											<td>
												<c:out value="${o.userName}"></c:out>
											</td>
											<td>
												<c:out value="${o.sellorderstotal}"></c:out>
											</td>
											<td>
												<c:out value="${o.normalorder}"></c:out>
											</td>
											<td>
												<c:out value="${o.alteredorder}"></c:out>
											</td>
											<td>
												<c:out value="${o.refundorder}"></c:out>
											</td>
											<td>
												<c:out value="${o.invalidorder}"></c:out>
											</td>
											<td>
												<c:out value="${o.cancelorder}"></c:out>
											</td>
											<td>
												<c:out value="${o.soldticketCount}"></c:out>
											</td>
											<td>
												<c:out value="${o.inamount}"></c:out>
											</td>
											<td>
												<c:out value="${o.outamount}"></c:out>
											</td>
											<td>
												<c:out value="${o.profit}"></c:out>
											</td>
											<td>
												<c:out value="${o.refundamountreceived}"></c:out>
											</td>
											<td>
												<c:out value="${o.refundamountpaid}"></c:out>
											</td>
											<td>
												<c:out value="${o.cancelticketcollection}"></c:out>
											</td>
											<td>
												<c:out value="${o.cancelticketrefund}"></c:out>
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
											&nbsp;
										</td>
										<td>
											&nbsp;
										</td>
										<td>
											&nbsp;
										</td>
										<td>
											&nbsp;
										</td>
										<td>
											&nbsp;
										</td>
										<td>
											&nbsp;
										</td>
										<td>
											&nbsp;
										</td>
										<td>
											<c:out value="${otf.totalValue1}" />
										</td>
										<td>
											<c:out value="${otf.totalValue2}" />
										</td>
										<td>
											<c:out value="${otf.totalValue3}" />
										</td>
										<td>
											<c:out value="${otf.totalValue4}" />
										</td>
										<td>
											<c:out value="${otf.totalValue5}" />
										</td>
										<td>
											<c:out value="${otf.totalValue6}" />
										</td>
										<td>
											<c:out value="${otf.totalValue7}" />
										</td>
									</tr>
								</table>

								<table width="100%" style="margin-top: 5px;">
									<tr>
										<td></td>
										<td align="right">
											<div>
												共有记录&nbsp;
												<c:out value="${otf.totalRowCount}"></c:out>
												&nbsp;条&nbsp;&nbsp;&nbsp;&nbsp; [
												<a href="JavaScript:turnToPage(document.forms[0],0)">首页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],1)">上一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],2)">下一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],3)"> 末页</a>]
												页数:
												<c:out value="${otf.intPage}" />
												/
												<c:out value="${otf.pageCount}" />
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
