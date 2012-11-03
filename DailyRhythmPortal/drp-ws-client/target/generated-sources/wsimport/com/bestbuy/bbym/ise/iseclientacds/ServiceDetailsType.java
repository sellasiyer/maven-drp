
package com.bestbuy.bbym.ise.iseclientacds;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Information about the Service details .
 * 			
 * 
 * <p>Java class for serviceDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="serviceDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Service" type="{http://www.tsh.bestbuy.com/common/datatype/v1}TextType" minOccurs="0"/>
 *         &lt;element name="ProcessId" type="{http://www.tsh.bestbuy.com/common/datatype/v1}TextType" minOccurs="0"/>
 *         &lt;element name="DomainName" type="{http://www.tsh.bestbuy.com/common/datatype/v1}TextType" minOccurs="0"/>
 *         &lt;element name="ServerName" type="{http://www.tsh.bestbuy.com/common/datatype/v1}TextType" minOccurs="0"/>
 *         &lt;element name="ApplicationName" type="{http://www.tsh.bestbuy.com/common/datatype/v1}TextType" minOccurs="0"/>
 *         &lt;element name="ExceptionCode" type="{http://www.tsh.bestbuy.com/common/datatype/v1}TextType" minOccurs="0"/>
 *         &lt;element name="ExceptionType" type="{http://www.tsh.bestbuy.com/common/datatype/v1}TextType" minOccurs="0"/>
 *         &lt;element name="ExceptionMessage" type="{http://www.tsh.bestbuy.com/common/datatype/v1}TextType" minOccurs="0"/>
 *         &lt;element name="ExceptionAdditionalInfo" type="{http://www.tsh.bestbuy.com/common/datatype/v1}TextType" minOccurs="0"/>
 *         &lt;element name="LogMessage" type="{http://www.tsh.bestbuy.com/common/datatype/v1}TextType" minOccurs="0"/>
 *         &lt;element name="ExceptionTimeStamp" type="{http://www.tsh.bestbuy.com/common/datatype/v1}TextType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "serviceDetailsType", namespace = "http://tsh.bestbuy.com/tsh/tshfault", propOrder = {
    "service",
    "processId",
    "domainName",
    "serverName",
    "applicationName",
    "exceptionCode",
    "exceptionType",
    "exceptionMessage",
    "exceptionAdditionalInfo",
    "logMessage",
    "exceptionTimeStamp"
})
public class ServiceDetailsType {

    @XmlElement(name = "Service")
    protected TextType service;
    @XmlElement(name = "ProcessId")
    protected TextType processId;
    @XmlElement(name = "DomainName")
    protected TextType domainName;
    @XmlElement(name = "ServerName")
    protected TextType serverName;
    @XmlElement(name = "ApplicationName")
    protected TextType applicationName;
    @XmlElement(name = "ExceptionCode")
    protected TextType exceptionCode;
    @XmlElement(name = "ExceptionType")
    protected TextType exceptionType;
    @XmlElement(name = "ExceptionMessage")
    protected TextType exceptionMessage;
    @XmlElement(name = "ExceptionAdditionalInfo")
    protected TextType exceptionAdditionalInfo;
    @XmlElement(name = "LogMessage")
    protected TextType logMessage;
    @XmlElement(name = "ExceptionTimeStamp")
    protected TextType exceptionTimeStamp;

    /**
     * Gets the value of the service property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getService() {
        return service;
    }

    /**
     * Sets the value of the service property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setService(TextType value) {
        this.service = value;
    }

    /**
     * Gets the value of the processId property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getProcessId() {
        return processId;
    }

    /**
     * Sets the value of the processId property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setProcessId(TextType value) {
        this.processId = value;
    }

    /**
     * Gets the value of the domainName property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getDomainName() {
        return domainName;
    }

    /**
     * Sets the value of the domainName property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setDomainName(TextType value) {
        this.domainName = value;
    }

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
     * Gets the value of the applicationName property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getApplicationName() {
        return applicationName;
    }

    /**
     * Sets the value of the applicationName property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setApplicationName(TextType value) {
        this.applicationName = value;
    }

    /**
     * Gets the value of the exceptionCode property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getExceptionCode() {
        return exceptionCode;
    }

    /**
     * Sets the value of the exceptionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setExceptionCode(TextType value) {
        this.exceptionCode = value;
    }

    /**
     * Gets the value of the exceptionType property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getExceptionType() {
        return exceptionType;
    }

    /**
     * Sets the value of the exceptionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setExceptionType(TextType value) {
        this.exceptionType = value;
    }

    /**
     * Gets the value of the exceptionMessage property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getExceptionMessage() {
        return exceptionMessage;
    }

    /**
     * Sets the value of the exceptionMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setExceptionMessage(TextType value) {
        this.exceptionMessage = value;
    }

    /**
     * Gets the value of the exceptionAdditionalInfo property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getExceptionAdditionalInfo() {
        return exceptionAdditionalInfo;
    }

    /**
     * Sets the value of the exceptionAdditionalInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setExceptionAdditionalInfo(TextType value) {
        this.exceptionAdditionalInfo = value;
    }

    /**
     * Gets the value of the logMessage property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getLogMessage() {
        return logMessage;
    }

    /**
     * Sets the value of the logMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setLogMessage(TextType value) {
        this.logMessage = value;
    }

    /**
     * Gets the value of the exceptionTimeStamp property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getExceptionTimeStamp() {
        return exceptionTimeStamp;
    }

    /**
     * Sets the value of the exceptionTimeStamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setExceptionTimeStamp(TextType value) {
        this.exceptionTimeStamp = value;
    }

}
