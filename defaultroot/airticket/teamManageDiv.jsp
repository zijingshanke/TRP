<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>

<jsp:include page="../airticket/teamProfitStatistic.jsp" />

<div id="dialog10" title="确认支付" >
 	<form id="form2" action="../airticket/airticketOrder.do?thisAction=confirmTeamPayment" name="airticketOrder" method="post">
        <table align="left">
           		<tr>
            		<input type="hidden" id="id10"  name="id"/>
                	<td>购票客户:</td><td><span id="agentName" /> </td>                
                </tr>
                <tr>
                	<td>实际收款:</td><td><span id="sellAmount" /> </td>
                </tr>
                <tr>
                	<td>航空公司:</td><td><span id="carrier" /></td>                
                </tr>                                  
             <tr>
                 <td>订单号:</td><td><span id="airOrderNo10" /></td>
             </tr>
             <tr>
                  <td>订单金额:</td><td><span id="orderAmount"/></td>
             </tr>
             <tr>
                  <td>支付账号:</td><td>
                  <select style="width: 150px;" id="selAccount" name="accountId" class="text ui-widget-content ui-corner-all">
					<c:forEach items="${platComAccountList}" var="p">
						<option value="<c:out value="${p.account.id}"/>" ><c:out value="${p.account.name}"/></option>
					</c:forEach>
					</select>
                   </td>
           	<tr>
           	<tr>
                <td>实际支付:</td><td><input type="text" id="totalAmount10" name="totalAmount" value="0"   style="width: 150px;" readonly="readonly"   class="text ui-widget-content ui-corner-all"></td>
            </tr> 
            <tr>
            	<td>备注:</td><td><input type="text"  name="memo" style="width: 150px;"  class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td>支付时间:</td><td><input type="text"  readonly="readonly"  style="width: 150px;" value="<c:out value="${newTime }"/>"  id="txtConfirmTime" name="txtConfirmTime" onfocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td>支付人:</td><td><span id="userName" /></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input value="提交" type="submit" >
                </td>
            </tr>
        </table>
	</form>
</div>

<div id="dialog11" title="确认付退款" >
 	<form id="form11" action="../airticket/airticketOrder.do?thisAction=confirmTeamRefundPayment" name="airticketOrder" method="post">
         <table>
            <tr>
            	<input type="hidden" id="id11"  name="id"/>
                <td> 付退票手续费：</td>
                <td><input type="text"  id="incomeretreatCharge11" name="incomeretreatCharge" style="width: 150px;" class="text ui-widget-content ui-corner-all">
                </td>
            </tr>
            <tr>
            	<td>支付账号:</td>
            	<td><select style="width: 150px;" id="account11" name="accountId" class="text ui-widget-content ui-corner-all">
							<c:forEach items="${platComAccountList}" var="p">
								<option value="<c:out value="${p.account.id}"/>" ><c:out value="${p.account.name}"/></option>
							</c:forEach>
					</select>
               </td>
            </tr>
            <tr>
                <td>确认时间：</td><td><input type="text"  id="optTime11" name="optTime" style="width: 150px;" value="<c:out value="${newTime }"/>"  onfocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" class="text ui-widget-content ui-corner-all">
                </td>
            </tr>
            <tr>
                <td>操作人：</td><td><span id="userName11"/>
                </td>
            </tr>
            <tr>
            	<td>备注:</td><td><input type="text"  id="memo11" name="memo" style="width: 150px;" class="text ui-widget-content ui-corner-all">
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="提 交" >
                </td>
            </tr>
        </table>
	</form>
</div>

<div id="dialog12" title="确认收退款" >
 	<form id="form12" action="../airticket/airticketOrder.do?thisAction=confirmTeamRefundCollection" name="airticketOrder" method="post">
         <table>
            <tr>
             	<input type="hidden" id="id12"  name="id"/>
                <td>收退票手续费：</td>
                <td><input type="text" style="width: 150px;" id="incomeretreatCharge12" name="incomeretreatCharge" class="text ui-widget-content ui-corner-all">
                </td>
            </tr>
            <tr>
            	<td>收款账号:</td>
            	<td>
                      <select style="width: 150px;" id="accountId12" name="accountId" class="text ui-widget-content ui-corner-all">
							<c:forEach items="${platComAccountList}" var="p">
								<option value="<c:out value="${p.account.id}"/>" ><c:out value="${p.account.name}"/></option>
							</c:forEach>
					</select>
               </td>
            </tr>
            <tr>
                <td>确认时间：</td><td><input type="text"   id="optTime12" name="optTime"  style="width: 150px;" value="<c:out value="${newTime }"/>"  onfocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" class="text ui-widget-content ui-corner-all">
                </td>
            </tr>
            <tr>
                <td>操作人：</td><td><span id="userName12" />
                </td>
            </tr>
            <tr>
            	<td>备注：</td><td><input type="text" id="memo12" name="memo" style="width: 150px;"  class="text ui-widget-content ui-corner-all">
                </td>
            </tr>
            <tr>
                <td colspan="2">
                     <input type="submit" value="提 交" >
                </td>
            </tr>
       </table>
	</form>
</div>


<div id="dialog21" title="创建团队退票--选择航程、乘机人" >
<form id="form21" action="../airticket/airticketOrderTeam.do?thisAction=createTeamRefundBySale" name="airticketOrder" method="post">
        <fieldset>
	    <table id="tempFlightTable">
	    <input type="hidden" name="flightIds" id="flightIds21" />
	    <input type="hidden" name="groupId" id="groupId21"/>
	    
	    <tbody>
		</tbody>
		</table>
		 <table id="temppassengerTable">
	    <tbody>
		</tbody>
		</table>
		<input value="提交" type="button"  onclick="submitForm21();" >
	</fieldset>
</form>
</div>


