
package com.bestbuy.bbym.ise.iseclienthub;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ContractProductDetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContractProductDetailType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CloseReason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InspectionDetails" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ServiceEligible" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Replacement" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Scheme/V4}ReplacementType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContractProductDetailType", propOrder = {
    "closeReason",
    "inspectionDetails",
    "serviceEligible",
    "replacement"
})
public class ContractProductDetailType {

    @XmlElement(name = "CloseReason")
    protected String closeReason;
    @XmlElement(name = "InspectionDetails")
    protected String inspectionDetails;
    @XmlElement(name = "ServiceEligible")
    protected Boolean serviceEligible;
    @XmlElement(name = "Replacement")
    protected ReplacementType replacement;

    /**
     * Gets the value of the closeReason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCloseReason() {
        return closeReason;
    }

    /**
     * Sets the value of the closeReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCloseReason(String value) {
        this.closeReason = value;
    }

    /**
     * Gets the value of the inspectionDetails property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInspectionDetails() {
        return inspectionDetails;
    }

    /**
     * Sets the value of the inspectionDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInspectionDetails(String value) {
        this.inspectionDetails = value;
    }

    /**
     * Gets the value of the serviceEligible property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isServiceEligible() {
        return serviceEligible;
    }

    /**
     * Sets the value of the serviceEligible property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setServiceEligible(Boolean value) {
        this.serviceEligible = value;
    }

    /**
     * Gets the value of the replacement property.
     * 
     * @return
     *     possible object is
     *     {@link ReplacementType }
     *     
     */
    public ReplacementType getReplacement() {
        return replacement;
    }

    /**
     * Sets the value of the replacement property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReplacementType }
     *     
     */
    public void setReplacement(ReplacementType value) {
        this.replacement = value;
    }

}
