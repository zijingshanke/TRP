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
		<script src="../_js/common.js" type="text/javascript"></script>
		<script src="../_js/calendar/WdatePicker.js" type="text/javascript"></script>
		<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" src="../_js/base/DateUtil.js"></script>

		<script type="text/javascript">
			$(function() {		
				var startDateValue=$('#startDate').val();
				if(startDateValue==null||startDateValue==""){
					  var today = new Date();
		      			var dateNow= showLocaleYesterday();
			  			$('#startDate').val(dateNow+" 00:00:00");
			 			 $('#endDate').val(dateNow+" 23:59:59");	
				}
				  
			});
		
			function download(){     
				document.forms[0].thisAction="downloadOptTransactionReport";
				document.forms[0].submit();
			}
			
			function list(){	
				var startDate=document.forms[0].startDate.value;
				var endDate=document.forms[0].endDate.value;
				if(startDate.length<5 || endDate.length<5){
					alert("请选择日期");
				}else{
					document.forms[0].submit();
				}
			}
		</script>

	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
				<html:form action="/airticket/report.do">
					<html:hidden property="thisAction"></html:hidden>
					<html:hidden property="optHead"></html:hidden>
					
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
												<html:select property="operatorDepart">													
													<html:option value="0">请选择</html:option>													
													<html:option value="1">出票组</html:option>
													<html:option value="2">倒票组</html:option>
													<html:option value="3">退票组</html:option>
													<html:option value="11">B2C组</html:option>
													<html:option value="12">团队部</html:option>
													<html:option value="21">支付组</html:option>
													<html:option value="22">财务部</html:option>
												</html:select>
											</td>
											<td>
												操作员
												<html:text property="operator" styleClass="colorblue2 p_5"
													style="width:100px;" />
											</td>
													<td>
									选择日期:
									<html:text property="startDate" styleId="startDate" styleClass="colorblue2 p_5"
										style="width:150px;"
										onfocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
										readonly="true" />
									__
									<html:text property="endDate" styleId="endDate" styleClass="colorblue2 p_5"
										style="width:150px;"
										onfocus="WdatePicker({startDate:'%y-%M-01 23:59:59',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
										readonly="true" />
								</td>
											<td>
												<input type="button" name="button" id="button" value="提交"
													class="submit greenBtn" onclick="list()" />

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
										<c:if test="${report.optHead[0]!=null}">
											<th>
												<div>
													操作员
												</div>
											</th>
										</c:if>
										<c:if test="${report.optHead[1]!=null}">
											<th>
												<div>
													操作员姓名
												</div>
											</th>
										</c:if>
										<c:if test="${report.optHead[2]!=null}">
											<th>
												<div>
													订单总数
												</div>
											</th>
										</c:if>
										<c:if test="${report.optHead[3]!=null}">
											<th>
												<div>
													正常订单
												</div>
											</th>
										</c:if>
										<c:if test="${report.optHead[4]!=null}">
											<th>
												<div>
													改签订单
												</div>
											</th>
										</c:if>
										<c:if test="${report.optHead[5]!=null}">
											<th>
												<div>
													退票订单
												</div>
											</th>
										</c:if>
										<c:if test="${report.optHead[6]!=null}">
											<th>
												<div>
													废票订单
												</div>
											</th>
										</c:if>
										<c:if test="${report.optHead[7]!=null}">
											<th>
												<div>
													取消订单
												</div>
											</th>
										</c:if>
										<c:if test="${report.optHead[8]!=null}">
											<th>
												<div>
													卖出机票数量
												</div>
											</th>
										</c:if>
										<c:if test="${report.optHead[9]!=null}">
											<th>
												<div>
													收款金额
												</div>
											</th>
										</c:if>
										<c:if test="${report.optHead[10]!=null}">
											<th>
												<div>
													付款金额
												</div>
											</th>
										</c:if>
										<c:if test="${report.optHead[11]!=null}">
											<th>
												<div>
													利润
												</div>
											</th>
										</c:if>
										<c:if test="${report.optHead[12]!=null}">
											<th>
												<div>
													收退款金额
												</div>
											</th>
										</c:if>
										<c:if test="${report.optHead[13]!=null}">
											<th>
												<div>
													付退款金额
												</div>
											</th>
										</c:if>
										<c:if test="${report.optHead[14]!=null}">
											<th>
												<div>
													取消出票收款
												</div>
											</th>
										</c:if>
										<c:if test="${report.optHead[15]!=null}">
											<th>
												<div>
													取消出票退款
												</div>
											</th>
										</c:if>
									</tr>
									<c:forEach var="optTransaction" items="${report.optList}"
										varStatus="sta">
										<tr>
											<c:if test="${report.optHead[0]!=null}">
												<td>
													<c:out value="${optTransaction.opterateNo}"></c:out>
												</td>
											</c:if>
											<c:if test="${report.optHead[1]!=null}">
												<td>
													<c:out value="${optTransaction.opterateName}"></c:out>
												</td>
											</c:if>
											<c:if test="${report.optHead[2]!=null}">
												<td>
													<c:out value="${optTransaction.totalOrderNum}"></c:out>
												</td>
											</c:if>
											<c:if test="${report.optHead[3]!=null}">
												<td>
													<c:out value="${optTransaction.normalOrderNum}"></c:out>
												</td>
											</c:if>
											<c:if test="${report.optHead[4]!=null}">
												<td>
													<c:out value="${optTransaction.umbuchenOrderNum}"></c:out>
												</td>
											</c:if>
											<c:if test="${report.optHead[5]!=null}">
												<td>
													<c:out value="${optTransaction.retireOrderNum}"></c:out>
												</td>
											</c:if>
											<c:if test="${report.optHead[6]!=null}">
												<td>
													<c:out value="${optTransaction.invalidOrderNum}"></c:out>
												</td>
											</c:if>
											<c:if test="${report.optHead[7]!=null}">
												<td>
													<c:out value="${optTransaction.cancelOrderNum}"></c:out>
												</td>
											</c:if>
											<c:if test="${report.optHead[8]!=null}">
												<td>
													<c:out value="${optTransaction.saleTicketNum}"></c:out>
												</td>
											</c:if>
											<c:if test="${report.optHead[9]!=null}">
												<td>
													<fmt:formatNumber value="${optTransaction.inAmount}" />
												</td>
											</c:if>
											<c:if test="${report.optHead[10]!=null}">
												<td>
													<c:out value="${optTransaction.outAmount}" />
												</td>
											</c:if>
											<c:if test="${report.optHead[11]!=null}">
												<td>
													<c:out value="${optTransaction.profits}" />
												</td>
											</c:if>
											<c:if test="${report.optHead[12]!=null}">
												<td>
													<c:out value="${optTransaction.inRetireAmount}" />
												</td>
											</c:if>
											<c:if test="${report.optHead[13]!=null}">
												<td>
													<c:out value="${optTransaction.outRetireAmount}" />
												</td>
											</c:if>
											<c:if test="${report.optHead[14]!=null}">
												<td>
													<c:out value="${optTransaction.inCancelAmount}" />
												</td>
											</c:if>
											<c:if test="${report.optHead[15]!=null}">
												<td>
													<c:out value="${optTransaction.outCancelAmount}" />
												</td>
											</c:if>
										</tr>
									</c:forEach>
								</table>
							</td>
						</tr>
					</table>
				</html:form>
			</div>
		</div>
	</body>
</html>
