
package com.bestbuy.tsh.common.metadata.components.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *     &lt;extension base="{http://www.tsh.bestbuy.com/common/metadata/components/v1}MessageMetatDataBaseType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/metadata/fields/v1}Environment" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/metadata/fields/v1}ResponseTimeStamp" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResponseMetaDataType", propOrder = {
    "environment",
    "responseTimeStamp"
})
public class ResponseMetaDataType
    extends MessageMetatDataBaseType
{

    @XmlElement(name = "Environment", namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1")
    protected String environment;
    @XmlElement(name = "ResponseTimeStamp", namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1")
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
     * is the date time stamp that the given instance of the message was created in response to original MessageID. 
     * 			
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
