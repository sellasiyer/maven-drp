/**
Adds support for prefill text on input elements.  2 different classes:

.prefill-hint supports hint text, which will be cleared out on focus and will come back if the field
loses focus without any value being entered

.prefill-value is to be used for actual values that are being prefilled, and so the text will just
be selected when the field gets focus

**/
$(document).ready(function(){
	preFillPage();
}); 
 
function preFillPage() {
	
	$('input.prefill-hint').each(function(){
		$(this).val($(this).data("prefill"));
		$(this).addClass("initgray");

		$(this).focus(function(){
			if($(this).val() === $(this).data("prefill")){
				//clear it out
				$(this).val("");
			}
		});
		$(this).blur(function(){
			if($(this).val() === ""){
				$(this).val($(this).data("prefill"));
			}
		});
	});
	$('input.prefill-value').each(function(){
		$(this).val($(this).data("prefill"));

		$(this).focus(function(){
			$(this).select();
		});

	});
}