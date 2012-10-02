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
		<title>platformCompareManage</title>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />
		<script src="../_js/common.js" type="text/javascript"></script>
		<script src="../_js/calendar/WdatePicker.js" type="text/javascript"></script>
	</head>	
	<frameset rows="200px,100px,*" frameborder="NO" border="0"
		framespacing="0">
		<frame src="<%=path%>/report/platformCompareToolBar.jsp" name="searchFrame" noresize>
		<frame src="<%=path%>/report/listProblemCompare.jsp" name="problemFrame" noresize>
		<frameset rows="*" frameborder="NO" border="0" framespacing="0">
			<frameset id="compareLists" cols="50%,50%" frameborder="NO" border="0"
				framespacing="0">
				<frame style="width: 100%" src="<%=path%>/report/listReportCompare.jsp"
					name="reportFrame" noresize>
				<frame style="width: 100%" src="<%=path%>/report/listOrderCompare.jsp"
					name="orderFrame">
			</frameset>
		</frameset>
	</frameset>
	<noframes>
		<body>
		</body>
</html>
