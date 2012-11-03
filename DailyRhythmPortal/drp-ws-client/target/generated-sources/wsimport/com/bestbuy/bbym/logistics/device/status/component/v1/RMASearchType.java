
package com.bestbuy.bbym.logistics.device.status.component.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Contains relevant fields for searching for return records
 * 
 * <p>Java class for RMASearchType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RMASearchType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TransactionID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TransactionDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="CustomerPartNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PrimarySerialNumber" type="{http://bestbuy.com/bbym/logistics/device/status/component/v1}SN_Type"/>
 *         &lt;element name="IMEI" type="{http://bestbuy.com/bbym/logistics/device/status/component/v1}SN_Type" minOccurs="0"/>
 *         &lt;element name="MEID_DEC" type="{http://bestbuy.com/bbym/logistics/device/status/component/v1}SN_Type" minOccurs="0"/>
 *         &lt;element name="MEID_HEX" type="{http://bestbuy.com/bbym/logistics/device/status/component/v1}SN_Type" minOccurs="0"/>
 *         &lt;element name="ManuSerialNumber" type="{http://bestbuy.com/bbym/logistics/device/status/component/v1}SN_Type" minOccurs="0"/>
 *         &lt;element name="CourierTrackingNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Color" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StoreID" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Carrier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RMASearchType", propOrder = {
    "transactionID",
    "transactionDate",
    "customerPartNumber",
    "primarySerialNumber",
    "imei",
    "meiddec",
    "meidhex",
    "manuSerialNumber",
    "courierTrackingNumber",
    "color",
    "storeID",
    "carrier"
})
public class RMASearchType {

    @XmlElement(name = "TransactionID")
    protected String transactionID;
    @XmlElement(name = "TransactionDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar transactionDate;
    @XmlElement(name = "CustomerPartNumber", required = true)
    protected String customerPartNumber;
    @XmlElement(name = "PrimarySerialNumber", required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String primarySerialNumber;
    @XmlElement(name = "IMEI")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String imei;
    @XmlElement(name = "MEID_DEC")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String meiddec;
    @XmlElement(name = "MEID_HEX")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String meidhex;
    @XmlElement(name = "ManuSerialNumber")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String manuSerialNumber;
    @XmlElement(name = "CourierTrackingNumber")
    protected String courierTrackingNumber;
    @XmlElement(name = "Color")
    protected String color;
    @XmlElement(name = "StoreID")
    protected Integer storeID;
    @XmlElement(name = "Carrier")
    protected String carrier;

    /**
     * Gets the value of the transactionID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionID() {
        return transactionID;
    }

    /**
     * Sets the value of the transactionID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionID(String value) {
        this.transactionID = value;
    }

    /**
     * Gets the value of the transactionDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTransactionDate() {
        return transactionDate;
    }

    /**
     * Sets the value of the transactionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTransactionDate(XMLGregorianCalendar value) {
        this.transactionDate = value;
    }

    /**
     * Gets the value of the customerPartNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerPartNumber() {
        return customerPartNumber;
    }

    /**
     * Sets the value of the customerPartNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerPartNumber(String value) {
        this.customerPartNumber = value;
    }

    /**
     * Gets the value of the primarySerialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrimarySerialNumber() {
        return primarySerialNumber;
    }

    /**
     * Sets the value of the primarySerialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrimarySerialNumber(String value) {
        this.primarySerialNumber = value;
    }

    /**
     * Gets the value of the imei property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIMEI() {
        return imei;
    }

    /**
     * Sets the value of the imei property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIMEI(String value) {
        this.imei = value;
    }

    /**
     * Gets the value of the meiddec property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMEIDDEC() {
        return meiddec;
    }

    /**
     * Sets the value of the meiddec property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMEIDDEC(String value) {
        this.meiddec = value;
    }

    /**
     * Gets the value of the meidhex property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMEIDHEX() {
        return meidhex;
    }

    /**
     * Sets the value of the meidhex property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMEIDHEX(String value) {
        this.meidhex = value;
    }

    /**
     * Gets the value of the manuSerialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManuSerialNumber() {
        return manuSerialNumber;
    }

    /**
     * Sets the value of the manuSerialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManuSerialNumber(String value) {
        this.manuSerialNumber = value;
    }

    /**
     * Gets the value of the courierTrackingNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCourierTrackingNumber() {
        return courierTrackingNumber;
    }

    /**
     * Sets the value of the courierTrackingNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCourierTrackingNumber(String value) {
        this.courierTrackingNumber = value;
    }

    /**
     * Gets the value of the color property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets the value of the color property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColor(String value) {
        this.color = value;
    }

    /**
     * Gets the value of the storeID property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getStoreID() {
        return storeID;
    }

    /**
     * Sets the value of the storeID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setStoreID(Integer value) {
        this.storeID = value;
    }

    /**
     * Gets the value of the carrier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarrier() {
        return carrier;
    }

    /**
     * Sets the value of the carrier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarrier(String value) {
        this.carrier = value;
    }

}
