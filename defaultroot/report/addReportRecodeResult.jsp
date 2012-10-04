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
		<script src="../_js/common.js" type="text/javascript"></script>
		<script src="../_js/base/FormUtil.js" type="text/javascript"></script>
		<script src="../_js/calendar/WdatePicker.js" type="text/javascript"></script>
		<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" src="../_js/base/DateUtil.js"></script>
	</head>
	<script type="text/javascript">			
		function add(){	
			if(checkForm()){
				var reportDate = document.forms[0].reportDate;
				if(reportDate.value.length <15){
					reportDate.value = reportDate.value+" 00:00:00"	
				}
							
				var thisAction =document.forms[0].thisAction;	
				if(thisAction==null){
					alert("thisAction不能为空");
					return false;
				}else{
					if(thisAction.value==''){
						alert("thisAction不能为空");
						return false;
					}
				 	document.forms[0].action="<%=path%>/transaction/reportRecodeResult.do?thisAction="+thisAction.value;
			    	document.forms[0].submit();
				} 
			}
			
		}
		
		function iniReportDate(){
			var reportDate = document.forms[0].reportDate;
			reportDate.value = (reportDate.value).substring(0,10);
		}
	</script>
	<body onload="iniReportDate();";>
		<c:import url="../_jsp/mainTitle.jsp" charEncoding="UTF-8">
			<c:param name="title1" value="报表对比管理" />
			<c:param name="title2" value="新增导入报表" />
		</c:import>
		<html:form action="/transaction/reportRecodeResult.do" method="post">
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
											日期
										</td>
										<td style="text-align: left">
											<html:text property="reportDate" name="reportRecodeResult"
												styleId="reportDate" styleClass="colorblue2 p_5" style="width:120px;"	readonly="true" 
												onfocus="WdatePicker({startDate:'%y-%M-%D',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"
												/>
										</td>
									</tr>
									<tr>
										<td class="lef">
											报表类型
										</td>
										<td style="text-align: left">
											<html:select property="reportType" name="reportRecodeResult"
												styleClass="colorblue2 p_5" style="width:100px;"
												onchange="showSelectObj()">
												<html:option value="1">平台</html:option>
												<html:option value="2">支付工具</html:option>
											</html:select>
										</td>
									</tr>
									<tr>
										<td class="lef">
											备注
										</td>
										<td style="text-align: left">
											<html:text property="memo" name="reportRecodeResult"
												styleClass="colorblue2 p_5" style="width:220px;">
											</html:text>
										</td>
									</tr>
									<tr>
										<td class="lef">
											状态
										</td>
										<td style="text-align: left">
											<html:select property="status" name="reportRecodeResult"
												styleClass="colorblue2 p_5" style="width:50px;">
												<html:option value="1">有效</html:option>
												<html:option value="0">无效</html:option>
											</html:select>
										</td>
									</tr>
									<tr>

									</tr>
								</table>
								<table width="100%" style="margin-top: 5px;">
									<tr>
										<td align="center">
											<html:hidden property="id"></html:hidden>
											<html:hidden property="thisAction" name="reportRecodeResult" />
											<html:hidden property="fileName" name="reportRecodeResult" />

											<input name="label" type="button" class="button1" value="返 回"
												onclick="window.history.back();">
											<input name="label" type="reset" class="button1" value="重 置">
											<input name="label" type="button" class="button1" value="提交"
												onclick="add();">
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
		<script type="text/javascript">
		function checkForm(){		
			var reportDate=document.getElementById("reportDate").value;			
			//alert(reportDate)
			if(reportDate == null|| reportDate ==""){
				alert("请选择报表日期")
				return false;
			}			
			//var fileName=document.forms[0].fileName.value;
			//if(fileName==""){
				//alert("请选择并上传报表文件")
				//return false;
			//}				
			return true;
		}	
		</script>
	</body>
</html>