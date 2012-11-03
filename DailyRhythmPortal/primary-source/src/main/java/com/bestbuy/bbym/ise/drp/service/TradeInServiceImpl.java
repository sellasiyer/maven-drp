package com.bestbuy.bbym.ise.drp.service;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bestbuy.bbym.ise.drp.dao.DataTransferDao;
import com.bestbuy.bbym.ise.drp.dao.TradeInDao;
import com.bestbuy.bbym.ise.drp.dao.TradeInDeviceDetailsDao;
import com.bestbuy.bbym.ise.drp.domain.BeastDevice;
import com.bestbuy.bbym.ise.drp.domain.DataTransferSummary;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.TitanDevice;
import com.bestbuy.bbym.ise.drp.sao.TradabilityCheckSao;
import com.bestbuy.bbym.ise.exception.DataAccessException;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.WorkFlowTypeEnum;

/**
 * @author a175157
 */
@Transactional
@Service("tradeInService")
public class TradeInServiceImpl implements TradeInService {

    private static Logger logger = LoggerFactory.getLogger(TradeInServiceImpl.class);
    @Autowired
    private DataTransferDao dataTransferDao;
    @Autowired
    private TradeInDeviceDetailsDao tradeInDeviceDetailsDao;
    @Autowired
    private TradeInDao tradeInDao;
    @Autowired
    private DrpPropertyService drpPropertyService;
    @Autowired
    private TradabilityCheckSao tradabilityCheckSao;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public String writeDeviceDetails(List<BeastDevice> tradeInDetails, String storeId, String userId,
	    String sourceSystem) throws ServiceException {
	String dataSharingKey = UUID.randomUUID().toString();
	String beastTransactionId = "";
	if (tradeInDetails != null && tradeInDetails.size() > 0 && tradeInDetails.get(0) != null){
	    if (StringUtils.isNotBlank(tradeInDetails.get(0).getDataSharingKey())){
		dataSharingKey = tradeInDetails.get(0).getDataSharingKey();
	    }
	    beastTransactionId = tradeInDetails.get(0).getBeastTransactionId();
	}
	try{
	    dataTransferDao.persistDataTransfer(dataSharingKey, storeId, userId, false, sourceSystem,
		    beastTransactionId, WorkFlowTypeEnum.TRADE_IN.name());
	    for(BeastDevice device: tradeInDetails){
		if (device != null){
		    device.setId(UUID.randomUUID().toString());
		    device.setDataSharingKey(dataSharingKey);
		    tradeInDeviceDetailsDao.persistTradeInDeviceDetails(device);
		}
	    }
	}catch(DataAccessException e){
	    logger
		    .error("TRADE IN : Error in Writing Beast device details to DSL, dataSharingKey:" + dataSharingKey,
			    e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, "Error in Writing Beast device details to DSL");
	}catch(Exception e){
	    logger.error("TRADE IN : Unexpected Error, dataSharingKey:" + dataSharingKey, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}
	return dataSharingKey;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BeastDevice> getDeviceDetails(String datasharingKey) throws ServiceException {
	List<BeastDevice> returnList = null;
	logger.info("TRADE IN : Going to get BEAST device list datasharingKey = " + datasharingKey);
	try{
	    returnList = tradeInDeviceDetailsDao.getBEASTDevices(datasharingKey);

	}catch(EmptyResultDataAccessException e){
	    logger.error("TRADE IN: No Beast device found for dataSharingKey:" + datasharingKey, e);
	    throw new ServiceException(IseExceptionCodeEnum.NOTFOUND, e.getMessage());
	}catch(DataAccessException e){
	    logger.error("TRADE IN: Error in retrieving Beast device, dataSharingKey:" + datasharingKey, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}catch(Exception e){
	    logger.error("TRADE IN: Unexpected Error, dataSharingKey:" + datasharingKey, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}
	return returnList;
    }

    @Override
    public BeastDevice getTradeableDevice(String dataSharingKey, String serialNumber, DrpUser drpUser)
	    throws ServiceException {
	BeastDevice tradeableDevice = null;
	try{
	    if (serialNumber != null && serialNumber.length() == 4){
		for(BeastDevice device: getDeviceDetails(dataSharingKey)){
		    if (device != null && device.getFourSymbolHandsetIdentifier() != null
			    && device.getFourSymbolHandsetIdentifier().equalsIgnoreCase(serialNumber)){
			tradeableDevice = device;
			tradeInDeviceDetailsDao.updateSelectedDevice(tradeableDevice.getDataSharingKey(),
				tradeableDevice.getId(), drpUser.getUserId(), true);
			tradeableDevice.setSelectedFlag(true);
			break;
		    }
		}

	    }else if (StringUtils.isNotBlank(serialNumber)){
		for(BeastDevice device: getDeviceDetails(dataSharingKey)){
		    if (device != null && device.getHandsetIdentifier() != null
			    && device.getHandsetIdentifier().equalsIgnoreCase(serialNumber)){
			tradeableDevice = device;
			tradeInDeviceDetailsDao.updateSelectedDevice(tradeableDevice.getDataSharingKey(),
				tradeableDevice.getId(), drpUser.getUserId(), true);
			tradeableDevice.setSelectedFlag(true);
			break;
		    }
		}
		if (tradeableDevice == null){
		    tradeableDevice = new BeastDevice();
		    tradeableDevice.setDataSharingKey(dataSharingKey);
		    tradeableDevice.setId(UUID.randomUUID().toString());
		    tradeableDevice.setHandsetIdentifier(serialNumber);
		    tradeableDevice.setSource(drpPropertyService.getProperty("SOURCE_SYSTEM"));
		    tradeableDevice.setCreatedBy(drpUser.getUserId());
		    tradeInDeviceDetailsDao.resetSelectedDevice(tradeableDevice.getDataSharingKey(), drpUser
			    .getUserId());
		    tradeableDevice.setSelectedFlag(true);
		    tradeInDeviceDetailsDao.persistTradeInDeviceDetails(tradeableDevice);
		}
	    }
	}catch(EmptyResultDataAccessException e){
	    logger.error("TRADE IN: No Beast device found for dataSharingKey:" + dataSharingKey, e);
	    throw new ServiceException(IseExceptionCodeEnum.NOTFOUND, e.getMessage());
	}catch(DataAccessException e){
	    logger.error("TRADE IN: Error in retrieving Beast device, dataSharingKey:" + dataSharingKey, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}catch(Exception e){
	    logger.error("TRADE IN: Unexpected Error, dataSharingKey:" + dataSharingKey, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}
	return tradeableDevice;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void updateSelectedFlag(String datasharingKey, String deviceDetailsId, String modifiedBy)
	    throws ServiceException {
	try{
	    tradeInDeviceDetailsDao.updateSelectedDevice(datasharingKey, deviceDetailsId, modifiedBy, true);
	}catch(DataAccessException e){
	    logger.error("TRADE IN: Error in updating selected flag for Beast device, dataSharingKey:" + datasharingKey
		    + " deviceId = " + deviceDetailsId, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}catch(Exception e){
	    logger.error("TRADE IN: Unexpected Error in updating selected flag for Beast device, dataSharingKey:"
		    + datasharingKey + " deviceId = " + deviceDetailsId, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void updateSelectedFlag(String datasharingKey, String fourSymbolSerialNumber) throws ServiceException,
	    IseBusinessException, DataAccessException, RuntimeException {
	for(BeastDevice beastDevice: getDeviceDetails(datasharingKey)){
	    if (beastDevice.getFourSymbolHandsetIdentifier().equals(fourSymbolSerialNumber)){
		tradeInDeviceDetailsDao.updateSelectedDevice(datasharingKey, beastDevice.getId(), beastDevice
			.getModifiedBy(), true);
	    }
	}

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    // return ID
    public void addDeviceDetails(BeastDevice tradeInDetails) throws ServiceException {
	tradeInDetails.setId(UUID.randomUUID().toString());
	tradeInDetails.setSource(drpPropertyService.getProperty("SOURCE_SYSTEM"));
	tradeInDeviceDetailsDao.resetSelectedDevice(tradeInDetails.getDataSharingKey(), tradeInDetails.getModifiedBy());
	tradeInDetails.setSelectedFlag(true);
	try{
	    tradeInDeviceDetailsDao.persistTradeInDeviceDetails(tradeInDetails);
	}catch(DataAccessException e){
	    logger.error("TRADE IN: Error in retrieving Beast device, dataSharingKey:"
		    + tradeInDetails.getDataSharingKey(), e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}catch(Exception e){
	    logger.error("TRADE IN: Unexpected Error, dataSharingKey:" + tradeInDetails.getDataSharingKey(), e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}

    }

    @Override
    public String writeTradeInRecord(TitanDevice titanDevice) throws ServiceException {
	try{
	    tradeInDao.insertTradeInDevice(titanDevice);
	}catch(DataAccessException e){
	    logger.error("TRADE IN: Error in writing Titan Device to DSL, dataSharingKey:"
		    + titanDevice.getDataSharingKey(), e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}catch(Exception e){
	    logger.error("TRADE IN: Unexpected Error in getting Beast device, dataSharingKey:"
		    + titanDevice.getDataSharingKey(), e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}
	return titanDevice.getDataSharingKey();
    }

    @Override
    public String updateTradeInDevice(TitanDevice titanDevice) throws ServiceException {
	try{
	    tradeInDao.updateTradeInDevice(titanDevice);
	}catch(DataAccessException e){
	    logger.error("TRADE IN: Error in writing Titan Device to DSL, dataSharingKey:"
		    + titanDevice.getDataSharingKey(), e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}catch(Exception e){
	    logger.error("TRADE IN: Unexpected Error in getting Beast device, dataSharingKey:"
		    + titanDevice.getDataSharingKey(), e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}
	return titanDevice.getDataSharingKey();
    }

    @Override
    public void updateTradeInDocs(TitanDevice titanDevice) throws ServiceException {
	try{
	    tradeInDao.updateTradeInDocs(titanDevice);
	}catch(DataAccessException e){
	    logger.error("TRADE IN: Error in writing Titan Device to DSL, dataSharingKey:"
		    + titanDevice.getDataSharingKey(), e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}catch(Exception e){
	    logger.error("TRADE IN: Unexpected Error in getting Beast device, dataSharingKey:"
		    + titanDevice.getDataSharingKey(), e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}
    }

    @Override
    public TitanDevice getTradeInDocs(String dataSharingKey) throws ServiceException {

	try{
	    return tradeInDao.getTradeInDocs(dataSharingKey);
	}catch(EmptyResultDataAccessException e){
	    logger.error("TRADE IN: No Beast device found for dataSharingKey:" + dataSharingKey, e);
	    throw new ServiceException(IseExceptionCodeEnum.NOTFOUND, e.getMessage());
	}catch(DataAccessException e){
	    logger.error("TRADE IN: Error in getting Beast device, dataSharingKey:" + dataSharingKey, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}catch(Exception e){
	    logger.error("TRADE IN: Unexpected Error in getting Beast device, dataSharingKey:" + dataSharingKey, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}
    }

    @Override
    public TitanDevice getTradeInCarrierRecord(String dataSharingKey) throws ServiceException {

	try{
	    return tradeInDao.getTradeInCarrierRecord(dataSharingKey);
	}catch(EmptyResultDataAccessException e){
	    logger.error("TRADE IN: No Beast device found for dataSharingKey:" + dataSharingKey, e);
	    throw new ServiceException(IseExceptionCodeEnum.NOTFOUND, e.getMessage());
	}catch(DataAccessException e){
	    logger.error("TRADE IN: Error in getting Beast device, dataSharingKey:" + dataSharingKey, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}catch(Exception e){
	    logger.error("TRADE IN: Unexpected Error in getting Beast device, dataSharingKey:" + dataSharingKey, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}
    }

    @Override
    public TitanDevice getTradeInRecord(String dataSharingKey) throws ServiceException {

	try{
	    return tradeInDao.getTradeInRecord(dataSharingKey);
	}catch(EmptyResultDataAccessException e){
	    logger.error("TRADE IN: No Beast device found for dataSharingKey:" + dataSharingKey, e);
	    throw new ServiceException(IseExceptionCodeEnum.NOTFOUND, e.getMessage());
	}catch(DataAccessException e){
	    logger.error("TRADE IN: Error in getting Beast device, dataSharingKey:" + dataSharingKey, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}catch(Exception e){
	    logger.error("TRADE IN: Unexpected Error in getting Beast device, dataSharingKey:" + dataSharingKey, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}
    }

    @Override
    public boolean isTradable(BeastDevice tradeInDevice, DrpUser drpUser) throws ServiceException, IseBusinessException {
	String storeId = "";
	try{
	    DataTransferSummary dataTransferSummary = dataTransferDao.getDataTransferRow(tradeInDevice
		    .getDataSharingKey());
	    storeId = dataTransferSummary.getStoreId();
	    if (StringUtils.isBlank(tradeInDevice.getBeastTransactionId())){
		tradeInDevice.setBeastTransactionId(dataTransferSummary.getCapTransId());
	    }
	}catch(EmptyResultDataAccessException e){
	    logger.error("TRADE IN: Tradability check failed for device:" + tradeInDevice.toString(), e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}catch(DataAccessException e){
	    logger.error("TRADE IN: Tradability check failed for device:" + tradeInDevice.toString(), e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}catch(RuntimeException e){
	    logger.error("TRADE IN: Tradability check failed for device:" + tradeInDevice.toString(), e);
	    throw new ServiceException(IseExceptionCodeEnum.BusinessException, e.getMessage());
	}
	return tradabilityCheckSao.checkForTradability(tradeInDevice, drpUser, storeId);
    }

    public void setDataTransferDao(DataTransferDao dataTransferDao) {
	this.dataTransferDao = dataTransferDao;
    }

    public void setTradeInDeviceDetailsDao(TradeInDeviceDetailsDao tradeInDeviceDetailsDao) {
	this.tradeInDeviceDetailsDao = tradeInDeviceDetailsDao;
    }

    public void setTradeInDao(TradeInDao tradeInDao) {
	this.tradeInDao = tradeInDao;
    }

    public void setTradabilityCheckSao(TradabilityCheckSao tradabilityCheckSao) {
	this.tradabilityCheckSao = tradabilityCheckSao;
    }

}
