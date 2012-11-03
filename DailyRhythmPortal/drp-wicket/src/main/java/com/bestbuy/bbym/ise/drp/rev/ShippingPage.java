package com.bestbuy.bbym.ise.drp.rev;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.LoadingModalAjaxLink;
import com.bestbuy.bbym.ise.drp.common.NewBaseNavPage;
import com.bestbuy.bbym.ise.drp.domain.Manifest;
import com.bestbuy.bbym.ise.drp.domain.ManifestLine;
import com.bestbuy.bbym.ise.drp.domain.ManifestSearchCriteria;
import com.bestbuy.bbym.ise.drp.service.ShippingService;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.Util;

@AuthorizeInstantiation( {DrpConstants.DRP_ADMIN, DrpConstants.SHP_USER, DrpConstants.SHP_MANAGER })
public class ShippingPage extends NewBaseNavPage {

    public enum TabId {
	MANIFESTS, INVENTORY
    }

    private static Logger logger = LoggerFactory.getLogger(ShippingPage.class);
    private static final long serialVersionUID = 1L;

    @SpringBean(name = "shippingService")
    private ShippingService shippingService;

    // State Data
    private TabId selectedTab = TabId.MANIFESTS;
    private boolean canSeeInventoryTab = false;
    private Manifest openManifest;
    private ShippingParameters shippingParams;

    // Wicket Elements
    private FeedbackPanel feedbackPanel;
    private ResumeExistingManifestDialogPanel resumeExistingManifestDialog;
    private ManifestsPanel manifestsPanel;
    private InventoryPanel inventoryPanel;
    private ConfirmShrinkModalPanel confirmShrinkModalPanel;

    public ShippingPage(final PageParameters parameters) {
	super(parameters);
	final ShippingPage thisPage = this;

	feedbackPanel = new FeedbackPanel("feedback");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	add(feedbackPanel);

	resumeExistingManifestDialog = new ResumeExistingManifestDialogPanel("resumeExistingManifestDialog") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		if (isYesPressed()){
		    setResponsePage(new BuildManifestPage(cloneManifest(openManifest), shippingParams));
		}
	    }
	};
	resumeExistingManifestDialog.setOutputMarkupPlaceholderTag(true);
	add(resumeExistingManifestDialog);

	final AjaxLink<Object> printListLink = new AjaxLink<Object>("printListLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.debug("printListLink onClick");
		target.appendJavaScript("window.print();");
	    }

	    @Override
	    public boolean isVisible() {
		return canSeeInventoryTab && (selectedTab == TabId.INVENTORY);
	    }
	};
	printListLink.setMarkupId("printListLink");
	printListLink.setOutputMarkupId(true);
	printListLink.setOutputMarkupPlaceholderTag(true);
	add(printListLink);

	final LoadingModalAjaxLink createNewManifestLink = new LoadingModalAjaxLink("createNewManifestLink", null,
		new ResourceModel("loadingModalMessage.shippingPage.openManifestCheck")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		ManifestSearchCriteria msc = new ManifestSearchCriteria();
		msc.setMostRecentNumber(new Integer(shippingParams.getNumMostRecentManifests()));
		DailyRhythmPortalSession session = getDailyRhythmPortalSession();
		if (session.getDrpUser() != null){
		    msc.setStoreID(new BigInteger(session.getDrpUser().getStoreId()));
		}
		logger.debug("storeID=" + msc.getStoreID());
		openManifest = null;
		try{
		    List<Manifest> manifestList = shippingService.searchManifests(msc, session.getDrpUser());
		    if (manifestList != null && !manifestList.isEmpty()){
			for(Manifest mf: manifestList){
			    if (mf.getTrackingIdentifier() == null){
				openManifest = mf;
				break;
			    }
			}
		    }
		}catch(ServiceException se){
		    logger.error("Failed to find open manifest using ShippingService", se);
		    error(se.getMessage());
		    target.add(feedbackPanel);
		}catch(IseBusinessException be){
		    logger.error("Failed to find open manifest using ShippingService");
		    error(be.getMessage());
		    target.add(feedbackPanel);
		}
		if (thisPage.hasErrorMessage()){
		    return;
		}
		if (openManifest != null){
		    resumeExistingManifestDialog.open(target);
		}else{
		    // Build new manifest
		    setResponsePage(new BuildManifestPage(cloneManifest(null), shippingParams));
		}
	    }

	};
	createNewManifestLink.setMarkupId("createNewManifestLink");
	createNewManifestLink.setOutputMarkupId(true);
	createNewManifestLink.setOutputMarkupPlaceholderTag(true);
	add(createNewManifestLink);

	final List<ITab> tabs = new ArrayList<ITab>();
	tabs.add(new AbstractTab(new Model<String>("Manifests")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public Panel getPanel(String panelId) {
		selectedTab = TabId.MANIFESTS;
		manifestsPanel = new ManifestsPanel(panelId, feedbackPanel, shippingParams);
		launchAction(manifestsPanel.getLaunchAction());
		return manifestsPanel;
	    }
	});
	if (getDailyRhythmPortalSession().isAuthorizedToInstantiate(InventoryPanel.class)){
	    canSeeInventoryTab = true;
	    tabs.add(new AbstractTab(new Model<String>("Inventory Management")) {

		private static final long serialVersionUID = 1L;

		@Override
		public Panel getPanel(String panelId) {
		    selectedTab = TabId.INVENTORY;
		    inventoryPanel = new InventoryPanel(panelId, feedbackPanel, shippingParams, confirmShrinkModalPanel);
		    launchAction(inventoryPanel.getLaunchAction());
		    return inventoryPanel;
		}
	    });
	}

	final AjaxTabbedPanel<ITab> shippingTabPanel = new AjaxTabbedPanel<ITab>("shippingTabPanel", tabs) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onAjaxUpdate(AjaxRequestTarget target) {
		super.onAjaxUpdate(target);
		target.add(printListLink);
		String onAjaxUpdateTabsJS = getOnDomReadyJS();
		logger.debug("onAjaxUpdateTabsJS=" + onAjaxUpdateTabsJS);
		target.appendJavaScript(onAjaxUpdateTabsJS);
		if (selectedTab == TabId.MANIFESTS && manifestsPanel != null){
		    launchAction(manifestsPanel.getLaunchAction(), target);
		}else if (selectedTab == TabId.INVENTORY && inventoryPanel != null){
		    launchAction(inventoryPanel.getLaunchAction(), target);
		}
	    }
	};
	shippingTabPanel.setMarkupId("shippingTabPanel");
	shippingTabPanel.setOutputMarkupId(true);
	shippingTabPanel.setSelectedTab(0);
	add(shippingTabPanel);

	confirmShrinkModalPanel = new ConfirmShrinkModalPanel("confirmShrinkModalPanel", getString("noLabel"),
		getString("yesLabel")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		if (isYesSelected()){
		    openLoadingModal(getString("inventoryPanel.shrinkDevice.loading.label"), target);
		    launchAction("shrinkDevice", target);
		}
	    }
	};
	add(confirmShrinkModalPanel);
    }

    public static Manifest cloneManifest(final Manifest manifest) {
	Manifest mf;
	if (manifest == null){
	    mf = new Manifest();
	}else{
	    mf = (Manifest) Util.clone(manifest);
	}
	if (mf.getManifestLine() == null){
	    mf.setManifestLine(new ArrayList<ManifestLine>());
	}
	return mf;
    }

    @Override
    protected String getOnDomReadyJS() {
	StringBuilder onDomReadyJS = new StringBuilder();
	onDomReadyJS.append("applyTabStyles('#shippingTabPanel');");
	if (selectedTab == TabId.MANIFESTS && manifestsPanel != null){
	    onDomReadyJS.append(manifestsPanel.getOnDomReadyJS());
	}else if (selectedTab == TabId.INVENTORY && inventoryPanel != null){
	    onDomReadyJS.append(inventoryPanel.getOnDomReadyJS());
	}
	return onDomReadyJS.toString();
    }

    @Override
    protected void doAction(String action, AjaxRequestTarget target) {
	if (selectedTab == TabId.MANIFESTS && manifestsPanel != null && manifestsPanel.canHandleAction(action)){
	    if (shippingParams == null){
		launchAction("getShippingParams" + action, target);
		return;
	    }
	    if (manifestsPanel.getShippingParams() == null){
		manifestsPanel.setShippingParams(shippingParams);
	    }
	    manifestsPanel.doAction(action, target);

	    // shippingParams must be set before opening inventory panel
	}else if (selectedTab == TabId.INVENTORY && inventoryPanel != null && inventoryPanel.canHandleAction(action)){
	    inventoryPanel.doAction(action, target);

	}else if (selectedTab == TabId.INVENTORY && inventoryPanel != null && "shrinkDevice".equals(action)){
	    try{
		shippingService.shrinkDevice(confirmShrinkModalPanel.getItemTag(), shippingParams
			.getStoreShrinkStatus(), shippingParams.getShrinkDispositionType());
		// Refresh page
		target.appendJavaScript(getOnDomReadyJS());
		launchAction(inventoryPanel.getLaunchAction(), target);
	    }catch(ServiceException se){
		logger.error("Failed to shrink device using ShippingService", se);
		error(se.getMessage());
		target.add(feedbackPanel);
	    }catch(IseBusinessException be){
		logger.error("Failed to shrink device using ShippingService");
		error(be.getMessage());
		target.add(feedbackPanel);
	    }finally{
		closeLoadingModal(target);
	    }

	}else if (action != null && action.startsWith("getShippingParams")){
	    if (!isLoadingModalOpen()){
		openLoadingModal(getString("shippingPage.getParams.loading.label"), target);
		launchAction(action, target);
		return;
	    }
	    try{
		shippingParams = new ShippingParameters();
		shippingParams.retrieveParameters(shippingService);
	    }catch(IseBusinessException be){
		processException(getString("shippingParams.getData.message.label"), be);
	    }catch(ServiceException se){
		processException(getString("shippingParams.getData.message.label"), se);
	    }finally{
		closeLoadingModal(target);
	    }
	    String panelAction = action.substring("getShippingParams".length());
	    if (StringUtils.isNotBlank(panelAction)){
		doAction(panelAction, target);
	    }
	}
    }
}
