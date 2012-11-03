
package com.bestbuy.tsh.common.location.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for TimeZoneType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TimeZoneType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="abbreviations" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}NormalizedStringType" />
 *       &lt;attribute name="designator" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}NormalizedStringType" default="UTC" />
 *       &lt;attribute name="isDayLightSavingsInEffect" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}BooleanType" />
 *       &lt;attribute name="offset" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}BooleanType" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TimeZoneType", propOrder = {
    "value"
})
public class TimeZoneType {

    @XmlValue
    protected String value;
    @XmlAttribute
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String abbreviations;
    @XmlAttribute
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String designator;
    @XmlAttribute
    protected Boolean isDayLightSavingsInEffect;
    @XmlAttribute
    protected Boolean offset;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the abbreviations property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAbbreviations() {
        return abbreviations;
    }

    /**
     * Sets the value of the abbreviations property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAbbreviations(String value) {
        this.abbreviations = value;
    }

    /**
     * Gets the value of the designator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesignator() {
        if (designator == null) {
            return "UTC";
        } else {
            return designator;
        }
    }

    /**
     * Sets the value of the designator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesignator(String value) {
        this.designator = value;
    }

    /**
     * Gets the value of the isDayLightSavingsInEffect property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsDayLightSavingsInEffect() {
        return isDayLightSavingsInEffect;
    }

    /**
     * Sets the value of the isDayLightSavingsInEffect property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsDayLightSavingsInEffect(Boolean value) {
        this.isDayLightSavingsInEffect = value;
    }

    /**
     * Gets the value of the offset property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isOffset() {
        return offset;
    }

    /**
     * Sets the value of the offset property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOffset(Boolean value) {
        this.offset = value;
    }

}
