var VALIDATION_TYPE = {
	SET_IN_JAVA : 0,
	NUMERIC : 1,
	ALPHANUMERIC : 2,
	NAME : 3,
	EMAIL : 4,
	PHONE : 5,
	SHARED : 6,
	DATE : 7
};

function getSelectedText(textElement) {
	var txt = '';
	if (textElement && textElement.selectionStart != undefined) {
		var startPos = textElement.selectionStart;
		var endPos = textElement.selectionEnd;
		txt = textElement.value.substring(startPos, endPos);
	} else if (window.getSelection) {
		txt = window.getSelection();
	} else if (document.getSelection) {
		txt = document.getSelection();
	} else if (document.selection) {
		txt = document.selection.createRange().text;
	}
	return txt;
}

function keyEntryValidation(e, field, validation) {
	var target = extractElement(e);
	var elemValue = target.value;
	var keyNum = extractKeyNum(e);

	var validationField = validation[field];
	if (validation[field].type == VALIDATION_TYPE.SHARED
			&& validation[field].useField) {
		validationField = validation[validation[field].useField];
	}

	var selectedTextLength = 0;
	var selectedText = getSelectedText(target);
	if (selectedText) {
		selectedTextLength = selectedText.length;
	}

	if (isKeyNumControlChar(keyNum)) {
		return true;
	}

	switch (validationField.type) {
	case VALIDATION_TYPE.NUMERIC:
		if (!isKeyNumNumeric(keyNum)) {
			return false;
		}
		break;
	case VALIDATION_TYPE.ALPHANUMERIC:
		if (!isKeyNumAlphaNumeric(keyNum)) {
			return false;
		}
		break;
	case VALIDATION_TYPE.EMAIL:
		if (!isKeyNumEmailValid(keyNum)) {
			return false;
		}
		break;
	case VALIDATION_TYPE.NAME:
		if (!isKeyNumNameValid(keyNum)) {
			return false;
		}
		break;
	case VALIDATION_TYPE.PHONE:
		if (!isKeyNumPhoneValid(keyNum)) {
			return false;
		}
		break;
	case VALIDATION_TYPE.DATE:
		if (!isKeyNumDateValid(keyNum)) {
			return false;
		}
		break;
	}

	if (selectedTextLength > 0) {
		return true;
	}

	if (validationField.exactLength) {
		if (elemValue.length >= validationField.exactLength) {
			return false;
		}
	}
	if (validationField.maxLength) {
		if (elemValue.length >= validationField.maxLength) {
			return false;
		}
	}
	return true;
}

function fieldValidation(e, field, validation) {
	var target = extractElement(e);
	var elemValue = target.value;

	var realField = field;
	if (validation[field].type == VALIDATION_TYPE.SHARED
			&& validation[field].useField) {
		realField = validation[field].useField;
	}

	fieldValueValidation(elemValue, realField, validation);
	handleValidationDisplay(validation);
}

function fieldValueValidation(elemValue, field, validation) {
	if (validation[field].type == VALIDATION_TYPE.SET_IN_JAVA) {
		return;
	}

	validation[field].valid = true;

	if (validation[field].optional && isValueBlank(elemValue)) {
		return;
	}

	switch (validation[field].type) {
	case VALIDATION_TYPE.NUMERIC:
		validation[field].valid = /^[0-9]+$/.test(elemValue);
		break;
	case VALIDATION_TYPE.ALPHANUMERIC:
		validation[field].valid = /^[0-9a-zA-Z]+$/.test(elemValue);
		break;
	case VALIDATION_TYPE.EMAIL:
		validation[field].valid = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+[.][A-Za-z]{2,4}$/
				.test(elemValue);
		break;
	case VALIDATION_TYPE.NAME:
		validation[field].valid = /^[a-zA-Z -]+$/.test(elemValue);
		break;
	case VALIDATION_TYPE.PHONE:
		validation[field].valid = /^[(]{0,1}[0-9]{3}[)]{0,1}[-\s.]{0,1}[0-9]{3}[-\s.]{0,1}[0-9]{4}$/.test(elemValue);
		return;
		break;
	case VALIDATION_TYPE.DATE:
		validation[field].valid = /^[0-9\/]+$/.test(elemValue);
		break;
	}

	if (!validation[field].valid) {
		return;
	}
	if (validation[field].exactLength) {
		if (elemValue.length != validation[field].exactLength) {
			validation[field].valid = false;
			return;
		}
	}
	if (validation[field].minLength) {
		if (elemValue.length < validation[field].minLength) {
			validation[field].valid = false;
			return;
		}
	}
	if (validation[field].maxLength) {
		if (elemValue.length > validation[field].maxLength) {
			validation[field].valid = false;
			return;
		}
	}
}

function doFormFieldValidation(validation) {
	var realField;
	for ( var field in validation) {
		if (isFunction(validation[field])) {
			continue;
		}

		realField = field;
		if (validation[field].type == VALIDATION_TYPE.SHARED
				&& validation[field].useField) {
			realField = validation[field].useField;
		}

		if (validation[realField].ignore) {
			continue;
		}
		if (validation[realField].valueSelector) {
			var value = $(validation[realField].valueSelector).val();
			if (value) {
				fieldValueValidation(value, realField, validation);
			}
		}
	}
	handleValidationDisplay(validation);
}

function handleValidationDisplay(validation) {
	var allFormDataValid = true;
	for ( var field in validation) {
		if (isFunction(validation[field])) {
			continue;
		}
		if (validation[field].type == VALIDATION_TYPE.SHARED) {
			continue;
		}
		if (validation[field].ignore) {
			continue;
		}
		// alert(field + '=' + validation[field].valid);

		if (validation[field].feedbackSelector) {
			var jFeedback = $(validation[field].feedbackSelector);
			if (jFeedback.length > 0) {
				if (validation[field].valid) {
					jFeedback.hide();
				} else {
					jFeedback.show();
				}
			}
		}
		if (!validation[field].valid) {
			allFormDataValid = false;
		}
	}
	// alert("allFormDataValid=" + allFormDataValid);

	if (isFunction(validation.handleButtonState)) {
		validation.handleButtonState(allFormDataValid);
	}
}

function extractElement(e) {
	if (window.event) // IE
	{
		return window.event.srcElement;
	} else // Netscape/Firefox/Opera
	{
		return e.target;
	}
}

function extractKeyNum(e) {
	if (window.event) // IE
	{
		return window.event.keyCode;
	} else // Netscape/Firefox/Opera
	{
		return e.which;
	}
}

function isKeyNumControlChar(keyNum) {
	if (keyNum >= 0 && keyNum <= 31) {
		return true;
	} else if (keyNum == 127) {
		return true;
	}
	return false;
}

function isKeyNumLowerCaseAlpha(keyNum) {
	return keyNum >= 97 && keyNum <= 122;
}

function isKeyNumUpperCaseAlpha(keyNum) {
	return keyNum >= 65 && keyNum <= 90;
}

function isKeyNumAlpha(keyNum) {
	return isKeyNumLowerCaseAlpha(keyNum) || isKeyNumUpperCaseAlpha(keyNum);
}

function isKeyNumNumeric(keyNum) {
	return keyNum >= 48 && keyNum <= 57;
}

function isKeyNumPhoneValid(keyNum) {
	return isKeyNumNumeric(keyNum) || keyNum == 40 || keyNum == 41 || keyNum == 45;
}

function isKeyNumDateValid(keyNum) {
	return isKeyNumNumeric(keyNum) || keyNum == 47;
}

function isKeyNumAlphaNumeric(keyNum) {
	return isKeyNumAlpha(keyNum) || isKeyNumNumeric(keyNum);
}

function isKeyNumEmailValid(keyNum) {
	if (isKeyNumAlphaNumeric(keyNum)) {
		return true;
	}
	// .(46) _(95) %(37) +(43) -(45) @(64)
	return keyNum == 46 || keyNum == 95 || keyNum == 37 || keyNum == 43
			|| keyNum == 45 || keyNum == 64;
}

function isKeyNumNameValid(keyNum) {
	if (isKeyNumAlpha(keyNum)) {
		return true;
	}
	// space(32) -(45)
	return keyNum == 32 || keyNum == 45;
}

function isValueBlank(value) {
	if (!value || value.length == 0) {
		return true;
	}
	return !/[^\s]+/.test(value);
}

function cleanupValidationMarkup(formElementSelector) {
	$(formElementSelector).each(function(index) {
		$(this).find('span').each(function(index) {
			if ($(this).text() == '*') {
				$(this).remove();
			} else if ($(this).text() == 'E') {
				$(this).text('*');
			}
		});
	});
}

function isFunction(functionToCheck) {
	var getType = {};
	return functionToCheck
			&& getType.toString.call(functionToCheck) == '[object Function]';
}
