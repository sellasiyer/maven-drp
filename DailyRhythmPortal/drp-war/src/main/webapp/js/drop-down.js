$(document).ready(function() {
	createDropdowns();
}); // End Document Ready

/*
 * CREATE DROPDOWN: USAGE - Add the class 'create-dropdown' to any select menu
 * you want to replace with the custom dropdown - Add data attribute
 * 'data-instant-launch="true"' to any select menu you want to launch instantly
 * (this is an external function) - Select options with the data attribute
 * 'data-add-class="class-name"' will add that class to the dropdown on click
 * useful for color changes or icons.  data-parent-class will add the given class
 * to the dropdown, for styling. data-first-item-label can be used to indicate that
 * the first option is just a label and should not appear in the dropdown.
 */

function createDropdowns() {
	$('select.create-dropdown').not('.dynamic').each(function(i) {

		var ddClass = "dropdown";

		var select = $(this);
		if(select.data('parent-class') !== ""){
			ddClass += " " + select.data('parent-class');
		}

		var dropdownWrap = $('<div class="dropdown-wrap"></div>');
		var dropdown = $('<div class="' + ddClass + '"></div>');
		var primary = $('<a class="primary"></a>');
		var ul = $('<ul>');

		// LOOP THROUGH OPTIONS CREATING LIST
		select.find('option').each(function(j) {
			var option = $(this);

			// primary
			if (option.attr('selected')) {
				primary.html(option.text() + '<span class="arrow"></span>');
				dropdown.addClass(option.data('add-class'));
			}

			var li = $('<li class="option">');
			li.html(option.text());
			li.attr('data-value', option.val());
			li.attr('data-jump', option.data('jump'));
			li.attr('data-add-class', option.data('add-class'));
			ul.append(li);
		});

		// DON'T INLCLUDE FIRST OPTION IF USING INSTANT LAUNCH
		if (select.data('instant-launch') || select.data('first-item-label')) {
			ul.find('li:first-child').remove();
		}

		// ASSEMBLE
		dropdown.append(primary);
		dropdown.append(ul);
		dropdownWrap.append(dropdown);


		// INSERT MARKUP
		select.after(dropdownWrap);

		// BIND
		dropdown.find('.primary').bind('click', clickDropdown);
		dropdown.find('.option').bind('click', clickDropdown);

		// MOVE & HIDE ORIGINAL SELECT
		select.appendTo(dropdownWrap);
		select.hide();
	});
}

function clickDropdown(e) {
	var clickedItem = $(this);
	var dropdownWrap = clickedItem.parents('.dropdown-wrap');
	var dropdown = dropdownWrap.find('.dropdown');
	var options = dropdown.find('ul');
	var currentOption = options.find('.selected');
	var select = dropdownWrap.find('select');

	e.stopPropagation();

	if (clickedItem.hasClass('option')) {
		if (select.data('instant-launch')) {
			// INSTANT LAUNCH
			selectID = select.attr('id');
			optionValue = clickedItem.attr('data-value');
			tableID = clickedItem.closest('table').attr('id');
			dropdownLaunch(selectID, optionValue, tableID);
		} else {
			// OPERATE NORMALLY
			dropdown.find('a.primary').html(
					clickedItem.html() + '<span class="arrow"></span>'); // Update
																			// text
			dropdown.addClass(clickedItem.data('add-class')); // Update
																// 'add-class'
			select.val(clickedItem.data('value')); // Update hidden select menu
			select.trigger('change');
		}

	} else {
		dropdown.addClass(currentOption.data('add-class'));
	}

	// SET SELECTED ITEM
	$('li.option').removeClass('selected');
	$('li.option[data-value=' + select.val() + ']').addClass('selected');

	// TOGGLE DROPDOWN
	dropdown.toggleClass('open');
	if (dropdown.hasClass('open')) {
		options.slideToggle(225, 'easeInOutCubic'); // Open Dropdown
		dropdown.attr('class', 'dropdown open'); // Reset Classes

		if(select.data('parent-class')){
			dropdown.addClass(select.data('parent-class'));
		}

		$(document).bind('click', clickOutsideDropdown); // Enable clicking
															// outside the
															// dropdown check
	} else {
		options.hide();
	}
}

function clickOutsideDropdown(e) {
	var dropdown = $('.dropdown.open');
	var options = dropdown.find('ul');
	var currentOption = options.find('.selected');

	$(document).unbind('click', clickOutsideDropdown); // Disable clicking
														// outside the dropdown
														// check
	dropdown.removeClass('open');
	options.hide();
	dropdown.addClass(currentOption.data('add-class')); // sometimes won't do
														// anything
}

/*
 * UPDATE DROP DOWN This is used to update the dropdown list color and selected
 * text when dropdowns are added/modified after initial page load.
 */

function updateDropdown(element) {
	var dropdownWrap = element;
	var dropdown = dropdownWrap.find('.dropdown');
	var selectedOption = dropdownWrap.find('option:selected');
	var primary = dropdownWrap.find('.primary');
	primary.html(selectedOption.text() + '<span class="arrow"></span>');
	dropdown.addClass(selectedOption.data('add-class'));
}