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
	function addCompany()
	{
	    document.forms[0].thisAction.value="savePage";
	    document.forms[0].submit();
	}
	function editCompany()
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
	    document.forms[0].thisAction.value="updatePage";
	    document.forms[0].submit();
	  }
	}
	
	function delCompany()
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
			
		</script>
		
	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
				<html:form action="/transaction/companyList.do">
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
								<c:import url="../_jsp/mainTitle.jsp?title1=系统管理&title2=公司列表"
									charEncoding="UTF-8" />

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
												<html:text property="name" styleClass="colorblue2 p_5"
													style="width:150px;" />
											</td>	
											<td>
												公司类型：
											</td>
											<td>
												<html:select property="type" styleClass="colorblue2 p_5"
													style="width:180px;">
													<html:option value="">请选择</html:option>
													<html:option value="1">供应商</html:option>
													<html:option value="2">采购商</html:option>
													<html:option value="3">供采商</html:option>
												</html:select>
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
										<th>
											<div>
												类型
											</div>
										</th>
										<th>
											<div>
												状态
											</div>
										</th>
									</tr>
									<c:forEach var="com" items="${companyListForm.list}" varStatus="sta">
										<tr>
											<td>
												<html:multibox property="selectedItems"
													value="${com.id}"></html:multibox>
											</td>
											<td>
												<c:out value="${sta.count+(companyListForm.intPage-1)*companyListForm.perPageNum}" />
											</td>
											<td>
												<a href="<%=path %>/transaction/companyList.do?thisAction=viewCompanyPage&companyId=<c:out value="${com.id}" />">
													<c:out value="${com.name}" />
												</a>
											</td>
											<td>
												<c:out value="${com.typeInfo}" />
											</td>											
											<td>
												<c:out value="${com.statusInfo}" />
											</td>
										</tr>
									</c:forEach>

								</table>

								<table width="100%" style="margin-top: 5px;">
									<tr>
										<td>
											<input name="label" type="button" class="button1" value="新 增"
												onclick="addCompany();">
											<input name="label" type="button" class="button1" value="修 改"
												onclick="editCompany();">
											<input name="label" type="button" class="button1" value="删 除"
												onclick="delCompany();" style="display: none;">
										</td>
										<td align="right">
											<div>
												共有记录&nbsp;
												<c:out value="${companyListForm.totalRowCount}"></c:out>
												&nbsp;条&nbsp;&nbsp;&nbsp;&nbsp; [
												<a href="JavaScript:turnToPage(document.forms[0],0)">首页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],1)">上一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],2)">下一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],3)"> 末页</a>]
												页数:
												<c:out value="${companyListForm.intPage}" />
												/
												<c:out value="${companyListForm.pageCount}" />
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
