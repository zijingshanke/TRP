<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<html>
	<head>
		<title>泰申管理系统-政策管理</title>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />
		<script src="../_js/common.js" type="text/javascript"></script>
		<script type="text/javascript" language="javascript">
			function addForum(){
			    document.forms[0].submit();
			}
		</script>
	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
				<html:form action="/user/user.do">
					<html:hidden property="thisAction" name="user" />
					<table width="100%" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td width="10" height="10" class="tblt"></td>
							<td height="10" class="tbtt"></td>
							<td width="10" height="10" class="tbrt"></td>
						</tr>
						<tr>
							<td width="10" class="tbll"></td>
							<td valign="top" class="body">
								<div class="crumb">
									<c:import
										url="../_jsp/mainTitle.jsp?title1=票务政策管理&title2=航空公司政策录入"
										charEncoding="UTF-8" />
								</div>
								<hr>
								<table width="100%" cellpadding="0" cellspacing="0" border="0"
									class="dataList">
									<tr>
										<td class="lef">
											承运人
										</td>
										<td style="text-align: left">
											<html:text property="userNo" name="user"
												styleClass="colorblue2 p_5" />
										</td>
									</tr>
									<tr>
										<td class="lef">
											登录地址
										</td>
										<td style="text-align: left">
											<html:text property="userNo" name="user"
												styleClass="colorblue2 p_5" />
										</td>
									</tr>
									<tr>
										<td class="lef">
											协议
										</td>
										<td style="text-align: left">
											<html:select property="userNo" name="user">
												<html:option value="80">HTTP</html:option>
												<html:option value="443">HTTPS</html:option>
											</html:select>
										</td>
									</tr>
									<tr>
										<td class="lef">
											状态
										</td>
										<td style="text-align: left">
											<html:radio property="userNo" value="1" name="user">有效</html:radio>
											<html:radio property="userNo" value="2" name="user">无效</html:radio>
										</td>
									</tr>
								</table>
								<table width="100%" style="margin-top: 5px;">
									<tr>
										<td>
											<input name="label" type="button" class="button1" value="保存"
												onclick="adduser();">
											<input name="label" type="button" class="button1" value="重置"
												onclick="document.user.reset();">
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
