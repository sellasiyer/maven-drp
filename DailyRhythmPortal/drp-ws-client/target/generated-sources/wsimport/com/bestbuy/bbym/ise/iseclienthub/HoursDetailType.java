
package com.bestbuy.bbym.ise.iseclienthub;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for HoursDetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HoursDetailType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SalesOrderNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SalesSKU" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HoursPurchased" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="AvailableHours" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="HoursEndDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HoursDetailType", propOrder = {
    "salesOrderNumber",
    "salesSKU",
    "orderAmount",
    "hoursPurchased",
    "availableHours",
    "hoursEndDate"
})
public class HoursDetailType {

    @XmlElement(name = "SalesOrderNumber")
    protected String salesOrderNumber;
    @XmlElement(name = "SalesSKU")
    protected String salesSKU;
    @XmlElement(name = "OrderAmount")
    protected String orderAmount;
    @XmlElement(name = "HoursPurchased")
    protected BigInteger hoursPurchased;
    @XmlElement(name = "AvailableHours")
    protected BigInteger availableHours;
    @XmlElement(name = "HoursEndDate", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar hoursEndDate;

    /**
     * Gets the value of the salesOrderNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalesOrderNumber() {
        return salesOrderNumber;
    }

    /**
     * Sets the value of the salesOrderNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalesOrderNumber(String value) {
        this.salesOrderNumber = value;
    }

    /**
     * Gets the value of the salesSKU property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalesSKU() {
        return salesSKU;
    }

    /**
     * Sets the value of the salesSKU property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalesSKU(String value) {
        this.salesSKU = value;
    }

    /**
     * Gets the value of the orderAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderAmount() {
        return orderAmount;
    }

    /**
     * Sets the value of the orderAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderAmount(String value) {
        this.orderAmount = value;
    }

    /**
     * Gets the value of the hoursPurchased property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getHoursPurchased() {
        return hoursPurchased;
    }

    /**
     * Sets the value of the hoursPurchased property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setHoursPurchased(BigInteger value) {
        this.hoursPurchased = value;
    }

    /**
     * Gets the value of the availableHours property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAvailableHours() {
        return availableHours;
    }

    /**
     * Sets the value of the availableHours property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAvailableHours(BigInteger value) {
        this.availableHours = value;
    }

    /**
     * Gets the value of the hoursEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getHoursEndDate() {
        return hoursEndDate;
    }

    /**
     * Sets the value of the hoursEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setHoursEndDate(XMLGregorianCalendar value) {
        this.hoursEndDate = value;
    }

}
