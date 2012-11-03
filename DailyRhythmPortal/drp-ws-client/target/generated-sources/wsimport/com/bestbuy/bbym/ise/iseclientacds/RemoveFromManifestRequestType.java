
package com.bestbuy.bbym.ise.iseclientacds;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Defines the objects and structure for removing items from a manifest.
 * 
 * <p>Java class for RemoveFromManifestRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RemoveFromManifestRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RequestMetaData" type="{http://www.tsh.bestbuy.com/common/metadata/components/v2}RequestMetaDataType" minOccurs="0"/>
 *         &lt;element name="InternationBusinessHierarchy" type="{http://www.tsh.bestbuy.com/common/metadata/components/v2}InternationalBusinessHierarchyType"/>
 *         &lt;element name="RemoveLines" type="{http://bestbuy.com/bbym/logistics/manifest/fields/v1}ManifestRemoveType"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/v1}UserArea" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RemoveFromManifestRequestType", namespace = "http://bestbuy.com/bbym/logistics/manifest/service/v1", propOrder = {
    "requestMetaData",
    "internationBusinessHierarchy",
    "removeLines",
    "userArea"
})
public class RemoveFromManifestRequestType {

    @XmlElement(name = "RequestMetaData")
    protected RequestMetaDataType requestMetaData;
    @XmlElement(name = "InternationBusinessHierarchy", required = true)
    protected InternationalBusinessHierarchyType internationBusinessHierarchy;
    @XmlElement(name = "RemoveLines", required = true)
    protected ManifestRemoveType removeLines;
    @XmlElement(name = "UserArea", namespace = "http://www.tsh.bestbuy.com/common/v1")
    protected UserAreaType userArea;

    /**
     * Gets the value of the requestMetaData property.
     * 
     * @return
     *     possible object is
     *     {@link RequestMetaDataType }
     *     
     */
    public RequestMetaDataType getRequestMetaData() {
        return requestMetaData;
    }

    /**
     * Sets the value of the requestMetaData property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestMetaDataType }
     *     
     */
    public void setRequestMetaData(RequestMetaDataType value) {
        this.requestMetaData = value;
    }

    /**
     * Gets the value of the internationBusinessHierarchy property.
     * 
     * @return
     *     possible object is
     *     {@link InternationalBusinessHierarchyType }
     *     
     */
    public InternationalBusinessHierarchyType getInternationBusinessHierarchy() {
        return internationBusinessHierarchy;
    }

    /**
     * Sets the value of the internationBusinessHierarchy property.
     * 
     * @param value
     *     allowed object is
     *     {@link InternationalBusinessHierarchyType }
     *     
     */
    public void setInternationBusinessHierarchy(InternationalBusinessHierarchyType value) {
        this.internationBusinessHierarchy = value;
    }

    /**
     * Gets the value of the removeLines property.
     * 
     * @return
     *     possible object is
     *     {@link ManifestRemoveType }
     *     
     */
    public ManifestRemoveType getRemoveLines() {
        return removeLines;
    }

    /**
     * Sets the value of the removeLines property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManifestRemoveType }
     *     
     */
    public void setRemoveLines(ManifestRemoveType value) {
        this.removeLines = value;
    }

    /**
     * Allows the Tag Super Highway schema data model to extend the specification in order to provide additional information that is not captured in Canonical Schema.
     * 
     * @return
     *     possible object is
     *     {@link UserAreaType }
     *     
     */
    public UserAreaType getUserArea() {
        return userArea;
    }

    /**
     * Sets the value of the userArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserAreaType }
     *     
     */
    public void setUserArea(UserAreaType value) {
        this.userArea = value;
    }

}
