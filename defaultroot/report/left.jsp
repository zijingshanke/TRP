<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<html>
	<head>
		<title>泰申管理系统</title>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />
		<script>
		function showUL(ulId){
			var selectedUL=document.getElementById(ulId);
			if(selectedUL.style.display==""){
				selectedUL.style.display="none";
			}else{
				selectedUL.style.display="";
			}		
		}
	</script>
	</head>
	<body>
		<div id="mainContainer">
			<div class="fixedSideBar"></div>
			<div id="sideBar">
				<div class="sideBarItem webAdmin">
					<span class="title"><a href="#"
						onclick="showUL('ulBusiness')">业务报表</a> </span>
					<ul class="contents" id="ulBusiness">
						<c:check code="sg01">
						<li>
							<a href="marketReports.jsp" target="mainFrame">原始销售报表</a>
						</li>
						</c:check>
						<c:check code="sg02">
						<li>
							<a href="../report/listReports.do?thisAction=loadSaleReports"
								target="mainFrame">销售报表</a>
						</li>
						</c:check>
						<li>
							<a href="../report/listReports.do?thisAction=retireReports"
								target="mainFrame">退废报表</a>
						</li>
						<c:check code="sg03">
						<li>
							<a href="../report/listOpTtransaction.jsp" target="mainFrame">操作员收付款统计</a>
						</li>
						</c:check>
						<c:check code="sg04">
						<li>
							<a href="../_jsp/inMarking.jsp" target="mainFrame">平台销售统计报表</a>
						</li>
						</c:check>
						<c:check code="sg05">
						<li>
							<a href="../_jsp/inMarking.jsp" target="mainFrame">营业销售统计报表</a>
						</li>
						</c:check>
						<c:check code="sg06">
						<li>
							<a href="../_jsp/inMarking.jsp" target="mainFrame">散客统计报表</a>
						</li>
						</c:check>
						<c:check code="sg07">
						<li>
							<a href="../report/teamAirTicketOrder.jsp" target="mainFrame">团队销售统计报表</a>
						</li>
						</c:check>
						<c:check code="sg08">
						<li>
							<a href="../report/teamNotReturnProxy.jsp" target="mainFrame">团队未返代理费报表</a>
						</li>
						</c:check>
					</ul>
					<span class="title"><a href="#"
						onclick="showUL('ulFinance')">财务报表</a> </span>
					<ul class="contents" id="ulFinance" style="display: none">
						<c:check code="sg11">
						<li>
							<a
								href="../transaction/bankCardPaymentList.do?thisAction=getBankCardPaymentlist"
								target="mainFrame">银行卡付款统计表</a>
						</li>
						</c:check>
						<c:check code="sg12">
						<li>
							<a
								href="../transaction/platformbankCardPaymentList.do?thisAction=getPlatformbankCardPaymentlist"
								target="mainFrame">平台银行交易统计表</a>
						</li>
						</c:check>
						<c:check code="sg13">
						<li>
							<a href="saleReports.jsp" target="mainFrame">销售报表</a>
						</li>
						</c:check>
						<c:check code="sg14">
						<li>
							<a href="../_jsp/inMarking.jsp" target="mainFrame">退废报表</a>
						</li>
						</c:check>
						<c:check code="sg15">
						<li>
							<a href="../_jsp/inMarking.jsp" target="mainFrame">对比报表</a>
						</li>
						</c:check>
					</ul>
				</div>
			</div>
			<div class="closeSiseBar">
				<span class="btn"></span>
			</div>
		</div>
	</body>
</html>
