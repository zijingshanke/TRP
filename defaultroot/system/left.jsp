<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<html>
	<head>
		<title>left</title>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />

		<script type="text/javascript" language="javascript"
			src="../_js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" language="javascript"
			src="../_js/goto.js"></script>
		<script type="text/javascript" language="javascript"
			src="../_js/menu.js"></script>
		<c:if test="${URI==null}">
			<script language="JavaScript">
   	top.location="../login.jsp" 
	</script>
		</c:if>
	</head>
	<body>
		<div id="mainContainer">
			<div class="fixedSideBar"></div>
			<div id="sideBar">
				<div class="sideBarItem webAdmin">
					<span class="title"><a href="#" onclick="showUL('ulLog')">日志管理</a>
					</span>
					<ul class="contents" id="ulLog" style="display: none">
						<li>
							<a href="../system/loginloglist.do?thisAction=list&locate=2"
								target="mainFrame">登录日志</a>
						</li>
						<li>
							<a href="../system/ticketloglist.do?thisAction=list"
								target="mainFrame">操作日志</a>
						</li>
					</ul>

					<span class="title"><a href="#"
						onclick="showUL('ulPCAccount')">平台账号管理</a> </span>
					<ul class="contents" id="ulPCAccount" style="display: none">
						<c:check code="sf11">
							<li>
								<a href="../transaction/platComAccountList.do?thisAction=list"
									target="mainFrame">平台账号列表</a>
							</li>
						</c:check>
						<c:check code="sf12">
							<li>
								<a href="../transaction/platformList.do?thisAction=list"
									target="mainFrame">交易平台列表</a>
							</li>
						</c:check>
						<c:check code="sf13">
							<li>
								<a href="../transaction/companyList.do?thisAction=list"
									target="mainFrame">集团旗下公司列表</a>
							</li>
						</c:check>
						<c:check code="sf14">
							<li>
								<a href="../transaction/paymentToolList.do?thisAction=list"
									target="mainFrame">支付工具列表</a>
							</li>
						</c:check>
						<c:check code="sf15">
							<li>
								<a href="../transaction/accountList.do?thisAction=list"
									target="mainFrame">账号列表</a>
							</li>
						</c:check>
					</ul>


					<span class="title"><a href="#"
						onclick="showUL('ulEditAirline')">航线折扣管理</a> </span>
					<ul class="contents" id="ulEditAirline" style="display: none">
						<c:check code="sa05">
							<li>
								<a href="../airticket/airlinelist.do?thisAction=list"
									target="mainFrame">航线列表</a>
							</li>
						</c:check>
						<c:check code="sa06">
							<li>
								<a href="../airticket/airlinePlacelist.do?thisAction=list"
									target="mainFrame">舱位折扣列表</a>
							</li>
						</c:check>
					</ul>

					<span class="title"><a href="#"
						onclick="showUL('ulEditReportCompare')">报表对比管理</a> </span>
					<ul class="contents" id="ulEditReportCompare">
						<li>
							<a
								href="../transaction/platformReportIndexList.do?thisAction=list"
								target="mainFrame">平台报表字段索引</a>
						</li>
					</ul>

					<span class="title"><a href="#"
						onclick="showUL('ulEditStatementManage')">结算数据管理</a> </span>
					<ul class="contents" id="ulEditStatementManage">
						<li>
							<a
								href="../transaction/listStatement.do?thisAction=listStatementManage"
								target="mainFrame">更新订单表结算金额</a>
						</li>					
					</ul>
				</div>
			</div>
			<div class="closeSiseBar">
				<span class="btn"></span>
			</div>
		</div>
		<script type="text/javascript" language="javascript">
      initMenu("sideBar");
      		<c:if test="${URI==null}">
   	top.location="../login.jsp" 
</c:if>
</script>
	</body>
</html>
