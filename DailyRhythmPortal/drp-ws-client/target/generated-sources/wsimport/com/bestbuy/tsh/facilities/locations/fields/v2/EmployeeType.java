
package com.bestbuy.tsh.facilities.locations.fields.v2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Entire Details of the Employee like Employee Name,Gender,Job details
 * 
 * <p>Java class for EmployeeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EmployeeType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tsh.bestbuy.com/facilities/locations/fields/v2}PersonType">
 *       &lt;sequence>
 *         &lt;element name="Job" type="{http://www.tsh.bestbuy.com/facilities/locations/fields/v2}EmployeeJobType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmployeeType", propOrder = {
    "job"
})
public class EmployeeType
    extends PersonType
{

    @XmlElement(name = "Job")
    protected EmployeeJobType job;

    /**
     * Gets the value of the job property.
     * 
     * @return
     *     possible object is
     *     {@link EmployeeJobType }
     *     
     */
    public EmployeeJobType getJob() {
        return job;
    }

    /**
     * Sets the value of the job property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmployeeJobType }
     *     
     */
    public void setJob(EmployeeJobType value) {
        this.job = value;
    }

}
