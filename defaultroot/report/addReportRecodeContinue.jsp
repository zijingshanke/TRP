<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%
String path = request.getContextPath();
%>
<html>
	<head>
		<title>继续增加报表</title>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />
		<script src="../_js/common.js" type="text/javascript"></script>
		<script src="../_js/base/FormUtil.js" type="text/javascript"></script>
		<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" src="../_js/base/DateUtil.js"></script>
		<style>
#divSearchBarTool {
	margin-bottom: 5px;
}

#divProblemCompareList1 {
	float: left;
	width: 700px;
}

#divProblemCompareList2 {
	margin-left: 700px;
}

#divReportCompareList {
	margin-left: 700px;
}
</style>
	<script type="text/javascript">		
		function add(){	
			var addContinueForm = document.all.addContinueForm;
			if(checkForm()){
				var fileName = addContinueForm.fileName.value;
					if(fileName==""){
						alert("请选择并上传报表文件")
						return false;
				}						
				var thisAction = addContinueForm.thisAction;
				if(thisAction == null){
					alert("thisAction不能为空");
					return false;
				}else{
					if(thisAction.value==""){
						alert("thisAction不能为空");
						return false;
					}
				 	addContinueForm.action="<%=path%>/transaction/reportRecodeResult.do?thisAction="+thisAction.value;
			    	addContinueForm.submit();
				} 
			}
			
		}		
			
		
		function checkForm(){				
			return true;
		}	
			
			function selectAttachment() {
			if(checkForm()){
				var _url = "../page/editAttach.jsp";
				openWindow(580,220,_url);	
			}
		}
		
		function getAttachs(listAttachName,_tempAttach,listAttachNum, vname) {
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
			return true;   
 	}
 	</script>
 	<script type="text/javascript">
			function deleteReportRecode(){
				var form1 = document.all.form1;
				var selectItem = form1.selectedItems;
				var i = 0;
				for(j=0;j<selectItem.length;j++){
					if(selectItem[j].checked == true){
						i++;
					}
				}
				if(i<1){
					alert("请选择要删除的数据");
				}else{
					if(window.confirm("你真的要删除选中的记录吗？")){
						var action = "<%=path%>/transaction/reportRecodeList.do";
						form1.thisAction.value="delete";
						var reportRecodeResultId = document.all.addContinueForm.id.value;
						form1.reportRecodeResultId.value = reportRecodeResultId;
						form1.submit();
					}
				}
			}
			function deleteReportRecodeAll(){
				if(window.confirm("注意:此操作将会删除所有记录,你真的要删除全部记录吗？")){
					var form1 = document.all.form1;
					var reportRecodeResultId = document.all.addContinueForm.id.value;
					form1.reportRecodeResultId.value = reportRecodeResultId;
					var action = "<%=path%>/transaction/reportRecodeList.do?";
					form1.thisAction.value="deleteAllByResultId";
					form1.submit();
				}
			}
		</script>
	</head>
	<body>
		<div id="divSearchBarTool">
			<html:form action="/transaction/reportRecodeResult.do" styleId="addContinueForm">
				<html:hidden property="thisAction" name="reportRecodeResult" />
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
								<c:param name="title2" value="继续添加原始报表" />
							</c:import>
							<hr>
							<table width="100%" cellpadding="0" cellspacing="0" border="0"
								class="dataList">
								<tr>
									<td class="lef">
										报表名称
									</td>
									<td style="text-align: left">
										<c:out value="${reportRecodeResult.name}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										开始时间
									</td>
									<td style="text-align: left">
										<c:out value="${reportRecodeResult.formatBeginDate}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										结束时间
									</td>
									<td style="text-align: left">
										<c:out value="${reportRecodeResult.formatEndDate}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										已经导入部分
									</td>
									<td style="text-align: left">
										<c:forEach items="${reportRecodeResult.idNameCount}" var="idNameCount">
											<html:link
												page="/transaction/reportRecodeResultList.do
													?thisAction=addReportRecodeContinue&id=${reportRecodeResult.id}&indexId=${idNameCount.key}">
													<c:forEach items="${idNameCount.value}" var="nameCount">
														<c:out value="${nameCount.key}" />(<c:out value="${nameCount.value}" />)条,
													</c:forEach>
												
											</html:link>
										</c:forEach>
									</td>
								</tr>
								<c:if test="${reportRecodeResult.reportType==1}">
									<tr>
										<td class="lef">
											交易平台
										</td>
										<td style="text-align: left">
											<html:select property="idTranType"
												styleClass="colorblue2 p_5" style="width:140px;">
												<c:forEach items="${platformList}" var="platform">
													<html:option value="${platform.idTranType}">
														<c:out value="${platform.nameTranType}" />
													</html:option>
												</c:forEach>
											</html:select>
											<input type="button" class="button3" value="选择上传文件"
												onclick="selectAttachment();" />
												<input name="label" type="button" class="button2"
														value="提交导入" onclick="add();">
										</td>
									</tr>
								</c:if>
								<c:if test="${reportRecodeResult.reportType==2}">
									<tr>
										<td class="lef">
											支付工具：
										</td>
										<td style="text-align: left">
											<html:select property="reportIndexId" styleClass="colorblue2 p_5"
												style="width:140px;">
												<c:forEach items="${reportIndexList}" var="reportIndex">
													<html:option value="${reportIndex.id}">
														<c:out value="${reportIndex.name}" />
													</html:option>
												</c:forEach>
											</html:select>
											<input type="button" class="button3" value="选择上传文件"
												onclick="selectAttachment();" />
												<input name="label" type="button" class="button2"
														value="提交导入" onclick="add();">
										</td>
									</tr>
									
									<tr style="display: none">
										<td class="lef">
											索引名称：
										</td>
										<td style="text-align: left">
											<html:select property="reportIndexId" styleClass="colorblue2 p_5"
												style="width:100px;">
												<c:forEach items="${reportIndexList}" var="reportIndex">
													<html:option value="${reportIndex.id}">
														<c:out value="${reportIndex.name}" />
													</html:option>
												</c:forEach>
											</html:select>
											<input type="button" class="button3" value="选择上传文件"
												onclick="selectAttachment();" />
												<input name="label" type="button" class="button2"
														value="提交导入" onclick="add();">
										</td>
									</tr>
								</c:if>
							</table>
							<table width="100%" style="margin-top: 5px;">
								<tr>
									<td>
										<table width="100%" style="margin-top: 5px;">
											<tr>
												<td align="center">
													<html:hidden property="id"></html:hidden>
														<!--129822011022142 支付宝付航司  -->
														<!--129822011022198 财付通收平台  -->
														<!--129822011022168 易宝信用支付-->
														<!--129822011022188 今日通采购-->
														<!--129822011022155 泰申公司供应-->
														<!-- 12982201177777 足印供应 -->
													<html:hidden property="fileName" value="12982201177777.xls" name="reportRecodeResult" />&nbsp;&nbsp; 
													
												</td>
											</tr>
										</table>
										<table width="100%" style="margin-top: 5px;">
											<tr>
												<td>
													<input name="label" type="button" class="button1" value="返 回"
														onclick="window.history.back();" />
													<input name="label" type="button" class="button1" value="清空全部"
														onclick="deleteReportRecodeAll();" />
													<input name="label" type="button" class="button1" value="选择删除"
														onclick="deleteReportRecode();" />&nbsp;										
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
						<td width="10" class="tbrr"></td>
					</tr>
					<tr>
				</table>
			</html:form>
			<div id="divReportRecodeList">
				<jsp:include page="../transaction/listReportRecode.jsp"></jsp:include>
			</div>
		</div>
	</body>
</html>