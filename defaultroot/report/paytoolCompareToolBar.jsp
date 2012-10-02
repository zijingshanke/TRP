<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="../WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="../WEB-INF/c.tld" prefix="c"%>
<script src="../_js/common.js" type="text/javascript"></script>
<%
String path = request.getContextPath();
%>
<c:import url="../_jsp/mainTitle.jsp" charEncoding="UTF-8">
	<c:param name="title1" value="报表对比管理" />
	<c:param name="title2" value="对比平台报表" />
</c:import>

<script type="text/javascript">
	function startComparePlatformSystem(){
		var manageForm=document.getElementById("manageForm");
		var platformRecodeResultId=manageForm.platformRecodeResultId.value;
		var paytoolRecodeResultId=manageForm.paytoolRecodeResultId.value;
		
		if(platformRecodeResultId==""){
			alert("请先导入当日平台报表");
			return false;
		}
		if(paytoolRecodeResultId==""){
			alert("请先导入当日支付工具报表");
			return false;
		}
		
		var compareCondition=manageForm.compareCondition;
		if(sumCheckedBox(compareCondition)<1){
			alert("请至少选择一项对比条件，例如编码");
			return false;
		}
		
		manageForm.action="<%=path%>/transaction/reportRecode.do?thisAction=comparePlatformPaytool";
		manageForm.submit();
	}
	
	function saveComparePlatformSystem(){
		var manageForm=document.getElementById("manageForm");
		var platformRecodeResultId=manageForm.platformRecodeResultId.value;
		var paytoolRecodeResultId=manageForm.paytoolRecodeResultId.value;
		if(platformRecodeResultId==""){
			alert("请先导入当日平台报表");
			return false;
		}
		if(paytoolRecodeResultId==""){
			alert("请先导入当日支付工具报表");
			return false;
		}
		
		manageForm.action="<%=path%>/transaction/reportRecode.do?thisAction=saveCompareResult";
		manageForm.submit();
	}
	
</script>
<html:form action="transaction/reportRecode.do" method="post"
	styleId="manageForm">
	<html:hidden property="thisAction" />
	<html:hidden property="compareType"
		value="${tempReportRecode.compareType}" />
	<html:hidden property="reportCompareResultId"
		value="${tempReportRecode.reportCompareResultId}" />
	<html:hidden property="platformRecodeResultId"
		value="${tempReportRecode.platformRecodeResultId}" />
	<html:hidden property="paytoolRecodeResultId"
		value="${tempReportRecode.paytoolRecodeResultId}" />
	<input type="hidden" id="exception"
		value="<c:out value="${exception}" />" />
	<tr>
		<td width="10" height="10" class="tblt">
			&nbsp;
		</td>
		<td height="10" class="tbtt">
			&nbsp;
		</td>
		<td width="10" height="10" class="tbrt">
			&nbsp;
		</td>
	</tr>
	<tr>
		<td width="10" class="tbll"></td>
		<td valign="top" class="body">
			<table cellpadding="0" cellspacing="0" border="0">
				<tr style="text-align: left">
					<td>
						选择日期:
						<html:text property="tempReportDate"
							value="${tempReportRecode.reportDate}" style="width:150px;"
							onfocus="WdatePicker({startDate:'%y-%M-%D 00:00:00',dateFmt:'yyyy-MM-dd 00:00:00',alwaysUseStartDate:true})"
							readonly="true" onchange="selectedDate();" />
						<html:hidden property="reportDate"
							value="${tempReportRecode.reportDate}" />
					</td>
					<td style="width: 20px;"></td>
					<td width="250px">
						交易平台：
						<html:link
							page="/transaction/reportRecodeResultList.do?thisAction=view&id=${tempReportRecode.platformRecodeResultId}">
							<c:out value="${tempReportRecode.platformRecodeResultName}" />
						</html:link>
					</td>
					<td style="width: 200px;"></td>
					<td width="250px">
						支付工具：
						<html:link
							page="/transaction/reportRecodeResultList.do?thisAction=view&id=${tempReportRecode.paytoolRecodeResultId}">
							<c:out value="${tempReportRecode.paytoolRecodeResultName}" />
						</html:link>
					</td>
					<td>
						<input name="label" type="button" class="button1" value="开始对比"
							onclick="startComparePlatformSystem()" />
						<input name="label" type="button" class="button1" value="保存结果"
							onclick="saveComparePlatformSystem()" />
					</td>
				</tr>
				<tr style="text-align: left">
					<td>
					</td>
					<td style="width: 20px;"></td>
					<td>
						<input type="radio" name="compareStandard" value="1"  />
						以我为标准对比
					</td>
					<td style="width: 250px;">
						<input type="checkbox" name="compareCondition" value="subPnr" checked="checked" />
						编码
						<input type="checkbox" name="compareCondition" value="airOrderNo">
						票号
						<input type="checkbox" name="compareCondition" value="account">
						账号
						<input type="checkbox" name="compareCondition" value="amount">
						金额
					</td>
					<td>
						<input type="radio" name="compareStandard" value="2">
						以我为标准对比
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<hr>
</html:form>
<script type="text/javascript">	
	window.onload=initOnload;
		
		function selectedDate(){
			document.forms[0].thisAction.value = "getReportRecodeByDate";
			document.forms[0].submit();
		}
		
		function displayDate(){
			var date = document.forms[0].tempReportDate;
			var dateValue = date.value;
			if(dateValue.length > 10){
				date.value = dateValue.substring(0,10);
			}
		}
		
		function notice(){
			var info = document.getElementById("exception").value;
			if(info == "noInfo"){
				document.forms[0].name.value="";
				alert("无此日期的记录，请先导入此日期的交易平台记录和支付工具记录");
			}
			if(info == "noPlatformResult"){
				document.forms[0].name.value="";
				alert("无此日期的交易平台记录，请先导入");
			}
			if(info == "noToolResult"){
				document.forms[0].name.value="";
				alert("无此日期的支付工具记录，请先导入");
			}
		}	
		
		function setCompareCondition(){
			var obj=document.forms[0].compareCondition;
			var compareConditionStr="<c:out value='${tempReportRecode.compareConditionStr}' />";
			if(compareConditionStr!=null && compareConditionStr!=""){
				setCheckBoxByValue(obj,compareConditionStr);
			}			 
		}
		
		function setCompareStandard(){
			var obj=document.forms[0].compareStandard;
			var compareStandard="<c:out value='${tempReportRecode.compareStandard}' />";
			if(compareStandard!=null && compareStandard!=""){
				setRadioByValue(obj,compareStandard);
			}			 
		}
		
		function initOnload(){
			displayDate();
			notice();
			setCompareCondition();
			setCompareStandard();
		}			
	</script>