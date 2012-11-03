package com.bestbuy.bbym.ise.drp.loanerphone;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.common.NewBaseNavPage;
import com.bestbuy.bbym.ise.drp.common.PdfByteArrayResource;
import com.bestbuy.bbym.ise.drp.domain.CheckOutCheckInExtra;
import com.bestbuy.bbym.ise.drp.domain.CheckOutCheckInHistory;
import com.bestbuy.bbym.ise.drp.domain.LoanerPhoneCondition;
import com.bestbuy.bbym.ise.drp.domain.LoanerPhoneFulFillmentType;
import com.bestbuy.bbym.ise.drp.domain.Phone;
import com.bestbuy.bbym.ise.drp.util.FormatPropertyModel;
import com.bestbuy.bbym.ise.drp.util.MoneyFormatter;
import com.bestbuy.bbym.ise.drp.util.WicketUtils;

public class ViewCheckedOutPage extends NewBaseNavPage {

    private static Logger logger = LoggerFactory.getLogger(ViewCheckedOutPage.class);
    private static final long serialVersionUID = 1L;

    private Phone checkOutPhone;
    private CheckOutCheckInHistory cocih;
    private boolean phoneDataStatus = false;
    private boolean checkOutChargerStatus = false;
    private boolean checkOutBatteryStatus = false;
    private boolean checkOutAdapterStatus = false;

    public ViewCheckedOutPage(Phone phone, final Page returnPage) {
	super(null);

	checkOutPhone = phone;
	logger.trace("checkOutPhone=" + checkOutPhone);

	if (checkOutPhone == null){
	    checkOutPhone = new Phone();
	    cocih = new CheckOutCheckInHistory();
	}else if (checkOutPhone.getLatestCheckOutCheckInHistory() == null){
	    cocih = new CheckOutCheckInHistory();
	}else{
	    cocih = checkOutPhone.getLatestCheckOutCheckInHistory();
	}

	List<CheckOutCheckInExtra> checkOutPhoneExtrasList = cocih.getPhoneExtraList();
	if (checkOutPhoneExtrasList != null){
	    for(CheckOutCheckInExtra checkOutExtra: checkOutPhoneExtrasList){
		if (checkOutExtra != null
			&& getString("loanerPhoneCheckedOutForm.checkList.dataWipedClean.label").equalsIgnoreCase(
				checkOutExtra.getName())){
		    phoneDataStatus = checkOutExtra.isCheckOutStatus();
		}
		if (checkOutExtra != null
			&& getString("loanerPhoneCheckedOutForm.checkList.accessories.charger.label").equalsIgnoreCase(
				checkOutExtra.getName())){
		    checkOutChargerStatus = checkOutExtra.isCheckOutStatus();
		}
		if (checkOutExtra != null
			&& getString("loanerPhoneCheckedOutForm.checkList.accessories.battery.label").equalsIgnoreCase(
				checkOutExtra.getName())){
		    checkOutBatteryStatus = checkOutExtra.isCheckOutStatus();
		}
		if (checkOutExtra != null
			&& getString("loanerPhoneCheckedOutForm.checkList.accessories.adapter.label").equalsIgnoreCase(
				checkOutExtra.getName())){
		    checkOutAdapterStatus = checkOutExtra.isCheckOutStatus();
		}

	    }
	}

	final Label carrierLabel = new Label("carrierLabel",
		new PropertyModel<String>(checkOutPhone, "carrier.carrier"));
	carrierLabel.setOutputMarkupPlaceholderTag(true);
	add(carrierLabel);

	final Label osLabel = new Label("osLabel", new PropertyModel<String>(checkOutPhone, "operatingSystem.os"));
	add(osLabel);

	final Label makeLabel = new Label("makeLabel", new PropertyModel<String>(checkOutPhone, "make"));
	add(makeLabel);

	final Label modelLabel = new Label("modelLabel", new PropertyModel<String>(checkOutPhone, "modelNumber"));
	add(modelLabel);

	final Label imeiLabel = new Label("imeiLabel", new PropertyModel<String>(checkOutPhone, "serialNumber"));
	add(imeiLabel);

	final Label firstName = new Label("firstName", new PropertyModel<String>(cocih, "firstName"));
	add(firstName);

	final Label lastName = new Label("lastName", new PropertyModel<String>(cocih, "lastName"));
	add(lastName);

	final Label contactNo = new Label("contactNo", new PropertyModel<String>(cocih, "contactPhone"));
	add(contactNo);

	final Label email = new Label("email", new PropertyModel<String>(cocih, "contactEmail"));
	add(email);

	final Label serviceOrderNo = new Label("serviceOrderNo", new PropertyModel<String>(cocih, "serviceOrderNo"));
	add(serviceOrderNo);

	final Label gspNo = new Label("gspNo", new PropertyModel<String>(cocih, "gspNo"));
	add(gspNo);

	final Label fulfillmentType = new Label("fulfillmentType", new PropertyModel<LoanerPhoneFulFillmentType>(cocih,
		"fulfillmentType"));
	add(fulfillmentType);

	WebMarkupContainer phoneDataContainer = new WebMarkupContainer("phoneDataContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return phoneDataStatus;
	    }
	};
	phoneDataContainer.setOutputMarkupPlaceholderTag(true);
	add(phoneDataContainer);

	WebMarkupContainer conditionGood = new WebMarkupContainer("conditionGood") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return LoanerPhoneCondition.GOOD == cocih.getCheckOutCondition();
	    }
	};
	conditionGood.setOutputMarkupPlaceholderTag(true);
	add(conditionGood);

	WebMarkupContainer conditionFair = new WebMarkupContainer("conditionFair") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return LoanerPhoneCondition.FAIR == cocih.getCheckOutCondition();
	    }
	};
	conditionFair.setOutputMarkupPlaceholderTag(true);
	add(conditionFair);

	WebMarkupContainer conditionPoor = new WebMarkupContainer("conditionPoor") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return LoanerPhoneCondition.POOR == cocih.getCheckOutCondition();
	    }
	};
	conditionPoor.setOutputMarkupPlaceholderTag(true);
	add(conditionPoor);

	Label conditionCommentTextArea = new Label("conditionCommentTextArea", new PropertyModel<String>(cocih,
		"checkOutConditionComment"));
	add(conditionCommentTextArea);

	WebMarkupContainer checkOutChargerContainer = new WebMarkupContainer("checkOutChargerContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return checkOutChargerStatus;
	    }
	};
	checkOutChargerContainer.setOutputMarkupPlaceholderTag(true);
	add(checkOutChargerContainer);

	WebMarkupContainer checkOutBatteryContainer = new WebMarkupContainer("checkOutBatteryContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return checkOutBatteryStatus;
	    }
	};
	checkOutBatteryContainer.setOutputMarkupPlaceholderTag(true);
	add(checkOutBatteryContainer);

	WebMarkupContainer checkOutAdapterContainer = new WebMarkupContainer("checkOutAdapterContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return checkOutAdapterStatus;
	    }
	};
	checkOutAdapterContainer.setOutputMarkupPlaceholderTag(true);
	add(checkOutAdapterContainer);

	final Label deposit = new Label("deposit", new FormatPropertyModel<String, BigDecimal>(
		new PropertyModel<String>(cocih, "checkOutDeposit"), new MoneyFormatter<BigDecimal>(false), "N/A"));
	add(deposit);

	final AjaxLink<Object> reprintLink = new AjaxLink<Object>("reprintLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.debug("reprintLink onClick");
		cocih.setPhone(checkOutPhone);

		//uncommenting this code will make it popup a window with the html version, for debugging purposes.
		//PrintAgreementPage tmp = new PrintAgreementPage(cocih);
		//CharSequence url = RequestCycle.get().urlFor(new RenderPageRequestHandler(new PageProvider(tmp)));
		//target.appendJavaScript("window.open('" + url.toString() + "');");

		String html = WicketUtils.renderHTMLFromPage(new PrintAgreementPage(cocih));
		final PdfByteArrayResource pdfResource = WicketUtils.renderPDFFromHtml(html);
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
	};
	reprintLink.setOutputMarkupPlaceholderTag(true);
	add(reprintLink);

	final AjaxLink<Object> closeLink = new AjaxLink<Object>("closeLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.debug("closeLink onClick");
		if (returnPage != null){
		    setResponsePage(returnPage);
		}else{
		    setResponsePage(LoanerPhoneLandingPage.class);
		}
	    }
	};
	closeLink.setOutputMarkupPlaceholderTag(true);
	add(closeLink);
    }
}
