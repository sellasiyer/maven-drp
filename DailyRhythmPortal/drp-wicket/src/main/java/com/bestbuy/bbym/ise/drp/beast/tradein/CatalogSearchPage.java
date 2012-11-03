package com.bestbuy.bbym.ise.drp.beast.tradein;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ContextRelativeResource;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.common.PageParameterKeys;
import com.bestbuy.bbym.ise.drp.domain.ui.UIComponent;
import com.bestbuy.bbym.ise.drp.domain.ui.UIDataItem;
import com.bestbuy.bbym.ise.drp.domain.ui.UIElement;
import com.bestbuy.bbym.ise.drp.domain.ui.UIImage;
import com.bestbuy.bbym.ise.drp.domain.ui.UILabel;
import com.bestbuy.bbym.ise.drp.domain.ui.UILink;
import com.bestbuy.bbym.ise.drp.domain.ui.UIList;
import com.bestbuy.bbym.ise.drp.domain.ui.UIListRow;
import com.bestbuy.bbym.ise.drp.domain.ui.UIReply;
import com.bestbuy.bbym.ise.drp.domain.ui.UIRequest;
import com.bestbuy.bbym.ise.drp.service.UIService;
import com.bestbuy.bbym.ise.drp.util.FormatPropertyModel;
import com.bestbuy.bbym.ise.drp.util.behavior.DefaultFocusBehavior;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.Util;

public class CatalogSearchPage extends BeastPage {

    private static Logger LOGGER = LoggerFactory.getLogger(CatalogSearchPage.class);
    private static final long serialVersionUID = 1L;

    @SpringBean(name = "uiService")
    private UIService uiService;

    private AbstractDefaultAjaxBehavior wicketBehavior, catalogRadioGroupOnClick;
    private String dataSharingKey;

    private String storeId;
    private String catalogSearch;
    private List<UIListRow> catalogRowList;
    private WebMarkupContainer catalogListContainer;
    private WebMarkupContainer noCatalogListContainer;
    private String noResultsMessage;
    private long selectedCatalogItemId = -1L;

    final TextField<String> catalogSearchTextField;

    public CatalogSearchPage(final PageParameters parameters) {
	super(parameters);

	getBorder().setPageTitle(new String(""));

	dataSharingKey = parameters.get(PageParameterKeys.DATA_SHARING_KEY.getUrlParameterKey()).toString();
	storeId = parameters.get(PageParameterKeys.STORE_ID.getUrlParameterKey()).toString();
	catalogSearch = parameters.get(PageParameterKeys.SEARCH_DESCRIPTION.getUrlParameterKey()).toString();
	LOGGER.debug("passed in DSK=" + dataSharingKey);

	getBorder().setButtonEnabled(nextButton, false);

	try{
	    catalogRowList = getSearchReply(createUIRequest());
	}catch(Exception exc){
	    throw new RuntimeException(exc);
	}
	// FIXME get search results from page params
	if (catalogRowList == null || catalogRowList.isEmpty()){
	    noResultsMessage = getString("CatalogSearch.noDataFound.label");
	}
	final String na = getString("notApplicable.label");

	catalogSearchTextField = new TextField<String>("catalogSearch",
		new PropertyModel<String>(this, "catalogSearch"));
	catalogSearchTextField.setMarkupId("catalogSearch");
	catalogSearchTextField.setOutputMarkupPlaceholderTag(true);
	catalogSearchTextField.add(new DefaultFocusBehavior());
	catalogSearchTextField.add(new OnChangeAjaxBehavior() {

	    private static final long serialVersionUID = 2462233190993745889L;

	    @Override
	    protected void onUpdate(final AjaxRequestTarget target) {

		final String valueAsString = ((TextField<String>) getComponent()).getModelObject();
		setCatalogSearch(valueAsString);
	    }
	});
	add(catalogSearchTextField);

	final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	add(feedbackPanel);

	catalogListContainer = new WebMarkupContainer("catalogListContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return catalogRowList != null && !catalogRowList.isEmpty();
	    }
	};
	catalogListContainer.setMarkupId("catalogListContainer");
	catalogListContainer.setOutputMarkupPlaceholderTag(true);
	add(catalogListContainer);

	final ListView<UIListRow> catalogListView = new ListView<UIListRow>("catalogListView",
		new PropertyModel<List<UIListRow>>(this, "catalogRowList")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void populateItem(final ListItem<UIListRow> listItem) {
		final UIListRow listRow = listItem.getModelObject();
		List<UIComponent> columnList = listRow.getColumnList();
		if (columnList == null){
		    return;
		}
		boolean imgUrlFlag = false;
		for(UIComponent columnComp: columnList){
		    listItem.add(AttributeModifier.replace("onclick", "selectFoundProduct('" + "catalogItemRadio"
			    + listItem.getIndex() + "', '" + catalogRadioGroupOnClick.getCallbackUrl() + "&id="
			    + listRow.getId() + "')"));

		    if (columnComp.getType() == UIComponent.Type.LABEL){
			UILabel uiLabel = (UILabel) columnComp;
			Label label = new Label("catalogItemName", new FormatPropertyModel<UILabel, String>(
				new PropertyModel<UILabel>(uiLabel, "value"), null, na));
			listItem.add(label);

		    }else if (columnComp.getType() == UIComponent.Type.IMAGE){
			UIImage uiImage = (UIImage) columnComp;
			Image img = new NonCachingImage("catalogItemImage");
			img.add(AttributeModifier.replace("src", uiImage.getUrl()));
			img.add(AttributeModifier.replace("width", "30"));

			listItem.add(img);
			imgUrlFlag = true;
		    }
		}

		if (!imgUrlFlag){
		    Image img = new NonCachingImage("catalogItemImage", new ContextRelativeResource(
			    "/images/titan_generic-product.png"));

		    img.add(AttributeModifier.replace("width", "30"));
		    listItem.add(img);
		    imgUrlFlag = false;

		}
		Radio<Object> radio = new Radio<Object>("catalogItemRadio") {

		    private static final long serialVersionUID = 1L;

		    @Override
		    protected void onComponentTag(final ComponentTag tag) {
			tag.put("onClick", "wicketAjaxGet('" + catalogRadioGroupOnClick.getCallbackUrl() + "&id="
				+ listRow.getId() + "');");
			tag.put("id", "catalogItemRadio" + listItem.getIndex());
		    }
		};

		listItem.add(radio);
	    }
	};
	catalogListView.setOutputMarkupPlaceholderTag(true);
	catalogListContainer.add(catalogListView);

	noCatalogListContainer = new WebMarkupContainer("noCatalogListContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return noResultsMessage != null;
	    }
	};
	noCatalogListContainer.setOutputMarkupPlaceholderTag(true);
	add(noCatalogListContainer);

	final Label noCatalogListLabel = new Label("noCatalogListLabel", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		return noResultsMessage;
	    }

	});
	noCatalogListLabel.setOutputMarkupPlaceholderTag(true);
	noCatalogListContainer.add(noCatalogListLabel);

	// search button
	AjaxButton searchButton = new AjaxButton("searchButton", new ResourceModel("searchButton.label"), this
		.getBorder().getForm()) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		LOGGER.debug("searchButton onSubmit");
		doSearch(target);
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		getBorder().getLoadingModalPanel().close(target);
		displayError(getString("CatalogSearchPage.service.down.message"), target);
		return;
	    }
	};
	searchButton.setMarkupId("searchButton");
	searchButton.setOutputMarkupPlaceholderTag(true);
	add(searchButton);

	// means by which JS can callback into Java
	wicketBehavior = new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
		try{
		    String id = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("id")
			    .toString();
		    LOGGER.debug("in wicketBehavior id=" + id);
		    if ("continue".equals(id)){
			doContinue(target);
		    }else if ("search".equals(id)){
			doSearch(target);
		    }else if ("message".equals(id)){
			doMessage(target);
		    }
		}catch(Exception exc){
		    LOGGER.error(ExceptionUtils.getStackTrace(exc));
		    displayError(getString("CatalogSearchPage.service.down.message"), target);
		    return;
		}
	    }
	};
	add(wicketBehavior);

	catalogRadioGroupOnClick = new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
		String id = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("id").toString();
		LOGGER.debug("onClick id=" + id);
		selectedCatalogItemId = Util.getLong(id, -1L);
		target.appendJavaScript("handleButtonState(true, '#continueButton');");

		getBorder().setButtonEnabled(nextButton, true, target);
	    }
	};
	add(catalogRadioGroupOnClick);

	add(new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
	    }

	    @Override
	    public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		StringBuilder onDomReadyJS = new StringBuilder();
		onDomReadyJS.append("handleButtonState(false, '#continueButton');");
		onDomReadyJS.append("wicketBehavior = function(id) { ");
		onDomReadyJS.append("wicketAjaxGet('");
		onDomReadyJS.append(wicketBehavior.getCallbackUrl());
		onDomReadyJS.append("&id='+id); ");
		onDomReadyJS.append("bindHotKeysNew()}");
		LOGGER.debug("onDomReadyJS=" + onDomReadyJS);
		response.renderOnDomReadyJavaScript(onDomReadyJS.toString());
	    }
	});
    }

    private void doSearch(AjaxRequestTarget target) {
	try{
	    noResultsMessage = null;
	    if (!getBorder().getLoadingModalPanel().isOpen()){
		getBorder().getLoadingModalPanel().setMessage(getString("CatalogSearch.searchCatalog.loading.label"));
		getBorder().getLoadingModalPanel().open(target);
		target.appendJavaScript("handleButtonState(false, '#searchButton');clickButton('#searchButton');");
		return;
	    }

	    UIRequest uiRequest = createUIRequest();

	    UIReply uiReply = null;
	    try{
		uiReply = uiService.processUIRequest(uiRequest);
		LOGGER.info("uiReply=" + uiReply);
		List<UIListRow> catalogRows = getSearchCatalogResults(uiReply);
		LOGGER.debug("# catalog items returned=" + catalogRows.size());
	    }catch(ServiceException se){
		catalogRowList = null;
		getBorder().getLoadingModalPanel().close(target);
		String message = "An unexpected exception occured while attempting to get catalog list";
		LOGGER.error(message, se);
		target.add(catalogListContainer);
		target.add(noCatalogListContainer);
		displayError(getString("CatalogSearchPage.service.down.message"), target);
		target.appendJavaScript("handleButtonState(true, '#searchButton');wicketBehavior('message');");
		return;
	    }catch(IseBusinessException be){
		catalogRowList = null;
		getBorder().getLoadingModalPanel().close(target);
		String message = "An unexpected exception occured while attempting to get catalog list";
		LOGGER.error(message, be);
		target.add(catalogListContainer);
		target.add(noCatalogListContainer);
		displayError(getString("CatalogSearchPage.service.down.message"), target);
		target.appendJavaScript("handleButtonState(true, '#searchButton');wicketBehavior('message');");
		return;
	    }
	    getBorder().getLoadingModalPanel().close(target);
	    target.appendJavaScript("handleButtonState(true, '#searchButton');");

	    if (catalogRowList.isEmpty()){
		noResultsMessage = getString("CatalogSearch.noDataFound.label");
	    }
	    LOGGER.debug("noResultsMessage=" + noResultsMessage);
	    target.add(catalogListContainer);
	    target.add(noCatalogListContainer);
	}catch(Exception exc){
	    catalogRowList.clear();

	    target.add(catalogListContainer);
	    target.add(noCatalogListContainer);
	    getBorder().getLoadingModalPanel().close(target);
	    target.appendJavaScript("handleButtonState(true, '#searchButton');wicketBehavior('message');");
	    LOGGER.error(ExceptionUtils.getStackTrace(exc));
	    displayError(getString("CatalogSearchPage.service.down.message"), target);
	    return;
	}
    }

    private void doContinue(AjaxRequestTarget target) throws ServiceException, IseBusinessException {

	LOGGER.info("selectedCatalogItemId=" + selectedCatalogItemId);
	UIRequest nextUIRequest = null;
	UILink selectedLink;
	findNextUIRequest:
	// find UILink that contains next UIRequest
	for(UIListRow catalogRow: catalogRowList){
	    if (catalogRow.getId() != null && catalogRow.getId().longValue() == selectedCatalogItemId){
		for(UIComponent columnComp: catalogRow.getColumnList()){
		    if (columnComp.getType() == UIComponent.Type.LINK){
			selectedLink = (UILink) columnComp;
			if (selectedLink != null && selectedLink.getUiRequest() != null){
			    nextUIRequest = selectedLink.getUiRequest();
			    break findNextUIRequest;
			}
		    }
		}
	    }
	}

	UIDataItem di = (UIDataItem) nextUIRequest.get("productDetailsLink");
	di.setStringProp("userId", getDailyRhythmPortalSession().getDrpUser().getUserId());
	LOGGER.info("nextUIRequest=" + nextUIRequest);

	UIReply uiReply = null;
	try{
	    uiReply = uiService.processUIRequest(nextUIRequest);
	    LOGGER.info("uiReply=" + uiReply);
	}catch(ServiceException se){

	    String message = "An unexpected exception occured while attempting to get product details";
	    LOGGER.error(message, se);
	    throw se;
	}catch(IseBusinessException be){

	    String message = "An unexpected exception occured while attempting to get product details";
	    LOGGER.error(message, be);
	    throw be;
	}

	setResponsePage(new ProductDetailsPage(this.getPageParameters(), uiReply));
    }

    private void doMessage(AjaxRequestTarget target) {
	if (!messageModalPanel.isOpen()){
	    messageModalPanel.open(target);
	}
    }

    public List<UIListRow> getCatalogRowList() {
	return catalogRowList;
    }

    public void setCatalogRowList(List<UIListRow> catalogRowList) {
	this.catalogRowList = catalogRowList;
    }

    public String getCatalogSearch() {
	return catalogSearch;
    }

    public void setCatalogSearch(String catalogSearch) {
	this.catalogSearch = catalogSearch;
    }

    private List<UIListRow> getSearchCatalogResults(UIReply uiReply) {
	List<UIListRow> catalogRows = null;
	if (uiReply != null && "CatalogResource".equals(uiReply.getName())){
	    UIElement listElem = uiReply.get("catalogList");
	    if (listElem != null && listElem.isComponent()){
		UIComponent listComp = (UIComponent) listElem;
		if (listComp.getType() == UIComponent.Type.LIST){
		    UIList list = (UIList) listComp;
		    catalogRows = list.getRowList();
		}
	    }
	}
	if (catalogRows == null){
	    catalogRows = new ArrayList<UIListRow>();
	}
	setCatalogRowList(catalogRows);
	return catalogRows;
    }

    private List<UIListRow> getSearchReply(UIRequest uiRequest) throws IseBusinessException, ServiceException {
	UIReply uiReply = null;
	List<UIListRow> catalogRows = new ArrayList<UIListRow>();
	try{
	    uiReply = uiService.processUIRequest(uiRequest);
	    LOGGER.info("uiReply=" + uiReply);

	    catalogRows = getSearchCatalogResults(uiReply);
	    LOGGER.debug("# catalog items returned=" + catalogRows.size());
	}catch(IseBusinessException ise){
	    String message = "An unexpected exception occured while attempting to get product details";
	    LOGGER.error(message, ise);
	    throw ise;
	}catch(ServiceException se){
	    String message = "An unexpected exception occured while attempting to get product details";
	    LOGGER.error(message, se);
	    throw se;
	}
	return catalogRows;
    }

    private UIRequest createUIRequest() {
	UIRequest uiRequest = new UIRequest();
	uiRequest.setName("CatalogSearch");
	UIDataItem searchParams = new UIDataItem();
	searchParams.setName("searchParams");
	searchParams.setStringProp("storeID", storeId);
	searchParams.setStringProp("userId", getDailyRhythmPortalSession().getDrpUser().getUserId());
	searchParams.setStringProp("searchFilter", catalogSearch);
	uiRequest.put(searchParams.getName(), searchParams);
	LOGGER.debug("doing catalog search searchParams=" + searchParams);

	return uiRequest;

    }

    @Override
    void clearForm(AjaxRequestTarget target) {
	try{
	    catalogSearch = "";

	    catalogSearchTextField.setDefaultModel(new PropertyModel<String>(this, "catalogSearch"));

	    if (target != null){
		target.add(catalogSearchTextField);
		target.appendJavaScript("bindHotKeys();");
	    }

	}catch(Exception exc){
	    LOGGER.error(ExceptionUtils.getStackTrace(exc));
	    displayError(getString("CatalogSearchPage.service.down.message"), target);
	    return;

	}
    }

    /**
     * @see com.bestbuy.bbym.ise.drp.beast.tradein.BeastPage#goBack(org.apache.wicket.ajax.AjaxRequestTarget)
     */
    @Override
    void goBack(AjaxRequestTarget target) {
	try{
	    this.setResponsePage(new TradeInTradabilityCheck(this.getPageParameters()));
	}catch(Exception exc){
	    LOGGER.error(exc.getMessage(), exc);
	    displayError(getString("CatalogSearchPage.service.down.message"), target);
	    return;
	}
    }

    @Override
    void nextPage(AjaxRequestTarget target, Form<?> form) {
	try{
	    doContinue(target);
	}catch(Exception exc){
	    displayError(getString("CatalogSearchPage.service.down.message"), target);
	    return;
	}
    }
}
