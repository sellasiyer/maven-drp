function getTachDial(elementId, amount, outOf, formattedAmt, formattedGoal,
		style) {
	if (style === "fill") {
		drawDial(elementId, amount, outOf);
	} else if (style === "line") {
		drawLineDial(elementId, amount, outOf);
	}

	label(formattedAmt, formattedGoal);

	function label(formattedAmt, formattedGoal) {
		$('#' + elementId).append('<h2></h2>');
		$('#' + elementId).append('<h3></h3>');

		var amtElement = $('#' + elementId).children('h2');
		amtElement.removeClass();

		// determine whether we need to shrink the current amount
		if (formattedAmt.length > 4) {
			amtElement.addClass("small");
		}

		amtElement.text(formattedAmt);

		var goalElement = $('#' + elementId).children('h3');
		goalElement.removeClass();

		// determine whether we need to shrink the current amount
		if (formattedGoal.length > 4) {
			goalElement.addClass("small");
		}

		goalElement.text("Goal " + formattedGoal);
	}

	function drawLineDial(elementId, amount, outOf) {
		var paper = Raphael(elementId, 78, 108);
		paper.customAttributes.arc = function(centerX, centerY, startAngle,
				endAngle, innerR, outerR) {
			var radians = Math.PI / 180,

			outerX2 = centerX + outerR * Math.cos((endAngle - 90) * radians);
			outerY2 = centerY + outerR * Math.sin((endAngle - 90) * radians);
			innerX1 = centerX + innerR * Math.cos((endAngle - 90) * radians);
			innerY1 = centerY + innerR * Math.sin((endAngle - 90) * radians);

			var moveToStartPoint = [ "M", outerX2, outerY2 ];
			var drawLineInwardsToStartOfInnerEdgeOfArc = [ "L", innerX1,
					innerY1 ];
			var closePath = [ "z" ];

			var path = [ moveToStartPoint,
					drawLineInwardsToStartOfInnerEdgeOfArc, closePath ];
			return {
				path : path
			};
		};

		var barParams = {
			"stroke-width" : 2,
			"stroke" : "#b8dbf5"
		}, cx = 39, cy = 69, innerRadius = 27, outerRadius = 37;

		var myAngles = translateToStartEndAngle(amount, outOf, true);

		paper.path().attr(barParams).attr(
				{
					arc : [ cx, cy, myAngles.begin, myAngles.end, innerRadius,
							outerRadius ]
				});
	}

	function drawDial(elementId, amount, outOf) {
		var paper = Raphael(elementId, 78, 108);
		paper.customAttributes.arc = function(centerX, centerY, startAngle,
				endAngle, innerR, outerR) {
			var radians = Math.PI / 180, largeArc = +(endAngle - startAngle > 180);
			// calculate the start and end points for both inner and outer edges
			// of the arc segment
			// the -90s are about starting the angle measurement from the top
			// get rid of these if this doesn't suit your needs
			outerX1 = centerX + outerR * Math.cos((startAngle - 90) * radians);
			outerY1 = centerY + outerR * Math.sin((startAngle - 90) * radians);
			outerX2 = centerX + outerR * Math.cos((endAngle - 90) * radians);
			outerY2 = centerY + outerR * Math.sin((endAngle - 90) * radians);
			innerX1 = centerX + innerR * Math.cos((endAngle - 90) * radians);
			innerY1 = centerY + innerR * Math.sin((endAngle - 90) * radians);
			innerX2 = centerX + innerR * Math.cos((startAngle - 90) * radians);
			innerY2 = centerY + innerR * Math.sin((startAngle - 90) * radians);

			var moveToStartPoint = [ "M", outerX1, outerY1 ];
			var drawOuterEdgeOfArc = [ "A", outerR, outerR, 0, largeArc, 1,
					outerX2, outerY2 ];
			var drawLineInwardsToStartOfInnerEdgeOfArc = [ "L", innerX1,
					innerY1 ];
			var drawInnerArc = [ "A", innerR, innerR, 0, largeArc, 0, innerX2,
					innerY2 ];
			var closePath = [ "z" ];

			var path = [ moveToStartPoint, drawOuterEdgeOfArc,
					drawLineInwardsToStartOfInnerEdgeOfArc, drawInnerArc,
					closePath ];
			return {
				path : path
			};
		};

		var barParams = {
			"stroke-width" : 0,
			"fill" : "120-#233f65-#749bd0"
		};
		cx = 39, cy = 69, innerRadius = 25, outerRadius = 38;

		var myAngles = translateToStartEndAngle(amount, outOf, false);

		if (myAngles.begin !== myAngles.end) {
			paper.path().attr(barParams).attr(
					{
						arc : [ cx, cy, myAngles.begin, myAngles.end,
								innerRadius, outerRadius ]
					});
		}
	}

	function translateToStartEndAngle(number, outOf, limit) {
		var fullAngle = 266;
		// don't let it go over 100%
		var percent = (number <= outOf) ? number / outOf : 1.0;

		//don't let it go under 0
		if(percent < 0) percent = 0.0;

		if(limit){
			if(percent < 0.01){
				percent = 0.01;
			}else if (percent > 0.99){
				percent = 0.99;
			}
		}
		
		var startAngle = 0 - (fullAngle / 2);
		var endAngle = startAngle + Math.round(fullAngle * percent);
		var angles = {
			begin : startAngle,
			end : endAngle
		};
		return angles;
	}
}
