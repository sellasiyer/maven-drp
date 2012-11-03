package com.bestbuy.bbym.ise.drp.tokencode;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.bestbuy.bbym.ise.domain.Carrier;
import com.bestbuy.bbym.ise.drp.common.NewBaseNavPage;

public class TmobileRedirectPage extends NewBaseNavPage {

    /**
     * 
     */
    private static final long serialVersionUID = -7606457187386418072L;

    public TmobileRedirectPage(PageParameters parameters) {
	super(parameters);

	setResponsePage(new TokenCodesPage(parameters, Carrier.TMOBILE));
    }

}
