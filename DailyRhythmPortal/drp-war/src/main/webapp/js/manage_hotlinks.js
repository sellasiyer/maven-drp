function setupHotlinkTable() {
	// Remove sorting from all columns
	$('#hotlinkTable tr th').each(function(index) {
		var jSpan = $(this).find('a span');
		$(this).find('a').replaceWith(jSpan);
	});

	// Row highlighting on mouse over
	$('#hotlinkTable tr:[class="odd"]').mouseover(function() {
		$(this).removeClass('even').addClass('row_highlight');
	}).mouseout(function() {
		$(this).removeClass('row_highlight').addClass('odd');
	});
	$('#hotlinkTable tr:[class="even"]').mouseover(function() {
		$(this).removeClass('even').addClass('row_highlight');
	}).mouseout(function() {
		$(this).removeClass('row_highlight').addClass('even');
	});

	// Add styling classes to cells
	$('#hotlinkTable tr').each(function(index) {
		$(this).find('th:eq(1)').addClass("panelTable_02");
		$(this).find('td:eq(1)').addClass("panelTable_02");
		$(this).find('th:eq(2)').addClass("panelTable_03");
		$(this).find('td:eq(2)').addClass("panelTable_03");
		$(this).find('th:first-child').addClass("panelTable_first");
		$(this).find('th:last-child').addClass("panelTable_last");
		$(this).find('td:first-child').addClass("panelTable_first");
		$(this).find('td:last-child').addClass("panelTable_last");
	});
}

function handleAddHotlinkButtonEnabling(enabled) {
	if (enabled) {
		$('#add_hotlink_link a').removeAttr('disabled').removeClass(
				'btninactive').addClass('btnactive');
	} else {
		$('#add_hotlink_link a').attr('disabled', true).removeClass(
				'btnactive').addClass('btninactive');
	}
}

