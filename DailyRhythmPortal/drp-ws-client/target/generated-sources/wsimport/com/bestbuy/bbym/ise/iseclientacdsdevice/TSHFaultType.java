
package com.bestbuy.bbym.ise.iseclientacdsdevice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Provides information about TSH Fault generated for
 * 				TSH service
 * 
 * <p>Java class for TSHFaultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TSHFaultType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SourceID" type="{http://www.tsh.bestbuy.com/common/datatype/v1}TextType" minOccurs="0"/>
 *         &lt;element name="SourceName" type="{http://www.tsh.bestbuy.com/common/datatype/v1}TextType" minOccurs="0"/>
 *         &lt;element name="Fault" type="{http://tsh.bestbuy.com/tsh/tshfault}faultType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Status" type="{http://www.tsh.bestbuy.com/common/datatype/v1}TextType" minOccurs="0"/>
 *         &lt;element name="ServiceDetails" type="{http://tsh.bestbuy.com/tsh/tshfault}serviceDetailsType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/v1}UserArea"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TSHFaultType", namespace = "http://tsh.bestbuy.com/tsh/tshfault", propOrder = {
    "sourceID",
    "sourceName",
    "fault",
    "status",
    "serviceDetails",
    "userArea"
})
public class TSHFaultType {

    @XmlElement(name = "SourceID")
    protected TextType sourceID;
    @XmlElement(name = "SourceName")
    protected TextType sourceName;
    @XmlElement(name = "Fault")
    protected List<FaultType> fault;
    @XmlElement(name = "Status")
    protected TextType status;
    @XmlElement(name = "ServiceDetails")
    protected List<ServiceDetailsType> serviceDetails;
    @XmlElement(name = "UserArea", namespace = "http://www.tsh.bestbuy.com/common/v1", required = true)
    protected UserAreaType userArea;

    /**
     * Gets the value of the sourceID property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getSourceID() {
        return sourceID;
    }

    /**
     * Sets the value of the sourceID property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setSourceID(TextType value) {
        this.sourceID = value;
    }

    /**
     * Gets the value of the sourceName property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getSourceName() {
        return sourceName;
    }

    /**
     * Sets the value of the sourceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setSourceName(TextType value) {
        this.sourceName = value;
    }

    /**
     * Gets the value of the fault property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fault property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFault().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FaultType }
     * 
     * 
     */
    public List<FaultType> getFault() {
        if (fault == null) {
            fault = new ArrayList<FaultType>();
        }
        return this.fault;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setStatus(TextType value) {
        this.status = value;
    }

    /**
     * Gets the value of the serviceDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the serviceDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getServiceDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ServiceDetailsType }
     * 
     * 
     */
    public List<ServiceDetailsType> getServiceDetails() {
        if (serviceDetails == null) {
            serviceDetails = new ArrayList<ServiceDetailsType>();
        }
        return this.serviceDetails;
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
