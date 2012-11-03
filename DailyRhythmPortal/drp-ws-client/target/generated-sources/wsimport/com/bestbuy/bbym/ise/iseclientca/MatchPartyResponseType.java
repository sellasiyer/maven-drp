
package com.bestbuy.bbym.ise.iseclientca;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MatchPartyResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MatchPartyResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://webservices.bestbuy.com/ca/services/entity/v2}BaseResponseType">
 *       &lt;choice>
 *         &lt;element name="PartyDetail" type="{http://webservices.bestbuy.com/ca/services/entity/v2}PartyType" minOccurs="0"/>
 *         &lt;element name="Party" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="PartyId" type="{http://webservices.bestbuy.com/ca/services/entity/v2}IdType" minOccurs="0"/>
 *                   &lt;element name="Alias" type="{http://webservices.bestbuy.com/ca/services/entity/v2}AliasType" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MatchPartyResponseType", propOrder = {
    "partyDetail",
    "party"
})
public class MatchPartyResponseType
    extends BaseResponseType
{

    @XmlElement(name = "PartyDetail")
    protected PartyType partyDetail;
    @XmlElement(name = "Party")
    protected MatchPartyResponseType.Party party;

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
     * Gets the value of the party property.
     * 
     * @return
     *     possible object is
     *     {@link MatchPartyResponseType.Party }
     *     
     */
    public MatchPartyResponseType.Party getParty() {
        return party;
    }

    /**
     * Sets the value of the party property.
     * 
     * @param value
     *     allowed object is
     *     {@link MatchPartyResponseType.Party }
     *     
     */
    public void setParty(MatchPartyResponseType.Party value) {
        this.party = value;
    }


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
     *         &lt;element name="PartyId" type="{http://webservices.bestbuy.com/ca/services/entity/v2}IdType" minOccurs="0"/>
     *         &lt;element name="Alias" type="{http://webservices.bestbuy.com/ca/services/entity/v2}AliasType" maxOccurs="unbounded" minOccurs="0"/>
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
        "partyId",
        "alias"
    })
    public static class Party {

        @XmlElement(name = "PartyId")
        protected String partyId;
        @XmlElement(name = "Alias")
        protected List<AliasType> alias;

        /**
         * Gets the value of the partyId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPartyId() {
            return partyId;
        }

        /**
         * Sets the value of the partyId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPartyId(String value) {
            this.partyId = value;
        }

        /**
         * Gets the value of the alias property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the alias property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAlias().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AliasType }
         * 
         * 
         */
        public List<AliasType> getAlias() {
            if (alias == null) {
                alias = new ArrayList<AliasType>();
            }
            return this.alias;
        }

    }

}
