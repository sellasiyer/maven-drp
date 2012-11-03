	//expects one array of 4 inputs (jQuery objects), in the order which they should be filled in with the gun
	//input.  listener is bound to the first element in the group, so it must have focus in order for this
	//to be called
	function listenForGun(fieldGroup){

		if(fieldGroup.length !== 4){
			console.log("Gun listener requires groups of 4 inputs.  Listener not bound!");
			return;
		}

		var keyPresses = ''; // Store keys pressed
		var timeout; // Track the setTimeout instance so we can clear it later
		
		console.log("binding gun listener to: " + fieldGroup[0].attr('id'));

		fieldGroup[0].bind('keypress.guncheck', function(e){

			// CANCEL PREVIOUS 'setTimeout' SO WE DON'T STACK UP A QUEUE
			clearTimeout(timeout);
			
			// CLEAR 'keyPresses' VARIABLE AFTER X SECONDS 
			timeout = setTimeout(function(){
				console.log('clearing keyPresses, they were: ' + keyPresses);
				keyPresses = '';
				}, 1000);
			
			// STORE KEY PRESSES
			keyPresses += String.fromCharCode(e.which);
			
			console.log('keypresses variable: ' + keyPresses + ', length: ' + keyPresses.length);
			
			// Check number of stored key presses - the gun sends 23 (last one is enter key)
			if (e.which == '13') {
				// VALIDATION
				if(keyPresses.length == 23){//For old bar codes
				checkIfNumbers(keyPresses); // Is only numbers
				//if (keyPresses.substring(0, 2) != '01') { return false }; // Starts with '01'
				// BREAK UP NUMBER
				var store = keyPresses.substring(2, 6);
				var register = keyPresses.substring(6, 9);
				var transaction = keyPresses.substring(9, 13);
				var date = keyPresses.substring(13, 21);

				var formatteddate = date.substring(0, 2) + "/" + date.substring(2, 4) + "/" + date.substring(4,6);

				setValues(fieldGroup, store, register, transaction, formatteddate);
				}else if(keyPresses.length == 35){//For new bar codes
					//FIXME This is not correct - Best way is to make a Ajax call 
					var temp1 = keyPresses.substring(1, 33);
					console.log('*** GUN SCAN *** Step1: ' + temp1);
					var temp2 = temp1.substring(29, 32);
					temp1 = temp1.substring(0, 29);
					console.log('*** GUN SCAN *** Step2: ' + temp1);
					console.log('*** GUN SCAN *** Step2: ' + temp2);
					var temp3 = parseInt(temp1) * 751;
					console.log('*** GUN SCAN *** Step3: ' + temp3);
					temp3 = temp3 + parseInt(temp2);
					console.log('*** GUN SCAN *** Step4: ' + temp3);
					var temp4 = lpad(temp3, 29);
					console.log('*** GUN SCAN *** Step5: ' + temp3);
					
					var store = temp4.substring(17, 21);
					var register = '0' + temp4.substring(13, 15);
					var transaction = temp4.substring(21, 25);
					var formatteddate = temp4.substring(0, 2) + "/" + temp4.substring(2, 4) + "/" + temp4.substring(26,29);
					setValues(fieldGroup, store, register, transaction, formatteddate);
					
				}else{
					//Set error message - make a Ajax call
				}
				console.log('*** GUN SCAN *** triggered from input id: ' + $(this).attr('id'));
			} 

		});

	}


	function checkIfNumbers(val){
		var regex = new RegExp('[0-9]+');	
		return regex.test(val);
	}

	//declare console if it doesn't exist...
	if (!window.console){
		console = {log: function() {}};
	}
	
	function lpad(number, length) {
	    var str = '' + number;
	    while (str.length < length) {
	        str = '0' + str;
	    }
	    return str;
	}
	
	function setValues(fieldGroup, store, register, transaction, formatteddate){
		// SET FIELDS
		fieldGroup[0].val(store);
		fieldGroup[1].val(register);
		fieldGroup[2].val(transaction);
		fieldGroup[3].val(formatteddate);
	}