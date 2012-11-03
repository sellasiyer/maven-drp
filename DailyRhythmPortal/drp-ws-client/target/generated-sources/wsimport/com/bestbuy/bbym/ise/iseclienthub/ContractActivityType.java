
package com.bestbuy.bbym.ise.iseclienthub;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ContractActivityType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContractActivityType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SysTxnId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SrcSysCd" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="UserNm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BusFuncNm" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SysTxnTs" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="MachineNm" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SessionId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TblNm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EntityId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EntitySeqNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ElementNm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Old" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="New" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContractActivityType", propOrder = {
    "sysTxnId",
    "srcSysCd",
    "userNm",
    "busFuncNm",
    "sysTxnTs",
    "machineNm",
    "sessionId",
    "tblNm",
    "entityId",
    "entitySeqNum",
    "elementNm",
    "old",
    "_new"
})
public class ContractActivityType {

    @XmlElement(name = "SysTxnId")
    protected String sysTxnId;
    @XmlElement(name = "SrcSysCd", required = true, nillable = true)
    protected String srcSysCd;
    @XmlElement(name = "UserNm")
    protected String userNm;
    @XmlElement(name = "BusFuncNm", required = true, nillable = true)
    protected String busFuncNm;
    @XmlElement(name = "SysTxnTs", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar sysTxnTs;
    @XmlElement(name = "MachineNm", required = true, nillable = true)
    protected String machineNm;
    @XmlElement(name = "SessionId", required = true, nillable = true)
    protected String sessionId;
    @XmlElement(name = "TblNm")
    protected String tblNm;
    @XmlElement(name = "EntityId")
    protected String entityId;
    @XmlElement(name = "EntitySeqNum")
    protected String entitySeqNum;
    @XmlElement(name = "ElementNm")
    protected String elementNm;
    @XmlElement(name = "Old")
    protected String old;
    @XmlElement(name = "New")
    protected String _new;
    @XmlAttribute
    protected String type;

    /**
     * Gets the value of the sysTxnId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSysTxnId() {
        return sysTxnId;
    }

    /**
     * Sets the value of the sysTxnId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSysTxnId(String value) {
        this.sysTxnId = value;
    }

    /**
     * Gets the value of the srcSysCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSrcSysCd() {
        return srcSysCd;
    }

    /**
     * Sets the value of the srcSysCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSrcSysCd(String value) {
        this.srcSysCd = value;
    }

    /**
     * Gets the value of the userNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserNm() {
        return userNm;
    }

    /**
     * Sets the value of the userNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserNm(String value) {
        this.userNm = value;
    }

    /**
     * Gets the value of the busFuncNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusFuncNm() {
        return busFuncNm;
    }

    /**
     * Sets the value of the busFuncNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusFuncNm(String value) {
        this.busFuncNm = value;
    }

    /**
     * Gets the value of the sysTxnTs property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSysTxnTs() {
        return sysTxnTs;
    }

    /**
     * Sets the value of the sysTxnTs property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSysTxnTs(XMLGregorianCalendar value) {
        this.sysTxnTs = value;
    }

    /**
     * Gets the value of the machineNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMachineNm() {
        return machineNm;
    }

    /**
     * Sets the value of the machineNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMachineNm(String value) {
        this.machineNm = value;
    }

    /**
     * Gets the value of the sessionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Sets the value of the sessionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSessionId(String value) {
        this.sessionId = value;
    }

    /**
     * Gets the value of the tblNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTblNm() {
        return tblNm;
    }

    /**
     * Sets the value of the tblNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTblNm(String value) {
        this.tblNm = value;
    }

    /**
     * Gets the value of the entityId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntityId() {
        return entityId;
    }

    /**
     * Sets the value of the entityId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntityId(String value) {
        this.entityId = value;
    }

    /**
     * Gets the value of the entitySeqNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntitySeqNum() {
        return entitySeqNum;
    }

    /**
     * Sets the value of the entitySeqNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntitySeqNum(String value) {
        this.entitySeqNum = value;
    }

    /**
     * Gets the value of the elementNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getElementNm() {
        return elementNm;
    }

    /**
     * Sets the value of the elementNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setElementNm(String value) {
        this.elementNm = value;
    }

    /**
     * Gets the value of the old property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOld() {
        return old;
    }

    /**
     * Sets the value of the old property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOld(String value) {
        this.old = value;
    }

    /**
     * Gets the value of the new property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNew() {
        return _new;
    }

    /**
     * Sets the value of the new property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNew(String value) {
        this._new = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

}
