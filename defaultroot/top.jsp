<%@ page contentType="text/html;charset=utf-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>


<c:if test="${URI==null}">
	<script language="JavaScript">
   	top.location="./login.jsp" 
	</script>
</c:if>

<html>
	<head>
		<title>top</title>
		<link href="_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="_css/global.css" rel="stylesheet" type="text/css" />

		<script type="text/javascript" language="javascript"
			src="./_js/jquery-1.3.2.min.js"></script>
	</head>
	<body>
		<div id="header">
			<div class="logo">
				Logo
			</div>
			<div class="mainNav">
				<ul class="navContent">
					<li>
						<a href="left.jsp" target="leftFrame">首页</a>
					</li>
					<c:check code="sb01-sb12">
					<li>
						<a href="airticket/left.jsp" target="leftFrame">票务订单管理</a>
					</li>
					</c:check>					
					<li>
						<a href="policy/left.jsp" target="leftFrame">票务政策管理</a>
					</li>
					<c:check code="sd01-sd03">
					<li>
						<a href="agent/left.jsp" target="leftFrame">客户管理</a>
					</li>	
					</c:check>	
					<c:check code="sf01-sf16">			
					<li>
						<a href="transaction/left.jsp" target="leftFrame">结算管理</a>
					</li>
					</c:check>
					<c:check code="sg01-sg15">	
					<li>
						<a href="report/left.jsp" target="leftFrame">报表管理</a>
					</li>
					</c:check>
					<c:check code="sh01-sh04">	
					<li>
						<a href="user/left.jsp" target="leftFrame">用户管理</a>
					</li>
					</c:check>
					<c:check code="se01-se02">	
					<li>
						<a href="right/left.jsp" target="leftFrame">权限管理</a>
					</li>
					</c:check>
					<li>
						<a href="system/left.jsp" target="leftFrame">系统管理</a>
					</li>

				</ul>

				<ul class="userPanel">
					<li>
						欢迎：
						<FONT color="red"><c:out value="${URI.user.userName}" /> </FONT>
					</li>
					<li>
						<a href="user/user.do?thisAction=exit">退出</a>
					</li>
				</ul>
			</div>
		</div>
	</body>
</html>
