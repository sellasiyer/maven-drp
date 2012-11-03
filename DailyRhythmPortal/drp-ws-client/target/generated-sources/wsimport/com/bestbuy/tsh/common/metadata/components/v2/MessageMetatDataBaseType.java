
package com.bestbuy.tsh.common.metadata.components.v2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Metadata base type to define  
 * 
 * <p>Java class for MessageMetatDataBaseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MessageMetatDataBaseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/metadata/fields/v1}Version" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/metadata/fields/v1}SourceID" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/metadata/fields/v1}UserID" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/metadata/fields/v1}ProgramID" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/metadata/fields/v1}MessageID" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MessageMetatDataBaseType", propOrder = {
    "version",
    "sourceID",
    "userID",
    "programID",
    "messageID"
})
public abstract class MessageMetatDataBaseType {

    @XmlElement(name = "Version", namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1")
    protected Integer version;
    @XmlElement(name = "SourceID", namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1")
    protected String sourceID;
    @XmlElement(name = "UserID", namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1")
    protected String userID;
    @XmlElement(name = "ProgramID", namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1")
    protected String programID;
    @XmlElement(name = "MessageID", namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1")
    protected String messageID;

    /**
     *  Whenever service interface is changed in such a way that the
     * 				new service is backward compatible with existing deployments, minor version of service interface MUST be incremented.
     * 				Major version will remain same in this case. In cases where the consumers are invoking the existing definitions of the 
     * 				service and not the new compatible changes in the service, the minor version in request message can continue to be the 
     * 				older value before the change.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setVersion(Integer value) {
        this.version = value;
    }

    /**
     * The Id of the source system which is originating this object.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceID() {
        return sourceID;
    }

    /**
     * Sets the value of the sourceID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceID(String value) {
        this.sourceID = value;
    }

    /**
     * User Identifier from the application who requests for the object creation
     * 			
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Sets the value of the userID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserID(String value) {
        this.userID = value;
    }

    /**
     * Program Id of the application which is originating this object
     * 			
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProgramID() {
        return programID;
    }

    /**
     * Sets the value of the programID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProgramID(String value) {
        this.programID = value;
    }

    /**
     * This MENDATORY element (of type String) conveys unique [message id] property. 
     * 			
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageID() {
        return messageID;
    }

    /**
     * Sets the value of the messageID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageID(String value) {
        this.messageID = value;
    }

}
