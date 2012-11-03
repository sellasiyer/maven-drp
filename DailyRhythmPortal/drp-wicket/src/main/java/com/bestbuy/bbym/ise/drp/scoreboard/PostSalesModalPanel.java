package com.bestbuy.bbym.ise.drp.scoreboard;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardCategory;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardDataItem;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardEmployeeShift;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardTransactionType;
import com.bestbuy.bbym.ise.drp.scoreboard.ScoreboardModalColumnPanel.PostMode;
import com.bestbuy.bbym.ise.exception.ServiceException;

public abstract class PostSalesModalPanel extends ScoreboardBaseModal {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(PostSalesModalPanel.class);

    // State Data
    private List<ScoreboardEmployeeShift> employeeShiftList;

    // Model Data
    private ScoreboardEmployeeShift selectedEmployee;

    // Wicket Elements
    private FeedbackPanel feedbackPanel;
    private DropDownChoice<ScoreboardEmployeeShift> employeeSelector;
    private ChoiceRenderer<ScoreboardEmployeeShift> employeeChoiceRenderer;
    private Label employeeSelectorError;

    public PostSalesModalPanel(String id, List<ScoreboardCategory> categoryList,
	    List<ScoreboardEmployeeShift> employeeList) {
	super(id, categoryList);

	feedbackPanel = new FeedbackPanel("feedbackPanel");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	mainform.add(feedbackPanel);

	submitButton.setDefaultModel(Model.of(getString("scoreboardModal.swas.submitbutton.label")));

	employeeChoiceRenderer = new ChoiceRenderer<ScoreboardEmployeeShift>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public Object getDisplayValue(ScoreboardEmployeeShift object) {
		return object.getEmpFullName() + ": " + object.getEmpBBYId();
	    }
	};

	refreshEmployeeShiftData(employeeList);

	employeeSelectorError = new Label("employeeSelectorError", new ResourceModel(
		"scoreboardModal.error.forgotEmployee"));
	employeeSelectorError.setOutputMarkupPlaceholderTag(true);
	employeeSelectorError.setVisible(false);
	mainform.add(employeeSelectorError);
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
	int startIndex = getNumberOfCategoriesOnLeftside();
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
	panel.setPostMode(PostMode.SALE);
	return panel;
    }

    @Override
    protected void submitHandler(AjaxRequestTarget target, Form<?> form) {

	if (categoryList != null && !categoryList.isEmpty()){
	    for(ScoreboardCategory sc: categoryList){
		if (sc != null){
		    for(ScoreboardDataItem sdi: sc.getDataItems()){
			logger.info("SUBMIT=" + sdi);
		    }
		}
	    }
	}

	// printCategoryListData();

	target.add(employeeSelectorError);
	employeeSelectorError.setVisible(false);
	if (selectedEmployee == null || StringUtils.isBlank(selectedEmployee.getEmpFullName())){
	    employeeSelectorError.setVisible(true);
	    return;
	}

	try{
	    scoreboardService.saveScoreboardItems(selectedEmployee.getStoreId(), selectedEmployee,
		    ScoreboardTransactionType.EMP_SALES_POST, categoryList, getDailyRhythmPortalSession().getDrpUser(),
		    getClientTimeZoneOffsetMinutes());
	    close(target);
	}catch(ServiceException e){
	    logger.info(e.getMessage(), e);
	    target.add(feedbackPanel);
	    feedbackPanel.error(e.getMessage());
	}

    }

    @Override
    public void update(AjaxRequestTarget target) {
	target.add(tableListingMarkupContainer);
	target.add(mainform);
	target.add(feedbackPanel);
	target.add(employeeSelector);
    }

    @Override
    public void resetAfterClose() {
	// mainform.clearInput();
	resetCategoryList();
	setSelectedEmployee(null);
	employeeSelectorError.setVisible(false);
    }

    @Override
    public void resetCategoryList() {
	if (categoryList != null && !categoryList.isEmpty()){
	    for(ScoreboardCategory sc: categoryList){
		if (sc != null){
		    for(ScoreboardDataItem sdi: sc.getDataItems()){
			sdi.setSalesQuantity(null);
			sdi.setEditUnitPrice(null);
		    }
		}
	    }
	}
    }

    public ScoreboardEmployeeShift getSelectedEmployee() {
	return selectedEmployee;
    }

    public void setSelectedEmployee(ScoreboardEmployeeShift selectedEmployee) {
	this.selectedEmployee = selectedEmployee;
    }

    public void refreshEmployeeShiftData(List<ScoreboardEmployeeShift> employeeList) {
	if (employeeList == null){
	    employeeShiftList = new ArrayList<ScoreboardEmployeeShift>();
	}else{
	    employeeShiftList = employeeList;
	}
	employeeSelector = new DropDownChoice<ScoreboardEmployeeShift>("employeeSelector",
		new PropertyModel<ScoreboardEmployeeShift>(this, "selectedEmployee"), employeeShiftList,
		employeeChoiceRenderer);
	employeeSelector.setOutputMarkupPlaceholderTag(true);
	mainform.addOrReplace(employeeSelector);
    }

    public int getNumEmployees() {
	if (employeeShiftList == null){
	    return 0;
	}
	return employeeShiftList.size();
    }

}
