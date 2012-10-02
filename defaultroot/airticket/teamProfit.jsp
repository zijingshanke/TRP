<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>

<jsp:include page="../airticket/totalProfit.jsp" />


<div id="dialogOk" title="确认支付" >
 	<form id="form2" action="../airticket/airticketOrder.do?thisAction=teamAirticketOrderupdateAccount" name="airticketOrder" method="post">
        <table width="70%">
            <tr>
                <td>
                <input type="hidden" id="airticketOrderOkId"  name="airticketOrderId"/>
                <input type="hidden" id="platComAccountId" name="platComAccountId"/>
                    购票客户：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <select  style="width: 150px;" id="selTeamCmp" name="selTeamCmp"  disabled="disabled" class="text ui-widget-content ui-corner-all">
						<c:forEach items="${agentList}" var="a">
							<option value="<c:out value="${a.id}"/>" ><c:out value="${a.name}"/></option>
						</c:forEach>
						
					</select>
                </td>
                <td>
                   实收：<input type="text"  readonly="readonly"  value="0"style="width: 100px;" id="txtSAmount10" name="txtSAmount10" class="text ui-widget-content ui-corner-all">
                </td>
            </tr>
            <tr>
                <td>
                    航空公司：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text"  disabled="disabled" style="width: 150px;" id="txtCarrier" name="txtCarrier" class="text ui-widget-content ui-corner-all">
                </td>
                <td>
                   实付：<input type="text"  readonly="readonly"  style="width: 100px;" id="txtTAmount10" name="txtTAmount10" class="text ui-widget-content ui-corner-all">
                </td>
            </tr>
        </table>
        <table width="100%">
           <tr>
                <td>
                    订单金额：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                </td>
                <td>
                    <div class="dataList" id="divOrderItem">
                                <table style="margin-left: 5px;" class="dataList">
                                    <tbody><tr>
                                        <th>
                                            订单号
                                        </th>
                                        <th>
                                            订单金额
                                        </th>
                                        <th>
                                            支付账号
                                        </th>
                                        <th>
                                            备注
                                        </th>
                                    </tr>
                            
                                <tr>
                                    <td>
                                        <input type="text" readonly="readonly" style="width: 180px;" id="txtOrderNo" name="txtOrderNo" class="text ui-widget-content ui-corner-all">
                                    </td>
                                    <td>
                                        <input type="text"  readonly="readonly" style="width: 80px;" id="txtOrderAmount" name="txtOrderAmount" class="text ui-widget-content ui-corner-all">
                                    </td>
                                    <td>
                                        <select style="width: 150px;" id="selAccount" name="selAccount" class="text ui-widget-content ui-corner-all">
											<c:forEach items="${platComAccountList}" var="p">
												<option value="<c:out value="${p.account.id}"/>" ><c:out value="${p.account.name}"/></option>
											</c:forEach>
										</select>
                                    </td>
                                    <td>
                                        <input type="text" style="width: 200px;" id="txtOrderRemark" name="txtOrderRemark" class="text ui-widget-content ui-corner-all">
                                    </td>
                                </tr>
                            </tbody></table>
                    </div>
                </td>
            </tr>
        </table>
        <table>
            <tr>
                <td>确认支付时间：
                	<input type="text"  readonly="readonly"  style="width: 180px;" value="<c:out value="${newTime }"/>"  id="txtConfirmTime" name="txtConfirmTime" onfocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" class="text ui-widget-content ui-corner-all">
                </td>
            </tr>
            <tr>
                <td>
                    确认支付人&nbsp;&nbsp;&nbsp;&nbsp;：<input type="text"  readonly="readonly" style="width: 150px;" id="txtConfirmUser" name="txtConfirmUser" class="text ui-widget-content ui-corner-all">
                </td>
            </tr>
            <tr>
                <td align="center">
                    &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
                    &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="button" id="btnAdd" value="提 交" onclick="readonlyOK()" name="btnAdd" class="text ui-widget-content ui-corner-all">
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="reset" id="btnReset" value="重 置" class="text ui-widget-content ui-corner-all">
                    &nbsp;&nbsp;&nbsp;&nbsp;
                </td>
            </tr>
        </table>
	</form>
</div>

<div id="dialogFo" title="确认付款" >
 	<form id="formFo" action="../airticket/airticketOrder.do?thisAction=teamRefundAirticketOrder" name="airticketOrder" method="post">
         <table width="100%">
            <tr>
                <td>
                	 <input type="hidden" id="airticketOrderFoId"  name="airticketOrderFoId"/>
                     付退票手续费：&nbsp;<input type="text" style="width: 100px;" id="txtRefundIncomeretreatChargeFo" name="txtRefundIncomeretreatChargeFo" class="text ui-widget-content ui-corner-all">
                </td>
            </tr>
            <tr>
            	<td>
            	支付账号:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                      <select style="width: 150px;" id="selAccount" name="selAccount" class="text ui-widget-content ui-corner-all">
							<c:forEach items="${platComAccountList}" var="p">
								<option value="<c:out value="${p.account.id}"/>" ><c:out value="${p.account.name}"/></option>
							</c:forEach>
					</select>
               </td>
            </tr>
       </table>
        <table width="100%">
            <tr>
                <td>确认时间：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text"  readonly="readonly"  style="width: 180px;" value="<c:out value="${newTime }"/>" id="txtConfirmTimeFo" name="txtConfirmTimeFo" onfocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" class="text ui-widget-content ui-corner-all">
                </td>
            </tr>
            <tr>
                <td>
                    操作人：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text"  disabled="disabled" style="width: 100px;" id="txtCurrentOperatorFo" name="txtCurrentOperatorFo" class="text ui-widget-content ui-corner-all">
                </td>
            </tr>
            <tr>
            	<td>
                  	 备注：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" style="width: 180px;" id="txtOrderRemarkFo" name="txtOrderRemarkFo" class="text ui-widget-content ui-corner-all">
                </td>
            </tr>
            <tr>
                <td align="center">
                    &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
                    &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="button" id="btnAddFo" value="提 交" onclick="readonlyOKFO()" name="btnAdd" class="text ui-widget-content ui-corner-all">
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="reset" id="btnReset" value="重 置" class="text ui-widget-content ui-corner-all">
                    &nbsp;&nbsp;&nbsp;&nbsp;
                </td>
            </tr>
        </table>
	</form>
</div>

<div id="dialogTo" title="确认收款" >
 	<form id="formTo" action="../airticket/airticketOrder.do?thisAction=teamRefundAirticketOrderTo" name="airticketOrder" method="post">
         <table width="100%">
            <tr>
                <td>
                	 <input type="hidden" id="airticketOrderToId"  name="airticketOrderToId"/>
                     收退票手续费：&nbsp;<input type="text" style="width: 100px;" id="txtRefundIncomeretreatChargeTo" name="txtRefundIncomeretreatChargeTo" class="text ui-widget-content ui-corner-all">
                </td>
            </tr>
            <tr>
            	<td>
            	支付账号:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                      <select style="width: 150px;" id="selAccount" name="selAccount" class="text ui-widget-content ui-corner-all">
							<c:forEach items="${platComAccountList}" var="p">
								<option value="<c:out value="${p.account.id}"/>" ><c:out value="${p.account.name}"/></option>
							</c:forEach>
					</select>
               </td>
            </tr>
        </table>
        <table width="100%">
            <tr>
                <td>确认时间：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text"  readonly="readonly"  style="width: 180px;" value="<c:out value="${newTime }"/>"  id="txtConfirmTimeTo" name="txtConfirmTimeTo" onfocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" class="text ui-widget-content ui-corner-all">
                </td>
            </tr>
            <tr>
                <td>
                    操作人：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" disabled="disabled"  style="width: 100px;" id="txtCurrentOperatorTo" name="txtCurrentOperatorTo" class="text ui-widget-content ui-corner-all">
                </td>
            </tr>
            <tr>
            	<td>
                  	 备注：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" style="width: 180px;" id="txtOrderRemarkTo" name="txtOrderRemarkTo" class="text ui-widget-content ui-corner-all">
                </td>
            </tr>
            <tr>
                <td align="center">
                    &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
                    &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="button" id="btnAddTo" value="提 交" onclick="readonlyOKTo()" name="btnAdd2" class="text ui-widget-content ui-corner-all">
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="reset" id="btnResetTo" value="重 置" class="text ui-widget-content ui-corner-all">
                    &nbsp;&nbsp;&nbsp;&nbsp;
                </td>
            </tr>
       </table>
	</form>
</div>

