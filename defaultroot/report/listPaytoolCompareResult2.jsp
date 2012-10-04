<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<script type="text/javascript">
function deleteProblemCompare2(){	
	var selectedItems = document.getElementById("problemCompareForm2").selectedItems;	
	if(document.getElementById("problemCompareForm2").selectedItems==null){
		alert("没有数据，无法操作！");
	}else if (sumCheckedBox(document.getElementById("problemCompareForm2").selectedItems)<1){
	   	alert("您还没有选择数据！");
	 }else{
	    document.getElementById("problemCompareForm2").thisAction.value="deleteReportCompare";
	   // document.getElementById("problemCompareForm2").submit();
	  }
}

</script>

<font color="red">对账只存在于支付工具</font>
<c:if test="${empty problemCompareList2}">
	0
</c:if>

<c:if test="${!empty problemCompareList2}">
	<c:out value="${problemCompareList2Size}"></c:out>
	<input value="标记修改" type="button" onclick="deleteProblemCompare2()"
		class="button1" />

	<html:form action="/transaction/reportRecodeList.do"
		styleId="problemCompareForm2" method="post">
		<input type="hidden" name="thisAction" />
		<input type="hidden" name="resultId"
			value="<c:out value='${reportCompareResult.id}' />" />
		<table cellpadding="0" cellspacing="0" border="0" class="dataList">
			<th width="35">
				<div>
					&nbsp;序号
				</div>
			</th>
			<th>
				<div>
					<input type="checkbox" onclick="checkAll(this, 'selectedItems')"
						name="compare2" />
				</div>
			</th>
			<th>
				<div>
					预定编码
				</div>
			</th>
			<th>
				<div>
					平台
				</div>
			</th>
			<th>
				<div>
					支付工具
				</div>
			</th>

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
			<c:forEach var="reportRecode" items="${problemCompareList2}"
				varStatus="status">
				<tr>
					<td>
						<input type="checkbox" name="selectedItems"
							value="<c:out value='${reportRecode.id}' />"
							onclick="checkItem(this, 'compare2')" />
					</td>
					<td>
						<c:out value="${status.count}" />
					</td>
					<td>
						<c:out value="${reportRecode.subPnr}" />
					</td>
					<td>
						<c:out value="${reportRecode.platformName}" />
					</td>
					<td>
						<c:out value="${reportRecode.paytoolName}" />
					</td>
					<td>
						<c:out value="${reportRecode.statementTypeInfo}" />
					</td>
					<td>
						<c:out value="${reportRecode.amount}" />
					</td>
					<td>
						<c:out value="${reportRecode.passengerCount}" />
					</td>
				</tr>
			</c:forEach>
		</table>
	</html:form>
</c:if>

<hr />
