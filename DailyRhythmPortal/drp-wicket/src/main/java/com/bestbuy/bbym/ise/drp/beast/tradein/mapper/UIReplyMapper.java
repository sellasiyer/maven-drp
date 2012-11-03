package com.bestbuy.bbym.ise.drp.beast.tradein.mapper;

import com.bestbuy.bbym.ise.drp.domain.ui.UIData;
import com.bestbuy.bbym.ise.drp.domain.ui.UIDataItem;
import com.bestbuy.bbym.ise.drp.domain.ui.UIDataList;
import com.bestbuy.bbym.ise.drp.domain.ui.UIReply;
import com.bestbuy.bbym.ise.drp.domain.ui.UIRequest;

/**
 * Maps UIReply to BuyPriceQuestion.
 * 
 * @author a915722
 *
 */
public class UIReplyMapper {

    private final UIReply uiReply;
    private BuyPriceQuestion buyPriceQuestion;
    private final boolean redirect;

    /**
     * Returns BuyPriceQuestion.  
     * 
     * @return BuyPriceQuestion.
     */
    public BuyPriceQuestion getBuyPriceQuestion() {
	return buyPriceQuestion;
    }

    /**
     * Constructs a new instance.
     * 
     * @param uiReply UIReply.
     * @throws Exception 
     */
    public UIReplyMapper(final UIReply uiReply, final boolean redirect) throws Exception {
	this.uiReply = uiReply;
	this.redirect = redirect;

	map();
    }

    private void map() throws Exception {

	if ("NewCartItemResource".equalsIgnoreCase(uiReply.getName()) == false){
	    throw new RuntimeException("UIReply must contain DOMType.NEW_CART_ITEM_RESOURCE.getNodeName() root node.");
	}

	String questionText = null;
	if (uiReply.containsKey("Text")){
	    UIDataItem textData = (UIDataItem) uiReply.get("Text");
	    questionText = textData.getStringProperty("Text.STR");
	}else{
	    throw new RuntimeException("Unable to find question text.");
	}

	String justificationText = null;
	if (uiReply.containsKey("Justification")){
	    UIDataItem textData = (UIDataItem) uiReply.get("Justification");
	    justificationText = textData.getStringProperty("Justification.STR");
	}else{
	    throw new RuntimeException("Unable to find question justification text.");
	}
	String totalQuestions = null;
	if (uiReply.containsKey("TotalQuestions")){
	    UIDataItem textData = (UIDataItem) uiReply.get("TotalQuestions");
	    totalQuestions = textData.getStringProperty("TotalQuestions.STR");
	}else{
	    throw new RuntimeException("Unable to find total number of questions attribute.");
	}

	String id = null;
	if (uiReply.containsKey("Name")){
	    UIDataItem nameData = (UIDataItem) uiReply.get("Name");
	    id = nameData.getStringProperty("Name.STR");
	}else{
	    throw new RuntimeException("Unable to find question id/name.");
	}

	String buyPrice = null;
	if (uiReply.containsKey("BuyPrice")){
	    UIDataItem nameData = (UIDataItem) uiReply.get("BuyPrice");
	    buyPrice = nameData.getStringProperty("BuyPrice.STR");
	}

	String nextURI = null;
	if (uiReply.containsKey("nextUIRequest")){
	    UIRequest request = (UIRequest) uiReply.get("nextUIRequest");

	    if (request.containsKey("pricingQALink")){
		UIDataItem pricingQALinkData = (UIDataItem) request.get("pricingQALink");

		nextURI = pricingQALinkData.getStringProperty("pricingQAHref.STR");

	    }else{
		// last step, that page must be redirected to price summary screen
		if (redirect == false){
		    throw new RuntimeException("Unable to find pricingQALink.");
		}
	    }
	}else{
	    throw new RuntimeException("Unable to find nextUIRequest.");
	}

	buyPriceQuestion = new BuyPriceQuestion(id, questionText, nextURI, justificationText);

	buyPriceQuestion.setTotalNumberOfQuestions(totalQuestions);

	buyPriceQuestion.setFinalEsimatePrice(buyPrice);

	UIDataList optionsList = null;
	if (uiReply.containsKey("options")){
	    optionsList = (UIDataList) uiReply.get("options");
	    for(UIData current: optionsList){

		UIDataItem dataItem = (UIDataItem) current;

		String val = dataItem.getStringProperty("Value.STR");
		String desc = dataItem.getStringProperty("Description.STR");
		String price = dataItem.getStringProperty("AdjustmentDisplayText.STR");

		BuyPriceQuestionOption buyPriceQuestionOption = new BuyPriceQuestionOption(val, desc, price);

		buyPriceQuestion.addBuyPriceQuestionOption(buyPriceQuestionOption);
	    }
	}else{
	    throw new RuntimeException("Unable to find question id/name.");
	}

    }

}
