
package com.bestbuy.bbym.ise.iseclientca;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ManageAccountResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ManageAccountResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://webservices.bestbuy.com/ca/services/entity/v2}BaseResponseType">
 *       &lt;sequence>
 *         &lt;element name="Party" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="PartyId" type="{http://webservices.bestbuy.com/ca/services/entity/v2}IdType" minOccurs="0"/>
 *                   &lt;element name="Type" type="{http://webservices.bestbuy.com/ca/services/entity/v2}PartyTypeCodeType" minOccurs="0"/>
 *                   &lt;element name="StatusCode" type="{http://webservices.bestbuy.com/ca/services/entity/v2}StatusCodeType" minOccurs="0"/>
 *                   &lt;element name="Alias" type="{http://webservices.bestbuy.com/ca/services/entity/v2}AliasType" minOccurs="0"/>
 *                   &lt;element name="Account" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="AccountId" type="{http://webservices.bestbuy.com/ca/services/entity/v2}IdType" minOccurs="0"/>
 *                             &lt;element name="StatusCode" type="{http://webservices.bestbuy.com/ca/services/entity/v2}StatusCodeType" minOccurs="0"/>
 *                           &lt;/sequence>
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
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ManageAccountResponseType", propOrder = {
    "party"
})
public class ManageAccountResponseType
    extends BaseResponseType
{

    @XmlElement(name = "Party")
    protected ManageAccountResponseType.Party party;

    /**
     * Gets the value of the party property.
     * 
     * @return
     *     possible object is
     *     {@link ManageAccountResponseType.Party }
     *     
     */
    public ManageAccountResponseType.Party getParty() {
        return party;
    }

    /**
     * Sets the value of the party property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManageAccountResponseType.Party }
     *     
     */
    public void setParty(ManageAccountResponseType.Party value) {
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
     *         &lt;element name="Type" type="{http://webservices.bestbuy.com/ca/services/entity/v2}PartyTypeCodeType" minOccurs="0"/>
     *         &lt;element name="StatusCode" type="{http://webservices.bestbuy.com/ca/services/entity/v2}StatusCodeType" minOccurs="0"/>
     *         &lt;element name="Alias" type="{http://webservices.bestbuy.com/ca/services/entity/v2}AliasType" minOccurs="0"/>
     *         &lt;element name="Account" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="AccountId" type="{http://webservices.bestbuy.com/ca/services/entity/v2}IdType" minOccurs="0"/>
     *                   &lt;element name="StatusCode" type="{http://webservices.bestbuy.com/ca/services/entity/v2}StatusCodeType" minOccurs="0"/>
     *                 &lt;/sequence>
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
        "partyId",
        "type",
        "statusCode",
        "alias",
        "account"
    })
    public static class Party {

        @XmlElement(name = "PartyId")
        protected String partyId;
        @XmlElement(name = "Type")
        protected String type;
        @XmlElement(name = "StatusCode")
        protected String statusCode;
        @XmlElement(name = "Alias")
        protected AliasType alias;
        @XmlElement(name = "Account")
        protected ManageAccountResponseType.Party.Account account;

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
         * Gets the value of the type property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getType() {
            return type;
        }

        /**
         * Sets the value of the type property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setType(String value) {
            this.type = value;
        }

        /**
         * Gets the value of the statusCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStatusCode() {
            return statusCode;
        }

        /**
         * Sets the value of the statusCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStatusCode(String value) {
            this.statusCode = value;
        }

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
         * Gets the value of the account property.
         * 
         * @return
         *     possible object is
         *     {@link ManageAccountResponseType.Party.Account }
         *     
         */
        public ManageAccountResponseType.Party.Account getAccount() {
            return account;
        }

        /**
         * Sets the value of the account property.
         * 
         * @param value
         *     allowed object is
         *     {@link ManageAccountResponseType.Party.Account }
         *     
         */
        public void setAccount(ManageAccountResponseType.Party.Account value) {
            this.account = value;
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
         *         &lt;element name="AccountId" type="{http://webservices.bestbuy.com/ca/services/entity/v2}IdType" minOccurs="0"/>
         *         &lt;element name="StatusCode" type="{http://webservices.bestbuy.com/ca/services/entity/v2}StatusCodeType" minOccurs="0"/>
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
            "accountId",
            "statusCode"
        })
        public static class Account {

            @XmlElement(name = "AccountId")
            protected String accountId;
            @XmlElement(name = "StatusCode")
            protected String statusCode;

            /**
             * Gets the value of the accountId property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAccountId() {
                return accountId;
            }

            /**
             * Sets the value of the accountId property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAccountId(String value) {
                this.accountId = value;
            }

            /**
             * Gets the value of the statusCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getStatusCode() {
                return statusCode;
            }

            /**
             * Sets the value of the statusCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setStatusCode(String value) {
                this.statusCode = value;
            }

        }

    }

}
