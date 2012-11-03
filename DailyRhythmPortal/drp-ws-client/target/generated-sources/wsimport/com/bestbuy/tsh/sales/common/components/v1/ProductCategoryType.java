
package com.bestbuy.tsh.sales.common.components.v1;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.bestbuy.tsh.common.datatype.v1.TextType;


/**
 * <p>Java class for ProductCategoryType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProductCategoryType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}DeptID" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}DeptName" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}ClassID" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}ClassName" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}SubClassID" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}SubClassName" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProductCategoryType", propOrder = {
    "deptID",
    "deptName",
    "classID",
    "className",
    "subClassID",
    "subClassName"
})
public class ProductCategoryType {

    @XmlElement(name = "DeptID", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected BigInteger deptID;
    @XmlElement(name = "DeptName", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType deptName;
    @XmlElement(name = "ClassID", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected BigInteger classID;
    @XmlElement(name = "ClassName", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType className;
    @XmlElement(name = "SubClassID", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected BigInteger subClassID;
    @XmlElement(name = "SubClassName", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType subClassName;

    /**
     * DeptID
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDeptID() {
        return deptID;
    }

    /**
     * Sets the value of the deptID property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDeptID(BigInteger value) {
        this.deptID = value;
    }

    /**
     * DeptName
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getDeptName() {
        return deptName;
    }

    /**
     * Sets the value of the deptName property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setDeptName(TextType value) {
        this.deptName = value;
    }

    /**
     * ClassId
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getClassID() {
        return classID;
    }

    /**
     * Sets the value of the classID property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setClassID(BigInteger value) {
        this.classID = value;
    }

    /**
     * ClassName
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getClassName() {
        return className;
    }

    /**
     * Sets the value of the className property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setClassName(TextType value) {
        this.className = value;
    }

    /**
     * SubClassId
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSubClassID() {
        return subClassID;
    }

    /**
     * Sets the value of the subClassID property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSubClassID(BigInteger value) {
        this.subClassID = value;
    }

    /**
     * SubClassName
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getSubClassName() {
        return subClassName;
    }

    /**
     * Sets the value of the subClassName property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setSubClassName(TextType value) {
        this.subClassName = value;
    }

}
