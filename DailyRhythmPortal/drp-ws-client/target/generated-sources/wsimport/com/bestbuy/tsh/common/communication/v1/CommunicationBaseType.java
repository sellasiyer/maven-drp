
package com.bestbuy.tsh.common.communication.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.bestbuy.tsh.common.datatype.v1.CodeType;
import com.bestbuy.tsh.common.location.v1.MailingAddressType;


/**
 * Communication Base Type to define basic communication methods
 * 
 * <p>Java class for CommunicationBaseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CommunicationBaseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/v1}ChannelCode"/>
 *         &lt;choice>
 *           &lt;element name="Address" type="{http://www.tsh.bestbuy.com/common/location/v1}MailingAddressType" minOccurs="0"/>
 *           &lt;element ref="{http://www.tsh.bestbuy.com/common/communication/v1}EMail" minOccurs="0"/>
 *           &lt;element ref="{http://www.tsh.bestbuy.com/common/communication/v1}Phone" minOccurs="0"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute ref="{http://www.tsh.bestbuy.com/common/v1}operationCode"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CommunicationBaseType", propOrder = {
    "channelCode",
    "address",
    "eMail",
    "phone"
})
public class CommunicationBaseType {

    @XmlElement(name = "ChannelCode", namespace = "http://www.tsh.bestbuy.com/common/v1", required = true)
    protected CodeType channelCode;
    @XmlElement(name = "Address")
    protected MailingAddressType address;
    @XmlElement(name = "EMail")
    protected EMailBaseType eMail;
    @XmlElement(name = "Phone")
    protected PhoneBaseType phone;
    @XmlAttribute(namespace = "http://www.tsh.bestbuy.com/common/v1")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String operationCode;

    /**
     * Channel code denotes the communication option used in the message 
     * 
     * @return
     *     possible object is
     *     {@link CodeType }
     *     
     */
    public CodeType getChannelCode() {
        return channelCode;
    }

    /**
     * Sets the value of the channelCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeType }
     *     
     */
    public void setChannelCode(CodeType value) {
        this.channelCode = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link MailingAddressType }
     *     
     */
    public MailingAddressType getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link MailingAddressType }
     *     
     */
    public void setAddress(MailingAddressType value) {
        this.address = value;
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
     * Gets the value of the operationCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperationCode() {
        return operationCode;
    }

    /**
     * Sets the value of the operationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperationCode(String value) {
        this.operationCode = value;
    }

}
