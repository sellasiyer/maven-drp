package com.bestbuy.bbym.ise.drp.rev;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.common.OkCancelDialogPanel;
import com.bestbuy.bbym.ise.drp.domain.ManifestLine;

public abstract class ConfirmShrinkModalPanel extends OkCancelDialogPanel {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(ConfirmShrinkModalPanel.class);

    private String confirmShrinkQuestion;
    private ManifestLine manifestLine;

    public ConfirmShrinkModalPanel(final String id, final String noLabel, final String yesLabel) {
	super(id, noLabel, yesLabel);

	final String na = getString("notApplicable.label");

	add(new Label("confirmShrinkQuestion", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (confirmShrinkQuestion == null){
		    return "";
		}
		return confirmShrinkQuestion;
	    }
	}));

	add(new Label("itemTag", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (manifestLine == null || manifestLine.getItemTag() == null){
		    return na;
		}
		return manifestLine.getItemTag();
	    }
	}));

	add(new Label("serialNum", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (manifestLine == null || manifestLine.getImeiesn() == null){
		    return na;
		}
		return manifestLine.getImeiesn();
	    }
	}));

	add(new Label("productDescription", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (manifestLine == null || manifestLine.getProductDescription() == null){
		    return na;
		}
		return manifestLine.getProductDescription();
	    }
	}));

	add(new Label("returnType", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (manifestLine == null || manifestLine.getReturnType() == null){
		    return na;
		}
		return manifestLine.getReturnType();
	    }
	}));
    }

    @Override
    public String getOpenModalJS() {
	// focus will be set to yes button
	return "setModalPanelFocus('#cancelLink');";
    }

    @Override
    public void update(AjaxRequestTarget target) {
    }

    public void setMainfestLine(ManifestLine manifestLine) {
	this.manifestLine = manifestLine;
    }

    public boolean isYesSelected() {
	return !isOk();
    }

    public void setConfirmShrinkQuestion(String confirmShrinkQuestion) {
	this.confirmShrinkQuestion = confirmShrinkQuestion;
    }

    public String getItemTag() {
	if (manifestLine == null){
	    return null;
	}
	return manifestLine.getItemTag();
    }
}
