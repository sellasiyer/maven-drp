
package com.bestbuy.bbym.ise.iseclientacds;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Returns information about the Fault Data like
 * 				ErrorCode, Error Message
 * 
 * <p>Java class for faultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="faultType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Code" type="{http://www.tsh.bestbuy.com/common/datatype/v1}TextType" minOccurs="0"/>
 *         &lt;element name="Message" type="{http://www.tsh.bestbuy.com/common/datatype/v1}TextType" minOccurs="0"/>
 *         &lt;element name="Detail" type="{http://www.tsh.bestbuy.com/common/datatype/v1}TextType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="type" type="{http://tsh.bestbuy.com/tsh/tshfault}FaultTypeEnumeration" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "faultType", namespace = "http://tsh.bestbuy.com/tsh/tshfault", propOrder = {
    "code",
    "message",
    "detail"
})
public class FaultType {

    @XmlElement(name = "Code")
    protected TextType code;
    @XmlElement(name = "Message")
    protected TextType message;
    @XmlElement(name = "Detail")
    protected TextType detail;
    @XmlAttribute
    protected FaultTypeEnumeration type;

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setCode(TextType value) {
        this.code = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setMessage(TextType value) {
        this.message = value;
    }

    /**
     * Gets the value of the detail property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getDetail() {
        return detail;
    }

    /**
     * Sets the value of the detail property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setDetail(TextType value) {
        this.detail = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link FaultTypeEnumeration }
     *     
     */
    public FaultTypeEnumeration getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link FaultTypeEnumeration }
     *     
     */
    public void setType(FaultTypeEnumeration value) {
        this.type = value;
    }

}
