
package com.bestbuy.bbym.sales.transactioncheck.component.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.bestbuy.tsh.common.metadata.components.v1.InternationalBusinessHierarchyType;
import com.bestbuy.tsh.common.metadata.components.v1.RequestMetaDataType;


/**
 * <p>Java class for TransactionCheckRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransactionCheckRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RequestMetaData" type="{http://www.tsh.bestbuy.com/common/metadata/components/v1}RequestMetaDataType" minOccurs="0"/>
 *         &lt;element name="InternationalBusinessHierarchy" type="{http://www.tsh.bestbuy.com/common/metadata/components/v1}InternationalBusinessHierarchyType" minOccurs="0"/>
 *         &lt;element name="TransactionCheck" type="{http://bestbuy.com/bbym/sales/transactioncheck/component/v1}TransactionCheckType"/>
 *         &lt;element ref="{http://bestbuy.com/bbym/sales/transactioncheck/component/v1}RuleSet"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransactionCheckRequestType", propOrder = {
    "requestMetaData",
    "internationalBusinessHierarchy",
    "transactionCheck",
    "ruleSet"
})
public class TransactionCheckRequestType {

    @XmlElement(name = "RequestMetaData")
    protected RequestMetaDataType requestMetaData;
    @XmlElement(name = "InternationalBusinessHierarchy")
    protected InternationalBusinessHierarchyType internationalBusinessHierarchy;
    @XmlElement(name = "TransactionCheck", required = true)
    protected TransactionCheckType transactionCheck;
    @XmlElement(name = "RuleSet", required = true)
    protected RuleSetType ruleSet;

    /**
     * Gets the value of the requestMetaData property.
     * 
     * @return
     *     possible object is
     *     {@link RequestMetaDataType }
     *     
     */
    public RequestMetaDataType getRequestMetaData() {
        return requestMetaData;
    }

    /**
     * Sets the value of the requestMetaData property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestMetaDataType }
     *     
     */
    public void setRequestMetaData(RequestMetaDataType value) {
        this.requestMetaData = value;
    }

    /**
     * Gets the value of the internationalBusinessHierarchy property.
     * 
     * @return
     *     possible object is
     *     {@link InternationalBusinessHierarchyType }
     *     
     */
    public InternationalBusinessHierarchyType getInternationalBusinessHierarchy() {
        return internationalBusinessHierarchy;
    }

    /**
     * Sets the value of the internationalBusinessHierarchy property.
     * 
     * @param value
     *     allowed object is
     *     {@link InternationalBusinessHierarchyType }
     *     
     */
    public void setInternationalBusinessHierarchy(InternationalBusinessHierarchyType value) {
        this.internationalBusinessHierarchy = value;
    }

    /**
     * Gets the value of the transactionCheck property.
     * 
     * @return
     *     possible object is
     *     {@link TransactionCheckType }
     *     
     */
    public TransactionCheckType getTransactionCheck() {
        return transactionCheck;
    }

    /**
     * Sets the value of the transactionCheck property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransactionCheckType }
     *     
     */
    public void setTransactionCheck(TransactionCheckType value) {
        this.transactionCheck = value;
    }

    /**
     * Client can either indicate RuleSet Name to execute, e.g. NewActivation, TradeIn or specify the specific one or more rules to execute e.g. 30DayCheck or stolen check or velocity check.
     * NOTE: Rule Set consisits of multiple rules.
     * 
     * @return
     *     possible object is
     *     {@link RuleSetType }
     *     
     */
    public RuleSetType getRuleSet() {
        return ruleSet;
    }

    /**
     * Sets the value of the ruleSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link RuleSetType }
     *     
     */
    public void setRuleSet(RuleSetType value) {
        this.ruleSet = value;
    }

}
