<%@ page language="java" pageEncoding="utf-8"%>

  <html>
						   <tr>
								<td>
										平台：
								</td>
								<td>
									<select name="platformId" onclick="checkPlatform()" class="text ui-widget-content ui-corner-all">		
												<option value="">请选择</option>															
									</select>
									
								</td>
								</tr>	
								<tr>
								<td>
									公司：
								</td>
								<td>
									<select name="companyId"  onclick="checkCompany()" class="text ui-widget-content ui-corner-all">		
										<option value="">请选择</option>								
									</select>
								</td>
									</tr>	
								<tr>
								<td>
									账号：
								</td>
								<td>
									<select name="accountId"  class="text ui-widget-content ui-corner-all">		
										<option value="">请选择</option>								
									</select>
								</td>
								
								</tr>
							
					
	<script type="text/javascript">
		
				platComAccountStore.getPlatFormList(getData2);		   
		    
		   	  function getData2(data2)
			   {
			   		for(var i=0;i<data2.length;i++)
			   		{		
			   			
			   			document.all.platformId.options[i] = new Option(data2[i].name,data2[i].id);
			   			
			   		}
			   		setTimeout("checkPlatform()",100);
			   }
			   
			
		  
			function checkPlatform()//点击交易平台名称
			{
				var platformId = document.all.platformId.value;
				platComAccountStore.getPlatComAccountListByPlatformId(platformId,getData)
				setTimeout("checkCompany()",100);
			}
			function getData(data)
			{
				document.all.companyId.options.length=0;
				document.all.companyId.options[0]= new Option("请选择",0);
				for(var i=0;i<data.length;i++)
				{
					document.all.companyId.options[i] = new Option(data[i].company.name,data[i].company.id);
				}
			}
			function checkCompany() //点击公司名称
			{
				var companyId =document.all.companyId.value;
				var platformId = document.all.platformId.value;
				platComAccountStore.getPlatComAccountListByCompanyId(companyId,platformId,getData1)
			}
			function getData1(data1)
			{
				if(data1.length<1)
				{
					document.all.accountId.options.length=0;
					document.all.accountId.options[0]= new Option("请选择");
				}
				document.all.accountId.options.length=0;
				document.all.accountId.options[0]= new Option("请选择",0);
				for(var i=0;i<data1.length;i++)
				{
					if(data1[i].name != null || data1[i].name != "")
					{
						document.all.accountId.options[i] = new Option(data1[i].account.name,data1[i].account.id);
					}
				}
			}
		</script>
		
</html>
