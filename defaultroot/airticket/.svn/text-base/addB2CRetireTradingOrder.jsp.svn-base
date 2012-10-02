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
								<c:import url="../_jsp/mainTitle.jsp?title1=票务管理&title2=B2C退废订单录入"
									charEncoding="UTF-8" />

								<div class="searchBar">
									<table cellpadding="0" cellspacing="0" border="0"
										class="searchPanel">
										<tr>
											<td>
												PNR:
												<html:text property="userNo" styleClass="colorblue2 p_5"
													style="width:120px;" />
											</td>
											<td>
												<input type="submit" name="button" id="button" value="导入"
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
												航班日期
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
												乘客
											</div>
										</th>
										<th>
											<div>
												票号
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
									</tr>
								</table>
								<hr />
								<table width="100%" cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td>
											平台
											<html:select property="userNo" styleClass="colorblue2 p_5"
												style="width:150px;" />
										</td>
										<td>
											公司
											<html:select property="userNo" styleClass="colorblue2 p_5"
												style="width:150px;" />
										</td>
										<td>
											收款方式（未确定）(欠款)
											<html:select property="userNo" styleClass="colorblue2 p_5"
												style="width:150px;" />
										</td>
										<td>
											客户
											<html:select property="userNo" styleClass="colorblue2 p_5"
												style="width:150px;" />
										</td>
									</tr>
									<tr>
										<td>
											政策
											<html:text property="userNo" styleClass="colorblue2 p_5"
												style="width:150px;" />
										</td>
										<td>
											金额
											<html:text property="userNo" styleClass="colorblue2 p_5"
												style="width:150px;" />
										</td>
										<td>
											大PNR(为什么还要手输？)
											<html:text property="userNo" styleClass="colorblue2 p_5"
												style="width:150px;" />
										</td>
									</tr>
									<tr>
										<td>
											是否打印行程单
											<html:multibox property="selectedItems"
												value="${info.userId}"></html:multibox>
										</td>
										<td>
											行程单费用
											<html:text property="userNo" styleClass="colorblue2 p_5"
												style="width:150px;" />
										</td>
										<td>
											保险费
											<html:text property="userNo" styleClass="colorblue2 p_5"
												style="width:150px;" />
										</td>
										<td>
											<input name="label" type="button" class="button1" value="创 建"
												onclick="addUser();">
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
