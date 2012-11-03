
package com.bestbuy.tsh.sales.common.components.v1;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.bestbuy.tsh.common.datatype.v1.TextType;


/**
 * TransactionPaymentType
 * 
 * <p>Java class for TransactionPaymentType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransactionPaymentType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}TransactionLineNumber" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}TransactionLineType" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}PaymentAmount" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}TransactionAccountCategory" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}TransactionAccountNumber" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}SourceLineType" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}CreditCardFinancePlanID" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}SettlementKey" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransactionPaymentType", propOrder = {
    "transactionLineNumber",
    "transactionLineType",
    "paymentAmount",
    "transactionAccountCategory",
    "transactionAccountNumber",
    "sourceLineType",
    "creditCardFinancePlanID",
    "settlementKey"
})
public class TransactionPaymentType {

    @XmlElement(name = "TransactionLineNumber", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType transactionLineNumber;
    @XmlElement(name = "TransactionLineType", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType transactionLineType;
    @XmlElement(name = "PaymentAmount", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected BigDecimal paymentAmount;
    @XmlElement(name = "TransactionAccountCategory", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType transactionAccountCategory;
    @XmlElement(name = "TransactionAccountNumber", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType transactionAccountNumber;
    @XmlElement(name = "SourceLineType", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType sourceLineType;
    @XmlElement(name = "CreditCardFinancePlanID", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected BigInteger creditCardFinancePlanID;
    @XmlElement(name = "SettlementKey", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType settlementKey;

    /**
     * TransactionLineNumber
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getTransactionLineNumber() {
        return transactionLineNumber;
    }

    /**
     * Sets the value of the transactionLineNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setTransactionLineNumber(TextType value) {
        this.transactionLineNumber = value;
    }

    /**
     * TransactionLineType
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getTransactionLineType() {
        return transactionLineType;
    }

    /**
     * Sets the value of the transactionLineType property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setTransactionLineType(TextType value) {
        this.transactionLineType = value;
    }

    /**
     * PaymentAmount
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    /**
     * Sets the value of the paymentAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPaymentAmount(BigDecimal value) {
        this.paymentAmount = value;
    }

    /**
     * TransactionAccountCategory
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getTransactionAccountCategory() {
        return transactionAccountCategory;
    }

    /**
     * Sets the value of the transactionAccountCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setTransactionAccountCategory(TextType value) {
        this.transactionAccountCategory = value;
    }

    /**
     * TransactionAccountNumber
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getTransactionAccountNumber() {
        return transactionAccountNumber;
    }

    /**
     * Sets the value of the transactionAccountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setTransactionAccountNumber(TextType value) {
        this.transactionAccountNumber = value;
    }

    /**
     * his attribute holds the source line type.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getSourceLineType() {
        return sourceLineType;
    }

    /**
     * Sets the value of the sourceLineType property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setSourceLineType(TextType value) {
        this.sourceLineType = value;
    }

    /**
     * This attribute holds the credit card finance plan id.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCreditCardFinancePlanID() {
        return creditCardFinancePlanID;
    }

    /**
     * Sets the value of the creditCardFinancePlanID property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCreditCardFinancePlanID(BigInteger value) {
        this.creditCardFinancePlanID = value;
    }

    /**
     * This attribute holds an additional key for the payment required by payment types such as Pay Pal.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getSettlementKey() {
        return settlementKey;
    }

    /**
     * Sets the value of the settlementKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setSettlementKey(TextType value) {
        this.settlementKey = value;
    }

}
