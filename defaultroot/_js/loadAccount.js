	/////////////////////////////////////加载 1：卖出 2买入/////////////////////////////////////////////////////
	//加载平台list,最后一个参数1：卖出 2买入
	function loadPlatListByType(platformId,companyId,accountId,type){
		if(type!=null&&type=="1"){
			 platComAccountStore.getToPlatform(function(platList){
	    
	        var  platObj= document.getElementById(platformId);
	             platObj.options.length=0;
	         for(var i=0;i<platList.length;i++){		
		   		 option = new Option(platList[i].name,platList[i].id);
		         platObj.options.add(option);
		             
		         if(companyId!=null&&companyId!=""){
		           if(i==platList.length-1){
		             loadCompanyListByType(platformId,companyId,accountId,type);
		            }
		          }
		   	}	          
	    });	    
		}else if(type!=null&&type=="2"){
			 platComAccountStore.getFormPlatform(function(platList){
	    
	        var  platObj= document.getElementById(platformId);
	             platObj.options.length=0;
	         for(var i=0;i<platList.length;i++){		
		   		 option = new Option(platList[i].name,platList[i].id);
		         platObj.options.add(option);
		             
		         if(companyId!=null&&companyId!=""){
		           if(i==platList.length-1){
		              loadCompanyListByType(platformId,companyId,accountId,type);
		            }
		          }
		   	}	          
	    });	    
		}	   
	   }

     		  //加载公司
		  function loadCompanyListByType(platformId,companyId,accountId,type){
		   var platformValve = document.getElementById(platformId).value;
		   var  companyObj= document.getElementById(companyId);
		   platComAccountStore.getPlatComAccountListByPlatformId(platformValve,function(companyList){
		     
		        companyObj.options.length=0;
		        //option = new Option("请选择",0);
		        //companyObj.options.add(option);
		    
		       if(companyList.length>0){
		        for(var i=0;i<companyList.length;i++)
			   		{		
			   			 option = new Option(companyList[i].company.name,companyList[i].company.id);
			             companyObj.options.add(option);
			             
			          if(accountId!=null&&accountId!=""){
			            if(i==companyList.length-1){
			             loadAccountByType(platformId,companyId,accountId,type);
			             }
			             }
			   		}
			   	}else{
			   	
			   	option = new Option("请选择","");
		        companyObj.options.add(option);
		        if(accountId!=null&&accountId!=""){
			    loadAccount(platformId,companyId,accountId);
			    }
			   	}
		          
		   });
		     
		  }
		  
		  //加载账户
		  function loadAccountByType(platformId,companyId,accountId,type){
		   var platformValve = document.getElementById(platformId).value;
		   var companyValve = document.getElementById(companyId).value;
		   var accountObj= document.getElementById(accountId);
	     // alert(platformValve+companyValve);
		   platComAccountStore.getPlatComAccountListByCompanyIdType(companyValve,platformValve,type,function(accountList){
		    
		       accountObj.options.length=0;
		    
		      if(accountList.length>0){
		      
		        for(var i=0;i<accountList.length;i++)
			   		{		
			   			 option = new Option(accountList[i].account.name,accountList[i].account.id);
			             accountObj.options.add(option);
			             
			   	}
			   }else{
			   
		        option = new Option("请选择","");
		        accountObj.options.add(option);
		        check=true;
		   }
		     
		   });
		 
	  } 
	  

     
	/////////////////////////////////////加载 全部/////////////////////////////////////////////////////	
	 //加载平台list(全部)
	function loadPlatList(platformId,companyId,accountId){
	    platComAccountStore.getPlatFormList(function(platList){
	    
	        var  platObj= document.getElementById(platformId);
	             platObj.options.length=0;
	         for(var i=0;i<platList.length;i++)
		   		{		
		   			 option = new Option(platList[i].name,platList[i].id);
		             platObj.options.add(option);
		             
		               if(companyId!=null&&companyId!=""){
		               if(i==platList.length-1){
		               loadCompanyList(platformId,companyId,accountId);
		               }
		         }
		   	}	          
	    });	    
	   }
		
		  //加载公司
		  function loadCompanyList(platformId,companyId,accountId){
		   var platformValve = document.getElementById(platformId).value;
		   var  companyObj= document.getElementById(companyId);
		   platComAccountStore.getPlatComAccountListByPlatformId(platformValve,function(companyList){
		     
		        companyObj.options.length=0;
		        //option = new Option("请选择",0);
		        //companyObj.options.add(option);
		    
		       if(companyList.length>0){
		        for(var i=0;i<companyList.length;i++)
			   		{		
			   			 option = new Option(companyList[i].company.name,companyList[i].company.id);
			             companyObj.options.add(option);
			             
			          if(accountId!=null&&accountId!=""){
			            if(i==companyList.length-1){
			             loadAccount(platformId,companyId,accountId);
			             }
			             }
			   		}
			   	}else{
			   	
			   	option = new Option("请选择","");
		        companyObj.options.add(option);
		        if(accountId!=null&&accountId!=""){
			    loadAccount(platformId,companyId,accountId);
			    }
			   	}
		          
		   });
		     
		  }
		  
		  //加载账户
		  function loadAccount(platformId,companyId,accountId){
		   var platformValve = document.getElementById(platformId).value;
		   var companyValve = document.getElementById(companyId).value;
		   var accountObj= document.getElementById(accountId);
	     // alert(platformValve+companyValve);
		   platComAccountStore.getPlatComAccountListByCompanyId(companyValve,platformValve,function(accountList){
		    
		       accountObj.options.length=0;
		    
		      if(accountList.length>0){
		      
		        for(var i=0;i<accountList.length;i++)
			   		{		
			   			 option = new Option(accountList[i].account.name,accountList[i].account.id);
			             accountObj.options.add(option);
			             
			   	}
			   }else{
			   
		        option = new Option("请选择","");
		        accountObj.options.add(option);
		        check=true;
		   }
		     
		   });
		 
	  } 
	  
	  
	  
	  
	  ///////////////////////////////////////加载list 并选中默认初始值 1：卖出 2买入//////////////////////////////////////////////////////////////////////////////
	  
	    	    //加载平台list  并选中默认初始值 1：卖出 2买入
	    function loadPlatListSelectedByType(platformId,companyId,accountId,platformIdValue,companyIdValue,accountIdValue,type){
	        
			   if(type!=null&&type=="1"){
			    platComAccountStore.getToPlatform(function(platList){
			    
			        var  platObj= document.getElementById(platformId);
			             platObj.options.length=0;
			         for(var i=0;i<platList.length;i++)
				   		{		
				   			 option = new Option(platList[i].name,platList[i].id);
				             platObj.options.add(option);
				             
				             if(companyId!=null&&companyId!=""){
				               if(i==platList.length-1){
				               if(platformIdValue!=null&&platformIdValue!=""){
				                 $("#"+platformId+" option[value='"+platformIdValue+"']").attr("selected", true);
				                 }
				                loadCompanyListSelected(platformId,companyId,accountId,platformIdValue,companyIdValue,accountIdValue);
				               }
				             }
				   		}
			    });
			    }else if(type!=null&&type=="2"){
	              platComAccountStore.getFormPlatform(function(platList){
			     
			        var  platObj= document.getElementById(platformId);
			             platObj.options.length=0;
			         for(var i=0;i<platList.length;i++)
				   		{		
				   			 option = new Option(platList[i].name,platList[i].id);
				             platObj.options.add(option);
				             
				             if(companyId!=null&&companyId!=""){
				               if(i==platList.length-1){
				               if(platformIdValue!=null&&platformIdValue!=""){
				                 $("#"+platformId+" option[value='"+platformIdValue+"']").attr("selected", true);
				                 }
				                loadCompanyListSelectedByType(platformId,companyId,accountId,platformIdValue,companyIdValue,accountIdValue,type);
				               }
				             }
				   		}
			    });
	    
	    }
	    
	   }
	   
	   
	   		  //加载公司  并选中默认初始值
		  function loadCompanyListSelectedByType(platformId,companyId,accountId,platformIdValue,companyIdValue,accountIdValue,type){
		   var platformValve = document.getElementById(platformId).value;
		   var  companyObj= document.getElementById(companyId);
		   platComAccountStore.getPlatComAccountListByPlatformId(platformValve,function(companyList){
		     
		        companyObj.options.length=0;
		        //option = new Option("请选择",0);
		        //companyObj.options.add(option);
		    
		       if(companyList.length>0){
		        for(var i=0;i<companyList.length;i++)
			   		{		
			   			 option = new Option(companyList[i].company.name,companyList[i].company.id);
			             companyObj.options.add(option);
			             
			            if(accountId!=null&&accountId!=""){
			             if(i==companyList.length-1){
			             if(companyIdValue!=null&&companyIdValue!=""){
			              $("#"+companyId+" option[value='"+companyIdValue+"']").attr("selected", true);
			              }
			             loadAccountSelectedByType(platformId,companyId,accountId,platformIdValue,companyIdValue,accountIdValue,type);
			             }
			            }
			   		}
			   	}else{
			   	
			   	option = new Option("请选择","");
		        companyObj.options.add(option);
		        if(accountId!=null&&accountId!=""){
			    loadAccountSelectedByType(platformId,companyId,accountId,platformIdValue,companyIdValue,accountIdValue,type);
			    }
			  }
		          
		   });
		     
		  }
		  
		  //加载账户  并选中默认初始值
		  function loadAccountSelectedByType(platformId,companyId,accountId,platformIdValue,companyIdValue,accountIdValue,type){
		   var platformValve = document.getElementById(platformId).value;
		   var companyValve = document.getElementById(companyId).value;
		   var accountObj= document.getElementById(accountId);
	     // alert(platformValve+companyValve);
		   platComAccountStore.getPlatComAccountListByCompanyIdType(companyValve,platformValve,type,function(accountList){
		    
		       accountObj.options.length=0;
		    
		      if(accountList.length>0){
		      
		        for(var i=0;i<accountList.length;i++)
			   		{		
			   			 option = new Option(accountList[i].account.name,accountList[i].account.id);
			             accountObj.options.add(option);
			             if(i==accountList.length-1){
			            // alert(accountIdValue);
			             if(accountIdValue!=null&&accountIdValue!=""){
			               $("#"+accountId+" option[value='"+accountIdValue+"']").attr("selected", true);
			               }
			             }
			   	}
			   }else{
			   
		        option = new Option("请选择","");
		        accountObj.options.add(option);
		        check=true;
		   }
		     
		   });
		 
	  } 
	   
	   	  ///////////////////////////////////////加载list 并选中默认初始值(全部)//////////////////////////////////////////////////////////////////////////////
	  	    //加载平台list  并选中默认初始值
	    function loadPlatListSelected(platformId,companyId,accountId,platformIdValue,companyIdValue,accountIdValue){
	    platComAccountStore.getPlatFormList(function(platList){
	   
	    
	        var  platObj= document.getElementById(platformId);
	             platObj.options.length=0;
	         for(var i=0;i<platList.length;i++)
		   		{		
		   			 option = new Option(platList[i].name,platList[i].id);
		             platObj.options.add(option);
		             
		             if(companyId!=null&&companyId!=""){
		               if(i==platList.length-1){
		               if(platformIdValue!=null&&platformIdValue!=""){
		                 $("#"+platformId+" option[value='"+platformIdValue+"']").attr("selected", true);
		                 }
		                loadCompanyListSelected(platformId,companyId,accountId,platformIdValue,companyIdValue,accountIdValue);
		               }
		             }
		   		}
	          
	    });
	    
	   }
		
		  //加载公司  并选中默认初始值
		  function loadCompanyListSelected(platformId,companyId,accountId,platformIdValue,companyIdValue,accountIdValue){
		   var platformValve = document.getElementById(platformId).value;
		   var  companyObj= document.getElementById(companyId);
		   platComAccountStore.getPlatComAccountListByPlatformId(platformValve,function(companyList){
		     
		        companyObj.options.length=0;
		        //option = new Option("请选择",0);
		        //companyObj.options.add(option);
		    
		       if(companyList.length>0){
		        for(var i=0;i<companyList.length;i++)
			   		{		
			   			 option = new Option(companyList[i].company.name,companyList[i].company.id);
			             companyObj.options.add(option);
			             
			            if(accountId!=null&&accountId!=""){
			             if(i==companyList.length-1){
			             if(companyIdValue!=null&&companyIdValue!=""){
			              $("#"+companyId+" option[value='"+companyIdValue+"']").attr("selected", true);
			              }
			             loadAccountSelected(platformId,companyId,accountId,platformIdValue,companyIdValue,accountIdValue);
			             }
			            }
			   		}
			   	}else{
			   	
			   	option = new Option("请选择","");
		        companyObj.options.add(option);
		        if(accountId!=null&&accountId!=""){
			    loadAccountSelected(platformId,companyId,accountId,platformIdValue,companyIdValue,accountIdValue);
			    }
			  }
		          
		   });
		     
		  }
		  
		  //加载账户  并选中默认初始值
		  function loadAccountSelected(platformId,companyId,accountId,platformIdValue,companyIdValue,accountIdValue){
		   var platformValve = document.getElementById(platformId).value;
		   var companyValve = document.getElementById(companyId).value;
		   var accountObj= document.getElementById(accountId);
	     // alert(platformValve+companyValve);
		   platComAccountStore.getPlatComAccountListByCompanyId(companyValve,platformValve,function(accountList){
		    
		       accountObj.options.length=0;
		    
		      if(accountList.length>0){
		      
		        for(var i=0;i<accountList.length;i++)
			   		{		
			   			 option = new Option(accountList[i].account.name,accountList[i].account.id);
			             accountObj.options.add(option);
			             if(i==accountList.length-1){
			            // alert(accountIdValue);
			             if(accountIdValue!=null&&accountIdValue!=""){
			               $("#"+accountId+" option[value='"+accountIdValue+"']").attr("selected", true);
			               }
			             }
			   	}
			   }else{
			   
		        option = new Option("请选择","");
		        accountObj.options.add(option);
		        check=true;
		   }
		     
		   });
		 
	  } 