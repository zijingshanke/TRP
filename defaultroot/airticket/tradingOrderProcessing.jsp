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
		
		var pnr = $("#pnr"),
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

		}
		
		$("#dialog").dialog({
			bgiframe: true,
			autoOpen: false,
			//height: 500,
			//width:300,
			modal: true,
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
			},*/
			close: function() {
				allFields.val('').removeClass('ui-state-error');
			}
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
	});
	
	//申请支付
	function showDiv9(oId,suPnr,airOrderNo,totalAmount,rebate){
	 
	  $('#oId9').val(oId);
	  $('#pnr9').val(suPnr);
	  $('#airOrderNo9').val(airOrderNo);
	  $('#totalAmount9').val(totalAmount);
	  $('#rebate9').val(rebate);
	  $('#dialog9').dialog('open');
	 
	}
	//确认支付
	function showDiv(oId,suPnr,airOrderNo,totalAmount,rebate){
	 
	  $('#oId').val(oId);
	  $('#pnr1').val(suPnr);
	  $('#airOrderNo1').val(airOrderNo);
	  $('#totalAmount1').val(totalAmount);
	  $('#rebate1').val(rebate);
	  $('#dialog').dialog('open');
	 
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
	   $('#groupMarkNo').val(groupMarkNo);
	  $('#dialog2').dialog('open');
	 
	}
	
	function getTempPNR(){
	   
	   
	tempPNRBizImp.getTempPNRByPnr("TBP3G",function(list){
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
	
 //取消出票
 function showDiv8(oId,tranType,groupMarkNo){

	  $('#oId8').val(oId);
	  $('#tranType8').val(tranType);
	  $('#groupMarkNo').val(groupMarkNo);
	  $('#dialog8').dialog('open');
	 
	}	
 //审核
 function showDiv3(oId,tranType,groupMarkNo){

	  $('#oId3').val(oId);
	  $('#tranType3').val(tranType);
	  $('#groupMarkNo').val(groupMarkNo);
	  $('#dialog3').dialog('open');
	 
	}

 //审核
 function showDiv7(oId,tranType,groupMarkNo){

	  $('#oId7').val(oId);
	  $('#tranType7').val(tranType);
	  $('#groupMarkNo').val(groupMarkNo);
	  $('#dialog7').dialog('open');
	 
	}

//确认收款
 function showDiv4(oId,suPnr,groupMarkNo){

	  $('#oId4').val(oId);
	  $('#tranType4').val(suPnr);
	  $('#groupMarkNo').val(groupMarkNo);
	  $('#dialog4').dialog('open');
	 
	}
	
//申请改签
 function showDiv5(oId,suPnr,groupMarkNo){

	  $('#oId5').val(oId);
	  $('#tranType5').val(suPnr);
	  $('#groupMarkNo').val(groupMarkNo);
	  $('#dialog5').dialog('open');
	 
	 platComAccountStore.getPlatFormList(function(data){
			   		for(var i=0;i<data.length;i++)
			   		{		
			   			
			   			document.all.platformId2.options[i] = new Option(data[i].name,data[i].id);
			   			
			   		}
			   		
			   });
	 
	}
	
	
	//申请改签
 function showDiv6(oId,suPnr,groupMarkNo){

	  $('#oId6').val(oId);
	  $('#tranType6').val(suPnr);
	  $('#groupMarkNo').val(groupMarkNo);
	  $('#dialog6').dialog('open');
	 
	 platComAccountStore.getPlatFormList(function(data){
			   		for(var i=0;i<data.length;i++)
			   		{		
			   			
			   			document.all.platformId6.options[i] = new Option(data[i].name,data[i].id);
			   			
			   		}
			   		setTimeout("checkPlatform6()",100);
			   });
	 
	 }
	
	function checkPlatform6()//点击交易平台名称
			{
				var platformId6 = document.all.platformId6.value;
				platComAccountStore.getPlatComAccountListByPlatformId(platformId6,getData6)
				setTimeout("checkCompany6()",100);
			}
			function getData6(data6)
			{
				document.all.companyId6.options.length=0;
				document.all.companyId6.options[0]= new Option("请选择",0);
				for(var i=0;i<data6.length;i++)
				{
					document.all.companyId6.options[i] = new Option(data6[i].company.name,data6[i].company.id);
				}
			}
			function checkCompany6() //点击公司名称
			{
				var companyId6 =document.all.companyId6.value;
				var platformId6 = document.all.platformId6.value;
				platComAccountStore.getPlatComAccountListByCompanyId(companyId6,platformId6,getData61)
			}
			function getData61(data61)
			{
				if(data61.length<1)
				{
					document.all.accountId6.options.length=0;
					document.all.accountId6.options[0]= new Option("请选择");
				}
				document.all.accountId6.options.length=0;
				document.all.accountId6.options[0]= new Option("请选择",0);
				for(var i=0;i<data61.length;i++)
				{
					if(data61[i].name != null || data61[i].name != "")
					{
						document.all.accountId6.options[i] = new Option(data61[i].account.name,data61[i].account.id);
					}
				}
			}
		
	//设置退票原因		
	function submitForm8(){
	   
	    var  rbtnReason= $("input[name='rbtnReason']:checked").val();
	    var  rbtnType= $("input[name='rbtnType']:checked").val();
        var  cause=$("#cause").val(); 
        $("input[name='memo']").val(rbtnType+","+rbtnReason+","+cause);
	    return true;
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
										
								<c:if test="${info.ticketType==1 && info.tranType==2 &&info.status==1}">
								     <a   onclick="showDiv8('<c:out value='${info.id}' />','<c:out value='${info.subPnr}'/>')"  href="#">                    
		                        [取消出票]</a>
		                        <br>
								   <td>
								   <a   onclick="showDiv9('<c:out value='${info.id}' />','<c:out value='${info.subPnr}'/>','<c:out value='${info.airOrderNo}'/>','<c:out value='${info.statement.totalAmount}'/>','<c:out value='${info.rebate}'/>')"  href="#">                    
		                        [申请支付	]</a>
								   </td>
									
								</c:if>		
								
								<c:if test="${info.ticketType==1 && info.tranType==2 &&info.status==2||info.status==8}">
								     <a   onclick="showDiv8('<c:out value='${info.id}' />','<c:out value='${info.subPnr}'/>')"  href="#">                    
		                        [取消出票]</a>
		                        <br>
								   
								<a href="<%=path %>/airticket/airticketOrder.do?thisAction=updateOrderStatus&status=7&id=<c:out value='${info.id}' />">                     
		                        [锁定]</a>
		                        <br>	
								</c:if>	
								
								
									
								<c:if test="${info.ticketType==1 && info.tranType==2 &&info.status==7}">
								     <a   onclick="showDiv8('<c:out value='${info.id}' />','<c:out value='${info.subPnr}'/>')"  href="#">                    
		                        [取消出票]</a>
		                        <br>
		                        <a href="<%=path %>/airticket/airticketOrder.do?thisAction=updateOrderStatus&status=8&id=<c:out value='${info.id}' />">                     
		                        [解锁]</a><br>
								   <td>
								   <a   onclick="showDiv('<c:out value='${info.id}' />','<c:out value='${info.subPnr}'/>','<c:out value='${info.airOrderNo}'/>','<c:out value='${info.statement.totalAmount}'/>','<c:out value='${info.rebate}'/>')"  href="#">                       
		                        [确认支付]</a>
								   </td>
								</c:if>	
								
								<c:if test="${info.ticketType==1 && info.tranType==2 &&info.status==4}">
		                      
		                    	<a href="<%=path %>/airticket/airticketOrder.do?thisAction=updateOrderStatus&status=6&id=<c:out value='${info.id}' />"> 
		                    	[确认退款]</a>
								</c:if>	
									
							  	<c:if test="${info.ticketType==1 && info.tranType==1 &&info.status==3}">
		                        <a    href="#" onclick="showDiv2('<c:out value='${info.id}' />','<c:out value='${info.subPnr}'/>','<c:out value='${info.groupMarkNo}'/>')">                    
		                        [出票]</a>
		                        <br>
		                    	   <a   onclick="showDiv8('<c:out value='${info.id}' />','<c:out value='${info.tranType}'/>')"  href="#">   
		                    	[取消出票]</a>
								</c:if>			
								
								
							  	<c:if test="${info.ticketType==1 && info.tranType==1 &&info.status==4}">
		                      
		                    	<a href="<%=path %>/airticket/airticketOrder.do?thisAction=updateOrderStatus&status=6&id=<c:out value='${info.id}' />"> 
		                    	[确认退款]</a>
								</c:if>		
									
										
								<c:if test="${info.ticketType==3 && info.tranType==2 &&info.status==3}">
		                        <a    href="#" onclick="showDiv2('<c:out value='${info.id}' />','<c:out value='${info.subPnr}'/>','<c:out value='${info.groupMarkNo}'/>')">                    
		                        [出票]</a>
		                        <br>
		                    	<a href="#">[取消出票]</a>
								</c:if>	
								
								
								<!-- 退票-->
								<c:if test="${info.tranType==3 && info.status==19}">
								
								   <a href="#">                     
		                        [备注]</a>
		                        
		                   	   <td>
								   <a   onclick="showDiv3('<c:out value='${info.id}' />','<c:out value='${info.tranType}'/>')"  href="#">                    
		                        [通过申请]</a>
								   </td>
								</c:if>	
								
							<c:if test="${info.tranType==3 && info.status==20}">
								  
								 <a   onclick="showDiv7('<c:out value='${info.id}' />','<c:out value='${info.tranType}'/>')"  href="#">                  
		                        [通过申请]</a>
								   
								</c:if>	
								
							<c:if test="${info.tranType==3 && info.status==21 && info.statement.type==1}">
								  
								 <a href="#" onclick="showDiv4('<c:out value='${info.id}' />','<c:out value='${info.tranType}'/>','<c:out value='${info.groupMarkNo}'/>')">                
		                        [确认退款]</a>
								   
							</c:if>		
									
								<!-- 废票-->
								<c:if test="${info.tranType==4 && info.status==29}">
								
		                         <a href="#">                       
		                        [备注]</a>
		                        <br>
								   <td>
								   <a   onclick="showDiv3('<c:out value='${info.id}' />','<c:out value='${info.tranType}'/>')"  href="#">                    
		                        [通过申请]</a>
								   </td>
									
								</c:if>		
								
							<c:if test="${info.tranType==4 && info.status==30}">
								
								 <a   onclick="showDiv7('<c:out value='${info.id}' />','<c:out value='${info.tranType}'/>')"  href="#">                 
		                        [通过申请]</a>
								
								</c:if>		
								
							<c:if test="${info.tranType==4 && info.status==31 && info.statement.type==1}" >
								
						 <a href="#" onclick="showDiv4('<c:out value='${info.id}' />','<c:out value='${info.tranType}'/>','<c:out value='${info.groupMarkNo}'/>')">                                  
		                        [确认退款]</a>
								
								</c:if>	
								
								<!-- 确认收款 -->
								
							<c:if test="${info.tranType==3 && info.status==21 && info.statement.type==2}">
		                        <a    href="#" onclick="showDiv4('<c:out value='${info.id}' />','<c:out value='${info.tranType}'/>','<c:out value='${info.groupMarkNo}'/>')">                    
		                        [确认收款]</a>
								</c:if>	
								
								
							<c:if test="${info.tranType==4 && info.status==31 && info.statement.type==2}">
		                        <a    href="#" onclick="showDiv4('<c:out value='${info.id}' />','<c:out value='${info.tranType}'/>','<c:out value='${info.groupMarkNo}'/>')">                    
		                        [确认收款]</a>
								</c:if>			
								
							  <!-- 申请改签 -->
									<c:if test="${info.statement.type==1 && info.status==39}">
									 <a href="<%=path %>/airticket/airticketOrder.do?thisAction=updateOrderStatus&status=44&id=<c:out value='${info.id}' />">                    
		                           [拒绝申请]</a><br/>
		                     
									 <td>
								   <a   onclick="showDiv5('<c:out value='${info.id}' />','<c:out value='${info.tranType}'/>')"  href="#">                    
		                        [通过申请]</a>
								   </td>
									</c:if>
									
									
								<c:if test="${info.statement.type==1 && info.status==40}">
									 
								  <a href="<%=path %>/airticket/airticketOrder.do?thisAction=updateOrderStatus&status=41&id=<c:out value='${info.id}' />">                     
		                        [通过申请]</a>
								 
									</c:if>	
									
									<c:if test="${info.statement.type==1 && info.status==41}">
									 
								   <a   onclick="showDiv6('<c:out value='${info.id}' />','<c:out value='${info.tranType}'/>')"  href="#">                      
		                        [收款]</a>
								 
									</c:if>		
									
									<c:if test="${info.statement.type==1 && info.status==43}">
									 
								<a href="<%=path %>/airticket/airticketOrder.do?thisAction=updateOrderStatus&status=45&id=<c:out value='${info.id}' />">                      
		                        [确认收款]</a>
								 
									</c:if>		
									
								<!-- 申请改签 -->
									<c:if test="${info.statement.type==2 && info.status==42}">
									 
								   <a   onclick="showDiv6('<c:out value='${info.id}' />','<c:out value='${info.tranType}'/>')"  href="#">                    
		                        [付款]</a>
								 
									</c:if>	
								
								<c:if test="${info.statement.type==2 && info.status==43}">
									 
								 <a href="<%=path %>/airticket/airticketOrder.do?thisAction=updateOrderStatus&status=45&id=<c:out value='${info.id}' />">                    
		                        [确认付款]</a>
								 
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
	     <td><input type="text" name="totalAmount" id="totalAmount9" value="0"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
		
		  <tr>
	     <td><label for="password">政策</label></td>
	     <td><input type="text" name="rebate" id="rebate9"   value="0"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
	    <tr>
	     <td><label for="password">利润</label></td>
	     <td><input type="text" name="liruen" id="liruen"   value="0"  class="text ui-widget-content ui-corner-all" /></td>
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
	     <td><input type="text" name="totalAmount" id="totalAmount1" value="0"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
		
		  <tr>
	     <td><label for="password">政策</label></td>
	     <td><input type="text" name="rebate" id="rebate1"   value="0"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
	     <tr>
	     <td><label for="password">利润</label></td>
	     <td><input type="text" name="liruen" id="liruen"   value="0"  class="text ui-widget-content ui-corner-all" /></td>
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
	    <input id="groupMarkNo" name="groupMarkNo" type="hidden" />
	    出票PNR<input type="text" name="drawPnr" id="pnr2"  class="text ui-widget-content ui-corner-all" />
	    <input type="button" value="自动刷新" onclick="getTempPNR()"/> [黑屏刷新]
	    <table id="per">
	    <tbody>
		</tbody>
		</table>
		<input value="提交" type="submit" >
	</fieldset>
	</form>
</div>

<div id="dialog8" title="取消出票">
	<p id="validateTips"></p>
<form action="../airticket/airticketOrder.do?thisAction=quitTicket"  method="post" id="form8"  onsubmit="return submitForm8()">
	
	    <input id="oId8" name="id" type="hidden" />
	    <input id="groupMarkNo" name="groupMarkNo" type="hidden" />
	    
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




<div id="dialog3" title="审核">
	<p id="validateTips"></p>
<form action="../airticket/airticketOrder.do?thisAction=auditRetireTrading"  method="post" id="form3" >
	<fieldset>
	 <input id="oId3" name="id" type="hidden" />
	  <input id="tranType3" name="tranType" type="hidden" />
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
	     <td><input type="text" name="totalAmount" id="totalAmount" value="0"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
		 <tr>
	     <td><label for="password">手续费</label></td>
	     <td><input type="text" name="handlingCharge" id="handlingCharge"   value="0"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
	    <tr>
	     <td><label for="password">客规</label></td>
	     <td>
	       <select name="selTuiPercent" id="selTuiPercent">
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
		<input value="提交" type="submit" >
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
	     <td><input type="text" name="totalAmount" id="totalAmount" value="0"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
		 <tr>
	     <td><label for="password">手续费</label></td>
	     <td><input type="text" name="handlingCharge" id="handlingCharge"   value="0"  class="text ui-widget-content ui-corner-all" /></td>
	    </tr>
	    <tr>
	     <td><label for="password">客规</label></td>
	     <td>
	       <select name="selTuiPercent" id="selTuiPercent">
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
		<input value="提交" type="submit" >
		</td>
		</tr>
		   
		</table>
	</fieldset>
	</form>
</div>

<div id="dialog4" title="确认收款">
	<p id="validateTips"></p>
<form action="../airticket/airticketOrder.do?thisAction=collectionRetireTrading"  method="post" id="form3" >
	<fieldset>
	 <input id="oId4" name="id" type="hidden" />
	  <input id="tranType4" name="tranType" type="hidden" />
	  	    <table>
	     <tr>
	     <td><label for="password">实收金额</label></td>
	     <td><input type="text" name="statement.actualAmount" value="0"  class="text ui-widget-content ui-corner-all" /></td>
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

<div id="dialog5" title="改签审核">
	<p id="validateTips"></p>
<form action="../airticket/airticketOrder.do?thisAction=auditWaitAgreeUmbuchenOrder"  method="post" id="form3" >
	<fieldset>
	 <input id="oId5" name="id" type="hidden" />
	  <input id="tranType5" name="tranType" type="hidden" />
	  	    <table>
	    <tr>
	    <td>平台</td>
		<td>
	<select name="platformId2"  class="text ui-widget-content ui-corner-all">		
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
	<select name="platformId6" onclick="checkPlatform6()"  class="text ui-widget-content ui-corner-all">		
		<option value="">请选择</option>															
		</select>
		</td>
		</tr>
		
		  <tr>
	    <td>公司：</td>
		<td>
	<select name="companyId6" onclick="checkCompany6()"  class="text ui-widget-content ui-corner-all">		
		<option value="">请选择</option>															
		</select>
		</td>
		</tr>
		
	<tr>
	    <td>账号：</td>
		<td>
	<select name="accountId6"   class="text ui-widget-content ui-corner-all">		
		<option value="">请选择</option>															
		</select>
		</td>
		</tr>
		
	     <tr>
	     <td><label for="password">支付金额</label></td>
	     <td><input type="text" name="pnr" id="pnr"  class="text ui-widget-content ui-corner-all" /></td>
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
	</body>
</html>
