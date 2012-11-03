package com.bestbuy.bbym.ise.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Calendar;
import java.util.Date;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Test;

/**
 * JUnit test for {@link Util}.
 */
public class UtilTest {

    /**
     * Test {@link Util#toXmlGregorianCalendar(Date)}.
     */
    @Test
    public void tesToXmlGregorianCalendar() throws Exception {
	// Check null date
	assertNull("Incorrect XMLGregorianCalendar", Util.toXmlGregorianCalendar(null));

	Calendar calendar = Calendar.getInstance();
	calendar.set(Calendar.YEAR, 2012);
	calendar.set(Calendar.MONTH, 11);
	calendar.set(Calendar.DATE, 31);
	calendar.set(Calendar.HOUR_OF_DAY, 15);
	calendar.set(Calendar.MINUTE, 58);
	calendar.set(Calendar.SECOND, 59);
	Date date = calendar.getTime();
	XMLGregorianCalendar xmlCalendar = Util.toXmlGregorianCalendar(date);
	assertNotNull("Incorrect XMLGregorianCalendar", xmlCalendar);
	assertEquals("Incorrect year", calendar.get(Calendar.YEAR), xmlCalendar.getYear());
	assertEquals("Incorrect month", calendar.get(Calendar.MONTH) + 1, xmlCalendar.getMonth());
	assertEquals("Incorrect day", calendar.get(Calendar.DATE), xmlCalendar.getDay());
	assertEquals("Incorrect hour", calendar.get(Calendar.HOUR_OF_DAY), xmlCalendar.getHour());
	assertEquals("Incorrect minutes", calendar.get(Calendar.MINUTE), xmlCalendar.getMinute());
	assertEquals("Incorrect seconds", calendar.get(Calendar.SECOND), xmlCalendar.getSecond());
    }

    /**
     * Test {@link Util#toXmlGregorianCalendarNoTimePart(Date)}.
     */
    @Test
    public void testToXmlGregorianCalendarNoTimePart() throws Exception {
	// Check null date
	assertNull("Incorrect XMLGregorianCalendar", Util.toXmlGregorianCalendarNoTimePart(null));

	Calendar calendar = Calendar.getInstance();
	calendar.set(Calendar.YEAR, 2012);
	calendar.set(Calendar.MONTH, 11);
	calendar.set(Calendar.DATE, 31);
	Date date = calendar.getTime();
	XMLGregorianCalendar xmlCalendar = Util.toXmlGregorianCalendarNoTimePart(date);
	assertNotNull("Incorrect XMLGregorianCalendar", xmlCalendar);
	assertEquals("Incorrect year", calendar.get(Calendar.YEAR), xmlCalendar.getYear());
	assertEquals("Incorrect month", calendar.get(Calendar.MONTH) + 1, xmlCalendar.getMonth());
	assertEquals("Incorrect day", calendar.get(Calendar.DATE), xmlCalendar.getDay());
	assertEquals("Incorrect hour", DatatypeConstants.FIELD_UNDEFINED, xmlCalendar.getHour());
	assertEquals("Incorrect minutes", DatatypeConstants.FIELD_UNDEFINED, xmlCalendar.getMinute());
	assertEquals("Incorrect seconds", DatatypeConstants.FIELD_UNDEFINED, xmlCalendar.getSecond());
	assertEquals("Incorrect milliseconds", DatatypeConstants.FIELD_UNDEFINED, xmlCalendar.getMillisecond());
    }

}
