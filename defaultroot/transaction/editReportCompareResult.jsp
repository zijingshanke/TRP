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
	</head>
	<script type="text/javascript">	
		function add(){	
			var name=document.forms[0].name.value;
			if(name==""){
				alert("请输入支付帐号名称!")
				return false;
			}
			var thisAction =document.forms[0].thisAction;	
			if(thisAction==null){
				alert("thisAction不能为空")
				return false;
			}else{
				if(thisAction.value==''){
					alert("thisAction不能为空")
					return false;
				}
			 	document.forms[0].action="<%=path%>/transaction/platformReportIndex.do?thisAction="+thisAction.value;
		    	document.forms[0].submit();
			} 
		}
	</script>
	<body>
		<c:import url="../_jsp/mainTitle.jsp" charEncoding="UTF-8">
			<c:param name="title1" value="报表对比管理" />
			<c:param name="title2" value="编辑报表对比结果" />
		</c:import>
		<html:form action="/transaction/reportCompareResult.do" method="post">
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
								<table cellpadding="0" cellspacing="0" border="0"
									class="dataList">
									<tr>
										<td class="lef">
											对比类型
										</td>
										<td style="text-align: left">
											<html:select property="compareType"
												name="reportCompareResult" styleClass="colorblue2 p_5"
												style="width:100px;">
												<html:option value="0">-请选择-</html:option>
												<html:option value="1">平台报表</html:option>
												<html:option value="2">BSP网电</html:option>
												<html:option value="3">银行/支付平台</html:option>
											</html:select>
										</td>

										<td class="lef">
											交易类型
										</td>
										<td style="text-align: left">
											<html:select property="type" name="reportCompareResult"
												styleClass="colorblue2 p_5" style="width:100px;">
												<html:option value="0">-请选择-</html:option>
												<html:option value="1">--供应--</html:option>
												<html:option value="2">--采购--</html:option>
												<html:option value="13">供应退废</html:option>
												<html:option value="14">采购退废</html:option>
												<html:option value="15">供应退票</html:option>
												<html:option value="16">采购退票</html:option>
												<html:option value="17">供应废票</html:option>
												<html:option value="18">采购废票</html:option>
											</html:select>
										</td>
										<td class="lef">
											交易平台
										</td>
										<td style="text-align: left">
											<html:select property="platformId" name="reportCompareResult"
												value="${reportCompareResult.platformId}">
												<html:option value="0">-请选择-</html:option>
												<c:forEach items="${platformList}" var="platform">
													<html:option value="${platform.id}">
														<c:out value="${platform.showName}" />
													</html:option>
												</c:forEach>
											</html:select>
										</td>
										<td class="lef" style="display: none">
											账号
										</td>
										<td style="text-align: left" style="display: none">
											<html:select property="accountId" name="reportCompareResult"
												value="${reportCompareResult.accountId}">
												<html:option value="0">-请选择-</html:option>
												<c:forEach items="${accountList}" var="account">
													<html:option value="${account.id}">
														<c:out value="${account.showName}" />
													</html:option>
												</c:forEach>
											</html:select>
										</td>
										<td class="lef">
											银行/支付平台
										</td>
										<td style="text-align: left">
											<html:select property="paymentToolId"
												name="reportCompareResult"
												value="${reportCompareResult.paymentToolId}">
												<html:option value="0">-请选择-</html:option>
												<c:forEach items="${paymentToolList}" var="paymentTool">
													<html:option value="${paymentTool.id}">
														<c:out value="${paymentTool.showName}" />
													</html:option>
												</c:forEach>
											</html:select>
										</td>
									</tr>
									<tr>
										<td class="lef">
											状态
										</td>
										<td style="text-align: left">

											<html:select property="status" name="platformReportIndex"
												styleClass="colorblue2 p_5" style="width:50px;">
												<html:option value="1">有效</html:option>
												<html:option value="0">无效</html:option>
											</html:select>
										</td>
									</tr>
								</table>
								<table width="100%" style="margin-top: 5px;">
									<tr>
										<td>
											<html:hidden property="thisAction" name="reportCompareResult" />
											<input name="label" type="button" class="button1" value="提交"
												onclick="add();">
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