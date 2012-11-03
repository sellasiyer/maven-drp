
package com.bestbuy.bbym.ise.iseclientucs;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Carrier.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Carrier">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ATT"/>
 *     &lt;enumeration value="SPRINT"/>
 *     &lt;enumeration value="TMOBILE"/>
 *     &lt;enumeration value="VERIZON"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Carrier")
@XmlEnum
public enum Carrier {

    ATT,
    SPRINT,
    TMOBILE,
    VERIZON;

    public String value() {
        return name();
    }

    public static Carrier fromValue(String v) {
        return valueOf(v);
    }

}
