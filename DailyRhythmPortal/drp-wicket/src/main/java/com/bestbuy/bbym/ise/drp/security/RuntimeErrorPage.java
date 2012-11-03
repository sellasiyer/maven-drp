package com.bestbuy.bbym.ise.drp.security;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.bestbuy.bbym.ise.drp.common.BaseNavPage;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

@AuthorizeInstantiation( {DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER, DrpConstants.DRP_LEAD,
	DrpConstants.DRP_USER, DrpConstants.SHP_USER, DrpConstants.DRP_TEAM, DrpConstants.DRP_BEAST,
	DrpConstants.SHP_MANAGER, DrpConstants.DRP_UNAUTHORIZED })
public class RuntimeErrorPage extends BaseNavPage {

    private static final long serialVersionUID = 1L;

    public RuntimeErrorPage() {
	super(new PageParameters());
    }
}
