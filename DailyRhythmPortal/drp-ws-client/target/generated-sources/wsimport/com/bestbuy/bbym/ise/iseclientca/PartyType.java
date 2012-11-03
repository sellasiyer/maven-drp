
package com.bestbuy.bbym.ise.iseclientca;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PartyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PartyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Type" type="{http://webservices.bestbuy.com/ca/services/entity/v2}PartyTypeCodeType" minOccurs="0"/>
 *         &lt;element name="StatusCode" type="{http://webservices.bestbuy.com/ca/services/entity/v2}StatusCodeType" minOccurs="0"/>
 *         &lt;element name="LanguagePreference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Registration" type="{http://webservices.bestbuy.com/ca/services/entity/v2}RegistrationType" minOccurs="0"/>
 *         &lt;element name="AliasList" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Alias" type="{http://webservices.bestbuy.com/ca/services/entity/v2}AliasType" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Name" type="{http://webservices.bestbuy.com/ca/services/entity/v2}NameType" minOccurs="0"/>
 *         &lt;element name="DOB" type="{http://webservices.bestbuy.com/ca/services/entity/v2}DateOfBirthType" minOccurs="0"/>
 *         &lt;element name="SubAccountList" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="SubAccount" type="{http://webservices.bestbuy.com/ca/services/entity/v2}AccountType" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="PaymentAccountList" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="PaymentAccount" type="{http://webservices.bestbuy.com/ca/services/entity/v2}PaymentAccountType" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "PartyType", propOrder = {
    "type",
    "statusCode",
    "languagePreference",
    "registration",
    "aliasList",
    "name",
    "dob",
    "subAccountList",
    "paymentAccountList"
})
public class PartyType {

    @XmlElement(name = "Type")
    protected String type;
    @XmlElement(name = "StatusCode")
    protected String statusCode;
    @XmlElement(name = "LanguagePreference")
    protected String languagePreference;
    @XmlElement(name = "Registration")
    protected RegistrationType registration;
    @XmlElement(name = "AliasList")
    protected PartyType.AliasList aliasList;
    @XmlElement(name = "Name")
    protected NameType name;
    @XmlElement(name = "DOB")
    protected DateOfBirthType dob;
    @XmlElement(name = "SubAccountList")
    protected PartyType.SubAccountList subAccountList;
    @XmlElement(name = "PaymentAccountList")
    protected PartyType.PaymentAccountList paymentAccountList;
    @XmlAttribute(name = "Id")
    protected String id;

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
     * Gets the value of the languagePreference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguagePreference() {
        return languagePreference;
    }

    /**
     * Sets the value of the languagePreference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguagePreference(String value) {
        this.languagePreference = value;
    }

    /**
     * Gets the value of the registration property.
     * 
     * @return
     *     possible object is
     *     {@link RegistrationType }
     *     
     */
    public RegistrationType getRegistration() {
        return registration;
    }

    /**
     * Sets the value of the registration property.
     * 
     * @param value
     *     allowed object is
     *     {@link RegistrationType }
     *     
     */
    public void setRegistration(RegistrationType value) {
        this.registration = value;
    }

    /**
     * Gets the value of the aliasList property.
     * 
     * @return
     *     possible object is
     *     {@link PartyType.AliasList }
     *     
     */
    public PartyType.AliasList getAliasList() {
        return aliasList;
    }

    /**
     * Sets the value of the aliasList property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType.AliasList }
     *     
     */
    public void setAliasList(PartyType.AliasList value) {
        this.aliasList = value;
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
     * Gets the value of the dob property.
     * 
     * @return
     *     possible object is
     *     {@link DateOfBirthType }
     *     
     */
    public DateOfBirthType getDOB() {
        return dob;
    }

    /**
     * Sets the value of the dob property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateOfBirthType }
     *     
     */
    public void setDOB(DateOfBirthType value) {
        this.dob = value;
    }

    /**
     * Gets the value of the subAccountList property.
     * 
     * @return
     *     possible object is
     *     {@link PartyType.SubAccountList }
     *     
     */
    public PartyType.SubAccountList getSubAccountList() {
        return subAccountList;
    }

    /**
     * Sets the value of the subAccountList property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType.SubAccountList }
     *     
     */
    public void setSubAccountList(PartyType.SubAccountList value) {
        this.subAccountList = value;
    }

    /**
     * Gets the value of the paymentAccountList property.
     * 
     * @return
     *     possible object is
     *     {@link PartyType.PaymentAccountList }
     *     
     */
    public PartyType.PaymentAccountList getPaymentAccountList() {
        return paymentAccountList;
    }

    /**
     * Sets the value of the paymentAccountList property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType.PaymentAccountList }
     *     
     */
    public void setPaymentAccountList(PartyType.PaymentAccountList value) {
        this.paymentAccountList = value;
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
        "alias"
    })
    public static class AliasList {

        @XmlElement(name = "Alias")
        protected List<AliasType> alias;

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
     *         &lt;element name="PaymentAccount" type="{http://webservices.bestbuy.com/ca/services/entity/v2}PaymentAccountType" maxOccurs="unbounded" minOccurs="0"/>
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
        "paymentAccount"
    })
    public static class PaymentAccountList {

        @XmlElement(name = "PaymentAccount")
        protected List<PaymentAccountType> paymentAccount;

        /**
         * Gets the value of the paymentAccount property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the paymentAccount property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPaymentAccount().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link PaymentAccountType }
         * 
         * 
         */
        public List<PaymentAccountType> getPaymentAccount() {
            if (paymentAccount == null) {
                paymentAccount = new ArrayList<PaymentAccountType>();
            }
            return this.paymentAccount;
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
     *         &lt;element name="SubAccount" type="{http://webservices.bestbuy.com/ca/services/entity/v2}AccountType" maxOccurs="unbounded" minOccurs="0"/>
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
    public static class SubAccountList {

        @XmlElement(name = "SubAccount")
        protected List<AccountType> subAccount;

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
         * {@link AccountType }
         * 
         * 
         */
        public List<AccountType> getSubAccount() {
            if (subAccount == null) {
                subAccount = new ArrayList<AccountType>();
            }
            return this.subAccount;
        }

    }

}
