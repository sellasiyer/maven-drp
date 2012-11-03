
package com.bestbuy.bbym.sales.transactioncheck.component.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TransationSourceDetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransationSourceDetailType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://bestbuy.com/bbym/sales/transactioncheck/fields/v1}RepID" minOccurs="0"/>
 *         &lt;element ref="{http://bestbuy.com/bbym/sales/transactioncheck/fields/v1}AgentCode" minOccurs="0"/>
 *         &lt;element ref="{http://bestbuy.com/bbym/sales/transactioncheck/fields/v1}ManagerID" minOccurs="0"/>
 *         &lt;element ref="{http://bestbuy.com/bbym/sales/transactioncheck/fields/v1}LocationID" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransationSourceDetailType", propOrder = {
    "repID",
    "agentCode",
    "managerID",
    "locationID"
})
public class TransationSourceDetailType {

    @XmlElement(name = "RepID", namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1")
    protected String repID;
    @XmlElement(name = "AgentCode", namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1")
    protected String agentCode;
    @XmlElement(name = "ManagerID", namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1")
    protected String managerID;
    @XmlElement(name = "LocationID", namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1")
    protected String locationID;

    /**
     * The ID of the blue shirt or other agent that
     * 						assisted in this sale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRepID() {
        return repID;
    }

    /**
     * Sets the value of the repID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRepID(String value) {
        this.repID = value;
    }

    /**
     * Agent code is nothing but a dealerCode and will be
     * 						used for commission
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentCode() {
        return agentCode;
    }

    /**
     * Sets the value of the agentCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentCode(String value) {
        this.agentCode = value;
    }

    /**
     * The ID of the manager who supervises this transaction. 
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManagerID() {
        return managerID;
    }

    /**
     * Sets the value of the managerID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManagerID(String value) {
        this.managerID = value;
    }

    /**
     * Store or Warehouse ID
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocationID() {
        return locationID;
    }

    /**
     * Sets the value of the locationID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocationID(String value) {
        this.locationID = value;
    }

}
