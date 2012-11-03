
package com.bestbuy.tsh.sales.common.components.v1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.bestbuy.tsh.common.datatype.v1.UserAreaType;
import com.bestbuy.tsh.common.metadata.components.v1.ResponseMetaDataType;
import com.bestbuy.tsh.common.v1.StatusType;


/**
 * RetrieveTransactionResponseType
 * 
 * <p>Java class for RetrieveTransactionResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RetrieveTransactionResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MetaData" type="{http://www.tsh.bestbuy.com/common/metadata/components/v1}ResponseMetaDataType" minOccurs="0"/>
 *         &lt;element name="SystemStatus" type="{http://www.tsh.bestbuy.com/sales/common/components/v1}SystemStatusType" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/components/v1}Transaction" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/v1}Status" minOccurs="0"/>
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
@XmlType(name = "RetrieveTransactionResponseType", propOrder = {
    "metaData",
    "systemStatus",
    "transaction",
    "status",
    "userArea"
})
public class RetrieveTransactionResponseType {

    @XmlElement(name = "MetaData")
    protected ResponseMetaDataType metaData;
    @XmlElement(name = "SystemStatus")
    protected SystemStatusType systemStatus;
    @XmlElement(name = "Transaction")
    protected List<TransactionType> transaction;
    @XmlElement(name = "Status", namespace = "http://www.tsh.bestbuy.com/common/v1")
    protected StatusType status;
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
     *     {@link ResponseMetaDataType }
     *     
     */
    public ResponseMetaDataType getMetaData() {
        return metaData;
    }

    /**
     * Sets the value of the metaData property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseMetaDataType }
     *     
     */
    public void setMetaData(ResponseMetaDataType value) {
        this.metaData = value;
    }

    /**
     * Gets the value of the systemStatus property.
     * 
     * @return
     *     possible object is
     *     {@link SystemStatusType }
     *     
     */
    public SystemStatusType getSystemStatus() {
        return systemStatus;
    }

    /**
     * Sets the value of the systemStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link SystemStatusType }
     *     
     */
    public void setSystemStatus(SystemStatusType value) {
        this.systemStatus = value;
    }

    /**
     * Transaction Gets the value of the transaction property.
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
     *  Indicates the status of the associated object by providing the Status Code along with a description and when the status is effective.
     * 
     * @return
     *     possible object is
     *     {@link StatusType }
     *     
     */
    public StatusType getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusType }
     *     
     */
    public void setStatus(StatusType value) {
        this.status = value;
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
