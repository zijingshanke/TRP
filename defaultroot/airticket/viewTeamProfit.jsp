<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>

<div id="dialog" title="利润统计明细" >	
<table>
	   			<tr><td colspan="2">___________________________________客户___________________________________</td></tr>
                <tr>
                    <td>代理费(现返):</td>
                    <td align="left">
                    	<input type="hidden" name="id" id="id1" value="0" />
                    	<input type="hidden" name="tranType" id="tranType1" value="0"/>
                        <span id="commission1"></span>
                        =(票面价:<span id="totalTicketPrice1" ></span>
                       <span style="display: none;color: Green;">+多收票价:<input name="saleOverTicketPrice" id="overTicketPrice1" class="colorblue2 p_5" onkeyup="onkeyOverTicketPrice();" value="0" style="width: 70px; color: Green;" type="text"></span>
                        +多收票价:<span id="saleOverTicketPrice2" style="color: Green;"></span>                        
                        )*<span style="color: Green;">返点:<input name="saleCommissonCount" id="commissonCount1" value="0" onkeyup="onkeyCommissonCount1();" class="colorblue2 p_5" style="width: 70px; color: Green;" type="text"></span>
                    </td>
                </tr>
                <tr>
                    <td><span style="color: Green;">代理费(后返):</span></td>
                    <td align="left">
                        <input name="saleRakeOff" id="rakeOff1" value="0" onkeyup="onkeyRakeOff1();" class="colorblue2 p_5" style="width: 70px; color: Green;" type="text">
                        <span style="color: Green;">后返备注:</span><input name="saleMemo" id="memo1" class="colorblue2 p_5" style="width: 300px; color: Green;" type="text">
                    </td>
                </tr>
                <tr>
                    <td>应收票款:</td>
                    <td align="left">
                        <span id="saleTicketPrice"></span>
                        =(票面价:<span id="totalTicketPrice2" ></span> + <span>多收票价:<span id="overTicketPrice2"></span>)
                        -现返:<span id="commission2" ></span>
                        +<span style="color: Green;">多收税:<span id="overAirportfulePrice1" ></span></span>
                        -<span style="color: Green;">收退票手续费:</span><input name="saleIncomeretreatCharge" id="incomeretreatCharge1" value="0" onkeyup="onkeyincomeretreatCharge1()" class="colorblue2 p_5" style="color: Green; width: 70px;" type="text">
                  </td>
                </tr>
                <tr>
                    <td>实收票款:</td>
                    <td align="left"> 
	                    <span id="saleTotalAmount"></span>=应收票款:<span id="saleTicketPrice2" ></span>+机建燃油税:<span id="totalAirportFuelPrice1" ></span>
                   		<input type="hidden" name="saleTotalAmount" id="saleTotalAmount2" value="0" />
                    </td>                
                </tr>                
                <tr><td colspan="2">_________________________________航空公司_________________________________</td></tr>
                <tr>
                    <td>团毛利润：</td>
                    <td align="left">
                        <span id="grossProfit1"></span>
                        =票面价:<span id="totalTicketPrice3"></span>*<span style="color: Green;">返点</span>
                        <input  name="buyCommissonCount" id="commissonCount2"  onkeyup="onkeyCommissonCount2()" style="width:70px;color:Green;" value="0"  class="colorblue2 p_5"  type="text">
                        <span style="color: Green;">-手续费:</span>
                        <input name="buyHandlingCharge" id="handlingCharge1" value="0" onkeyup="onkeyhandlingCharge1()" tabindex="0" style="width: 70px; color: Green;" class="colorblue2 p_5" type="text">
                  </td>
                </tr>
                <tr>
                    <td>月底返代理费:</td>
                    <td align="left"> 
                        <span id="rakeOff2"></span>
                        =票面价:<span id="totalTicketPrice4" ></span>
                        *<span style="color: Green;">月底返点:</span>
                        <input name="buyRakeoffCount" id="rakeoffCount2" value="0" onkeyup="onkeyrakeoffCount2()" tabindex="0" style="width: 70px; color: Green;" class="colorblue2 p_5" type="text">
                  </td>
                </tr>
                   <tr>
                    <td>应付票款:</td>
                    <td align="left">
                        <span id="buyTicketPrice1" ></span>
                        =票面价:<span id="totalTicketPrice5" ></span>
                        -团毛利润:<span id="grossProfit2" ></span>
                        -<span style="color: Green;">付退票手续费:</span>
                        <input name="buyIncomeretreatCharge" id="incomeretreatCharge2" value="0" onkeyup="onkeyincomeretreatCharge2()" style="color: Green; width: 70px;" class="colorblue2 p_5" type="text"></td>
                </tr>
                <tr>
                    <td>实付票款:</td>
                    <td align="left">
                        <span id="buyTotalAmount2"></span>=应付票款:<span id="buyTicketPrice2" ></span>+机建燃油税:<span id="totalAirportFuelPrice2"></span>
                    </td>
                </tr>
                <tr>
                    <td>订单金额:</td><td align="left"><span id="buyTotalAmount1" style="color: red"></span></td>
                </tr>
                <tr><td colspan="2">___________________________________利润___________________________________</td></tr>             
                  <tr>
                    <td>退票利润</td>
                    <td align="left">
                    <span id="refundProfit" onkeyup="onkeyrefundProfit()"></span>=收退票手续费-付退票手续费
                  </td>
                </tr>
                <tr>
                    <td>净利合计:</td>
                    <td align="left"><span id="totalProfit" ></span>
                    <span id="saleTotalProfit">=团毛利润+退票利润+多收票款+多收税款-代理费(现返)-代理费(后返)</span>
                    <span id="refundTotalProfit" style="display: none">=退票利润+代理费(现返)+代理费(后返)-团毛利润-多收票款-多收税款</span>                    
                    </td>                     
                </tr>  
          </table>
</div>