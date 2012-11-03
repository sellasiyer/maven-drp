
package com.bestbuy.bbym.ise.iseclienthub;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PhoneTypeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PhoneTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="HOME"/>
 *     &lt;enumeration value="WORK"/>
 *     &lt;enumeration value="MOBILE"/>
 *     &lt;enumeration value="FAX"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PhoneTypeEnum", namespace = "http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Common/V4")
@XmlEnum
public enum PhoneTypeEnum {

    HOME,
    WORK,
    MOBILE,
    FAX;

    public String value() {
        return name();
    }

    public static PhoneTypeEnum fromValue(String v) {
        return valueOf(v);
    }

}
