<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="../WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="../WEB-INF/c.tld" prefix="c"%>

<%
String path = request.getContextPath();
%>
<c:import url="../_jsp/mainTitle.jsp" charEncoding="UTF-8">
	<c:param name="title1" value="报表对比管理" />
	<c:param name="title2" value="对比平台报表" />
</c:import>

	

<html:form action="/transaction/reportRecode.do" method="post">
 
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="dataList">	
	<tr>
      <td colspan="3" align="center">
			选择日期:
			<html:text property="tempReportDate" styleId="beginDateStr"
				value="${tempReportRecode.reportDate}" style="width:150px;"
				onfocus="WdatePicker({startDate:'%y-%M-%D 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
				readonly="true"  onchange="selectedDate();"/>
				<html:hidden property="reportDate" value="${tempReportRecode.reportDate}" />
	  </td>
	</tr>

	<tr>
		<td width="40%">
			本地财务系统(A)
		</td>
		<td >
			<input type="checkbox" name="compareCondition" value="subPnr" checked="checked" />
			编码
			<input type="checkbox" name="compareCondition" value="airOrderNo">
			票号
			<input type="checkbox" name="compareCondition" value="account">
			账号
			<input type="checkbox" name="compareCondition" value="amount">
			金额
		</td>
		<td width="40%">
			交易平台名称(B):
			<html:select property="platformId" name="reportCompare"
				value="${tempCompare.platformId}" styleClass="colorblue2 p_5"
				style="width:140px;">
				<c:forEach items="${platformList}" var="platform">
					<html:option value="${platform.id}">
						<c:out value="${platform.nameTranType}" />
					</html:option>
				</c:forEach>
			</html:select>
		</td>
	</tr>
	<tr>
		 <td colspan="3" align="center"> 
			<html:hidden property="compareType" value="1" name="reportRecode"  />
					<html:hidden property="thisAction" name="reportRecode" />
					<html:hidden property="fileName" name="reportRecode" />
					<input name="label" type="button" class="button2" value="更新系统报表"
						onclick=updateOrderCompareList();>
					<input name="label" type="button" class="button1" value="开始对比"
						onclick=startPlatformCompare();>
					<input name="label" type="button" class="button1" value="保存结果"
						onclick=addReportCompareResult();>						
					<input name="label" type="button" class="button1" value="重 置"
						onclick="clearPlatformCompare();">
		</td>
	</tr>
  </table>
</html:form>


	 
<script type="text/javascript">	
		window.onload=initOnload;
		function startPlatformCompare(){				   
		    document.forms[0].action="<%=path%>/transaction/reportRecode.do?thisAction=comparePlatformReport";
		    document.forms[0].submit();
		}
		
		function addReportCompareResult(){				   
		    document.forms[0].action="<%=path%>/transaction/reportRecode.do?thisAction=addReportCompareResult";
		    document.forms[0].submit();
		}
		
		function checkForm(){	
			return true;
		}	

		function selectedDate(){
			document.forms[0].thisAction.value = "getPlatformReportRecodeByDate";
			document.forms[0].submit();
		}
		
		function initOnload(){
			displayDate();
			notice();
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
			
			if(info == "noPlatformResult"){
				document.forms[0].name.value="";
				alert("无此日期的交易平台记录，请先导入");
			}
		}	
	</script>