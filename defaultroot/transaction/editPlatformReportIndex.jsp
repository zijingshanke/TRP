﻿<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%
String path = request.getContextPath();
%>
<html>
	<head>
		<title>main</title>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />
	</head>
	<script type="text/javascript">	
		function add(){	
			var name=document.forms[0].name.value;
			if(name==""){
				alert("请输入支付帐号名称!")
				return false;
			}
			var thisAction =document.forms[0].thisAction;	
			if(thisAction==null){
				alert("thisAction不能为空")
				return false;
			}else{
				if(thisAction.value==''){
					alert("thisAction不能为空")
					return false;
				}
			 	document.forms[0].action="<%=path%>/transaction/platformReportIndex.do?thisAction="+thisAction.value;
		    	document.forms[0].submit();
			} 
		}
	</script>
	<body>
		<c:import url="../_jsp/mainTitle.jsp" charEncoding="UTF-8">
			<c:param name="title1" value="报表对比管理" />
			<c:param name="title2" value="编辑平台报表字段索引" />
		</c:import>
		<html:form action="/transaction/platformReportIndex.do" method="post">
			<div id="mainContainer">
				<div id="container">
					<table width="100%" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td width="10" height="10" class="tblt"></td>
							<td height="10" class="tbtt"></td>
							<td width="10" height="10" class="tbrt"></td>
						</tr>
						<tr>
							<td width="10" class="tbll"></td>
							<td valign="top" class="body">

								<hr>
								<table width="100%" cellpadding="0" cellspacing="0" border="0"
									class="dataList">
									<tr>
										<td class="lef">
											交易平台名称
										</td>
										<td style="text-align: left">
											<html:select property="platformId" name="platformReportIndex"
											 value="${platformReportIndex.platformId}"	styleClass="colorblue2 p_5" style="width:100px;">
												<c:forEach items="${platformList}" var="platform">
													<html:option value="${platform.id}">
														<c:out value="${platform.name}" />
													</html:option>
												</c:forEach>
											</html:select>
										</td>
									</tr>
									<tr>
										<td class="lef">
											航班号
										</td>
										<td style="text-align: left">
											<html:text property="flightCode" name="platformReportIndex"
												value="${platformReportIndex.flightCode}"
												styleClass="colorblue2 p_5" style="width:100px;"></html:text>
										</td>
									</tr>
									<tr>
										<td class="lef">
											舱位
										</td>
										<td style="text-align: left">
											<html:text property="flightClass" name="platformReportIndex"
												value="${platformReportIndex.flightClass}"
												styleClass="colorblue2 p_5" style="width:100px;"></html:text>
										</td>
									</tr>
									<tr>
										<td class="lef">
											票号
										</td>
										<td style="text-align: left">
											<html:text property="ticketNumber" name="platformReportIndex"
												value="${platformReportIndex.ticketNumber}"
												styleClass="colorblue2 p_5" style="width:100px;"></html:text>
										</td>
									</tr>
									<tr>
										<td class="lef">
											出发地
										</td>
										<td style="text-align: left">
											<html:text property="startPoint" name="platformReportIndex"
												value="${platformReportIndex.startPoint}"
												styleClass="colorblue2 p_5" style="width:100px;"></html:text>
										</td>
									</tr>
									<tr>
										<td class="lef">
											目的地
										</td>
										<td style="text-align: left">
											<html:text property="endPoint" name="platformReportIndex"
												value="${platformReportIndex.endPoint}"
												styleClass="colorblue2 p_5" style="width:100px;"></html:text>
										</td>
									</tr>
									<tr>
										<td class="lef">
											预定编码
										</td>
										<td style="text-align: left">
											<html:text property="subPnr" name="platformReportIndex"
												value="${platformReportIndex.subPnr}"
												styleClass="colorblue2 p_5" style="width:100px;"></html:text>
										</td>
									</tr>
									<tr>
										<td class="lef">
											折扣
										</td>
										<td style="text-align: left">
											<html:text property="discount" name="platformReportIndex"
												value="${platformReportIndex.discount}"
												styleClass="colorblue2 p_5" style="width:100px;"></html:text>
										</td>
									</tr>
									<tr>
										<td class="lef">
											说明
										</td>
										<td style="text-align: left">
											<html:text property="memo" name="platformReportIndex"
												value="${platformReportIndex.memo}"
												styleClass="colorblue2 p_5" style="width:250px;"></html:text>
										</td>
									</tr>
									<tr>
										<td class="lef">
											类型
										</td>
										<td style="text-align: left">

											<html:select property="type" name="platformReportIndex"
												styleClass="colorblue2 p_5" style="width:50px;">
												<html:option value="1">销售</html:option>
												<html:option value="2">退废</html:option>
											</html:select>
										</td>
									</tr>
									<tr>
										<td class="lef">
											状态
										</td>
										<td style="text-align: left">

											<html:select property="status" name="platformReportIndex"
												styleClass="colorblue2 p_5" style="width:50px;">
												<html:option value="1">有效</html:option>
												<html:option value="0">无效</html:option>
											</html:select>
										</td>
									</tr>
								</table>
								<table width="100%" style="margin-top: 5px;">
									<tr>
										<td>
											<html:hidden property="id" name="platformReportIndex" />
											<html:hidden property="thisAction" name="platformReportIndex" />
											<input name="label" type="button" class="button1" value="提交"
												onclick="add();">
											<input name="label" type="reset" class="button1" value="重 置">
											<input name="label" type="button" class="button1" value="返 回"
												onclick="window.history.back();">
										</td>
									</tr>
								</table>
								<div class="clear"></div>

							</td>
							<td width="10" class="tbrr"></td>
						</tr>
						<tr>
							<td width="10" class="tblb"></td>
							<td class="tbbb"></td>
							<td width="10" class="tbrb"></td>
						</tr>
					</table>
				</div>
			</div>
		</html:form>
	</body>
</html>


