package com.bestbuy.bbym.ise.util;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.SerializationUtils;

public class Util {

    private static Logger logger = LoggerFactory.getLogger(Util.class);

    private Util() {
	// This class is not meant to be instantiated
    }

    public static String toStringTime(Date date) {
	if (date == null){
	    return null;
	}
	SimpleDateFormat dsf = new SimpleDateFormat("yyyy-MM-dd HH:mm Z");
	return dsf.format(date);
    }

    /**
     * Formats a {@code Date} into a {@code String}.
     * 
     * @param date
     *            the date to be formatted
     * @param format
     *            the pattern describing the format. If {@code null} the a
     *            default of <em>yyyy-MM-dd</em> will be used
     */
    public static String toString(Date date, String format) {
	if (date == null){
	    return null;
	}
	String fmt = "yyyy-MM-dd";
	if (format != null){
	    fmt = format;
	}
	SimpleDateFormat dsf = new SimpleDateFormat(fmt);
	return dsf.format(date);
    }

    public static String toEmptyString(Date date, String format) {
	String s = toString(date, format);
	return StringUtils.defaultString(s);
    }

    public static int getInt(String value, int defl) {
	if (value == null)
	    return defl;
	int retVal;
	try{
	    retVal = Integer.parseInt(value);
	}catch(NumberFormatException nfe){
	    retVal = defl;
	}
	return retVal;
    }

    public static long getLong(String value, long defl) {
	if (value == null)
	    return defl;
	long retVal;
	try{
	    retVal = Long.parseLong(value);
	}catch(NumberFormatException nfe){
	    retVal = defl;
	}
	return retVal;
    }

    public static float getFloat(String value, float defl) {
	if (value == null)
	    return defl;
	float retVal;
	try{
	    retVal = Float.parseFloat(value);
	}catch(NumberFormatException nfe){
	    retVal = defl;
	}
	return retVal;
    }

    @SuppressWarnings("deprecation")
    public static int daysBetween(Date date1, Date date2) {
	long diff = date2.getTime() - date1.getTime();
	if (Math.abs(diff) < 86400000L && date1.getDate() == date2.getDate()){
	    return 0;
	}
	return ((int) (diff / 86400000L)) + 1;
	// 86400000 = 1000 * 60 * 60 * 24
    }

    public static Date convertClientTime(String clientTimeString, Date dfltVal) {
	if (clientTimeString == null){
	    return dfltVal;
	}
	int tzIdx = clientTimeString.indexOf("TZ");
	if (tzIdx > 0){
	    return toDate(clientTimeString.substring(0, tzIdx), "HH:mm_yyyy-MM-dd", dfltVal);
	}
	return toDate(clientTimeString, "HH:mm_yyyy-MM-dd", dfltVal);
    }

    public static int getClientTimeZoneOffset(String clientTimeString) {
	String tzValue = null;
	if (clientTimeString != null){
	    int tzIdx = clientTimeString.indexOf("TZ");
	    if (tzIdx > 0){
		tzValue = clientTimeString.substring(tzIdx + 2);
	    }
	}
	if (tzValue != null){
	    try{
		return Integer.parseInt(tzValue);
	    }catch(NumberFormatException nfe){
	    }
	}
	Date now = new Date();
	return now.getTimezoneOffset();
    }

    public static Date toDate(String dateStr, String format, Date dfltVal) {
	if (dateStr == null || format == null){
	    return dfltVal;
	}
	DateFormat dateFormatter = new SimpleDateFormat(format);
	Date date = null;
	try{
	    date = (Date) dateFormatter.parse(dateStr);
	}catch(ParseException e){
	    date = dfltVal;
	}
	return date;
    }

    /**
     * Converts a {@link XMLGregorianCalendar} to a {@link Date}.
     */
    public static Date toUtilDate(XMLGregorianCalendar xmlGregorianCalendar) {
	if (xmlGregorianCalendar == null){
	    return null;
	}
	return xmlGregorianCalendar.toGregorianCalendar().getTime();
    }

    /**
     * Converts a {@link Date} to a {@link XMLGregorianCalendar} while retaining
     * the time information.
     */
    public static XMLGregorianCalendar toXmlGregorianCalendar(Date date) {
	if (date == null){
	    return null;
	}
	GregorianCalendar gregorianCalendar = new GregorianCalendar();
	gregorianCalendar.setTime(date);
	try{
	    return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
	}catch(DatatypeConfigurationException e){
	    logger.error("Failed to convert java.util.Date to XMLGregorianCalendar with time", e);
	    return null;
	}
    }

    /**
     * Converts a {@link Date} to a {@link XMLGregorianCalendar} discarding the
     * time information.
     */
    public static XMLGregorianCalendar toXmlGregorianCalendarNoTimePart(Date date) {
	if (date == null){
	    return null;
	}
	Calendar c = Calendar.getInstance();
	c.setTime(date);
	try{
	    XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
	    xmlGregorianCalendar.setDay(c.get(Calendar.DATE));
	    xmlGregorianCalendar.setMonth(c.get(Calendar.MONTH) + 1);
	    xmlGregorianCalendar.setYear(c.get(Calendar.YEAR));
	    return xmlGregorianCalendar;
	}catch(DatatypeConfigurationException e){
	    logger.error("Failed to convert java.util.Date to XMLGregorianCalendar", e);
	    return null;
	}
    }

    public static double parseDouble(String val, double defaultVal) {
	double result;
	try{
	    result = Double.parseDouble(val);
	}catch(NumberFormatException e){
	    logger.warn("Unavle to pasrse double val from String:" + val + ", Returning default value:" + defaultVal);
	    result = defaultVal;
	}
	return result;
    }

    public static String pad(int value, int width, String padChar) {
	boolean isNegative = (value < 0);
	String valueStr = String.valueOf(Math.abs(value));
	int diff = width - valueStr.length();
	if (isNegative){
	    diff--;
	}
	if (diff <= 0){
	    if (isNegative){
		return "-" + valueStr;
	    }
	    return valueStr;
	}
	StringBuilder sb = new StringBuilder();
	for(int i = 0; i < diff; i++){
	    sb.append(padChar);
	}
	sb.append(valueStr);

	if (isNegative){
	    return "-" + sb.toString();
	}
	return sb.toString();
    }

    public static void adjust2DigitYearDate(Date date) {
	if (date == null){
	    return;
	}
	Calendar d = Calendar.getInstance();
	d.setTime(date);
	int year = d.get(Calendar.YEAR);
	if (year <= 50){
	    d.set(Calendar.YEAR, year + 2000);
	    date.setTime(d.getTimeInMillis());
	}else if (year > 50 && year < 100){
	    d.set(Calendar.YEAR, year + 1900);
	    date.setTime(d.getTimeInMillis());
	}
    }

    /**
     * Generates the ISE transaction Id needed to fetch the same data from CAP
     * and UCS.
     */
    public static String generateTransactionId() {
	DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	Date now = new Date();
	String transactionId = "ISE" + df.format(now);
	return transactionId;
    }

    public static String toStringMap(Map<String, ?> map) {
	if (map == null){
	    return "null";
	}else if (map.isEmpty()){
	    return "empty";
	}
	StringBuilder buf = new StringBuilder();
	Object value;
	int i = 1;
	for(String key: map.keySet()){
	    if (i > 1){
		buf.append(" ");
	    }
	    if (key != null){
		buf.append(key.toString());
		buf.append("=");
		value = map.get(key);
		if (value == null){
		    buf.append("null");
		}else{
		    buf.append(value.toString());
		}
	    }else{
		buf.append("null");
	    }
	    i++;
	}
	return buf.toString();
    }

    public static String toStringList(List<?> list) {
	if (list == null){
	    return "null";
	}else if (list.isEmpty()){
	    return "empty";
	}
	StringBuilder buf = new StringBuilder();
	int i = 1;
	for(Object value: list){
	    if (i > 1){
		buf.append(" ");
	    }
	    buf.append(i);
	    buf.append("..");
	    if (value == null){
		buf.append("null");
	    }else{
		buf.append(value.toString());
	    }
	    i++;
	}
	return buf.toString();
    }

    /**
     * Deep clone an <code>Object</code> using serialization.
     * 
     * <p>
     * This is a workaround for a <a
     * href="https://issues.apache.org/jira/browse/LANG-626">Weblogic 10.3
     * bug</a> that crops up when we use
     * org.apache.commons.lang.SerializationUtils.
     * 
     * @param object
     *            the <code>Serializable</code> object to clone
     * @return the cloned object
     */
    public static Object clone(Serializable object) {
	return SerializationUtils.deserialize(SerializationUtils.serialize(object));
    }

    /**
     * Gets the IP address of the current machine.
     * 
     * @return the IP address; {@code null} if it could not be determined.
     */
    public static String getIPAddress() {

	String ipAddress = null;
	try{
	    return InetAddress.getLocalHost().getHostAddress();
	}catch(UnknownHostException e){
	    logger.error("Unable to get the IpAddress", e);
	}
	logger.debug("IP is " + ipAddress);
	return ipAddress;
    }

    public static String determineNameString(String firstName, String lastName) {
	if (StringUtils.isNotBlank(firstName) && StringUtils.isNotBlank(lastName)){
	    return firstName + " " + lastName;
	}else if (StringUtils.isNotBlank(lastName)){
	    return lastName;
	}
	return null;
    }

    public static Date getTodayDate() {
	Date today = Calendar.getInstance().getTime();
	SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy");
	String d = df.format(today);
	try{
	    today = df.parse(d);
	}catch(ParseException e){
	    logger.error("Error parsing date", e);
	}
	return today;
    }

    public static String formatPhoneNumber(String phoneNum) {
	if (phoneNum == null || phoneNum.length() != 10){
	    logger.warn("Invalid Phone Number:" + phoneNum);
	    return phoneNum;
	}
	return "(" + phoneNum.substring(0, 3) + ")" + phoneNum.substring(3, 6) + "-" + phoneNum.substring(6);
    }
}
