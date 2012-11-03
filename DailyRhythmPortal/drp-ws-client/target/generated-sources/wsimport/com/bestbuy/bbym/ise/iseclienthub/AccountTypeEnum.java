
package com.bestbuy.bbym.ise.iseclienthub;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AccountTypeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AccountTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="RZ"/>
 *     &lt;enumeration value="BBFB"/>
 *     &lt;enumeration value="TEC"/>
 *     &lt;enumeration value="CA"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AccountTypeEnum")
@XmlEnum
public enum AccountTypeEnum {

    RZ,
    BBFB,
    TEC,
    CA;

    public String value() {
        return name();
    }

    public static AccountTypeEnum fromValue(String v) {
        return valueOf(v);
    }

}
