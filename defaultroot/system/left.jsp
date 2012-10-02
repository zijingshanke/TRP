<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<html>
	<head>
		<title>left</title>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div id="mainContainer">
			<div class="fixedSideBar"></div>
			<div id="sideBar">
				<div class="sideBarItem webAdmin">
					<span class="title"><a href="#">系统管理</a> </span>
					<ul class="contents">
						<li>
							<a href="loginloglist.do?thisAction=list&locate=2"
								target="mainFrame">登录日志</a>
						</li>
						<li>
							<a href="ticketloglist.do?thisAction=list" target="mainFrame">操作日志</a>
						</li>
					</ul>
				</div>
			</div>
			<div class="closeSiseBar">
				<span class="btn"></span>
			</div>
		</div>
	</body>
</html>
