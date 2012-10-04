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
		<style>
.divstyle {
	padding: 5px;
	font-family: "MS Serif", "New York", serif;
	background: #e5e5e5;
}
</style>
	</head>
	<script src="../_js/base/CalculateUtil.js" type="text/javascript"></script>
	<script type="text/javascript" src="../_js/base/FormUtil.js"></script>	
	<script type="text/javascript">		
		function add(){		
			var thisAction =document.forms[0].thisAction.value;	
			var checkType='<c:out value="${accountCheck.type}" />';
			
			if(thisAction!=null){
				if(thisAction=="checkOff"){
					var currentBalance=document.getElementById("currentBalance").innerHTML;	
					var checkOffAmount=document.forms[0].checkOffAmount.value;
			
					if(currentBalance!=checkOffAmount){
						var result=Math.abs(1*currentBalance-1*checkOffAmount);
						//alert(result)
						if(result>3){
							alert("下班金额与计算余额不相等，请核实");
							return false;
						}								
					}
				}
				if(confirm('确定提交吗？')){
					document.forms[0].action="<%=path%>/transaction/accountCheck.do?thisAction="+thisAction;
					trim(document.forms[0]);
		    		document.forms[0].submit();
				}				
			}else{
				alert("thisAction参数不能为空");
			} 
		}
		
		function onkeyBalance(){
			var checkType='<c:out value="${accountCheck.type}" />';
			var currentBalance="0";
			var checkOnAmount=document.getElementById("checkOnAmount").value;//上班
			if(checkType=='0'){				
				var transInAmount=document.getElementById("transInAmount").value;//转入
				var refundAmount=document.getElementById("refundAmount").value;//退回
				var transOutAmount=document.getElementById("transOutAmount").value;//转出
				var payAmount=document.getElementById("payAmount").value;//支付
				
				var inAmount=accAdd(checkOnAmount,transInAmount);
				inAmount=accAdd(inAmount,refundAmount);
				
				var outAmount=accAdd(transOutAmount,payAmount);
				
				currentBalance=Subtr(inAmount,outAmount);			
			}else{
				currentBalance=checkOnAmount;
			}			
			document.getElementById("currentBalance").innerHTML=currentBalance;		
		}
	</script>
	<body>	
	 <c:set var="title1" value="结算管理"/>
	<c:set var="title2" value="账户交接"/>	
	<c:choose>
		 <c:when test="${accountCheck.thisAction=='CheckOn'}">
		   <c:set var="title3" value="上班"/>
		 </c:when> 
		    <c:when test="${accountCheck.thisAction=='checkOff'}">
		     <c:set var="title3" value="下班"/>
		    </c:when> 
		    <c:when test="${accountCheck.thisAction=='update'}">
		     <c:set var="title3" value="编辑"/>
		    </c:when> 
	</c:choose>
		<html:form action="/transaction/accountCheck.do" method="post">
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
							<c:import url="../_jsp/mainTitle.jsp" charEncoding="UTF-8">
									<c:param name="title1" value="${title1}" />
									<c:param name="title2" value="${title2}" />							
									<c:param name="title3" value="${title3}" />												
							</c:import>
								<hr>
								<table width="100%" cellpadding="0" cellspacing="0" border="0"
									class="dataList">
									<tr>
										<td class="lef">
											账户
											<html:hidden property="id" name="accountCheck" value="${accountCheck.id}"></html:hidden>
											<html:hidden property="thisAccount" name="accountCheck" value="${accountCheck.thisAction}"></html:hidden>
										</td>
										<td style="text-align: left">
										<logic:equal value="checkOn" property="thisAction" name="accountCheck">
											<html:select property="accountId" name="accountCheck"
												styleClass="colorblue2 p_5" style="width:200px;">
												<c:forEach items="${accountList}" var="account">
													<html:option value="${account.id}">
														<c:out value="${account.name}" />
													</html:option>
												</c:forEach>
											</html:select>
										</logic:equal>
										<logic:equal value="checkOff" property="thisAction" name="accountCheck">
											<a
													href="<%=path%>/transaction/accountList.do?thisAction=viewAccountPage&accountId=<c:out value="${accountCheck.account.id}" />">
													<c:out value="${accountCheck.account.name}" /> </a>
										</logic:equal>
										<logic:equal value="update" property="thisAction" name="accountCheck">
											<html:select property="accountId" value="${accountCheck.account.id}" name="accountCheck"
												styleClass="colorblue2 p_5" style="width:200px;">
												<c:forEach items="${accountList}" var="account">
													<html:option value="${account.id}">
														<c:out value="${account.name}" />
													</html:option>
												</c:forEach>
											</html:select>
										</logic:equal>
										</td>
									</tr>
									<tr>
										<td class="lef">
											上班金额
										</td>
										<td style="text-align: left">
											<html:text property="checkOnAmount" name="accountCheck" styleId="checkOnAmount"
												value="${accountCheck.checkOnAmount}" onkeyup="onkeyBalance();"
												styleClass="colorblue2 p_5" style="width:200px;"></html:text>
										</td>
										<c:if test="${accountCheck.type==0 or accountCheck.type==1}">
										<td class="lef">
											转入金额
										</td>
										<td style="text-align: left">
											<html:text property="transInAmount" name="accountCheck" styleId="transInAmount"
												value="${accountCheck.transInAmount}" onkeyup="onkeyBalance();"
												styleClass="colorblue2 p_5" style="width:200px;"></html:text>
										</td>
										</c:if>
									</tr>
									<c:if test="${accountCheck.type==0 or accountCheck.type==1}">
									<tr>
										<td class="lef">
											转出金额
										</td>
										<td style="text-align: left">
											<html:text property="transOutAmount" name="accountCheck" styleId="transOutAmount"
												value="${accountCheck.transOutAmount}" onkeyup="onkeyBalance();"
												styleClass="colorblue2 p_5" style="width:200px;"></html:text>
										</td>
										<td class="lef">
											退回金额
										</td>
										<td style="text-align: left">
											<html:text property="refundAmount" name="accountCheck" styleId="refundAmount"
												value="${accountCheck.refundAmount}" onkeyup="onkeyBalance();"
												styleClass="colorblue2 p_5" style="width:200px;"></html:text>
										</td>
									</tr>
									</c:if>
									<tr>
										<c:if test="${accountCheck.type==0 or accountCheck.type==1}">
										<td class="lef">
											支付金额
										</td>
										<td style="text-align: left">
											<html:text property="payAmount" name="accountCheck" styleId="payAmount"
												value="${accountCheck.payAmount}" onkeyup="onkeyBalance();"
												styleClass="colorblue2 p_5" style="width:200px;"></html:text>
										</td>										
										<td class="lef">
											下班金额
										</td>
										<td style="text-align: left">										
											<html:text property="checkOffAmount" name="accountCheck" styleId="checkOffAmount"
												value="${accountCheck.checkOffAmount}" onkeyup="onkeyBalance();"
												styleClass="colorblue2 p_5" style="width:200px;"></html:text>										
										</td>
										</c:if>
									</tr>
									<tr>
										<td class="lef">
											正常余额
										</td>
										<td style="text-align: left">
											<span id="currentBalance" style="color: red">0</span>
										</td>
										<td class="lef">
											备注
										</td>
										<td style="text-align: left">
											<html:text property="note" name="accountCheck"
												value="${account.note}" styleClass="colorblue2 p_5"
												style="width:200px;"></html:text>
										</td>
									</tr>
									<logic:equal value="update" property="thisAction" name="accountCheck">
									<tr>
										<td class="lef">
											状态
										</td>
										<td style="text-align: left">

											<html:select property="status" name="accountCheck"
												styleClass="colorblue2 p_5" style="width:50px;">
												<html:option value="0">有效</html:option>
												<html:option value="1">无效</html:option>
											</html:select>
										</td>
									</tr>
									</logic:equal>
								</table>
								<table width="100%" style="margin-top: 5px;">
									<tr>
										<td>
											<html:hidden property="thisAction" name="accountCheck" />
											
											<c:check code="sf20"><input name="label" type="button" class="button1" value="保 存"
												onclick="add();">
												</c:check>
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
			<script>
			onkeyBalance();
			</script>
		</html:form>
	</body>
</html>