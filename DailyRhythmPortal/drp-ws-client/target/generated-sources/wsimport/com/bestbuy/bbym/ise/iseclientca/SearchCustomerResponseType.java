
package com.bestbuy.bbym.ise.iseclientca;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SearchCustomerResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SearchCustomerResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://webservices.bestbuy.com/ca/services/entity/v2}BaseResponseType">
 *       &lt;sequence>
 *         &lt;element name="Customer" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="CustomerId" type="{http://webservices.bestbuy.com/ca/services/entity/v2}IdType" minOccurs="0"/>
 *                   &lt;element name="Name" type="{http://webservices.bestbuy.com/ca/services/entity/v2}NameType" minOccurs="0"/>
 *                   &lt;element name="PhoneAddress" type="{http://webservices.bestbuy.com/ca/services/entity/v2}PhoneType" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="EmailAddress" type="{http://webservices.bestbuy.com/ca/services/entity/v2}EmailType" minOccurs="0"/>
 *                   &lt;element name="PostalAddress" type="{http://webservices.bestbuy.com/ca/services/entity/v2}AddressType" minOccurs="0"/>
 *                   &lt;element name="Account" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="AccountNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="AccountCategory" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="Status" type="{http://webservices.bestbuy.com/ca/services/entity/v2}StatusCodeType" minOccurs="0"/>
 *                             &lt;element name="AccountId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="Alias" maxOccurs="unbounded" minOccurs="0">
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
 *                   &lt;element name="IsCustomerAccount" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                   &lt;element name="IsDynamicParty" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
@XmlType(name = "SearchCustomerResponseType", propOrder = {
    "customer"
})
public class SearchCustomerResponseType
    extends BaseResponseType
{

    @XmlElement(name = "Customer")
    protected List<SearchCustomerResponseType.Customer> customer;

    /**
     * Gets the value of the customer property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the customer property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCustomer().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SearchCustomerResponseType.Customer }
     * 
     * 
     */
    public List<SearchCustomerResponseType.Customer> getCustomer() {
        if (customer == null) {
            customer = new ArrayList<SearchCustomerResponseType.Customer>();
        }
        return this.customer;
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
     *         &lt;element name="CustomerId" type="{http://webservices.bestbuy.com/ca/services/entity/v2}IdType" minOccurs="0"/>
     *         &lt;element name="Name" type="{http://webservices.bestbuy.com/ca/services/entity/v2}NameType" minOccurs="0"/>
     *         &lt;element name="PhoneAddress" type="{http://webservices.bestbuy.com/ca/services/entity/v2}PhoneType" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="EmailAddress" type="{http://webservices.bestbuy.com/ca/services/entity/v2}EmailType" minOccurs="0"/>
     *         &lt;element name="PostalAddress" type="{http://webservices.bestbuy.com/ca/services/entity/v2}AddressType" minOccurs="0"/>
     *         &lt;element name="Account" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="AccountNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="AccountCategory" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="Status" type="{http://webservices.bestbuy.com/ca/services/entity/v2}StatusCodeType" minOccurs="0"/>
     *                   &lt;element name="AccountId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="Alias" maxOccurs="unbounded" minOccurs="0">
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
     *         &lt;element name="IsCustomerAccount" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *         &lt;element name="IsDynamicParty" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
        "customerId",
        "name",
        "phoneAddress",
        "emailAddress",
        "postalAddress",
        "account",
        "alias",
        "isCustomerAccount",
        "isDynamicParty"
    })
    public static class Customer {

        @XmlElement(name = "CustomerId")
        protected String customerId;
        @XmlElement(name = "Name")
        protected NameType name;
        @XmlElement(name = "PhoneAddress")
        protected List<PhoneType> phoneAddress;
        @XmlElement(name = "EmailAddress")
        protected EmailType emailAddress;
        @XmlElement(name = "PostalAddress")
        protected AddressType postalAddress;
        @XmlElement(name = "Account")
        protected List<SearchCustomerResponseType.Customer.Account> account;
        @XmlElement(name = "Alias")
        protected List<SearchCustomerResponseType.Customer.Alias> alias;
        @XmlElement(name = "IsCustomerAccount")
        protected Boolean isCustomerAccount;
        @XmlElement(name = "IsDynamicParty")
        protected Boolean isDynamicParty;

        /**
         * Gets the value of the customerId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCustomerId() {
            return customerId;
        }

        /**
         * Sets the value of the customerId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCustomerId(String value) {
            this.customerId = value;
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
         * Gets the value of the phoneAddress property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the phoneAddress property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPhoneAddress().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link PhoneType }
         * 
         * 
         */
        public List<PhoneType> getPhoneAddress() {
            if (phoneAddress == null) {
                phoneAddress = new ArrayList<PhoneType>();
            }
            return this.phoneAddress;
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
         * Gets the value of the account property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the account property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAccount().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link SearchCustomerResponseType.Customer.Account }
         * 
         * 
         */
        public List<SearchCustomerResponseType.Customer.Account> getAccount() {
            if (account == null) {
                account = new ArrayList<SearchCustomerResponseType.Customer.Account>();
            }
            return this.account;
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
         * {@link SearchCustomerResponseType.Customer.Alias }
         * 
         * 
         */
        public List<SearchCustomerResponseType.Customer.Alias> getAlias() {
            if (alias == null) {
                alias = new ArrayList<SearchCustomerResponseType.Customer.Alias>();
            }
            return this.alias;
        }

        /**
         * Gets the value of the isCustomerAccount property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isIsCustomerAccount() {
            return isCustomerAccount;
        }

        /**
         * Sets the value of the isCustomerAccount property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setIsCustomerAccount(Boolean value) {
            this.isCustomerAccount = value;
        }

        /**
         * Gets the value of the isDynamicParty property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isIsDynamicParty() {
            return isDynamicParty;
        }

        /**
         * Sets the value of the isDynamicParty property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setIsDynamicParty(Boolean value) {
            this.isDynamicParty = value;
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
         *         &lt;element name="Status" type="{http://webservices.bestbuy.com/ca/services/entity/v2}StatusCodeType" minOccurs="0"/>
         *         &lt;element name="AccountId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
            "accountCategory",
            "status",
            "accountId"
        })
        public static class Account {

            @XmlElement(name = "AccountNumber")
            protected String accountNumber;
            @XmlElement(name = "AccountCategory")
            protected String accountCategory;
            @XmlElement(name = "Status")
            protected String status;
            @XmlElement(name = "AccountId")
            protected String accountId;

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

            /**
             * Gets the value of the status property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getStatus() {
                return status;
            }

            /**
             * Sets the value of the status property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setStatus(String value) {
                this.status = value;
            }

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
