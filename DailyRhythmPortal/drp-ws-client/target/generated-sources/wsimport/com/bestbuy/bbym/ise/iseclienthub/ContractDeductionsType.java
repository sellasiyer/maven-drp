
package com.bestbuy.bbym.ise.iseclienthub;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ContractDeductionsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContractDeductionsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DeductionOrderNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DeductionSourceSystem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DeductionDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="DeductionType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DeductionQty" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="DeductionStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContractDeductionsType", propOrder = {
    "deductionOrderNbr",
    "deductionSourceSystem",
    "deductionDate",
    "deductionType",
    "deductionQty",
    "deductionStatus"
})
public class ContractDeductionsType {

    @XmlElement(name = "DeductionOrderNbr")
    protected String deductionOrderNbr;
    @XmlElement(name = "DeductionSourceSystem")
    protected String deductionSourceSystem;
    @XmlElement(name = "DeductionDate", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar deductionDate;
    @XmlElement(name = "DeductionType")
    protected String deductionType;
    @XmlElement(name = "DeductionQty")
    protected BigInteger deductionQty;
    @XmlElement(name = "DeductionStatus")
    protected String deductionStatus;

    /**
     * Gets the value of the deductionOrderNbr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeductionOrderNbr() {
        return deductionOrderNbr;
    }

    /**
     * Sets the value of the deductionOrderNbr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeductionOrderNbr(String value) {
        this.deductionOrderNbr = value;
    }

    /**
     * Gets the value of the deductionSourceSystem property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeductionSourceSystem() {
        return deductionSourceSystem;
    }

    /**
     * Sets the value of the deductionSourceSystem property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeductionSourceSystem(String value) {
        this.deductionSourceSystem = value;
    }

    /**
     * Gets the value of the deductionDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDeductionDate() {
        return deductionDate;
    }

    /**
     * Sets the value of the deductionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDeductionDate(XMLGregorianCalendar value) {
        this.deductionDate = value;
    }

    /**
     * Gets the value of the deductionType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeductionType() {
        return deductionType;
    }

    /**
     * Sets the value of the deductionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeductionType(String value) {
        this.deductionType = value;
    }

    /**
     * Gets the value of the deductionQty property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDeductionQty() {
        return deductionQty;
    }

    /**
     * Sets the value of the deductionQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDeductionQty(BigInteger value) {
        this.deductionQty = value;
    }

    /**
     * Gets the value of the deductionStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeductionStatus() {
        return deductionStatus;
    }

    /**
     * Sets the value of the deductionStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeductionStatus(String value) {
        this.deductionStatus = value;
    }

}
