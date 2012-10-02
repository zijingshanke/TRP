<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<html>
	<head>
		<title>main</title>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" language="javascript"
			src="../_js/jquery-1.3.2.min.js"></script>
		<script src="../_js/common.js" type="text/javascript"></script>

		<script>
			function addPolicyAfter()
			{
			    document.forms[0].thisAction.value="add";
			    document.forms[0].submit();
			}
			
			function editPolicyAfter()
			{
			 if(document.forms[0].selectedItems==null){
			   	alert("没有数据，无法操作！");
			  }else if (sumCheckedBox(document.forms[0].selectedItems)<1){
			    alert("您还没有选择记录！");
			   }
			  else if (sumCheckedBox(document.forms[0].selectedItems)>1){
			    alert("您一次只能选择一个记录！");
			  }
			  else{
			    document.forms[0].thisAction.value="edit";
			    document.forms[0].submit();
			  }
			}
			
			function delPolicyAfter(){
			
			 if(document.forms[0].selectedItems==null){
			  	alert("没有数据，无法操作！");
			  }else if (sumCheckedBox(document.forms[0].selectedItems)<1){
			    alert("您还没有选择记录！");
			  }
			  else if(confirm("您真的要删除选择的这些记录吗？")){
			    document.forms[0].thisAction.value="delete";
			    document.forms[0].submit();
			  }
			}
			
			function backToListAirlinePoliceAfter(){
				document.location.href="airlinePolicyAfterList.do?thisAction=list";
			}
		
		</script>
	</head>
	<body>		
		<div id="mainContainer">
			<div id="container">
				<html:form action="/policy/policyAfterList.do">
					<html:hidden property="thisAction" />
					<html:hidden property="lastAction" />
					<html:hidden property="intPage" />
					<html:hidden property="pageCount" />
					<html:hidden property="airlinePolicyAfterId" name="policyAfter" value="${palf.airlinePolicyAfterId}"/>
					<table width="100%" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td width="10" height="10" class="tblt"></td>
							<td height="10" class="tbtt"></td>
							<td width="10" height="10" class="tbrt"></td>
						</tr>
						<tr>
							<td width="10" class="tbll"></td>
							<td valign="top" class="body">
								<c:import url="../_jsp/mainTitle.jsp" charEncoding="UTF-8">
									<c:param name="title1" value="政策管理" />
									<c:param name="title2" value="后返政策列表" />																						
							</c:import>
								<div class="searchBar">
									<p>
										搜索栏
									</p>
									<hr />

									<table cellpadding="0" cellspacing="0" border="0"
										class="searchPanel">
										<tr>
											
											<td>
												操作人：
												<input type="text" name="userName" class="colorblue2 p_5"/>
												
											</td>
											<td>
												<input type="submit" name="button" id="button" value="查询"
													class="submit greenBtn" />
											</td>
										</tr>
									</table>
								</div>
								<table width="100%" cellpadding="0" cellspacing="0" border="0" 
									class="dataList">
									<thead align="center">政策：<c:out value="${palf.airlinePolicyAfter.name}"/></thead>
									<tr>
										<th width="35">
											<div>
												&nbsp;
											</div>
										</th>
										<th>
											<div>
												航班号
											</div>
										</th>
										<th>
											<div>
												折扣
											</div>
										</th>
										<th>
											<div>
												后返佣金%
											</div>
										</th>
										<th>
											<div>
												任务额度(元)
											</div>
										</th>
										<th>
											<div>
												操作人
											</div>
										</th>
										<th>
											<div>
												操作时间
											</div>
										</th>
										<th>
											<div>
												状态
											</div>
										</th>
									</tr>
									<c:forEach var="policyAfter" items="${palf.list}" varStatus="status">
										<tr>

											<td>
												<html:multibox property="selectedItems"
													value="${policyAfter.id}"></html:multibox>
											</td>
											<td>
												<html:link
													page="/policy/policyAfter.do?thisAction=view&id=${policyAfter.id}">
													<c:out value="${policyAfter.flightCode}" />
												</html:link>
											</td>
											<td>
												<c:out value="${policyAfter.discount}" />
											</td>
											<td>
												<c:out value="${policyAfter.rate}" />
											</td>
											<td>
												<c:out value="${policyAfter.quota}" />
											</td>
											<td>
												<c:out value="${policyAfter.userName}" />
											</td>
											<td>
												<c:out value="${policyAfter.updateDate}" />
											</td>
											<td>
												<c:out value="${policyAfter.statusInfo}" />
											</td>
										</tr>
									</c:forEach>

								</table>

								<table width="100%" style="margin-top: 5px;">
									<tr>
										<td>
											<input name="label" type="button" class="button1" value="新 增" onclick="addPolicyAfter();">
											<input name="label" type="button" class="button1" value="修 改" onclick="editPolicyAfter();">
											<input name="label" type="button" class="button1" value="删 除" onclick="delPolicyAfter();">
											<input name="label" type="button" class="button1" value="返回" onclick="backToListAirlinePoliceAfter();">
										</td>
										<td align="right">
											<div>
												共有记录&nbsp;
												<c:out value="${palf.totalRowCount}"></c:out>
												&nbsp;条&nbsp;&nbsp;&nbsp;&nbsp; [
												<a href="JavaScript:turnToPage(document.forms[0],0)">首页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],1)">上一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],2)">下一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],3)"> 末页</a>]
												页数:
												<c:out value="${palf.intPage}" />
												/
												<c:out value="${palf.pageCount}" />
												]
											</div>
										</td>
									</tr>
								</table>
								<div class="clear"></div>

							</td>
							<td width="10" class="tbrr"></td>
						</tr>
						<tr>
							<td width="10" class="tblb"></td>
							<td class="tbbb"></td>
							<td width="10" class="tbrb"></td>
						</tr>
					</table>
				</html:form>
			</div>
		</div>
	</body>
</html>
