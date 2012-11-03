
package com.bestbuy.tsh.sales.common.components.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.bestbuy.tsh.common.datatype.v1.TextType;
import com.bestbuy.tsh.common.datatype.v1.UserAreaType;
import com.bestbuy.tsh.common.metadata.components.v1.InternationalBusinessHierarchyType;


/**
 * RetrieveTransactionRequestType
 * 
 * <p>Java class for RetrieveTransactionRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RetrieveTransactionRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MetaData" type="{http://www.tsh.bestbuy.com/sales/common/components/v1}RequestMetaDataExtensionType" minOccurs="0"/>
 *         &lt;element name="InternationBusinessHierarcy" type="{http://www.tsh.bestbuy.com/common/metadata/components/v1}InternationalBusinessHierarchyType" minOccurs="0"/>
 *         &lt;element name="RetrieveConfig" type="{http://www.tsh.bestbuy.com/sales/common/components/v1}TransactionDetailConfigType"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}ECTransactionID" minOccurs="0"/>
 *         &lt;element name="TransactionKey" type="{http://www.tsh.bestbuy.com/sales/common/components/v1}TransactionKeyType" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/v1}UserArea" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute ref="{http://www.tsh.bestbuy.com/common/v1}languageCode"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RetrieveTransactionRequestType", propOrder = {
    "metaData",
    "internationBusinessHierarcy",
    "retrieveConfig",
    "ecTransactionID",
    "transactionKey",
    "userArea"
})
public class RetrieveTransactionRequestType {

    @XmlElement(name = "MetaData")
    protected RequestMetaDataExtensionType metaData;
    @XmlElement(name = "InternationBusinessHierarcy")
    protected InternationalBusinessHierarchyType internationBusinessHierarcy;
    @XmlElement(name = "RetrieveConfig", required = true)
    protected TransactionDetailConfigType retrieveConfig;
    @XmlElement(name = "ECTransactionID", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType ecTransactionID;
    @XmlElement(name = "TransactionKey")
    protected TransactionKeyType transactionKey;
    @XmlElement(name = "UserArea", namespace = "http://www.tsh.bestbuy.com/common/v1")
    protected UserAreaType userArea;
    @XmlAttribute(namespace = "http://www.tsh.bestbuy.com/common/v1")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String languageCode;

    /**
     * Gets the value of the metaData property.
     * 
     * @return
     *     possible object is
     *     {@link RequestMetaDataExtensionType }
     *     
     */
    public RequestMetaDataExtensionType getMetaData() {
        return metaData;
    }

    /**
     * Sets the value of the metaData property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestMetaDataExtensionType }
     *     
     */
    public void setMetaData(RequestMetaDataExtensionType value) {
        this.metaData = value;
    }

    /**
     * Gets the value of the internationBusinessHierarcy property.
     * 
     * @return
     *     possible object is
     *     {@link InternationalBusinessHierarchyType }
     *     
     */
    public InternationalBusinessHierarchyType getInternationBusinessHierarcy() {
        return internationBusinessHierarcy;
    }

    /**
     * Sets the value of the internationBusinessHierarcy property.
     * 
     * @param value
     *     allowed object is
     *     {@link InternationalBusinessHierarchyType }
     *     
     */
    public void setInternationBusinessHierarcy(InternationalBusinessHierarchyType value) {
        this.internationBusinessHierarcy = value;
    }

    /**
     * Gets the value of the retrieveConfig property.
     * 
     * @return
     *     possible object is
     *     {@link TransactionDetailConfigType }
     *     
     */
    public TransactionDetailConfigType getRetrieveConfig() {
        return retrieveConfig;
    }

    /**
     * Sets the value of the retrieveConfig property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransactionDetailConfigType }
     *     
     */
    public void setRetrieveConfig(TransactionDetailConfigType value) {
        this.retrieveConfig = value;
    }

    /**
     * This element holds EC transaction ID.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getECTransactionID() {
        return ecTransactionID;
    }

    /**
     * Sets the value of the ecTransactionID property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setECTransactionID(TextType value) {
        this.ecTransactionID = value;
    }

    /**
     * Gets the value of the transactionKey property.
     * 
     * @return
     *     possible object is
     *     {@link TransactionKeyType }
     *     
     */
    public TransactionKeyType getTransactionKey() {
        return transactionKey;
    }

    /**
     * Sets the value of the transactionKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransactionKeyType }
     *     
     */
    public void setTransactionKey(TransactionKeyType value) {
        this.transactionKey = value;
    }

    /**
     * Allows the Tag Super Highway schema data model to extend the specification in order to provide additional information that is not captured in Canonical Schema.
     * 
     * @return
     *     possible object is
     *     {@link UserAreaType }
     *     
     */
    public UserAreaType getUserArea() {
        return userArea;
    }

    /**
     * Sets the value of the userArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserAreaType }
     *     
     */
    public void setUserArea(UserAreaType value) {
        this.userArea = value;
    }

    /**
     * Gets the value of the languageCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * Sets the value of the languageCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguageCode(String value) {
        this.languageCode = value;
    }

}
