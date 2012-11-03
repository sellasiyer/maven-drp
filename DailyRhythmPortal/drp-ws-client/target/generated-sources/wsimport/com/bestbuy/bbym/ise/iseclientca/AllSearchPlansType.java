
package com.bestbuy.bbym.ise.iseclientca;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AllSearchPlansType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AllSearchPlansType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="caPortalSearchPlan"/>
 *     &lt;enumeration value="caPortalSearchandVerifyPlan"/>
 *     &lt;enumeration value="caDefaultPlan"/>
 *     &lt;enumeration value="ecByAccount"/>
 *     &lt;enumeration value="ecByAccountAndName"/>
 *     &lt;enumeration value="ecByEmail"/>
 *     &lt;enumeration value="ecByEmailAndFirstAndLastName"/>
 *     &lt;enumeration value="ecByFirstAndLastName"/>
 *     &lt;enumeration value="ecByAddress1LastNamePostalCode"/>
 *     &lt;enumeration value="ecByPhone"/>
 *     &lt;enumeration value="ecByPhoneAndAreaCode"/>
 *     &lt;enumeration value="ecByFirstAndLastNamePhoneEmail"/>
 *     &lt;enumeration value="ecByFirstAndLastNamePhone"/>
 *     &lt;enumeration value="ecByFirstAndLastNamePhoneAndAreaCode"/>
 *     &lt;enumeration value="ecByTransaction"/>
 *     &lt;enumeration value="ecByFirstAndLastNamePostalCode"/>
 *     &lt;enumeration value="ecByFirstAndLastNameState"/>
 *     &lt;enumeration value="ecByFirstAndLastNameStoreNumber"/>
 *     &lt;enumeration value="demoByFirstandLastName"/>
 *     &lt;enumeration value="demoByAddress1LastNamePostalCode"/>
 *     &lt;enumeration value="demoByPhone"/>
 *     &lt;enumeration value="demoByPhoneAndAreaCode"/>
 *     &lt;enumeration value="demoByFirstAndLastNamePhone"/>
 *     &lt;enumeration value="demoByFirstAndLastNamePhoneAndAreaCode"/>
 *     &lt;enumeration value="ecByCustomerID"/>
 *     &lt;enumeration value="searchPlan1"/>
 *     &lt;enumeration value="searchPlan2"/>
 *     &lt;enumeration value="searchPlan3"/>
 *     &lt;enumeration value="searchPlan4"/>
 *     &lt;enumeration value="searchPlan5"/>
 *     &lt;enumeration value="searchPlan6"/>
 *     &lt;enumeration value="searchPlan7"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AllSearchPlansType")
@XmlEnum
public enum AllSearchPlansType {

    @XmlEnumValue("caPortalSearchPlan")
    CA_PORTAL_SEARCH_PLAN("caPortalSearchPlan"),
    @XmlEnumValue("caPortalSearchandVerifyPlan")
    CA_PORTAL_SEARCHAND_VERIFY_PLAN("caPortalSearchandVerifyPlan"),
    @XmlEnumValue("caDefaultPlan")
    CA_DEFAULT_PLAN("caDefaultPlan"),
    @XmlEnumValue("ecByAccount")
    EC_BY_ACCOUNT("ecByAccount"),
    @XmlEnumValue("ecByAccountAndName")
    EC_BY_ACCOUNT_AND_NAME("ecByAccountAndName"),
    @XmlEnumValue("ecByEmail")
    EC_BY_EMAIL("ecByEmail"),
    @XmlEnumValue("ecByEmailAndFirstAndLastName")
    EC_BY_EMAIL_AND_FIRST_AND_LAST_NAME("ecByEmailAndFirstAndLastName"),
    @XmlEnumValue("ecByFirstAndLastName")
    EC_BY_FIRST_AND_LAST_NAME("ecByFirstAndLastName"),
    @XmlEnumValue("ecByAddress1LastNamePostalCode")
    EC_BY_ADDRESS_1_LAST_NAME_POSTAL_CODE("ecByAddress1LastNamePostalCode"),
    @XmlEnumValue("ecByPhone")
    EC_BY_PHONE("ecByPhone"),
    @XmlEnumValue("ecByPhoneAndAreaCode")
    EC_BY_PHONE_AND_AREA_CODE("ecByPhoneAndAreaCode"),
    @XmlEnumValue("ecByFirstAndLastNamePhoneEmail")
    EC_BY_FIRST_AND_LAST_NAME_PHONE_EMAIL("ecByFirstAndLastNamePhoneEmail"),
    @XmlEnumValue("ecByFirstAndLastNamePhone")
    EC_BY_FIRST_AND_LAST_NAME_PHONE("ecByFirstAndLastNamePhone"),
    @XmlEnumValue("ecByFirstAndLastNamePhoneAndAreaCode")
    EC_BY_FIRST_AND_LAST_NAME_PHONE_AND_AREA_CODE("ecByFirstAndLastNamePhoneAndAreaCode"),
    @XmlEnumValue("ecByTransaction")
    EC_BY_TRANSACTION("ecByTransaction"),
    @XmlEnumValue("ecByFirstAndLastNamePostalCode")
    EC_BY_FIRST_AND_LAST_NAME_POSTAL_CODE("ecByFirstAndLastNamePostalCode"),
    @XmlEnumValue("ecByFirstAndLastNameState")
    EC_BY_FIRST_AND_LAST_NAME_STATE("ecByFirstAndLastNameState"),
    @XmlEnumValue("ecByFirstAndLastNameStoreNumber")
    EC_BY_FIRST_AND_LAST_NAME_STORE_NUMBER("ecByFirstAndLastNameStoreNumber"),
    @XmlEnumValue("demoByFirstandLastName")
    DEMO_BY_FIRSTAND_LAST_NAME("demoByFirstandLastName"),
    @XmlEnumValue("demoByAddress1LastNamePostalCode")
    DEMO_BY_ADDRESS_1_LAST_NAME_POSTAL_CODE("demoByAddress1LastNamePostalCode"),
    @XmlEnumValue("demoByPhone")
    DEMO_BY_PHONE("demoByPhone"),
    @XmlEnumValue("demoByPhoneAndAreaCode")
    DEMO_BY_PHONE_AND_AREA_CODE("demoByPhoneAndAreaCode"),
    @XmlEnumValue("demoByFirstAndLastNamePhone")
    DEMO_BY_FIRST_AND_LAST_NAME_PHONE("demoByFirstAndLastNamePhone"),
    @XmlEnumValue("demoByFirstAndLastNamePhoneAndAreaCode")
    DEMO_BY_FIRST_AND_LAST_NAME_PHONE_AND_AREA_CODE("demoByFirstAndLastNamePhoneAndAreaCode"),
    @XmlEnumValue("ecByCustomerID")
    EC_BY_CUSTOMER_ID("ecByCustomerID"),
    @XmlEnumValue("searchPlan1")
    SEARCH_PLAN_1("searchPlan1"),
    @XmlEnumValue("searchPlan2")
    SEARCH_PLAN_2("searchPlan2"),
    @XmlEnumValue("searchPlan3")
    SEARCH_PLAN_3("searchPlan3"),
    @XmlEnumValue("searchPlan4")
    SEARCH_PLAN_4("searchPlan4"),
    @XmlEnumValue("searchPlan5")
    SEARCH_PLAN_5("searchPlan5"),
    @XmlEnumValue("searchPlan6")
    SEARCH_PLAN_6("searchPlan6"),
    @XmlEnumValue("searchPlan7")
    SEARCH_PLAN_7("searchPlan7");
    private final String value;

    AllSearchPlansType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AllSearchPlansType fromValue(String v) {
        for (AllSearchPlansType c: AllSearchPlansType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
