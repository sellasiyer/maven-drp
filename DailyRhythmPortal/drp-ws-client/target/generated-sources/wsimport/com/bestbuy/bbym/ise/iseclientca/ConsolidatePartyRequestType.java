
package com.bestbuy.bbym.ise.iseclientca;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ConsolidatePartyRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConsolidatePartyRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://webservices.bestbuy.com/ca/services/entity/v2}BaseRequestType">
 *       &lt;sequence>
 *         &lt;element name="PartyList" type="{http://webservices.bestbuy.com/ca/services/entity/v2}ThinPartyListType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConsolidatePartyRequestType", propOrder = {
    "partyList"
})
public class ConsolidatePartyRequestType
    extends BaseRequestType
{

    @XmlElement(name = "PartyList")
    protected ThinPartyListType partyList;

    /**
     * Gets the value of the partyList property.
     * 
     * @return
     *     possible object is
     *     {@link ThinPartyListType }
     *     
     */
    public ThinPartyListType getPartyList() {
        return partyList;
    }

    /**
     * Sets the value of the partyList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ThinPartyListType }
     *     
     */
    public void setPartyList(ThinPartyListType value) {
        this.partyList = value;
    }

}
