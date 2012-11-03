
package com.bestbuy.tsh.common.location.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import com.bestbuy.tsh.common.datatype.v1.MeasureType;


/**
 * Geographic Units
 * 			
 * 
 * <p>Java class for GeoCoordinateMeasureType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GeoCoordinateMeasureType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.tsh.bestbuy.com/common/datatype/v1>MeasureType">
 *       &lt;attribute ref="{http://www.tsh.bestbuy.com/common/location/v1}degreeUnit"/>
 *       &lt;attribute ref="{http://www.tsh.bestbuy.com/common/location/v1}minuteUnit"/>
 *       &lt;attribute ref="{http://www.tsh.bestbuy.com/common/location/v1}secondUnit"/>
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GeoCoordinateMeasureType")
public class GeoCoordinateMeasureType
    extends MeasureType
{

    @XmlAttribute(namespace = "http://www.tsh.bestbuy.com/common/location/v1")
    protected String degreeUnit;
    @XmlAttribute(namespace = "http://www.tsh.bestbuy.com/common/location/v1")
    protected String minuteUnit;
    @XmlAttribute(namespace = "http://www.tsh.bestbuy.com/common/location/v1")
    protected String secondUnit;

    /**
     * Gets the value of the degreeUnit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDegreeUnit() {
        return degreeUnit;
    }

    /**
     * Sets the value of the degreeUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDegreeUnit(String value) {
        this.degreeUnit = value;
    }

    /**
     * Gets the value of the minuteUnit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMinuteUnit() {
        return minuteUnit;
    }

    /**
     * Sets the value of the minuteUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMinuteUnit(String value) {
        this.minuteUnit = value;
    }

    /**
     * Gets the value of the secondUnit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecondUnit() {
        return secondUnit;
    }

    /**
     * Sets the value of the secondUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecondUnit(String value) {
        this.secondUnit = value;
    }

}
