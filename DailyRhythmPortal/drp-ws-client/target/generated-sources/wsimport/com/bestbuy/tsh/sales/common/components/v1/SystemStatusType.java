
package com.bestbuy.tsh.sales.common.components.v1;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.bestbuy.tsh.common.datatype.v1.TextType;


/**
 * SystemStatusType
 * 
 * <p>Java class for SystemStatusType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SystemStatusType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}ServerName" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}Environment" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}SystemWarningCode" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}SystemWarningMsg" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}Rows" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SystemStatusType", propOrder = {
    "serverName",
    "environment",
    "systemWarningCode",
    "systemWarningMsg",
    "rows"
})
public class SystemStatusType {

    @XmlElement(name = "ServerName", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType serverName;
    @XmlElement(name = "Environment", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType environment;
    @XmlElement(name = "SystemWarningCode", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType systemWarningCode;
    @XmlElement(name = "SystemWarningMsg", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType systemWarningMsg;
    @XmlElement(name = "Rows", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected BigInteger rows;

    /**
     * Gets the value of the serverName property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getServerName() {
        return serverName;
    }

    /**
     * Sets the value of the serverName property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setServerName(TextType value) {
        this.serverName = value;
    }

    /**
     * Gets the value of the environment property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getEnvironment() {
        return environment;
    }

    /**
     * Sets the value of the environment property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setEnvironment(TextType value) {
        this.environment = value;
    }

    /**
     * Gets the value of the systemWarningCode property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getSystemWarningCode() {
        return systemWarningCode;
    }

    /**
     * Sets the value of the systemWarningCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setSystemWarningCode(TextType value) {
        this.systemWarningCode = value;
    }

    /**
     * Gets the value of the systemWarningMsg property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getSystemWarningMsg() {
        return systemWarningMsg;
    }

    /**
     * Sets the value of the systemWarningMsg property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setSystemWarningMsg(TextType value) {
        this.systemWarningMsg = value;
    }

    /**
     * Gets the value of the rows property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRows() {
        return rows;
    }

    /**
     * Sets the value of the rows property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRows(BigInteger value) {
        this.rows = value;
    }

}
