package com.bestbuy.bbym.ise.drp.msw;

// "borrowed" from
// http://www.jroller.com/karthikg/entry/modelling_client_side_form_modifications#comments

import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.util.collections.MiniMap;
import org.apache.wicket.util.template.PackageTextTemplate;

public class FormModificationDetectorBehavior extends AbstractBehavior {

    private static final long serialVersionUID = 1L;

    // Wicket calls this method just after a behavior is bound to
    // a Component and that's nice as we need access to the Form
    // component.

    public void bind(Component component) {
	if (!(component instanceof Form)){
	    throw new WicketRuntimeException(getClass() + " behavior can be bound only to a Form component");
	}
	Form<?> form = (Form<?>) component;

	// This will make sure that the Form emits the
	// HTML 'ID' attribute when rendering. We require the 'ID' in our JS file

	form.setOutputMarkupId(true);
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
	super.renderHead(component, response);
	PackageTextTemplate template = new PackageTextTemplate(getClass(), "FormModificationDetector.js");
	response.renderJavaScript(template.asString(), component.getMarkupId());
    }

    // TextTemplateHeaderContributor expects to be supplied with a Map
    // as the backing Model. It will use the map to perform variable
    // substitution.

    class FormModificationDetectorModel extends AbstractReadOnlyModel {

	private static final long serialVersionUID = 1L;

	private Map<String, String> variables;
	private String formMarkupid;

	FormModificationDetectorModel(String formMarkupid) {
	    this.formMarkupid = formMarkupid;
	}

	// return the Map as the model object
	@Override
	public Object getObject() {
	    if (variables == null){
		// Use Wicket's built-in MiniMap when the
		// number of Map entries are known upfront.
		// A nice way of controlling Wicket's memory usage.
		// Also, while we are on this subject, you might also
		// want to look at the MicroMap class

		this.variables = new MiniMap<String, String>(2);

		// provide runtime values for the 'form_id' and 'message'
		variables.put("form_id", formMarkupid);
		variables.put("message", getDisplayMessage());
	    }
	    return variables;
	}

    };

    // override this if you want to see a different message displayed
    protected String getDisplayMessage() {
	return "You've made changes to this Recommendation sheet. Leaving the page will erase these changes.";
    }
}
