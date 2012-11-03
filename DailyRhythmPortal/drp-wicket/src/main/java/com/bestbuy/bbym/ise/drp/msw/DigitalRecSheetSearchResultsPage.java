package com.bestbuy.bbym.ise.drp.msw;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.CarrierPlan;
import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.Device;
import com.bestbuy.bbym.ise.domain.Line;
import com.bestbuy.bbym.ise.drp.common.NewBaseNavPage;
import com.bestbuy.bbym.ise.drp.dashboard.CarrierInfoDataProvider;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.Recommendation;
import com.bestbuy.bbym.ise.drp.recsheet.MobileYouSectionPage;
import com.bestbuy.bbym.ise.drp.util.FormatModel;
import com.bestbuy.bbym.ise.drp.util.PhoneFormatter;
import com.bestbuy.bbym.ise.drp.util.PhoneNumberConverter;

public class DigitalRecSheetSearchResultsPage extends NewBaseNavPage {

    private static Logger logger = LoggerFactory.getLogger(DigitalRecSheetSearchResultsPage.class);
    private static final long serialVersionUID = 1L;

    private final static PhoneNumberConverter phoneNumberConverter = new PhoneNumberConverter();
    private DefaultDataTable<Recommendation> searchResultsTable;
    private final MobileSalesWorkspacePage mswPage;
    private SortableRecSheetDataProvider searchResultsDataProvider;
    private final DigitalRecSheetSearchPanel searchPanel;
    private String storeLabel;
    private Recommendation recSheet;

    public DigitalRecSheetSearchResultsPage(final PageParameters parameters, MobileSalesWorkspacePage mswPage,
	    SortableRecSheetDataProvider searchResultsDataProvider,
	    DigitalRecSheetSearchPanel digitalRecSheetSearchPanel) {

	super(parameters);
	this.mswPage = mswPage;
	this.searchResultsDataProvider = searchResultsDataProvider;
	this.searchPanel = digitalRecSheetSearchPanel;

	final String na = getString("notApplicable.label");

	if (searchPanel.isSearchAllStores()){
	    storeLabel = "All Stores";
	}else{
	    storeLabel = getDailyRhythmPortalSession().getDrpUser().getStoreId();
	}

	// pre-fill values for search using customer info we may have already
	// obtained on the dashboard as a result of a lookup from the carrier
	final Customer cust = getDailyRhythmPortalSession().getCustomer();

	add(new Label("lastNameLabel", new FormatModel<String>(searchPanel.getLastName(), na)));

	add(new Label("phoneNumberLabel", new FormatModel<String>(phoneNumberConverter.convertToString(searchPanel
		.getPhoneNumber(), getLocale()), new PhoneFormatter<String>(), na)));

	add(new Label("storeLabel", new FormatModel<String>(storeLabel, na)));

	// %% New Recsheet Button %%
	AjaxLink<Object> createNewRecSheet = new AjaxLink<Object>("createNewRecSheetLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.debug("launching new rec worksheet");
		recSheet = new Recommendation();
		// copy over values from the carrier customer searched on
		// the dashboard to the extent possible
		try{
		    if (cust.getSubscription() != null && cust.getSubscription().getLines() != null){

			recSheet.setFirstName(cust.getFirstName());
			recSheet.setLastName(cust.getLastName());
			// Contact phone is the phone number looked up in a
			// carrier search
			recSheet.setMobileNo(cust.getContactPhone());
			recSheet.setTransferFlag(false);
			if (!cust.getSubscription().getLines().isEmpty()
				&& cust.getSubscription().getLines().get(0) != null){

			    // Retrieving line... In order to get the same line
			    // as carrier panel need to use logic from
			    // CarrierInfoDataProvider, mostly for same sorting
			    // logic, etc.
			    CarrierInfoDataProvider provider = new CarrierInfoDataProvider();
			    provider.setLineDataList(cust.getSubscription().getLines());
			    provider.setSearchPhoneNum(cust.getContactPhone());
			    List<Line> allLineList = provider.getLineDataList();
			    Line line = allLineList.get(0);

			    List<Line> lineList = new LinkedList<Line>(allLineList.subList(1, allLineList.size() > 6?6
				    :allLineList.size()));

			    Device device = line.getDevice();
			    recSheet.setUpgradeEligibilityDate(line.getStdEligibilityDate());
			    recSheet.setUpgradeReminderText(line.getOptinText());
			    recSheet.setUpgradeReminderCall(line.getOptinVoice());
			    recSheet.setTradeInValue(device == null?null:device.getTradeInValue());
			    recSheet.setOtherLineList(lineList);

			    String planInfo = "";
			    try{
				CarrierPlan plan = line.getCarrierPlans().get(0);
				planInfo = StringUtils.trimToEmpty(plan.getPlanName());
				planInfo += " " + StringUtils.trimToEmpty(plan.getPlanType());
			    }catch(IndexOutOfBoundsException e){
				logger.error("carrier plans was empty");
			    }

			    String subscriptionInfo = "Carrier: "
				    + StringUtils.trimToEmpty(cust.getSubscription().getCarrier().toString())
				    + "\nPlan: " + planInfo + "\nDevice: "
				    + StringUtils.trimToEmpty(device.getDescription());
			    recSheet.setSubscriptionInfo(subscriptionInfo);
			    recSheet.setCreatedOn(new Date());
			} // end of if lines empty check.
		    } // end of nulls check.

		}catch(NullPointerException e){
		    logger.warn("missing data in customer object: " + e.getLocalizedMessage());
		}
		logger.info(">> Create new recsheet :" + recSheet.toString());
		recSheet.setRecShtTyp(1);
		DrpUser drpUser = getDailyRhythmPortalSession().getDrpUser();
		recSheet.setBbyCnsFrstNm(drpUser.getFirstName());
		recSheet.setBbyCnsLastNm(drpUser.getLastName());
		recSheet.setBbyCnsPhNbr(drpUser.getLocationPhoneNum());
		getDailyRhythmPortalSession().setRecommendation(recSheet);
		setResponsePage(MobileYouSectionPage.class);
	    }
	};
	createNewRecSheet.setOutputMarkupPlaceholderTag(true);
	createNewRecSheet.setMarkupId("createNewRecSheetLink");
	add(createNewRecSheet);

	// rec sheet search results
	List<IColumn<Recommendation>> columns = new ArrayList<IColumn<Recommendation>>();

	columns
		.add(new PropertyColumn<Recommendation>(new Model<String>(getString("firstName")), "first", "firstName"));
	columns.add(new PropertyColumn<Recommendation>(new Model<String>(getString("lastName")), "last", "lastName"));
	columns
		.add(new PropertyColumn<Recommendation>(new Model<String>(getString("phoneNumber")), "phone",
			"mobileNo") {

		    private static final long serialVersionUID = 1L;

		    @Override
		    protected IModel<String> createLabelModel(IModel<Recommendation> rowModel) {
			String mobileNum = new PropertyModel<String>(rowModel, getPropertyExpression()).getObject()
				.toString();
			return new Model<String>(phoneNumberConverter.convertToString(mobileNum, getLocale()));
		    }
		});

	columns.add(new PropertyColumn<Recommendation>(new Model<String>(getString("createdOn")), "createdOn",
		"createdOn") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected IModel<String> createLabelModel(IModel<Recommendation> rowModel) {
		return new Model<String>(DateFormatUtils.format(new PropertyModel<Date>(rowModel,
			getPropertyExpression()).getObject(), "MM-dd-yyyy h:mma"));
	    }
	});

	// hyperlink to launch selected worksheet
	columns.add(new AbstractColumn<Recommendation>(null) {

	    private static final long serialVersionUID = 1L;

	    public void populateItem(Item<ICellPopulator<Recommendation>> cellItem, String componentId,
		    IModel<Recommendation> model) {
		cellItem.add(new ActionPanel(componentId, model));
	    }
	});

	searchResultsTable = new DefaultDataTable<Recommendation>("searchResultsTable", columns,
		this.searchResultsDataProvider, Integer.MAX_VALUE);
	// searchResultsTable.setOutputMarkupId(true);
	searchResultsTable.setOutputMarkupPlaceholderTag(true);
	add(searchResultsTable);

	AjaxLink<Object> newSearchLink = new AjaxLink<Object>("newSearchLink") {

	    private static final long serialVersionUID = -4891689786270670982L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.info("new search submit..");
		setResponsePage(MobileSalesWorkspacePage.class);

	    }

	};
	add(newSearchLink);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
	super.renderHead(response);
	response.renderOnDomReadyJavaScript("setupRecSheetTable();");
    }

    class ActionPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public ActionPanel(String id, IModel<Recommendation> model) {
	    super(id, model);
	    add(new Link<Recommendation>("editLink") {

		private static final long serialVersionUID = 1L;

		@Override
		public void onClick() {
		    Recommendation rec = (Recommendation) getParent().getDefaultModelObject();
		    rec.setTransferFlag(false);
		    getDailyRhythmPortalSession().setRecommendation(rec);
		    MobileYouSectionPage page = new MobileYouSectionPage(getPageParameters());
		    setResponsePage(page);
		}
	    });
	}
    }

}
