
package com.bestbuy.tsh.common.communication.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Provides Complete Email Type of the Customer.
 * 			
 * 
 * <p>Java class for EmailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EmailType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EmailAddress" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}StringType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="preferrence" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}StringType" />
 *       &lt;attribute name="type" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}StringType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmailType", propOrder = {
    "emailAddress"
})
public class EmailType {

    @XmlElement(name = "EmailAddress", required = true)
    protected String emailAddress;
    @XmlAttribute
    protected String preferrence;
    @XmlAttribute
    protected String type;

    /**
     * Gets the value of the emailAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Sets the value of the emailAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailAddress(String value) {
        this.emailAddress = value;
    }

    /**
     * Gets the value of the preferrence property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreferrence() {
        return preferrence;
    }

    /**
     * Sets the value of the preferrence property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreferrence(String value) {
        this.preferrence = value;
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
