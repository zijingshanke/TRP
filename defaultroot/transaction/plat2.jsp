<%@ page language="java" pageEncoding="utf-8"%>

  <html>
						   <tr>
								<td>
										平台：
								</td>
								<td>
									<select name="platformId9" id="platform_Id9" onchange="loadCompanyListByType('platform_Id9','company_Id9','account_Id9','2')" class="text ui-widget-content ui-corner-all">		
												<option value="">请选择</option>															
									</select>
									
								</td>
								</tr>	
								<tr>
								<td>
									公司：
								</td>
								<td>
									<select name="companyId9" id="company_Id9"  onchange="loadAccountByType('platform_Id9','company_Id9','account_Id9','2')" class="text ui-widget-content ui-corner-all">		
										<option value="">请选择</option>								
									</select>
								</td>
									</tr>	
								<tr>
								<td>
									账号：
								</td>
								<td>
									<select name="accountId9" id="account_Id9"  class="text ui-widget-content ui-corner-all">		
										<option value="">请选择</option>								
									</select>
								</td>
								
								</tr>
							
					
	<script type="text/javascript">
		
          // loadPlatList('platform_Id9','company_Id9','account_Id9');
		</script>
		
</html>
