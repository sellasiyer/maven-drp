/**
 * Best Buy Inc (c)2011
 */
package com.bestbuy.bbym.ise.workflow.rules;

import java.util.List;

/**
 * This class is intended to model a multiple choice question, where the choices
 * are supplied, and a single response is expected
 * 
 * @author Animesh Banerjee
 * 
 */
public interface DialogSelectItem extends DialogItem {

    public List<String> getOptions();

    public void setOptions(List<String> options);
}
