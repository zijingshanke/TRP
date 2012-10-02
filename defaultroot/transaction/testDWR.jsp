<%@ page language="java" pageEncoding="utf-8"%>

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
		<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
		
		
		
		<script type="text/javascript">
		
		   $(function(){
		   	
				platComAccountStore.getPlatFormList(getData2);		   
		        platComAccountStore.getTeamAgentList(getData3);	
		   	 function getData2(data2)
			   {
			   		for(var i=0;i<data2.length;i++)
			   		{
			   			document.forms[0].platformId.options[i] = new Option(data2[i].name,data2[i].id);
			   		}
			   }
			   
			   	 function getData3(data3)
			   {
			  
			   		for(var i=0;i<data3.length;i++)
			   		{
			   			document.forms[0].s001.options[i] = new Option(data3[i].name,data3[i].id);
			   		}
			   }
		   });
		  
			function checkPlatform()//点击交易平台名称
			{
				var platformId = document.forms[0].platformId.value;
				platComAccountStore.getPlatComAccountListByPlatformId(platformId,getData)
				setTimeout("checkCompany()",100);
			}
			function getData(data)
			{
				document.all.companyId.options.length=0;
				//document.forms[0].companyId.options[0]= new Option("请选择",0);
				for(var i=0;i<data.length;i++)
				{
					document.forms[0].companyId.options[i] = new Option(data[i].company.name,data[i].company.id);
				}
			}
			function checkCompany() //点击公司名称
			{
				var companyId =document.forms[0].companyId.value;
				var platformId = document.forms[0].platformId.value;
				platComAccountStore.getPlatComAccountListByCompanyId(companyId,platformId,getData1)
			}
			function getData1(data1)
			{
				if(data1.length<1)
				{
					document.all.accountId.options.length=0;
					document.forms[0].accountId.options[0]= new Option("请选择");
				}
				document.all.accountId.options.length=0;
				//document.forms[0].accountId.options[0]= new Option("请选择",0);
				for(var i=0;i<data1.length;i++)
				{
					if(data1[i].name != null || data1[i].name != "")
					{
						document.forms[0].accountId.options[i] = new Option(data1[i].account.name,data1[i].account.id);
					}
				}
			}
		</script>
		
	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
				<html:form action="/transaction/platComAccountList.do">
					<html:hidden property="thisAction" />
					<html:hidden property="lastAction" />
					<html:hidden property="intPage" />
					<html:hidden property="pageCount" />
					<input type="hidden" name="locate"
						value="<c:out value="${locate}"></c:out>" />
						
						<table align="center">
							
							<tr>
								<td>
										平台：
								</td>
								<td>
									<html:select property="platformId" styleClass="colorblue2 p_5"
										style="width:200px;" onclick="checkPlatform()">		
												<option value="">请选择</option>															
									</html:select>
								</td>	
								<td>
									公司：
								</td>
								<td>
									<html:select property="companyId" styleClass="colorblue2 p_5"
										style="width:200px;" onclick="checkCompany()">		
										<option value="">请选择</option>								
									</html:select>
								</td>
								<td>
									账号：
								</td>
								<td>
									<html:select property="accountId" styleClass="colorblue2 p_5"
										style="width:200px;" >		
										<option value="">请选择</option>								
									</html:select>
								</td>
								
								
								<td>
									客户：
								</td>
								<td>
									
									<select id="s001">
									   <option value="">请选择</option>	
									</select>
									
								</td>
							</tr>
						</table>
					
				</html:form>
			</div>
		</div>
	</body>
</html>
