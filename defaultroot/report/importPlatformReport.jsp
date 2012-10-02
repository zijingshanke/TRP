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
									<html:option value="2">退废</html:option>
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
								<input name="label" type="button" class="button1" value="导入"
									onclick=addPlatformReport();>
								<input name="label" type="button" class="button1" value="开始对比"
									onclick=startCompare();>
								<input name="label" type="reset" class="button1" value="重 置"
									style="display: none">
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<hr>
			</table>
			<c:if test="${!empty compareList}">
				<table cellpadding="0" cellspacing="0" border="0" class="dataList">
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
							预定编码
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
							状态
						</div>
					</th>
					<c:forEach var="reportCompare" items="${compareList}"
						varStatus="status">
						<tr>
							<td>
								<c:out value="${status.count}" />
							</td>
							<td>
								<c:out value="${reportCompare.platformName}" />
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

							<td>
								<c:out value="${reportCompare.subPnr}" />
							</td>

							<td>
								<c:out value="${reportCompare.discount}" />
							</td>
							<td>
								<c:out value="${reportCompare.typeInfo}" />
							</td>
							<td>
								<c:out value="${reportCompare.memo}" />
							</td>
							<td>
								<c:out value="${reportCompare.userName}" />
							</td>
							<td>
								<c:out value="${reportCompare.statusInfo}" />
							</td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
		</html:form>
	</body>
	<script type="text/javascript">	
		function addPlatformReport(){	
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
			var thisAction =document.forms[0].thisAction.value;			   
		    document.forms[0].action="<%=path%>/transaction/platformCompare.do?thisAction="+thisAction;
		    document.forms[0].submit();
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
			return true;   
 	}
	</script>
</html>