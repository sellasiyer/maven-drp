
package com.bestbuy.bbym.ise.iseclientcap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="submitActivationDetailsReturn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "submitActivationDetailsReturn"
})
@XmlRootElement(name = "submitActivationDetailsResponse")
public class SubmitActivationDetailsResponse {

    @XmlElement(required = true)
    protected String submitActivationDetailsReturn;

    /**
     * Gets the value of the submitActivationDetailsReturn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubmitActivationDetailsReturn() {
        return submitActivationDetailsReturn;
    }

    /**
     * Sets the value of the submitActivationDetailsReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubmitActivationDetailsReturn(String value) {
        this.submitActivationDetailsReturn = value;
    }

}
