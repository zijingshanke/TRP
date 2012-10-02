<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<html>
	<head>
		<title>main</title>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" language="javascript"
			src="../_js/jquery-1.3.2.min.js"></script>
		<script src="../_js/common.js" type="text/javascript"></script>
		<script src="../_js/calendar/WdatePicker.js" type="text/javascript"></script>
		<script src="../_js/popcalendar.js" type="text/javascript"></script>
		<script>
			String.prototype.Trim   =   function() {   
			  return   this.replace(/(^\s*)|(\s*$)/g,   "");   
			 }
			function edit(){
				
				if(check() == true){
					var quota = document.forms[0].quota;
					if(quota.value.Trim().length == 0){
						quota.value = 0;
					}
				    document.forms[0].submit();
				}
			}
			
			function check(){
				var flightCode = document.forms[0].flightCode.value.Trim();					//航班号
				var discount = document.forms[0].discount.value.Trim();						//折扣
				var rate = document.forms[0].rate.value.Trim();								//后返佣金
				var quota = document.forms[0].quota.value.Trim();							//任务额度
				if(flightCode.length==0 || discount.length==0 || rate.length==0 ){
					alert("请填写完整信息!");
					return false;
				}
				if(!isInteger(discount)){
					alert("折扣只能是整数");
					return false;
				}
				if(!(isMoney(rate) || isInteger(rate))){
					alert("后返佣金只能是金额或整数");
					return false;
				}
				if(quota.length!=0){
					if(!(isMoney(quota) || isInteger(quota))){
						alert("任务额度只能是金额或整数");
						return false;
					}
				}
				return true;
				
			}
			//检查是否金额
			function isMoney( s ){ 
				var regu = "^[0-9]+[\.][0-9]{0,3}$"; 
				var re = new RegExp(regu); 
				if (re.test(s)) { 
					return true; 
				} else { 
					return false; 
				} 
			} 
			//检查是否整数
			function isInteger( str ){ 
				var regu = /^[-]{0,1}[0-9]{1,}$/; 
				return regu.test(str); 
			} 
		</script>
	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
				<html:form action="/policy/policyAfter.do">
					<html:hidden property="thisAction" name="policyAfter"/>
					<html:hidden property="id" name="policyAfter" />
					<html:hidden property="ticketType" name="policyAfter" value="1" />
					<html:hidden property="airlinePolicyAfterId" name="policyAfter" value="${policyAfter.airlinePolicyAfter.id}"/> 
					<html:hidden property="userName" name="policyAfter" value="${policyAfter.userName}"/>
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
									<c:param name="title1" value="政策管理" />
									<c:param name="title2" value="编辑信息" />																			
								</c:import>							
								
								<hr>
								<table width="100%" cellpadding="0" cellspacing="0" border="0"
									class="dataList">
									<tr>
										<td class="lef">
											政策
										</td>
										<td style="text-align:left">
											<c:out value="${policyAfter.airlinePolicyAfter.name}"></c:out>
										</td>
									</tr>
									<tr>
										<td class="lef">
											航班号
										</td>
										<td style="text-align: left">
											<html:text property="flightCode" name="policyAfter" size="60"
												styleClass="colorblue2 p_5" />
											<font color="red">*</font>
										</td>
									</tr>
									
									<tr>
										<td class="lef">
											不适用航班
										</td>
										<td style="text-align: left">
											<html:text property="flightCodeExcept" name="policyAfter" size="60"
												styleClass="colorblue2 p_5" />
										</td>
									</tr>
									<tr>
										<td class="lef">
											航段
										</td>
										<td style="text-align: left">
											<html:textarea property="startEnd" name="policyAfter" cols="48" rows="3"/>
										</td>
									</tr>
									<tr>
										<td class="lef">
											舱位
										</td>
										<td style="text-align: left">
											<html:textarea property="flightClass" name="policyAfter" cols="48" rows="3"/>
												
										</td>
									</tr>
									<tr>
										<td class="lef">
											不适用舱位
										</td>
										<td style="text-align: left">
											<html:textarea property="flightClassExcept" name="policyAfter" cols="48" rows="3"/>
										</td>
									</tr>
									<tr>
										<td class="lef">
											折扣
										</td>
										<td style="text-align: left">
											<html:text property="discount" name="policyAfter"
												styleClass="colorblue2 p_5" />
											<font color="red">*</font>
										</td>
									</tr>
									<tr>
										<td class="lef">
											后返佣金
										</td>
										<td style="text-align: left">
											<html:text property="rate" name="policyAfter"
												styleClass="colorblue2 p_5" />%
											<font color="red">*</font>
										</td>
									</tr>
									<tr>
										<td class="lef">
											任务额度
										</td>
										<td style="text-align: left">
											<html:text property="quota" name="policyAfter"
												styleClass="colorblue2 p_5" />元
										</td>
									</tr>
									<tr>
										<td class="lef">
											备注
										</td>
										<td style="text-align:left">
											<html:textarea property="memo" name="policyAfter" cols="48" rows="3"/>
										</td>
									</tr>
									<tr>
										<td class="lef">
											行程类型
										</td>
										<td style="text-align: left">
											<html:radio property="travelType" value="1" name="policyAfter">单程</html:radio>
											<html:radio property="travelType" value="2" name="policyAfter">往返</html:radio>
										</td>
									</tr>
									<tr>
										<td class="lef">
											状态
										</td>
										<td style="text-align: left">
											<html:radio property="status" value="1" name="policyAfter">启用</html:radio>
											<html:radio property="status" value="2" name="policyAfter">停用</html:radio>
										</td>
									</tr>
								</table>
								<table width="100%" style="margin-top: 5px;">
									<tr>
										<td>
											<input name="label" type="button" class="button1" value="提 交"
												onclick="edit();">
											<input name="label" type="button" class="button1" value="重 置"
												onclick="document.policyAfter.reset();">
											<input name="label" type="button" class="button1" value="取消"
												onclick="javascript:window.history.back()">
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
