/**
 * Best Buy Inc (c)2011
 */
package com.bestbuy.bbym.ise.workflow.rules;

/**
 * Marker interface for all the types of questions and select items that the
 * rules engine needs to tell the UI to show
 * 
 * @author Animesh Banerjee
 * 
 */
public interface DialogItem {

    public String getName();

    public void setName(String name);

    public void setAnswer(String answer);

    public String getAnswer();

}
