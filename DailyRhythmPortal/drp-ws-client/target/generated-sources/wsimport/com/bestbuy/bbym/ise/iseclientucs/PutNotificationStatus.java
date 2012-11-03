
package com.bestbuy.bbym.ise.iseclientucs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for putNotificationStatus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="putNotificationStatus">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="notificationStatusPutRequest" type="{http://bestbuy.com/bbym/ucs}NotificationStatusPutRequest" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "putNotificationStatus", propOrder = {
    "notificationStatusPutRequest"
})
public class PutNotificationStatus {

    protected NotificationStatusPutRequest notificationStatusPutRequest;

    /**
     * Gets the value of the notificationStatusPutRequest property.
     * 
     * @return
     *     possible object is
     *     {@link NotificationStatusPutRequest }
     *     
     */
    public NotificationStatusPutRequest getNotificationStatusPutRequest() {
        return notificationStatusPutRequest;
    }

    /**
     * Sets the value of the notificationStatusPutRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotificationStatusPutRequest }
     *     
     */
    public void setNotificationStatusPutRequest(NotificationStatusPutRequest value) {
        this.notificationStatusPutRequest = value;
    }

}
