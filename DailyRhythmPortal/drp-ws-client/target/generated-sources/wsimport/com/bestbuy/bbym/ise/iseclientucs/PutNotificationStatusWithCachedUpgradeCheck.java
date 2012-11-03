
package com.bestbuy.bbym.ise.iseclientucs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for putNotificationStatusWithCachedUpgradeCheck complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="putNotificationStatusWithCachedUpgradeCheck">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="notificationStatusWithCachedUpgradeCheckPutRequest" type="{http://bestbuy.com/bbym/ucs}NotificationStatusWithCachedUpgradeCheckPutRequest" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "putNotificationStatusWithCachedUpgradeCheck", propOrder = {
    "notificationStatusWithCachedUpgradeCheckPutRequest"
})
public class PutNotificationStatusWithCachedUpgradeCheck {

    protected NotificationStatusWithCachedUpgradeCheckPutRequest notificationStatusWithCachedUpgradeCheckPutRequest;

    /**
     * Gets the value of the notificationStatusWithCachedUpgradeCheckPutRequest property.
     * 
     * @return
     *     possible object is
     *     {@link NotificationStatusWithCachedUpgradeCheckPutRequest }
     *     
     */
    public NotificationStatusWithCachedUpgradeCheckPutRequest getNotificationStatusWithCachedUpgradeCheckPutRequest() {
        return notificationStatusWithCachedUpgradeCheckPutRequest;
    }

    /**
     * Sets the value of the notificationStatusWithCachedUpgradeCheckPutRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotificationStatusWithCachedUpgradeCheckPutRequest }
     *     
     */
    public void setNotificationStatusWithCachedUpgradeCheckPutRequest(NotificationStatusWithCachedUpgradeCheckPutRequest value) {
        this.notificationStatusWithCachedUpgradeCheckPutRequest = value;
    }

}
