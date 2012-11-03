
package com.bestbuy.bbym.sales.transactioncheck.component.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.bestbuy.tsh.common.datatype.v1.NameType;


/**
 * <p>Java class for RuleSetType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RuleSetType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="RuleList" type="{http://bestbuy.com/bbym/sales/transactioncheck/component/v1}RuleListType" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/v1}Name" minOccurs="0"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RuleSetType", propOrder = {
    "ruleList",
    "name"
})
public class RuleSetType {

    @XmlElement(name = "RuleList")
    protected RuleListType ruleList;
    @XmlElement(name = "Name", namespace = "http://www.tsh.bestbuy.com/common/v1")
    protected NameType name;

    /**
     * Gets the value of the ruleList property.
     * 
     * @return
     *     possible object is
     *     {@link RuleListType }
     *     
     */
    public RuleListType getRuleList() {
        return ruleList;
    }

    /**
     * Sets the value of the ruleList property.
     * 
     * @param value
     *     allowed object is
     *     {@link RuleListType }
     *     
     */
    public void setRuleList(RuleListType value) {
        this.ruleList = value;
    }

    /**
     * Indicates the name of the ruleSet to execute. E.g: TradeIn, NewActivation etc..
     * 
     * @return
     *     possible object is
     *     {@link NameType }
     *     
     */
    public NameType getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameType }
     *     
     */
    public void setName(NameType value) {
        this.name = value;
    }

}
