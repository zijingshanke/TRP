<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%
String path = request.getContextPath();
%>
<html>
	<head>
		<title>查看报表对比结果</title>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />
		<script src="../_js/common.js" type="text/javascript"></script>
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

#divReportCompareList {
	margin-left: 700px;
}
</style>
		<script type="text/javascript">
		function refreshCompareResult(){
			var thisAction =document.forms[0].thisAction;	
			if(thisAction==null){
				alert("thisAction不能为空")
				return false;
			}else{
				if(thisAction.value==''){
					alert("thisAction不能为空")
					return false;
				}
				
				if(confirm('重新对比将替换现有对比记录，请确认')){
					var actionURL="<%=path%>/transaction/reportRecode.do";
					actionURL+="?thisAction=refreshCompareResult&resultId=<c:out value='${reportCompareResult.id}' />";
						
			 		document.forms[0].action=actionURL;
		    		document.forms[0].submit();
				}				
			} 
		}

 	
 	function checkForm(){					
			return true;
		}	
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
										对比类型
									</td>
									<td style="text-align: left">
										<c:out value="${reportCompareResult.compareTypeInfo}" />
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
										对比人
									</td>
									<td style="text-align: left">
										<c:out value="${reportCompareResult.userName}" />
									</td>
									<td class="lef">
										对比时间
									</td>
									<td style="text-align: left">
										<c:out value="${reportCompareResult.formatLastDate}" />
									</td>									
								</tr>
								<tr>
									<td class="lef">
										备注
									</td>
									<td style="text-align: left">
										<c:out value="${reportCompareResult.memo}" />
									</td>
								</tr>
							</table>
							<table width="100%" style="margin-top: 5px;">
								<tr>
									<td>
										<input name="label" type="button" class="button1" value="返 回"
											onclick="window.history.back();" />
										<html:hidden property="thisAction" name="reportCompareResult" />								
										<input name="label" type="button" class="button1" value="备注" />									
										<input name="label" type="button" class="button1" value="重新对比"
											onclick="refreshCompareResult()" style="display: none"/>
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
		<c:if test="${reportCompareResult.compareType==1}">
			<div id="divProblemCompareList1">
				<jsp:include page="../report/listPlatformCompareResult1.jsp"></jsp:include>
			</div>
			<div id="divProblemCompareList2">
				<jsp:include page="../report/listPlatformCompareResult2.jsp"></jsp:include>
			</div>
		</c:if>
		<c:if test="${reportCompareResult.compareType==2}">
			<div id="divProblemCompareList1">
				<jsp:include page="../report/listPaytoolCompareResult1.jsp"></jsp:include>
			</div>
			<div id="divProblemCompareList2">
				<jsp:include page="../report/listPaytoolCompareResult2.jsp"></jsp:include>
			</div>
		</c:if>
	</body>
</html>