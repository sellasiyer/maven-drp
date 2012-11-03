
package com.bestbuy.bbym.logistics.device.status.component.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Request schema for Get RMA
 * 
 * <p>Java class for GetRMAMessageType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetRMAMessageType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RMA_Request" type="{http://bestbuy.com/bbym/logistics/device/status/component/v1}RMASearchListType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetRMAMessageType", propOrder = {
    "rmaRequest"
})
public class GetRMAMessageType {

    @XmlElement(name = "RMA_Request", required = true)
    protected RMASearchListType rmaRequest;

    /**
     * Gets the value of the rmaRequest property.
     * 
     * @return
     *     possible object is
     *     {@link RMASearchListType }
     *     
     */
    public RMASearchListType getRMARequest() {
        return rmaRequest;
    }

    /**
     * Sets the value of the rmaRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link RMASearchListType }
     *     
     */
    public void setRMARequest(RMASearchListType value) {
        this.rmaRequest = value;
    }

}
