
function trim(form) {
	for (i = 0; i < form.elements.length; i++) {
		if (form.elements[i].tagName.toLowerCase() == "input" || form.elements[i].tagName.toLowerCase() == "select") {
			form.elements[i].value = form.elements[i].value.trim();
		}
	}
}

function setSubmitButtonDisable(submitButtonId) {
	var submitButtonObj=document.getElementById(submitButtonId);
	//alert(submitButtonObj);
	if(submitButtonObj!=null){
		//submitButtonObj.disable=true;//在type=button时使用会有不起作用的情况，原因不详
		submitButtonObj.style.display="none";
		
		//alert(submitButtonObj.disable);
		return true;
	}else{
		return false;
	}	
}

function setDefaultValue(objId, defaultValue) {
	var obj = document.getElementById(objId + "");
	obj.value = defaultValue;
}

 function checkAll(e, itemName){
	var aa = document.getElementsByName(itemName);
	for (var i=0; i<aa.length; i++){
		aa[i].checked = e.checked;
	}		    
}

function checkItem(e, allName){
	var all = document.getElementsByName(allName)[0];
	if(!e.checked) {
		all.checked = false;
	}else{
		var aa = document.getElementsByName(e.name);
		for (var i=0; i<aa.length; i++){
		   if(!aa[i].checked){
		   	return;
		   } 		
		}
		all.checked = true;
	}
}
