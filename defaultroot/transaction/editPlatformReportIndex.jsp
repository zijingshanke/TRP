<%@ page contentType="text/html;charset=UTF-8" language="java"%>
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
								<table cellpadding="0" cellspacing="0" border="0"
									class="dataList">
									<tr>
										<td class="lef">
											名称
										</td>
										<td style="text-align: left">
											<html:text property="name" name="platformReportIndex"
												value="${platformReportIndex.name}"
												styleClass="colorblue2 p_5" style="width:200px;"></html:text>
										</td>
									</tr>
									<tr>
										<td class="lef">
											对比类型
										</td>
										<td style="text-align: left">
											<html:select property="compareType"
												name="platformReportIndex" styleClass="colorblue2 p_5"
												style="width:100px;">
												<html:option value="0">-请选择-</html:option>
												<html:option value="1">平台报表</html:option>
												<html:option value="2">BSP网电</html:option>
												<html:option value="3">支付工具</html:option>
											</html:select>
										</td>

										<td class="lef">
											交易类型
										</td>
										<td style="text-align: left">
											<html:select property="tranType" name="platformReportIndex"
												styleClass="colorblue2 p_5" style="width:100px;">
												<html:option value="0">-请选择-</html:option>
												<html:option value="1">--供应--</html:option>
												<html:option value="2">--采购--</html:option>
												<html:option value="13">供应退废</html:option>
												<html:option value="14">采购退废</html:option>
												<html:option value="15">供应退票</html:option>
												<html:option value="16">采购退票</html:option>
												<html:option value="17">供应废票</html:option>
												<html:option value="18">采购废票</html:option>
											</html:select>
										</td>
										<td class="lef">
											交易平台
										</td>
										<td style="text-align: left">
											<html:select property="platformId" name="platformReportIndex"
												value="${platformReportIndex.platformId}">
												<html:option value="0">-请选择-</html:option>
												<c:forEach items="${platformList}" var="platform">
													<html:option value="${platform.id}">
														<c:out value="${platform.showName}" />
													</html:option>
												</c:forEach>
											</html:select>
										</td>
										<td class="lef" style="display: none">
											账号
										</td>
										<td style="text-align: left" style="display: none">
											<html:select property="accountId" name="platformReportIndex"
												value="${platformReportIndex.accountId}">
												<html:option value="0">-请选择-</html:option>
												<c:forEach items="${accountList}" var="account">
													<html:option value="${account.id}">
														<c:out value="${account.showName}" />
													</html:option>
												</c:forEach>
											</html:select>
										</td>
										<td class="lef">
											支付工具
										</td>
										<td style="text-align: left">
											<html:select property="paymenttoolId"
												name="platformReportIndex"
												value="${platformReportIndex.paymenttoolId}">
												<html:option value="0">-请选择-</html:option>
												<c:forEach items="${paymentToolList}" var="paymenttool">
													<html:option value="${paymenttool.id}">
														<c:out value="${paymenttool.showName}" />
													</html:option>
												</c:forEach>
											</html:select>
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
										<td class="lef">
											平台订单号
										</td>
										<td style="text-align: left">
											<html:text property="airOrderNo" name="platformReportIndex"
												value="${platformReportIndex.airOrderNo}"
												styleClass="colorblue2 p_5" style="width:100px;"></html:text>
										</td>
										<td class="lef">
											支付订单号
										</td>
										<td style="text-align: left">
											<html:text property="payOrderNo" name="platformReportIndex"
												value="${platformReportIndex.payOrderNo}"
												styleClass="colorblue2 p_5" style="width:100px;"></html:text>
										</td>
										<td class="lef">
											交易平台
										</td>
										<td style="text-align: left">
											<html:text property="tranPlatformName" name="platformReportIndex"
												value="${platformReportIndex.tranPlatformName}"
												styleClass="colorblue2 p_5" style="width:100px;"></html:text>
										</td>
									</tr>
									<tr>
										<td class="lef">
											收款账号
										</td>
										<td style="text-align: left">
											<html:text property="inAccount" name="platformReportIndex"
												value="${platformReportIndex.inAccount}"
												styleClass="colorblue2 p_5" style="width:100px;"></html:text>
										</td>
										<td class="lef">
											收款金额
										</td>
										<td style="text-align: left">
											<html:text property="inAmount" name="platformReportIndex"
												value="${platformReportIndex.inAmount}"
												styleClass="colorblue2 p_5" style="width:100px;"></html:text>
										</td>
										<td class="lef">
											付款账号
										</td>
										<td style="text-align: left">
											<html:text property="outAccount" name="platformReportIndex"
												value="${platformReportIndex.outAccount}"
												styleClass="colorblue2 p_5" style="width:100px;"></html:text>
										</td>
										<td class="lef">
											付款金额
										</td>
										<td style="text-align: left">
											<html:text property="outAmount" name="platformReportIndex"
												value="${platformReportIndex.outAmount}"
												styleClass="colorblue2 p_5" style="width:100px;"></html:text>
										</td>
									</tr>
									<tr>
										<td class="lef">
											收退款账号
										</td>
										<td style="text-align: left">
											<html:text property="inRetireAccount"
												name="platformReportIndex"
												value="${platformReportIndex.inRetireAccount}"
												styleClass="colorblue2 p_5" style="width:100px;"></html:text>
										</td>
										<td class="lef">
											收退款金额
										</td>
										<td style="text-align: left">
											<html:text property="inRetireAmount"
												name="platformReportIndex"
												value="${platformReportIndex.inRetireAmount}"
												styleClass="colorblue2 p_5" style="width:100px;"></html:text>
										</td>
										<td class="lef">
											付退款账号
										</td>
										<td style="text-align: left">
											<html:text property="outRetireAccount"
												name="platformReportIndex"
												value="${platformReportIndex.outRetireAccount}"
												styleClass="colorblue2 p_5" style="width:100px;"></html:text>
										</td>
										<td class="lef">
											付退款金额
										</td>
										<td style="text-align: left">
											<html:text property="outRetireAmount"
												name="platformReportIndex"
												value="${platformReportIndex.outRetireAmount}"
												styleClass="colorblue2 p_5" style="width:100px;"></html:text>
										</td>
									</tr>
									<tr style="display: none">
										<td class="lef">
											航班号
										</td>
										<td style="text-align: left">
											<html:text property="flightCode" name="platformReportIndex"
												value="${platformReportIndex.flightCode}"
												styleClass="colorblue2 p_5" style="width:100px;"></html:text>
										</td>
										<td class="lef">
											舱位
										</td>
										<td style="text-align: left">
											<html:text property="flightClass" name="platformReportIndex"
												value="${platformReportIndex.flightClass}"
												styleClass="colorblue2 p_5" style="width:100px;"></html:text>
										</td>
										<td class="lef">
											出发地
										</td>
										<td style="text-align: left">
											<html:text property="startPoint" name="platformReportIndex"
												value="${platformReportIndex.startPoint}"
												styleClass="colorblue2 p_5" style="width:100px;"></html:text>
										</td>
										<td class="lef">
											目的地
										</td>
										<td style="text-align: left">
											<html:text property="endPoint" name="platformReportIndex"
												value="${platformReportIndex.endPoint}"
												styleClass="colorblue2 p_5" style="width:100px;"></html:text>
										</td>
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
											票号
										</td>
										<td style="text-align: left">
											<html:text property="ticketNumber" name="platformReportIndex"
												value="${platformReportIndex.ticketNumber}"
												styleClass="colorblue2 p_5" style="width:100px;"></html:text>
										</td>
										<td class="lef">
											人数
										</td>
										<td style="text-align: left">
											<html:text property="passengerCount"
												name="platformReportIndex"
												value="${platformReportIndex.passengerCount}"
												styleClass="colorblue2 p_5" style="width:100px;"></html:text>
										</td>
										<td class="lef">
											结算类型
										</td>
										<td style="text-align: left">
											<html:text property="reportStatementType"
												name="platformReportIndex"
												value="${platformReportIndex.reportStatementType}"
												styleClass="colorblue2 p_5" style="width:100px;"></html:text>
										</td>
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


