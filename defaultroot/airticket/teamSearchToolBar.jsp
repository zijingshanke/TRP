<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<div class="searchBar">
	<table cellpadding="0" cellspacing="0" border="0" class="searchPanel">
		<tr>
			<td>
				承运人/航班号<html:text property="cyr" styleClass="colorblue2 p_5"
					style="width:120px;" />
			</td>
			<td>
				航班号<html:text property="flightCode" styleClass="colorblue2 p_5"
					style="width:120px;" />
			</td>
			<td>
				订单号<html:text property="airOrderNo" styleClass="colorblue2 p_5"
					style="width:150px;" />
			</td>
			<td>
				操作人<html:text property="sysName" styleClass="colorblue2 p_5"
					style="width:100px;" />
			</td>
			<td>
			  排序 <html:select property="orderBy" styleClass="colorblue2 p_5"
					style="width:80px;">
					<html:option value="0">操作时间</html:option>
					<html:option value="1">创建时间</html:option>
				</html:select>
			</td>
			<td>
				类型<html:select property="orderType" styleClass="colorblue2 p_5"
					style="width:90px;">
					<html:option value="">请选择</html:option>
					<html:option value="97">正常订单</html:option>
					<html:option value="98">退票订单</html:option>					
				</html:select>
			</td>
		</tr>
		<tr>
			<td>
				客户<html:select property="agentNo" styleClass="colorblue2 p_5"
					style="width:200px;">
					<html:option value="">--请选择--</html:option>
					<c:forEach items="${agentList}" var="agent">
						<html:option value="${agent.id}">
							<c:out value="${agent.showName}" />
						</html:option>
					</c:forEach>
				</html:select>
			</td>
			<td>
				出票<html:select property="drawer" styleClass="colorblue2 p_5"
					style="width:80px;">
					<html:option value="">请选择</html:option>
					<html:option value="B2B网电">B2B网电</html:option>
					<html:option value="B2C散客">B2C散客</html:option>
					<html:option value="倒票组">倒票组</html:option>
				</html:select>
			</td>
			<td>
				开始<html:text property="startDate" styleClass="colorblue2 p_5"
					style="width:150px;" onfocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
					readonly="true" />
			</td>
			<td>
				结束<html:text property="endDate" styleClass="colorblue2 p_5"
					style="width:150px;" onfocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
					readonly="true" />
			</td>
			<td>
				<input type="checkbox" name="ifRecently" checked="checked"
					id="ifRecentlyObj" value="1" onclick="selectRecent()">
				最近<html:text property="recentlyDay" styleId="recentlyDayId" value="${param.recentlyDay}"
					style="width:25px" maxlength="3" />天
			</td>
			<td>
				<input type="submit" name="button" id="button" value="提交"
					class="submit greenBtn" />
			</td>
		</tr>
	</table>
	<hr />
</div>