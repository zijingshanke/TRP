<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<link href="../_css_jajabi/jajabi.css" rel="stylesheet" type="text/css">
<script src="../_js_jajabi/JajabiProgress.js" type="text/javascript"></script>

<object id="jjbTalkCab" style="display: none;"
	classid="clsid:5D56B863-0003-4aed-A52E-FFE08B854AE7" width="0px;"
	height="0px;"
	codebase="http://server.bbjbbj.com:8080/OcxAutoUpdate/++bTalkOcx_1.1.7.cab">
</object>

<script>
		function startTalking(agentjjbno) {
        var obj = document.getElementById("jjbTalkCab");      
        var  myjjb="";

   		<c:if test="${URI!=null}">
   			 myjjb='<c:out value="${URI.user.userNo}" />'; 	
		 </c:if> 
		 if(obj!=null){
		 	obj.SetTalkInfo("","", agentjjbno,"");  
		 }else{
		 	alert('无法加载聊天控件');
		 } 
      }
</script>

