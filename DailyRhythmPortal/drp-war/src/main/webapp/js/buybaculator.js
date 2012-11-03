var rows = new Array();

$(document).ready(function(){
	
	//populate the array of rows with their percentages
	$('#buyback-value dd').each(function(){
		var thisPercent = $(this).text().replace("%", "");
		rows.push(thisPercent);
	});
	
	//now assign widths to the dt and dls
	for(var i = 0; i < rows.length; i ++){
		var thisPct = rows[i];
		var resultWidth = 70 - thisPct;
		var labelWidth = 100 - resultWidth - 1; //-1 to account for padding
		
		$('#buyback-value dt').eq(i).css("width", labelWidth + "%");
		$('#buyback-value dd').eq(i).css("width", resultWidth + "%");
	}
	
});

//reset the form back to the way it looks at the beginning
function resetForm(){
	//clear out input
	$('#retail-price').val("");
	
	//replace actual values with percentages
	for(var i = 0; i < rows.length; i ++){
		$('#buyback-value dd').eq(i).text(rows[i] + "%");
	}
}

//update the form with new values based on input
function calculateForm(){
	var retailPrice = $('#retail-price').val();
	
	if(retailPrice != ""){
		//replace actual values with percentages
		for(var i = 0; i < rows.length; i ++){
			var dollarVal = new Number(rows[i] * .01 * retailPrice);
			
			$('#buyback-value dd').eq(i).text("$" + dollarVal.toFixed(2));
		}
	}
}