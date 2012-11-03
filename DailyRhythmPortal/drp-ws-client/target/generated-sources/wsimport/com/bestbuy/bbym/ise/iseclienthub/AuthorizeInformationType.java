
package com.bestbuy.bbym.ise.iseclienthub;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AuthorizeInformationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AuthorizeInformationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ActionCode" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Scheme/V4}ActionCodeEnum"/>
 *         &lt;element name="AuthorizedUser" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Scheme/V4}AuthorizeUserType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuthorizeInformationType", propOrder = {
    "actionCode",
    "authorizedUser"
})
public class AuthorizeInformationType {

    @XmlElement(name = "ActionCode", required = true)
    protected ActionCodeEnum actionCode;
    @XmlElement(name = "AuthorizedUser")
    protected AuthorizeUserType authorizedUser;

    /**
     * Gets the value of the actionCode property.
     * 
     * @return
     *     possible object is
     *     {@link ActionCodeEnum }
     *     
     */
    public ActionCodeEnum getActionCode() {
        return actionCode;
    }

    /**
     * Sets the value of the actionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActionCodeEnum }
     *     
     */
    public void setActionCode(ActionCodeEnum value) {
        this.actionCode = value;
    }

    /**
     * Gets the value of the authorizedUser property.
     * 
     * @return
     *     possible object is
     *     {@link AuthorizeUserType }
     *     
     */
    public AuthorizeUserType getAuthorizedUser() {
        return authorizedUser;
    }

    /**
     * Sets the value of the authorizedUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthorizeUserType }
     *     
     */
    public void setAuthorizedUser(AuthorizeUserType value) {
        this.authorizedUser = value;
    }

}
