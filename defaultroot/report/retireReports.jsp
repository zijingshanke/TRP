<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="utf-8"%>

<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%
	String path = request.getContextPath();
 %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />
   	<script type='text/javascript' src='<%=path %>/dwr/interface/platComAccountStore.js'></script>
 	<script type='text/javascript' src='<%=path %>/dwr/engine.js'></script>
	<script type='text/javascript' src='<%=path %>/dwr/util.js'></script>
	<script src="../_js/common.js" type="text/javascript"></script>
	<script src="../_js/popcalendar.js" type="text/javascript"></script>
	<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
	</head>
	<body>	
		<div id="mainContainer">
			<div id="container">
				<html:form action="/report/listReports.do?thisAction=downLoadRetireReports">
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
								<c:import url="../_jsp/mainTitle.jsp?title1=付款管理&title2=退废报表"
									charEncoding="UTF-8" />

								<div class="searchBar">
									<table cellpadding="0" cellspacing="0" border="0"
										class="searchPanel">
										<tr>
											<td>
												操作员
												<html:text property="sysName" styleClass="colorblue2 p_5"
													style="width:100px;" />
											</td>
									       <td>
												选择日期:
												<html:text property="startDate" styleClass="colorblue2 p_5"
													style="width:150px;" onclick="popUpCalendar(this, this);"
													readonly="true" /> __
												<html:text property="endDate" styleClass="colorblue2 p_5"
													style="width:150px;" onclick="popUpCalendar(this, this);"
													readonly="true" />
											</td>
											<td>
												<input type="submit" name="button" id="button" value="导出"
													class="submit greenBtn" />
													
													
											</td>
										</tr>
									</table>
									<hr />
								</div>
						卖出平台：
						<table  cellpadding="0" cellspacing="0" border="0"	class="dataList">
									<tr>
							<c:forEach items="${toPlatformList}" var="platform" varStatus="sta">	
									
										<th>
									<div><input name="platformId" type="checkbox" value="<c:out value="${platform.id}"/>"/><c:out value="${platform.showName}"/></div>
										</th>
										
										<c:if test="${sta.count%8==0}">
										   </tr><tr>
										</c:if>
						  </c:forEach>	
						  </tr>			
					    </table>				    
					    
					    买入平台：
						<table  cellpadding="0" cellspacing="0" border="0"	class="dataList">
						<tbody id="div_demo1">										
								<tr>
									<th><font color="red"><input id="check_grp1" type="checkbox" onclick="selectGroup('div_demo1','check_grp1')" />平台</font></th>
							<c:forEach items="${formPlatformListByBSP}" var="platform2" varStatus="sta">	
									
										<th>
									<div><input name="platformId" type="checkbox" value="<c:out value="${platform2.id}"/>"/><c:out value="${platform2.showName}"/></div>
										</th>
										
										<c:if test="${sta.count%9==0}">
										   </tr><tr>
										</c:if>
						  </c:forEach>	
						  </tr>	
						  </tbody>
						    <tbody id="div_demo2">
						  		<tr>
									<th><FONT color="red"><input type="checkbox" id="check_grp2" onclick="selectGroup('div_demo2','check_grp2')"/>网电</FONT></th>
							<c:forEach items="${formPlatformListByB2B}" var="platform2" varStatus="sta">	
									
										<th>
									<div><input name="platformId" type="checkbox" value="<c:out value="${platform2.id}"/>"/><c:out value="${platform2.showName}"/></div>
										</th>
										
										<c:if test="${sta.count%9==0}">
										   </tr><tr>
										</c:if>
						  </c:forEach>	
						  </tr>		
						  </tbody>	
					    </table>
					    
					       收款帐号：
						<table cellpadding="0" cellspacing="0" border="0"	class="dataList">
					
						   <c:forEach items="${toAccountList}" var="account" varStatus="sta">
						  <tbody id="div_demo1<c:out value='${account.id}'/>">
						  <tr> 
						  		<th>
									<div><input id="check_grp1<c:out value='${account.id}'/>" onclick="selectGroup('div_demo1<c:out value='${account.id}'/>','check_grp1<c:out value='${account.id}'/>')" name="accountId" type="checkbox" value="<c:out value="${account.id}"/>"/>
									<FONT color="red"><c:out value="${account.name}"/></FONT></div>
										
										</th>
							<c:forEach items="${account.accounts}" var="ac">
							<th>
									<div><input name="accountId" type="checkbox" value="<c:out value="${ac.id}"/>"/><c:out value="${ac.name}"/></div>
										
										</th>
							</c:forEach>			
						  
						   </tr>
						   </tbody>
						  </c:forEach>
						  	
					    </table>
					    
					    
					     付款帐号：
						<table cellpadding="0" cellspacing="0" border="0"	class="dataList">
						  
						  <c:forEach items="${formAccountList}" var="account" varStatus="sta">
						  <tbody id="div_demo2<c:out value='${account.id}'/>">
						  <tr> 
						  		<th>
									<div><input id="check_grp2<c:out value='${account.id}'/>" onclick="selectGroup('div_demo2<c:out value='${account.id}'/>','check_grp2<c:out value='${account.id}'/>')" name="accountId" type="checkbox" value="<c:out value="${account.id}"/>"/>
									<FONT color="red"><c:out value="${account.name}"/></FONT></div>
										
										</th>
							<c:forEach items="${account.accounts}" var="ac">
							<th>
									<div><input name="accountId" type="checkbox" value="<c:out value="${ac.id}"/>"/><c:out value="${ac.name}"/></div>
										
										</th>
							</c:forEach>			
						  
						   </tr>
						   </tbody>
						  </c:forEach>
						  
						  	
					    </table>
				</html:form>
			</div>
		</div>
<script type="text/javascript">
		function uncheckGrp(grp_id)
		 {
		   document.getElementById(grp_id).checked = document.getElementById(grp_id).checked&0;
		   
		 }
		 function selectGroup(div_id,grp_id){
		
		  var g_div = document.getElementById(div_id);
		  var grp = document.getElementById(grp_id);
		  var eles = g_div.getElementsByTagName("input");
		//  alert(eles.length);
		  for(var i=0;i<eles.length;i++){
		   if(eles[i].disabled==false) eles[i].checked=grp.checked;
		  }
		 }
</script>
	</body>
</html>
