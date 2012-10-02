<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
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
	<script type="text/javascript">
	$(function() {		
		$("#dialog").dialog({
			bgiframe: true,
			autoOpen: false,
			//height: 500,
			//width:300,
			modal: true
		});
		
		
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
	 // $('#tmpTotalAmount9').val(totalAmount);
	  $('#totalAmount9').val(0);
	  $('#rebate9').val(0);
	  $('#liruen9').val(0);
	  $('#dialog9').dialog('open');
	  var gm=$('#tmpGroupMarkNo9'+oId).val();
	   $('#groupMarkNo9').val(gm);
	  
	   var tmpTranType=$("#tmpTranType9"+oId).val();
	  if(tmpTranType!=""&&tmpTranType!=null){
	  //alert(tmpTranType);
	  if(tmpTranType!=1){
	    $('#form9').attr('action','../airticket/airticketOrder.do?thisAction=anewApplyTicket');
	    }
	  }else{
	   $('#form9').attr('action','../airticket/airticketOrder.do?thisAction=applyTicket');
	  }
	  
	       	//设置下拉框  平台初始值 默认选中
	    var tmpPlatformValue=$("#tmpPlatformId9"+oId).val();
	    var tmpCompanyValue=$("#tmpCompanyId9"+oId).val();  	
	     var tmpAccountValue=$("#tmpAccountId9"+oId).val(); 
	     if(tmpPlatformValue!=null&&tmpPlatformValue!=""){	
	     
	     //loadPlatListSelected('platform_Id9','company_Id9','account_Id9',tmpPlatformValue,tmpCompanyValue,tmpAccountValue);
	     loadPlatListSelectedByType('platform_Id9','company_Id9','account_Id9',tmpPlatformValue,tmpCompanyValue,tmpAccountValue,'2');
	     }else{
	     
	     // loadPlatList('platform_Id9','company_Id9','account_Id9');
	     loadPlatListByType('platform_Id9','company_Id9','account_Id9','2');
	     }
	     
	airticketOrderBiz.getAirticketOrderByMarkNo(gm,1,function(ao){
      var tmpTa= ao.totalAmount;
	   if(tmpTa!=null){
	   // alert(tmpTa);
	   $('#tmpTotalAmount9').val(tmpTa);
	  }else{
	    $('#tmpTotalAmount9').val(0);
	  }
	 
	 });
	     
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
	     
	    // loadPlatListSelected('platform_Id','company_Id','account_Id',tmpPlatformValue,tmpCompanyValue,tmpAccountValue);
	      loadPlatListSelectedByType('platform_Id','company_Id','account_Id',tmpPlatformValue,tmpCompanyValue,tmpAccountValue,'2');
	     }else{
	   //  loadPlatList('platform_Id','company_Id','account_Id');
	     loadPlatListByType('platform_Id','company_Id','account_Id','2');		
	     }
	  
	 }
	  var gm=$('#tmpGroupMarkNo'+oId).val();
	 	airticketOrderBiz.getAirticketOrderByMarkNo(gm,1,function(ao){
      var tmpTa= ao.totalAmount;
	   if(tmpTa!=null){
	     $('#tmpTotalAmount1').val(tmpTa);
	  calculationLiren('tmpTotalAmount1','totalAmount1','liruen1');
	  }
	 
	 });
	}


   
    function showDiv2(oId,suPnr,groupMarkNo){
	 
	 
	 passengerBiz.listByairticketOrderId(oId,function(list){
	 $('#per tbody').html("");
	 $('#per tbody').append('<tr><td width="200">乘客姓名</td>  <td width="200" style="display: none;">证件号</td>  <td width="200">票号</td></tr>');
	 for(var i=0;i<list.length;i++){
	 var ticketNumbers=list[i].ticketNumber;
	 if(ticketNumbers==null){
	 ticketNumbers="";
	 }
	    $('#per tbody').append('<tr>' +
							'<td>' + list[i].name + '</td>' + 
							'<td style="display: none;" >' + list[i].cardno + '</td>' + 
							'<td><input type="text" name="ticketNumber"  value="' + ticketNumbers + '"  class="text ui-widget-content ui-corner-all" /></td>' +
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
 function showDiv8(oId,tranType,status){

	  $('#oId8').val(oId);
	  $('#tranType8').val(tranType);
	 // $('#groupMarkNo8').val(groupMarkNo);
	  $('#status8').val(status);
	  $('#dialog8').dialog('open');
	 
	}	
	
  //审核
 function showDiv3(oId,tranType,groupMarkNo){

	  $('#oId3').val(oId);
	  $('#tranType3').val(tranType);
	  $('#groupMarkNo3').val(groupMarkNo);
	  $('#dialog3').dialog('open');
	  $('#airOrderNo3').val('');
	  $('#selTuiPercent3').attr('disabled','');
	  
	  var passNum=$('#passengersCountTemp3'+oId).val();
	 if(passNum!=null&&passNum!=""){
	   $('#passengersCount3').val(passNum);//设置新人数
	 }else{
	    $('#passengersCount3').val(0);//设置新人数
	 }
	 // alert(passNum);
	
	 if(tranType==4){
	 $('#selTuiPercent3').attr('disabled','disabled');
	 }
	 airticketOrderBiz.getDrawedAirticketOrderByGroupMarkNo(groupMarkNo,2,function(ao){
	    var ta= ao.totalAmount;
	   if(ta!=null){
	    $('#TmptotalAmount3').val(ta);
	   var oldPassNum= $('#oldPassengersCount3').val(ao.passengersCount);//设置原来人数
	    //alert(oldPassNum.val());
	    }
	    if(ao.airOrderNo!=null){
	     $('#airOrderNo3').val(ao.airOrderNo);
	    }
	    //设置平台
	  if(ao.account!=null){
	   
	   var  platform_Id= document.getElementById("platform_Id3");
	     platform_Id.options.length=0;
	     $('#platform_Id3').attr("disabled","disabled");
	     option = new Option(ao.platform.name,ao.platform.id);
		 platform_Id.options.add(option);
		 
		 var  company_Id= document.getElementById("company_Id3");
	     company_Id.options.length=0;
	     $('#company_Id3').attr("disabled","disabled");
	     option2 = new Option(ao.company.name,ao.company.id);
		 company_Id.options.add(option2);
		 
		  var  account_Id= document.getElementById("account_Id3");
	     account_Id.options.length=0;
	     $('#account_Id3').attr("disabled","disabled");
	     option3 = new Option(ao.account.name,ao.account.id);
		 account_Id.options.add(option3);
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
	  $('#airOrderNo7').val('');
	  $('#selTuiPercent7').attr('disabled','');
	  
	  var passNum=$('#passengersCountTemp7'+oId).val();
	 if(passNum!=null&&passNum!=""){
	   $('#passengersCount7').val(passNum);//设置新人数
	 }else{
	    $('#passengersCount7').val(0);//设置新人数
	 }
	 // alert(passNum);
	  
	 if(tranType==4){
	 $('#selTuiPercent7').attr('disabled','disabled');
	 }
	 airticketOrderBiz.getDrawedAirticketOrderByGroupMarkNo(groupMarkNo,1,function(ao){
	    var ta= ao.totalAmount;
	   if(ta!=null){
	    //alert(ta);
	    $('#TmptotalAmount7').val(ta);
	     var oldPassNum= $('#oldPassengersCount7').val(ao.passengersCount);//设置原来人数
	    }
	    if(ao.airOrderNo!=null){
	     $('#airOrderNo7').val(ao.airOrderNo);
	    }
	    
	    //设置平台
	  if(ao.account!=null){
	   
	   var  platform_Id= document.getElementById("platform_Id7");
	     platform_Id.options.length=0;
	     $('#platform_Id7').attr("disabled","disabled");
	     option = new Option(ao.platform.name,ao.platform.id);
		 platform_Id.options.add(option);
		 
		 var  company_Id= document.getElementById("company_Id7");
	     company_Id.options.length=0;
	     $('#company_Id7').attr("disabled","disabled");
	     option2 = new Option(ao.company.name,ao.company.id);
		 company_Id.options.add(option2);
		 
		  var  account_Id= document.getElementById("account_Id7");
	     account_Id.options.length=0;
	     $('#account_Id7').attr("disabled","disabled");
	     option3 = new Option(ao.account.name,ao.account.id);
		 account_Id.options.add(option3);
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
	/* airticketOrderBiz.getDrawedAirticketOrderByGroupMarkNo(groupMarkNo,2,function(ao){
	    var ta= ao.totalAmount;
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
	  
	   var today = new Date();
	   var timeNow= showLocale(today);
	   $('#optTime4').val(timeNow); 
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
	   
	   	//设置下拉框  平台初始值 默认选中
	    var tmpPlatformValue=$("#tmpPlatformId5"+oId).val();
	    var tmpCompanyValue=$("#tmpCompanyId5"+oId).val();  	
	     var tmpAccountValue=$("#tmpAccountId5"+oId).val(); 
	     if(tmpPlatformValue!=null&&tmpPlatformValue!=""){	
	     
	     //loadPlatListSelected('platform_Id5','company_Id5','account_Id5',tmpPlatformValue,tmpCompanyValue,tmpAccountValue);
	     loadPlatListSelectedByType('platform_Id5','company_Id5','account_Id5',tmpPlatformValue,tmpCompanyValue,tmpAccountValue,'2');
	     }else{
	     
	     // loadPlatList('platform_Id5','company_Id5','account_Id5');
	     loadPlatListByType('platform_Id5','company_Id5','account_Id5','2');
	     } 

	 
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
	function submitForm6(){
	  $('#totalAmount6').val(0);
	 if(confirm("确定免费改签？")){
	  $('#form6').submit();
	 }
	
	}
		
	//设置退票原因		
	function submitForm8(){
	   
	    var  rbtnReason= $("input[name='rbtnReason']:checked").val();
	    var  rbtnType= $("input[name='rbtnType']:checked").val();
        var  cause=$("#cause").val(); 
        $("input[name='memo']").val(rbtnType+"/"+rbtnReason+"/"+cause);
	    return true;
	}
		//利润计算
	function calculationLiren(tmpTotalAmount,totalAmount,liren){
	 
	var tmpTa=$("#"+tmpTotalAmount).val();
	var ta=$("#"+totalAmount).val();
	var lr=$("#"+liren);
	 if(tmpTa!=null&&ta!=null){
	   var count=tmpTa-ta;
	  // count= count.toString().replace(/^(\d+\.\d{2})\d*$/,"$1");
	   count=Math.round(count*100)/100;
	   // count=getShowDecimal(count,2);
	   lr.val(count);
	 }
	
	}
	
		//-------获取规定小数位数
	function getShowDecimal(value,decimalLen){  
	//alert(value);
     if(value!=null&&value!=''){    
           var decimalIndex=value.indexOf('.');    
           if(decimalIndex=='-1'){    
              return value;    
           }else{               	  
              var decimalPart=value.substring(decimalIndex+1,value.length);    
             
              if(decimalPart.length>decimalLen){    
              	//alert(value);
              	 value=value.substring(0,decimalIndex+decimalLen+1);
              	 //alert("2-"+value);
                 return value;    
              }else{    
                 return value;    
              }    
            }    
        }else{
        	return '0';
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
	    var oldPassengerNum=$('#oldPassengersCount3').val();//原来人数
	   // alert(passengerNum);
	   // alert(oldPassengerNum);
	    if(passengerNum==null){
	      passengerNum=1;
	    }
	   if(ticketPrice!=null){ 
	     //alert(ticketPrice+'---'+selTuiPercent);
	    var tgqFee = ticketPrice * selTuiPercent / 100;//手续费计算=票面价*客规*退费人数
	   
	     tgqFee = tgqFee * Math.abs(passengerNum);
	     
	     handlingCharge.val(tgqFee);
	  
	  
	  var TmptotalAmount=$('#TmptotalAmount3').val();
      var totalTicketPrice=$('#totalAmount3');//
    if (TmptotalAmount * 1 > 0) {
        var receiveAmount = (TmptotalAmount/oldPassengerNum*passengerNum) - tgqFee;//应收金额=(卖出金额/原来人数*退费人数)-手续费
       //  alert(TmptotalAmount+'---'+tgqFee);
        
        var totalTicketPriceTemp= Math.round(receiveAmount * 100) / 100;
       // totalTicketPriceTemp= totalTicketPriceTemp.toString().replace(/^(\d+\.\d{2})\d*$/,"$1");//保留2位小数
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
	     var oldPassengerNum=$('#oldPassengersCount7').val();//原来人数
	    if(passengerNum==null){
	      passengerNum=1;
	    }
	    if(oldPassengerNum==null){
	    oldPassengerNum=1;
	    }
	   if(ticketPrice!=null){ 
	   //  alert(ticketPrice+'---'+selTuiPercent);
	    var tgqFee = ticketPrice * selTuiPercent / 100;//手续费计算=票面价*客规*退费人数
	   
	     tgqFee = tgqFee * Math.abs(passengerNum);
	     
	     handlingCharge.val(tgqFee);
	     
	  var TmptotalAmount=$('#TmptotalAmount7').val();
      var totalTicketPrice=$('#totalAmount7');
     
    if (TmptotalAmount * 1 > 0) {
    
    //    var receiveAmount = TmptotalAmount - tgqFee;
         var receiveAmount = (TmptotalAmount/oldPassengerNum*passengerNum) - tgqFee;//应收金额=(卖出金额/原来人数*退费人数)-手续费
        // alert(TmptotalAmount+'---'+tgqFee);
       // totalTicketPrice.val(Math.round(receiveAmount * 100) / 100);
       
        var totalTicketPriceTemp= Math.round(receiveAmount * 100) / 100;
        //totalTicketPriceTemp= totalTicketPriceTemp.toString().replace(/^(\d+\.\d{2})\d*$/,"$1");//保留2位小数
        totalTicketPrice.val(totalTicketPriceTemp); 
    } 
   }
	}
	
	//验证出票 showDiv2
function submitForm2(){
	
	var check=true;
   var from2= document.getElementById("form2");
   var ticketNumbers=from2.ticketNumber;
 //  alert(from2.ticketNumber.length)
  for(var i=0;i<ticketNumbers.length;i++){
     
  for(var j=i+1;j<ticketNumbers.length;j++){
    //   alert(ticketNumbers[i].value+"----"+ticketNumbers[j].value);
       
       if (ticketNumbers[i].value!=""&& ticketNumbers[j].value!="") {
        
       if(ticketNumbers[i].value==ticketNumbers[j].value){
          
           alert("票号不能重复！");
           return false;
           check=false;
        }
     }
    }
     
  }
  if(check){
  $('#form2').submit();
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
										<th>
											<div>
												订单时间
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
										    <c:if test="${!empty info.platform}">
										    <c:out value="${info.platform.showName}" />
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
											 <c:out value="${info.totalAmount}" />
										</td>	
										<td>
											<c:out value="${info.tranTypeText}" />(<c:out value="${info.businessTypeText}" />)
										</td>
										<td>
											 <c:out value="${info.statusText}" />
										</td>
							           			<td>
												<c:out	value="${info.entryOperatorName}" /> 
											</td>
											<td>
												<c:if test="${!empty info.payOperatorName}">
													<c:out	value="${info.payOperatorName}" /> 
												</c:if>
											</td>
									<td>
									<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${info.optTime}" />
										</td>	
								<td>
								
								
								<c:out value='${info.tradeOperate}' escapeXml="false"/>
									
		                            <input id="tmpPlatformId9<c:out value='${info.id}' />" value="<c:out value='${info.platform.id}'/>" type="hidden"/>
	                                  <input id="tmpCompanyId9<c:out value='${info.id}' />" value="<c:out value='${info.company.id}'/>" type="hidden"/>
	                                  <input id="tmpAccountId9<c:out value='${info.id}' />" value="<c:out value='${info.account.id}'/>" type="hidden"/>
	                                  <input id="tmpGroupMarkNo9<c:out value='${info.id}' />"  value="<c:out value='${info.groupMarkNo}' />"  type="hidden" />
								 	
							       <input value="<c:out value='${info.memo}' />" type="hidden" id="memo<c:out value='${info.id}' />"/>  
							
						
		                                  <input id="tmpPlatformId<c:out value='${info.id}' />" value="<c:out value='${info.platform.id}'/>" type="hidden"/>
		                                  <input id="tmpCompanyId<c:out value='${info.id}' />" value="<c:out value='${info.company.id}'/>" type="hidden"/>
		                                  <input id="tmpAccountId<c:out value='${info.id}' />" value="<c:out value='${info.account.id}'/>" type="hidden"/>
		                                  <input id="tmpGroupMarkNo<c:out value='${info.id}' />"  value="<c:out value='${info.groupMarkNo}' />"  type="hidden" />
								        <input id="tmpGroupMarkNo<c:out value='${info.id}' />"  value="<c:out value='${info.groupMarkNo}' />"  type="hidden" />
							
								
						
							
		                        <input id="tmpPlatformId9<c:out value='${info.id}' />" value="<c:out value='${info.platform.id}'/>" type="hidden"/>
	                                  <input id="tmpCompanyId9<c:out value='${info.id}' />" value="<c:out value='${info.company.id}'/>" type="hidden"/>
	                                  <input id="tmpAccountId9<c:out value='${info.id}' />" value="<c:out value='${info.account.id}'/>" type="hidden"/>
	                                   <input id="tmpGroupMarkNo9<c:out value='${info.id}' />"  value="<c:out value='${info.groupMarkNo}' />"  type="hidden" />
								   <input id="tmpTranType9<c:out value='${info.id}' />"  value="<c:out value='${info.tranType}' />"  type="hidden" />
								
									
									    <input type="hidden"  id="ticketPrice<c:out value='${info.id}' />"  value="<c:out value='${info.ticketPrice}' />"/>
							            <input type="hidden"  id="ticke"  value="<c:out value='${info.totalAmount}' />"/>
							            <input type="hidden" value="<c:out value='${info.adultCount}' />">
							            <input type="hidden"  id="passengersCountTemp3<c:out value='${info.id}' />"  value="<c:out value='${info.passengersCount}' />"/>
									
									 	    <input type="hidden"  id="ticketPrice<c:out value='${info.id}' />"  value="<c:out value='${info.ticketPrice}' />"/>
									        <input type="hidden"  id="ticke"  value="<c:out value='${info.totalAmount}' />"/>
									         <input type="hidden" value="<c:out value='${info.adultCount}' />">
									        <input type="hidden"  id="passengersCountTemp7<c:out value='${info.id}' />"  value="<c:out value='${info.passengersCount}' />"/>   
									
									
									  <input id="tmpPlatformId12<c:out value='${info.id}' />" value="<c:out value='${info.platform.id}'/>" type="hidden"/>
					                  <input id="tmpCompanyId12<c:out value='${info.id}' />" value="<c:out value='${info.company.id}'/>" type="hidden"/>
					                  <input id="tmpAccountId12<c:out value='${info.id}' />" value="<c:out value='${info.account.id}'/>" type="hidden"/>
						             
						              <input id="actualAmountTemp4<c:out value='${info.id}' />" type="hidden" value="<c:out value='${info.totalAmount}'/>"/>
						              
						               <input type="hidden"  id="passengersCountTemp3<c:out value='${info.id}' />"  value="<c:out value='${info.passengersCount}' />"/>
						               
						                 <input type="hidden" id="TmpFromPCAccount5" value="<c:out value="${info.platform.name}" />"/>
					                 <input id="tmpPlatformId5<c:out value='${info.id}' />" value="<c:out value='${info.platform.id}'/>" type="hidden"/>
					                    <input id="tmpCompanyId5<c:out value='${info.id}' />" value="<c:out value='${info.company.id}'/>" type="hidden"/>
					                    <input id="tmpAccountId5<c:out value='${info.id}' />" value="<c:out value='${info.account.id}'/>" type="hidden"/>
					                    
					                    <input type="hidden" id="TmpFromPCAccount14" value="<c:out value="${info.platform.name}" />"/>
                <input id="tmpPlatformId14<c:out value='${info.id}' />" value="<c:out value='${info.platform.id}'/>" type="hidden"/>
                <input id="tmpCompanyId14<c:out value='${info.id}' />" value="<c:out value='${info.company.id}'/>" type="hidden"/>
                <input id="tmpAccountId14<c:out value='${info.id}' />" value="<c:out value='${info.account.id}'/>" type="hidden"/>
                 <input type="hidden"  id="platformName<c:out value='${info.id}'/>" value="<c:out value="${info.platform.name}" />"/> 
				       <input type="hidden"  id="companyName<c:out value='${info.id}'/>" value="<c:out value="${info.company.name}" />"/> 
				       <input type="hidden"  id="accountName<c:out value='${info.id}'/>" value="<c:out value="${info.account.name}" />"/> 
									
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
	</body>
</html>
