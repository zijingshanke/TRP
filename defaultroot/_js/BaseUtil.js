//author YanRui

	function showUL(ulId){
			var selectedUL=document.getElementById(ulId);
			//alert(selectedUL.style.display);
			if(selectedUL.style.display==""){
				selectedUL.style.display="none";
			}else{
				selectedUL.style.display="";
			}		
	}