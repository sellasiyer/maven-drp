package com.bestbuy.bbym.ise.drp.beast.tradein;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.beast.tradein.mapper.BuyPriceQuestion;
import com.bestbuy.bbym.ise.drp.beast.tradein.mapper.BuyPriceQuestionOption;
import com.bestbuy.bbym.ise.drp.beast.tradein.mapper.PriceQAQuestions;
import com.bestbuy.bbym.ise.drp.domain.ui.UIReply;
import com.bestbuy.bbym.ise.drp.domain.ui.UIRequest;
import com.bestbuy.bbym.ise.drp.service.UIService;

/**
 * TradeInConditionPage page
 * 
 * @author a915722
 *
 */
public class TradeInConditionPage extends BeastPage {

    private static final long serialVersionUID = 1L;
    private static Logger LOGGER = LoggerFactory.getLogger(CatalogSearchPage.class);
    @SpringBean(name = "uiService")
    private UIService uiService;

    final Label currentQuestion;
    final Label justification;
    final Label steps;
    final MarkupContainer group;
    final ListView<BuyPriceQuestionOption> optionList;
    final RadioGroup<BuyPriceQuestion> radioGroup;
    final List<BuyPriceQuestionOption> options = new ArrayList<BuyPriceQuestionOption>();

    private String selected;
    private BuyPriceQuestion currentBuyPriceQuestion;

    /**
     * Returns selected value.
     * 
     * @return selected value.
     */
    public String getSelected() {
	return selected;
    }

    private UIReply uiReply;

    /**
     * Sets selected value.
     * 
     * @param selected  selected value.
     */
    public void setSelected(String selected) {
	this.selected = selected;
    }

    /**
     * Constructs a new instance.
     * 
     * @param uiReply UIReply.
     */
    public TradeInConditionPage(final PageParameters parameters, UIReply uiReply) {
	super(parameters);
	this.uiReply = uiReply;

	getBorder().setPageTitle(getString("tradeInConditionPage.phoneCondition.label"));

	if (this.getDailyRhythmPortalSession().getPriceQAQuestions() == null && uiReply != null){
	    UIRequest uiRequest = (UIRequest) uiReply.get("PricingQA");
	    PriceQAQuestions sessionPriceQAQuestions = new PriceQAQuestions(uiService, uiRequest,
		    getDailyRhythmPortalSession().getDrpUser().getUserId());

	    getDailyRhythmPortalSession().setPriceQAQuestions(sessionPriceQAQuestions);

	    currentBuyPriceQuestion = this.getDailyRhythmPortalSession().getPriceQAQuestions()
		    .requestNextQuestion(true);

	}else{
	    currentBuyPriceQuestion = this.getDailyRhythmPortalSession().getPriceQAQuestions()
		    .getCurrentBuyPriceQuestion();
	}

	justification = new Label("justification", new Model<String>(currentBuyPriceQuestion.getJustificationText()));
	justification.setOutputMarkupId(true);
	add(justification);
	options.clear();
	options.addAll(currentBuyPriceQuestion.getOptions());

	currentQuestion = new Label("question", new Model<String>(currentBuyPriceQuestion.getQuestionText()));
	currentQuestion.setOutputMarkupId(true);
	add(currentQuestion);

	// This will have the variable currentStep and the total size from
	// PriceQAQuestions
	steps = new Label("steps", new Model<String>("Step "
		+ this.getDailyRhythmPortalSession().getPriceQAQuestions().getCurrentStep() + " of "
		+ this.getDailyRhythmPortalSession().getPriceQAQuestions().getTotalQuestions()));
	steps.setOutputMarkupId(true);
	add(steps);

	group = new WebMarkupContainer("group");
	group.setOutputMarkupId(true);
	add(group);

	radioGroup = new RadioGroup<BuyPriceQuestion>("radioGroup", new PropertyModel<BuyPriceQuestion>(
		currentBuyPriceQuestion, "selected"));
	radioGroup.setOutputMarkupId(true);

	getBorder().setButtonEnabled(nextButton, false);

	group.add(radioGroup);

	optionList = new ListView<BuyPriceQuestionOption>("optionList", options) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void populateItem(ListItem<BuyPriceQuestionOption> item) {
		// Adds the odd or even class for CSS styling
		if (((item.getIndex() + 1) % 2) == 0){
		    item.add(AttributeModifier.replace("class", "even"));
		}else{
		    item.add(AttributeModifier.replace("class", "odd"));
		}

		BuyPriceQuestionOption option = item.getModelObject();

		final Radio<BuyPriceQuestionOption> optRadio = new Radio<BuyPriceQuestionOption>("radio",
			new Model<BuyPriceQuestionOption>(item.getModelObject()));

		optRadio.add(new AjaxFormSubmitBehavior(getBorder().getForm(), "onclick") {

		    private static final long serialVersionUID = 1L;

		    protected void onSubmit(AjaxRequestTarget target) {

			getBorder().setButtonEnabled(nextButton, true, target);
		    }

		    @Override
		    protected void onError(AjaxRequestTarget target) {
			// TODO Auto-generated method stub

		    }
		});
		item.add(optRadio);
		item.add(new Label("value", option.getValue()));
		item.add(new Label("description", option.getDescription()));
		item.add(new Label("adjustmentPrice", option.getAdjustmentPrice()));
	    }
	};
	optionList.setOutputMarkupId(true);
	radioGroup.add(optionList);

    }

    /**
     * @see com.bestbuy.bbym.ise.drp.beast.tradein.BeastPage#clearForm(org.apache.wicket.ajax.AjaxRequestTarget)
     */
    @Override
    void clearForm(AjaxRequestTarget target) {
	try{
	    this.getDailyRhythmPortalSession().getPriceQAQuestions().getCurrentBuyPriceQuestion().setSelected(null);
	    if (target != null){
		target.add(group);
	    }
	}catch(Exception exc){
	    LOGGER.error(exc.getMessage(), exc);
	    displayError(getString("TradeInConditionPage.service.down.message"), target);
	    return;
	}
    }

    /**
     * @see com.bestbuy.bbym.ise.drp.beast.tradein.BeastPage#goBack(org.apache.wicket.ajax.AjaxRequestTarget)
     */
    @Override
    void goBack(AjaxRequestTarget target) {
	try{
	    currentBuyPriceQuestion = this.getDailyRhythmPortalSession().getPriceQAQuestions()
		    .requestPreviousQuestion();

	    refreshPage(target);
	}catch(Exception exc){
	    LOGGER.error(exc.getMessage(), exc);
	    displayError(getString("TradeInConditionPage.service.down.message"), target);
	    return;
	}

    }

    private void refreshPage(AjaxRequestTarget target) {
	if (currentBuyPriceQuestion == null){
	    this.setResponsePage(new ProductDetailsPage(this.getPageParameters(), this.getDailyRhythmPortalSession()
		    .getCachedUIReply("ProductDetailsPage")));
	    return;
	}

	currentQuestion.setDefaultModel(new Model<String>(currentBuyPriceQuestion.getQuestionText()));
	steps.setDefaultModel(new Model<String>("Step "
		+ this.getDailyRhythmPortalSession().getPriceQAQuestions().getCurrentStep() + " of "
		+ this.getDailyRhythmPortalSession().getPriceQAQuestions().getTotalQuestions()));

	radioGroup.setDefaultModel(new PropertyModel<BuyPriceQuestion>(currentBuyPriceQuestion, "selected"));
	justification.setDefaultModel(new Model<String>(currentBuyPriceQuestion.getJustificationText()));
	options.clear();
	options.addAll(currentBuyPriceQuestion.getOptions());

	if (currentBuyPriceQuestion.getSelected() == null){
	    getBorder().setButtonEnabled(nextButton, false, target);
	}else{
	    getBorder().setButtonEnabled(nextButton, true, target);
	}

	if (target != null){
	    target.add(group);
	    target.add(justification);
	    target.add(currentQuestion);
	    target.add(steps);
	}

    }

    /**
     * @see com.bestbuy.bbym.ise.drp.beast.tradein.BeastPage#nextPage(org.apache.wicket.ajax.AjaxRequestTarget, org.apache.wicket.markup.html.form.Form)
     */
    @Override
    void nextPage(AjaxRequestTarget target, Form<?> form) {
	try{
	    currentBuyPriceQuestion = this.getDailyRhythmPortalSession().getPriceQAQuestions().requestNextQuestion(
		    false);

	    if (currentBuyPriceQuestion == null){
		this.setResponsePage(new PricingQASummaryPage(this.getPageParameters()));
		return;
	    }

	    refreshPage(target);
	}catch(Exception exc){
	    LOGGER.error(exc.getMessage(), exc);
	    displayError(getString("TradeInConditionPage.service.down.message"), target);
	    return;

	}
    }
}
