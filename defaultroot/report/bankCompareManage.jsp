<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
	<head>
		<title>银行(支付平台)报表对比</title>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />
		<script src="../_js/common.js" type="text/javascript"></script>

		<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
		<script src="../_js/calendar/WdatePicker.js" type="text/javascript"></script>
		<style type="text/css">
#divSearchBarTool {
	margin-bottom: 5px;
}

#divProblemCompareList1 {
	float: left;
	width: 700px;
}

#divProblemCompareList2 {
	margin-left: 700px;
}

#divReportCompareList {
	width: 700px;
	float: left;
}

#divOrderCompareList {
	margin-left: 700px;
}

#dyfoot {
	margin-top: 5px;
	clear: both;
}
/*
div {
	color: #363636;
	background-color: #eee;
	border: 1px dashed #630;
}
*/
</style>
	</head>
	<body>
		<div id="divSearchBarTool">
			<jsp:include page="./bankCompareToolBar.jsp?compareType=Bank"></jsp:include>
		</div>
		<div id="divProblemCompareList1">
			<jsp:include page="./listProblemCompare1.jsp?compareType=Bank&showOperate=Yes"></jsp:include>
		</div>
		<div id="divProblemCompareList2">
			<jsp:include page="./listProblemCompare2.jsp?compareType=Bank&showOperate=Yes"></jsp:include>
		</div>
		<div id="divReportCompareList">
			<jsp:include page="./listReportCompare.jsp?compareType=Bank"></jsp:include>
		</div>
		<div id="divOrderCompareList">
			<jsp:include page="./listOrderCompare.jsp?compareType=Bank"></jsp:include>
		</div>
		<div id="dyfoot">
			<!-- 底部 -->
		</div>
	</body>
</html>
