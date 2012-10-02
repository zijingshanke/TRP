<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />
		<script src="../_js/common.js" type="text/javascript"></script>
		<script src="../_js/calendar/WdatePicker.js" type="text/javascript"></script>
		<script src="../_js/common.js" type="text/javascript"></script>
		<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" src="../_js/base/DateUtil.js"></script>
		
		<script type="text/javascript">
		$(function() {		
		  var today = new Date();
	      var dateNow= showLocaleYesterday();
		  $('#startDate').val(dateNow+" 00:00:00");
		  $('#endDate').val(dateNow+" 23:59:59");
		  
		});
		
		function exportReport(){
			var startDate=$("#startDate").val();
			//alert(startDate)
			if(startDate == ""){
				alert("请选择开始日期")
				return false;
			}
			//var thisAction=document.forms[0].thisAction.value;
			//if(thisAction=="downloadTeamSaleReport"||thisAction=="downloadTeamRakeOffReport"){
				//alert("团队报表维护中");
				//return false;
			//}
			
			document.forms[0].submit();
		}		
		</script>
	</head>
	<body>
	<c:choose>
      <c:when test="${report.thisAction=='downloadSaleReport'}">
        <c:set var="title2" value="下载销售报表"/>
      </c:when>
      <c:when test="${report.thisAction=='downloadRetireReport'}">
        <c:set var="title2" value="下载退废报表"/>
      </c:when>
      <c:when test="${report.thisAction=='downloadTeamSaleReport'}">
        <c:set var="title2" value="下载团队统计报表"/>
      </c:when>
      <c:when test="${report.thisAction=='downloadTeamRakeOffReport'}">
        <c:set var="title2" value="下载团队未返报表"/>
      </c:when>
 </c:choose>
		<div id="mainContainer">
			<div id="container">
				<html:form action="/airticket/report.do">
					<html:hidden property="thisAction" />
					<html:hidden property="reportType"  />					
					<table width="100%" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td width="10" height="10" class="tblt"></td>
							<td height="10" class="tbtt"></td>
							<td width="10" height="10" class="tbrt"></td>
						</tr>
						<tr>
							<td width="10" class="tbll"></td>
							<td valign="top" class="body"></td>
						</tr>
						<c:import url="../_jsp/mainTitle.jsp" charEncoding="UTF-8">
							<c:param name="title1" value="报表管理" />
							<c:param name="title2" value="${title2}" />
						</c:import>

						<table cellpadding="0" cellspacing="0" border="0"
							class="searchPanel">
							<tr>
								<td>
									操作员
									<html:text property="operator" styleClass="colorblue2 p_5"
										style="width:100px;" />
								</td>
								<td>
									选择日期:
									<html:text property="startDate" styleId="startDate" styleClass="colorblue2 p_5"
										style="width:150px;"
										onfocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
										readonly="true" />
									__
									<html:text property="endDate" styleId="endDate" styleClass="colorblue2 p_5"
										style="width:150px;"
										onfocus="WdatePicker({startDate:'%y-%M-01 23:59:59',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
										readonly="true" />
								</td>
								<td>
									<input type="button" class="button3" value="导出"
										onclick="exportReport();" />
								</td>
							</tr>
						</table>
						<c:if test="${report.thisAction=='downloadSaleReport' or report.thisAction=='downloadRetireReport'}">
						<table cellpadding="0" cellspacing="0" border="0" class="dataList">
							卖出平台：
							<tr>
								<c:forEach items="${salePlatformList}" var="platform"
									varStatus="sta">
									<th>
										<div>
											<input name="salePlatformIds" type="checkbox"
												value="<c:out value="${platform.id}"/>" />
											<c:out value="${platform.name}" />
										</div>
									</th>
									<c:if test="${sta.count%7==0}">
							</tr>
							<tr>
								</c:if>
								</c:forEach>
							</tr>
						</table>


						<table cellpadding="0" cellspacing="0" border="0" class="dataList">
							<tbody id="div_demo1">
								买入平台：
								<tr>
									<th>
										<font color="red"><input id="check_grp1"
												type="checkbox"
												onclick="selectGroup('div_demo1','check_grp1')" />平台</font>
									</th>
									<c:forEach items="${buyPlatformListB2B}" var="platform2"
										varStatus="sta">

										<th>
											<div>
												<input name="buyPlatformIds" type="checkbox"
													value="<c:out value="${platform2.id}"/>" />
												<c:out value="${platform2.name}" />
											</div>
										</th>

										<c:if test="${sta.count%9==0}">
								</tr>
								<tr>
									</c:if>
									</c:forEach>
								</tr>
							</tbody>
							<tbody id="div_demo2">
								<tr>
									<th>
										<FONT color="red"><input type="checkbox"
												id="check_grp2"
												onclick="selectGroup('div_demo2','check_grp2')" />网电</FONT>
									</th>
									<c:forEach items="${buyPlatformListBSP}" var="platform2"
										varStatus="sta">

										<th>
											<div>
												<input name="buyPlatformIds" type="checkbox"
													value="<c:out value="${platform2.id}"/>" />
												<c:out value="${platform2.name}" />
											</div>
										</th>
										<c:if test="${sta.count%8==0}">
								</tr>
								<tr>
									</c:if>
									</c:forEach>
								</tr>
							</tbody>
						</table>

						<table cellpadding="0" cellspacing="0" border="0" class="dataList">
							收款帐号：
							<c:forEach items="${receiveAccountList}" var="account"
								varStatus="sta">
								<tbody id="div_demo1<c:out value='${account.id}'/>">
									<tr>
										<th>
											<div>
												<input id="check_grp1<c:out value='${account.id}'/>"
													onclick="selectGroup('div_demo1<c:out value='${account.id}'/>','check_grp1<c:out value='${account.id}'/>')"
													name="receiveAccountIds" type="checkbox"
													value="<c:out value="${account.id}"/>" />
												<FONT color="red"><c:out value="${account.name}" />
												</FONT>
											</div>

										</th>
										<c:forEach items="${account.accounts}" var="ac">
											<th>
												<div>
													<input name="receiveAccountIds" type="checkbox"
														value="<c:out value="${ac.id}"/>" />
													<c:out value="${ac.name}" />
												</div>

											</th>
										</c:forEach>

									</tr>
								</tbody>
							</c:forEach>
						</table>

						<table cellpadding="0" cellspacing="0" border="0" class="dataList">
							付款帐号：
							<c:forEach items="${payAccountList}" var="account"
								varStatus="sta">
								<tbody id="div_demo2<c:out value='${account.id}'/>">
									<tr>
										<th>
											<div>
												<input id="check_grp2<c:out value='${account.id}'/>"
													onclick="selectGroup('div_demo2<c:out value='${account.id}'/>','check_grp2<c:out value='${account.id}'/>')"
													name="payAccountIds" type="checkbox"
													value="<c:out value="${account.id}"/>" />
												<FONT color="red"><c:out value="${account.name}" />
												</FONT>
											</div>

										</th>
										<c:forEach items="${account.accounts}" var="ac">
											<th>
												<div>
													<input name="payAccountIds" type="checkbox"
														value="<c:out value="${ac.id}"/>" />
													<c:out value="${ac.name}" />
												</div>

											</th>
										</c:forEach>
									</tr>
								</tbody>
							</c:forEach>
						</table>

						</c:if>
				</html:form>
			</div>
		</div>
		<script type="text/javascript">
		function uncheckGrp(grp_id)
		 {
		   document.getElementById(grp_id).checked = document.getElementById(grp_id).checked&0;
		   
		 }
		 function selectGroup(div_id,grp_id){
		
		  var g_div = document.getElementById(div_id);
		  var grp = document.getElementById(grp_id);
		  var eles = g_div.getElementsByTagName("input");
		//  alert(eles.length);
		  for(var i=0;i<eles.length;i++){
		   if(eles[i].disabled==false) eles[i].checked=grp.checked;
		  }
}
</script>
	</body>
</html>
