
package com.bestbuy.tsh.sales.common.components.v1;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.bestbuy.tsh.common.datatype.v1.TextType;


/**
 * LineItemDetailsType
 * 
 * <p>Java class for LineItemDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LineItemDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}LineItemDetailKey" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}LineItemDetailType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}LineItemDetailAmount" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}Quantity" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}SourceLineType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}RebateSuppressFlag" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LineItemDetailsType", propOrder = {
    "lineItemDetailKey",
    "lineItemDetailType",
    "lineItemDetailAmount",
    "quantity",
    "sourceLineType",
    "rebateSuppressFlag"
})
public class LineItemDetailsType {

    @XmlElement(name = "LineItemDetailKey", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected List<TextType> lineItemDetailKey;
    @XmlElement(name = "LineItemDetailType", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected List<TextType> lineItemDetailType;
    @XmlElement(name = "LineItemDetailAmount", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected List<BigDecimal> lineItemDetailAmount;
    @XmlElement(name = "Quantity", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected List<BigInteger> quantity;
    @XmlElement(name = "SourceLineType", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected List<TextType> sourceLineType;
    @XmlElement(name = "RebateSuppressFlag", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected List<TextType> rebateSuppressFlag;

    /**
     * This element holds LineItemDetailKey Gets the value of the lineItemDetailKey property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lineItemDetailKey property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLineItemDetailKey().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TextType }
     * 
     * 
     */
    public List<TextType> getLineItemDetailKey() {
        if (lineItemDetailKey == null) {
            lineItemDetailKey = new ArrayList<TextType>();
        }
        return this.lineItemDetailKey;
    }

    /**
     * This element holds LineItemDetailType Gets the value of the lineItemDetailType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lineItemDetailType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLineItemDetailType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TextType }
     * 
     * 
     */
    public List<TextType> getLineItemDetailType() {
        if (lineItemDetailType == null) {
            lineItemDetailType = new ArrayList<TextType>();
        }
        return this.lineItemDetailType;
    }

    /**
     * This element holds LineItemDetailAmount Gets the value of the lineItemDetailAmount property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lineItemDetailAmount property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLineItemDetailAmount().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BigDecimal }
     * 
     * 
     */
    public List<BigDecimal> getLineItemDetailAmount() {
        if (lineItemDetailAmount == null) {
            lineItemDetailAmount = new ArrayList<BigDecimal>();
        }
        return this.lineItemDetailAmount;
    }

    /**
     * This element holds Quantity Gets the value of the quantity property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the quantity property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getQuantity().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BigInteger }
     * 
     * 
     */
    public List<BigInteger> getQuantity() {
        if (quantity == null) {
            quantity = new ArrayList<BigInteger>();
        }
        return this.quantity;
    }

    /**
     * his attribute holds the source line type.Gets the value of the sourceLineType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sourceLineType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSourceLineType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TextType }
     * 
     * 
     */
    public List<TextType> getSourceLineType() {
        if (sourceLineType == null) {
            sourceLineType = new ArrayList<TextType>();
        }
        return this.sourceLineType;
    }

    /**
     * This element holds RebateSuppressFlag Gets the value of the rebateSuppressFlag property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rebateSuppressFlag property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRebateSuppressFlag().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TextType }
     * 
     * 
     */
    public List<TextType> getRebateSuppressFlag() {
        if (rebateSuppressFlag == null) {
            rebateSuppressFlag = new ArrayList<TextType>();
        }
        return this.rebateSuppressFlag;
    }

}
