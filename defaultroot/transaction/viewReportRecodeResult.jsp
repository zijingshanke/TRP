<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%
String path = request.getContextPath();
%>
<html>
	<head>
		<title>查看原始报表</title>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />
		<script src="../_js/common.js" type="text/javascript"></script>
		<script src="../_js/base/FormUtil.js" type="text/javascript"></script>
		<style>
		#divSearchBarTool {
			margin-bottom: 5px;
		}
		
		#divReportRecodeList {
			float: left;
			width: 700px;
		}
		</style>
	</head>
	<body>
		<div id="divSearchBarTool">
			<html:form action="/transaction/reportRecodeResultList.do">
				<html:hidden property="id" />
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
								<c:param name="title2" value="查看原始报表" />
							</c:import>
							<hr>
							<table width="100%" cellpadding="0" cellspacing="0" border="0"
								class="dataList">
								<tr>
									<td class="lef">
										报表标题
									</td>
									<td style="text-align: left">
										<c:out value="${reportRecodeResult.name}" />
									</td>
									<td class="lef">
										报表类型
									</td>
									<td style="text-align: left">
										<c:out value="${reportRecodeResult.reportTypeInfo}" />
									</td>
									<td class="lef">
										导入人
									</td>
									<td style="text-align: left">
										<c:out value="${reportRecodeResult.userNo}" />
									</td>
									<td class="lef">
										状态
									</td>
									<td style="text-align: left">
										<c:out value="${reportRecodeResult.statusInfo}" />
									</td>
									
								</tr>
								<tr>
									<td class="lef">
										开始时间
									</td>
									<td style="text-align: left">
										<c:out value="${reportRecodeResult.formatBeginDate}" />
									</td>
									<td class="lef">
										结束时间
									</td>
									<td style="text-align: left">
										<c:out value="${reportRecodeResult.formatEndDate}" />
									</td>
									<td class="lef">
										导入时间
									</td>
									<td style="text-align: left">
										<c:out value="${reportRecodeResult.formatLastDate}" />
									</td>
									<td class="lef">
									</td>
									<td style="text-align: left">
									</td>
								</tr>
								<tr>
									<td class="lef">
										导入部分
									</td>
									<td style="text-align: left" colspan="7">
										<c:forEach items="${reportRecodeResult.idNameCount}" var="idNameCount">
											<html:link
												page="/transaction/reportRecodeResultList.do
													?thisAction=view&id=${reportRecodeResult.id}&indexId=${idNameCount.key}">
													<c:forEach items="${idNameCount.value}" var="nameCount">
														<c:out value="${nameCount.key}" />(<c:out value="${nameCount.value}" />)条,
													</c:forEach>
												
											</html:link>
										</c:forEach>
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
		<div id="divReportRecodeList">
			<jsp:include page="../transaction/listReportRecode.jsp"></jsp:include>
		</div>
	</body>
</html>