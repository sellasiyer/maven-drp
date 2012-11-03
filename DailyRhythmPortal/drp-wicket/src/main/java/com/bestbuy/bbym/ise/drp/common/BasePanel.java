package com.bestbuy.bbym.ise.drp.common;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.service.DrpPropertyService;
import com.bestbuy.bbym.ise.exception.IseBaseException;
import com.bestbuy.bbym.ise.exception.ServiceException;

public class BasePanel extends Panel {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(BasePanel.class);

    @SpringBean(name = "drpPropertyService")
    private DrpPropertyService drpPropertyService;

    public BasePanel(String id) {
	super(id);
    }

    public BasePanel(String id, IModel<?> model) {
	super(id, model);
    }

    public DailyRhythmPortalSession getDailyRhythmPortalSession() {
	return (DailyRhythmPortalSession) getSession();
    }

    public void processException(String message, IseBaseException iseBaseException, final PageParameters parameters) {
	ExceptionPageHandler.processException(message, iseBaseException, parameters, getSession());
    }

    public void processException(String message, IseBaseException iseBaseException, long workflowId,
	    String workflowName, final PageParameters parameters) {
	ExceptionPageHandler.processException(message, iseBaseException, workflowId, workflowName, parameters,
		getSession());
    }

    public String getStoreID() throws ServiceException {
	if (isDummyStoreEnabled()){
	    return getDummyStoreNum();
	}else{
	    DrpUser user = getDailyRhythmPortalSession().getDrpUser();
	    return user.getStoreId();
	}
    }

    public boolean isDummyStoreEnabled() throws ServiceException {
	return accessDummyStoreEnabled(drpPropertyService);
    }

    public String getDummyStoreNum() throws ServiceException {
	return accessDummyStoreNum(drpPropertyService);
    }

    public boolean getBooleanProperty(String name, boolean defaultValue) {
	return accessBooleanProperty(drpPropertyService, name, defaultValue);
    }

    public static boolean accessDummyStoreEnabled(DrpPropertyService drpPropertyService) throws ServiceException {
	String dummyStoreEnabled = drpPropertyService.getProperty("DUMMY_STORE_ENABLED");
	if (StringUtils.isNotBlank(dummyStoreEnabled)){
	    return Boolean.parseBoolean(dummyStoreEnabled);
	}else{
	    return false;
	}
    }

    public static String accessDummyStoreNum(DrpPropertyService drpPropertyService) throws ServiceException {
	String dummyStoreNum = drpPropertyService.getProperty("DUMMY_STORE_NUM");
	if (StringUtils.isNotBlank(dummyStoreNum)){
	    return dummyStoreNum;
	}else{
	    return "0699"; // This value is configured in both CAP and UCS.
	}
    }

    public static boolean accessBooleanProperty(DrpPropertyService drpPropertyService, String name, boolean defaultValue) {
	String value = null;
	try{
	    value = drpPropertyService.getProperty(name);
	    if (value == null){
		return defaultValue;
	    }
	    if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("yes")){
		return true;
	    }
	    return false;
	}catch(ServiceException se){
	    logger.error("ServiceException getting property value for " + name, se);
	    return defaultValue;
	}
    }

    public void doAction(String action, AjaxRequestTarget target) {
	logger.warn("Action not handled: " + action);
    }

    public final void launchAction(String action, AjaxRequestTarget target) {
	Page parentPage = getPage();
	if (parentPage instanceof NewBaseNavPage){
	    ((NewBaseNavPage) parentPage).launchAction(action, target);
	}else{
	    throw new RuntimeException("Cannot call launchAction unless parent page is derived from class "
		    + NewBaseNavPage.class.getName());
	}
    }

    public String getOnDomReadyJS() {
	return "";
    }

    public final void openLoadingModal(final String message, AjaxRequestTarget target) {
	Page parentPage = getPage();
	if (parentPage instanceof NewBaseNavPage){
	    ((NewBaseNavPage) parentPage).openLoadingModal(message, target);
	}else{
	    throw new RuntimeException("Cannot call openLoadingModal unless parent page is derived from class "
		    + NewBaseNavPage.class.getName());
	}
    }

    public final void closeLoadingModal(AjaxRequestTarget target) {
	Page parentPage = getPage();
	if (parentPage instanceof NewBaseNavPage){
	    ((NewBaseNavPage) parentPage).closeLoadingModal(target);
	}else{
	    throw new RuntimeException("Cannot call closeLoadingModal unless parent page is derived from class "
		    + NewBaseNavPage.class.getName());
	}
    }

    public final boolean isLoadingModalOpen() {
	Page parentPage = getPage();
	if (parentPage instanceof NewBaseNavPage){
	    return ((NewBaseNavPage) parentPage).isLoadingModalOpen();
	}else{
	    throw new RuntimeException("Cannot call isLoadingModalOpen unless parent page is derived from class "
		    + NewBaseNavPage.class.getName());
	}
    }

    public String getLaunchAction() {
	return null;
    }

    public boolean canHandleAction(String action) {
	return false;
    }
}
