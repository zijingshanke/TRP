<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%
	String path = request.getContextPath();
	String compareType = request.getParameter("compareType");
	String showOperate = request.getParameter("showOperate");
%>
<script type="text/javascript">
function deleteProblemCompare1(){	
	var selectedItems = document.getElementById("problemCompareForm1").selectedItems;	
	if(document.getElementById("problemCompareForm1").selectedItems==null){
		alert("没有数据，无法操作！");
	}else if (sumCheckedBox(document.getElementById("problemCompareForm1").selectedItems)<1){
	   	alert("您还没有选择数据！");
	 }else{
	    document.getElementById("problemCompareForm1").thisAction.value="deleteReportCompare";
	    document.getElementById("problemCompareForm1").submit();
	  }
}

function batchMemoProblemCompare1(){	
	var selectedItems = document.getElementById("problemCompareForm1").selectedItems;	
	if(document.getElementById("problemCompareForm1").selectedItems==null){
		alert("没有数据，无法操作！");
	}else if (sumCheckedBox(document.getElementById("problemCompareForm1").selectedItems)<1){
	   	alert("您还没有选择数据！");
	}else{
	 	alert("测试");
	    //document.getElementById("problemCompareForm1").thisAction.value="deleteReportCompare";
	    //document.getElementById("problemCompareForm1").submit();
	}
}
</script>

<font color="red">对账只存在于本系统</font>
<c:if test="${empty problemCompareList1}">
	0
</c:if>

<c:if test="${!empty problemCompareList1}">
	<c:out value="${problemCompareList1Size}"></c:out>
	<input value="标记修改" type="button" onclick="deleteProblemCompare1()"
		class="button1" />
	<input value="批量备注" type="button" onclick="batchMemoProblemCompare1()"
		class="button1" style="display: none" />
	<html:form action="/transaction/reportCompareList.do"
		styleId="problemCompareForm1" method="post">
		<input type="hidden" name="thisAction" />
		<input type="hidden" name="resultId"
			value="<c:out value='${reportCompareResult.id}' />" />
		<table cellpadding="0" cellspacing="0" border="0" class="dataList">
			<th width="35">
				<div>
					&nbsp;序号
				</div>
			</th>
			<%
			if (showOperate.equals("Yes")) {
			%>
			<th>
				<div>
					<input type="checkbox" onclick="checkAll(this, 'selectedItems')"
						name="compare1" />
				</div>
			</th>
			<%
			}
			%>
			<%
						if (compareType.equals("Platform") || compareType.equals("BSP")
						|| compareType.equals("Network")
						|| compareType.equals("Bank")) {
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
			<c:forEach var="problemCompare" items="${problemCompareList1}"
				varStatus="status">
				<tr>
					<td>
						<c:out value="${status.count}" />
					</td>
					<%
					if (showOperate.equals("Yes")) {
					%>
					<td>
						<input type="checkbox" name="selectedItems"
							value="<c:out value='${problemCompare.id}' />"
							onclick="checkItem(this, 'compare1')" />
					</td>
					<%
					}
					%>
					<%
								if (compareType.equals("Platform") || compareType.equals("BSP")
								|| compareType.equals("Network")
								|| compareType.equals("Bank")) {
					%>
					<td>
						<a
							href="<%=path%>/airticket/listAirTicketOrder.do?thisAction=view&id=<c:out value='${problemCompare.orderId}' />"
							target="_blank"> <c:out value="${problemCompare.subPnr}" />
						</a>
					</td>
					<%
					}
					%>

					<%
					if (compareType.equals("Platform")) {
					%>
					<td>
						<c:out value="${problemCompare.airOrderNo}" />
					</td>
					<td>
						<c:out value="${problemCompare.inAccountName}" />
						(
						<c:out value="${problemCompare.inAccountNo}" />
						)
					</td>
					<td>
						<c:out value="${problemCompare.inAmount}" />
					</td>
					<%
					}
					%>

					<%
					if (compareType.equals("BSP") || compareType.equals("Network")) {
					%>
					<td>
						<c:out value="${problemCompare.ticketNumber}" />
					</td>
					<td>
						<c:out value="${problemCompare.ticketPrice}" />
					</td>
					<td>
						<c:out value="${problemCompare.totalAirportFuelPrice}" />
					</td>
					<td>
						<c:out value="${problemCompare.outAmount}" />
					</td>
					<td>
						<c:out value="${problemCompare.drawProfits}" />
					</td>
					<%
					}
					%>

					<%
					if (compareType.equals("Bank")) {
					%>
					<td>
						<c:out value="${problemCompare.airOrderNo}" />
					</td>
					<td>
						<c:out value="${problemCompare.inAmount}" />
					</td>
					<td>
						<c:out value="${problemCompare.outAmount}" />
					</td>
					<%
					}
					%>
					<td>
						<c:out value="${problemCompare.passengerCount}" />
					</td>
				</tr>
			</c:forEach>
		</table>
	</html:form>
</c:if>

<hr />
