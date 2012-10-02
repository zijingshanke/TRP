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
<c:if test="${!empty reportCompareList}">已上传文件
	<table cellpadding="0" cellspacing="0" border="0" class="dataList">
		<th>
			<div>
				&nbsp;序号
			</div>
		</th>
		<%
		if (compareType.equals("Platform") || compareType.equals("BSP") || compareType.equals("Network")|| compareType.equals("Bank")) {
		%>
		<th>
			<div>
				预定编码
			</div>
		</th>
		<%
		}
		%>
		<%
		if (compareType.equals("Platform")) {
		%>
		<th>
			<div>
				平台订单号
			</div>
		</th>
		<th>
			<div>
				收款账号
			</div>
		</th>
		<th>
			<div>
				收款金额
			</div>
		</th>
		<%
		}
		%>

		<%
		if (compareType.equals("BSP") || compareType.equals("Network")) {
		%>
		<th>
			<div>
				票号
			</div>
		</th>
		<th>
			<div>
				运价
			</div>
		</th>
		<th>
			<div>
				税款总价
			</div>
		</th>
		<th>
			<div>
				付款金额
			</div>
		</th>
		<th>
			<div>
				利润
			</div>
		</th>
		<%
		}
		%>
		
			<%
		if (compareType.equals("Bank")) {
		%>
		<th>
			<div>
				商户订单号
			</div>
		</th>
		<th>
			<div>
				收入
			</div>
		</th>
		<th>
			<div>
				支出
			</div>
		</th>
		<%
		}
		%>
		<%
			if (compareType.equals("Platform") || compareType.equals("BSP") || compareType.equals("Network")) {
		%>
		<th>
			<div>
				人数
			</div>
		</th>
			<%
		}
		%>
		<c:forEach var="reportCompare" items="${reportCompareList}"
			varStatus="status">
			<tr>
				<td>
					<c:out value="${status.count}" />	
				</td>
				<%
				if (compareType.equals("Platform") || compareType.equals("BSP") || compareType.equals("Network") || compareType.equals("Bank")) {
				%>
				<td>
					<c:out value="${reportCompare.subPnr}" />
				</td>
				<%
				}
				%>

				<%
				if (compareType.equals("Platform")) {
				%>
				<td>
					<c:out value="${reportCompare.airOrderNo}" />
				</td>
				<td>
					<c:out value="${reportCompare.inAccountName}" />
				</td>
				<td>
					<c:out value="${reportCompare.inAmount}" />
				</td>
				<%
				}
				%>

				<%
				if (compareType.equals("BSP") || compareType.equals("Network")) {
				%>
				<td>
					<c:out value="${reportCompare.ticketNumber}" />
				</td>
				<td>
					<c:out value="${reportCompare.ticketPrice}" />
				</td>
				<td>
					<c:out value="${reportCompare.totalAirportFuelPrice}" />
				</td>
				<td>
					<c:out value="${reportCompare.outAmount}" />
				</td>
				<td>
					<c:out value="${reportCompare.drawProfits}" />
				</td>
				<%
				}
				%>
					<%
				if (compareType.equals("Bank")) {
				%>
				<td>
					<c:out value="${reportCompare.airOrderNo}" />
				</td>
				<td>
					<c:out value="${reportCompare.inAmount}" />
				</td>
				<td>
					<c:out value="${reportCompare.outAmount}" />
				</td>
				<%
				}
				%>
				<td>
					<c:out value="${reportCompare.passengerCount}" />
				</td>
			</tr>
		</c:forEach>
		<tr>
		<td>
			<c:out value="${totalReportCompare.totalRowNum}"></c:out>
		</td>
		<%
		if (compareType.equals("Platform") || compareType.equals("BSP") || compareType.equals("Network")|| compareType.equals("Bank")) {
		%>
		<td>
		</td>
		<%
		}
		%>
		<%
		if (compareType.equals("Platform")) {
		%>
		<td>
		</td>
		<td>
		</td>
		<td>
			<c:out value="${totalReportCompare.totalInAmount}"></c:out>
		</td>
		<%
		}
		%>

		<%
		if (compareType.equals("BSP") || compareType.equals("Network")) {
		%>
		<td>
			
		</td>
		<td>
			
		</td>
		<td>
			
		</td>
		<td>
			<c:out value="${totalReportCompare.totalOutAmount}"></c:out>
		</td>
		<td>
		
		</td>
		<%
		}
		%>
		
		<%
			if (compareType.equals("Bank")) {
		%>
		<td>			
		</td>
		<td>
			<c:out value="${totalReportCompare.totalInAmount}"></c:out>
		</td>
		<td>
			<c:out value="${totalReportCompare.totalOutAmount}"></c:out>
		</td>
		<%
		}
		%>
		<%
			if (compareType.equals("Platform") || compareType.equals("BSP") || compareType.equals("Network")) {
		%>
		<td>
			<c:out value="${totalReportCompare.totalPassengerCount}"></c:out>
		</td>
		<%
		}
		%>
		</tr>
	</table>
</c:if>