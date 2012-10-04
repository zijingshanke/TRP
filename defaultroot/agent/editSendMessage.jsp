
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
		<title>SendMessage</title>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />

		<style>
			.divstyle {
				padding: 5px;
				font-family: "MS Serif", "New York", serif;
				background: #e5e5e5;
			}
		</style>
		<script type="text/javascript">
	
			//添加
			function addAgentList()
		{	
			var name=document.forms[0].name.value;
			if(name=="")
			{
				alert("请输入支付名称!")
				return false;
			}
			var thisAction =document.forms[0].thisAction.value;			   
		    document.forms[0].action="<%=path%>/transaction/agent.do?thisAction="+thisAction;
		    document.forms[0].submit();
		}
		function sendMessage(){
			var content = document.forms[0].content.value; 
			var receiver = document.forms[0].receiver.value; 
			if(content.length==0){
				alert("发送内容不能为空");
				return;
			}
			if(!checkReceiver(receiver)){
				return;
			}
			if(window.confirm("确定要发送短信吗？")){
				document.forms[0].submit();
			}
		}
		function checkReceiver(receiverStr){
			var array = receiverStr.split(",");
			var mobelFormate = /^0*(13|15|18)\d{9}$/;
			var mobel = "";
			for(i=0;i<array.length;i++){
				if(array[i].indexOf("(") != -1){
					mobel = array[i].substring(0,array[i].indexOf("("));
				}else{
					mobel = array[i];
				}
				if(!mobelFormate.test(mobel)){
					alert("手机号码格式错误,请检查:"+array[i]);
					return false;
				}
			}
			return true;
		}
	</script>
	</head>
	<body>
		<html:form action="/transaction/agentList.do" method="post">
			<html:hidden property="operatorObject" name="agentListForm" />
			<div id="mainContainer">
				<div id="container">
					<table width="100%" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td width="10" height="10" class="tblt"></td>
							<td height="10" class="tbtt"></td>
							<td width="10" height="10" class="tbrt"></td>
						</tr>
						<tr>
							<td width="10" class="tbll"></td>
							<td valign="top" class="body">

								<hr>
								<table width="100%" cellpadding="0" cellspacing="0" border="0"
									class="dataList">
									<tr>
										<td class="lef">
											短信内容:
										</td>
										<td style="text-align: left">
											
											<html:textarea property="content"  name="agentListForm" cols="60" rows="4"
												value="">
											</html:textarea>
										</td>
									</tr>
									<tr>
										<td class="lef">
											发送到：
										</td>
										<td style="text-align:left;">
											<html:textarea  property="receiver"  name="agentListForm" cols="60" rows="4">
											</html:textarea>
											<font color="red">格式：13或15开头手机号码(姓名)&nbsp; 或 13或15开头手机号码；两个号码之间用英文逗号隔开,小括弧为英文括弧</font>
										</td>
									</tr>
								</table>
								<table width="100%" style="margin-top: 5px;">
									<tr>
										<td>
											<html:hidden property="thisAction" name="agentListForm" />
											<input name="label" type="button" class="button1" value="发送"
												onclick="sendMessage();">
											<input name="label" type="reset" class="button1" value="重 置">
											<input name="label" type="button" class="button1" value="返 回"
												onclick="window.history.back();">
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
				</div>
			</div>
		</html:form>
	</body>
</html>


