
package com.bestbuy.bbym.ise.iseclientacdsparam;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ResponseMetaDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResponseMetaDataType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tsh.bestbuy.com/common/metadata/components/v2}MessageMetatDataBaseType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/metadata/fields/v1}Environment" minOccurs="0"/>
 *         &lt;element name="ResponseTimeStamp" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResponseMetaDataType", namespace = "http://www.tsh.bestbuy.com/common/metadata/components/v2", propOrder = {
    "environment",
    "responseTimeStamp"
})
public class ResponseMetaDataType
    extends MessageMetatDataBaseType
{

    @XmlElement(name = "Environment", namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1")
    protected String environment;
    @XmlElement(name = "ResponseTimeStamp")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar responseTimeStamp;

    /**
     * This  element (of type String) defines environment type which is used to process the message. 
     * 			
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnvironment() {
        return environment;
    }

    /**
     * Sets the value of the environment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnvironment(String value) {
        this.environment = value;
    }

    /**
     * Gets the value of the responseTimeStamp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getResponseTimeStamp() {
        return responseTimeStamp;
    }

    /**
     * Sets the value of the responseTimeStamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setResponseTimeStamp(XMLGregorianCalendar value) {
        this.responseTimeStamp = value;
    }

}
