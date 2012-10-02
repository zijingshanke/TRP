
	//待处理新订单
	
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
		$("#txtTotalProfit").val((1*($("#txtProfits").val()) + 1*($("#txtTProfit").val())  + 1*($("#txtTaxMore").val()) + 1*($("#txtAmountMore").val()) -1*($("#txtAgentFeeTeams").val()) - 1*($("#txtUnAgentFeeTeams").val())).toFixed(4));	//净利合计=团毛利润+退票利润+多收票款+多收税款-应付出团代理费(金额为0略)
	}
	
	function unAgentFeeTeamsCheck() //应付出团代理费（未返)客户
	{
		/////////利润///////////
		$("#txtTotalProfit").val((1*($("#txtProfits").val()) + 1*($("#txtTProfit").val())  + 1*($("#txtTaxMore").val()) + 1*($("#txtAmountMore").val()) -1*($("#txtAgentFeeTeams").val()) - 1*($("#txtUnAgentFeeTeams").val())).toFixed(4));	//净利合计=团毛利润+退票利润+多收票款+多收税款-应付出团代理费(金额为0略)
	}
	
	function taxMoreCheck()//多收税(客户)
	{
		///////////对客户//////////////
		$("#txtSAmount").val(1*($("#txtTicketPriceTotal2").val()) + 1*($("#txtAmountMore").val()) -1*($("#txtAgentFeeTeam").val()) + 1*($("#txtTaxMore").val()) + 1*($("#txtSourceTGQFee").val()));//应收票价 =票面价+多收票价-现返+多收税 +收退票手续费：
		$("#txtSAmount2").val($("#txtSAmount").val());
		$("#txtTotalAmount").val(1*($("#txtSAmount2").val()) +1*($("#txtTax2").val()))//实收票款=应收票款 + 机场税
		
		/////////利润///////////
		$("#txtTotalProfit").val((1*($("#txtProfits").val()) + 1*($("#txtTProfit").val())  + 1*($("#txtTaxMore").val()) + 1*($("#txtAmountMore").val()) -1*($("#txtAgentFeeTeams").val()) - 1*($("#txtUnAgentFeeTeams").val())).toFixed(4));	//净利合计=团毛利润+退票利润+多收票款+多收税款-应付出团代理费(金额为0略)
	}
	
	function sourceTGQFeeCheck()//收退票手续费(客户)
	{
		var txtTProfit =1*($("#txtTargetTGQFee").val()) -1*($("#txtSourceTGQFee").val());
		$("#txtTProfit").val(txtTProfit.toFixed(4));
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
		$("#txtTotalProfit").val((1*($("#txtProfits").val()) + 1*($("#txtTProfit").val())  + 1*($("#txtTaxMore").val()) + 1*($("#txtAmountMore").val()) -1*($("#txtAgentFeeTeams").val()) - 1*($("#txtUnAgentFeeTeams").val())).toFixed(4))	//净利合计=团毛利润+退票利润+多收票款+多收税款-应付出团代理费(金额为0略)
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
		var txtTProfit =1*($("#txtTargetTGQFee").val()) -1*($("#txtSourceTGQFee").val());
		$("#txtTProfit").val(txtTProfit.toFixed(4));
		agentTCheck();
	}
	
	function tProfitCheck() //退票利润
	{
		/////////利润///////////
		$("#txtTotalProfit").val((1*($("#txtProfits").val()) + 1*($("#txtTProfit").val())  + 1*($("#txtTaxMore").val()) + 1*($("#txtAmountMore").val()) -1*($("#txtAgentFeeTeams").val()) - 1*($("#txtUnAgentFeeTeams").val())).toFixed(4));	//净利合计=团毛利润+退票利润+多收票款+多收税款-应付出团代理费(金额为0略)
	}

	
	//添加(利润统计)
	
	
	function showDiv(airticketOrderId,totalTicketPrice,totalAirportPrice,totalFuelPrice,totalAmount,teamaddPrice,agentaddPrice){
		 totalTickAirPrice= 1*totalAirportPrice + 1*totalFuelPrice;//机建燃油税＝机建税+燃油税
	  $("#txtTax2").val(totalTickAirPrice);//机建燃油税
	  $("#txtTax").val(totalTickAirPrice);//机建燃油税
	  
	  $("#airticketOrderId").val(airticketOrderId);
	  //-----客户------
	  $("#txtTicketPriceTotals").val(totalTicketPrice);//票面价
	  $("#txtTicketPriceTotal").val(totalTicketPrice);//票面价
	  $("#txtAmountMore").val(1*teamaddPrice + 1*agentaddPrice);//多收票价
	  
	  //-----航空-------
	  $("#txtAmountSum").val(totalAmount);//订单金额
	  $("#txtTicketPriceTotal3").val(totalTicketPrice);//票面价
	  $("#txtTicketPriceTotal1").val(totalTicketPrice);//票面价
	  $("#txtTicketPriceTotal2").val(totalTicketPrice);//票面价
	  
	  $('#dialog').dialog('open');
	  }
	  
	  //编辑(利润统计)
	function showDivUpdate(airticketOrderId,overTicketPrice,overAirportfulePrice,incomeRetreatcharge,totalTicketPriceAgent,totalAirportPriceAgent,
		totalFuelPriceAgent,totalAmount,incomeRetreatchargeAvia,commissonCountAgent,commissonCountAvia,rakeoffCount,totalAirportPriceAvia,totalFuelPriceAvia,
		totalTicketPriceAvia,actualAmountAgent,actualAmountAvia,handlingChargeAvia,memo,proxyPrice,tranType,tranTypeRufund,
		totalRufundTicket,commissonRufundCount,proxyRufundPrice,incomeRufundretreatCharge,
		totalRufundAirportPrice, totalRufundFuelPrice,rufundHandlingCharge,rufundRakeoffCount) 
		
	{   
			//setTimeout("amountMoreCheck()",100);//触发点
			setTimeout("agentsCheck()",100);
			setTimeout("agentTCheck()",100);
			setTimeout("targetTGQFee()",100);
			setTimeout("sourceTGQFeeCheck()",100);
			
		 $("#airticketOrderId").val(airticketOrderId);
	   
		
		 if(tranType ==1){//卖出
			  //-----客户------ 
			totalTickAirPriceAgent=1*totalAirportPriceAgent + 1*totalFuelPriceAgent;//机建燃油税＝机建税+燃油税
			 $("#txtTax2").val(totalTickAirPriceAgent);//机建燃油税
			$("#txtTicketPriceTotals").val(totalTicketPriceAgent);//票面价
			$("#txtTicketPriceTotal").val(totalTicketPriceAgent);//票面价
			$("#txtAmountMore").val(overTicketPrice);//多收票价
			$("#txtAmountMores").val(overTicketPrice);//多收票价
			$("#txtTaxMore").val(overAirportfulePrice);//多收税
			$("#txtSourceTGQFee").val(incomeRetreatcharge);//收退票手续费  
			$("#txtAgents").val(commissonCountAgent);//返点
			$("#txtTotalAmount").val(actualAmountAgent);//实收票款
			$("#txtRemark").val(memo);//备注
			$("#txtUnAgentFeeTeams").val(proxyPrice);//应付出团代理费(未返)
			
			 //-----航空-------
		 totalTickAirPriceAvia=1*totalAirportPriceAvia + 1*totalFuelPriceAvia;//机建燃油税＝机建税+燃油税
		  $("#txtTax").val(totalTickAirPriceAvia);//机建燃油税
		  
		$("#txtTicketPriceTotal3").val(totalTicketPriceAvia);//票面价
		 $("#txtTicketPriceTotal1").val(totalTicketPriceAvia);//票面价
		$("#txtTicketPriceTotal2").val(totalTicketPriceAvia);//票面价
		$("#txtAmountSum").val(totalAmount);//订单总金额
		$("#txtTargetTGQFee").val(incomeRetreatchargeAvia);//付退票手续费
		$("#txtAgentT").val(commissonCountAvia);//返点
		$("#txtAgent").val(rakeoffCount);//月底返点
		$("#txtTAmount").val(actualAmountAvia);//实付票款
		$("#txtCharge").val(handlingChargeAvia);//手续费
		}
		
		if(tranTypeRufund ==3)//退票
		{
			 totalTickAirPriceAgent=1*totalRufundAirportPrice + 1*totalRufundFuelPrice;//机建燃油税＝机建税+燃油税
			 $("#txtTax").val(totalTickAirPriceAgent);//机建燃油税
			$("#txtTicketPriceTotal3").val(totalRufundTicket);//票面价
			$("#txtTicketPriceTotal1").val(totalRufundTicket);//票面价
			$("#txtTicketPriceTotal2").val(totalRufundTicket);//票面价
			$("#txtAgentT").val(commissonRufundCount);//返点
			$("#txtAgent").val(rufundRakeoffCount);//月底返点
			$("#txtCharge").val(rufundHandlingCharge);//手续费
			$("#txtTargetTGQFee").val(incomeRufundretreatCharge);//付退票手续费
			
		}
		
		$('#dialog').dialog('open');
	}
	 
	  
	  //待申请支付订单
	  $(function() {		
		  var recentlyDayId=$("#recentlyDayId").val();
		  if(recentlyDayId==0){
		  $("#recentlyDayId").val('');
		   $("#ifRecentlyObj").attr("checked","");
		  }
		});
	   function selectRecent(){
      	var ifRecentlyObj=document.getElementById("ifRecentlyObj");
      	if(ifRecentlyObj.checked){
      		ifRecentlyObj.Checked=false;
      		ifRecentlyObj.value="1";
      		document.getElementById("recentlyDayId").value=1; 
      	}else{
      		ifRecentlyObj.Checked=true;
      		ifRecentlyObj.value="0";
      		document.getElementById("recentlyDayId").value='';
      	}      	
      }
      
      	
		
		//待确认支付订单
		function readonlyOK()
		{
			document.forms.form2.submit();
		}	
		function showDivOk(airOrderNo,totalAmount,agentId,cyrs,airticketOrderId,platComAccountId,sys_userName,groupMarkNo)    
		{
			airticketOrderBiz.getAirticketOrderByMarkNo(groupMarkNo,'1',getdata);
			
			$('#txtConfirmUser').val(sys_userName);//操作人
			$('#txtOrderNo').val(airOrderNo);//订单金额
			$('#txtOrderAmount').val(totalAmount);//订单金额
			$('#selTeamCmp').val(agentId);//购票客户
			$('#txtTAmount10').val(totalAmount);//实付
			$('#txtCarrier').val(cyrs);//航空公司
			$('#airticketOrderOkId').val(airticketOrderId);//订单ID
			
			$('#dialogOk').dialog('open');
		}
		
		function getdata(date)//回调
		{
			if(date !=null)
			{
				$("#txtSAmount10").val(date.totalAmount);
			}
		}
		
		//等待出票订单
		
		//待审核退废订单
		
		//已审待退款订单
		function readonlyOKFO()
		{
			document.forms.formFo.submit();
		}		
		function readonlyOKTo()
		{
			document.forms.formTo.submit();
		}		
		
		//退款
		function showDivFo(airticketId,incomeretreatCharge,entryOperatorName,memo)    
		{ 
			$('#airticketOrderFoId').val(airticketId);
			$('#txtRefundIncomeretreatChargeFo').val(incomeretreatCharge);//付退手续费
			$('#txtCurrentOperatorFo').val(entryOperatorName);//操作人
			$('#txtOrderRemarkFo').val(memo);//备注
			$('#dialogFo').dialog('open');
		}
		
		//卖出
		function showDivTo(airticketId,incomeretreatCharge,entryOperatorName,memo)    
		{
			$('#airticketOrderToId').val(airticketId);
			$('#txtRefundIncomeretreatChargeTo').val(incomeretreatCharge);//付退手续费
			$('#txtCurrentOperatorTo').val(entryOperatorName);//操作人
			$('#txtOrderRemarkTo').val(memo);//备注
			$('#dialogTo').dialog('open');
		}
		
		
			