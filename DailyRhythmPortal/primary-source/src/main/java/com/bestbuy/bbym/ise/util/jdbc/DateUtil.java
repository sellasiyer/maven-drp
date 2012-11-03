package com.bestbuy.bbym.ise.util.jdbc;

public class DateUtil {

    public static final java.sql.Date utilDateToSqlDate(java.util.Date utilDate) {
	if (utilDate == null){
	    return null;
	}
	return new java.sql.Date(utilDate.getTime());
    }

    public static final java.util.Date sqlDateToUtilDate(java.sql.Date sqlDate) {
	if (sqlDate == null){
	    return null;
	}
	return new java.util.Date(sqlDate.getTime());
    }

    public static final java.sql.Timestamp utilDateToSqlTimestamp(java.util.Date utilDate) {
	if (utilDate == null){
	    return null;
	}
	return new java.sql.Timestamp(utilDate.getTime());
    }

    public static final java.util.Date sqlTimestampToUtilDate(java.sql.Timestamp timestamp) {
	if (timestamp == null){
	    return null;
	}
	long milliseconds = timestamp.getTime() + (timestamp.getNanos() / 1000000);
	return new java.util.Date(milliseconds);
    }

    public static final java.sql.Time utilDateToSqlTime(java.util.Date utilDate) {
	if (utilDate == null){
	    return null;
	}
	return new java.sql.Time(utilDate.getTime());
    }

}
