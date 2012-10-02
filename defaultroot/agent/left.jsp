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
</head>
<body>
<div id="mainContainer">
  <div class="fixedSideBar"></div>
  <div id="sideBar">
    <div class="sideBarItem webAdmin"> <span class="title"><a href="#">客户管理</a> </span>
      <ul class="contents">
        <c:check code="sd04">
          <li> <a href="../transaction/agentList.do?thisAction=list"
								target="mainFrame">全部客户</a> </li>
          <li> <a href="../transaction/agentList.do?thisAction=getB2CAgentlist" target="mainFrame">B2C客户列表</a> </li>
          <li> <a href="../transaction/agentList.do?thisAction=getTeamAgentlist" target="mainFrame">团队客户列表</a> </li>
          <li> <a href="../transaction/companyList.do?thisAction=getClient" target="mainFrame">客户公司列表</a> </li>
        </c:check>
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
