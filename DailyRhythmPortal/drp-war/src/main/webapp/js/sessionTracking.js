var oneSecond = 1000;
var oneMinute = 60 * oneSecond;
var reloadPage = 0;
var consoleDebug = 0;	//set to 1 in console to enable printing.

/* Keep Alive Logic */
var userActivity = false;

var keepAliveBehavior; // wicketBehavior. Don't touch!

/* session timeout logic */
var activityInterval;
var activityIntervalDuration = 30 * oneSecond;	//wicket supplied maybe 
$(document).ready(function() {

	// apply onblur/onfocus to all input fields.
	$('input, select, textarea, a').blur( userAction);

	$('input, select, textarea, a').focus( userAction);

	// setup keepalive interval:
	activityInterval = setInterval(keepAlive, activityIntervalDuration);
});


function userAction() {
	userActivity = true;
	if (elapsedTime_seconds > timeoutAt_seconds) 
		//showWarning();
		keepAliveBehavior();
}

function keepAlive() {
	if (userActivity == true) {
		userActivity = false;
		keepAliveBehavior();
	}
}

// ==================================================================
// Countdown / Warning logic:
var warningAt_seconds;	//wicket supplied
var timeoutAt_seconds;  //wicket supplied
var elapsedTime_seconds = 0;	//wicket will reset this when it wants to.

var timeoutWarningBehavior; // wicketBehavior. Don't touch!

var oneSecondInterval;

// set clock pulse to every 1s.
$(document).ready(function() {
	//oneSecondInterval = setInterval(clockPulse, oneSecond);
	//setinterval was messing up after ajax calls.
	oneSecondInterval = setTimeout(clockPulse, oneSecond);
	
	
});

function clockPulse() {
	elapsedTime_seconds++;
	if (elapsedTime_seconds == warningAt_seconds)
		showWarning();
	if (elapsedTime_seconds % 5 == 0 )
		consoleLog(elapsedTime_seconds + "s passed");
	if (reloadPage == 1) {
           window.location.reload();
           reloadPage = 0;
        }

	oneSecondInterval = setTimeout(clockPulse, oneSecond);

	
	refresh();
}

function showWarning() {
	$("#SESS_TIMEOUT_POPUP").show();
}

function hideWarning() {
	$("#SESS_TIMEOUT_POPUP").hide();
}

function resetElapsedTime() {
	elapsedTime_seconds = 0;
}

function refresh() {

	if (elapsedTime_seconds < warningAt_seconds)
		return;

	if (elapsedTime_seconds > timeoutAt_seconds) {
		hideWarning();
		clearTimeout( oneSecondInterval);
	}
		
	var timestr = makeTimeStr(timeoutAt_seconds - elapsedTime_seconds);
	$("#SESS_TIMEOUT_CNTDOWN").html(timestr);
}

function zeroPad(number) {
	if (number < 10)
		return "0" + number;
	return number;
}

function makeTimeStr(timeVal) {
	if (timeVal < 0)
		timeVal = 0;
	var secs = timeVal % 60;
	var mins = (timeVal - secs) / 60;

	var str = zeroPad(mins) + ":" + zeroPad(secs);
	return str;
}

function consoleLog( message ) {
	if (typeof console != "undefined" && consoleDebug)
		console.log( new Date().toTimeString() + ":  " + message);
}
