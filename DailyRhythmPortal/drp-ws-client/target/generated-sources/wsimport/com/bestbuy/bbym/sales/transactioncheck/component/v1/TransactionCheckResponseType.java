
package com.bestbuy.bbym.sales.transactioncheck.component.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.bestbuy.tsh.common.datatype.v1.UserAreaType;
import com.bestbuy.tsh.common.metadata.components.v1.ResponseMetaDataType;
import com.bestbuy.tsh.common.v1.StatusType;


/**
 * <p>Java class for TransactionCheckResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransactionCheckResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ResponseMetaData" type="{http://www.tsh.bestbuy.com/common/metadata/components/v1}ResponseMetaDataType"/>
 *         &lt;element name="TransactionCheckResult" type="{http://bestbuy.com/bbym/sales/transactioncheck/component/v1}TransactionCheckResultType" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/v1}Status" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/v1}UserArea" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransactionCheckResponseType", propOrder = {
    "responseMetaData",
    "transactionCheckResult",
    "status",
    "userArea"
})
public class TransactionCheckResponseType {

    @XmlElement(name = "ResponseMetaData", required = true)
    protected ResponseMetaDataType responseMetaData;
    @XmlElement(name = "TransactionCheckResult")
    protected TransactionCheckResultType transactionCheckResult;
    @XmlElement(name = "Status", namespace = "http://www.tsh.bestbuy.com/common/v1")
    protected StatusType status;
    @XmlElement(name = "UserArea", namespace = "http://www.tsh.bestbuy.com/common/v1")
    protected UserAreaType userArea;

    /**
     * Gets the value of the responseMetaData property.
     * 
     * @return
     *     possible object is
     *     {@link ResponseMetaDataType }
     *     
     */
    public ResponseMetaDataType getResponseMetaData() {
        return responseMetaData;
    }

    /**
     * Sets the value of the responseMetaData property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseMetaDataType }
     *     
     */
    public void setResponseMetaData(ResponseMetaDataType value) {
        this.responseMetaData = value;
    }

    /**
     * Gets the value of the transactionCheckResult property.
     * 
     * @return
     *     possible object is
     *     {@link TransactionCheckResultType }
     *     
     */
    public TransactionCheckResultType getTransactionCheckResult() {
        return transactionCheckResult;
    }

    /**
     * Sets the value of the transactionCheckResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransactionCheckResultType }
     *     
     */
    public void setTransactionCheckResult(TransactionCheckResultType value) {
        this.transactionCheckResult = value;
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

}
