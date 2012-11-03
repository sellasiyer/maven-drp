package com.bestbuy.bbym.ise.drp.beast.tradein;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

public class NotificationPanel extends FeedbackPanel {

    private static final long serialVersionUID = 1L;
    private final BeastBorder beastBorder;

    // Create a notifcation panel with the default additional class, specified as a field variable
    public NotificationPanel(String id, final BeastBorder beastBorder) {
	super(id);
	this.beastBorder = beastBorder;

	init(id);
    }

    private void init(String id) {
	// set custom markup id and ouput it, to find the component later on in the js function
	setMarkupId(id);
	setOutputMarkupId(true);
    }

    /**
     * Method to refresh the notification panel
     *
     * if there are any feedback messages for the user, find the gravest level,
     * format the notification panel accordingly and show it
     *
     * @param target
     *            AjaxRequestTarget to add panel and the calling javascript function
     */
    public boolean refresh(AjaxRequestTarget target) {

	List<FeedbackMessage> messages = getCurrentMessages();
	StringBuilder builder = new StringBuilder();

	for(FeedbackMessage current: messages){
	    builder.append(current.getMessage()).append("\n");
	}

	if (builder.length() > 0){
	    beastBorder.displayError(builder.toString(), target);
	    return true;
	}
	return false;
    }
}
