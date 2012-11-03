
package com.bestbuy.tsh.common.party.v1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.bestbuy.tsh.common.datatype.v1.IdentifierType;
import com.bestbuy.tsh.common.datatype.v1.NameType;
import com.bestbuy.tsh.common.datatype.v1.TextType;
import com.bestbuy.tsh.common.datatype.v1.UserAreaType;


/**
 * 
 * 				This element can be used to describe party details, name, contact etc.
 * 			
 * 
 * <p>Java class for PartyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PartyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/party/v1}PartyIDs" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/party/v1}AccountID" minOccurs="0"/>
 *         &lt;group ref="{http://www.tsh.bestbuy.com/common/v1}PersonNameGroup" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/party/v1}Contact" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/v1}UserArea" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PartyType", propOrder = {
    "partyIDs",
    "accountID",
    "title",
    "salutationPreferredName",
    "firstName",
    "middleName",
    "lastName",
    "maidenName",
    "nickName",
    "prefix",
    "suffix",
    "contact",
    "userArea"
})
public class PartyType {

    @XmlElement(name = "PartyIDs")
    protected PartyIDs partyIDs;
    @XmlElement(name = "AccountID")
    protected IdentifierType accountID;
    @XmlElement(name = "Title", namespace = "http://www.tsh.bestbuy.com/common/v1")
    protected List<TextType> title;
    @XmlElement(name = "SalutationPreferredName", namespace = "http://www.tsh.bestbuy.com/common/v1")
    protected NameType salutationPreferredName;
    @XmlElement(name = "FirstName", namespace = "http://www.tsh.bestbuy.com/common/v1")
    protected NameType firstName;
    @XmlElement(name = "MiddleName", namespace = "http://www.tsh.bestbuy.com/common/v1")
    protected List<NameType> middleName;
    @XmlElement(name = "LastName", namespace = "http://www.tsh.bestbuy.com/common/v1")
    protected NameType lastName;
    @XmlElement(name = "MaidenName", namespace = "http://www.tsh.bestbuy.com/common/v1")
    protected NameType maidenName;
    @XmlElement(name = "NickName", namespace = "http://www.tsh.bestbuy.com/common/v1")
    protected List<NameType> nickName;
    @XmlElement(name = "Prefix", namespace = "http://www.tsh.bestbuy.com/common/v1")
    protected NameType prefix;
    @XmlElement(name = "Suffix", namespace = "http://www.tsh.bestbuy.com/common/v1")
    protected NameType suffix;
    @XmlElement(name = "Contact")
    protected List<Contact> contact;
    @XmlElement(name = "UserArea", namespace = "http://www.tsh.bestbuy.com/common/v1")
    protected UserAreaType userArea;

    /**
     * 
     * 				This element can be used for different IDs associated to a party.
     * 			
     * 
     * @return
     *     possible object is
     *     {@link PartyIDs }
     *     
     */
    public PartyIDs getPartyIDs() {
        return partyIDs;
    }

    /**
     * Sets the value of the partyIDs property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyIDs }
     *     
     */
    public void setPartyIDs(PartyIDs value) {
        this.partyIDs = value;
    }

    /**
     * Gets the value of the accountID property.
     * 
     * @return
     *     possible object is
     *     {@link IdentifierType }
     *     
     */
    public IdentifierType getAccountID() {
        return accountID;
    }

    /**
     * Sets the value of the accountID property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentifierType }
     *     
     */
    public void setAccountID(IdentifierType value) {
        this.accountID = value;
    }

    /**
     * A title is a prefix or suffix added to someone's name to signify either veneration, 
     * 				an official position or a professional or academic qualification. ...Gets the value of the title property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the title property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTitle().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TextType }
     * 
     * 
     */
    public List<TextType> getTitle() {
        if (title == null) {
            title = new ArrayList<TextType>();
        }
        return this.title;
    }

    /**
     * Way the person is addressed e.g. Honorable etc?
     * 
     * @return
     *     possible object is
     *     {@link NameType }
     *     
     */
    public NameType getSalutationPreferredName() {
        return salutationPreferredName;
    }

    /**
     * Sets the value of the salutationPreferredName property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameType }
     *     
     */
    public void setSalutationPreferredName(NameType value) {
        this.salutationPreferredName = value;
    }

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
     * 				as a second, third, etc. given name. ...Gets the value of the middleName property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the middleName property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMiddleName().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NameType }
     * 
     * 
     */
    public List<NameType> getMiddleName() {
        if (middleName == null) {
            middleName = new ArrayList<NameType>();
        }
        return this.middleName;
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
     * the original surname (last name) of a woman (as opposed to her married name).
     * 
     * @return
     *     possible object is
     *     {@link NameType }
     *     
     */
    public NameType getMaidenName() {
        return maidenName;
    }

    /**
     * Sets the value of the maidenName property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameType }
     *     
     */
    public void setMaidenName(NameType value) {
        this.maidenName = value;
    }

    /**
     * a familiar name for a person (often a shortened version of a person's given name) 
     * 				"Joe's mother would not use his nickname and always called him Joseph"; "Henry's nickname was Slim"Gets the value of the nickName property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nickName property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNickName().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NameType }
     * 
     * 
     */
    public List<NameType> getNickName() {
        if (nickName == null) {
            nickName = new ArrayList<NameType>();
        }
        return this.nickName;
    }

    /**
     * Prefixes precede the name and should not be included in he name field.  Prefixes include Mr., Mrs., Ms., Miss and Dr.
     * 			
     * 
     * @return
     *     possible object is
     *     {@link NameType }
     *     
     */
    public NameType getPrefix() {
        return prefix;
    }

    /**
     * Sets the value of the prefix property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameType }
     *     
     */
    public void setPrefix(NameType value) {
        this.prefix = value;
    }

    /**
     * A name suffix, in the Western English-language naming tradition, follows a person’s full name 
     * 				and provides additional information about the person. Post-nominal letters indicate that the individual holds a 
     * 				position, educational degree, accreditation, office or honour.
     * 			
     * 
     * @return
     *     possible object is
     *     {@link NameType }
     *     
     */
    public NameType getSuffix() {
        return suffix;
    }

    /**
     * Sets the value of the suffix property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameType }
     *     
     */
    public void setSuffix(NameType value) {
        this.suffix = value;
    }

    /**
     * 
     * 				This element can be used to describe contact information for a party.
     * 			Gets the value of the contact property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contact property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContact().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Contact }
     * 
     * 
     */
    public List<Contact> getContact() {
        if (contact == null) {
            contact = new ArrayList<Contact>();
        }
        return this.contact;
    }

    /**
     * Allows the Tag Super Highway schema data model to extend the specification in order to provide additional information that is not captured in Canonical Schema.
     * 
     * @return
     *     possible object is
     *     {@link UserAreaType }
     *     
     */
    public UserAreaType getUserArea() {
        return userArea;
    }

    /**
     * Sets the value of the userArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserAreaType }
     *     
     */
    public void setUserArea(UserAreaType value) {
        this.userArea = value;
    }

}
