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
<title>填写备注</title>
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
  document.forms[0].memo.value=document.forms[0].memo.value.trim();
  document.forms[0].submit();  
}

</script>
</head>	

<body> 		
<div>
<html:form action="/airticket/airticketOrder.do?thisAction=updateOrderMemo"  method="post">
	 <html:hidden property="id" name="order"/>
	  <table width="100%" cellpadding="0" cellspacing="0" border="0"
								class="dataList">
	  	   <tr>
			     <td>流水号</td>
			     <td><c:out value="${order.orderNo}"/></td>
	       </tr>  
	         	      <tr>
			     <td>订单号</td>
			     <td><c:out value="${order.airOrderNo}"/></td>
	       </tr>   
 
           <tr>
			     <td><label for="password">备注</label></td>
			     <td><div align="left"><html:text property="memo" name="order" size="40" styleClass="text ui-widget-content ui-corner-all" /></div></td>
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




</script>
</body>
</html>


