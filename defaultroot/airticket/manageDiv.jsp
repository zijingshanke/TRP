<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="utf-8"%>
<script src="../_js/tsms/loadManage.js" type="text/javascript"></script>
<div id="dialog9" title="申请支付">
<form action="../airticket/airticketOrder.do?thisAction=applyTicket" id="form9"   method="post" >
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
		     <td><label for="password" style="color: red">利润</label></td>
		     <td><input type="text" name="liruen" id="liruen9"   value="0"  class="text ui-widget-content ui-corner-all" style="color: red"/></td>
		    </tr>
			<tr>
			<td colspan="2" align="center">
			  <input value="提交" type="button"  onclick="submitForm9()" class="button1">
			</td>
			</tr>
		</table>
	</form>
</div>
		
<div id="dialog" title="确认支付">
<form action="../airticket/airticketOrder.do?thisAction=confirmPayment" id="form1"   method="post" >
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
	     <td><label for="password" style="color: red">利润</label></td>
	     <td><input type="text" name="liruen" id="liruen1"   value="0"  class="text ui-widget-content ui-corner-all" style="color: red"/></td>
	    </tr>
		<tr>
		<td colspan="2" align="center">
		  <input value="提交" type="submit" class="button1" >
		</td>		
		</tr>		   
		</table>
	</form>
</div>
	
<div id="dialog2" title="确认出票">
<form action="../airticket/airticketOrder.do?thisAction=ticket"  method="post" id="form2" >
	    <input id="oId2" name="id" type="hidden" />
	    <input id="groupId2" name="groupId" type="hidden" />
	    出票PNR<input id="pnr2"  name="drawPnr" type="text" class="text ui-widget-content ui-corner-all" />
	    <input style="display: none;" type="button" value="自动刷新" onclick="getTempPNR()"/> <a href="#" onclick="showDiv10()"> [黑屏刷新]</a>
	    <table id="per">
	    <tbody>
		</tbody>
		</table>
		<input value="提交" type="button"  onclick="submitForm2();" class="button1">	
	</form>
</div>

<div id="dialog10" title="黑屏刷新">	
	<table>
		 <tr>		    
		     <td>
			      <input type="hidden" name="forwardPage" value="addOrder" />
			      <textarea rows="12" cols="60" id="memo10"></textarea>		     
		     </td>
		 </tr>
		 <tr>
			<td align="center">
				<input value="提交" type="button" onclick="getTempBlackPNR();" class="button1">
			</td>
		</tr>			   
	</table>
</div>

<div id="dialog17" title="卖出订单取消出票">
<form action="../airticket/airticketOrder.do?thisAction=quitSaleTicket"  method="post" id="form17"  onsubmit="return submitForm8()">  
	    <input id="status17" name="status" type="hidden"/>
	    <input id="oId17" name="id" type="hidden" />
	<table style="margin-left: 20px; margin-top: 20px; border: 1px solid black;" id="table1">
        <tbody>
       		 <tr>
                <td style="width: 300px;">
  				<table id="rbtnType" border="0">
		<tbody>
		<tr>
			<td><input id="rbtnType_0" name="rbtnType" value="自已取消出票" type="radio"><label for="rbtnType_0">自已取消出票</label></td>
			<td><input id="rbtnType_1" name="rbtnType" value="对方取消出票" checked="checked" type="radio" >
			<label for="rbtnType_1">对方取消出票</label></td>
		</tr>
		</tbody>
	</table>                
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
		</tbody>
	</table>
                </td>
           </tr>
           <tr>
            <td style="border: 1px solid black;" align="left">
                &nbsp;&nbsp;其它原因：
                 <textarea rows="5" cols="33"  id="cause"  class="text ui-widget-content ui-corner-all" ></textarea>
                <input type="hidden" name="memo"/>
            </td>
           </tr>
           <tr>
                <td align="center">
                                 
                </td>
            </tr>
        </tbody>
      </table>	    
	   <br/>
	<input value="提交" type="submit" class="button1">
	</form>
</div>

<div id="dialog18" title="买入订单取消出票">
<form action="../airticket/airticketOrder.do?thisAction=quitBuyTicket"  method="post" id="form18"  onsubmit="return submitForm8()">  
	    <input id="status18" name="status" type="hidden"/>
	    <input id="oId18" name="id" type="hidden" />
	<table style="margin-left: 20px; margin-top: 20px; border: 1px solid black;" id="table1">
        <tbody>
       		 <tr>
                <td style="width: 300px;">
  				<table id="rbtnType" border="0">
		<tbody>
		<tr>
			<td><input id="rbtnType_0" name="rbtnType" value="自已取消出票" type="radio"><label for="rbtnType_0">自已取消出票</label></td>
			<td><input id="rbtnType_1" name="rbtnType" value="对方取消出票" checked="checked" type="radio" >
			<label for="rbtnType_1">对方取消出票</label></td>
		</tr>
		</tbody>
	</table>                
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
		</tbody>
	</table>
                </td>
           </tr>
           <tr>
            <td style="border: 1px solid black;" align="left">
                &nbsp;&nbsp;其它原因：
                <textarea rows="5" cols="33"  id="cause"  class="text ui-widget-content ui-corner-all" ></textarea>
                <input type="hidden" name="memo"/>
            </td>
           </tr>
           <tr>
                <td align="center">
                                 
                </td>
            </tr>
        </tbody>
      </table>	    
	   <br/>
	<input value="提交" type="submit" class="button1">
	</form>
</div>


<div id="dialog3" title="审核退废1">
<form action="../airticket/airticketOrder.do?thisAction=auditRetire"  method="post" id="form3" >
   <table>
	 <input id="oId3" name="id" type="hidden" />
	  <input id="tranType3" name="tranType" type="hidden" />
	  <input id="groupId3" name="groupId" type="hidden"/>
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
	     <td><input type="text" name="handlingCharge" id="handlingCharge3"   value="0"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
	    <tr>
	     <td><label for="password">客规</label></td>
	     <td>
	       <select name="transRule" id="selTuiPercent3"  onchange="onchangeTransRule('3')">
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
		<input value="提交" type="button" onclick="submitForm3()" class="button1">
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
	       <select name="selTuiPercent3" id="selTuiPercent7" onchange="onchangeTransRule('7')">
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
		<input value="提交" type="button" onclick="submitForm7()" class="button1">
		</td>
		</tr>
		   
		</table>
	</form>
</div>

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
			     <td><input type="text" name="optTime" id="optTime4"  class="text ui-widget-content ui-corner-all"  onfocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" /></td>
	       </tr>  
	        <tr>
			     <td><label for="password">实际金额</label></td>
			     <td><input type="text" name="totalAmount" id="actualAmount4" value="0"  class="text ui-widget-content ui-corner-all" /></td>
	       </tr>
		   <tr>
				<td colspan="2" class="center">
					<input value="提交" type="button" onclick="submitForm4()" class="button1">
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
			<input value="提交" type="submit" class="button1" >
		</td>
		</tr>
		</table>
	</form>
</div>

<div id="dialog14" title="改签审核(外部)">
<form action="../airticket/airticketOrder.do?thisAction=auditOutUmbuchenOrder"  method="post" id="form14" >
	 <input id="oId14" name="id" type="hidden" />
	 <input id="tranType14" name="tranType" type="hidden" />
	 <input id="groupId14" name="groupId" type="hidden"/>
	 
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
		<td colspan="2" align="center">
			<input value="提交" type="submit" class="button1">
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
		       <input id="oId11" name="id" type="hidden" />
		  	    <table>
		     <tr>
		    
		     <td>
		      <textarea rows="12" cols="60" name="memo" id="memo11"></textarea>
		     
		     </td>
		    </tr>
			<tr>
			<td align="center">
			<input value="提交" type="submit" class="button1">
			</td>
			</tr>			   
			</table>
		</form>
	</div>
	
<div id="dialog12" title="审核退废(外部)">
<form action="../airticket/airticketOrder.do?thisAction=auditOutRetire"  method="post" id="form12" >
	  <input id="oId12" name="id" type="hidden" />
	  <input id="tranType12" name="tranType" type="hidden" />
	  <input id="groupId12" name="groupId" type="hidden"/>
	  <input id="TmptotalAmount12"  type="hidden"/>
	  <input id="passengersCount12"  type="hidden"/>
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
	     <td><label for="password">客规</label></td>
	     <td>
	       <select name="transRule" id="selTuiPercent12" onchange="onchangeTransRuleOut('12');">
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
		<input value="提交" type="submit" class="button1">
		</td>
		</tr>
	</table>
	</form>
</div>	

	
<div id="dialog13" title="审核退废2（外部）">
<form action="../airticket/airticketOrder.do?thisAction=auditOutRetire2"  method="post" id="form13" >
	 <input id="oId13" name="id" type="hidden" />
	  <input id="tranType13" name="tranType" type="hidden" />
	    <input id="groupId13" name="groupId" type="hidden"/>
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
	     <td><label for="password">应退金额</label></td>
	     <td><input type="text" name="totalAmount" id="totalAmount13" value="0"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
		 <tr>
	     <td><label for="password">手续费</label></td>
	     <td><input type="text" name="handlingCharge" id="handlingCharge13"   value="0"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
	    <tr>
	     <td><label for="password">客规</label></td>
	     <td>
	       <select name="transRule" id="selTuiPercent13" onchange="onchangeTransRuleOut('13')">
			<option selected="selected" value="0">-请选择-</option>
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
		<input value="提交" type="submit" class="button1">
		</td>
		</tr>
		   
		</table>
	</form>
</div>
