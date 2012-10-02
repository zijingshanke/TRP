<%@ page language="java" pageEncoding="utf-8"%>

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
		<script type="text/javascript" language="javascript"
			src="../_js/jquery-1.3.1.min.js"></script>
		<script src="../_js/common.js" type="text/javascript"></script>
		<script src="../_js/popcalendar.js" type="text/javascript"></script>
		<script type="text/javascript">
			function thisExport()
			{
				document.forms[0].action="<%=path %>/transaction/accountList.do?thisAction=downloadAccountBalance";
				document.forms[0].submit();
			}
		</script>

	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
				<html:form action="/transaction/accountList.do?thisAction=listAccountBanlance" method="post">
					<html:hidden property="thisAction" />
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
								<c:import url="../_jsp/mainTitle.jsp?title1=结算管理&title2=帐号余额查询"
									charEncoding="UTF-8" />
								<br />
								
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
												账号
											</div>
										</th>
										<th>
											<div>
												余额
											</div>
										</th>
										<th>
											<div>
												未结款
											</div>
										</th>
										<th>
											<div>
												现返佣金
											</div>
										</th>
										<th>
											<div>
												后返佣金
											</div>
										</th>										
										
									</tr>
									<c:forEach var="acc" items="${accountListForm.list}" varStatus="sta">
										<tr>
											<td>
												<c:out value="${sta.count+(accountListForm.intPage-1)*accountListForm.perPageNum}" />
											</td>
											<td>
												<a href="<%=path %>/transaction/accountList.do?thisAction=viewAccountPage&accountId=<c:out value="${acc.id}" />">
													<c:out value="${acc.name}" />
												</a>
											</td>
											<td>
												<c:out value="${acc.actualAmount}" />
											</td>
											<td>
												<c:out value="${acc.unsettledAccount}" />
											</td>
											<td>
												<c:out value="${acc.commission}" />
											</td>											
											<td>
												<c:out value="${acc.rakeOff}" />
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
												<c:out value="${accountListForm.totalValue2}" />
											</td>
											<td>
												<c:out value="${accountListForm.totalValue3}" />
											</td>
											<td>
												<c:out value="${accountListForm.totalValue4}" />
											</td>	
											<td>
												<c:out value="${accountListForm.totalValue5}" />
											</td>										
											
										</tr>
								</table>
								<br />
									<table cellpadding="0" cellspacing="0" border="0" class="searchPanel">
										<tr>
											<td>
												<input type="button" class="button1" value="导 出" onclick="thisExport()"/>
											</td>
										</tr>
									</table>
								<table width="100%" style="margin-top: 5px;">
									<tr>

										<td align="right">
											<div>
												共有记录&nbsp;
												<c:out value="${accountListForm.totalRowCount}"></c:out>
												&nbsp;条&nbsp;&nbsp;&nbsp;&nbsp; [
												<a href="JavaScript:turnToPage(document.forms[0],0)">首页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],1)">上一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],2)">下一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],3)"> 末页</a>]
												页数:
												<c:out value="${accountListForm.intPage}" />
												/
												<c:out value="${accountListForm.pageCount}" />
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
