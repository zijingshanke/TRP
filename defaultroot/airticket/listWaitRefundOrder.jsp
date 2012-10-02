<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="utf-8"%>

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
				<html:form action="/system/loginloglist.do?thisAction=list">
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
								<c:import url="../_jsp/mainTitle.jsp?title1=票务管理&title2=等待退款订单"
									charEncoding="UTF-8" />

								<div class="searchBar" style="display: none;">
									<table cellpadding="0" cellspacing="0" border="0"
										class="searchPanel">
										<tr>
											<td>
												承运人
												<html:select property="userNo" styleClass="colorblue2 p_5"
													style="width:120px;" />
											</td>
											<td>
												出票PNR
												<html:text property="userNo" styleClass="colorblue2 p_5"
													style="width:120px;" />
											</td>
											<td>
												预定PNR
												<html:text property="userNo" styleClass="colorblue2 p_5"
													style="width:120px;" />
											</td>
											<td>
												大PNR
												<html:text property="userNo" styleClass="colorblue2 p_5"
													style="width:120px;" />
											</td>
											<td>
												订单号
												<html:text property="userNo" styleClass="colorblue2 p_5"
													style="width:150px;" />
											</td>
											<td>
												操作人
												<html:text property="userNo" styleClass="colorblue2 p_5"
													style="width:100px;" />
											</td>
										</tr>
										<tr>
											<td>
												航班号
												<html:text property="userNo" styleClass="colorblue2 p_5"
													style="width:120px;" />
											</td>
											<td>
												乘客
												<html:text property="userNo" styleClass="colorblue2 p_5"
													style="width:150px;" />
											</td>
											<td>
												票号
												<html:text property="userNo" styleClass="colorblue2 p_5"
													style="width:150px;" />
											</td>
											<td>
												开始:
												<html:text property="fromDate" styleClass="colorblue2 p_5"
													style="width:150px;" onclick="popUpCalendar(this, this);"
													readonly="true" />
											</td>
											<td>
												结束
												<html:text property="toDate" styleClass="colorblue2 p_5"
													style="width:150px;" onclick="popUpCalendar(this, this);"
													readonly="true" />
											</td>
											<td>
												最近
												<html:select property="userNo" styleClass="colorblue2 p_5"
													style="width:120px;" />
											</td>
										</tr>
										<tr>
											<td>
												买入
												<html:select property="userNo" styleClass="colorblue2 p_5"
													style="width:150px;" />
											</td>
											<td>
												付款
												<html:select property="userNo" styleClass="colorblue2 p_5"
													style="width:150px;" />
											</td>
											<td>
												卖出
												<html:select property="userNo" styleClass="colorblue2 p_5"
													style="width:150px;" />
											</td>
											<td>
												收款
												<html:select property="userNo" styleClass="colorblue2 p_5"
													style="width:150px;" />
											</td>
											<td>
												状态
												<html:select property="userNo" styleClass="colorblue2 p_5"
													style="width:150px;" />
											</td>
											<td>
												<input type="submit" name="button" id="button" value="提交"
													class="submit greenBtn" />
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
												承运人
											</div>
										</th>
										<th>
											<div>
												航班号
											</div>
										</th>
										<th>
											<div>
												行程
											</div>
										</th>
										<th>
											<div>
												出票PNR
											</div>
										</th>
										<th>
											<div>
												乘客
											</div>
										</th>
										<th>
											<div>
												票号
											</div>
										</th>
										<th>
											<div>
												票面价
											</div>
										</th>
										<th>
											<div>
												机建
											</div>
										</th>
										<th>
											<div>
												燃油
											</div>
										</th>
										<th>
											<div>
												操作日期
											</div>
										</th>
										<th>
											<div>
												平台
											</div>
										</th>
										<th>
											<div>
												预定PNR
											</div>
										</th>
										<th>
											<div>
												出票PNR
											</div>
										</th>
										<th>
											<div>
												大PNR
											</div>
										</th>
										<th>
											<div>
												政策
											</div>
										</th>
										<th>
											<div>
												帐号
											</div>
										</th>
										<th>
											<div>
												交易状态
											</div>
										</th>
										<th>
											<div>
												操作人
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

											<c:out value="33" />
										</td>
										<td>

										</td>
										<td>

										</td>
										<td>

										</td>
										<td>
										</td>
										<td>
										</td>
										<td>
										</td>
										<td>
										</td>
										<td>
										</td>
										<td>
										</td>
										<td>
										</td>
										<td>
											dd
										</td>
										<td>
											dd
										</td>
										<td>
											dd
										</td>
										<td>
											dd
										</td>
										<td>
											dd
										</td>
										<td>
											dd
										</td>
										<td>
											dd
										</td>
										<td>
											<a href="#">[确认退款]</a>
											<br>
											<a href="#">[取消退款]</a>
										</td>
									</tr>

								</table>

								<table width="100%" style="margin-top: 5px;">
									<tr>
										<td></td>
										<td align="right">
											<div>
												共有记录&nbsp;
												<c:out value="${lllf.totalRowCount}"></c:out>
												&nbsp;条&nbsp;&nbsp;&nbsp;&nbsp; [
												<a href="JavaScript:turnToPage(document.forms[0],0)">首页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],1)">上一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],2)">下一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],3)"> 末页</a>]
												页数:
												<c:out value="${lllf.intPage}" />
												/
												<c:out value="${lllf.pageCount}" />
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
