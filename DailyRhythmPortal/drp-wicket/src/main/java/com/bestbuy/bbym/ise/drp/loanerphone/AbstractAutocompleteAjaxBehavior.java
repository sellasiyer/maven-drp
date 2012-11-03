package com.bestbuy.bbym.ise.drp.loanerphone;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.handler.TextRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Integrates jquery autocomplete function with wicket.
 * You must reference js/jquery-ui-1.8.19.autocomplete.min.js and css/ui-lightness/jquery-ui-1.8.19.custom.css on your page.
 * FIXME: is it possible to have the dependent files automatically referenced?
 */
abstract class AbstractAutocompleteAjaxBehavior extends AbstractAjaxBehavior {

    private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(AbstractAutocompleteAjaxBehavior.class);

    private TextField<String> textField;

    public AbstractAutocompleteAjaxBehavior(TextField<String> textField) {
	this.textField = textField;
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
	super.renderHead(component, response);
	String js = "$(document).ready(function(){\n" + "    $.getJSON(\"" + getCallbackUrl()
		+ "\", function(data) {\n" + "        $(\"#" + textField.getMarkupId()
		+ "\").autocomplete({source:data});\n" + "    });\n" + "});\n";
	response.renderJavaScript(js, null);
    }

    /* This is a more complicated design where we take over the filtering process.
     * This is necessary if we want to implement a phonetic search algorithm like metaphone.
    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        String cacheName = textField.getId() + "_cache";
        String js = 
    	"var " + cacheName + ";\n"
    	+ "$(document).ready(function(){\n"
    	+ "    $(\"#" + textField.getMarkupId() + "\").autocomplete({\n"	
    	+ "        source: function(req, add) {\n"
    	+ "            if (" + cacheName + " == null) {\n"
    	+ "                $.getJSON(\"" + getCallbackUrl() + "\", function(data) {\n"
    	+ "                    " + cacheName + " = data;\n"
    	+ "                    add(doFilter(req.term, " + cacheName + "));\n"
    	+ "                });\n"
    	+ "            }\n"
    	+ "            else {\n"
    	+ "                add(doFilter(req.term, " + cacheName + "));\n"
    	+ "            }\n"
    	+ "        }\n"
            + "    });\n"
            + "});\n";
        response.renderJavascript(js, null);
    }
     */

    public void onRequest() {
	String[] data = getData();
	if (data == null){
	    logger.info("No data");
	}
	String json = "[";
	for(int i = 0; i < data.length; ++i){
	    if (i != 0){
		json += ",";
	    }
	    // Don't forget to escape quotes!
	    String escapedData = data[i].replace("\"", "\\\"").replace("'", "\\\'");
	    json += "\"" + escapedData + "\"";
	}
	json += "]";
	IRequestHandler requestHandler = new TextRequestHandler("application/json", "UTF-8", json);
	textField.getRequestCycle().scheduleRequestHandlerAfterCurrent(requestHandler);
    }

    /**
     * You must override this to provide a list of suggestions. This is called
     * once upon page load. The list is further filtered on the client.
     */
    public abstract String[] getData();
};
