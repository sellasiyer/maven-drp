package com.bestbuy.bbym.ise.drp.rev;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.common.NewBaseNavPage;
import com.bestbuy.bbym.ise.drp.common.PdfByteArrayResource;
import com.bestbuy.bbym.ise.drp.common.RowIndexColumn;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.Manifest;
import com.bestbuy.bbym.ise.drp.domain.ManifestLine;
import com.bestbuy.bbym.ise.drp.service.ShippingService;
import com.bestbuy.bbym.ise.drp.util.FormatPropertyColumn;
import com.bestbuy.bbym.ise.drp.util.panel.SpanPanel;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.codec.GifImage;

@AuthorizeInstantiation( {DrpConstants.DRP_ADMIN, DrpConstants.SHP_USER, DrpConstants.SHP_MANAGER })
public class ManifestSummaryPage extends NewBaseNavPage {

    private static final long serialVersionUID = 7731744047988312157L;
    private static Logger logger = LoggerFactory.getLogger(ManifestSummaryPage.class);

    @SpringBean(name = "shippingService")
    private ShippingService shippingService;

    // Model Data
    private Manifest manifest;

    // Wicket Elements
    private FeedbackPanel feedbackPanel;
    private RowIndexColumn<ManifestLine> rowIndexColumn;
    private PdfByteArrayResource manifestDocResource, shippingLabelResource;

    public ManifestSummaryPage(Manifest inputManifest) {
	super(null);

	manifest = inputManifest;
	if (manifest == null){
	    manifest = new Manifest();
	}
	logger.debug("inputManifest=" + inputManifest);

	final String na = getString("notApplicable.label");

	feedbackPanel = new FeedbackPanel("feedback");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	add(feedbackPanel);

	final AjaxLink<Object> closeLink = new AjaxLink<Object>("closeLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.debug("closeLink onClick");
		setResponsePage(ShippingPage.class);
	    }
	};
	closeLink.setOutputMarkupPlaceholderTag(true);
	add(closeLink);

	manifestDocResource = new PdfByteArrayResource() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String fetchData() {
		setPdfByteArray(null);
		try{
		    DrpUser user = getDailyRhythmPortalSession().getDrpUser();
		    setPdfByteArray(shippingService.generateManifestDoc(manifest.getManifestID(), user));
		}catch(IseBusinessException be){
		    logger.error("IseBusinessException while getting the manifest doc");
		    return be.getMessage();
		}catch(ServiceException se){
		    logger.error("ServiceException while getting the manifest doc", se);
		    return se.getMessage();
		}
		if (getPdfByteArray() == null || getPdfByteArray().length == 0){
		    if (getPdfByteArray() == null){
			logger.warn("Manifest doc returned is null");
		    }else{
			logger.warn("Manifest doc returned is 0 bytes");
		    }
		    return getString("manifestSummary.badManifestDoc.message.label");
		}
		return null;
	    }
	};

	final AjaxLink<Object> printManifestLink = new AjaxLink<Object>("printManifestLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.debug("printManifestLink onClick");
		openLoadingModal(getString("manifestSummary.manifestDoc.loading.label"), target);
		launchAction("generateManifestDoc", target);
	    }

	};
	printManifestLink.setMarkupId("printManifestLink");
	printManifestLink.setOutputMarkupId(true);
	printManifestLink.setOutputMarkupPlaceholderTag(true);
	add(printManifestLink);

	shippingLabelResource = new PdfByteArrayResource() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String fetchData() {
		setPdfByteArray(null);
		byte[] bFile = null;
		try{
		    bFile = shippingService.getShippingImage(manifest.getManifestID());
		}catch(IseBusinessException be){
		    logger.error("IseBusinessException while getting the shipping image");
		    return be.getMessage();
		}catch(ServiceException se){
		    logger.error("ServiceException while getting the shipping image", se);
		    return se.getMessage();
		}
		if (bFile == null || bFile.length == 0){
		    if (bFile == null){
			logger.warn("Shipping image returned is null");
		    }else{
			logger.warn("Shipping image returned is 0 bytes");
		    }
		    return getString("manifestSummary.badShippingLabel.message.label");
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Document document = new Document();
		try{
		    PdfWriter.getInstance(document, baos);
		    document.open();
		    GifImage img = new GifImage(new ByteArrayInputStream(bFile));
		    img.getImage(1).scaleAbsolute(600.0f,
			    600.0f * img.getImage(1).getHeight() / img.getImage(1).getWidth());
		    document.add(img.getImage(1));
		}catch(DocumentException de){
		    logger.error("DocumentException while converting shipping image GIF file into PDF", de);
		    return getString("manifestSummary.failProcessShippingLabel.message.label");
		}catch(IOException ioe){
		    logger.error("IOException while converting shipping image GIF file into PDF", ioe);
		    return getString("manifestSummary.failProcessShippingLabel.message.label");
		}finally{
		    document.close();
		    try{
			baos.close();
		    }catch(IOException ioe){
		    }
		}
		setPdfByteArray(baos.toByteArray());
		return null;
	    }
	};

	final AjaxLink<Object> printShippingLabelLink = new AjaxLink<Object>("printShippingLabelLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.debug("printShippingLabelLink onClick");
		openLoadingModal(getString("manifestSummary.shippingLabel.loading.label"), target);
		launchAction("generateShippingLabel", target);
	    }

	};
	printShippingLabelLink.setMarkupId("printShippingLabelLink");
	printShippingLabelLink.setOutputMarkupId(true);
	printShippingLabelLink.setOutputMarkupPlaceholderTag(true);
	add(printShippingLabelLink);

	final Label manifestIdLabel = new Label("manifestIdLabel", new PropertyModel<String>(manifest, "manifestID"));
	manifestIdLabel.setOutputMarkupPlaceholderTag(true);
	add(manifestIdLabel);

	final Label dateCreatedLabel = new Label("dateCreatedLabel", new PropertyModel<Date>(manifest,
		"dateTimeCreated"));
	dateCreatedLabel.setOutputMarkupPlaceholderTag(true);
	add(dateCreatedLabel);

	final Label dateCompletedLabel = new Label("dateCompletedLabel", new PropertyModel<Date>(manifest,
		"dateCompleted"));
	dateCompletedLabel.setOutputMarkupPlaceholderTag(true);
	add(dateCompletedLabel);

	final Label manifestStatusLabel = new Label("manifestStatusLabel",
		new PropertyModel<String>(manifest, "status"));
	manifestStatusLabel.setOutputMarkupPlaceholderTag(true);
	add(manifestStatusLabel);

	final Label upsTrackingNumberLabel = new Label("upsTrackingNumberLabel", new PropertyModel<String>(manifest,
		"trackingIdentifier"));
	upsTrackingNumberLabel.setOutputMarkupPlaceholderTag(true);
	add(upsTrackingNumberLabel);

	final ManifestSummaryDataProvider manifestSummaryDataProvider = new ManifestSummaryDataProvider();

	if (manifest == null || manifest.getManifestLine() == null){
	    logger.debug("manifestlines=null");
	}else{
	    manifestSummaryDataProvider.setManifestLineList(manifest.getManifestLine());
	    logger.debug("manifestlines.size=" + manifest.getManifestLine().size());
	}

	final List<IColumn<ManifestLine>> columns = new ArrayList<IColumn<ManifestLine>>();
	rowIndexColumn = new RowIndexColumn<ManifestLine>(new ResourceModel("manifestSummaryPage.line.column"),
		"manifestLineID") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getCssClass() {
		return "rowIndex_col";
	    }
	};
	columns.add(rowIndexColumn);
	columns.add(new FormatPropertyColumn<ManifestLine, String>(new ResourceModel(
		"manifestSummaryPage.itemTag.column"), "itemTag", "itemTag", null, na) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getCssClass() {
		return "itemTag_col";
	    }
	});
	columns.add(new AbstractColumn<ManifestLine>(new ResourceModel("inventoryPanel.serialNum.column"), "imeiesn") {

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
	columns
		.add(new FormatPropertyColumn<ManifestLine, String>(new ResourceModel(
			"manifestSummaryPage.productDescription.column"), "productDescription", "productDescription",
			null, na));
	columns.add(new FormatPropertyColumn<ManifestLine, String>(new ResourceModel(
		"manifestSummaryPage.returnType.column"), "returnType", "returnType", null, na) {

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
	columns.add(new AbstractColumn<ManifestLine>(new ResourceModel("inventoryPanel.deviceStatus.column"),
		"deviceStatus") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getCssClass() {
		return "deviceStatus_col";
	    }

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
			if (row.getDeviceStatus() == null){
			    sb.append(na);
			}else{
			    sb.append(row.getDeviceStatus());
			}
			if (row.getTransferNumber() != null){
			    sb.append("<br/>");
			    sb.append(row.getTransferNumber());
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
	});

	final AjaxFallbackDefaultDataTable<ManifestLine> manifestSummaryTable = new AjaxFallbackDefaultDataTable<ManifestLine>(
		"manifestSummaryTable", columns, manifestSummaryDataProvider, 200);
	manifestSummaryTable.setMarkupId("manifestSummaryTable");
	manifestSummaryTable.setOutputMarkupId(true);
	manifestSummaryTable.setOutputMarkupPlaceholderTag(true);
	manifestSummaryTable.add(new AjaxEventBehavior("onclick") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onEvent(AjaxRequestTarget target) {
		rowIndexColumn.resetRowIndex();
	    }
	});
	add(manifestSummaryTable);
    }

    @Override
    protected String getOnDomReadyJS() {
	StringBuilder onDomReadyJS = new StringBuilder();
	rowIndexColumn.resetRowIndex();
	onDomReadyJS.append("setupManifestSummaryTable();");
	return onDomReadyJS.toString();
    }

    @Override
    protected void doAction(String action, AjaxRequestTarget target) {
	if ("generateManifestDoc".equals(action)){
	    String error = manifestDocResource.fetchData();
	    closeLoadingModal(target);
	    if (error != null){
		error(error);
		target.add(feedbackPanel);
		return;
	    }
	    String uuidString = UUID.randomUUID().toString().replaceAll("-", "");
	    ResourceReference ref = new ResourceReference(uuidString) {

		private static final long serialVersionUID = 1L;

		@Override
		public IResource getResource() {
		    return manifestDocResource;
		}
	    };
	    if (ref.canBeRegistered()){
		getApplication().getResourceReferenceRegistry().registerResourceReference(ref);
		target.appendJavaScript("window.open('" + urlFor(ref, null) + "')");
	    }

	}else if ("generateShippingLabel".equals(action)){
	    String error = shippingLabelResource.fetchData();
	    closeLoadingModal(target);
	    if (error != null){
		error(error);
		target.add(feedbackPanel);
		return;
	    }
	    String uuidString = UUID.randomUUID().toString().replaceAll("-", "");
	    ResourceReference ref = new ResourceReference(uuidString) {

		private static final long serialVersionUID = 1L;

		@Override
		public IResource getResource() {
		    return shippingLabelResource;
		}
	    };
	    if (ref.canBeRegistered()){
		getApplication().getResourceReferenceRegistry().registerResourceReference(ref);
		target.appendJavaScript("window.open('" + urlFor(ref, null) + "')");
	    }
	}
    }

}
