<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<html>
	<head>
		<title>main</title>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" language="javascript"
			src="../_js/jquery-1.3.2.min.js"></script>
		<script src="../_js/common.js" type="text/javascript"></script>

		<script>
			function addSaleStatistics(){
				
			    document.forms[0].thisAction.value="add";
			    document.forms[0].submit();
			}
			
			function editSaleStatistics(){
			
			 if(document.forms[0].selectedItems==null){
			   	alert("没有数据，无法操作！");
			  }else if (sumCheckedBox(document.forms[0].selectedItems)<1){
			    alert("您还没有选择记录！");
			   }
			  else if (sumCheckedBox(document.forms[0].selectedItems)>1){
			    alert("您一次只能选择一条记录！");
			  }
			  else{
			    document.forms[0].thisAction.value="edit";
			    document.forms[0].submit();
			  }
			}
			
			function delSaleStatistics(){
			 if(document.forms[0].selectedItems==null){
			  	alert("没有数据，无法操作！");
			  }else if (sumCheckedBox(document.forms[0].selectedItems)<1){
			    alert("您还没有选择记录！");
			  }
			  else if(confirm("您真的要删除选择的这些记录吗？")){
			    document.forms[0].thisAction.value="delete";
			    document.forms[0].submit();
			  }
			}
			
			function queryRecord(){
				var year = document.getElementById("year").value;
				var month = document.getElementById("month").value;
				var beginDate = document.forms[0].beginDate;
				if(year != 1970){
					if(month != 0){
						beginDate.value = year+"-"+month+"-"+"01 "+"00:00:01";
					}else{
						beginDate.value = year+"-01-01 00:00:00";
					}
				}else{
					if(month != 0){
						beginDate.value = year+"-"+month+"-"+"01 "+"00:00:01";
					}else{
						beginDate.value="1970-01-01 00:00:00";
					}
				}
				document.forms[0].submit();
			}
			
			function init(){
				var year = document.getElementById("year");
				var month = document.getElementById("month");
				var startDate = document.forms[0].beginDate.value;
				for(i=0;i<year.length;i++){
					if(year[i].value == startDate.substring(0,4)){
						year[i].selected = "true";
					}
				}
				if(startDate.substring(17,19)=="00"){
					month[0].selected="true";
				}else{
					for(j=0;j<month.length;j++){
						if(month[j].value == startDate.substring(5,7)){
							month[j].selected="true";
						}
					}
				}
				
			}
		</script>
	</head>
	<body onload="init();">	
		<div id="mainContainer">
			<div id="container">
				<html:form action="/policy/saleStatisticsList.do">
					<html:hidden property="thisAction" />
					<html:hidden property="lastAction" />
					<html:hidden property="intPage" />
					<html:hidden property="pageCount" />
					<html:hidden property="beginDate" />
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
									<c:param name="title1" value="订单后返" />
									<c:param name="title2" value="订单后返列表" />																						
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
												承运人：
												<html:select property="carrier">
													<html:option value="">请选择</html:option>
													<html:option value="CA">国航(CA)</html:option>
													<html:option value="MU">东航(MU)</html:option>
													<html:option value="HU">海航(HU)</html:option>
													<html:option value="ZH">深航(ZH)</html:option>
													<html:option value="KN">联航(KN)</html:option>
													<html:option value="SC">新、老山航(SC)</html:option>
													<html:option value="3U">川航(3U)</html:option>
													<html:option value="MF">厦航(MF)</html:option>
													<html:option value="CZ">南航(CZ)</html:option>
													<html:option value="FM">上航(FM)</html:option>
													<html:option value="GS">天津(GS)</html:option>
													<html:option value="8L">翔鹏(8L)</html:option>
													<html:option value="JD">(JD)金鹿</html:option>
												</html:select>
											</td>
											<td>
												<select id="year" Class="colorblue2 p_5" style="width:70px;">
													<option value="1970">请选择</option>
													<option value="2010">2010</option>
													<option value="2011">2011</option>
													<option value="2012">2012</option>
													<option value="2013">2013</option>
													<option value="2014">2014</option>
													<option value="2015">2015</option>
													<option value="2016">2016</option>
													<option value="2017">2017</option>
													<option value="2018">2018</option>
													<option value="2019">2019</option>
													<option value="2020">2020</option>
												</select>年
											</td>
											<td>
												<select id="month" Class="colorblue2 p_5" style="width:70px;">
													<option value="0">请选择</option>
													<option value="01">1</option>
													<option value="02">2</option>
													<option value="03">3</option>
													<option value="04">4</option>
													<option value="05">5</option>
													<option value="06">6</option>
													<option value="07">7</option>
													<option value="08">8</option>
													<option value="09">9</option>
													<option value="10">10</option>
													<option value="11">11</option>
													<option value="12">12</option>
												</select>月
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
										<th width="35">
											<div>
												&nbsp;
											</div>
										</th>
										<th>
											<div>
												航空公司
											</div>
										</th>
										<th>
											<div>
												政策
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
												完成高舱票数
											</div>
										</th>
										<th>
											<div>
												高舱票超指标奖励(元/张)
											</div>
										</th>
										<th>
											<div>
												销售量
											</div>
										</th>
										<th>
											<div>
												后返佣金
											</div>
										</th>
										<th>
											<div>
												总后返金额
											</div>
										</th>
										<th>
											<div>
												状态
											</div>
										</th>
									</tr>
									<c:forEach var="saleStatistics" items="${sslf.list}" varStatus="status">
										<tr>
											<td>
												<html:multibox property="selectedItems"
													value="${saleStatistics.id}">
												</html:multibox>
											</td>
											<td>
												<c:out value="${saleStatistics.carrier}" />
											</td>
											<td>
												<html:link
													page="/policy/saleStatistics.do?thisAction=view&id=${saleStatistics.id}">
													<c:out value="${saleStatistics.airlinePolicyAfter.name}" />
												</html:link>
												
											</td>
											<td>
												<c:out value="${saleStatistics.beginDate}" />
											</td>
											<td>
												<c:out value="${saleStatistics.endDate}" />
											</td>
											<td>
												<c:out value="${saleStatistics.highClassTicketNum}" />
											</td>
											<td>
												<c:out value="${saleStatistics.highClassAward}" />
											</td>
											<td>
												<c:out value="${saleStatistics.saleAmount}" />
											</td>
											<td>
												<c:out value="${saleStatistics.profitAfter}" />
											</td>
											<td>
												<c:out value="${saleStatistics.afterAmount}" />
											</td>
											<td>
												<c:out value="${saleStatistics.status}" />
												
											</td>
										</tr>
									</c:forEach>

								</table>

								<table width="100%" style="margin-top: 5px;">
									<tr>
										<td>
											<input name="label" type="button" class="button1" value="新 增" onclick="addSaleStatistics();">
											<input name="label" type="button" class="button1" value="修 改" onclick="editSaleStatistics();">
											<input name="label" type="button" class="button1" value="删 除" onclick="delSaleStatistics();">
										</td>
										<td align="right">
											<div>
												共有记录&nbsp;
												<c:out value="${sslf.totalRowCount}"></c:out>
												&nbsp;条&nbsp;&nbsp;&nbsp;&nbsp; [
												<a href="JavaScript:turnToPage(document.forms[0],0)">首页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],1)">上一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],2)">下一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],3)"> 末页</a>]
												页数:
												<c:out value="${sslf.intPage}" />
												/
												<c:out value="${sslf.pageCount}" />
												]
											</div>
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
