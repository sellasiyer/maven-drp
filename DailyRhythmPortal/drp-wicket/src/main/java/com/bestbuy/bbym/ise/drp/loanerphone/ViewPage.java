package com.bestbuy.bbym.ise.drp.loanerphone;

import java.util.List;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.bestbuy.bbym.ise.drp.common.NewBaseNavPage;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.Phone;
import com.bestbuy.bbym.ise.drp.helpers.PhoneSearchCriteria;
import com.bestbuy.bbym.ise.drp.service.LoanerPhoneService;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;

public class ViewPage extends NewBaseNavPage implements Loadable {

    private static Logger logger = LoggerFactory.getLogger(ViewPage.class);
    private static final long serialVersionUID = 1L;

    @SpringBean(name = "loanerPhoneService")
    private LoanerPhoneService loanerPhoneService;

    private ViewPage thisPage;
    private Phone viewPhone;
    private ViewPanel viewPanel;

    public ViewPage(Phone phone, final Page returnPage, final Loadable returnLoadable) {
	super(null);
	thisPage = this;

	viewPhone = phone;

	Form<Object> form = new Form<Object>("viewForm");
	add(form);

	viewPanel = new ViewPanel("viewPanel", viewPhone);
	form.add(viewPanel);

	final Button editButton = new Button("editButton") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onSubmit() {
		if (viewPhone != null && viewPhone.getLatestCheckOutCheckInHistory() != null
			&& viewPhone.getLatestCheckOutCheckInHistory().isCheckedOut()){
		    error(getString("inventoryForm.editLink.checkedOutPhone.error.label"));
		    return;
		}
		Phone phone = new Phone();
		BeanUtils.copyProperties(viewPhone, phone);
		setResponsePage(new EditPage(phone, getPage(), thisPage, returnPage, returnLoadable));
	    }
	};
	if (getDailyRhythmPortalSession().isAuthorizedToInstantiate(EditPage.class)){
	    editButton.setVisible(true);
	}else{
	    editButton.setVisible(false);
	}
	form.add(editButton);

	final Button closeButton = new Button("closeButton") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onSubmit() {
		logger.debug("closeButton onSubmit");
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
	form.add(closeButton);
    }

    @Override
    public void loadData() {
	logger.debug("loadData");
	Long phoneId = null;
	final DrpUser user = getDailyRhythmPortalSession().getDrpUser();
	try{
	    if (viewPhone != null){
		phoneId = viewPhone.getId();
	    }
	    if (phoneId == null){
		throw new ServiceException(IseExceptionCodeEnum.GeneralSystem, "phone ID is invalid");
	    }
	    PhoneSearchCriteria criteria = new PhoneSearchCriteria();
	    List<Phone> phoneList;
	    if (isDummyStoreEnabled()){
		phoneList = loanerPhoneService.getPhones(getDummyStoreNum(), criteria);
	    }else{
		phoneList = loanerPhoneService.getPhones(user.getStoreId(), criteria);
	    }
	    Phone phone = null;
	    if (phoneList != null){
		for(Phone ph: phoneList){
		    if (phoneId.equals(ph.getId())){
			viewPhone = ph;
			logger.debug("phone=" + viewPhone);
			viewPanel.setPhone(viewPhone);
			return;
		    }
		}
	    }
	    if (phone == null){
		throw new ServiceException(IseExceptionCodeEnum.GeneralSystem, "phone not in database");
	    }
	}catch(ServiceException e){
	    String message = "An unexpected exception occured while attempting to get the phone with ID " + phoneId;
	    logger.error(message, e);
	    processException(message, e);
	}
    }
}
