package com.bestbuy.bbym.ise.drp.rev;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.AjaxLinkPanel;
import com.bestbuy.bbym.ise.drp.common.NewBaseNavPage;
import com.bestbuy.bbym.ise.drp.common.OkCancelDialogPanel;
import com.bestbuy.bbym.ise.drp.common.OkDialogPanel;
import com.bestbuy.bbym.ise.drp.common.RowIndexColumn;
import com.bestbuy.bbym.ise.drp.domain.Manifest;
import com.bestbuy.bbym.ise.drp.domain.ManifestLine;
import com.bestbuy.bbym.ise.drp.domain.ManifestSearchCriteria;
import com.bestbuy.bbym.ise.drp.service.ShippingService;
import com.bestbuy.bbym.ise.drp.util.FormatPropertyColumn;
import com.bestbuy.bbym.ise.drp.util.panel.SpanPanel;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.Util;

@AuthorizeInstantiation( {DrpConstants.DRP_ADMIN, DrpConstants.SHP_MANAGER, DrpConstants.SHP_USER })
public class BuildManifestPage extends NewBaseNavPage {

    private static Logger logger = LoggerFactory.getLogger(BuildManifestPage.class);
    private static final long serialVersionUID = 1L;

    @SpringBean(name = "shippingService")
    private ShippingService shippingService;

    // Model Data
    private BuildManifestDataProvider dataProvider = new BuildManifestDataProvider();
    private Manifest manifest;

    // State Data
    private Manifest returnManifest;
    private String itemTagId = "";
    private int maxManifestLines = 10;
    private ManifestLine addManifestLine, removeManifestLine;

    // Wicket Elements
    private TextField<String> itemTagField;
    private RowIndexColumn<ManifestLine> rowIndexColumn;
    private OkDialogPanel maxDeviceReachedDialog;
    private OkCancelDialogPanel maxDeviceDialog;
    private OkDialogPanel errorDialog;
    private DeleteLineDialogPanel deleteLineDialog;
    private AjaxFallbackDefaultDataTable<ManifestLine> dataTable;
    private Label lineItemsRemainingLabel;

    // FIXME Hack for getting buttons to work properly
    private boolean refreshPage = true;

    public BuildManifestPage(Manifest inputManifest, ShippingParameters shippingParams) {
	super(null);

	manifest = inputManifest;
	if (manifest == null){
	    manifest = new Manifest();
	}
	dataProvider.setManifestLineList(manifest.getManifestLine());
	logger.trace("inputManifest=" + inputManifest);

	if (shippingParams != null){
	    maxManifestLines = shippingParams.getMaxNumItemsPerManifest();
	}

	final String na = getString("notApplicable.label");

	final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	add(feedbackPanel);

	rowIndexColumn = new RowIndexColumn<ManifestLine>(new ResourceModel("buildManifest.line.column"),
		"manifestLineID") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getCssClass() {
		return "rowIndex_col";
	    }
	};

	maxDeviceReachedDialog = new OkDialogPanel("maxDeviceReachedDialog", getString("okLabel")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		target.appendJavaScript("$('#itemTagId').focus();");
	    }
	};
	maxDeviceReachedDialog.setOutputMarkupId(true);
	maxDeviceReachedDialog.setMessage(getString("maxDeviceReachedDialog.message.label"));
	add(maxDeviceReachedDialog);

	maxDeviceDialog = new OkCancelDialogPanel("maxDeviceDialog", getString("yesLabel"), getString("noLabel")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		if (isOk()){
		    openLoadingModal(getString("buildManifest.complete.loading.label"), target);
		    launchAction("complete", target);
		    return;
		}
		target.appendJavaScript("$('#itemTagId').focus();");
	    }
	};
	maxDeviceDialog.setOutputMarkupId(true);
	maxDeviceDialog.setMessage(getString("maxDeviceDialog.message.label"));
	maxDeviceDialog.setQuestion(getString("maxDeviceDialog.question.label"));
	add(maxDeviceDialog);

	errorDialog = new OkDialogPanel("errorDialog", getString("okLabel")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		target.appendJavaScript("$('#itemTagId').focus();");
	    }

	};
	errorDialog.setOutputMarkupId(true);
	add(errorDialog);

	deleteLineDialog = new DeleteLineDialogPanel("deleteLineDialog") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		if (!isDelete()){
		    target.appendJavaScript("$('#itemTagId').focus();");
		    return;
		}
		removeManifestLine = getManifestLine();
		openLoadingModal(getString("buildManifest.removeLine.loading.label"), target);
		launchAction("remove", target);
	    }
	};
	deleteLineDialog.setOutputMarkupId(true);
	add(deleteLineDialog);

	final AjaxLink<Object> suspendLink = new AjaxLink<Object>("suspendLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.debug("suspendLink onClick");
		setResponsePage(ShippingPage.class);
	    }
	};
	suspendLink.setMarkupId("suspendLink");
	suspendLink.setOutputMarkupId(true);
	suspendLink.setOutputMarkupPlaceholderTag(true);
	add(suspendLink);

	final AjaxLink<Object> completeLink = new AjaxLink<Object>("completeLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.debug("completeLink onClick");
		openLoadingModal(getString("buildManifest.complete.loading.label"), target);
		launchAction("complete", target);
	    }

	};
	completeLink.setMarkupId("completeLink");
	completeLink.setOutputMarkupId(true);
	completeLink.setOutputMarkupPlaceholderTag(true);
	add(completeLink);

	Form<Object> manifestForm = new Form<Object>("manifestForm");
	add(manifestForm);

	itemTagField = new TextField<String>("itemTagId", new PropertyModel<String>(this, "itemTagId"));
	itemTagField.setMarkupId("itemTagId");
	itemTagField.setOutputMarkupId(true);
	itemTagField.setOutputMarkupPlaceholderTag(true);
	manifestForm.add(itemTagField);

	AjaxButton itemAddButton = new AjaxButton("itemAddButton", new ResourceModel(
		"buildManifest.addManifestLine.button")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.debug("itemAddButton onSubmit");
		if (StringUtils.isBlank(getItemTagId())){
		    itemTagId = "";
		    target.add(itemTagField);
		    target.appendJavaScript("$('#itemTagId').focus();");
		    errorDialog.setMessage(getString("buildManifest.itemTagRequired.label"));
		    errorDialog.open(target);
		    return;
		}
		Pattern pattern = Pattern.compile("[A-Za-z0-9_-]+");
		Matcher matcher = pattern.matcher(getItemTagId());
		if (!matcher.matches()){
		    itemTagId = "";
		    target.add(itemTagField);
		    target.appendJavaScript("$('#itemTagId').focus();");
		    errorDialog.setMessage(getString("buildManifest.itemTagInvalid.label"));
		    errorDialog.open(target);
		    return;
		}

		logger.debug("adding manifest line itemTagId=" + getItemTagId());

		if (dataProvider.size() >= maxManifestLines){
		    maxDeviceReachedDialog.open(target);
		    return;
		}

		addManifestLine = new ManifestLine();
		addManifestLine.setItemTag(getItemTagId());
		addManifestLine.setManifestID(manifest.getManifestID());
		openLoadingModal(getString("buildManifest.addLine.loading.label"), target);
		launchAction("add", target);
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
	    }
	};
	itemAddButton.setMarkupId("itemAddButton");
	itemAddButton.setOutputMarkupId(true);
	itemAddButton.setOutputMarkupPlaceholderTag(true);
	manifestForm.add(itemAddButton);
	manifestForm.setDefaultButton(itemAddButton);

	lineItemsRemainingLabel = new Label("lineItemsRemainingLabel", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		return String.valueOf(maxManifestLines - dataProvider.size());
	    }

	});
	lineItemsRemainingLabel.setOutputMarkupPlaceholderTag(true);
	add(lineItemsRemainingLabel);

	final List<IColumn<ManifestLine>> columns = new ArrayList<IColumn<ManifestLine>>();
	columns.add(rowIndexColumn);
	columns.add(new FormatPropertyColumn<ManifestLine, String>(new ResourceModel("buildManifest.itemTag.column"),
		"itemTag", "itemTag", null, na) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getCssClass() {
		return "itemTag_col";
	    }
	});
	columns.add(new AbstractColumn<ManifestLine>(new ResourceModel("buildManifest.serialNum.column"), "imeiesn") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void populateItem(Item<ICellPopulator<ManifestLine>> cellItem, String componentId,
		    IModel<ManifestLine> rowModel) {
		final ManifestLine row = (ManifestLine) rowModel.getObject();
		cellItem.add(new SpanPanel<ManifestLine>(componentId, rowModel) {

		    private static final long serialVersionUID = 1L;

		    @Override
		    public String generateLabelString() {
			StringBuilder sb = new StringBuilder();
			sb.append("<span>");
			if (row.getImeiesn() == null){
			    sb.append(na);
			}else{
			    sb.append(row.getImeiesn());
			}
			if (row.getSku() != null){
			    sb.append("<br/>");
			    sb.append(row.getSku());
			}
			sb.append("</span>");
			return sb.toString();
		    }
		});
	    }

	    @Override
	    public Component getHeader(String componentId) {
		Label label = new Label(componentId, getDisplayModel());
		label.setEscapeModelStrings(false);
		return label;
	    }

	    @Override
	    public String getCssClass() {
		return "serialNum_col";
	    }
	});
	columns.add(new FormatPropertyColumn<ManifestLine, String>(new ResourceModel(
		"buildManifest.productDescription.column"), "productDescription", "productDescription", null, na));
	columns.add(new FormatPropertyColumn<ManifestLine, String>(
		new ResourceModel("buildManifest.returnType.column"), "returnType", "returnType", null, na) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public Component getHeader(String componentId) {
		Label label = new Label(componentId, getDisplayModel());
		label.setEscapeModelStrings(false);
		return label;
	    }

	    @Override
	    public String getCssClass() {
		return "returnType_col";
	    }
	});
	columns.add(new AbstractColumn<ManifestLine>(null, "manifestID") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void populateItem(Item<ICellPopulator<ManifestLine>> cellItem, String componentId,
		    final IModel<ManifestLine> rowModel) {
		AjaxLinkPanel removeLinkPanel = new AjaxLinkPanel(componentId,
			getString("buildManifest.removeManifestLineLink.label")) {

		    private static final long serialVersionUID = 1L;

		    @Override
		    public void onClick(AjaxRequestTarget target) {
			ManifestLine deleteLine = (ManifestLine) Util.clone(rowModel.getObject());
			logger.debug("deleteLine=" + deleteLine);
			deleteLineDialog.setDeleteItemId(target.getLastFocusedElementId());
			deleteLineDialog.setManifestLine(deleteLine);
			deleteLineDialog.open(target);
		    }
		};
		cellItem.add(removeLinkPanel);
	    }

	    @Override
	    public String getCssClass() {
		return "remove_col";
	    }
	});

	dataTable = new AjaxFallbackDefaultDataTable<ManifestLine>("buildManifestTable", columns, dataProvider,
		maxManifestLines);
	dataTable.setMarkupId("buildManifestTable");
	dataTable.setOutputMarkupId(true);
	dataTable.setOutputMarkupPlaceholderTag(true);
	dataTable.add(new AjaxEventBehavior("onclick") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onEvent(AjaxRequestTarget target) {
		rowIndexColumn.resetRowIndex();
	    }
	});
	add(dataTable);
    }

    public String getItemTagId() {
	return itemTagId;
    }

    public void setItemTagId(String itemTagId) {
	this.itemTagId = itemTagId;
    }

    private String completeManifest() {
	DailyRhythmPortalSession session = getDailyRhythmPortalSession();
	try{
	    logger.debug("completing manifest " + manifest.getManifestID());
	    shippingService.getShipManifestInfo(manifest.getManifestID(), session.getDrpUser());

	    // Retrieve manifest to get completed date and status fields
	    ManifestSearchCriteria msc = new ManifestSearchCriteria();
	    msc.setManifestID(manifest.getManifestID());
	    List<Manifest> list = shippingService.searchManifests(msc, session.getDrpUser());
	    if (list == null || list.size() != 1){
		throw new IseBusinessException("Failed to retrieve Manifest details");
	    }
	    returnManifest = list.get(0);
	    logger.debug("returnManifest=" + returnManifest);
	    if (returnManifest == null || returnManifest.getTrackingIdentifier() == null
		    || returnManifest.getStatus() == null){
		throw new IseBusinessException("Invalid Manifest returned");
	    }
	    manifest.setTrackingIdentifier(returnManifest.getTrackingIdentifier());
	    manifest.setStatus(returnManifest.getStatus());
	    return null;
	}catch(ServiceException se){
	    logger.error("ServiceException completing manifest", se);
	    return se.getMessage();
	}catch(IseBusinessException be){
	    logger.error("IseBusinessException completing manifest");
	    return be.getMessage();
	}
    }

    private String addManifestLine() {
	DailyRhythmPortalSession session = getDailyRhythmPortalSession();
	try{
	    logger.debug("dataProvider.size1=" + dataProvider.size());
	    logger.debug("addManifestLine=" + addManifestLine);
	    ManifestLine line = shippingService.addManifestLine(addManifestLine, session.getDrpUser());
	    logger.debug("dataProvider.size2=" + dataProvider.size());
	    if (manifest.getManifestID() == null){
		manifest.setManifestID(line.getManifestID());
	    }
	    ManifestLine ml = (ManifestLine) Util.clone(line);
	    logger.debug("returnedManifestLine=" + ml);
	    dataProvider.addManifestLine(ml);
	    dataTable.modelChanged();
	    logger.debug("dataProvider.size3=" + dataProvider.size());
	    return null;
	}catch(ServiceException se){
	    logger.error("ServiceException adding line to manifest", se);
	    return se.getMessage();
	}catch(IseBusinessException be){
	    logger.error("IseBusinessException adding line to manifest");
	    return be.getMessage();
	}
    }

    private String removeManifestLine() {
	DailyRhythmPortalSession session = getDailyRhythmPortalSession();
	try{
	    logger.debug("dataProvider.size1=" + dataProvider.size());
	    logger.debug("removeManifestLine=" + removeManifestLine);
	    List<ManifestLine> linesToRemove = new ArrayList<ManifestLine>();
	    linesToRemove.add(removeManifestLine);

	    if (shippingService.removeManifestLine(linesToRemove, session.getDrpUser())){
		logger.debug("dataProvider.size2=" + dataProvider.size());
		dataProvider.removeManifestLine(removeManifestLine.getManifestLineID());
	    }else{
		return getString("buildManifest.lineRemoveError.label");
	    }
	    // Removed last line so reset manifest ID to null
	    if (dataProvider.size() == 0){
		manifest.setManifestID(null);
	    }
	    dataTable.modelChanged();
	    logger.debug("dataProvider.size3=" + dataProvider.size());
	    return null;
	}catch(ServiceException se){
	    logger.error("ServiceException removing line from manifest", se);
	    return se.getMessage();
	}catch(IseBusinessException be){
	    logger.error("IseBusinessException removing line from manifest");
	    return be.getMessage();
	}
    }

    @Override
    protected String getOnDomReadyJS() {
	rowIndexColumn.resetRowIndex();
	if (refreshPage){
	    logger.debug("CALLING refreshPage()");
	    refreshPage = false;
	    return "refreshPage();";
	}
	StringBuilder onDomReadyJS = new StringBuilder();
	if (dataProvider.size() == 0){
	    onDomReadyJS.append("handleLinkState(false, '#completeLink');");
	}else{
	    onDomReadyJS.append("handleLinkState(true, '#completeLink');");
	}
	onDomReadyJS.append("tableNoRecordsText='" + getString("buildManifest.noRecords.label") + "';");
	onDomReadyJS.append("setupBuildManifestTable();");
	onDomReadyJS.append("$('#itemTagId').focus();");
	return onDomReadyJS.toString();
    }

    @Override
    protected void doAction(String action, AjaxRequestTarget target) {
	String followUpAction = null;
	StringBuilder respondJS = new StringBuilder();
	if ("add".equals(action)){
	    String error = addManifestLine();
	    this.closeLoadingModal(target);
	    if (error != null){
		itemTagId = "";
		target.add(itemTagField);
		respondJS.append("$('#itemTagId').focus();");
		logger.debug("respondJS=" + respondJS.toString());
		target.appendJavaScript(respondJS.toString());
		errorDialog.setMessage(error);
		errorDialog.open(target);
		return;
	    }
	    rowIndexColumn.resetRowIndex();
	    target.add(dataTable);
	    target.add(lineItemsRemainingLabel);
	    itemTagId = "";
	    target.add(itemTagField);
	    respondJS.append("setupBuildManifestTable();");
	    if (dataProvider.size() >= maxManifestLines){
		followUpAction = "maxReached";
	    }else{
		respondJS.append("$('#itemTagId').focus();");
	    }
	    if (dataProvider.size() == 0){
		respondJS.append("handleLinkState(false, '#completeLink');");
	    }else{
		respondJS.append("handleLinkState(true, '#completeLink');");
	    }
	    logger.debug("respondJS=" + respondJS.toString());
	    target.appendJavaScript(respondJS.toString());

	}else if ("remove".equals(action)){
	    String error = removeManifestLine();
	    this.closeLoadingModal(target);
	    if (error != null){
		respondJS.append("$('#itemTagId').focus();");
		logger.debug("respondJS=" + respondJS.toString());
		target.appendJavaScript(respondJS.toString());
		errorDialog.setMessage(error);
		errorDialog.open(target);
		return;
	    }
	    rowIndexColumn.resetRowIndex();
	    target.add(dataTable);
	    target.add(lineItemsRemainingLabel);
	    respondJS.append("setupBuildManifestTable();");
	    respondJS.append("$('#itemTagId').focus();");
	    if (dataProvider.size() == 0){
		respondJS.append("handleLinkState(false, '#completeLink');");
	    }else{
		respondJS.append("handleLinkState(true, '#completeLink');");
	    }
	    logger.debug("respondJS=" + respondJS.toString());
	    target.appendJavaScript(respondJS.toString());

	}else if ("maxReached".equals(action)){
	    maxDeviceDialog.open(target);

	}else if ("complete".equals(action)){
	    String error = completeManifest();
	    this.closeLoadingModal(target);
	    if (error != null){
		errorDialog.setMessage(error);
		errorDialog.open(target);
		return;
	    }
	    setResponsePage(new ManifestSummaryPage(returnManifest));
	    return;
	}
	launchAction(followUpAction, target);
    }
}
