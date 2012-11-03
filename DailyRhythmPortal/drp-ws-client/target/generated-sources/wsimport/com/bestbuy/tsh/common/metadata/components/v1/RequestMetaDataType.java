
package com.bestbuy.tsh.common.metadata.components.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *     &lt;extension base="{http://www.tsh.bestbuy.com/common/metadata/components/v1}MessageMetatDataBaseType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/metadata/fields/v1}RequestTimeStamp" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestMetaDataType", propOrder = {
    "requestTimeStamp"
})
public class RequestMetaDataType
    extends MessageMetatDataBaseType
{

    @XmlElement(name = "RequestTimeStamp", namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1")
    protected XMLGregorianCalendar requestTimeStamp;

    /**
     * is the date time stamp that the given instance of the message was created by originating system. 
     * 			
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
