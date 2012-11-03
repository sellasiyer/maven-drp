package com.bestbuy.bbym.ise.drp.loanerphone;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.NewBaseNavPage;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.LoanerPhoneDeletedReason;
import com.bestbuy.bbym.ise.drp.domain.Phone;
import com.bestbuy.bbym.ise.drp.service.LoanerPhoneService;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;

@AuthorizeInstantiation( {DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER, DrpConstants.DRP_LEAD })
public class EditPage extends NewBaseNavPage {

    private static Logger logger = LoggerFactory.getLogger(EditPage.class);
    private static final long serialVersionUID = 1L;

    @SpringBean(name = "loanerPhoneService")
    private LoanerPhoneService loanerPhoneService;

    private EditPage thisPage;
    final EditPanel loanerPhoneEditPanel;
    final CustomModalWindow deleteConfirmationModalWindow;
    private Phone editPhone;

    public EditPage(Phone phone, final Page returnPage, final Loadable returnLoadable, final Page returnParentPage,
	    final Loadable returnParentLoadable) {
	super(null);
	thisPage = this;

	editPhone = phone;
	logger.debug("editPhone=" + editPhone);

	Form<Object> form = new Form<Object>("editForm");
	add(form);

	loanerPhoneEditPanel = new EditPanel("loanerPhoneEditPanel", editPhone);
	form.add(loanerPhoneEditPanel);

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
	form.add(cancelButton);

	// FIXME use ModalPanel instead of CustomModalWindow
	deleteConfirmationModalWindow = new CustomModalWindow("deleteConfirmationModalWindow");
	deleteConfirmationModalWindow.setContent(new DeleteConfirmationPanel(deleteConfirmationModalWindow
		.getContentId(), editPhone, new DeleteConfirmationPanel.Observer() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void delete(AjaxRequestTarget target, LoanerPhoneDeletedReason deletedReason) {
		deleteConfirmationModalWindow.hide(target);
		String error = thisPage.delete(deletedReason);
		if (error != null){
		    error(error);
		    return;
		}
		// Return to parent page
		if (returnParentPage != null){
		    if (returnParentLoadable != null){
			returnParentLoadable.loadData();
		    }
		    setResponsePage(returnParentPage);
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

	    @Override
	    public void cancel(AjaxRequestTarget target) {
		deleteConfirmationModalWindow.hide(target);
	    }
	}));
	add(deleteConfirmationModalWindow);

	AjaxButton deleteButton = new AjaxButton("deleteButton") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		deleteConfirmationModalWindow.show(target);
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		// TODO Implement me!
	    }
	};
	deleteButton.setDefaultFormProcessing(false);
	form.add(deleteButton);

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
	form.add(saveButton);
    }

    private String delete(LoanerPhoneDeletedReason deletedReason) {
	try{
	    // We don't actually delete phones. We just mark them as inactive.
	    DailyRhythmPortalSession drpSession = getDailyRhythmPortalSession();
	    DrpUser user = drpSession.getDrpUser();
	    editPhone.setModifiedOn(new java.util.Date());
	    editPhone.setModifiedBy(user.getUserId());
	    editPhone.setLastActionUserId(user.getUserId());
	    editPhone.setDeletedReason(deletedReason);
	    editPhone.setActive(false);
	    editPhone.setLastName(user.getLastName());
	    editPhone.setFirstName(user.getFirstName());
	    loanerPhoneService.updatePhone(editPhone);
	    return null;
	}catch(ServiceException ex){
	    if (ex.getErrorCode() == IseExceptionCodeEnum.DeactivatedCheckedOutLoanerPhone){
		return "You cannot delete a checked out phone. Please execute the check in process before deleting this phone.";
	    }else{
		logger.error("Failed to delete phone", ex);
		return ex.getMessage();
	    }
	}
    }

    private String save() {
	Phone phone = loanerPhoneEditPanel.getPhone();
	try{
	    DailyRhythmPortalSession drpSession = getDailyRhythmPortalSession();
	    DrpUser user = drpSession.getDrpUser();
	    phone.setModifiedOn(new java.util.Date());
	    phone.setModifiedBy(user.getUserId());
	    phone.setLastActionUserId(user.getUserId());
	    phone.setLastName(user.getLastName());
	    phone.setFirstName(user.getFirstName());
	    loanerPhoneService.updatePhone(phone);
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
