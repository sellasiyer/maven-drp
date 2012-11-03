package com.bestbuy.bbym.ise.drp.dashboard;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.springframework.beans.BeanUtils;
import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.BasePanel;
import com.bestbuy.bbym.ise.drp.util.FormatModel;

public class CustomerDashboardHeaderPanel extends BasePanel {

    private static final long serialVersionUID = 1L;

    final String na = getString("notApplicable.label");
    private Customer bbyCustomer = new Customer();

    public CustomerDashboardHeaderPanel(String id, Customer selectedCustomer) {
	super(id);

	DailyRhythmPortalSession session = getDailyRhythmPortalSession();

	if (selectedCustomer == null && session.getBestBuyCustomer() != null){
	    BeanUtils.copyProperties(session.getBestBuyCustomer(), bbyCustomer);
	}else if (selectedCustomer != null){
	    BeanUtils.copyProperties(selectedCustomer, bbyCustomer);
	}

	// bby customer details
	add(new Label("customerNameLabel", new FormatModel<String>(bbyCustomer.getNameString(), na)));
	add(new Label("customerAddressLabel", new FormatModel<String>(bbyCustomer.getAddressString(), na)));
	add(new Label("customerRewardZoneLabel", new FormatModel<String>(bbyCustomer.getRewardZoneId(), na)));

	Image memberTypeImage = new NonCachingImage("memberCCCardTypeImage") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		if (bbyCustomer != null && bbyCustomer.getRewardZone() != null
			&& StringUtils.isNotEmpty(bbyCustomer.getRewardZone().getCcAccountType())){
		    return true;
		}
		return false;
	    }
	};
	memberTypeImage.add(AttributeModifier.replace("src", "/drp-war/images/cc_small_new.jpg"));
	add(memberTypeImage);
    }
}
