<%@ page contentType="text/html;charset=utf-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<c:if test="${URI==null}">
	<script language="JavaScript">
   	top.location="login.jsp" 
	</script>
</c:if>
<html>
  <head>

    <title>bottom</title>
  
   <link href="_css/global.css" rel="stylesheet" type="text/css" />
   
  </head>
  
  <body>
    <div align="center"> <br/>Copyright 2010-2020,珠海泰申发展有限公司.All Rights Reserved</div>
  </body>
</html>
