
package com.bestbuy.tsh.sales.common.components.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.bestbuy.tsh.common.datatype.v1.TextType;


/**
 * LoyaltyInfoType
 * 
 * <p>Java class for LoyaltyInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LoyaltyInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}LoyaltyMemberNumber" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}LoyaltyMemberLevel" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}LoyaltyMemberLevelDesc" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LoyaltyInfoType", propOrder = {
    "loyaltyMemberNumber",
    "loyaltyMemberLevel",
    "loyaltyMemberLevelDesc"
})
public class LoyaltyInfoType {

    @XmlElement(name = "LoyaltyMemberNumber", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType loyaltyMemberNumber;
    @XmlElement(name = "LoyaltyMemberLevel", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType loyaltyMemberLevel;
    @XmlElement(name = "LoyaltyMemberLevelDesc", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType loyaltyMemberLevelDesc;

    /**
     * This element holds LoyaltyMemberNumber
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getLoyaltyMemberNumber() {
        return loyaltyMemberNumber;
    }

    /**
     * Sets the value of the loyaltyMemberNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setLoyaltyMemberNumber(TextType value) {
        this.loyaltyMemberNumber = value;
    }

    /**
     * This element holds LoyaltyMemberLevel
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getLoyaltyMemberLevel() {
        return loyaltyMemberLevel;
    }

    /**
     * Sets the value of the loyaltyMemberLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setLoyaltyMemberLevel(TextType value) {
        this.loyaltyMemberLevel = value;
    }

    /**
     * This element holds LoyaltyMemberLevelDesc
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getLoyaltyMemberLevelDesc() {
        return loyaltyMemberLevelDesc;
    }

    /**
     * Sets the value of the loyaltyMemberLevelDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setLoyaltyMemberLevelDesc(TextType value) {
        this.loyaltyMemberLevelDesc = value;
    }

}
