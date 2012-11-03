
package com.bestbuy.tsh.facilities.locations.fields.v2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.bestbuy.tsh.common.communication.v1.PhoneBaseType;


/**
 * Geographical location of the store
 * 
 * <p>Java class for CommunicationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CommunicationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/communication/v1}Phone" maxOccurs="5" minOccurs="0"/>
 *         &lt;element name="Address" type="{http://www.tsh.bestbuy.com/facilities/locations/fields/v2}CustomAddressType" minOccurs="0"/>
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
    "address"
})
public class CommunicationType {

    @XmlElement(name = "Phone", namespace = "http://www.tsh.bestbuy.com/common/communication/v1")
    protected List<PhoneBaseType> phone;
    @XmlElement(name = "Address")
    protected CustomAddressType address;

    /**
     * Gets the value of the phone property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the phone property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPhone().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PhoneBaseType }
     * 
     * 
     */
    public List<PhoneBaseType> getPhone() {
        if (phone == null) {
            phone = new ArrayList<PhoneBaseType>();
        }
        return this.phone;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link CustomAddressType }
     *     
     */
    public CustomAddressType getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomAddressType }
     *     
     */
    public void setAddress(CustomAddressType value) {
        this.address = value;
    }

}
