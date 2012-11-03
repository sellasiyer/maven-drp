
package com.bestbuy.bbym.ise.iseclientucs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Subscriber complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Subscriber">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mobilePhoneNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="contractEndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="upgradeEligibilityFlag" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="upgradeEligibilityDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="earlyUpgradeEligibilityDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="upgradeEligibilityType" type="{http://bestbuy.com/bbym/ucs}UpgradeEligibilityTypeCode"/>
 *         &lt;element name="upgradeEligibilityMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="upgradeEligibilityFootnote" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="earlyTerminationWarning" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="optInAllowed" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="tradeInMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tradeInValue" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="tradeInPhoneName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modeId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="imei" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="esn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Subscriber", propOrder = {
    "mobilePhoneNumber",
    "contractEndDate",
    "upgradeEligibilityFlag",
    "upgradeEligibilityDate",
    "earlyUpgradeEligibilityDate",
    "upgradeEligibilityType",
    "upgradeEligibilityMessage",
    "upgradeEligibilityFootnote",
    "earlyTerminationWarning",
    "optInAllowed",
    "tradeInMessage",
    "tradeInValue",
    "tradeInPhoneName",
    "modeId",
    "imei",
    "esn"
})
public class Subscriber {

    @XmlElement(required = true)
    protected String mobilePhoneNumber;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar contractEndDate;
    protected boolean upgradeEligibilityFlag;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar upgradeEligibilityDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar earlyUpgradeEligibilityDate;
    @XmlElement(required = true)
    protected UpgradeEligibilityTypeCode upgradeEligibilityType;
    protected String upgradeEligibilityMessage;
    protected String upgradeEligibilityFootnote;
    protected String earlyTerminationWarning;
    protected boolean optInAllowed;
    protected String tradeInMessage;
    protected Float tradeInValue;
    protected String tradeInPhoneName;
    protected String modeId;
    protected String imei;
    protected String esn;

    /**
     * Gets the value of the mobilePhoneNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    /**
     * Sets the value of the mobilePhoneNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMobilePhoneNumber(String value) {
        this.mobilePhoneNumber = value;
    }

    /**
     * Gets the value of the contractEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getContractEndDate() {
        return contractEndDate;
    }

    /**
     * Sets the value of the contractEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setContractEndDate(XMLGregorianCalendar value) {
        this.contractEndDate = value;
    }

    /**
     * Gets the value of the upgradeEligibilityFlag property.
     * 
     */
    public boolean isUpgradeEligibilityFlag() {
        return upgradeEligibilityFlag;
    }

    /**
     * Sets the value of the upgradeEligibilityFlag property.
     * 
     */
    public void setUpgradeEligibilityFlag(boolean value) {
        this.upgradeEligibilityFlag = value;
    }

    /**
     * Gets the value of the upgradeEligibilityDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getUpgradeEligibilityDate() {
        return upgradeEligibilityDate;
    }

    /**
     * Sets the value of the upgradeEligibilityDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setUpgradeEligibilityDate(XMLGregorianCalendar value) {
        this.upgradeEligibilityDate = value;
    }

    /**
     * Gets the value of the earlyUpgradeEligibilityDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEarlyUpgradeEligibilityDate() {
        return earlyUpgradeEligibilityDate;
    }

    /**
     * Sets the value of the earlyUpgradeEligibilityDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEarlyUpgradeEligibilityDate(XMLGregorianCalendar value) {
        this.earlyUpgradeEligibilityDate = value;
    }

    /**
     * Gets the value of the upgradeEligibilityType property.
     * 
     * @return
     *     possible object is
     *     {@link UpgradeEligibilityTypeCode }
     *     
     */
    public UpgradeEligibilityTypeCode getUpgradeEligibilityType() {
        return upgradeEligibilityType;
    }

    /**
     * Sets the value of the upgradeEligibilityType property.
     * 
     * @param value
     *     allowed object is
     *     {@link UpgradeEligibilityTypeCode }
     *     
     */
    public void setUpgradeEligibilityType(UpgradeEligibilityTypeCode value) {
        this.upgradeEligibilityType = value;
    }

    /**
     * Gets the value of the upgradeEligibilityMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpgradeEligibilityMessage() {
        return upgradeEligibilityMessage;
    }

    /**
     * Sets the value of the upgradeEligibilityMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpgradeEligibilityMessage(String value) {
        this.upgradeEligibilityMessage = value;
    }

    /**
     * Gets the value of the upgradeEligibilityFootnote property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpgradeEligibilityFootnote() {
        return upgradeEligibilityFootnote;
    }

    /**
     * Sets the value of the upgradeEligibilityFootnote property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpgradeEligibilityFootnote(String value) {
        this.upgradeEligibilityFootnote = value;
    }

    /**
     * Gets the value of the earlyTerminationWarning property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEarlyTerminationWarning() {
        return earlyTerminationWarning;
    }

    /**
     * Sets the value of the earlyTerminationWarning property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEarlyTerminationWarning(String value) {
        this.earlyTerminationWarning = value;
    }

    /**
     * Gets the value of the optInAllowed property.
     * 
     */
    public boolean isOptInAllowed() {
        return optInAllowed;
    }

    /**
     * Sets the value of the optInAllowed property.
     * 
     */
    public void setOptInAllowed(boolean value) {
        this.optInAllowed = value;
    }

    /**
     * Gets the value of the tradeInMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTradeInMessage() {
        return tradeInMessage;
    }

    /**
     * Sets the value of the tradeInMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTradeInMessage(String value) {
        this.tradeInMessage = value;
    }

    /**
     * Gets the value of the tradeInValue property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getTradeInValue() {
        return tradeInValue;
    }

    /**
     * Sets the value of the tradeInValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setTradeInValue(Float value) {
        this.tradeInValue = value;
    }

    /**
     * Gets the value of the tradeInPhoneName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTradeInPhoneName() {
        return tradeInPhoneName;
    }

    /**
     * Sets the value of the tradeInPhoneName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTradeInPhoneName(String value) {
        this.tradeInPhoneName = value;
    }

    /**
     * Gets the value of the modeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModeId() {
        return modeId;
    }

    /**
     * Sets the value of the modeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModeId(String value) {
        this.modeId = value;
    }

    /**
     * Gets the value of the imei property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImei() {
        return imei;
    }

    /**
     * Sets the value of the imei property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImei(String value) {
        this.imei = value;
    }

    /**
     * Gets the value of the esn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsn() {
        return esn;
    }

    /**
     * Sets the value of the esn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsn(String value) {
        this.esn = value;
    }

}
