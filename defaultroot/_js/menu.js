function initMenu(_parent)
{
    var as = document.getElementById(_parent).getElementsByTagName("a");
    var target = null;

    for (var i = 0; i < as.length; i++)
    {
        if (as[i].parentNode.className != "title")
        {
            as[i].className = "menuNormal";
            as[i].style.color = "#000000";

            as[i].onmouseover = function()
            {
                if (this.className.indexOf('menuClick') == -1)
                {
                    this.className = "menuNormal menuHover";
                    this.style.color = "#3B5998";
                }
            }

            as[i].onmouseout = function()
            {
                if (this.className.indexOf("menuClick") == -1)
                {
                    this.className = "menuNormal";
                    this.style.color = "#000000";
                }
            }

            as[i].onclick = function()
            {
                if (target != null)
                {
                    target.className = "menuNormal";
                    target.style.color = "#000000";
                }

                target = this;
                this.className = "menuNormal menuClick";
                this.style.color = "#FFFFFF";
            }
        }
    }
}

//显示指定菜单
function showUL(ulId){
	var selectedUL=document.getElementById(ulId);	

	if(selectedUL.style.display==""){
		selectedUL.style.display="none";
	}else{
		selectedUL.style.display="";
		var as = selectedUL.getElementsByTagName("a");
		for (var i = 0; i < as.length; i++){
	       	if (as[i]!=null){
	       		//alert("===")
	            as[i].className = "menuNormal";
	            as[i].style.color = "#000000";
	       	}
		}
	}				
}

//指定菜单颜色显示
function setSelectedMenu(_parent,index){
	
	//alert("==setSelectedMenu==");
	var selectedUl=parent.frames["leftFrame"].document.getElementById(_parent);
	//alert("selectedUL:"+selectedUl);
	var as = selectedUl.getElementsByTagName("a");
	
	   for (var i = 0; i < as.length; i++)
    {
        if (as[i].parentNode.className != "title")
        {
        	as[i].className = "menuNormal";
            as[i].style.color = "#000000";
            if(i==index){
            	if(as[index]!=null){
	 				as[index].className = "menuNormal menuClick";
     				as[index].style.color = "#FFFFFF";
				}	
            }        		
        }
     }
}


