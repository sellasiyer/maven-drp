package com.bestbuy.bbym.ise.drp.error;

import java.util.Iterator;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.FeedbackMessages;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.bestbuy.bbym.ise.drp.common.BaseNavPage;
import com.bestbuy.bbym.ise.drp.common.PageParameterKeys;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;

/**
 * @author a186288
 */
@AuthorizeInstantiation( {DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER, DrpConstants.DRP_LEAD,
	DrpConstants.DRP_USER, DrpConstants.SHP_USER, DrpConstants.SHP_MANAGER, DrpConstants.DRP_TEAM,
	DrpConstants.DRP_BEAST })
public class DRPErrorPage extends BaseNavPage {

    private static final long serialVersionUID = 1L;

    public DRPErrorPage(final PageParameters parameters) {
	super(parameters);
	FeedbackMessages messages = getSession().getFeedbackMessages();
	if (messages.isEmpty()){
	    add(new Label("message", new String("")));
	}else{
	    Iterator<FeedbackMessage> iterator = messages.iterator();
	    FeedbackMessage message = iterator.next();
	    add(new Label("message", (String) message.getMessage()));

	    // mark message as rendered so it will not be displayed again
	    message.markRendered();
	}

	add(new Label("exceptionCodeLabel", new ResourceModel("exceptionCode.label")));
	add(new Label("exceptionCodeDescriptionLabel", new ResourceModel("exceptionCodeDescription.label")));

	String exceptionCode = parameters.get(PageParameterKeys.EXCEPTION_CODE.name()).toOptionalString();
	IseExceptionCodeEnum codeEnum = exceptionCode == null?null:IseExceptionCodeEnum.valueOf(exceptionCode);
	String code = codeEnum != null?Integer.toString(codeEnum.getCode()):getString("noCode.label");
	add(new Label("exceptionCodeValue", code));

	String codeDescription = codeEnum != null?codeEnum.getDescription():getString("noCodeDescription.label");
	add(new Label("exceptionCodeDescription", codeDescription));

	BookmarkablePageLink link = homePageLink("homePageLink");
	link.add(new Label("returnMainScreen", new ResourceModel("returnMainScreen.label")));
	add(link);
    }
}
