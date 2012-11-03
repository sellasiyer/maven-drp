package com.bestbuy.bbym.ise.drp.loanerphone;

import org.apache.wicket.Page;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.NewBaseNavPage;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.Phone;
import com.bestbuy.bbym.ise.drp.service.LoanerPhoneService;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;

@AuthorizeInstantiation( {DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER, DrpConstants.DRP_LEAD })
public class AddPage extends NewBaseNavPage {

    private static Logger logger = LoggerFactory.getLogger(AddPage.class);
    private static final long serialVersionUID = 1L;

    @SpringBean(name = "loanerPhoneService")
    private LoanerPhoneService loanerPhoneService;

    final EditPanel loanerPhoneEditPanel;

    public AddPage(final Page returnPage, final Loadable returnLoadable) {
	super(null);

	Form<Object> addForm = new Form<Object>("addForm");
	add(addForm);

	loanerPhoneEditPanel = new EditPanel("loanerPhoneEditPanel");
	addForm.add(loanerPhoneEditPanel);

	final Button cancelButton = new Button("cancelButton") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onSubmit() {
		logger.debug("cancelButton onSubmit");
		if (returnLoadable != null){
		    returnLoadable.loadData();
		}
		if (returnPage != null){
		    setResponsePage(returnPage);
		}else{
		    setResponsePage(LoanerPhoneLandingPage.class);
		}
	    }
	};
	cancelButton.setDefaultFormProcessing(false);
	addForm.add(cancelButton);

	final Button saveButton = new Button("saveButton") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onSubmit() {
		String error = save();
		if (error != null){
		    error(error);
		    return;
		}
		if (returnLoadable != null){
		    returnLoadable.loadData();
		}
		if (returnPage != null){
		    setResponsePage(returnPage);
		}else{
		    setResponsePage(LoanerPhoneLandingPage.class);
		}
	    }
	};
	addForm.add(saveButton);
    }

    private String save() {
	Phone phone = loanerPhoneEditPanel.getPhone();
	try{
	    DailyRhythmPortalSession drpSession = getDailyRhythmPortalSession();
	    DrpUser user = drpSession.getDrpUser();
	    phone.setCreatedOn(new java.util.Date());
	    phone.setCreatedBy(user.getUserId());
	    phone.setLastActionUserId(user.getUserId());
	    phone.setLastName(user.getLastName());
	    phone.setFirstName(user.getFirstName());
	    phone.setStoreId(loanerPhoneEditPanel.getStoreID());
	    phone.setActive(true);
	    loanerPhoneService.createPhone(phone);
	    return null;
	}catch(ServiceException ex){
	    if (ex.getErrorCode() == IseExceptionCodeEnum.DuplicateLoanerPhone){
		return "This IMEI/ESN already exists: " + phone.getSerialNumber();
	    }else{
		logger.error("Failed to save phone", ex);
		return ex.getMessage();
	    }
	}
    }
}
