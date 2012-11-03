function setupSearchNav() {
	$(".table-nav a").bind('click', openSearchPanel);
	
	// Close all panels
	$('.table-nav').find('li').each(function(i){
		var closedWidth = $(this).find('a').outerWidth();
		closedWidth += 1; // This is the right border
		$(this).animate({width:closedWidth}, 0);
	});
	
	// Open the panel with the class 'open' 
	$('.table-nav li.open').animateAuto('width', 0);
}

function openSearchPanel(){	
	var previous = $(this).closest('ul').find('li.open');
	var previousClosedWidth = previous.find('a').outerWidth();
		previousClosedWidth += 1; // This is the right border
	var current = $(this).closest('li');

	previous.removeClass('open');
	current.addClass('open');
	
	previous.animate({width:previousClosedWidth}, 250);
	current.animateAuto('width', 250);
}

//
// Intended Fix for height/width animation of "auto" val 
//
jQuery.fn.animateAuto = function(prop, speed, callback){
    var elem, temp;
    return this.each(function(i, el){
        el = jQuery(el); 
        elem = el.clone().css(prop,"auto").insertBefore(el);
        temp = elem.css(prop);
        elem.remove();
        if(prop === "height")
            el.animate({"height":temp}, speed, callback);
        else if(prop === "width")
            el.animate({"width":temp}, speed, callback); 
    });  
};