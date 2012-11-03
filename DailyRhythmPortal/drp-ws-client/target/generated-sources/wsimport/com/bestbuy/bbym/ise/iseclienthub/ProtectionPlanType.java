
package com.bestbuy.bbym.ise.iseclienthub;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProtectionPlanType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProtectionPlanType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Contract" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Scheme/V4}ContractType" minOccurs="0"/>
 *         &lt;element name="Product" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Scheme/V4}ContractProductType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Owner" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Common/V4}PersonType" minOccurs="0"/>
 *         &lt;element name="ID" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Scheme/V4}ProtectionPlanIDType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="type" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Scheme/V4}ProtectionPlanTypeEnum" />
 *       &lt;attribute name="category" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProtectionPlanType", propOrder = {
    "contract",
    "product",
    "owner",
    "id"
})
public class ProtectionPlanType {

    @XmlElement(name = "Contract")
    protected ContractType contract;
    @XmlElement(name = "Product")
    protected List<ContractProductType> product;
    @XmlElement(name = "Owner")
    protected PersonType owner;
    @XmlElement(name = "ID")
    protected ProtectionPlanIDType id;
    @XmlAttribute
    protected ProtectionPlanTypeEnum type;
    @XmlAttribute
    protected String category;

    /**
     * Gets the value of the contract property.
     * 
     * @return
     *     possible object is
     *     {@link ContractType }
     *     
     */
    public ContractType getContract() {
        return contract;
    }

    /**
     * Sets the value of the contract property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContractType }
     *     
     */
    public void setContract(ContractType value) {
        this.contract = value;
    }

    /**
     * Gets the value of the product property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the product property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProduct().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContractProductType }
     * 
     * 
     */
    public List<ContractProductType> getProduct() {
        if (product == null) {
            product = new ArrayList<ContractProductType>();
        }
        return this.product;
    }

    /**
     * Gets the value of the owner property.
     * 
     * @return
     *     possible object is
     *     {@link PersonType }
     *     
     */
    public PersonType getOwner() {
        return owner;
    }

    /**
     * Sets the value of the owner property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonType }
     *     
     */
    public void setOwner(PersonType value) {
        this.owner = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link ProtectionPlanIDType }
     *     
     */
    public ProtectionPlanIDType getID() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProtectionPlanIDType }
     *     
     */
    public void setID(ProtectionPlanIDType value) {
        this.id = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link ProtectionPlanTypeEnum }
     *     
     */
    public ProtectionPlanTypeEnum getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProtectionPlanTypeEnum }
     *     
     */
    public void setType(ProtectionPlanTypeEnum value) {
        this.type = value;
    }

    /**
     * Gets the value of the category property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the value of the category property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCategory(String value) {
        this.category = value;
    }

}
