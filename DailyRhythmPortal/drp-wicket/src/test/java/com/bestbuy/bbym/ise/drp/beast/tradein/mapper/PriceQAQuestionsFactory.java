package com.bestbuy.bbym.ise.drp.beast.tradein.mapper;

import com.bestbuy.bbym.ise.drp.domain.ui.UIDataItem;
import com.bestbuy.bbym.ise.drp.domain.ui.UIRequest;

/**
 * Factory used to create dummy and mock {@link PriceQAQuestions} objects for testing.
 */
public class PriceQAQuestionsFactory {

    private PriceQAQuestionsFactory() {
	// This class is not meant to be extended or instantiated
    }

    public static PriceQAQuestions getDummyPriceQAQuestions() {

	UIRequest uiRequest = new UIRequest();

	UIDataItem uiDataItem = new UIDataItem();
	uiDataItem.setStringProp("pricingQAHref", "");
	uiRequest.put("pricingQALink", uiDataItem);

	PriceQAQuestions questions = new PriceQAQuestions(null, uiRequest, "a904002");

	String questionText1 = "What is the condition of the device?";
	String questionText2 = "Is there any water damage to the battery or the phone?";

	BuyPriceQuestion buyPriceQuestion1 = new BuyPriceQuestion("buy price question 1", questionText1, "0", null);
	buyPriceQuestion1.addBuyPriceQuestionOption(new BuyPriceQuestionOption("Perfect", "Perfect description here.",
		"$87.01"));
	buyPriceQuestion1.addBuyPriceQuestionOption(new BuyPriceQuestionOption("Good", "Good description here.",
		"$52.21"));
	buyPriceQuestion1.addBuyPriceQuestionOption(new BuyPriceQuestionOption("Poor", "Poor description here.",
		"$39.15"));

	buyPriceQuestion1.setSelected(new BuyPriceQuestionOption("Good", "Good description here.", "$52.21"));

	questions.addQuestion(buyPriceQuestion1);

	BuyPriceQuestion buyPriceQuestion2 = new BuyPriceQuestion("buy price question 2", questionText2, "1", null);
	buyPriceQuestion2.addBuyPriceQuestionOption(new BuyPriceQuestionOption("No", "No water damage.", "$XX.XX"));
	buyPriceQuestion2.addBuyPriceQuestionOption(new BuyPriceQuestionOption("Yes", "Has water damage.", "$0.00"));

	buyPriceQuestion2.setSelected(new BuyPriceQuestionOption("No", "No water damage.", "$XX.XX"));
	questions.addQuestion(buyPriceQuestion2);

	BuyPriceQuestion buyPriceQuestion3 = new BuyPriceQuestion("buy price question 3", questionText1, "2", null);
	buyPriceQuestion3.addBuyPriceQuestionOption(new BuyPriceQuestionOption("Perfect", "Perfect description here.",
		"$87.01"));
	buyPriceQuestion3.addBuyPriceQuestionOption(new BuyPriceQuestionOption("Good", "Good description here.",
		"$52.21"));
	buyPriceQuestion3.addBuyPriceQuestionOption(new BuyPriceQuestionOption("Poor", "Poor description here.",
		"$39.15"));

	buyPriceQuestion3.setSelected(new BuyPriceQuestionOption("Perfect", "Perfect description here.", "$87.01"));

	questions.addQuestion(buyPriceQuestion3);

	BuyPriceQuestion buyPriceQuestion4 = new BuyPriceQuestion("buy price question 4", questionText2, "3", null);
	buyPriceQuestion4.addBuyPriceQuestionOption(new BuyPriceQuestionOption("No", "No water damage.", "$XX.XX"));
	buyPriceQuestion4.addBuyPriceQuestionOption(new BuyPriceQuestionOption("Yes", "Has water damage.", "$0.00"));

	buyPriceQuestion4.setSelected(new BuyPriceQuestionOption("No", "No water damage.", "$XX.XX"));
	questions.addQuestion(buyPriceQuestion4);

	BuyPriceQuestion buyPriceQuestion5 = new BuyPriceQuestion("buy price question 5", questionText2, "4", null);
	buyPriceQuestion5.addBuyPriceQuestionOption(new BuyPriceQuestionOption("No", "No water damage.", "$XX.XX"));
	buyPriceQuestion5.addBuyPriceQuestionOption(new BuyPriceQuestionOption("Yes", "Has water damage.", "$0.00"));

	buyPriceQuestion5.setSelected(new BuyPriceQuestionOption("No", "No water damage.", "$XX.XX"));

	questions.addQuestion(buyPriceQuestion5);

	UIDataItem domDataItem = new UIDataItem();
	domDataItem.setStringProp("finalAdjustedPrice", "11");
	questions.setFinalAdjustedPrice("111");

	return questions;
    }
}
