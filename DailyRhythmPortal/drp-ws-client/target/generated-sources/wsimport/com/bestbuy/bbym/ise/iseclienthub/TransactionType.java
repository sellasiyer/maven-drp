
package com.bestbuy.bbym.ise.iseclienthub;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TransactionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransactionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SourceSystem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SourceOrderNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TransactionKey" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Scheme/V4}TransactionKeyType" minOccurs="0"/>
 *         &lt;element name="PONumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EmployeeID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TermsVersion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ThirdPartyAdmin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Underwriter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SKUCost" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="SKUAverageCost" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="SKURegularPrice" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="SKUTermMonths" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ActualPricePaid" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="TaxPaid" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="objID" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="lineCode" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Scheme/V4}TransactionLineCodeEnum" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransactionType", propOrder = {
    "sourceSystem",
    "sourceOrderNumber",
    "transactionKey",
    "poNumber",
    "employeeID",
    "termsVersion",
    "thirdPartyAdmin",
    "underwriter",
    "skuCost",
    "skuAverageCost",
    "skuRegularPrice",
    "skuTermMonths",
    "actualPricePaid",
    "taxPaid"
})
public class TransactionType {

    @XmlElement(name = "SourceSystem")
    protected String sourceSystem;
    @XmlElement(name = "SourceOrderNumber")
    protected String sourceOrderNumber;
    @XmlElement(name = "TransactionKey")
    protected TransactionKeyType transactionKey;
    @XmlElement(name = "PONumber")
    protected String poNumber;
    @XmlElement(name = "EmployeeID")
    protected String employeeID;
    @XmlElement(name = "TermsVersion")
    protected String termsVersion;
    @XmlElement(name = "ThirdPartyAdmin")
    protected String thirdPartyAdmin;
    @XmlElement(name = "Underwriter")
    protected String underwriter;
    @XmlElement(name = "SKUCost")
    protected BigDecimal skuCost;
    @XmlElement(name = "SKUAverageCost")
    protected BigDecimal skuAverageCost;
    @XmlElement(name = "SKURegularPrice")
    protected BigDecimal skuRegularPrice;
    @XmlElement(name = "SKUTermMonths")
    protected Integer skuTermMonths;
    @XmlElement(name = "ActualPricePaid")
    protected BigDecimal actualPricePaid;
    @XmlElement(name = "TaxPaid")
    protected BigDecimal taxPaid;
    @XmlAttribute
    protected Long objID;
    @XmlAttribute
    protected TransactionLineCodeEnum lineCode;

    /**
     * Gets the value of the sourceSystem property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceSystem() {
        return sourceSystem;
    }

    /**
     * Sets the value of the sourceSystem property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceSystem(String value) {
        this.sourceSystem = value;
    }

    /**
     * Gets the value of the sourceOrderNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceOrderNumber() {
        return sourceOrderNumber;
    }

    /**
     * Sets the value of the sourceOrderNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceOrderNumber(String value) {
        this.sourceOrderNumber = value;
    }

    /**
     * Gets the value of the transactionKey property.
     * 
     * @return
     *     possible object is
     *     {@link TransactionKeyType }
     *     
     */
    public TransactionKeyType getTransactionKey() {
        return transactionKey;
    }

    /**
     * Sets the value of the transactionKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransactionKeyType }
     *     
     */
    public void setTransactionKey(TransactionKeyType value) {
        this.transactionKey = value;
    }

    /**
     * Gets the value of the poNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPONumber() {
        return poNumber;
    }

    /**
     * Sets the value of the poNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPONumber(String value) {
        this.poNumber = value;
    }

    /**
     * Gets the value of the employeeID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmployeeID() {
        return employeeID;
    }

    /**
     * Sets the value of the employeeID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmployeeID(String value) {
        this.employeeID = value;
    }

    /**
     * Gets the value of the termsVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTermsVersion() {
        return termsVersion;
    }

    /**
     * Sets the value of the termsVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTermsVersion(String value) {
        this.termsVersion = value;
    }

    /**
     * Gets the value of the thirdPartyAdmin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThirdPartyAdmin() {
        return thirdPartyAdmin;
    }

    /**
     * Sets the value of the thirdPartyAdmin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThirdPartyAdmin(String value) {
        this.thirdPartyAdmin = value;
    }

    /**
     * Gets the value of the underwriter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnderwriter() {
        return underwriter;
    }

    /**
     * Sets the value of the underwriter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnderwriter(String value) {
        this.underwriter = value;
    }

    /**
     * Gets the value of the skuCost property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSKUCost() {
        return skuCost;
    }

    /**
     * Sets the value of the skuCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSKUCost(BigDecimal value) {
        this.skuCost = value;
    }

    /**
     * Gets the value of the skuAverageCost property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSKUAverageCost() {
        return skuAverageCost;
    }

    /**
     * Sets the value of the skuAverageCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSKUAverageCost(BigDecimal value) {
        this.skuAverageCost = value;
    }

    /**
     * Gets the value of the skuRegularPrice property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSKURegularPrice() {
        return skuRegularPrice;
    }

    /**
     * Sets the value of the skuRegularPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSKURegularPrice(BigDecimal value) {
        this.skuRegularPrice = value;
    }

    /**
     * Gets the value of the skuTermMonths property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSKUTermMonths() {
        return skuTermMonths;
    }

    /**
     * Sets the value of the skuTermMonths property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSKUTermMonths(Integer value) {
        this.skuTermMonths = value;
    }

    /**
     * Gets the value of the actualPricePaid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getActualPricePaid() {
        return actualPricePaid;
    }

    /**
     * Sets the value of the actualPricePaid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setActualPricePaid(BigDecimal value) {
        this.actualPricePaid = value;
    }

    /**
     * Gets the value of the taxPaid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTaxPaid() {
        return taxPaid;
    }

    /**
     * Sets the value of the taxPaid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTaxPaid(BigDecimal value) {
        this.taxPaid = value;
    }

    /**
     * Gets the value of the objID property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getObjID() {
        return objID;
    }

    /**
     * Sets the value of the objID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setObjID(Long value) {
        this.objID = value;
    }

    /**
     * Gets the value of the lineCode property.
     * 
     * @return
     *     possible object is
     *     {@link TransactionLineCodeEnum }
     *     
     */
    public TransactionLineCodeEnum getLineCode() {
        return lineCode;
    }

    /**
     * Sets the value of the lineCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransactionLineCodeEnum }
     *     
     */
    public void setLineCode(TransactionLineCodeEnum value) {
        this.lineCode = value;
    }

}
