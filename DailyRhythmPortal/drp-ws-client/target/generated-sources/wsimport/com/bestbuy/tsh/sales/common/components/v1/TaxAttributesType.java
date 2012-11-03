
package com.bestbuy.tsh.sales.common.components.v1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.bestbuy.tsh.common.datatype.v1.TextType;


/**
 * TaxAttributesType
 * 
 * <p>Java class for TaxAttributesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TaxAttributesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TaxItems" type="{http://www.tsh.bestbuy.com/sales/common/components/v1}TaxItemType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="AdministrativeLocation" type="{http://www.tsh.bestbuy.com/sales/common/components/v1}TaxAreaType" minOccurs="0"/>
 *         &lt;element name="FulfillingLocation" type="{http://www.tsh.bestbuy.com/sales/common/components/v1}TaxAreaType" minOccurs="0"/>
 *         &lt;element name="ShipToLocation" type="{http://www.tsh.bestbuy.com/sales/common/components/v1}ShipToLocationType" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}RPIMFlag" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}PimCode" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaxAttributesType", propOrder = {
    "taxItems",
    "administrativeLocation",
    "fulfillingLocation",
    "shipToLocation",
    "rpimFlag",
    "pimCode"
})
public class TaxAttributesType {

    @XmlElement(name = "TaxItems")
    protected List<TaxItemType> taxItems;
    @XmlElement(name = "AdministrativeLocation")
    protected TaxAreaType administrativeLocation;
    @XmlElement(name = "FulfillingLocation")
    protected TaxAreaType fulfillingLocation;
    @XmlElement(name = "ShipToLocation")
    protected ShipToLocationType shipToLocation;
    @XmlElement(name = "RPIMFlag", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected Boolean rpimFlag;
    @XmlElement(name = "PimCode", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType pimCode;

    /**
     * Gets the value of the taxItems property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the taxItems property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTaxItems().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TaxItemType }
     * 
     * 
     */
    public List<TaxItemType> getTaxItems() {
        if (taxItems == null) {
            taxItems = new ArrayList<TaxItemType>();
        }
        return this.taxItems;
    }

    /**
     * Gets the value of the administrativeLocation property.
     * 
     * @return
     *     possible object is
     *     {@link TaxAreaType }
     *     
     */
    public TaxAreaType getAdministrativeLocation() {
        return administrativeLocation;
    }

    /**
     * Sets the value of the administrativeLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxAreaType }
     *     
     */
    public void setAdministrativeLocation(TaxAreaType value) {
        this.administrativeLocation = value;
    }

    /**
     * Gets the value of the fulfillingLocation property.
     * 
     * @return
     *     possible object is
     *     {@link TaxAreaType }
     *     
     */
    public TaxAreaType getFulfillingLocation() {
        return fulfillingLocation;
    }

    /**
     * Sets the value of the fulfillingLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxAreaType }
     *     
     */
    public void setFulfillingLocation(TaxAreaType value) {
        this.fulfillingLocation = value;
    }

    /**
     * Gets the value of the shipToLocation property.
     * 
     * @return
     *     possible object is
     *     {@link ShipToLocationType }
     *     
     */
    public ShipToLocationType getShipToLocation() {
        return shipToLocation;
    }

    /**
     * Sets the value of the shipToLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipToLocationType }
     *     
     */
    public void setShipToLocation(ShipToLocationType value) {
        this.shipToLocation = value;
    }

    /**
     * This element holds RPIMFlag
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRPIMFlag() {
        return rpimFlag;
    }

    /**
     * Sets the value of the rpimFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRPIMFlag(Boolean value) {
        this.rpimFlag = value;
    }

    /**
     * This element holds PimCode
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getPimCode() {
        return pimCode;
    }

    /**
     * Sets the value of the pimCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setPimCode(TextType value) {
        this.pimCode = value;
    }

}
