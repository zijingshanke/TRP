<%@ page language="java" pageEncoding="utf-8"%>

<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%
String path = request.getContextPath();
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
	function addAccount(){
	    document.forms[0].thisAction.value="savePage";
	    document.forms[0].submit();
	}
	function editAccount(){
	 	if(document.forms[0].selectedItems==null){
			alert("没有数据，无法操作！");
		}else if (sumCheckedBox(document.forms[0].selectedItems)<1){
	   		alert("您还没有选择数据！");
	 	}else if (sumCheckedBox(document.forms[0].selectedItems)>1){
	    	alert("您一次只能选择一条数据！");
	  	}else{
	    	document.forms[0].thisAction.value="updatePage";
	    	document.forms[0].submit();
	  	}
	}
	
	function delAccount(){	
	 if(document.forms[0].selectedItems==null){
		alert("没有数据，无法操作！");
	 }else if (sumCheckedBox(document.forms[0].selectedItems)<1){
	    alert("您还没有选择数据！");
	 }else if(confirm("您真的要删除选择的这些数据吗？")){
	    document.forms[0].thisAction.value="delete";
	    document.forms[0].submit();
	  }
	}	
	</script>
	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
				<html:form action="/transaction/accountList.do">
					<html:hidden property="thisAction" />
					<html:hidden property="lastAction" />
					<html:hidden property="intPage" />
					<html:hidden property="pageCount" />
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
									<c:param name="title1" value="平台账号管理" />
									<c:param name="title2" value="账号列表" />																						
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
												支付工具名称：
											</td>
											<td>
												<html:select property="paymentToolId"
													styleClass="colorblue2 p_5" style="width:200px;">
													<option value="">
														请选择
													</option>
													<c:forEach items="${paymentToolList}" var="p">
														<html:option value="${p.id}">
															<c:out value="${p.name}" />
														</html:option>
													</c:forEach>
												</html:select>
											</td>
											<td>
												用途：
											</td>
											<td>
												<html:select property="tranType"
													styleClass="colorblue2 p_5" style="width:200px;">
													<html:option value="0">请选择	</html:option>
													<html:option value="1">付款</html:option>
													<html:option value="2">收款</html:option>
													<html:option value="3">收付	</html:option>
												</html:select>
											</td>
											<td>
												支付账号名称：
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
										<th>
											<div>
												支付工具
											</div>
										</th>
										<th>
											<div>
												账号简称
											</div>
										</th>
										<th>
											<div>
												账号
											</div>
										</th>
										<th>
											<div>
												交易用途
											</div>
										</th>
										<th>
											说明
										</th>
										<th>
											<div>
												状态
											</div>
										</th>
									</tr>
									<c:forEach var="account" items="${accountListForm.list}"
										varStatus="status">
										<tr>
											<td>
												<html:multibox property="selectedItems" value="${account.id}"></html:multibox>
											</td>
											<td>
												<c:out
													value="${status.count+(accountListForm.intPage-1)*accountListForm.perPageNum}" />
											</td>
											<td>
												<c:out value="${account.paymentTool.name}" />
											</td>
											<td>
												<a
													href="<%=path%>/transaction/accountList.do?thisAction=viewAccountPage&accountId=<c:out value="${account.id}" />">
													<c:out value="${account.name}" /> </a>
											</td>
											<td>
												<c:out value="${account.accountNo}" /> </a>												
											</td>
											<td>
												<c:out value="${account.tranTypeInfo}" />
											</td>
											<td>
												<c:out value="${account.description}" />
											</td>
											
											<td>
												<c:out value="${account.statusInfo}" />
											</td>
										</tr>
									</c:forEach>
								</table>
								<table width="100%" style="margin-top: 5px;">
									<tr>
										<td>
											<c:check code="sf15">
											<input name="label" type="button" class="button1" value="新 增"
												onclick="addAccount();">
											<input name="label" type="button" class="button1" value="修 改"
												onclick="editAccount();">
											<input name="label" type="button" class="button1" value="删 除"
												onclick="delAccount();" style="display: none;">
											</c:check>
										</td>
										<td align="right">
											<div>
												共有记录&nbsp;
												<c:out value="${accountListForm.totalRowCount}"></c:out>
												&nbsp;条&nbsp;&nbsp;&nbsp;&nbsp; [
												<a href="JavaScript:turnToPage(document.forms[0],0)">首页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],1)">上一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],2)">下一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],3)"> 末页</a>]
												页数:
												<c:out value="${accountListForm.intPage}" />
												/
												<c:out value="${accountListForm.pageCount}" />
												]
											</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</html:form>
			</div>
		</div>
	</body>
</html>
