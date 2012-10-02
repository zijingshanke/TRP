<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<% String path = request.getContextPath(); %>
<html>
	<head>
		<title>main</title>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" language="javascript"
			src="../_js/jquery-1.3.2.min.js"></script>
		<script src="../_js/common.js" type="text/javascript"></script>

		<script>
			/*
			流水号查询
			*/
			function queryRecord(){
				document.forms[0].submit();
			}
			/*	
			双击清空内容
			*/
			function clearValue( obj ){
				obj.value = "";
			}
			
			/*
			更新后返报表列表
			*/
			function updateTable(){
				if(window.confirm("您真的要更新此表记录吗？这也许将会花费较长时间，请耐心等待......")){
				   var url="<%=path%>/airticket/statisticsOrder.do?thisAction=insert&saleStatisticsId="
				   		+document.forms[0].saleStatisticsId.value;
				    openWindow(400,340,url); 
				}
			}
			
			/*
			下载信息
			*/
			function downloadTable(){
				if(window.confirm("您真的要下载记录信息吗？这也许将会花费较长时间，请耐心等待......")){
					document.forms[0].labeDownload.disabled = true;
				    document.forms[0].action="<%=path%>/airticket/statisticsOrder.do";
				   	document.forms[0].thisAction.value="download";
				    document.forms[0].submit();
				    document.forms[0].action="<%=path%>/airticket/listStatisticsOrder.do";
				    document.forms[0].thisAction.value="listStatisticsOrder";
				}
			}
		</script>
	</head>
	<body>	
		<div id="mainContainer">
			<div id="container">
				<html:form action="/airticket/listStatisticsOrder.do">
					<html:hidden property="thisAction" />
					<html:hidden property="saleStatisticsId" />
					<html:hidden property="lastAction" />
					<html:hidden property="intPage" />
					<html:hidden property="pageCount" />
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
									<c:param name="title1" value="订单后返报表列表" />
									<c:param name="title2" value="订单后返报表列表" />																						
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
												流水号：
												<html:text property="orderNo" ondblclick="clearValue(this);"/>
											</td>
											<td>
												排序：
												<html:select property="sort">
													<html:option value="orderNo">流水号</html:option>
													<html:option value="totalAmount">订单金额</html:option>
													<html:option value="profit">利润</html:option>
													<html:option value="profitAfter">后返利润</html:option>
												</html:select>
											</td>
											<td>
												<input type="button" name="query" id="button" value="查询" onclick="queryRecord();"
													class="submit greenBtn"/>
											</td>
										</tr>
									</table>
								</div>
								<table width="100%" cellpadding="0" cellspacing="0" border="0"
									class="dataList">
									<tr>
										<th>
											<div>
												流水号
											</div>
										</th>
										<th>
											<div>
												航班号
											</div>
										</th>
										<th>
											<div>
												起止城市
											</div>
										</th>
										<th>
											<div>
												起飞时间
											</div>
										</th>
										<th>
											<div>
												乘客姓名
											</div>
										</th>
										
										<th>
											<div>
												订单金额
											</div>
										</th>
										<th>
											<div>
												利润
											</div>
										</th>
										<th>
											<div>
												后返利润
											</div>
										</th>
										
										<th>
											<div>
												查看详细
											</div>
										</th>
									</tr>
									<c:forEach var="statisticsInf" items="${solf.list}" varStatus="status">
										<tr>
											<td>
												<html:link
													page="/airticket/listAirTicketOrder.do?thisAction=listAirTicketOrder&recentlyDay=0&orderNo=${statisticsInf.orderNo}&sysName=">
													<c:out value="${statisticsInf.orderNo}" />
												</html:link>
											</td>
											<td>
												<c:out value="${statisticsInf.flightCode}" />
												
											</td>
											<td>
												<c:out value="${statisticsInf.startEnd}" />
											</td>
											<td>
												<c:out value="${statisticsInf.boardingTime}" />
											</td>
											<td>
												<c:out value="${statisticsInf.passengerName}" />
											</td>
											
											<td>
												<c:out value="${statisticsInf.totalAmount}" />
											</td>
											<td>
												<c:out value="${statisticsInf.profit}" />
												
											</td>
											<td>
												<c:out value="${statisticsInf.profitAfter}" />
												
											</td>
											<td>
											<html:link
												page="/airticket/statisticsOrder.do?thisAction=view&id=${statisticsInf.id}">
												详细
											</html:link>
										</td>
											
										</tr>
										
									</c:forEach>

								</table>

								<table width="100%" style="margin-top: 5px;">
									<tr>
										<td>
											&nbsp;
										</td>
										<td align="right">
											<div>
												共有记录&nbsp;
												<c:out value="${solf.totalRowCount}"></c:out>
												&nbsp;条&nbsp;&nbsp;&nbsp;&nbsp; [
												<a href="JavaScript:turnToPage(document.forms[0],0)">首页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],1)">上一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],2)">下一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],3)"> 末页</a>]
												页数:
												<c:out value="${solf.intPage}" />
												/
												<c:out value="${solf.pageCount}" />
												]
											</div>
										</td>
									</tr>
								</table>
								<table width="1043" style="margin-top: 5px;" height="34">
							<tr>
								<td>
									<input name="label" type="button" class="button1" value="返 回"
										onclick="window.history.back();">
										
									<input name="labeUpdateTable"  type="button" class="button3"  value="更新后返报表列表"
										onclick="updateTable();">
									<input name="labeDownload" type="button" class="button3"  value="下载后返报表列表"
										onclick="downloadTable();">
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
