
	/*
	 *	进度条
	 *  comId:进度条容器的ID，可以是DIV或SPAN
	 *  percent:完成百分数 如果完成50%，则此参数为50
	*/
	function showProgressPercent(comId,percent){
		var ic = document.getElementById(comId); 	//������� 
		ic.style.height = 15+"px"; 					//高���������� 
		ic.style.width = 200+"px"; 					//长
		ic.style.background = "white";				//背景色������ɫ 
		var sumrow = document.createElement("div"); //����
		sumrow.id = "sumrow";						// 
		sumrow.style.height = 15+"px";				//
		if(percent>100){
			percent=100;
		}
		sumrow.style.width=percent*2+"px";			//
		sumrow.style.overflow = "hidden"; 			//超出部分隐藏 ��������� 
		sumrow.style.background = "#8fd053"; 			//进度色������ɫ 
		sumrow.style.display = "block"; 			//块状显示����ʾ 
		ic.appendChild(sumrow); 					//将创建的sumcow元素添加到ic容器中去
	}
	/*
	 *	进度条
	 *  comId:进度条容器的ID，可以是DIV或SPAN
	 *  total:总任务数量
	 *  proces:已完成数量
	*/
	function showProgress(comId,total,proces){
		var ic = document.getElementById(comId); 	//������� 
		ic.style.height = 15+"px"; 					//高���������� 
		ic.style.width = 200+"px"; 					//长���������� 
		ic.style.background = "white";				//背景色������ɫ 
		var sumrow = document.createElement("div"); //����
		sumrow.id = "sumrow";						// 
		sumrow.style.height = 15+"px";				//
		if(total===0){
			sumrow.style.width=200+"px";
		}else{
			if(proces>total){
				sumrow.style.width=200+"px";
			}else{
				sumrow.style.width=(proces/total*200)+"px";//
			}
		}
		sumrow.style.overflow = "hidden"; 			//超出部分隐藏 ��������� 
		sumrow.style.background = "#8fd053"; 		//进度色������ɫ 
		sumrow.style.display = "block"; 			//块状显示����ʾ 
		ic.appendChild(sumrow); 					//将创建的sumcow元素添加到ic容器中去
	}
	