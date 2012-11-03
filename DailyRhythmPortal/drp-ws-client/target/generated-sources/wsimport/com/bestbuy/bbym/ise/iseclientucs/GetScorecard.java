
package com.bestbuy.bbym.ise.iseclientucs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getScorecard complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getScorecard">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="scorecardRequest" type="{http://bestbuy.com/bbym/ucs}ScorecardRequest" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getScorecard", propOrder = {
    "scorecardRequest"
})
public class GetScorecard {

    protected ScorecardRequest scorecardRequest;

    /**
     * Gets the value of the scorecardRequest property.
     * 
     * @return
     *     possible object is
     *     {@link ScorecardRequest }
     *     
     */
    public ScorecardRequest getScorecardRequest() {
        return scorecardRequest;
    }

    /**
     * Sets the value of the scorecardRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link ScorecardRequest }
     *     
     */
    public void setScorecardRequest(ScorecardRequest value) {
        this.scorecardRequest = value;
    }

}
