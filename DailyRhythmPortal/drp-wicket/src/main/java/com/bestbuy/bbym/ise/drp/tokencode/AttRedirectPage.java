package com.bestbuy.bbym.ise.drp.tokencode;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.bestbuy.bbym.ise.domain.Carrier;
import com.bestbuy.bbym.ise.drp.common.NewBaseNavPage;

public class AttRedirectPage extends NewBaseNavPage {

    public AttRedirectPage(PageParameters parameters) {
	super(parameters);
	setResponsePage(new TokenCodesPage(parameters, Carrier.ATT));
    }

}
