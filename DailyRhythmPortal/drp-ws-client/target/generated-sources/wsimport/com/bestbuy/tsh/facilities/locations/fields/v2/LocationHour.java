
package com.bestbuy.tsh.facilities.locations.fields.v2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LocationHour complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LocationHour">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TimeSlot">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="OpenTimeText" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}StringType" minOccurs="0"/>
 *                   &lt;element name="CloseTimeText" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}StringType" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="WorkDay" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}StringType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LocationHour", propOrder = {
    "timeSlot",
    "workDay"
})
public class LocationHour {

    @XmlElement(name = "TimeSlot", required = true)
    protected LocationHour.TimeSlot timeSlot;
    @XmlElement(name = "WorkDay")
    protected String workDay;

    /**
     * Gets the value of the timeSlot property.
     * 
     * @return
     *     possible object is
     *     {@link LocationHour.TimeSlot }
     *     
     */
    public LocationHour.TimeSlot getTimeSlot() {
        return timeSlot;
    }

    /**
     * Sets the value of the timeSlot property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationHour.TimeSlot }
     *     
     */
    public void setTimeSlot(LocationHour.TimeSlot value) {
        this.timeSlot = value;
    }

    /**
     * Gets the value of the workDay property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkDay() {
        return workDay;
    }

    /**
     * Sets the value of the workDay property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkDay(String value) {
        this.workDay = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="OpenTimeText" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}StringType" minOccurs="0"/>
     *         &lt;element name="CloseTimeText" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}StringType" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "openTimeText",
        "closeTimeText"
    })
    public static class TimeSlot {

        @XmlElement(name = "OpenTimeText")
        protected String openTimeText;
        @XmlElement(name = "CloseTimeText")
        protected String closeTimeText;

        /**
         * Gets the value of the openTimeText property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOpenTimeText() {
            return openTimeText;
        }

        /**
         * Sets the value of the openTimeText property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOpenTimeText(String value) {
            this.openTimeText = value;
        }

        /**
         * Gets the value of the closeTimeText property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCloseTimeText() {
            return closeTimeText;
        }

        /**
         * Sets the value of the closeTimeText property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCloseTimeText(String value) {
            this.closeTimeText = value;
        }

    }

}
