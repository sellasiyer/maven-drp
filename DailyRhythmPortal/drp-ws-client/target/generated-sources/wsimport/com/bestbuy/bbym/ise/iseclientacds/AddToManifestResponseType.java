
package com.bestbuy.bbym.ise.iseclientacds;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Defines objects returned when requesting to add to a manifest.
 * 
 * <p>Java class for AddToManifestResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AddToManifestResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ResponseMetaData" type="{http://www.tsh.bestbuy.com/common/metadata/components/v2}ResponseMetaDataType" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/v1}Status"/>
 *         &lt;element name="ManifestLine" type="{http://bestbuy.com/bbym/logistics/manifest/fields/v1}ManifestLineItemType"/>
 *         &lt;element name="ValidationWarning" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "AddToManifestResponseType", namespace = "http://bestbuy.com/bbym/logistics/manifest/service/v1", propOrder = {
    "responseMetaData",
    "status",
    "manifestLine",
    "validationWarning",
    "userArea"
})
public class AddToManifestResponseType {

    @XmlElement(name = "ResponseMetaData")
    protected ResponseMetaDataType responseMetaData;
    @XmlElement(name = "Status", namespace = "http://www.tsh.bestbuy.com/common/v1", required = true)
    protected StatusType status;
    @XmlElement(name = "ManifestLine", required = true)
    protected ManifestLineItemType manifestLine;
    @XmlElement(name = "ValidationWarning")
    protected String validationWarning;
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
     * Gets the value of the manifestLine property.
     * 
     * @return
     *     possible object is
     *     {@link ManifestLineItemType }
     *     
     */
    public ManifestLineItemType getManifestLine() {
        return manifestLine;
    }

    /**
     * Sets the value of the manifestLine property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManifestLineItemType }
     *     
     */
    public void setManifestLine(ManifestLineItemType value) {
        this.manifestLine = value;
    }

    /**
     * Gets the value of the validationWarning property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidationWarning() {
        return validationWarning;
    }

    /**
     * Sets the value of the validationWarning property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidationWarning(String value) {
        this.validationWarning = value;
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
