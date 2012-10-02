<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="utf-8"%>

<html>
<div id="dialog9" title="申请支付">
	<p id="validateTips"></p>
<form action="../airticket/airticketOrder.do?thisAction=applyTicket" id="form9"   method="post" >
	<fieldset>
	    <input id="oId9" name="id" type="hidden" />
	     <input id="groupMarkNo9" name="groupMarkNo" type="hidden" />
	    <table>
		   <jsp:include page="../transaction/plat2.jsp"></jsp:include>
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
	     <td><label for="password" style="color: red">利润</label></td>
	     <td><input type="text" name="liruen" id="liruen9"   value="0"  class="text ui-widget-content ui-corner-all" style="color: red"/></td>
	    </tr>
		<tr>
		<td>
		  <input value="提交" type="button"  onclick="submitForm9()">
		</td>
		</tr>
		</table>
	</fieldset>
	</form>
</div>
		
<div id="dialog" title="确认支付">
	<p id="validateTips"></p>
<form action="../airticket/airticketOrder.do?thisAction=confirmTicket" id="form1"   method="post" >
	<fieldset>
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
	     <td><input type="text" id="totalAmount2" value="0"  class="text ui-widget-content ui-corner-all"  disabled="disabled"/>
	      <input type="hidden" id="totalAmount1" name="totalAmount" >
	      <input type="hidden" id="tmpTotalAmount1">
	     </td>
	    </tr>
		
		  <tr>
	     <td><label for="password">政策</label></td>
	     <td><input type="text" name="rebate" id="rebate1"   value="0"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
	     <tr>
	     <td><label for="password" style="color: red">利润</label></td>
	     <td><input type="text" name="liruen" id="liruen1"   value="0"  class="text ui-widget-content ui-corner-all" style="color: red"/></td>
	    </tr>
		<tr>
		<td>
		  <input value="提交" type="submit" >
		</td>
		
		</tr>
		   
		</table>
	</fieldset>
	</form>
</div>
	
<div id="dialog2" title="出票">
	<p id="validateTips"></p>
<form action="../airticket/airticketOrder.do?thisAction=ticket"  method="post" id="form2" >
	<fieldset>
	    <input id="oId2" name="id" type="hidden" />
	    <input id="groupMarkNo2" name="groupMarkNo2" type="hidden" />
	    出票PNR<input type="text" name="drawPnr" id="pnr2"  class="text ui-widget-content ui-corner-all" />
	    <input style="display: none;" type="button" value="自动刷新" onclick="getTempPNR()"/> <a href="#" onclick="showDiv10()"> [黑屏刷新]</a>
	    <table id="per">
	    <tbody>
		</tbody>
		</table>
		<input value="提交" type="button"  onclick="submitForm2();">	
	</fieldset>
	</form>
</div>



	<div id="dialog10" title="黑屏刷新">
		<p id="validateTips"></p>
	
		<fieldset>
		      <html:hidden property="forwardPage" value="addTradingOrder"/>
		  	    <table>
		     <tr>
		    
		     <td>
		      <textarea rows="12" cols="60" id="memo10"></textarea>
		     
		     </td>
		    </tr>
			<tr>
			<td>
			<input value="提交" type="button" onclick="getTempBlackPNR();">
			</td>
			</tr>
			   
			</table>
		</fieldset>
	</div>


<div id="dialog8" title="取消出票">
	<p id="validateTips"></p>
<form action="../airticket/airticketOrder.do?thisAction=quitTicket"  method="post" id="form8"  onsubmit="return submitForm8()">
	 <input id="status8" name="status" type="hidden"/>
	    <input id="oId8" name="id" type="hidden" />
	    <input id="groupMarkNo8" name="groupMarkNo8" type="hidden" />
	    
<table style="margin-left: 20px; margin-top: 20px; border: 1px solid black;" id="table1">
            <tbody><tr>
                <td style="width: 300px;">
  <table id="rbtnType" border="0">
	<tbody>
	<tr>
		<td><input id="rbtnType_0" name="rbtnType" value="自已取消出票" type="radio"><label for="rbtnType_0">自已取消出票</label></td>
		<td><input id="rbtnType_1" name="rbtnType" value="对方取消出票" checked="checked" type="radio" ">
		<label for="rbtnType_1">对方取消出票</label></td>
	</tr>
</tbody></table>                
                </td>
            </tr>
            <tr>
                <td style="border: 1px solid black;">
                &nbsp;&nbsp;取消原因：
                 <table id="rbtnReason" border="0">
	<tbody><tr>
		<td><input id="rbtnReason_0" name="rbtnReason" value="政策错误" type="radio"><label for="rbtnReason_0">政策错误</label></td>
	</tr><tr>
		<td><input id="rbtnReason_1" name="rbtnReason" value="位置不是KK或者HK" type="radio"><label for="rbtnReason_1">位置不是KK或者HK</label></td>
	</tr><tr>
		<td><input id="rbtnReason_2" name="rbtnReason" value="航段不连续" type="radio"><label for="rbtnReason_2">航段不连续</label></td>
	</tr><tr>
		<td><input id="rbtnReason_3" name="rbtnReason" value="该编码入库失败" type="radio"><label for="rbtnReason_3">该编码入库失败</label></td>
	</tr><tr>
		<td><input id="rbtnReason_4" name="rbtnReason" value="该航空网站上不去" type="radio"><label for="rbtnReason_4">该航空网站上不去</label></td>
	</tr><tr>
		<td><input id="rbtnReason_5" name="rbtnReason" value="价格不符" type="radio"><label for="rbtnReason_5">价格不符</label></td>
	</tr><tr>
		<td><input id="rbtnReason_6" name="rbtnReason" value="白金卡无此政策" type="radio"><label for="rbtnReason_6">白金卡无此政策</label></td>
	</tr>
</tbody></table>
                </td>
           </tr>
           <tr>
            <td style="border: 1px solid black;" align="left">
                &nbsp;&nbsp;其它原因： <textarea rows="5" cols="33"  id="cause"  class="text ui-widget-content ui-corner-all" ></textarea>
                <input type="hidden" name="memo"/>
            </td>
           </tr>
           <tr>
                <td align="center">
                                 
                </td>
            </tr>
        </tbody></table>	    
	    
	    
	   <br/>
		<input value="提交" type="submit" >
		
	
	</form>
</div>




<div id="dialog3" title="审核1">
	<p id="validateTips"></p>
<form action="../airticket/airticketOrder.do?thisAction=auditRetireTrading"  method="post" id="form3" >
	<fieldset>
	 <input id="oId3" name="id" type="hidden" />
	  <input id="tranType3" name="tranType" type="hidden" />
	  <input id="groupMarkNo3" name="groupMarkNo" type="hidden"/>
	  <input id="TmptotalAmount3"  type="hidden"/>
	    <input id="passengersCount3"  type="hidden"/>
	      <input id="oldPassengersCount3"  type="hidden"/>
	  	    <table>
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
	     <td><input type="text" name="handlingCharge" id="handlingCharge3"   value="0"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
	    <tr>
	     <td><label for="password">客规</label></td>
	     <td>
	       <select name="transRule" id="selTuiPercent3"  onchange="selTuiPercent_onchange()">
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
		<input value="提交" type="button" onclick="submitForm3()">
		</td>
		</tr>
		</table>
	</fieldset>
	</form>
</div>


<div id="dialog7" title="审核.">
	<p id="validateTips"></p>
<form action="../airticket/airticketOrder.do?thisAction=auditRetireTrading2"  method="post" id="form7" >
	<fieldset>
	 <input id="oId7" name="id" type="hidden" />
	  <input id="tranType7" name="tranType" type="hidden" />
	    <input id="groupMarkNo7" name="groupMarkNo" type="hidden"/>
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
	     <td><label for="password">应收金额</label></td>
	     <td><input type="text" name="totalAmount" id="totalAmount7" value="0"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
		 <tr>
	     <td><label for="password">手续费</label></td>
	     <td><input type="text" name="handlingCharge" id="handlingCharge7"   value="0"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
	    <tr>
	     <td><label for="password">客规</label></td>
	     <td>
	       <select name="selTuiPercent3" id="selTuiPercent7" onchange="selTuiPercent_onchange2()">
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
		<input value="提交" type="button" onclick="submitForm7()">
		</td>
		</tr>
		   
		</table>
	</fieldset>
	</form>
</div>

<div id="dialog4" title="确认金额">
	<p id="validateTips"></p>
<form action="../airticket/airticketOrder.do?thisAction=collectionRetireTrading"  method="post" id="form4" >
	<fieldset>
	 <input id="oId4" name="id" type="hidden" />
	  <input id="tranType4" name="tranType" type="hidden" />
	  	    <table>
	  	   <tr>
	     <td><label>时间</label></td>
	     <td><input type="text" name="optTime" id="optTime4"  class="text ui-widget-content ui-corner-all"  onfocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" /></td>
	    </tr>  
	     <tr>
	     <td><label for="password">实际金额</label></td>
	     <td><input type="text" name="totalAmount" id="actualAmount4" value="0"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
		<tr>
		<td>
		<input value="提交" type="button" onclick="submitForm4()">
		</td>
		</tr>		   
		</table>
	</fieldset>
	</form>
</div>

<div id="dialog5" title="改签审核1">
	<p id="validateTips"></p>
<form action="../airticket/airticketOrder.do?thisAction=auditWaitAgreeUmbuchenOrder"  method="post" id="form5" >
	<fieldset>
	 <input id="oId5" name="id" type="hidden" />
	 <input id="tranType5" name="tranType" type="hidden" />
	 <input id="groupMarkNo5" name="groupMarkNo" type="hidden"/>
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
		<td>
		<input value="提交" type="submit" >
		</td>
		</tr>
		</table>
	</fieldset>
	</form>
</div>

<div id="dialog14" title="改签审核(外部)">
	<p id="validateTips"></p>
<form action="../airticket/airticketOrder.do?thisAction=auditOutWaitAgreeUmbuchenOrder"  method="post" id="form14" >
	<fieldset>
	 <input id="oId14" name="id" type="hidden" />
	 <input id="tranType14" name="tranType" type="hidden" />
	 <input id="groupMarkNo14" name="groupMarkNo" type="hidden"/>
	  	    <table>
	 
	      <tr>
			<td>平台：</td>
			<td>
				<select name="platformId14" id="platform_Id14" onchange="loadCompanyList('platform_Id14','company_Id14','account_Id14')" class="text ui-widget-content ui-corner-all">		
							<option value="">请选择</option>															
				</select>
			</td>
			</tr>	
			<tr>
			<td>
				公司：
			</td>
			<td>
				<select name="companyId14" id="company_Id14"  onchange="loadAccount('platform_Id14','company_Id14','account_Id14')" class="text ui-widget-content ui-corner-all">		
					<option value="">请选择</option>								
				</select>
			</td>
				</tr>	
			<tr>
			<td>
				账号：
			</td>
			<td>
				<select name="accountId14" id="account_Id14"  class="text ui-widget-content ui-corner-all">		
					<option value="">请选择</option>								
				</select>
			</td>
			
			</tr>
		<tr>
	     <td><label for="password">订单号</label></td>
	     <td><input type="text" name="airOrderNo"   class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
		<tr>
		<td>
		<input value="提交" type="submit" >
		</td>
		</tr>
		</table>
	</fieldset>
	</form>
</div>


<div id="dialog6" title="确认款">
	<p id="validateTips"></p>
<form action="../airticket/airticketOrder.do?thisAction=receiptWaitAgreeUmbuchenOrder"  method="post" id="form6" >
	<fieldset>
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
		<input value="提交" type="submit" >
		</td>
		<td>
		<input value="免费改签" type="button" onclick="submitForm6();">
		</td>
		</tr>
		   
		</table>
	</fieldset>
	</form>
</div>

	<div id="dialog11" title="备注">
		<p id="validateTips"></p>
	<form action="../airticket/airticketOrder.do?thisAction=editRemark"  method="post" id="form11" >
		<fieldset>
		       <input id="oId11" name="id" type="hidden" />
		  	    <table>
		     <tr>
		    
		     <td>
		      <textarea rows="12" cols="60" name="memo" id="memo11"></textarea>
		     
		     </td>
		    </tr>
			<tr>
			<td>
			<input value="提交" type="submit" >
			</td>
			</tr>
			   
			</table>
		</fieldset>
		</form>
	</div>
	
	
	
<div id="dialog12" title="审核12">
	<p id="validateTips"></p>
<form action="../airticket/airticketOrder.do?thisAction=auditOutRetireTrading"  method="post" id="form12" >
	<fieldset>
	 <input id="oId12" name="id" type="hidden" />
	  <input id="tranType12" name="tranType" type="hidden" />
	  <input id="groupMarkNo12" name="groupMarkNo" type="hidden"/>
	  <input id="TmptotalAmount12"  type="hidden"/>
	  	    <table>
     	   <tr>
			<td>平台：</td>
			<td>
				<select name="platformId12" id="platform_Id12" onchange="loadCompanyList('platform_Id12','company_Id12','account_Id12')" class="text ui-widget-content ui-corner-all">		
							<option value="">请选择</option>															
				</select>
			</td>
			</tr>	
			<tr>
			<td>
				公司：
			</td>
			<td>
				<select name="companyId12" id="company_Id12"  onchange="loadAccount('platform_Id12','company_Id12','account_Id12')" class="text ui-widget-content ui-corner-all">		
					<option value="">请选择</option>								
				</select>
			</td>
				</tr>	
			<tr>
			<td>
				账号：
			</td>
			<td>
				<select name="accountId12" id="account_Id12"  class="text ui-widget-content ui-corner-all">		
					<option value="">请选择</option>								
				</select>
			</td>
			
			</tr>
		
	     <tr>
	     <td><label for="password">订单号</label></td>
	     <td><input type="text" name="airOrderNo" id="airOrderNo"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
	    <tr>
	     <td><label for="password">应收金额</label></td>
	     <td><input type="text" name="totalAmount" id="totalAmount12" value="0"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
		 <tr>
	     <td><label for="password">手续费</label></td>
	     <td><input type="text" name="handlingCharge" id="handlingCharge12"   value="0"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
	
		<tr>
		<td>
		<input value="提交" type="submit" >
		</td>
		</tr>
		</table>
	</fieldset>
	</form>
</div>
	
	
	
<div id="dialog13" title="审核13">
	<p id="validateTips"></p>
<form action="../airticket/airticketOrder.do?thisAction=auditOutRetireTrading2"  method="post" id="form13" >
	<fieldset>
	 <input id="oId13" name="id" type="hidden" />
	  <input id="tranType13" name="tranType" type="hidden" />
	    <input id="groupMarkNo13" name="groupMarkNo" type="hidden"/>
	  <input id="TmptotalAmount13"  type="hidden"/>
	  	    <table>
<!--  	  	 <tr>
	     <td><label for="password">平台：</label></td>
	     <td><input type="text" name="pnr" id="pnr"  class="text ui-widget-content ui-corner-all" disabled="disabled" /></td>
	    </tr> 
	     <tr>
	     <td><label for="password">公司：</label></td>
	     <td><input type="text" name="pnr" id="pnr"  class="text ui-widget-content ui-corner-all" disabled="disabled" /></td>
	    </tr>  
	        <tr>
	     <td><label for="password">账号：</label></td>
	     <td><input type="text" name="pnr" id="pnr"  class="text ui-widget-content ui-corner-all" disabled="disabled" /></td>
	    </tr>  -->
		
	     <tr>
	     <td><label for="password">订单号</label></td>
	     <td><input type="text" name="airOrderNo" id="airOrderNo"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
	    <tr>
	     <td><label for="password">应收金额</label></td>
	     <td><input type="text" name="totalAmount" id="totalAmount13" value="0"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
		 <tr>
	     <td><label for="password">手续费</label></td>
	     <td><input type="text" name="handlingCharge" id="handlingCharge13"   value="0"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
		<tr>
		<td>
		<input value="提交" type="submit" >
		</td>
		</tr>
		   
		</table>
	</fieldset>
	</form>
</div>

</html>
