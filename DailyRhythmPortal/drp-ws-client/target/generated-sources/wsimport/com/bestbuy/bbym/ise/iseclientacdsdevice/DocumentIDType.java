
package com.bestbuy.bbym.ise.iseclientacdsdevice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * This is the ID assigned to the document by the creator of the document.
 * 
 * <p>Java class for DocumentIDType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DocumentIDType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/v1}ID"/>
 *       &lt;/sequence>
 *       &lt;attribute name="agencyID" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}StringType" />
 *       &lt;attribute name="agencyName" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}StringType" />
 *       &lt;attribute name="agencyRole" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}StringType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentIDType", propOrder = {
    "id"
})
public class DocumentIDType {

    @XmlElement(name = "ID", required = true)
    protected IdentifierType id;
    @XmlAttribute
    protected String agencyID;
    @XmlAttribute
    protected String agencyName;
    @XmlAttribute
    protected String agencyRole;

    /**
     * Is the Identifiers of the given instance of an entity within the scope of the integration. 
     * 				The schemeAgencyID attribute identifies the party that provided or knows this party by the given identifier.
     * 			
     * 
     * @return
     *     possible object is
     *     {@link IdentifierType }
     *     
     */
    public IdentifierType getID() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentifierType }
     *     
     */
    public void setID(IdentifierType value) {
        this.id = value;
    }

    /**
     * Gets the value of the agencyID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgencyID() {
        return agencyID;
    }

    /**
     * Sets the value of the agencyID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgencyID(String value) {
        this.agencyID = value;
    }

    /**
     * Gets the value of the agencyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgencyName() {
        return agencyName;
    }

    /**
     * Sets the value of the agencyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgencyName(String value) {
        this.agencyName = value;
    }

    /**
     * Gets the value of the agencyRole property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgencyRole() {
        return agencyRole;
    }

    /**
     * Sets the value of the agencyRole property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgencyRole(String value) {
        this.agencyRole = value;
    }

}
