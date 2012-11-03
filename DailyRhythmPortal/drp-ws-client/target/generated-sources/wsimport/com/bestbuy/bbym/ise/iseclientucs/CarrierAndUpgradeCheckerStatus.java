
package com.bestbuy.bbym.ise.iseclientucs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for CarrierAndUpgradeCheckerStatus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CarrierAndUpgradeCheckerStatus">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mobilePhoneNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="carrier" type="{http://bestbuy.com/bbym/ucs}Carrier" minOccurs="0"/>
 *         &lt;element name="notificationStatusCode" type="{http://bestbuy.com/bbym/ucs}NotificationStatus" minOccurs="0"/>
 *         &lt;element name="contractEndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="upgradeEligibilityDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="upgradeEligibilityType" type="{http://bestbuy.com/bbym/ucs}UpgradeEligibilityTypeCode" minOccurs="0"/>
 *         &lt;element name="upgradeCheckDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CarrierAndUpgradeCheckerStatus", propOrder = {
    "mobilePhoneNumber",
    "carrier",
    "notificationStatusCode",
    "contractEndDate",
    "upgradeEligibilityDate",
    "upgradeEligibilityType",
    "upgradeCheckDate"
})
public class CarrierAndUpgradeCheckerStatus {

    @XmlElement(required = true)
    protected String mobilePhoneNumber;
    protected Carrier carrier;
    protected NotificationStatus notificationStatusCode;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar contractEndDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar upgradeEligibilityDate;
    protected UpgradeEligibilityTypeCode upgradeEligibilityType;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar upgradeCheckDate;

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
     * Gets the value of the carrier property.
     * 
     * @return
     *     possible object is
     *     {@link Carrier }
     *     
     */
    public Carrier getCarrier() {
        return carrier;
    }

    /**
     * Sets the value of the carrier property.
     * 
     * @param value
     *     allowed object is
     *     {@link Carrier }
     *     
     */
    public void setCarrier(Carrier value) {
        this.carrier = value;
    }

    /**
     * Gets the value of the notificationStatusCode property.
     * 
     * @return
     *     possible object is
     *     {@link NotificationStatus }
     *     
     */
    public NotificationStatus getNotificationStatusCode() {
        return notificationStatusCode;
    }

    /**
     * Sets the value of the notificationStatusCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotificationStatus }
     *     
     */
    public void setNotificationStatusCode(NotificationStatus value) {
        this.notificationStatusCode = value;
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
     * Gets the value of the upgradeCheckDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getUpgradeCheckDate() {
        return upgradeCheckDate;
    }

    /**
     * Sets the value of the upgradeCheckDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setUpgradeCheckDate(XMLGregorianCalendar value) {
        this.upgradeCheckDate = value;
    }

}
