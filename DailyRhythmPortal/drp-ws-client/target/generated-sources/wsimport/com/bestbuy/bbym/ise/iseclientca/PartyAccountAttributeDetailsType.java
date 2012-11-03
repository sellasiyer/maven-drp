
package com.bestbuy.bbym.ise.iseclientca;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PartyAccountAttributeDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PartyAccountAttributeDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Party" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Alias" type="{http://webservices.bestbuy.com/ca/services/entity/v2}AliasType" minOccurs="0"/>
 *                   &lt;element name="SubAccountWithAttributesList" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="SubAccount" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="Category" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="Number" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="SubAccountAttributeList" type="{http://webservices.bestbuy.com/ca/services/entity/v2}SubAccountAttributeListType" minOccurs="0"/>
 *                                       &lt;element name="AllAttributesCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                     &lt;attribute name="AccountId" type="{http://webservices.bestbuy.com/ca/services/entity/v2}IdType" />
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
 *                 &lt;attribute name="PartyId" type="{http://webservices.bestbuy.com/ca/services/entity/v2}IdType" />
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
@XmlType(name = "PartyAccountAttributeDetailsType", propOrder = {
    "party"
})
public class PartyAccountAttributeDetailsType {

    @XmlElement(name = "Party")
    protected PartyAccountAttributeDetailsType.Party party;

    /**
     * Gets the value of the party property.
     * 
     * @return
     *     possible object is
     *     {@link PartyAccountAttributeDetailsType.Party }
     *     
     */
    public PartyAccountAttributeDetailsType.Party getParty() {
        return party;
    }

    /**
     * Sets the value of the party property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyAccountAttributeDetailsType.Party }
     *     
     */
    public void setParty(PartyAccountAttributeDetailsType.Party value) {
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
     *         &lt;element name="Alias" type="{http://webservices.bestbuy.com/ca/services/entity/v2}AliasType" minOccurs="0"/>
     *         &lt;element name="SubAccountWithAttributesList" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="SubAccount" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="Category" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="Number" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="SubAccountAttributeList" type="{http://webservices.bestbuy.com/ca/services/entity/v2}SubAccountAttributeListType" minOccurs="0"/>
     *                             &lt;element name="AllAttributesCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                           &lt;/sequence>
     *                           &lt;attribute name="AccountId" type="{http://webservices.bestbuy.com/ca/services/entity/v2}IdType" />
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
     *       &lt;attribute name="PartyId" type="{http://webservices.bestbuy.com/ca/services/entity/v2}IdType" />
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
        "subAccountWithAttributesList"
    })
    public static class Party {

        @XmlElement(name = "Alias")
        protected AliasType alias;
        @XmlElement(name = "SubAccountWithAttributesList")
        protected PartyAccountAttributeDetailsType.Party.SubAccountWithAttributesList subAccountWithAttributesList;
        @XmlAttribute(name = "PartyId")
        protected String partyId;

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
         * Gets the value of the subAccountWithAttributesList property.
         * 
         * @return
         *     possible object is
         *     {@link PartyAccountAttributeDetailsType.Party.SubAccountWithAttributesList }
         *     
         */
        public PartyAccountAttributeDetailsType.Party.SubAccountWithAttributesList getSubAccountWithAttributesList() {
            return subAccountWithAttributesList;
        }

        /**
         * Sets the value of the subAccountWithAttributesList property.
         * 
         * @param value
         *     allowed object is
         *     {@link PartyAccountAttributeDetailsType.Party.SubAccountWithAttributesList }
         *     
         */
        public void setSubAccountWithAttributesList(PartyAccountAttributeDetailsType.Party.SubAccountWithAttributesList value) {
            this.subAccountWithAttributesList = value;
        }

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
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="SubAccount" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="Category" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="Number" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="SubAccountAttributeList" type="{http://webservices.bestbuy.com/ca/services/entity/v2}SubAccountAttributeListType" minOccurs="0"/>
         *                   &lt;element name="AllAttributesCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                 &lt;/sequence>
         *                 &lt;attribute name="AccountId" type="{http://webservices.bestbuy.com/ca/services/entity/v2}IdType" />
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
            "subAccount"
        })
        public static class SubAccountWithAttributesList {

            @XmlElement(name = "SubAccount")
            protected List<PartyAccountAttributeDetailsType.Party.SubAccountWithAttributesList.SubAccount> subAccount;

            /**
             * Gets the value of the subAccount property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the subAccount property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getSubAccount().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link PartyAccountAttributeDetailsType.Party.SubAccountWithAttributesList.SubAccount }
             * 
             * 
             */
            public List<PartyAccountAttributeDetailsType.Party.SubAccountWithAttributesList.SubAccount> getSubAccount() {
                if (subAccount == null) {
                    subAccount = new ArrayList<PartyAccountAttributeDetailsType.Party.SubAccountWithAttributesList.SubAccount>();
                }
                return this.subAccount;
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
             *         &lt;element name="Category" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="Number" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="SubAccountAttributeList" type="{http://webservices.bestbuy.com/ca/services/entity/v2}SubAccountAttributeListType" minOccurs="0"/>
             *         &lt;element name="AllAttributesCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *       &lt;/sequence>
             *       &lt;attribute name="AccountId" type="{http://webservices.bestbuy.com/ca/services/entity/v2}IdType" />
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "category",
                "type",
                "number",
                "subAccountAttributeList",
                "allAttributesCode"
            })
            public static class SubAccount {

                @XmlElement(name = "Category")
                protected String category;
                @XmlElement(name = "Type")
                protected String type;
                @XmlElement(name = "Number")
                protected String number;
                @XmlElement(name = "SubAccountAttributeList")
                protected SubAccountAttributeListType subAccountAttributeList;
                @XmlElement(name = "AllAttributesCode")
                protected String allAttributesCode;
                @XmlAttribute(name = "AccountId")
                protected String accountId;

                /**
                 * Gets the value of the category property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getCategory() {
                    return category;
                }

                /**
                 * Sets the value of the category property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setCategory(String value) {
                    this.category = value;
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
                 * Gets the value of the number property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getNumber() {
                    return number;
                }

                /**
                 * Sets the value of the number property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setNumber(String value) {
                    this.number = value;
                }

                /**
                 * Gets the value of the subAccountAttributeList property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link SubAccountAttributeListType }
                 *     
                 */
                public SubAccountAttributeListType getSubAccountAttributeList() {
                    return subAccountAttributeList;
                }

                /**
                 * Sets the value of the subAccountAttributeList property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link SubAccountAttributeListType }
                 *     
                 */
                public void setSubAccountAttributeList(SubAccountAttributeListType value) {
                    this.subAccountAttributeList = value;
                }

                /**
                 * Gets the value of the allAttributesCode property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAllAttributesCode() {
                    return allAttributesCode;
                }

                /**
                 * Sets the value of the allAttributesCode property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAllAttributesCode(String value) {
                    this.allAttributesCode = value;
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

        }

    }

}
