
package com.bestbuy.bbym.logistics.device.status.component.v1;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TransactionStatusType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TransactionStatusType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="RLSCLS"/>
 *     &lt;enumeration value="RLSSHT"/>
 *     &lt;enumeration value="RLSRCV"/>
 *     &lt;enumeration value="RLSRRT"/>
 *     &lt;enumeration value="REPRCV"/>
 *     &lt;enumeration value="RLSRPR"/>
 *     &lt;enumeration value="RLSPRT"/>
 *     &lt;enumeration value="FGIASN"/>
 *     &lt;enumeration value="FGIRCV"/>
 *     &lt;enumeration value="FWDRCV"/>
 *     &lt;enumeration value="FWDLRT"/>
 *     &lt;enumeration value="FWDREC"/>
 *     &lt;enumeration value="RLSSHK"/>
 *     &lt;enumeration value="FWDNOS"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TransactionStatusType")
@XmlEnum
public enum TransactionStatusType {

    RLSCLS,
    RLSSHT,
    RLSRCV,
    RLSRRT,
    REPRCV,
    RLSRPR,
    RLSPRT,
    FGIASN,
    FGIRCV,
    FWDRCV,
    FWDLRT,
    FWDREC,
    RLSSHK,
    FWDNOS;

    public String value() {
        return name();
    }

    public static TransactionStatusType fromValue(String v) {
        return valueOf(v);
    }

}
