
package com.bestbuy.bbym.ise.iseclientacds;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GenerateManifestDocResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GenerateManifestDocResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ManifestDoc" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GenerateManifestDocResponseType", namespace = "http://bestbuy.com/bbym/logistics/manifest/fields/v1", propOrder = {
    "manifestDoc"
})
public class GenerateManifestDocResponseType {

    @XmlElement(name = "ManifestDoc", required = true)
    protected byte[] manifestDoc;

    /**
     * Gets the value of the manifestDoc property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getManifestDoc() {
        return manifestDoc;
    }

    /**
     * Sets the value of the manifestDoc property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setManifestDoc(byte[] value) {
        this.manifestDoc = ((byte[]) value);
    }

}
