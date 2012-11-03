
package com.bestbuy.bbym.ise.iseclientucs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getNotificationStatusResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getNotificationStatusResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="notificationStatusGetResponse" type="{http://bestbuy.com/bbym/ucs}NotificationStatusGetResponse" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getNotificationStatusResponse", propOrder = {
    "notificationStatusGetResponse"
})
public class GetNotificationStatusResponse {

    protected NotificationStatusGetResponse notificationStatusGetResponse;

    /**
     * Gets the value of the notificationStatusGetResponse property.
     * 
     * @return
     *     possible object is
     *     {@link NotificationStatusGetResponse }
     *     
     */
    public NotificationStatusGetResponse getNotificationStatusGetResponse() {
        return notificationStatusGetResponse;
    }

    /**
     * Sets the value of the notificationStatusGetResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotificationStatusGetResponse }
     *     
     */
    public void setNotificationStatusGetResponse(NotificationStatusGetResponse value) {
        this.notificationStatusGetResponse = value;
    }

}
