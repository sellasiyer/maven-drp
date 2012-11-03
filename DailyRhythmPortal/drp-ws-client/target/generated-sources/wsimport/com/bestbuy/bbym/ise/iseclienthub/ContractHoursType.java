
package com.bestbuy.bbym.ise.iseclienthub;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ContractHoursType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContractHoursType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TopLineRevenue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TotalHoursPurchased" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="TotalPendingHours" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="TotalUsedHours" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="TotalRemainingHours" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="HoursDetail" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Scheme/V4}HoursDetailType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContractHoursType", propOrder = {
    "topLineRevenue",
    "totalHoursPurchased",
    "totalPendingHours",
    "totalUsedHours",
    "totalRemainingHours",
    "hoursDetail"
})
public class ContractHoursType {

    @XmlElement(name = "TopLineRevenue")
    protected String topLineRevenue;
    @XmlElement(name = "TotalHoursPurchased")
    protected BigInteger totalHoursPurchased;
    @XmlElement(name = "TotalPendingHours")
    protected BigInteger totalPendingHours;
    @XmlElement(name = "TotalUsedHours")
    protected BigInteger totalUsedHours;
    @XmlElement(name = "TotalRemainingHours")
    protected BigInteger totalRemainingHours;
    @XmlElement(name = "HoursDetail")
    protected List<HoursDetailType> hoursDetail;

    /**
     * Gets the value of the topLineRevenue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTopLineRevenue() {
        return topLineRevenue;
    }

    /**
     * Sets the value of the topLineRevenue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTopLineRevenue(String value) {
        this.topLineRevenue = value;
    }

    /**
     * Gets the value of the totalHoursPurchased property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTotalHoursPurchased() {
        return totalHoursPurchased;
    }

    /**
     * Sets the value of the totalHoursPurchased property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTotalHoursPurchased(BigInteger value) {
        this.totalHoursPurchased = value;
    }

    /**
     * Gets the value of the totalPendingHours property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTotalPendingHours() {
        return totalPendingHours;
    }

    /**
     * Sets the value of the totalPendingHours property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTotalPendingHours(BigInteger value) {
        this.totalPendingHours = value;
    }

    /**
     * Gets the value of the totalUsedHours property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTotalUsedHours() {
        return totalUsedHours;
    }

    /**
     * Sets the value of the totalUsedHours property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTotalUsedHours(BigInteger value) {
        this.totalUsedHours = value;
    }

    /**
     * Gets the value of the totalRemainingHours property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTotalRemainingHours() {
        return totalRemainingHours;
    }

    /**
     * Sets the value of the totalRemainingHours property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTotalRemainingHours(BigInteger value) {
        this.totalRemainingHours = value;
    }

    /**
     * Gets the value of the hoursDetail property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hoursDetail property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHoursDetail().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HoursDetailType }
     * 
     * 
     */
    public List<HoursDetailType> getHoursDetail() {
        if (hoursDetail == null) {
            hoursDetail = new ArrayList<HoursDetailType>();
        }
        return this.hoursDetail;
    }

}
