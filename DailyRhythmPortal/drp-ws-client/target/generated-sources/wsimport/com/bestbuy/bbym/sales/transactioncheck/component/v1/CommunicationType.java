
package com.bestbuy.bbym.sales.transactioncheck.component.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.bestbuy.bbym.sales.transactioncheck.fields.v1.AddressType;
import com.bestbuy.tsh.common.communication.v1.EMailBaseType;
import com.bestbuy.tsh.common.communication.v1.PhoneBaseType;


/**
 * <p>Java class for CommunicationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CommunicationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/communication/v1}Phone" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/communication/v1}EMail" minOccurs="0"/>
 *         &lt;element name="Address" type="{http://bestbuy.com/bbym/sales/transactioncheck/fields/v1}AddressType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CommunicationType", propOrder = {
    "phone",
    "eMail",
    "address"
})
public class CommunicationType {

    @XmlElement(name = "Phone", namespace = "http://www.tsh.bestbuy.com/common/communication/v1")
    protected PhoneBaseType phone;
    @XmlElement(name = "EMail", namespace = "http://www.tsh.bestbuy.com/common/communication/v1")
    protected EMailBaseType eMail;
    @XmlElement(name = "Address", required = true)
    protected AddressType address;

    /**
     * Gets the value of the phone property.
     * 
     * @return
     *     possible object is
     *     {@link PhoneBaseType }
     *     
     */
    public PhoneBaseType getPhone() {
        return phone;
    }

    /**
     * Sets the value of the phone property.
     * 
     * @param value
     *     allowed object is
     *     {@link PhoneBaseType }
     *     
     */
    public void setPhone(PhoneBaseType value) {
        this.phone = value;
    }

    /**
     * Gets the value of the eMail property.
     * 
     * @return
     *     possible object is
     *     {@link EMailBaseType }
     *     
     */
    public EMailBaseType getEMail() {
        return eMail;
    }

    /**
     * Sets the value of the eMail property.
     * 
     * @param value
     *     allowed object is
     *     {@link EMailBaseType }
     *     
     */
    public void setEMail(EMailBaseType value) {
        this.eMail = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link AddressType }
     *     
     */
    public AddressType getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressType }
     *     
     */
    public void setAddress(AddressType value) {
        this.address = value;
    }

}
