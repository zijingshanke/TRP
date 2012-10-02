<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="utf-8"%>
<tr>
	<td>
		承运人
		<html:select property="cyr" styleClass="colorblue2 p_5"
			style="width:120px;">
			<html:option value="">--请选择--</html:option>
			<html:option value="3U">3U-四川航空</html:option>
			<html:option value="8C">8C-东星航空</html:option>
			<html:option value="8L">8L-翔鹏航空</html:option>
			<html:option value="9C">9C-春秋航空</html:option>
			<html:option value="BK">BK-奥凯航空</html:option>
			<html:option value="CA">CA-国际航空</html:option>
			<html:option value="CN">CN-大新华航空</html:option>
			<html:option value="CZ">CZ-南方航空</html:option>
			<html:option value="EU">EU-鹰联航空</html:option>
			<html:option value="FM">FM-上海航空</html:option>
			<html:option value="G5">GS-华夏航空</html:option>
			<html:option value="GS">GS-大新华快运航空</html:option>
			<html:option value="HO">HO-吉祥航空</html:option>
			<html:option value="HU">HU-海南航空</html:option>
			<html:option value="JD">JD-金鹿航空</html:option>
			<html:option value="JR">JR-幸福航空</html:option>
			<html:option value="KY">KY-昆明航空</html:option>
			<html:option value="KN">KN-联合航空</html:option>
			<html:option value="MF">MF-厦门航空</html:option>
			<html:option value="MU">MU_东方航空</html:option>
			<html:option value="NS">NS-东北航空</html:option>
			<html:option value="OQ">OQ-重庆航空</html:option>
			<html:option value="PN">PN-西部航空</html:option>
			<html:option value="SC">SC-山东航空</html:option>
			<html:option value="VD">VD-鲲鹏航空</html:option>
			<html:option value="ZH">ZH-深圳航空</html:option>
		</html:select>
	</td>
	<td>
		出票PNR
		<html:text property="drawPnr" styleClass="colorblue2 p_5"
			style="width:120px;" />
	</td>
	<td>
		预定PNR
		<html:text property="subPnr" styleClass="colorblue2 p_5"
			style="width:120px;" />
	</td>
	<td>
		大PNR
		<html:text property="bigPnr" styleClass="colorblue2 p_5"
			style="width:120px;" />
	</td>
	<td>
		订单号
		<html:text property="airOrderNo" styleClass="colorblue2 p_5"
			style="width:150px;" />
	</td>
	<td>
		操作人
		<html:text property="sysName" styleClass="colorblue2 p_5"
			style="width:100px;" />
	</td>
</tr>
<tr>
	<td>
		航班号
		<html:text property="flightCode" styleClass="colorblue2 p_5"
			style="width:120px;" />
	</td>
	<td>
		乘客
		<html:text property="agentName" styleClass="colorblue2 p_5"
			style="width:150px;" />
	</td>
	<td>
		票号
		<html:text property="ticketNumber" styleClass="colorblue2 p_5"
			style="width:150px;" />
	</td>
	<td>
		开始:
		<html:text property="startDate" styleClass="colorblue2 p_5"
			style="width:150px;" onclick="popUpCalendar(this, this);"
			readonly="true" />
	</td>
	<td>
		结束
		<html:text property="endDate" styleClass="colorblue2 p_5"
			style="width:150px;" onclick="popUpCalendar(this, this);"
			readonly="true" />
	</td>
	<td>
		最近
		<html:select property="userNo" styleClass="colorblue2 p_5"
			style="width:120px;" />
	</td>
</tr>
<tr>
	<td>
		买入
		<html:select property="userNo" styleClass="colorblue2 p_5"
			style="width:150px;" />
	</td>
	<td>
		付款
		<html:select property="userNo" styleClass="colorblue2 p_5"
			style="width:150px;" />
	</td>
	<td>
		卖出
		<html:select property="userNo" styleClass="colorblue2 p_5"
			style="width:150px;" />
	</td>
	<td>
		收款
		<html:select property="userNo" styleClass="colorblue2 p_5"
			style="width:150px;" />
	</td>
	<td>
		状态
		<html:select property="airticketOrder_status"
			styleClass="colorblue2 p_5" style="width:150px;">
			<html:option value="0">请选择</html:option>
			<html:option value="1">新订单</html:option>
			<html:option value="2">申请成功，等待支付</html:option>
			<html:option value="3">支付成功，等待出票</html:option>
			<html:option value="4">取消出票，等待退款</html:option>
			<html:option value="10">B2C订单，等待收款</html:option>
			<html:option value="20">退票订单，等待审核</html:option>
			<html:option value="21">退票审核通过，等待退款</html:option>
			<html:option value="30">废票订单，等待审核</html:option>
			<html:option value="31">废票审核通过，等待退款</html:option>
			<html:option value="41">改签订单，等待审核</html:option>
			<html:option value="42">改签审核通过，等待支付</html:option>
			<html:option value="43">改签已支付，等待确认</html:option>
			<html:option value="80">交易结束</html:option>
		</html:select>
	</td>
	<td>
		<input type="submit" name="button" id="button" value="提交"
			class="submit greenBtn" />
	</td>
</tr>
