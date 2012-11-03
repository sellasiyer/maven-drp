package com.bestbuy.bbym.ise.drp.utils;

import java.lang.reflect.Method;
import java.util.Date;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanAssert {

    private static Logger logger = LoggerFactory.getLogger(BeanAssert.class);

    public static void assertBeanEquals(Object object1, Object object2) throws Exception {
	assertBeanEquals(null, object1, object2);
    }

    public static void assertBeanEquals(String message, Object object1, Object object2) throws Exception {
	if (object1 == null && object2 == null){
	    return;
	}
	if (object1 == null || object2 == null){
	    Assert.assertEquals(message, object1, object2);
	    return;
	}
	Assert.assertSame(message, object1.getClass(), object2.getClass());
	for(Method method: object1.getClass().getMethods()){
	    if ((method.getName().startsWith("get") || method.getName().startsWith("is"))
		    && method.getParameterTypes().length == 0 && !method.getDeclaringClass().equals(Object.class)){
		logger.debug(method.getName());
		Object value1 = method.invoke(object1, null);
		Object value2 = method.invoke(object2, null);
		logger.debug(object1.getClass() + "." + method.getName() + " " + value1 + " " + value2);
		if (method.getReturnType() == byte[].class){
		    Assert.assertArrayEquals(constructMessage(method), (byte[]) value1, (byte[]) value2);
		}else if (!isBeanClass(method.getReturnType())){
		    Assert.assertEquals(constructMessage(method), value1, value2);
		}else{
		    assertBeanEquals(constructMessage(method), value1, value2);
		}
	    }
	}
    }

    public static void assertBeanNotSame(Object object1, Object object2) throws Exception {
	assertBeanNotSame(null, object1, object2);
    }

    public static void assertBeanNotSame(String message, Object object1, Object object2) throws Exception {
	Assert.assertNotSame(message, object1, object2);
	for(Method method: object1.getClass().getMethods()){
	    if ((method.getName().startsWith("get") || method.getName().startsWith("is"))
		    && method.getParameterTypes().length == 0 && !method.getDeclaringClass().equals(Object.class)){
		logger.debug(method.getName());
		Object value1 = method.invoke(object1, null);
		Object value2 = method.invoke(object2, null);
		logger.debug(object1.getClass() + "." + method.getName() + " " + value1 + " " + value2);
		if (isBeanClass(method.getReturnType())){
		    assertBeanNotSame(constructMessage(method), value1, value2);
		}
	    }
	}
    }

    private static String constructMessage(Method method) {
	return method.getDeclaringClass().getName() + "." + method.getName();
    }

    public static boolean isBeanClass(Class clazz) {
	return !String.class.isAssignableFrom(clazz) && !Number.class.isAssignableFrom(clazz)
		&& !Date.class.isAssignableFrom(clazz) && !Boolean.class.isAssignableFrom(clazz)
		&& !Character.class.isAssignableFrom(clazz) && !Byte.class.isAssignableFrom(clazz)
		&& !int.class.isAssignableFrom(clazz) && !long.class.isAssignableFrom(clazz)
		&& !float.class.isAssignableFrom(clazz) && !double.class.isAssignableFrom(clazz)
		&& !char.class.isAssignableFrom(clazz) && !byte.class.isAssignableFrom(clazz)
		&& !boolean.class.isAssignableFrom(clazz) && !clazz.isArray() && !clazz.isEnum();
    }
}
