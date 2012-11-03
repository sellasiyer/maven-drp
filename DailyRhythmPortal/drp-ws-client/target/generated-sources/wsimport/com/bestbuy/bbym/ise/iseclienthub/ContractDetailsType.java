
package com.bestbuy.bbym.ise.iseclienthub;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ContractDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContractDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ContractHours" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Scheme/V4}ContractHoursType" minOccurs="0"/>
 *         &lt;element name="ContractLicensing" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Scheme/V4}ContractLicensingType" minOccurs="0"/>
 *         &lt;element name="FulfillmentInformation" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Scheme/V4}FulfillmentInformationType" minOccurs="0"/>
 *         &lt;element name="ContractDeductions" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Scheme/V4}ContractDeductionsType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContractDetailsType", propOrder = {
    "contractHours",
    "contractLicensing",
    "fulfillmentInformation",
    "contractDeductions"
})
public class ContractDetailsType {

    @XmlElement(name = "ContractHours")
    protected ContractHoursType contractHours;
    @XmlElement(name = "ContractLicensing")
    protected ContractLicensingType contractLicensing;
    @XmlElement(name = "FulfillmentInformation")
    protected FulfillmentInformationType fulfillmentInformation;
    @XmlElement(name = "ContractDeductions")
    protected List<ContractDeductionsType> contractDeductions;

    /**
     * Gets the value of the contractHours property.
     * 
     * @return
     *     possible object is
     *     {@link ContractHoursType }
     *     
     */
    public ContractHoursType getContractHours() {
        return contractHours;
    }

    /**
     * Sets the value of the contractHours property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContractHoursType }
     *     
     */
    public void setContractHours(ContractHoursType value) {
        this.contractHours = value;
    }

    /**
     * Gets the value of the contractLicensing property.
     * 
     * @return
     *     possible object is
     *     {@link ContractLicensingType }
     *     
     */
    public ContractLicensingType getContractLicensing() {
        return contractLicensing;
    }

    /**
     * Sets the value of the contractLicensing property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContractLicensingType }
     *     
     */
    public void setContractLicensing(ContractLicensingType value) {
        this.contractLicensing = value;
    }

    /**
     * Gets the value of the fulfillmentInformation property.
     * 
     * @return
     *     possible object is
     *     {@link FulfillmentInformationType }
     *     
     */
    public FulfillmentInformationType getFulfillmentInformation() {
        return fulfillmentInformation;
    }

    /**
     * Sets the value of the fulfillmentInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link FulfillmentInformationType }
     *     
     */
    public void setFulfillmentInformation(FulfillmentInformationType value) {
        this.fulfillmentInformation = value;
    }

    /**
     * Gets the value of the contractDeductions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contractDeductions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContractDeductions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContractDeductionsType }
     * 
     * 
     */
    public List<ContractDeductionsType> getContractDeductions() {
        if (contractDeductions == null) {
            contractDeductions = new ArrayList<ContractDeductionsType>();
        }
        return this.contractDeductions;
    }

}
