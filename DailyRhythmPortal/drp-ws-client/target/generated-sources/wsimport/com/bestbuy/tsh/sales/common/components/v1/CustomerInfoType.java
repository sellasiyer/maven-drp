
package com.bestbuy.tsh.sales.common.components.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.bestbuy.tsh.common.datatype.v1.TextType;


/**
 * CustomerInfoType
 * 
 * <p>Java class for CustomerInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustomerInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LoyaltyInfo" type="{http://www.tsh.bestbuy.com/sales/common/components/v1}LoyaltyInfoType" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}TECCard" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerInfoType", propOrder = {
    "loyaltyInfo",
    "tecCard"
})
public class CustomerInfoType {

    @XmlElement(name = "LoyaltyInfo")
    protected LoyaltyInfoType loyaltyInfo;
    @XmlElement(name = "TECCard", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType tecCard;

    /**
     * Gets the value of the loyaltyInfo property.
     * 
     * @return
     *     possible object is
     *     {@link LoyaltyInfoType }
     *     
     */
    public LoyaltyInfoType getLoyaltyInfo() {
        return loyaltyInfo;
    }

    /**
     * Sets the value of the loyaltyInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link LoyaltyInfoType }
     *     
     */
    public void setLoyaltyInfo(LoyaltyInfoType value) {
        this.loyaltyInfo = value;
    }

    /**
     * This element holds TECCard
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getTECCard() {
        return tecCard;
    }

    /**
     * Sets the value of the tecCard property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setTECCard(TextType value) {
        this.tecCard = value;
    }

}
