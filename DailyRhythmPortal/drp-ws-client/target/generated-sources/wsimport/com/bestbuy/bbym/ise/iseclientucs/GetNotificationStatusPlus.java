
package com.bestbuy.bbym.ise.iseclientucs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getNotificationStatusPlus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getNotificationStatusPlus">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="notificationStatusGetRequest" type="{http://bestbuy.com/bbym/ucs}NotificationStatusGetRequest" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getNotificationStatusPlus", propOrder = {
    "notificationStatusGetRequest"
})
public class GetNotificationStatusPlus {

    protected NotificationStatusGetRequest notificationStatusGetRequest;

    /**
     * Gets the value of the notificationStatusGetRequest property.
     * 
     * @return
     *     possible object is
     *     {@link NotificationStatusGetRequest }
     *     
     */
    public NotificationStatusGetRequest getNotificationStatusGetRequest() {
        return notificationStatusGetRequest;
    }

    /**
     * Sets the value of the notificationStatusGetRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotificationStatusGetRequest }
     *     
     */
    public void setNotificationStatusGetRequest(NotificationStatusGetRequest value) {
        this.notificationStatusGetRequest = value;
    }

}
