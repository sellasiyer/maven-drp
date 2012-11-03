package com.bestbuy.bbym.ise.drp.recsheet;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.BitSet;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.domain.Recommendation;
import com.bestbuy.bbym.ise.drp.recsheet.MobileWOWSectionPage.EssentialTypeId;

/**
 * Combined Rec Sheets:
 * <ol>
 * <li>You</li>
 * <li>The Right <recType>
 * <li>How do you connect to the internet?</li>
 * <li>Existing Hardware</li>
 * <li>Recommendations</li>
 * <li>Essentials</li>
 * <li>Consultant Info</li>
 * <ol>
 * 
 * @author a915722
 */
public enum RecSheetsSections {

    YOU_CT("ct.you.section", CTYouSectionPage.class, 0, true) {

	@Override
	boolean dataWasEntered(Recommendation recommendation) {
	    return false;
	}
    },
    YOU_MOBILE("mobile.you.section", MobileYouSectionPage.class, 1, false) {

	@Override
	boolean dataWasEntered(Recommendation recommendation) {

	    try{
		return checkRecommendationValues(recommendation, new String[] {"firstName", "lastName", "mobileNo",
			"upgradeEligibilityDate", "subscriptionInfo", "upgradeReminderText", "upgradeReminderCall",
			"tradeInValue" });
	    }catch(IllegalArgumentException e){
		LOGGER.error(e.getMessage(), e);
		return false;
	    }catch(IllegalAccessException e){
		LOGGER.error(e.getMessage(), e);
		return false;
	    }
	}
    },
    THE_RIGHT_RECTYPE_PC("the.right.rectype.pc", CTYouSectionPage.class, 2, false) {

	@Override
	boolean dataWasEntered(Recommendation recommendation) {
	    return false;
	}
    },
    THE_RIGHT_RECTYPE_MAC("the.right.rectype.mac", CTYouSectionPage.class, 3, false) {

	@Override
	boolean dataWasEntered(Recommendation recommendation) {
	    return false;
	}
    },
    THE_RIGHT_RECTYPE_TABLET("the.right.rectype.tablet", CTYouSectionPage.class, 4, false) {

	@Override
	boolean dataWasEntered(Recommendation recommendation) {
	    return false;
	}
    },

    INTERNET_CONNECTION("ct.internet.connection", CTYouSectionPage.class, 0, true) {

	@Override
	boolean dataWasEntered(Recommendation recommendation) {
	    return false;
	}
    },
    EXISTING_HARDWARE("ct.existing.hardware", CTYouSectionPage.class, 0, true) {

	@Override
	boolean dataWasEntered(Recommendation recommendation) {
	    return false;
	}
    },
    RECOMMENDATIONS("ct.recommendations", CTYouSectionPage.class, 0, true) {

	@Override
	boolean dataWasEntered(Recommendation recommendation) {
	    return false;
	}
    },
    ESSENTIALS("ct.essentials", CTYouSectionPage.class, 0, true) {

	@Override
	boolean dataWasEntered(Recommendation recommendation) {
	    return false;
	}
    },
    CONSULTANT_INFO("ct.consultant.info", CTYouSectionPage.class, 0, true) {

	@Override
	boolean dataWasEntered(Recommendation recommendation) {
	    return false;
	}
    },

    PERFECT_MOBILE_DEVICE("mobile.perfect.mobile.device", MobilePerfectDeviceSectionPage.class, 1, false) {

	@Override
	boolean dataWasEntered(Recommendation recommendation) {
	    if (recommendation != null){
		if (recommendation.getPreference(EssentialTypeEnum.INTERNET.getId()) != null
			&& recommendation.getPreference(EssentialTypeEnum.INTERNET.getId()).equals("true")){
		    return true;
		}
		if (recommendation.getPreference(EssentialTypeEnum.MUSIC.getId()) != null
			&& recommendation.getPreference(EssentialTypeEnum.MUSIC.getId()).equals("true")){
		    return true;
		}
		if (recommendation.getPreference(EssentialTypeEnum.EMAIL.getId()) != null
			&& recommendation.getPreference(EssentialTypeEnum.EMAIL.getId()).equals("true")){
		    return true;
		}
		if (recommendation.getPreference(EssentialTypeEnum.VIDEO.getId()) != null
			&& recommendation.getPreference(EssentialTypeEnum.VIDEO.getId()).equals("true")){
		    return true;
		}
		if (recommendation.getPreference(EssentialTypeEnum.PHOTOS.getId()) != null
			&& recommendation.getPreference(EssentialTypeEnum.PHOTOS.getId()).equals("true")){
		    return true;
		}
		if (recommendation.getPreference(EssentialTypeEnum.TV.getId()) != null
			&& recommendation.getPreference(EssentialTypeEnum.TV.getId()).equals("true")){
		    return true;
		}
		if (recommendation.getPreference(EssentialTypeEnum.NAVIGATION.getId()) != null
			&& recommendation.getPreference(EssentialTypeEnum.NAVIGATION.getId()).equals("true")){
		    return true;
		}
		if (recommendation.getPreference(EssentialTypeEnum.GAMING.getId()) != null
			&& recommendation.getPreference(EssentialTypeEnum.GAMING.getId()).equals("true")){
		    return true;
		}
		if (recommendation.getPreference(EssentialTypeEnum.TEXTING.getId()) != null
			&& recommendation.getPreference(EssentialTypeEnum.TEXTING.getId()).equals("true")){
		    return true;
		}
		if (recommendation.getPreference(EssentialTypeEnum.UNLOCKED.getId()) != null
			&& recommendation.getPreference(EssentialTypeEnum.UNLOCKED.getId()).equals("true")){
		    return true;
		}
		if (recommendation.getPreference(EssentialTypeEnum.OTHER.getId()) != null
			&& StringUtils.isNotBlank(recommendation.getPreference(EssentialTypeEnum.OTHER.getId()))){
		    return true;
		}
	    }
	    return false;
	}
    },
    INTERNET_USAGE("mobile.internet.usage", MobileInternetSectionPage.class, 1, false) {

	@Override
	boolean dataWasEntered(Recommendation recommendation) {
	    try{
		return checkRecommendationValues(recommendation, new String[] {"netUseInfo" });
	    }catch(IllegalArgumentException e){
		LOGGER.error(e.getMessage(), e);
		return false;
	    }catch(IllegalAccessException e){
		LOGGER.error(e.getMessage(), e);
		return false;
	    }
	}
    },
    OUR_RECOMMENDATIONS("mobile.our.recommendations", MobileOurRecommendationsSectionPage.class, 1, false) {

	@Override
	boolean dataWasEntered(Recommendation recommendation) {
	    try{
		return checkRecommendationValues(recommendation, new String[] {"recommendedSubscription",
			"recommendedDevice" });
	    }catch(IllegalArgumentException e){
		LOGGER.error(e.getMessage(), e);
		return false;
	    }catch(IllegalAccessException e){
		LOGGER.error(e.getMessage(), e);
		return false;
	    }
	}
    },
    WALK_OUT_WORKING("mobile.walk.out.working", MobileWOWSectionPage.class, 1, false) {

	@Override
	boolean dataWasEntered(Recommendation recommendation) {
	    if (recommendation == null){
		return false;
	    }

	    Map<Long, String> preferences = recommendation.getPreferences();
	    if (preferences == null){
		return false;
	    }

	    if (preferences.get(EssentialTypeId.DATATRANSFER.getId()) != null
		    && "true".equalsIgnoreCase(preferences.get(EssentialTypeId.DATATRANSFER.getId()))){
		return true;
	    }
	    if (preferences.get(EssentialTypeId.SOCIAL.getId()) != null
		    && "true".equalsIgnoreCase(preferences.get(EssentialTypeId.SOCIAL.getId()))){
		return true;
	    }
	    if (preferences.get(EssentialTypeId.EMAIL.getId()) != null
		    && "true".equalsIgnoreCase(preferences.get(EssentialTypeId.EMAIL.getId()))){
		return true;
	    }
	    if (preferences.get(EssentialTypeId.POWER.getId()) != null
		    && "true".equalsIgnoreCase(preferences.get(EssentialTypeId.POWER.getId()))){
		return true;
	    }
	    if (preferences.get(EssentialTypeId.BLUETOOTH.getId()) != null
		    && "true".equalsIgnoreCase(preferences.get(EssentialTypeId.BLUETOOTH.getId()))){
		return true;
	    }
	    if (preferences.get(EssentialTypeId.VOICEMAIL.getId()) != null
		    && "true".equalsIgnoreCase(preferences.get(EssentialTypeId.VOICEMAIL.getId()))){
		return true;
	    }
	    if (preferences.get(EssentialTypeId.APPLICATIONS.getId()) != null
		    && "true".equalsIgnoreCase(preferences.get(EssentialTypeId.APPLICATIONS.getId()))){
		return true;
	    }
	    if (preferences.get(EssentialTypeId.OTHER.getId()) != null
		    && "true".equalsIgnoreCase(preferences.get(EssentialTypeId.OTHER.getId()))){
		return true;
	    }
	    if (preferences.get(EssentialTypeId.SOFTWARE.getId()) != null
		    && "true".equalsIgnoreCase(preferences.get(EssentialTypeId.SOFTWARE.getId()))){
		return true;
	    }
	    return false;
	}
    },
    ESSENTIALS_MOBILE("mobile.essentials", MobileEssentialsSectionPage.class, 1, false) {

	@Override
	boolean dataWasEntered(Recommendation recommendation) {
	    if (recommendation == null){
		return false;
	    }

	    Map<Long, String> preferences = recommendation.getPreferences();
	    if (preferences == null){
		return false;
	    }

	    if (recommendation.getPreference(EssentialTypeEnum.HANDSFREE.getId()) != null
		    && StringUtils.isNotBlank(recommendation.getPreference(EssentialTypeEnum.HANDSFREE.getId()))){
		return true;
	    }
	    if (recommendation.getPreference(EssentialTypeEnum.MEMORY.getId()) != null
		    && StringUtils.isNotBlank(recommendation.getPreference(EssentialTypeEnum.MEMORY.getId()))){
		return true;
	    }
	    if (recommendation.getPreference(EssentialTypeEnum.CASE_SHIELD.getId()) != null
		    && StringUtils.isNotBlank(recommendation.getPreference(EssentialTypeEnum.CASE_SHIELD.getId()))){
		return true;
	    }
	    if (recommendation.getPreference(EssentialTypeEnum.APPCESSORIES.getId()) != null
		    && StringUtils.isNotBlank(recommendation.getPreference(EssentialTypeEnum.APPCESSORIES.getId()))){
		return true;
	    }
	    if (recommendation.getPreference(EssentialTypeEnum.CHARGER.getId()) != null
		    && StringUtils.isNotBlank(recommendation.getPreference(EssentialTypeEnum.CHARGER.getId()))){
		return true;
	    }
	    if (recommendation.getPreference(EssentialTypeEnum.GEEK_SQUAD_PROTECTION.getId()) != null
		    && StringUtils.isNotBlank(recommendation.getPreference(EssentialTypeEnum.GEEK_SQUAD_PROTECTION
			    .getId()))){
		return true;
	    }
	    if (recommendation.getPreference(EssentialTypeEnum.TRADE_IN_PLUS.getId()) != null
		    && StringUtils.isNotBlank(recommendation.getPreference(EssentialTypeEnum.TRADE_IN_PLUS.getId()))){
		return true;
	    }
	    if (recommendation.getPreference(EssentialTypeEnum.FINANCING.getId()) != null
		    && StringUtils.isNotBlank(recommendation.getPreference(EssentialTypeEnum.FINANCING.getId()))){
		return true;
	    }
	    return false;
	}

    },
    SPECIALIST_INFO("mobile.specialist.info", MobileSpecialistInfoSectionPage.class, 1, false) {

	@Override
	boolean dataWasEntered(Recommendation recommendation) {
	    try{
		return checkRecommendationValues(recommendation, new String[] {"bbyCnsFrstNm", "bbyCnsLastNm",
			"bbyCnsPhNbr" });
	    }catch(IllegalArgumentException e){
		LOGGER.error(e.getMessage(), e);
		return false;
	    }catch(IllegalAccessException e){
		LOGGER.error(e.getMessage(), e);
		return false;
	    }
	}
    },

    PC_SERVICES_WALK_OUT_WALKING("pc.services.walk.out.working", MobileYouSectionPage.class, 2, false) {

	@Override
	boolean dataWasEntered(Recommendation recommendation) {
	    return false;
	}
    },
    PC_SERVICES_STEP_1("pc.services.step.1", MobileYouSectionPage.class, 2, false) {

	@Override
	boolean dataWasEntered(Recommendation recommendation) {
	    return false;
	}
    },
    PC_SERVICES_STEP_2("pc.services.step.2", MobileYouSectionPage.class, 2, false) {

	@Override
	boolean dataWasEntered(Recommendation recommendation) {
	    return false;
	}
    },
    PC_SERVICES_STEP_3("pc.services.step.3", MobileYouSectionPage.class, 2, false) {

	@Override
	boolean dataWasEntered(Recommendation recommendation) {
	    return false;
	}
    },

    MAC_SERVICES_WALK_OUT_WALKING("mac.services.walk.out.working", MobileYouSectionPage.class, 3, false) {

	@Override
	boolean dataWasEntered(Recommendation recommendation) {
	    return false;
	}
    },
    MAC_SERVICES_STEP_1("mac.services.step.1", MobileYouSectionPage.class, 3, false) {

	@Override
	boolean dataWasEntered(Recommendation recommendation) {
	    return false;
	}
    },
    MAC_SERVICES_STEP_2("mac.services.step.2", MobileYouSectionPage.class, 3, false) {

	@Override
	boolean dataWasEntered(Recommendation recommendation) {
	    return false;
	}
    },
    MAC_SERVICES_STEP_3("mac.services.step.3", MobileYouSectionPage.class, 3, false) {

	@Override
	boolean dataWasEntered(Recommendation recommendation) {
	    return false;
	}
    },

    TABLET_SERVICES_WALK_OUT_WALKING("tablet.services.walk.out.working", MobileYouSectionPage.class, 4, false) {

	@Override
	boolean dataWasEntered(Recommendation recommendation) {
	    return false;
	}
    },
    TABLET_SERVICES_STEP_1("tablet.services.step.1", MobileYouSectionPage.class, 4, false) {

	@Override
	boolean dataWasEntered(Recommendation recommendation) {
	    return false;
	}
    },
    TABLET_SERVICES_STEP_2("tablet.services.step.2", MobileYouSectionPage.class, 4, false) {

	@Override
	boolean dataWasEntered(Recommendation recommendation) {
	    return false;
	}
    };

    /**
     * Returns true if data was entered for that section.
     * 
     * @param recommendation
     *            Recommendation
     * @return true if data was entered for that section.
     */
    abstract boolean dataWasEntered(final Recommendation recommendation);

    private final String sectionTitle;
    private final Class clazz;
    private final int recSheetType;
    private final boolean ctCommon;

    private RecSheetsSections(final String sectionTitle, final Class clazz, final int recSheetType,
	    final boolean ctCommon) {
	this.sectionTitle = sectionTitle;
	this.clazz = clazz;
	this.recSheetType = recSheetType;
	this.ctCommon = ctCommon;
    }

    public String getSectionTitle() {
	return sectionTitle;
    }

    public Class getClazz() {
	return clazz;
    }

    public int getRecSheetType() {
	return recSheetType;
    }

    public boolean isCtCommon() {
	return ctCommon;
    }

    protected boolean checkRecommendationValues(final Recommendation rec, String... strings)
	    throws IllegalArgumentException, IllegalAccessException {

	Field[] fields = rec.getClass().getDeclaredFields();
	for(String fieldName: strings){
	    if (StringUtils.isEmpty(fieldName)){
		continue;
	    }
	    for(int i = 0; i < fields.length; i++){
		fields[i].setAccessible(true);
		if (fields[i].getName().equalsIgnoreCase(fieldName)){

		    if (fields[i].getType().getName().equals("java.lang.String")){
			if (fields[i].get(rec) != null){
			    String val = (String) fields[i].get(rec);
			    if (StringUtils.isNotEmpty(val)){
				return true;
			    }
			}

		    }else if (fields[i].getType().getName().equals("java.util.Date")){

			if (fields[i].get(rec) != null){
			    Date val = (Date) fields[i].get(rec);
			    if (val != null && StringUtils.isNotEmpty(val.toString())){
				return true;
			    }
			}
		    }else if (fields[i].getType().getName().equals("java.math.BigDecimal")){

			if (fields[i].get(rec) != null){
			    BigDecimal val = (BigDecimal) fields[i].get(rec);
			    if (val != null && StringUtils.isNotEmpty(val.toString())){
				return true;
			    }
			}
		    }else if (fields[i].getType().getName().equals("java.util.BitSet")){

			if (fields[i].get(rec) != null){
			    BitSet val = (BitSet) fields[i].get(rec);
			    if (val != null && StringUtils.isNotEmpty(val.toString())){
				return true;
			    }
			}
		    }
		}
	    }
	}
	return false;

    }

    private static Logger LOGGER = LoggerFactory.getLogger(AbstractRecSheetPage.class);
}
