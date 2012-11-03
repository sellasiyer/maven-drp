
package com.bestbuy.bbym.ise.iseclientca;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ManageSubAccountAttributesRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ManageSubAccountAttributesRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://webservices.bestbuy.com/ca/services/entity/v2}BaseRequestType">
 *       &lt;sequence>
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
@XmlType(name = "ManageSubAccountAttributesRequestType", propOrder = {
    "partyAccountAttributeDetails"
})
public class ManageSubAccountAttributesRequestType
    extends BaseRequestType
{

    @XmlElement(name = "PartyAccountAttributeDetails")
    protected PartyAccountAttributeDetailsType partyAccountAttributeDetails;

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
