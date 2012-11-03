
package com.bestbuy.bbym.ise.iseclientca;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ManageSubAccountAttributesResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ManageSubAccountAttributesResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://webservices.bestbuy.com/ca/services/entity/v2}BaseResponseType">
 *       &lt;sequence>
 *         &lt;element name="Success" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="PartyAccountAttributeDetails" type="{http://webservices.bestbuy.com/ca/services/entity/v2}PartyAccountAttributeDetailsType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ManageSubAccountAttributesResponseType", propOrder = {
    "success",
    "partyAccountAttributeDetails"
})
public class ManageSubAccountAttributesResponseType
    extends BaseResponseType
{

    @XmlElement(name = "Success")
    protected Boolean success;
    @XmlElement(name = "PartyAccountAttributeDetails")
    protected PartyAccountAttributeDetailsType partyAccountAttributeDetails;

    /**
     * Gets the value of the success property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSuccess() {
        return success;
    }

    /**
     * Sets the value of the success property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSuccess(Boolean value) {
        this.success = value;
    }

    /**
     * Gets the value of the partyAccountAttributeDetails property.
     * 
     * @return
     *     possible object is
     *     {@link PartyAccountAttributeDetailsType }
     *     
     */
    public PartyAccountAttributeDetailsType getPartyAccountAttributeDetails() {
        return partyAccountAttributeDetails;
    }

    /**
     * Sets the value of the partyAccountAttributeDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyAccountAttributeDetailsType }
     *     
     */
    public void setPartyAccountAttributeDetails(PartyAccountAttributeDetailsType value) {
        this.partyAccountAttributeDetails = value;
    }

}
