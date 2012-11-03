package com.bestbuy.bbym.ise.drp.tokencode;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.bestbuy.bbym.ise.domain.Carrier;
import com.bestbuy.bbym.ise.drp.common.NewBaseNavPage;

public class SprintRedirectPage extends NewBaseNavPage {

    private static final long serialVersionUID = 1L;

    public SprintRedirectPage(PageParameters parameters) {
	super(parameters);
	// 
	setResponsePage(new TokenCodesPage(parameters, Carrier.SPRINT));
    }
}
