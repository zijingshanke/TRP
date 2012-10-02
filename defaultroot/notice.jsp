<%@ page contentType="text/html;charset=utf-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<c:if test="${URI==null}">
	<script language="JavaScript">
   	top.location="login.jsp" 
	</script>
</c:if>
<html>
	<head>
		<title>泰申管理系统</title>
		<link href="_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="_css/global.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" language="javascript"
			src="_js/jquery-1.3.1.min.js"></script>
		<script src="_js/common.js" type="text/javascript"></script>
<script>
	function isSimplePass(){
		var a = "<%=request.getAttribute("inf")%>";
		if(a=="123456"){
			alert("您的密码过于简单，请修改----");
			//alert(<c:out value="${inf}"/>);
			document.location.href="user/user.do?thisAction=editMyPassword&userId=<c:out value='${URI.user.userId}'/>";
		}
	}
</script>
	</head>
	<body onload = "isSimplePass();">
	<input id="inf" type="hidden" value=<%=session.getAttribute("inf")+""%>>
		<div id="mainContainer">
			<div id="container">
				<table width="100%" cellpadding="0" cellspacing="0" border="0">
					<tr>						 
						<td valign="top" colspan="3">
							<c:import url="_jsp/mainTitle.jsp" charEncoding="UTF-8">
								<c:param name="title1" value="系统公告" />
							</c:import>
						</td>
					</tr>
					<tr>
						<td  colspan="3" style="display: none">
							<p>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2010年11月15日（下周一）
								<a href="http://tsms.fdays.com">泰申综合业务管理系统</a>散票部分正式上线
							</p>
							<p>
								从
								<font color="red">2010年11月15日0时起，旧财务系统的散票录单功能将停止使用</font>
							</p>
							<p>
								新订单一律进入新系统录单，这两天请大家抓紧时间测试，把问题充分报露出来。
							</p>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<a href="airticket/index.jsp">测试</a>
	</body>
</html>
