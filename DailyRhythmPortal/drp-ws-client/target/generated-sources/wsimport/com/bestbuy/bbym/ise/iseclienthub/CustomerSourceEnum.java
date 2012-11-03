
package com.bestbuy.bbym.ise.iseclienthub;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CustomerSourceEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CustomerSourceEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="EC"/>
 *     &lt;enumeration value="STAR"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CustomerSourceEnum", namespace = "http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Common/V4")
@XmlEnum
public enum CustomerSourceEnum {

    EC,
    STAR;

    public String value() {
        return name();
    }

    public static CustomerSourceEnum fromValue(String v) {
        return valueOf(v);
    }

}
