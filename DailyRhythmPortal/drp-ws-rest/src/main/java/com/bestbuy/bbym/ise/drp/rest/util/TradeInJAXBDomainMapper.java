package com.bestbuy.bbym.ise.drp.rest.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.bestbuy.bbym.beast.portal.tradein.reqres.TradeIn;
import com.bestbuy.bbym.beast.portal.tradeindevicedetails.request.LineType;
import com.bestbuy.bbym.ise.common.CustomerProfileType;
import com.bestbuy.bbym.ise.common.FourPartKeyType;
import com.bestbuy.bbym.ise.drp.domain.CarrierAccount;
import com.bestbuy.bbym.ise.drp.domain.FourPartKey;
import com.bestbuy.bbym.ise.drp.domain.TitanDevice;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.Util;

public class TradeInJAXBDomainMapper extends JAXBDomainMapper {

    public static List<com.bestbuy.bbym.ise.drp.domain.BeastDevice> getTradeInDeviceDetails(
	    com.bestbuy.bbym.beast.portal.tradeindevicedetails.request.TradeInDeviceDetails tradeInDeviceDetails) {
	List<com.bestbuy.bbym.ise.drp.domain.BeastDevice> deviceList = new ArrayList<com.bestbuy.bbym.ise.drp.domain.BeastDevice>();

	for(LineType lineType: tradeInDeviceDetails.getLine()){
	    com.bestbuy.bbym.ise.drp.domain.BeastDevice deviceDetails = new com.bestbuy.bbym.ise.drp.domain.BeastDevice();
	    if (StringUtils.isNotBlank(lineType.getHandsetIdentifier())){
		deviceDetails.setHandsetIdentifier(lineType.getHandsetIdentifier());
		if (lineType.getHandsetIdentifier().length() > 4){
		    deviceDetails.setFourSymbolHandsetIdentifier(lineType.getHandsetIdentifier().substring(
			    lineType.getHandsetIdentifier().length() - 4));
		}
	    }
	    deviceDetails.setBeastTransactionId(tradeInDeviceDetails.getTransactionId());
	    deviceDetails.setDeviceDesc(lineType.getDeviceDescription());
	    deviceDetails.setHandsetIdentifierType(lineType.getHandsetIdentifierType());
	    deviceDetails.setPhoneNumber(lineType.getMobileNumber());
	    deviceDetails.setCarrier(tradeInDeviceDetails.getCarrier().value());
	    deviceDetails.setSelectedFlag(false);
	    deviceDetails.setSource("BEAST");
	    deviceDetails.setCreatedBy(tradeInDeviceDetails.getCreatedBy());
	    deviceList.add(deviceDetails);
	}
	return deviceList;
    }

    public static TitanDevice createTitanDevice(TradeIn tradeIn) {
	TitanDevice titanDevice = new TitanDevice();
	if (tradeIn.getDataSharingKey() != null){
	    titanDevice.setDataSharingKey(tradeIn.getDataSharingKey());
	}
	if (tradeIn.getSelectedHandsetIdentifier() != null){
	    titanDevice.setSerialNumber(tradeIn.getSelectedHandsetIdentifier());
	}
	if (tradeIn.getTradeInValue() != null){
	    titanDevice.setTradeInValue(new BigDecimal(tradeIn.getTradeInValue()));
	}
	if (tradeIn.getCustomerProfile() != null){
	    titanDevice.setCarrierAccount(getCarrierAccount(tradeIn.getCustomerProfile(), tradeIn.getDataSharingKey(),
		    tradeIn.getCreatedBy()));
	}
	if (tradeIn.getNextURL() != null){
	    titanDevice.setNextUrl(tradeIn.getNextURL());
	}
	if (tradeIn.getTitanCartId() != null){
	    titanDevice.setCartId(Integer.valueOf(tradeIn.getTitanCartId()));
	}
	if (tradeIn.getPromoCode() != null){
	    titanDevice.setPromoCode(tradeIn.getPromoCode());
	}
	if (tradeIn.getPromoValue() != null){
	    titanDevice.setPromoValue(tradeIn.getPromoValue());
	}
	if (tradeIn.isReceiptRequired() != null){
	    titanDevice.setReceiptRequired(tradeIn.isReceiptRequired());
	}
	if (tradeIn.getDeviceDetail() != null){
	    if (tradeIn.getDeviceDetail().getTechnologyType() != null){
		titanDevice.setTechnologyType(tradeIn.getDeviceDetail().getTechnologyType().value());
	    }
	    if (tradeIn.getDeviceDetail().getHandsetIdentifier() != null){
		titanDevice.setId(Long.valueOf(tradeIn.getDeviceDetail().getHandsetIdentifier()));
	    }
	    if (tradeIn.getDeviceDetail().getDeviceModelNumber() != null){
		titanDevice.setModelNumber(tradeIn.getDeviceDetail().getDeviceModelNumber());
	    }
	    if (tradeIn.getDeviceDetail().getPhoneName() != null){
		titanDevice.setDeviceName(tradeIn.getDeviceDetail().getPhoneName());
	    }
	    if (tradeIn.getDeviceDetail().getDeviceManufacturerName() != null){
		titanDevice.setManufacturer(tradeIn.getDeviceDetail().getDeviceManufacturerName());
	    }
	    if (tradeIn.getDeviceDetail().getDeviceDescription() != null){
		titanDevice.setDescription(tradeIn.getDeviceDetail().getDeviceDescription());
	    }
	    if (tradeIn.getDeviceDetail().getDeviceSKU() != null){
		titanDevice.setSku(tradeIn.getDeviceDetail().getDeviceSKU());
	    }
	}
	if (tradeIn.getFourPartKey() != null && getFourPartKey(tradeIn.getFourPartKey()) != null){
	    titanDevice.setFourPartKey(getFourPartKey(tradeIn.getFourPartKey()));
	}
	if (tradeIn.getLastModifiedBy() != null){
	    titanDevice.setModifiedBy(tradeIn.getLastModifiedBy());
	}
	titanDevice.setModifiedOn(Util.toUtilDate(tradeIn.getLastModifiedDate()));
	if (tradeIn.getCreatedBy() != null){
	    titanDevice.setCreatedBy(tradeIn.getCreatedBy());
	}
	if (tradeIn.getCreatedDate() != null){
	    titanDevice.setCreatedBy(tradeIn.getCreatedDate().toString());
	}
	return titanDevice;

    }

    private static FourPartKey getFourPartKey(FourPartKeyType fourPartKey) {
	FourPartKey returnKey = new FourPartKey();
	if (fourPartKey.getStoreId() != null){
	    returnKey.setStoreId(fourPartKey.getStoreId());
	}
	if (fourPartKey.getWorkStationId() != null){
	    returnKey.setWorkStationId(fourPartKey.getWorkStationId());
	}
	if (fourPartKey.getRegisterTransactionNum() != null){
	    returnKey.setRegisterTransactionNum(fourPartKey.getRegisterTransactionNum());
	}
	returnKey.setBusinessDate(Util.toUtilDate(fourPartKey.getBusinessDate()));
	return returnKey;
    }

    private static CarrierAccount getCarrierAccount(CustomerProfileType customerProfile, String dataSharingKey,
	    String userId) {
	CarrierAccount carrierAccount = new CarrierAccount();
	if (dataSharingKey != null){
	    carrierAccount.setDataSharingKey(dataSharingKey);
	}
	if (customerProfile.getFirstName() != null){
	    carrierAccount.setFirstName(customerProfile.getFirstName());
	}
	if (customerProfile.getLastName() != null){
	    carrierAccount.setLastName(customerProfile.getLastName());
	}
	if (customerProfile.getPhoneNumber() != null){
	    carrierAccount.setPhoneNumber(customerProfile.getPhoneNumber());
	}
	if (customerProfile.getEmailId() != null){
	    carrierAccount.setEmail(customerProfile.getEmailId());
	}
	if (customerProfile.getAddress() != null){
	    carrierAccount.setAddress(getAddressFromRestAddressType(customerProfile.getAddress(), userId));
	}
	if (customerProfile.getDOB() != null){
	    carrierAccount.setDob(Util.toUtilDate(customerProfile.getDOB()));
	}
	if (customerProfile.getIdType() != null){
	    carrierAccount.setIdType(customerProfile.getIdType());
	}
	if (customerProfile.getIdIssuer() != null){
	    carrierAccount.setIdIssuer(customerProfile.getIdIssuer());
	}
	if (customerProfile.getIdCode() != null){
	    carrierAccount.setIdCode(customerProfile.getIdCode());
	}
	carrierAccount.setIdExpirationDate(Util.toUtilDate(customerProfile.getIdExpirationDate()));
	if (userId != null){
	    carrierAccount.setCreatedBy(userId);
	}

	return carrierAccount;
    }

    public static TradeIn getTradeIn(TitanDevice titanDevice) throws ServiceException {
	TradeIn tradeIn = new TradeIn();
	if (titanDevice.getDataSharingKey() != null){
	    tradeIn.setDataSharingKey(titanDevice.getDataSharingKey());
	}
	// Need to Check
	if (titanDevice.getSerialNumber() != null){
	    tradeIn.setSelectedHandsetIdentifier(titanDevice.getSerialNumber());
	}
	if (titanDevice.getTradeInValue() != null){
	    tradeIn.setTradeInValue(titanDevice.getTradeInValue().toString());
	}
	if (titanDevice.getCarrierAccount() != null){
	    tradeIn.setCustomerProfile(getCustomerProfileType(titanDevice.getCarrierAccount()));
	}
	if (titanDevice.getNextUrl() != null){
	    tradeIn.setNextURL(titanDevice.getNextUrl());
	}
	if (titanDevice.getCartId() != 0){
	    tradeIn.setTitanCartId(String.valueOf(titanDevice.getCartId()));
	}
	if (titanDevice.getPromoCode() != null){
	    tradeIn.setPromoCode(titanDevice.getPromoCode());
	}
	if (titanDevice.getPromoValue() != null){
	    tradeIn.setPromoValue(titanDevice.getPromoValue());
	}
	if (titanDevice.getPromoValue() != null){
	    tradeIn.setPromoValue(titanDevice.getPromoValue());
	}
	if (getDeviceDetailsType(titanDevice) != null){
	    tradeIn.setDeviceDetail(getDeviceDetailsType(titanDevice));
	}
	if (titanDevice.getFourPartKey() != null){
	    tradeIn.setFourPartKey(getTradeInFourPartKey(titanDevice.getFourPartKey()));
	}
	if (titanDevice.getDocURL() != null){
	    tradeIn.setTradeInDocUrl(titanDevice.getDocURL());
	}
	if (titanDevice.getItemTag() != null){
	    tradeIn.setItemTag(titanDevice.getItemTag());
	}
	tradeIn.setGiftCardNumber(String.valueOf(titanDevice.getGiftCardNo()));
	if (titanDevice.getModifiedBy() != null){
	    tradeIn.setLastModifiedBy(titanDevice.getModifiedBy());
	}
	if (titanDevice.getModifiedOn() != null){
	    tradeIn.setLastModifiedDate(Util.toXmlGregorianCalendar(titanDevice.getModifiedOn()));
	}
	if (titanDevice.getCreatedBy() != null){
	    tradeIn.setCreatedBy(titanDevice.getCreatedBy());
	}
	if (titanDevice.getCreatedOn() != null){
	    tradeIn.setCreatedDate(Util.toXmlGregorianCalendar(titanDevice.getCreatedOn()));
	}
	return tradeIn;
    }

    private static FourPartKeyType getTradeInFourPartKey(FourPartKey fourPartKey) throws ServiceException {
	FourPartKeyType fourPartKeyType = new FourPartKeyType();
	boolean isObjectNull = true;
	if (fourPartKey.getStoreId() != null){
	    fourPartKeyType.setStoreId(fourPartKey.getStoreId());
	    isObjectNull = false;
	}
	if (fourPartKey.getRegisterTransactionNum() != null){
	    fourPartKeyType.setRegisterTransactionNum(fourPartKey.getRegisterTransactionNum());
	    isObjectNull = false;
	}
	if (fourPartKey.getWorkStationId() != null){
	    fourPartKeyType.setWorkStationId(fourPartKey.getWorkStationId());
	    isObjectNull = false;
	}
	if (fourPartKey.getBusinessDate() != null){
	    fourPartKeyType.setBusinessDate(Util.toXmlGregorianCalendar(fourPartKey.getBusinessDate()));
	    isObjectNull = false;
	}
	return isObjectNull?null:fourPartKeyType;
    }

    private static com.bestbuy.bbym.ise.common.DeviceDetailsType getDeviceDetailsType(TitanDevice titanDevice) {
	com.bestbuy.bbym.ise.common.DeviceDetailsType device = new com.bestbuy.bbym.ise.common.DeviceDetailsType();
	boolean isObjectNull = true;
	if (titanDevice.getTechnologyType() != null){
	    device.setTechnologyType(com.bestbuy.bbym.ise.common.TechnologyType.fromValue(titanDevice
		    .getTechnologyType()));
	    isObjectNull = false;
	}
	if (titanDevice.getSerialNumber() != null){
	    device.setHandsetIdentifier(titanDevice.getSerialNumber());
	    isObjectNull = false;
	}
	if (titanDevice.getModelNumber() != null){
	    device.setDeviceModelNumber(titanDevice.getModelNumber());
	    isObjectNull = false;
	}
	if (titanDevice.getDeviceName() != null){
	    device.setPhoneName(titanDevice.getDeviceName());
	    isObjectNull = false;
	}
	if (titanDevice.getManufacturer() != null){
	    device.setDeviceManufacturerName(titanDevice.getManufacturer());
	    isObjectNull = false;
	}
	if (titanDevice.getDescription() != null){
	    device.setDeviceDescription(titanDevice.getDescription());
	    isObjectNull = false;
	}
	if (titanDevice.getSku() != null){
	    device.setDeviceSKU(titanDevice.getSku());
	    isObjectNull = false;
	}
	return isObjectNull?null:device;
    }

    private static CustomerProfileType getCustomerProfileType(CarrierAccount carrierAccount) {
	CustomerProfileType customerProfile = new CustomerProfileType();
	boolean isObjectNull = true;
	if (carrierAccount.getFirstName() != null){
	    customerProfile.setFirstName(carrierAccount.getFirstName());
	    isObjectNull = false;
	}
	if (carrierAccount.getLastName() != null){
	    customerProfile.setLastName(carrierAccount.getLastName());
	    isObjectNull = false;
	}
	if (carrierAccount.getPhoneNumber() != null){
	    customerProfile.setPhoneNumber(carrierAccount.getPhoneNumber());
	    isObjectNull = false;
	}
	if (carrierAccount.getEmail() != null){
	    customerProfile.setEmailId(carrierAccount.getEmail());
	    isObjectNull = false;
	}
	if (carrierAccount.getAddress() != null){
	    customerProfile.setAddress(getRestAddressType(carrierAccount.getAddress()));
	    isObjectNull = false;
	}
	if (carrierAccount.getDob() != null){
	    customerProfile.setDOB(Util.toXmlGregorianCalendar(carrierAccount.getDob()));
	    isObjectNull = false;
	}
	if (carrierAccount.getIdType() != null){
	    customerProfile.setIdType(carrierAccount.getIdType());
	    isObjectNull = false;
	}
	if (carrierAccount.getIdIssuer() != null){
	    customerProfile.setIdIssuer(carrierAccount.getIdIssuer());
	    isObjectNull = false;
	}
	if (carrierAccount.getIdCode() != null){
	    customerProfile.setIdCode(carrierAccount.getIdCode());
	    isObjectNull = false;
	}
	if (carrierAccount.getIdExpirationDate() != null){
	    customerProfile.setIdExpirationDate(Util.toXmlGregorianCalendar(carrierAccount.getIdExpirationDate()));
	    isObjectNull = false;
	}
	return isObjectNull?null:customerProfile;
    }
}
