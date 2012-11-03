
package com.bestbuy.tsh.facilities.locations.fields.v2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * Hierarchy of the BBY Location.
 * 							The Location Hierarchy: Location -> District -> Region/Territory -> Area/Division -> Chain.
 * 				District: Contains the name of the district which, along with the district number, identifies the district.
 * 				Region: Unique identifier for a region of zones. Contains the name of the region which, along with the region number, identifies
 * 				the region. 
 * 				Location			:   LOC_ID=281     LOC_NM=RICHFIELD MN
 * 				District				:   DIST_ID=100    DIST_NM=D100
 * 				Region/Territory	: 	RGN_ID=34      RGN_NM=TERRITORY 34
 * 				Area					:   AREA_ID=1      AREA_NM=DIVISION 1
 * 				Chain				:   CHN_ID=110     CHN_NM=BEST BUY CO., INC.
 * 			
 * 
 * <p>Java class for LocationHierarchyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LocationHierarchyType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tsh.bestbuy.com/facilities/locations/fields/v2}CustomCodeType">
 *       &lt;attribute name="type" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}StringType" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LocationHierarchyType")
public class LocationHierarchyType
    extends CustomCodeType
{

    @XmlAttribute
    protected String type;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

}
