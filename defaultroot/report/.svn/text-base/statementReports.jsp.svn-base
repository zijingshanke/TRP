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
   	<script type='text/javascript' src='<%=path %>/dwr/interface/platComAccountStore.js'></script>
 	<script type='text/javascript' src='<%=path %>/dwr/engine.js'></script>
	<script type='text/javascript' src='<%=path %>/dwr/util.js'></script>
	<script src="../_js/common.js" type="text/javascript"></script>
	<script src="../_js/popcalendar.js" type="text/javascript"></script>
	<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
	</head>
	<body>	
		<div id="mainContainer">
			<div id="container">
				<html:form action="/report/listStatementReports.do?thisAction=statementReports">
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
								<c:import url="../_jsp/mainTitle.jsp?title1=付款管理&title2=银行卡付款报表"
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
												<html:text property="sysName" styleClass="colorblue2 p_5"
													style="width:150px;" onclick="popUpCalendar(this, this);"
													readonly="true" /> __
												<html:text property="sysName" styleClass="colorblue2 p_5"
													style="width:150px;" onclick="popUpCalendar(this, this);"
													readonly="true" />
											</td>
											<td>
												<input type="submit" name="button" id="button" value="提交"
													class="submit greenBtn" />
													
													<input type="button" name="button" id="button" value="导出" onclick="download()"
													class="submit greenBtn" />
											</td>
										</tr>
									</table>
									<hr />
								</div>
								<table width="100%" cellpadding="0" cellspacing="0" border="0"
									class="dataList">
									<tr>
										<th><div>操作人</div></th>
										<th><div>操作人姓名</div></th>
										<c:forEach items="${accountList}" var="acc">
											
											<th><div><c:out value="${acc.name}" /></div></th>
										</c:forEach>
										<th><div>工妙</div></th>
										<th><div>工苏</div></th>
										<th><div>工何</div></th>
										<th><div>工总</div></th>
										<th><div>工毛</div></th>
										<th><div>工行北岭支行</div></th>
										<th><div>现金</div></th>
										<th><div>易宝信用5838</div></th>
										<th><div>工行拱北支行</div></th>
										<th><div>B2C代出</div></th>
										<th><div>欠款</div></th>
										<th><div>小计</div></th>
										<th><div>总计</div></th>
									</tr>
									 
                        		<c:forEach var="sta" items="${statementForm.list}" varStatus="status">
									<tr>
									<td>
										<c:out value="${sta.sysUser.userName}"></c:out>
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
                                        &nbsp;
									</td>
									<td>
										&nbsp;
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
												<c:out value="${statementForm.totalRowCount}"></c:out>
												&nbsp;条&nbsp;&nbsp;&nbsp;&nbsp; [
												<a href="JavaScript:turnToPage(document.forms[0],0)">首页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],1)">上一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],2)">下一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],3)"> 末页</a>]
												页数:
												<c:out value="${statementForm.intPage}" />
												/
												<c:out value="${statementForm.pageCount}" />
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
