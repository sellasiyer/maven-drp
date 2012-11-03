package com.bestbuy.bbym.ise.drp.beast.tradein.mapper;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Java bean encapsulates the attributes that should be displayed on Product
 * Information screen, user story B-09025
 * 
 * @author a915722
 * 
 */
public class ProductInformationBean implements Serializable {

    private String deviceColor;

    // later will have to move to resource file.
    private final List<String> colorDropdownChoice = Arrays.asList(new String[] {"Black", "Blue", "Brown", "Gray",
	    "Green", "Light Blue", "Light Green", "Multi-Color", "Orange", "Pink", "Purple/Violet", "Red",
	    "Silver/Stainless", "White", "Yellow"

    });

    /**
     * Returns device color.
     * 
     * @return device color.
     */
    public String getDeviceColor() {
	return deviceColor;
    }

    /**
     * Sets device color.
     * 
     * @param deviceColor
     *            device color.
     */
    public void setDeviceColor(String deviceColor) {
	this.deviceColor = deviceColor;
    }

    /**
     * Returns list of possible colors.
     * 
     * @return list of possible colors.
     */
    public List<String> getColorDropdownChoice() {
	return colorDropdownChoice;
    }
}
