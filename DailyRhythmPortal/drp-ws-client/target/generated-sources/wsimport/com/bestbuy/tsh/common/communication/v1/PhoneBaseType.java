
package com.bestbuy.tsh.common.communication.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.bestbuy.tsh.common.datatype.v1.IdentifierType;
import com.bestbuy.tsh.common.datatype.v1.TextType;
import com.bestbuy.tsh.common.v1.StatusType;


/**
 * The phone type used to bind phone number details
 * 
 * <p>Java class for PhoneBaseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PhoneBaseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/v1}ID" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/communication/v1}CountryDialing" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/communication/v1}AreaDialing" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/communication/v1}DialNumber" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/communication/v1}Extension" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/v1}Status" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute ref="{http://www.tsh.bestbuy.com/common/v1}preferedIndicator"/>
 *       &lt;attribute ref="{http://www.tsh.bestbuy.com/common/v1}primary"/>
 *       &lt;attribute ref="{http://www.tsh.bestbuy.com/common/v1}type"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PhoneBaseType", propOrder = {
    "id",
    "countryDialing",
    "areaDialing",
    "dialNumber",
    "extension",
    "status"
})
public class PhoneBaseType {

    @XmlElement(name = "ID", namespace = "http://www.tsh.bestbuy.com/common/v1")
    protected IdentifierType id;
    @XmlElement(name = "CountryDialing")
    protected TextType countryDialing;
    @XmlElement(name = "AreaDialing")
    protected TextType areaDialing;
    @XmlElement(name = "DialNumber")
    protected TextType dialNumber;
    @XmlElement(name = "Extension")
    protected TextType extension;
    @XmlElement(name = "Status", namespace = "http://www.tsh.bestbuy.com/common/v1")
    protected StatusType status;
    @XmlAttribute(namespace = "http://www.tsh.bestbuy.com/common/v1")
    protected Boolean preferedIndicator;
    @XmlAttribute(namespace = "http://www.tsh.bestbuy.com/common/v1")
    protected Boolean primary;
    @XmlAttribute(namespace = "http://www.tsh.bestbuy.com/common/v1")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String type;

    /**
     * Identifier of the associated object in source system
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
     * The country dialing code for a communication number.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getCountryDialing() {
        return countryDialing;
    }

    /**
     * Sets the value of the countryDialing property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setCountryDialing(TextType value) {
        this.countryDialing = value;
    }

    /**
     * The area dialing code for a communication number.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getAreaDialing() {
        return areaDialing;
    }

    /**
     * Sets the value of the areaDialing property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setAreaDialing(TextType value) {
        this.areaDialing = value;
    }

    /**
     * The commication number, not including country dialing or area dialing codes.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getDialNumber() {
        return dialNumber;
    }

    /**
     * Sets the value of the dialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setDialNumber(TextType value) {
        this.dialNumber = value;
    }

    /**
     * The extension of the assoicated communication number.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getExtension() {
        return extension;
    }

    /**
     * Sets the value of the extension property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setExtension(TextType value) {
        this.extension = value;
    }

    /**
     *  Indicates the status of the associated object by providing the Status Code along with a description and when the status is effective.
     * 
     * @return
     *     possible object is
     *     {@link StatusType }
     *     
     */
    public StatusType getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusType }
     *     
     */
    public void setStatus(StatusType value) {
        this.status = value;
    }

    /**
     * Gets the value of the preferedIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPreferedIndicator() {
        return preferedIndicator;
    }

    /**
     * Sets the value of the preferedIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPreferedIndicator(Boolean value) {
        this.preferedIndicator = value;
    }

    /**
     * Gets the value of the primary property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPrimary() {
        return primary;
    }

    /**
     * Sets the value of the primary property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPrimary(Boolean value) {
        this.primary = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

}
