
package com.bestbuy.tsh.sales.common.components.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.bestbuy.tsh.common.datatype.v1.TextType;


/**
 * AccountType
 * 
 * <p>Java class for AccountType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccountType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}AccountCategory" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}AccountNumber" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountType", propOrder = {
    "accountCategory",
    "accountNumber"
})
public class AccountType {

    @XmlElement(name = "AccountCategory", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType accountCategory;
    @XmlElement(name = "AccountNumber", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType accountNumber;

    /**
     * This element holds AccountCategory
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getAccountCategory() {
        return accountCategory;
    }

    /**
     * Sets the value of the accountCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setAccountCategory(TextType value) {
        this.accountCategory = value;
    }

    /**
     * This element holds AccountNumber
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getAccountNumber() {
        return accountNumber;
    }

    /**
     * Sets the value of the accountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setAccountNumber(TextType value) {
        this.accountNumber = value;
    }

}
