<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>

<div id="dialog" title="利润汇总统计" >
	
	<form id="form1" action="../airticket/airticketOrder.do?thisAction=saveTeamTradingOrder" name="airticketOrder" method="post">
	   <table id="list">
                <tbody><tr><td colspan="3">___________________________________________对客户_____________________________________________</td></tr>
                <tr>
                    <td>应付出团代理费（现返）：</td>
                    <td align="left">
                    	<input type="hidden" name="airticketOrderId" id="airticketOrderId" />
                        <input name="txtAgentFeeTeams" id="txtAgentFeeTeams" class="colorblue2 p_5" readonly="readonly" type="text" style="width: 70px;">
                        =(票面价&nbsp;&nbsp;&nbsp;<input name="txtTicketPriceTotals" id="txtTicketPriceTotals" class="colorblue2 p_5" readonly="readonly"  type="text" style="width: 70px;">
                        +<span style="color: Green;">多收票价<input name="txtAmountMore" id="txtAmountMore" class="colorblue2 p_5" onkeyup="amountMoreCheck();" value="0" style="width: 70px; color: Green;" type="text"></span>
                        )*<span style="color: Green;">返点&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="txtAgents" id="txtAgents" value="0" onkeyup="agentsCheck();" class="colorblue2 p_5" style="width: 70px; color: Green;" type="text"></span></td>
                </tr>
                <tr>
                    <td><span style="color: Green;">应付出团代理费（未返）：</span></td>
                    <td align="left">
                        <input name="txtUnAgentFeeTeams" id="txtUnAgentFeeTeams" value="0" onkeyup="unAgentFeeTeamsCheck();" class="colorblue2 p_5" style="width: 70px; color: Green;" type="text">
                        <span style="color: Green;">&nbsp;&nbsp;备注：&nbsp;&nbsp;&nbsp;&nbsp;</span><input name="txtRemark" id="txtRemark" class="colorblue2 p_5" style="width: 300px; color: Green;" class="TextUnderLine" type="text">
                    </td>
                </tr>
                <tr>
                    <td>应收票款：</td>
                    <td align="left">
                        <input name="txtSAmount" id="txtSAmount"  class="colorblue2" type="text" style="width: 70px;">
                        =(票面价&nbsp;&nbsp;&nbsp;<input name="txtTicketPriceTotal" id="txtTicketPriceTotal" style="width: 70px;" class="colorblue2 p_5" readonly="readonly" type="text">
                         +<span>多收票价<input name="txtAmountMores" id="txtAmountMores" style="width: 70px;" class="colorblue2 p_5" readonly="readonly" type="text"></span>
                        )-现返<input name="txtAgentFeeTeam" id="txtAgentFeeTeam" class="colorblue2 p_5" readonly="readonly" style="width: 70px;" type="text">
                        +<span style="color: Green;">多收税<input name="txtTaxMore" id="txtTaxMore" value="0" onkeyup="taxMoreCheck();" class="colorblue2 p_5" style="width: 70px;" type="text"></span>
                       +<span style="color: Green;">收退票手续费：</span><input name="txtSourceTGQFee" id="txtSourceTGQFee" value="0" onkeyup="sourceTGQFeeCheck();" class="colorblue2 p_5" style="color: Green; width: 70px;" type="text">
                  </td>
                </tr>
                    <tr>
                    <td>实收票款：</td>
                    <td align="left"> 
                    <input name="txtTotalAmount" id="txtTotalAmount" class="colorblue2 p_5" type="text" style="width: 70px;">
                    =应收票款<input name="txtSAmount2" id="txtSAmount2" class="colorblue2 p_5" readonly="readonly" type="text" style="width: 70px;">
                    +机建燃油税&nbsp;&nbsp;&nbsp;<input name="txtTax2" id="txtTax2" class="colorblue2 p_5" readonly="readonly" type="text" style="width: 70px;">
                    </td>                
                </tr>
                <tr><td colspan="3" style="width: 750px;"></td></tr>
                <tr><td colspan="3">__________________________________________对航空公司___________________________________________</td></tr>
                <tr>
                    <td>团毛利润：</td>
                    <td align="left">
                        <input name="txtProfits" id="txtProfits" class="colorblue2 p_5" readonly="readonly" type="text" style="width: 70px;">
                        =票面价&nbsp;&nbsp;&nbsp;&nbsp;<input name="txtTicketPriceTotal3" id="txtTicketPriceTotal3" class="colorblue2 p_5" readonly="readonly" type="text" style="width: 70px;">
                        *<span style="color: Green;">返点</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="txtAgentT" value="0" id="txtAgentT" onkeyup="agentTCheck();" class="colorblue2 p_5" style="width: 70px; color: Green;" type="text">
                        -手续费<input name="txtCharge" id="txtCharge" value="0" onkeyup="chargeCheck();" class="colorblue2 p_5" style="color: Green; width: 70px;" type="text">
                    </td>
                </tr>
                <tr>
                    <td>月底返代理费：</td>
                    <td align="left"> 
                        <input name="txtAgentFeeCarrier" id="txtAgentFeeCarrier" class="colorblue2 p_5" readonly="readonly" type="text" style="width: 70px;">
                        =<span>票面价</span>&nbsp;&nbsp;&nbsp;&nbsp;<input name="txtTicketPriceTotal1" id="txtTicketPriceTotal1" class="colorblue2 p_5" readonly="readonly" type="text" style="width: 70px;">
                        *<span style="color: Green;">月底返点</span><input name="txtAgent" id="txtAgent" value="0" onkeyup="agentCheck();" tabindex="0" style="width: 70px; color: Green;" class="colorblue2 p_5" type="text">
                  </td>
                </tr>
                   <tr>
                    <td>应付票款：</td>
                    <td align="left">
                        <input name="txtTUnAmount" id="txtTUnAmount" class="colorblue2 p_5" readonly="readonly" type="text" style="width: 70px;">
                        =票面价&nbsp;&nbsp;&nbsp;&nbsp;<input name="txtTicketPriceTotal2" id="txtTicketPriceTotal2" class="colorblue2 p_5" readonly="readonly"  type="text" style="width: 70px;">
                        -团毛利润<input name="txtProfit" id="txtProfit" class="colorblue2 p_5" readonly="readonly" type="text" style="width: 70px;">
                        +<span style="color: Green;">付退票手续费：</span><input name="txtTargetTGQFee" id="txtTargetTGQFee" value="0" onkeyup="targetTGQFee();" style="color: Green; width: 70px;" class="colorblue2 p_5" type="text"></td>
                </tr>
                <tr>
                    <td>实付票款：</td>
                    <td align="left">
                        <input name="txtTAmount" id="txtTAmount" class="colorblue2 p_5" style="width: 70px; color: Red;" readonly="readonly" type="text" />
                        =应付票款<input name="txtTUnAmount2" id="txtTUnAmount2" class="colorblue2 p_5" readonly="readonly" type="text" style="width: 70px;" />
                        +机建燃油税&nbsp;&nbsp;&nbsp;<input name="txtTax" id="txtTax" class="colorblue2 p_5" type="text" style="width: 70px;" />
                        </td>
                </tr>
                <tr>
                    <td>订单金额：</td>
                    <td align="left">
                    <input name="txtAmountSum" id="txtAmountSum" class="colorblue2 p_5" readonly="readonly" style="color: Red;" type="text" style="width: 70px;" />
                   
                </tr>
                <tr><td colspan="3"></td></tr>
                <tr><td colspan="3">___________________________________________利润_______________________________________________</td></tr>
                  <tr>
                    <td><span style="color: Green;">退票利润：</span></td>
                    <td align="left">
                    <input name="txtTProfit" id="txtTProfit" onkeyup="tProfitCheck();" value="0" style="width: 70px; color: Green;" class="colorblue2 p_5" type="text">
                    =付退票手续费-收退票手续费
                  </td>
                </tr>
                <tr>
                    <td>净利合计：</td>
                    <td align="left">  
                    <input name="txtTotalProfit" id="txtTotalProfit" class="colorblue2 p_5" readonly="readonly" type="text">
                    =团毛利润+退票利润+多收票款+多收税款-应付出团代理费(现返)-应付出团代理费(未返)</td>
                     
                </tr>  
                <tr>
                    <td></td>
                    
                    <td><br>注：只用填写绿色字体部分，其他数据自动计算得到！<br></td>
                </tr>
                 <tr>
                    <td colspan="2" align="center"> 
                        <input name="btnAdd" value="提 交" type="button" onclick="btnair()"/> &nbsp;&nbsp;&nbsp;&nbsp; 
                        <input value="重 置" id="btnReset" type="reset"> &nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                </tr>

          </tbody>
          </table>

	</form>
</div>


