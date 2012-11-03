var tradeInScreen;

var nextButtonEnableBehavior;
//usage:
/*
 * nextButtonEnableBehavior( enabled ); //where enabled is a boolean.
 */

/*var delay = (function(){
	  var timer = 0;
	  return function(callback, ms){
	    clearTimeout (timer);
	    timer = setTimeout(callback, ms);
	  };
	})();

//usage:
/*$('input').keyup(function() {
    delay(function(){
      alert('Time elapsed!');
    }, 1000 );
});
*/

var paste = false;

function showErrorModal(){
    $('#barcode').val("");
    $('#barcode').attr("disabled", "disabled");
    $('.fader').show();
    $('.modal .button').bind('click', function(){
    	 location.reload(true);
    
    });
}

function initGiftCardScreen() {
	var ctrlDown = false;
    var ctrlKey = 17, vKey = 86, cKey = 67;
    
    var lastKeyStroke = null;

    $(document).keydown(function(e)
    {
        if (e.keyCode == ctrlKey) ctrlDown = true;
    }).keyup(function(e)
    {
        if (e.keyCode == ctrlKey) ctrlDown = false;
    });


       function hideErrorModal(){
            
       }


    $('#barcode').keydown(function(e)
    {
        if (ctrlDown && e.keyCode == vKey ) {
        	showErrorModal();
            

        }
        else if(e.keyCode > 112 && e.keyCode < 118 )
        	{
       //Do nothing for hotkeys
        	}

        else
        {if (lastKeyStroke != null) { // SECOND KEYSTROKE, CHECK DIFFERENCE IN TIME
    		var now = new Date();

    		if ( (now.getTime() - lastKeyStroke.getTime()) > 100) {


    			lastKeyStroke = null;
    			showErrorModal();

	           
    			

    		} else {
    			lastKeyStroke = new Date();
    		}

    	} else if( $('#barcode').value !== '') { // THIS IS FIRST KEYSTROKE, RECORD TIME
    		lastKeyStroke = new Date();
    	}
    	}


    });
    $('#barcode').val("");
 //  $("#barcode").value='';//("");
}

//global variable to hang on to the time of the last keystroke
var lastKeyStroke = null;

// THIS FUNCTION CHECKS THE TIME BETWEEN KEYSTROKES
function checkKeyStroke(field) {
	if (lastKeyStroke != null) { // SECOND KEYSTROKE, CHECK DIFFERENCE IN TIME
		var now = new Date();
		
		if ( (now.getTime() - lastKeyStroke.getTime()) > 100) {
			

			lastKeyStroke = null;

            displayPopupErrorMessage("Gift Card cannot be manually entered.", "barcode");
           field.value="";
            field.focus();
			//displayPopupErrorMessage("Gift Card cannot be manually entered.");


		} else {
			lastKeyStroke = new Date();
		}
		
	} else if( field.val() !== '') { // THIS IS FIRST KEYSTROKE, RECORD TIME
		lastKeyStroke = new Date();
	}
}

function validateForm() {
// get all the inputs into an array.
    var $inputs = $('#tradeInHotKeysForm :input');

    // not sure if you wanted this, but I thought I'd add it.
    // get an associative array of just the values.
    var isEmpty = false; 
    $inputs.each(function() {
        if ($(this).attr('type') == 'hidden' || $(this).attr('type') == 'radio') {
            return;
        }
 

        if ($(this).is(':visible') && isEmpty == false && isBlank($(this).val())) {
           isEmpty = true;
        } 
    });

    if (isEmpty) {
       nextButtonEnableBehavior(false);
    } else {
       nextButtonEnableBehavior(true);
    } 

}

function returnControlFocus(id) {
    $('#'+id).focus();
}

function isBlank(str) {
    return (!str || /^\s*$/.test(str));
}

    function lpadding(input) {
        if( /^[0-9]+$/i.test(input.value)) {
            if (parseInt(input.maxLength) > input.value.length) {
               var added ="";   
               for (var i =0; i <  (parseInt(input.maxLength) - input.value.length); i++) {
                  added = "0" + added;
               } 
               input.value = added + input.value;
            }
        }
     }

	 function tradeincheckdate(input){

	        if (/(\d{4})/.test(input.value) && input.value.length == 4) {



	            var newvalue = input.value.substring(0,1) + "/" + input.value.substring(1,2) + "/" + input.value.substring(2,input.value.length);
	            input.value = newvalue;
	        }  else if (/(\d{6})/.test(input.value) && input.value.length == 6) {
	            if (parseInt(input.value.substring(0,2)) > 12) {
                       return;
                    }

	            if (parseInt(input.value.substring(2,4)) > 31) {
                       return;
                    }



	            var newvalue = input.value.substring(0,2) + "/" + input.value.substring(2,4) + "/" + input.value.substring(4,input.value.length);
	            input.value = newvalue;
	        } else  if (/(\d{8})/.test(input.value) && input.value.length == 8) {
	            if (parseInt(input.value.substring(0,2)) > 12) {
                       return;
                    }

	            if (parseInt(input.value.substring(2,4)) > 31) {
                       return;
                    }

	            if (parseInt(input.value.substring(4,6)) != '20' && parseInt(input.value.substring(4,6)) != '19') {
                       return; 
                    }


	            var newvalue = input.value.substring(0,2) + "/" + input.value.substring(2,4) + "/" + input.value.substring(6,input.value.length);
	            input.value = newvalue;
	        }

                var dd = new Date(input.value);
                if ("Invalid Date" == dd) {
                   return;
                } 

                input.value = $.format.date(dd.toString(), "MM/dd/yy");
                
	    }

function checkAlphaNumeric(input) {
     if( /^[a-z0-9]+$/i.test(input.value) == false && input.value.length > 0) {
        var replaced = "";
        for(var i=0; i < input.value.length;i++) {
           if( /^[a-z0-9]+$/i.test(input.value[i]) == true) {
               replaced = replaced + input.value[i];
           }
        }
        input.value = replaced;
        displayPopupErrorMessage("Only alphanumeric characters are permitted.");
        input.focus();
        return;
     }
}


function applyMinimumLengthCheck(fieldId, minLength, buttonId) {
	var field = $("#" + fieldId); 
	
	field.keyup(function() { 
		var button = $("#" + buttonId);

		if( /^[a-z0-9]+$/i.test(field.val()) &&  field.val().length >= minLength )	{
			if (button.hasClass("disabled")) {
				//	if field is disabled and passes length check -> enable.
				nextButtonEnableBehavior(true);
			}
		} 
		else if (!button.hasClass("disabled")) {
			nextButtonEnableBehavior(false);
		}
			
		//by checking the css class, i am limiting redundant calls to wicket.
	});
}

function enableNextButton() {
	var button = $("#nextButton");
	var field = $("#serialNumber"); 
        var dropdown = $("#deviceColor"); 
        if( field.val().length >= 7  &&  dropdown.val().length > 0 ) {
	  if (button.hasClass("disabled")) {
	       //	if field is disabled and passes length check -> enable.
              nextButtonEnableBehavior(true);
          }

        }
	else if (!button.hasClass("disabled")) {
			nextButtonEnableBehavior(false);
	}
}


function initTradeInTradabilityCheck() {
	applyMinimumLengthCheck("selLastFour", 4, "nextButton");
	
	applyMinimumLengthCheck("selFullSerial", 9, "nextButton");

}

function initEditProductAnswersPage() {
       fixedHeaderTables();

        var field = $("#serialNumber");
	field.keyup(function() { 
            enableNextButton();
	});

        if ('checked' == $('#yes').attr('checked')) {
            displayFourPartKey();
        } else  if ('checked' == $('#no').attr('checked')) {
            hideFourPartKey();
        }

 
}


function setVisible(componentId, visibility) {
	componentId = "#" + componentId;
	if (visibility) 
		$(componentId).show();
	else
		$(componentId).hide();
}


function autoTab(e, element, nextElement) {
    var code;

    if(!e) var e = window.event;
			
    if(e.which) code = e.which;
			
    else if (e.keyCode) code = e.keyCode;
			
    if ((code > 47 && code < 91)||(code<95 && code < 105)) {
	if (element.value.length == element.maxLength && nextElement != null) {
	   nextElement.focus();
        }
    }
}

function selectFoundProduct(id,url) {
  $("#" + id).attr('checked', 'checked');
  wicketAjaxGet(url);
}

function toggleButtonEnabledCss(buttonId) {
   $("#" + buttonId).toggleClass("disabled");
}

function fixedHeaderTables(){
   $('#fixedHeaderTable01 table').fixedHeaderTable({ height: '100', altClass: 'odd' });
}

