
package com.bestbuy.bbym.ise.iseclienthub;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProtectionPlanTypeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ProtectionPlanTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="PSP"/>
 *     &lt;enumeration value="PRP"/>
 *     &lt;enumeration value="BB"/>
 *     &lt;enumeration value="TS"/>
 *     &lt;enumeration value="AOS"/>
 *     &lt;enumeration value="GSOS"/>
 *     &lt;enumeration value="SPT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ProtectionPlanTypeEnum")
@XmlEnum
public enum ProtectionPlanTypeEnum {

    PSP,
    PRP,
    BB,
    TS,
    AOS,
    GSOS,
    SPT;

    public String value() {
        return name();
    }

    public static ProtectionPlanTypeEnum fromValue(String v) {
        return valueOf(v);
    }

}
