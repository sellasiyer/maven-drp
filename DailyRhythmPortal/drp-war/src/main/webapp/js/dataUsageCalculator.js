var MAXGB = 30; // aka 30 gigabytes.
var MAXWIDTH; // in pixels
var SELECTED_CSSCLASS = "selected";

// wicket-supplied.
var wicket3Gvals;
var wicket3G4Gvals;
var wicket4Gvals;
//
var currentBandwidthValues;

// maps to data-usage-type of clicked li's section.
var dataUsageTypes = new Array("email", "web", "audio", "video_lo", "video_hi",
		"photos");

// default values for display.
var webValues = new Array(0, 150, 750, 1500, 3000, 7500);
// needs to be x or "x mins" or "x hrs" for a search and replace.
var streamingValues = new Array("0 mins", "150 mins", "450 mins", "30 hrs",
		"60 hrs");
var photoValues = new Array(0, 60, 300, 1500, 3000);

$(document).ready(function() {
	// calculate & store width for later use.
	MAXWIDTH = $('#data-calc .bar').width();

	initializeItemLabels();

	// handles a click on any of the data (non-bandwidth) anchors.
	$(".num-choices a").bind("click", function() {

		// if already on, or if the parent list is disabled, return.
		if ($(this).parent('li').hasClass(SELECTED_CSSCLASS) || $(this).closest('.button-selector').is('.disabled')) {
			return;
		}

		refreshLineItem(this);

		refreshTotals();
	});

	refreshTotals();

});

function refreshLineItem( anchorElement) {
	var mine = anchorElement;
	var parentLine = $(mine).closest('.line-item');
	var qty = $(mine).attr("data-val");
	var usageType = parentLine.data('usage-type');
	var lineItemTotal = calcLineItemTotal(usageType, qty);
	// set the total onto the line item
	parentLine.find('.sum .value').text(lineItemTotal);

}

function refreshAll() {
	//refresh all line items.
	$( ".num-choices .selected >a").each( function () {
		refreshLineItem(this);
	});
	
	refreshTotals();
}

/**
 * looks up the bandwidth factor for the usageType and multiplies it by the
 * amount selected
 * 
 * @param usageType -
 *            one of the values from dataUsageTypes
 * @param numberSelected - Quantity to multiply by bandwidthFactor.
 * @returns A JS Number.
 */
function calcLineItemTotal(usageType, numberSelected) {
	var factor = 0;
	switch (usageType) {
	case "email":
		factor = currentBandwidthValues.emailFactorInGB;
		break;
	case "web":
		factor = currentBandwidthValues.webPageFactorInGB;
		break;
	case "audio":
		factor = currentBandwidthValues.audioFactorInGB;
		break;
	case "video_lo":
		factor = currentBandwidthValues.loResVideoFactorInGB;
		break;
	case "video_hi":
		factor = currentBandwidthValues.highResVideoFactorInGB;
		break;
	case "photos":
		factor = currentBandwidthValues.photoFactorInGB;
		break;
	}

	consoleLog("lineItemTotal: " + usageType + ": " + "dataFactor:" + factor + ", qty: " + numberSelected);
	return new Number(factor * numberSelected).toFixed(2);
}

function calculateTotalUsage() {
	var total = new Number(0);
	$(".line-item .sum .value").each(function() {
		total += new Number($(this).text()).valueOf();
	});

	return total.toFixed(2);
}

// refreshes data-usage totals on page.
// suggested-use: changing bandwidth values, changing day/month.
function refreshTotals() {
	var totalUsage = calculateTotalUsage();
	var newRightPos = gbToPixels(totalUsage);

	var fill = $('#data-calc #meter .bar .fill');

	// slide the bar into the right position
	fill.animate({
		width : newRightPos
	}, "slow");

	// updates total usage box.
	if (totalUsage > 100)
		totalUsage = Math.round(totalUsage);
	$(".result .value").text( totalUsage);
}

//divides usage by maxUsage to get a percent, then takes that percent of maxwidth
// to see how much of the bar should be filled. 
function gbToPixels(usageInGB) {

	var percentage = usageInGB / MAXGB;
	var pixels = percentage * MAXWIDTH;
	if (pixels > MAXWIDTH)
		pixels = MAXWIDTH;
	return Math.round(pixels); // round to nearest pixel.
}

/**
 * Used to change the display values of the Data Usage li's for a given section.   
 * @param isMonth - If false, will divide given values (see global defs at top of file)
 * by the number 30 
 * @param section - The section to update; See dataUsageTypes array. 
 */
function setValues(isMonth, section) {
	// decide which values array we're going to use.
	var valueList;
	switch (section) {
	case "email":
	case "web":
		valueList = webValues;
		break;
	case "audio":
	case "video_lo":
	case "video_hi":
		valueList = streamingValues;
		break;
	case "photos":
		valueList = photoValues;
		break;
	}

	// get collection of li's.
	var selector = 'div[data-usage-type="' + section + '"] li > a';
	var items = $(selector).each(function(index) {
		var labelType = "";
		var labelData = valueList[index];
		if (typeof (labelData) == "string") {
			var vals = labelData.split(" ");

			labelData = vals[0];
			labelType = vals[1];
		}

		var labelVal = labelData;
		if (!isMonth)	{	//want labels to be their normal / 30. Data-val stays the same
			labelVal = Math.round(labelData / 30);
		}
		$(this).html(labelVal + " " + labelType);
		
		if (labelType == "hrs")
			labelData = labelData * 60;

		$(this).attr("data-val", labelData);
	});

} // end of setValues()

/**
 * Used on page load to set appropriate display / data-val for the clickable LI's.
 * Iterative calls to setValues. (see setValues)
 */
function initializeItemLabels() {

	var isMonth = false;
	//foreach dataUsageType, call setValues with month = true. 
	for (i in dataUsageTypes) {
		setValues(isMonth, dataUsageTypes[i]);
	}
}

/**
 * (allowed: "3g", "3g4g", "4g") sets the variable currentBandwidthValues to the
 * corresponding array, defined in globals section at beginning of file.
 * 
 * @param newBand -
 *            the bandwidthArray to use.
 */
function setBandwidth(newBand) {
	
	switch (newBand) {
	case "3g":
		currentBandwidthValues = wicket3Gvals;
		break;
	case "3g4g":
		currentBandwidthValues = wicket3G4Gvals;
		break;
	case "4g":
		currentBandwidthValues = wicket4Gvals;
		break;
	}
	consoleLog("switching to new bandwidths: " + newBand + ": "+ currentBandwidthValues);
}

function consoleLog( message ) {
	if (typeof console != "undefined")
		console.log( new Date().toTimeString() + ":  " + message);
}

function disableRow( sectionType) {
	var selector = '[data-usage-type="' + sectionType + '"] a';
}
