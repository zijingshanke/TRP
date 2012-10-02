<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
参照系统记录--问题单:
<c:if test="${empty problemCompareList2}">
	0
</c:if>
<c:out value="${problemCompareList2Size}"></c:out>

<c:if test="${!empty problemCompareList2}">
	<table cellpadding="0" cellspacing="0" border="0"  class="dataList" >
		<th width="35">
			<div>
				&nbsp;序号
			</div>
		</th>
		<th style="display: none">
			<div>
				交易平台
			</div>
		</th>
		<th>
			<div>
				预定编码
			</div>
		</th>
		<th>
			<div>
				平台订单号
			</div>
		</th>
		<th style="display: none">
			<div>
				支付订单号
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
		<th>
			<div>
				航班
			</div>
		</th>
		<th>
			<div>
				舱位
			</div>
		</th>
		<th>
			<div>
				票号
			</div>
		</th>
		<th>
			<div>
				出发地
			</div>
		</th>
		<th>
			<div>
				目的地
			</div>
		</th>
			<th>
			<div>
				人数
			</div>
		</th>
		<c:forEach var="problemCompare" items="${problemCompareList2}"
			varStatus="status">
			<tr>
				<td>
					<c:out value="${status.count}" />
				</td>
				<td style="display: none">
					<c:out value="${problemCompare.platformName}" />
				</td>
				<td>
					<c:out value="${problemCompare.subPnr}" />
				</td>
				<td>
					<c:out value="${problemCompare.airOrderNo}" />
				</td>
				<td style="display: none">
					<c:out value="${problemCompare.payOrderNo}" />
				</td>
				<td>
					<c:out value="${problemCompare.inAccountName}" />
				</td>
				<td>
					<c:out value="${problemCompare.inAmount}" />
				</td>
				<td>
					<c:out value="${problemCompare.flightCode}" />
				</td>
				<td>
					<c:out value="${problemCompare.flightClass}" />
				</td>
				<td>
					<c:out value="${problemCompare.ticketNumber}" />
				</td>
				<td>
					<c:out value="${problemCompare.startPoint}" />
				</td>
				<td>
					<c:out value="${problemCompare.endPoint}" />
				</td>
				<td>
					<c:out value="${problemCompare.passengerCount}" />
				</td>
			</tr>
		</c:forEach>
	</table>
</c:if>

<hr/>
