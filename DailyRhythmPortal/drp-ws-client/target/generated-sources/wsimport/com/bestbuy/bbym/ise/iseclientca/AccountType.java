
package com.bestbuy.bbym.ise.iseclientca;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AccountType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccountType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AcctType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Category" type="{http://webservices.bestbuy.com/ca/services/entity/v2}AccountCategoryType" minOccurs="0"/>
 *         &lt;element name="Number" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StatusCode" type="{http://webservices.bestbuy.com/ca/services/entity/v2}StatusCodeType" minOccurs="0"/>
 *         &lt;element name="Preference" type="{http://webservices.bestbuy.com/ca/services/entity/v2}CommunicationPreferenceType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="RoleList" type="{http://webservices.bestbuy.com/ca/services/entity/v2}RoleTypeList" minOccurs="0"/>
 *         &lt;element name="EffectiveBeginDate" type="{http://webservices.bestbuy.com/ca/services/entity/v2}TimePeriodType" minOccurs="0"/>
 *         &lt;element name="PaymentPreferenceList" type="{http://webservices.bestbuy.com/ca/services/entity/v2}PaymentPreferenceListType" minOccurs="0"/>
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
@XmlType(name = "AccountType", propOrder = {
    "acctType",
    "category",
    "number",
    "statusCode",
    "preference",
    "roleList",
    "effectiveBeginDate",
    "paymentPreferenceList"
})
public class AccountType {

    @XmlElement(name = "AcctType")
    protected String acctType;
    @XmlElement(name = "Category")
    protected AccountCategoryType category;
    @XmlElement(name = "Number")
    protected String number;
    @XmlElement(name = "StatusCode")
    protected String statusCode;
    @XmlElement(name = "Preference")
    protected List<CommunicationPreferenceType> preference;
    @XmlElement(name = "RoleList")
    protected RoleTypeList roleList;
    @XmlElement(name = "EffectiveBeginDate")
    protected TimePeriodType effectiveBeginDate;
    @XmlElement(name = "PaymentPreferenceList")
    protected PaymentPreferenceListType paymentPreferenceList;
    @XmlAttribute(name = "Id")
    protected String id;

    /**
     * Gets the value of the acctType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcctType() {
        return acctType;
    }

    /**
     * Sets the value of the acctType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcctType(String value) {
        this.acctType = value;
    }

    /**
     * Gets the value of the category property.
     * 
     * @return
     *     possible object is
     *     {@link AccountCategoryType }
     *     
     */
    public AccountCategoryType getCategory() {
        return category;
    }

    /**
     * Sets the value of the category property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountCategoryType }
     *     
     */
    public void setCategory(AccountCategoryType value) {
        this.category = value;
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
     * Gets the value of the preference property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the preference property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPreference().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CommunicationPreferenceType }
     * 
     * 
     */
    public List<CommunicationPreferenceType> getPreference() {
        if (preference == null) {
            preference = new ArrayList<CommunicationPreferenceType>();
        }
        return this.preference;
    }

    /**
     * Gets the value of the roleList property.
     * 
     * @return
     *     possible object is
     *     {@link RoleTypeList }
     *     
     */
    public RoleTypeList getRoleList() {
        return roleList;
    }

    /**
     * Sets the value of the roleList property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoleTypeList }
     *     
     */
    public void setRoleList(RoleTypeList value) {
        this.roleList = value;
    }

    /**
     * Gets the value of the effectiveBeginDate property.
     * 
     * @return
     *     possible object is
     *     {@link TimePeriodType }
     *     
     */
    public TimePeriodType getEffectiveBeginDate() {
        return effectiveBeginDate;
    }

    /**
     * Sets the value of the effectiveBeginDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimePeriodType }
     *     
     */
    public void setEffectiveBeginDate(TimePeriodType value) {
        this.effectiveBeginDate = value;
    }

    /**
     * Gets the value of the paymentPreferenceList property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentPreferenceListType }
     *     
     */
    public PaymentPreferenceListType getPaymentPreferenceList() {
        return paymentPreferenceList;
    }

    /**
     * Sets the value of the paymentPreferenceList property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentPreferenceListType }
     *     
     */
    public void setPaymentPreferenceList(PaymentPreferenceListType value) {
        this.paymentPreferenceList = value;
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

}
