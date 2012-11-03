package com.bestbuy.bbym.ise.drp.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.drp.domain.ACDSParameters;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.Manifest;
import com.bestbuy.bbym.ise.drp.domain.ManifestLine;
import com.bestbuy.bbym.ise.drp.domain.ManifestSearchCriteria;
import com.bestbuy.bbym.ise.drp.utils.SortUtil;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;

@Service("shippingService2")
public class DummyShippingService implements ShippingService {

    private static Logger logger = LoggerFactory.getLogger(DummyShippingService.class);

    private final static List<ACDSParameters> acdsParameters = new ArrayList<ACDSParameters>();
    static{
	ACDSParameters acdsParam;
	//
	acdsParam = new ACDSParameters();
	acdsParam.setGroupName("$SHIPMENT_PORTAL$");
	acdsParam.setSysCode("$ISE_SHK_WARN_MSG$");
	acdsParam.setValue("Tradein");
	acdsParam.setDescription("Do you wish to Tradein shrink?");
	acdsParam.setActivationFlag("1");
	acdsParameters.add(acdsParam);
	//
	acdsParam = new ACDSParameters();
	acdsParam.setGroupName("$SHIPMENT_PORTAL$");
	acdsParam.setSysCode("$ISE_SHK_WARN_MSG$");
	acdsParam.setValue("BuyBack");
	acdsParam.setDescription("Do you wish to BuyBack shrink?");
	acdsParam.setActivationFlag("1");
	acdsParameters.add(acdsParam);
	//
	acdsParam = new ACDSParameters();
	acdsParam.setGroupName("$SHIPMENT_PORTAL$");
	acdsParam.setSysCode("$ISE_SHK_WARN_MSG$");
	acdsParam.setValue("ReturnExchange");
	acdsParam.setDescription("Do you wish to ReturnExchange shrink?");
	acdsParam.setActivationFlag("1");
	acdsParameters.add(acdsParam);
	//
	acdsParam = new ACDSParameters();
	acdsParam.setGroupName("$SHIPMENT_PORTAL$");
	acdsParam.setSysCode("$ISE_SHK_WARN_MSG$");
	acdsParam.setValue("RapidExchange");
	acdsParam.setDescription("Do you wish to RapidExchange shrink?");
	acdsParam.setActivationFlag("1");
	acdsParameters.add(acdsParam);
	//
	acdsParam = new ACDSParameters();
	acdsParam.setGroupName("$SHIPMENT_PORTAL$");
	acdsParam.setSysCode("$ISE_SHK_WARN_MSG$");
	acdsParam.setValue("DOALM");
	acdsParam.setDescription("Do you wish to DOALM shrink?");
	acdsParameters.add(acdsParam);
	//
	acdsParam = new ACDSParameters();
	acdsParam.setGroupName("$SHIPMENT_PORTAL$");
	acdsParam.setSysCode("$RECENT_MANIFEST_NO$");
	acdsParam.setValue("15");
	acdsParam.setActivationFlag("1");
	acdsParameters.add(acdsParam);
	//
	acdsParam = new ACDSParameters();
	acdsParam.setGroupName("$SHIPMENT_PORTAL$");
	acdsParam.setSysCode("$MAX_ITEM_PER_MANIFEST$");
	acdsParam.setValue("10");
	acdsParam.setActivationFlag("1");
	acdsParameters.add(acdsParam);
	//
	acdsParam = new ACDSParameters();
	acdsParam.setGroupName("$SHIPMENT_PORTAL$");
	acdsParam.setSysCode("$DEVICE_ELIGIBLE_STAT_CODE$");
	acdsParam.setValue("RDYSHP");
	acdsParam.setDescription("Ready For Shipping");
	acdsParam.setActivationFlag("1");
	acdsParameters.add(acdsParam);
	//
	acdsParam = new ACDSParameters();
	acdsParam.setGroupName("$SHIPMENT_PORTAL$");
	acdsParam.setSysCode("$DEVICE_HOLD_STAT_CODE$");
	acdsParam.setValue("NRFSHP");
	acdsParam.setDescription("Not Ready For Shipping");
	acdsParam.setActivationFlag("1");
	acdsParameters.add(acdsParam);
	//
	acdsParam = new ACDSParameters();
	acdsParam.setGroupName("$SHIPMENT_PORTAL$");
	acdsParam.setSysCode("$DEVICE_SHIPPED_STAT_CODE$");
	acdsParam.setValue("RLSUPS");
	acdsParam.setDescription("UPS");
	acdsParam.setActivationFlag("1");
	acdsParameters.add(acdsParam);
	//
	acdsParam = new ACDSParameters();
	acdsParam.setGroupName("$SHIPMENT_PORTAL$");
	acdsParam.setSysCode("$DEVICE_RECEIVED_STAT_CODE$");
	acdsParam.setValue("RECIVD");
	acdsParam.setDescription("Received");
	acdsParam.setActivationFlag("1");
	acdsParameters.add(acdsParam);
	//
	acdsParam = new ACDSParameters();
	acdsParam.setGroupName("$SHIPMENT_PORTAL$");
	acdsParam.setSysCode("$SHK_DISPT_TYPE$");
	acdsParam.setValue("SHK");
	acdsParam.setDescription("Shrink Disposition Type");
	acdsParam.setActivationFlag("1");
	acdsParameters.add(acdsParam);
	//
	acdsParam = new ACDSParameters();
	acdsParam.setGroupName("$SHIPMENT_PORTAL$");
	acdsParam.setSysCode("$SHK_ITEM_STATUS$");
	acdsParam.setValue("RLSCLS");
	acdsParam.setDescription("Store Shrink Status");
	acdsParam.setActivationFlag("1");
	acdsParameters.add(acdsParam);
    }

    @Override
    public List<ACDSParameters> getACDSParameters(String groupName) throws ServiceException, IseBusinessException {
	DummyData.sleep(1);
	DummyData.throwServiceException(30);
	DummyData.throwIseBusinessException(30);
	if (groupName == null){
	    throw new IseBusinessException("Input groupName is null");
	}
	List<ACDSParameters> acdsParams = new ArrayList<ACDSParameters>();
	for(ACDSParameters acdsParam: acdsParameters){
	    if (acdsParam.getGroupName() != null && acdsParam.getGroupName().equals(groupName)){
		acdsParams.add(acdsParam);
	    }
	}
	return acdsParams;
    }

    @Override
    public Manifest getShipManifestInfo(BigInteger manifestID, DrpUser drpUser) throws ServiceException,
	    IseBusinessException {
	DummyData.sleep(2);
	DummyData.throwServiceException(30);
	DummyData.throwIseBusinessException(30);
	if (manifestID == null){
	    throw new IseBusinessException("Input manifestID is null");
	}
	return closeOpenManifest(manifestID);
    }

    @Override
    public void shrinkDevice(String itemTag, String returnStatus, String disposition) throws ServiceException,
	    IseBusinessException {
	logger.info("Shrinking Device itemTag=" + itemTag + " returnStatus=" + returnStatus + " disposition="
		+ disposition);
	DummyData.sleep(2);
	DummyData.throwServiceException(30);
	DummyData.throwIseBusinessException(30);
	for(ManifestLine m: baseInventory){
	    if (m.getItemTag() != null && m.getItemTag().equals(itemTag)){
		m.setShrinkable(false);
	    }
	}
    }

    @Override
    public byte[] getShippingImage(BigInteger manifestID) throws ServiceException, IseBusinessException {
	DummyData.sleep(2);
	DummyData.throwServiceException(20);
	DummyData.throwIseBusinessException(20);

	if (manifestID == null){
	    throw new ServiceException(IseExceptionCodeEnum.GeneralSystem, "Input manifestID is null");
	}

	byte[] imageArray = new byte[DummyShippingLabel1.BYTES.length + DummyShippingLabel2.BYTES.length
		+ DummyShippingLabel3.BYTES.length + DummyShippingLabel4.BYTES.length];
	int count = 0;
	for(int i = 0; i < DummyShippingLabel1.BYTES.length; i++){
	    imageArray[count++] = (byte) DummyShippingLabel1.BYTES[i];
	}
	for(int i = 0; i < DummyShippingLabel2.BYTES.length; i++){
	    imageArray[count++] = (byte) DummyShippingLabel2.BYTES[i];
	}
	for(int i = 0; i < DummyShippingLabel3.BYTES.length; i++){
	    imageArray[count++] = (byte) DummyShippingLabel3.BYTES[i];
	}
	for(int i = 0; i < DummyShippingLabel4.BYTES.length; i++){
	    imageArray[count++] = (byte) DummyShippingLabel4.BYTES[i];
	}
	return imageArray;
    }

    private static Comparator<Manifest> manifestComparator = new Comparator<Manifest>() {

	@Override
	public int compare(Manifest m1, Manifest m2) {
	    return SortUtil.sortBigInteger(m1.getManifestID(), m2.getManifestID(), false);
	}
    };
    private static List<Manifest> baseManifests = new ArrayList<Manifest>();
    static{
	int numBaseManifests = DummyData.getRandomIndex(40) + 1, numManifestLines;
	Manifest mf;
	ManifestLine mfl;
	List<ManifestLine> lineList;
	for(int i = 0; i < numBaseManifests; i++){
	    mf = new Manifest();
	    mf.setManifestID(new BigInteger(DummyData.getManifestId()));
	    mf.setDateTimeCreated(DummyData.getDate());
	    mf.setStatus(DummyData.getNonOpenManifestStatus());
	    mf.setTrackingIdentifier(DummyData.getUpsTrackingNumber());
	    lineList = new ArrayList<ManifestLine>();
	    numManifestLines = DummyData.getRandomIndex(10) + 1;
	    for(int j = 0; j < numManifestLines; j++){
		mfl = new ManifestLine();
		mfl.setManifestLineID(DummyData.getManifestLineId());
		mfl.setManifestID(mf.getManifestID());
		mfl.setItemTag(DummyData.getItemTagId());
		mfl.setImeiesn(DummyData.getManifestItemImei());
		mfl.setProductDescription(DummyData.getManifestItemDescription());
		mfl.setReturnType(DummyData.getManifestItemReturnType());
		mfl.setDeviceStatus(DummyData.getManifestItemStatus());
		mfl.setSku(DummyData.getSku());
		mfl.setTransferNumber(DummyData.getRewardZoneNumber());
		mfl.setShrinkable(DummyData.getRandomIndex(10) < 7);
		lineList.add(mfl);
	    }
	    mf.setManifestLine(lineList);

	    baseManifests.add(mf);
	}
	Collections.sort(baseManifests, manifestComparator);

	logger.debug("# of manifests is " + baseManifests.size());

	mf = baseManifests.get(0);
	mf.setStatus("Open");
	mf.setTrackingIdentifier(null);
    }

    @Override
    public ManifestLine addManifestLine(ManifestLine manifestLine, DrpUser User) throws ServiceException,
	    IseBusinessException {
	DummyData.sleep(2);
	DummyData.throwServiceException(30);
	DummyData.throwIseBusinessException(30);

	if (manifestLine == null){
	    throw new IseBusinessException("Input manifestLine is null");
	}
	if (manifestLine.getItemTag() == null){
	    throw new IseBusinessException("Input manifestLine.itemTag is null");
	}

	// check if item tag already in some manifest
	for(Manifest m: baseManifests){
	    for(ManifestLine ml: m.getManifestLine()){
		if (manifestLine.getItemTag().equals(ml.getItemTag())){
		    throw new IseBusinessException("Input manifestLine.itemTag already exists in some manifest");
		}
	    }
	}

	Manifest mf;

	// create new manifest
	if (manifestLine.getManifestID() == null){
	    mf = new Manifest();
	    mf.setDateTimeCreated(new Date());
	    mf.setManifestID(new BigInteger(DummyData.getManifestId()));
	    mf.setStatus("Open");
	    mf.setManifestLine(new ArrayList<ManifestLine>());
	    baseManifests.add(mf);
	    Collections.sort(baseManifests, manifestComparator);
	    manifestLine.setManifestID(mf.getManifestID());
	    logger.debug("newManifest=" + mf);
	}else{
	    mf = findManifest(manifestLine.getManifestID());
	    if (mf == null){
		throw new IseBusinessException("No manifest with ID " + manifestLine.getManifestID() + " exists");
	    }
	    if (mf.getTrackingIdentifier() != null){
		throw new IseBusinessException("Can only add manifestLine to open manifest");
	    }
	    logger.debug("openManifest=" + mf);
	}
	manifestLine.setManifestLineID(DummyData.getManifestLineId());
	manifestLine.setImeiesn(DummyData.getManifestItemImei());
	manifestLine.setProductDescription(DummyData.getManifestItemDescription());
	manifestLine.setReturnType(DummyData.getManifestItemReturnType());
	manifestLine.setDeviceStatus(DummyData.getManifestItemStatus());
	mf.getManifestLine().add(manifestLine);
	return manifestLine;
    }

    private static Manifest findManifest(BigInteger manifestID) {
	for(Manifest mf: baseManifests){
	    if (ObjectUtils.equals(mf.getManifestID(), manifestID)){
		return mf;
	    }
	}
	return null;
    }

    static Manifest closeOpenManifest(BigInteger manifestID) throws ServiceException, IseBusinessException {
	Manifest mf = findManifest(manifestID);
	if (mf == null){
	    throw new IseBusinessException("No manifest with ID " + manifestID + " exists");
	}
	if (mf.getTrackingIdentifier() != null){
	    throw new IseBusinessException("Can only close an open manifest");
	}

	mf.setTrackingIdentifier(DummyData.getUpsTrackingNumber());
	mf.setStatus("In Transit");
	mf.setDateCompleted(new Date());

	Manifest returnManifest = new Manifest();
	returnManifest.setManifestID(mf.getManifestID());
	returnManifest.setTrackingIdentifier(mf.getTrackingIdentifier());
	returnManifest.setStatus(mf.getStatus());
	return returnManifest;
    }

    @Override
    public boolean removeManifestLine(List<ManifestLine> manifestlines, DrpUser drpUser) throws ServiceException,
	    IseBusinessException {
	DummyData.sleep(2);
	DummyData.throwServiceException(20);
	DummyData.throwIseBusinessException(20);

	if (manifestlines == null){
	    throw new ServiceException(IseExceptionCodeEnum.GeneralSystem, "Input manifestLines is null");
	}

	Manifest mf;
	int countRemovedLines = 0, idx;
	for(ManifestLine mfl: manifestlines){
	    mf = findManifest(mfl.getManifestID());
	    if (mf == null){
		continue;
	    }
	    idx = findManifestLineIndex(mf, mfl.getManifestLineID());
	    if (idx < 0){
		continue;
	    }
	    mf.getManifestLine().remove(idx);
	    countRemovedLines++;
	}
	return countRemovedLines == manifestlines.size();
    }

    private int findManifestLineIndex(Manifest manifest, BigInteger manifestLineID) {
	if (manifestLineID == null || manifest.getManifestLine() == null){
	    return -1;
	}
	ManifestLine mfl;
	for(int j = 0; j < manifest.getManifestLine().size(); j++){
	    mfl = manifest.getManifestLine().get(j);
	    if (mfl != null && ObjectUtils.equals(mfl.getManifestLineID(), manifestLineID)){
		return j;
	    }
	}
	return -1;
    }

    @Override
    public List<Manifest> searchManifests(ManifestSearchCriteria manifestSearchCriteria, DrpUser drpUser)
	    throws ServiceException, IseBusinessException {
	DummyData.sleep(2);
	DummyData.throwServiceException(40);
	DummyData.throwIseBusinessException(40);
	if (manifestSearchCriteria == null){
	    return baseManifests;
	}
	List<Manifest> filteredList = new ArrayList<Manifest>();
	boolean keepIt;
	int count = 0;
	Calendar manifestCal = Calendar.getInstance(), startDateCal = Calendar.getInstance(), endDateCal = Calendar
		.getInstance();
	if (manifestSearchCriteria.getStartDate() != null){
	    startDateCal.setTime(manifestSearchCriteria.getStartDate());
	}
	if (manifestSearchCriteria.getEndDate() != null){
	    endDateCal.setTime(manifestSearchCriteria.getEndDate());
	}
	for(Manifest mf: baseManifests){
	    count++;
	    keepIt = true;
	    if (manifestSearchCriteria.getManifestID() != null){
		if (!manifestSearchCriteria.getManifestID().equals(mf.getManifestID())){
		    keepIt = false;
		}
	    }
	    if (manifestSearchCriteria.getMostRecentNumber() != null){
		if (count > manifestSearchCriteria.getMostRecentNumber().intValue()){
		    keepIt = false;
		}
	    }
	    if (manifestSearchCriteria.getTrackingIdentifier() != null){
		if (!manifestSearchCriteria.getTrackingIdentifier().equals(mf.getTrackingIdentifier())){
		    keepIt = false;
		}
	    }
	    if (mf.getDateTimeCreated() != null){
		manifestCal.setTime(mf.getDateTimeCreated());
		if (manifestSearchCriteria.getStartDate() != null){
		    if (manifestCal.before(startDateCal)){
			keepIt = false;
		    }
		}
		if (manifestSearchCriteria.getEndDate() != null){
		    if (manifestCal.after(endDateCal)){
			keepIt = false;
		    }
		}
	    }
	    // TODO add search by IMEI
	    // TODO add search by item tag ID
	    if (keepIt){
		filteredList.add(mf);
	    }
	}
	return filteredList;
    }

    @Override
    public byte[] generateManifestDoc(BigInteger manifestID, DrpUser User) throws ServiceException {
	DummyData.sleep(2);
	DummyData.throwServiceException(20);

	if (manifestID == null){
	    throw new ServiceException(IseExceptionCodeEnum.GeneralSystem, "Input manifestID is null");
	}

	byte[] docArray = new byte[DummyManifestDoc.BYTES.length];
	for(int i = 0; i < DummyManifestDoc.BYTES.length; i++){
	    docArray[i] = (byte) DummyManifestDoc.BYTES[i];
	}
	return docArray;
    }

    private static List<ManifestLine> baseInventory = new ArrayList<ManifestLine>();
    static{
	int numManifestLines = DummyData.getRandomIndex(20) + 1;
	ManifestLine mfl;
	for(int j = 0; j < numManifestLines; j++){
	    mfl = new ManifestLine();
	    mfl.setItemTag(DummyData.getItemTagId());
	    mfl.setImeiesn(DummyData.getManifestItemImei());
	    mfl.setProductDescription(DummyData.getManifestItemDescription());
	    mfl.setReturnType(DummyData.getManifestItemReturnType());
	    mfl.setDeviceStatus(DummyData.getManifestItemStatus());
	    mfl.setShort(DummyData.getManifestItemShortStatus(mfl.getDeviceStatus()));
	    mfl.setSku(DummyData.getSku());
	    mfl.setTransferNumber(DummyData.getRewardZoneNumber());
	    mfl.setShrinkable(DummyData.getRandomIndex(10) < 7);
	    baseInventory.add(mfl);
	}
	logger.debug("# of inventory items is " + baseInventory.size());
    }

    @Override
    public List<ManifestLine> searchManifest(ManifestSearchCriteria manifestSearchCriteria, DrpUser drpUser)
	    throws ServiceException, IseBusinessException {
	DummyData.sleep(2);
	DummyData.throwServiceException(50);
	DummyData.throwIseBusinessException(50);
	if (manifestSearchCriteria == null){
	    return baseInventory;
	}
	List<ManifestLine> filteredList = new ArrayList<ManifestLine>();
	boolean keepIt;
	for(ManifestLine mfl: baseInventory){
	    keepIt = true;
	    if (manifestSearchCriteria.getItemTag() != null){
		if (!manifestSearchCriteria.getItemTag().equals(mfl.getItemTag())){
		    keepIt = false;
		}
	    }
	    if (manifestSearchCriteria.getImeiesn() != null){
		if (!manifestSearchCriteria.getImeiesn().equals(mfl.getImeiesn())){
		    keepIt = false;
		}
	    }
	    if (manifestSearchCriteria.getManifestStatus() != null){
		if ("RDYSHP".equals(manifestSearchCriteria.getManifestStatus())){
		    if (!(mfl.getDeviceStatus() != null && mfl.getDeviceStatus().startsWith("Ready"))){
			keepIt = false;
		    }
		}else if ("NRFSHP".equals(manifestSearchCriteria.getManifestStatus())){
		    if (!(mfl.getDeviceStatus() != null && mfl.getDeviceStatus().startsWith("Not Ready"))){
			keepIt = false;
		    }
		}else if ("RLSUPS".equals(manifestSearchCriteria.getManifestStatus())){
		    if (!(mfl.getDeviceStatus() != null && mfl.getDeviceStatus().startsWith("UPS-"))){
			keepIt = false;
		    }
		}else if ("RECIVD".equals(manifestSearchCriteria.getManifestStatus())){
		    if (!(mfl.getDeviceStatus() != null && mfl.getDeviceStatus().startsWith("Received"))){
			keepIt = false;
		    }
		}
	    }
	    if (keepIt){
		filteredList.add(mfl);
	    }
	}
	return filteredList;
    }
}
