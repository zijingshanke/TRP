
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
			function addCompanyList()
		{	
			var name=document.forms[0].name.value;
			if(name=="")
			{
				alert("请输入公司名称!")
				return false;
			}
			var thisAction =document.forms[0].thisAction.value;			   
		     document.forms[0].action="<%=path %>/transaction/company.do?thisAction="+thisAction;
		    document.forms[0].submit();
		}
	
		
	</script>
	
	<body>
		<html:form action="/transaction/company.do" method="post">			
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
											公司名称
										</td>
										<td style="text-align: left">										
											<html:text property="name" name="company" value="${company.showName}" styleClass="colorblue2 p_5"
												style="width:200px;"></html:text>
											<html:hidden property="id" value="${company.id}"></html:hidden>
											<html:hidden property="type" value="${company.type}"></html:hidden>
										</td>
									</tr>
									<tr>
										<td class="lef">
											状态
										</td>
										<td style="text-align: left" >
											
											<html:select property="status" name="company" styleClass="colorblue2 p_5"
												style="width:50px;">
												<html:option value="0">有效</html:option>
												<html:option value="1">无效</html:option>											
											</html:select>
										</td>									
									</tr>
							</table>
							<table width="100%" style="margin-top: 5px;">
								<tr>
									<td>	
										<html:hidden property="thisAction" name="company"/>							
										<input name="label" type="button" class="button1" value="提交"
												onclick="addCompanyList();">									
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


