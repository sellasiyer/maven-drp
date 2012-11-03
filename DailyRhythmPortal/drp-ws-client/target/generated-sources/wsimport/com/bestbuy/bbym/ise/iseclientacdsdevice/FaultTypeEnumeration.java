
package com.bestbuy.bbym.ise.iseclientacdsdevice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FaultTypeEnumeration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="FaultTypeEnumeration">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ERROR"/>
 *     &lt;enumeration value="WARNING"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "FaultTypeEnumeration", namespace = "http://tsh.bestbuy.com/tsh/tshfault")
@XmlEnum
public enum FaultTypeEnumeration {

    ERROR,
    WARNING;

    public String value() {
        return name();
    }

    public static FaultTypeEnumeration fromValue(String v) {
        return valueOf(v);
    }

}
