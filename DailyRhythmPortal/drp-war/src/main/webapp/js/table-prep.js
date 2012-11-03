function tablePrep(tableProps){
	var table = $('#'+tableProps.tableID);
		
	// TIDY HEADER
	if(tableProps.tidyHeader){
		// Remove spans but not their content
		table.find('span').each(function (i) {
			$(this).replaceWith($(this).html());
		});
	}
	
	// STRIP ANCHOR HREF ATTRIBUTES FROM HEADER
	if(tableProps.stripHeaderAnchorHref){
		table.find('th a').removeAttr('href');
		table.find('th a').removeAttr('onclick');
	}
		
	// ADD LAST CLASS 'LAST' TO CELLS
	if(tableProps.lastClasses){
		table.find('tr th').removeClass('last');
		table.find('tr th:last-child').addClass('last');
		table.find('tr').removeClass('last');
		table.find('tr:last-child').addClass('last');
		table.find('tr td').removeClass('last');
		table.find('tr td:last-child').addClass('last');
	}
	
	// ADD ODD CLASES TO EVERY OTHER ROW
	if(tableProps.oddClasses){
		table.find('tr').removeClass('odd');
		table.find('tr:nth-child(odd)').addClass('odd');
	}
	
	// SET THE WIDTHS OF THE COLUMNS
	var cellWidths = tableProps.widths;
	table.find('th').each(function(i){
		if(cellWidths[i] != '-'){
			$(this).attr('width',cellWidths[i]);
		}
	});
	
	// SET TEXT ALIGNMENT
	var alignments = tableProps.textAlignment;
	for (var i in alignments) {
		columnNum = parseFloat(i) + 1;
		if (alignments[i] === 'c'){
			table.find('tr>td:nth-child('+columnNum+')').addClass('txt-center');
		} else if (alignments[i] === 'r'){
			table.find('tr>td:nth-child('+columnNum+')').addClass('txt-right');
		}
	}
	
	// SET WORD WRAPPING
	var truncates = tableProps.truncate;
	for (var i in truncates) {
		columnNum = parseFloat(i) + 1;
		if (truncates[i] === false){
			table.find('tr>td:nth-child('+columnNum+')').addClass('wrap');
		}
	}
	
	// HANDLE TABLE SORTING
	var sortable = tableProps.sortable;
	table.find('th').each(function(i){
		if(sortable[i]){
			$(this).addClass('sortable');
		}
	});
 	
	table.find('th.sortable').each(function(){
		var th = $(this),
			thIndex = th.index(),
			inverse = false;
	        
		th.click(function(){
			
			table.find('td').filter(function(){
				return $(this).index() === thIndex;
			}).
			sortElements(function(a, b){
				return $.text([a]) > $.text([b]) ?
				inverse ? -1 : 1
				: inverse ? 1 : -1;
			}, function(){
				// parentNode is the element we want to move
				return this.parentNode;
			});
			inverse = !inverse;
			
			// Update last row and odd classes
			table.find('tr').removeClass('last');
			table.find('tr:last').addClass('last');
			table.find('tr').removeClass('odd');
			table.find('tr:nth-child(odd)').addClass('odd');
		});
		
	});
	var jNoRec = $('#' + tableProps.tableID
			+ ' tfoot tr.norecords-tr td.norecords-td');
	if (jNoRec.length > 0) {
		var colspan = parseInt(jNoRec.attr('colspan'), 10);
		var colClass = jNoRec.attr('class');
		if (colspan > 1) {
			jNoRec.after("<td class='" + colClass + "'></td>");
			jNoRec.attr('colspan', (colspan - 1));
			jNoRec.removeClass('last');
		}
	}
}



/**
 * jQuery.fn.sortElements
 * --------------
 * @author James Padolsey (http://james.padolsey.com)
 * @version 0.11
 * @updated 18-MAR-2010
 * --------------
 * @param Function comparator:
 *   Exactly the same behaviour as [1,2,3].sort(comparator)
 *   
 * @param Function getSortable
 *   A function that should return the element that is
 *   to be sorted. The comparator will run on the
 *   current collection, but you may want the actual
 *   resulting sort to occur on a parent or another
 *   associated element.
 *   
 *   E.g. $('td').sortElements(comparator, function(){
 *      return this.parentNode; 
 *   })
 *   
 *   The <td>'s parent (<tr>) will be sorted instead
 *   of the <td> itself.
 */
jQuery.fn.sortElements = (function(){
    
    var sort = [].sort;
    
    return function(comparator, getSortable) {
        
        getSortable = getSortable || function(){return this;};
        
        var placements = this.map(function(){
            
            var sortElement = getSortable.call(this),
                parentNode = sortElement.parentNode,
                
                // Since the element itself will change position, we have
                // to have some way of storing it's original position in
                // the DOM. The easiest way is to have a 'flag' node:
                nextSibling = parentNode.insertBefore(
                    document.createTextNode(''),
                    sortElement.nextSibling
                );
            
            return function() {
                
                if (parentNode === this) {
                    throw new Error(
                        "You can't sort elements if any one is a descendant of another."
                    );
                }
                
                // Insert before flag:
                parentNode.insertBefore(this, nextSibling);
                // Remove flag:
                parentNode.removeChild(nextSibling);
                
            };
            
        });
       
        return sort.call(this, comparator).each(function(i){
            placements[i].call(getSortable.call(this));
        });
        
    };
    
})();