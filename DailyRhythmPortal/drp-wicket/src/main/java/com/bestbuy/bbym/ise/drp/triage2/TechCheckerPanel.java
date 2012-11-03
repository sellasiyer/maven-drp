package com.bestbuy.bbym.ise.drp.triage2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.WordUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.Device;
import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.NewBaseNavPage;
import com.bestbuy.bbym.ise.drp.common.ServiceBasePanel;
import com.bestbuy.bbym.ise.drp.domain.YGIBDevice;
import com.bestbuy.bbym.ise.drp.domain.YGIBResponse;
import com.bestbuy.bbym.ise.drp.domain.YGIBTestResult;
import com.bestbuy.bbym.ise.drp.service.YGIBService;
import com.bestbuy.bbym.ise.drp.util.DateFormatter;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

public class TechCheckerPanel extends ServiceBasePanel {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(TechCheckerPanel.class);

    @SpringBean(name = "ygibService")
    private YGIBService ygibService;

    private List<YGIBTestResult> testResultList = new ArrayList<YGIBTestResult>();
    private Device testedDevice = new Device();
    private Date testedOn;

    private final DateFormatter<Date> dateFmt = new DateFormatter<Date>("MM/dd/yy");
    private final TriageTests triageTests = TriageTests.generate(this);

    private AbstractDefaultAjaxBehavior wicketBehaviorTCP;
    private WebMarkupContainer noTestResults, haveTestResults, deviceInfo;
    private Label techCheckerTitle;

    public TechCheckerPanel(String id) {
	super(id);

	final String passed = getString("triageTechChecker.pass.label");
	final String failed = getString("triageTechChecker.fail.label");
	final String na = getString("notApplicable.label");

	final String haveResultsTitle = getString("triageTechChecker.title.label");
	final String noResultsTitle = getString("triageTechChecker.noTestResults.title.label");

	techCheckerTitle = new Label("techCheckerTitle", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (testResultList == null || testResultList.isEmpty()){
		    return noResultsTitle;
		}
		return haveResultsTitle;
	    }

	});
	techCheckerTitle.setOutputMarkupPlaceholderTag(true);
	add(techCheckerTitle);

	final AjaxLink<Object> refreshLink = new AjaxLink<Object>("refreshLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.debug("refreshLink onClick");
		NewBaseNavPage page = (NewBaseNavPage) getPage();
		page.openLoadingModal(getString("triageTechChecker.loading.label"), target);
		target.appendJavaScript("doWicketBehavior('wicketBehaviorTCP(\"refresh\")');");
	    }
	};
	refreshLink.setMarkupId("refreshLink");
	refreshLink.setOutputMarkupId(true);
	refreshLink.setOutputMarkupPlaceholderTag(true);
	add(refreshLink);

	deviceInfo = new WebMarkupContainer("deviceInfo") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return testResultList != null && !testResultList.isEmpty();
	    }
	};
	deviceInfo.setOutputMarkupPlaceholderTag(true);
	add(deviceInfo);

	final Label deviceName = new Label("deviceName", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (testedDevice.getDescription() == null){
		    return getString("unknown.label");
		}
		return testedDevice.getDescription();
	    }

	});
	deviceName.setOutputMarkupPlaceholderTag(true);
	deviceInfo.add(deviceName);

	final Label testedOnDate = new Label("testedOnDate", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (testedOn == null){
		    return na;
		}
		return dateFmt.format(testedOn);
	    }

	});
	testedOnDate.setOutputMarkupPlaceholderTag(true);
	deviceInfo.add(testedOnDate);

	noTestResults = new WebMarkupContainer("noTestResults") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return testResultList == null || testResultList.isEmpty();
	    }
	};
	noTestResults.setOutputMarkupPlaceholderTag(true);
	add(noTestResults);

	haveTestResults = new WebMarkupContainer("haveTestResults") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return testResultList != null && !testResultList.isEmpty();
	    }
	};
	haveTestResults.setOutputMarkupPlaceholderTag(true);
	add(haveTestResults);

	final ListView<YGIBTestResult> testResultListView = new ListView<YGIBTestResult>("testResultListView",
		new PropertyModel<List<YGIBTestResult>>(this, "testResultList")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void populateItem(final ListItem<YGIBTestResult> listItem) {
		final YGIBTestResult testResult = listItem.getModelObject();
		boolean testPassed = false;
		if (testResult.getStatus() == 0){
		    testPassed = true;
		}

		String title = triageTests.getTitle(testResult.getTestId()) + " ";
		String iconClass = triageTests.getIconClass(testResult.getTestId());

		//generic test item
		if (iconClass == null){
		    title = WordUtils.capitalizeFully(testResult.getTestId());
		    title = title + " ";

		    iconClass = triageTests.getIconClass("GENERIC");
		}

		if (testPassed){
		    title += passed;
		}else{
		    title += failed;
		}

		if (!testPassed){
		    iconClass += " fail";
		}

		listItem.add(AttributeModifier.replace("class", iconClass));
		listItem.add(AttributeModifier.replace("title", title));
	    }
	};
	testResultListView.setOutputMarkupPlaceholderTag(true);
	haveTestResults.add(testResultListView);

	// means by which JS can callback into Java
	wicketBehaviorTCP = new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
		NewBaseNavPage page = (NewBaseNavPage) getPage();
		String id = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("id").toString();
		logger.debug("wicketBehaviorTCP id=" + id);
		if ("refresh".equals(id)){
		    String error = doInitialServiceCalls();
		    page.closeLoadingModal(target);
		    updatePanel(target);
		    if (error != null){
			error(error);
			target.add(getPage().get("feedback"));
		    }
		}
	    }
	};
	add(wicketBehaviorTCP);

	add(new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
	    }

	    @Override
	    public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		StringBuilder onDomReadyJS = new StringBuilder();
		onDomReadyJS.append("wicketBehaviorTCP = function(id) { ");
		onDomReadyJS.append("wicketAjaxGet('");
		onDomReadyJS.append(wicketBehaviorTCP.getCallbackUrl());
		onDomReadyJS.append("&id='+id); };");
		logger.debug("onDomReadyJS=" + onDomReadyJS.toString());
		response.renderOnDomReadyJavaScript(onDomReadyJS.toString());
	    }
	});
    }

    @Override
    public String doInitialServiceCalls() {
	testResultList.clear();
	String error = getString("triageTechChecker.getTestResultsFailed.message.label");
	try{
	    YGIBDevice ygibDevice = new YGIBDevice();
	    DailyRhythmPortalSession session = getDailyRhythmPortalSession();
	    if (session.getSelectedLine() != null){
		ygibDevice.setPhoneNo(session.getSelectedLine().getMobileNumber());
		if (session.getSelectedLine().getDevice() != null){
		    ygibDevice.setUid(session.getSelectedLine().getDevice().getSerialNumber());
		}
	    }
	    if (ygibDevice.getUid() == null && ygibDevice.getPhoneNo() == null){
		return error + " Device serial # and phone # not provided";
	    }else if (ygibDevice.getPhoneNo() == null){
		return error + " Device phone # not provided";
	    }else if (ygibDevice.getUid() == null){
		return error + " Device serial # not provided";
	    }
	    boolean noResults = false;
	    YGIBResponse response = ygibService.processRequest(ygibDevice);
	    if (response == null || response.getTestResults() == null || response.getTestResults().isEmpty()){
		noResults = true;
	    }else if (response.getResponseCode() != YGIBService.SUCCESS_RESPONSE_CODE){
		noResults = true;
	    }
	    if (noResults){
		if (session.getSelectedTriageIssue() != null){
		    session.getSelectedTriageIssue().setTechCheckerIssue(getString("notApplicable.label"));
		}
		testedDevice.setDescription(getString("unknown.label"));
		return getString("triageTechChecker.noTestResults.message.label");
	    }

	    if (Strings.isEmpty(response.getManufacturer()) && Strings.isEmpty(response.getModel())){
		testedDevice.setDescription(getString("unknown.label"));
	    }else{
		String desc = "";
		if (!Strings.isEmpty(response.getManufacturer())){
		    desc = response.getManufacturer() + " ";
		}
		if (!Strings.isEmpty(response.getModel())){
		    desc = desc + response.getModel();
		}
		testedDevice.setDescription(desc);
	    }

	    if (session.getSelectedTriageIssue() != null){
		session.getSelectedTriageIssue().setTechCheckerIssue(buildTechCheckerIssueList(response));
	    }

	    // Sort to put failed tests first
	    Collections.sort(response.getTestResults(), new Comparator<YGIBTestResult>() {

		@Override
		public int compare(YGIBTestResult o1, YGIBTestResult o2) {
		    if (o1.getStatus() == o2.getStatus()){
			return 0;
		    }
		    if (o1.getStatus() == 2){
			return -1;
		    }
		    if (o2.getStatus() == 2){
			return 1;
		    }
		    return 0;
		}
	    });
	    for(YGIBTestResult result: response.getTestResults()){
		if (result.getStatus() == 0 || result.getStatus() == 2){
		    testResultList.add(result);
		}
	    }
	    testedOn = null;
	    for(YGIBTestResult result: testResultList){
		if (result.getTimestamp() != null){
		    if (testedOn == null || result.getTimestamp().getTime() > testedOn.getTime()){
			testedOn = result.getTimestamp();
		    }
		}
	    }
	    return null;
	}catch(ServiceException se){
	    logger.error("ServiceException calling YGIBService.processRequest", se);
	    return error + " " + se.getMessage();
	}catch(IseBusinessException be){
	    logger.error("IseBusinessException calling YGIBService.processRequest", be);
	    return error + " " + be.getMessage();
	}
    }

    @Override
    public void updatePanel(AjaxRequestTarget target) {
	target.add(this, noTestResults, haveTestResults, techCheckerTitle, deviceInfo);
    }

    private String buildTechCheckerIssueList(YGIBResponse response) {
	StringBuilder sb = new StringBuilder();
	if (response.getTestResults() != null){

	    for(YGIBTestResult tr: response.getTestResults()){
		if (tr.getStatus() == 2){
		    if (sb.length() != 0){
			sb.append(", ");
		    }
		    sb.append(WordUtils.capitalizeFully(tr.getTestId()));
		}
	    }
	}

	String tmpTechCheckerIssue = sb.toString();

	if (Strings.isEmpty(tmpTechCheckerIssue)){
	    tmpTechCheckerIssue = getString("notApplicable.label");
	}
	return tmpTechCheckerIssue;
    }

}
