
package com.bestbuy.tsh.sales.common.components.v1;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import com.bestbuy.tsh.common.metadata.components.v1.RequestMetaDataType;


/**
 * RequestMetaDataExtensionType
 * 
 * <p>Java class for RequestMetaDataExtensionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestMetaDataExtensionType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tsh.bestbuy.com/common/metadata/components/v1}RequestMetaDataType">
 *       &lt;sequence>
 *         &lt;element name="MaximumResponseCount" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestMetaDataExtensionType", propOrder = {
    "maximumResponseCount"
})
public class RequestMetaDataExtensionType
    extends RequestMetaDataType
{

    @XmlElement(name = "MaximumResponseCount", required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger maximumResponseCount;

    /**
     * Gets the value of the maximumResponseCount property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMaximumResponseCount() {
        return maximumResponseCount;
    }

    /**
     * Sets the value of the maximumResponseCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMaximumResponseCount(BigInteger value) {
        this.maximumResponseCount = value;
    }

}
