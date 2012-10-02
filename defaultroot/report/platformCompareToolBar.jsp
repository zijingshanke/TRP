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
	<tr>
		<td width="10" height="10" class="tblt"></td>
		<td height="10" class="tbtt"></td>
		<td width="10" height="10" class="tbrt"></td>
	</tr>
	<tr>
		<td width="10" class="tbll"></td>
		<td valign="top" class="body">
			<table cellpadding="0" cellspacing="0" border="0">
				<tr>
					<td>
						选择日期:
						<html:text property="beginDateStr" styleId="beginDateStr"
							value="${tempCompare.beginDateStr}" style="width:150px;"
							onfocus="WdatePicker({startDate:'%y-%M-%D 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
							readonly="true" />
						--
						<html:text property="endDateStr" styleId="endDateStr"
							value="${tempCompare.endDateStr}" style="width:150px;"
							onfocus="WdatePicker({startDate:'%y-%M-%D 23:59:59',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
							readonly="true" />
					</td>
					<td>
						交易平台名称
					</td>
					<td>
						<html:select property="platformId" name="reportCompare"
							value="${tempCompare.platformId}" styleClass="colorblue2 p_5"
							style="width:100px;">
							<c:forEach items="${platformList}" var="platform">
								<html:option value="${platform.id}">
									<c:out value="${platform.showName}" />
								</html:option>
							</c:forEach>
						</html:select>
					</td>
					<td>
						交易类型
					</td>
					<td>
						<html:select property="tranType" name="reportCompare"
							value="${tempCompare.type}" styleClass="colorblue2 p_5"
							style="width:100px;">
							<html:option value="1">供应</html:option>
							<html:option value="2">采购</html:option>
							<html:option value="13">供应退废</html:option>
							<html:option value="14">采购退废</html:option>
							<html:option value="15">供应退票</html:option>
							<html:option value="16">采购退票</html:option>
							<html:option value="17">供应废票</html:option>
							<html:option value="18">采购废票</html:option>
						</html:select>
					</td>
					<td>
						<input type="button" class="button3" value="选择上传文件"
							onclick="selectAttachment();" />
					</td>
					<td>
						&nbsp;
					</td>
					<td>
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
		</td>
	</tr>
	<hr>
</html:form>
<script type="text/javascript">	
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

	</script>