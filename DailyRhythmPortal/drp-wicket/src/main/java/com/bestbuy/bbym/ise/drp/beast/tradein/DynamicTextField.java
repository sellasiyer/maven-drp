package com.bestbuy.bbym.ise.drp.beast.tradein;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.AbstractValidator;

import com.bestbuy.bbym.ise.drp.beast.tradein.validator.LengthValidator;
import com.bestbuy.bbym.ise.drp.util.behavior.DefaultFocusBehavior;
import com.bestbuy.bbym.ise.drp.validator.IsRequiredValidator;

/**
 * Used dynamically add text field. 
 * 
 * @author a915722
 *
 */
public class DynamicTextField extends Panel {

    private final TextField<String> textField;
    private final Label label;
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public DynamicTextField(String id, ProductQuestion question, boolean focus,
	    final NotificationPanel notificationPanel) {
	super(id);

	this.setOutputMarkupId(true);

	textField = new TextField<String>("text", new PropertyModel<String>(question, "value"));
	textField.setOutputMarkupId(true);

	textField.add(new IsRequiredValidator(question.getName()));

	if (focus){
	    textField.add(new DefaultFocusBehavior());
	}

	if (question.getValidators() != null && question.getValidators().size() > 0){
	    for(AbstractValidator current: question.getValidators()){
		textField.add(current);
		if (current instanceof LengthValidator){
		    LengthValidator lengthValidator = (LengthValidator) current;
		    textField.add(AttributeModifier.replace("maxLength", lengthValidator.getMaxLength()));
		}
	    }
	}

	add(textField);

	label = new Label("label", new Model<String>(question.getName()));
	add(label);
    }

}
