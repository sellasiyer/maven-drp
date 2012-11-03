// Search Customers Panel
var wicketBehaviorSCP;
function showBestBuyCustomerSearchLoading(enabled) {
	if (enabled) {
		$('#searchCustomersLoading').show();
		$('#searchCustomersContainer').hide();
		$('#noSearchCustomersContainer').hide();
		$('#searchCustomersFilter input.button').attr('disabled', true);
	} else {
		$('#searchCustomersLoading').hide();
		$('#searchCustomersContainer').show();
		$('#noSearchCustomersContainer').show();
		$('#searchCustomersFilter input.button').removeAttr('disabled');
	}
}
function setupSelectBestBuyCustomerLoading() {
	$("#searchCustomersTable tr td:last-child span a").click(function() {
		$(this).replaceWith("<div class='loading-spinner-small'></div>");
	});
}
function selectSearchCustomersNavFilter(filter) {
	$('#searchCustomersFilter li.open').each(function(index) {
		$(this).removeClass('open');
	});
	if (filter && filter == 'PHONE_NUMBER') {
		$('#searchCustomersFilter li:eq(0)').addClass('open');
	} else if (filter && filter == 'FN_LN_ZIP') {
		$('#searchCustomersFilter li:eq(1)').addClass('open');
	} else if (filter && filter == 'EMAIL') {
		$('#searchCustomersFilter li:eq(2)').addClass('open');
	}
}
var custInfoModalSearchCustomersTable = {
	tableID : 'searchCustomersTable',
	stripHeaderAnchorHref : true,
	tidyHeader : false,
	oddClasses : true,
	lastClasses : true,
	widths : [ '100', '100', '-', '100', '60', '110', '70' ],
	textAlignment : [ 'l', 'l', 'l', 'l', 'l', 'l', 'r' ],
	sortable : [ true, true, true, true, true, true, false ],
	truncate : [ true, true, true, true, true, true, false ]
};

// Search Reward Zones Panel
var wicketBehaviorRZP;
function showRewardZoneSearchLoading(enabled) {
	if (enabled) {
		$('#searchRewardZonesLoading').show();
		$('#searchRewardZonesContainer').hide();
		$('#noSearchRewardZonesContainer').hide();
		$('#searchRewardZonesFilter input.button').attr('disabled', true);
	} else {
		$('#searchRewardZonesLoading').hide();
		$('#searchRewardZonesContainer').show();
		$('#noSearchRewardZonesContainer').show();
		$('#searchRewardZonesFilter input.button').removeAttr('disabled');
	}
}
function selectSearchRewardZonesNavFilter(filter) {
	$('#searchRewardZonesFilter li.open').each(function(index) {
		$(this).removeClass('open');
	});
	if (filter && filter == 'PHONE_NUMBER') {
		$('#searchRewardZonesFilter li:eq(0)').addClass('open');
	} else if (filter && filter == 'FN_LN_ZIP') {
		$('#searchRewardZonesFilter li:eq(1)').addClass('open');
	} else if (filter && filter == 'EMAIL') {
		$('#searchRewardZonesFilter li:eq(2)').addClass('open');
	}
}
var custInfoModalSearchRewardZonesTable = {
	tableID : 'searchRewardZonesTable',
	stripHeaderAnchorHref : true,
	tidyHeader : false,
	oddClasses : true,
	lastClasses : true,
	widths : [ '250', '-', '100', '70', '70', '70', '70' ],
	textAlignment : [ 'l', 'l', 'l', 'l', 'l', 'l', 'r' ],
	sortable : [ true, true, true, true, true, true, false ],
	truncate : [ false, true, true, true, true, true, false ]
};

// GSP Select Panel
var wicketBehaviorGSP;
var custInfoModalGspSelectTable = {
	tableID : 'gspTable',
	stripHeaderAnchorHref : true,
	tidyHeader : false,
	oddClasses : true,
	lastClasses : true,
	widths : [ '60', '90', '180', '60', '-', '70' ],
	textAlignment : [ 'l', 'l', 'l', 'l', 'l', 'r' ],
	sortable : [ false, false, false, false, false, false ],
	truncate : [ true, true, false, true, true, true ]
};

// Purchase Select Panel
var wicketBehaviorPSP;
var custInfoModalPurchaseSelectTable = {
	tableID : 'purchasesTable',
	stripHeaderAnchorHref : true,
	tidyHeader : false,
	oddClasses : true,
	lastClasses : true,
	widths : [ '100', '130', '100', '200', '140', '70' ],
	textAlignment : [ 'l', 'l', 'l', 'l', , 'l', 'r' ],
	sortable : [ false, false, false, false, false, false ],
	truncate : [ true, true, true, false, true, false ]
};
