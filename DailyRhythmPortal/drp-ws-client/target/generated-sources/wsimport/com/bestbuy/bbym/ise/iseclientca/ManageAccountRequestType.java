
package com.bestbuy.bbym.ise.iseclientca;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ManageAccountRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ManageAccountRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://webservices.bestbuy.com/ca/services/entity/v2}BaseRequestType">
 *       &lt;sequence>
 *         &lt;element name="partyDetail" type="{http://webservices.bestbuy.com/ca/services/entity/v2}PartyType" minOccurs="0"/>
 *         &lt;element name="PartyIdentity" type="{http://webservices.bestbuy.com/ca/services/entity/v2}IdentityType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ManageAccountRequestType", propOrder = {
    "partyDetail",
    "partyIdentity"
})
public class ManageAccountRequestType
    extends BaseRequestType
{

    protected PartyType partyDetail;
    @XmlElement(name = "PartyIdentity")
    protected IdentityType partyIdentity;

    /**
     * Gets the value of the partyDetail property.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getPartyDetail() {
        return partyDetail;
    }

    /**
     * Sets the value of the partyDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setPartyDetail(PartyType value) {
        this.partyDetail = value;
    }

    /**
     * Gets the value of the partyIdentity property.
     * 
     * @return
     *     possible object is
     *     {@link IdentityType }
     *     
     */
    public IdentityType getPartyIdentity() {
        return partyIdentity;
    }

    /**
     * Sets the value of the partyIdentity property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentityType }
     *     
     */
    public void setPartyIdentity(IdentityType value) {
        this.partyIdentity = value;
    }

}
