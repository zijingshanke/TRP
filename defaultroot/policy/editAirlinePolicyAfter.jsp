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
			String.prototype.Trim   =   function()   
			  {   
			  return   this.replace(/(^\s*)|(\s*$)/g,   "");   
			  }
			function addAirlinePolicyAfter(){
				if(check()){
					var quota = document.forms[0].quota;
					if(quota.value.Trim().length == 0){
						quota.value = 0;
					}
					document.forms[0].action="airlinePolicyAfter.do";
				    document.forms[0].submit();
				}
				
			}
			
			function check(){
				var name = document.forms[0].name.value.Trim();						//政策标题
				var carrier = document.forms[0].carrier.value.Trim();				//承运人
				var beginDate = document.forms[0].beginDate.value.Trim();			//开始时间
				var endDate = document.forms[0].endDate.value.Trim();				//结束时间
				var quota = document.forms[0].quota.value.Trim();					//任务额度
				var statu = document.forms[0].status;					//状态
				var isChecked = 0;
				
				if(name.length==0 || beginDate.length==0 || endDate.length==0){
					alert("请填写完整信息");
					return false;
				}
				if(carrier.length==0){
					alert("请选择承运人");
					return false;
				}
				if(quota.length != 0){
					if(!(isMoney(quota) || isInteger(quota))){
						alert("任务额度只能是金额或整数");
						return false;
					}
				}
				for(i=0;i<statu.length;i++){
					if(statu[i].checked){
						isChecked = 1;
						break;
					}
				}
				if(isChecked == 0){
					alert("请选择状态");
					return false;
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
			
			function backToListAirlinePoliceAfter(){
				document.location.href="airlinePolicyAfterList.do?thisAction=list";
			}
		</script>
	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
				<html:form action="/policy/airlinePolicyAfter.do">
					<html:hidden property="thisAction" name="airlinePolicyAfter" />
					<html:hidden property="id" name="airlinePolicyAfter" />
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
											政策标题
										</td>
										<td style="text-align: left">
											<html:text property="name" name="airlinePolicyAfter"
												styleClass="colorblue2 p_5" />
											<font color="red">*</font>
										</td>
									</tr>
									<tr>
										<td class="lef">
											承运人：
										</td>
										<td style="text-align: left">
										<html:select property="carrier" name="airlinePolicyAfter" style="width:155px;">
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
											<font color="red">*</font>
										</td>
									</tr>
									<tr>
										<td class="lef">
											开始时间
										</td>
										<td style="text-align: left">
											<html:text property="beginDate" name="airlinePolicyAfter" styleClass="colorblue2 p_5"  readonly="true"
											onfocus="WdatePicker({startDate:'%y-%M-%d 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"/>
											
											<font color="red">*</font>
										</td>
									</tr>
									<tr>
										<td class="lef">
											结束时间
										</td>
										<td style="text-align: left">
											<html:text property="endDate" name="airlinePolicyAfter" styleClass="colorblue2 p_5"  readonly="true"
											onfocus="WdatePicker({startDate:'%y-%M-%d 23:59:59',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"/>
											<font color="red">*</font>
										</td>
									</tr>
									<tr>
										<td class="lef">
											备注
										</td>
										<td style="text-align:left">
											<html:textarea property="memo" name="airlinePolicyAfter" cols="18" rows="3"/>
										</td>
									</tr>
									<tr>
										<td class="lef">
											任务额度
										</td>
										<td style="text-align:left">
											<html:text property="quota" name="airlinePolicyAfter"
												styleClass="colorblue2 p_5" />
										</td>
									</tr>
									<tr>
										<td class="lef">
											状态
										</td>
										<td style="text-align: left">
											<html:radio property="status" value="1" name="airlinePolicyAfter">启用</html:radio>
											<html:radio property="status" value="2" name="airlinePolicyAfter">停用</html:radio>
										</td>
									</tr>
								</table>
								<table width="100%" style="margin-top: 5px;">
									<tr>
										<td>
											<input name="label" type="button" class="button1" value="提 交"
												onclick="addAirlinePolicyAfter();">
											<input name="label" type="button" class="button1" value="重 置"
												onclick="document.airlinePolicyAfter.reset();">
											<input name="label" type="button" class="button1" value="取消"
												onclick="backToListAirlinePoliceAfter();">
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
