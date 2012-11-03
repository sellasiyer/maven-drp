package com.bestbuy.bbym.ise.drp.beast.tradein.mapper;

import com.bestbuy.bbym.ise.drp.domain.ui.UIData;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.domain.ui.UIDataItem;
import com.bestbuy.bbym.ise.drp.domain.ui.UIDataList;
import com.bestbuy.bbym.ise.drp.domain.ui.UIReply;
import com.bestbuy.bbym.ise.drp.domain.ui.UIRequest;
import com.bestbuy.bbym.ise.drp.service.UIService;

/**
 * Encapsulates price Q&A questions and keeps the pages flow.
 *
 * @author a915722
 */
public class PriceQAQuestions implements Serializable {

    private static final long serialVersionUID = 1L;

    private static Logger LOG = LoggerFactory.getLogger(PriceQAQuestions.class);
    private final UIService uiService;
    private final List<BuyPriceQuestion> questions;
    private final List<BuyPriceQuestion> summaryQuestions;
    private int currentStep = 0;
    private String finalAdjustedPrice;
    final String defaultNextHref;
    private int totalQuestions;
    private UIDataItem domDataItem;
    private UIRequest uiRequest;
    private String userId;

    /**
     * Returns list of questions.
     */
    public List<BuyPriceQuestion> getQuestions() {
	return questions;

    }

    public List<BuyPriceQuestion> getSummaryQuestions() {
	return summaryQuestions;
    }

    /**
     * Returns total number of questions.
     */
    public int getTotalQuestions() {
	return totalQuestions;
    }

    public UIDataItem getDomDataItem() {
	return domDataItem;
    }

    public void setDomDataItem(UIDataItem domDataItem) {
	this.domDataItem = domDataItem;
    }

    /**
     * Sets total number of questions.
     */
    public void setTotalQuestions(int totalQuestions) {
	this.totalQuestions = totalQuestions;
    }

    public BuyPriceQuestion getCurrentBuyPriceQuestion() {
	return questions.get(currentStep);

    }

    public PriceQAQuestions(final UIService uiService, final UIRequest uiRequest, final String userId) {
	this.questions = new ArrayList<BuyPriceQuestion>();
	this.summaryQuestions = new ArrayList<BuyPriceQuestion>();
	this.uiService = uiService;
	this.uiRequest = uiRequest;
	this.userId = userId;
	this.defaultNextHref = ((UIDataItem) uiRequest.get("pricingQALink")).getStringProp("pricingQAHref", null);
    }

    /**
     * Returns current step.
     */
    public int getCurrentStep() {
	return currentStep + 1;
    }

    /**
     * Returns final adjusted price.
     */
    public String getFinalAdjustedPrice() {
	return finalAdjustedPrice;
    }

    /**
     * Sets final adjusted price.
     */
    public void setFinalAdjustedPrice(String finalAdjustedPrice) {
	this.finalAdjustedPrice = finalAdjustedPrice;
    }

    /**
     * Returns BuyPriceQuestion question by current step id.
     */
    public BuyPriceQuestion getBuyPriceQuestionByCurrentStepId() {
	if (questions.size() > currentStep){
	    return questions.get(currentStep);
	}
	String href = null;
	if (currentStep == 0){
	    href = this.defaultNextHref;
	}else{
	}
	return null;
    }

    /**
     * Returns previous question.
     */
    public BuyPriceQuestion requestPreviousQuestion() {
	currentStep--;
	if (this.currentStep < 0){
	    // redirect to detail page.
	    return null;
	}

	return questions.get(currentStep);
    }

    /**
     * Returns next question.
     */
    public BuyPriceQuestion requestNextQuestion(boolean init) {
	if (init == false){
	    currentStep++;
	}

	//if (currentStep < questions.size()){
	//    return questions.get(currentStep);
	//}

	boolean redirect = false;
	if (currentStep >= totalQuestions && (init == false)){
	    // redirect to questions summary page.
	    redirect = true;
	}

	if (redirect){
	    currentStep--;
	}

	StringBuffer uri = new StringBuffer(defaultNextHref);

	int cnt = 0;
	if (redirect){
	    cnt = questions.size();
	}else{
	    cnt = currentStep;
	}

	for(int i = 0; i < cnt; i++){
	    BuyPriceQuestion current = questions.get(i);
	    if (current.getSelected() != null){
		uri.append("&").append(current.getId()).append("=").append(current.getSelected().getValue());
	    }
	}

	uiRequest = new UIRequest();
	uiRequest.setName("PricingQA");
	UIDataItem dataItem = new UIDataItem();
	dataItem.setStringProp("pricingQAHref", uri.toString());
	dataItem.setName("pricingQALink");
	dataItem.setStringProp("userId", userId);

	uiRequest.put("pricingQALink", dataItem);

	UIReply uiReply = null;
	BuyPriceQuestion question = null;
	try{
	    uiReply = uiService.processUIRequest(uiRequest);

	    if (redirect){

		domDataItem = ((UIDataItem) ((UIRequest) uiReply.get("nextUIRequest")).get("domDocument"));
		UIDataItem summaryDI = ((UIDataItem) uiReply.get("Summary"));
		uiRequest = (UIRequest) uiReply.get("nextUIRequest");
		finalAdjustedPrice = summaryDI.getStringProp("BuyPrice", null);
		summaryQuestions.clear();
		UIDataList qaList = (UIDataList) uiReply.get("QASummaryList");
		for(UIData di: qaList){
		    UIDataItem udi = (UIDataItem) di;
		    BuyPriceQuestion q = new BuyPriceQuestion(udi.getStringProp("Name", null), udi.getStringProp(
			    "Text", null), null, null);
		    BuyPriceQuestionOption opt = new BuyPriceQuestionOption(udi.getStringProp("Value", null), null,
			    null);
		    q.setSelected(opt);
		    summaryQuestions.add(q);
		}

		return null;
	    }else{

		UIReplyMapper uiReplyMapper = new UIReplyMapper(uiReply, redirect);

		question = uiReplyMapper.getBuyPriceQuestion();

		if (init){
		    totalQuestions = Integer.parseInt(question.getTotalNumberOfQuestions());
		}

	    }

	}catch(Exception e){
	    LOG.error("Unable to get next question", e);
	    throw new RuntimeException("Unable to get next question.");
	}

	if (currentStep < questions.size()){
	    questions.set(currentStep, question);
	}else{
	    questions.add(question);
	}

	return question;
    }

    public UIRequest getUiRequest() {
	return uiRequest;
    }

    public void setUiRequest(UIRequest uiRequest) {
	this.uiRequest = uiRequest;
    }

    public void addQuestion(BuyPriceQuestion buyPriceQuestion) {
	this.questions.add(buyPriceQuestion);
    }
}
