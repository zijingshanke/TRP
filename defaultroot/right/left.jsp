<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>left</title>
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
    <div class="sideBarItem webAdmin"><span class="title"><a
	href="#">权限管理</a></span>
<ul class="contents">
  <c:check code="se01,se02">
      <ul class="contents">
 <li><a href="rolelist.do?thisAction=sys" target="mainFrame"
		class="cWhite">用户角色</a></li>
		 <li><a href="rolelist.do?thisAction=user" target="mainFrame"
		class="cWhite">用户角色</a></li>
          <li><a href="rolelist.do?thisAction=editrole4user"
		target="mainFrame" class="cWhite">给用户授角色</a></li>
          <li><a href="rolelist.do?thisAction=edituser4role"
		target="mainFrame" class="cWhite">给角色分配用户</a></li>       
      </ul>
       </c:check>
    </div>
  </div>
  <div class="closeSiseBar"><span class="btn"></span></div>
</div>
<script type="text/javascript" language="javascript">
      initMenu("sideBar");
      		<c:if test="${URI==null}">
   	top.location="../login.jsp" 
</c:if>
</script>
</body>
</html>
