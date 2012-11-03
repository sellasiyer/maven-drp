package com.bestbuy.bbym.ise.drp.scoreboard;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardCategory;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardDataItem;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardTransactionType;
import com.bestbuy.bbym.ise.drp.scoreboard.ScoreboardModalColumnPanel.PostMode;
import com.bestbuy.bbym.ise.exception.ServiceException;

public abstract class PostReturnsModalPanel extends ScoreboardBaseModal {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(PostReturnsModalPanel.class);

    // Wicket Elements
    private FeedbackPanel feedbackPanel;

    public PostReturnsModalPanel(String id, List<ScoreboardCategory> categoryList) {
	super(id, categoryList);

	feedbackPanel = new FeedbackPanel("feedbackPanel");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	mainform.add(feedbackPanel);

	submitButton.add(new AttributeModifier("class", new Model<String>("button return")));
	submitButton.setDefaultModel(Model.of(getString("scoreboardModal.swasreturn.submitbutton.label")));
    }

    @Override
    protected void submitHandler(AjaxRequestTarget target, Form<?> form) {
	String storeId = getDailyRhythmPortalSession().getDrpUser().getStoreId();
	//printCategoryListData();
	try{
	    scoreboardService.saveScoreboardItems(storeId, null, ScoreboardTransactionType.STORE_RETURNS_POST,
		    categoryList, getDailyRhythmPortalSession().getDrpUser(), getClientTimeZoneOffsetMinutes());
	    close(target);
	}catch(ServiceException e){
	    logger.info(e.getMessage(), e);
	    target.add(feedbackPanel);
	    feedbackPanel.error(e.getMessage());
	}

    }

    @Override
    public void resetCategoryList() {
	if (categoryList != null && !categoryList.isEmpty()){
	    for(ScoreboardCategory sc: categoryList){
		if (sc != null){
		    for(ScoreboardDataItem sdi: sc.getDataItems()){
			sdi.setReturnQuantity(null);
			sdi.setEditUnitPrice(null);
		    }
		}
	    }
	}
    }

    @Override
    public ScoreboardModalColumnPanel getLeftPanel(String id, List<ScoreboardCategory> categoryList) {
	int startIndex = 0;
	int endIndex = getNumberOfCategoriesOnLeftside();
	ScoreboardModalColumnPanel panel = makeColumnPanel(id, categoryList, startIndex, endIndex);
	return panel;
    }

    @Override
    public ScoreboardModalColumnPanel getRightPanel(String id, List<ScoreboardCategory> categoryList) {
	int startIndex = getNumberOfCategoriesOnLeftside(); // right list.
	int endIndex = categoryList.size();
	ScoreboardModalColumnPanel panel = makeColumnPanel(id, categoryList, startIndex, endIndex);
	return panel;
    }

    protected ScoreboardModalColumnPanel makeColumnPanel(String id, List<ScoreboardCategory> categoryList,
	    int startIndex, int endIndex) {

	List<ScoreboardCategory> myList = new ArrayList<ScoreboardCategory>();
	if (categoryList.size() > 0){
	    myList.addAll(categoryList.subList(startIndex, endIndex));
	}
	ScoreboardModalColumnPanel panel = null;
	switch (operationType) {
	    case COMPUTING:
		panel = new PostComputingColumnPanel(id, myList);
		break;
	    case MOBILE:
		panel = new PostMobileColumnPanel(id, myList);
		break;

	}
	panel.setPostMode(PostMode.RETURN);
	return panel;
    }

    @Override
    public void update(AjaxRequestTarget target) {
	target.add(tableListingMarkupContainer);
	target.add(mainform);
	target.add(feedbackPanel);
    }

    @Override
    public void resetAfterClose() {
	resetCategoryList();
    }

}
