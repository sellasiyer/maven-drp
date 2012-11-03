
package com.bestbuy.bbym.ise.iseclientca;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TransactionTypesType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TransactionTypesType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="POS"/>
 *     &lt;enumeration value="ORDER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TransactionTypesType")
@XmlEnum
public enum TransactionTypesType {

    POS,
    ORDER;

    public String value() {
        return name();
    }

    public static TransactionTypesType fromValue(String v) {
        return valueOf(v);
    }

}
