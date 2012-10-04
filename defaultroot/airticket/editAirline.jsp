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
function addAirline(){
	var begin = document.forms[0].begin.value.Trim();
	var end = document.forms[0].end.value.Trim();
	var price=document.forms[0].price.value.Trim();
	if(begin.length==0||end.length==0||price.length==0){
		alert("请填写完整信息!");
		return false;
	}else{
		var thisaction=document.forms[0].thisAction.value;
		document.forms[0].action="airline.do?thisAction="+thisaction+"";
    	document.forms[0].submit();
    }
}
</script>
	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
				<html:form action="/airticket/airline.do">
					<html:hidden property="thisAction" name="airline" />
					<html:hidden property="id" name="airline" />
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
									<c:param name="title2" value="编辑航线信息" />																			
								</c:import>	
								<hr>
								<table width="100%" cellpadding="0" cellspacing="0" border="0"
									class="dataList">
									<tr>
										<td class="lef">
											出发地
										</td>
										<td style="text-align: left">
											<html:text property="begin" name="airline"
												styleClass="colorblue2 p_5" />
											<font color="red">*</font>
										</td>
									</tr>
									<tr>
										<td class="lef">
											目的地
										</td>
										<td style="text-align: left">
											<html:text property="end" name="airline"
												styleClass="colorblue2 p_5" />
											<font color="red">*</font>
										</td>
									</tr>
									<tr>
										<td class="lef">
											Y舱全价
										</td>
										<td style="text-align: left">
											<html:text property="price" name="airline"
												styleClass="colorblue2 p_5" />
											<font color="red">*</font>
										</td>
									</tr>
										<tr>
										<td class="lef">
											里程
										</td>
										<td style="text-align: left">
											<html:text property="distance" name="airline"
												styleClass="colorblue2 p_5" />
											<font color="red">*</font>
										</td>
									</tr>
									
									
									<tr>
										<td class="lef">
											状态
										</td>
										<td style="text-align: left">
											<html:radio property="status" value="1" name="airline">启用</html:radio>
											<html:radio property="status" value="2" name="airline">停用</html:radio>
										</td>
									</tr>									
								</table>
								<table width="100%" style="margin-top: 5px;">
									<tr>
										<td>
										<c:check code="sa05">
											<input name="label" type="button" class="button1" value="提 交"
												onclick="addAirline();">
												
											<input name="label" type="button" class="button1" value="重 置"
												onclick="document.airline.reset();">
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
	</body>
</html>
