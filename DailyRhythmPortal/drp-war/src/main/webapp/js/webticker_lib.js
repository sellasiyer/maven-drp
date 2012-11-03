function ticker_start() {
	TICKER_CONTENT = document.getElementById(TICKER_ID).innerHTML;
	var tickerSupported = false;
	TICKER_WIDTH = document.getElementById(TICKER_ID).style.width;
	var img = "<img src=../images/spacer.png width=" + TICKER_WIDTH
			+ " height=1>";

	$('#' + TICKER_ID).mouseover(function() {
		TICKER_PAUSED = true;
	});
	$('#' + TICKER_ID).mouseout(function() {
		TICKER_PAUSED = false;
	});

	if (navigator.userAgent.indexOf("MSIE") != -1
			|| navigator.userAgent.indexOf("Firefox") != -1
			|| navigator.userAgent.indexOf("Safari") != -1) {
		document.getElementById(TICKER_ID).innerHTML = "<table cellspacing='0' cellpadding='0' width='100%'><tr><td nowrap='nowrap'>"
				+ img
				+ "<span id='"
				+ TICKER_ID
				+ "_body' width='100%'>&nbsp;</span>"
				+ img
				+ "</td></tr></table>";
		tickerSupported = true;
	}
	if (!tickerSupported) {
		document.getElementById(TICKER_ID).outerHTML = "";
	} else {
		document.getElementById(TICKER_ID).scrollLeft = TICKER_RIGHTTOLEFT ? document
				.getElementById(TICKER_ID).scrollWidth
				- document.getElementById(TICKER_ID).offsetWidth
				: 0;
		document.getElementById(TICKER_ID + "_body").innerHTML = TICKER_CONTENT;
		document.getElementById(TICKER_ID).style.display = "block";
		TICKER_tick();
	}
}

function TICKER_tick() {
	if (!TICKER_PAUSED) {
		document.getElementById(TICKER_ID).scrollLeft += TICKER_SPEED
				* (TICKER_RIGHTTOLEFT ? -1 : 1);
	}
	if (TICKER_RIGHTTOLEFT
			&& document.getElementById(TICKER_ID).scrollLeft <= 0) {
		document.getElementById(TICKER_ID).scrollLeft = document
				.getElementById(TICKER_ID).scrollWidth
				- document.getElementById(TICKER_ID).offsetWidth;
	}
	if (!TICKER_RIGHTTOLEFT
			&& document.getElementById(TICKER_ID).scrollLeft >= document
					.getElementById(TICKER_ID).scrollWidth
					- document.getElementById(TICKER_ID).offsetWidth) {
		document.getElementById(TICKER_ID).scrollLeft = 0;
	}
	window.setTimeout("TICKER_tick()", 30);
}
