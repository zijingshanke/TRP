<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%
	String path =request.getContextPath();
 %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />

		<link href="../_css/global.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" language="javascript"
			src="../_js/jquery-1.3.1.min.js"></script>
		<script src="../_js/common.js" type="text/javascript"></script>
		<script src="../_js/popcalendar.js" type="text/javascript"></script>
		
		<script type="text/javascript">
	function selectAll(){
		var selectedItems = document.forms[0].selectedItems;
		for(i=0;i<selectedItems.length;i++){
			selectedItems[i].checked = true;
		}
	}
	function selectNone(){
		var selectedItems = document.forms[0].selectedItems;
		for(i=0;i<selectedItems.length;i++){
			selectedItems[i].checked = false;
		}
	}
	
	function addAgent()
	{
	    document.forms[0].thisAction.value="saveB2CPage";
	    document.forms[0].submit();
	}
	function editAgent()
	{
	 if(document.forms[0].selectedItems==null){
			   	alert("没有数据，无法操作！");
			   }
			  else
	  if (sumCheckedBox(document.forms[0].selectedItems)<1)
	    alert("您还没有选择数据！");
	  else if (sumCheckedBox(document.forms[0].selectedItems)>1)
	    alert("您一次只能选择一个数据！");
	  else
	  {
	    document.forms[0].thisAction.value="updateB2CPage";
	    document.forms[0].submit();
	  }
	}
	
	function delAgent()
	{	
	 if(document.forms[0].selectedItems==null){
			   	alert("没有数据，无法操作！");
			   }
			  else
	  if (sumCheckedBox(document.forms[0].selectedItems)<1)
	    alert("您还没有选择数据！");
	  else if(confirm("您真的要删除选择的这些数据吗？"))
	  {
	    document.forms[0].thisAction.value="delete";
	    document.forms[0].submit();
	  }
	}
	
	function sendMessage(){
		if (sumCheckedBox(document.forms[0].selectedItems)<1){
	   	 alert("请选择需要发送短信的记录");
	   	 return;
	    }
		 document.forms[0].thisAction.value="sendMessagePage";
		 document.forms[0].submit();
	}
		
		</script>
		
	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
				<html:form action="/transaction/agentList.do">
					<html:hidden property="thisAction" />
					<html:hidden property="lastAction" />
					<html:hidden property="intPage" />
					<html:hidden property="pageCount" />
					<html:hidden property="operatorObject" value="b2c"/>
					<input type="hidden" name="locate"
						value="<c:out value="${locate}"></c:out>" />

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
									<c:param name="title1" value="客户管理" />
									<c:param name="title2" value="B2C客户列表" />																						
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
												公司名称：
											</td>
											<td>
												<html:select property="companyId" styleClass="colorblue2 p_5"
													style="width:200px;" >		
														<option value="">请选择</option>										
													<c:forEach items="${companyList}" var="company">													
														<html:option value="${company.id}"><c:out value="${company.showName}"/></html:option>
													</c:forEach>
											</html:select>
											</td>	
											<td>
												客户名称：
											</td>
											<td>
												<html:text property="name" styleClass="colorblue2 p_5"
													style="width:150px;" />
											</td>																						
											<td>
												<input type="submit" name="button" id="button" value="提交"
													class="submit greenBtn" />
											</td>
										</tr>
									</table>
								</div>
								<table width="100%" cellpadding="0" cellspacing="0" border="0"
									class="dataList">
									<tr>
										<th width="60">
											<div>
												&nbsp;请选择
											</div>
										</th>
										<th width="35">
											<div>
												&nbsp;序号
											</div>
										</th>
										<th width="230">
											<div> 
												公司名称
											</div>
										</th>
										<th width="230">
											<div> 
												客户名称
											</div>
										</th>
										<th width="230">
											<div> 
												联系方式
											</div>
										</th>
										<th>
											<div> 
												手机号码
											</div>
										</th>
										<th>
											<div>
												地址
											</div>
										</th>
										<th>
											<div> 
												操作者
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
									<c:forEach var="ag" items="${agentListForm.list}" varStatus="sta">
										<tr>
											<td>
												<html:multibox property="selectedItems"
													value="${ag.id}"></html:multibox>
											</td>
											<td>
												<c:out value="${sta.count+(agentListForm.intPage-1)*agentListForm.perPageNum}" />
											</td>
											<td><div  align="left">
												<a href="<%=path %>/transaction/companyList.do?thisAction=viewCompanyPage&companyId=<c:out value="${ag.company.id}" />">
													<c:out value="${ag.company.name}" />
												</a></div>
											</td>
											<td>
												<a href="<%=path %>/transaction/agentList.do?thisAction=viewAgentPage&agentId=<c:out value="${ag.id}" />">
													<c:out value="${ag.name}" />
												</a>
											</td>
											<td>
												<c:out value="${ag.contactWay}" />
											</td>
											<td>
												<c:out value="${ag.mobilePhone}" />
											</td>											
											<td>
												<c:out value="${ag.address}" />
											</td>
											<td>
												<c:out value="${ag.userName}" />
											</td>									
											<td>
												<c:out value="${ag.updateDate}" />
											</td>
											<td>
												<c:out value="${ag.statusInfo}" />
											</td>
										</tr>
									</c:forEach>

								</table>

								<table width="100%" style="margin-top: 5px;">
									<tr>
										<td>
											<input name="label" type="button" class="button1" value="全选"
												onclick="selectAll();">
											<input name="label" type="button" class="button1" value="全不选"
												onclick="selectNone();">
										<c:check code="sd01">	
											<input name="label" type="button" class="button1" value="新 增"
												onclick="addAgent();">
												</c:check>
												<c:check code="sd02">
											<input name="label" type="button" class="button1" value="修 改"
												onclick="editAgent();">
												</c:check>
												<input name="label" type="button" class="button1" value="发短信"
												onclick="sendMessage();">
												<c:check code="sd03">
											<input name="label" type="button" class="button1" value="删 除"
												onclick="delAgent();" style="display: none;">
												</c:check>												
										</td>
										<td align="right">
											<div>
												共有记录&nbsp;
												<c:out value="${agentListForm.totalRowCount}"></c:out>
												&nbsp;条&nbsp;&nbsp;&nbsp;&nbsp; [
												<a href="JavaScript:turnToPage(document.forms[0],0)">首页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],1)">上一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],2)">下一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],3)"> 末页</a>]
												页数:
												<c:out value="${agentListForm.intPage}" />
												/
												<c:out value="${agentListForm.pageCount}" />
												]
											</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					<table>
						<tr>
							<c:forEach var="agentId" items="${agentIdList}">
								<td>
									<html:multibox property="selectedItems" value="${agentId}" style="display:none">
									</html:multibox>
								</td>
							</c:forEach>
						</tr>
					</table>
				</html:form>
			</div>
		</div>
	</body>
</html>
