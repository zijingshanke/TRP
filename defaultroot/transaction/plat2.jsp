<%@ page language="java" pageEncoding="utf-8"%>

  <html>
						   <tr>
								<td>
										平台：
								</td>
								<td>
									<select name="platformId9" onclick="checkPlatform9()" class="text ui-widget-content ui-corner-all">		
												<option value="">请选择</option>															
									</select>
									
								</td>
								</tr>	
								<tr>
								<td>
									公司：
								</td>
								<td>
									<select name="companyId9"  onclick="checkCompany9()" class="text ui-widget-content ui-corner-all">		
										<option value="">请选择</option>								
									</select>
								</td>
									</tr>	
								<tr>
								<td>
									账号..：
								</td>
								<td>
									<select name="accountId9"  class="text ui-widget-content ui-corner-all">		
										<option value="">请选择</option>								
									</select>
								</td>
								
								</tr>
							
					
	<script type="text/javascript">
		
				platComAccountStore.getPlatFormList(getData9);		   
		    
		   	  function getData9(data2)
			   {
			   		for(var i=0;i<data2.length;i++)
			   		{		
			   			
			   			document.all.platformId9.options[i] = new Option(data2[i].name,data2[i].id);
			   			
			   		}
			   		setTimeout("checkPlatform9()",100);
			   }
			   
			
		  
			function checkPlatform9()//点击交易平台名称
			{
				var platformId9 = document.all.platformId9.value;
				platComAccountStore.getPlatComAccountListByPlatformId(platformId9,getData10)
				setTimeout("checkCompany9()",100);
			}
			function getData10(data)
			{
				document.all.companyId9.options.length=0;
				document.all.companyId9.options[0]= new Option("请选择",0);
				for(var i=0;i<data.length;i++)
				{
					document.all.companyId9.options[i] = new Option(data[i].company.name,data[i].company.id);
				}
			}
			function checkCompany9() //点击公司名称
			{
				var companyId9 =document.all.companyId9.value;
				var platformId9 = document.all.platformId9.value;
				platComAccountStore.getPlatComAccountListByCompanyId(companyId9,platformId9,getData11)
			}
			function getData11(data1)
			{
				if(data1.length<1)
				{
					document.all.accountId9.options.length=0;
					document.all.accountId9.options[0]= new Option("请选择");
				}
				document.all.accountId9.options.length=0;
				document.all.accountId9.options[0]= new Option("请选择",0);
				for(var i=0;i<data1.length;i++)
				{
					if(data1[i].name != null || data1[i].name != "")
					{
						document.all.accountId9.options[i] = new Option(data1[i].account.name,data1[i].account.id);
					}
				}
			}
		</script>
		
</html>
