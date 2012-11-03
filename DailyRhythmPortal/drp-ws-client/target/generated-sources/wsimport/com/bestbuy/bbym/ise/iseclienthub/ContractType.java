
package com.bestbuy.bbym.ise.iseclienthub;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ContractType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContractType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SKU" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Status" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Scheme/V4}ContractStatusEnum"/>
 *         &lt;element name="EffectiveDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="ExpirationDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="SKUDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OCISContractID" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="SalesChannelNum" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="GroupContractID" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="ContractOfferType" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Scheme/V4}ContractOfferTypeType" minOccurs="0"/>
 *         &lt;element name="ContractClassCode" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Scheme/V4}ContractClassCodeType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContractType", propOrder = {
    "sku",
    "status",
    "effectiveDate",
    "expirationDate",
    "skuDescription",
    "ocisContractID",
    "salesChannelNum",
    "groupContractID",
    "contractOfferType",
    "contractClassCode"
})
public class ContractType {

    @XmlElement(name = "SKU")
    protected String sku;
    @XmlElement(name = "Status", required = true)
    protected ContractStatusEnum status;
    @XmlElement(name = "EffectiveDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar effectiveDate;
    @XmlElement(name = "ExpirationDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar expirationDate;
    @XmlElement(name = "SKUDescription")
    protected String skuDescription;
    @XmlElement(name = "OCISContractID")
    protected BigInteger ocisContractID;
    @XmlElement(name = "SalesChannelNum")
    protected BigInteger salesChannelNum;
    @XmlElement(name = "GroupContractID")
    protected BigInteger groupContractID;
    @XmlElement(name = "ContractOfferType")
    protected ContractOfferTypeType contractOfferType;
    @XmlElement(name = "ContractClassCode")
    protected ContractClassCodeType contractClassCode;

    /**
     * Gets the value of the sku property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSKU() {
        return sku;
    }

    /**
     * Sets the value of the sku property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSKU(String value) {
        this.sku = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link ContractStatusEnum }
     *     
     */
    public ContractStatusEnum getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContractStatusEnum }
     *     
     */
    public void setStatus(ContractStatusEnum value) {
        this.status = value;
    }

    /**
     * Gets the value of the effectiveDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * Sets the value of the effectiveDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEffectiveDate(XMLGregorianCalendar value) {
        this.effectiveDate = value;
    }

    /**
     * Gets the value of the expirationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets the value of the expirationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExpirationDate(XMLGregorianCalendar value) {
        this.expirationDate = value;
    }

    /**
     * Gets the value of the skuDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSKUDescription() {
        return skuDescription;
    }

    /**
     * Sets the value of the skuDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSKUDescription(String value) {
        this.skuDescription = value;
    }

    /**
     * Gets the value of the ocisContractID property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOCISContractID() {
        return ocisContractID;
    }

    /**
     * Sets the value of the ocisContractID property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOCISContractID(BigInteger value) {
        this.ocisContractID = value;
    }

    /**
     * Gets the value of the salesChannelNum property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSalesChannelNum() {
        return salesChannelNum;
    }

    /**
     * Sets the value of the salesChannelNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSalesChannelNum(BigInteger value) {
        this.salesChannelNum = value;
    }

    /**
     * Gets the value of the groupContractID property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getGroupContractID() {
        return groupContractID;
    }

    /**
     * Sets the value of the groupContractID property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setGroupContractID(BigInteger value) {
        this.groupContractID = value;
    }

    /**
     * Gets the value of the contractOfferType property.
     * 
     * @return
     *     possible object is
     *     {@link ContractOfferTypeType }
     *     
     */
    public ContractOfferTypeType getContractOfferType() {
        return contractOfferType;
    }

    /**
     * Sets the value of the contractOfferType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContractOfferTypeType }
     *     
     */
    public void setContractOfferType(ContractOfferTypeType value) {
        this.contractOfferType = value;
    }

    /**
     * Gets the value of the contractClassCode property.
     * 
     * @return
     *     possible object is
     *     {@link ContractClassCodeType }
     *     
     */
    public ContractClassCodeType getContractClassCode() {
        return contractClassCode;
    }

    /**
     * Sets the value of the contractClassCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContractClassCodeType }
     *     
     */
    public void setContractClassCode(ContractClassCodeType value) {
        this.contractClassCode = value;
    }

}
