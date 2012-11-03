package com.bestbuy.bbym.ise.drp.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Essentials implements Serializable {

    private static final long serialVersionUID = -8268828841081277850L;

    public class Essential implements Serializable {

	private static final long serialVersionUID = 1L;
	public Long id = new Long(0);
	public String value = "";
	public EssentialType type;

	public Essential(String value) {
	    this.value = value;
	}

	public Essential(String value, Long id) {
	    this.value = value;
	    this.id = id;
	}
    }

    public enum EssentialType {
	//note: anything added here will need to be added on the form and to the database recommendation_essential_type table. 
	// Order doesn't matter. 
	HANDSFREE("handsfree"), MEMORY("memory"), SHIELDS("shields"), ACCESSORIES("accessories"), CHARGERS("chargers"),
	GSBTP("gsbtp"), BUYBACK("buyback"), FINANCING("financing");

	private String label;

	private EssentialType(String label) {
	    this.label = label;
	}

	@Override
	public String toString() {
	    return this.label;
	}

    }

    private Map<EssentialType, Essential> eMap = new HashMap<EssentialType, Essential>();

    /**
     * Given the essential type calls the appropriate setter.
     * Designed to be used for setting essentials from the DB.
     * @param type - The type (ie bluetooth, shield, etc.)
     * @param value - The value to set for that type... ie handsfree = "yes"
     * @param id - The id of the record to set for updating in the future.
     */
    public void setEssential(String type, String value, Long id) {
	EssentialType eType = EssentialType.valueOf(type);
	Essential e = new Essential(value, id);
	e.type = eType;
	eMap.put(eType, e);
    }

    /**
     * Helper function that updates the Essential record in the map. 
     * If no record is found will create a new one.
     * @param type - ( EssentialType.HANDSFREE, MEMORY, SHIELDS, etc.)
     * @param value - The value to store, ie MEMORY = 8 gigs
     */
    private void setEssential(EssentialType type, String value) {
	Essential e = eMap.get(type);
	if (e == null){
	    e = new Essential(value);
	    e.type = type;
	}
	e.value = value;
	eMap.put(type, e);

    }

    /**
     * This method is used to put one essential into the Map.
     * @param e - The essential to store.
     */
    public void setEssential(Essential e) {
	eMap.put(e.type, e);
    }

    /**
     * Helper function that accesses the map and checks/handles nulls.
     * @param type  ( EssentialType.HANDSFREE, MEMORY, SHIELDS, etc.)
     * @return The Essential matching type, retrieved from the Map.
     */
    private Essential getEssential(EssentialType type) {
	Essential e = this.eMap.get(type);
	if (e == null)
	    e = new Essential("");
	return e;
    }

    public Collection<Essential> getEssentials() {
	return eMap.values();
    }

    public String getHandsfree() {
	return this.getEssential(EssentialType.HANDSFREE).value;
    }

    public void setHandsfree(String value) {
	setEssential(EssentialType.HANDSFREE, value);
    }

    public String getMemory() {
	return this.getEssential(EssentialType.MEMORY).value;
    }

    public void setMemory(String value) {
	setEssential(EssentialType.MEMORY, value);
    }

    public String getShields() {
	return this.getEssential(EssentialType.SHIELDS).value;
    }

    public void setShields(String value) {
	setEssential(EssentialType.SHIELDS, value);
    }

    public String getAccessories() {
	return this.getEssential(EssentialType.ACCESSORIES).value;
    }

    public void setAccessories(String value) {
	setEssential(EssentialType.ACCESSORIES, value);
    }

    public String getChargers() {
	return this.getEssential(EssentialType.CHARGERS).value;
    }

    public void setChargers(String value) {
	setEssential(EssentialType.CHARGERS, value);
    }

    public String getGsbtp() {
	return this.getEssential(EssentialType.GSBTP).value;
    }

    public void setGsbtp(String value) {
	setEssential(EssentialType.GSBTP, value);
    }

    public String getBuyback() {
	return this.getEssential(EssentialType.BUYBACK).value;
    }

    public void setBuyback(String value) {
	setEssential(EssentialType.BUYBACK, value);
    }

    public String getFinancing() {
	return this.getEssential(EssentialType.FINANCING).value;
    }

    public void setFinancing(String value) {
	setEssential(EssentialType.FINANCING, value);
    }

    public static long getSerialversionuid() {
	return serialVersionUID;
    }

}
