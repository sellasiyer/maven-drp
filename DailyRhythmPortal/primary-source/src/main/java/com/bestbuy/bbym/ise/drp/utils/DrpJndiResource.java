/**
 * 
 */
package com.bestbuy.bbym.ise.drp.utils;

/**
 * @author a218635
 * 
 */

import javax.naming.Context;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jndi.JndiCallback;
import org.springframework.jndi.JndiTemplate;

public class DrpJndiResource implements JndiCallback {

    private static Logger logger = LoggerFactory.getLogger(DrpJndiResource.class);
    private String key;

    public DrpJndiResource(String key) {
	this.key = key;
    }

    public Object getJndiResource() {

	try{
	    JndiTemplate template = new JndiTemplate();
	    DrpJndiResource callback = new DrpJndiResource(this.key);
	    Object result = template.execute(callback);
	    return result;
	}catch(NamingException e){
	    logger.error("Error getting JNDI resource", e);
	    return null;
	}
    }

    public Object doInContext(Context context) throws NamingException {
	logger.debug("Start lookup operation");
	Object value = context.lookup(key);
	logger.debug("End lookup operation");
	return value;
    }
}
