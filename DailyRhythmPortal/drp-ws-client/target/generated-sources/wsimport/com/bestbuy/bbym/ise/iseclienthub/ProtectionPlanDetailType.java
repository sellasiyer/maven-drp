
package com.bestbuy.bbym.ise.iseclienthub;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ProtectionPlanDetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProtectionPlanDetailType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Scheme/V4}ProtectionPlanType">
 *       &lt;sequence>
 *         &lt;element name="ContractDetails" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Scheme/V4}ContractDetailsType" minOccurs="0"/>
 *         &lt;element name="ActivityLog" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Scheme/V4}ArrayOfContractActivityType" minOccurs="0"/>
 *         &lt;element name="AuthorizedUser" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Scheme/V4}AuthorizedUserType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Purchaser" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Common/V4}PersonType" minOccurs="0"/>
 *         &lt;element name="OwnerAccount" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Scheme/V4}AccountType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ContractNotes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Transaction" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Scheme/V4}TransactionType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="PurchaseLocation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MonthlyPayment" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="StatusReasonCode" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Common/V4}CodeDescriptionType" minOccurs="0"/>
 *         &lt;element name="CancelRequested" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProtectionPlanDetailType", propOrder = {
    "contractDetails",
    "activityLog",
    "authorizedUser",
    "purchaser",
    "ownerAccount",
    "contractNotes",
    "transaction",
    "purchaseLocation",
    "monthlyPayment",
    "statusReasonCode",
    "cancelRequested"
})
public class ProtectionPlanDetailType
    extends ProtectionPlanType
{

    @XmlElement(name = "ContractDetails")
    protected ContractDetailsType contractDetails;
    @XmlElement(name = "ActivityLog")
    protected ArrayOfContractActivityType activityLog;
    @XmlElement(name = "AuthorizedUser")
    protected List<AuthorizedUserType> authorizedUser;
    @XmlElement(name = "Purchaser")
    protected PersonType purchaser;
    @XmlElement(name = "OwnerAccount")
    protected List<AccountType> ownerAccount;
    @XmlElement(name = "ContractNotes")
    protected String contractNotes;
    @XmlElement(name = "Transaction")
    protected List<TransactionType> transaction;
    @XmlElement(name = "PurchaseLocation")
    protected String purchaseLocation;
    @XmlElement(name = "MonthlyPayment")
    protected BigDecimal monthlyPayment;
    @XmlElement(name = "StatusReasonCode")
    protected CodeDescriptionType statusReasonCode;
    @XmlElement(name = "CancelRequested")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar cancelRequested;

    /**
     * Gets the value of the contractDetails property.
     * 
     * @return
     *     possible object is
     *     {@link ContractDetailsType }
     *     
     */
    public ContractDetailsType getContractDetails() {
        return contractDetails;
    }

    /**
     * Sets the value of the contractDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContractDetailsType }
     *     
     */
    public void setContractDetails(ContractDetailsType value) {
        this.contractDetails = value;
    }

    /**
     * Gets the value of the activityLog property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfContractActivityType }
     *     
     */
    public ArrayOfContractActivityType getActivityLog() {
        return activityLog;
    }

    /**
     * Sets the value of the activityLog property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfContractActivityType }
     *     
     */
    public void setActivityLog(ArrayOfContractActivityType value) {
        this.activityLog = value;
    }

    /**
     * Gets the value of the authorizedUser property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the authorizedUser property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAuthorizedUser().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AuthorizedUserType }
     * 
     * 
     */
    public List<AuthorizedUserType> getAuthorizedUser() {
        if (authorizedUser == null) {
            authorizedUser = new ArrayList<AuthorizedUserType>();
        }
        return this.authorizedUser;
    }

    /**
     * Gets the value of the purchaser property.
     * 
     * @return
     *     possible object is
     *     {@link PersonType }
     *     
     */
    public PersonType getPurchaser() {
        return purchaser;
    }

    /**
     * Sets the value of the purchaser property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonType }
     *     
     */
    public void setPurchaser(PersonType value) {
        this.purchaser = value;
    }

    /**
     * Gets the value of the ownerAccount property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ownerAccount property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOwnerAccount().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AccountType }
     * 
     * 
     */
    public List<AccountType> getOwnerAccount() {
        if (ownerAccount == null) {
            ownerAccount = new ArrayList<AccountType>();
        }
        return this.ownerAccount;
    }

    /**
     * Gets the value of the contractNotes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractNotes() {
        return contractNotes;
    }

    /**
     * Sets the value of the contractNotes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractNotes(String value) {
        this.contractNotes = value;
    }

    /**
     * Gets the value of the transaction property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the transaction property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTransaction().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TransactionType }
     * 
     * 
     */
    public List<TransactionType> getTransaction() {
        if (transaction == null) {
            transaction = new ArrayList<TransactionType>();
        }
        return this.transaction;
    }

    /**
     * Gets the value of the purchaseLocation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPurchaseLocation() {
        return purchaseLocation;
    }

    /**
     * Sets the value of the purchaseLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPurchaseLocation(String value) {
        this.purchaseLocation = value;
    }

    /**
     * Gets the value of the monthlyPayment property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMonthlyPayment() {
        return monthlyPayment;
    }

    /**
     * Sets the value of the monthlyPayment property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMonthlyPayment(BigDecimal value) {
        this.monthlyPayment = value;
    }

    /**
     * Gets the value of the statusReasonCode property.
     * 
     * @return
     *     possible object is
     *     {@link CodeDescriptionType }
     *     
     */
    public CodeDescriptionType getStatusReasonCode() {
        return statusReasonCode;
    }

    /**
     * Sets the value of the statusReasonCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeDescriptionType }
     *     
     */
    public void setStatusReasonCode(CodeDescriptionType value) {
        this.statusReasonCode = value;
    }

    /**
     * Gets the value of the cancelRequested property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCancelRequested() {
        return cancelRequested;
    }

    /**
     * Sets the value of the cancelRequested property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCancelRequested(XMLGregorianCalendar value) {
        this.cancelRequested = value;
    }

}
