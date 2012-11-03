package com.bestbuy.bbym.ise.drp.beast.tradein;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements1.5. (25) Customer is on Do not Buy list.
 *
 * @author a915722
 */
public class DoNotBuyListPage extends BeastPage {

    private static Logger logger = LoggerFactory.getLogger(DoNotBuyListPage.class);
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new new instance.
     *
     * @param parameters PageParameters
     */
    public DoNotBuyListPage(final PageParameters parameters) {
	super(parameters);

	this.getBorder().setPageTitle("");

	nextButton.setVisible(false);
	backButton.setVisible(false);
	clearButton.setVisible(false);
    }

    /**
     * @see
     * com.bestbuy.bbym.ise.drp.beast.tradein.BeastPage#clearForm(org.apache.wicket.ajax.AjaxRequestTarget)
     */
    @Override
    void clearForm(AjaxRequestTarget target) {

    }

    /**
     * @see
     * com.bestbuy.bbym.ise.drp.beast.tradein.BeastPage#goBack(org.apache.wicket.ajax.AjaxRequestTarget)
     */
    @Override
    void goBack(AjaxRequestTarget target) {
    }

    /**
     * @see
     * com.bestbuy.bbym.ise.drp.beast.tradein.BeastPage#nextPage(org.apache.wicket.ajax.AjaxRequestTarget,
     * org.apache.wicket.markup.html.form.Form)
     */
    @Override
    void nextPage(AjaxRequestTarget target, Form<?> form) {

    }
}
