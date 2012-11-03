
package com.bestbuy.tsh.common.party.v1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.bestbuy.tsh.common.communication.v1.CommunicationBaseType;
import com.bestbuy.tsh.common.datatype.v1.IdentifierType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/v1}ID" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/communication/v1}Communication" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "id",
    "communication"
})
@XmlRootElement(name = "Contact")
public class Contact {

    @XmlElement(name = "ID", namespace = "http://www.tsh.bestbuy.com/common/v1")
    protected IdentifierType id;
    @XmlElement(name = "Communication", namespace = "http://www.tsh.bestbuy.com/common/communication/v1")
    protected List<CommunicationBaseType> communication;

    /**
     * Is the Identifiers of the given instance of an entity within the scope of the integration. 
     * 				The schemeAgencyID attribute identifies the party that provided or knows this party by the given identifier.
     * 			
     * 
     * @return
     *     possible object is
     *     {@link IdentifierType }
     *     
     */
    public IdentifierType getID() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentifierType }
     *     
     */
    public void setID(IdentifierType value) {
        this.id = value;
    }

    /**
     * Basic Communication methods Gets the value of the communication property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the communication property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCommunication().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CommunicationBaseType }
     * 
     * 
     */
    public List<CommunicationBaseType> getCommunication() {
        if (communication == null) {
            communication = new ArrayList<CommunicationBaseType>();
        }
        return this.communication;
    }

}
