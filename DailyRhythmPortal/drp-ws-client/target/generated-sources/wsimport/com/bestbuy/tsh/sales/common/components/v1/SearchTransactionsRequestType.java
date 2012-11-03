
package com.bestbuy.tsh.sales.common.components.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.bestbuy.tsh.common.datatype.v1.UserAreaType;
import com.bestbuy.tsh.common.metadata.components.v1.InternationalBusinessHierarchyType;


/**
 * SearchTransactionsRequestType
 * 
 * <p>Java class for SearchTransactionsRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SearchTransactionsRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MetaData" type="{http://www.tsh.bestbuy.com/sales/common/components/v1}RequestMetaDataExtensionType" minOccurs="0"/>
 *         &lt;element name="InternationBusinessHierarcy" type="{http://www.tsh.bestbuy.com/common/metadata/components/v1}InternationalBusinessHierarchyType" minOccurs="0"/>
 *         &lt;element name="SearchConfig" type="{http://www.tsh.bestbuy.com/sales/common/components/v1}SearchConfigType"/>
 *         &lt;element name="SearchParameter" type="{http://www.tsh.bestbuy.com/sales/common/components/v1}SearchParameterType"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/v1}UserArea" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute ref="{http://www.tsh.bestbuy.com/common/v1}languageCode"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchTransactionsRequestType", propOrder = {
    "metaData",
    "internationBusinessHierarcy",
    "searchConfig",
    "searchParameter",
    "userArea"
})
public class SearchTransactionsRequestType {

    @XmlElement(name = "MetaData")
    protected RequestMetaDataExtensionType metaData;
    @XmlElement(name = "InternationBusinessHierarcy")
    protected InternationalBusinessHierarchyType internationBusinessHierarcy;
    @XmlElement(name = "SearchConfig", required = true)
    protected SearchConfigType searchConfig;
    @XmlElement(name = "SearchParameter", required = true)
    protected SearchParameterType searchParameter;
    @XmlElement(name = "UserArea", namespace = "http://www.tsh.bestbuy.com/common/v1")
    protected UserAreaType userArea;
    @XmlAttribute(namespace = "http://www.tsh.bestbuy.com/common/v1")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String languageCode;

    /**
     * Gets the value of the metaData property.
     * 
     * @return
     *     possible object is
     *     {@link RequestMetaDataExtensionType }
     *     
     */
    public RequestMetaDataExtensionType getMetaData() {
        return metaData;
    }

    /**
     * Sets the value of the metaData property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestMetaDataExtensionType }
     *     
     */
    public void setMetaData(RequestMetaDataExtensionType value) {
        this.metaData = value;
    }

    /**
     * Gets the value of the internationBusinessHierarcy property.
     * 
     * @return
     *     possible object is
     *     {@link InternationalBusinessHierarchyType }
     *     
     */
    public InternationalBusinessHierarchyType getInternationBusinessHierarcy() {
        return internationBusinessHierarcy;
    }

    /**
     * Sets the value of the internationBusinessHierarcy property.
     * 
     * @param value
     *     allowed object is
     *     {@link InternationalBusinessHierarchyType }
     *     
     */
    public void setInternationBusinessHierarcy(InternationalBusinessHierarchyType value) {
        this.internationBusinessHierarcy = value;
    }

    /**
     * Gets the value of the searchConfig property.
     * 
     * @return
     *     possible object is
     *     {@link SearchConfigType }
     *     
     */
    public SearchConfigType getSearchConfig() {
        return searchConfig;
    }

    /**
     * Sets the value of the searchConfig property.
     * 
     * @param value
     *     allowed object is
     *     {@link SearchConfigType }
     *     
     */
    public void setSearchConfig(SearchConfigType value) {
        this.searchConfig = value;
    }

    /**
     * Gets the value of the searchParameter property.
     * 
     * @return
     *     possible object is
     *     {@link SearchParameterType }
     *     
     */
    public SearchParameterType getSearchParameter() {
        return searchParameter;
    }

    /**
     * Sets the value of the searchParameter property.
     * 
     * @param value
     *     allowed object is
     *     {@link SearchParameterType }
     *     
     */
    public void setSearchParameter(SearchParameterType value) {
        this.searchParameter = value;
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

    /**
     * Gets the value of the languageCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * Sets the value of the languageCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguageCode(String value) {
        this.languageCode = value;
    }

}
