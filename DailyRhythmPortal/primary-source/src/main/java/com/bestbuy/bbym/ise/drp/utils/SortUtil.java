package com.bestbuy.bbym.ise.drp.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

public class SortUtil {

    private SortUtil() {
	// This class is not meant to be instantiated
    }

    public static int sortString(String s1, String s2, boolean ascending) {
	if (s1 == null && s2 == null){
	    return 0;
	}else if (s1 == null){
	    return 1;
	}else if (s2 == null){
	    return -1;
	}
	int result = s1.compareTo(s2);
	if (!ascending){
	    result = -result;
	}
	return result;
    }

    public static int sortInteger(Integer v1, Integer v2, boolean ascending) {
	if (v1 == null && v2 == null){
	    return 0;
	}else if (v1 == null){
	    return 1;
	}else if (v2 == null){
	    return -1;
	}
	int result = v1.compareTo(v2);
	if (!ascending){
	    result = -result;
	}
	return result;
    }

    public static int sortDouble(Double v1, Double v2, boolean ascending) {
	if (v1 == null && v2 == null){
	    return 0;
	}else if (v1 == null){
	    return 1;
	}else if (v2 == null){
	    return -1;
	}
	int result = v1.compareTo(v2);
	if (!ascending){
	    result = -result;
	}
	return result;
    }

    public static int sortCollectionSize(Collection<?> v1, Collection<?> v2, boolean ascending) {
	if (v1 == null && v2 == null){
	    return 0;
	}else if (v1 == null){
	    return 1;
	}else if (v2 == null){
	    return -1;
	}
	int result = v1.size() - v2.size();
	if (!ascending){
	    result = -result;
	}
	return result;
    }

    public static int sortBigInteger(BigInteger v1, BigInteger v2, boolean ascending) {
	if (v1 == null && v2 == null){
	    return 0;
	}else if (v1 == null){
	    return 1;
	}else if (v2 == null){
	    return -1;
	}
	int result = v1.compareTo(v2);
	if (!ascending){
	    result = -result;
	}
	return result;
    }

    public static int sortBigDecimal(BigDecimal v1, BigDecimal v2, boolean ascending) {
	if (v1 == null && v2 == null){
	    return 0;
	}else if (v1 == null){
	    return 1;
	}else if (v2 == null){
	    return -1;
	}
	int result = v1.compareTo(v2);
	if (!ascending){
	    result = -result;
	}
	return result;
    }

    public static int sortDate(Date v1, Date v2, boolean ascending) {
	if (v1 == null && v2 == null){
	    return 0;
	}else if (v1 == null){
	    return 1;
	}else if (v2 == null){
	    return -1;
	}
	int result = v1.compareTo(v2);
	if (!ascending){
	    result = -result;
	}
	return result;
    }

    public static int sortStringSpecifiedValueAlwaysFirst(String s1, String s2, String specifiedValue) {
	if (s1 == null && s2 == null){
	    return 0;
	}else if (s2 != null && s2.equals(specifiedValue)){
	    return 1;
	}else if (s1 != null && s1.equals(specifiedValue)){
	    return -1;
	}else if (s1 == null){
	    return 1;
	}else if (s2 == null){
	    return -1;
	}
	return s1.compareTo(s2);
    }

}
