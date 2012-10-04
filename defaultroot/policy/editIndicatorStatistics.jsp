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
				    document.forms[0].submit();
				}
			}
			
			function check(){
				var flightCode = document.forms[0].flightCode.value.Trim();					//航班号
				var flightCodeExcept = document.forms[0].flightCodeExcept.value.Trim();		//不适航班号
				var flightPoint = document.forms[0].flightPoint.value.Trim();				//航段
				var flightPointExcept = document.forms[0].flightPointExcept.value.Trim();	//不适航段
				var flightClass = document.forms[0].flightClass.value.Trim();				//舱位
				var flightClassExcept = document.forms[0].flightClassExcept.value.Trim();	//不适舱位
				var remark = document.forms[0].remark.value.Trim();							//备注
				
				if(flightCode.length != 0){								//航班号
					if(!isFlightCode(flightCode)){
						alert("航班号格式错误");
						return false;
					}
				}
				if(flightCodeExcept.length != 0){						//不适航班号
					if(!isFlightCode(flightCodeExcept)){
						alert("不适航班格式错误");
						return false;
					}
				}
				if(flightPoint.length != 0){								//航段
					if(!isFlightPoint(flightPoint)){
						alert("航段格式错误");
						return false;
					}
				}
				if(flightPointExcept.length != 0){							//不适航段
					if(!isFlightPoint(flightPointExcept)){
					alert("不适航段格式错误");
						return false;
					}
				}
				if(flightClass.length != 0){							//舱位
					if(!isFlightClass(flightClass)){
					alert("舱位格式错误");
						return false;
					}
				}
				if(flightClassExcept.length != 0){						//不适舱位
					if(!isFlightClass(flightClassExcept)){
						alert("不适舱位格式错误");
						return false;
					}
				}
				
				if(remark.length==0){
					alert("请填写政策内容");
					document.forms[0].remark.focus();
					return false;
				}
				return true;
			}
			
			//检查是否小数
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
					return false;
				}
				var re1 = /[,，]/;										//英文逗号或中文逗号
				var arr = str.split(re1);
				var re2 = /^[A-Z,a-z]{2}[0-9]{3,5}$/;					//格式：开头2个字母加3至5个数字结束如CA1234
				for(i=0;i<arr.length;i++){
					if(!re2.test([arr[i]])){
						return false;
					}
				}
				return true;
			}
			
			function isFlightPoint(str){									//检查是否符合航段格式
				if(isStartWith(str)){
					return false;
				}
				var re1 = /[,，]/;										//英文逗号或中文逗号
				var arr = str.split(re1);
				var re2 = /^([A-Za-z]{3}|[*])[-]([A-Za-z]{3}|[*])$/;	//格式：3个字母-3个字母或任意(一边或两边)3个字母换成*
				for(i=0;i<arr.length;i++){
					if(!re2.test(arr[i])){
						return false;
					}
				}
				return true;
			}
			
			function isFlightClass(str){								//检查是否符合舱位格式
				if(isStartWith(str)){
					return false;
				}
				var re1 = /[,，]/;
				var arr = str.split(re1);
				var re2 = /^[A-Za-z]{1}$/;								//格式：单个字母
				for(i=0;i<arr.length;i++){
					if(!re2.test(arr[i])){
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
				<html:form action="/policy/indicatorStatistics.do">
					<html:hidden property="thisAction" name="indicatorStatistics"/>
					<html:hidden property="id" name="indicatorStatistics" />
					<html:hidden property="airlinePolicyAfterId" name="policyAfter" value="${indicatorStatistics.airlinePolicyAfter.id}"/> 
					
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
									<c:param name="title1" value="指标计算政策管理" />
									<c:param name="title2" value="编辑信息" />																			
								</c:import>							
								
								<hr>
								<table width="100%" cellpadding="0" cellspacing="0" border="0"
									class="dataList">
									<tr>
										<td class="lef">
											后返政策主标题
										</td>
										<td style="text-align:left">
											<c:out value="${indicatorStatistics.airlinePolicyAfter.name}"></c:out>
										</td>
									</tr>
									<tr>
										<td class="lef">
											承运人
										</td>
										<td style="text-align: left">
											<c:out value="${indicatorStatistics.airlinePolicyAfter.carrier}"></c:out>
										</td>
									</tr>
									<tr>
										<td class="lef">
											航班号
										</td>
										<td style="text-align: left">
											<html:text property="flightCode" name="indicatorStatistics" size="60"
												styleClass="colorblue2 p_5" />
										</td>
									</tr>
									
									<tr>
										<td class="lef">
											不适用航班
										</td>
										<td style="text-align: left">
											<html:text property="flightCodeExcept" name="indicatorStatistics" size="60"
												styleClass="colorblue2 p_5" />
										</td>
									</tr>
									<tr>
										<td class="lef">
											航段
										</td>
										<td style="text-align: left">
											<html:textarea property="flightPoint" name="indicatorStatistics" cols="48" rows="3"/>
										</td>
									</tr>
												<tr>
										<td class="lef">
											不适用航段
										</td>
										<td style="text-align: left">
											<html:textarea property="flightPointExcept" name="indicatorStatistics" cols="48" rows="3"/>
										</td>
									</tr>
									<tr>
										<td class="lef">
											舱位
										</td>
										<td style="text-align: left">
											<html:textarea property="flightClass" name="indicatorStatistics" cols="48" rows="3"/>
												
										</td>
									</tr>
									<tr>
										<td class="lef">
											不适用舱位
										</td>
										<td style="text-align: left">
											<html:textarea property="flightClassExcept" name="indicatorStatistics" cols="48" rows="3"/>
										</td>
									</tr>
									<tr>
										<td class="lef">
											起始日期
										</td>
										<td style="text-align: left">
											<html:text property="beginDate" name="indicatorStatistics" styleClass="colorblue2 p_5"  readonly="true"
											onfocus="WdatePicker({startDate:'%y-%M-%d 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"/>
										</td>
									</tr>
									<tr>
										<td class="lef">
											结束日期
										</td>
										<td style="text-align: left">
											<html:text property="endDate" name="indicatorStatistics" styleClass="colorblue2 p_5"  readonly="true"
											onfocus="WdatePicker({startDate:'%y-%M-%d 23:59:59',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"/>
										</td>
									</tr>
									<tr>
										<td class="lef">
											是否计量额
										</td>
										<td style="text-align: left">
											<html:radio property="isAmount" value="1" name="indicatorStatistics">是</html:radio>
											<html:radio property="isAmount" value="0" name="indicatorStatistics">否</html:radio>
										</td>
									</tr>
									<tr>
										<td class="lef">
											是否计奖额
										</td>
										<td style="text-align: left">
											<html:radio property="isAward" value="1" name="indicatorStatistics">是</html:radio>
											<html:radio property="isAward" value="0" name="indicatorStatistics">否</html:radio>
										</td>
									</tr>
									<tr>
										<td class="lef">
											是否高舱
										</td>
										<td style="text-align: left">
											<html:radio property="isHighClass" value="1" name="indicatorStatistics">是</html:radio>
											<html:radio property="isHighClass" value="0" name="indicatorStatistics">否</html:radio>
										</td>
									</tr>
									<tr>
										<td class="lef">
											行程类型
										</td>
										<td style="text-align: left">
											<html:radio property="travelType" value="1" name="indicatorStatistics" disabled="true">单去程</html:radio>
											<html:radio property="travelType" value="2" name="indicatorStatistics" disabled="true">单返程</html:radio>
											<html:radio property="travelType" value="3" name="indicatorStatistics" disabled="true">来回程</html:radio>
											<html:radio property="travelType" value="4" name="indicatorStatistics" disabled="true">缺口程</html:radio>
											<html:radio property="travelType" value="5" name="indicatorStatistics" disabled="true">中转程</html:radio>
											&nbsp; &nbsp; &nbsp; &nbsp; 注：当前黙所有行程类型为单程
										</td>
									</tr>
									<tr>
										<td class="lef">
											机票类型
										</td>
										<td style="text-align: left">
											<html:radio property="ticketType" value="1" name="indicatorStatistics" disabled="true">普通</html:radio>
											<html:radio property="ticketType" value="2" name="indicatorStatistics" disabled="true">包机</html:radio>
											<html:radio property="ticketType" value="3" name="indicatorStatistics" disabled="true">包收入</html:radio>
											&nbsp; &nbsp; &nbsp; &nbsp; 注：当前黙认所有机票类型为普通类型
										</td>
									</tr>
									<tr>
										<td class="lef">
											状态
										</td>
										<td style="text-align: left">
											<html:radio property="status" value="1" name="indicatorStatistics">启用</html:radio>
											<html:radio property="status" value="2" name="indicatorStatistics">停用</html:radio>
										</td>
									</tr>
									<tr>
										<td class="lef">
											政策内容
										</td>
										<td style="text-align:left">
											<html:textarea property="remark" name="indicatorStatistics" cols="48" rows="3"/>
											<font color="red">*</font>
										</td>
									</tr>
								</table>
								<table width="100%" style="margin-top: 5px;">
									<tr>
										<td>
											<input name="label" type="button" class="button1" value="提 交"
												onclick="edit();">
											<input name="label" type="button" class="button1" value="重 置"
												onclick="document.indicatorStatistics.reset();">
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
