﻿
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%
String path = request.getContextPath();

%>

<html>
	<head>
		<title>main</title>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />

<style>
.divstyle {
	padding: 5px;
	font-family: "MS Serif", "New York", serif;
	background: #e5e5e5;
}
</style>
	</head>
	
	<script type="text/javascript">
	
			//添加
			function addAccountList()
		{	
			var loginName=document.forms[0].loginName.value;
			var loginPwd=document.forms[0].loginPwd.value;
			if(loginName=="")
			{
				alert("请输入登录名称!")
				return false;
			}
			if(loginPwd =="")
			{
				alert("登录密码不能为空!");
				return false;
			}
			var thisAction =document.forms[0].thisAction.value;			   
		     document.forms[0].action="<%=path %>/airticket/platLoginAccount.do?thisAction="+thisAction;
		    document.forms[0].submit();
		}
	
		
	</script>
	
	<body>
		<html:form action="/airticket/platLoginAccount.do" method="post">			
		<div id="mainContainer">
			<div id="container">
				<table width="100%" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td width="10" height="10" class="tblt"></td>
						<td height="10" class="tbtt"></td>
						<td width="10" height="10" class="tbrt"></td>
					</tr>
					<tr>
						<td width="10" class="tbll"></td>
						<td valign="top" class="body">
								
							<hr>
							<table width="100%" cellpadding="0" cellspacing="0" border="0"
									class="dataList">									
																	
									<tr>
										<td class="lef">
											平台名称
										</td>
										<td style="text-align: left">										
											<html:select property="platformId" name="platLoginAccount" styleClass="colorblue2 p_5"
												style="width:200px;" >												
												<c:forEach items="${platFormList}" var="p">													
													<html:option value="${p.id}"><c:out value="${p.name}"/></html:option>
												</c:forEach>
											</html:select>
										</td>
									</tr>
									<tr>
										<td class="lef">
											登录名称
										</td>
										<td style="text-align: left">										
											<html:text property="loginName" name="platLoginAccount" value="${platLoginAccount.loginName}"  styleClass="colorblue2 p_5"
												style="width:200px;"/>
											<html:hidden property="id" value="${platLoginAccount.id}"></html:hidden>
											<html:hidden property="type" value="1"></html:hidden>
										</td>
									</tr>
									<tr>
										<td class="lef">
											登录密码
										</td>
										<td style="text-align: left">										
											<html:text property="loginPwd" name="platLoginAccount" value="${platLoginAccount.loginPwd}" styleClass="colorblue2 p_5"
												style="width:200px;" />
										</td>
									</tr>
																
									<tr>
										<td class="lef">
											状态
										</td>
										<td style="text-align: left" >
											
											<html:select property="status" name="platLoginAccount" styleClass="colorblue2 p_5"
												style="width:50px;">
												<html:option value="1">在线</html:option>	
												<html:option value="0">离线</html:option>
											</html:select>
										</td>									
									</tr>
							</table>
							<table width="100%" style="margin-top: 5px;">
								<tr>
									<td>	
										<html:hidden property="thisAction" name="platLoginAccount"/>							
										<input name="label" type="button" class="button1" value="提交"
												onclick="addAccountList();">									
										<input name="label" type="reset" class="button1" value="重 置">
										<input name="label" type="button" class="button1" value="返 回"
											onclick="window.history.back();">
									</td>

								</tr>
							</table>
							<div class="clear"></div>

						</td>
						<td width="10" class="tbrr"></td>
					</tr>
					<tr>
						<td width="10" class="tblb"></td>
						<td class="tbbb"></td>
						<td width="10" class="tbrb"></td>
					</tr>
				</table>
			</div>
		</div>
	  </html:form>
	</body>
</html>


