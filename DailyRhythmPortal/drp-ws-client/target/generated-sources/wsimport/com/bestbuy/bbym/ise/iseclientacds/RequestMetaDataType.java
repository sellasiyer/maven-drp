
package com.bestbuy.bbym.ise.iseclientacds;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RequestMetaDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestMetaDataType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tsh.bestbuy.com/common/metadata/components/v2}MessageMetatDataBaseType">
 *       &lt;sequence>
 *         &lt;element name="RequestTimeStamp" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestMetaDataType", namespace = "http://www.tsh.bestbuy.com/common/metadata/components/v2", propOrder = {
    "requestTimeStamp"
})
public class RequestMetaDataType
    extends MessageMetatDataBaseType
{

    @XmlElement(name = "RequestTimeStamp")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar requestTimeStamp;

    /**
     * Gets the value of the requestTimeStamp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRequestTimeStamp() {
        return requestTimeStamp;
    }

    /**
     * Sets the value of the requestTimeStamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRequestTimeStamp(XMLGregorianCalendar value) {
        this.requestTimeStamp = value;
    }

}
