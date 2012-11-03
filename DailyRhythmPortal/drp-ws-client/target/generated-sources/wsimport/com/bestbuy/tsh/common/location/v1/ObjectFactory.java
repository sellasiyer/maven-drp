
package com.bestbuy.tsh.common.location.v1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;
import com.bestbuy.tsh.common.datatype.v1.CodeType;
import com.bestbuy.tsh.common.datatype.v1.MeasureType;
import com.bestbuy.tsh.common.datatype.v1.NameType;
import com.bestbuy.tsh.common.datatype.v1.SequencedNameType;
import com.bestbuy.tsh.common.datatype.v1.SequencedTextType;
import com.bestbuy.tsh.common.datatype.v1.TextType;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.bestbuy.tsh.common.location.v1 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Sectioncode_QNAME = new QName("http://www.tsh.bestbuy.com/common/location/v1", "Sectioncode");
    private final static QName _BuildingNumber_QNAME = new QName("http://www.tsh.bestbuy.com/common/location/v1", "BuildingNumber");
    private final static QName _Bsacode_QNAME = new QName("http://www.tsh.bestbuy.com/common/location/v1", "Bsacode");
    private final static QName _State_QNAME = new QName("http://www.tsh.bestbuy.com/common/location/v1", "State");
    private final static QName _PostalCode_QNAME = new QName("http://www.tsh.bestbuy.com/common/location/v1", "PostalCode");
    private final static QName _Province_QNAME = new QName("http://www.tsh.bestbuy.com/common/location/v1", "Province");
    private final static QName _StreetNumber_QNAME = new QName("http://www.tsh.bestbuy.com/common/location/v1", "StreetNumber");
    private final static QName _Faultmessage_QNAME = new QName("http://www.tsh.bestbuy.com/common/location/v1", "Faultmessage");
    private final static QName _Altitude_QNAME = new QName("http://www.tsh.bestbuy.com/common/location/v1", "Altitude");
    private final static QName _Longitude_QNAME = new QName("http://www.tsh.bestbuy.com/common/location/v1", "Longitude");
    private final static QName _CountyName_QNAME = new QName("http://www.tsh.bestbuy.com/common/location/v1", "CountyName");
    private final static QName _UnitNumber_QNAME = new QName("http://www.tsh.bestbuy.com/common/location/v1", "UnitNumber");
    private final static QName _Faultcode_QNAME = new QName("http://www.tsh.bestbuy.com/common/location/v1", "Faultcode");
    private final static QName _Latitude_QNAME = new QName("http://www.tsh.bestbuy.com/common/location/v1", "Latitude");
    private final static QName _CountryCode_QNAME = new QName("http://www.tsh.bestbuy.com/common/location/v1", "CountryCode");
    private final static QName _Direction_QNAME = new QName("http://www.tsh.bestbuy.com/common/location/v1", "Direction");
    private final static QName _Matchcode_QNAME = new QName("http://www.tsh.bestbuy.com/common/location/v1", "Matchcode");
    private final static QName _PostCodeType_QNAME = new QName("http://www.tsh.bestbuy.com/common/location/v1", "PostCodeType");
    private final static QName _Elevation_QNAME = new QName("http://www.tsh.bestbuy.com/common/location/v1", "Elevation");
    private final static QName _City_QNAME = new QName("http://www.tsh.bestbuy.com/common/location/v1", "City");
    private final static QName _PostalCodePlus4_QNAME = new QName("http://www.tsh.bestbuy.com/common/location/v1", "PostalCodePlus4");
    private final static QName _BuildingName_QNAME = new QName("http://www.tsh.bestbuy.com/common/location/v1", "BuildingName");
    private final static QName _Statuscode_QNAME = new QName("http://www.tsh.bestbuy.com/common/location/v1", "Statuscode");
    private final static QName _DeliveryPoint_QNAME = new QName("http://www.tsh.bestbuy.com/common/location/v1", "DeliveryPoint");
    private final static QName _UnitDescription_QNAME = new QName("http://www.tsh.bestbuy.com/common/location/v1", "UnitDescription");
    private final static QName _PostalBoxNumber_QNAME = new QName("http://www.tsh.bestbuy.com/common/location/v1", "PostalBoxNumber");
    private final static QName _AddressLine1_QNAME = new QName("http://www.tsh.bestbuy.com/common/location/v1", "AddressLine1");
    private final static QName _AddressLine2_QNAME = new QName("http://www.tsh.bestbuy.com/common/location/v1", "AddressLine2");
    private final static QName _AddressLine3_QNAME = new QName("http://www.tsh.bestbuy.com/common/location/v1", "AddressLine3");
    private final static QName _AddressLine4_QNAME = new QName("http://www.tsh.bestbuy.com/common/location/v1", "AddressLine4");
    private final static QName _AddressLine5_QNAME = new QName("http://www.tsh.bestbuy.com/common/location/v1", "AddressLine5");
    private final static QName _StreetName_QNAME = new QName("http://www.tsh.bestbuy.com/common/location/v1", "StreetName");
    private final static QName _AddressLine_QNAME = new QName("http://www.tsh.bestbuy.com/common/location/v1", "AddressLine");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.bestbuy.tsh.common.location.v1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MailingAddressType }
     * 
     */
    public MailingAddressType createMailingAddressType() {
        return new MailingAddressType();
    }

    /**
     * Create an instance of {@link GeoCoordinateMeasureType }
     * 
     */
    public GeoCoordinateMeasureType createGeoCoordinateMeasureType() {
        return new GeoCoordinateMeasureType();
    }

    /**
     * Create an instance of {@link TimeZoneType }
     * 
     */
    public TimeZoneType createTimeZoneType() {
        return new TimeZoneType();
    }

    /**
     * Create an instance of {@link GeographyType }
     * 
     */
    public GeographyType createGeographyType() {
        return new GeographyType();
    }

    /**
     * Create an instance of {@link CountryCodeType }
     * 
     */
    public CountryCodeType createCountryCodeType() {
        return new CountryCodeType();
    }

    /**
     * Create an instance of {@link PostalCodeType }
     * 
     */
    public PostalCodeType createPostalCodeType() {
        return new PostalCodeType();
    }

    /**
     * Create an instance of {@link StateCodeType }
     * 
     */
    public StateCodeType createStateCodeType() {
        return new StateCodeType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/location/v1", name = "Sectioncode")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createSectioncode(String value) {
        return new JAXBElement<String>(_Sectioncode_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/location/v1", name = "BuildingNumber")
    public JAXBElement<TextType> createBuildingNumber(TextType value) {
        return new JAXBElement<TextType>(_BuildingNumber_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/location/v1", name = "Bsacode")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createBsacode(String value) {
        return new JAXBElement<String>(_Bsacode_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StateCodeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/location/v1", name = "State")
    public JAXBElement<StateCodeType> createState(StateCodeType value) {
        return new JAXBElement<StateCodeType>(_State_QNAME, StateCodeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PostalCodeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/location/v1", name = "PostalCode")
    public JAXBElement<PostalCodeType> createPostalCode(PostalCodeType value) {
        return new JAXBElement<PostalCodeType>(_PostalCode_QNAME, PostalCodeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StateCodeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/location/v1", name = "Province")
    public JAXBElement<StateCodeType> createProvince(StateCodeType value) {
        return new JAXBElement<StateCodeType>(_Province_QNAME, StateCodeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/location/v1", name = "StreetNumber")
    public JAXBElement<TextType> createStreetNumber(TextType value) {
        return new JAXBElement<TextType>(_StreetNumber_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/location/v1", name = "Faultmessage")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createFaultmessage(String value) {
        return new JAXBElement<String>(_Faultmessage_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GeoCoordinateMeasureType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/location/v1", name = "Altitude")
    public JAXBElement<GeoCoordinateMeasureType> createAltitude(GeoCoordinateMeasureType value) {
        return new JAXBElement<GeoCoordinateMeasureType>(_Altitude_QNAME, GeoCoordinateMeasureType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GeoCoordinateMeasureType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/location/v1", name = "Longitude")
    public JAXBElement<GeoCoordinateMeasureType> createLongitude(GeoCoordinateMeasureType value) {
        return new JAXBElement<GeoCoordinateMeasureType>(_Longitude_QNAME, GeoCoordinateMeasureType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NameType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/location/v1", name = "CountyName")
    public JAXBElement<NameType> createCountyName(NameType value) {
        return new JAXBElement<NameType>(_CountyName_QNAME, NameType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/location/v1", name = "UnitNumber")
    public JAXBElement<TextType> createUnitNumber(TextType value) {
        return new JAXBElement<TextType>(_UnitNumber_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/location/v1", name = "Faultcode")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createFaultcode(String value) {
        return new JAXBElement<String>(_Faultcode_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GeoCoordinateMeasureType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/location/v1", name = "Latitude")
    public JAXBElement<GeoCoordinateMeasureType> createLatitude(GeoCoordinateMeasureType value) {
        return new JAXBElement<GeoCoordinateMeasureType>(_Latitude_QNAME, GeoCoordinateMeasureType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CountryCodeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/location/v1", name = "CountryCode")
    public JAXBElement<CountryCodeType> createCountryCode(CountryCodeType value) {
        return new JAXBElement<CountryCodeType>(_CountryCode_QNAME, CountryCodeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/location/v1", name = "Direction")
    public JAXBElement<TextType> createDirection(TextType value) {
        return new JAXBElement<TextType>(_Direction_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/location/v1", name = "Matchcode")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createMatchcode(String value) {
        return new JAXBElement<String>(_Matchcode_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CodeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/location/v1", name = "PostCodeType")
    public JAXBElement<CodeType> createPostCodeType(CodeType value) {
        return new JAXBElement<CodeType>(_PostCodeType_QNAME, CodeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MeasureType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/location/v1", name = "Elevation")
    public JAXBElement<MeasureType> createElevation(MeasureType value) {
        return new JAXBElement<MeasureType>(_Elevation_QNAME, MeasureType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/location/v1", name = "City")
    public JAXBElement<TextType> createCity(TextType value) {
        return new JAXBElement<TextType>(_City_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/location/v1", name = "PostalCodePlus4")
    public JAXBElement<String> createPostalCodePlus4(String value) {
        return new JAXBElement<String>(_PostalCodePlus4_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SequencedNameType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/location/v1", name = "BuildingName")
    public JAXBElement<SequencedNameType> createBuildingName(SequencedNameType value) {
        return new JAXBElement<SequencedNameType>(_BuildingName_QNAME, SequencedNameType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/location/v1", name = "Statuscode")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createStatuscode(String value) {
        return new JAXBElement<String>(_Statuscode_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CodeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/location/v1", name = "DeliveryPoint")
    public JAXBElement<CodeType> createDeliveryPoint(CodeType value) {
        return new JAXBElement<CodeType>(_DeliveryPoint_QNAME, CodeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/location/v1", name = "UnitDescription")
    public JAXBElement<TextType> createUnitDescription(TextType value) {
        return new JAXBElement<TextType>(_UnitDescription_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/location/v1", name = "PostalBoxNumber")
    public JAXBElement<TextType> createPostalBoxNumber(TextType value) {
        return new JAXBElement<TextType>(_PostalBoxNumber_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/location/v1", name = "AddressLine1")
    public JAXBElement<TextType> createAddressLine1(TextType value) {
        return new JAXBElement<TextType>(_AddressLine1_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/location/v1", name = "AddressLine2")
    public JAXBElement<TextType> createAddressLine2(TextType value) {
        return new JAXBElement<TextType>(_AddressLine2_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/location/v1", name = "AddressLine3")
    public JAXBElement<TextType> createAddressLine3(TextType value) {
        return new JAXBElement<TextType>(_AddressLine3_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/location/v1", name = "AddressLine4")
    public JAXBElement<TextType> createAddressLine4(TextType value) {
        return new JAXBElement<TextType>(_AddressLine4_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/location/v1", name = "AddressLine5")
    public JAXBElement<TextType> createAddressLine5(TextType value) {
        return new JAXBElement<TextType>(_AddressLine5_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/location/v1", name = "StreetName")
    public JAXBElement<TextType> createStreetName(TextType value) {
        return new JAXBElement<TextType>(_StreetName_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SequencedTextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/location/v1", name = "AddressLine")
    public JAXBElement<SequencedTextType> createAddressLine(SequencedTextType value) {
        return new JAXBElement<SequencedTextType>(_AddressLine_QNAME, SequencedTextType.class, null, value);
    }

}
