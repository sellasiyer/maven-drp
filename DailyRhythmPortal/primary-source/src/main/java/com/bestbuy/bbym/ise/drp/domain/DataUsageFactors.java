package com.bestbuy.bbym.ise.drp.domain;

import java.util.ArrayList;
import java.util.List;

import com.bestbuy.bbym.ise.drp.service.DrpPropertyService;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * @author a186288
 */
public class DataUsageFactors {

    private static final long serialVersionUID = 4360967176320538665L;

    //enums correspond to DB property values. 
    //order matters
    private enum DataUsage3g4g {
	DU_3G4G_EMAIL, DU_3G4G_WEB, DU_3G4G_AUDIO, DU_3G4G_VIDEOLO, DU_3G4G_VIDEOHI, DU_3G4G_PHOTO
    }

    private enum DataUsage3g {
	DU_3G_EMAIL, DU_3G_WEB, DU_3G_AUDIO, DU_3G_VIDEOLO, DU_3G_VIDEOHI, DU_3G_PHOTO
    }

    private enum DataUsage4g {
	DU_4G_EMAIL, DU_4G_WEB, DU_4G_AUDIO, DU_4G_VIDEOLO, DU_4G_VIDEOHI, DU_4G_PHOTO
    }

    //order doesnt matter
    public enum DataUsageTypes {
	THREE_G, THREE_FOUR_G, FOUR_G
    }

    private float emailFactorInGB;
    private float webPageFactorInGB;
    private float audioFactorInGB;
    private float loResVideoFactorInGB;
    private float highResVideoFactorInGB;
    private float photoFactorInGB;
    private float voipFactorInGB;
    private float voipAndVideoFactorInGB;
    private float onlineGamingFactorInGB;

    public float getEmailFactorInGB() {
	return emailFactorInGB;
    }

    public void setEmailFactorInGB(float emailFactorInGB) {
	this.emailFactorInGB = emailFactorInGB;
    }

    public float getWebPageFactorInGB() {
	return webPageFactorInGB;
    }

    public void setWebPageFactorInGB(float webPageFactorInGB) {
	this.webPageFactorInGB = webPageFactorInGB;
    }

    public float getAudioFactorInGB() {
	return audioFactorInGB;
    }

    public void setAudioFactorInGB(float audioFactorInGB) {
	this.audioFactorInGB = audioFactorInGB;
    }

    public float getLoResVideoFactorInGB() {
	return loResVideoFactorInGB;
    }

    public void setLoResVideoFactorInGB(float loResVideoFactorInGB) {
	this.loResVideoFactorInGB = loResVideoFactorInGB;
    }

    public float getHighResVideoFactorInGB() {
	return highResVideoFactorInGB;
    }

    public void setHighResVideoFactorInGB(float highResVideoFactorInGB) {
	this.highResVideoFactorInGB = highResVideoFactorInGB;
    }

    public float getPhotoFactorInGB() {
	return photoFactorInGB;
    }

    public void setPhotoFactorInGB(float photoFactorInGB) {
	this.photoFactorInGB = photoFactorInGB;
    }

    public float getVoipFactorInGB() {
	return voipFactorInGB;
    }

    public void setVoipFactorInGB(float voipFactorInGB) {
	this.voipFactorInGB = voipFactorInGB;
    }

    public float getVoipAndVideoFactorInGB() {
	return voipAndVideoFactorInGB;
    }

    public void setVoipAndVideoFactorInGB(float voipAndVideoFactorInGB) {
	this.voipAndVideoFactorInGB = voipAndVideoFactorInGB;
    }

    public float getOnlineGamingFactorInGB() {
	return onlineGamingFactorInGB;
    }

    public void setOnlineGamingFactorInGB(float onlineGamingFactorInGB) {
	this.onlineGamingFactorInGB = onlineGamingFactorInGB;
    }

    public static DataUsageFactors buildDataUsageFactors(DataUsageTypes type, DrpPropertyService drpPropertyService)
	    throws ServiceException {
	DataUsageFactors factors = new DataUsageFactors();
	Enum<?>[] enumList = null;
	switch (type) {
	    case FOUR_G:
		enumList = DataUsage4g.values();
		break;
	    case THREE_FOUR_G:
		enumList = DataUsage3g4g.values();
		break;
	    default:
	    case THREE_G:
		enumList = DataUsage3g.values();
		break;
	} //end switch

	List<Float> propList = new ArrayList<Float>();
	for(Enum<?> e: enumList){
	    String propName = e.name();
	    String propStr = drpPropertyService.getProperty(propName);
	    propList.add(new Float(propStr));
	}

	factors.mapDataUsageFactors(propList);

	return factors;
    }

    private void mapDataUsageFactors(List<Float> propList) {

	if (propList.size() < 6){
	    throw new RuntimeException("DataUsageFactors property list is setup incorrectly.");
	}
	this.setEmailFactorInGB(propList.get(0));
	this.setWebPageFactorInGB(propList.get(1));
	this.setAudioFactorInGB(propList.get(2));
	this.setLoResVideoFactorInGB(propList.get(3));
	this.setHighResVideoFactorInGB(propList.get(4));
	this.setPhotoFactorInGB(propList.get(5));
    }

}
