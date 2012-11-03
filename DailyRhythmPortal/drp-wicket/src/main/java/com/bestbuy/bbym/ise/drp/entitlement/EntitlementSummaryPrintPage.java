package com.bestbuy.bbym.ise.drp.entitlement;

import java.util.Date;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.common.BaseWebPage;
import com.bestbuy.bbym.ise.drp.domain.Entitlement;
import com.bestbuy.bbym.ise.drp.domain.EntitlementLookup;
import com.bestbuy.bbym.ise.drp.domain.EntitlementPurchaseType;
import com.bestbuy.bbym.ise.drp.domain.MobileEntitlement;
import com.bestbuy.bbym.ise.drp.util.DateFormatter;
import com.bestbuy.bbym.ise.drp.util.FormatModel;

public class EntitlementSummaryPrintPage extends BaseWebPage {

    private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(EntitlementSummaryPrintPage.class);

    private final DateFormatter<Date> dateFmt = new DateFormatter<Date>();
    private final String na = getString("notApplicable.label");

    public EntitlementSummaryPrintPage(PageParameters pp) {
	super(null);

	logger.debug("printPageViewType " + pp.get("printPageViewType"));

	String printPageViewType = pp.get("printPageViewType").toString();

	EntitlementLookup entitlementLookup = (EntitlementLookup) getDailyRhythmPortalSession().getEntitlementDataMap()
		.get("EntitlementLookup");

	MobileEntitlement mobileEntitlement = (MobileEntitlement) getDailyRhythmPortalSession().getEntitlementDataMap()
		.get("MobileEntitlement");

	Entitlement selectedEntitlement = (Entitlement) getDailyRhythmPortalSession().getEntitlementDataMap().get(
		"SelectedEntitlement");

	CompoundPropertyModel<EntitlementLookup> compoundPropertyModel = new CompoundPropertyModel<EntitlementLookup>(
		entitlementLookup);
	Form<EntitlementLookup> entitlementPrintPageForm = new Form<EntitlementLookup>("entitlementPrintPageForm",
		compoundPropertyModel);

	// Adding Image...  
	Image entitlementImage = new NonCachingImage("entitlementImage");
	entitlementImage.add(AttributeModifier.replace("src", "/drp-war/images/agreement_masthead.bmp"));
	entitlementPrintPageForm.add(entitlementImage);

	add(entitlementPrintPageForm);
	// Adding all Page contents to entitlementPrintPageContainer
	entitlementPrintPageForm
		.add(new Label("customerFullName",
			(entitlementLookup.getCustomerFullName() == null || entitlementLookup.getCustomerFullName()
				.isEmpty())?na:entitlementLookup.getCustomerFullName()));

	String returnsLogoLabel = "";

	if (printPageViewType.equalsIgnoreCase("SELC_PAGE")){
	    returnsLogoLabel = getString("entitlement.printPage.selectPageLogo");

	}else if (printPageViewType.equalsIgnoreCase("NXT_PAGE")){
	    returnsLogoLabel = getString("entitlement.printPage.nxtStpsPageLogo");
	}

	entitlementPrintPageForm.add(new Label("returnsTypeLabel", new Model<String>(returnsLogoLabel)));

	entitlementPrintPageForm.add(new Label("carrier"));
	entitlementPrintPageForm.add(new Label("serialNumber"));
	entitlementPrintPageForm.add(new Label("defectiveDevice"));
	entitlementPrintPageForm.add(new Label("line.mobileNumber"));
	entitlementPrintPageForm.add(new Label("rzNumber",
		(entitlementLookup.getRzNumber() == null || entitlementLookup.getRzNumber().isEmpty())?na
			:entitlementLookup.getRzNumber()));
	entitlementPrintPageForm.add(new Label("rzTier", (entitlementLookup.getRzTier() == null || entitlementLookup
		.getRzTier().isEmpty())?na:entitlementLookup.getRzTier()));
	entitlementPrintPageForm.add(new Label("manufacturerSerialNumber", (entitlementLookup
		.getManufacturerSerialNumber() == null || entitlementLookup.getManufacturerSerialNumber().isEmpty())?na
		:entitlementLookup.getManufacturerSerialNumber()));
	entitlementPrintPageForm.add(new Label("skuDescription", compoundPropertyModel.bind("item.description")));
	entitlementPrintPageForm.add(new Label("productSku"));
	entitlementPrintPageForm.add(new Label("purchaseDate", new FormatModel<Date>(entitlementLookup.getItem()
		.getPurchaseDate(), dateFmt, na)));
	entitlementPrintPageForm.add(new Label("gspDescription",
		(entitlementLookup.getGspDescription() == null || entitlementLookup.getGspDescription().isEmpty())?na
			:entitlementLookup.getGspDescription()));
	entitlementPrintPageForm.add(new Label("gspNumber",
		(entitlementLookup.getGspNumber() == null || entitlementLookup.getGspNumber().isEmpty())?na
			:entitlementLookup.getGspNumber()));

	final WebMarkupContainer storeSkuCointainer = new WebMarkupContainer("storeSkuCointainer");
	storeSkuCointainer.setOutputMarkupPlaceholderTag(true);
	// Add Store fields to container...
	storeSkuCointainer.add(new Label("storeNum", compoundPropertyModel.bind("fourpartkey.storeId")));
	storeSkuCointainer.add(new Label("registerNum", compoundPropertyModel.bind("fourpartkey.workStationId")));
	storeSkuCointainer.add(new Label("transNum", compoundPropertyModel.bind("fourpartkey.registerTransactionNum")));
	entitlementPrintPageForm.add(storeSkuCointainer);

	final WebMarkupContainer onlineCointainer = new WebMarkupContainer("onlineCointainer");
	onlineCointainer.setOutputMarkupPlaceholderTag(true);
	onlineCointainer.add(new Label("onlineOrderNum", compoundPropertyModel.bind("item.transactionId")));
	entitlementPrintPageForm.add(onlineCointainer);

	if (entitlementLookup.getEntitlementPurchaseType() == EntitlementPurchaseType.STORE){
	    storeSkuCointainer.setVisible(true);
	    onlineCointainer.setVisible(false);
	}else if (entitlementLookup.getEntitlementPurchaseType() == EntitlementPurchaseType.ONLINE){
	    storeSkuCointainer.setVisible(false);
	    onlineCointainer.setVisible(true);
	}

	final WebMarkupContainer selectionPageContainer = new WebMarkupContainer("selectionPageContainer");
	selectionPageContainer.setOutputMarkupPlaceholderTag(true);
	entitlementPrintPageForm.add(selectionPageContainer);

	final WebMarkupContainer nextStepsPageContainer = new WebMarkupContainer("nextStepsPageContainer");
	nextStepsPageContainer.setOutputMarkupPlaceholderTag(true);
	entitlementPrintPageForm.add(nextStepsPageContainer);

	if (printPageViewType.equalsIgnoreCase("SELC_PAGE")){
	    selectionPageContainer.setVisible(true);
	    nextStepsPageContainer.setVisible(false);
	}else if (printPageViewType.equalsIgnoreCase("NXT_PAGE")){
	    selectionPageContainer.setVisible(false);
	    nextStepsPageContainer.setVisible(true);
	}

	entitlementPrintPageForm.add(new Label("printedDate", new FormatModel<Date>(new Date(), dateFmt, na)));

	ListView<Entitlement> listview = new ListView<Entitlement>("listview", mobileEntitlement.getEntitlementList()) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void populateItem(ListItem<Entitlement> item) {
		Entitlement entitlement = item.getModelObject();
		item.add(new Label("displayText", entitlement.getDisplayText()));
		item
			.add(new Label("details", "<ul>" + entitlement.getDetails() + "</ul>")
				.setEscapeModelStrings(false));
	    }

	};

	selectionPageContainer.add(listview);
	nextStepsPageContainer.add(new Label("selEntitleLabel", selectedEntitlement != null?selectedEntitlement
		.getActions():na).setEscapeModelStrings(false));

    }
}
