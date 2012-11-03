function setupScoreboardSubNav(){
	//handle clicks on the scoreboard sub-nav
	$('.button-selector li a').click(function(e){
		e.preventDefault();
		var parentList = $(this).closest('.button-selector');
		if(parentList.is('.disabled')){
			return;
		}
		var parentLi = $(this).closest('li');
		
		parentList.children('li').removeClass("selected");
		parentLi.addClass("selected");
		
		if(parentList.hasClass("button-selector-ind")){
			//handles click on bandwidth selector
			parentList.find('div.indicator').hide();
			parentLi.children('.indicator').show();
			
		}
	});
}