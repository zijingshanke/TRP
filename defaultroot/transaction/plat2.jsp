<%@ page language="java" pageEncoding="utf-8"%>
<%
	String currentObjId=request.getParameter("currentObjId");
	//System.out.println("currentObjId:"+currentObjId);
%>
 <html>
				 <tr>
								<td>
										平台：
								</td>
								<td>
									<select name="platformId" id="platform_Id<%=currentObjId%>" onchange="loadCompanyListByType('platform_Id<%=currentObjId%>','company_Id<%=currentObjId%>','account_Id<%=currentObjId%>','2')" class="text ui-widget-content ui-corner-all">		
												<option value="">请选择</option>															
									</select>
									
								</td>
								</tr>	
								<tr>
								<td>
									公司：
								</td>
								<td>
									<select name="companyId" id="company_Id<%=currentObjId%>"  onchange="loadAccountByType('platform_Id<%=currentObjId%>','company_Id<%=currentObjId%>','account_Id<%=currentObjId%>','2')" class="text ui-widget-content ui-corner-all">		
										<option value="">请选择</option>								
									</select>
								</td>
									</tr>	
								<tr>
								<td>
									账号：
								</td>
								<td>
									<select name="accountId" id="account_Id<%=currentObjId%>"  class="text ui-widget-content ui-corner-all">		
										<option value="">请选择</option>								
									</select>
								</td>
								
								</tr>	
		
</html>
