package com.bestbuy.bbym.ise.util.jdbc;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class InputDataMap {

    private Map<Integer, Object> inputDataMap = new HashMap<Integer, Object>();

    public void put(int dataFieldInput, long dataValue) {
	inputDataMap.put(new Integer(dataFieldInput), new Long(dataValue));
    }

    public void put(int dataFieldInput, int dataValue) {
	inputDataMap.put(new Integer(dataFieldInput), new Integer(dataValue));
    }

    public void put(int dataFieldInput, Date dataValue) {
	inputDataMap.put(new Integer(dataFieldInput), dataValue);
    }

    public void put(int dataFieldInput, String dataValue) {
	inputDataMap.put(new Integer(dataFieldInput), dataValue);
    }

    public Object get(int dataFieldInput) {
	return inputDataMap.get(new Integer(dataFieldInput));
    }

}
