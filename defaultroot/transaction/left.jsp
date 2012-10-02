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
					<span class="title"><a href="#">结算管理</a> </span>
					<ul class="contents">
						<li>
							<!-- 
							<a href="listStatement.do?thisAction=list" target="mainFrame">结算列表</a>
							-->
							<a
								href="listStatement.do?thisAction=getStatementListByStatus&status1=1,2"
								target="mainFrame">结算列表</a>
						</li>
						<li>
							<a
								href="listStatement.do?thisAction=list&status1=0"
								target="mainFrame">未结算列表</a>
						</li>
						<li>
							<a
								href="accountList.do?thisAction=listAccountBanlance"
								target="mainFrame">帐号余额查询</a>
						</li>
						<li>
							<a href="platComAccountList.do?thisAction=list"
								target="mainFrame">平台帐号管理</a>
						</li>
						<li>
							<a href="platformList.do?thisAction=list" target="mainFrame">平台列表</a>
						</li>
						<li>
							<a href="companyList.do?thisAction=list" target="mainFrame">公司列表</a>
						</li>
						<li>
							<a href="paymentToolList.do?thisAction=list" target="mainFrame">支付工具列表</a>
						</li>
						<li>
							<a href="accountList.do?thisAction=list" target="mainFrame">帐号列表</a>
						</li>
						<!-- 
						<li>
							<a href="../transaction/testDWR.jsp" target="mainFrame">DWR测试</a>
						</li>
						 -->
					</ul>
				</div>
			</div>
			<div class="closeSiseBar">
				<span class="btn"></span>
			</div>
		</div>
	</body>
</html>
