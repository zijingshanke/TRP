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
				<html:form action="/transaction/platformReportIndexList.do">
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
									<c:param name="title2" value="报表字段索引列表" />
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
												交易平台：
											</td>
											<td>
												<html:select property="platformId"
													styleClass="colorblue2 p_5" style="width:200px;">
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
											<td>
												类型：
											</td>
											<td>
												<html:select property="type" styleClass="colorblue2 p_5"
													style="width:200px;">
													<html:option value="0">请选择	</html:option>
													<html:option value="1">销售</html:option>
													<html:option value="2">退废</html:option>
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
										<th>
											<div>
												交易平台
											</div>
										</th>
										<th>
											<div>
												预定编码
											</div>
										</th>
										<th>
											<div>
												平台订单号
											</div>
										</th>
										<th>
											<div>
												支付订单号
											</div>
										</th>
										<th>
											<div>
												收款账号
											</div>
										</th>
										<th>
											<div>
												收款金额
											</div>
										</th>
										<th>
											<div>
												付款账号
											</div>
										</th>
										<th>
											<div>
												付款金额
											</div>
										</th>
										<th>
											<div>
												收退款账号
											</div>
										</th>
										<th>
											<div>
												收退款金额
											</div>
										</th>
										<th>
											<div>
												付退款账号
											</div>
										</th>
										<th>
											<div>
												付退款金额
											</div>
										</th>
										<th>
											<div>
												人数
											</div>
										</th>										
										<th>
											<div>
												航班
											</div>
										</th>
										<th>
											<div>
												舱位
											</div>
										</th>
										<th>
											<div>
												票号
											</div>
										</th>
										
										<th>
											<div>
												出发地
											</div>
										</th>
										<th>
											<div>
												目的地
											</div>
										</th>										
										<th>
											<div>
												折扣
											</div>
										</th>
										<th>
											<div>
												类型
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
									<c:forEach var="reportIndex"
										items="${platformReportIndexListForm.list}" varStatus="sta">
										<tr>
											<td  style="width=15px">
												<html:multibox property="selectedItems"
													value="${reportIndex.id}"></html:multibox>											</td>
											<td>
												<c:out
													value="${sta.count+(platformReportIndexListForm.intPage-1)*platformReportIndexListForm.perPageNum}" />
											</td>
											<td>
												<c:out value="${reportIndex.platformName}" />
											</td>
											<td style="width=15px">
												<c:out value="${reportIndex.subPnr}" />
											</td>
											<td style="width=15px">
												<c:out value="${reportIndex.airOrderNo}" />
											</td>
											<td style="width=15px">
												<c:out value="${reportIndex.payOrderNo}" />
											</td>
											<td style="width=15px">
												<c:out value="${reportIndex.inAccount}" />
											</td>
											<td style="width=15px">
												<c:out value="${reportIndex.inAmount}" />
											</td>
											<td style="width=15px">
												<c:out value="${reportIndex.outAccount}" />
											</td>
											<td style="width=15px">
												<c:out value="${reportIndex.outAmount}" />
											</td>
											<td style="width=15px">
												<c:out value="${reportIndex.inRetireAccount}" />
											</td>
											<td style="width=15px">
												<c:out value="${reportIndex.inRetireAmount}" />
											</td>
											<td style="width=15px">
												<c:out value="${reportIndex.outRetireAccount}" />
											</td>
											<td style="width=15px">
												<c:out value="${reportIndex.outRetireAmount}" />
											</td>
											<td>
												<c:out value="${reportIndex.passengerCount}" />
											</td>											
											<td>
												<c:out value="${reportIndex.flightCode}" />
											</td>
											<td>
												<c:out value="${reportIndex.flightClass}" />
											</td>
											<td>
												<c:out value="${reportIndex.ticketNumber}" />
											</td>
											<td style="width=15px">
												<c:out value="${reportIndex.startPoint}" />
											</td>
											<td style="width=15px">
												<c:out value="${reportIndex.endPoint}" />
											</td>
											<td>
												<c:out value="${reportIndex.discount}" />
											</td>
											<td>
												<c:out value="${reportIndex.typeInfo}" />
											</td>
											<td>
												<c:out value="${reportIndex.memo}" />
											</td>
											<td>
												<c:out value="${reportIndex.userName}" />
											</td>
											<td>
												<c:out value="${reportIndex.formatLastDate}" />
											</td>											
											<td>
												<c:out value="${reportIndex.statusInfo}" />
											</td>
										</tr>
									</c:forEach>
								</table>
								<table width="100%" style="margin-top: 5px;">
									<tr>
										<td>
											<c:check code="sf15">
												<input name="label" type="button" class="button1"
													value="新 增" onclick="add();">
												<input name="label" type="button" class="button1"
													value="修 改" onclick="edit();">
												<input name="label" type="button" class="button1"
													value="删 除" onclick="del();" >
											</c:check>
										</td>
										<td align="right">
											<div>
												共有记录&nbsp;
												<c:out value="${platformReportIndexListForm.totalRowCount}"></c:out>
												&nbsp;条&nbsp;&nbsp;&nbsp;&nbsp; [
												<a href="JavaScript:turnToPage(document.forms[0],0)">首页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],1)">上一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],2)">下一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],3)"> 末页</a>]
												页数:
												<c:out value="${platformReportIndexListForm.intPage}" />
												/
												<c:out value="${platformReportIndexListForm.pageCount}" />
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
