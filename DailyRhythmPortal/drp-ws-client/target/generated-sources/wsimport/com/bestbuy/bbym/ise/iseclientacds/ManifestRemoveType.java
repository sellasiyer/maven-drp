
package com.bestbuy.bbym.ise.iseclientacds;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ManifestRemoveType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ManifestRemoveType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ManifestRequest" type="{http://bestbuy.com/bbym/logistics/manifest/fields/v1}ManifestRemoveLineType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ManifestRemoveType", namespace = "http://bestbuy.com/bbym/logistics/manifest/fields/v1", propOrder = {
    "manifestRequest"
})
public class ManifestRemoveType {

    @XmlElement(name = "ManifestRequest", required = true)
    protected List<ManifestRemoveLineType> manifestRequest;

    /**
     * Gets the value of the manifestRequest property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the manifestRequest property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getManifestRequest().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ManifestRemoveLineType }
     * 
     * 
     */
    public List<ManifestRemoveLineType> getManifestRequest() {
        if (manifestRequest == null) {
            manifestRequest = new ArrayList<ManifestRemoveLineType>();
        }
        return this.manifestRequest;
    }

}
