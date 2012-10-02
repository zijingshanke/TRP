<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="utf-8"%>

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
                        <input name="btnAdd" value="提 交" type="button" onclick="btnair()"/> &nbsp;&nbsp;&nbsp;&nbsp; 
                        <input value="重 置" id="btnReset" type="reset"> &nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                </tr>

          </tbody>
          </table>

	</form>
<script type="text/javascript">
	function btnair()//添加利润统计 
	{
		var pan = /^[0-9]+$/;
		var txtAmountMores =$("#txtAmountMores").val();
		var txtAmountSum =$("#txtAmountSum").val();//订单金额
		var txtTAmount =$("#txtTAmount").val();//实付票款
		//if(!pan.test(txtAmountMores))
		//{
		//	alert("多收票价输入格式不正确!");
		//	return false;
		//}
		if(txtAmountSum != txtTAmount)
		{
			alert("实付票款与订单金额不一至，请核对！");
			return false;
		}
		document.forms.form1.submit();
	}
</script>

	<script type="text/javascript">
	function totalAmountCheck()//总金额
	{
		$("#txt_UnsettledAccount").val(1*($("#txt_TotalAmount").val()) - 1*($("#txt_ActualAmount").val()));//未结款=总金额-实收款
		if(1*($("#txt_TotalAmount").val())> (1*($("#txt_ActualAmount").val())) && 1*($("#txt_ActualAmount").val())>0)//总金额>实收款
		{
			$("#txt_Status").val(2);//部分结算
		}
		if(1*($("#txt_TotalAmount").val())<= 1*($("#txt_ActualAmount").val()))//总金额<=实收款
		{
			$("#txt_Status").val(1);//已结算
		}
		if(1*($("#txt_ActualAmount").val()) == 0)
		{
			$("#txt_Status").val(0);//未结算
		}
	}
	function actualAmountCheck()//实收款
	{
		$("#txt_UnsettledAccount").val(1*($("#txt_TotalAmount").val()) - 1*($("#txt_ActualAmount").val()));//未结款=总金额-实收款
		if(1*($("#txt_TotalAmount").val())> (1*($("#txt_ActualAmount").val())) && 1*($("#txt_ActualAmount").val())>0)//总金额>实收款
		{
			$("#txt_Status").val(2);//部分结算
		}
		if(1*($("#txt_TotalAmount").val())<= 1*($("#txt_ActualAmount").val()))//总金额<=实收款
		{
			$("#txt_Status").val(1);//已结算
		}
		if(1*($("#txt_ActualAmount").val()) == 0)
		{
			$("#txt_Status").val(0);//未结算
		}
	}
</script>

<script type="text/javascript">

	function amountMoreCheck()//多收票价(对客户)
	{
		///////////对客户//////////////
		
		$("#txtAgentFeeTeams").val(((1*($("#txtTicketPriceTotals").val()) + 1*($("#txtAmountMore").val())) * (1*($("#txtAgents").val()))).toFixed(4));//应付出团代理费（现返）=(票面价+票面价)*返点
		$("#txtAmountMores").val($("#txtAmountMore").val());//多收票价2
		$("#txtAgentFeeTeam").val($("#txtAgentFeeTeams").val());//现返
		$("#txtSAmount").val(1*($("#txtTicketPriceTotal2").val()) + 1*($("#txtAmountMores").val()) -1*($("#txtAgentFeeTeam").val()) + 1*($("#txtTaxMore").val()) + 1*($("#txtSourceTGQFee").val()));//应收票价 =票面价+多收票价-现返+多收税 +收退票手续费：
		$("#txtSAmount2").val($("#txtSAmount").val());//应收票价2
		$("#txtTotalAmount").val(1*($("#txtSAmount2").val()) +1*($("#txtTax2").val()) + 1*($("#txtTaxMore").val()) + 1*($("#txtSourceTGQFee").val()))//实收票款=应收票款 + 机场税
		
		//////////对航空公司//////////
		$("#txtProfits").val((1*($("#txtTicketPriceTotal3").val()) * (1*($("#txtAgentT").val())) - 1*($("#txtCharge").val())).toFixed(4));//团毛利润=票面价*返点-手续费
		$("#txtAgentFeeCarrier").val((1*($("#txtTicketPriceTotal1").val()) * (1*($("#txtAgent").val()))).toFixed(4));//月底返代理费=票面价*月底返点
		$("#txtProfit").val($("#txtProfits").val());//团毛利润2
		$("#txtTUnAmount").val(1*($("#txtTicketPriceTotal2").val()) - 1*($("#txtProfit").val()) + 1*($("#txtTargetTGQFee").val()));//应付票款=票面价-团毛利润2+付退票手续费
		$("#txtTUnAmount2").val($("#txtTUnAmount").val());//应付票款2
		$("#txtTAmount").val(1*($("#txtTUnAmount2").val()) + 1*($("#txtTax").val()))//实付票款2=应付票款2  + 机场税2
		
		/////////利润///////////
		$("#txtTotalProfit").val(Math.round(1*($("#txtProfits").val()) + 1*($("#txtTProfit").val())  + 1*($("#txtTaxMore").val()) + 1*($("#txtAmountMore").val()) -1*($("#txtAgentFeeTeams").val()) - 1*($("#txtUnAgentFeeTeams").val()))); //净利合计=团毛利润+退票利润+多收票款+多收税款-应付出团代理费
	}
	
	function agentsCheck()//返点(客户)
	{
		///////////对客户//////////////
		$("#txtAgentFeeTeams").val(((1*($("#txtTicketPriceTotals").val()) + 1*($("#txtAmountMore").val())) * 1*($("#txtAgents").val())).toFixed(4));
		$("#txtAgentFeeTeam").val($("#txtAgentFeeTeams").val());//现返 
		$("#txtSAmount").val(1*($("#txtTicketPriceTotal2").val()) + 1*($("#txtAmountMore").val()) -1*($("#txtAgentFeeTeam").val()) + 1*($("#txtTaxMore").val()) + 1*($("#txtSourceTGQFee").val()));//应收票价 =票面价+多收票价-现返+多收税 +收退票手续费：
		$("#txtSAmount2").val($("#txtSAmount").val());//应收票款2
		$("#txtTotalAmount").val(1*($("#txtSAmount2").val()) +1*($("#txtTax2").val()))//实收票款=应收票款 + 机场税
		
		amountMoreCheck();//多收票价(对客户)
		//////////对航空公司//////////
		
		/////////利润///////////
		$("#txtTotalProfit").val((1*($("#txtProfits").val()) + 1*($("#txtTProfit").val())  + 1*($("#txtTaxMore").val()) + 1*($("#txtAmountMore").val()) - 1*($("#txtAgentFeeTeams").val()) - 1*($("#txtUnAgentFeeTeams").val())).toFixed(4));	//净利合计=团毛利润+退票利润+多收票款+多收税款-应付出团代理费(金额为0略)
	}
	
	function unAgentFeeTeamsCheck() //应付出团代理费（未返)客户
	{
		/////////利润///////////
		$("#txtTotalProfit").val((1*($("#txtProfits").val()) + 1*($("#txtTProfit").val())  + 1*($("#txtTaxMore").val()) + 1*($("#txtAmountMore").val()) - 1*($("#txtAgentFeeTeams").val()) - 1*($("#txtUnAgentFeeTeams").val())).toFixed(4));	//净利合计=团毛利润+退票利润+多收票款+多收税款-应付出团代理费(金额为0略)
	}
	
	function taxMoreCheck()//多收税(客户)
	{
		///////////对客户//////////////
		$("#txtSAmount").val(1*($("#txtTicketPriceTotal2").val()) + 1*($("#txtAmountMore").val()) -1*($("#txtAgentFeeTeam").val()) + 1*($("#txtTaxMore").val()) + 1*($("#txtSourceTGQFee").val()));//应收票价 =票面价+多收票价-现返+多收税 +收退票手续费：
		$("#txtSAmount2").val($("#txtSAmount").val());
		$("#txtTotalAmount").val(1*($("#txtSAmount2").val()) +1*($("#txtTax2").val()))//实收票款=应收票款 + 机场税
		
		/////////利润///////////
		$("#txtTotalProfit").val((1*($("#txtProfits").val()) + 1*($("#txtTProfit").val())  + 1*($("#txtTaxMore").val()) + 1*($("#txtAmountMore").val()) - 1*($("#txtAgentFeeTeams").val()) - 1*($("#txtUnAgentFeeTeams").val())).toFixed(4));	//净利合计=团毛利润+退票利润+多收票款+多收税款-应付出团代理费(金额为0略)
	}
	
	function sourceTGQFeeCheck()//收退票手续费(客户)
	{
		$("#txtTProfit").val(1*($("#txtTargetTGQFee").val()) -1*($("#txtSourceTGQFee").val()));
		taxMoreCheck();//多收税(客户)
	}
	
	function agentTCheck() //返点(对航空公司)
	{
		$("#txtProfits").val((1*($("#txtTicketPriceTotal3").val()) * (1*($("#txtAgentT").val())) -1*($("#txtCharge").val())).toFixed(4));//团毛利润=票面价*返点-手续费
		 $("#txtAgentFeeCarrier").val((1*($("#txtTicketPriceTotal1").val()) * 1*($("#txtAgent").val())).toFixed(4));//月底返代理费=票面价*月底返点
		$("#txtProfit").val($("#txtProfits").val());//团毛利润2
		$("#txtTUnAmount").val(1*($("#txtTicketPriceTotal2").val()) - 1*($("#txtProfits").val()) + 1*($("#txtTargetTGQFee").val()));//应付票款=票面价-团毛利润+付退票手续费
		$("#txtTUnAmount2").val($("#txtTUnAmount").val());//应付票款2
		$("#txtTAmount").val(1*($("#txtTUnAmount").val()) + 1*($("#txtTax").val()));//实付票款=应付票款+机场税 
		/////////利润///////////
		$("#txtTotalProfit").val((1*($("#txtProfits").val()) + 1*($("#txtTProfit").val())  + 1*($("#txtTaxMore").val()) + 1*($("#txtAmountMore").val()) - 1*($("#txtAgentFeeTeams").val()) - 1*($("#txtUnAgentFeeTeams").val())).toFixed(4))	//净利合计=团毛利润+退票利润+多收票款+多收税款-应付出团代理费(金额为0略)
	}  
	
	function chargeCheck() //手续费(对航空公司)
	{
		agentTCheck();
	}
	
	function agentCheck() //月底返点(对航空公司)
	{
		 $("#txtAgentFeeCarrier").val((1*($("#txtTicketPriceTotal1").val()) * 1*($("#txtAgent").val())).toFixed(4));//月底返代理费=票面价*月底返点
	}
	
	function targetTGQFee() //付退票手续费(对航空公司)
	{
		$("#txtTProfit").val(1*($("#txtTargetTGQFee").val()) -1*($("#txtSourceTGQFee").val()));
		agentTCheck();
	}
	
	function tProfitCheck() //退票利润
	{
		/////////利润///////////
		$("#txtTotalProfit").val((1*($("#txtProfits").val()) + 1*($("#txtTProfit").val())  + 1*($("#txtTaxMore").val()) + 1*($("#txtAmountMore").val()) - 1*($("#txtAgentFeeTeams").val()) - 1*($("#txtUnAgentFeeTeams").val())).toFixed(4));	//净利合计=团毛利润+退票利润+多收票款+多收税款-应付出团代理费(金额为0略)
	}
</script>
</div>