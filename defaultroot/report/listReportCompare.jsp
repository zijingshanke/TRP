<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
<link href="../_css/global.css" rel="stylesheet" type="text/css" />
<script src="../_js/common.js" type="text/javascript"></script>
<c:if test="${!empty reportCompareList}">
文件报表
	<table cellpadding="0" cellspacing="0" border="0" class="dataList">
		<th>
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
		<c:forEach var="reportCompare" items="${reportCompareList}"
			varStatus="status">
			<tr>
				<td>
					<c:out value="${status.count}" />
				</td>
				<td style="display: none">
					<c:out value="${reportCompare.platformName}" />
				</td>
				<td>
					<c:out value="${reportCompare.subPnr}" />
				</td>
				<td>
					<c:out value="${reportCompare.airOrderNo}" />
				</td>
				<td style="display: none">
					<c:out value="${reportCompare.payOrderNo}" />
				</td>
				<td>
					<c:out value="${reportCompare.inAccountName}" />
				</td>
				<td>
					<c:out value="${reportCompare.inAmount}" />
				</td>
				<td>
					<c:out value="${reportCompare.flightCode}" />
				</td>
				<td>
					<c:out value="${reportCompare.flightClass}" />
				</td>
				<td>
					<c:out value="${reportCompare.ticketNumber}" />
				</td>
				<td>
					<c:out value="${reportCompare.startPoint}" />
				</td>
				<td>
					<c:out value="${reportCompare.endPoint}" />
				</td>
				<td>
					<c:out value="${reportCompare.passengerCount}" />
				</td>
			</tr>
		</c:forEach>
	</table>
</c:if>