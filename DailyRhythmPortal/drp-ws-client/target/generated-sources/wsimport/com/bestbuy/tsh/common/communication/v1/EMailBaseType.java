
package com.bestbuy.tsh.common.communication.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.bestbuy.tsh.common.v1.StatusType;


/**
 * The EMail type used to bind email details
 * 
 * <p>Java class for EMailBaseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EMailBaseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/communication/v1}EmailID" minOccurs="0"/>
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
@XmlType(name = "EMailBaseType", propOrder = {
    "emailID",
    "status"
})
public class EMailBaseType {

    @XmlElement(name = "EmailID")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String emailID;
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
     * ID for the associated Object
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailID() {
        return emailID;
    }

    /**
     * Sets the value of the emailID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailID(String value) {
        this.emailID = value;
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
