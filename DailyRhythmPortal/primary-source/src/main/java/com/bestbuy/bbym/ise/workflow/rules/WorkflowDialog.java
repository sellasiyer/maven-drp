/**
 * Best Buy Inc (c)2011
 */
package com.bestbuy.bbym.ise.workflow.rules;

import java.util.List;

import com.bestbuy.bbym.ise.workflow.WorkflowService;

/**
 * This class is used to convey the state of the UI dialog between the user and
 * the workflow service engine. It is <em>not</em> used between the UI and the
 * Service. The service is responsible for translating this to a form the UI can
 * consume.
 * 
 * @author Animesh Banerjee
 * 
 * @see WorkflowService
 */
public interface WorkflowDialog {

    public List<DialogItem> getDialogItems();

    public DialogSelectItem addDialogSelectItem(String name);

    public DialogItem addDialogItem(String name);

    // Type of dialog that will be created for the UI
    public String getName();
}
