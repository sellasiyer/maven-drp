
package com.bestbuy.bbym.ise.iseclientucs;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NotificationStatusWithCachedUpgradeCheckPutResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NotificationStatusWithCachedUpgradeCheckPutResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="subscriberStatuses" type="{http://bestbuy.com/bbym/ucs}SubscriberNotificationStatus" maxOccurs="unbounded"/>
 *         &lt;element name="upgradeCheckCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="optInCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NotificationStatusWithCachedUpgradeCheckPutResponse", propOrder = {
    "subscriberStatuses",
    "upgradeCheckCount",
    "optInCount"
})
public class NotificationStatusWithCachedUpgradeCheckPutResponse {

    @XmlElement(required = true)
    protected List<SubscriberNotificationStatus> subscriberStatuses;
    protected int upgradeCheckCount;
    protected int optInCount;

    /**
     * Gets the value of the subscriberStatuses property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subscriberStatuses property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubscriberStatuses().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SubscriberNotificationStatus }
     * 
     * 
     */
    public List<SubscriberNotificationStatus> getSubscriberStatuses() {
        if (subscriberStatuses == null) {
            subscriberStatuses = new ArrayList<SubscriberNotificationStatus>();
        }
        return this.subscriberStatuses;
    }

    /**
     * Gets the value of the upgradeCheckCount property.
     * 
     */
    public int getUpgradeCheckCount() {
        return upgradeCheckCount;
    }

    /**
     * Sets the value of the upgradeCheckCount property.
     * 
     */
    public void setUpgradeCheckCount(int value) {
        this.upgradeCheckCount = value;
    }

    /**
     * Gets the value of the optInCount property.
     * 
     */
    public int getOptInCount() {
        return optInCount;
    }

    /**
     * Sets the value of the optInCount property.
     * 
     */
    public void setOptInCount(int value) {
        this.optInCount = value;
    }

}
