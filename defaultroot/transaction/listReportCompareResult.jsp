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
	</script>
	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
				<html:form action="/transaction/reportCompareResultList.do">
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
									<c:param name="title2" value="对比结果列表" />
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
												对比类型：
											</td>
											<td>
												<html:select property="compareType"
													styleClass="colorblue2 p_5" style="width:100px;" onchange="showSelectObj()">
													<html:option value="0">请选择	</html:option>
													<html:option value="1">平台报表</html:option>
													<html:option value="2">BSP报表</html:option>
													<html:option value="4">网电报表</html:option>													
													<html:option value="3">银行/支付平台报表</html:option>
												</html:select>
											</td>											
											<td id="platformSelectObj" style="display: none">
												交易平台：
												<html:select property="platformId" 
													styleClass="colorblue2 p_5" style="width:100px;">
													<option value="">
														请选择
													</option>
													<c:forEach items="${platformList}" var="platform">
														<html:option value="${platform.id}">
															<c:out value="${platform.showName}" />
														</html:option>
													</c:forEach>
												</html:select>
											</td>
											<td id="tranTypeSelectObj" style="display: none">
												交易类型:
												<html:select property="tranType" styleClass="colorblue2 p_5"
													style="width:100px;" >
													<html:option value="0">请选择	</html:option>
													<html:option value="1">--供应--</html:option>
													<html:option value="2">--采购--</html:option>
													<html:option value="13">供应退废</html:option>
													<html:option value="14">采购退废</html:option>
													<html:option value="15">供应退票</html:option>
													<html:option value="16">采购退票</html:option>
													<html:option value="17">供应废票</html:option>
													<html:option value="18">采购废票</html:option>
												</html:select>
											</td>											
											<td id="paymenttoolSelectObj" style="display: none">
												支付工具：
												<html:select property="paymenttoolId" 
													styleClass="colorblue2 p_5" style="width:100px;">
													<option value="">
														请选择
													</option>
													<c:forEach items="${paymentToolList}" var="paymentTool">
														<html:option value="${paymentTool.id}">
															<c:out value="${paymentTool.showName}" />
														</html:option>
													</c:forEach>
												</html:select>
											</td>
											<td id="accountSelectObj" style="display: none">
												账号：
												<html:select property="accountId" 
													styleClass="colorblue2 p_5" style="width:100px;">
													<option value="">
														请选择
													</option>
													<c:forEach items="${accountList}" var="account">
														<html:option value="${account.id}">
															<c:out value="${account.showName}" />
														</html:option>
													</c:forEach>
												</html:select>
											</td>											 
											 <td>操作人:
	        									<html:text property="userNo" styleClass="colorblue2 p_5"
													style="width:80px;" value="${URI.user.userName}"  ondblclick="JavaScript:this.value=''"/>
											</td>
											<td> 开始:
	        									<html:text property="beginDateStr" styleClass="colorblue2 p_5"
													style="width:120px;" onfocus="WdatePicker({startDate:'%y-%M-%D 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
													readonly="true" />
											</td>
										    <td> 结束
										        <html:text property="endDateStr" styleClass="colorblue2 p_5"
															style="width:120px;" onfocus="WdatePicker({startDate:'%y-%M-%D 23:59:59',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
															readonly="true" />
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
										<th width="35">
											<div>
												&nbsp;序号
											</div>
										</th>
										<th>
											<div>
												<input type="checkbox"
													onclick="checkAll(this, 'selectedItems')"
													name="checkAllObj" />
											</div>
										</th>
										<th>
											<div>
												标题
											</div>
										</th>
										<th>
											<div>
												对比类型
											</div>
										</th>
										<th>
											<div>
												交易平台
											</div>
										</th>
										<th>
											<div>
												交易类型
											</div>
										</th>
										<th>
											<div>
												备注
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
									<c:forEach var="result" items="${ulf.list}" varStatus="status">
										<tr>
											<td>
												<c:out
													value="${status.count+(ulf.intPage-1)*ulf.perPageNum}" />
											</td>
											<td>
												<input type="checkbox" name="selectedItems"
													value="<c:out value='${result.id}' />"
													onclick="checkItem(this, 'checkAllObj')">
											</td>
											<td>
												<a
													href="<%=path%>/transaction/reportCompareResultList.do?thisAction=view&id=<c:out value="${result.id}" />">
													<c:out value="${result.name}" /> </a>
											</td>
											<td>
												<c:out value="${result.compareTypeInfo}" />
											</td>
											<td>
												<c:out value="${result.platformName}" />
											</td>
											<td>
												<c:out value="${result.tranTypeInfo}" />
											</td>
											<td>
												<c:out value="${result.memo}" />
											</td>
											<td>
												<c:out value="${result.userName}" />
											</td>
											<td>
												<c:out value="${result.formatLastDate}" />
											</td>
											<td>
												<c:out value="${result.statusInfo}" />
											</td>
										</tr>
									</c:forEach>
								</table>
								<table width="100%" style="margin-top: 5px;">
									<tr>
										<td>
											<c:check code="sf15">
												<input name="label" type="button" class="button1"
													value="修 改" onclick="edit();">
												<input name="label" type="button" class="button1"
													value="删 除" onclick="del();">
											</c:check>
										</td>
										<td align="right">
											<div>
												共有记录&nbsp;
												<c:out value="${ulf.totalRowCount}"></c:out>
												&nbsp;条&nbsp;&nbsp;&nbsp;&nbsp; [
												<a href="JavaScript:turnToPage(document.forms[0],0)">首页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],1)">上一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],2)">下一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],3)"> 末页</a>]
												页数:
												<c:out value="${ulf.intPage}" />
												/
												<c:out value="${ulf.pageCount}" />
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
		<script type="text/javascript">
		function showSelectObj(){		
		 	 var compareType=document.forms[0].compareType.value;
		 	  	//alert(compareType);
		 	 var tranTypeSelectObj=document.getElementById("tranTypeSelectObj");
		 	 var platformSelectObj=document.getElementById("platformSelectObj");
		 	 var accountSelectObj=document.getElementById("accountSelectObj");
		 	 var paymenttoolSelectObj=document.getElementById("paymenttoolSelectObj");		 	 
		 	 
		 	 if(compareType=="0"){
		 	 	   tranTypeSelectObj.style.display="none";
		 	 	platformSelectObj.style.display="none";
		 	 	accountSelectObj.style.display="none";
		 	 	paymenttoolSelectObj.style.display="none";		
		 	 }
		 	 if(compareType=="1"){
		 	    tranTypeSelectObj.style.display="";
		 	 	platformSelectObj.style.display="";
		 	 	accountSelectObj.style.display="none";
		 	 	paymenttoolSelectObj.style.display="none";		 	 	
		 	 }		 	 
		 	 if(compareType=="2"){
		 	 	tranTypeSelectObj.style.display="none";
		 	 	platformSelectObj.style.display="none";
		 	 	accountSelectObj.style.display="none";
		 	 	paymenttoolSelectObj.style.display="none";	
		 	 }	
		 	 if(compareType=="4"){
		 	 	tranTypeSelectObj.style.display="none";
		 	 	platformSelectObj.style.display="none";
		 	 	accountSelectObj.style.display="none";
		 	 	paymenttoolSelectObj.style.display="none";	
		 	 }	 	 
		 	 if(compareType=="3"){
		 	 	tranTypeSelectObj.style.display="none";
		 	 	platformSelectObj.style.display="none";
		 	 	accountSelectObj.style.display="";
		 	 	paymenttoolSelectObj.style.display="";	
		 	 }		 
		 }
		</script>
	</body>
</html>
