package com.bestbuy.bbym.ise.drp.recsheet;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.WindowClosedCallback;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ContextRelativeResource;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.drp.beast.common.OkCancelModalPanel;
import com.bestbuy.bbym.ise.drp.common.LoadingModalAjaxSubmitLink;
import com.bestbuy.bbym.ise.drp.common.NewBaseNavPage;
import com.bestbuy.bbym.ise.drp.common.PdfByteArrayResource;
import com.bestbuy.bbym.ise.drp.domain.Recommendation;
import com.bestbuy.bbym.ise.drp.msw.CTRecSheetPage;
import com.bestbuy.bbym.ise.drp.msw.MobileSalesWorkspacePage;
import com.bestbuy.bbym.ise.drp.service.CustomerService;
import com.bestbuy.bbym.ise.drp.service.MobileSalesWorkspaceService;
import com.bestbuy.bbym.ise.util.Util;

/**
 * Parent class for all new rec sheet classes
 * 
 * @author a915722
 * 
 */
abstract class AbstractRecSheetPage extends NewBaseNavPage {

    public static final Locale es_PR = new Locale("es", "PR");
    public static final Locale es_US = new Locale("es", "US");

    @SpringBean(name = "mobileSalesWorkspaceService")
    private MobileSalesWorkspaceService mswService;

    @SpringBean(name = "customerService")
    private CustomerService customerService;

    private static Logger LOGGER = LoggerFactory.getLogger(AbstractRecSheetPage.class);

    protected final OkCancelModalPanel clearSection;
    protected final OkCancelModalPanel clearCustomerData;

    protected Form form;
    protected final FeedbackPanel feedbackPanel;
    protected final AjaxLink addNotesButton;

    protected final LoadingModalAjaxSubmitLink saveRecsheetButton;
    protected final AjaxSubmitLink mailRecsheetButton, printRecsheetButton;
    protected final AjaxLink clearCustomerButton;
    protected final AjaxLink clearSectionButton;
    protected final AjaxLink searchButton;

    protected Image transferToBeastImage;

    private final List<SectionDropdown> sectionMenus = new ArrayList<SectionDropdown>();

    final WebMarkupContainer recSheetTypes;

    final WebMarkupContainer refreshMenus;

    protected final Label saveIndicator;

    protected NotePanel notePanel;

    protected Recommendation recommendation;

    private List<List<SectionMenuItemBean>> sectionMenuItems;

    protected static final String CHECKBOX_CHECKED = "checked";

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * @see org.apache.wicket.Component#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
     */
    @Override
    public void renderHead(IHeaderResponse response) {
	super.renderHead(response);
	response.renderOnDomReadyJavaScript("setTimeout('calculateSaveIndicatorValue(\"saveIndicator\")', 60000);");
    }

    /**
     * For certain recommendation type display of hide sections drop down menus.
     */
    private void managedItemsView() {

	for(int i = 0; i < sectionMenus.size(); i++){
	    SectionDropdown sectionDropdown = sectionMenus.get(i);

	    if ((i + 1) == recommendation.getRecShtTyp()){
		sectionDropdown.setVisible(true);
	    }else{
		sectionDropdown.setVisible(false);
	    }
	}
    }

    /**
     * Constructs a new instance.
     * 
     * @param parameters
     */
    public AbstractRecSheetPage(PageParameters parameters) {
	super(parameters);

	if (getDailyRhythmPortalSession().getSectionMenuItems() == null){
	    sectionMenuItems = new ArrayList<List<SectionMenuItemBean>>();
	    for(int i = 0; i < 4; i++){
		sectionMenuItems.add(this.generateSectionMenus(i + 1));
	    }
	    getDailyRhythmPortalSession().setSectionMenuItems(sectionMenuItems);
	}else{
	    sectionMenuItems = getDailyRhythmPortalSession().getSectionMenuItems();
	}

	final AbstractDefaultAjaxBehavior nextButtonEnableBehavior = new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    /**
	     * @see org.apache.wicket.ajax.AbstractDefaultAjaxBehavior#respond(org.apache.wicket.ajax.AjaxRequestTarget)
	     */
	    @Override
	    protected void respond(AjaxRequestTarget target) {

		if (RequestCycle.get().getRequest().getRequestParameters().getParameterValue("saveIndicator") != null){
		    String saveIndicator = RequestCycle.get().getRequest().getRequestParameters().getParameterValue(
			    "saveIndicator").toString();

		    if (StringUtils.isNotEmpty(saveIndicator)){

			AbstractRecSheetPage.this.saveIndicator.setDefaultModel(new Model<String>(
				AbstractRecSheetPage.this.getSaveIndicatorMessage()));
			target.add(AbstractRecSheetPage.this.saveIndicator);

			return;
		    }

		}

		if (RequestCycle.get().getRequest().getRequestParameters().getParameterValue("selectType") != null){
		    String selectType = RequestCycle.get().getRequest().getRequestParameters().getParameterValue(
			    "selectType").toString();

		    if (StringUtils.isNotEmpty(selectType)){

			AbstractRecSheetPage.this.recommendation.assignRecSheetType(selectType);
			managedItemsView();

			target.add(refreshMenus);

			return;
		    }

		}

	    }

	    /**
	     * @see org.apache.wicket.ajax.AbstractDefaultAjaxBehavior#renderHead(org.apache.wicket.Component,
	     *      org.apache.wicket.markup.html.IHeaderResponse)
	     */
	    @Override
	    public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		StringBuilder onDomReadyJS = new StringBuilder();
		// enable/disable button
		onDomReadyJS.append("calculateSaveIndicatorValue = function(saveIndicator) { ");
		onDomReadyJS.append("wicketAjaxGet('");
		onDomReadyJS.append(this.getCallbackUrl());
		onDomReadyJS
			.append("&saveIndicator='+saveIndicator); setTimeout('calculateSaveIndicatorValue(\"saveIndicator\")', 60000); };");

		onDomReadyJS.append("assignRecType = function(stype) { ");
		onDomReadyJS.append("wicketAjaxGet('");
		onDomReadyJS.append(this.getCallbackUrl());
		onDomReadyJS.append("&selectType='+stype); };");

		onDomReadyJS.append("selectType = function(val) { ");
		onDomReadyJS.append("$('#pc').removeClass('selected'); ");
		onDomReadyJS.append("$('#mac').removeClass('selected'); ");
		onDomReadyJS.append("$('#tablet').removeClass('selected'); ");
		onDomReadyJS.append("$('#' + val).addClass('selected'); ");
		onDomReadyJS.append("assignRecType(val); };");

		onDomReadyJS.append("markSection = function(obj) { ");
		onDomReadyJS.append("alert('CP12'); ");
		onDomReadyJS.append("$(obj).addClass('full'); ");
		onDomReadyJS.append("};");

		onDomReadyJS
			.append("selectType('" + AbstractRecSheetPage.this.recommendation.getRecStringType() + "')");

		response.renderOnDomReadyJavaScript(onDomReadyJS.toString());

		LOGGER.debug("onDomReadyJS=" + onDomReadyJS);
	    }
	};
	add(nextButtonEnableBehavior);

	if (getDailyRhythmPortalSession().getRecommendation() != null){
	    recommendation = getDailyRhythmPortalSession().getRecommendation();
	}else{
	    recommendation = new Recommendation();
	    recommendation.setRecShtTyp(2);
	    getDailyRhythmPortalSession().setRecommendation(recommendation);
	}

	if (StringUtils.isEmpty(recommendation.getLanguage()) == true
		|| "US".equalsIgnoreCase(recommendation.getLanguage())){
	    this.getSession().setLocale(Locale.US);
	}else if ("es_PR".equalsIgnoreCase(recommendation.getLanguage())){
	    this.getSession().setLocale(es_PR);
	}else if ("es_US".equalsIgnoreCase(recommendation.getLanguage())){
	    this.getSession().setLocale(es_US);
	}

	//this.getSession().setLocale(es_US);

	markSections();

	if (form == null){
	    form = new Form("recSheetForm");
	    form.setMarkupId("recSheetForm");
	    form.setOutputMarkupId(true);

	    add(form);
	}

	transferToBeastImage = new Image("transfer-to-beast", new ContextRelativeResource(
		"/css/img/transfer-to-beast.png")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return recommendation.isTransferFlag();
	    }
	};
	transferToBeastImage.setOutputMarkupPlaceholderTag(true);
	form.add(transferToBeastImage);

	recSheetTypes = new WebMarkupContainer("recSheetTypes");
	recSheetTypes.setMarkupId("recSheetTypes");
	recSheetTypes.setOutputMarkupId(true);
	form.add(recSheetTypes);

	if (recommendation.getRecShtTyp() == 1){
	    recSheetTypes.setVisible(false);
	}else{
	    recSheetTypes.setVisible(true);
	}

	refreshMenus = new WebMarkupContainer("refreshMenus");
	refreshMenus.setMarkupId("refreshMenus");
	refreshMenus.setOutputMarkupId(true);
	form.add(refreshMenus);

	//sectionMenus
	for(int i = 0; i < 4; i++){
	    SectionDropdown sectionDropdown = new SectionDropdown("sectionMenus" + i, this.sectionMenuItems.get(i));
	    sectionDropdown.setMarkupId("sectionMenus" + i);
	    sectionDropdown.setOutputMarkupId(true);

	    refreshMenus.add(sectionDropdown);

	    sectionMenus.add(sectionDropdown);
	}

	managedItemsView();

	feedbackPanel = new FeedbackPanel("feedback");
	feedbackPanel.setMarkupId("feedback");
	feedbackPanel.setOutputMarkupId(true);

	form.add(feedbackPanel);

	// Add Notes Button
	addNotesButton = new AjaxLink("addNotesButton") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		if (!notePanel.isOpen()){
		    notePanel.setNoteText(AbstractRecSheetPage.this.recommendation.getNotes());
		    notePanel.open(target);
		}
	    }

	};
	addNotesButton.setMarkupId("addNotesButton");
	addNotesButton.setOutputMarkupPlaceholderTag(true);
	form.add(addNotesButton);

	// Save Recsheet
	saveRecsheetButton = new LoadingModalAjaxSubmitLink("saveRecsheetButton", form, new Model<String>("Saving ...")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		saveScreenValues(target);
		markSections();
		AbstractRecSheetPage.this.saveRecommendation(target);
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		target.add(feedbackPanel);
	    }

	};
	saveRecsheetButton.setMarkupId("saveRecsheetButton");
	saveRecsheetButton.setOutputMarkupPlaceholderTag(true);
	form.add(saveRecsheetButton);

	saveIndicator = new Label("saveIndicator", new Model<String>(this.getSaveIndicatorMessage()));
	saveIndicator.setOutputMarkupId(true);
	saveIndicator.setMarkupId("saveIndicator");
	saveRecsheetButton.add(saveIndicator);

	final ModalWindow printOrEmailModal = new ModalWindow("printOrEmailModal");
	printOrEmailModal.setHeightUnit("px");
	printOrEmailModal.setInitialHeight(Util.getInt(getString("printOrEmailPopup.height"), 220));
	printOrEmailModal.setWidthUnit("px");
	printOrEmailModal.setInitialWidth(Util.getInt(getString("printOrEmailPopup.width"), 440));
	printOrEmailModal.setResizable(false);
	printOrEmailModal.setWindowClosedCallback(new WindowClosedCallback() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		target.add(form);
	    }
	});
	form.add(printOrEmailModal);

	printOrEmailModal.setPageCreator(new ModalWindow.PageCreator() {

	    private static final long serialVersionUID = 1L;

	    public Page createPage() {
		return new PrintOrEmailDialog(recommendation);
	    }
	});

	printOrEmailModal.setCloseButtonCallback(new ModalWindow.CloseButtonCallback() {

	    private static final long serialVersionUID = 1L;

	    public boolean onCloseButtonClicked(AjaxRequestTarget target) {
		return true;
	    }
	});

	printRecsheetButton = new LoadingModalAjaxSubmitLink("printRecsheetButton", form, new ResourceModel(
		"loadingModalMessage.generatingPdf")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onSubmit(AjaxRequestTarget target, Form<?> form) {

		saveScreenValues(target);

		final PdfByteArrayResource pdfResource = new PdfByteArrayResource() {

		    private static final long serialVersionUID = 1L;

		    @Override
		    public String fetchData() {
			setPdfByteArray(null);

			try{
			    setPdfByteArray(mswService.getRecSheetPDF(recommendation));
			}catch(Exception e){
			    LOGGER.error("Error generating recommendation sheet PDF", e);
			}
			return null;
		    }

		};
		pdfResource.fetchData();

		String uidString = UUID.randomUUID().toString().replaceAll("-", "");
		ResourceReference rr = new ResourceReference(uidString) {

		    private static final long serialVersionUID = 1L;

		    @Override
		    public IResource getResource() {
			return pdfResource;

		    }
		};

		if (rr.canBeRegistered()){
		    getApplication().getResourceReferenceRegistry().registerResourceReference(rr);
		    target.appendJavaScript("window.open('" + urlFor(rr, null) + "')");
		}

	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		target.add(feedbackPanel);
	    }
	};

	printRecsheetButton.setMarkupId("printRecsheetButton");
	printRecsheetButton.setOutputMarkupPlaceholderTag(true);
	form.add(printRecsheetButton);

	// Mail Recsheet
	mailRecsheetButton = new AjaxSubmitLink("mailRecsheetButton", form) {

	    private static final long serialVersionUID = 1L;

	    /**
	     * @see org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink#onSubmit(org.apache.wicket.ajax.AjaxRequestTarget, org.apache.wicket.markup.html.form.Form)
	     */
	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		saveScreenValues(target);

		markSections();
		printOrEmailModal.show(target);
	    }

	    /**
	     * @see org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink#onError(org.apache.wicket.ajax.AjaxRequestTarget, org.apache.wicket.markup.html.form.Form)
	     */
	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		target.add(feedbackPanel);
	    }

	};
	mailRecsheetButton.setMarkupId("mailRecsheetButton");
	mailRecsheetButton.setOutputMarkupPlaceholderTag(true);
	form.add(mailRecsheetButton);

	// Search Button
	searchButton = new AjaxLink("searchButton") {

	    private static final long serialVersionUID = 1L;

	    /**
	     * @see org.apache.wicket.ajax.markup.html.AjaxLink#onClick(org.apache.wicket.ajax.AjaxRequestTarget)
	     */
	    @Override
	    public void onClick(AjaxRequestTarget target) {
		if (recommendation.getRecShtTyp() == 1){
		    setResponsePage(new MobileSalesWorkspacePage(getPageParameters()));
		}else{
		    setResponsePage(new CTRecSheetPage(getPageParameters()));
		}
	    }

	};
	searchButton.setMarkupId("searchButton");
	searchButton.setOutputMarkupPlaceholderTag(true);
	form.add(searchButton);

	// Clear Customer
	clearCustomerButton = new AjaxLink("clearCustomerButton") {

	    private static final long serialVersionUID = 1L;

	    /**
	     * @see org.apache.wicket.ajax.markup.html.AjaxLink#onClick(org.apache.wicket.ajax.AjaxRequestTarget)
	     */
	    @Override
	    public void onClick(AjaxRequestTarget target) {
		if (!clearCustomerData.isOpen()){
		    clearCustomerData.setQuestion(this.getString("clear.customer.data"));
		    clearCustomerData.open(target);
		}
	    }

	};
	clearCustomerButton.setMarkupId("clearCustomerButton");
	clearCustomerButton.setOutputMarkupPlaceholderTag(true);
	form.add(clearCustomerButton);

	// Clear Section
	clearSectionButton = new AjaxLink("clearSectionButton") {

	    private static final long serialVersionUID = 1L;

	    /**
	     * @see org.apache.wicket.ajax.markup.html.AjaxLink#onClick(org.apache.wicket.ajax.AjaxRequestTarget)
	     */
	    @Override
	    public void onClick(AjaxRequestTarget target) {
		if (!clearSection.isOpen()){
		    clearSection.setQuestion(this.getString("rec.sheet.clear.section"));
		    clearSection.open(target);
		}
	    }

	};
	clearSectionButton.setMarkupId("clearSectionButton");
	clearSectionButton.setOutputMarkupPlaceholderTag(true);
	form.add(clearSectionButton);

	clearSection = new OkCancelModalPanel("clearSection", "Clear", "Back") {

	    private static final long serialVersionUID = 1L;

	    /**
	     * @see com.bestbuy.bbym.ise.drp.common.ModalPanel#onClose(org.apache.wicket.ajax.AjaxRequestTarget)
	     */
	    @Override
	    public void onClose(AjaxRequestTarget target) {
		if (clearSection.isOk()){
		    recommendation.setTransferFlag(false);
		    clearSection(target);
		}
	    }
	};
	clearSection.setMarkupId("clearSection");
	clearSection.setOutputMarkupPlaceholderTag(true);
	add(clearSection);

	clearCustomerData = new OkCancelModalPanel("clearCustomerData", "Clear", "Back") {

	    private static final long serialVersionUID = 1L;

	    /**
	     * @see com.bestbuy.bbym.ise.drp.common.ModalPanel#onClose(org.apache.wicket.ajax.AjaxRequestTarget)
	     */
	    @Override
	    public void onClose(AjaxRequestTarget target) {
		if (clearCustomerData.isOk()){
		    clearCustomerData(target);
		}
	    }
	};
	clearCustomerData.setMarkupId("clearCustomerData");
	clearCustomerData.setOutputMarkupPlaceholderTag(true);
	add(clearCustomerData);

	notePanel = new NotePanel("notePanel") {

	    @Override
	    public void update(AjaxRequestTarget target) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		AbstractRecSheetPage.this.recommendation.setNotes(this.getNoteText());
	    }

	};

	notePanel.setMarkupId("notePanel");
	notePanel.setOutputMarkupPlaceholderTag(true);
	add(notePanel);

    }

    /**
     * Adds child component.
     * 
     * @param child
     * @return
     */
    public MarkupContainer addChild(final Component child) {
	if (form == null){
	    form = new Form("recSheetForm");
	    form.setMarkupId("recSheetForm");
	    form.setOutputMarkupId(true);

	    add(form);
	}
	form.add(child);
	return this;
    }

    /**
     * Implement that method to clear section.
     * 
     * @param target
     */
    abstract void clearSection(final AjaxRequestTarget target);

    /**
     * Clear Customer
     * 
     * @param target
     */
    public void clearCustomer(final AjaxRequestTarget target) {
	LOGGER.debug("Clear Customer .................");
    }

    /**
     * Saves recommendation.
     * 
     * @param target
     *            AjaxRequestTarget
     */
    public void saveRecommendation(final AjaxRequestTarget target) {
	try{
	    if (StringUtils.isEmpty(recommendation.getFirstName())){
		this.error(this.getString("mobileYouSectionForm.firstName.label") + " is required.");
	    }

	    if (StringUtils.isEmpty(recommendation.getLastName())){
		this.error(this.getString("mobileYouSectionForm.lastName.label") + " is required.");
	    }

	    if (StringUtils.isEmpty(recommendation.getMobileNo())){
		this.error(this.getString("mobileYouSectionForm.phone.label") + " is required.");
	    }

	    if (this.hasErrorMessage()){
		if (recommendation.getRecShtTyp() == 1){
		    setResponsePage(new MobileYouSectionPage(this.getPageParameters()));
		}else{
		    setResponsePage(new CTYouSectionPage(this.getPageParameters()));
		}
		return;
	    }

	    mswService.saveRecommendation(recommendation, getDailyRhythmPortalSession().getDrpUser());

	    Customer customer = getDailyRhythmPortalSession().getSearchCustomer();
	    if (customerService != null){
		customerService
			.addRecSheetSummary(customer, recommendation, getDailyRhythmPortalSession().getDrpUser());
	    }

	    getDailyRhythmPortalSession().getCustomer().setFirstName(
		    getDailyRhythmPortalSession().getSearchCustomer().getFirstName());
	    getDailyRhythmPortalSession().getCustomer().setLastName(
		    getDailyRhythmPortalSession().getSearchCustomer().getLastName());

	    recommendation.setWhenSaved(System.currentTimeMillis());

	    saveIndicator.setDefaultModel(new Model<String>(getSaveIndicatorMessage()));
	    target.add(saveIndicator);
	    target.add(transferToBeastImage);

	}catch(Exception exc){
	    LOGGER.error("unable to save recommendation", exc);
	    this.error(this.getString("rec.sheet.unable.to.save"));
	    target.add(feedbackPanel);
	    return;
	}
    }

    /**
     * Need to be overwrite in the wired cases. 
     * 
     * @param target
     */
    void saveScreenValues(final AjaxRequestTarget target) {

    }

    /**
     * Returns number of minutes since the user last saved the current rec sheet.
     * 
     * @return number of minutes since the user last saved the current rec sheet.
     */
    protected String getSaveIndicatorMessage() {

	if (recommendation.getWhenSaved() == null || recommendation.getWhenSaved() == 0){
	    return this.getString("rec.sheet.not.saved");
	}else{
	    String message = null;
	    int minutes = (int) ((System.currentTimeMillis() - recommendation.getWhenSaved()) / 60000);

	    if (minutes <= 1){
		message = "1 " + this.getString("rec.sheet.saved.minute.ago");
	    }else{
		message = minutes + " " + this.getString("rec.sheet.saved.minutes.ago");
	    }
	    return message;
	}

    }

    /**
     * Marks corresponding menu item if the data for these sections were populated.
     * 
     */
    void markSections() {
	for(List<SectionMenuItemBean> current: sectionMenuItems){
	    for(SectionMenuItemBean item: current){
		item.setSaved(item.getRecSheetsSections().dataWasEntered(recommendation));
	    }
	}
    }

    /**
     * Clear customer data ? This will apply to both the Dashboard and the
     * Recommendation Sheet.
     * 
     * @param target 
     *            AjaxRequestTarget
     */
    private void clearCustomerData(final AjaxRequestTarget target) {
	recommendation = new Recommendation();
	recommendation.setRecShtTyp(getDailyRhythmPortalSession().getRecommendation().getRecShtTyp());
	getDailyRhythmPortalSession().setRecommendation(recommendation);
	getDailyRhythmPortalSession().clearCarrierCustomer();
	getDailyRhythmPortalSession().clearBestBuyCustomer();
	getDailyRhythmPortalSession().clearSearchCustomer();
	getDailyRhythmPortalSession().setSessionPropertyBoolean("needCustomer", true);
	getDailyRhythmPortalSession().setPageError(null);

	sectionMenuItems = new ArrayList<List<SectionMenuItemBean>>();
	for(int i = 0; i < 4; i++){
	    sectionMenuItems.add(this.generateSectionMenus(i + 1));
	}
	getDailyRhythmPortalSession().setSectionMenuItems(sectionMenuItems);

	if (recommendation.getRecShtTyp() == 1){
	    setResponsePage(MobileYouSectionPage.class);
	}else{
	    setResponsePage(CTYouSectionPage.class);
	}
    }

    /**
     * Generated section menu list for each recommendation type.
     * 
     * @param recType  recommendation type id.
     * @return  list of section menu.
     */
    private List<SectionMenuItemBean> generateSectionMenus(int recType) {
	List<SectionMenuItemBean> items = new ArrayList<SectionMenuItemBean>();

	RecSheetsSections[] recSheetsSections = RecSheetsSections.values();

	int cnt = 0;

	for(RecSheetsSections current: recSheetsSections){
	    if ((current.isCtCommon() == true && recType > 1) || (current.getRecSheetType() == recType)){
		cnt++;
		SectionMenuItemBean item = new SectionMenuItemBean();
		item.setUid("" + recType + cnt);
		item.setLabel(this.getString(current.getSectionTitle()));
		item.setClazz(current.getClazz());
		item.setRecSheetsSections(current);

		items.add(item);

	    }

	}

	return items;
    }

    /**
     * Enable/disable link. 
     * 
     * @param link
     * @param enabled
     */
    public void setLinkVisible(AjaxLink link, boolean enabled) {
	link.setVisible(true);
    }

    /**
     * Represents sections dropdown menu.
     * 
     * @author a915722
     *
     */
    class SectionDropdown extends ListView<SectionMenuItemBean> {

	public SectionDropdown(String id, List<SectionMenuItemBean> model) {
	    super(id, model);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see org.apache.wicket.markup.html.list.ListView#populateItem(org.apache.wicket.markup.html.list.ListItem)
	 */
	@Override
	protected void populateItem(ListItem<SectionMenuItemBean> item) {
	    final SectionMenuItemBean menuItemBean = item.getModelObject();

	    AjaxSubmitLink link = new AjaxSubmitLink("menuItem", form) {

		@Override
		protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		    saveScreenValues(target);
		    markSections();
		    setResponsePage(menuItemBean.getClazz());
		}

		@Override
		protected void onError(AjaxRequestTarget target, Form<?> form) {
		    target.add(form);
		}
	    };
	    link.setOutputMarkupId(true);
	    if (menuItemBean.isSaved()){
		link.add(new AttributeAppender("class", new Model<String>("full")));
	    }
	    link.add(new Label("menuLabel", new Model<String>(menuItemBean.getLabel())));

	    item.add(link);

	}

    }

}
