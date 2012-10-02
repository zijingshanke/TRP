

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

