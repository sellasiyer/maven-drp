package com.bestbuy.bbym.ise.drp.rewardzone;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.common.BasePanel;
import com.bestbuy.bbym.ise.drp.domain.RewardZone;
import com.bestbuy.bbym.ise.drp.domain.RwzMemberCert;
import com.bestbuy.bbym.ise.drp.service.RewardZoneService;
import com.bestbuy.bbym.ise.drp.util.DateFormatter;
import com.bestbuy.bbym.ise.drp.util.FeedbackMessageFilter;
import com.bestbuy.bbym.ise.drp.util.FormatModel;
import com.bestbuy.bbym.ise.drp.util.IntegerFormatter;
import com.bestbuy.bbym.ise.drp.util.MoneyFormatter;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

public class RewardZoneTabPanel extends BasePanel {

    private static Logger logger = LoggerFactory.getLogger(RewardZoneTabPanel.class);
    private static final long serialVersionUID = 1L;

    @SpringBean(name = "rewardZoneService")
    private RewardZoneService rewardZoneService;

    String showHideButtonText = getString("rewardZone.showHideButton.showText");

    String rzMemNumber = null;

    public RewardZoneTabPanel(String id, String memberNumber, final FeedbackPanel feedbackPanel,
	    final Form<Object> form, final FeedbackMessageFilter filter) {
	super(id);
	this.rzMemNumber = memberNumber;

	RewardZone rzInfo = new RewardZone();
	final DateFormatter<Date> dateFmt = new DateFormatter<Date>();
	final String na = getString("notApplicable.label");

	try{

	    rzInfo = rewardZoneService.getRewardZonePointsAndCerts(memberNumber);

	}catch(ServiceException se){
	    String message = "An unexpected exception occured while attempting to get the Reward Zone Data for the customer";
	    logger.error(message, se);
	    error(se.getFullMessage());
	}catch(IseBusinessException be){
	    String message = "An unexpected exception occured while attempting to get the Reward Zone Data for the customer";
	    logger.error(message);
	    error(be.getFullMessage());
	}

	String url = "";
	Image memberTypeImage = new NonCachingImage("memberTypeImage");
	if (RewardZone.ACCOUNT_STATUS_A.equalsIgnoreCase(rzInfo.getAccountStatus())){
	    String memTier = rzInfo.getMemberTier();
	    if (RewardZone.MEMBER_TIER_SILVER.equalsIgnoreCase(memTier)){
		url = "/drp-war/images/rewardzone_silver.jpg";
	    }else if (RewardZone.MEMBER_TIER_PREMIUM.equalsIgnoreCase(memTier)){
		url = "/drp-war/images/rewardzone_premier_silver.jpg";
	    }
	}else{
	    url = "/drp-war/images/rewardzone_pending.jpg";
	}

	if (url.equals("")){
	    memberTypeImage.setVisible(false);
	}

	memberTypeImage.add(AttributeModifier.replace("src", url));
	add(memberTypeImage);

	int numAvailableCerts = calculateAvailableCertAmt(rzInfo.getRwzMemberCertList());

	Label availableCertificatesLabel = new Label("availableCertificatesLabel", new FormatModel<String>(String
		.valueOf(numAvailableCerts), new MoneyFormatter<String>(true), "$0.0"));

	add(availableCertificatesLabel);

	Label availablePointsLabel = new Label("availablePointsLabel", new FormatModel<Integer>(
		new PropertyModel<Integer>(rzInfo, "pointsBalance"), new IntegerFormatter<Integer>()));
	add(availablePointsLabel);

	int pointsWorth = rzInfo.getConvertedPointsDollarAmt();
	Label pointsWorthValue = new Label("pointsWorthValue", getString("rewardZone.pointsWorth.label").replace(
		"${amount}", new MoneyFormatter<String>(true, false).format(String.valueOf(pointsWorth))));
	add(pointsWorthValue);

	int totalAvailable = (numAvailableCerts + pointsWorth);
	Label totalAvailableLabel = new Label("totalAvailableLabel", new FormatModel<String>(String
		.valueOf(totalAvailable), new MoneyFormatter<String>(true), "$0.0"));
	add(totalAvailableLabel);

	List<RwzMemberCert> certificateList = rzInfo.getRwzMemberCertList();

	if (certificateList != null){
	    Collections.sort(certificateList, RwzMemberCert.getExpiryDateComparator());
	}

	ListView<RwzMemberCert> certificateListView = new ListView<RwzMemberCert>("certificateListView",
		certificateList) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void populateItem(ListItem<RwzMemberCert> item) {
		RwzMemberCert cert = item.getModelObject();
		Label amountLabel = new Label("amountLabel", new FormatModel<String>(Integer.toString(cert
			.getCertAmount()), new MoneyFormatter<String>(true), "0.00"));
		item.add(amountLabel);

		Label expirationLabel = new Label("expirationLabel", new FormatModel<Date>(cert.getExpiredDate(),
			dateFmt, na));
		item.add(expirationLabel);

	    }
	};
	add(certificateListView);

	ExternalLink rewardZoneLink = new ExternalLink("rewardZoneLink", getString("rewardZone.visit.linkurl"));
	add(rewardZoneLink);

    }

    public RewardZoneTabPanel(String id) {
	super(id);
    }

    public int calculateAvailableCertAmt(List<RwzMemberCert> rzCertList) {
	int certAmt = 0;
	if (rzCertList != null && rzCertList.size() > 0){
	    for(RwzMemberCert rwzMemberCert: rzCertList){
		certAmt += rwzMemberCert.getCertAmount();
	    }
	}
	return certAmt;
    }

    public class RewardZoneNoDataTabPanel extends BasePanel {

	private static final long serialVersionUID = 1L;

	public RewardZoneNoDataTabPanel(String id) {
	    super(id);

	    final WebMarkupContainer noRZData = new WebMarkupContainer("noRZData") {

		private static final long serialVersionUID = 1L;

		@Override
		public boolean isVisible() {
		    return true;
		}
	    };
	    noRZData.setOutputMarkupPlaceholderTag(true);
	    add(noRZData);

	    Label noRZDataLabel = new Label("noRZDataLabel", new Model<String>() {

		private static final long serialVersionUID = 1L;

		@Override
		public String getObject() {
		    return getString("rewardZone.noData.label");
		}
	    });
	    noRZDataLabel.setOutputMarkupPlaceholderTag(true);
	    noRZData.add(noRZDataLabel);

	}

    }
}
