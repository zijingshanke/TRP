<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<html>
<head>
<title>泰申管理系统-票务政策管理</title>
<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
<link href="../_css/global.css" rel="stylesheet" type="text/css" />

	<script type="text/javascript" language="javascript" src="../_js/jquery-1.3.2.min.js"></script>
	<script type="text/javascript" language="javascript" src="../_js/goto.js"></script>
	<script type="text/javascript" language="javascript" src="../_js/menu.js"></script>
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
    <div class="sideBarItem webAdmin"> <span class="title"><a href="#">票务政策管理</a> </span>
      <ul class="contents">
      	<li> <a href="airlinePolicyAfterList.do?thisAction=list" target="mainFrame">后返政策列表</a> </li>
        <li> <a href="listPolicy.jsp" target="mainFrame">全部政策</a> </li>
        <li> <a href="../_jsp/inMarking.jsp" target="mainFrame">航空公司政策录入</a> </li>
        <li> <a href="../_jsp/inMarking.jsp" target="mainFrame">航空公司政策维护</a> </li>
        <li> <a href="../_jsp/inMarking.jsp" target="mainFrame">出票政策管理</a> </li>
        <li> <a href="../_jsp/inMarking.jsp" target="mainFrame">倒票政策管理</a> </li>
        <li> <a href="../_jsp/inMarking.jsp" target="mainFrame">政策分析</a> </li>
        <li> <a href="../airticket/listPlatLoginAccount.do?thisAction=list"
								target="mainFrame">平台登录帐号列表</a> </li>
        <li> <a href="test.jsp" target="mainFrame">政策抓取控件</a> </li>
         <li> <a href="../policy/listPolicyAfter.do?thisAction=list"
								target="mainFrame">平台登录帐号列表</a> </li>
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
