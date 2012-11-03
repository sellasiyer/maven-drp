
package com.bestbuy.bbym.ise.iseclientca;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CustomerSearchConfigType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustomerSearchConfigType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MaxRows" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="CrossReferenceAppID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SearchTimeLimitMS" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="SearchTypeToPerform" type="{http://webservices.bestbuy.com/ca/services/entity/v2}AllSearchPlansType" minOccurs="0"/>
 *         &lt;element name="DiscardResult" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="PerformDrillDown" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="RetrieveForOrgID" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               &lt;enumeration value="100"/>
 *               &lt;enumeration value="120"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerSearchConfigType", propOrder = {
    "maxRows",
    "crossReferenceAppID",
    "searchTimeLimitMS",
    "searchTypeToPerform",
    "discardResult",
    "performDrillDown",
    "retrieveForOrgID"
})
public class CustomerSearchConfigType {

    @XmlElement(name = "MaxRows", defaultValue = "100")
    protected Integer maxRows;
    @XmlElement(name = "CrossReferenceAppID")
    protected String crossReferenceAppID;
    @XmlElement(name = "SearchTimeLimitMS", defaultValue = "3000")
    protected Integer searchTimeLimitMS;
    @XmlElement(name = "SearchTypeToPerform", defaultValue = "searchPlan7")
    protected AllSearchPlansType searchTypeToPerform;
    @XmlElement(name = "DiscardResult", defaultValue = "false")
    protected Boolean discardResult;
    @XmlElement(name = "PerformDrillDown", defaultValue = "false")
    protected Boolean performDrillDown;
    @XmlElement(name = "RetrieveForOrgID")
    protected Integer retrieveForOrgID;

    /**
     * Gets the value of the maxRows property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaxRows() {
        return maxRows;
    }

    /**
     * Sets the value of the maxRows property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxRows(Integer value) {
        this.maxRows = value;
    }

    /**
     * Gets the value of the crossReferenceAppID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCrossReferenceAppID() {
        return crossReferenceAppID;
    }

    /**
     * Sets the value of the crossReferenceAppID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCrossReferenceAppID(String value) {
        this.crossReferenceAppID = value;
    }

    /**
     * Gets the value of the searchTimeLimitMS property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSearchTimeLimitMS() {
        return searchTimeLimitMS;
    }

    /**
     * Sets the value of the searchTimeLimitMS property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSearchTimeLimitMS(Integer value) {
        this.searchTimeLimitMS = value;
    }

    /**
     * Gets the value of the searchTypeToPerform property.
     * 
     * @return
     *     possible object is
     *     {@link AllSearchPlansType }
     *     
     */
    public AllSearchPlansType getSearchTypeToPerform() {
        return searchTypeToPerform;
    }

    /**
     * Sets the value of the searchTypeToPerform property.
     * 
     * @param value
     *     allowed object is
     *     {@link AllSearchPlansType }
     *     
     */
    public void setSearchTypeToPerform(AllSearchPlansType value) {
        this.searchTypeToPerform = value;
    }

    /**
     * Gets the value of the discardResult property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDiscardResult() {
        return discardResult;
    }

    /**
     * Sets the value of the discardResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDiscardResult(Boolean value) {
        this.discardResult = value;
    }

    /**
     * Gets the value of the performDrillDown property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPerformDrillDown() {
        return performDrillDown;
    }

    /**
     * Sets the value of the performDrillDown property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPerformDrillDown(Boolean value) {
        this.performDrillDown = value;
    }

    /**
     * Gets the value of the retrieveForOrgID property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRetrieveForOrgID() {
        return retrieveForOrgID;
    }

    /**
     * Sets the value of the retrieveForOrgID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRetrieveForOrgID(Integer value) {
        this.retrieveForOrgID = value;
    }

}
