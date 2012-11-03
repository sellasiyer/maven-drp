package com.bestbuy.bbym.ise.drp.beast.tradein;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.AbstractValidator;

import com.bestbuy.bbym.ise.drp.util.SelectItem;
import com.bestbuy.bbym.ise.drp.util.behavior.DefaultFocusBehavior;
import com.bestbuy.bbym.ise.drp.validator.IsSelectRequiredValidator;

/**
 * Used dynamically add text field. 
 * 
 * @author a915722
 *
 */
public class DynamicSelectField extends Panel {

    private final DropDownChoice<SelectItem<String>> select;
    private final Label label;
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public DynamicSelectField(String id, ProductQuestion question, boolean focus,
	    final NotificationPanel notificationPanel) {
	super(id);
	this.setOutputMarkupId(true);
	ChoiceRenderer<SelectItem<String>> choiceRenderer = new ChoiceRenderer<SelectItem<String>>("value", "key");

	select = new DropDownChoice("select", new PropertyModel<SelectItem<String>>(question, "selectOption"), question
		.getOptions(), choiceRenderer);

	if (focus){
	    select.add(new DefaultFocusBehavior());
	}

	select.add(new IsSelectRequiredValidator(question.getName()));

	if (question.getValidators() != null && question.getValidators().size() > 0){
	    for(AbstractValidator current: question.getValidators()){
		select.add(current);
	    }
	}
	select.setOutputMarkupId(true);
	add(select);

	label = new Label("label", new Model<String>(question.getName()));
	add(label);
    }

}
