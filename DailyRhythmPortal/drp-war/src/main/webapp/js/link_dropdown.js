$(document).ready(function(){
	
	$('.dropdown ul').width($('.dropdown').outerWidth());
	$('.dropdown').on('click', clickDropdownNav);
	$('.dropdown a').on('click', clickDropdownLink);

	function clickDropdownLink(e){
		if($(this).hasClass("primary")){
			//stop the link click unless there's no list of options underneath
			if($(this).siblings('ul').length > 0){
				e.preventDefault();
			}
		}
	}
	
	function clickDropdownNav(e) {
		var dropdown = $(this);
		var options = dropdown.find('ul');
				
		// Close/open dropdown
		dropdown.toggleClass('open');
		
		if ( dropdown.hasClass('open') ){
			options.slideToggle(225);
			$('body').prepend('<div class="click-blocker"></div>'); // Add click-blocker
			$('.click-blocker').bind('click', clickOutsideDropdownNav); // Bind click-blocker
		} else {
			options.hide();
			$('.click-blocker').remove(); // Remove click-blocker
		}
	}
	
	
	function clickOutsideDropdownNav(e) {
		var dropdown = $('.dropdown.open');
		var options = dropdown.find('ul');
				
		// Close Dropdown
		dropdown.removeClass('open');
		options.hide();
		$('.click-blocker').remove(); // Remove click-blocker
	}

}); // End Document Ready