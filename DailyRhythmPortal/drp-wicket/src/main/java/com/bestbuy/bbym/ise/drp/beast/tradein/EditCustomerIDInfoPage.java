package com.bestbuy.bbym.ise.drp.beast.tradein;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.beast.tradein.mapper.CustomerQABean;
import com.bestbuy.bbym.ise.drp.beast.tradein.validator.AgeValidator;
import com.bestbuy.bbym.ise.drp.beast.tradein.validator.LengthValidator;
import com.bestbuy.bbym.ise.drp.beast.tradein.validator.TodayDateValidator;
import com.bestbuy.bbym.ise.drp.domain.TitanDevice;
import com.bestbuy.bbym.ise.drp.domain.ui.UIDataItem;
import com.bestbuy.bbym.ise.drp.domain.ui.UIDataList;
import com.bestbuy.bbym.ise.drp.domain.ui.UIReply;
import com.bestbuy.bbym.ise.drp.domain.ui.UIRequest;
import com.bestbuy.bbym.ise.drp.service.UIService;
import com.bestbuy.bbym.ise.drp.util.SelectItem;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * @author a915726
 */
public class EditCustomerIDInfoPage extends BeastPage {

    private final static DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
    private static final long serialVersionUID = 1L;
    private CustomerQABean customerQABean;
    private String dataSharingKey;
    private SelectItem<String> idIssuerSelectionSelected;
    private String idNumber;
    private String idDateIssued;
    private String idExpiration;
    @SpringBean(name = "uiService")
    private UIService uiService;
    private UIReply uiReply;
    private UIRequest uiRequest;
    private TitanDevice titanDevice;
    private static Logger LOGGER = LoggerFactory.getLogger(EditCustomerIDInfoPage.class);
    private String questionsNumber;

    public String getQuestionsNumber() {
	return questionsNumber;
    }

    public void setQuestionsNumber(String questionsNumber) {
	this.questionsNumber = questionsNumber;
    }

    public EditCustomerIDInfoPage(final PageParameters parameters, UIReply uiReply) {
	super(parameters);
	this.backButton.setVisible(false);
	//EditCustomerAnswers

	this.uiRequest = (UIRequest) uiReply.get("EditCustomerAnswers");

	getBorder().setPageTitle(getString("tradeInCustomerQAPage.customerInformation.label"));

	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");

	this.uiReply = null;

	try{
	    if (uiRequest != null)
		this.uiReply = uiService.processUIRequest(uiRequest);
	    else
		this.uiReply = uiReply;

	    populatePageTitle(this.uiReply, "LoadGiftCard", "EditCustomerAnswers");

	    UIDataList qList = (UIDataList) this.uiReply.get("Questions");
	    this.uiRequest = (UIRequest) this.uiReply.get("LoadGiftCard");
	    UIDataItem requestDataItem = (UIDataItem) uiRequest.get("EditCustomerAnswers");
	    requestDataItem.setStringProp("userId", getDailyRhythmPortalSession().getDrpUser().getUserId());

	}catch(ServiceException ex){
	    LOGGER.error(ex.getMessage());
	    displayError("Service error or unavailable", null);
	}catch(IseBusinessException ex){
	    LOGGER.error(ex.getMessage());
	    displayError("Service error or unavailable", null);
	}

	titanDevice = getDailyRhythmPortalSession().getTitanDevice();

	customerQABean = getDailyRhythmPortalSession().getCustomerQABean();
	if (customerQABean == null){
	    customerQABean = new CustomerQABean();

	}else{

	    idNumber = getLast4ID(customerQABean.getIdNumber());
	    if (customerQABean.getIdDateIssued() != null){
		idDateIssued = sdf.format(customerQABean.getIdDateIssued());
	    }
	    if (customerQABean.getIdExpiration() != null){
		idExpiration = sdf.format(customerQABean.getIdExpiration());
	    }

	}

	this.readQuestions(this.uiReply);

	try{
	    populateQuestionValues(titanDevice.getCarrierAccount());

	    ProductQuestion dob = getProductQuestionByQname("17");
	    if (dob != null){
		dob.setDate(titanDevice.getCarrierAccount().getDob());
		dob.addValidator(new AgeValidator());
	    }

	    ProductQuestion idCode = getProductQuestionByQname("ID NUMBER");
	    if (idCode != null){
		idCode.setValue(getLast4ID(titanDevice.getCarrierAccount().getIdCode()));
		idCode.addValidator(new LengthValidator(1, 50, idCode.getName()));
	    }

	    ProductQuestion idExpirationDate = getProductQuestionByQname("ID EXPIRATION DATE");
	    if (idExpirationDate != null){
		idExpirationDate.setDate(titanDevice.getCarrierAccount().getIdExpirationDate());
		idExpirationDate.addValidator(new TodayDateValidator(idExpirationDate.getName(), true));
	    }

	    ProductQuestion idIssuer = getProductQuestionByQname("PRIMARY ID ISSUER");
	    if (idIssuer != null){
		idIssuer.markOptionsAsSelected(titanDevice.getCarrierAccount().getIdIssuer());
	    }

	    ProductQuestion idIssuerDate = getProductQuestionByQname("ID ISSUE DATE");
	    if (idIssuerDate != null){
		idIssuerDate.addValidator(new TodayDateValidator(idExpirationDate.getName(), false));
	    }

	}catch(IllegalArgumentException e){
	    LOGGER.error(e.getMessage());
	}catch(IllegalAccessException e){
	    LOGGER.error(e.getMessage());
	}

	ListView<ProductQuestion> listview = createListView();
	add(listview);

	this.questionsNumber = new Integer(questions.size()).toString();

	//textField = new TextField<String>("text", new PropertyModel<String>(question, "value"));
	HiddenField<String> hiddenField = new HiddenField<String>("questionsNumber", new PropertyModel<String>(this,
		"questionsNumber"));

	hiddenField.setMarkupId("questionsNumber");
	hiddenField.setOutputMarkupId(true);
	add(hiddenField);
	if (this.questions == null || this.questions.size() == 0){
	    getBorder().setButtonEnabled(nextButton, true);
	}
	validateQuestions();
    }

    public SelectItem<String> getIdIssuerSelectionSelected() {
	return idIssuerSelectionSelected;
    }

    public void setIdIssuerSelectionSelected(SelectItem<String> idIssuerSelectionSelected) {
	this.idIssuerSelectionSelected = idIssuerSelectionSelected;
    }

    // prefix mask
    private String getLast4ID(String id) {
	String tmpId = id;
	if (id != null && id.length() > 4){
	    tmpId = "*****" + id.substring(id.length() - 4);
	}
	return tmpId;
    }

    @Override
    void clearForm(AjaxRequestTarget target) {
	this.getDailyRhythmPortalSession().setCustomerQABean(null);
	this.setResponsePage(EditCustomerIDInfoPage.class);

	target.appendJavaScript("bindHotKeys();");
    }

    @Override
    void goBack(AjaxRequestTarget target) {
	this.addBackButtonParameter();
	this.setResponsePage(new EditCustomerBasicInfoPage(this.getPageParameters(), this.uiReply));
    }

    @Override
    void nextPage(AjaxRequestTarget target, Form<?> form) {

	ProductQuestion idCode = getProductQuestionByQname("ID NUMBER");

	if (idCode != null && idCode.getValue().indexOf("*****") != -1){
	    idCode.setValue(titanDevice.getCarrierAccount().getIdCode());
	}

	try{

	    for(ProductQuestion current: questions){
		if (current.getType() == QuestionType.TEXT){
		    current.getUiDataItem().setStringProp("Value", current.getValue());
		    current.getUiDataItem().setStringProp("userId",
			    getDailyRhythmPortalSession().getDrpUser().getUserId());
		}else if (current.getType() == QuestionType.DATE){
		    current.getUiDataItem().setStringProp("Value", formatter.format(current.getDate()));
		    current.getUiDataItem().setStringProp("userId",
			    getDailyRhythmPortalSession().getDrpUser().getUserId());
		}else if (current.getType() == QuestionType.SELECT){
		    current.getUiDataItem().setStringProp("Value", current.getSelectOption().getKey());
		    current.getUiDataItem().setStringProp("userId",
			    getDailyRhythmPortalSession().getDrpUser().getUserId());
		}
	    }
	    this.getDailyRhythmPortalSession().saveUIReply(this.getClass().getName(), this.uiReply);

	    logger.debug(">>> NEXT UI Request " + this.uiRequest.toString());

	    this.getDailyRhythmPortalSession().setCustomerQABean(customerQABean);
	    uiReply = uiService.processUIRequest(uiRequest);
	    //TODO: change after titan sending the correct resource.
	    if (uiReply.getName().equals("TitanError")){
		UIDataList dl = (UIDataList) uiReply.get("ErrorList");
		if (dl.isEmpty())
		    this.setResponsePage(new DoNotBuyListPage(this.getPageParameters()));

	    }else
		this.setResponsePage(new LoadGiftCardPage(this.getPageParameters(), uiReply));
	}catch(Exception exc){
	    LOGGER.error(exc.getMessage(), exc);
	    this.displayError(this.getString("EditCustomerIDInfoPage.service.down.message"), target);
	    return;
	}

    }
}
