<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="utf-8"%>
	
<script src="../_js/tsms/loadManage.js" type="text/javascript"></script>
<div id="dialog9" title="申请支付">
<form action="../airticket/airticketOrder.do?thisAction=applyPayOrder" id="form9"   method="post" >
	    <input id="oId9" name="id" type="hidden" />
	    <input id="groupId9" name="groupId" type="hidden" />
	    <table>
		   <jsp:include page="../transaction/plat2.jsp?currentObjId=9"></jsp:include>
			<tr>
			     <td><label for="password">PNR</label></td>
			     <td><input type="text" name="pnr" id="pnr9"  class="text ui-widget-content ui-corner-all" /></td>
		    </tr>
		     <tr>
			     <td><label for="password">订单号</label></td>
			     <td><input type="text" name="airOrderNo" id="airOrderNo9"  class="text ui-widget-content ui-corner-all" /></td>
		    </tr>
		    <tr>
		     <td><label for="password">金额</label></td>
		     <td><input type="text" name="totalAmount" id="totalAmount9" value="0" onkeyup="calculationLiren('tmpTotalAmount9','totalAmount9','liruen9');"
		     onkeydown="calculationLiren('tmpTotalAmount9','totalAmount9','liruen9');"
		       class="text ui-widget-content ui-corner-all" />
		     <input type="hidden" id="tmpTotalAmount9"/></td>
		    </tr>		
			<tr>
		     <td><label for="password">政策</label></td>
		     <td><input type="text" name="rebate" id="rebate9"   value="0"  class="text ui-widget-content ui-corner-all" /></td>
		    </tr>
		     <tr>
			     <td><label>时间</label></td>
			     <td><input type="text" name="optTime" id="optTime9"  ondblclick="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" />鼠标双击修改</td>
	       </tr>  
		    <tr>
		     <td><label for="password" style="color: red">利润</label></td>
		     <td><input type="text" name="liruen" id="liruen9"   value="0"  class="text ui-widget-content ui-corner-all" style="color: red"/></td>
		    </tr>
			<tr>
			<td colspan="2" align="center">
			  <input value="提交" type="button" id="submitButton9"  onclick="submitForm9()" class="button1">
			</td>
			</tr>
		</table>
	</form>
</div>
		
<div id="dialog" title="确认支付">
<form action="../airticket/airticketOrder.do?thisAction=confirmPayment" id="form1"   method="post"
 onsubmit="setSubmitButtonDisable('submitButton1')">
	    <input id="oId" name="id" type="hidden" />
	    <table>
		   <jsp:include page="../transaction/plat.jsp"></jsp:include>
		<tr>
	     <td><label for="password">PNR</label></td>
	     <td><input type="text" name="pnr" id="pnr1"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
	     <tr>
	     <td><label for="password">订单号</label></td>
	     <td><input type="text" name="airOrderNo" id="airOrderNo1"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
	    <tr>
	     <td><label for="password">金额</label></td>
	     <td><input type="text" name="sureTotalAmount" id="totalAmount2" value="0" onkeyup="calculationLiren('tmpTotalAmount1','totalAmount2','liruen1');"
	     onkeydown="calculationLiren('tmpTotalAmount1','totalAmount2','liruen1');" class="text ui-widget-content ui-corner-all" />
	      <input type="hidden" id="totalAmount1" name="totalAmount" >
	      <input type="hidden" id="tmpTotalAmount1">
	     </td>
	    </tr>		
		 <tr>
	     <td><label for="password">政策</label></td>
	     <td><input type="text" name="rebate" id="rebate1"   value="0"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
	     <tr>
			     <td><label>时间</label></td>
			     <td><input type="text" name="optTime" id="optTime1"    ondblclick="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" /></td>
	       </tr>    
	     <tr>
	     <td><label for="password" style="color: red">利润</label></td>
	     <td><input type="text" name="liruen" id="liruen1"   value="0"  class="text ui-widget-content ui-corner-all" style="color: red"/></td>
	    </tr>
		<tr>
		<td colspan="2" align="center">
		  <input value="提交" type="submit"  class="button1"  id="submitButton1">
		</td>		
		</tr>		   
		</table>
	</form>
</div>
	
<div id="dialog2" title="确认出票">
<form action="../airticket/airticketOrder.do?thisAction=confirmTicket"  method="post" id="form2" >
	    <input id="oId2" name="id" type="hidden" />
	    出票PNR<input id="pnr2"  name="drawPnr" type="text" onkeypress="keypressSubmitForm2(event)"/>	    
	    <table id="per">
		    <tbody>
			</tbody>
		</table>
		<input value="提交" type="button"  onclick="submitForm2();" onkeypress="keypressSubmitForm2(event)" class="button1" id="submitButton2">	
	</form>
</div>

<div id="dialog17" title="卖出订单取消出票">
<form action="../airticket/airticketOrder.do?thisAction=quitSaleTicket"  method="post" id="form17">  
  <table>
  	<tr>
  		<td colspan="2">
  			<input id="status17" name="status" type="hidden"/>
	    	<input id="oId17" name="id" type="hidden" />
  		</td>
  	</tr>
	<tr>
		<td><input  name="quitTicketType" value="自已取消出票" type="radio"/>自已取消出票</td>
		<td><input  name="quitTicketType" value="对方取消出票" type="radio" checked="checked" />对方取消出票</td>
	</tr>    
     <tr>
     	<td>&nbsp;&nbsp;取消原因：</td>
     	<td></td>
     </tr>
     <tr>
		<td><input name="quitTicketReason" value="政策错误" type="radio">政策错误</td></tr>
	<tr>
		<td><input name="quitTicketReason" value="位置不是KK或者HK" type="radio">位置不是KK或者HK</td></tr>
	<tr>
		<td><input name="quitTicketReason" value="航段不连续" type="radio">航段不连续</td></tr>
	<tr>
		<td><input name="quitTicketReason" value="该编码入库失败" type="radio">该编码入库失败</td></tr>
	<tr>
		<td><input name="quitTicketReason" value="该航空网站上不去" type="radio">该航空网站上不去</td></tr>
	<tr>
		<td><input name="quitTicketReason" value="价格不符" type="radio">价格不符</td></tr>
	<tr>
		<td><input name="quitTicketReason" value="白金卡无此政策" type="radio">白金卡无此政策</td></tr>
     <tr>
     <tr>
        <td colspan="2">
            <textarea rows="5" cols="40" name="memo"></textarea>
        </td>
      </tr>
      <tr>
      	<td colspan="2" align="center">
      		<input value="提交" type="submit" class="button1" id="submitButton17">
      	</td>
      </tr>
      </table>	
	</form>
</div>

<div id="dialog18" title="买入订单取消出票">
<form action="../airticket/airticketOrder.do?thisAction=quitBuyTicket"  method="post" id="form18"  onsubmit="return submitForm8()">  
	<table>
	  <tr>
	  <td colspan="2">
	    <input id="status18" name="status" type="hidden"/>
	    <input id="oId18" name="id" type="hidden" />
	    </td>
	</tr>
	<tr>
		<td><input  name="quitTicketType" value="自已取消出票" type="radio"/>自已取消出票</td>
		<td><input  name="quitTicketType" value="对方取消出票" type="radio" checked="checked" />对方取消出票</td>
	</tr>    
     <tr>
     	<td>&nbsp;&nbsp;取消原因：</td>
     	<td></td>
     </tr>
     <tr>
		<td><input name="quitTicketReason" value="政策错误" type="radio">政策错误</td></tr>
	<tr>
		<td><input name="quitTicketReason" value="位置不是KK或者HK" type="radio">位置不是KK或者HK</td></tr>
	<tr>
		<td><input name="quitTicketReason" value="航段不连续" type="radio">航段不连续</td></tr>
	<tr>
		<td><input name="quitTicketReason" value="该编码入库失败" type="radio">该编码入库失败</td></tr>
	<tr>
		<td><input name="quitTicketReason" value="该航空网站上不去" type="radio">该航空网站上不去</td></tr>
	<tr>
		<td><input name="quitTicketReason" value="价格不符" type="radio">价格不符</td></tr>
	<tr>
		<td><input name="quitTicketReason" value="白金卡无此政策" type="radio">白金卡无此政策</td></tr>
     <tr>
     <tr>
        <td colspan="2">
            <textarea rows="5" cols="40" name="memo"></textarea>
        </td>
      </tr>
      <tr>
      	<td colspan="2" align="center">
      		<input value="提交" type="submit" class="button1" id="submitButton18">
      	</td>
      </tr>	
      </table>
	</form>
</div>


<!-- 取消出票，确认退款 -->
<div id="dialog19" title="取消出票确认退款">
<form action="../airticket/airticketOrder.do?thisAction=agreeCancelRefund"  method="post" id="form19" >
	 <input id="id19" name="id" type="hidden" />
	  	    <table>
	  	     <tr>
			     <td><label>平台--账号</label></td>
			     <td><span id="platformName19"></span><span id="accountName19"></span></td>
	       </tr>
	  	   <tr>
			     <td><label>时间</label></td>
			     <td><input type="text" name="optTime" id="optTime19" ondblclick="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" /></td>
	       </tr>  
	        <tr>
			     <td><label for="password">实际金额</label></td>
			     <td><input type="text" name="totalAmount" id="totalAmount19" value="0"  class="text ui-widget-content ui-corner-all" /></td>
	       </tr>
		   <tr>
				<td colspan="2" class="center">
					<input value="提交" type="button" onclick="submitForm19()" class="button1" id="submitButton19">
				</td>
		   </tr>		   
		</table>
	</form>
</div>

<div id="dialog3" title="审核退废1">
<form action="../airticket/airticketOrder.do?thisAction=auditRetire"  method="post" id="form3" >
   <table>
	 <input id="oId3" name="id" type="hidden" />
	  <input id="tranType3" name="tranType" type="hidden" />
	  <input id="TmptotalAmount3"  type="hidden"/>
	  <input id="passengersCount3"  type="hidden"/>
	  <input id="oldPassengersCount3"  type="hidden"/>
	  <input id="flightClass3" type="hidden"/>	 
    		 <tr>
			<td>平台：</td>
			<td>
				<select name="platformId3" id="platform_Id3" class="text ui-widget-content ui-corner-all">		
							<option value="">请选择</option>															
				</select>
			</td>
			</tr>	
			<tr>
			<td>
				公司：
			</td>
			<td>
				<select name="companyId3" id="company_Id3"  class="text ui-widget-content ui-corner-all">		
					<option value="">请选择</option>								
				</select>
			</td>
				</tr>	
			<tr>
			<td>
				账号：
			</td>
			<td>
				<select name="accountId3" id="account_Id3"  class="text ui-widget-content ui-corner-all">		
					<option value="">请选择</option>								
				</select>
			</td>
		<tr>
	     <td><label for="password">订单号</label></td>
	     <td><input type="text" name="airOrderNo" id="airOrderNo3"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
	    <tr>
	     <td><label for="password">应收金额</label></td>
	     <td><input type="text" name="totalAmount" id="totalAmount3" value="0"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
		 <tr>
	     <td><label for="password">手续费</label></td>
	     <td><input type="text" name="handlingCharge" id="handlingCharge3"  value="0"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
	    <tr>
	     <td><label for="password">客规</label></td>
	     <td>
	       <select name="transRule" id="transRuleSelectObj3"  onchange="onchangeTransRule('3')">
			<option selected="selected" value="0">--请选择--</option>
			<option value="0">0%</option>
			<option value="5">5%</option>
			<option value="10">10%</option>
			<option value="20">20%</option>
			<option value="30">30%</option>
			<option value="50">50%</option>
			<option value="100">100%</option>
		</select>
	     </td>
	    </tr>
		<tr>
		<td>
		<input value="提交" type="button" onclick="submitForm3()" class="button1" id="submitButton3">
		</td>
		</tr>
		</table>
	</form>
</div>

<div id="dialog7" title="审核退废2">
<form action="../airticket/airticketOrder.do?thisAction=auditRetire2"  method="post" id="form7" >
	 <input id="oId7" name="id" type="hidden" />
	  <input id="tranType7" name="tranType" type="hidden" />
	    <input id="groupId7" name="groupId" type="hidden"/>
	  <input id="TmptotalAmount7"  type="hidden"/>
	   <input id="passengersCount7"  type="hidden"/>
	    <input id="oldPassengersCount7"  type="hidden"/>
	  	    <table>
 			<tr>
			<td>平台：</td>
			<td>
				<select name="platformId7" id="platform_Id7" class="text ui-widget-content ui-corner-all">		
							<option value="">请选择</option>															
				</select>
			</td>
			</tr>	
			<tr>
			<td>
				公司：
			</td>
			<td>
				<select name="companyId7" id="company_Id7"  class="text ui-widget-content ui-corner-all">		
					<option value="">请选择</option>								
				</select>
			</td>
				</tr>	
			<tr>
			<td>
				账号：
			</td>
			<td>
				<select name="accountId7" id="account_Id7"  class="text ui-widget-content ui-corner-all">		
					<option value="">请选择</option>								
				</select>
			</td>			
			</tr>		
	     <tr>
	     <td><label for="password">订单号</label></td>
	     <td><input type="text" name="airOrderNo" id="airOrderNo7"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
	    <tr>
	     <td><label for="password">应退金额</label></td>
	     <td><input type="text" name="totalAmount" id="totalAmount7" value="0"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
		 <tr>
	     <td><label for="password">手续费</label></td>
	     <td><input type="text" name="handlingCharge" id="handlingCharge7"   value="0"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
	    <tr>
	     <td><label for="password">客规</label></td>
	     <td>
	       <select name="transRuleSelectObj3" id="transRuleSelectObj7" onchange="onchangeTransRule('7')">
			<option selected="selected" value="0">--请选择--</option>
			<option value="0">0%</option>
			<option value="5">5%</option>
			<option value="10">10%</option>
			<option value="20">20%</option>
			<option value="30">30%</option>
			<option value="50">50%</option>
			<option value="100">100%</option>
		</select>
	     </td>
	    </tr>
		<tr>
		<td colspan="2" align="center">
		<input value="提交" type="button" onclick="submitForm7()" class="button1" id="submitButton7">
		</td>
		</tr>
		   
		</table>
	</form>
</div>

<!-- 退票、废票退款 -->
<div id="dialog4" title="确认金额">
<form action="../airticket/airticketOrder.do?thisAction=collectionRetire"  method="post" id="form4" >
	 <input id="id4" name="id" type="hidden" />
	  <input id="tranType4" name="tranType" type="hidden" />
	  	    <table>
	  	     <tr>
			     <td><label>平台--账号</label></td>
			     <td><span id="platformName4"></span><span id="accountName4"></span></td>
	       </tr>
	  	   <tr>
			     <td><label>时间</label></td>
			     <td><input type="text" name="optTime" id="optTime4" ondblclick="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" /></td>
	       </tr>  
	        <tr>
			     <td><label for="password">实际金额</label></td>
			     <td><input type="text" name="totalAmount" id="actualAmount4" value="0"  class="text ui-widget-content ui-corner-all" /></td>
	       </tr>
		   <tr>
				<td colspan="2" class="center">
					<input value="提交" type="button" onclick="submitForm4()" class="button1" id="submitButton4">
				</td>
		   </tr>		   
		</table>
	</form>
</div>

<div id="dialog5" title="改签审核1">
<form action="../airticket/airticketOrder.do?thisAction=auditUmbuchenOrder"  method="post" id="form5" >
	 <input id="oId5" name="id" type="hidden" />
	 <input id="tranType5" name="tranType" type="hidden" />
	 <input id="groupId5" name="groupId" type="hidden"/>
	  	    <table>
		 <tr>
			<td>平台：</td>
			<td>
				<select name="platformId5" id="platform_Id5" onchange="loadCompanyList('platform_Id5','company_Id5','account_Id5')" class="text ui-widget-content ui-corner-all">		
						<option value="">请选择</option>															
				</select>
			</td>
			</tr>	
			<tr>
				<td>
					公司：
				</td>
				<td>
					<select name="companyId5" id="company_Id5"  onchange="loadAccount('platform_Id5','company_Id5','account_Id5')" class="text ui-widget-content ui-corner-all">		
						<option value="">请选择</option>								
					</select>
				</td>
			</tr>	
			<tr>
			<td>
				账号：
			</td>
			<td>
				<select name="accountId5" id="account_Id5"  class="text ui-widget-content ui-corner-all">		
					<option value="">请选择</option>								
				</select>
			</td>			
			</tr>	
		<tr>
	     <td><label for="password">订单号</label></td>
	     <td><input type="text" name="airOrderNo"   class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
		<tr>
		<td colspan="2" align="center">
			<input value="提交" type="submit" class="button1" id="submitButton5">
		</td>
		</tr>
		</table>
	</form>
</div>

<div id="dialog6" title="确认改签金额">
<form action="../airticket/airticketOrder.do?thisAction=receiptUmbuchenOrder"  method="post" id="form6" >
	 <input id="oId6" name="id" type="hidden" />
	  <input id="tranType6" name="tranType" type="hidden" />
	  <table>
	  <tr>
		    <td>平台：</td>
			<td>
				<select name="platformId6"  id="platformId6" disabled="disabled"  class="text ui-widget-content ui-corner-all">		
					<option value="">请选择</option>															
				</select>
			</td>
		</tr>		
		<tr>
	    <td>公司：</td>
			<td>
			<select name="companyId6" id="companyId6"  disabled="disabled"   class="text ui-widget-content ui-corner-all">		
				<option value="">请选择</option>															
			</select>
			</td>
		</tr>
		<tr>
		    <td>账号：</td>
			<td>
				<select name="accountId6" id="accountId6" disabled="disabled"    class="text ui-widget-content ui-corner-all">		
				<option value="">请选择</option>															
				</select>
			</td>
		</tr>		
	     <tr>
		     <td><label for="password">支付金额</label></td>
		     <td><input type="text" name="totalAmount" id="totalAmount6"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
		<tr>
			<td>
				<input value="提交" type="submit" class="button1">
			</td>
			<td>
				<input value="免费改签" type="button" onclick="submitForm6();" class="button1">
			</td>
		</tr>		   
		</table>
	</form>
</div>

<div id="dialog11" title="备注">
	<form action="../airticket/airticketOrder.do?thisAction=editRemark"  method="post" id="form11" >		      
		<table>
		  <tr>		    
		     <td>
		      <input id="oId11" name="id" type="hidden" />
		      <textarea rows="12" cols="60" name="memo" id="memo11"></textarea>		     
		     </td>
		  </tr>
		  <tr>
			<td align="center">
			<input value="提交" type="submit" class="button1" id="submitButton11">
			</td>
			</tr>			   
		</table>
	</form>
</div>