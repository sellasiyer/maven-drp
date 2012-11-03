var displayFlag = 0; 


function doLoad()
{
    reloadPage = 1;
}


function displayMenu() {
                displayFlag = 1;

		var pos = $("#toolsButton").position();
		var width = $("#toolsButton").width();
		var height = $("#toolsButton").outerHeight();



		$("#toolsMenu").css( {
			position: "fixed",
			top: (pos.top + height) + "px",
			left: (pos.left) + "px"
		});
		$("#toolsMenu").toggle( 'fast');
		return false;
}



$(document).ready(function() {

	//make it so changing a checkbox's value will highlight its label.
	var evt = $.browser.msie ? "click" : "change";	//ie8 hack.
	$(".multiselect input").bind( evt, function() {
		var id = $(this).attr("id");
		
		var getme = 'label[for="' + id + '"]';
		
		if ( $(this).is(":checked"))
			$(getme).addClass("selected");
		else
			$(getme).removeClass("selected");
	});
	

	$(".multiselect label").each( function(index) {
		//console.log(index + ": " + $(this).text());
		
		var checkboxId = $(this).attr("for");
		var checkbox = $("#" + checkboxId);
		if ( $("#" + checkboxId).is(":checked")) {
			$(this).addClass("selected");
		}
	});
	
	//maxlength check for ie
	$("textarea").keypress( function( event) {
		var maxlen = $(this).attr("maxlength");
		return (this.value.length < maxlen || event.keyCode == 8 || event.keyCode==46 || (event.keyCode >= 35 && event.keyCode <= 40));
			
	});
	
	//DIRTY CHECK FOR FORM
	$("input, select, textarea").one("change", function() {
		$("#formChanged").val(1);
	}); 
	
	
	//toolsMenu functionality
	//$("#toolsButton").click( function( event) {
	//	var toolsMenu = "#toolsMenu";
	//	var pos = $(this).position();
	//	var width = $(this).width();
	//	var height = $(this).outerHeight();
	//
	//	$(toolsMenu).css( {
	//		position: "fixed",
	//		top: (pos.top + height) + "px",
	//		left: (pos.left) + "px"
	//	});
	//	$(toolsMenu).toggle( 'fast');
	//	event.stopPropagation();
	//	
	//});
		
	//closes tools menu on click elsewhere.
	$('body').click(function(event) {
            if (displayFlag == 1) {
              displayFlag = 0;
              return; 
            }  
	    if (!$(event.target).closest('#toolsMenu').length) {
	        $('#toolsMenu').hide();
	    }
	});
	
});	//end of doc.ready


