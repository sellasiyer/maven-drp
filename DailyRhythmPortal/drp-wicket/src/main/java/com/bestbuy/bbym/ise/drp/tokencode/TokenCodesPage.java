package com.bestbuy.bbym.ise.drp.tokencode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.wicket.ajax.AbstractAjaxTimerBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.Carrier;
import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.NewBaseNavPage;
import com.bestbuy.bbym.ise.drp.service.CarrierDataService;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

public class TokenCodesPage extends NewBaseNavPage {

    private static Logger logger = LoggerFactory.getLogger(TokenCodesPage.class);
    private static final long serialVersionUID = 1L;
    private Map<String, String> tokenCodesMap = new LinkedHashMap<String, String>();
    private boolean tokenExpiredFlag = false;
    private boolean getTokenCodesFlag = false;
    private final Label countdownTimerSeconds;
    private int sessionSecondVal;
    private String carrierName;
    private String TOKEN_EXPIRED = "TOKEN EXPIRED";
    private long timeDiffSeconds;
    private final FeedbackPanel feedbackPanel;
    private boolean serviceAvailable = true;
    private AbstractAjaxTimerBehavior tokenCodeTimer;
    final WebMarkupContainer tokenCodeListContainer;
    final Label tokenCodeExpiryLabel;

    @SpringBean(name = "carrierDataService")
    private CarrierDataService carrierDataService;

    public TokenCodesPage(final PageParameters parameters, final Carrier carrierTitle) {
	super(null);

	feedbackPanel = new FeedbackPanel("feedback");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	add(feedbackPanel);

	tokenCodeListContainer = new WebMarkupContainer("tokenCodeListContainer");

	tokenCodeExpiryLabel = new Label("tokenCodeExpiryLabel", "Token will expire in ");
	tokenCodeExpiryLabel.setOutputMarkupId(true);
	tokenCodeExpiryLabel.setOutputMarkupPlaceholderTag(true);
	add(tokenCodeExpiryLabel);

	tokenCodeTimer = new AbstractAjaxTimerBehavior(Duration.seconds(1)) {

	    private static final long serialVersionUID = 1L;

	    protected void onTimer(AjaxRequestTarget target) {
		target.add(countdownTimerSeconds);
	    }
	};

	add(tokenCodeTimer);

	carrierName = carrierTitle.getLabel();
	logger.info("CARRIER SELECTED = " + carrierName);

	DailyRhythmPortalSession session = getDailyRhythmPortalSession();
	if (getSessionTimeInstant()){// true means user has revisited this page

	    if (timeDiffSeconds >= 240){// token expired
		tokenCodesMap = getTokenCodes(carrierTitle);
	    }else{// token still alive, display original token data
		tokenCodesMap = session.getCarrierTokenDataList().get(carrierName);
	    }
	}else{// false means this is first time
	    tokenCodesMap = getTokenCodes(carrierTitle);
	}

	List<String> tokenCodeList = new ArrayList<String>();

	if (tokenCodesMap != null && !tokenCodesMap.isEmpty()){
	    Set<Map.Entry<String, String>> tokenCodesEntrySet = tokenCodesMap.entrySet();
	    Iterator<Map.Entry<String, String>> it = tokenCodesEntrySet.iterator();
	    Map.Entry<String, String> mapEntry;
	    while(it.hasNext()){
		mapEntry = it.next();
		tokenCodeList.add("<label>" + mapEntry.getKey() + ":</label><span>" + mapEntry.getValue() + "</span>");
	    }
	}

	final Label carrier = new Label("carrier", new CompoundPropertyModel<String>(carrierName));
	add(carrier);

	final ListView<String> tokenListView = new ListView<String>("tokenListView", tokenCodeList) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void populateItem(ListItem<String> item) {
		item.add(new Label("id", item.getModelObject()).setEscapeModelStrings(false));
	    }

	};
	tokenListView.setOutputMarkupId(true);

	tokenCodeListContainer.setOutputMarkupPlaceholderTag(true);
	tokenCodeListContainer.add(tokenListView);
	add(tokenCodeListContainer);

	countdownTimerSeconds = new Label("countdownTimerSeconds", new ClockModelSeconds(60, sessionSecondVal));// change
	// to
	// 60
	countdownTimerSeconds.setOutputMarkupId(true);
	add(countdownTimerSeconds);

	AjaxLink<Object> getTokenCodesLink = new AjaxLink<Object>("getTokenCodesLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		getTokenCodesFlag = true;

		tokenCodesMap = getTokenCodes(carrierTitle, target);
		target.add(tokenCodeListContainer);
		target.add(tokenCodeExpiryLabel);
		logger.info("getTokenCodes invoked for Carrier=" + carrierName);
		final PageParameters pp = null;
		if (serviceAvailable){
		    setResponsePage(new TokenCodesPage(pp, carrierTitle));
		}

	    }
	};
	getTokenCodesLink.setOutputMarkupPlaceholderTag(true);
	add(getTokenCodesLink);

    }

    private boolean getSessionTimeInstant() {

	DailyRhythmPortalSession session = getDailyRhythmPortalSession();
	if (session.getCarrierTokenFetchTimeMap() != null && !session.getCarrierTokenFetchTimeMap().isEmpty()
		&& session.getCarrierTokenFetchTimeMap().get(carrierName) != null){
	    timeDiffSeconds = (System.currentTimeMillis() - session.getCarrierTokenFetchTimeMap().get(carrierName)) / 1000;
	    sessionSecondVal = 239 - (int) (timeDiffSeconds % 60) - ((int) timeDiffSeconds / 60) * 60;
	    return true;
	}else
	    return false;
    }

    public class ClockModelSeconds implements IModel<String> {

	private long zeroTime;
	private int timeInSecs;

	public ClockModelSeconds(int timeInSecs, int startTime) {
	    this.zeroTime = System.currentTimeMillis() + (startTime * 1000);
	    this.timeInSecs = timeInSecs;
	}

	public String getObject() {
	    if (serviceAvailable){
		if (getTokenCodesFlag){
		    this.zeroTime = System.currentTimeMillis() + (sessionSecondVal * 1000);
		    getTokenCodesFlag = false;
		}
		if (((zeroTime - System.currentTimeMillis()) / 60000 == 0)
			&& (((zeroTime - System.currentTimeMillis()) / 1000) % 60) == 0){

		    tokenExpiredFlag = true;
		    tokenCodeTimer.stop();
		    return TOKEN_EXPIRED;
		}else{
		    if (!tokenExpiredFlag){
			if ((((zeroTime - System.currentTimeMillis()) / 1000) % 60) < 10)
			    return String.valueOf((zeroTime - System.currentTimeMillis()) / 60000) + ":0"
				    + String.valueOf((((zeroTime - System.currentTimeMillis()) / 1000) % 60));
			else
			    return String.valueOf((zeroTime - System.currentTimeMillis()) / 60000) + ":"
				    + String.valueOf((((zeroTime - System.currentTimeMillis()) / 1000) % 60));
		    }else
			return TOKEN_EXPIRED;
		}

	    }else{

		return "";
	    }
	}

	@Override
	public void detach() {
	}

	@Override
	public void setObject(String arg0) {
	}
    }

    public Map<String, String> getTokenCodes(Carrier carrierType, AjaxRequestTarget... target) {
	Map<String, String> tokenCodesMap = new LinkedHashMap<String, String>();

	if (target != null && target.length != 0){
	    if (!loadingModalPanel.isOpen()){
		loadingModalPanel.setMessage(getString("gspCancelCustomerLookup.customerSearch.loading.label"));
		loadingModalPanel.open(target[0]);
	    }
	}
	try{
	    tokenCodesMap = carrierDataService.getTokenCodes(carrierType, getDailyRhythmPortalSession().getDrpUser());
	}catch(ServiceException se){
	    if (target != null && target.length != 0)
		loadingModalPanel.close(target[0]);
	    logger.error(se.getMessage(), se);
	    serviceAvailable = false;
	    tokenCodeListContainer.setVisible(false);
	    tokenCodeExpiryLabel.setVisible(false);
	    tokenCodeTimer.stop();
	    error(se.getMessage());
	}catch(IseBusinessException be){
	    if (target != null && target.length != 0)
		loadingModalPanel.close(target[0]);
	    logger.error(be.getMessage(), be);
	    serviceAvailable = false;
	    tokenCodeListContainer.setVisible(false);
	    tokenCodeExpiryLabel.setVisible(false);
	    tokenCodeTimer.stop();
	    error(be.getMessage());
	}catch(Exception e){
	    if (target != null && target.length != 0)
		loadingModalPanel.close(target[0]);
	    logger.error(e.getMessage(), e);
	    serviceAvailable = false;
	    tokenCodeListContainer.setVisible(false);
	    tokenCodeExpiryLabel.setVisible(false);
	    tokenCodeTimer.stop();
	    error(e.getMessage());
	}
	if (target != null && target.length != 0)
	    loadingModalPanel.close(target[0]);
	if (serviceAvailable){
	    getDailyRhythmPortalSession().getCarrierTokenFetchTimeMap().put(carrierType.toString(),
		    new Long(System.currentTimeMillis()));
	}else{
	    getDailyRhythmPortalSession().getCarrierTokenFetchTimeMap().put(carrierType.toString(),
		    (new Long(System.currentTimeMillis() - 240 * 1000)));
	}

	getDailyRhythmPortalSession().getCarrierTokenDataList().put(carrierType.toString(), tokenCodesMap);
	sessionSecondVal = 239;
	tokenExpiredFlag = false;

	return tokenCodesMap;
    }

}
