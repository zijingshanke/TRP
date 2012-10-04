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
				var flightCodeExcept = document.forms[0].flightCodeExcept.value.Trim();		//不适航班号
				var startEnd = document.forms[0].startEnd.value.Trim();						//航段
				var startEndExcept = document.forms[0].startEndExcept.value.Trim();			//不适航段
				var flightClass = document.forms[0].flightClass.value.Trim();				//舱位
				var flightClassExcept = document.forms[0].flightClassExcept.value.Trim();	//不适舱位
				var discount = document.forms[0].discount.value.Trim();						//折扣
				var rate = document.forms[0].rate.value.Trim();								//后返佣金
				var quota = document.forms[0].quota.value.Trim();							//任务额度
				var memo = document.forms[0].memo.value.Trim();								//政策内容
				var beginDate = document.forms[0].beginDate.value.Trim();					//任务额度
				/*
				var endDate = document.forms[0].endDate.value.Trim();						//任务额度
				*/
	
				if(flightCodeExcept.length != 0){						//不适航班号
					if(!isFlightCode(flightCodeExcept)){
						return false;
					}
				}
				if(startEnd.length != 0){								//航段
					if(!isStartEnd(startEnd)){
						return false;
					}
				}
				if(startEndExcept.length != 0){							//不适航段
					if(!isStartEnd(startEndExcept)){
						return false;
					}
				}
				if(flightClass.length != 0){							//舱位
					if(!isFlightClass(flightClass)){
						return false;
					}
				}
				if(flightClassExcept.length != 0){						//不适舱位
					if(!isFlightClass(flightClassExcept)){
						return false;
					}
				}
				if(discount.length == 0){
					alert("请填写折扣信息");
					return false;
				}else{
					if(!(isInteger(discount) || isMoney(discount))){
						alert("折扣只能是整数或小数");
						return false;
					}
				}
				
				if(rate.length == 0){
					alert("请填写后返佣金信息");
					return false;
				}else{
					if(!(isMoney(rate) || isInteger(rate))){
						alert("后返佣金只能是小数或整数");
						return false;
					}
				}
				if(quota.length!=0){
					if(!(isMoney(quota) || isInteger(quota))){
						alert("任务额度只能是小数或整数");
						return false;
					}
				}
			/*
				if(beginDate.length==0){
					alert("请填写起始日期");
					return false;
				}
				
				if(endDate.length==0){
					alert("请填写结束日期");
					return fales;
				}
			*/
				if(memo.length==0){
					alert("请填写政策内容");
					document.forms[0].memo.focus();
					return false;
				}
				return true;
			}
			
			//检查是否整数
			function isMoney( s ){ 
				var regu = "^[0-9]+[\.][0-9]{0,}$"; 
				var re = new RegExp(regu); 
				if (re.test(s)) { 
					return true; 
				} else { 
					return false; 
				} 
			} 
			
			//检查是否整数
			function isInteger( str ){ 
				var regu = /^[0-9]{1,}$/; 
				return regu.test(str); 
			} 
			
			function isFlightCode(str){									//检查是否符合航班号格式
				if(isStartWith(str)){
					alert("航班号不能以逗号开头或结尾");
					return false;
				}
				var re1 = /[,，]/;										//英文逗号或中文逗号
				var arr = str.split(re1);
				var re2 = /^[A-Z,a-z]{2}[0-9]{3,5}$/;					//格式：开头2个字母加3至5个数字结束如CA1234
				for(i=0;i<arr.length;i++){
					if(!re2.test([arr[i]])){
						alert("航班号"+arr[i]+"格式不正确");
						return false;
					}
				}
				return true;
			}
			
			function isStartEnd(str){									//检查是否符合航段格式
				if(isStartWith(str)){
					alert("航段不能以逗号开头或结尾");
					return false;
				}
				var re1 = /[,，]/;										//英文逗号或中文逗号
				var arr = str.split(re1);
				var re2 = /^([A-Za-z]{3}|[*])[-]([A-Za-z]{3}|[*])$/;	//格式：3个字母-3个字母或任意(一边或两边)3个字母换成*
				for(i=0;i<arr.length;i++){
					if(!re2.test(arr[i])){
						alert("航段"+arr[i]+"格式不正确");
						return false;
					}
				}
				return true;
			}
			
			function isFlightClass(str){								//检查是否符合舱位格式
				if(isStartWith(str)){
					alert("舱位不能以逗号开头或结尾");
					return false;
				}
				var re1 = /[,，]/;
				var arr = str.split(re1);
				var re2 = /^[A-Za-z]{1}$/;								//格式：单个字母
				for(i=0;i<arr.length;i++){
					if(!re2.test(arr[i])){
						alert("舱位"+arr[i]+"格式不正确");
						return false;
					}
				}
				return true;
			}
			
			function isStartWith(str){									//是否以逗号开头或结尾
				if(str.substring(0,1)=="," || str.substring(0,1)=="，" 
					|| str.substring(str.length-1,str.length)=="," || str.substring(str.length-1,str.length)=="，"){
					return true;
				}
				return false;
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
											不适用航段
										</td>
										<td style="text-align: left">
											<html:textarea property="startEndExcept" name="policyAfter" cols="48" rows="3"/>
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
											起始日期
										</td>
										<td style="text-align: left">
											<html:text property="beginDate" name="policyAfter" styleClass="colorblue2 p_5"  readonly="true"
											onfocus="WdatePicker({startDate:'%y-%M-%d 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"/>
										</td>
									</tr>
									<tr>
										<td class="lef">
											结束日期
										</td>
										<td style="text-align: left">
											<html:text property="endDate" name="policyAfter" styleClass="colorblue2 p_5"  readonly="true"
											onfocus="WdatePicker({startDate:'%y-%M-%d 23:59:59',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"/>
										</td>
									</tr>
									<tr>
										<td class="lef">
											机票数
										</td>
										<td style="text-align: left">
											<html:text property="ticketNum" name="policyAfter"
												styleClass="colorblue2 p_5" />张
										</td>
									</tr>
									<tr>
										<td class="lef">
											政策内容
										</td>
										<td style="text-align:left">
											<html:textarea property="memo" name="policyAfter" cols="48" rows="3"/>
											<font color="red">*</font>
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
