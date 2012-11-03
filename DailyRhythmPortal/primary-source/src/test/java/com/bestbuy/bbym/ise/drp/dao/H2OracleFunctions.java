package com.bestbuy.bbym.ise.drp.dao;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Add support to H2 for Oracle functions.
 */
public class H2OracleFunctions {

    /**
     * Support for Oracle <em>trunc</em> function.
     */
    public final static Date trunc(Timestamp timeStamp) {
	return new Date(timeStamp.getTime());
    }

    /**
     * Support for Oracle <em>to_char</em> function.
     */
    public final static String toChar(String date, String pattern) throws Exception {
	pattern = pattern.replaceAll("YY", "yy");
	pattern = pattern.replaceAll("DD", "dd");
	pattern = pattern.replaceAll("HH24|hh24", "HH");
	pattern = pattern.replaceAll("HH?!24|hh?!24", "KK");
	pattern = pattern.replaceAll("MON|mon", "MMM");
	pattern = pattern.replaceAll("MI|mi", "mm");
	pattern = pattern.replaceAll("SS|ss", "ss");
	pattern = pattern.replaceAll("AM|PM", "aa");
	SimpleDateFormat sm = new SimpleDateFormat(pattern);
	java.util.Date dt;
	if (date.length() > 10)
	    dt = java.sql.Timestamp.valueOf(date);
	else
	    dt = java.sql.Date.valueOf(date);
	return sm.format(dt);
    }

    /**
     * Support for Oracle <em>to_date</em> function.
     */
    public final static java.util.Date toDate(String date, String pattern) throws Exception {

	pattern = pattern.replaceAll("YY", "yy");
	pattern = pattern.replaceAll("DD", "dd");
	pattern = pattern.replaceAll("HH24|hh24", "HH");
	pattern = pattern.replaceAll("HH?!24|hh?!24", "KK");
	pattern = pattern.replaceAll("MON|mon", "MMM");
	pattern = pattern.replaceAll("mm", "MM");
	pattern = pattern.replaceAll("MI|mi", "mm");
	pattern = pattern.replaceAll("SS|ss", "ss");
	pattern = pattern.replaceAll("AM|PM", "aa");

	SimpleDateFormat sdf = new SimpleDateFormat(pattern);

	return sdf.parse(date);
    }

    /**
     * Support for Oracle <em>to_number</em> function.
     */
    public final static Double toNumber(String number) throws Exception {

	return Double.parseDouble(number);
    }
}
