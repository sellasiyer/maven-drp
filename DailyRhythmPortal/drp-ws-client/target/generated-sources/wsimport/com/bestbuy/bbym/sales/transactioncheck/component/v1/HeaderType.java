
package com.bestbuy.bbym.sales.transactioncheck.component.v1;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.bestbuy.bbym.sales.transactioncheck.fields.v1.CarrierBaseType;
import com.bestbuy.tsh.common.datatype.v1.IdentifierType;
import com.bestbuy.tsh.common.datatype.v1.UserAreaType;


/**
 * <p>Java class for HeaderType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HeaderType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/v1}ID" maxOccurs="10" minOccurs="0"/>
 *         &lt;element ref="{http://bestbuy.com/bbym/sales/transactioncheck/component/v1}TransationSourceDetail" minOccurs="0"/>
 *         &lt;element ref="{http://bestbuy.com/bbym/sales/transactioncheck/fields/v1}OrderType" minOccurs="0"/>
 *         &lt;element ref="{http://bestbuy.com/bbym/sales/transactioncheck/fields/v1}ActivationType" minOccurs="0"/>
 *         &lt;element ref="{http://bestbuy.com/bbym/sales/transactioncheck/fields/v1}ContractType" minOccurs="0"/>
 *         &lt;element ref="{http://bestbuy.com/bbym/sales/transactioncheck/component/v1}Customer" minOccurs="0"/>
 *         &lt;element ref="{http://bestbuy.com/bbym/sales/transactioncheck/fields/v1}Carrier" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/v1}UserArea" minOccurs="0"/>
 *         &lt;element ref="{http://bestbuy.com/bbym/sales/transactioncheck/fields/v1}LinesRequested" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HeaderType", propOrder = {
    "id",
    "transationSourceDetail",
    "orderType",
    "activationType",
    "contractType",
    "customer",
    "carrier",
    "userArea",
    "linesRequested"
})
public class HeaderType {

    @XmlElement(name = "ID", namespace = "http://www.tsh.bestbuy.com/common/v1")
    protected List<IdentifierType> id;
    @XmlElement(name = "TransationSourceDetail")
    protected TransationSourceDetailType transationSourceDetail;
    @XmlElement(name = "OrderType", namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1")
    protected String orderType;
    @XmlElement(name = "ActivationType", namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1")
    protected String activationType;
    @XmlElement(name = "ContractType", namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1")
    protected String contractType;
    @XmlElement(name = "Customer")
    protected CustomerType customer;
    @XmlElement(name = "Carrier", namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1")
    protected CarrierBaseType carrier;
    @XmlElement(name = "UserArea", namespace = "http://www.tsh.bestbuy.com/common/v1")
    protected UserAreaType userArea;
    @XmlElement(name = "LinesRequested", namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1")
    protected BigInteger linesRequested;

    /**
     * Add N number of ID we can use it here Gets the value of the id property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the id property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getID().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IdentifierType }
     * 
     * 
     */
    public List<IdentifierType> getID() {
        if (id == null) {
            id = new ArrayList<IdentifierType>();
        }
        return this.id;
    }

    /**
     * Gets the value of the transationSourceDetail property.
     * 
     * @return
     *     possible object is
     *     {@link TransationSourceDetailType }
     *     
     */
    public TransationSourceDetailType getTransationSourceDetail() {
        return transationSourceDetail;
    }

    /**
     * Sets the value of the transationSourceDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransationSourceDetailType }
     *     
     */
    public void setTransationSourceDetail(TransationSourceDetailType value) {
        this.transationSourceDetail = value;
    }

    /**
     * Order Type - SALE, RETURN or CANCEL
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * Sets the value of the orderType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderType(String value) {
        this.orderType = value;
    }

    /**
     * Activation Type e.g. New, Upgrade, etc.Use same codes as in VBS
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActivationType() {
        return activationType;
    }

    /**
     * Sets the value of the activationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActivationType(String value) {
        this.activationType = value;
    }

    /**
     * Indicate if the Activation is NOCONTRACT or POSTPAID
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractType() {
        return contractType;
    }

    /**
     * Sets the value of the contractType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractType(String value) {
        this.contractType = value;
    }

    /**
     * Gets the value of the customer property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerType }
     *     
     */
    public CustomerType getCustomer() {
        return customer;
    }

    /**
     * Sets the value of the customer property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerType }
     *     
     */
    public void setCustomer(CustomerType value) {
        this.customer = value;
    }

    /**
     * Gets the value of the carrier property.
     * 
     * @return
     *     possible object is
     *     {@link CarrierBaseType }
     *     
     */
    public CarrierBaseType getCarrier() {
        return carrier;
    }

    /**
     * Sets the value of the carrier property.
     * 
     * @param value
     *     allowed object is
     *     {@link CarrierBaseType }
     *     
     */
    public void setCarrier(CarrierBaseType value) {
        this.carrier = value;
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

    /**
     * Number of Lines requested for Activation
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLinesRequested() {
        return linesRequested;
    }

    /**
     * Sets the value of the linesRequested property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLinesRequested(BigInteger value) {
        this.linesRequested = value;
    }

}
