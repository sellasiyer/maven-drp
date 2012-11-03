package com.bestbuy.bbym.ise.drp.entitlement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.BasePanel;
import com.bestbuy.bbym.ise.drp.common.NewBaseNavPage;
import com.bestbuy.bbym.ise.drp.dashboard.CustomerDashboardHeaderPanel;
import com.bestbuy.bbym.ise.drp.dashboard.CustomerDashboardPage;
import com.bestbuy.bbym.ise.drp.domain.DataTransferSummary;
import com.bestbuy.bbym.ise.drp.domain.Entitlement;
import com.bestbuy.bbym.ise.drp.domain.EntitlementLookup;
import com.bestbuy.bbym.ise.drp.domain.MobileEntitlement;
import com.bestbuy.bbym.ise.drp.domain.RetExchDevcEntitlsModel;
import com.bestbuy.bbym.ise.drp.entitlement.EntitlementLookupPage.GSPViewPanel;
import com.bestbuy.bbym.ise.drp.entitlement.EntitlementLookupPage.MobileDeviceViewPanel;
import com.bestbuy.bbym.ise.drp.entitlement.EntitlementLookupPage.RZViewPanel;
import com.bestbuy.bbym.ise.drp.service.DrpPropertyService;
import com.bestbuy.bbym.ise.drp.service.ReturnExchangeService;
import com.bestbuy.bbym.ise.drp.service.TriageService;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

public class EntitlementSelectionPage extends NewBaseNavPage {

    private static Logger logger = LoggerFactory.getLogger(EntitlementSelectionPage.class);
    private static final long serialVersionUID = 1L;

    private final NextStepsPanel nextStepsPanel;
    private final EntitlementSelectionPanel entitlementSelectionPanel;
    private Entitlement selectedEntitlement = null;
    private AjaxLink<Object> cancelButton, backButton, continueLink;
    private Label disclaimerLabel;
    private boolean isNextStepsPaneldisplayed = false;
    private String dataSharingKey = null;
    private String NO_ENTITLEMENTS_AVAILABLE = "No Entitlements Available";
    //private String returnExchangeWorkflowType = null;
    public String printPageViewType = "SELC_PAGE";

    @SpringBean(name = "returnExchangeService")
    private ReturnExchangeService returnExchangeService;

    @SpringBean(name = "drpPropertyService")
    private DrpPropertyService drpPropertyService;

    @SpringBean(name = "triageService")
    private TriageService triageService;

    private boolean bbyCustomerInfo;

    public EntitlementSelectionPage(final EntitlementLookup entitlementLookup,
	    final MobileEntitlement mobileEntitlement, Customer selectedCustomer, final Page returnPage) {
	super(null);
	final FeedbackPanel feedbackPanel = new FeedbackPanel("entitlementSelectionFeedback");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	add(feedbackPanel);

	DailyRhythmPortalSession session = getDailyRhythmPortalSession();

	if (selectedCustomer == null && session.getBestBuyCustomer() != null){
	    bbyCustomerInfo = true;
	}else if (selectedCustomer != null && selectedCustomer.getFirstName() != null
		&& selectedCustomer.getLastName() != null){
	    bbyCustomerInfo = true;
	}

	/** Customer Dashboard Header Panel to display the Header on the Page */
	CustomerDashboardHeaderPanel customerDashboardHeaderPanel = new CustomerDashboardHeaderPanel(
		"customerDashboardHeaderPanel", selectedCustomer) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return bbyCustomerInfo;
	    }

	};
	customerDashboardHeaderPanel.setOutputMarkupPlaceholderTag(true);
	add(customerDashboardHeaderPanel);

	String disclaimer = null;

	try{
	    disclaimer = drpPropertyService.getProperty("Disclaimer");
	}catch(ServiceException e){
	    logger.error("DrpPropertyService : Error while fetching drpProperty : Disclaimer ", e);
	}
	/*try{
	    returnExchangeWorkflowType = drpPropertyService.getProperty("RET_EXCH_WORKFLOW_TYPE");
	}catch(ServiceException e){
	    logger.error("DrpPropertyService : Error while fetching drpProperty : RET_EXCH_WORKFLOW_TYPE", e);
	}*/
	disclaimerLabel = new Label("disclaimerLabel", disclaimer);
	disclaimerLabel.setMarkupId("disclaimerLabel");
	disclaimerLabel.setOutputMarkupId(true);
	disclaimerLabel.setOutputMarkupPlaceholderTag(true);
	disclaimerLabel.setVisible(false);

	//add(disclaimerLabel);

	final WebMarkupContainer purchaseViewContainer = new WebMarkupContainer("purchaseViewContainer");
	purchaseViewContainer.setOutputMarkupPlaceholderTag(true);

	/** Reusing View Modes of Entitlement Lookup Purcahse Transaction.... **/

	final EntitlementLookupPage.PurchaseViewPanel purchaseViewPanel = new EntitlementLookupPage().new PurchaseViewPanel(
		"purchaseViewPanel", entitlementLookup);
	purchaseViewPanel.setOutputMarkupPlaceholderTag(true);
	purchaseViewPanel.setVisible(true);

	purchaseViewContainer.add(purchaseViewPanel);

	/** Reusing View Modes of Mobile Device Panel **/
	final MobileDeviceViewPanel mobileDeviceViewPanel = new EntitlementLookupPage().new MobileDeviceViewPanel(
		"mobileDeviceViewPanel", entitlementLookup);
	mobileDeviceViewPanel.setOutputMarkupPlaceholderTag(true);
	mobileDeviceViewPanel.setVisible(true);

	purchaseViewContainer.add(mobileDeviceViewPanel);

	WebMarkupContainer mobileSearchViewContainer = (WebMarkupContainer) mobileDeviceViewPanel.get(0);
	mobileSearchViewContainer.get(0).setVisible(false);
	mobileSearchViewContainer.get(1).setVisible(true);

	/** Reusing View Modes of GSP View Panel **/

	final GSPViewPanel gspViewPanel = new EntitlementLookupPage().new GSPViewPanel("gspViewPanel",
		entitlementLookup);
	gspViewPanel.setOutputMarkupPlaceholderTag(true);
	gspViewPanel.setVisible(true);

	logger.debug("gsp page........." + gspViewPanel.get(0));

	WebMarkupContainer gspSearchViewContainer = (WebMarkupContainer) gspViewPanel.get(0);
	WebMarkupContainer gspViewContainer = (WebMarkupContainer) gspSearchViewContainer.get(1);
	gspSearchViewContainer.get(0).setVisible(false);
	gspSearchViewContainer.get(1).setVisible(true);

	logger.debug("gsp view........." + gspSearchViewContainer.get(1));
	logger.debug("gspViewContainer.get(0)..........." + gspViewContainer.get(0));
	logger.debug("gspViewContainer.get(0)..........." + gspViewContainer.get(1));

	if (StringUtils.isEmpty(entitlementLookup.getGspNumber()) || entitlementLookup.getGspNumber().length() != 10){
	    gspViewContainer.get(0).setVisible(false);
	    gspViewContainer.get(1).setVisible(true);
	}else{
	    gspViewContainer.get(0).setVisible(true);
	    gspViewContainer.get(1).setVisible(false);
	}

	purchaseViewContainer.add(gspViewPanel);

	/** Reusing View Modes of RewardZone View Panel **/

	final RZViewPanel rzViewPanel = new EntitlementLookupPage().new RZViewPanel("rzViewPanel", entitlementLookup);
	rzViewPanel.setOutputMarkupPlaceholderTag(true);
	rzViewPanel.setVisible(true);
	purchaseViewContainer.add(rzViewPanel);

	WebMarkupContainer rzSearchViewContainer = (WebMarkupContainer) rzViewPanel.get(0);
	WebMarkupContainer rzViewContainer = (WebMarkupContainer) rzSearchViewContainer.get(1);
	rzSearchViewContainer.get(0).setVisible(false);
	rzSearchViewContainer.get(1).setVisible(true);

	if (StringUtils.isNotEmpty(entitlementLookup.getRzNumber()) && entitlementLookup.getRzNumber().length() == 9){
	    rzViewContainer.get(0).setVisible(true);
	    rzViewContainer.get(1).setVisible(false);
	}else{
	    rzViewContainer.get(0).setVisible(false);
	    rzViewContainer.get(1).setVisible(true);
	}
	// Add container to Page..
	add(purchaseViewContainer);

	/** Entitlement Selection Code here...................... **/

	final WebMarkupContainer entitlementTypesSelectionContainer = new WebMarkupContainer(
		"entitlementTypesSelectionContainer");
	entitlementTypesSelectionContainer.setOutputMarkupPlaceholderTag(true);
	add(entitlementTypesSelectionContainer);

	entitlementSelectionPanel = new EntitlementSelectionPanel("entitlementSelectionPanel", entitlementLookup,
		mobileEntitlement);
	entitlementSelectionPanel.setOutputMarkupPlaceholderTag(true);
	entitlementSelectionPanel.setOutputMarkupId(true);
	entitlementTypesSelectionContainer.add(entitlementSelectionPanel);
	entitlementTypesSelectionContainer.add(disclaimerLabel);
	/** Next Steps Panel................**/

	if (mobileEntitlement.getEntitlementList() != null)
	    selectedEntitlement = new Entitlement();
	try{
	    if (mobileEntitlement.getEntitlementList().size() == 0)
		throw new IseBusinessException("No Entitlements returned from ERA");
	    else{
		selectedEntitlement.setDisplayText(mobileEntitlement.getEntitlementList().get(0).getDisplayText());
	    }
	}catch(IseBusinessException ibe){
	    error(ibe.getFullMessage());
	}

	nextStepsPanel = new NextStepsPanel("nextStepsPanel", selectedEntitlement);
	entitlementTypesSelectionContainer.add(nextStepsPanel);

	nextStepsPanel.setOutputMarkupId(true);
	nextStepsPanel.setOutputMarkupPlaceholderTag(true);
	nextStepsPanel.setVisible(false);

	/** Page Buttons Code here.........**/

	cancelButton = new AjaxLink<Object>("cancelButton") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		if (target != null){
		    logger.debug("Select Entitlements : Cancel clicked .....");
		    //Canceling triage flow
		    getDailyRhythmPortalSession().setTriageEvent(null);
		    setResponsePage(CustomerDashboardPage.class);
		}
	    }
	};
	cancelButton.setOutputMarkupPlaceholderTag(true);
	add(cancelButton);

	continueLink = new AjaxLink<Object>("continueLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.debug("continueLink onClick");
		//Canceling triage flow
		getDailyRhythmPortalSession().setTriageEvent(null);
		setResponsePage(CustomerDashboardPage.class);
	    }
	};
	continueLink.setMarkupId("continueLink");
	continueLink.setOutputMarkupId(true);
	continueLink.setOutputMarkupPlaceholderTag(true);
	continueLink.setVisible(false);
	add(continueLink);

	backButton = new AjaxLink<Object>("backButton") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		if (target != null){
		    target.add(entitlementSelectionPanel);
		    target.add(nextStepsPanel);
		    if (isNextStepsPaneldisplayed){
			nextStepsPanel.setVisible(false);
			entitlementSelectionPanel.setVisible(true);
			cancelButton.setVisible(true);
			isNextStepsPaneldisplayed = false;
			continueLink.setVisible(false);
			printPageViewType = "SELC_PAGE";
			String test[] = selectedEntitlement.getDisplayText().split(":");
			selectedEntitlement.setDisplayText(test[0].trim());
			setResponsePage(getPage());
		    }else{
			logger.info("Select Entitlements : Back clicked .....");
			setResponsePage(returnPage);
		    }
		}
	    }
	};
	backButton.setOutputMarkupPlaceholderTag(true);
	add(backButton);

	add(new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
	    }

	    @Override
	    public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		response.renderOnLoadJavaScript("scrollToBottom();");
	    }
	});

	final AjaxLink<Object> printButton = new AjaxLink<Object>("printButton") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.debug("print onClick");

		HashMap<String, Object> entitlementDataMap = new HashMap<String, Object>();
		entitlementDataMap.put("MobileEntitlement", mobileEntitlement);
		entitlementDataMap.put("SelectedEntitlement", selectedEntitlement);
		entitlementDataMap.put("EntitlementLookup", entitlementLookup);
		// Store the selected info to session temporarily.... 
		getDailyRhythmPortalSession().setEntitlementDataMap(entitlementDataMap);

		PageParameters pageParams = new PageParameters();
		pageParams.add("printPageViewType", printPageViewType);

		String url = String.valueOf(getPage().urlFor(EntitlementSummaryPrintPage.class, pageParams));
		target.appendJavaScript("window.open('" + url + "')");
	    }

	};
	printButton.setOutputMarkupPlaceholderTag(true);
	add(printButton);

    }

    class EntitlementSelectionPanel extends BasePanel {

	private static final long serialVersionUID = 1L;
	private AjaxFallbackDefaultDataTable<Entitlement> dataTable;

	public EntitlementSelectionPanel(String id, final EntitlementLookup entitlementLookup,
		MobileEntitlement mobileEntitlement) {
	    super(id);
	    logger.info("in the Entitlement Selection panel........");

	    final List<IColumn<Entitlement>> columns = new ArrayList<IColumn<Entitlement>>();
	    ReturnExchangeDataProvider returnExchangeDataProvider = null;

	    returnExchangeDataProvider = new ReturnExchangeDataProvider(mobileEntitlement.getEntitlementList());

	    columns.add(new PropertyColumn<Entitlement>(new ResourceModel("retExch.entitlement.option"), "displayText",
		    "displayText"));

	    columns.add(new AbstractColumn<Entitlement>(new ResourceModel("retExch.entitlement.details")) {

		private static final long serialVersionUID = 1L;

		@Override
		public void populateItem(Item<ICellPopulator<Entitlement>> cellItem, String componentId,
			IModel<Entitlement> rowModel) {
		    Entitlement row = (Entitlement) rowModel.getObject();
		    cellItem.add(new DetailsDisplayLabelPanel(componentId, rowModel, row, entitlementLookup));
		}

	    });

	    columns.add(new AbstractColumn<Entitlement>(new ResourceModel("retExch.select.header")) {

		private static final long serialVersionUID = 1L;

		@Override
		public void populateItem(Item<ICellPopulator<Entitlement>> cellItem, String componentId,
			IModel<Entitlement> rowModel) {
		    Entitlement row = (Entitlement) rowModel.getObject();
		    cellItem.add(new SelectReturnActionPanel(componentId, rowModel, row, entitlementLookup));
		}

		@Override
		public Component getHeader(String componentId) {
		    Label label = new Label(componentId, getDisplayModel());
		    label.setEscapeModelStrings(false);
		    return label;
		}
	    });
	    dataTable = new AjaxFallbackDefaultDataTable<Entitlement>("dataTable", columns, returnExchangeDataProvider,
		    Integer.MAX_VALUE);
	    dataTable.setOutputMarkupPlaceholderTag(true);
	    dataTable.setVisible(true);
	    disclaimerLabel.setVisible(true);
	    this.add(dataTable);
	}

    }

    class DetailsDisplayLabelPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public DetailsDisplayLabelPanel(String id, final IModel<Entitlement> model, final Entitlement row,
		final EntitlementLookup entitlementLookup) {
	    super(id, model);
	    String details = row.getDetails();
	    // Fix Me !!  - Need Regular Expression to replace..   
	    details = details.replaceAll("<li>", "").replaceAll("</li>", "");
	    add(new Label("detailsLabel", new Model<String>(details)));
	}
    }

    class SelectReturnActionPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public SelectReturnActionPanel(String id, final IModel<Entitlement> model, final Entitlement row,
		final EntitlementLookup entitlementLookup) {
	    super(id, model);

	    AjaxLink<Void> selectReturnLink = new AjaxLink<Void>("selectReturnLink") {

		private static final long serialVersionUID = 1L;

		@Override
		public void onClick(AjaxRequestTarget target) {
		    if (row == null){// ERROR CASE - CHECK FOR SUITABLE   // ERROR	// CONDITIONS
			error("SOMEERROR"/* * getString(* "SOMEERRORSTRING FROM PROPERTIES FILE"* )*/);
			// target.add(feedbackPanel);
			target.appendJavaScript("scrollToTop();");
		    }else{
			selectedEntitlement.setDisplayText(row.getDisplayText() + " : Next Steps");
			selectedEntitlement.setActions(row.getActions());

			target.add(nextStepsPanel);
			target.add(entitlementSelectionPanel);
			target.add(continueLink);
			target.add(cancelButton);
			target.add(backButton);
			target.add(disclaimerLabel);
			entitlementSelectionPanel.setVisible(false);
			nextStepsPanel.setVisible(true);
			continueLink.setVisible(true);
			cancelButton.setVisible(false);
			//backButton.setVisible(false); 
			disclaimerLabel.setVisible(true);
			isNextStepsPaneldisplayed = true;
			printPageViewType = "NXT_PAGE";

			RetExchDevcEntitlsModel retExchDevcEntitlsModel = new RetExchDevcEntitlsModel();
			BeanUtils.copyProperties(row, retExchDevcEntitlsModel);

			// 1. write selected Customer & Return info to DB
			try{
			    DataTransferSummary dataTransferSummary = new DataTransferSummary();
			    DailyRhythmPortalSession session = getDailyRhythmPortalSession();
			    dataTransferSummary.setStoreId(session.getDrpUser().getStore().getId());
			    dataTransferSummary.setTransferFlag(false);
			    dataTransferSummary.setSrcSys(drpPropertyService.getProperty("SOURCE_SYSTEM"));

			    //dataTransferSummary.setWorkFlowType(WorkFlowTypeEnum.valueOf(returnExchangeWorkflowType));
			    if (session.getDrpUser() != null && session.getDrpUser().getUserId() != null){
				dataTransferSummary.setCreatedBy(session.getDrpUser().getUserId());
				retExchDevcEntitlsModel.setCreated_by(session.getDrpUser().getUserId());
			    }

			    retExchDevcEntitlsModel.setTransaction_key(entitlementLookup.getTransactionId());

			    if (row.getCoverageBenefit() != null){
				retExchDevcEntitlsModel.setCoverageBenefit(row.getCoverageBenefit());
			    }
			    if (row.getCoverageBenefitPromptCode() != null){
				retExchDevcEntitlsModel
					.setCoverageBenefitPromptCode(row.getCoverageBenefitPromptCode());
			    }
			    if (row.getCoverageVehicle() != null){
				retExchDevcEntitlsModel.setCoverageVehicle(row.getCoverageVehicle());
			    }
			    if (entitlementLookup.getDevicePhoneNumber() != null){
				retExchDevcEntitlsModel.setPhoneNumber(entitlementLookup.getDevicePhoneNumber());
			    }else
				throw new IseBusinessException("Phone number is null");
			    if (entitlementLookup.getSerialNumber() != null){
				retExchDevcEntitlsModel.setHandsetId(entitlementLookup.getSerialNumber());
			    }else
				throw new IseBusinessException("HandsetId is null");
			    if (entitlementLookup.getDefectiveDevice() != null){
				retExchDevcEntitlsModel.setDefectiveStatus(Boolean.parseBoolean(entitlementLookup
					.getDefectiveDevice().name().toLowerCase()));
			    }
			    if (row.getCoverageBenefitPromptCode() != null){
				retExchDevcEntitlsModel
					.setCoverageBenefitPromptCode(row.getCoverageBenefitPromptCode());
			    }
			    if (row.getEntitlementType() != null){
				retExchDevcEntitlsModel.setType(row.getEntitlementType());
			    }
			    if (entitlementLookup.getGspNumber() != null){
				retExchDevcEntitlsModel.setProtectionPlanId(entitlementLookup.getGspNumber());
			    }
			    if (dataSharingKey == null){
				dataSharingKey = returnExchangeService.createDeviceEntitlement(retExchDevcEntitlsModel,
					dataTransferSummary, null);
			    }else{
				dataSharingKey = returnExchangeService.createDeviceEntitlement(retExchDevcEntitlsModel,
					dataTransferSummary, dataSharingKey);
			    }
			    //If flow is started with triage then updating triage history customer benefits.
			    if (session.getWorkflowAction() == DailyRhythmPortalSession.WorkflowAction.Triage){
				if (session.getTriageEvent() != null){
				    session.getTriageEvent().setCustomerBenefits(row.getDisplayText());
				    triageService.updateTriageHistoryCustomerBenefit(session.getTriageEvent());
				}
			    }

			}catch(ServiceException se){
			    error(se.getFullMessage());
			}catch(IseBusinessException ibe){
			    error(ibe.getFullMessage());
			}
		    }
		}

		@Override
		public boolean isVisible() {
		    return !row.getDisplayText().equalsIgnoreCase(NO_ENTITLEMENTS_AVAILABLE);
		}
	    };
	    selectReturnLink.setOutputMarkupPlaceholderTag(true);
	    logger.debug("trial in return button of select entitlements");
	    add(selectReturnLink);
	}
    }

    class NextStepsPanel extends BasePanel {

	private static final long serialVersionUID = 1L;
	private CompoundPropertyModel<Entitlement> model;

	public NextStepsPanel(String id, Entitlement entitlement) {
	    super(id);

	    model = new CompoundPropertyModel<Entitlement>(entitlement);
	    add(new Label("nextStepsHeader", model.bind("displayText")).setEscapeModelStrings(false));
	    add(new Label("nextStepsLabel", model.bind("actions")).setEscapeModelStrings(false));
	}
    }

}
