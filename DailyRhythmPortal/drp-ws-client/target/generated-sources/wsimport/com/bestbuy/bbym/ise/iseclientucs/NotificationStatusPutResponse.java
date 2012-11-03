
package com.bestbuy.bbym.ise.iseclientucs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NotificationStatusPutResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NotificationStatusPutResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mobilePhoneNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="notificationStatusCode" type="{http://bestbuy.com/bbym/ucs}NotificationStatus"/>
 *         &lt;element name="upgradeCheckCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="optInCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="notificationStatusChange" type="{http://bestbuy.com/bbym/ucs}notificationStatusChange" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NotificationStatusPutResponse", propOrder = {
    "mobilePhoneNumber",
    "notificationStatusCode",
    "upgradeCheckCount",
    "optInCount",
    "notificationStatusChange"
})
public class NotificationStatusPutResponse {

    @XmlElement(required = true)
    protected String mobilePhoneNumber;
    @XmlElement(required = true)
    protected NotificationStatus notificationStatusCode;
    protected int upgradeCheckCount;
    protected int optInCount;
    protected NotificationStatusChange notificationStatusChange;

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
     * Gets the value of the upgradeCheckCount property.
     * 
     */
    public int getUpgradeCheckCount() {
        return upgradeCheckCount;
    }

    /**
     * Sets the value of the upgradeCheckCount property.
     * 
     */
    public void setUpgradeCheckCount(int value) {
        this.upgradeCheckCount = value;
    }

    /**
     * Gets the value of the optInCount property.
     * 
     */
    public int getOptInCount() {
        return optInCount;
    }

    /**
     * Sets the value of the optInCount property.
     * 
     */
    public void setOptInCount(int value) {
        this.optInCount = value;
    }

    /**
     * Gets the value of the notificationStatusChange property.
     * 
     * @return
     *     possible object is
     *     {@link NotificationStatusChange }
     *     
     */
    public NotificationStatusChange getNotificationStatusChange() {
        return notificationStatusChange;
    }

    /**
     * Sets the value of the notificationStatusChange property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotificationStatusChange }
     *     
     */
    public void setNotificationStatusChange(NotificationStatusChange value) {
        this.notificationStatusChange = value;
    }

}
