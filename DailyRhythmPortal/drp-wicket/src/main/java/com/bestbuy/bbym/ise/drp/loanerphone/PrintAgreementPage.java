package com.bestbuy.bbym.ise.drp.loanerphone;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.bestbuy.bbym.ise.drp.common.BaseWebPage;
import com.bestbuy.bbym.ise.drp.domain.Carrier;
import com.bestbuy.bbym.ise.drp.domain.CheckOutCheckInExtra;
import com.bestbuy.bbym.ise.drp.domain.CheckOutCheckInHistory;
import com.bestbuy.bbym.ise.drp.domain.Phone;
import com.bestbuy.bbym.ise.drp.util.FormatPropertyModel;
import com.bestbuy.bbym.ise.drp.util.MoneyFormatter;
import com.bestbuy.bbym.ise.drp.util.PhoneFormatter;
import com.bestbuy.bbym.ise.util.Util;

public class PrintAgreementPage extends BaseWebPage {

    private static final long serialVersionUID = 1L;

    public PrintAgreementPage(CheckOutCheckInHistory ciHist) {

	final String na = getString("notApplicable.label");

	Phone p = ciHist.getPhone();

	String name = Util.determineNameString(ciHist.getFirstName(), ciHist.getLastName());
	if (name == null){
	    name = "";
	}
	Label custNameLabel = new Label("custNameLabel", new String(name));
	add(custNameLabel);

	Label custNameLabelInParagraph = new Label("custNameLabelInParagraph", new String(name));
	add(custNameLabelInParagraph);

	Label custEmailLabel = new Label("custEmailLabel", new PropertyModel<String>(ciHist, "contactEmail"));
	add(custEmailLabel);

	Label serviceOrderLabel = new Label("serviceOrderLabel", new PropertyModel<String>(ciHist, "serviceOrderNo"));
	add(serviceOrderLabel);

	Label fulfillmentLabel = new Label("fulfillmentLabel", new PropertyModel<String>(ciHist, "fulfillmentType"));
	add(fulfillmentLabel);

	Label custPhoneLabel = new Label("custPhoneLabel", new FormatPropertyModel<String, Object>(
		new PropertyModel<String>(ciHist, "contactPhone"), new PhoneFormatter<Object>(), na));
	add(custPhoneLabel);

	Label gspNumLabel = new Label("gspNumLabel", new PropertyModel<String>(ciHist, "gspNo"));
	add(gspNumLabel);

	String carrierOs = (p.getCarrier().getCarrier() + " / " + p.getOperatingSystem().getOs()).replace("null", na);

	Label carrierosLabel = new Label("carrierosLabel", new Model<String>(carrierOs));
	add(carrierosLabel);

	String makemodel = (p.getMake() + " / " + p.getModelNumber()).replace("null", na);
	Label makemodelLabel = new Label("makemodelLabel", new Model<String>(makemodel));
	add(makemodelLabel);

	Label serialLabel = new Label("serialLabel", new PropertyModel<String>(p, "serialNumber"));
	add(serialLabel);

	Label depositLabel = new Label("depositLabel", new FormatPropertyModel<String, Object>(
		new PropertyModel<String>(ciHist, "checkOutDeposit"), new MoneyFormatter<Object>(), na));
	add(depositLabel);

	Carrier phoneCarrier = p.getCarrier();
	Label skuLabel = new Label("skuLabel", new PropertyModel<String>(phoneCarrier, "carrierLoanerSku"));
	add(skuLabel);

	// cloning list so that i dont remove the extra from it and affect
	// something unintentionally.
	List<CheckOutCheckInExtra> extrasList = new ArrayList<CheckOutCheckInExtra>(ciHist.getPhoneExtraList());
	StringBuilder accessories = new StringBuilder();

	extrasList.remove(0); // first item is whether or not the phone has been
	// wiped.
	for(CheckOutCheckInExtra extra: extrasList){
	    if (extra.isCheckOutStatus()){
		accessories.append(extra.getName());
		accessories.append(", ");
	    }
	}

	Label accessoriesLabel = new Label("accessoriesLabel", new Model<String>(StringUtils.chomp(accessories
		.toString(), ", ")));
	add(accessoriesLabel);

    }

}
