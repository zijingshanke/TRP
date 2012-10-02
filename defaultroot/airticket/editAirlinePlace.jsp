<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<html>
	<head>
		<title>main</title>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" language="javascript"
			src="../_js/jquery-1.3.2.min.js"></script>
		<script src="../_js/common.js" type="text/javascript"></script>

		<script>
String.prototype.Trim   =   function()  {   
  return   this.replace(/(^\s*)|(\s*$)/g,   "");   
  }
function addAirlinePlace(){
	var company = document.forms[0].company.value.Trim();
	var code = document.forms[0].code.value.Trim();
	var rate=document.forms[0].rate.value.Trim();
	if(company.length==0||code.length==0||rate.length==0){
		alert("请填写完整信息!");
		return false;
	}else if(company.length>2){
		alert("请正确填写承运人代码");
		return false;
	}else{
		var thisaction=document.forms[0].thisAction.value;
		document.forms[0].action="airlinePlace.do?thisAction="+thisaction+"";
    	document.forms[0].submit();
    }
}
</script>
	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
				<html:form action="/airticket/airlinePlace.do">
					<html:hidden property="thisAction" name="airlinePlace" />
					<html:hidden property="id" name="airlinePlace" />
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
									<c:param name="title1" value="票务管理" />
									<c:param name="title2" value="编辑舱位折扣" />
								</c:import>
								<hr>
								<table width="100%" cellpadding="0" cellspacing="0" border="0"
									class="dataList">
									<tr>
										<td class="lef">
											承运人
										</td>
										<td style="text-align: left">
											<html:text property="company" name="airlinePlace"
												styleClass="colorblue2 p_5" />
											<font color="red">*</font>
										</td>
									</tr>
									<tr>
										<td class="lef">
											舱位
										</td>
										<td style="text-align: left">
											<html:text property="code" name="airlinePlace"
												styleClass="colorblue2 p_5" />
											<font color="red">*</font>
										</td>
									</tr>
									<tr>
										<td class="lef">
											折扣
										</td>
										<td style="text-align: left">
											<html:text property="rate" name="airlinePlace"
												styleClass="colorblue2 p_5" />
											<font color="red">*</font>
										</td>
									</tr>
									<tr>
										<td class="lef">
											特殊航线
										</td>
										<td style="text-align: left">
											<logic:equal value="insert" property="thisAction"
												name="airlinePlace">
												<html:select property="airlineId" name="airlinePlace"
													styleClass="colorblue2 p_5" style="width:150px;">
													<html:option value="0">-请选择-</html:option>
													<c:forEach items="${airlineList}" var="airline">
														<html:option value="${airline.id}">
															<c:out value="${airline.showName}" />
														</html:option>
													</c:forEach>
												</html:select>
											</logic:equal>
											<logic:equal value="update" property="thisAction"
												name="airlinePlace">
												<html:select property="airlineId" name="airlinePlace"
													styleId="airlineId" styleClass="colorblue2 p_5"
													style="width:150px;">
													<html:option value="0">-请选择-</html:option>
													<c:forEach items="${airlineList}" var="airline">
														<html:option value="${airline.id}">
															<c:out value="${airline.showName}" />
														</html:option>
													</c:forEach>
												</html:select>
											</logic:equal>
										</td>
									</tr>
									<tr>
										<td class="lef">
											状态
										</td>
										<td style="text-align: left">
											<html:radio property="status" value="1" name="airlinePlace">启用</html:radio>
											<html:radio property="status" value="2" name="airlinePlace">停用</html:radio>
										</td>
									</tr>
								</table>
								<table width="100%" style="margin-top: 5px;">
									<tr>
										<td>
											<c:check code="sa06">
												<input name="label" type="button" class="button1"
													value="提 交" onclick="addAirlinePlace();">
												<input name="label" type="button" class="button1"
													value="重 置" onclick="document.airlinePlace.reset();">
												<input name="label" type="button" class="button1" value="取消"
													onclick="javascript:history.back();">
											</c:check>

										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</html:form>
			</div>
		</div>
		<script>
											var airline='<c:out value="${airlinePlace.airline.id}" />';
											//alert(airline)
											if(airline!=null){
												var selects=document.getElementById('airlineId');
												for(var i=0;i<selects.length;i++)
												{
												   if(selects[i].value==airline)  {
												        selects.selectedIndex=i;
												        break;
												   }
												}
											}
											</script>
	</body>
</html>
