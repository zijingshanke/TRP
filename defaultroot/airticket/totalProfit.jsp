﻿<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="utf-8"%>

<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />

		<script src="../_js/common.js" type="text/javascript"></script>
		<script src="../_js/popcalendar.js" type="text/javascript"></script>
	
	</head>
	<body>
		
	   <table id="list">
                <tbody><tr><td colspan="3">___________________________________________对客户_____________________________________________</td></tr>
                <tr>
                    <td>应付出团代理费（现返）：</td>
                    <td align="left">
                        <input name="txtAgentFeeTeams" id="txtAgentFeeTeams" class="colorblue2 p_5" readonly="readonly" type="text">
                        =(票面价&nbsp;&nbsp;&nbsp;<input name="txtTicketPriceTotals" id="txtTicketPriceTotals" class="colorblue2 p_5" value="1100" type="text">
                        +<span style="color: Green;">多收票价<input name="txtAmountMore" id="txtAmountMore" class="colorblue2 p_5" onkeyup="amountMoreCheck();" style="width: 70px; color: Green;"" type="text"></span>
                        )*<span style="color: Green;">返点&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="txtAgents" id="txtAgents" onkeyup="agentsCheck();" class="colorblue2 p_5" style="width: 70px; color: Green;" type="text"></span></td>
                </tr>
                <tr>
                    <td><span style="color: Green;">应付出团代理费（未返）：</span></td>
                    <td align="left">
                        <input name="txtUnAgentFeeTeams" id="txtUnAgentFeeTeams" onkeyup="unAgentFeeTeamsCheck();" class="colorblue2 p_5" style="width: 70px; color: Green;" type="text">
                        <span style="color: Green;">&nbsp;&nbsp;备注：&nbsp;&nbsp;&nbsp;&nbsp;</span><input name="txtRemark" id="txtRemark" class="colorblue2 p_5" style="width: 300px; color: Green;" class="TextUnderLine" type="text">
                    </td>
                </tr>
                <tr>
                    <td>应收票款：</td>
                    <td align="left">
                        <input name="txtSAmount" id="txtSAmount"  class="colorblue2" type="text">
                        =(票面价&nbsp;&nbsp;&nbsp;<input name="txtTicketPriceTotal" id="txtTicketPriceTotal" class="colorblue2 p_5" readonly="readonly" value="1100" type="text">
                         +<span>多收票价<input name="txtAmountMores" id="txtAmountMores" style="width: 70px;" class="colorblue2 p_5" readonly="readonly" type="text"></span>
                        )-现返<input name="txtAgentFeeTeam" id="txtAgentFeeTeam" class="colorblue2 p_5" readonly="readonly" type="text">
                        +<span style="color: Green;">多收税<input name="txtTaxMore" id="txtTaxMore" onkeyup="taxMoreCheck();" class="colorblue2 p_5" style="width: 70px;" type="text"></span>
                       +<span style="color: Green;">收退票手续费：</span><input name="txtSourceTGQFee" id="txtSourceTGQFee" onkeyup="sourceTGQFeeCheck();" class="colorblue2 p_5" style="color: Green; width: 70px;" type="text">
                  </td>
                </tr>
                    <tr>
                    <td>实收票款：</td>
                    <td align="left"> 
                    <input name="txtTotalAmount" id="txtTotalAmount" class="colorblue2 p_5" type="text">
                    =应收票款<input name="txtSAmount2" id="txtSAmount2" class="colorblue2 p_5" readonly="readonly" type="text">
                    +机场税&nbsp;&nbsp;&nbsp;<input name="txtTax2" id="txtTax2" class="colorblue2 p_5" readonly="readonly" value="130" type="text">
                    </td>                
                </tr>
                <tr><td colspan="3" style="width: 750px;"></td></tr>
                <tr><td colspan="3">__________________________________________对航空公司___________________________________________</td></tr> 
                <tr>
                    <td>团毛利润：</td>
                    <td align="left">
                        <input name="txtProfits" id="txtProfits" class="colorblue2 p_5" readonly="readonly" type="text">
                        =票面价&nbsp;&nbsp;&nbsp;&nbsp;<input name="txtTicketPriceTotal3" id="txtTicketPriceTotal3" class="colorblue2 p_5" readonly="readonly" value="1100" type="text">
                        *<span style="color: Green;">返点</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="txtAgentT" id="txtAgentT" onkeyup="agentTCheck();" class="colorblue2 p_5" style="width: 70px; color: Green;" type="text">
                        -手续费<input name="txtCharge" id="txtCharge" class="colorblue2 p_5" style="color: Green; width: 70px;" type="text">
                    </td>
                </tr>
                <tr>
                    <td>月底返代理费：</td>
                    <td align="left"> 
                        <input name="txtAgentFeeCarrier" id="txtAgentFeeCarrier" class="colorblue2 p_5" readonly="readonly" type="text">
                        =<span>票面价</span>&nbsp;&nbsp;&nbsp;&nbsp;<input name="txtTicketPriceTotal1" id="txtTicketPriceTotal1" class="colorblue2 p_5" readonly="readonly" value="1100" type="text">
                        *<span style="color: Green;">月底返点</span><input name="txtAgent" id="txtAgent" onkeyup="agentCheck();" tabindex="0" style="width: 70px; color: Green;" class="colorblue2 p_5" type="text">
                  </td>
                </tr>
                   <tr>
                    <td>应付票款：</td>
                    <td align="left">
                        <input name="txtTUnAmount" id="txtTUnAmount" class="colorblue2 p_5" readonly="readonly" type="text">
                        =票面价&nbsp;&nbsp;&nbsp;&nbsp;<input name="txtTicketPriceTotal2" id="txtTicketPriceTotal2" class="colorblue2 p_5" readonly="readonly" value="1100" type="text">
                        -团毛利润<input name="txtProfit" id="txtProfit" class="colorblue2 p_5" readonly="readonly" type="text">
                        +<span style="color: Green;">付退票手续费：</span><input name="txtTargetTGQFee" id="txtTargetTGQFee" onkeyup="targetTGQFee();" style="color: Green; width: 70px;" class="colorblue2 p_5" type="text"></td>
                </tr>
                <tr>
                    <td>实付票款：</td>
                    <td align="left">
                        <input name="txtTAmount" id="txtTAmount" class="colorblue2 p_5" style="color: Red;" readonly="readonly" type="text">
                        =应付票款<input name="txtTUnAmount2" id="txtTUnAmount2" class="colorblue2 p_5" readonly="readonly" type="text">
                        +机场税&nbsp;&nbsp;&nbsp;<input name="txtTax" id="txtTax" class="colorblue2 p_5" value="130" type="text">
                        </td>
                </tr>
                <tr>
                    <td>订单金额：</td>
                    <td align="left">
                    <input name="txtAmountSum" id="txtAmountSum" class="colorblue2 p_5" readonly="readonly" style="color: Red;" value="300.00" type="text">
                    =(<input name="txtAmountItem" id="txtAmountItem" class="colorblue2 p_5" style="width: 200px;" readonly="readonly" value="100.00+200.00" type="text">)</td>
                </tr>
                <tr><td colspan="3"></td></tr>
                <tr><td colspan="3">___________________________________________利润_______________________________________________</td></tr>
                  <tr>
                    <td><span style="color: Green;">退票利润：</span></td>
                    <td align="left">
                    <input name="txtTProfit" id="txtTProfit" onkeyup="tProfitCheck();" style="width: 70px; color: Green;" class="colorblue2 p_5" type="text">
                  </td>
                </tr>
                <tr>
                    <td>净利合计：</td>
                    <td align="left">  
                    <input name="txtTotalProfit" id="txtTotalProfit" class="colorblue2 p_5" readonly="readonly" type="text">
                    =团毛利润+退票利润+多收票款+多收税款-应付出团代理费</td>
                     
                </tr>  
                <tr>
                    <td></td>
                    
                    <td><br>注：只用填写绿色字体部分，其他数据自动计算得到！<br></td>
                </tr>
                 <tr>
                    <td colspan="2" align="center"> 
                        <input name="btnAdd" value="提 交" id="btnAdd" type="submit"> &nbsp;&nbsp;&nbsp;&nbsp; 
                        <input value="重 置" id="btnReset" type="reset"> &nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                </tr>

          </tbody>
          </table>
		
	</body>
</html>