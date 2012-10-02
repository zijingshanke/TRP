<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="utf-8"%>
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
		<script type='text/javascript'
			src='<%=path%>/dwr/interface/platComAccountStore.js'></script>
		<script type='text/javascript' src='<%=path%>/dwr/engine.js'></script>
		<script type='text/javascript' src='<%=path%>/dwr/util.js'></script>
		<script src="../_js/common.js" type="text/javascript"></script>
		<script src="../_js/popcalendar.js" type="text/javascript"></script>
		<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>

		<script type="text/javascript">
			function download(){
				document.forms[0].action="<%=path%>/transaction/optTransactionList.do?thisAction=downloadBankCardPayment";
				document.forms[0].submit();
			}
			
			function createOptList(){	
				var startDate=document.forms[0].startDate.value;
				var endDate=document.forms[0].endDate.value;
				if(startDate.length<5 || endDate.length<5){
					alert("请选择日期");
				}else{
				document.forms[0].action="<%=path%>/transaction/optTransactionList.do?thisAction=list";
				document.forms[0].submit();
				}
				
				
			}
		</script>

	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
				<html:form
					action="/transaction/optTransactionList.do?thisAction=getOptTransactionlist">
					<html:hidden property="thisAction" value="getOptTransactionlist" />
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
									<c:param name="title1" value="报表管理" />
									<c:param name="title2" value="操作员收付款统计报表" />																			
								</c:import>	

								<div class="searchBar">
									<table cellpadding="0" cellspacing="0" border="0"
										class="searchPanel">
										<tr>
											<td>
												部门：
												<html:select property="userDepart">
													<html:option value="1">出票组</html:option>
													<html:option value="2">导票组</html:option>
													<html:option value="3">退票组</html:option>
													<html:option value="11">B2C组</html:option>
													<html:option value="12">团队部</html:option>
													<html:option value="21">支付组</html:option>
													<html:option value="22">财务部</html:option>
												</html:select>
											</td>
											<!-- 
											<td>
												操作员代号
												<html:text property="sysName" styleClass="colorblue2 p_5"
													style="width:100px;" />
											</td>
											 -->
											<td>
												选择日期:
												<html:text property="startDate" styleClass="colorblue2 p_5"
													style="width:150px;" onclick="popUpCalendar(this, this);"
													readonly="true" />
												__
												<html:text property="endDate" styleClass="colorblue2 p_5"
													style="width:150px;" onclick="popUpCalendar(this, this);"
													readonly="true" />
											</td>
											<td>
												<input type="button" name="button" id="button" value="提交"
													class="submit greenBtn" onclick="createOptList()" />

												<input type="button" name="button" id="button" value="导出"
													onclick="download()" class="submit greenBtn" />
											</td>
										</tr>
									</table>
									<hr />
								</div>
								<table width="100%" cellpadding="0" cellspacing="0" border="0"
									class="dataList">
									<tr>
										<th>
											<div>
												序号
											</div>
										</th>
										<th>
											<div>
												操作员
											</div>
										</th>
										<th>
											<div>
												操作员姓名
											</div>
										</th>
										<th>
											<div>
												卖出订单总数
											</div>
										</th>
										<th>
											<div>
												正常订单
											</div>
										</th>
										<th>
											<div>
												改签订单
											</div>
										</th>
										<th>
											<div>
												退票订单
											</div>
										</th>
										<th>
											<div>
												废票订单
											</div>
										</th>
										<th>
											<div>
												取消订单
											</div>
										</th>
										<th>
											<div>
												卖出机票数量
											</div>
										</th>
										<th>
											<div>
												收款金额
											</div>
										</th>
										<th>
											<div>
												付款金额
											</div>
										</th>
										<th>
											<div>
												收退款金额
											</div>
										</th>
										<th>
											<div>
												付退款金额
											</div>
										</th>
										<th>
											<div>
												取消出票收款
											</div>
										</th>
										<th>
											<div>
												取消出票退款
											</div>
										</th>
										<th>
											<div>
												利润
											</div>
										</th>

									</tr>

									<c:forEach var="o" items="${otf.list}" varStatus="sta">
										<tr>
											<td>
												<c:out value="${sta.count+(otf.intPage-1)*otf.perPageNum}" />
											</td>
											<td>
												<c:out value="${o.userNo}"></c:out>
											</td>
											<td>
												<c:out value="${o.userName}"></c:out>
											</td>
											<td>
												<c:out value="${o.sellorderstotal}"></c:out>
											</td>
											<td>
												<c:out value="${o.normalorder}"></c:out>
											</td>
											<td>
												<c:out value="${o.alteredorder}"></c:out>
											</td>
											<td>
												<c:out value="${o.refundorder}"></c:out>
											</td>
											<td>
												<c:out value="${o.invalidorder}"></c:out>
											</td>
											<td>
												<c:out value="${o.cancelorder}"></c:out>
											</td>
											<td>
												<c:out value="${o.soldticketCount}"></c:out>
											</td>
											<td>
												<c:out value="${o.inamount}"></c:out>
											</td>
											<td>
												<c:out value="${o.outamount}"></c:out>
											</td>
											<td>
												<c:out value="${o.refundamountreceived}"></c:out>
											</td>
											<td>
												<c:out value="${o.refundamountpaid}"></c:out>
											</td>
											<td>
												<c:out value="${o.cancelticketcollection}"></c:out>
											</td>
											<td>
												<c:out value="${o.cancelticketrefund}"></c:out>
											</td>
											<td>
												<c:out value="${o.profit}"></c:out>
											</td>
										</tr>
									</c:forEach>
									<tr>
										<td>
											<div align="center">
												<font>合计</font>
											</div>
										</td>
										<td>
											&nbsp;
										</td>
										<td>
											&nbsp;
										</td>
										<td>
											<fmt:formatNumber value="${otf.totalValues[7]}"
												pattern="####" />
										</td>
										<td>
											<fmt:formatNumber value="${otf.totalValues[8]}"
												pattern="####" />
										</td>
										<td>
											<fmt:formatNumber value="${otf.totalValues[9]}"
												pattern="####" />
										</td>
										<td>
											<fmt:formatNumber value="${otf.totalValues[10]}"
												pattern="####" />
										</td>
										<td>
											<fmt:formatNumber value="${otf.totalValues[11]}"
												pattern="####" />
										</td>
										<td>
											<fmt:formatNumber value="${otf.totalValues[12]}"
												pattern="####" />
										</td>
										<td>
											<fmt:formatNumber value="${otf.totalValues[13]}"
												pattern="####" />
										</td>
										<td>
											<c:out value="${otf.totalValue1}" />
										</td>
										<td>
											<c:out value="${otf.totalValue2}" />
										</td>
										<td>
											<c:out value="${otf.totalValue4}" />
										</td>
										<td>
											<c:out value="${otf.totalValue5}" />
										</td>
										<td>
											<c:out value="${otf.totalValue6}" />
										</td>
										<td>
											<c:out value="${otf.totalValue7}" />
										</td>
										<td>
											<c:out value="${otf.totalValue3}" />
										</td>
									</tr>
								</table>

								<table width="100%" style="margin-top: 5px;">
									<tr>
										<td></td>
										<td align="right">
											<div>
												共有记录&nbsp;
												<c:out value="${otf.totalRowCount}"></c:out>
												&nbsp;条&nbsp;&nbsp;&nbsp;&nbsp; [
												<a href="JavaScript:turnToPage(document.forms[0],0)">首页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],1)">上一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],2)">下一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],3)"> 末页</a>]
												页数:
												<c:out value="${otf.intPage}" />
												/
												<c:out value="${otf.pageCount}" />
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
