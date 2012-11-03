
package com.bestbuy.tsh.sales.common.components.v1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * TransactionType
 * 
 * <p>Java class for TransactionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransactionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TransactionHeader" type="{http://www.tsh.bestbuy.com/sales/common/components/v1}TransactionHeaderType" minOccurs="0"/>
 *         &lt;element name="TransactionLineItem" type="{http://www.tsh.bestbuy.com/sales/common/components/v1}TransactionLineItemType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="TransactionPayment" type="{http://www.tsh.bestbuy.com/sales/common/components/v1}TransactionPaymentType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="TransactionContract" type="{http://www.tsh.bestbuy.com/sales/common/components/v1}TransactionContractType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransactionType", propOrder = {
    "transactionHeader",
    "transactionLineItem",
    "transactionPayment",
    "transactionContract"
})
public class TransactionType {

    @XmlElement(name = "TransactionHeader")
    protected TransactionHeaderType transactionHeader;
    @XmlElement(name = "TransactionLineItem")
    protected List<TransactionLineItemType> transactionLineItem;
    @XmlElement(name = "TransactionPayment")
    protected List<TransactionPaymentType> transactionPayment;
    @XmlElement(name = "TransactionContract")
    protected List<TransactionContractType> transactionContract;

    /**
     * Gets the value of the transactionHeader property.
     * 
     * @return
     *     possible object is
     *     {@link TransactionHeaderType }
     *     
     */
    public TransactionHeaderType getTransactionHeader() {
        return transactionHeader;
    }

    /**
     * Sets the value of the transactionHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransactionHeaderType }
     *     
     */
    public void setTransactionHeader(TransactionHeaderType value) {
        this.transactionHeader = value;
    }

    /**
     * Gets the value of the transactionLineItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the transactionLineItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTransactionLineItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TransactionLineItemType }
     * 
     * 
     */
    public List<TransactionLineItemType> getTransactionLineItem() {
        if (transactionLineItem == null) {
            transactionLineItem = new ArrayList<TransactionLineItemType>();
        }
        return this.transactionLineItem;
    }

    /**
     * Gets the value of the transactionPayment property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the transactionPayment property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTransactionPayment().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TransactionPaymentType }
     * 
     * 
     */
    public List<TransactionPaymentType> getTransactionPayment() {
        if (transactionPayment == null) {
            transactionPayment = new ArrayList<TransactionPaymentType>();
        }
        return this.transactionPayment;
    }

    /**
     * Gets the value of the transactionContract property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the transactionContract property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTransactionContract().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TransactionContractType }
     * 
     * 
     */
    public List<TransactionContractType> getTransactionContract() {
        if (transactionContract == null) {
            transactionContract = new ArrayList<TransactionContractType>();
        }
        return this.transactionContract;
    }

}
