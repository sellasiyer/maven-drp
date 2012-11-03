/**
 * Best Buy Inc (c) 2012
 */
package com.bestbuy.bbym.ise.drp.triage2;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.BasePanel;
import com.bestbuy.bbym.ise.drp.common.OkCancelDialogPanel;
import com.bestbuy.bbym.ise.drp.domain.TriageEvent;
import com.bestbuy.bbym.ise.drp.service.TriageService;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * @author Yernar Khuangan (a1003810)
 * @version 1.0
 * @since iteration 22
 */
public class TriageHistoryPanel extends BasePanel {

    private static final long serialVersionUID = -6135046689546289432L;

    private static Logger logger = LoggerFactory.getLogger(TriageHistoryPanel.class);

    @SpringBean(name = "triageService")
    private TriageService triageService;

    private List<TriageEvent> listTriageHistory = null;
    ListView<TriageEvent> listEvents = null;
    private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
    private static final String NA = "notApplicable.label";

    private OkCancelDialogPanel deleteConfirmationDlg;
    private TriageEvent deleteCommentTriageEvent = null;
    private boolean isIssueCommentType = true;
    private Component updateCommentComponent = null;
    private Component updateLinkComponent = null;

    public TriageHistoryPanel(String id, String serialNumber) {
	super(id);

	final WebMarkupContainer container = new WebMarkupContainer("historyContainer");
	container.setOutputMarkupId(true);

	try{
	    listTriageHistory = triageService.getTriageHistoryBySerialNumber(serialNumber);
	    if (getDailyRhythmPortalSession().getTriageEvent() != null){
		if (!listTriageHistory.isEmpty()){
		    listTriageHistory.remove(0);
		}
	    }
	}catch(ServiceException e1){
	    String message = "An unexpected exception occured while attempting to load device triage history";
	    logger.error(message, e1);
	    processException(message, e1, getPage().getPageParameters());
	}

	listEvents = new ListView<TriageEvent>("listEvents", listTriageHistory) {

	    private static final long serialVersionUID = 3542192391231283713L;

	    @Override
	    protected void populateItem(ListItem<TriageEvent> item) {
		if (item.getIndex() % 2 == 1){
		    item.add(new AttributeModifier("class", "odd"));
		}

		item.add(new Label("reportedIssue", "" + sdf.format(item.getModelObject().getCreatedOn()) + " - "
			+ item.getModelObject().getTriageRecommendation().getIssueDisplayText()));
		item.add(new Label("techCheckerIssue", item.getModelObject().getTechCheckerIssues()));
		item.add(new Label("action", item.getModelObject().getTriageResolution().getResolutionDesc()));
		item.add(new Label("recommendation", item.getModelObject().getTriageRecommendation()
			.getRecommendation()));

		Label issueComment = new Label("issueComment");
		issueComment.setOutputMarkupPlaceholderTag(true);
		//issueComment.setRenderBodyOnly(true);

		DeleteCommentLinkPanel deleteIssueCommentLink = new DeleteCommentLinkPanel("deleteIssueCommentLink",
			item.getModel(), true, issueComment);
		deleteIssueCommentLink.setOutputMarkupPlaceholderTag(true);

		if (item.getModelObject().getIssueComment() != null
			&& !item.getModelObject().getIssueComment().isEmpty()){
		    issueComment.setDefaultModel(Model.of(item.getModelObject().getIssueComment()));
		}else{
		    issueComment.setDefaultModel(Model.of(getString(NA)));
		    deleteIssueCommentLink.setVisible(false);
		}
		item.add(issueComment);
		item.add(deleteIssueCommentLink);

		Label rslnComment = new Label("rslnComment");
		rslnComment.setOutputMarkupPlaceholderTag(true);
		//rslnComment.setRenderBodyOnly(true);

		DeleteCommentLinkPanel deleteRslnCommentLink = new DeleteCommentLinkPanel("deleteRslnCommentLink", item
			.getModel(), false, rslnComment);
		deleteRslnCommentLink.setOutputMarkupPlaceholderTag(true);

		if (item.getModelObject().getResolutionComment() != null
			&& !item.getModelObject().getResolutionComment().isEmpty()){
		    rslnComment.setDefaultModel(Model.of(item.getModelObject().getResolutionComment()));
		}else{
		    rslnComment.setDefaultModel(Model.of(getString(NA)));
		    deleteRslnCommentLink.setVisible(false);
		}
		item.add(rslnComment);
		item.add(deleteRslnCommentLink);

	    }
	};
	listEvents.setOutputMarkupId(true);

	container.add(listEvents);
	add(container);

	deleteConfirmationDlg = new OkCancelDialogPanel("deleteConfirmationDlg", getString("yesLabel"),
		getString("noLabel")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		if (isOk()){
		    DailyRhythmPortalSession session = getDailyRhythmPortalSession();
		    try{
			if (isIssueCommentType){
			    triageService.deleteIssueComment(deleteCommentTriageEvent, session.getDrpUser());
			}else{
			    triageService.deleteResolutionComment(deleteCommentTriageEvent, session.getDrpUser());
			}
		    }catch(ServiceException e){
			String message = "An unexpected exception occured while attempting to create/update a triage history.";
			logger.error(message, e);
			processException(message, e, getPage().getPageParameters());
		    }
		    updateLinkComponent.setVisible(false);
		    target.add(updateLinkComponent);
		    updateCommentComponent.setDefaultModel(Model.of(getString(NA)));
		    target.add(updateCommentComponent);
		}
	    }
	};
	deleteConfirmationDlg.setOutputMarkupPlaceholderTag(true);
	// deleteConfirmationDlg.setMessage("");
	deleteConfirmationDlg.setQuestion(getString("triageDeviceInfoPanel.deleteCommentConfirmation.message"));
	add(deleteConfirmationDlg);
    }

    public List<TriageEvent> getListTriageHistory() {
	return listTriageHistory;
    }

    public void setListTriageHistory(List<TriageEvent> listTriageHistory) {
	this.listTriageHistory = listTriageHistory;
    }

    public boolean isTriageHistoryEmpty() {
	if (listTriageHistory != null && !listTriageHistory.isEmpty()){
	    return false;
	}

	return true;
    }

    class DeleteCommentLinkPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public DeleteCommentLinkPanel(String id, final IModel<TriageEvent> model, final boolean isIssueComment,
		final Component commentComp) {
	    super(id, model);

	    final DeleteCommentLinkPanel thisPanel = this;
	    add(new AjaxLink<Void>("deleteCommentLink") {

		private static final long serialVersionUID = -7178439027256413904L;

		@Override
		public void onClick(AjaxRequestTarget target) {
		    deleteCommentTriageEvent = model.getObject();
		    isIssueCommentType = isIssueComment;
		    updateCommentComponent = commentComp;
		    updateLinkComponent = thisPanel;
		    deleteConfirmationDlg.open(target);

		}

	    });
	}

    }

}
