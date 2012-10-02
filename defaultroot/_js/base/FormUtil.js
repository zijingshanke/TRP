function trim(form){ 
	 
  for(i=0;i<form.elements.length;i++)
  {
  	if(form.elements[i].tagName.toLowerCase()=='input' || form.elements[i].tagName.toLowerCase()=='select')
  	{
  	  form.elements[i].value=form.elements[i].value.trim();  		
  	}  	
  }
} 


function setDefaultValue(objId,defaultValue){
	var obj=document.getElementById(objId+"");
	obj.value=defaultValue;	
}





  