
package com.bestbuy.bbym.ise.iseclientca;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SearchCustomerRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SearchCustomerRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://webservices.bestbuy.com/ca/services/entity/v2}BaseRequestType">
 *       &lt;sequence>
 *         &lt;element name="Configuration" type="{http://webservices.bestbuy.com/ca/services/entity/v2}CustomerSearchConfigType" minOccurs="0"/>
 *         &lt;element name="SearchParameters" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Customer" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Name" type="{http://webservices.bestbuy.com/ca/services/entity/v2}NameType" minOccurs="0"/>
 *                             &lt;element name="Phone" type="{http://webservices.bestbuy.com/ca/services/entity/v2}PhoneType" minOccurs="0"/>
 *                             &lt;element name="EmailAddress" type="{http://webservices.bestbuy.com/ca/services/entity/v2}EmailType" minOccurs="0"/>
 *                             &lt;element name="PostalAddress" type="{http://webservices.bestbuy.com/ca/services/entity/v2}AddressType" minOccurs="0"/>
 *                             &lt;element name="Alias" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="SystemId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="EntityId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="Account" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="AccountNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="AccountCategory" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="StoreNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Transaction" type="{http://webservices.bestbuy.com/ca/services/entity/v2}CustomerSearchTransactionType" minOccurs="0"/>
 *                   &lt;element name="PartyIdentity" type="{http://webservices.bestbuy.com/ca/services/entity/v2}IdentityType" minOccurs="0"/>
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
@XmlType(name = "SearchCustomerRequestType", propOrder = {
    "configuration",
    "searchParameters"
})
public class SearchCustomerRequestType
    extends BaseRequestType
{

    @XmlElement(name = "Configuration")
    protected CustomerSearchConfigType configuration;
    @XmlElement(name = "SearchParameters")
    protected SearchCustomerRequestType.SearchParameters searchParameters;

    /**
     * Gets the value of the configuration property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerSearchConfigType }
     *     
     */
    public CustomerSearchConfigType getConfiguration() {
        return configuration;
    }

    /**
     * Sets the value of the configuration property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerSearchConfigType }
     *     
     */
    public void setConfiguration(CustomerSearchConfigType value) {
        this.configuration = value;
    }

    /**
     * Gets the value of the searchParameters property.
     * 
     * @return
     *     possible object is
     *     {@link SearchCustomerRequestType.SearchParameters }
     *     
     */
    public SearchCustomerRequestType.SearchParameters getSearchParameters() {
        return searchParameters;
    }

    /**
     * Sets the value of the searchParameters property.
     * 
     * @param value
     *     allowed object is
     *     {@link SearchCustomerRequestType.SearchParameters }
     *     
     */
    public void setSearchParameters(SearchCustomerRequestType.SearchParameters value) {
        this.searchParameters = value;
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
     *         &lt;element name="Customer" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Name" type="{http://webservices.bestbuy.com/ca/services/entity/v2}NameType" minOccurs="0"/>
     *                   &lt;element name="Phone" type="{http://webservices.bestbuy.com/ca/services/entity/v2}PhoneType" minOccurs="0"/>
     *                   &lt;element name="EmailAddress" type="{http://webservices.bestbuy.com/ca/services/entity/v2}EmailType" minOccurs="0"/>
     *                   &lt;element name="PostalAddress" type="{http://webservices.bestbuy.com/ca/services/entity/v2}AddressType" minOccurs="0"/>
     *                   &lt;element name="Alias" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="SystemId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="EntityId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
     *         &lt;element name="Account" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="AccountNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="AccountCategory" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="StoreNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Transaction" type="{http://webservices.bestbuy.com/ca/services/entity/v2}CustomerSearchTransactionType" minOccurs="0"/>
     *         &lt;element name="PartyIdentity" type="{http://webservices.bestbuy.com/ca/services/entity/v2}IdentityType" minOccurs="0"/>
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
        "customer",
        "account",
        "storeNumber",
        "transaction",
        "partyIdentity"
    })
    public static class SearchParameters {

        @XmlElement(name = "Customer")
        protected SearchCustomerRequestType.SearchParameters.Customer customer;
        @XmlElement(name = "Account")
        protected SearchCustomerRequestType.SearchParameters.Account account;
        @XmlElement(name = "StoreNumber")
        protected String storeNumber;
        @XmlElement(name = "Transaction")
        protected CustomerSearchTransactionType transaction;
        @XmlElement(name = "PartyIdentity")
        protected IdentityType partyIdentity;

        /**
         * Gets the value of the customer property.
         * 
         * @return
         *     possible object is
         *     {@link SearchCustomerRequestType.SearchParameters.Customer }
         *     
         */
        public SearchCustomerRequestType.SearchParameters.Customer getCustomer() {
            return customer;
        }

        /**
         * Sets the value of the customer property.
         * 
         * @param value
         *     allowed object is
         *     {@link SearchCustomerRequestType.SearchParameters.Customer }
         *     
         */
        public void setCustomer(SearchCustomerRequestType.SearchParameters.Customer value) {
            this.customer = value;
        }

        /**
         * Gets the value of the account property.
         * 
         * @return
         *     possible object is
         *     {@link SearchCustomerRequestType.SearchParameters.Account }
         *     
         */
        public SearchCustomerRequestType.SearchParameters.Account getAccount() {
            return account;
        }

        /**
         * Sets the value of the account property.
         * 
         * @param value
         *     allowed object is
         *     {@link SearchCustomerRequestType.SearchParameters.Account }
         *     
         */
        public void setAccount(SearchCustomerRequestType.SearchParameters.Account value) {
            this.account = value;
        }

        /**
         * Gets the value of the storeNumber property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStoreNumber() {
            return storeNumber;
        }

        /**
         * Sets the value of the storeNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStoreNumber(String value) {
            this.storeNumber = value;
        }

        /**
         * Gets the value of the transaction property.
         * 
         * @return
         *     possible object is
         *     {@link CustomerSearchTransactionType }
         *     
         */
        public CustomerSearchTransactionType getTransaction() {
            return transaction;
        }

        /**
         * Sets the value of the transaction property.
         * 
         * @param value
         *     allowed object is
         *     {@link CustomerSearchTransactionType }
         *     
         */
        public void setTransaction(CustomerSearchTransactionType value) {
            this.transaction = value;
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
         *         &lt;element name="AccountNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="AccountCategory" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
            "accountNumber",
            "accountCategory"
        })
        public static class Account {

            @XmlElement(name = "AccountNumber")
            protected String accountNumber;
            @XmlElement(name = "AccountCategory")
            protected String accountCategory;

            /**
             * Gets the value of the accountNumber property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAccountNumber() {
                return accountNumber;
            }

            /**
             * Sets the value of the accountNumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAccountNumber(String value) {
                this.accountNumber = value;
            }

            /**
             * Gets the value of the accountCategory property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAccountCategory() {
                return accountCategory;
            }

            /**
             * Sets the value of the accountCategory property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAccountCategory(String value) {
                this.accountCategory = value;
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
         *         &lt;element name="Name" type="{http://webservices.bestbuy.com/ca/services/entity/v2}NameType" minOccurs="0"/>
         *         &lt;element name="Phone" type="{http://webservices.bestbuy.com/ca/services/entity/v2}PhoneType" minOccurs="0"/>
         *         &lt;element name="EmailAddress" type="{http://webservices.bestbuy.com/ca/services/entity/v2}EmailType" minOccurs="0"/>
         *         &lt;element name="PostalAddress" type="{http://webservices.bestbuy.com/ca/services/entity/v2}AddressType" minOccurs="0"/>
         *         &lt;element name="Alias" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="SystemId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="EntityId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
            "name",
            "phone",
            "emailAddress",
            "postalAddress",
            "alias"
        })
        public static class Customer {

            @XmlElement(name = "Name")
            protected NameType name;
            @XmlElement(name = "Phone")
            protected PhoneType phone;
            @XmlElement(name = "EmailAddress")
            protected EmailType emailAddress;
            @XmlElement(name = "PostalAddress")
            protected AddressType postalAddress;
            @XmlElement(name = "Alias")
            protected SearchCustomerRequestType.SearchParameters.Customer.Alias alias;

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
             * Gets the value of the phone property.
             * 
             * @return
             *     possible object is
             *     {@link PhoneType }
             *     
             */
            public PhoneType getPhone() {
                return phone;
            }

            /**
             * Sets the value of the phone property.
             * 
             * @param value
             *     allowed object is
             *     {@link PhoneType }
             *     
             */
            public void setPhone(PhoneType value) {
                this.phone = value;
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
             * Gets the value of the alias property.
             * 
             * @return
             *     possible object is
             *     {@link SearchCustomerRequestType.SearchParameters.Customer.Alias }
             *     
             */
            public SearchCustomerRequestType.SearchParameters.Customer.Alias getAlias() {
                return alias;
            }

            /**
             * Sets the value of the alias property.
             * 
             * @param value
             *     allowed object is
             *     {@link SearchCustomerRequestType.SearchParameters.Customer.Alias }
             *     
             */
            public void setAlias(SearchCustomerRequestType.SearchParameters.Customer.Alias value) {
                this.alias = value;
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
             *         &lt;element name="SystemId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="EntityId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
                "systemId",
                "entityId"
            })
            public static class Alias {

                @XmlElement(name = "SystemId")
                protected String systemId;
                @XmlElement(name = "EntityId")
                protected String entityId;

                /**
                 * Gets the value of the systemId property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getSystemId() {
                    return systemId;
                }

                /**
                 * Sets the value of the systemId property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setSystemId(String value) {
                    this.systemId = value;
                }

                /**
                 * Gets the value of the entityId property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getEntityId() {
                    return entityId;
                }

                /**
                 * Sets the value of the entityId property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setEntityId(String value) {
                    this.entityId = value;
                }

            }

        }

    }

}
