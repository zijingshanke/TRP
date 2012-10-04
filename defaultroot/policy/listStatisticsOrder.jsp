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
		<script src="../_js/progressBar.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=path%>/_js/jquery-1.3.2.min.js"></script>
		<style type="text/css">
			.progressBar {
				width: 200px;
				height: 20px;
				border: solid 1px #B3B3DC;
				position: relative;
			}
		</style>
		<script>
			/*
			流水号、排序查询
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
					document.forms[0].labeUpdateTable.disabled = true;
					document.forms[0].action="<%=path%>/airticket/statisticsOrder.do";
				   	document.forms[0].thisAction.value="insert";
				    document.forms[0].submit();
				    sendRequest("showInsertProgressBar");
				    document.forms[0].action="<%=path%>/airticket/listStatisticsOrder.do";
				    document.forms[0].thisAction.value="listStatisticsOrder";
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
				    sendRequest("showDownloadProgressBar");
				    document.forms[0].action="<%=path%>/airticket/listStatisticsOrder.do";
				    document.forms[0].thisAction.value="listStatisticsOrder";
				}
			}
			
		</script>
		<script type="text/javascript"> 
			function sendRequest(action){
				jQuery.get("<%=path%>/airticket/statisticsOrder.do?thisAction="+action,
							function(backJson){
								var backInf = eval("("+backJson+")");
								var totalCount = backInf["total"];
								var currentCount = backInf["current"];
								var action = backInf["action"]+"";
								backHandle(totalCount,currentCount,action);
							},
							"json");
			}
			
			function backHandle(total,current,action){
				var totalCount =  parseInt(total);
				var currentCount = parseInt(current);
				var comPercent = $("#compPercent");
				var display = $("#display");
				if(totalCount == 0){
					comple();
					comPercent.hide();
					display.hide();
					document.forms[0].labeUpdateTable.disabled = false;
					document.forms[0].labeDownload.disabled = false;
				}else{
					
					comPercent.show();
					if(currentCount == 0 && action == "showInsertProgressBar"){
						comPercent.text("正在清除原有数据，请稍候......");
					}else{
						comPercent.text("已完成："+Math.round(currentCount/totalCount*100)+"%");
						display.show();
						showProgress("display",totalCount,currentCount);
					}
					if(currentCount < totalCount){
						setTimeout("sendRequest('"+action+"')",200);//定时调用
					}else{
						if(action == "showInsertProgressBar"){
							comPercent.text("已完成数据查询，正在保存数据，请稍候......");
						}else{
							comPercent.hide();
						}
						display.hide();
						document.forms[0].labeUpdateTable.disabled = false;
						document.forms[0].labeDownload.disabled = false;
						comple();
					}
				}
			}
			
			function comple(){
				jQuery.get("<%=path%>/airticket/statisticsOrder.do?thisAction=comple");
				 
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
												票面价
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
										<c:check code="sc04">
									<input name="labeDownload" type="button" class="button3"  value="下载后返报表列表"
										onclick="downloadTable();"></c:check>
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
				<table align="center">
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td align="left">
							<span id="display" class="progressBar" style="display:none;"></span>
						</td>
					</tr>
					<tr>
						<td align="center" id="comPercent"><span id="compPercent"></span></td>
					</tr>
					
				</table>
			</div>
		</div>
		<br>
	</body>
</html>
