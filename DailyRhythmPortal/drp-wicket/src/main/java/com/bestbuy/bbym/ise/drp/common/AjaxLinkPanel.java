package com.bestbuy.bbym.ise.drp.common;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * For use in tables when you need a link with an ajaxRequestTarget
 * 
 * @author a885527
 * 
 */
public abstract class AjaxLinkPanel extends Panel {

    private static final long serialVersionUID = 1L;

    private Model<String> linkLabelModel;
    private AjaxLink<Void> mainLink;

    public AjaxLinkPanel(String id, String linkText) {
	super(id);

	mainLink = new AjaxLink<Void>("mainLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		AjaxLinkPanel.this.onClick(target);
	    }
	};
	add(mainLink);
	mainLink.setOutputMarkupId(true);

	linkLabelModel = new Model<String>(linkText);
	mainLink.add(new Label("mainLinkLabel", linkLabelModel));
    }

    public abstract void onClick(AjaxRequestTarget target);

    public IModel<String> getLinkLabelModel() {
	return linkLabelModel;
    }

}
