package com.bestbuy.bbym.ise.drp.triage2;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.StringValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.common.BasePanel;
import com.bestbuy.bbym.ise.drp.common.modal.MessageDialogPanel;
import com.bestbuy.bbym.ise.drp.domain.TriageIssue;

public class IssuesPanel extends BasePanel {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(IssuesPanel.class);

    private IModel<TriageIssue> triageIssueModel;
    private List<TriageIssue> triageIssues;
    private TriageIssue selectedTriageIssue = new TriageIssue();

    public IssuesPanel(String id, List<TriageIssue> triageIssues, final MessageDialogPanel issuePopupDialog) {
	super(id);
	this.triageIssues = triageIssues;

	triageIssueModel = new Model<TriageIssue>();
	RadioGroup<TriageIssue> issuesRadioGroup = new RadioGroup<TriageIssue>("issuesRadioGroup", triageIssueModel);
	add(issuesRadioGroup);
	issuesRadioGroup.add(new ListView<TriageIssue>("issuesRadioChoice", new PropertyModel<List<TriageIssue>>(this,
		"triageIssues")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void populateItem(ListItem<TriageIssue> item) {
		final TriageIssue ti = item.getModelObject();
		item.add(new Radio<TriageIssue>("radio", item.getModel()));
		item.add(new Label("label", item.getModelObject().getIssueDesc()));
		AjaxLink<Object> popupLink = new AjaxLink<Object>("popupLink") {

		    private static final long serialVersionUID = 1L;

		    @Override
		    public void onClick(AjaxRequestTarget target) {
			logger.debug("popupLink onClick");
			logger.debug("triageIssue=" + ti);
			issuePopupDialog.setMessage(ti.getTooltip(), false);
			issuePopupDialog.open(target);
		    }
		};
		item.add(popupLink);
	    }
	});

	final TextArea<String> issueComment = new TextArea<String>("issueComment", new PropertyModel<String>(
		selectedTriageIssue, "issueComment"));
	issueComment.setOutputMarkupPlaceholderTag(true);
	issueComment.add(StringValidator.maximumLength(500));
	add(issueComment);
    }

    public TriageIssue getSelectedTriageIssue() {
	if (triageIssueModel == null || triageIssueModel.getObject() == null){
	    return null;
	}
	triageIssueModel.getObject().setIssueComment(selectedTriageIssue.getIssueComment());
	return triageIssueModel.getObject();
    }

    public List<TriageIssue> getTriageIssues() {
	return triageIssues;
    }

}
