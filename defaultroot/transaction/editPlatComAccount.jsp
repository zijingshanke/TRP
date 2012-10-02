
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
		
		<script type='text/javascript' src='<%=path %>/dwr/interface/platComAccountStore.js'></script>
 		 <script type='text/javascript' src='<%=path %>/dwr/engine.js'></script>
		<script type='text/javascript' src='<%=path %>/dwr/util.js'></script>
		<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
		

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
		function addPlatComAccount()
		{				
			var platformId = document.forms[0].platformId.value;
			var companyId = document.forms[0].companyId.value;
			var accountId = document.forms[0].accountId.value;
			platComAccountStore.getPlatComAccountListbyAllId(platformId,companyId,accountId,getData);		
		}		
		function getData(data)
		{
			var thisAction =document.forms[0].thisAction.value;	
			if(thisAction == "savePlatComAccount")
			{
				if(data != "")
				{
					alert("你选择的数据已经存在,请重新选择!");
					return false;
				}else
				{
				    document.forms[0].action="<%=path %>/transaction/platComAccount.do?thisAction="+thisAction;
				    document.forms[0].submit();
				}
			}else if(thisAction == "updatePlatComAccount")
			{
				document.forms[0].action="<%=path %>/transaction/platComAccount.do?thisAction="+thisAction;
				document.forms[0].submit();
			}else
			{
				return false;
			}
		}
	
		
	</script>
	
	<body>
		<html:form action="/transaction/platComAccount.do" method="post">			
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
											交易平台名称:
										</td>
										<td style="text-align: left">																
											<html:select property="platformId" name="platComAccount" styleClass="colorblue2 p_5"
												style="width:200px;">
												<c:forEach items="${platformList}" var="pla">
													<html:option value="${pla.id}"><c:out value="${pla.name}"/></html:option>
												</c:forEach>
											</html:select>											
										</td>
									</tr>								
									<tr>
										<td class="lef">
											公司名称:
										</td>
										<td style="text-align: left">													
											<html:select property="companyId" name="platComAccount" styleClass="colorblue2 p_5"
												style="width:200px;">
												<c:forEach items="${companyList}" var="com">
													<html:option value="${com.id}"><c:out value="${com.name}"/></html:option>
												</c:forEach>
											</html:select>											
										</td>
									</tr>									
									<tr>
										<td class="lef">
											支付名称:
										</td>
										<td style="text-align: left">	
											<html:hidden property="id" value="${platComAccount.id}"></html:hidden>													
											<html:select property="accountId" name="platComAccount" styleClass="colorblue2 p_5"
												style="width:200px;">
												<c:forEach items="${accountList}" var="acc">
													<html:option value="${acc.id}"><c:out value="${acc.name}"/></html:option>
												</c:forEach>
											</html:select>											
										</td>
									</tr>	
									<tr>
										<td class="lef">
											类型
										</td>
										<td style="text-align: left">
											
											<html:select property="type" name="platComAccount" styleClass="colorblue2 p_5"
												style="width:150px;">
												<html:option value="1">卖出收款</html:option>
												<html:option value="2">买入付款</html:option>	
												<html:option value="3">收付</html:option>											
											</html:select>
										</td>									
									</tr>
									<tr>
										<td class="lef">
											状态
										</td>
										<td style="text-align: left">
											
											<html:select property="status" name="platComAccount" styleClass="colorblue2 p_5"
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
										<html:hidden property="thisAction" name="platComAccount"/>							
										<input name="label" type="button" class="button1" value="提交"
												onclick="addPlatComAccount();">									
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


