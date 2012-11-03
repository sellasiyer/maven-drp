/**
 * Best Buy (c)2012
 */
package com.bestbuy.bbym.ise.drp.common;

import java.io.Serializable;

/**
 * This class models a phone number and is used by wicket forms
 * 
 * @author Animesh Banerjee
 * 
 */
public class PhoneNumber implements Serializable {

    private static final long serialVersionUID = 4935093239459198784L;
    private String phoneNumber;

    public PhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber == null?"":phoneNumber;
    }

    @Override
    public String toString() {
	return phoneNumber == null?"":phoneNumber.toString();
    }

    @Override
    public boolean equals(Object obj) {
	if (obj instanceof PhoneNumber){
	    return phoneNumber.equals(obj.toString());
	}
	return false;
    }
}
