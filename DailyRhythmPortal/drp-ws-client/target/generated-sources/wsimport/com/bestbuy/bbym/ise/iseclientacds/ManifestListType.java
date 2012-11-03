
package com.bestbuy.bbym.ise.iseclientacds;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Collection of Manifests
 * 
 * <p>Java class for ManifestListType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ManifestListType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://bestbuy.com/bbym/logistics/manifest/fields/v1}Manifest" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ManifestListType", namespace = "http://bestbuy.com/bbym/logistics/manifest/fields/v1", propOrder = {
    "manifest"
})
public class ManifestListType {

    @XmlElement(name = "Manifest")
    protected List<ManifestHeaderType> manifest;

    /**
     * Gets the value of the manifest property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the manifest property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getManifest().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ManifestHeaderType }
     * 
     * 
     */
    public List<ManifestHeaderType> getManifest() {
        if (manifest == null) {
            manifest = new ArrayList<ManifestHeaderType>();
        }
        return this.manifest;
    }

}
