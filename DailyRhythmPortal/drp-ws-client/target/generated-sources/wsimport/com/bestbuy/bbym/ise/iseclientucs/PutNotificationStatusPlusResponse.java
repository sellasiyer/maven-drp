
package com.bestbuy.bbym.ise.iseclientucs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for putNotificationStatusPlusResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="putNotificationStatusPlusResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="notificationStatusPutResponse" type="{http://bestbuy.com/bbym/ucs}NotificationStatusPutResponse" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "putNotificationStatusPlusResponse", propOrder = {
    "notificationStatusPutResponse"
})
public class PutNotificationStatusPlusResponse {

    protected NotificationStatusPutResponse notificationStatusPutResponse;

    /**
     * Gets the value of the notificationStatusPutResponse property.
     * 
     * @return
     *     possible object is
     *     {@link NotificationStatusPutResponse }
     *     
     */
    public NotificationStatusPutResponse getNotificationStatusPutResponse() {
        return notificationStatusPutResponse;
    }

    /**
     * Sets the value of the notificationStatusPutResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotificationStatusPutResponse }
     *     
     */
    public void setNotificationStatusPutResponse(NotificationStatusPutResponse value) {
        this.notificationStatusPutResponse = value;
    }

}
