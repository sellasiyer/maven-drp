
package com.bestbuy.bbym.ise.iseclientca;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DirtyStatusType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DirtyStatusType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="EXCLUDE"/>
 *     &lt;enumeration value="UPDATE"/>
 *     &lt;enumeration value="CREATE"/>
 *     &lt;enumeration value="DELETE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DirtyStatusType")
@XmlEnum
public enum DirtyStatusType {

    EXCLUDE,
    UPDATE,
    CREATE,
    DELETE;

    public String value() {
        return name();
    }

    public static DirtyStatusType fromValue(String v) {
        return valueOf(v);
    }

}
