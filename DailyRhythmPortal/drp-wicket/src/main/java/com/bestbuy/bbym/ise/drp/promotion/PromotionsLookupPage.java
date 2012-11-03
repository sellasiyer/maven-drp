package com.bestbuy.bbym.ise.drp.promotion;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.drp.common.BasePanel;
import com.bestbuy.bbym.ise.drp.common.NewBaseNavPage;
import com.bestbuy.bbym.ise.drp.common.Styles;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.Promotion;
import com.bestbuy.bbym.ise.drp.domain.RelatedPromotion;
import com.bestbuy.bbym.ise.drp.service.DrpPropertyService;
import com.bestbuy.bbym.ise.drp.service.PromotionService;
import com.bestbuy.bbym.ise.drp.util.DateFormatter;
import com.bestbuy.bbym.ise.drp.util.FormatPropertyColumn;
import com.bestbuy.bbym.ise.drp.util.panel.SpanPanel;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

public class PromotionsLookupPage extends NewBaseNavPage {

    private static Logger logger = LoggerFactory.getLogger(PromotionsLookupPage.class);
    private static final long serialVersionUID = 1L;

    enum SearchByField {

	Customer, PromoCode
    };

    private SearchByField searchByField = SearchByField.Customer;
    private String phoneNumber;
    private String email;
    private String promoCode;
    HashMap<RelatedPromotion.SearchParameterType, String> searchParamMap;
    private TextField<String> phoneSearchField;
    private TextField<String> emailSearchField;

    @SpringBean(name = "promotionService")
    private PromotionService promotionService;

    @SpringBean(name = "drpPropertyService")
    private DrpPropertyService drpPropertyService;

    private AbstractDefaultAjaxBehavior wicketBehavior;

    public PromotionsLookupPage() {
	super(null);

	final Form<Object> promotionsLookupForm = new Form<Object>("promotionsLookupForm");
	promotionsLookupForm.setOutputMarkupPlaceholderTag(true);
	add(promotionsLookupForm);

	final FeedbackPanel promotionLookupFeedbackPanel = new FeedbackPanel("promotionLookupFeedback");
	promotionLookupFeedbackPanel.setOutputMarkupPlaceholderTag(true);
	add(promotionLookupFeedbackPanel);

	final SearchPromotionDataProvider searchPromotionDataProvider = new SearchPromotionDataProvider();

	final DateFormatter<Date> dateFmt = new DateFormatter<Date>();
	final String na = getString("notApplicable.label");

	final String promotionValueNa = getString("promotionsLookupForm.promotionValue.notApplicable.label");

	final String defaultPhoneNumberText = getString("promotionsLookupPage.phoneNumber.default");

	final String defaultEmailText = getString("promotionsLookupPage.email.default");

	final String defaultPromoCodeText = getString("promotionsLookupPage.promotionCode.default");

	final DrpUser user = getDailyRhythmPortalSession().getDrpUser();

	final Customer customer = getDailyRhythmPortalSession().getCustomer();

	if (customer != null && customer.getContactPhone() != null){
	    setPhoneNumber(customer.getContactPhone());
	}
	if (customer != null && customer.getEmail() != null){
	    setEmail(customer.getEmail());
	}

	final List<IColumn<RelatedPromotion>> columns = new ArrayList<IColumn<RelatedPromotion>>();
	columns.add(new FormatPropertyColumn<RelatedPromotion, String>(new ResourceModel(
		"promotionsLookupPage.promotionCode.column"), "promotion.promotionCode", "promotion.promotionCode",
		null, na));
	columns.add(new FormatPropertyColumn<RelatedPromotion, String>(new ResourceModel(
		"promotionsLookupPage.promotionDescription.column"), "promotion.promotionDescription",
		"promotion.promotionDescription", null, na) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void populateItem(Item<ICellPopulator<RelatedPromotion>> item, String componentId,
		    IModel<RelatedPromotion> rowModel) {
		RelatedPromotion row = (RelatedPromotion) rowModel.getObject();
		item.add(new DescriptionLinkPanel(componentId, row, na));
	    }

	});
	columns.add(new FormatPropertyColumn<RelatedPromotion, String>(new ResourceModel(
		"promotionsLookupPage.promotionValue.column"), "promotion.promotionValue", "promotion.promotionValue",
		null, promotionValueNa) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void populateItem(Item<ICellPopulator<RelatedPromotion>> item, String componentId,
		    IModel<RelatedPromotion> rowModel) {
		RelatedPromotion row = (RelatedPromotion) rowModel.getObject();
		item.add(new PromotionValueLinkPanel(componentId, row, promotionValueNa));
	    }

	});
	columns.add(new FormatPropertyColumn<RelatedPromotion, Date>(new ResourceModel(
		"promotionsLookupPage.promotionEndDate.column"), "promotion.promotionEndDate",
		"promotion.promotionEndDate", dateFmt, na));

	columns.add(new PropertyColumn<RelatedPromotion>(new ResourceModel("promotionsLookupPage.availability.column"),
		"promotionAvailabilityType", "promotionAvailabilityType") {

	    private static final long serialVersionUID = 1L;

	    @SuppressWarnings("rawtypes")
	    @Override
	    public void populateItem(Item cellItem, String componentId, IModel rowModel) {

		final RelatedPromotion row = (RelatedPromotion) rowModel.getObject();
		cellItem.add(new SpanPanel<RelatedPromotion>(componentId, rowModel) {

		    private static final long serialVersionUID = 1L;

		    @Override
		    public String generateLabelString() {
			return generateFormattedLabelString(row.getPromotionAvailabilityType());
		    }
		});
	    }

	});

	columns.add(new PropertyColumn<RelatedPromotion>(new ResourceModel(
		"promotionsLookupPage.unavailableReason.column"), "reason", "reason"));

	final WebMarkupContainer searchCustomersContainer = new WebMarkupContainer("searchCustomersContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return searchPromotionDataProvider.size() > 0 && searchByField == SearchByField.Customer;
	    }
	};
	searchCustomersContainer.setMarkupId("searchCustomersContainer");
	searchCustomersContainer.setOutputMarkupId(true);
	searchCustomersContainer.setOutputMarkupPlaceholderTag(true);
	add(searchCustomersContainer);

	final AjaxFallbackDefaultDataTable<RelatedPromotion> searchCustomersTable = new AjaxFallbackDefaultDataTable<RelatedPromotion>(
		"searchCustomersTable", columns, searchPromotionDataProvider, 200);
	searchCustomersTable.setOutputMarkupPlaceholderTag(true);
	searchCustomersContainer.add(searchCustomersTable);

	searchCustomersTable.add(new AjaxEventBehavior("onClick") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onEvent(AjaxRequestTarget target) {
		logger.debug("onClickJS=setupSearchCustomersTable();");
		target.appendJavaScript("setupSearchCustomersTable();");
	    }
	});

	final List<IColumn<RelatedPromotion>> promotionSearchColumns = new ArrayList<IColumn<RelatedPromotion>>();
	promotionSearchColumns.add(new FormatPropertyColumn<RelatedPromotion, String>(new ResourceModel(
		"promotionsLookupPage.promotionCode.column"), "promotion.promotionCode", "promotion.promotionCode",
		null, na));
	promotionSearchColumns.add(new FormatPropertyColumn<RelatedPromotion, String>(new ResourceModel(
		"promotionsLookupPage.promotionDescription.column"), "promotion.promotionDescription",
		"promotion.promotionDescription", null, na) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void populateItem(Item<ICellPopulator<RelatedPromotion>> item, String componentId,
		    IModel<RelatedPromotion> rowModel) {
		RelatedPromotion row = (RelatedPromotion) rowModel.getObject();
		item.add(new DescriptionLinkPanel(componentId, row, na));
	    }

	});
	promotionSearchColumns.add(new FormatPropertyColumn<RelatedPromotion, String>(new ResourceModel(
		"promotionsLookupPage.promotionValue.column"), "promotion.promotionValue", "promotion.promotionValue",
		null, na) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void populateItem(Item<ICellPopulator<RelatedPromotion>> item, String componentId,
		    IModel<RelatedPromotion> rowModel) {
		RelatedPromotion row = (RelatedPromotion) rowModel.getObject();
		item.add(new PromotionValueLinkPanel(componentId, row, promotionValueNa));
	    }

	});
	promotionSearchColumns.add(new FormatPropertyColumn<RelatedPromotion, Date>(new ResourceModel(
		"promotionsLookupPage.regEndDate.column"), "promotion.promotionEndDate", "promotion.promotionEndDate",
		dateFmt, na));
	promotionSearchColumns.add(new PropertyColumn<RelatedPromotion>(null, null, null) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void populateItem(Item<ICellPopulator<RelatedPromotion>> item, String componentId,
		    IModel<RelatedPromotion> rowModel) {
		RelatedPromotion row = (RelatedPromotion) rowModel.getObject();
		item.add(new RegisterLinkPanel(componentId, row, promotionLookupFeedbackPanel));
	    }

	});

	final WebMarkupContainer searchPromotionsContainer = new WebMarkupContainer("searchPromotionsContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return searchPromotionDataProvider.size() > 0 && searchByField == SearchByField.PromoCode;
	    }
	};
	searchPromotionsContainer.setMarkupId("searchPromotionsContainer");
	searchPromotionsContainer.setOutputMarkupId(true);
	searchPromotionsContainer.setOutputMarkupPlaceholderTag(true);
	add(searchPromotionsContainer);

	final AjaxFallbackDefaultDataTable<RelatedPromotion> searchPromotionsTable = new AjaxFallbackDefaultDataTable<RelatedPromotion>(
		"searchPromotionsTable", promotionSearchColumns, searchPromotionDataProvider, 200);
	searchPromotionsTable.setOutputMarkupPlaceholderTag(true);
	searchPromotionsContainer.add(searchPromotionsTable);

	searchPromotionsTable.add(new AjaxEventBehavior("onClick") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onEvent(AjaxRequestTarget target) {
		logger.debug("onClickJS=setupSearchPromotionsTable();");
		target.appendJavaScript("setupSearchPromotionsTable();");
	    }
	});

	// Customer Search Filter

	// phone search field
	phoneSearchField = new TextField<String>("phoneNumberSearch", new PropertyModel<String>(this, "phoneNumber"));
	phoneSearchField.setOutputMarkupPlaceholderTag(true);
	promotionsLookupForm.add(phoneSearchField);

	phoneSearchField.add(new Behavior() {

	    private static final long serialVersionUID = 1L;

	    public void onComponentTag(Component component, ComponentTag tag) {
		tag.put("data-prefill", defaultPhoneNumberText);
	    }
	});

	// email search field

	emailSearchField = new TextField<String>("emailSearch", new PropertyModel<String>(this, "email"));
	emailSearchField.setOutputMarkupPlaceholderTag(true);
	promotionsLookupForm.add(emailSearchField);

	emailSearchField.add(new Behavior() {

	    private static final long serialVersionUID = 1L;

	    public void onComponentTag(Component component, ComponentTag tag) {
		tag.put("data-prefill", defaultEmailText);
	    }
	});

	AjaxLink<Object> customerSearchLink = new AjaxLink<Object>("customerSearchLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		searchByField = SearchByField.Customer;
		logger.debug("customerSearchLink onClick" + searchByField);
	    }
	};
	customerSearchLink.setOutputMarkupPlaceholderTag(true);
	promotionsLookupForm.add(customerSearchLink);

	// Customer search button
	AjaxButton customerSearchButton = new AjaxButton("customerSearchButton", new ResourceModel(
		"promotionsLookupForm.customerSearch.button"), promotionsLookupForm) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.debug("customerSearch onsubmit");

		if (SearchByField.Customer.equals(searchByField)
			&& ((email == null || "".equalsIgnoreCase(email) || defaultEmailText.equalsIgnoreCase(email)) && (phoneNumber == null
				|| "".equalsIgnoreCase(phoneNumber) || defaultPhoneNumberText
				.equalsIgnoreCase(phoneNumber)))){
		    error(getString("promotionsLookupForm.phoneNumberSearch.Required"));
		    return;
		}

		if (SearchByField.Customer.equals(searchByField) && phoneNumber != null
			&& !defaultPhoneNumberText.equalsIgnoreCase(phoneNumber)){
		    String NUM_REGEX = "[0-9]+";

		    if (!phoneNumber.matches(NUM_REGEX)){
			error(getString("promotionsLookupForm.phoneNumberSearch.Validator"));
			return;
		    }
		}

		if (SearchByField.Customer.equals(searchByField) && email != null
			&& !defaultEmailText.equalsIgnoreCase(email)){
		    String EMAIL_REGEX = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z]{2,}){1}$)";

		    if (!email.matches(EMAIL_REGEX)){
			error(getString("promotionsLookupForm.emailSearch.Validator"));
			return;
		    }
		}

		target.appendJavaScript("showCustomerPromotionSearchLoading(true);loadData();");

	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		logger.error("incustomer search for promotions onError...");
		target.add(promotionLookupFeedbackPanel);
	    }

	};
	customerSearchButton.setOutputMarkupPlaceholderTag(true);
	customerSearchButton.setMarkupId("customerSearchButton");
	customerSearchButton.setOutputMarkupId(true);
	promotionsLookupForm.add(customerSearchButton);

	// Promotions Filter
	// promo code search field

	final TextField<String> promoCodeSearchField = new TextField<String>("promoCodeSearch",
		new PropertyModel<String>(this, "promoCode"));
	promoCodeSearchField.setOutputMarkupPlaceholderTag(true);
	promotionsLookupForm.add(promoCodeSearchField);

	promoCodeSearchField.add(new Behavior() {

	    private static final long serialVersionUID = 1L;

	    public void onComponentTag(Component component, ComponentTag tag) {
		tag.put("data-prefill", defaultPromoCodeText);
	    }
	});

	AjaxLink<Object> promotionSearchLink = new AjaxLink<Object>("promotionSearchLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		searchByField = SearchByField.PromoCode;
		logger.debug("promotionSearchLink onClick" + searchByField);
	    }
	};
	promotionSearchLink.setOutputMarkupPlaceholderTag(true);
	promotionsLookupForm.add(promotionSearchLink);

	// Customer search button
	AjaxButton promotionSearchButton = new AjaxButton("promotionSearchButton", new ResourceModel(
		"promotionsLookupForm.promotionSearch.button"), promotionsLookupForm) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.debug("promotionSearch onsubmit");
		if (SearchByField.PromoCode.equals(searchByField)
			&& (promoCode == null || "".equalsIgnoreCase(promoCode) || defaultPromoCodeText
				.equalsIgnoreCase(promoCode))){
		    error(getString("promotionsLookupForm.promoCodeSearch.Required"));
		    return;
		}
		target.appendJavaScript("showCustomerPromotionSearchLoading(true);loadData();");

	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		logger.error("in promo  search for promotions onError...");
		target.add(promotionLookupFeedbackPanel);
	    }

	};
	promotionSearchButton.setOutputMarkupPlaceholderTag(true);
	promotionSearchButton.setMarkupId("promotionSearchButton");
	promotionSearchButton.setOutputMarkupId(true);
	promotionsLookupForm.add(promotionSearchButton);

	// means by which JS can callback into Java
	wicketBehavior = new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
		String id = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("id").toString();
		logger.debug("wicketBehavior id=" + id);
		if ("load".equals(id)){
		    searchParamMap = new HashMap<RelatedPromotion.SearchParameterType, String>();

		    if (SearchByField.Customer.equals(searchByField) && phoneNumber != null
			    && !defaultPhoneNumberText.equalsIgnoreCase(phoneNumber)
			    && !"".equalsIgnoreCase(phoneNumber)){
			searchParamMap.put(RelatedPromotion.SearchParameterType.ACTIVATION_PHONE_NUMBER,
				getPhoneNumber());
		    }

		    if (SearchByField.Customer.equals(searchByField) && email != null
			    && !defaultEmailText.equalsIgnoreCase(email) && !"".equalsIgnoreCase(email)){
			searchParamMap.put(RelatedPromotion.SearchParameterType.EMAIL_ADDRESS, getEmail());
		    }

		    if (SearchByField.PromoCode.equals(searchByField) && promoCode != null
			    && !defaultPromoCodeText.equalsIgnoreCase(promoCode) && !"".equalsIgnoreCase(promoCode)){
			searchParamMap.put(RelatedPromotion.SearchParameterType.PROMOTION_CODE, getPromoCode());

		    }

		    try{
			List<RelatedPromotion> promotionList = promotionService.getRelatedPromotions(searchParamMap,
				Integer.parseInt(user.getStoreId()));
			if (promotionList != null){
			    logger.debug("promotionList from the service ...." + promotionList.toString());
			}

			searchPromotionDataProvider.setRelatedPromotionList(promotionList);
		    }catch(ServiceException se){
			String message = "An unexpected exception occured while attempting to get the promotions for the customer";
			logger.error(message, se);
			// processException(message, se);
			error(se.getFullMessage());
			target.add(promotionLookupFeedbackPanel);
			target.add(searchCustomersContainer);
			target.add(searchPromotionsContainer);
			target.appendJavaScript("showCustomerPromotionSearchLoading(false);");
			return;
		    }catch(IseBusinessException be){
			searchPromotionDataProvider.setRelatedPromotionList(null);
			logger.error("IseBusinessException while getting the promotions for the customer");
			error(be.getFullMessage());
			target.add(promotionLookupFeedbackPanel);
			target.add(searchCustomersContainer);
			target.add(searchPromotionsContainer);
			target.appendJavaScript("showCustomerPromotionSearchLoading(false);");
			return;
		    }catch(Exception e){
			searchPromotionDataProvider.setRelatedPromotionList(null);
			logger.error("Exception while getting the promotions for the customer", e);
		    }
		    target.add(searchCustomersContainer);
		    target.add(searchPromotionsContainer);
		    target
			    .appendJavaScript("showCustomerPromotionSearchLoading(false);setupSearchCustomersTable();setupSearchPromotionsTable();");
		    if (phoneNumber == null){
			phoneNumber = defaultPhoneNumberText;
		    }

		    if (email == null){
			email = defaultEmailText;
		    }
		    //  target.add(phoneSearchField);
		    //  target.add(emailSearchField);  

		    if (searchPromotionDataProvider.getRelatedPromotionList() == null
			    || searchPromotionDataProvider.getRelatedPromotionList().isEmpty()){
			info(new StringResourceModel("noDataFound", PromotionsLookupPage.this, null).getString());
		    }
		}
	    }

	};
	add(wicketBehavior);

	add(new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
	    }

	    @Override
	    public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		StringBuilder onDomReadyJS = new StringBuilder();
		onDomReadyJS.append("selectSearchPromotionsNavFilter('");
		onDomReadyJS.append(searchByField.name());
		onDomReadyJS.append("');");
		onDomReadyJS.append("setupSearchNav();");
		onDomReadyJS.append("wicketBehavior = function(id) { ");
		onDomReadyJS.append("wicketAjaxGet('");
		onDomReadyJS.append(wicketBehavior.getCallbackUrl());
		onDomReadyJS.append("&id='+id); };");
		onDomReadyJS.append("showCustomerPromotionSearchLoading(false);");
		onDomReadyJS.append("setupSearchPromotionsTable();");
		response.renderOnDomReadyJavaScript(onDomReadyJS.toString());
	    }
	});

    }

    private class RegisterLinkPanel extends BasePanel {

	private static final long serialVersionUID = 1L;

	public RegisterLinkPanel(String id, final RelatedPromotion row, final FeedbackPanel promotionLookupFeedbackPanel) {
	    super(id);

	    logger.debug("in the register link panel..." + row);

	    AjaxLink<Object> registerLink = new AjaxLink<Object>("registerLink") {

		Promotion promo = row.getPromotion();

		@Override
		public boolean isEnabled() {
		    boolean propToEnable = false;
		    try{
			propToEnable = Boolean.valueOf(drpPropertyService
				.getProperty(DrpConstants.PROMO_REGISTRATION_ENABLED));
		    }catch(Exception e){
			logger.debug("Register property Error :: " + e.getMessage());
		    }
		    return propToEnable;
		}

		@Override
		public boolean isVisible() {
		    return row != null && promo != null && promo.getPromotionEndDate() != null;
		}

		private static final long serialVersionUID = 1L;

		@Override
		public void onClick(AjaxRequestTarget target) {
		    setResponsePage(new RegistrationPage(row, getPage()));
		}

		@Override
		protected void onComponentTag(ComponentTag tag) {
		    super.onComponentTag(tag);
		    if (isEnabled()){
			tag.put("class", Styles.TRIAGE_BUTTON_ACTIVE);
		    }else{
			tag.put("class", Styles.TRIAGE_BUTTON_INACTIVE);
		    }
		}

	    };
	    registerLink.setMarkupId("registerLink");
	    registerLink.setOutputMarkupId(true);
	    registerLink.setOutputMarkupPlaceholderTag(true);
	    add(registerLink);
	}

    }

    class PromotionValueLinkPanel extends BasePanel {

	private static final long serialVersionUID = 1L;

	public PromotionValueLinkPanel(String id, RelatedPromotion row, String promotionValueNa) {
	    super(id);

	    String promotionValue = null;

	    if (row != null && row.getPromotion() != null){
		if (row.getPromotion().getPromotionValue() == null
			|| "".equalsIgnoreCase(row.getPromotion().getPromotionValue())){
		    promotionValue = promotionValueNa;
		}else{
		    promotionValue = "$" + row.getPromotion().getPromotionValue();
		}
	    }

	    Label promotionValueLabel = new Label("promotionValueLabel", promotionValue);
	    add(promotionValueLabel);

	}

    }

    class DescriptionLinkPanel extends BasePanel {

	private static final long serialVersionUID = 1L;

	public DescriptionLinkPanel(String id, RelatedPromotion row, String notApplicableLabel) {
	    super(id);

	    StringBuilder promoDescription = new StringBuilder();

	    if (row != null && row.getPromotion() != null){
		if (row.getPromotion().getPromotionValue() == null
			|| "".equalsIgnoreCase(row.getPromotion().getPromotionDescription())){
		    promoDescription.append(notApplicableLabel);
		}else{
		    promoDescription.append("<span class=\"wrap\">");
		    promoDescription.append(row.getPromotion().getPromotionDescription());
		    promoDescription.append("</span>");
		}
	    }

	    Label promoDescriptionLabel = new Label("promoDescriptionLabel", promoDescription.toString());
	    promoDescriptionLabel.setEscapeModelStrings(false);
	    add(promoDescriptionLabel);

	}

    }

    private String generateFormattedLabelString(RelatedPromotion.PromotionAvailabilityType availType) {

	if (availType != null && availType == RelatedPromotion.PromotionAvailabilityType.AVAILABLE){
	    return "<span class=\"green\">" + availType.toString() + "</span>";
	}else if (availType != null && availType == RelatedPromotion.PromotionAvailabilityType.UNAVAILABLE){
	    return "<span class=\"red\">" + availType.toString() + "</span>";
	}

	return availType.toString();
    }

    public String getPhoneNumber() {
	return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getPromoCode() {
	return promoCode;
    }

    public void setPromoCode(String promoCode) {
	this.promoCode = promoCode;
    }

}
