
package com.bestbuy.bbym.ise.iseclientca;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CommunicationPreferenceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CommunicationPreferenceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DirtyStatus" type="{http://webservices.bestbuy.com/ca/services/entity/v2}DirtyStatusType" minOccurs="0"/>
 *         &lt;element name="StatusCode" type="{http://webservices.bestbuy.com/ca/services/entity/v2}StatusCodeType" minOccurs="0"/>
 *         &lt;element name="PurposeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CommunicationAddress" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice>
 *                   &lt;element name="PhoneAddress" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;extension base="{http://webservices.bestbuy.com/ca/services/entity/v2}PhoneType">
 *                         &lt;/extension>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="PostalAddress" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;extension base="{http://webservices.bestbuy.com/ca/services/entity/v2}AddressType">
 *                         &lt;/extension>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="EmailAddress" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;extension base="{http://webservices.bestbuy.com/ca/services/entity/v2}EmailType">
 *                         &lt;/extension>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
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
@XmlType(name = "CommunicationPreferenceType", propOrder = {
    "dirtyStatus",
    "statusCode",
    "purposeCode",
    "communicationAddress"
})
public class CommunicationPreferenceType {

    @XmlElement(name = "DirtyStatus")
    protected DirtyStatusType dirtyStatus;
    @XmlElement(name = "StatusCode")
    protected String statusCode;
    @XmlElement(name = "PurposeCode")
    protected String purposeCode;
    @XmlElement(name = "CommunicationAddress")
    protected CommunicationPreferenceType.CommunicationAddress communicationAddress;

    /**
     * Gets the value of the dirtyStatus property.
     * 
     * @return
     *     possible object is
     *     {@link DirtyStatusType }
     *     
     */
    public DirtyStatusType getDirtyStatus() {
        return dirtyStatus;
    }

    /**
     * Sets the value of the dirtyStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link DirtyStatusType }
     *     
     */
    public void setDirtyStatus(DirtyStatusType value) {
        this.dirtyStatus = value;
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
     * Gets the value of the purposeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPurposeCode() {
        return purposeCode;
    }

    /**
     * Sets the value of the purposeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPurposeCode(String value) {
        this.purposeCode = value;
    }

    /**
     * Gets the value of the communicationAddress property.
     * 
     * @return
     *     possible object is
     *     {@link CommunicationPreferenceType.CommunicationAddress }
     *     
     */
    public CommunicationPreferenceType.CommunicationAddress getCommunicationAddress() {
        return communicationAddress;
    }

    /**
     * Sets the value of the communicationAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommunicationPreferenceType.CommunicationAddress }
     *     
     */
    public void setCommunicationAddress(CommunicationPreferenceType.CommunicationAddress value) {
        this.communicationAddress = value;
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
     *         &lt;element name="PhoneAddress" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;extension base="{http://webservices.bestbuy.com/ca/services/entity/v2}PhoneType">
     *               &lt;/extension>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="PostalAddress" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;extension base="{http://webservices.bestbuy.com/ca/services/entity/v2}AddressType">
     *               &lt;/extension>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="EmailAddress" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;extension base="{http://webservices.bestbuy.com/ca/services/entity/v2}EmailType">
     *               &lt;/extension>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
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
        protected CommunicationPreferenceType.CommunicationAddress.PhoneAddress phoneAddress;
        @XmlElement(name = "PostalAddress")
        protected CommunicationPreferenceType.CommunicationAddress.PostalAddress postalAddress;
        @XmlElement(name = "EmailAddress")
        protected CommunicationPreferenceType.CommunicationAddress.EmailAddress emailAddress;

        /**
         * Gets the value of the phoneAddress property.
         * 
         * @return
         *     possible object is
         *     {@link CommunicationPreferenceType.CommunicationAddress.PhoneAddress }
         *     
         */
        public CommunicationPreferenceType.CommunicationAddress.PhoneAddress getPhoneAddress() {
            return phoneAddress;
        }

        /**
         * Sets the value of the phoneAddress property.
         * 
         * @param value
         *     allowed object is
         *     {@link CommunicationPreferenceType.CommunicationAddress.PhoneAddress }
         *     
         */
        public void setPhoneAddress(CommunicationPreferenceType.CommunicationAddress.PhoneAddress value) {
            this.phoneAddress = value;
        }

        /**
         * Gets the value of the postalAddress property.
         * 
         * @return
         *     possible object is
         *     {@link CommunicationPreferenceType.CommunicationAddress.PostalAddress }
         *     
         */
        public CommunicationPreferenceType.CommunicationAddress.PostalAddress getPostalAddress() {
            return postalAddress;
        }

        /**
         * Sets the value of the postalAddress property.
         * 
         * @param value
         *     allowed object is
         *     {@link CommunicationPreferenceType.CommunicationAddress.PostalAddress }
         *     
         */
        public void setPostalAddress(CommunicationPreferenceType.CommunicationAddress.PostalAddress value) {
            this.postalAddress = value;
        }

        /**
         * Gets the value of the emailAddress property.
         * 
         * @return
         *     possible object is
         *     {@link CommunicationPreferenceType.CommunicationAddress.EmailAddress }
         *     
         */
        public CommunicationPreferenceType.CommunicationAddress.EmailAddress getEmailAddress() {
            return emailAddress;
        }

        /**
         * Sets the value of the emailAddress property.
         * 
         * @param value
         *     allowed object is
         *     {@link CommunicationPreferenceType.CommunicationAddress.EmailAddress }
         *     
         */
        public void setEmailAddress(CommunicationPreferenceType.CommunicationAddress.EmailAddress value) {
            this.emailAddress = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;extension base="{http://webservices.bestbuy.com/ca/services/entity/v2}EmailType">
         *     &lt;/extension>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class EmailAddress
            extends EmailType
        {


        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;extension base="{http://webservices.bestbuy.com/ca/services/entity/v2}PhoneType">
         *     &lt;/extension>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class PhoneAddress
            extends PhoneType
        {


        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;extension base="{http://webservices.bestbuy.com/ca/services/entity/v2}AddressType">
         *     &lt;/extension>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class PostalAddress
            extends AddressType
        {


        }

    }

}
