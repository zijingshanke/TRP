<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
<link href="../_css/global.css" rel="stylesheet" type="text/css" />
<script src="../_js/common.js" type="text/javascript"></script>
<%
	String path = request.getContextPath();
	String compareType = request.getParameter("compareType");
%>

<c:if test="${!empty reportRecodeList}">
	<html:form action="/transaction/reportRecodeList.do" styleId="form1">
		<html:hidden property="thisAction" />
		<html:hidden property="reportRecodeResultId" />
		<table cellpadding="0" cellspacing="0" border="0" class="dataList">
			<th>
				<div>
					<input type="checkbox" onclick="checkAll(this, 'selectedItems')"
						name="compare1" />
				</div>
			</th>
			<th>
				<div>
					序号
				</div>
			</th>
			<th>
				<div>
					预定编码
				</div>
			</th>
			<c:if test="${reportRecodeResult.reportType == 1}">
				<th>
					<div>
						平台
					</div>
				</th>
			</c:if>
			<c:if test="${reportRecodeResult.reportType == 2}">
			<th>
				<div>
					支付工具
				</div>
			</th>
			</c:if>
			<th>
				<div>
					类型
				</div>
			</th>
			<th>
				<div>
					金额
				</div>
			</th>
			<th>
				<div>
					人数
				</div>
			</th>
			<th>
				<div>
					报表名称
				</div>
			</th>
			<c:forEach var="reportRecode" items="${reportRecodeList}"
				varStatus="status">
				<tr>
					<td>
						<input type="checkbox" name="selectedItems"
							value="<c:out value='${reportRecode.id}' />"
							onclick="checkItem(this, 'compare1')" />
					</td>
					<td>
						<c:out value="${status.count}" />
					</td>
					<td>
						<c:out value="${reportRecode.subPnr}" />
					</td>
					<c:if test="${reportRecodeResult.reportType == 1}">
						<td>
							<c:out value="${reportRecode.platformName}" />
						</td>
					</c:if>
					<c:if test="${reportRecodeResult.reportType == 2}">
						<td>
							<c:out value="${reportRecode.paytoolName}" />
						</td>
					</c:if>
					<td>
						<c:out value="${reportRecode.statementTypeInfo}" />
					</td>
					<td>
						<c:out value="${reportRecode.amount}" />
					</td>
					<td>
						<c:out value="${reportRecode.passengerCount}" />
					</td>
					<td>
						<c:out value="${reportRecode.reportName}" />
					</td>					
				</tr>
			</c:forEach>
			<tr>
				<td>
					<c:out value="${totalReportRecode.totalRowNum}"></c:out>
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
					<c:out value="${totalReportRecode.totalInAmount}"></c:out>
				</td>
				<td>
					<c:out value="${totalReportRecode.totalOutAmount}"></c:out>
				</td>
				<td>
					<c:out value="${totalReportRecode.totalPassengerCount}"></c:out>
				</td>
			</tr>
		</table>
	</html:form>
</c:if>