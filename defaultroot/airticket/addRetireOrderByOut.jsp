<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
 %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />
		<script src="../_js/common.js" type="text/javascript"></script>
		<script src="../_js/popcalendar.js" type="text/javascript"></script>
        <script src="../_js/common.js" type="text/javascript"></script>
		<script src="../_js/popcalendar.js" type="text/javascript"></script>
		<script type='text/javascript' src='<%=path %>/dwr/interface/platComAccountStore.js'></script>
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
		<script type="text/javascript" src="../_js/tsms/loadAccount.js"></script>		
		
	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
				<html:form action="/airticket/airticketOrder.do">
						<html:hidden property="forwardPage" value="addRetireOrder"/>
						<input type="hidden" id="pnrNo" value="<c:out value="${airticketOrder.subPnr}" />"/>
						<input type="hidden" name="id" value="<c:out value="${airticketOrder.id}" />"/>
					<table width="100%" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td width="10" height="10" class="tblt"></td>
							<td height="10" class="tbtt"></td>
							<td width="10" height="10" class="tbrt"></td>
						</tr>
						<tr>
							<td width="10" class="tbll"></td>
							<td valign="top" class="body">									
								<c:import url="../_jsp/mainTitle.jsp" charEncoding="UTF-8">
									<c:param name="title1" value="票务管理" />
									<c:param name="title2" value="外部导入退废订单" />									
								</c:import>

								<div class="searchBar">
									<table cellpadding="0" cellspacing="0" border="0"
										class="searchPanel">
										<tr>
											<td>
												PNR:
												<html:text property="pnr" styleClass="colorblue2 p_5" value="${airticketOrder.subPnr}"
													style="width:120px;"  />
											<span> <input id="radOutSidePNR" type="radio" style="width: 15px;" name="ImportType" value="radOutSidePNR"/>
                                                  外部 PNR 导入</span>
                                          <input id="radInSidePNR" type="radio" checked="checked" style="width: 15px;" name="ImportType" value="radInSidePNR"/>
                                                   内部 PNR 导入 		
											</td>
											<td>
												<input type="button" name="button" id="button" value="导入"
													class="submit greenBtn" onclick=" getPNRinfo()" />
													<a href="#" onclick="showDiv()">	  [黑屏信息解析]  </a>
												<a href="../airticket/addOrderByHand.jsp">	[手工录入]</a>
											</td>
										</tr>
									</table>
									<hr />
								</div>
								行程
								<table width="100%" cellpadding="0" cellspacing="0" border="0"
									class="dataList">
									<tr>

										<th>
											<div>
												PNR
											</div>
										</th>
										
										<th>
											<div>
												航空公司
											</div>
										</th>
										<th>
											<div>
												航班号
											</div>
										</th>
										<th>
											<div>
												航段
											</div>
										</th>										
										<th>
											<div>
												乘机日期
											</div>
										</th>
										<th>
											<div>
												舱位
											</div>
										</th>
										<th>
											<div>
												折扣
											</div>
										</th>
										<th>
											<div>
												选择
												<input type="checkbox" name="fcheckbox" id="fcheckbox" checked="checked"  onclick="Quitfcheckbox()">
											</div>
										</th>
									</tr>								
                                   <c:forEach var="flight" items="${airticketOrder.flights}" varStatus="status">
									<tr>
										<td>
											<c:out value="${airticketOrder.subPnr}" />
										</td>
										<td>
                                            <c:out value="${flight.cyr}" />
										</td>
										<td>
                                              <c:out value="${flight.flightCode}" />
                                              <input type="hidden" name="flightCodes" value="<c:out value="${flight.flightCode}" />"/>
                                          
										</td>
										<td>                                        	
                                              <c:out value="${flight.startPoint}" />-
                                              <c:out value="${flight.endPoint}" />                                               
                                              <input type="hidden" name="startPoints" value="<c:out value="${flight.startPoint}" />"/>
                                              <input type="hidden" name="endPoints" value="<c:out value="${flight.endPoint}" />"/>
										</td>									
										<td>
										     <c:out value="${flight.boardingTime}" />
                                             <input type="hidden" name="boardingTimes" value="<c:out value="${flight.boardingTime}" />"/>
										</td>
										<td>									
										 <c:out value="${flight.flightClass}" />
										 <input type="hidden" name="flightClasss" value="<c:out value="${flight.flightClass}" />"/>                                        
										</td>
										<td>
										
                                              <c:out value="${flight.discount}" />
                                           <input type="hidden" name="discounts" value="<c:out value="${flight.discount}" />"/>
										</td>
										<td>
										<input type="checkbox"   id="flightIds<c:out value="${status.count-1}" />" name="flightIds" value="<c:out value="${status.count-1}" />"   checked="checked"/>
                                       
										</td>
										
									</tr>
									</c:forEach>
									
								</table>
								乘客
								<table width="100%" cellpadding="0" cellspacing="0" border="0"	class="dataList">
								   <tr>
								   <th>	<div>乘客姓名</div></th>
								   <th> <div>证件号</div></th>	
								   <th>	<div>票号</div></th>
								   <th> <div>选择 <input type="checkbox" name="pcheckbox" checked="checked" onclick="Quitpcheckbox()"/> </div></th>	
								   </tr>
								   <c:forEach var="passenger" items="${airticketOrder.passengers}" varStatus="status">
								    <tr>
								      <td><c:out value="${passenger.name}" />
								        <input type="hidden" name="passengerNames" value="<c:out value="${passenger.name}" />"/>
								      </td>
								       <td>
								       <c:out value="${passenger.cardno}" />
								       <input type="hidden" name="passengerCardno" value="<c:out value="${passenger.cardno}" />"/>
								       </td>
								        <td><c:out value="${passenger.ticketNumber}" />
								        <input type="hidden" name="passengerTicketNumber" value="<c:out value="${passenger.ticketNumber}" />"/>
								        </td>
								         <td><input type="checkbox"  name="passengerIds" id="passengerIds<c:out value="${status.count-1}" />"  value="<c:out value="${status.count-1}" />"  checked="checked"></td>
								    </tr>
								   </c:forEach>
								</table>
								<table>
									<tr>
									<td>类型</td>
										<td>											
									<select  Class="colorblue2 p_5" name="tranType" id="tranType" onchange="checktranType()">											
										<option value="3">退票</option>	
										<option value="4">废票</option>						
									</select>
									<select  Class="colorblue2 p_5" name="returnReason" id="memo">											
									<option value="0">--请选择--</option>
									<option value="取消">取消</option>
									<option value="航班延误">航班延误</option>
									<option value="重复付款">重复付款</option>
									<option value="申请病退">申请病退</option>
									<option value="多收票款">多收票款</option>	
									<option value="只退税">只退税</option>
									<option value="升舱换开">升舱换开</option>
									<option value="客规">客规</option>
									</select>
										</td>
										
										<td>
											平台</td><td>	
										<html:select property="platformId" styleClass="colorblue2 p_5" styleId="platform_Id"
										style="width:150px;" onchange="loadCompanyListByType('platform_Id','company_Id','account_Id','2')">		
												<option value="">请选择</option>															
									</html:select>
										</td>
										<td>
											公司</td><td>
											<html:select property="companyId" styleClass="colorblue2 p_5" styleId="company_Id"
										style="width:150px;" onchange="loadAccountByType('platform_Id','company_Id','account_Id','2')">		
										<option value="">请选择</option>								
									</html:select>
										</td>
										<td>帐号</td>
										<td>											
											<html:select property="accountId" styleClass="colorblue2 p_5" styleId="account_Id"
										style="width:100px;" >		
										<option value="">请选择</option>								
									</html:select>
										</td>
										
										
										<td>
											订单号
											<html:text property="airOrderNo" styleClass="colorblue2 p_5"
												style="width:100px;" />
										</td>
										<td>
											大PNR
											<html:text property="bigPnr" styleClass="colorblue2 p_5"
												style="width:100px;" />
										</td>
										<td>
											出票PNR
											<html:text property="drawPnr" styleClass="colorblue2 p_5"
												style="width:100px;" />
										</td>
									
										<td>
										<c:if test="${empty airticketOrder.addType}">
											<input name="label" type="button" class="button1" value="创 建" onclick="add();">
												
										</c:if>
										<c:if test="${!empty airticketOrder.addType}">
											<input name="label" type="button" class="button1" value="创 建" onclick="addOutPnr();">
												
										</c:if>
										<c:out value="${airticketOrder.addType}"></c:out>
										</td>

									</tr>
								</table>


							</td>
						</tr>
					</table>
			
				</html:form>
			</div>
			
 <div id="dialog" title="PNR信息导入">
		<p id="validateTips"></p>
	<form action="../airticket/airticketOrder.do?thisAction=airticketOrderByBlackOutPNR"  method="post" id="form3" >
		<fieldset>
		      <html:hidden property="forwardPage" value="addRetireOrderByOut"/>
		  	    <table>
		     <tr>
		    
		     <td>
		      <textarea rows="15" cols="90" name="pnrInfo"></textarea>
		     
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
		</div>
		<script type="text/javascript">
		      function getPNRinfo(){
		      
		         var pnr = document.forms[0].pnr.value;
		         var ImportType=$("input:radio[name='ImportType'][checked]").val();
		         if(pnr==""){
		              alert("请正确填写PNR!");
		              return false;
		         }
                if(ImportType=="radOutSidePNR"){
                
		            document.forms[0].action="airticketOrder.do?thisAction=airticketOrderByOutPNR";
                    document.forms[0].submit();
		         }else if(ImportType=="radInSidePNR"){
		        
		           document.forms[0].action="airticketOrder.do?thisAction=getAirticketOrderForRetireUmbuchen&businessType=2&tranType=2";
                   document.forms[0].submit();
		         }
                 
		      }
		      //是否只包含数字
				function isNum(b){
				   		var re=/^([1-9][0-9]*|0)(\.[0-9]{0,2})?$/;
				   		return(re.test(b));
				}
				
			//内部pnr添加  
		   function add(){		      
			     var pnr = document.forms[0].pnrNo.value;
			     var airOrderNo = document.forms[0].airOrderNo.value;
			     var drawPnr = document.forms[0].drawPnr.value;		      
			       
			     if(pnr==""){
			        alert("请先导入PNR!");
			        return false;
			     }
			     if(airOrderNo==""){
			       alert("请正确填写订单号!");
			       return false;
			     }
			     if(drawPnr==""){
					alert("请正确填写出票pnr!");
					return false;
				}  
			    document.forms[0].action="airticketOrder.do?thisAction=addRetireOrder";
	            document.forms[0].submit();
		  }
		  
		    //黑屏或外部pnr添加  
		   function addOutPnr(){
		         var airOrderNo = document.forms[0].airOrderNo.value;
		         var drawPnr = document.forms[0].drawPnr.value;		      
		         if(airOrderNo==""){
		             alert("请正确填写订单号!");
		             return false;
		         }
		         if(drawPnr==""){
				     alert("请正确填写出票pnr!");
				     return false;
				 }  				 
		      document.forms[0].action="airticketOrder.do?thisAction=addOutRetireOrder";
              document.forms[0].submit();
		  }
		      
		  function checktranType(){		      
		      var tranType=document.forms[0].tranType.value;
		       
		      if(tranType==3){
		         document.getElementById("memo").disabled=false; 
		      }else if(tranType==4){		            
		             document.getElementById("memo").disabled=true;
		             document.getElementById("memo").selectedIndex=0;
		      }
		}
		
		//反选和全选
		 function Quitfcheckbox(){		         
		     var fb= document.getElementsByName("fcheckbox")[0].checked;  
		     var fbox= document.getElementsByName("flightIds");  
		         
		     for(var i=0;i<fbox.length;i++){		        
		        document.getElementsByName("flightIds")[i].checked=fb;  
		     }
		        // checkFilebox();
		}
		     
		 //验证是否选中
		 function checkFilebox(){
		    var fbox= document.getElementsByName("flightIds"); 
		    for(var i=0;i<fbox.length;i++){
		       if(fbox[i].checked==false){
		         //   $("#flightIds"+i).attr("value","0");
		          }else{
		         //  $("#flightIds"+i).attr("value","1");
		          }		         
		       }
		 }
		     
		  //反选和全选
		 function Quitpcheckbox(){		    
		     var pc= document.getElementsByName("pcheckbox")[0].checked;  
		     var fbox= document.getElementsByName("passengerIds");  		         
		     for(var i=0;i<fbox.length;i++){		        
		      	document.getElementsByName("passengerIds")[i].checked=pc;  
		     }
		    // checkPassengerbox();
		 } 
		    
		      //验证是否选中
		  function checkPassengerbox(){
		         var fbox= document.getElementsByName("passengerIds"); 
		         for(var i=0;i<fbox.length;i++){
		              if(fbox[i].checked==false){
		               // $("#passengerIds"+i).attr("value","0");
		              }else{
		              // $("#passengerIds"+i).attr("value","1");
		              }		         
		         }
		   }
		     
		  $(function(){		  
		     //  loadPlatList('platform_Id','company_Id','account_Id');
		        loadPlatListByType('platform_Id','company_Id','account_Id','2');
			$("#dialog").dialog({
				bgiframe: true,
				autoOpen: false,
				height: 550,
				width:650,
				modal: true
		    });
		    });
	
		 //黑屏导入
		 function showDiv(){
			  $('#dialog').dialog('open');			 
		}	
		</script>
	</body>
</html>
