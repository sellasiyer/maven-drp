
package com.bestbuy.bbym.ise.iseclientucs;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NotificationStatusWithCachedUpgradeCheckPutRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NotificationStatusWithCachedUpgradeCheckPutRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="subscriberStatuses" type="{http://bestbuy.com/bbym/ucs}SubscriberNotificationStatus" maxOccurs="unbounded"/>
 *         &lt;element name="mobilePhoneNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="zip" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="internationalBusinessHierarchy" type="{http://bestbuy.com/bbym/ucs}InternationalBusinessHierarchy"/>
 *         &lt;element name="sourceSystem" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="capTransactionId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="locationId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="language" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NotificationStatusWithCachedUpgradeCheckPutRequest", propOrder = {
    "subscriberStatuses",
    "mobilePhoneNumber",
    "zip",
    "internationalBusinessHierarchy",
    "sourceSystem",
    "capTransactionId",
    "locationId",
    "userId",
    "language"
})
public class NotificationStatusWithCachedUpgradeCheckPutRequest {

    @XmlElement(required = true)
    protected List<SubscriberNotificationStatus> subscriberStatuses;
    @XmlElement(required = true)
    protected String mobilePhoneNumber;
    @XmlElement(required = true)
    protected String zip;
    @XmlElement(required = true)
    protected InternationalBusinessHierarchy internationalBusinessHierarchy;
    @XmlElement(required = true)
    protected String sourceSystem;
    @XmlElement(required = true)
    protected String capTransactionId;
    protected int locationId;
    protected String userId;
    protected String language;

    /**
     * Gets the value of the subscriberStatuses property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subscriberStatuses property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubscriberStatuses().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SubscriberNotificationStatus }
     * 
     * 
     */
    public List<SubscriberNotificationStatus> getSubscriberStatuses() {
        if (subscriberStatuses == null) {
            subscriberStatuses = new ArrayList<SubscriberNotificationStatus>();
        }
        return this.subscriberStatuses;
    }

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
     * Gets the value of the zip property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZip() {
        return zip;
    }

    /**
     * Sets the value of the zip property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZip(String value) {
        this.zip = value;
    }

    /**
     * Gets the value of the internationalBusinessHierarchy property.
     * 
     * @return
     *     possible object is
     *     {@link InternationalBusinessHierarchy }
     *     
     */
    public InternationalBusinessHierarchy getInternationalBusinessHierarchy() {
        return internationalBusinessHierarchy;
    }

    /**
     * Sets the value of the internationalBusinessHierarchy property.
     * 
     * @param value
     *     allowed object is
     *     {@link InternationalBusinessHierarchy }
     *     
     */
    public void setInternationalBusinessHierarchy(InternationalBusinessHierarchy value) {
        this.internationalBusinessHierarchy = value;
    }

    /**
     * Gets the value of the sourceSystem property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceSystem() {
        return sourceSystem;
    }

    /**
     * Sets the value of the sourceSystem property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceSystem(String value) {
        this.sourceSystem = value;
    }

    /**
     * Gets the value of the capTransactionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCapTransactionId() {
        return capTransactionId;
    }

    /**
     * Sets the value of the capTransactionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCapTransactionId(String value) {
        this.capTransactionId = value;
    }

    /**
     * Gets the value of the locationId property.
     * 
     */
    public int getLocationId() {
        return locationId;
    }

    /**
     * Sets the value of the locationId property.
     * 
     */
    public void setLocationId(int value) {
        this.locationId = value;
    }

    /**
     * Gets the value of the userId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserId(String value) {
        this.userId = value;
    }

    /**
     * Gets the value of the language property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the value of the language property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguage(String value) {
        this.language = value;
    }

}
