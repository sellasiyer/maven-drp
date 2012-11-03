/**
 * Best Buy Inc (c) 2011
 */
package com.bestbuy.bbym.ise.drp.util;

import java.io.Serializable;

import org.apache.wicket.PageReference;

/**
 * This class is used by {@linkplain SubmitOnceForm} to uniquely identify a
 * wicket form
 * 
 * @author Animesh Banerjee
 */
public class FormToken implements Serializable {

    private static final long serialVersionUID = 1L;
    private final PageReference reference;
    private final String pathToForm;

    public FormToken(PageReference reference, String pathToForm) {
	this.reference = reference;
	this.pathToForm = pathToForm;
    }

    @Override
    public int hashCode() {
	return 31 * reference.hashCode() * pathToForm.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == this){
	    return true;
	}else if (obj == null){
	    return false;
	}else if (obj instanceof FormToken){
	    FormToken other = (FormToken) obj;
	    return other.reference.equals(reference) && other.pathToForm.equals(pathToForm);
	}
	return false;
    }

}
