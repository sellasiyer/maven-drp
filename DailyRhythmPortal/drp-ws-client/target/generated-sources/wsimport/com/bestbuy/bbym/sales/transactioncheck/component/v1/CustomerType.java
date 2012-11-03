
package com.bestbuy.bbym.sales.transactioncheck.component.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.bestbuy.tsh.common.party.v1.PartyType;


/**
 * <p>Java class for CustomerType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustomerType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tsh.bestbuy.com/common/party/v1}PartyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://bestbuy.com/bbym/sales/transactioncheck/fields/v1}Qualification" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute ref="{http://www.tsh.bestbuy.com/common/v1}languagePreference"/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerType", propOrder = {
    "qualification"
})
public class CustomerType
    extends PartyType
{

    @XmlElement(name = "Qualification", namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String qualification;
    @XmlAttribute(namespace = "http://www.tsh.bestbuy.com/common/v1")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String languagePreference;

    /**
     *  Customer Qualification 
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQualification() {
        return qualification;
    }

    /**
     * Sets the value of the qualification property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQualification(String value) {
        this.qualification = value;
    }

    /**
     * Gets the value of the languagePreference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguagePreference() {
        return languagePreference;
    }

    /**
     * Sets the value of the languagePreference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguagePreference(String value) {
        this.languagePreference = value;
    }

}
