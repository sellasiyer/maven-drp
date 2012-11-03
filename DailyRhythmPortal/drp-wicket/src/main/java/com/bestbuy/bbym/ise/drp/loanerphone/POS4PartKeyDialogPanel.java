package com.bestbuy.bbym.ise.drp.loanerphone;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.common.ModalPanel;
import com.bestbuy.bbym.ise.drp.domain.CheckOutCheckInHistory;
import com.bestbuy.bbym.ise.drp.domain.Phone;

public class POS4PartKeyDialogPanel extends ModalPanel {

    private static final long serialVersionUID = 1L;
    // data
    private String fourPartKey;
    private CheckOutCheckInHistory history = new CheckOutCheckInHistory();
    // formstuff
    private Form<Object> form = null;
    private FeedbackPanel feedbackPanel;

    private AjaxSubmitLink okLink;
    private AjaxLink<Void> cancelLink;

    private Logger logger = LoggerFactory.getLogger(POS4PartKeyDialogPanel.class);

    private boolean okSelected = false;

    public boolean isOk() {
	return okSelected;
    }

    public POS4PartKeyDialogPanel(String id, CheckOutCheckInHistory hist) {
	super(id);

	this.history = hist;
	form = new Form<Object>("form1");
	add(form);

	feedbackPanel = new FeedbackPanel("feedback");
	feedbackPanel.setOutputMarkupId(true);
	form.add(feedbackPanel);

	Phone phone = history.getPhone();
	if (phone == null){
	    phone = new Phone();
	    history.setPhone(phone);
	}

	Label titleLabel = new Label("titleLabel", this.constructTitleString(history));
	add(titleLabel);

	TextField<String> storeField = new RequiredTextField<String>("storeField", new PropertyModel<String>(history,
		"posStoreId"));
	storeField.setOutputMarkupId(true);
	storeField.setMarkupId("storeField");
	form.add(storeField);

	TextField<String> registerField = new RequiredTextField<String>("registerField", new PropertyModel<String>(
		history, "registerId"));
	registerField.setOutputMarkupId(true);
	registerField.setMarkupId("registerField");
	form.add(registerField);

	TextField<String> transNumField = new RequiredTextField<String>("transNumField", new PropertyModel<String>(
		history, "transactionNumber"));
	transNumField.setOutputMarkupId(true);
	transNumField.setMarkupId("transNumField");
	form.add(transNumField);

	@SuppressWarnings( {"unchecked", "rawtypes" })
	DateTextField transDateField = new DateTextField("transDateField",
		new PropertyModel(history, "transactionDate"));
	transDateField.setRequired(true);
	transDateField.setOutputMarkupId(true);
	transDateField.setMarkupId("transDateField");

	form.add(transDateField);

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
	okLink.setDefaultFormProcessing(true);
	form.add(okLink);

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
	form.add(cancelLink);

    }

    @Override
    public void renderHead(IHeaderResponse response) {
	response.renderOnLoadJavaScript("posModalInit();");
	response.renderOnLoadJavaScript("gunInit();");
    }

    @Override
    public void onClose(AjaxRequestTarget target) {
	if (isOk()){
	    fourPartKey = history.getPhone().getStoreId() + "-" + history.getRegisterId() + "-"
		    + history.getTransactionNumber() + "-" + history.getTransactionDate();
	    logger.info(fourPartKey);
	}
    }

    public CheckOutCheckInHistory getHistory() {
	return history;
    }

    public void setHistory(CheckOutCheckInHistory history) {
	this.history = history;
	if (history.getPhone() == null){
	    history.setPhone(new Phone());
	}
    }

    @Override
    public void update(AjaxRequestTarget target) {
	// TODO Auto-generated method stub
    }

    private String constructTitleString(CheckOutCheckInHistory hist) {
	String titleParts[] = {history.getFirstName(), history.getLastName(), ",", history.getContactPhone() };
	StringBuilder sb = new StringBuilder();
	for(String str: titleParts){
	    if (str != null){
		sb.append(str);
		sb.append(" ");
	    }

	}
	return sb.toString();
    }
}
