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
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" language="javascript"
			src="../_js/jquery-1.3.1.min.js"></script>
		<script src="../_js/common.js" type="text/javascript"></script>
	</head>

	<body>
		<div id="mainContainer">
			<div id="container">
				<table width="100%" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td width="10" height="10" class="tblt"></td>
						<td height="10" class="tbtt"></td>
						<td width="10" height="10" class="tbrt"></td>
					</tr>
					<tr>
						<td width="10" class="tbll"></td>
						<td valign="top" class="body">
							<table>
								<tr>
									<td>
										公告
										<hr />
									</td>
								</tr>
								<tr>
									<td>
										测试项目：
									</td>
								</tr>
								<tr>
									<td>
										1、票务订单管理所有操作流程
										<br />
										2、报表管理，销售报表、操作员收付款统计、团队销售报表、团队未返代理费报表
										<br />
										3、权限管理
										<br />
										详见
										<a target="_blank" href="./notice/CeShi20100926.doc">《公测工作安排》</a>
									</td>
								</tr>
								<tr style="display: none">
									<td>
										<hr />
										测试前请先阅读说明文件，重复问题请勿提交：
										<a target="_blank" href="./notice/20100926.doc">《操作说明》</a>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td width="10" class="tbrr"></td>
					</tr>
					<tr>
						<td width="10" class="tblb"></td>
						<td class="tbbb"></td>
						<td width="10" class="tbrb"></td>
					</tr>
				</table>
				<div class="clear"></div>
			</div>
		</div>
	</body>
</html>
