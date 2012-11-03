package com.bestbuy.bbym.ise.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * 
 * @author a904002
 */
public class User implements Serializable {

    private static final long serialVersionUID = 6276987683958571423L;

    private List<String> applicationRoles = new ArrayList<String>();
    private String firstName;
    private String lastName;
    private String userId;
    private Store store;
    private String managerId;
    private String locationType;
    private String locationPhoneNum;

    public List<String> getApplicationRoles() {
	return applicationRoles;
    }

    public void setApplicationRoles(List<String> applicationRoles) {
	this.applicationRoles = applicationRoles;
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    /**
     * Gets the aid of the blue shirt.
     */
    public String getUserId() {
	return userId;
    }

    /**
     * Sets the aid of the blue shirt.
     */
    public void setUserId(String userId) {
	this.userId = userId;
    }

    public String getStoreId() {
	if (store != null && store.getId() != null){
	    return store.getId();
	}else{
	    return "";
	}
    }

    public void setStoreId(String storeId) {
	if (store == null){
	    this.setStore(new Store());
	}
	this.getStore().setId(storeId);
    }

    /**
     * Gets the BBY store manager Id.
     */
    public String getManagerId() {
	return managerId;
    }

    /**
     * Sets the BBY store manager Id.
     */
    public void setManagerId(String managerId) {
	this.managerId = managerId;
    }

    /**
     * Gets the location type.
     * <p>
     * A value of "ST" indicates store.
     * </p>
     */
    public String getLocationType() {
	return locationType;
    }

    /**
     * Sets the location type.
     */
    public void setLocationType(String locationType) {
	this.locationType = locationType;
    }

    /**
     * Gets the location phone number of the blue shirt.
     */
    public String getLocationPhoneNum() {
	return locationPhoneNum;
    }

    /**
     * Sets the location phone number of the blue shirt.
     */
    public void setLocationPhoneNum(String locationPhoneNum) {
	this.locationPhoneNum = locationPhoneNum;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }

    /**
     * Gets the store that the user is associated with.
     */
    public Store getStore() {
	return store;
    }

    /**
     * Sets the store that the user is associated with.
     */
    public void setStore(Store store) {
	this.store = store;
    }

    public boolean hasRole(String role) {
	return applicationRoles.contains(role);
    }
}
