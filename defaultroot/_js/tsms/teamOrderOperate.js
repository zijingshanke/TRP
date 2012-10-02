/**
 * 团队订单管理操作
 **/
	//保存编辑利润统计 
	function editTeamProfig(){
		//订单金额与实付票款必须相同
		var buyTotalAmount1=document.getElementById("buyTotalAmount1").innerHTML;
		var buyTotalAmount2=document.getElementById("buyTotalAmount2").innerHTML;
		
		//alert("amount1:"+buyTotalAmount1+"--amount2:"+buyTotalAmount2);
		
		buyTotalAmount1=ForDight(buyTotalAmount1,2);
		buyTotalAmount2=ForDight(buyTotalAmount2,2);
		
		
		if (buyTotalAmount1!=buyTotalAmount2) {
			alert("实付票款必须与订单金额相等");
			return false;
		}		
		
		var saleTotalAmount=document.getElementById("saleTotalAmount").innerHTML;
		if(saleTotalAmount==null||saleTotalAmount==""){
			saleTotalAmount="0";
		}
		document.forms.form1.saleTotalAmount.value=saleTotalAmount;
		trim(document.forms.form1);
		document.forms.form1.submit(); 
	}
	
	//确认支付
	function showDiv10(airticketOrderId,groupId,airOrderNo,totalAmount,cyrs){
		document.getElementById("userName").innerHTML="<c:out value='${URI.user.userName}'>";//操作人
		document.getElementById("airOrderNo10").innerHTML=airOrderNo;//订单号
		document.getElementById("orderAmount").innerHTML=totalAmount;//订单金额
		//alert(totalAmount);
		document.getElementById("totalAmount10").value=totalAmount;//实付
		
		document.getElementById("carrier").innerHTML=cyrs;//航空公司
		$('#id10').val(airticketOrderId);//订单ID
		
		$('#dialog10').dialog('open');
		$('#dialog10').draggable({cancle: 'form'}); 
		 			
		airticketOrderBiz.getAirticketOrderByGroupIdAndTranType(groupId,1,function(saleOrder){//卖出
			if(saleOrder !=null){
				document.getElementById("agentName").innerHTML=saleOrder.agent.name;//购票客户
				document.getElementById("sellAmount").innerHTML=saleOrder.totalAmount;//实收
			}
		});	
		
		airticketOrderBiz.getAirticketOrderByGroupIdAndTranType(groupId,2,function(buyOrder){//买入
			if(buyOrder !=null){
				document.getElementById("airOrderNo10").innerHTML=buyOrder.airOrderNo;//订单号
				document.getElementById("totalAmount10").value=buyOrder.totalAmount;//实付
			}
		});							
	}
	
	//根据销售订单创建退票--选择航程
	function showDiv21(groupId){		
		$('#groupId21').val(groupId);
		$('#dialog21').dialog('open');
		$('#dialog21').draggable({cancle: 'form'}); 
		//alert(groupId);
		if(groupId!=null&&groupId>0){
			airticketOrderBiz.getAirticketOrderByGroupIdAndTranType(groupId,1,function(order){//卖出
				if(order !=null){
				 $('#tempFlightTable tbody').html("");
	 			 $('#tempFlightTable tbody').append('<tr><td width="200">承运人</td><td width="200">行程</td>'
	 				+'<td width="200">选择</td></tr>');
	 			
		 			 var cyr="";
		  			 var hc="";
					 var passengerName="";
					 var cpTime=order.drawTimeText;
					 var aoId=order.id;
		  
					 var  flights= order.flights;
					// alert("flights length:"+flights.length);
					 var flightHTML="";
					 for(var f=0;f<flights.length;f++){
					 	var flight=flights[f];
					     flightHTML=flightHTML+'<tr>'+'<td>'+flight.cyr+'</td>' 
					     			+'<td>'+flight.hcText+'</td>'
					     			+'<td><input type="checkBox" name="flightIds"  value="' + flight.id + '" /></td>' 
					     			+'</tr>';
					 }
					  $('#tempFlightTable tbody').append(flightHTML);
					   
		  			//alert("222");
		 		 	//var passengers=order.passengers;		 		 	
		 			// for(var p=0;p<passengers.length;p++){
		    		 // alert(passengers[p].name);
		     			// passengerName+=passengers[p].name;
		      			//if(p<passengers.length-1){
		      				 //passengerName+="|";
		     			 //}
		  			//}    
	    		}				
			});		
		} 				
	}
	
//根据销售订单创建退票--提交
function submitForm21(){	
    var  tempflightIds=document.getElementsByName("flightIds");	  
    //alert("flightIds:"+tempflightIds);
    if(tempflightIds!=null){
    	var flightIds=new Array();
    	var flightIdsCount=0;
    	for(var i=0;i<tempflightIds.length;i++){
    		if(tempflightIds[i].checked){
    			flightIdsCount=1;
    			break;
    		}
    	}
    	//alert(flightIdsCount);
    	if(flightIdsCount==0){
    		alert("请至少选择一个航程");
    		return false;
    	}
    	
    	$('#form21').submit();    	
    }else{
    	alert("请选择需要退票的航程");
    	return false;
    }
}
	

	//卖出退票，确认付退款
	function showDiv11(id,incomeretreatCharge){ 
		$('#id11').val(id);
		$('#incomeretreatCharge11').val(incomeretreatCharge);//付退手续费				
		document.getElementById("userName11").innerHTML="<c:out value='${URI.user.userName}'>";//操作人	
		$('#dialog11').dialog('open');
	}
		
	//买入退票，确认收退款
	function showDiv12(id,incomeretreatCharge){
		$('#id12').val(id);
		$('#incomeretreatCharge12').val(incomeretreatCharge);//收退手续费		
		document.getElementById("userName12").innerHTML="<c:out value='${URI.user.userName}'>";//操作人			
		$('#dialog12').dialog('open');
	}
	
	
	//编辑页面航空公司现返
	function onkeybuyCommissonCount(){
		var commissonCount=document.getElementById("buyCommissonCount").value;
		var totalTicketPrice=$("input[name='totalTicketPrice']").val();
		var totalAirportPrice=$("input[name='totalAirportPrice']").val();
		var totalFuelPrice=$("input[name='totalFuelPrice']").val();
		
		
		if(totalTicketPrice==null){
			totalTicketPrice="0";
		}
		if(totalAirportPrice==null){
			totalAirportPrice="0";
		}
		if(totalFuelPrice==null){
			totalFuelPrice="0";
		}		
		
		var buyTotalAmount=accMul(1*totalTicketPrice,1*commissonCount);//返点
		buyTotalAmount=ForDight(buyTotalAmount,1);
		
		buyTotalAmount=1*totalTicketPrice-1*buyTotalAmount+1*totalAirportPrice+1*totalFuelPrice;
		
		document.getElementById("totalAmount").value=buyTotalAmount;
		
	}
	
	//=====================================利润统计==初始化=================================================================
	//添加(利润统计)
	function showDiv(groupId){		
		airticketOrderBiz.listByGroupIdAndTranType(groupId,"1,2,3",function(list){
			var listLength=list.length;
			//alert("listLength:"+listLength);
			if(listLength>0){
				for(var i=0;i<listLength;i++){
					var order=list[i];
					var tranType=order.tranType;
					var businessType=order.businessType;
					//alert("tranType:"+tranType+"--businessType:"+businessType);	
					//alert(tranType==2);
					if(tranType==1){
						setProfitValueBySaleOrder(order);						
					}else if(tranType==2){
						setProfitValueByBuyOrder(order);						
					}else if(tranType==3){
						if(businessType==1){
							setProfitValueBySaleOrder(order);	
						}else if(businessType==2){
							setProfitValueByBuyOrder(order);
						}						
					}							
				}				
				setTimeout("onkeyCommission()",100);
				setTimeout("onkeysaleTicketPrice()",100);
				setTimeout("onkeysaleTotalAmount()",100);
				setTimeout("onkeygrossProfit()",100);
				setTimeout("onkeyrakeoffCount2()",100);
				setTimeout("onkeyincomeretreatCharge1()",100);
				setTimeout("onkeyincomeretreatCharge2()",100);
				setTimeout("onkeyBuyTicketPrice()",100);
				setTimeout("onkeybuyTotalAmount2()",100);
				setTimeout("onkeyrefundProfit()",100);				
				setTimeout("onkeytotalProfit()",100);	
				$('#dialog').dialog('open');
				$('#dialog').draggable({cancle: 'form'}); 							
			}
		});	  
 } 	
	
	//卖出---客户
	function setProfitValueBySaleOrder(order){
		  var id=order.id;
		  var totalTicketPrice=order.totalTicketPrice;//总票面价
		  var totalAirportPrice=order.totalAirportPrice;//总机建
		  var totalFuelPrice=order.totalFuelPrice;//总燃油税
		  var totalAirportFuelPrice=1*totalAirportPrice + 1*totalFuelPrice;//机建燃油税＝机建税+燃油税
		  // 		  
		  $("#id1").val(id);
		  document.getElementById("totalTicketPrice1").innerHTML=totalTicketPrice;
	  	  document.getElementById("totalTicketPrice2").innerHTML=totalTicketPrice;
	  	  document.getElementById("totalAirportFuelPrice1").innerHTML=totalAirportFuelPrice;
	  	  
	  	  //-------以下为统计过利润的订单才有的值
		  var overTicketPrice=accAdd(order.teamaddPrice,order.agentaddPrice);//卖出：多收票价=团队加价+客户加价	
	  	  var overAirportfulePrice=order.overAirportfulePrice;
	  	  var commissonCount=order.commissonCount;
	  	  var rakeOff=order.rakeOff;
	  	  var incomeretreatCharge=order.incomeretreatCharge;
	  	  var memo=order.memo;
	  	  var agentaddPrice=order.agentaddPrice;
	  	  
	  	  if (overTicketPrice==null) {
	  	  	overTicketPrice="0";
	  	  }
	  	  if (agentaddPrice==null) {
	  	  	agentaddPrice="0";
	  	  }	  	  
	  	  if (overAirportfulePrice==null) {
	  	  	overAirportfulePrice="0";
	  	  } if (commissonCount==null) {
	  	  	commissonCount="0";
	  	  }if(rakeOff==null){
	  	  	rakeOff="0";
	  	  } if (incomeretreatCharge==null) {
	  	  	incomeretreatCharge="0";
	  	  } if (memo==null) {
	  	  	memo=""; 
	  	  }  	  	  
	  	
		  document.getElementById("saleOverTicketPrice2").innerHTML=overTicketPrice;
		  document.getElementById("overTicketPrice2").innerHTML=overTicketPrice;
	  	  document.getElementById("overAirportfulePrice1").value=overAirportfulePrice;
	  	  document.getElementById("commissonCount1").value=commissonCount;
	  	  document.getElementById("rakeOff1").value=rakeOff;
	  	  document.getElementById("incomeretreatCharge1").value=incomeretreatCharge;
	  	  document.getElementById("memo1").value=memo;	  	 
	}
	
	//买入---航空公司
	function setProfitValueByBuyOrder(order){	
		 //alert("buyOrder");	
		  var totalTicketPrice=order.totalTicketPrice;//票面价
		  var totalAirportPrice=order.totalAirportPrice;//总机建
		  var totalFuelPrice=order.totalFuelPrice;//总燃油税
		  var totalAirportFuelPrice=1*totalAirportPrice + 1*totalFuelPrice;//机建燃油税＝机建税+燃油税
		  var totalAmount=order.totalAmount;
		  
		  document.getElementById("totalTicketPrice3").innerHTML=totalTicketPrice;
	  	  document.getElementById("totalTicketPrice4").innerHTML=totalTicketPrice;
	  	  document.getElementById("totalTicketPrice5").innerHTML=totalTicketPrice;
	  	  document.getElementById("totalAirportFuelPrice2").innerHTML=totalAirportFuelPrice;	  	  
	  	  document.getElementById("buyTotalAmount1").innerHTML=totalAmount;
	  	  
	  	    //-------以下为统计过利润的订单才有的值
	  	    var commissonCount=order.commissonCount;
	  	    var incomeretreatCharge=order.incomeretreatCharge;
	  	    var rakeoffCount=order.rakeoffCount;
	  	    if (commissonCount==null) {
	  	    	commissonCount="0";
	  	    } 
	  	    if (rakeoffCount==null) {
	  	    	rakeoffCount="0";
	  	    } 	  	    
	  	    if(incomeretreatCharge==null) {
	  	    	incomeretreatCharge="0";
	  	    }
	  	    
	  	  document.getElementById("commissonCount2").value=commissonCount;
	  	  document.getElementById("rakeoffCount2").value=rakeoffCount;	  	  
	  	 //  alert(order.incomeretreatCharge);
	  	  document.getElementById("incomeretreatCharge2").value=incomeretreatCharge;	  	  
	}
	
	//=============================利润统计==计算==============================================================	
	//多收票价--
	function onkeyOverTicketPrice(){
		//var overTicketPrice=document.getElementById("overTicketPrice1").value;
		var overTicketPrice=document.getElementById("saleOverTicketPrice2").innerHTML;
			
		//alert(overTicketPrice);
		document.getElementById("overTicketPrice2").innerHTML=overTicketPrice;
		
		onkeyCommission();	
		
	}
	
	//卖出（客户）返点
	function onkeyCommissonCount1(){
		var commissonCount=document.getElementById("commissonCount1").value;
		
		onkeyCommission();
	}
	
	//现返
	function onkeyCommission(){
		//alert("onkeyCommission()");
		var totalTicketPrice=document.getElementById("totalTicketPrice1").innerHTML;	
		//alert("onkeyCommission1:ticketprice:"+totalTicketPrice1);
		//var overTicketPrice=document.getElementById("overTicketPrice1").value;	
		var overTicketPrice=document.getElementById("saleOverTicketPrice2").innerHTML;	
		
		
		var commissonCount=document.getElementById("commissonCount1").value;	
		
		var mulNum1=1*totalTicketPrice+1*overTicketPrice
		var commission=accMul(mulNum1,commissonCount);
		//alert(commission);
		commission=ForDight(commission,0);
		
		document.getElementById("commission1").innerHTML=commission;	
		document.getElementById("commission2").innerHTML=commission;	
		
		onkeytotalProfit();		
		onkeysaleTicketPrice();	
	}	
	
	//后返
	function onkeyRakeOff1(){
		onkeytotalProfit();
	}
	
	//收退票手续费
	function onkeyincomeretreatCharge1(){
		onkeysaleTicketPrice();
		onkeyrefundProfit();
	}
	
	
	//应收票款
	function onkeysaleTicketPrice(){
		//alert("onkeysaleTicketPrice()");
		var totalTicketPrice=document.getElementById("totalTicketPrice2").innerHTML;	
		var overTicketPrice=document.getElementById("overTicketPrice2").innerHTML;	
		var commission=document.getElementById("commission1").innerHTML;
		var overAirportfulePrice=document.getElementById("overAirportfulePrice1").value;
		var incomeretreatCharge=document.getElementById("incomeretreatCharge1").value;
	
		//alert(totalTicketPrice+"-"+overTicketPrice+"-"+commission+"-"+overAirportfulePrice+"-"+incomeretreatCharge);
	
		var saleTicketPrice=(1*totalTicketPrice + 1*overTicketPrice)-1*commission+1*overAirportfulePrice-1*incomeretreatCharge;
		//alert(saleTicketPrice);
		saleTicketPrice=ForDight(saleTicketPrice,0);
		document.getElementById("saleTicketPrice").innerHTML=saleTicketPrice;	
		document.getElementById("saleTicketPrice2").innerHTML=saleTicketPrice;	
		
		onkeysaleTotalAmount();
	}
	
	//实收票款
	function onkeysaleTotalAmount(){
		var saleTicketPrice=document.getElementById("saleTicketPrice2").innerHTML;
		var totalAirportFuelPrice=document.getElementById("totalAirportFuelPrice1").innerHTML;
		var saleTotalAmount=1*saleTicketPrice+1*totalAirportFuelPrice;
		
		saleTotalAmount=ForDight(saleTotalAmount,0);
		document.getElementById("saleTotalAmount").innerHTML=saleTotalAmount;	
		document.getElementsByName("saleTotalAmount2").value=saleTotalAmount;			
						
	}
	
		//买入（航空公司）现返点数
	function onkeyCommissonCount2(){
		var commissonCount=document.getElementById("commissonCount2").value;
		
		onkeygrossProfit();
	}
	
	
	//买入 手续费
	function onkeyhandlingCharge1(){
		var handlingCharge=document.getElementById("handlingCharge1").value;
		
		onkeygrossProfit();
	}
	
	
	
	//团毛利润
	function onkeygrossProfit(){
		var totalTicketPrice=document.getElementById("totalTicketPrice3").innerHTML;		
		var commissonCount=document.getElementById("commissonCount2").value;
		var handlingCharge=document.getElementById("handlingCharge1").value;
			
		var mulNum1=1*totalTicketPrice;
		var mulNum2=1*commissonCount;
		var mulNum3=accMul(mulNum1,mulNum2);
		mulNum3=ForDight(mulNum3,2);
		var grossProfit=mulNum3-1*handlingCharge;//mulNum1*mulNum2-handlingCharge
		//alert("grossProfit:"+grossProfit);	
		grossProfit=ForDight(grossProfit,2);
		document.getElementById("grossProfit1").innerHTML=grossProfit;	
		document.getElementById("grossProfit2").innerHTML=grossProfit;	
		
		onkeyBuyTicketPrice();	
		onkeytotalProfit();	
	}
	
	//买入（航空公司）后返点数
	function onkeyrakeoffCount2(){
		var totalTicketPrice=document.getElementById("totalTicketPrice4").innerHTML;		
		var rakeoffCount=document.getElementById("rakeoffCount2").value;
		//alert(totalTicketPrice+"--"+rakeoffCount);
		
		var mulNum1=1*totalTicketPrice;
		var mulNum2=1*rakeoffCount;
		var rakeOff=accMul(mulNum1,mulNum2);//mulNum1*mulNum2
		rakeOff=ForDight(rakeOff,2);
		document.getElementById("rakeOff2").innerHTML=rakeOff;
	}
	
	//付退票手续费
	function onkeyincomeretreatCharge2(){		
		onkeyBuyTicketPrice();
		onkeyrefundProfit();
	}
	
	//应付票款
	function onkeyBuyTicketPrice(){
		var totalTicketPrice=document.getElementById("totalTicketPrice5").innerHTML;	
		var grossProfit=document.getElementById("grossProfit2").innerHTML;
			
		var incomeretreatCharge=document.getElementById("incomeretreatCharge2").value;
		
		var buyTicketPrice=1*totalTicketPrice-1*grossProfit-1*incomeretreatCharge;
		
		buyTicketPrice=ForDight(buyTicketPrice,2);
		document.getElementById("buyTicketPrice1").innerHTML=buyTicketPrice;
		document.getElementById("buyTicketPrice2").innerHTML=buyTicketPrice;	
		
		onkeybuyTotalAmount2();	
	}
	
	//实付票款
	function onkeybuyTotalAmount2(){
		var buyTicketPrice=document.getElementById("buyTicketPrice2").innerHTML;
		var totalAirportFuelPrice=document.getElementById("totalAirportFuelPrice2").innerHTML;
		
		var buyTotalAmount=1*buyTicketPrice+1*totalAirportFuelPrice;
		
		buyTotalAmount=ForDight(buyTotalAmount,2);
		document.getElementById("buyTotalAmount2").innerHTML=buyTotalAmount;
	}
	
	//退票利润
	function onkeyrefundProfit(){
		var incomeretreatCharge1=document.getElementById("incomeretreatCharge1").value;		
		var incomeretreatCharge2=document.getElementById("incomeretreatCharge2").value;	
		
		var refundProfit=Subtr(1*incomeretreatCharge1,1*incomeretreatCharge2);
		document.getElementById("refundProfit").innerHTML=refundProfit;
		
		onkeytotalProfit();
	}
	
	//净利合计
	function onkeytotalProfit(){
		var grossProfit=document.getElementById("grossProfit1").innerHTML;	
		var refundProfit=document.getElementById("refundProfit").innerHTML;	
		//var overTicketPrice=document.getElementById("overTicketPrice1").value;	
		var overTicketPrice=document.getElementById("saleOverTicketPrice2").innerHTML;	
		
		var overAirportfulePrice=document.getElementById("overAirportfulePrice1").value;	
		var commission=document.getElementById("commission1").innerHTML;	
		var rakeOff=document.getElementById("rakeOff1").value;		
		
		
		
		var totalProfit=1*grossProfit+1*refundProfit+1*overTicketPrice+1*overAirportfulePrice-1*commission-1*rakeOff;			
		//团毛利润+退票利润+多收票款+多收税款-应付出团代理费(现返)-应付出团代理费(未返)
		//alert(totalProfit);
		totalProfit=ForDight(totalProfit,2);
		document.getElementById("totalProfit").innerHTML=totalProfit;
	}		