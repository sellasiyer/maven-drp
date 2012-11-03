package com.bestbuy.bbym.ise.drp.beast.common;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.bestbuy.bbym.ise.drp.common.BaseWebPage;
import com.bestbuy.bbym.ise.drp.common.PageParameterKeys;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

@AuthorizeInstantiation(DrpConstants.DRP_BEAST)
public abstract class BaseBeastPage extends BaseWebPage {

    protected LoadingModalPanel loadingModalPanel;
    protected OkCancelModalPanel quitModalPanel;
    protected MessageModalPanel messageModalPanel;

    public BaseBeastPage() {
	this(null);
    }

    public BaseBeastPage(PageParameters parameters) {
	super(parameters);
	init();
    }

    private void init() {
	getSession().clear();

	loadingModalPanel = new LoadingModalPanel("loadingModalPanel") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		loadingModalPanelOnClose(target);
	    }
	};
	loadingModalPanel.setOutputMarkupPlaceholderTag(true);
	add(loadingModalPanel);

	quitModalPanel = new OkCancelModalPanel("quitModalPanel", "Yes", "No") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		quitModalPanelOnClose(target);
	    }
	};
	quitModalPanel.setOutputMarkupPlaceholderTag(true);
	add(quitModalPanel);

	messageModalPanel = new MessageModalPanel("messageModalPanel", "OK") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		messageModalPanelOnClose(target);
	    }
	};
	messageModalPanel.setOutputMarkupPlaceholderTag(true);
	add(messageModalPanel);
    }

    protected void loadingModalPanelOnClose(AjaxRequestTarget target) {
    }

    protected void quitModalPanelOnClose(AjaxRequestTarget target) {
	if (quitModalPanel.isOk()){
	    getDailyRhythmPortalSession().setBestBuyCustomer(null);
	    getDailyRhythmPortalSession().setProtectionPlanList(null);
	    getDailyRhythmPortalSession().setProtectionPlanDetails(null);
	    PageParameters pp = new PageParameters();
	    pp.add(PageParameterKeys.EXIT_STATUS.getUrlParameterKey(), "cancel");
	    setResponsePage(new StatusPage(pp));
	}
    }

    protected void messageModalPanelOnClose(AjaxRequestTarget target) {
    }
}
