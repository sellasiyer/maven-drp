package com.bestbuy.bbym.ise.drp.beast.tradein.mapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents BuyPriceQuestion XML node.
 * 
 * @author a915722
 */
public class BuyPriceQuestion implements Serializable {

    private final String id;
    private final List<BuyPriceQuestionOption> options;
    private BuyPriceQuestionOption selected;
    private final String questionText;
    private final String justificationText;
    private final String nextHref;
    private String finalEsimatePrice;
    private String totalNumberOfQuestions;

    /**
     * Constructs a new instance.
     * 
     * @param id question id.
     */
    public BuyPriceQuestion(final String id, final String questionText, final String nextHref,
	    final String justificationText) {
	this.id = id;
	this.questionText = questionText;
	this.justificationText = justificationText;
	this.nextHref = nextHref;
	options = new ArrayList<BuyPriceQuestionOption>();
    }

    /**
     * Returns selected option;
     * 
     * @return selected option;
     */
    public BuyPriceQuestionOption getSelected() {
	return selected;
    }

    /**
     * Sets selected option.
     * 
     * @param selected selected option.
     */
    public void setSelected(BuyPriceQuestionOption selected) {
	this.selected = selected;
    }

    /**
     * Returns question id.
     * 
     * @return question id.
     */
    public String getId() {
	return id;
    }

    /**
     * Returns list of options.
     * 
     * @return list of options.
     */
    public List<BuyPriceQuestionOption> getOptions() {
	return options;
    }

    /**
     * Adds BuyPriceQuestionOption.
     * 
     * @param buyPriceQuestionOption  BuyPriceQuestionOption.
     */
    public void addBuyPriceQuestionOption(BuyPriceQuestionOption buyPriceQuestionOption) {
	this.getOptions().add(buyPriceQuestionOption);
    }

    /**
     * Returns question text.
     * 
     * @return question text.
     */
    public String getQuestionText() {
	return questionText;
    }

    /**
     * Returns URI to get next QA question.
     * 
     * @return URI to get next QA question.
     */
    public String getNextHref() {
	return nextHref;
    }

    /**
     * @return the justificationText
     */
    public String getJustificationText() {
	return justificationText;
    }

    /**
     * Returns final estimate price. Used only when all questions are answered and
     * we need to get the final price.
     * 
     * @return final estimate price. Used only when all questions are answered and
     * we need to get the final price.
     */
    public String getFinalEsimatePrice() {
	return finalEsimatePrice;
    }

    /**
     * Sets final estimate price.
     * 
     * @param finalEsimatePrice  final estimate price.
     */
    public void setFinalEsimatePrice(String finalEsimatePrice) {
	this.finalEsimatePrice = finalEsimatePrice;
    }

    /**
     * Returns total number of questions.
     * 
     * @return total number of questions.
     */
    public String getTotalNumberOfQuestions() {
	return totalNumberOfQuestions;
    }

    /**
     * Sets  total number of questions.
     * 
     * @param totalNumberOfQuestions  total number of questions.
     */
    public void setTotalNumberOfQuestions(String totalNumberOfQuestions) {
	this.totalNumberOfQuestions = totalNumberOfQuestions;
    }

}
