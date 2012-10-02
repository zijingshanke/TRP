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
<title>确认金额</title>
<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
<link href="../_css/global.css" rel="stylesheet" type="text/css" />
<script src="../_js/common.js" type="text/javascript"></script>
<script src="../_js/prototype.js" type="text/javascript"></script>
<script src="../_js/calendar/WdatePicker.js" type="text/javascript"></script>
<script>

function closex()
{
 window.opener=null 
 window.open("","_self") 
 window.close(); 
}

function submitForm()
{
  document.forms[0].totalAmount.value=document.forms[0].totalAmount.value.trim();
  if(!isNumber(document.forms[0].totalAmount.value))
  {
    alert("金额不是合法的数字！");  
  }
  else if(document.forms[0].statementDate.value=="")
  {
    alert("时间不对！");  
  }
  else if(document.getElementById("accountId").value<0)
  {
    alert("帐号不正确！");  
  }
  else if(confirm("您确认收退款吗？并确认收款日期，收款金额无误！"))
  {
    document.forms[0].memo.value=document.forms[0].memo.value.trim();
    document.forms[0].submit();  
  }

}

</script>
</head>	

<body> 		
<div>
<html:form action="/transaction/statement.do?thisAction=confirmStatement"  method="post">
	 <html:hidden property="id" name="statement"/>
	  <table width="100%" cellpadding="0" cellspacing="0" border="0"
								class="dataList">
	  	      <tr>
			     <td>结算单号</td>
			     <td><c:out value="${statement.statementNo}"/></td>
	       </tr>  
	       <tr>
			     <td>平台--账号</td>
			     <td>
    		     <c:if test="${statement.type==1}">
			         <html:select property="toAccountId" value="${statement.toAccount.id}" styleId="accountId" name="statement" styleClass="colorblue2 p_5"
													style="width:150px;">
					<c:forEach items="${accountList}" var="o">
					<html:option value="${o.account.id}"><c:out value="${o.account.name}"/></html:option>
					</c:forEach>			 
				</html:select>
				</c:if>
				
				<c:if test="${statement.type==2}">
				  <html:select property="fromAccountId" value="${statement.fromAccount.id}" styleId="accountId" name="statement" styleClass="colorblue2 p_5"
												style="width:150px;">
				<c:forEach items="${accountList}" var="o">
				<html:option value="${o.account.id}"><c:out value="${o.account.name}"/></html:option>
				</c:forEach>			 
				</html:select>
				</c:if>	
				 </td>
	       </tr>
	  	   <tr>
			     <td><label>时间</label></td>
			     <td><html:text property="statementDate"  name="statement" styleClass="text ui-widget-content ui-corner-all"  onfocus="WdatePicker({startDate:'%y-%M-%D 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" /></td>
	       </tr>  
	        <tr>
			     <td><label for="password">实际金额</label></td>
			     <td><html:text property="totalAmount"    name="statement" styleClass="text ui-widget-content ui-corner-all" /></td>
	       </tr>

	       <tr>
			     <td>结算状态</td>
			     <td><html:radio property="status" value="0"  name="statement" />未结算
			     <html:radio property="status" value="1"  name="statement" />已结算
			     <html:radio property="status" value="8"  name="statement" />已确认结算,不再修改
			     </td>
	       </tr>
	       	        <tr>
			     <td><label for="password">备注</label></td>
			     <td><html:text property="memo"    name="statement" styleClass="text ui-widget-content ui-corner-all" /></td>
	       </tr>
		   <tr>
				<td colspan="2" class="center">
					<input value="提 交" type="button" onclick="submitForm()" class="button1">
					<input value="关 闭" type="button" onclick="closex();" class="button1">
				</td>
		   </tr>		   
		</table>
	</html:form>
</div>
<script>
var type='<c:out value="${statement.type}"/>';



</script>
</body>
</html>


