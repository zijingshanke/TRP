	//申请支付
function showDiv9(oId,suPnr,airOrderNo,totalAmount,rebate,entryTime){	 
	  $('#oId9').val(oId);
	  if(suPnr!=null){
	  	$('#pnr9').val(suPnr);
	  }
	  
	  $('#airOrderNo9').val();
	  $('#totalAmount9').val(0);
	  $('#rebate9').val(0);
	  $('#liruen9').val(0);
	  $('#optTime9').val(entryTime);
	  $('#dialog9').dialog('open');
	  $('#dialog9').draggable({cancle: 'form'}); 
	  
	  var gm=$('#tmpGroupId'+oId).val();
	  $('#groupId9').val(gm);
	  
	  var tmpTranType=$("#tmpTranType9"+oId).val();
	  if(tmpTranType!=""&&tmpTranType!=null){
	  //alert(tmpTranType);
	  	if(tmpTranType!=1){
	    	$('#form9').attr('action','../airticket/airticketOrder.do?thisAction=anewApplyPayOrder');
	    }
	  }else{
	   $('#form9').attr('action','../airticket/airticketOrder.do?thisAction=applyPayOrder');
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
	     
	    var onfocusObj=document.getElementById("platform_Id9");
	    onfocusObj.focus(); 
	     
	     
	//alert("groupId:"+groupId);	    
	airticketOrderBiz.getAirticketOrderByGroupIdAndTranType(gm,1,function(ao){
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
	var totalAmount=$('#totalAmount9').val();
	var rebate=$('#rebate9').val();
	totalAmount=$('#totalAmount9').val($.trim(totalAmount));
	rebate=$('#rebate9').val($.trim(rebate));
	
	 if(!re.test(totalAmount.val())||totalAmount.val()==""){
           alert("请正确填写金额!");       
     }else if(!re.test(rebate.val())||rebate.val()==""){
           alert("请正确填写政策!");
     }else{
     	if(setSubmitButtonDisable("submitButton9")){
     	   $('#form9').submit();	
     	}	  	
	 }	  
 }
 	
//确认支付
function showDiv(oId,suPnr,airOrderNo,totalAmount,rebate,entryTime){
	  $('#oId').val(oId);
	  $('#pnr1').val(suPnr);
	  $('#airOrderNo1').val(airOrderNo);
	  $('#totalAmount1').val(totalAmount);
	  $('#totalAmount2').val(totalAmount);
	  $('#tmpTotalAmount1').val(totalAmount);
	  $('#rebate1').val(rebate);
	  $('#optTime1').val(entryTime);
	  calculationLiren('tmpTotalAmount1','totalAmount1','liren1');
	  $('#dialog').dialog('open');
	  $('#dialog').draggable({cancle: 'form'}); 
	   
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
	  var gm=$('#tmpGroupId'+oId).val();
	  //alert('#tmpGroupId'+oId+"确认支付 groupId:"+gm);
	 	airticketOrderBiz.getAirticketOrderByGroupIdAndTranType(gm,1,function(ao){
      var tmpTa= ao.totalAmount;
	   if(tmpTa!=null){
	     $('#tmpTotalAmount1').val(tmpTa);
	  	 calculationLiren('tmpTotalAmount1','totalAmount1','liruen1');
	  }	 
	 });
}   
   
   //确认出票
  function showDiv2(oId,suPnr,groupId){	 
	 passengerBiz.listByairticketOrderId(oId,function(list){
		 $('#per tbody').html("");
		 $('#per tbody').append('<tr><td width="200">乘客姓名</td><td width="200">票号</td></tr>');
		 for(var i=0;i<list.length;i++){
			 var ticketNumber=list[i].ticketNumber;
			 if(ticketNumber==null){
			 	ticketNumber="";
			 }	 
			 $('#per tbody').append('<tr>' +
									'<td>' + list[i].name + '</td>' + 
									'<td><input type="hidden" name="pId"  value="' + list[i].id + '" /></td>'+
									'<td><input type="text" name="ticketNumber"  value="' + ticketNumber + '"  onkeypress="keypressForm2(event)" /></td>' +									
									'</tr>'); 
		 }
	 });
	  $('#oId2').val(oId);
	  $('#pnr2').val(suPnr);
	  $('#dialog2').dialog('open');
	  $('#dialog2').draggable({cancle: 'form'}); 
	}
	
	//验证 确认出票 showDiv2
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

function keypressSubmitForm2(e){
	var isie = (document.all) ? true : false;//判断是IE内核还是Mozilla  
	var key;  
	if (isie)  {
	  key = window.event.keyCode;//IE使用windows.event事件  
	}else{
	    key = e.which;//3个按键函数有一个默认的隐藏变量，这里用e来传递。e.which给出一个索引值给Mo内核（注释1） 	   
	}
	if(key==13){
		submitForm2();
	} 	
}


 //卖出订单,取消出票
 function showDiv17(oId){
	  $('#oId17').val(oId);
	  $('#dialog17').dialog('open');
	  $('#dialog17').draggable({cancle: 'form'}); 	 
}	

 //买入订单,取消出票
 function showDiv18(oId){
	  $('#oId18').val(oId);
	  $('#dialog18').dialog('open');
	  $('#dialog18').draggable({cancle: 'form'}); 	 
}	

//取消出票，确认退款
 function showDiv19(id,totalAmount,entryTime){
	      if(totalAmount!=null){
	        $('#totalAmount19').val(totalAmount);
	      }
		  $('#id19').val(id);
		  
		  var platformName=$('#platformName'+id).val();
		  var accountName=$('#accountName'+id).val();
		  
		  document.getElementById("platformName19").innerHTML=platformName;
		  document.getElementById("accountName19").innerHTML=accountName;	  	  
		  
		  $('#optTime19').val(entryTime); 
		   
		  $('#dialog19').dialog('open');
		  $('#dialog19').draggable({cancle: 'form'}); 
}
	
//验证 取消出票，确认退款提交表单 showDiv19	
function submitForm19(){		  
		  var re=/^([1-9][0-9]*|0)(\.[0-9]{0,3})?$/;
		   var totalAmount= $('#totalAmount19').val();
	       $('#totalAmount19').val($.trim(totalAmount));
	       var optTime=$('#optTime19').val();
	       
	       if(optTime==null||optTime==''){
	         alert("请选择日期！");       
	         return false;  
	       }else if(!re.test(totalAmount)||totalAmount==""){
	           alert("请正确填写金额！");
	           return false;  
	       }
	       
			 if(setSubmitButtonDisable('submitButton19')){  
			        $('#form19').submit();  	       	 
		     }else{
		     	alert("页面错误，请联系管理员");
		     	return false;
		     }
}

	
  //审核退废1--创建买入
 function showDiv3(oId,tranType,groupId){
	  $('#oId3').val(oId);
	  $('#tranType3').val(tranType);
	  $('#dialog3').dialog('open');
	  $('#dialog3').draggable({cancle: 'form'}); 	  
	  $('#airOrderNo3').val('');
	  $('#transRuleSelectObj3').attr('disabled','');
	    
	  var totalPerson=$('#totalPerson'+oId).val();
	  if(totalPerson!=null&&totalPerson!=""){
	    $('#passengersCount3').val(totalPerson);//设置新人数
	  }else{
	    $('#passengersCount3').val(0);//设置新人数
	  }
	 //alert("passNum:"+passNum);
	
	 if(tranType==4){
	 	$('#transRuleSelectObj3').attr('disabled','disabled');		 	
	 }
		 airticketOrderBiz.getDrawedAirticketOrderByGroupId(groupId,2,function(drawOrder){		 
		   	   var totalAmount= drawOrder.showTotalAmount;
			   if(totalAmount!=null){
				    $('#TmptotalAmount3').val(totalAmount);
				   var oldPassNum=drawOrder.totalPerson;
				   $('#oldPassengersCount3').val(oldPassNum);//设置原来人数
			    }
			    if(drawOrder.airOrderNo!=null){
			     	$('#airOrderNo3').val(drawOrder.airOrderNo);
			    }

		   		 //设置平台
			  	if(drawOrder.platform!=null&&drawOrder.company!=null&&drawOrder.account!=null){	  
			  		//alert(" order account is not null..."); 
				    var  platform_Id= document.getElementById("platform_Id3");
				     platform_Id.options.length=0;
				     $('#platform_Id3').attr("disabled","disabled");
				     option = new Option(drawOrder.platform.name,drawOrder.platform.id);
					 platform_Id.options.add(option);
					 
					 var  company_Id= document.getElementById("company_Id3");
				     company_Id.options.length=0;
				     $('#company_Id3').attr("disabled","disabled");
				     option2 = new Option(drawOrder.company.name,drawOrder.company.id);
					 company_Id.options.add(option2);
					 
					 var  account_Id= document.getElementById("account_Id3");
				     account_Id.options.length=0;
				     $('#account_Id3').attr("disabled","disabled");
				     option3 = new Option(drawOrder.account.name,drawOrder.account.id);
					 account_Id.options.add(option3);					 
			  } 
			  
			  var drawType=drawOrder.platform.drawType;				
				if(tranType==4){//废票
					if(drawType!=null){
						var handlingCharge=0;
					 	if(drawType==0){//平台出票
					 	    handlingCharge=10*totalPerson;
							$('#handlingCharge3').val(handlingCharge);							 	
					 	 }else if(drawType==1||drawType==2){//网电/BSP
					 	 	handlingCharge=0;
					 	 	$('#handlingCharge3').val(handlingCharge);
					 	 	alert("网电废票不收手续费");					 	 		
					 	 }
					 	 //采购的废票应收金额=出票采购金额/出票订单人数*废票订单人数-手续费
					 	 var retireTotalAmount=(totalAmount/oldPassNum)*totalPerson-handlingCharge;
					 	 retireTotalAmount= getShowDecimal(retireTotalAmount+"",1);//直接获取一位小数,截掉后面的
					 	 //retireTotalAmount=ForDight(retireTotalAmount,2);//保留二位小数
					 	 	
					 	 $('#totalAmount3').val(retireTotalAmount);
					 }
				} 
		 });
}

 //审核退废1
function submitForm3(){	
	if(setSubmitButtonDisable('submitButton3')){
		var totalAmount=  $('#totalAmount3').val();
		totalAmount=  $('#totalAmount3').val($.trim(totalAmount));	
		var handlingCharge=  $('#handlingCharge3').val();
		handlingCharge=  $('#handlingCharge3').val($.trim(handlingCharge));
		$('#form3').submit();	 
	}		 
}

  //审核退废2 --供应
 function showDiv7(oId,tranType,groupId){
	  $('#oId7').val(oId);
	  $('#tranType7').val(tranType);
	  $('#groupId7').val(groupId);
	  $('#dialog7').dialog('open');
	  $('#airOrderNo7').val('');
	  $('#transRuleSelectObj7').attr('disabled','');
	  
	  var totalPerson=$('#totalPerson'+oId).val();
	  if(totalPerson!=null&&totalPerson!=""){
	    $('#passengersCount7').val(totalPerson);//设置新人数
	  }else{
	    $('#passengersCount7').val(0);//设置新人数
	  }
	  // alert(passNum);
	  
	 if(tranType==4){
	 	$('#transRuleSelectObj7').attr('disabled','disabled');
	 }
	 
	 airticketOrderBiz.getDrawedAirticketOrderByGroupId(groupId,1,function(drawedOrder){
	    var totalAmount= drawedOrder.showTotalAmount;
	   if(totalAmount!=null){
	    	//alert(ta);
		    $('#TmptotalAmount7').val(totalAmount);
			     var oldPassNum=drawedOrder.totalPerson;
			     $('#oldPassengersCount7').val(oldPassNum);//设置原来人数
		    }
		    if(drawedOrder.airOrderNo!=null){
		     $('#airOrderNo7').val(drawedOrder.airOrderNo);
		    }
	    
	    //设置平台
	  if(drawedOrder.account!=null){	   
	   var  platform_Id= document.getElementById("platform_Id7");
	     platform_Id.options.length=0;
	     $('#platform_Id7').attr("disabled","disabled");
	     option = new Option(drawedOrder.platform.name,drawedOrder.platform.id);
		 platform_Id.options.add(option);
		 
		 var  company_Id= document.getElementById("company_Id7");
	     company_Id.options.length=0;
	     $('#company_Id7').attr("disabled","disabled");
	     option2 = new Option(drawedOrder.company.name,drawedOrder.company.id);
		 company_Id.options.add(option2);
		 
		  var  account_Id= document.getElementById("account_Id7");
	     account_Id.options.length=0;
	     $('#account_Id7').attr("disabled","disabled");
	     option3 = new Option(drawedOrder.account.name,drawedOrder.account.id);
		 account_Id.options.add(option3);
	  } 
	  
	   var drawType=drawedOrder.platform.drawType;				
	   if(tranType==4){//废票
			if(drawType!=null){
			    //手续费=10*废票订单人数  
				var handlingCharge=10*totalPerson;					 	 	
				$('#handlingCharge7').val(handlingCharge);				 	 		
					
				//供应的废票应收金额=出票时收到的供应金额/出票订单人数*废票订单人数-手续费
			    var retireTotalAmount=(totalAmount/oldPassNum)*totalPerson-handlingCharge;
				retireTotalAmount= getShowDecimal(retireTotalAmount+"",1);//直接获取一位小数,截掉后面的
				//retireTotalAmount=ForDight(retireTotalAmount,2);//保留二位小数
					 	 	
				$('#totalAmount7').val(retireTotalAmount);
			}
		}
	 });
}	

  //审核退废2  showDiv7
function submitForm7(){	
	if(setSubmitButtonDisable('submitButton7')){
		var totalAmount=  $('#totalAmount7').val();
		totalAmount=  $('#totalAmount7').val($.trim(totalAmount));
		
		var handlingCharge=  $('#handlingCharge7').val();
		handlingCharge=  $('#handlingCharge7').val($.trim(handlingCharge));
		$('#form7').submit();	 
	} 
}	

//退/废票确认收款
 function showDiv4(id,suPnr,groupId,entryTime){
      var actualAmountTemp4=$('#actualAmountTemp4'+id).val();
       
      if(actualAmountTemp4!=null){
        $('#actualAmount4').val(actualAmountTemp4);
      }
	  $('#id4').val(id);
	  $('#tranType4').val(suPnr);
	  $('#groupId').val(groupId);
	  
	  var platformName=$('#platformName'+id).val();
	  var accountName=$('#accountName'+id).val();
	  
	  document.getElementById("platformName4").innerHTML=platformName;
	  document.getElementById("accountName4").innerHTML=accountName;	  	  
	  
	 // var today = new Date();
	  //var timeNow= showLocale(today);
	  $('#optTime4').val(entryTime); 
	   
	  $('#dialog4').dialog('open');
	  $('#dialog4').draggable({cancle: 'form'}); 
}
	
//验证  退/废票确认退款提交表单 showDiv4	
function submitForm4(){	
	
	   var re=/^([1-9][0-9]*|0)(\.[0-9]{0,3})?$/;
	   var actualAmount= $('#actualAmount4').val();
       $('#actualAmount4').val($.trim(actualAmount));
       var optTime=$('#optTime4').val();
       
       if(optTime==null||optTime==''){
         alert("请选择日期！");      
         return false;   
       }else if(!re.test(actualAmount)||actualAmount==""){
         alert("请正确填写金额！");
         return false;
       }
       	   
       if(setSubmitButtonDisable('submitButton4')){
        	$('#form4').submit();  
	    }else{
	    	alert("页面错误，请联系管理员");
	    	return false;
	    }
}
	
//申请改签
 function showDiv5(oId,suPnr,groupId){
	  $('#oId5').val(oId);
	  $('#tranType5').val(suPnr);
	  $('#groupId5').val(groupId);
	  $('#dialog5').dialog('open');
	  $('#dialog5').draggable({cancle: 'form'}); 
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
	
	//申请改签
 function showDiv6(oId,suPnr,groupId){
	  $('#oId6').val(oId);
	  $('#tranType6').val(suPnr);
	  $('#groupId6').val(groupId);
	  $('#dialog6').dialog('open');
	  $('#dialog6').draggable({cancle: 'form'}); 
	 
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

//利润计算
function calculationLiren(tmpTotalAmount,totalAmount,liren){	
	var tmpTotalAmount=$("#"+tmpTotalAmount).val();
	var totalAmount=$("#"+totalAmount).val();
	var lr=$("#"+liren);

	 if(tmpTotalAmount!=null&&totalAmount!=null){
	   var count=Subtr(tmpTotalAmount,totalAmount);   
	   lr.val(count);
	 }	
}
	
	//退票客规--审核
function onchangeTransRule(divId) {	
		//alert("onchangeTransRule-----divId:"+divId);
	    var id=$('#oId'+divId).val();
	    var cyr=$('#cyr'+id).val();//承运人
	    var flightClass=$('#flightClass'+id).val();//舱位
	   // alert("oIdValue:"+oIdValue);
	    var ticketPrice=$('#ticketPrice'+id).val();//票面价
	    var handlingCharge=$('#handlingCharge'+divId);//手续费
	    var transRuleSelectObj=$('#transRuleSelectObj'+divId).val();//客规    
	    var totalPerson=$('#totalPerson'+id).val();//人数
	    var oldPassengerNum=$('#oldPassengersCount'+divId).val();//原来人数
	   // alert(totalPerson);
	   //alert(oldPassengerNum);
	    if(totalPerson==null){ 
	       totalPerson=1;
	    }
	   if(ticketPrice!=null){ 
		    // alert(ticketPrice+'---'+transRuleSelectObj);
		    var handlingChargeValue =getRetireHandlinngCharge(id,cyr,flightClass,ticketPrice,transRuleSelectObj,totalPerson);	
		    //alert("onchangeTransRule:"+handlingChargeValue);
		    handlingCharge.val(handlingChargeValue);  
		  
		  	var temptotalAmount=$('#TmptotalAmount'+divId).val();
	      	var totalAmount=$('#totalAmount'+divId);//
	    	if (temptotalAmount * 1 > 0) {
	        	 var totalAmountValue=getRetireReceiveAmount(temptotalAmount,oldPassengerNum,totalPerson,handlingChargeValue);
	        	 totalAmount.val(totalAmountValue);
	   		} 
    	}
}	
	
//退废应收金额=(卖出金额/原来人数*退费人数)-手续费   
function getRetireReceiveAmount(oldSaleAmount,oldPassengerNum,nowPassengerNum,handlingChargeValue){
	var cellSaleAmount=accDiv(1*oldSaleAmount,1*oldPassengerNum);
	// alert(cellSaleAmount);
	 var totalAmount =1*cellSaleAmount * 1*nowPassengerNum -1*handlingChargeValue;   
	 //alert(totalAmount);
     totalAmount= getShowDecimal(totalAmount+"",1);//直接获取一位小数,截掉后面的
	return totalAmount;
}

//计算退票手续费=票面价*客规*退票人数	  
function getRetireHandlinngCharge(id,cyr,flightClass,ticketPrice,transRule,passengersCount){
	//alert("---getRetireHandlinngCharge()");
	var handlingCharge="0";
    handlingCharge = ticketPrice * transRule/100;//手续费计算=票面价*客规*退费人数	   
	handlingCharge = handlingCharge * Math.abs(passengersCount);
	handlingCharge=ForDight(handlingCharge,2);//保留二位小数
	handlingCharge=specialRetireRule(id,cyr,flightClass,handlingCharge);
	return handlingCharge;
}	
	
function specialRetireRule(id,cyr,flightClass,handlingCharge){
		var memo="正常手续费：";		
		 if(cyr=='MU' || cyr=='FM'){//东航、上航特殊手续费计算			     
        	if(id!=null&&id>0){	  
        	//	alert(flightClass);   
	        	if(flightClass!=null){
	        		if(flightClass.indexOf("F")!=-1||flightClass.indexOf("C")!=-1||flightClass.indexOf("Y")!=-1){
	        			alert("东航、上航"+flightClass+"舱,手续费为0");
	        			handlingCharge=0;
	        		}else{
	        			if(cyr=='MU'){
	        				handlingCharge=ForDight2(handlingCharge,-1);//保留整数
	        				if(1*handlingCharge<50){
		        				alert("东航手续费规则：金额不小于50,大于50的整数进位收取");
		        				handlingCharge=50;		        						
		        			}
	        			}
	        			if(cyr=='FM'){
	        				handlingCharge=ForDight2(handlingCharge,0);//根据个位进位
	        				//alert("1-"+handlingCharge);
	        				if(1*handlingCharge<50){
		        				alert("上航手续费规则：金额不小于50,大于50的第一位小数进位收取");
		        				handlingCharge=50;		     						
		        			}
	        			}
	        		}	        		
        		}   
        	}
        }
       //alert(memo+handlingCharge);        
	   return handlingCharge;
}


//备注
function showDiv11(oId){	  
	  $('#oId11').val(oId);
	  var  memo=$('#memo'+oId).val()
	  $('#memo11').val(memo);
	  $('#dialog11').dialog('open');
	  $('#dialog11').draggable({cancle: 'form'}); 
}