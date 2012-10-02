<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%
	String path = request.getContextPath();
 %>
<html xmlns="http://www.w3.org/1999/xhtml">

	<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
	<link href="../_css/global.css" rel="stylesheet" type="text/css" />
   	<script type='text/javascript' src='<%=path %>/dwr/interface/platComAccountStore.js'></script>
   	<script type='text/javascript' src='<%=path %>/dwr/interface/passengerBiz.js'></script>
   	<script type='text/javascript' src='<%=path %>/dwr/interface/tempPNRBizImp.js'></script>
   	<script type='text/javascript' src='<%=path %>/dwr/interface/parseBlackUtil.js'></script>
   	<script type='text/javascript' src='<%=path %>/dwr/interface/airticketOrderBiz.js'></script>
 	<script type='text/javascript' src='<%=path %>/dwr/engine.js'></script>
	<script type='text/javascript' src='<%=path %>/dwr/util.js'></script>
	<link type="text/css" href="../_js/development-bundle/themes/base/ui.all.css" rel="stylesheet" />
	<script type="text/javascript" src="../_js/development-bundle/jquery-1.3.2.js"></script>
	<script type="text/javascript" src="../_js/development-bundle/ui/ui.core.js"></script>
	<script type="text/javascript" src="../_js/development-bundle/ui/ui.draggable.js"></script>
	<script type="text/javascript" src="../_js/development-bundle/ui/ui.resizable.js"></script>
	<script type="text/javascript" src="../_js/development-bundle/ui/ui.dialog.js"></script>
	<script type="text/javascript" src="../_js/development-bundle/ui/effects.core.js"></script>
	<script type="text/javascript" src="../_js/development-bundle/ui/effects.highlight.js"></script>
	<script type="text/javascript" src="../_js/loadAccount.js"></script>
	<script src="../_js/calendar/WdatePicker.js" type="text/javascript"></script>
<!--  	<style type="text/css">
		body { font-size: 62.5%; }
		label, input { display:block; }
		input.text { margin-bottom:12px; width:95%; padding: .4em; }
		fieldset { padding:0; border:0; margin-top:25px; }
		h1 { font-size: 1.2em; margin: .6em 0; }
		div#users-contain {  width: 350px; margin: 20px 0; }
		div#users-contain table { margin: 1em 0; border-collapse: collapse; width: 100%; }
		div#users-contain table td, div#users-contain table th { border: 1px solid #eee; padding: .6em 10px; text-align: left; }
		.ui-button { outline: 0; margin:0; padding: .4em 1em .5em; text-decoration:none;  !important; cursor:pointer; position: relative; text-align: center; }
		.ui-dialog .ui-state-highlight, .ui-dialog .ui-state-error { padding: .3em;  }
	</style>-->
	
	<script type="text/javascript">
	$(function() {
		
	/*	var pnr = $("#pnr"),
			airOrderNo = $("#airOrderNo"),
			totalAmount = $("#totalAmount"),
			rebate = $("#totalAmount"),
			allFields = $([]).add(airOrderNo).add(totalAmount).add(rebate).add(pnr),
			tips = $("#validateTips");

		function updateTips(t) {
			tips.text(t);
		}

		function checkLength(o,n,min,max) {

			if ( o.val().length > max || o.val().length < min ) {
				o.addClass('ui-state-error');
				updateTips("Length of " + n + " must be between "+min+" and "+max+".");
				return false;
			} else {
				return true;
			}

		}

		function checkRegexp(o,regexp,n) {

			if ( !( regexp.test( o.val() ) ) ) {
				o.addClass('ui-state-error');
				updateTips(n);
				return false;
			} else {
				return true;
			}

		}*/
		
		$("#dialog").dialog({
			bgiframe: true,
			autoOpen: false,
			//height: 500,
			//width:300,
			modal: true
		/*	buttons: {
				'确 定': function() {
					var bValid = true;
					allFields.removeClass('ui-state-error');

				//	bValid = bValid && checkLength(pnr,"username",3,16);
				//	bValid = bValid && checkLength(email,"email",6,80);
				//	bValid = bValid && checkLength(password,"password",5,16);

					bValid = bValid && checkRegexp(pnr,/^[a-z]([0-9a-z_])+$/i,"请正确填写PNR!");
					bValid = bValid && checkRegexp(airOrderNo,/^([0-9a-zA-Z])+$/,"请正确填写订单号!");
					bValid = bValid && checkRegexp(totalAmount,/^([0-9])+$/,"请正确填写金额!");
					bValid = bValid && checkRegexp(rebate,/^([0-9])+$/,"请正确填写订政策!");
					if (bValid) {
						$("#form1").submit();
						$(this).dialog('close');
						
					}
				},
				'取 消': function() {
					$(this).dialog('close');
				}
			},
			close: function() {
				allFields.val('').removeClass('ui-state-error');
			}*/
		});
		
		
		
	///	$('#create-user').click(function() {
	//		$('#dialog').dialog('open');
	////	})
		
		
		$("#dialog2").dialog({
			bgiframe: true,
			autoOpen: false,
			height: 500,
			width:450,
			modal: true
			
		});
	  	$("#dialog3").dialog({
			bgiframe: true,
			autoOpen: false,
			height: 500,
			width:450,
			modal: true
			
		});	
		$("#dialog4").dialog({
			bgiframe: true,
			autoOpen: false,
			//height: 500,
			//width:450,
			modal: true
			
		});	
       	$("#dialog5").dialog({
			bgiframe: true,
			autoOpen: false,
			//height: 500,
			//width:450,
			modal: true
			
		});	
		
	 	$("#dialog6").dialog({
			bgiframe: true,
			autoOpen: false,
			//height: 500,
			//width:450,
			modal: true
			
		});	
		$("#dialog7").dialog({
			bgiframe: true,
			autoOpen: false,
			//height: 500,
			//width:450,
			modal: true
			
		});
		$("#dialog8").dialog({
			bgiframe: true,
			autoOpen: false,
			//height: 500,
			//width:450,
			modal: true
			
		});	
		$("#dialog9").dialog({
			bgiframe: true,
			autoOpen: false,
			//height: 500,
			//width:450,
			modal: true
			
		});	
		 $("#dialog10").dialog({
			bgiframe: true,
			autoOpen: false,
			height: 500,
			width:450,
			modal: true
			
		});	
		 $("#dialog11").dialog({
			bgiframe: true,
			autoOpen: false,
			height: 500,
			width:450,
			modal: true
			
		});
		
		$("#dialog12").dialog({
			bgiframe: true,
			autoOpen: false,
			height: 500,
			width:450,
			modal: true
			
		});	
		$("#dialog13").dialog({
			bgiframe: true,
			autoOpen: false,
			height: 500,
			width:450,
			modal: true
			
		});	
	   $("#dialog14").dialog({
			bgiframe: true,
			autoOpen: false,
			//height: 500,
			//width:450,
			modal: true
			
		});	
							
	});
	
	//申请支付
	function showDiv9(oId,suPnr,airOrderNo,totalAmount,rebate){
	 
	  $('#oId9').val(oId);
	  $('#pnr9').val(suPnr);
	  $('#airOrderNo9').val();
	  $('#tmpTotalAmount9').val(totalAmount);
	  $('#totalAmount9').val(0);
	  $('#rebate9').val(0);
	  $('#liruen9').val(0);
	  $('#dialog9').dialog('open');
	 
	       	//设置下拉框  平台初始值 默认选中
	    var tmpPlatformValue=$("#tmpPlatformId9"+oId).val();
	    var tmpCompanyValue=$("#tmpCompanyId9"+oId).val();  	
	     var tmpAccountValue=$("#tmpAccountId9"+oId).val(); 
	     if(tmpPlatformValue!=null&&tmpPlatformValue!=""){	
	     
	     loadPlatListSelected('platform_Id9','company_Id9','account_Id9',tmpPlatformValue,tmpCompanyValue,tmpAccountValue);
	     }else{
	     
	      loadPlatList('platform_Id9','company_Id9','account_Id9');
	     }
	}
	//申请支付 验证
	function submitForm9(){
	
	 var re=/^([1-9][0-9]*|0)(\.[0-9]{0,3})?$/;
	var totalAmount=  $('#totalAmount9').val();
	var rebate=  $('#rebate9').val();
	totalAmount=  $('#totalAmount9').val($.trim(totalAmount));
	rebate=  $('#rebate9').val($.trim(rebate));
	
	 if(!re.test(totalAmount.val())||totalAmount.val()==""){
           alert("请正确填写金额！");
           
      }else if(!re.test(rebate.val())||rebate.val()==""){
           alert("请正确填写政策！");
       }else{
	  $('#form9').submit();
	}
	  
  }
	

	
	//确认支付
	function showDiv(oId,suPnr,airOrderNo,totalAmount,rebate){
	  $('#oId').val(oId);
	  $('#pnr1').val(suPnr);
	  $('#airOrderNo1').val(airOrderNo);
	  $('#totalAmount1').val(totalAmount);
	  $('#totalAmount2').val(totalAmount);
	  $('#tmpTotalAmount1').val(totalAmount);
	  $('#rebate1').val(rebate);
	  calculationLiren('tmpTotalAmount1','totalAmount1','liren1');
	   $('#dialog').dialog('open');
	   
	   	//设置下拉框  平台初始值 默认选中
    	var tmpPlatformValue=$("#tmpPlatformId"+oId).val();
    	var tmpCompanyValue=$("#tmpCompanyId"+oId).val();
    	var tmpAccountValue=$("#tmpAccountId"+oId).val();
    	if(tmpPlatformValue!=""){
    	 if(tmpPlatformValue!=null&&tmpPlatformValue!=""){	
	     
	     loadPlatListSelected('platform_Id','company_Id','account_Id',tmpPlatformValue,tmpCompanyValue,tmpAccountValue);
	     }else{
	     loadPlatList('platform_Id','company_Id','account_Id');	
	     }
	  
	 }
	}


   
    function showDiv2(oId,suPnr,groupMarkNo){
	 
	 
	 passengerBiz.listByairticketOrderId(oId,function(list){
	 $('#per tbody').html("");
	 $('#per tbody').append('<tr><td width="200">乘客姓名</td>  <td width="200" style="display: none;">证件号</td>  <td width="200">票号</td></tr>');
	 for(var i=0;i<list.length;i++){
	    $('#per tbody').append('<tr>' +
							'<td>' + list[i].name + '</td>' + 
							'<td style="display: none;" >' + list[i].cardno + '</td>' + 
							'<td><input type="text" name="ticketNumber"  value="' + list[i].ticketNumber + '"  class="text ui-widget-content ui-corner-all" /></td>' +
							'<input type="hidden" name="pId"  value="' + list[i].id + '" />'+
							'</tr>'); 
	 }
	   
	 
	 });
	  $('#oId2').val(oId);
	  $('#pnr2').val(suPnr);
	   $('#groupMarkNo2').val(groupMarkNo);
	  $('#dialog2').dialog('open');
	 
	}
	//自动刷新
	function getTempPNR(){
	var pnr2=$("#pnr2").val();  
	if(pnr2!=null){ 
		tempPNRBizImp.getTempPNRByPnr(pnr2,function(list){
		//alert(list.tempPassengerList.length);
		if(list.tempPassengerList!=null&&list.tempTicketsList!=null){
		var passList=list.tempPassengerList;
		var ticketsList=list.tempTicketsList;
		 $('#per tbody').html("");
		 $('#per tbody').append('<tr><td width="200">乘客姓名</td>  <td width="200" style="display: none;">证件号</td>  <td width="200">票号</td></tr>');
		 for(var i=0;i<passList.length;i++){
		    $('#per tbody').append('<tr>' +
								'<td>' + passList[i].name + '</td>' + 
								'<td style="display: none;">' + passList[i].ni + '</td>' + 
								'<td><input type="text" name="ticketNumber"  value="' + ticketsList[i] + '"  class="text ui-widget-content ui-corner-all" /></td>' +
								'<input type="hidden" name="pId"  value="' + passList[i].name + '" />'+
								'</tr>'); 
		 }
		}else{
		   alert("链接超时！");
		}  
		 
		 });
	 }
	}
	//show刷新黑屏
 function showDiv10(){

	 
	  $('#dialog10').dialog('open');
	 
	}	
	
	//黑屏刷新
	function getTempBlackPNR(){
	 $("#dialog10").dialog('close');
	 var memo10=$("#memo10").val();
	 if(memo10!=null){  
			parseBlackUtil.getTempPNRByBlack(memo10,function(list){
			//alert(list.tempPassengerList.length);
			var passList=list.tempPassengerList;
			var ticketsList=list.tempTicketsList;
			 $('#per tbody').html("");
			 $('#per tbody').append('<tr><td width="200">乘客姓名</td>  <td width="200" style="display: none;">证件号</td>  <td width="200">票号</td></tr>');
			 for(var i=0;i<passList.length;i++){
			    $('#per tbody').append('<tr>' +
									'<td>' + passList[i].name + '</td>' + 
									'<td style="display: none;">' + passList[i].ni + '</td>' + 
									'<td><input type="text" name="ticketNumber"  value="' + ticketsList[i] + '"  class="text ui-widget-content ui-corner-all" /></td>' +
									'<input type="hidden" name="pId"  value="' + passList[i].name + '" />'+
									'</tr>'); 
			 }
			   
			 
			 });
	}
  }	
	
 //取消出票
 function showDiv8(oId,tranType,groupMarkNo){

	  $('#oId8').val(oId);
	  $('#tranType8').val(tranType);
	  $('#groupMarkNo8').val(groupMarkNo);
	  $('#dialog8').dialog('open');
	 
	}	
 //审核
 function showDiv3(oId,tranType,groupMarkNo){

	  $('#oId3').val(oId);
	  $('#tranType3').val(tranType);
	  $('#groupMarkNo3').val(groupMarkNo);
	  $('#dialog3').dialog('open');
	//var  TmptotalAmount3=$('#TmptotalAmount3');
	 airticketOrderBiz.getAirticketOrderByGroupMarkNor(groupMarkNo,2,function(ao){
	    var ta= ao.statement.totalAmount;
	   if(ta!=null){
	    $('#TmptotalAmount3').val(ta);
	    $('#passengersCount3').val(ao.passengersCount);
	    //alert(ta);
	    }
	 });
	}

  	 //审核 验证 showDiv3
	function submitForm3(){
	
	var totalAmount=  $('#totalAmount3').val();
	totalAmount=  $('#totalAmount3').val($.trim(totalAmount));
	
	var handlingCharge=  $('#handlingCharge3').val();
	handlingCharge=  $('#handlingCharge3').val($.trim(handlingCharge));

	$('#form3').submit();
	  
	}

 //审核
 function showDiv7(oId,tranType,groupMarkNo){

	  $('#oId7').val(oId);
	  $('#tranType7').val(tranType);
	  $('#groupMarkNo7').val(groupMarkNo);
	  $('#dialog7').dialog('open');
	 airticketOrderBiz.getAirticketOrderByGroupMarkNor(groupMarkNo,2,function(ao){
	    var ta= ao.statement.totalAmount;
	   if(ta!=null){
	    //alert(ta);
	    $('#TmptotalAmount7').val(ta);
	    $('#passengersCount7').val(ao.passengersCount);
	    }
	 });
	}
	

   //审核 验证 showDiv7
	function submitForm7(){
	
	var totalAmount=  $('#totalAmount7').val();
	totalAmount=  $('#totalAmount7').val($.trim(totalAmount));
	
	var handlingCharge=  $('#handlingCharge7').val();
	handlingCharge=  $('#handlingCharge7').val($.trim(handlingCharge));

	$('#form7').submit();
	  
	}
	
	 //审核(外部)
 function showDiv13(oId,tranType,groupMarkNo){

	  $('#oId13').val(oId);
	  $('#tranType13').val(tranType);
	  $('#groupMarkNo13').val(groupMarkNo);
	  $('#dialog13').dialog('open');

	}
	 //审核(外部)
 function showDiv12(oId,tranType,groupMarkNo){

	  $('#oId12').val(oId);
	  $('#tranType12').val(tranType);
	  $('#groupMarkNo12').val(groupMarkNo);
	  $('#dialog12').dialog('open');
	
	    //设置下拉框  平台初始值 默认选中
	    var tmpPlatformValue=$("#tmpPlatformId12"+oId).val();
	    var tmpCompanyValue=$("#tmpCompanyId12"+oId).val();  	
	     var tmpAccountValue=$("#tmpAccountId12"+oId).val(); 
	     if(tmpPlatformValue!=null&&tmpPlatformValue!=""){	
	     
	     loadPlatListSelected('platform_Id12','company_Id12','account_Id12',tmpPlatformValue,tmpCompanyValue,tmpAccountValue);
	     }else{
	     
	      loadPlatList('platform_Id12','company_Id12','account_Id12');
	     }
	   
	   
	//var  TmptotalAmount3=$('#TmptotalAmount3');
	/* airticketOrderBiz.getAirticketOrderByGroupMarkNor(groupMarkNo,2,function(ao){
	    var ta= ao.statement.totalAmount;
	   if(ta!=null){
	    $('#TmptotalAmount3').val(ta);
	    //alert(ta);
	    }
	 });*/
	}

//确认收款
 function showDiv4(oId,suPnr,groupMarkNo){
      var actualAmountTemp4=$('#actualAmountTemp4'+oId).val();
       
      if(actualAmountTemp4!=null){
        $('#actualAmount4').val(actualAmountTemp4);
      }
	  $('#oId4').val(oId);
	  $('#tranType4').val(suPnr);
	  $('#groupMarkNo').val(groupMarkNo);
	  $('#dialog4').dialog('open');
	 
	}
	
	//验证  showDiv4	
	function submitForm4(){
	
	  var re=/^([1-9][0-9]*|0)(\.[0-9]{0,3})?$/;
	   var actualAmount= $('#actualAmount4').val();
       $('#actualAmount4').val($.trim(actualAmount));
       var optTime=$('#optTime4').val();
       
       if(optTime==null||optTime==''){
         alert("请选择日期！");
         
       }else if(!re.test(actualAmount)||actualAmount==""){
           alert("请正确填写金额！");
       }else{
        $('#form4').submit();  
       }
	   
	}
	
//申请改签
 function showDiv5(oId,suPnr,groupMarkNo){

	  $('#oId5').val(oId);
	  $('#tranType5').val(suPnr);
	  $('#groupMarkNo5').val(groupMarkNo);
	  $('#dialog5').dialog('open');
	  var TmpFromPCAccount5=$('#TmpFromPCAccount5').val();
	  
	  
	  var platformId5= document.getElementById('platformId5');
	  platformId5.options.length=0;
	  option = new Option(TmpFromPCAccount5,'');
	  platformId5.options.add(option);
	   
	   
/*	 platComAccountStore.getPlatFormList(function(data){
			   		for(var i=0;i<data.length;i++)
			   		{		
			   			
			   			document.all.platformId2.options[i] = new Option(data[i].name,data[i].id);
			   			
			   		}
			   		
			   });*/
	 
	}
	
	//申请改签(外部)
 function showDiv14(oId,suPnr,groupMarkNo){

	  $('#oId14').val(oId);
	  $('#tranType14').val(suPnr);
	  $('#groupMarkNo14').val(groupMarkNo);
	  $('#dialog14').dialog('open');
       //  loadPlatList('platform_Id14','company_Id14','account_Id14');
	    //设置下拉框  平台初始值 默认选中
	    var tmpPlatformValue=$("#tmpPlatformId14"+oId).val();
	    var tmpCompanyValue=$("#tmpCompanyId14"+oId).val();  	
	     var tmpAccountValue=$("#tmpAccountId14"+oId).val(); 
	     if(tmpPlatformValue!=null&&tmpPlatformValue!=""){	
	     
	     loadPlatListSelected('platform_Id14','company_Id14','account_Id14',tmpPlatformValue,tmpCompanyValue,tmpAccountValue);
	     }else{
	     
	      loadPlatList('platform_Id14','company_Id14','account_Id14');
	     }
	 
	}
	
	//申请改签
 function showDiv6(oId,suPnr,groupMarkNo){

	  $('#oId6').val(oId);
	  $('#tranType6').val(suPnr);
	  $('#groupMarkNo6').val(groupMarkNo);
	  $('#dialog6').dialog('open');
	 
	 var platformName=$('#platformName'+oId).val();
	 var companyName=$('#companyName'+oId).val();
	 var accountName=$('#accountName'+oId).val();
	 
	  var platformId= document.getElementById('platformId6');
	  platformId.options.length=0;
	  option = new Option(platformName,'');
	  platformId.options.add(option);
	  
	  var companyId= document.getElementById('companyId6');
	  companyId.options.length=0;
	  option2 = new Option(companyName,'');
	  companyId.options.add(option2);
	  
	  var accountId= document.getElementById('accountId6');
	  accountId.options.length=0;
	  option3 = new Option(accountName,'');
	  accountId.options.add(option3);
	 
	 }
	
		
	//设置退票原因		
	function submitForm8(){
	   
	    var  rbtnReason= $("input[name='rbtnReason']:checked").val();
	    var  rbtnType= $("input[name='rbtnType']:checked").val();
        var  cause=$("#cause").val(); 
        $("input[name='memo']").val(rbtnType+","+rbtnReason+","+cause);
	    return true;
	}
	//利润计算
	function calculationLiren(tmpTotalAmount,totalAmount,liren){
	 
	var tmpTa=$("#"+tmpTotalAmount).val();
	var ta=$("#"+totalAmount).val();
	var lr=$("#"+liren);
	 if(tmpTa!=null&&ta!=null){
	 
	   var count=tmpTa-ta;
	   count= count.toString().replace(/^(\d+\.\d{2})\d*$/,"$1");
	   lr.val(count);
	 }
	
	}
	
	
	//备注
	function showDiv11(oId){
	  
	  $('#oId11').val(oId);
	  var  memo=$('#memo'+oId).val()
	  $('#memo11').val(memo);
	  $('#dialog11').dialog('open');
	 
	}
	
	//客规	
	function selTuiPercent_onchange() {
	
	    var oIdValue=$('#oId3').val();
	    var ticketPrice=$('#ticketPrice'+oIdValue).val();//票面价
	    var handlingCharge=$('#handlingCharge3');//手续费
	    var selTuiPercent=$('#selTuiPercent3').val();//客规
	    var passengerNum=$('#passengersCount3').val();//人数
	   // alert(passengerNum);
	    if(passengerNum==null){
	      passengerNum=1;
	    }
	   if(ticketPrice!=null){ 
	    // alert(ticketPrice+'---'+selTuiPercent);
	    var tgqFee = ticketPrice * selTuiPercent / 100;
	   
	     tgqFee = tgqFee * Math.abs(passengerNum);
	     
	     handlingCharge.val(tgqFee);
	  
	  
	  var TmptotalAmount=$('#TmptotalAmount3').val();
      var totalTicketPrice=$('#totalAmount3');//
    if (TmptotalAmount * 1 > 0) {
        var receiveAmount = TmptotalAmount - tgqFee;
        // alert(TmptotalAmount+'---'+tgqFee);
        
        var totalTicketPriceTemp= Math.round(receiveAmount * 100) / 100;
        totalTicketPriceTemp= totalTicketPriceTemp.toString().replace(/^(\d+\.\d{2})\d*$/,"$1");//保留2位小数
         totalTicketPrice.val(totalTicketPriceTemp);
       // totalTicketPrice.val(Math.round(receiveAmount * 100) / 100);
    } 
    }
	}	
	
		//客规	
	function selTuiPercent_onchange2() {
	
	    var oIdValue=$('#oId7').val();
	    var ticketPrice=$('#ticketPrice'+oIdValue).val();//票面价
	    var handlingCharge=$('#handlingCharge7');//手续费
	    var selTuiPercent=$('#selTuiPercent7').val();//客规
	    var passengerNum=$('#passengersCount7').val();//人数
	    if(passengerNum==null){
	      passengerNum=1;
	    }
	   if(ticketPrice!=null){ 
	    // alert(ticketPrice+'---'+selTuiPercent);
	    var tgqFee = ticketPrice * selTuiPercent / 100;
	   
	     tgqFee = tgqFee * Math.abs(passengerNum);
	     
	     handlingCharge.val(tgqFee);
	     
	  var TmptotalAmount=$('#TmptotalAmount7').val();
      var totalTicketPrice=$('#totalAmount7');
    if (TmptotalAmount * 1 > 0) {
        var receiveAmount = TmptotalAmount - tgqFee;
        // alert(TmptotalAmount+'---'+tgqFee);
       // totalTicketPrice.val(Math.round(receiveAmount * 100) / 100);
       
        var totalTicketPriceTemp= Math.round(receiveAmount * 100) / 100;
        totalTicketPriceTemp= totalTicketPriceTemp.toString().replace(/^(\d+\.\d{2})\d*$/,"$1");//保留2位小数
        totalTicketPrice.val(totalTicketPriceTemp);
    } 
   }
	}	
	</script>
		<head>
	<body>
		<div id="mainContainer">
			<div id="container">
				<html:form action="/airticket/listAirTicketOrder.do?thisAction=tradingOrderProcessing">
					<html:hidden property="thisAction" />
					<html:hidden property="lastAction" />
					<html:hidden property="intPage" />
					<html:hidden property="pageCount" />
					<input type="hidden" name="locate"
						value="<c:out value="${locate}"></c:out>" />

					<table width="100%" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td width="10" height="10" class="tblt"></td>
							<td height="10" class="tbtt"></td>
							<td width="10" height="10" class="tbrt"></td>
						</tr>
						<tr>
							<td width="10" class="tbll"></td>
							<td valign="top" class="body">
								<c:import url="../_jsp/mainTitle.jsp?title1=票务管理&title2=关联订单"
									charEncoding="UTF-8" />
								<br />
								<table width="100%" cellpadding="0" cellspacing="0" border="0"
									class="dataList">
									<tr>
										<th>
											<div>
												承运人
											</div>
										</th>
										<th>
											<div>
												航班号
											</div>
										</th>
										<th>
											<div>
												行程
											</div>
										</th>
										
										<th>
											<div>
												乘客
											</div>
										</th>
										<th>
											<div>
												票号
											</div>
										</th>
										<th>
											<div>
												票面价
											</div>
										</th>
										<th>
											<div>
												机建
											</div>
										</th>
										<th>
											<div>
												燃油
											</div>
										</th>
										
										<th>
											<div>
												平台
											</div>
										</th>
										<th>
											<div>
												预定PNR
											</div>
										</th>
										<th>
											<div>
												出票PNR
											</div>
										</th>
										<th>
											<div>
												大PNR
											</div>
										</th>
										<th>
											<div>
												政策
											</div>
										</th>
										<th>
											<div>
												交易金额
											</div>
										</th>
										<th>
											<div>
												交易类型
											</div>
										</th>
										<th>
											<div>
												订单状态
											</div>
										</th>
										<th>
											<div>
													操作人
											</div>
										</th>
										<th>
											<div>
													支付人
											</div>
										</th>
										<th colspan="2">
											<div>
												操作
											</div>
										</th>
									</tr>
                        		<c:forEach var="info" items="${ulf.list}" varStatus="status">
									<tr>
										<td>
                                         <c:forEach var="flight1" items="${info.flights}">
                                             <c:out value="${flight1.cyr}" /></br>
                                         </c:forEach>
										</td>
										<td>
										<c:forEach var="flight2" items="${info.flights}">
                                             <c:out value="${flight2.flightCode}" /></br>
                                         </c:forEach>
										</td>
										<td>
                                      <c:forEach var="flight3" items="${info.flights}">
                                             <c:out value="${flight3.startPoint}" />-
                                             <c:out value="${flight3.endPoint}" /></br>
                                         </c:forEach>
										</td>
										
										<td>
										 <c:forEach var="passenger1" items="${info.passengers}">
                                             <c:out value="${passenger1.name}" /></br>
                                         </c:forEach>
										</td>
										<td>
										 <c:forEach var="passenger2" items="${info.passengers}">
                                             <c:out value="${passenger2.ticketNumber}" /></br>
                                         </c:forEach>
										</td>
										<td>
										 <c:out value="${info.ticketPrice}" />
										</td>
										<td>
										 <c:out value="${info.airportPrice}" />
										</td>
										<td>
										  <c:out value="${info.fuelPrice}" />
										</td>
									
										<td>
										    <c:if test="${!empty info.statement.fromPCAccount}">
										    <c:out value="${info.statement.fromPCAccount.platform.name}" />
										    </c:if>
										    
										    <c:if test="${!empty info.statement.toPCAccount}">
										    <c:out value="${info.statement.toPCAccount.platform.name}" />
										    </c:if>
										</td>
										<td>
											<c:out value="${info.subPnr}" />
										</td>
										<td>
											 <c:out value="${info.drawPnr}" />
										</td>
										<td>
									    <c:out value="${info.bigPnr}" />
										</td>
										<td>
										  <c:out value="${info.rebate}" />
										</td>
										<td>
											 <c:out value="${info.statement.totalAmount}" />
										</td>
										<td>
											<c:out value="${info.tranTypeText}" />
										</td>
										<td>
											 <c:out value="${info.statusText}" />
										</td>
									<td>
											 <c:out value="${info.statement.sysUser.userName}" />
										</td>
										<td>
											  <c:out value="${info.currentOperator}" />
										</td>
										
								<td>
										
								<c:if test="${ info.tranType==2 &&info.status==1}">
								     <a   onclick="showDiv8('<c:out value='${info.id}' />','<c:out value='${info.subPnr}'/>')"  href="#">                    
		                        [取消出票]</a>
		                        <br>
								   <td>
								   <a   onclick="showDiv9('<c:out value='${info.id}' />','<c:out value='${info.subPnr}'/>','<c:out value='${info.airOrderNo}'/>','<c:out value='${info.statement.totalAmount}'/>','<c:out value='${info.rebate}'/>')"  href="#">                    
		                        [申请支付	]
	                                  <input id="tmpPlatformId9<c:out value='${info.id}' />" value="<c:out value='${info.statement.platComAccount.platform.id}'/>" type="hidden"/>
	                                  <input id="tmpCompanyId9<c:out value='${info.id}' />" value="<c:out value='${info.statement.platComAccount.company.id}'/>" type="hidden"/>
	                                  <input id="tmpAccountId9<c:out value='${info.id}' />" value="<c:out value='${info.statement.platComAccount.account.id}'/>" type="hidden"/>
		                        </a>
								   </td>
									
								</c:if>		
								
								
								<c:if test="${ info.tranType==2 &&info.status==3}">
								 
							  <a  onclick="showDiv8('<c:out value='${info.id}' />','<c:out value='${info.subPnr}'/>')"  href="#">                    
		                        [取消出票]</a>
		                        <br>
								  <a href="#" onclick="showDiv11('<c:out value='${info.id}' />')"> 
								 <c:if test="${empty info.memo}">[备注]</c:if>
								  <c:if test="${!empty info.memo}"><font color="red">[备注]</font></c:if>            
		                        </a>
							       <input value="<c:out value='${info.memo}' />" type="hidden" id="memo<c:out value='${info.id}' />"/>  
							
								</c:if>		
								
								<c:if test="${ info.tranType==1 &&info.status==2||info.status==8}">
								
							  <a  onclick="showDiv8('<c:out value='${info.id}' />','<c:out value='${info.subPnr}'/>')"  href="#">                    
		                        [取消出票]</a>
		                        <br>
								   
								<a href="<%=path %>/airticket/airticketOrder.do?thisAction=updateOrderStatus&status=7&id=<c:out value='${info.id}' />">                     
		                        [锁定]</a>
		                        <br>	
								</c:if>	
								
								
									
								<c:if test="${ info.tranType==1 &&info.status==7}">
								     <a   onclick="showDiv8('<c:out value='${info.id}' />','<c:out value='${info.subPnr}'/>')"  href="#">                    
		                        [取消出票]</a>
		                        <br>
		                        <a href="<%=path %>/airticket/airticketOrder.do?thisAction=updateOrderStatus&status=8&id=<c:out value='${info.id}' />">                     
		                        [解锁]</a><br>
								   <td>
								   <a   onclick="showDiv('<c:out value='${info.id}' />','<c:out value='${info.subPnr}'/>','<c:out value='${info.airOrderNo}'/>','<c:out value='${info.statement.totalAmount}'/>','<c:out value='${info.rebate}'/>')"  href="#">                       
		                        [确认支付]  </a>
		                                  <input id="tmpPlatformId<c:out value='${info.id}' />" value="<c:out value='${info.statement.platComAccount.platform.id}'/>" type="hidden"/>
		                                  <input id="tmpCompanyId<c:out value='${info.id}' />" value="<c:out value='${info.statement.platComAccount.company.id}'/>" type="hidden"/>
		                                  <input id="tmpAccountId<c:out value='${info.id}' />" value="<c:out value='${info.statement.platComAccount.account.id}'/>" type="hidden"/>
								   </td>
								</c:if>	
								
								<c:if test="${ info.tranType==2 &&info.status==4}">
		                      
		                    	<a href="<%=path %>/airticket/airticketOrder.do?thisAction=updateOrderStatus&status=6&id=<c:out value='${info.id}' />"> 
		                    	[确认退款]</a><br/>
								   <td>
								   <a   onclick="showDiv9('<c:out value='${info.id}' />','<c:out value='${info.subPnr}'/>','<c:out value='${info.airOrderNo}'/>','<c:out value='${info.statement.totalAmount}'/>','<c:out value='${info.rebate}'/>')"  href="#">                    
		                        [申请支付	]
	                                  <input id="tmpPlatformId9<c:out value='${info.id}' />" value="<c:out value='${info.statement.platComAccount.platform.id}'/>" type="hidden"/>
	                                  <input id="tmpCompanyId9<c:out value='${info.id}' />" value="<c:out value='${info.statement.platComAccount.company.id}'/>" type="hidden"/>
	                                  <input id="tmpAccountId9<c:out value='${info.id}' />" value="<c:out value='${info.statement.platComAccount.account.id}'/>" type="hidden"/>
		                        </a>
								   </td>
								</c:if>	
									
							  	<c:if test="${info.tranType==1 &&info.status==3}">
		                        <a    href="#" onclick="showDiv2('<c:out value='${info.id}' />','<c:out value='${info.subPnr}'/>','<c:out value='${info.groupMarkNo}'/>')">                    
		                        [出票]</a>
		                        <br>
		                    	   <a   onclick="showDiv8('<c:out value='${info.id}' />','<c:out value='${info.tranType}'/>')"  href="#">   
		                    	[取消出票]</a>
								</c:if>			
								
								
							  	<c:if test="${info.tranType==1 &&info.status==4}">
		                      
		                    	<a href="<%=path %>/airticket/airticketOrder.do?thisAction=updateOrderStatus&status=6&id=<c:out value='${info.id}' />"> 
		                    	[确认退款]</a>
								</c:if>		
									
								
								<c:if test="${info.status==5}">
								 
								  <a href="#" onclick="showDiv11('<c:out value='${info.id}' />')">              
		                        <c:if test="${empty info.memo}">[备注]</c:if>
								<c:if test="${!empty info.memo}"><font color="red">[备注]</font></c:if> 
                                   </a>
							       <input value="<c:out value='${info.memo}' />" type="hidden" id="memo<c:out value='${info.id}' />"/>   
								</c:if>	
								
								 <!--取消出票 已退款，交易结束 -->
								<c:if test="${info.status==6}">
								 
								   <a onclick="showDiv9('<c:out value='${info.id}' />','<c:out value='${info.subPnr}'/>','<c:out value='${info.airOrderNo}'/>','<c:out value='${info.statement.totalAmount}'/>','<c:out value='${info.rebate}'/>')"  href="#">                    
		                        [申请支付	]
	                                  <input id="tmpPlatformId9<c:out value='${info.id}' />" value="<c:out value='${info.statement.platComAccount.platform.id}'/>" type="hidden"/>
	                                  <input id="tmpCompanyId9<c:out value='${info.id}' />" value="<c:out value='${info.statement.platComAccount.company.id}'/>" type="hidden"/>
	                                  <input id="tmpAccountId9<c:out value='${info.id}' />" value="<c:out value='${info.statement.platComAccount.account.id}'/>" type="hidden"/>
		                        </a>
								  <br/>
								 <td>
								 <a href="#" onclick="showDiv11('<c:out value='${info.id}' />')">              
		                           <c:if test="${empty info.memo}">[备注]</c:if>
								  <c:if test="${!empty info.memo}"><font color="red">[备注]</font></c:if> 
		                           </a>
							       <input value="<c:out value='${info.memo}' />" type="hidden" id="memo<c:out value='${info.id}' />"/> 
							        </td>
								</c:if>	
								
								
								<!-- 退票-->
								<c:if test="${info.tranType==3 && info.status==19}">
								
								    <a href="#" onclick="showDiv11('<c:out value='${info.id}' />')">              
		                           <c:if test="${empty info.memo}">[备注]</c:if>
								  <c:if test="${!empty info.memo}"><font color="red">[备注]</font></c:if> 
		                           </a>
							       <input value="<c:out value='${info.memo}' />" type="hidden" id="memo<c:out value='${info.id}' />"/>  
		                        
		                   	   <td>
								   <a   onclick="showDiv3('<c:out value='${info.id}' />','<c:out value='${info.tranType}'/>','<c:out value='${info.groupMarkNo}'/>')"  href="#">                    
		                        [通过申请]</a>
		                        <input type="hidden"  id="ticketPrice<c:out value='${info.id}' />"  value="<c:out value='${info.ticketPrice}' />"/>
		                        <input type="hidden"  id="ticke"  value="<c:out value='${info.statement.totalAmount}' />"/>
		                        <input type="hidden" value="<c:out value='${info.adultCount}' />">
								   </td>
								</c:if>	
								
								
						       <c:if test="${info.tranType==3 && info.status==20}">
								  
								 <a   onclick="showDiv7('<c:out value='${info.id}' />','<c:out value='${info.tranType}'/>','<c:out value='${info.groupMarkNo}'/>')"  href="#">
							    <input type="hidden"  id="ticketPrice<c:out value='${info.id}' />"  value="<c:out value='${info.ticketPrice}' />"/>
		                        <input type="hidden"  id="ticke"  value="<c:out value='${info.statement.totalAmount}' />"/>
		                        <input type="hidden" value="<c:out value='${info.adultCount}' />">                  
		                        [通过申请]</a>
								   
								</c:if>	
								
								<!-- 退票外部-->
								<c:if test="${info.tranType==3 && info.status==24}">
								
								 <a href="#" onclick="showDiv11('<c:out value='${info.id}' />')">              
		                         <c:if test="${empty info.memo}">[备注]</c:if>
								  <c:if test="${!empty info.memo}"><font color="red">[备注]</font></c:if> 
		                        </a>
							       <input value="<c:out value='${info.memo}' />" type="hidden" id="memo<c:out value='${info.id}' />"/>  
		                        
		                   	   <td>
							   <a onclick="showDiv12('<c:out value='${info.id}' />','<c:out value='${info.tranType}'/>','<c:out value='${info.groupMarkNo}'/>')"  href="#">                    
		                        [通过申请]</a>
		                        <input type="hidden"  id="ticketPrice<c:out value='${info.id}' />"  value="<c:out value='${info.ticketPrice}' />"/>
		                        <input type="hidden"  id="ticke"  value="<c:out value='${info.statement.totalAmount}' />"/>
		                        <input type="hidden" value="<c:out value='${info.adultCount}' />">
		                        
		                               <input id="tmpPlatformId12<c:out value='${info.id}' />" value="<c:out value='${info.statement.platComAccount.platform.id}'/>" type="hidden"/>
	                                  <input id="tmpCompanyId12<c:out value='${info.id}' />" value="<c:out value='${info.statement.platComAccount.company.id}'/>" type="hidden"/>
	                                  <input id="tmpAccountId12<c:out value='${info.id}' />" value="<c:out value='${info.statement.platComAccount.account.id}'/>" type="hidden"/>
		                        
								   </td>
								</c:if>	
								
			                    <c:if test="${info.tranType==3 && info.status==25}">
								  
								 <a   onclick="showDiv13('<c:out value='${info.id}' />','<c:out value='${info.tranType}'/>','<c:out value='${info.groupMarkNo}'/>')"  href="#">
							    <input type="hidden"  id="ticketPrice<c:out value='${info.id}' />"  value="<c:out value='${info.ticketPrice}' />"/>
		                        <input type="hidden"  id="ticke"  value="<c:out value='${info.statement.totalAmount}' />"/>
		                        <input type="hidden" value="<c:out value='${info.adultCount}' />">                  
		                        [通过申请]</a>
								   
								</c:if>	
								
								
							<c:if test="${info.tranType==3 && info.status==21 && info.statement.type==1}">
								  
								 <a href="#" onclick="showDiv4('<c:out value='${info.id}' />','<c:out value='${info.tranType}'/>','<c:out value='${info.groupMarkNo}'/>')">   
								 <input id="actualAmountTemp4<c:out value='${info.id}' />" type="hidden" value="<c:out value='${info.statement.totalAmount}'/>"/>
								              
		                        [确认退款]</a>
								   
							</c:if>		
									
								<!-- 废票-->
								<c:if test="${info.tranType==4 && info.status==29}">
								
		                          <a href="#" onclick="showDiv11('<c:out value='${info.id}' />')">              
		                         <c:if test="${empty info.memo}">[备注]</c:if>
								  <c:if test="${!empty info.memo}"><font color="red">[备注]</font></c:if> 
		                        </a>
							       <input value="<c:out value='${info.memo}' />" type="hidden" id="memo<c:out value='${info.id}' />"/>  
		                        <br>
								   <td>
								   <a   onclick="showDiv3('<c:out value='${info.id}' />','<c:out value='${info.tranType}'/>','<c:out value='${info.groupMarkNo}'/>')"  href="#">                    
		                        [通过申请]</a>
		                        <input type="hidden"  id="ticketPrice<c:out value='${info.id}' />"  value="<c:out value='${info.ticketPrice}' />"/>
		                        <input type="hidden"  id="ticke"  value="<c:out value='${info.statement.totalAmount}' />"/>
		                        <input type="hidden" value="<c:out value='${info.adultCount}' />">
								   </td>
									
								</c:if>		
								
							<c:if test="${info.tranType==4 && info.status==30}">
								
								 <a   onclick="showDiv7('<c:out value='${info.id}' />','<c:out value='${info.tranType}'/>','<c:out value='${info.groupMarkNo}'/>')"  href="#">                 
		                        [通过申请]</a>
								<input type="hidden"  id="ticketPrice<c:out value='${info.id}' />"  value="<c:out value='${info.ticketPrice}' />"/>
		                        <input type="hidden"  id="ticke"  value="<c:out value='${info.statement.totalAmount}' />"/>
		                        <input type="hidden" value="<c:out value='${info.adultCount}' />">
								</c:if>		
								
							<c:if test="${info.tranType==4 && info.status==31 && info.statement.type==1}" >
								
						 <a href="#" onclick="showDiv4('<c:out value='${info.id}' />','<c:out value='${info.tranType}'/>','<c:out value='${info.groupMarkNo}'/>')">                                  
		                        [确认退款]</a>
								 <input id="actualAmountTemp4<c:out value='${info.id}' />" type="hidden" value="<c:out value='${info.statement.totalAmount}'/>"/>
								</c:if>	
								
								
								<!-- 废票- 外部 -->
								<c:if test="${info.tranType==4 && info.status==34}">
								
		                          <a href="#" onclick="showDiv11('<c:out value='${info.id}' />')">              
		                          <c:if test="${empty info.memo}">[备注]</c:if>
								  <c:if test="${!empty info.memo}"><font color="red">[备注]</font></c:if> 
		                        </a>
							       <input value="<c:out value='${info.memo}' />" type="hidden" id="memo<c:out value='${info.id}' />"/>  
		                        <br>
								   <td>
								   <a   onclick="showDiv12('<c:out value='${info.id}' />','<c:out value='${info.tranType}'/>','<c:out value='${info.groupMarkNo}'/>')"  href="#">                    
		                        [通过申请]</a>
		                        <input type="hidden"  id="ticketPrice<c:out value='${info.id}' />"  value="<c:out value='${info.ticketPrice}' />"/>
		                        <input type="hidden"  id="ticke"  value="<c:out value='${info.statement.totalAmount}' />"/>
		                        <input type="hidden" value="<c:out value='${info.adultCount}' />">
								   </td>
									
								</c:if>		
								
							<c:if test="${info.tranType==4 && info.status==35}">
								
								 <a   onclick="showDiv13('<c:out value='${info.id}' />','<c:out value='${info.tranType}'/>','<c:out value='${info.groupMarkNo}'/>')"  href="#">                 
		                        [通过申请]</a>
								<input type="hidden"  id="ticketPrice<c:out value='${info.id}' />"  value="<c:out value='${info.ticketPrice}' />"/>
		                        <input type="hidden"  id="ticke"  value="<c:out value='${info.statement.totalAmount}' />"/>
		                        <input type="hidden" value="<c:out value='${info.adultCount}' />">
								</c:if>		
								
								<!-- 确认收款 -->
								
							<c:if test="${info.tranType==3 && info.status==21 && info.statement.type==2}">
		                        <a    href="#" onclick="showDiv4('<c:out value='${info.id}' />','<c:out value='${info.tranType}'/>','<c:out value='${info.groupMarkNo}'/>')">                    
		                        [确认收款]</a>
		                         <input id="actualAmountTemp4<c:out value='${info.id}' />" type="hidden" value="<c:out value='${info.statement.totalAmount}'/>"/>
								</c:if>	
								
								
							<c:if test="${info.tranType==4 && info.status==31 && info.statement.type==2}">
		                        <a    href="#" onclick="showDiv4('<c:out value='${info.id}' />','<c:out value='${info.tranType}'/>','<c:out value='${info.groupMarkNo}'/>')">                    
		                        [确认收款]</a>
		                         <input id="actualAmountTemp4<c:out value='${info.id}' />" type="hidden" value="<c:out value='${info.statement.totalAmount}'/>"/>
								</c:if>			
								
								<c:if test="${info.status==22||info.status==32 ||info.status==23||info.status==33}">
								 
								  <a href="#" onclick="showDiv11('<c:out value='${info.id}' />')">              
		                          <c:if test="${empty info.memo}">[备注]</c:if>
								  <c:if test="${!empty info.memo}"><font color="red">[备注]</font></c:if> 
		                        </a>
							       <input value="<c:out value='${info.memo}' />" type="hidden" id="memo<c:out value='${info.id}' />"/>   
								</c:if>	
								
								
							  <!-- 申请改签 -->
									<c:if test="${info.statement.type==1 && info.status==39}">
									 <a href="<%=path %>/airticket/airticketOrder.do?thisAction=updateOrderStatus&status=44&id=<c:out value='${info.id}' />">                    
		                           [拒绝申请]</a><br/>
		                     
									 <td>
								   <a   onclick="showDiv5('<c:out value='${info.id}' />','<c:out value='${info.tranType}'/>','<c:out value='${info.groupMarkNo}'/>')"  href="#">                    
		                        [通过申请]</a>
		                        <input type="hidden" id="TmpFromPCAccount5" value="<c:out value="${info.statement.fromPCAccount.platform.name}" />"/>
								   </td>
									</c:if>
									
							 <!-- 申请改签 外部-->		
							<c:if test="${info.statement.type==1 && info.status==46}">
									 <a href="<%=path %>/airticket/airticketOrder.do?thisAction=updateOrderStatus&status=44&id=<c:out value='${info.id}' />">                    
		                           [拒绝申请]</a><br/>
		                     
									 <td>
								   <a   onclick="showDiv14('<c:out value='${info.id}' />','<c:out value='${info.tranType}'/>','<c:out value='${info.groupMarkNo}'/>')"  href="#">                    
		                        [通过申请]</a>
		                        <input type="hidden" id="TmpFromPCAccount14" value="<c:out value="${info.statement.fromPCAccount.platform.name}" />"/>
		                          <input id="tmpPlatformId14<c:out value='${info.id}' />" value="<c:out value='${info.statement.platComAccount.platform.id}'/>" type="hidden"/>
	                              <input id="tmpCompanyId14<c:out value='${info.id}' />" value="<c:out value='${info.statement.platComAccount.company.id}'/>" type="hidden"/>
	                              <input id="tmpAccountId14<c:out value='${info.id}' />" value="<c:out value='${info.statement.platComAccount.account.id}'/>" type="hidden"/>
								   </td>
									</c:if>
									
										
								<c:if test="${info.statement.type==1 && info.status==40}">
									 
								  <a href="<%=path %>/airticket/airticketOrder.do?thisAction=updateOrderStatus&status=41&id=<c:out value='${info.id}' />">                     
		                        [通过申请]</a>
								 
									</c:if>	
									
									<c:if test="${info.statement.type==1 && info.status==41}">
									 
								   <a   onclick="showDiv6('<c:out value='${info.id}'/>','<c:out value='${info.tranType}'/>')"  href="#">                      
		                        [收款]</a>
								       <input type="hidden"  id="platformName<c:out value='${info.id}'/>" value="<c:out value="${info.statement.fromPCAccount.platform.name}" />"/> 
								       <input type="hidden"  id="companyName<c:out value='${info.id}'/>" value="<c:out value="${info.statement.fromPCAccount.company.name}" />"/> 
								       <input type="hidden"  id="accountName<c:out value='${info.id}'/>" value="<c:out value="${info.statement.fromPCAccount.account.name}" />"/> 
									</c:if>		
									
									<c:if test="${info.statement.type==1 && info.status==43}">
									 
								<a href="<%=path %>/airticket/airticketOrder.do?thisAction=updateOrderStatus&status=45&id=<c:out value='${info.id}' />">                      
		                        [确认收款]</a>
								 
									</c:if>		
									
								<!-- 申请改签 -->
									<c:if test="${info.statement.type==2 && info.status==42}">
									 
								   <a   onclick="showDiv6('<c:out value='${info.id}' />','<c:out value='${info.tranType}'/>')"  href="#">                    
		                        [付款]</a>
								 
								       <input type="hidden"  id="platformName<c:out value='${info.id}'/>" value="<c:out value="${info.statement.toPCAccount.platform.name}" />"/> 
								       <input type="hidden"  id="companyName<c:out value='${info.id}'/>" value="<c:out value="${info.statement.toPCAccount.company.name}" />"/> 
								       <input type="hidden"  id="accountName<c:out value='${info.id}'/>" value="<c:out value="${info.statement.toPCAccount.account.name}" />"/>
									</c:if>	
								
								<c:if test="${info.statement.type==2 && info.status==43}">
									 
								 <a href="<%=path %>/airticket/airticketOrder.do?thisAction=updateOrderStatus&status=45&id=<c:out value='${info.id}' />">                    
		                        [确认付款]</a>
								 
									</c:if>		
									
								<c:if test="${info.status==44||info.status==45}">
								 
								  <a href="#" onclick="showDiv11('<c:out value='${info.id}' />')">              
		                            <c:if test="${empty info.memo}">[备注]</c:if>
								  <c:if test="${!empty info.memo}"><font color="red">[备注]</font></c:if> 
		                        </a>
							       <input value="<c:out value='${info.memo}' />" type="hidden" id="memo<c:out value='${info.id}' />"/>   
								</c:if>			
									
									
							<c:if test="${info.statement.type==2 && info.status==41}">
							
								 <a href="#" onclick="showDiv11('<c:out value='${info.id}' />')">              
		                             <c:if test="${empty info.memo}">[备注]</c:if>
								  <c:if test="${!empty info.memo}"><font color="red">[备注]</font></c:if> 
		                        </a>
							       <input value="<c:out value='${info.memo}' />" type="hidden" id="memo<c:out value='${info.id}' />"/> 
							
								</c:if>	
									
									
										</td>
									</tr>
                                 </c:forEach>
								</table>

								<table width="100%" style="margin-top: 5px;">
									<tr>
										<td></td>
										<td align="right">
											<div>
												<input name="label" type="button" class="button1" value="返 回"
											onclick="window.history.back();">
											</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</html:form>
			</div>
		</div>
		
<div id="dialog9" title="申请支付">
	<p id="validateTips"></p>
<form action="../airticket/airticketOrder.do?thisAction=applyTicket" id="form9"   method="post" >
	<fieldset>
	    <input id="oId9" name="id" type="hidden" />
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
		<input value="提交" type="submit" >
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
	     <td><input type="text" name="totalAmount" id="totalAmount3" value="0"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
		 <tr>
	     <td><label for="password">手续费</label></td>
	     <td><input type="text" name="handlingCharge" id="handlingCharge3"   value="0"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
	    <tr>
	     <td><label for="password">客规</label></td>
	     <td>
	       <select name="selTuiPercent" id="selTuiPercent3"  onchange="selTuiPercent_onchange()">
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
	     <td><input type="text" name="totalAmount" id="totalAmount7" value="0"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
		 <tr>
	     <td><label for="password">手续费</label></td>
	     <td><input type="text" name="handlingCharge" id="handlingCharge7"   value="0"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
	    <tr>
	     <td><label for="password">客规</label></td>
	     <td>
	       <select name="selTuiPercent" id="selTuiPercent7" onchange="selTuiPercent_onchange2()">
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

<div id="dialog4" title="确认款">
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
	     <td><label for="password">实收金额</label></td>
	     <td><input type="text" name="statement.actualAmount" id="actualAmount4" value="0"  class="text ui-widget-content ui-corner-all" /></td>
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
	    <td>平台</td>
		<td>
	<select name="platformId2" id="platformId5" disabled="disabled"  class="text ui-widget-content ui-corner-all">		
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


<div id="dialog6" title="确认收款">
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
		<input value="免费改签" type="submit" >
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
	</body>
</html>
