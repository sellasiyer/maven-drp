
package com.bestbuy.tsh.sales.common.components.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.bestbuy.tsh.common.datatype.v1.TextType;
import com.bestbuy.tsh.common.location.v1.CountryCodeType;
import com.bestbuy.tsh.common.location.v1.PostalCodeType;
import com.bestbuy.tsh.common.location.v1.StateCodeType;


/**
 * AddressType
 * 
 * <p>Java class for AddressType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AddressType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}Name" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/location/v1}AddressLine1" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/location/v1}AddressLine2" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/location/v1}City" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/location/v1}State" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/location/v1}PostalCode" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/location/v1}CountryCode" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddressType", propOrder = {
    "name",
    "addressLine1",
    "addressLine2",
    "city",
    "state",
    "postalCode",
    "countryCode"
})
public class AddressType {

    @XmlElement(name = "Name", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType name;
    @XmlElement(name = "AddressLine1", namespace = "http://www.tsh.bestbuy.com/common/location/v1")
    protected TextType addressLine1;
    @XmlElement(name = "AddressLine2", namespace = "http://www.tsh.bestbuy.com/common/location/v1")
    protected TextType addressLine2;
    @XmlElement(name = "City", namespace = "http://www.tsh.bestbuy.com/common/location/v1")
    protected TextType city;
    @XmlElement(name = "State", namespace = "http://www.tsh.bestbuy.com/common/location/v1")
    protected StateCodeType state;
    @XmlElement(name = "PostalCode", namespace = "http://www.tsh.bestbuy.com/common/location/v1")
    protected PostalCodeType postalCode;
    @XmlElement(name = "CountryCode", namespace = "http://www.tsh.bestbuy.com/common/location/v1")
    protected CountryCodeType countryCode;

    /**
     * This element holds Name
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setName(TextType value) {
        this.name = value;
    }

    /**
     * Special Characters Do not use special characters or
     *                 punctuation (such as %, &, ', -, ?, !, etc.) when entering your address.
     * 							
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getAddressLine1() {
        return addressLine1;
    }

    /**
     * Sets the value of the addressLine1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setAddressLine1(TextType value) {
        this.addressLine1 = value;
    }

    /**
     *  Address line 2 should contain the following, if necessary:
     * 				Apartment number (Apt 244)
     * 				Care of (c o, a space is necessary between c and o)
     * 				Attention (attn John Doe)
     * 							
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getAddressLine2() {
        return addressLine2;
    }

    /**
     * Sets the value of the addressLine2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setAddressLine2(TextType value) {
        this.addressLine2 = value;
    }

    /**
     *  Identifies the Town or the City 
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getCity() {
        return city;
    }

    /**
     * Sets the value of the city property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setCity(TextType value) {
        this.city = value;
    }

    /**
     *  State in US are referred as Province in Canada.
     * 
     * @return
     *     possible object is
     *     {@link StateCodeType }
     *     
     */
    public StateCodeType getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link StateCodeType }
     *     
     */
    public void setState(StateCodeType value) {
        this.state = value;
    }

    /**
     *  PostalCode of the address 
     * 
     * @return
     *     possible object is
     *     {@link PostalCodeType }
     *     
     */
    public PostalCodeType getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the value of the postalCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link PostalCodeType }
     *     
     */
    public void setPostalCode(PostalCodeType value) {
        this.postalCode = value;
    }

    /**
     *  Country in which the Address is in. Preferable values is as specified in ISO 3166-2. 
     * 
     * @return
     *     possible object is
     *     {@link CountryCodeType }
     *     
     */
    public CountryCodeType getCountryCode() {
        return countryCode;
    }

    /**
     * Sets the value of the countryCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CountryCodeType }
     *     
     */
    public void setCountryCode(CountryCodeType value) {
        this.countryCode = value;
    }

}
