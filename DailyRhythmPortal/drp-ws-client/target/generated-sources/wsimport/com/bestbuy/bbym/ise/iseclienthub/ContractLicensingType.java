
package com.bestbuy.bbym.ise.iseclienthub;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ContractLicensingType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContractLicensingType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NumberofSeats" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="NumberofUsers" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContractLicensingType", propOrder = {
    "numberofSeats",
    "numberofUsers"
})
public class ContractLicensingType {

    @XmlElement(name = "NumberofSeats")
    protected BigInteger numberofSeats;
    @XmlElement(name = "NumberofUsers")
    protected BigInteger numberofUsers;

    /**
     * Gets the value of the numberofSeats property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumberofSeats() {
        return numberofSeats;
    }

    /**
     * Sets the value of the numberofSeats property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumberofSeats(BigInteger value) {
        this.numberofSeats = value;
    }

    /**
     * Gets the value of the numberofUsers property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumberofUsers() {
        return numberofUsers;
    }

    /**
     * Sets the value of the numberofUsers property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumberofUsers(BigInteger value) {
        this.numberofUsers = value;
    }

}
