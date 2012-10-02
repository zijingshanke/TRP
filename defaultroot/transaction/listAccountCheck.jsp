<%@ page language="java" pageEncoding="utf-8"%>

<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>

<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />
		<script src="../_js/common.js" type="text/javascript"></script>
		<script src="../_js/calendar/WdatePicker.js" type="text/javascript"></script>

		<script type="text/javascript">
	function add(){
	    document.forms[0].thisAction.value="add";
	    document.forms[0].submit();
	}
	function edit(){
	 	if(document.forms[0].selectedItems==null){
			alert("没有数据，无法操作！");
		}else if (sumCheckedBox(document.forms[0].selectedItems)<1){
	   		alert("您还没有选择数据！");
	 	}else if (sumCheckedBox(document.forms[0].selectedItems)>1){
	    	alert("您一次只能选择一条数据！");
	  	}else{
	    	document.forms[0].thisAction.value="edit";
	    	document.forms[0].submit();
	  	}
	}
	
	function checkOff(id){	 	
		document.forms[0].id.value=id;
	    document.forms[0].thisAction.value="checkOff";
	    document.forms[0].submit();	  	
	}
	
	function del(){	
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
				<html:form action="/transaction/accountCheckList.do">
					<html:hidden property="thisAction" />
					<html:hidden property="lastAction" />
					<html:hidden property="intPage" />
					<html:hidden property="pageCount" />
					<html:hidden property="id" />
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
									<c:param name="title1" value="结算管理" />
									<c:param name="title2" value="账户上下班交接" />																						
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
												账号：
											</td>
											<td>
												<html:text property="accountName" 
													styleClass="colorblue2 p_5" style="width:100px;"></html:text>
											</td>
											<td>
												操作员：
											</td>
											<td>
												<html:text property="userNo" 
													styleClass="colorblue2 p_5" style="width:100px;"></html:text>
											</td>
											<td>
												开始:
												<html:text property="startDate" styleClass="colorblue2 p_5"
													style="width:150px;" onfocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
													readonly="true" />
											</td>
											<td>
												结束
												<html:text property="endDate" styleClass="colorblue2 p_5"
													style="width:150px;" onfocus="WdatePicker({startDate:'%y-%M-01 23:59:59',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
													readonly="true" />
											</td>

											<td>
												<input type="submit" name="button" id="button" value="提交"
													class="submit greenBtn" />
											</td>
											<td>
												<input  name="label" type="button" value="上 班"
												onclick="add();">
											</td>
										</tr>
									</table>
								</div>
								<table width="100%" cellpadding="0" cellspacing="0" border="0"
									class="dataList">
									<tr>
										<th width="60">
											请选择
										</th>
										<th width="35">
											序号
										</th>
										<th>
											帐户
										</th>
										<th>
											上班金额
										</th>
										<th>
											转入金额
										</th>
										<th>
											退回金额
										</th>
										<th>
											转出金额
										</th>
										<th>
											支付金额
										</th>
										<th>
											下班金额
										</th>
										<th>
											操作员
										</th>
										<th>
											上班时间
										</th>
										<th>
											下班时间
										</th>
										<th>
											说明
										</th>
										<th>
											操作
										</th>										
									</tr>
									<c:forEach var="check" items="${aclf.list}" varStatus="status">
										<tr>
											<td>
												<html:multibox property="selectedItems" value="${check.id}"></html:multibox>
											</td>
											<td>
												<c:out
													value="${status.count+(aclf.intPage-1)*aclf.perPageNum}" />
											</td>
											<td style="text-align: left">
												<a
													href="<%=path%>/transaction/accountList.do?thisAction=viewAccountPage&accountId=<c:out value="${check.account.id}" />">
													<c:out value="${check.account.name}" /> </a>
											</td>
											<td style="text-align: right">
												<fmt:formatNumber value="${check.checkOnAmount}" pattern="0.00" />
											</td>
											<td style="text-align: right">
												<fmt:formatNumber value="${check.transInAmount}" pattern="0.00" />
											</td>
											<td style="text-align: right">
												<fmt:formatNumber value="${check.refundAmount}" pattern="0.00" />
											</td>
											<td style="text-align: right">
												<fmt:formatNumber value="${check.transOutAmount}" pattern="0.00" />
											</td>
											<td style="text-align: right">
												<fmt:formatNumber value="${check.payAmount}" pattern="0.00" />
											</td>
											<td style="text-align: right">
												<fmt:formatNumber value="${check.checkOffAmount}" pattern="0.00" />
											</td>
											<td style="text-align: left">
												<c:out value="${check.sysUser.userName}" />
											</td>
											<td>
												<c:out value="${check.formatCheckOnDate}" />												
											</td>
											<td>
												<c:out value="${check.formatCheckOffDate}" />
											</td>
											<td style="text-align:left">
												<c:out value="${check.note}" />
											</td>
											<td>
											<c:if test="${check.type==0 and check.sysUser.userId==URI.user.userId}">
												<a href="#" onclick="checkOff('<c:out value='${check.id}'/>');">下 班</a>
											</c:if>
											</td>
										</tr>
									</c:forEach>
								</table>
								<table width="100%" style="margin-top: 5px;">
									<tr>
										<td>	
										    <c:check code="sf20">										
												<input name="label" type="button" class="button1" value="修 改"
													onclick="edit();">										
												<input name="label" type="button" class="button1" value="删 除"
													onclick="del();">
											</c:check>
										</td>
										<td align="right">
											<div>
												共有记录&nbsp;
												<c:out value="${aclf.totalRowCount}"></c:out>
												&nbsp;条&nbsp;&nbsp;&nbsp;&nbsp; [
												<a href="JavaScript:turnToPage(document.forms[0],0)">首页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],1)">上一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],2)">下一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],3)"> 末页</a>]
												页数:
												<c:out value="${aclf.intPage}" />
												/
												<c:out value="${aclf.pageCount}" />
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
