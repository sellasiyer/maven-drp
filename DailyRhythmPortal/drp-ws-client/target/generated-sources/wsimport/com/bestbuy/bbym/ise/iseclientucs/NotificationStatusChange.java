
package com.bestbuy.bbym.ise.iseclientucs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for notificationStatusChange complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="notificationStatusChange">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="notf_stat_chg_id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="cust_id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="notf_stat_chg_dt" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="usr_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="loc_id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="intl_bsns_hier_id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="notf_stat_cde" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="source_system" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "notificationStatusChange", propOrder = {
    "notfStatChgId",
    "custId",
    "notfStatChgDt",
    "usrId",
    "locId",
    "intlBsnsHierId",
    "notfStatCde",
    "sourceSystem"
})
public class NotificationStatusChange {

    @XmlElement(name = "notf_stat_chg_id")
    protected long notfStatChgId;
    @XmlElement(name = "cust_id")
    protected long custId;
    @XmlElement(name = "notf_stat_chg_dt")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar notfStatChgDt;
    @XmlElement(name = "usr_id")
    protected String usrId;
    @XmlElement(name = "loc_id")
    protected int locId;
    @XmlElement(name = "intl_bsns_hier_id")
    protected long intlBsnsHierId;
    @XmlElement(name = "notf_stat_cde")
    protected String notfStatCde;
    @XmlElement(name = "source_system")
    protected String sourceSystem;

    /**
     * Gets the value of the notfStatChgId property.
     * 
     */
    public long getNotfStatChgId() {
        return notfStatChgId;
    }

    /**
     * Sets the value of the notfStatChgId property.
     * 
     */
    public void setNotfStatChgId(long value) {
        this.notfStatChgId = value;
    }

    /**
     * Gets the value of the custId property.
     * 
     */
    public long getCustId() {
        return custId;
    }

    /**
     * Sets the value of the custId property.
     * 
     */
    public void setCustId(long value) {
        this.custId = value;
    }

    /**
     * Gets the value of the notfStatChgDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getNotfStatChgDt() {
        return notfStatChgDt;
    }

    /**
     * Sets the value of the notfStatChgDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setNotfStatChgDt(XMLGregorianCalendar value) {
        this.notfStatChgDt = value;
    }

    /**
     * Gets the value of the usrId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsrId() {
        return usrId;
    }

    /**
     * Sets the value of the usrId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsrId(String value) {
        this.usrId = value;
    }

    /**
     * Gets the value of the locId property.
     * 
     */
    public int getLocId() {
        return locId;
    }

    /**
     * Sets the value of the locId property.
     * 
     */
    public void setLocId(int value) {
        this.locId = value;
    }

    /**
     * Gets the value of the intlBsnsHierId property.
     * 
     */
    public long getIntlBsnsHierId() {
        return intlBsnsHierId;
    }

    /**
     * Sets the value of the intlBsnsHierId property.
     * 
     */
    public void setIntlBsnsHierId(long value) {
        this.intlBsnsHierId = value;
    }

    /**
     * Gets the value of the notfStatCde property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotfStatCde() {
        return notfStatCde;
    }

    /**
     * Sets the value of the notfStatCde property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotfStatCde(String value) {
        this.notfStatCde = value;
    }

    /**
     * Gets the value of the sourceSystem property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceSystem() {
        return sourceSystem;
    }

    /**
     * Sets the value of the sourceSystem property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceSystem(String value) {
        this.sourceSystem = value;
    }

}
