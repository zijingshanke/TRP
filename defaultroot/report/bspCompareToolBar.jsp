<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="../WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="../WEB-INF/c.tld" prefix="c"%>

<%
String path = request.getContextPath();
%>
<c:import url="../_jsp/mainTitle.jsp" charEncoding="UTF-8">
	<c:param name="title1" value="报表对比管理" />
	<c:param name="title2" value="对比BSP报表" />
</c:import>
<html:form action="/transaction/reportCompare.do" method="post">
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
							value="${tempCompare.beginDateStr}" style="width:150px;"
							onfocus="WdatePicker({startDate:'%y-%M-%D 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
							readonly="true" />
						--
						<html:text property="endDateStr" styleId="endDateStr"
							value="${tempCompare.endDateStr}" style="width:150px;"
							onfocus="WdatePicker({startDate:'%y-%M-%D 23:59:59',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
							readonly="true" />
					</td>
					<td>
						<html:hidden property="listAttachName"></html:hidden>
						<input type="button" class="button3" value="选择上传文件"
							onclick="selectAttachment();"  />
					</td>
					<td>
						<html:hidden property="compareType" value="2" name="reportCompare"  />
						<html:hidden property="thisAction" name="reportCompare" />
						<html:hidden property="fileName" name="reportCompare" />
						<html:hidden property="listAttachName" name="reportCompare" />
						<input name="label" type="button" class="button2" value="更新系统报表"
							onclick=updateBSPOrderCompareList();>
						<input name="label" type="button" class="button1" value="开始对比"
							onclick=startBSPCompare();>
						<input name="label" type="button" class="button1" value="重 置"
							onclick="clearBSPCompare();">
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<hr>
</html:form>
<script type="text/javascript">	
		function startBSPCompare(){				   
		    document.forms[0].action="<%=path%>/transaction/reportCompare.do?thisAction=compareBSPReport";
		    document.forms[0].submit();
		}		
		
		function addBSPReport(){	
			if(checkForm()){   
		    	document.forms[0].action="<%=path%>/transaction/reportCompare.do?thisAction=insertBSPReport";
		    	document.forms[0].submit();
			}			
		}
		
		function updateBSPOrderCompareList(){	
			if(checkForm()){		   
		    	document.forms[0].action="<%=path%>/transaction/reportCompare.do?thisAction=updateBSPOrderCompareList";
		    	document.forms[0].submit();
			}			 
		}		
		
		function clearBSPCompare(){	   
		    	document.forms[0].action="<%=path%>/transaction/reportCompareList.do?thisAction=clearBSPCompare";
		    	document.forms[0].submit();
		}				
		
		function checkForm(){		
			var startDate=document.getElementById("beginDateStr").value;
			var endDate=document.getElementById("endDateStr").value;
			
			//alert(startDate)
			if(startDate == null|| startDate ==""){
				alert("请选择开始日期")
				return false;
			}
			if(endDate == null|| endDate ==""){
				alert("请选择结束日期")
				return false;
			}
			
			//var fileName=document.forms[0].fileName.value;
			//if(fileName==""){
				//alert("请选择并上传报表文件")
				//return false;
			//}
			
			var reportCompareList='';
			
			return true;
		}	
		
		function selectAttachment() {
			if(checkForm()){
				var _url = "../page/editAttach.jsp";
				openWindow(580,220,_url);	
			}
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
			
			addBSPReport();
			return true;   
 	}
	</script>
