package com.bestbuy.bbym.ise.drp.triage;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;

import com.bestbuy.bbym.ise.drp.common.BasePanel;
import com.bestbuy.bbym.ise.drp.domain.Instruction;
import com.bestbuy.bbym.ise.drp.workflow.SingleChoiceWithExplanationWorkflowDialog;

public class InstructionsPanel extends BasePanel {

    private static final long serialVersionUID = 1L;

    public InstructionsPanel(String id, final Instruction instruction) {
	super(id);

	Label checkBoxLabel = new Label("checkboxLabel", "View Details");
	boolean visible = instruction != null && instruction.getDescription() != null;
	checkBoxLabel.setVisible(visible);
	add(checkBoxLabel);

	WebMarkupContainer viewDetailsLinkContainer = new WebMarkupContainer("viewDetailsLinkContainer");
	viewDetailsLinkContainer.setVisible(visible);
	viewDetailsLinkContainer.setOutputMarkupPlaceholderTag(true);
	add(viewDetailsLinkContainer);

	// instruction
	WebMarkupContainer instructionContainer = new WebMarkupContainer("instructionContainer");
	Label instructionLabel = new Label("instructionLabel", new PropertyModel<Instruction>(instruction, "title"));
	instructionLabel.setEscapeModelStrings(false);
	viewDetailsLinkContainer.add(instructionLabel);
	instructionContainer.setVisible(instruction != null && instruction.getTitle() != null);
	viewDetailsLinkContainer.add(instructionContainer);

	// instruction description
	final WebMarkupContainer instructionDescriptionContainer = new WebMarkupContainer(
		"instructionDescriptionContainer");
	instructionDescriptionContainer.setVisible(true);
	instructionDescriptionContainer.setOutputMarkupPlaceholderTag(true);
	instructionContainer.add(instructionDescriptionContainer);

	Label instructionDescription = new Label("instructionDescription",
		new PropertyModel<SingleChoiceWithExplanationWorkflowDialog>(instruction, "description"));
	instructionDescription.setEscapeModelStrings(false);
	instructionDescriptionContainer.add(instructionDescription);

	AjaxLink<Object> viewDetailsLink = new AjaxLink<Object>("viewDetailsLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		instructionDescriptionContainer.setVisible(true);
		target.add(instructionDescriptionContainer);
	    }

	};
	viewDetailsLink.setVisible(true);
	viewDetailsLinkContainer.add(viewDetailsLink);
    }
}
