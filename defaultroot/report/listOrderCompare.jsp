<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%
	String path = request.getContextPath();
	String compareType = request.getParameter("compareType");
%>

<c:if test="${!empty orderCompareList}">
	系统内符合搜索条件的订单
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
		<th>
			<div>
				人数
			</div>
		</th>
		<c:forEach var="orderCompare" items="${orderCompareList}"
			varStatus="status">
			<tr>
				<td>
					<c:out value="${status.count}" />
				</td>
				<%
				if (compareType.equals("Platform") || compareType.equals("BSP") || compareType.equals("Network") || compareType.equals("Bank")) {
				%>
				<td>
					<a
						href="<%=path%>/airticket/listAirTicketOrder.do?thisAction=view&id=<c:out value='${orderCompare.orderId}' />">
						<c:out value="${orderCompare.subPnr}" /> </a>
				</td>
				<%
				}
				%>

				<%
				if (compareType.equals("Platform")) {
				%>
				<td>
					<c:out value="${orderCompare.airOrderNo}" />
				</td>
				<td title="<c:out value='${orderCompare.inAccountNo}' />">
					<c:out value="${orderCompare.inAccountName}" />
					(
					<c:out value="${orderCompare.inAccountNo}" />
					)
				</td>
				<td>
					<c:out value="${orderCompare.inAmount}" />
				</td>
				<%
				}
				%>

				<%
				if (compareType.equals("BSP")  || compareType.equals("Network") ) {
				%>
				<td>
					<c:out value="${orderCompare.ticketNumber}" />
				</td>
				<td>
					<c:out value="${orderCompare.ticketPrice}" />
				</td>
				<td>
					<c:out value="${orderCompare.totalAirportFuelPrice}" />
				</td>
				<td>
					<c:out value="${orderCompare.perOutAmount}" />
				</td>
				<td>
					<c:out value="${orderCompare.drawProfits}" />
				</td>
				<%
				}
				%>
				
				
				<%
				if (compareType.equals("Bank")) {
				%>
				<td>
					<c:out value="${orderCompare.airOrderNo}" />
				</td>
				<td>
					<c:out value="${orderCompare.inAmount}" />
				</td>
				<td>
					<c:out value="${orderCompare.outAmount}" />
				</td>
				<%
				}
				%>
				<td>
					<c:out value="${orderCompare.passengerCount}" />
				</td>
			</tr>
		</c:forEach>
	</table>
</c:if>