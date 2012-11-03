
package com.bestbuy.bbym.ise.iseclientca;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TransferAccountRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransferAccountRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://webservices.bestbuy.com/ca/services/entity/v2}BaseRequestType">
 *       &lt;sequence>
 *         &lt;element name="SourceParty" type="{http://webservices.bestbuy.com/ca/services/entity/v2}ThinPartyType" minOccurs="0"/>
 *         &lt;element name="DestinationParty" type="{http://webservices.bestbuy.com/ca/services/entity/v2}ThinPartyType" minOccurs="0"/>
 *         &lt;element name="AccountList" type="{http://webservices.bestbuy.com/ca/services/entity/v2}ThinAccountListType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransferAccountRequestType", propOrder = {
    "sourceParty",
    "destinationParty",
    "accountList"
})
public class TransferAccountRequestType
    extends BaseRequestType
{

    @XmlElement(name = "SourceParty")
    protected ThinPartyType sourceParty;
    @XmlElement(name = "DestinationParty")
    protected ThinPartyType destinationParty;
    @XmlElement(name = "AccountList")
    protected ThinAccountListType accountList;

    /**
     * Gets the value of the sourceParty property.
     * 
     * @return
     *     possible object is
     *     {@link ThinPartyType }
     *     
     */
    public ThinPartyType getSourceParty() {
        return sourceParty;
    }

    /**
     * Sets the value of the sourceParty property.
     * 
     * @param value
     *     allowed object is
     *     {@link ThinPartyType }
     *     
     */
    public void setSourceParty(ThinPartyType value) {
        this.sourceParty = value;
    }

    /**
     * Gets the value of the destinationParty property.
     * 
     * @return
     *     possible object is
     *     {@link ThinPartyType }
     *     
     */
    public ThinPartyType getDestinationParty() {
        return destinationParty;
    }

    /**
     * Sets the value of the destinationParty property.
     * 
     * @param value
     *     allowed object is
     *     {@link ThinPartyType }
     *     
     */
    public void setDestinationParty(ThinPartyType value) {
        this.destinationParty = value;
    }

    /**
     * Gets the value of the accountList property.
     * 
     * @return
     *     possible object is
     *     {@link ThinAccountListType }
     *     
     */
    public ThinAccountListType getAccountList() {
        return accountList;
    }

    /**
     * Sets the value of the accountList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ThinAccountListType }
     *     
     */
    public void setAccountList(ThinAccountListType value) {
        this.accountList = value;
    }

}
