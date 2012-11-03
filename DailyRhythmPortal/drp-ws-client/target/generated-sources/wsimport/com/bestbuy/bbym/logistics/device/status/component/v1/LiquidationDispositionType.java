
package com.bestbuy.bbym.logistics.device.status.component.v1;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LiquidationDispositionType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="LiquidationDispositionType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="B2C"/>
 *     &lt;enumeration value="B2E"/>
 *     &lt;enumeration value="B2B"/>
 *     &lt;enumeration value="B2V"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "LiquidationDispositionType")
@XmlEnum
public enum LiquidationDispositionType {

    @XmlEnumValue("B2C")
    B_2_C("B2C"),
    @XmlEnumValue("B2E")
    B_2_E("B2E"),
    @XmlEnumValue("B2B")
    B_2_B("B2B"),
    @XmlEnumValue("B2V")
    B_2_V("B2V");
    private final String value;

    LiquidationDispositionType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static LiquidationDispositionType fromValue(String v) {
        for (LiquidationDispositionType c: LiquidationDispositionType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
