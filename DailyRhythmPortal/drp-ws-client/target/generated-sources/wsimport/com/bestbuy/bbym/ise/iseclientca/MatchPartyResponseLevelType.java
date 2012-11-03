
package com.bestbuy.bbym.ise.iseclientca;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MatchPartyResponseLevelType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="MatchPartyResponseLevelType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ALL"/>
 *     &lt;enumeration value="MINIMUM"/>
 *     &lt;enumeration value="RELEVANT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "MatchPartyResponseLevelType")
@XmlEnum
public enum MatchPartyResponseLevelType {

    ALL,
    MINIMUM,
    RELEVANT;

    public String value() {
        return name();
    }

    public static MatchPartyResponseLevelType fromValue(String v) {
        return valueOf(v);
    }

}
