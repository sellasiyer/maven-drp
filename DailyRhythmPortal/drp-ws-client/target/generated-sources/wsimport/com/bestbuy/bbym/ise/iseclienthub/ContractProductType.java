
package com.bestbuy.bbym.ise.iseclienthub;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ContractProductType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContractProductType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Common/V4}ProductInformationType">
 *       &lt;sequence>
 *         &lt;element name="CoverageStartDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="CoverageEndDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="RegistrationDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="ContractProductDetail" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Scheme/V4}ContractProductDetailType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="objectID" type="{http://www.w3.org/2001/XMLSchema}long" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContractProductType", propOrder = {
    "coverageStartDate",
    "coverageEndDate",
    "registrationDate",
    "contractProductDetail"
})
public class ContractProductType
    extends ProductInformationType
{

    @XmlElement(name = "CoverageStartDate", required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar coverageStartDate;
    @XmlElement(name = "CoverageEndDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar coverageEndDate;
    @XmlElement(name = "RegistrationDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar registrationDate;
    @XmlElement(name = "ContractProductDetail")
    protected ContractProductDetailType contractProductDetail;
    @XmlAttribute
    protected Long objectID;

    /**
     * Gets the value of the coverageStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCoverageStartDate() {
        return coverageStartDate;
    }

    /**
     * Sets the value of the coverageStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCoverageStartDate(XMLGregorianCalendar value) {
        this.coverageStartDate = value;
    }

    /**
     * Gets the value of the coverageEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCoverageEndDate() {
        return coverageEndDate;
    }

    /**
     * Sets the value of the coverageEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCoverageEndDate(XMLGregorianCalendar value) {
        this.coverageEndDate = value;
    }

    /**
     * Gets the value of the registrationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRegistrationDate() {
        return registrationDate;
    }

    /**
     * Sets the value of the registrationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRegistrationDate(XMLGregorianCalendar value) {
        this.registrationDate = value;
    }

    /**
     * Gets the value of the contractProductDetail property.
     * 
     * @return
     *     possible object is
     *     {@link ContractProductDetailType }
     *     
     */
    public ContractProductDetailType getContractProductDetail() {
        return contractProductDetail;
    }

    /**
     * Sets the value of the contractProductDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContractProductDetailType }
     *     
     */
    public void setContractProductDetail(ContractProductDetailType value) {
        this.contractProductDetail = value;
    }

    /**
     * Gets the value of the objectID property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getObjectID() {
        return objectID;
    }

    /**
     * Sets the value of the objectID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setObjectID(Long value) {
        this.objectID = value;
    }

}
