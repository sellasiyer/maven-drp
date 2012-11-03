package com.bestbuy.bbym.ise.drp.beast.common;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.common.BaseWebPage;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

@AuthorizeInstantiation(DrpConstants.DRP_BEAST)
public abstract class BaseBeastPage2 extends BaseWebPage {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(BaseBeastPage2.class);

    private AbstractDefaultAjaxBehavior wicketBehavior;

    protected LoadingModalPanel loadingModalPanel;
    protected OkCancelModalPanel quitModalPanel;
    protected MessageModalPanel messageModalPanel;
    protected OkCancelModalPanel clearModalPanel;
    protected ServiceErrorMessageModalPanel serviceErrorMsgModalPanel;

    protected Form<Object> form;

    public BaseBeastPage2() {
	this(null);
    }

    public BaseBeastPage2(PageParameters parameters) {
	super(parameters);
	init();
    }

    private void init() {
	getSession().clear();

	form = new Form<Object>("form");
	form.setOutputMarkupPlaceholderTag(true);
	add(form);

	for(int i = 1; i <= 7; i++){
	    addButton(i);
	}

	loadingModalPanel = new LoadingModalPanel("loadingModalPanel") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		loadingModalPanelOnClose(target);
	    }
	};
	loadingModalPanel.setOutputMarkupPlaceholderTag(true);
	add(loadingModalPanel);

	quitModalPanel = new OkCancelModalPanel("quitModalPanel", "Yes", "No") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		quitModalPanelOnClose(target);
	    }
	};
	quitModalPanel.setOutputMarkupPlaceholderTag(true);
	add(quitModalPanel);

	messageModalPanel = new MessageModalPanel("messageModalPanel", "OK") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		messageModalPanelOnClose(target);
	    }
	};
	messageModalPanel.setOutputMarkupPlaceholderTag(true);
	add(messageModalPanel);

	clearModalPanel = new OkCancelModalPanel("clearModalPanel", "Yes", "No") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		clearModalPanelOnClose(target);
	    }
	};
	clearModalPanel.setOutputMarkupPlaceholderTag(true);
	add(clearModalPanel);

	serviceErrorMsgModalPanel = new ServiceErrorMessageModalPanel("serviceErrorMsgModalPanel", "OK") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		// TODO Auto-generated method stub

	    }
	};
	serviceErrorMsgModalPanel.setOutputMarkupPlaceholderTag(true);
	add(serviceErrorMsgModalPanel);

	// means by which JS can callback into Java
	wicketBehavior = new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
		wicketBehaviorRespond(target);
	    }
	};
	add(wicketBehavior);

	add(new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
	    }

	    @Override
	    public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		StringBuilder onDomReadyJS = new StringBuilder();
		onDomReadyJS.append("wicketBehavior = function(id) { ");
		onDomReadyJS.append("wicketAjaxGet('");
		onDomReadyJS.append(wicketBehavior.getCallbackUrl());
		onDomReadyJS.append("&id='+id); };");
		onDomReadyJS.append("bindHotKeys2();");
		onDomReadyJS.append(getOnDomReadyJS());
		logger.debug("onDomReadyJS=" + onDomReadyJS);
		response.renderOnDomReadyJavaScript(onDomReadyJS.toString());
	    }
	});
    }

    private void addButton(final int buttonId) {
	WebMarkupContainer btnContainer;
	AjaxButton button;

	boolean defaultFormProcessingProperty = getDefaultFormProcessingProperty(buttonId);

	String buttonName = getButtonName(buttonId);
	if (buttonName == null){
	    btnContainer = new WebMarkupContainer("btn" + buttonId + "Container") {

		private static final long serialVersionUID = 1L;

		@Override
		public boolean isVisible() {
		    return false;
		}
	    };
	    btnContainer.setOutputMarkupPlaceholderTag(true);
	    form.add(btnContainer);
	    button = new AjaxButton("button" + buttonId, null, form) {

		private static final long serialVersionUID = 1L;

		@Override
		protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		}

		@Override
		protected void onError(AjaxRequestTarget target, Form<?> form) {
		}
	    };
	    btnContainer.add(button);
	}else{
	    btnContainer = new WebMarkupContainer("btn" + buttonId + "Container");
	    btnContainer.setOutputMarkupPlaceholderTag(true);
	    form.add(btnContainer);
	    button = new AjaxButton("button" + buttonId, getButtonModel(buttonId), form) {

		private static final long serialVersionUID = 1L;

		@Override
		protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		    onButtonSubmit(buttonId, target);
		}

		@Override
		protected void onError(AjaxRequestTarget target, Form<?> form) {
		}

	    };
	    button.setMarkupId(buttonName);
	    button.setOutputMarkupPlaceholderTag(true);
	    btnContainer.add(button);
	    button.setDefaultFormProcessing(defaultFormProcessingProperty);

	}
	String fctKey = getButtonFunctionKey(buttonId);
	btnContainer.add(new Label("fctKey" + buttonId, fctKey));
	String btnStyleClasses = getButtonStyleClasses(buttonId);
	if (btnStyleClasses != null){
	    btnContainer.add(AttributeModifier.replace("class", btnStyleClasses));
	}
    }

    protected void loadingModalPanelOnClose(AjaxRequestTarget target) {
    }

    protected void quitModalPanelOnClose(AjaxRequestTarget target) {
    }

    protected void messageModalPanelOnClose(AjaxRequestTarget target) {
    }

    protected void clearModalPanelOnClose(AjaxRequestTarget target) {
    }

    // return null if no button needed
    // id must be 1-7 inclusive
    protected abstract String getButtonName(int buttonId);

    // return null if no button needed
    // id must be 1-7 inclusive
    protected abstract String getButtonFunctionKey(int buttonId);

    // return null if no button needed
    // id must be 1-7 inclusive
    protected abstract String getButtonStyleClasses(int buttonId);

    protected abstract void onButtonSubmit(int buttonId, AjaxRequestTarget target);

    // return null if no button needed
    // id must be 1-7 inclusive
    protected abstract IModel<String> getButtonModel(int buttonId);

    protected abstract void wicketBehaviorRespond(AjaxRequestTarget target);

    // return "" if not used
    protected abstract String getOnDomReadyJS();

    protected boolean getDefaultFormProcessingProperty(int buttonId) {
	return true;
    }
}
