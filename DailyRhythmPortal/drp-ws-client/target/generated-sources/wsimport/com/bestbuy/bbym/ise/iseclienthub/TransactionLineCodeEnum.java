
package com.bestbuy.bbym.ise.iseclienthub;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TransactionLineCodeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TransactionLineCodeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="S"/>
 *     &lt;enumeration value="R"/>
 *     &lt;enumeration value="PVS"/>
 *     &lt;enumeration value="PVR"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TransactionLineCodeEnum")
@XmlEnum
public enum TransactionLineCodeEnum {

    S,
    R,
    PVS,
    PVR;

    public String value() {
        return name();
    }

    public static TransactionLineCodeEnum fromValue(String v) {
        return valueOf(v);
    }

}
