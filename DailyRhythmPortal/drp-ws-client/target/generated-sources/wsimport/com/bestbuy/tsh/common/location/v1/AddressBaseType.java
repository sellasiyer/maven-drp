
package com.bestbuy.tsh.common.location.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.bestbuy.tsh.common.datatype.v1.IdentifierType;
import com.bestbuy.tsh.common.datatype.v1.NameType;
import com.bestbuy.tsh.common.datatype.v1.TextType;


/**
 * Address BaseType provides the information about the address or 
 * 				semantic address of an associated entity.
 * 			
 * 
 * <p>Java class for AddressBaseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AddressBaseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/v1}ID" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/v1}Description" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/location/v1}AddressLine1" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/location/v1}AddressLine2" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/location/v1}AddressLine3" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/location/v1}City" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element ref="{http://www.tsh.bestbuy.com/common/location/v1}Province" minOccurs="0"/>
 *           &lt;element ref="{http://www.tsh.bestbuy.com/common/location/v1}State" minOccurs="0"/>
 *         &lt;/choice>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/location/v1}PostalCode"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/location/v1}PostalCodePlus4" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/location/v1}CountryCode"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/location/v1}CountyName" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddressBaseType", propOrder = {
    "id",
    "description",
    "addressLine1",
    "addressLine2",
    "addressLine3",
    "city",
    "province",
    "state",
    "postalCode",
    "postalCodePlus4",
    "countryCode",
    "countyName"
})
public abstract class AddressBaseType {

    @XmlElement(name = "ID", namespace = "http://www.tsh.bestbuy.com/common/v1")
    protected IdentifierType id;
    @XmlElement(name = "Description", namespace = "http://www.tsh.bestbuy.com/common/v1")
    protected String description;
    @XmlElement(name = "AddressLine1")
    protected TextType addressLine1;
    @XmlElement(name = "AddressLine2")
    protected TextType addressLine2;
    @XmlElement(name = "AddressLine3")
    protected TextType addressLine3;
    @XmlElement(name = "City")
    protected TextType city;
    @XmlElement(name = "Province")
    protected StateCodeType province;
    @XmlElement(name = "State")
    protected StateCodeType state;
    @XmlElement(name = "PostalCode", required = true)
    protected PostalCodeType postalCode;
    @XmlElement(name = "PostalCodePlus4")
    protected String postalCodePlus4;
    @XmlElement(name = "CountryCode", required = true)
    protected CountryCodeType countryCode;
    @XmlElement(name = "CountyName")
    protected NameType countyName;

    /**
     *  Address ID in the object originating system
     * 
     * @return
     *     possible object is
     *     {@link IdentifierType }
     *     
     */
    public IdentifierType getID() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentifierType }
     *     
     */
    public void setID(IdentifierType value) {
        this.id = value;
    }

    /**
     * Address description
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
     * 
     * 				Special Characters Do not use special characters or
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
     *  Additional space to capture more information of the address.  
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getAddressLine3() {
        return addressLine3;
    }

    /**
     * Sets the value of the addressLine3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setAddressLine3(TextType value) {
        this.addressLine3 = value;
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
     * 
     * 				Province in Canada are referred as State in US
     * 			
     * 
     * @return
     *     possible object is
     *     {@link StateCodeType }
     *     
     */
    public StateCodeType getProvince() {
        return province;
    }

    /**
     * Sets the value of the province property.
     * 
     * @param value
     *     allowed object is
     *     {@link StateCodeType }
     *     
     */
    public void setProvince(StateCodeType value) {
        this.province = value;
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
     * An extended Postal CodePlus4 to identify a
     * 				geographic segment
     * 			
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostalCodePlus4() {
        return postalCodePlus4;
    }

    /**
     * Sets the value of the postalCodePlus4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostalCodePlus4(String value) {
        this.postalCodePlus4 = value;
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

    /**
     *  The fully-spelled county name. 
     * 
     * @return
     *     possible object is
     *     {@link NameType }
     *     
     */
    public NameType getCountyName() {
        return countyName;
    }

    /**
     * Sets the value of the countyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameType }
     *     
     */
    public void setCountyName(NameType value) {
        this.countyName = value;
    }

}
