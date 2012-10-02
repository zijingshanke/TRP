<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
<link href="../_css/global.css" rel="stylesheet" type="text/css" />
<script src="../_js/common.js" type="text/javascript"></script>
<c:if test="${!empty orderCompareList}">
系统报表
	<table cellpadding="0" cellspacing="0" border="0" class="dataList">
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
		<c:forEach var="orderCompare" items="${orderCompareList}"
			varStatus="status">
			<tr>
				<td>
					<c:out value="${status.count}" />
				</td>
				<td style="display: none">
					<c:out value="${orderCompare.platformName}" />
				</td>
				<td>
					<c:out value="${orderCompare.subPnr}" />
				</td>
				<td>
					<c:out value="${orderCompare.airOrderNo}" />
				</td>
				<td style="display: none">
					<c:out value="${orderCompare.payOrderNo}" />
				</td>
				<td>
					<c:out value="${orderCompare.inAccountName}" />
				</td>
				<td>
					<c:out value="${orderCompare.inAmount}" />
				</td>
				<td>
					<c:out value="${orderCompare.flightCode}" />
				</td>
				<td>
					<c:out value="${orderCompare.flightClass}" />
				</td>
				<td>
					<c:out value="${orderCompare.ticketNumber}" />
				</td>
				<td>
					<c:out value="${orderCompare.startPoint}" />
				</td>
				<td>
					<c:out value="${orderCompare.endPoint}" />
				</td>
			</tr>
		</c:forEach>
	</table>
</c:if>