package com.bestbuy.bbym.ise.drp.loanerphone;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.common.ModalPanel;
import com.bestbuy.bbym.ise.drp.domain.CheckOutCheckInHistory;
import com.bestbuy.bbym.ise.drp.domain.Phone;

public class NotesDialogPanel extends ModalPanel {

    private Logger logger = LoggerFactory.getLogger(NotesDialogPanel.class);
    private static final long serialVersionUID = 1L;

    private Form<Object> notesForm;
    private FeedbackPanel feedbackPanel;

    private AjaxSubmitLink okLink;
    private AjaxLink<Void> cancelLink;

    private boolean okSelected = false;

    public boolean isOk() {
	return okSelected;
    }

    public NotesDialogPanel(String id, CheckOutCheckInHistory history) {
	super(id);

	if (history.getPhone() == null){
	    history.setPhone(new Phone());
	}

	notesForm = new Form<Object>("notesForm");
	add(notesForm);

	feedbackPanel = new FeedbackPanel("feedback");
	feedbackPanel.setOutputMarkupId(true);
	notesForm.add(feedbackPanel);

	Label titleLabel = new Label("titleLabel", this.constructTitleString(history));
	add(titleLabel);

	TextArea<String> notesTextArea = new TextArea<String>("notesTextArea", new PropertyModel<String>(history,
		"notes"));
	notesForm.add(notesTextArea);

	okLink = new AjaxSubmitLink("okLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		okSelected = true;
		close(target);
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		okSelected = false;
		target.add(feedbackPanel);
	    }

	};
	okLink.setOutputMarkupId(true);
	okLink.setMarkupId("okLink");
	notesForm.add(okLink);

	cancelLink = new AjaxLink<Void>("cancelLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		okSelected = false;
		close(target);
	    }

	};
	cancelLink.setOutputMarkupId(true);
	cancelLink.setMarkupId("cancelLink");
	notesForm.add(cancelLink);
    }

    @Override
    public void onClose(AjaxRequestTarget target) {
    }

    @Override
    public void update(AjaxRequestTarget target) {
	// TODO Auto-generated method stub

    }

    private String constructTitleString(CheckOutCheckInHistory history) {

	String titleParts[] = {history.getFirstName(), history.getLastName(), ",", history.getContactPhone() };
	return StringUtils.join(titleParts, ' ');
    }

}
