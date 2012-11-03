package com.bestbuy.bbym.ise.drp.domain;

import com.bestbuy.bbym.ise.domain.Device;

/**
 * 
 * @author a904002
 */
public class TitanDevice extends Device {

    private String dataSharingKey;
    private String receiptNumber;

    private String manufacturer;
    private String deviceName;
    private String deviceId;
    private long cartId;
    private String nextUrl;

    private String bestPriceEstimate;
    private CarrierAccount carrierAccount;
    private String technologyType;
    private String promoCode;
    private String promoValue;
    private FourPartKey fourPartKey;

    private boolean isReceiptRequired;
    private String docURL;
    private String itemTag;
    private long giftCardNo;

    public String getBestPriceEstimate() {
	return bestPriceEstimate;

    }

    public void setBestPriceEstimate(String bestPriceEstimate) {
	this.bestPriceEstimate = bestPriceEstimate;
    }

    public String getDataSharingKey() {
	return dataSharingKey;
    }

    public void setDataSharingKey(String dataSharingKey) {
	this.dataSharingKey = dataSharingKey;
    }

    public String getManufacturer() {
	return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
	this.manufacturer = manufacturer;
    }

    public String getProductName() {
	return deviceName;
    }

    public void setProductName(String productName) {
	this.deviceName = productName;
    }

    public String getReceiptNumber() {
	return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
	this.receiptNumber = receiptNumber;
    }

    public long getCartId() {
	return cartId;
    }

    public void setCartId(long cartId) {
	this.cartId = cartId;
    }

    public String getDeviceName() {
	return deviceName;
    }

    public void setDeviceName(String deviceName) {
	this.deviceName = deviceName;
    }

    public String getNextUrl() {
	return nextUrl;
    }

    public void setNextUrl(String nextUrl) {
	this.nextUrl = nextUrl;
    }

    public String getTechnologyType() {
	return technologyType;
    }

    public void setTechnologyType(String technologyType) {
	this.technologyType = technologyType;
    }

    public String getPromoCode() {
	return promoCode;
    }

    public void setPromoCode(String promoCode) {
	this.promoCode = promoCode;
    }

    public String getPromoValue() {
	return promoValue;
    }

    public void setPromoValue(String promoValue) {
	this.promoValue = promoValue;
    }

    public FourPartKey getFourPartKey() {
	return fourPartKey;
    }

    public void setFourPartKey(FourPartKey fourPartKey) {
	this.fourPartKey = fourPartKey;
    }

    @Override
    public String toString() {
	return "TitanDevice [dataSharingKey=" + dataSharingKey + ", receiptNumber=" + receiptNumber + ", manufacturer="
		+ manufacturer + ", deviceName=" + deviceName + ", deviceId=" + deviceId + ", cartId=" + cartId
		+ ", nextUrl=" + nextUrl + ", bestPriceEstimate=" + bestPriceEstimate + ", carrierAccount="
		+ carrierAccount + ", technologyType=" + technologyType + ", promoCode=" + promoCode + ", promoValue="
		+ promoValue + ", fourPartKey=" + fourPartKey + ", isReceiptRequired=" + isReceiptRequired
		+ ", docURL=" + docURL + ", itemTag=" + itemTag + ", giftCardNo=" + giftCardNo + "]";
    }

    public String getDeviceId() {
	return deviceId;
    }

    public void setDeviceId(String deviceId) {
	this.deviceId = deviceId;
    }

    public CarrierAccount getCarrierAccount() {
	return carrierAccount;
    }

    public void setCarrierAccount(CarrierAccount carrierAccount) {
	this.carrierAccount = carrierAccount;
    }

    public String getDocURL() {
	return docURL;
    }

    public void setDocURL(String docURL) {
	this.docURL = docURL;
    }

    public String getItemTag() {
	return itemTag;
    }

    public void setItemTag(String itemTag) {
	this.itemTag = itemTag;
    }

    public long getGiftCardNo() {
	return giftCardNo;
    }

    public void setGiftCardNo(long giftCardNo) {
	this.giftCardNo = giftCardNo;
    }

    public boolean isReceiptRequired() {
	return isReceiptRequired;
    }

    public void setReceiptRequired(boolean isReceiptRequired) {
	this.isReceiptRequired = isReceiptRequired;
    }

}
