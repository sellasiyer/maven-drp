
package com.bestbuy.bbym.ise.iseclientca;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CleanseCustomerDataRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CleanseCustomerDataRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://webservices.bestbuy.com/ca/services/entity/v2}BaseRequestType">
 *       &lt;sequence>
 *         &lt;element name="Party" type="{http://webservices.bestbuy.com/ca/services/entity/v2}ThinPartyType" minOccurs="0"/>
 *         &lt;element name="PhoneIdList" type="{http://webservices.bestbuy.com/ca/services/entity/v2}PhoneIdListType" minOccurs="0"/>
 *         &lt;element name="EmailIdList" type="{http://webservices.bestbuy.com/ca/services/entity/v2}EmailAddressIDListType" minOccurs="0"/>
 *         &lt;element name="PostalAddressIdList" type="{http://webservices.bestbuy.com/ca/services/entity/v2}PostalAddressIdListType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CleanseCustomerDataRequestType", propOrder = {
    "party",
    "phoneIdList",
    "emailIdList",
    "postalAddressIdList"
})
public class CleanseCustomerDataRequestType
    extends BaseRequestType
{

    @XmlElement(name = "Party")
    protected ThinPartyType party;
    @XmlElement(name = "PhoneIdList")
    protected PhoneIdListType phoneIdList;
    @XmlElement(name = "EmailIdList")
    protected EmailAddressIDListType emailIdList;
    @XmlElement(name = "PostalAddressIdList")
    protected PostalAddressIdListType postalAddressIdList;

    /**
     * Gets the value of the party property.
     * 
     * @return
     *     possible object is
     *     {@link ThinPartyType }
     *     
     */
    public ThinPartyType getParty() {
        return party;
    }

    /**
     * Sets the value of the party property.
     * 
     * @param value
     *     allowed object is
     *     {@link ThinPartyType }
     *     
     */
    public void setParty(ThinPartyType value) {
        this.party = value;
    }

    /**
     * Gets the value of the phoneIdList property.
     * 
     * @return
     *     possible object is
     *     {@link PhoneIdListType }
     *     
     */
    public PhoneIdListType getPhoneIdList() {
        return phoneIdList;
    }

    /**
     * Sets the value of the phoneIdList property.
     * 
     * @param value
     *     allowed object is
     *     {@link PhoneIdListType }
     *     
     */
    public void setPhoneIdList(PhoneIdListType value) {
        this.phoneIdList = value;
    }

    /**
     * Gets the value of the emailIdList property.
     * 
     * @return
     *     possible object is
     *     {@link EmailAddressIDListType }
     *     
     */
    public EmailAddressIDListType getEmailIdList() {
        return emailIdList;
    }

    /**
     * Sets the value of the emailIdList property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmailAddressIDListType }
     *     
     */
    public void setEmailIdList(EmailAddressIDListType value) {
        this.emailIdList = value;
    }

    /**
     * Gets the value of the postalAddressIdList property.
     * 
     * @return
     *     possible object is
     *     {@link PostalAddressIdListType }
     *     
     */
    public PostalAddressIdListType getPostalAddressIdList() {
        return postalAddressIdList;
    }

    /**
     * Sets the value of the postalAddressIdList property.
     * 
     * @param value
     *     allowed object is
     *     {@link PostalAddressIdListType }
     *     
     */
    public void setPostalAddressIdList(PostalAddressIdListType value) {
        this.postalAddressIdList = value;
    }

}
