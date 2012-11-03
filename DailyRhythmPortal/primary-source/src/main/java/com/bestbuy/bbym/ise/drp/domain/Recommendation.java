package com.bestbuy.bbym.ise.drp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.BitSet;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.bestbuy.bbym.ise.domain.Line;

@XmlRootElement
public class Recommendation implements Serializable {

    private static final long serialVersionUID = 4267266673973705698L;
    private long id = 0;
    // Customer info
    private String firstName;
    private String lastName;
    private String mobileNo;
    private String bestTimeToContact;
    private String bbyCustomerId;
    private Boolean upgradeReminderText;
    private Boolean upgradeReminderCall;
    private Date upgradeEligibilityDate;
    private String subscriptionInfo;
    private BigDecimal tradeInValue;
    private BitSet deviceCapabilities;
    private String netUseInfo;
    private String recommendedSubscription;
    private String recommendedDevice;
    private BitSet wowRequirements;
    private Essentials essentials = new Essentials();
    private String notes;
    private String specialistContactInfo;
    private String storeId;
    private Date createdOn;
    private Date amendedOn;
    private String createdBy;
    private String amendedBy;
    // having serialization exceptions from this.
    private transient List<Line> otherLineList;
    private String empCrtLastNm;
    private String empCrtFrstNm;
    private String empAltLastNm;
    private String empAltFrstNm;

    // TODO Rather use RecommendationTypeEnum
    private int recShtTyp;
    private String addr;
    private String city;
    private String state;
    private String zipcode;
    private String bbyCnsFrstNm;
    private String bbyCnsLastNm;
    private String bbyCnsPhNbr;
    private String bbyCnsPhExt;

    private String dataSharingKey;
    private boolean transferFlag;

    private String email;
    private transient Long whenSaved;
    private String language;

    private Map<Long, String> preferences = new HashMap<Long, String>();

    /**
     * DO NOT CHANGE THE POSITION. The DB bit fields are mapped to these
     * positions. Add any new stuff to the end.
     */
    public enum DeviceCapabilities {

	INTERNET("internet"), EMAIL("email"), MUSIC("music"), VIDEO("video"), PHOTO("photo"), TV("tv"), NAVIGATION(
		"navigation"), GAMING("gaming"), TEXTING("texting"), UNLOCKED("unlocked");

	private final String label;

	private DeviceCapabilities(String label) {
	    this.label = label;
	}

	@Override
	public String toString() {
	    return this.label;
	}
    }

    /**
     * DO NOT CHANGE THE POSITION. The DB bit fields are mapped to these
     * positions. Add any new stuff to the end.
     */
    public enum WowRequirements {

	DATATRANSFER("datatransfer"), SOCIALNETWORKING("social"), PERSONALEMAIL("email"), POWERMANAGEMENT("power"),
	BLUETOOTHPAIRING("bluetooth"), VOICEMAIL("voicemail"), APPLICATIONS("applications"), OTHER("other"), SOFTWARE(
		"software");

	private final String label;

	private WowRequirements(String label) {
	    this.label = label;
	}

	@Override
	public String toString() {
	    return this.label;
	}
    }

    public Recommendation() {
	super();
	deviceCapabilities = new BitSet();
	setDeviceCapabilities(0);
	wowRequirements = new BitSet();
	setWowRequirements(0);
    }

    public void setId(long sheetId) {
	this.id = sheetId;
    }

    public long getId() {
	return id;
    }

    // ======= device capabilities ============
    /**
     * Used to set all the capabilities at once by sending a whole int in,
     * instead of one bit at a time.
     */
    public void setDeviceCapabilities(long value) {
	this.deviceCapabilities.clear();
	int i = 0;
	while(value != 0){
	    if ((value & 1) > 0){
		this.deviceCapabilities.set(i, true);
	    }else{
		this.deviceCapabilities.set(i, false);
	    }

	    i++;
	    value = value >>> 1;
	}
    }

    /**
     * Sets the flag corresponding to 'key' on or off
     * 
     * @param key
     *            the key to set
     * @param flag
     *            {@code true} if it's on, {@code false} if not.
     */
    public void setDeviceCapabilities(String key, Boolean flag) {
	DeviceCapabilities dc = DeviceCapabilities.valueOf(key);
	int bitpos = dc.ordinal();
	this.deviceCapabilities.set(bitpos, flag.booleanValue());

    }

    /**
     * Fetches a long to be used where a bitset would no longer be viable. (ie
     * db storage). <em>Note</em> A maximum of 64 flags can be encoded using
     * this scheme given a long is 64 bits long.
     * 
     * @return a long containing bits turned on relative to which device
     *         capabilities are set.
     */
    public long getDeviceCapabilities() {
	int length = DeviceCapabilities.values().length;
	long out = 0;
	for(int i = length; i >= 0; i--){
	    out = out << 1; // will have no effect first iteration.
	    if (this.deviceCapabilities.get(i)){
		out++; // should set trailing bit to true.
	    }
	}
	return out;
    }

    public Boolean getDeviceCapabilities(String key) {
	DeviceCapabilities dc = DeviceCapabilities.valueOf(key);
	int bitpos = dc.ordinal();
	return this.deviceCapabilities.get(bitpos);
    }

    // =========== wow requirements =============
    /**
     * Fetches a long to be used where a bitset would no longer be viable. (ie
     * db storage)<em>Note</em> A maximum of 64 flags can be encoded using this
     * scheme given a long is 64 bits long.
     * 
     * @return a long containing bits turned on relative to which
     *         wowRequirements are set.
     */
    public long getWowRequirements() { // converts the bitset into a long.
	int length = WowRequirements.values().length;
	long out = 0;
	for(int i = length; i >= 0; i--){
	    out = out << 1; // will have no effect first iteration.
	    if (this.wowRequirements.get(i)){
		out++; // should set trailing bit to true.
	    }
	}
	return out;
    }

    /**
     * Given a key will return whether or not that key is set to true.
     */
    public Boolean getWowRequirements(String key) {
	int bitpos = WowRequirements.valueOf(key).ordinal();
	return this.wowRequirements.get(bitpos);
    }

    public void setWowRequirements(long value) {
	this.wowRequirements.clear();
	int i = 0;
	while(value != 0){
	    if ((value & 1) > 0){
		this.wowRequirements.set(i, true);
	    }else{
		this.wowRequirements.set(i, false);
	    }

	    i++;
	    value = value >>> 1;
	}
    }

    public void setWowRequirements(String key, Boolean flag) {
	int bitpos = WowRequirements.valueOf(key).ordinal();
	this.wowRequirements.set(bitpos, flag.booleanValue());

	// TODO: -miked this should be refactored to use the Enum Name instead
	// of a string.
    }

    // end of bitfield related operations. i may move these to the front end as
    // it makes more sense for recsheet to just store the long's...

    public String getNetUseInfo() {
	return netUseInfo;
    }

    public void setNetUseInfo(String netUseInfo) {
	this.netUseInfo = netUseInfo;
    }

    public void setStoreId(String storeId) {
	this.storeId = storeId;
    }

    public String getStoreId() {
	return storeId;
    }

    public Essentials getEssentials() {
	return essentials;
    }

    public void setEssentials(Essentials essentials) {
	this.essentials = essentials;
	if (this.essentials == null){
	    this.essentials = new Essentials();
	}
    }

    public String getNotes() {
	return notes;
    }

    public void setNotes(String notes) {
	this.notes = notes;
    }

    public void setSpecialistContactInfo(String specialistContactInfo) {
	this.specialistContactInfo = specialistContactInfo;
    }

    public String getSpecialistContactInfo() {
	if (specialistContactInfo == null){
	    return "";
	}
	return specialistContactInfo;
    }

    // FIXME Animesh: this needs to be refactored along with WOW and Dev
    // Capability bitset implementation
    public boolean isInternetCapability() {
	return getDeviceCapabilities(DeviceCapabilities.INTERNET.name());
    }

    public boolean isEmailCapability() {
	return getDeviceCapabilities(DeviceCapabilities.EMAIL.name());
    }

    public boolean isMusicCapability() {
	return getDeviceCapabilities(DeviceCapabilities.MUSIC.name());
    }

    public boolean isVideoCapability() {
	return getDeviceCapabilities(DeviceCapabilities.VIDEO.name());
    }

    public boolean isPhotoCapability() {
	return getDeviceCapabilities(DeviceCapabilities.PHOTO.name());
    }

    public boolean isTvCapability() {
	return getDeviceCapabilities(DeviceCapabilities.TV.name());
    }

    public boolean isNavCapability() {
	return getDeviceCapabilities(DeviceCapabilities.NAVIGATION.name());
    }

    public boolean isGamingCapability() {
	return getDeviceCapabilities(DeviceCapabilities.GAMING.name());
    }

    public boolean isTextingCapability() {
	return getDeviceCapabilities(DeviceCapabilities.TEXTING.name());
    }

    public boolean isUnlockedCapability() {
	return getDeviceCapabilities(DeviceCapabilities.UNLOCKED.name());
    }

    public boolean isDataTransferWOW() {
	return getWowRequirements(WowRequirements.DATATRANSFER.name());
    }

    public boolean isEmailWOW() {
	return getWowRequirements(WowRequirements.PERSONALEMAIL.name());
    }

    public boolean isBluetoothWOW() {
	return getWowRequirements(WowRequirements.BLUETOOTHPAIRING.name());
    }

    public boolean isAppsWOW() {
	return getWowRequirements(WowRequirements.APPLICATIONS.name());
    }

    public boolean isSoftwareUpdatesWOW() {
	return getWowRequirements(WowRequirements.SOFTWARE.name());
    }

    public boolean isSocialWOW() {
	return getWowRequirements(WowRequirements.SOCIALNETWORKING.name());
    }

    public boolean isPowerMgmtWOW() {
	return getWowRequirements(WowRequirements.POWERMANAGEMENT.name());
    }

    public boolean isVmailWOW() {
	return getWowRequirements(WowRequirements.VOICEMAIL.name());
    }

    public boolean isOtherWOW() {
	return getWowRequirements(WowRequirements.OTHER.name());
    }

    public Date getCreatedOn() {
	return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
	this.createdOn = createdOn;
    }

    public Date getAmendedOn() {
	return amendedOn;
    }

    public void setAmendedOn(Date amendedOn) {
	this.amendedOn = amendedOn;
    }

    public String getCreatedBy() {
	return createdBy;
    }

    public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
    }

    public String getAmendedBy() {
	return amendedBy;
    }

    public void setAmendedBy(String amendedBy) {
	this.amendedBy = amendedBy;
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public String getMobileNo() {
	return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
	this.mobileNo = mobileNo;
    }

    public String getBestTimeToContact() {
	return bestTimeToContact;
    }

    public void setBestTimeToContact(String bestTimeToContact) {
	this.bestTimeToContact = bestTimeToContact;
    }

    public String getBbyCustomerId() {
	return bbyCustomerId;
    }

    public void setBbyCustomerId(String bbyCustomerId) {
	this.bbyCustomerId = bbyCustomerId;
    }

    public Boolean getUpgradeReminderText() {
	return upgradeReminderText;
    }

    public void setUpgradeReminderText(Boolean upgradeReminderText) {
	this.upgradeReminderText = upgradeReminderText;
    }

    public Boolean getUpgradeReminderCall() {
	return upgradeReminderCall;
    }

    public void setUpgradeReminderCall(Boolean upgradeReminderCall) {
	this.upgradeReminderCall = upgradeReminderCall;
    }

    public Date getUpgradeEligibilityDate() {
	return upgradeEligibilityDate;
    }

    public void setUpgradeEligibilityDate(Date upgradeEligibilityDate) {
	this.upgradeEligibilityDate = upgradeEligibilityDate;
    }

    public String getSubscriptionInfo() {
	return subscriptionInfo;
    }

    public void setSubscriptionInfo(String subscriptionInfo) {
	this.subscriptionInfo = subscriptionInfo;
    }

    public BigDecimal getTradeInValue() {
	return tradeInValue;
    }

    public void setTradeInValue(BigDecimal tradeInValue) {
	this.tradeInValue = tradeInValue;
    }

    public String getRecommendedSubscription() {
	return recommendedSubscription;
    }

    public void setRecommendedSubscription(String recommendedSubscription) {
	this.recommendedSubscription = recommendedSubscription;
    }

    public String getRecommendedDevice() {
	return recommendedDevice;
    }

    public void setRecommendedDevice(String recommendedDevice) {
	this.recommendedDevice = recommendedDevice;
    }

    public String getDataSharingKey() {
	return dataSharingKey;
    }

    public void setDataSharingKey(String dataSharingKey) {
	this.dataSharingKey = dataSharingKey;
    }

    public boolean isTransferFlag() {
	return transferFlag;
    }

    public void setTransferFlag(boolean transferFlag) {
	this.transferFlag = transferFlag;
    }

    public List<Line> getOtherLineList() {
	return otherLineList;
    }

    public void setOtherLineList(List<Line> otherLineList) {
	this.otherLineList = otherLineList;
    }

    /**
     * Sets the created employee last name.
     */
    public void setEmpCrtLastNm(String empCrtLastNm) {
	this.empCrtLastNm = empCrtLastNm;
    }

    /**
     * Gets the created employee last name.
     */
    public String getEmpCrtLastNm() {
	return empCrtLastNm;
    }

    /**
     * Sets the created employee first name.
     */
    public void setEmpCrtFrstNm(String empCrtFrstNm) {
	this.empCrtFrstNm = empCrtFrstNm;
    }

    /**
     * Gets the created employee first name.
     */
    public String getEmpCrtFrstNm() {
	return empCrtFrstNm;
    }

    /**
     * Sets the altered or amended employee last name.
     */
    public void setEmpAltLastNm(String empAltLastNm) {
	this.empAltLastNm = empAltLastNm;
    }

    /**
     * Gets the altered or amended employee last name.
     */
    public String getEmpAltLastNm() {
	return empAltLastNm;
    }

    /**
     * Sets the altered or amended employee first name.
     */
    public void setEmpAltFrstNm(String empAltFrstNm) {
	this.empAltFrstNm = empAltFrstNm;
    }

    /**
     * Gets the altered or amended employee first name.
     */
    public String getEmpAltFrstNm() {
	return empAltFrstNm;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    /**
     * Returns time when rec sheet was saved.
     * 
     * @return  time when rec sheet was saved.
     */
    public Long getWhenSaved() {
	return whenSaved;
    }

    /**
     * Sets  time when rec sheet was saved.
     * 
     * @param whenSaved  time when rec sheet was saved.
     */
    public void setWhenSaved(Long whenSaved) {
	this.whenSaved = whenSaved;
    }

    public String getAddr() {
	return addr;
    }

    public void setAddr(String addr) {
	this.addr = addr;
    }

    public String getCity() {
	return city;
    }

    public void setCity(String city) {
	this.city = city;
    }

    public String getState() {
	return state;
    }

    public void setState(String state) {
	this.state = state;
    }

    public String getZipcode() {
	return zipcode;
    }

    public void setZipcode(String zipcode) {
	this.zipcode = zipcode;
    }

    public String getBbyCnsFrstNm() {
	return bbyCnsFrstNm;
    }

    public void setBbyCnsFrstNm(String bbyCnsFrstNm) {
	this.bbyCnsFrstNm = bbyCnsFrstNm;
    }

    public String getBbyCnsLastNm() {
	return bbyCnsLastNm;
    }

    public void setBbyCnsLastNm(String bbyCnsLastNm) {
	this.bbyCnsLastNm = bbyCnsLastNm;
    }

    public String getBbyCnsPhNbr() {
	return bbyCnsPhNbr;
    }

    public void setBbyCnsPhNbr(String bbyCnsPhNbr) {
	this.bbyCnsPhNbr = bbyCnsPhNbr;
    }

    public String getBbyCnsPhExt() {
	return bbyCnsPhExt;
    }

    public void setBbyCnsPhExt(String bbyCnsPhExt) {
	this.bbyCnsPhExt = bbyCnsPhExt;
    }

    /**
     * Sets value for recShtTyp
     * The values in the database are numbers with the following as the key:
     * 1 = Mobile
     * 2 = PC
     * 3 = MAC
     * 4 = Tablet
     * @param recShtTyp
     */
    public void setRecShtTyp(int recShtTyp) {
	this.recShtTyp = recShtTyp;
    }

    /**
     * Gets value for recShtTyp
     * The values in the database are numbers with the following as the key:
     * 1 = Mobile
     * 2 = PC
     * 3 = MAC
     * 4 = Tablet
     * @return recShtTyp
     */
    public int getRecShtTyp() {
	return recShtTyp;
    }

    public String getRecStringType() {
	return RecommendationTypeEnum.getIdByUniq(this.recShtTyp);
    }

    public void assignRecSheetType(String recType) {
	this.setRecShtTyp(RecommendationTypeEnum.getUniqById(recType));
    }

    public Map<Long, String> getPreferences() {
	return preferences;
    }

    public void setPreferences(Map<Long, String> preferences) {
	this.preferences = preferences;
    }

    public void setPreference(Long id, String val) {
	preferences.put(id, val);
    }

    public String getPreference(Long id) {
	return preferences.get(id);
    }

    public void removePreferences(Long[] ids) {
	if (ids != null && ids.length != 0){
	    for(int i = 0; i < ids.length; i++){
		preferences.remove(ids[i]);
	    }
	}
    }

    public String getLanguage() {
	return language;
    }

    public void setLanguage(String language) {
	this.language = language;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }
}
