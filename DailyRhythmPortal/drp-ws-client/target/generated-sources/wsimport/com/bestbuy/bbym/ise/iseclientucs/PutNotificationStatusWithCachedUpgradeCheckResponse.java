
package com.bestbuy.bbym.ise.iseclientucs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for putNotificationStatusWithCachedUpgradeCheckResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="putNotificationStatusWithCachedUpgradeCheckResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="notificationStatusWithCachedUpgradeCheckPutResponse" type="{http://bestbuy.com/bbym/ucs}NotificationStatusWithCachedUpgradeCheckPutResponse" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "putNotificationStatusWithCachedUpgradeCheckResponse", propOrder = {
    "notificationStatusWithCachedUpgradeCheckPutResponse"
})
public class PutNotificationStatusWithCachedUpgradeCheckResponse {

    protected NotificationStatusWithCachedUpgradeCheckPutResponse notificationStatusWithCachedUpgradeCheckPutResponse;

    /**
     * Gets the value of the notificationStatusWithCachedUpgradeCheckPutResponse property.
     * 
     * @return
     *     possible object is
     *     {@link NotificationStatusWithCachedUpgradeCheckPutResponse }
     *     
     */
    public NotificationStatusWithCachedUpgradeCheckPutResponse getNotificationStatusWithCachedUpgradeCheckPutResponse() {
        return notificationStatusWithCachedUpgradeCheckPutResponse;
    }

    /**
     * Sets the value of the notificationStatusWithCachedUpgradeCheckPutResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotificationStatusWithCachedUpgradeCheckPutResponse }
     *     
     */
    public void setNotificationStatusWithCachedUpgradeCheckPutResponse(NotificationStatusWithCachedUpgradeCheckPutResponse value) {
        this.notificationStatusWithCachedUpgradeCheckPutResponse = value;
    }

}
