
package com.bestbuy.bbym.ise.iseclientacds;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Defines the objects contained in the response schema.
 * 
 * <p>Java class for SearchForManifestsResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SearchForManifestsResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ResponseMetaData" type="{http://www.tsh.bestbuy.com/common/metadata/components/v2}ResponseMetaDataType" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/v1}Status"/>
 *         &lt;element name="Manifests" type="{http://bestbuy.com/bbym/logistics/manifest/fields/v1}ManifestListType"/>
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
@XmlType(name = "SearchForManifestsResponseType", namespace = "http://bestbuy.com/bbym/logistics/manifest/service/v1", propOrder = {
    "responseMetaData",
    "status",
    "manifests",
    "userArea"
})
public class SearchForManifestsResponseType {

    @XmlElement(name = "ResponseMetaData")
    protected ResponseMetaDataType responseMetaData;
    @XmlElement(name = "Status", namespace = "http://www.tsh.bestbuy.com/common/v1", required = true)
    protected StatusType status;
    @XmlElement(name = "Manifests", required = true)
    protected ManifestListType manifests;
    @XmlElement(name = "UserArea", namespace = "http://www.tsh.bestbuy.com/common/v1")
    protected UserAreaType userArea;

    /**
     * Gets the value of the responseMetaData property.
     * 
     * @return
     *     possible object is
     *     {@link ResponseMetaDataType }
     *     
     */
    public ResponseMetaDataType getResponseMetaData() {
        return responseMetaData;
    }

    /**
     * Sets the value of the responseMetaData property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseMetaDataType }
     *     
     */
    public void setResponseMetaData(ResponseMetaDataType value) {
        this.responseMetaData = value;
    }

    /**
     *  Indicates the status of the associated object by providing the Status Code along with a description and when the status is effective.
     * 
     * @return
     *     possible object is
     *     {@link StatusType }
     *     
     */
    public StatusType getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusType }
     *     
     */
    public void setStatus(StatusType value) {
        this.status = value;
    }

    /**
     * Gets the value of the manifests property.
     * 
     * @return
     *     possible object is
     *     {@link ManifestListType }
     *     
     */
    public ManifestListType getManifests() {
        return manifests;
    }

    /**
     * Sets the value of the manifests property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManifestListType }
     *     
     */
    public void setManifests(ManifestListType value) {
        this.manifests = value;
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
