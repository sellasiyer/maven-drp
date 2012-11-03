
package com.bestbuy.bbym.ise.iseclientucs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for putNotificationStatusPlusMultiResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="putNotificationStatusPlusMultiResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="notificationStatusMultiPutResponse" type="{http://bestbuy.com/bbym/ucs}NotificationStatusMultiPutResponse" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "putNotificationStatusPlusMultiResponse", propOrder = {
    "notificationStatusMultiPutResponse"
})
public class PutNotificationStatusPlusMultiResponse {

    protected NotificationStatusMultiPutResponse notificationStatusMultiPutResponse;

    /**
     * Gets the value of the notificationStatusMultiPutResponse property.
     * 
     * @return
     *     possible object is
     *     {@link NotificationStatusMultiPutResponse }
     *     
     */
    public NotificationStatusMultiPutResponse getNotificationStatusMultiPutResponse() {
        return notificationStatusMultiPutResponse;
    }

    /**
     * Sets the value of the notificationStatusMultiPutResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotificationStatusMultiPutResponse }
     *     
     */
    public void setNotificationStatusMultiPutResponse(NotificationStatusMultiPutResponse value) {
        this.notificationStatusMultiPutResponse = value;
    }

}
