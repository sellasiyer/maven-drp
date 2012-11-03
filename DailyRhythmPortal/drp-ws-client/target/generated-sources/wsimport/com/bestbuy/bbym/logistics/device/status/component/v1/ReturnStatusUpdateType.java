
package com.bestbuy.bbym.logistics.device.status.component.v1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.bestbuy.tsh.common.datatype.v1.IdentifierType;
import com.bestbuy.tsh.common.datatype.v1.UserAreaType;


/**
 * Contains status info for a device
 * 
 * <p>Java class for ReturnStatusUpdateType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReturnStatusUpdateType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://bestbuy.com/bbym/logistics/device/status/component/v1}ReturnID"/>
 *         &lt;element ref="{http://bestbuy.com/bbym/logistics/device/status/component/v1}StatusUpdate" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/v1}UserArea" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReturnStatusUpdateType", propOrder = {
    "returnID",
    "statusUpdate",
    "userArea"
})
public class ReturnStatusUpdateType {

    @XmlElement(name = "ReturnID", required = true)
    protected IdentifierType returnID;
    @XmlElement(name = "StatusUpdate")
    protected List<StatusUpdateType> statusUpdate;
    @XmlElement(name = "UserArea", namespace = "http://www.tsh.bestbuy.com/common/v1")
    protected UserAreaType userArea;

    /**
     * BBY unique identifier for return transaction at device level; 
     * 				Traveler ID/Item Tag
     * 			
     * 
     * @return
     *     possible object is
     *     {@link IdentifierType }
     *     
     */
    public IdentifierType getReturnID() {
        return returnID;
    }

    /**
     * Sets the value of the returnID property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentifierType }
     *     
     */
    public void setReturnID(IdentifierType value) {
        this.returnID = value;
    }

    /**
     * Gets the value of the statusUpdate property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the statusUpdate property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStatusUpdate().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StatusUpdateType }
     * 
     * 
     */
    public List<StatusUpdateType> getStatusUpdate() {
        if (statusUpdate == null) {
            statusUpdate = new ArrayList<StatusUpdateType>();
        }
        return this.statusUpdate;
    }

    /**
     * Allows the Tag Super Highway schema data model to extend the specification in order to provide additional information that is not captured in Canonical Schema.
     * 
     * @return
     *     possible object is
     *     {@link UserAreaType }
     *     
     */
    public UserAreaType getUserArea() {
        return userArea;
    }

    /**
     * Sets the value of the userArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserAreaType }
     *     
     */
    public void setUserArea(UserAreaType value) {
        this.userArea = value;
    }

}
