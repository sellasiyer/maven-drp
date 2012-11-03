function setupPropertyTable() {
	// Remove sorting from Action column
	var jSpan = $('#propertyTable tr th:eq(2) a span');
	$('#propertyTable tr th:eq(2) a').replaceWith(jSpan);

	// Row highlighting on mouse over
	$('#propertyTable tr:[class="odd"]').mouseover(function() {
		$(this).removeClass('even').addClass('row_highlight');
	}).mouseout(function() {
		$(this).removeClass('row_highlight').addClass('odd');
	});
	$('#propertyTable tr:[class="even"]').mouseover(function() {
		$(this).removeClass('even').addClass('row_highlight');
	}).mouseout(function() {
		$(this).removeClass('row_highlight').addClass('even');
	});

	// Add styling classes to cells
	$('#propertyTable tr').each(function(index) {
		$(this).find('th:eq(1)').addClass("panelTable_02");
		$(this).find('td:eq(1)').addClass("panelTable_02");
		$(this).find('th:first-child').addClass("panelTable_first");
		$(this).find('th:last-child').addClass("panelTable_last");
		$(this).find('td:first-child').addClass("panelTable_first");
		$(this).find('td:last-child').addClass("panelTable_last");
	});
}

function handleAddPropertyButtonEnabling(enabled) {
	if (enabled) {
		$('#add_property_link a').removeAttr('disabled').removeClass(
				'btninactive').addClass('btnactive');
	} else {
		$('#add_property_link a').attr('disabled', true).removeClass(
				'btnactive').addClass('btninactive');
	}
}
