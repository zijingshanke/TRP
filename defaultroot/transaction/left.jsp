<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<html>
	<head>
	<title>left</title>
	<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
	<link href="../_css/global.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" language="javascript" src="../_js/jquery-1.3.2.min.js"></script>
	<script type="text/javascript" language="javascript" src="../_js/goto.js"></script>
	<script type="text/javascript" language="javascript" src="../_js/menu.js"></script>
	<script type="text/javascript" language="javascript">
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
    <div class="sideBarItem webAdmin"> <span class="title"><a href="#"
						onclick="showUL('ulStatement')">结算管理</a> </span>
          <ul class="contents" id="ulStatement">
        <li> <a href="accountCheckList.do?thisAction=list" target="mainFrame">账户上下班交接</a> </li>
        <c:check code="sf01">
              <li> <a
								href="listStatement.do?thisAction=listStatementOut&status1=1,2"
								target="mainFrame">结算列表</a> </li>
            </c:check>
        <c:check code="sf02"> 
              <!-- 
						<li>
							<a href="listStatement.do?thisAction=list&status1=0"
								target="mainFrame">未结算列表</a>
						</li>
						 --> 
            </c:check>
        <c:check code="sf03">
              <li> <a href="accountList.do?thisAction=listAccountBanlance"
								target="mainFrame">帐号余额查询</a> </li>
            </c:check>
      </ul>
          <span class="title"><a href="#"
						onclick="showUL('ulPCAccount')">基本设置</a> </span>
          <ul class="contents" id="ulPCAccount" style="display: none">
        <c:check code="sf11">
              <li> <a href="platComAccountList.do?thisAction=list"
								target="mainFrame">平台帐号管理</a> </li>
            </c:check>
        <c:check code="sf12">
              <li> <a href="platformList.do?thisAction=list" target="mainFrame">交易平台列表</a> </li>
            </c:check>
        <c:check code="sf13">
              <li> <a href="companyList.do?thisAction=list" target="mainFrame">集团旗下公司列表</a> </li>
            </c:check>
        <c:check code="sf14">
              <li> <a href="paymentToolList.do?thisAction=list" target="mainFrame">支付工具列表</a> </li>
            </c:check>
        <c:check code="sf15">
              <li> <a href="accountList.do?thisAction=list" target="mainFrame">帐号列表</a> </li>
            </c:check>
        <!-- 
						<li>
							<a href="../transaction/testDWR.jsp" target="mainFrame">DWR测试</a>
						</li>
						 -->
      </ul>
        </div>
  </div>
      <div class="closeSiseBar"> <span class="btn"></span> </div>
    </div>
<script type="text/javascript" language="javascript">
      initMenu("sideBar");
</script>
</body>
</html>
