
package com.bestbuy.bbym.sales.transactioncheck.component.v1;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.bestbuy.tsh.common.datatype.v1.CodeType;


/**
 * <p>Java class for ItemizedRuleResultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ItemizedRuleResultType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://bestbuy.com/bbym/sales/transactioncheck/fields/v1}Rule" minOccurs="0"/>
 *         &lt;element ref="{http://bestbuy.com/bbym/sales/transactioncheck/fields/v1}Status" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/v1}Description" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/v1}ReasonCode" minOccurs="0"/>
 *         &lt;element ref="{http://bestbuy.com/bbym/sales/transactioncheck/fields/v1}Score" minOccurs="0"/>
 *         &lt;element ref="{http://bestbuy.com/bbym/sales/transactioncheck/component/v1}NameValuePairList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemizedRuleResultType", propOrder = {
    "rule",
    "status",
    "description",
    "reasonCode",
    "score",
    "nameValuePairList"
})
public class ItemizedRuleResultType {

    @XmlElement(name = "Rule", namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1")
    protected String rule;
    @XmlElement(name = "Status", namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String status;
    @XmlElement(name = "Description", namespace = "http://www.tsh.bestbuy.com/common/v1")
    protected String description;
    @XmlElement(name = "ReasonCode", namespace = "http://www.tsh.bestbuy.com/common/v1")
    protected CodeType reasonCode;
    @XmlElement(name = "Score", namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1")
    protected BigInteger score;
    @XmlElement(name = "NameValuePairList")
    protected NameValuePairListType nameValuePairList;

    /**
     * Indicates the Rule that was executed. E.g: 30DayCheck
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRule() {
        return rule;
    }

    /**
     * Sets the value of the rule property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRule(String value) {
        this.rule = value;
    }

    /**
     * 
     * 				Status of the Ruleset or RuleList.Example Approved, Declined
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the reasonCode property.
     * 
     * @return
     *     possible object is
     *     {@link CodeType }
     *     
     */
    public CodeType getReasonCode() {
        return reasonCode;
    }

    /**
     * Sets the value of the reasonCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeType }
     *     
     */
    public void setReasonCode(CodeType value) {
        this.reasonCode = value;
    }

    /**
     * Indicates the score after executing the rule.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getScore() {
        return score;
    }

    /**
     * Sets the value of the score property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setScore(BigInteger value) {
        this.score = value;
    }

    /**
     * Gets the value of the nameValuePairList property.
     * 
     * @return
     *     possible object is
     *     {@link NameValuePairListType }
     *     
     */
    public NameValuePairListType getNameValuePairList() {
        return nameValuePairList;
    }

    /**
     * Sets the value of the nameValuePairList property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameValuePairListType }
     *     
     */
    public void setNameValuePairList(NameValuePairListType value) {
        this.nameValuePairList = value;
    }

}
