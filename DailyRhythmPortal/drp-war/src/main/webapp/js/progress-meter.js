function setStoreProgressMeter(meterId, amount, goal, timeDisplay,
		timeFractionComplete, productivity, textAmount, textGoal,
		textProductivity) {
	var meterSel = '#' + meterId;
	var barWidth = $(meterSel + ' .bar').width();
	var storeBarWidth = $(meterSel).innerWidth();
	var pctLabelWidth = $(meterSel + ' .percent').width();

	// don't go off the side of the panel
	var labelMaxLeft = storeBarWidth - pctLabelWidth;

	var percentComplete = 0;
	if (goal > 0) {
		percentComplete = Math.floor((amount / goal) * 100);
	}
	percentComplete = (percentComplete < 0) ? 0 : percentComplete;
	percentComplete = (percentComplete > 100) ? 100 : percentComplete;

	// set the bar fill width
	var fillWidth = Math.floor((percentComplete * .01) * barWidth);
	$(meterSel + ' .bar .fill').css('width', fillWidth);

	// set the "next hour goal" location
	var nextHrWidth = Math.floor(timeFractionComplete * barWidth);
	$(meterSel + ' .time-pointer').css('width', nextHrWidth);

	if (productivity >= 0) {
		$(meterSel + ' .pph .number').text(textProductivity);
	}

	$(meterSel + ' .measure .amt').text(textAmount);
	$(meterSel + ' .measure .goal').text(' / ' + textGoal + " Goal");

	// set the next hour goal time position
	var nextHrLabelMargin = nextHrWidth - 19;
	if (nextHrLabelMargin < -5) {
		// don't let it slide too far to the left
		nextHrLabelMargin = -5;
	} else if (nextHrLabelMargin > 320) {
		// or to the right
		// nextHrLabelMargin = 320;
	}
	$(meterSel + ' .time').css('margin-left', nextHrLabelMargin);

	// set the time value
	$(meterSel + ' .time').text(timeDisplay);

	// now set the label position
	var labelLeft;
	if (fillWidth === 0) {
		labelLeft = 0;
	} else if ((fillWidth - 2) < labelMaxLeft) {
		labelLeft = fillWidth - 2;
	} else {
		labelLeft = labelMaxLeft;
	}

	if (labelLeft === 0) {
		$(meterSel + ' .bar .shadow').hide();
	} else {
		$(meterSel + ' .bar .shadow').show();
	}
	$(meterSel + ' .percent').css('left', labelLeft);
	$(meterSel + ' .percent').text(percentComplete + "%");
}

function setOtherProgressMeter(meterId, amount, goal, productivity, textAmount,
		textGoal, textProductivity) {
	var meterSel = $('#' + meterId);
	var barWidth = meterSel.width();

	// normalize to 0-100%
	var percentComplete = Math.floor((amount / goal) * 100);
	percentComplete = (percentComplete < 0) ? 0 : percentComplete;
	percentComplete = (percentComplete > 100) ? 100 : percentComplete;

	// set the bar fill width
	var fillWidth = Math.floor((percentComplete * .01) * barWidth);
	meterSel.children('.fill').css('width', fillWidth);

	if (percentComplete === 0) {
		meterSel.children('.shadow').hide();
	} else {
		meterSel.children('.shadow').show();
	}

	if (productivity >= 0) {
		meterSel.parent().find('.pph .number').text(textProductivity);
		meterSel.parent().find('.measure .amt').text(textAmount);
		meterSel.parent().find('.measure .goal')
				.text('/ ' + textGoal + " Goal");
	}
}
