
package com.bestbuy.bbym.ise.iseclienthub;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReplacementType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReplacementType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ReasonCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Notes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReplacementProduct" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Scheme/V4}ReplacementProductType" minOccurs="0"/>
 *         &lt;element name="ReplacementCredit" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Scheme/V4}ReplacementCreditType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReplacementType", propOrder = {
    "reasonCode",
    "notes",
    "replacementProduct",
    "replacementCredit"
})
public class ReplacementType {

    @XmlElement(name = "ReasonCode")
    protected String reasonCode;
    @XmlElement(name = "Notes")
    protected String notes;
    @XmlElement(name = "ReplacementProduct")
    protected ReplacementProductType replacementProduct;
    @XmlElement(name = "ReplacementCredit")
    protected ReplacementCreditType replacementCredit;

    /**
     * Gets the value of the reasonCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReasonCode() {
        return reasonCode;
    }

    /**
     * Sets the value of the reasonCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReasonCode(String value) {
        this.reasonCode = value;
    }

    /**
     * Gets the value of the notes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the value of the notes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotes(String value) {
        this.notes = value;
    }

    /**
     * Gets the value of the replacementProduct property.
     * 
     * @return
     *     possible object is
     *     {@link ReplacementProductType }
     *     
     */
    public ReplacementProductType getReplacementProduct() {
        return replacementProduct;
    }

    /**
     * Sets the value of the replacementProduct property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReplacementProductType }
     *     
     */
    public void setReplacementProduct(ReplacementProductType value) {
        this.replacementProduct = value;
    }

    /**
     * Gets the value of the replacementCredit property.
     * 
     * @return
     *     possible object is
     *     {@link ReplacementCreditType }
     *     
     */
    public ReplacementCreditType getReplacementCredit() {
        return replacementCredit;
    }

    /**
     * Sets the value of the replacementCredit property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReplacementCreditType }
     *     
     */
    public void setReplacementCredit(ReplacementCreditType value) {
        this.replacementCredit = value;
    }

}
