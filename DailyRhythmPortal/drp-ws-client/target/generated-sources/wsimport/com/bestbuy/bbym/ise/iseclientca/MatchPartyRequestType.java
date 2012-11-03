
package com.bestbuy.bbym.ise.iseclientca;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MatchPartyRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MatchPartyRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://webservices.bestbuy.com/ca/services/entity/v2}BaseRequestType">
 *       &lt;sequence>
 *         &lt;element name="Party" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="PartyIdentity" type="{http://webservices.bestbuy.com/ca/services/entity/v2}IdentityType" minOccurs="0"/>
 *                   &lt;element name="PartyDetail" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Alias" type="{http://webservices.bestbuy.com/ca/services/entity/v2}AliasType" minOccurs="0"/>
 *                             &lt;element name="Name" type="{http://webservices.bestbuy.com/ca/services/entity/v2}NameType" minOccurs="0"/>
 *                             &lt;element name="CommunicationAddress" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;choice>
 *                                       &lt;element name="PhoneAddress" type="{http://webservices.bestbuy.com/ca/services/entity/v2}PhoneType" minOccurs="0"/>
 *                                       &lt;element name="PostalAddress" type="{http://webservices.bestbuy.com/ca/services/entity/v2}AddressType" minOccurs="0"/>
 *                                       &lt;element name="EmailAddress" type="{http://webservices.bestbuy.com/ca/services/entity/v2}EmailType" minOccurs="0"/>
 *                                     &lt;/choice>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="Id" type="{http://webservices.bestbuy.com/ca/services/entity/v2}IdType" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Configuration" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ResponseLevel" type="{http://webservices.bestbuy.com/ca/services/entity/v2}MatchPartyResponseLevelType" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MatchPartyRequestType", propOrder = {
    "party",
    "configuration"
})
public class MatchPartyRequestType
    extends BaseRequestType
{

    @XmlElement(name = "Party")
    protected MatchPartyRequestType.Party party;
    @XmlElement(name = "Configuration")
    protected MatchPartyRequestType.Configuration configuration;

    /**
     * Gets the value of the party property.
     * 
     * @return
     *     possible object is
     *     {@link MatchPartyRequestType.Party }
     *     
     */
    public MatchPartyRequestType.Party getParty() {
        return party;
    }

    /**
     * Sets the value of the party property.
     * 
     * @param value
     *     allowed object is
     *     {@link MatchPartyRequestType.Party }
     *     
     */
    public void setParty(MatchPartyRequestType.Party value) {
        this.party = value;
    }

    /**
     * Gets the value of the configuration property.
     * 
     * @return
     *     possible object is
     *     {@link MatchPartyRequestType.Configuration }
     *     
     */
    public MatchPartyRequestType.Configuration getConfiguration() {
        return configuration;
    }

    /**
     * Sets the value of the configuration property.
     * 
     * @param value
     *     allowed object is
     *     {@link MatchPartyRequestType.Configuration }
     *     
     */
    public void setConfiguration(MatchPartyRequestType.Configuration value) {
        this.configuration = value;
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
     *         &lt;element name="ResponseLevel" type="{http://webservices.bestbuy.com/ca/services/entity/v2}MatchPartyResponseLevelType" minOccurs="0"/>
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
        "responseLevel"
    })
    public static class Configuration {

        @XmlElement(name = "ResponseLevel")
        protected MatchPartyResponseLevelType responseLevel;

        /**
         * Gets the value of the responseLevel property.
         * 
         * @return
         *     possible object is
         *     {@link MatchPartyResponseLevelType }
         *     
         */
        public MatchPartyResponseLevelType getResponseLevel() {
            return responseLevel;
        }

        /**
         * Sets the value of the responseLevel property.
         * 
         * @param value
         *     allowed object is
         *     {@link MatchPartyResponseLevelType }
         *     
         */
        public void setResponseLevel(MatchPartyResponseLevelType value) {
            this.responseLevel = value;
        }

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
     *         &lt;element name="PartyIdentity" type="{http://webservices.bestbuy.com/ca/services/entity/v2}IdentityType" minOccurs="0"/>
     *         &lt;element name="PartyDetail" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Alias" type="{http://webservices.bestbuy.com/ca/services/entity/v2}AliasType" minOccurs="0"/>
     *                   &lt;element name="Name" type="{http://webservices.bestbuy.com/ca/services/entity/v2}NameType" minOccurs="0"/>
     *                   &lt;element name="CommunicationAddress" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;choice>
     *                             &lt;element name="PhoneAddress" type="{http://webservices.bestbuy.com/ca/services/entity/v2}PhoneType" minOccurs="0"/>
     *                             &lt;element name="PostalAddress" type="{http://webservices.bestbuy.com/ca/services/entity/v2}AddressType" minOccurs="0"/>
     *                             &lt;element name="EmailAddress" type="{http://webservices.bestbuy.com/ca/services/entity/v2}EmailType" minOccurs="0"/>
     *                           &lt;/choice>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="Id" type="{http://webservices.bestbuy.com/ca/services/entity/v2}IdType" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "partyIdentity",
        "partyDetail"
    })
    public static class Party {

        @XmlElement(name = "PartyIdentity")
        protected IdentityType partyIdentity;
        @XmlElement(name = "PartyDetail")
        protected MatchPartyRequestType.Party.PartyDetail partyDetail;
        @XmlAttribute(name = "Id")
        protected String id;

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

        /**
         * Gets the value of the partyDetail property.
         * 
         * @return
         *     possible object is
         *     {@link MatchPartyRequestType.Party.PartyDetail }
         *     
         */
        public MatchPartyRequestType.Party.PartyDetail getPartyDetail() {
            return partyDetail;
        }

        /**
         * Sets the value of the partyDetail property.
         * 
         * @param value
         *     allowed object is
         *     {@link MatchPartyRequestType.Party.PartyDetail }
         *     
         */
        public void setPartyDetail(MatchPartyRequestType.Party.PartyDetail value) {
            this.partyDetail = value;
        }

        /**
         * Gets the value of the id property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getId() {
            return id;
        }

        /**
         * Sets the value of the id property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setId(String value) {
            this.id = value;
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
         *         &lt;element name="Alias" type="{http://webservices.bestbuy.com/ca/services/entity/v2}AliasType" minOccurs="0"/>
         *         &lt;element name="Name" type="{http://webservices.bestbuy.com/ca/services/entity/v2}NameType" minOccurs="0"/>
         *         &lt;element name="CommunicationAddress" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;choice>
         *                   &lt;element name="PhoneAddress" type="{http://webservices.bestbuy.com/ca/services/entity/v2}PhoneType" minOccurs="0"/>
         *                   &lt;element name="PostalAddress" type="{http://webservices.bestbuy.com/ca/services/entity/v2}AddressType" minOccurs="0"/>
         *                   &lt;element name="EmailAddress" type="{http://webservices.bestbuy.com/ca/services/entity/v2}EmailType" minOccurs="0"/>
         *                 &lt;/choice>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
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
            "alias",
            "name",
            "communicationAddress"
        })
        public static class PartyDetail {

            @XmlElement(name = "Alias")
            protected AliasType alias;
            @XmlElement(name = "Name")
            protected NameType name;
            @XmlElement(name = "CommunicationAddress")
            protected List<MatchPartyRequestType.Party.PartyDetail.CommunicationAddress> communicationAddress;

            /**
             * Gets the value of the alias property.
             * 
             * @return
             *     possible object is
             *     {@link AliasType }
             *     
             */
            public AliasType getAlias() {
                return alias;
            }

            /**
             * Sets the value of the alias property.
             * 
             * @param value
             *     allowed object is
             *     {@link AliasType }
             *     
             */
            public void setAlias(AliasType value) {
                this.alias = value;
            }

            /**
             * Gets the value of the name property.
             * 
             * @return
             *     possible object is
             *     {@link NameType }
             *     
             */
            public NameType getName() {
                return name;
            }

            /**
             * Sets the value of the name property.
             * 
             * @param value
             *     allowed object is
             *     {@link NameType }
             *     
             */
            public void setName(NameType value) {
                this.name = value;
            }

            /**
             * Gets the value of the communicationAddress property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the communicationAddress property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getCommunicationAddress().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link MatchPartyRequestType.Party.PartyDetail.CommunicationAddress }
             * 
             * 
             */
            public List<MatchPartyRequestType.Party.PartyDetail.CommunicationAddress> getCommunicationAddress() {
                if (communicationAddress == null) {
                    communicationAddress = new ArrayList<MatchPartyRequestType.Party.PartyDetail.CommunicationAddress>();
                }
                return this.communicationAddress;
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
             *       &lt;choice>
             *         &lt;element name="PhoneAddress" type="{http://webservices.bestbuy.com/ca/services/entity/v2}PhoneType" minOccurs="0"/>
             *         &lt;element name="PostalAddress" type="{http://webservices.bestbuy.com/ca/services/entity/v2}AddressType" minOccurs="0"/>
             *         &lt;element name="EmailAddress" type="{http://webservices.bestbuy.com/ca/services/entity/v2}EmailType" minOccurs="0"/>
             *       &lt;/choice>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "phoneAddress",
                "postalAddress",
                "emailAddress"
            })
            public static class CommunicationAddress {

                @XmlElement(name = "PhoneAddress")
                protected PhoneType phoneAddress;
                @XmlElement(name = "PostalAddress")
                protected AddressType postalAddress;
                @XmlElement(name = "EmailAddress")
                protected EmailType emailAddress;

                /**
                 * Gets the value of the phoneAddress property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link PhoneType }
                 *     
                 */
                public PhoneType getPhoneAddress() {
                    return phoneAddress;
                }

                /**
                 * Sets the value of the phoneAddress property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link PhoneType }
                 *     
                 */
                public void setPhoneAddress(PhoneType value) {
                    this.phoneAddress = value;
                }

                /**
                 * Gets the value of the postalAddress property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link AddressType }
                 *     
                 */
                public AddressType getPostalAddress() {
                    return postalAddress;
                }

                /**
                 * Sets the value of the postalAddress property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link AddressType }
                 *     
                 */
                public void setPostalAddress(AddressType value) {
                    this.postalAddress = value;
                }

                /**
                 * Gets the value of the emailAddress property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link EmailType }
                 *     
                 */
                public EmailType getEmailAddress() {
                    return emailAddress;
                }

                /**
                 * Sets the value of the emailAddress property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link EmailType }
                 *     
                 */
                public void setEmailAddress(EmailType value) {
                    this.emailAddress = value;
                }

            }

        }

    }

}
