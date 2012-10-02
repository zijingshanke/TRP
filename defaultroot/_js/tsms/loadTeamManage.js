$(function() {		
	//添加利润
		$("#dialog").dialog({
			bgiframe: true,
			autoOpen: false,
			height:900,
			width:850,
			modal: true
		});
		
		//确认支付
		$("#dialog10").dialog({
			bgiframe: true,
			autoOpen: false,
			//height:550,
			//width:450,
			modal: true
		});
		
		//确认收退款
		$("#dialog11").dialog({
			bgiframe: true,
			autoOpen: false,
			height: 360,
			width: 330,
			modal: true
		});
		
		//确认付退款
		$("#dialog12").dialog({
			bgiframe: true,
			autoOpen: false,
			height: 360,
			width: 330,
			modal: true
		});		
		
		//创建退票,选择航程、乘机人
		$("#dialog21").dialog({
			bgiframe: true,
			autoOpen: false,
			height: 650,
			width:800,
			modal: true
		});				
});
