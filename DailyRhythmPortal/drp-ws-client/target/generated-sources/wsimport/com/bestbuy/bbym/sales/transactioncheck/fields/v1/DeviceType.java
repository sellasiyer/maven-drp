
package com.bestbuy.bbym.sales.transactioncheck.fields.v1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.bestbuy.tsh.common.datatype.v1.IdentifierType;


/**
 * <p>Java class for DeviceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeviceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TechnologyCode" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}NormalizedStringType" minOccurs="0"/>
 *         &lt;element name="SKU" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}NormalizedStringType" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element name="ESN" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}NormalizedStringType" minOccurs="0"/>
 *           &lt;element name="IMEI" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}NormalizedStringType" minOccurs="0"/>
 *         &lt;/choice>
 *         &lt;element name="SIM" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}NormalizedStringType" minOccurs="0"/>
 *         &lt;element name="UPC" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}NormalizedStringType" minOccurs="0"/>
 *         &lt;element name="MSN" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}NormalizedStringType" minOccurs="0"/>
 *         &lt;element name="MACID" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}NormalizedStringType" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/v1}ID" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeviceType", propOrder = {
    "technologyCode",
    "sku",
    "esn",
    "imei",
    "sim",
    "upc",
    "msn",
    "macid",
    "id"
})
public class DeviceType {

    @XmlElement(name = "TechnologyCode")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String technologyCode;
    @XmlElement(name = "SKU")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String sku;
    @XmlElement(name = "ESN")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String esn;
    @XmlElement(name = "IMEI")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String imei;
    @XmlElement(name = "SIM")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String sim;
    @XmlElement(name = "UPC")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String upc;
    @XmlElement(name = "MSN")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String msn;
    @XmlElement(name = "MACID")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String macid;
    @XmlElement(name = "ID", namespace = "http://www.tsh.bestbuy.com/common/v1")
    protected List<IdentifierType> id;

    /**
     * Gets the value of the technologyCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTechnologyCode() {
        return technologyCode;
    }

    /**
     * Sets the value of the technologyCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTechnologyCode(String value) {
        this.technologyCode = value;
    }

    /**
     * Gets the value of the sku property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSKU() {
        return sku;
    }

    /**
     * Sets the value of the sku property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSKU(String value) {
        this.sku = value;
    }

    /**
     * Gets the value of the esn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getESN() {
        return esn;
    }

    /**
     * Sets the value of the esn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setESN(String value) {
        this.esn = value;
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
     * Gets the value of the sim property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSIM() {
        return sim;
    }

    /**
     * Sets the value of the sim property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSIM(String value) {
        this.sim = value;
    }

    /**
     * Gets the value of the upc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUPC() {
        return upc;
    }

    /**
     * Sets the value of the upc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUPC(String value) {
        this.upc = value;
    }

    /**
     * Gets the value of the msn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMSN() {
        return msn;
    }

    /**
     * Sets the value of the msn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMSN(String value) {
        this.msn = value;
    }

    /**
     * Gets the value of the macid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMACID() {
        return macid;
    }

    /**
     * Sets the value of the macid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMACID(String value) {
        this.macid = value;
    }

    /**
     * Is the Identifiers of the given instance of an entity within the scope of the integration. 
     * 				The schemeAgencyID attribute identifies the party that provided or knows this party by the given identifier.
     * 			Gets the value of the id property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the id property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getID().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IdentifierType }
     * 
     * 
     */
    public List<IdentifierType> getID() {
        if (id == null) {
            id = new ArrayList<IdentifierType>();
        }
        return this.id;
    }

}
