package com.bestbuy.bbym.ise.drp.beast.tradein.mapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Java bean encapsulates the attributes that should be displayed on Customer
 * QA screen, user story B-09025
 * 
 * @author a915722
 */
public class CustomerQABean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String addr1;
    private String addr2;
    private String city;
    private String state;
    private String zip;
    private String phoneNumber;
    private String email;
    private String idType;

    private static final List<String> idTypeOptions = new ArrayList<String>(Arrays.asList(new String[] {
	    "Driver's License", "Passport", "Military ID", "Others" }));

    private String idIssuer;
    private String idNumber;
    private Date idDateIssued;
    private Date idExpiration;
    private String idDescription;

    /**
     * Returns first name.
     * 
     * @return first name.
     */
    public String getFirstName() {
	return firstName;
    }

    /**
     * Sets  first name.
     * 
     * @param firstName  first name.
     */
    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    /**
     * Returns last name.
     * 
     * @return last name.
     */
    public String getLastName() {
	return lastName;
    }

    /**
     * Sets  last name.
     * 
     * @param lastName  last name.
     */
    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    /**
     * Returns date of birth.
     * 
     * @return date of birth.
     */
    public Date getDateOfBirth() {
	return dateOfBirth;
    }

    /**
     * Sets  date of birth.
     * 
     * @param dateOfBirth  date of birth.
     */
    public void setDateOfBirth(Date dateOfBirth) {
	this.dateOfBirth = dateOfBirth;
    }

    /**
     * Returns address line 1.
     * 
     * @return address line 1.
     */
    public String getAddr1() {
	return addr1;
    }

    /**
     * Sets  address line 1.
     * 
     * @param addr1  address line 1.
     */
    public void setAddr1(String addr1) {
	this.addr1 = addr1;
    }

    /**
     * Returns  address line 2.
     * 
     * @return  address line 2.
     */
    public String getAddr2() {
	return addr2;
    }

    /**
     * Sets  address line 2.
     * 
     * @param addr1  address line 2.
     */
    public void setAddr2(String addr2) {
	this.addr2 = addr2;
    }

    /**
     * Returns city.
     * 
     * @return city.
     */
    public String getCity() {
	return city;
    }

    /**
     * Sets  city.
     * 
     * @param city  city.
     */
    public void setCity(String city) {
	this.city = city;
    }

    /**
     * Returns state.
     * 
     * @return state.
     */
    public String getState() {
	return state;
    }

    /**
     * Sets  state.
     * 
     * @param state  state.
     */
    public void setState(String state) {
	this.state = state;
    }

    /**
     * Returns zip.
     * 
     * @return zip.
     */
    public String getZip() {
	return zip;
    }

    /**
     * Sets  zip.
     * 
     * @param zip  zip.
     */
    public void setZip(String zip) {
	this.zip = zip;
    }

    /**
     * Returns phone number.
     * 
     * @return phone number.
     */
    public String getPhoneNumber() {
	return phoneNumber;
    }

    /**
     * Sets  phone number.
     * 
     * @param phoneNumber  phone number.
     */
    public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
    }

    /**
     * Returns email.
     * 
     * @return email.
     */
    public String getEmail() {
	return email;
    }

    /**
     * Sets  email.
     * 
     * @param email  email.
     */
    public void setEmail(String email) {
	this.email = email;
    }

    /**
     * Returns ID type.
     * 
     * @return ID type.
     */
    public String getIdType() {
	return idType;
    }

    /**
     * Sets  ID type.
     * 
     * @param idType  ID type.
     */
    public void setIdType(String idType) {
	this.idType = idType;
    }

    /**
     * Returns ID description.
     * 
     * @return ID description.
     */
    public String getIdDescription() {
	return idDescription;
    }

    /**
     * Sets  ID description.
     * 
     * @param idDescription  ID description.
     */
    public void setIdDescription(String idDescription) {
	this.idDescription = idDescription;
    }

    /**
     * Returns ID issuer.
     * 
     * @return ID issuer.
     */
    public String getIdIssuer() {
	return idIssuer;
    }

    /**
     * Sets  ID issuer.
     * 
     * @param idIssuer  ID issuer.
     */
    public void setIdIssuer(String idIssuer) {
	this.idIssuer = idIssuer;
    }

    /**
     * Returns ID number.
     * 
     * @return ID number.
     */
    public String getIdNumber() {
	return idNumber;
    }

    /**
     * Sets  ID number.
     * 
     * @param idNumber  ID number.
     */
    public void setIdNumber(String idNumber) {
	this.idNumber = idNumber;
    }

    /**
     * Returns ID date issued.
     * 
     * @return ID date issued.
     */
    public Date getIdDateIssued() {
	return idDateIssued;
    }

    /**
     * Sets  ID date issued.
     * 
     * @param isDateIssued  ID date issued.
     */
    public void setIdDateIssued(Date isDateIssued) {
	this.idDateIssued = isDateIssued;
    }

    /**
     * Returns ID expiration date.
     * 
     * @return ID expiration date.
     */
    public Date getIdExpiration() {
	return idExpiration;
    }

    /**
     * Sets  ID expiration date.
     * 
     * @param idExpiration  ID expiration date.
     */
    public void setIdExpiration(Date idExpiration) {
	this.idExpiration = idExpiration;
    }

    /**
     * Returns list of ID types.
     * 
     * @return list of ID types.
     */
    public List<String> getIdTypeOptions() {
	return idTypeOptions;
    }

    // TODO: Get the list from backend service
    public enum State {

	Alaska("AK", "Alaska"), Alabama("AL", "Alabama"), Arkansas("AR", "Arkansas"), Arizona("AZ", "Arizona"),
	California("CA", "California"), Colorado("CO", "Colorado"), Connecticut("CT", "Connecticut"), Washington_DC(
		"DC", "Washington DC"), Delaware("DE", "Delaware"), Florida("FL", "Florida"), Georgia("GA", "Georgia"),
	Hawaii("HI", "Hawaii"), Iowa("IA", "Iowa"), Idaho("ID", "Idaho"), Illinois("IL", "Illinois"), Indiana("IN",
		"Indiana"), Kansas("KS", "Kansas"), Kentucky("KY", "Kentucky"), Louisiana("LA", "Louisiana"),
	Massachusetts("MA", "Massachusetts"), Maryland("MD", "Maryland"), Maine("ME", "Maine"), Michigan("MI",
		"Michigan"), Minnesota("MN", "Minnesota"), Missouri("MO", "Missouri"),
	Mississippi("MS", "Mississippi"), Montana("MT", "Montana"), North_Carolina("NC", "North Carolina"),
	North_Dakota("ND", "North Dakota"), Nebraska("NE", "Nebraska"), New_Hampshire("NH", "New Hampshire"),
	New_Jersey("NJ", "New Jersey"), New_Mexico("NM", "New Mexico"), Nevada("NV", "Nevada"), New_York("NY",
		"New York"), Ohio("OH", "Ohio"), Oklahoma("OK", "Oklahoma"), Oregon("OR", "Oregon"), Pennsylvania("PA",
		"Pennsylvania"), Rhode_Island("RI", "Rhode Island"), South_Carolina("SC", "South Carolina"),
	South_Dakota("SD", "South Dakota"), Tennessee("TN", "Tennessee"), Texas("TX", "Texas"), Utah("UT", "Utah"),
	Virginia("VA", "Virginia"), Vermont("VT", "Vermont"), Washington("WA", "Washington"), Wisconsin("WI",
		"Wisconsin"), West_Virginia("WV", "West Virginia"), Wyoming("WY", "Wyoming");

	private String stateCode;
	private String stateName;

	private State(String stateCode, String stateName) {
	    this.stateCode = stateCode;
	    this.stateName = stateName;
	}

	public String getStateCode() {
	    return stateCode;
	}

	public String getStateName() {
	    return stateName;
	}

	public void setStateCode(String stateCode) {
	    this.stateCode = stateCode;
	}

	public void setStateName(String stateName) {
	    this.stateName = stateName;
	}

    }

}
