<%@ page language="java" pageEncoding="utf-8"%>

  <html>
						   <tr>
								<td>
										平台：
								</td>
								<td>
									<select name="platformId" id="platform_Id" onchange="loadCompanyListByType('platform_Id','company_Id','account_Id','2')" class="text ui-widget-content ui-corner-all">		
												<option value="">请选择</option>															
									</select>
									
								</td>
								</tr>	
								<tr>
								<td>
									公司：
								</td>
								<td>
									<select name="companyId" id="company_Id" onchange="loadAccountByType('platform_Id','company_Id','account_Id','2')" class="text ui-widget-content ui-corner-all">		
										<option value="">请选择</option>								
									</select>
								</td>
									</tr>	
								<tr>
								<td>
									账号：
								</td>
								<td>
									<select name="accountId" id="account_Id" class="text ui-widget-content ui-corner-all">		
										<option value="">请选择</option>								
									</select>
								</td>
								
								</tr>
							
					
	<script type="text/javascript">
		
				  //loadPlatList('platform_Id','company_Id','account_Id');	   

		</script>
		
</html>
