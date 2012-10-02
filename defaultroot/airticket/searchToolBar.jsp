<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>

<div id="searchBarObj" class="searchBar">
  <table cellpadding="0" cellspacing="0" border="0" class="searchPanel" style=" width:99%; table-layout:fixed;">
    <tr>
      <td> 承运人
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
        </html:select></td>
      <td> 航班号
        <html:text property="flightCode"  styleClass="colorblue2 p_5"
					style="width:60px;" /></td>
      <td> PNR
        <html:text property="pnr" ondblclick="this.value=''" styleClass="colorblue2 p_5"
					style="width:50px;" /></td>
      <td> 订单号
        <html:text property="orderNo" styleClass="colorblue2 p_5"
					style="width:120px;" /></td>
      <td> 操作人
        <html:text property="sysName"  ondblclick="JavaScript:this.value=''" styleClass="colorblue2 p_5"
					style="width:60px;" /></td>
      <td>
        最近
        <html:text property="recentlyDay" styleId="recentlyDayId" ondblclick="this.value=''" title="0,空格表示所有天"
					style="width:30px" maxlength="3" />
        天 </td>
    </tr>
    <tr>
      <td> 乘客
        <html:text property="agentName" styleClass="colorblue2 p_5"
					style="width:100px;" /></td>
      <td> 票号
        <html:text property="ticketNumber" styleClass="colorblue2 p_5"
					style="width:120px;" /></td>
      <td> 开始:
        <html:text property="startDate" styleClass="colorblue2 p_5"
					style="width:120px;" onfocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
					readonly="true" /></td>
      <td> 结束
        <html:text property="endDate" styleClass="colorblue2 p_5"
					style="width:120px;" onfocus="WdatePicker({startDate:'%y-%M-01 23:59:59',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
					readonly="true" /></td>
      <td> 排序
        <html:select property="orderBy" styleClass="colorblue2 p_5"
					style="width:80px;">
          <html:option value="0">操作时间</html:option>
          <html:option value="1">创建时间</html:option>
        </html:select></td>
      <td> 平台
        <html:select property="drawType" styleClass="colorblue2 p_5"
					style="width:80px;">
          <html:option value="99">---请选择---</html:option>
          <html:option value="0">平台</html:option>
          <html:option value="1">网电</html:option>
        </html:select></td>
    </tr>
    <tr>
      <td> 买入
        <html:select property="fromPlatformId" styleClass="colorblue2 p_5"
					style="width:120px;">
          <html:option value="">---请选择---</html:option>
          <c:forEach items="${formPlatFormList}" var="foplat">
            <html:option value="${foplat.id}">
              <c:out value="${foplat.name }" />
            </html:option>
          </c:forEach>
        </html:select></td>
      <td> 付款
        <html:select property="fromAccountId" styleClass="colorblue2 p_5"
					style="width:120px;">
          <html:option value="">---请选择---</html:option>
          <c:forEach items="${formAccountList}" var="flac">
            <html:option value="${flac.id }">
              <c:out value="${flac.name }" />
            </html:option>
          </c:forEach>
        </html:select></td>
      <td> 卖出
        <html:select property="toPlatformId" styleClass="colorblue2 p_5"
					style="width:120px;">
          <html:option value="">---请选择---</html:option>
          <c:forEach items="${toPlatFormList}" var="toplat">
            <html:option value="${toplat.id }">
              <c:out value="${toplat.name }" />
            </html:option>
          </c:forEach>
        </html:select></td>
      <td> 收款
        <html:select property="toAccountId" styleClass="colorblue2 p_5"
					style="width:120px;">
          <html:option value="">---请选择---</html:option>
          <c:forEach items="${toAccountList}" var="toac">
            <html:option value="${toac.id }">
              <c:out value="${toac.name }" />
            </html:option>
          </c:forEach>
        </html:select></td>
      <td style="display: none;"> 状态
        <html:select property="airticketOrder_status"
					styleClass="colorblue2 p_5" style="width:120px;">
          <html:option value="0">请选择</html:option>
          <html:option value="1">新订单</html:option>
          <html:option value="2">申请成功，等待支付</html:option>
          <html:option value="3">支付成功，等待出票</html:option>
          <html:option value="4">取消出票，等待退款</html:option>
          <html:option value="5">出票成功，交易结束</html:option>
          <html:option value="6">取消出票,已经退款</html:option>
          <html:option value="19">退票订单，等待审核</html:option>
          <html:option value="21">退票审核通过，等待退款</html:option>
          <html:option value="21">退票,已经退款</html:option>
          <html:option value="29">废票订单，等待审核</html:option>
          <html:option value="31">废票审核通过，等待退款</html:option>
          <html:option value="41">改签订单，等待审核</html:option>
          <html:option value="42">改签审核通过，等待支付</html:option>
          <html:option value="43">改签已支付，等待确认</html:option>
          <html:option value="80">交易结束</html:option>
        </html:select></td>
      <td> 订单类型
        <html:select property="orderType" styleClass="colorblue2 p_5"
					style="width:120px;">
          <html:option value="">---请选择---</html:option>
          <html:option value="91">正常订单</html:option>
          <html:option value="93">退废订单</html:option>
          <html:option value="92">改签订单</html:option>
        </html:select></td>
      <td><input type="submit" name="button" id="button" value="提交"
					class="submit greenBtn" />
        <a href="#" onclick="shrinkSearchBar()">收起搜索栏</a></td>
    </tr>
  </table>
</div>
<div id="showSearcheBarObj"  style="display: none;float: right"> <a href="#" onclick="shrinkSearchBar()">显示搜索栏</a> </div>
<script type="text/javascript">
     function shrinkSearchBar(){
      	var searchBarObj=document.getElementById("searchBarObj");
      	var showSearcheBarObj=document.getElementById("showSearcheBarObj");
      	
      	if(searchBarObj.style.display==''){
      		searchBarObj.style.display='none';
      		showSearcheBarObj.style.display='';
      	}else{
      		searchBarObj.style.display='';
      		showSearcheBarObj.style.display='none';
      	}      	
      }
</script> 
