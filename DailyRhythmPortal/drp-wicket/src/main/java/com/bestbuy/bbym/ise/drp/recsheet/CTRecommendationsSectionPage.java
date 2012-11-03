package com.bestbuy.bbym.ise.drp.recsheet;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.validation.validator.StringValidator.MaximumLengthValidator;

import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

@AuthorizeInstantiation( {DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER, DrpConstants.DRP_LEAD,
	DrpConstants.DRP_USER, DrpConstants.DRP_TEAM })
public class CTRecommendationsSectionPage extends AbstractRecSheetPage {

    private static final long serialVersionUID = 1L;

    public CTRecommendationsSectionPage(PageParameters parameters) {
	super(parameters);

	Label title = new Label("sectionTitle", "Recommendations");
	title.setOutputMarkupPlaceholderTag(true);
	form.add(title);

	TextArea recommendationsTextArea = new TextArea("recommendations", new PropertyModel<String>(recommendation,
		"recommendedSubscription"));
	recommendationsTextArea.setMarkupId("recommendedSubscription");
	recommendationsTextArea.setOutputMarkupPlaceholderTag(true);
	recommendationsTextArea.add(new MaximumLengthValidator(430));
	form.add(recommendationsTextArea);
    }

    @Override
    void clearSection(AjaxRequestTarget target) {
	// TODO Auto-generated method stub

    }

}
