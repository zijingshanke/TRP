<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>


<c:if test="${URI==null}">
	<script language="JavaScript">
   	top.location="../login.jsp" 
	</script>
</c:if>
<%
	String title1 = request.getParameter("title1");	 
	if (title1  == null)
	title1 = "";
	String title2 = request.getParameter("title2");
	if (title2 == null)
	title2 = "";		
		
	String title3 = request.getParameter("title3");
	if (title3  == null)
	title3 = "";		
%>

<style>
.divstyle {
	padding: 5px;
	font-family: "MS Serif", "New York", serif;
	background: #e5e5e5;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<div class="divstyle"><%=title1%>
	&gt;&gt;
	<%=title2%>
	<%if (!title3.equals("")){%>
	&gt;&gt;
	<font color="red"><%=title3%></font>
	<%}%>
</div>

