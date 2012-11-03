
package com.bestbuy.bbym.ise.iseclientucs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for putNotificationStatusPlusMulti complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="putNotificationStatusPlusMulti">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="notificationStatusMultiPutRequest" type="{http://bestbuy.com/bbym/ucs}NotificationStatusMultiPutRequest" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "putNotificationStatusPlusMulti", propOrder = {
    "notificationStatusMultiPutRequest"
})
public class PutNotificationStatusPlusMulti {

    protected NotificationStatusMultiPutRequest notificationStatusMultiPutRequest;

    /**
     * Gets the value of the notificationStatusMultiPutRequest property.
     * 
     * @return
     *     possible object is
     *     {@link NotificationStatusMultiPutRequest }
     *     
     */
    public NotificationStatusMultiPutRequest getNotificationStatusMultiPutRequest() {
        return notificationStatusMultiPutRequest;
    }

    /**
     * Sets the value of the notificationStatusMultiPutRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotificationStatusMultiPutRequest }
     *     
     */
    public void setNotificationStatusMultiPutRequest(NotificationStatusMultiPutRequest value) {
        this.notificationStatusMultiPutRequest = value;
    }

}
