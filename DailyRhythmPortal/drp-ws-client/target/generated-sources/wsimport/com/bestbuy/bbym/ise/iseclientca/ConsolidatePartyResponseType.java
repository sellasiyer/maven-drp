
package com.bestbuy.bbym.ise.iseclientca;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ConsolidatePartyResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConsolidatePartyResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://webservices.bestbuy.com/ca/services/entity/v2}BaseResponseType">
 *       &lt;sequence>
 *         &lt;element name="ConsolidatedParty" type="{http://webservices.bestbuy.com/ca/services/entity/v2}ThinPartyType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConsolidatePartyResponseType", propOrder = {
    "consolidatedParty"
})
public class ConsolidatePartyResponseType
    extends BaseResponseType
{

    @XmlElement(name = "ConsolidatedParty")
    protected ThinPartyType consolidatedParty;

    /**
     * Gets the value of the consolidatedParty property.
     * 
     * @return
     *     possible object is
     *     {@link ThinPartyType }
     *     
     */
    public ThinPartyType getConsolidatedParty() {
        return consolidatedParty;
    }

    /**
     * Sets the value of the consolidatedParty property.
     * 
     * @param value
     *     allowed object is
     *     {@link ThinPartyType }
     *     
     */
    public void setConsolidatedParty(ThinPartyType value) {
        this.consolidatedParty = value;
    }

}
