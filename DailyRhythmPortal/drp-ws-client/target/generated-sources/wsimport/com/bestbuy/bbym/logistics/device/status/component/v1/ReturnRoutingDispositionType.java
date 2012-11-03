
package com.bestbuy.bbym.logistics.device.status.component.v1;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReturnRoutingDispositionType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ReturnRoutingDispositionType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="REP"/>
 *     &lt;enumeration value="LIQ"/>
 *     &lt;enumeration value="FGI"/>
 *     &lt;enumeration value="RCY"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ReturnRoutingDispositionType")
@XmlEnum
public enum ReturnRoutingDispositionType {

    REP,
    LIQ,
    FGI,
    RCY;

    public String value() {
        return name();
    }

    public static ReturnRoutingDispositionType fromValue(String v) {
        return valueOf(v);
    }

}
