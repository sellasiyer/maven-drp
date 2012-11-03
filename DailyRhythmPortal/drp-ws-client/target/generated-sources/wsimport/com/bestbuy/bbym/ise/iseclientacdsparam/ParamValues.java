
package com.bestbuy.bbym.ise.iseclientacdsparam;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ParamValues complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ParamValues">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ParamValue" type="{http://bestbuy.com/bbym/logistics/param/fields/v1}ParamType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParamValues", namespace = "http://bestbuy.com/bbym/logistics/param/fields/v1", propOrder = {
    "paramValue"
})
public class ParamValues {

    @XmlElement(name = "ParamValue")
    protected List<ParamType> paramValue;

    /**
     * Gets the value of the paramValue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the paramValue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParamValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ParamType }
     * 
     * 
     */
    public List<ParamType> getParamValue() {
        if (paramValue == null) {
            paramValue = new ArrayList<ParamType>();
        }
        return this.paramValue;
    }

}
