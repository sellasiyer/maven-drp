
package com.bestbuy.tsh.facilities.locations.fields.v2;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DistanceUOM.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DistanceUOM">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Kilometer"/>
 *     &lt;enumeration value="Mile"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DistanceUOM")
@XmlEnum
public enum DistanceUOM {

    @XmlEnumValue("Kilometer")
    KILOMETER("Kilometer"),
    @XmlEnumValue("Mile")
    MILE("Mile");
    private final String value;

    DistanceUOM(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DistanceUOM fromValue(String v) {
        for (DistanceUOM c: DistanceUOM.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
