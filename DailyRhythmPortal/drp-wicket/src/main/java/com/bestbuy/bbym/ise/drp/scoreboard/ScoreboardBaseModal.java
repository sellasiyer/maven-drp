package com.bestbuy.bbym.ise.drp.scoreboard;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.common.ModalPanel;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardCategory;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardDataItem;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardOperationType;
import com.bestbuy.bbym.ise.drp.service.ScoreboardService;

public abstract class ScoreboardBaseModal extends ModalPanel {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(ScoreboardBaseModal.class);

    @SpringBean(name = "scoreboardService")
    protected ScoreboardService scoreboardService;

    // State Data
    protected List<ScoreboardCategory> categoryList;
    protected boolean okSelected = false;
    protected boolean showFooter = true;
    protected boolean showColumns = true;
    protected ScoreboardOperationType operationType = ScoreboardOperationType.MOBILE;
    private int numberOfCategoriesOnLeftSide;
    private int clientTimeZoneOffsetMinutes;

    // Wicket Elements
    protected Form<Void> mainform;
    protected AjaxLink<Void> exitButton;
    protected AjaxSubmitLink submitButton;
    protected WebMarkupContainer tableListingMarkupContainer;

    public ScoreboardBaseModal(String id, List<ScoreboardCategory> categoryList) {
	super(id);
	this.setOutputMarkupPlaceholderTag(true);
	this.categoryList = categoryList;

	exitButton = new AjaxLink<Void>("exitButton") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		okSelected = false;
		close(target);
	    }
	};
	add(exitButton);

	mainform = new Form<Void>("mainform");
	mainform.setOutputMarkupPlaceholderTag(true);
	add(mainform);

	tableListingMarkupContainer = new WebMarkupContainer("tableListingMarkupContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return showColumns;
	    }
	};
	tableListingMarkupContainer.setOutputMarkupPlaceholderTag(true);
	mainform.add(tableListingMarkupContainer);

	final WebMarkupContainer footer = new WebMarkupContainer("footer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return showFooter;
	    }
	};
	footer.setOutputMarkupPlaceholderTag(true);
	mainform.add(footer);

	submitButton = new AjaxSubmitLink("submitButton") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		okSelected = true;
		submitHandler(target, form);
	    }

	};
	submitButton.add(new Label("submitButtonLabel", new Model<String>(getString("submitLabel"))));
	footer.add(submitButton);

	if (categoryList == null){
	    tableListingMarkupContainer.add(new EmptyPanel("columnLeft"));
	    tableListingMarkupContainer.add(new EmptyPanel("columnRight"));
	}else{
	    ScoreboardModalColumnPanel leftPanel = getLeftPanel("columnLeft", categoryList);
	    tableListingMarkupContainer.add(leftPanel);
	    ScoreboardModalColumnPanel rightPanel = getRightPanel("columnRight", categoryList);
	    tableListingMarkupContainer.add(rightPanel);
	}
    }

    protected abstract void submitHandler(AjaxRequestTarget target, Form<?> form);

    // public abstract void validateHandler();

    public abstract ScoreboardModalColumnPanel getLeftPanel(String id, List<ScoreboardCategory> categoryList);

    public abstract ScoreboardModalColumnPanel getRightPanel(String id, List<ScoreboardCategory> categoryList);

    @Override
    public String getModalSelector() {
	return "#" + getId() + " .new-modal";
    }

    public void refreshCategoryData(List<ScoreboardCategory> categoryList) {
	this.categoryList = categoryList;
	resetCategoryList();
	ScoreboardModalColumnPanel leftPanel = getLeftPanel("columnLeft", categoryList);
	tableListingMarkupContainer.replace(leftPanel);
	ScoreboardModalColumnPanel rightPanel = getRightPanel("columnRight", categoryList);
	tableListingMarkupContainer.replace(rightPanel);
    }

    public void resetCategoryList() {
    }

    public ScoreboardOperationType getOperationType() {
	return operationType;
    }

    public void setOperationType(ScoreboardOperationType operationType) {
	this.operationType = operationType;
    }

    public boolean isOk() {
	return okSelected;
    }

    public void setOkSelected(boolean okSelected) {
	this.okSelected = okSelected;
    }

    public void setNumberOfCategoriesOnLeftSide(int number) {
	this.numberOfCategoriesOnLeftSide = number;
    }

    public int getNumberOfCategoriesOnLeftside() {
	return this.numberOfCategoriesOnLeftSide;
    }

    public int getClientTimeZoneOffsetMinutes() {
	return clientTimeZoneOffsetMinutes;
    }

    public void setClientTimeZoneOffsetMinutes(int clientTimeZoneOffsetMinutes) {
	this.clientTimeZoneOffsetMinutes = clientTimeZoneOffsetMinutes;
    }

    protected void printCategoryListData() {
	//	Logger logger = LoggerFactory.getLogger(getClass());
	//	for(ScoreboardCategory cat: categoryList){
	//	    logger.debug("category: " + cat.getName());
	//	    for(ScoreboardDataItem di: cat.getDataItems()){
	//		logger.debug("dataItem: " + di.toString());
	//	    }
	//	}
    }
}
