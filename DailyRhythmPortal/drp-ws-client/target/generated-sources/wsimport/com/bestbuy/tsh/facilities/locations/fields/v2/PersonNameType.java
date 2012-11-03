
package com.bestbuy.tsh.facilities.locations.fields.v2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.bestbuy.tsh.common.datatype.v1.NameType;


/**
 * Name of the Employee includes the First Name,Last Name
 * 
 * <p>Java class for PersonNameType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PersonNameType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/v1}FirstName" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/v1}MiddleName" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/v1}LastName" minOccurs="0"/>
 *         &lt;element name="DisplayName" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}StringType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonNameType", propOrder = {
    "firstName",
    "middleName",
    "lastName",
    "displayName"
})
public class PersonNameType {

    @XmlElement(name = "FirstName", namespace = "http://www.tsh.bestbuy.com/common/v1")
    protected NameType firstName;
    @XmlElement(name = "MiddleName", namespace = "http://www.tsh.bestbuy.com/common/v1")
    protected NameType middleName;
    @XmlElement(name = "LastName", namespace = "http://www.tsh.bestbuy.com/common/v1")
    protected NameType lastName;
    @XmlElement(name = "DisplayName")
    protected String displayName;

    /**
     * A given name is a personal name that specifies and differentiates between 
     * 				members of a group of individuals, especially in a family, all of whose members usually share
     * 				the same family name (surname). 
     * 				A given name is a name given to a person, as opposed to an inherited one such as a family name.
     * 
     * @return
     *     possible object is
     *     {@link NameType }
     *     
     */
    public NameType getFirstName() {
        return firstName;
    }

    /**
     * Sets the value of the firstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameType }
     *     
     */
    public void setFirstName(NameType value) {
        this.firstName = value;
    }

    /**
     * People's names in several countries include one or more middle names, 
     * 				placed between the first given name and the surname. In most such countries, except
     * 				Northern America, this notion of middle name does not exist, and those names are considered 
     * 				as a second, third, etc. given name. ...
     * 
     * @return
     *     possible object is
     *     {@link NameType }
     *     
     */
    public NameType getMiddleName() {
        return middleName;
    }

    /**
     * Sets the value of the middleName property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameType }
     *     
     */
    public void setMiddleName(NameType value) {
        this.middleName = value;
    }

    /**
     *  family name (in Western contexts often referred to as a last name) is a type of surname and part of 
     * 				a person's name indicating the family to which the person belongs. The use of family names is widespread in 
     * 				cultures around the world. ...
     * 
     * @return
     *     possible object is
     *     {@link NameType }
     *     
     */
    public NameType getLastName() {
        return lastName;
    }

    /**
     * Sets the value of the lastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameType }
     *     
     */
    public void setLastName(NameType value) {
        this.lastName = value;
    }

    /**
     * Gets the value of the displayName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the value of the displayName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisplayName(String value) {
        this.displayName = value;
    }

}
