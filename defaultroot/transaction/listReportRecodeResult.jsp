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
		<script src="../_js/common.js" type="text/javascript"></script>
		<script src="../_js/base/FormUtil.js" type="text/javascript"></script>
		<script src="../_js/calendar/WdatePicker.js" type="text/javascript"></script>
		<script type="text/javascript">	
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

	
	function addReportRecodeResult(){
		document.forms[0].action="<%=path%>/transaction/reportRecodeResultList.do?thisAction=addReportRecodeResult";
		document.forms[0].submit();
	}	
	
	function addReportRecodeContinue(resultId){
		document.forms[0].action="<%=path%>/transaction/reportRecodeResultList.do?thisAction=addReportRecodeContinue&id="+resultId;
		document.forms[0].submit();
	} 
	
	function setReportDate(){
		var reportDate = document.forms[0].reportDate;
			reportDate.value = reportDate.value+" 00:00:00";
	}
	
	</script>
	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
				<html:form action="/transaction/reportRecodeResultList.do">
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
									<c:param name="title1" value="报表对比管理" />
									<c:param name="title2" value="导入的报表" />
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
												操作人:
												<html:text property="userNo" styleClass="colorblue2 p_5"
													style="width:80px;" ondblclick="JavaScript:this.value=''" />
											</td>
											<td>
												日期:
												<html:text property="reportDate" styleClass="colorblue2 p_5"
													style="width:120px;"
													onfocus="WdatePicker({startDate:'%y-%M-%D',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"
													readonly="true" />
											</td>
											<td>
												<html:select property="reportType"
													styleClass="colorblue2 p_5" style="width:100px;">
													<html:option value="0">全部报表</html:option>
													<html:option value="1">平台</html:option>
													<html:option value="2">支付工具</html:option>
												</html:select>
											</td>
											<td>
												<input type="submit" name="button" id="button" value="提交" onClick="setReportDate();"
													class="submit greenBtn" />
											</td>
										</tr>
									</table>
								</div>
								<table width="100%" cellpadding="0" cellspacing="0" border="0"
									class="dataList">
									<tr>
										<th>
											<div>
												<input type="checkbox"
													onclick="checkAll(this, 'selectedItems')"
													name="checkAllObj" />
											</div>
										</th>
										<th>
											<div>
												报表名称
											</div>
										</th>
										<th>
											<div>
												开始时间
											</div>
										</th>
										<th>
											<div>
												结束时间
											</div>
										</th>
										<th>
											<div>
												报表类型
											</div>
										</th>
										<th>
											<div>
												导入部分
											</div>
										</th>
										<th>
											<div>
												导入人
											</div>
										</th>
										<th>
											<div>
												导入时间
											</div>
										</th>
										<th>
											<div>
												操作
											</div>
										</th>
									</tr>
									<c:forEach var="result" items="${rrrlf.list}"
										varStatus="status">
										<tr>
											<td>
												<input type="checkbox" name="selectedItems"
													value="<c:out value='${result.id}' />"
													onclick="checkItem(this, 'checkAllObj')">
											</td>
											<td>
												<a
													href="<%=path%>/transaction/reportRecodeResultList.do?thisAction=view&id=<c:out value="${result.id}" />">
													<c:out value="${result.name}" /> </a>
											</td>
											<td>
												<c:out value="${result.formatBeginDate}" />
											</td>
											<td>
												<c:out value="${result.formatEndDate}" />
											</td>
											<td>
												<c:out value="${result.reportTypeInfo}" />
											</td>
											<td>
												<div align="left">
												  <c:out value="${result.recodeSet}" />
										        </div></td>
											<td>
												<c:out value="${result.userName}" />
											</td>
											<td>
												<c:out value="${result.formatLastDate}" />
											</td>
											<td>
												<input type="button" class="button1" value="继续导入"
													onclick="addReportRecodeContinue('<c:out value="${result.id}" />')" />
											</td>
										</tr>
									</c:forEach>
								</table>
								<table width="100%" style="margin-top: 5px;">
									<tr>
										<td>
											<input name="label" type="button" class="button1" value="修 改"
												onclick="edit();">
											<input name="label" type="button" class="button1" value="删 除"
												onclick="del();">
											<input name="label" type="button" class="button2"
												value="新增导入报表" onClick="addReportRecodeResult();">
										</td>
										<td align="right">
											<div>
												共有记录&nbsp;
												<c:out value="${rrrlf.totalRowCount}"></c:out>
												&nbsp;条&nbsp;&nbsp;&nbsp;&nbsp; [
												<a href="JavaScript:turnToPage(document.forms[0],0)">首页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],1)">上一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],2)">下一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],3)"> 末页</a>]
												页数:
												<c:out value="${rrrlf.intPage}" />
												/
												<c:out value="${rrrlf.pageCount}" />
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
