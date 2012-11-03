
package com.bestbuy.bbym.ise.iseclientucs;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UpgradeEligibilityTypeCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="UpgradeEligibilityTypeCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="UNKNOWN"/>
 *     &lt;enumeration value="ELIGIBLE_FULL"/>
 *     &lt;enumeration value="ELIGIBLE_EARLY"/>
 *     &lt;enumeration value="ELIGIBLE_EARLY_IPHONE"/>
 *     &lt;enumeration value="NOT_ELIGIBLE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "UpgradeEligibilityTypeCode")
@XmlEnum
public enum UpgradeEligibilityTypeCode {

    UNKNOWN,
    ELIGIBLE_FULL,
    ELIGIBLE_EARLY,
    ELIGIBLE_EARLY_IPHONE,
    NOT_ELIGIBLE;

    public String value() {
        return name();
    }

    public static UpgradeEligibilityTypeCode fromValue(String v) {
        return valueOf(v);
    }

}
