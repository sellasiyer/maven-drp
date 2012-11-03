
package com.bestbuy.bbym.sales.transactioncheck.fields.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.bestbuy.tsh.common.location.v1.AddressBaseType;


/**
 * <p>Java class for AddressType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AddressType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tsh.bestbuy.com/common/location/v1}AddressBaseType">
 *       &lt;sequence>
 *         &lt;element name="AddressType" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}StringType" minOccurs="0"/>
 *         &lt;element name="UrbanizationCode" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}NormalizedStringType" minOccurs="0"/>
 *         &lt;element name="Direction" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}NormalizedStringType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddressType", propOrder = {
    "addressType",
    "urbanizationCode",
    "direction"
})
public class AddressType
    extends AddressBaseType
{

    @XmlElement(name = "AddressType")
    protected String addressType;
    @XmlElement(name = "UrbanizationCode")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String urbanizationCode;
    @XmlElement(name = "Direction")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String direction;

    /**
     * Gets the value of the addressType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressType() {
        return addressType;
    }

    /**
     * Sets the value of the addressType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressType(String value) {
        this.addressType = value;
    }

    /**
     * Gets the value of the urbanizationCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrbanizationCode() {
        return urbanizationCode;
    }

    /**
     * Sets the value of the urbanizationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrbanizationCode(String value) {
        this.urbanizationCode = value;
    }

    /**
     * Gets the value of the direction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Sets the value of the direction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirection(String value) {
        this.direction = value;
    }

}
