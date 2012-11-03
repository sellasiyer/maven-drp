
package com.bestbuy.bbym.ise.iseclienthub;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProtectionPlanAuthorizeRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProtectionPlanAuthorizeRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ProtectionPlanID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SourceSystemCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AuthorizedUserInfo" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Scheme/V4}AuthorizeInformationType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="AuthorizedSeatsInfo" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Scheme/V4}AuthorizeSeatsInformationType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProtectionPlanAuthorizeRequestType", namespace = "http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Service/V4", propOrder = {
    "protectionPlanID",
    "sourceSystemCode",
    "authorizedUserInfo",
    "authorizedSeatsInfo"
})
public class ProtectionPlanAuthorizeRequestType {

    @XmlElement(name = "ProtectionPlanID")
    protected String protectionPlanID;
    @XmlElement(name = "SourceSystemCode")
    protected String sourceSystemCode;
    @XmlElement(name = "AuthorizedUserInfo")
    protected List<AuthorizeInformationType> authorizedUserInfo;
    @XmlElement(name = "AuthorizedSeatsInfo")
    protected AuthorizeSeatsInformationType authorizedSeatsInfo;

    /**
     * Gets the value of the protectionPlanID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProtectionPlanID() {
        return protectionPlanID;
    }

    /**
     * Sets the value of the protectionPlanID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProtectionPlanID(String value) {
        this.protectionPlanID = value;
    }

    /**
     * Gets the value of the sourceSystemCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceSystemCode() {
        return sourceSystemCode;
    }

    /**
     * Sets the value of the sourceSystemCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceSystemCode(String value) {
        this.sourceSystemCode = value;
    }

    /**
     * Gets the value of the authorizedUserInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the authorizedUserInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAuthorizedUserInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AuthorizeInformationType }
     * 
     * 
     */
    public List<AuthorizeInformationType> getAuthorizedUserInfo() {
        if (authorizedUserInfo == null) {
            authorizedUserInfo = new ArrayList<AuthorizeInformationType>();
        }
        return this.authorizedUserInfo;
    }

    /**
     * Gets the value of the authorizedSeatsInfo property.
     * 
     * @return
     *     possible object is
     *     {@link AuthorizeSeatsInformationType }
     *     
     */
    public AuthorizeSeatsInformationType getAuthorizedSeatsInfo() {
        return authorizedSeatsInfo;
    }

    /**
     * Sets the value of the authorizedSeatsInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthorizeSeatsInformationType }
     *     
     */
    public void setAuthorizedSeatsInfo(AuthorizeSeatsInformationType value) {
        this.authorizedSeatsInfo = value;
    }

}
