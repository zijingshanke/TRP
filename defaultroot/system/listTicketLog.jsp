<%@ page language="java" pageEncoding="utf-8"%>

<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />

		<script src="../_js/common.js" type="text/javascript"></script>
		<script src="../_js/popcalendar.js" type="text/javascript"></script>

	</head>

	<body>
		<div id="mainContainer">
			<div id="container">
				<html:form action="/system/ticketloglist.do?thisAction=list">
					<html:hidden property="thisAction" />
					<html:hidden property="lastAction" />
					<html:hidden property="intPage" />
					<html:hidden property="pageCount" />

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
									<c:param name="title1" value="系统管理" />
									<c:param name="title2" value="票务操作日志" />																						
							</c:import>
								<div class="searchBar">
									<p>
										搜索栏
									</p>
									<hr />
									<table cellpadding="0" cellspacing="0" border="0"
										class="searchPanel">
										<tr>
											<td>
												帐号：
											</td>
											<td>
												<html:text property="userNo" styleClass="colorblue2 p_5" />
											</td>
											<td>
												日期:
												<html:text property="fromDate" styleClass="colorblue2 p_5"
													style="width:150px;" onclick="popUpCalendar(this, this);"
													readonly="true" />
												——
											</td>
											<td>
												<html:text property="toDate" styleClass="colorblue2 p_5"
													style="width:150px;" onclick="popUpCalendar(this, this);"
													readonly="true" />
											</td>

											<td>
												<input type="submit" name="button" id="button" value="提交"
													class="submit greenBtn" />
											</td>
										</tr>
									</table>
								</div>
								<table width="100%" cellpadding="0" cellspacing="0" border="0"
									class="dataList">
									<tr>
										<th width="230">
											<div>
												操作员:
											</div>
										</th>
										<th>
											<div>
												订单号:
											</div>
										</th>
										<th>
											<div>
												订单类型:
											</div>
										</th>
										<th>
											<div>
												类型:
											</div>
										</th>
										<th>
											<div>
												IP:
											</div>
										</th>
										<th>
											<div>
												日期:
											</div>
										</th>
									</tr>
									<c:forEach var="info" items="${tllf.list}">
										<tr>
											<td>
												<c:out value="${info.sysUser.userNo}" />
											</td>
											<td>
												<c:out value="${info.orderNo}" />
											</td>
											<td>
												<c:out value="${info.orderTypeInfo}" />
											</td>
											<td>
												<c:out value="${info.typeInfo}" />
											</td>
											<td>
												<c:out value="${info.ip}" />
											</td>
											<td>
												<c:out value="${info.optTime}" />
											</td>
										</tr>
									</c:forEach>

								</table>

								<table width="100%" style="margin-top: 5px;">
									<tr>
										<td></td>
										<td align="right">
											<div>
												共有记录&nbsp;
												<c:out value="${tllf.totalRowCount}"></c:out>
												&nbsp;条&nbsp;&nbsp;&nbsp;&nbsp; [
												<a href="JavaScript:turnToPage(document.forms[0],0)">首页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],1)">上一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],2)">下一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],3)"> 末页</a>]
												页数:
												<c:out value="${tllf.intPage}" />
												/
												<c:out value="${tllf.pageCount}" />
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
