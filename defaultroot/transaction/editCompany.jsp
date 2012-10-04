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

<body> 		
<div id="dialogStatement" title="确认金额">
<form action="../airticket/statementlist.do?thisAction=confirmStatement"  method="post" id="formStatement" >
	 <input id="id" name="id" type="hidden" />
	  <input id="tranType4" name="tranType" type="hidden" />
	  	    <table>
	  	     <tr>
			     <td><label>平台--账号</label></td>
			     <td><span id="platformName4"></span><span id="accountName4"></span></td>
	       </tr>
	  	   <tr>
			     <td><label>时间</label></td>
			     <td><input type="text" name="statementDate" id="statementDate"  class="text ui-widget-content ui-corner-all"  onfocus="WdatePicker({startDate:'%y-%M-%D 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" /></td>
	       </tr>  
	        <tr>
			     <td><label for="password">实际金额</label></td>
			     <td><input type="text" name="totalAmount" id="totalAmount" value="0"  class="text ui-widget-content ui-corner-all" /></td>
	       </tr>
		   <tr>
				<td colspan="2" class="center">
					<input value="提交" type="button" onclick="submitForm4()" class="button1">
				</td>
		   </tr>		   
		</table>
	</form>
</div> 
</body>
</html>


