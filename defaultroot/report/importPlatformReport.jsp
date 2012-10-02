<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%
String path = request.getContextPath();
%>
<html>
	<head>
		<title>main</title>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />
		<script src="../_js/common.js" type="text/javascript"></script>
		<script src="../_js/calendar/WdatePicker.js" type="text/javascript"></script>
	</head>
	<body>
		<c:import url="../_jsp/mainTitle.jsp" charEncoding="UTF-8">
			<c:param name="title1" value="报表对比管理" />
			<c:param name="title2" value="导入平台报表" />
		</c:import>
		<html:form action="/transaction/platformCompare.do" method="post">
			<tr>
				<td width="10" height="10" class="tblt"></td>
				<td height="10" class="tbtt"></td>
				<td width="10" height="10" class="tbrt"></td>
			</tr>
			<tr>
				<td width="10" class="tbll"></td>
				<td valign="top" class="body">
					<table cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td>
								选择日期:
								<html:text property="beginDateStr" styleId="beginDateStr"
									styleClass="colorblue2 p_5" style="width:150px;"
									onfocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
									readonly="true" />
								--
								<html:text property="endDateStr" styleId="endDateStr"
									styleClass="colorblue2 p_5" style="width:150px;"
									onfocus="WdatePicker({startDate:'%y-%M-01 23:59:59',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
									readonly="true" />
							</td>
							<td>
								交易平台名称
							</td>
							<td>
								<html:select property="platformId" name="platformCompare"
									value="${platformCompare.platformId}"
									styleClass="colorblue2 p_5" style="width:100px;">
									<c:forEach items="${platformList}" var="platform">
										<html:option value="${platform.id}">
											<c:out value="${platform.showName}" />
										</html:option>
									</c:forEach>
								</html:select>
							</td>
							<td>
								报表类型
							</td>
							<td>
								<html:select property="type" name="platformCompare"
									value="${platformCompare.type}" styleClass="colorblue2 p_5"
									style="width:100px;">
										<html:option value="1">销售</html:option>
										<html:option value="2">采购</html:option>
										<html:option value="13">供应退废</html:option>
										<html:option value="14">采购退废</html:option>										
										<html:option value="15">供应退票</html:option>
										<html:option value="16">采购退票</html:option>																						
										<html:option value="17">供应废票</html:option>	
										<html:option value="18">采购废票</html:option>																					
								</html:select>
							</td>
							<td>
								<html:hidden property="listAttachName"></html:hidden>
								<input type="button" class="button1" value="选择报表"
									onclick="selectAttachment();" />
							</td>
							<td style="display: none">
								<input name="label" type="button" class="button1" value="清空文件"
									onclick="clearPlatformCompare();">
							</td>
							<td>
							</td>
							<td>
								<html:hidden property="thisAction" name="platformCompare" />
								<html:hidden property="fileName" name="platformCompare" />
								<html:hidden property="listAttachName" name="platformCompare" />
								<input name="label" type="button" class="button1" value="更新系统报表"
									onclick=updateOrderCompareList();>
								<input name="label" type="button" class="button1" value="开始对比"
									onclick=startPlatformCompare();>
								<input name="label" type="reset" class="button1" value="重 置"
									style="display: none">
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<hr>
			</table>
			<table cellpadding="0" cellspacing="0" border="0" class="dataList">
			<tbody>
				<tr>
					<td>
							<c:if test="${!empty problemCompareList}">
			问题单
				<table cellpadding="0" cellspacing="0" border="0" class="dataList">
					<th width="35">
						<div>
							&nbsp;序号
						</div>
					</th>
					<th  style="display: none">
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
					<th style="display: none">
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
					<c:forEach var="problemCompare" items="${problemCompareList}"
						varStatus="status">
						<tr>
							<td>
								<c:out value="${status.count}" />
							</td>
							<td  style="display: none">
								<c:out value="${problemCompare.platformName}" />
							</td>
							<td>
								<c:out value="${problemCompare.subPnr}" />
							</td>
							<td>
								<c:out value="${problemCompare.airOrderNo}" />
							</td>
							<td style="display: none">
								<c:out value="${problemCompare.payOrderNo}" />
							</td>
							<td>
								<c:out value="${problemCompare.inAccountName}" />
							</td>
							<td>
								<c:out value="${problemCompare.inAmount}" />
							</td>
							<td>
								<c:out value="${problemCompare.flightCode}" />
							</td>
							<td>
								<c:out value="${problemCompare.flightClass}" />
							</td>
							<td>
								<c:out value="${problemCompare.ticketNumber}" />
							</td>
							<td>
								<c:out value="${problemCompare.startPoint}" />
							</td>
							<td>
								<c:out value="${problemCompare.endPoint}" />
							</td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
					</td>
				</tr>
				<tr>
					<td>
			<c:if test="${!empty reportCompareList}">
			文件报表
				<table cellpadding="0" cellspacing="0" border="0" class="dataList">
					<th width="35">
						<div>
							&nbsp;序号
						</div>
					</th>
					<th  style="display: none">
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
					<th style="display: none">
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
					<c:forEach var="reportCompare" items="${reportCompareList}"
						varStatus="status">
						<tr>
							<td>
								<c:out value="${status.count}" />
							</td>
							<td  style="display: none">
								<c:out value="${reportCompare.platformName}" />
							</td>
							<td>
								<c:out value="${reportCompare.subPnr}" />
							</td>
								<td>
								<c:out value="${reportCompare.airOrderNo}" />
							</td>
							<td style="display: none">
								<c:out value="${reportCompare.payOrderNo}" />
							</td>
							<td>
								<c:out value="${reportCompare.inAccountName}" />
							</td>
							<td>
								<c:out value="${reportCompare.inAmount}" />
							</td>
							<td>
								<c:out value="${reportCompare.flightCode}" />
							</td>
							<td>
								<c:out value="${reportCompare.flightClass}" />
							</td>
							<td>
								<c:out value="${reportCompare.ticketNumber}" />
							</td>
							<td>
								<c:out value="${reportCompare.startPoint}" />
							</td>
							<td>
								<c:out value="${reportCompare.endPoint}" />
							</td>						
						</tr>
					</c:forEach>
				</table>
			</c:if>
						</td>
					<td>
			<c:if test="${!empty orderCompareList}">
			系统报表
				<table  cellpadding="0" cellspacing="0" border="0" class="dataList">
					<th width="35">
						<div>
							&nbsp;序号
						</div>
					</th>
					<th  style="display: none">
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
					<th style="display: none">
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
					<c:forEach var="orderCompare" items="${orderCompareList}"
						varStatus="status">
						<tr>
							<td>
								<c:out value="${status.count}" />
							</td>
							<td style="display: none">
								<c:out value="${orderCompare.platformName}" />
							</td>
							<td>
								<c:out value="${orderCompare.subPnr}" />
							</td>
							<td>
								<c:out value="${orderCompare.airOrderNo}" />
							</td>
							<td style="display: none">
								<c:out value="${orderCompare.payOrderNo}" />
							</td>
							<td>
								<c:out value="${orderCompare.inAccountName}" />
							</td>
							<td>
								<c:out value="${orderCompare.inAmount}" />
							</td>
							<td>
								<c:out value="${orderCompare.flightCode}" />
							</td>
							<td>
								<c:out value="${orderCompare.flightClass}" />
							</td>
							<td>
								<c:out value="${orderCompare.ticketNumber}" />
							</td>
							<td>
								<c:out value="${orderCompare.startPoint}" />
							</td>
							<td>
								<c:out value="${orderCompare.endPoint}" />
							</td>								
						</tr>
					</c:forEach>
				</table>
			</c:if>
			</td>
			</tr>
			</tbody>
			</table>
		
		</html:form>
	</body>
	<script type="text/javascript">	
		function startPlatformCompare(){				   
		    document.forms[0].action="<%=path%>/transaction/platformCompare.do?thisAction=comparePlatformReport";
		    document.forms[0].submit();
		}		
		
		function addPlatformReport(){	
			if(checkForm()){
				var thisAction =document.forms[0].thisAction.value;			   
		    	document.forms[0].action="<%=path%>/transaction/platformCompare.do?thisAction="+thisAction;
		    	document.forms[0].submit();
			}			
		}
		
		function updateOrderCompareList(){	
			if(checkForm()){		   
		    	document.forms[0].action="<%=path%>/transaction/platformCompare.do?thisAction=updateOrderCompareList";
		    	document.forms[0].submit();
			}			 
		}		
		
		function checkForm(){
		
			var startDate=document.getElementById("beginDateStr").value;
			var endDate=document.getElementById("endDateStr").value;
			
			//alert(startDate)
			if(startDate == null){
				alert("请选择开始日期")
				return false;
			}
			if(endDate == null){
				alert("请选择结束日期")
				return false;
			}
			
			var fileName=document.forms[0].fileName.value;
			if(fileName==""){
				alert("请选择并上传报表文件")
				return false;
			}
			
			return true;
		}	
		
		function selectAttachment() {
			var _url = "../page/editAttach.jsp";
			openWindow(580,220,_url);	
		}
		
		function getAttachs(listAttachName,_tempAttach,listAttachNum, vname) {
			 //alert('_tempAttach='+_tempAttach);
 			// alert('listAttachNum='+listAttachNum); 
			if(listAttachNum==0)			{
			  alert("您还没有上传附件！");
			  return false;
			}

			if(listAttachNum>1)	{
			  alert("您一次只能上传一个附件！");
			  return false;
			}
			var exName=vname.substr(vname.lastIndexOf(".")+1).toUpperCase();
			//alert(exName);
			//if(!(exName=="JPG"||exName=="BMP"||exName=="GIF"||exName=="JPEG")){
			//	alert("文件类型只能是JPG/BMP/GIF/JPEG");
				//return false;
			//}
			//alert(vname);
			document.forms[0].fileName.value=vname;
			//document.forms[0].listAttachName.value=listAttachName;
			
			addPlatformReport();
			return true;   
 	}
	</script>
</html>