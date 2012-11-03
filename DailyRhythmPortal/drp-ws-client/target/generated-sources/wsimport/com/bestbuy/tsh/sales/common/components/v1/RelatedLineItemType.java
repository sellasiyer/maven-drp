
package com.bestbuy.tsh.sales.common.components.v1;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.bestbuy.tsh.common.datatype.v1.TextType;


/**
 * RelatedLineItemType
 * 
 * <p>Java class for RelatedLineItemType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RelatedLineItemType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}RelationshipType" minOccurs="0"/>
 *         &lt;element name="SourceTransactionKey" type="{http://www.tsh.bestbuy.com/sales/common/components/v1}TransactionKeyType" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}SourceLineNumber" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RelatedLineItemType", propOrder = {
    "relationshipType",
    "sourceTransactionKey",
    "sourceLineNumber"
})
public class RelatedLineItemType {

    @XmlElement(name = "RelationshipType", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType relationshipType;
    @XmlElement(name = "SourceTransactionKey")
    protected TransactionKeyType sourceTransactionKey;
    @XmlElement(name = "SourceLineNumber", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected BigDecimal sourceLineNumber;

    /**
     * This element holds RelationshipType
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getRelationshipType() {
        return relationshipType;
    }

    /**
     * Sets the value of the relationshipType property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setRelationshipType(TextType value) {
        this.relationshipType = value;
    }

    /**
     * Gets the value of the sourceTransactionKey property.
     * 
     * @return
     *     possible object is
     *     {@link TransactionKeyType }
     *     
     */
    public TransactionKeyType getSourceTransactionKey() {
        return sourceTransactionKey;
    }

    /**
     * Sets the value of the sourceTransactionKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransactionKeyType }
     *     
     */
    public void setSourceTransactionKey(TransactionKeyType value) {
        this.sourceTransactionKey = value;
    }

    /**
     * This element holds SourceLineNumber
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSourceLineNumber() {
        return sourceLineNumber;
    }

    /**
     * Sets the value of the sourceLineNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSourceLineNumber(BigDecimal value) {
        this.sourceLineNumber = value;
    }

}
