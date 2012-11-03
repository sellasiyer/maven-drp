function getCookie(c_name) {
	var i, x, y, ARRcookies = document.cookie.split(";");
	for (i = 0; i < ARRcookies.length; i++) {
		x = ARRcookies[i].substr(0, ARRcookies[i].indexOf("="));
		y = ARRcookies[i].substr(ARRcookies[i].indexOf("=") + 1);
		x = x.replace(/^\s+|\s+$/g, "");
		if (x == c_name) {
			return unescape(y);
		}
	}
}

function setCookie(c_name, value, exdays) {
	var exdate = new Date();
	exdate.setDate(exdate.getDate() + exdays);
	var c_value = escape(value)
			+ ((exdays == null) ? "" : "; expires=" + exdate.toUTCString());
	var loc = new String(window.parent.document.location);
	if (loc.indexOf("https://") != -1) {
		c_value += ";secure";
	}
	document.cookie = c_name + "=" + c_value;
}

function applyRowHighlighting(tableSelector) {
	$(tableSelector + ' tr:[class="odd"]').mouseover(function() {
		$(this).removeClass('even').addClass('row_highlight');
	}).mouseout(function() {
		$(this).removeClass('row_highlight').addClass('odd');
	});
	$(tableSelector + ' tr:[class="even"]').mouseover(function() {
		$(this).removeClass('even').addClass('row_highlight');
	}).mouseout(function() {
		$(this).removeClass('row_highlight').addClass('even');
	});
}

function removeAllColumnSorting(tableSelector) {
	$(tableSelector + ' tr th').each(function(index) {
		$(this).find('a').removeAttr('href').removeAttr('onclick');
	});
}

function removeColumnSorting(tableSelector, columnIndex) {
	$(tableSelector + ' tr th:eq(' + columnIndex + ')').each(function(index) {
		$(this).find('a').removeAttr('href').removeAttr('onclick');
	});
}

function fixTableNoRecords(tableSelector, noRecordsText) {
	var jNoRec = $(tableSelector + ' tfoot tr.norecords-tr td.norecords-td');
	if (jNoRec.length > 0) {
		var colspan = parseInt(jNoRec.attr('colspan'), 10);
		if (colspan > 1) {
			jNoRec.after('<td></td>');
			jNoRec.attr('colspan', (colspan - 1));
			jNoRec.find('span').text(noRecordsText);
		}
	}
}

function scrollToTop() {
	window.scrollTo(0, 0);
}

function scrollToBottom() {
	window.scrollTo(0, document.body.scrollHeight);
}

function refreshPage() {
	setTimeout("window.location.reload();", 20);
}

function setupTableSorting(tableSelector, sortCommand) {
	if ($(tableSelector).length == 0) {
		return;
	}
	$(tableSelector + ' th a').live("click", function(event) {
		setTimeout(sortCommand, 200);
	});
}

function fixTableBorders(tableSelector) {
	$(tableSelector + ' tr').each(function(index) {
		// header cells
		$(this).find('th:last-child').addClass("last");
		// data cells
		$(this).find('td:last-child').addClass("last");
	});
	$(tableSelector + ' tr:last-child').addClass("last");
}

function doWicketBehavior(command) {
	setTimeout(command, 50);
}

function setupLinkLoading(linkSelector, delay) {
	$(linkSelector).click(function() {
		var fixedWidth = $(this).css('width');
		$(this).css('width', fixedWidth).addClass('spinner');
		setTimeout("clearLinkLoading('" + linkSelector + "');", delay);
	});
}
function clearLinkLoading(linkSelector) {
	$(linkSelector + '.spinner').removeClass('spinner');
}

function showLinkLoading(enabled, linkSelector) {
	if (enabled) {
		var jLink = $(linkSelector);
		var fixedWidth = jLink.css('width');
		jLink.css('width', fixedWidth).addClass('spinner');
	} else {
		$(linkSelector).removeClass('spinner');
	}
}

function showButtonLoading(enabled, buttonSelector) {
	if (enabled) {
		var jButton = $(buttonSelector);
		var fixedWidth = jButton.outerWidth();
		jButton.css('width', fixedWidth).addClass('spinner');
	} else {
		$(buttonSelector).removeClass('spinner');
	}
}

function handleLinkState(enabled, selector) {
	var link = $(selector);
	if (enabled) {
		link.removeAttr('disabled').removeClass('disabled');
	} else {
		link.attr('disabled', true).addClass('disabled');
	}
}

function handleButtonState(enabled, selector) {
	var button = $(selector);
	if (enabled) {
		button.removeAttr('disabled').parent().removeClass('disabled');
	} else {
		button.attr('disabled', true).parent().addClass('disabled');
	}
}

function clickButton(selector) {
	$(selector).click();
}

function handleGunEntry(fieldSelector, prefix, wicketBehavior) {
	var keyPresses = ''; // Store keys pressed
	var timeout = null; // Track the timeout instance so we can clear it later
	var jField = $(fieldSelector);
	jField.bind('keypress.guncheck', function(e) {
		if (timeout) {
			clearTimeout(timeout);
		}
		timeout = setTimeout(function() {
			keyPresses = '';
		}, 100);
		keyPresses += String.fromCharCode(e.which);
		if (e.which == '13') {
			wicketBehavior(prefix + keyPresses);
		}
	});
}

function popupResourceLink(selector, name, specs) {
	var jLink = $(selector);
	if (jLink.length == 0) {
		return;
	}
	var href = jLink.attr('href');
	if (href) {
		window.open(href, name, specs);
	}
}

function handlePhoneOnBlur(selector) {
	jPhone = $(selector);
	if (jPhone.length == 0) {
		return;
	}
	var text = jPhone.val().replace(/[^0-9]/g, '');
	if (text.length != 10) {
		jPhone.val(text);
		return;
	}
	var fmtPhone = '(' + text.substring(0, 3) + ')' + text.substring(3, 6)
			+ '-' + text.substring(6);
	jPhone.val(fmtPhone);
}

function handlePhoneOnFocus(selector) {
	jPhone = $(selector);
	if (jPhone.length == 0) {
		return;
	}
	var text = jPhone.val().replace(/[^0-9]/g, '');
	jPhone.val(text);
	jPhone.caret(text.length);
}

function applyTabStyles(selector) {
	$(selector + " ul").first().addClass("tabs");
}

function isFunction(functionToCheck) {
	var getType = {};
	return functionToCheck
			&& getType.toString.call(functionToCheck) == '[object Function]';
}

(function($) {
	$.fn.caret = function(pos) {
		var target = this[0];
		if (arguments.length == 0) { // get
			if (target.selectionStart) { // DOM
				var pos = target.selectionStart;
				return pos > 0 ? pos : 0;
			} else if (target.createTextRange) { // IE
				target.focus();
				var range = document.selection.createRange();
				if (range == null)
					return '0';
				var re = target.createTextRange();
				var rc = re.duplicate();
				re.moveToBookmark(range.getBookmark());
				rc.setEndPoint('EndToStart', re);
				return rc.text.length;
			} else
				return 0;
		}

		// set
		var pos_start = pos;
		var pos_end = pos;

		if (arguments.length > 1) {
			pos_end = arguments[1];
		}

		if (target.setSelectionRange) // DOM
			target.setSelectionRange(pos_start, pos_end);
		else if (target.createTextRange) { // IE
			var range = target.createTextRange();
			range.collapse(true);
			range.moveEnd('character', pos_end);
			range.moveStart('character', pos_start);
			range.select();
		}
	}
})(jQuery)

function checkdate(input) {
	var valitinputformat = /(\d{6})/;
	if (input.value != "") {
		var n = input.value.replace(/\//gi, "");
		input.value = n;
		if (input.value.substring(4, input.value.length).length < 4
				&& input.value.substring(4, input.value.length) != "20") {
			var changevalue = input.value.substring(0, 4)
					+ input.value.substring(4, input.value.length);
			input.value = changevalue;
		} else if (input.value.substring(6, input.value.length).length < 4
				&& input.value.substring(4, 6) == "20") {
			var changevalue = input.value.substring(0, 4)
					+ input.value.substring(6, input.value.length);
			input.value = changevalue;
		} else if (input.value.substring(6, input.value.length).length < 4
				&& input.value.substring(6, input.value.length) != "20") {
			var changevalue = input.value.substring(0, 6)
					+ input.value.substring(6, input.value.length);
			input.value = changevalue;
		}
	}
	if (valitinputformat.test(input.value)) {
		var newvalue = input.value.substring(0, 2) + "/"
				+ input.value.substring(2, 4) + "/"
				+ input.value.substring(4, input.value.length);
		input.value = newvalue;
	}
}

function getClientTime(wicketBehavior) {
	var now = new Date();
	var leadHoursZero = "";
	if (now.getHours() < 10) {
		leadHoursZero = "0";
	}
	var leadMinsZero = "";
	if (now.getMinutes() < 10) {
		leadMinsZero = "0";
	}
	wicketBehavior("clientTime" + leadHoursZero + now.getHours() + ":"
			+ leadMinsZero + now.getMinutes() + "_" + now.getFullYear() + "-"
			+ (now.getMonth() + 1) + "-" + now.getDate() + "TZ"
			+ now.getTimezoneOffset());
}
