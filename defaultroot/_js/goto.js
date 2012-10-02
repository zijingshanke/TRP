$(function(){
	$('.btn').toggle(function(){
		$('#sideBar').animate({marginLeft: "-=150px"}, { queue: false, duration: 500 });
		parent.document.getElementById('x').cols='20,*'; 
		
		if( parent.frames[2].window.document.getElementById("myTable") !=null )
		{
			
		    parent.frames[2].window.document.getElementById("myTable").style.width = screen.width - 195 + 150 + "px";

		}

	},function(){
		$('.fixedSideBar').css('display','none')
		$('#sideBar').animate({marginLeft: "+=150px"}, { queue: false, duration: 500 });
		parent.document.getElementById('x').cols='170,*'; 
		
		if( parent.frames[2].window.document.getElementById("myTable") !=null )
		{			
			parent.frames[2].window.document.getElementById("myTable").style.width = screen.width - 195  + "px";
		}
	})
})