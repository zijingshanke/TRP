<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%
String path = request.getContextPath();
%>
<html>
	<head>
		<title>main</title>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />
		<script src="../_js/base/FormUtil.js" type="text/javascript"></script>
		<style>
#divSearchBarTool {
	margin-bottom: 5px;
}

#divProblemCompareList1 {
	float: left;
	width: 700px;
}

#divProblemCompareList2 {
	margin-left: 700px;
}
</style>
	<script type="text/javascript">
		
	</script>
	</head>
	<body>
		<div id="divSearchBarTool">
			<html:form action="/transaction/reportCompareResultList.do">
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
								<c:param name="title1" value="报表对比管理" />
								<c:param name="title2" value="查看报表对比结果" />
							</c:import>
							<hr>
							<table width="100%" cellpadding="0" cellspacing="0" border="0"
								class="dataList">
								<tr>
									<td class="lef">
										标题
									</td>
									<td style="text-align: left">
										<c:out value="${reportCompareResult.name}" />
									</td>
									<td class="lef">
										类型
									</td>
									<td style="text-align: left">
										<c:out value="${reportCompareResult.typeInfo}" />
									</td>
									<td class="lef">
										状态
									</td>
									<td style="text-align: left">
										<c:out value="${reportCompareResult.statusInfo}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										平台
									</td>
									<td style="text-align: left">
										<c:out value="${reportCompareResult.platformName}" />
									</td>
									<td class="lef">
										支付工具
									</td>
									<td style="text-align: left">
										<c:out value="${reportCompareResult.statusInfo}" />
									</td>
									<td class="lef">
										状态
									</td>
									<td style="text-align: left">
										<c:out value="${reportCompareResult.statusInfo}" />
									</td>
								</tr>
							</table>
							<table width="100%" style="margin-top: 5px;">
								<tr>
									<td>
										<html:hidden property="thisAction" name="reportCompareResult" />
										<input name="label" type="button" class="button1" value="返 回"
											onclick="window.history.back();">
									</td>
								</tr>
							</table>
						</td>
						<td width="10" class="tbrr"></td>
					</tr>
					<tr>
				</table>
			</html:form>
		</div>
		<div id="divProblemCompareList1">
			<jsp:include
				page="../report/listProblemCompare1.jsp?compareType=Platform&showOperate=Yes"></jsp:include>
		</div>
		<div id="divProblemCompareList2">
			<jsp:include
				page="../report/listProblemCompare2.jsp?compareType=Platform&showOperate=Yes"></jsp:include>
		</div>
	</body>
</html>
