package com.bestbuy.bbym.ise.drp.beast.tradein;

import java.util.Date;

import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.AbstractValidator;

import com.bestbuy.bbym.ise.drp.util.behavior.DefaultFocusBehavior;
import com.bestbuy.bbym.ise.drp.validator.IsDateRequiredValidator;

/**
 * Used dynamically add date text field. 
 * 
 * @author a915722
 */
public class DynamicDateTextField extends Panel {

    private static final long serialVersionUID = 1L;

    private final DateTextField dateTextField;
    private final Label label;

    public DynamicDateTextField(String id, ProductQuestion question, boolean focus,
	    final NotificationPanel notificationPanel) {
	super(id);

	this.setOutputMarkupId(true);

	dateTextField = DateTextField.forDatePattern("dateTextField", new PropertyModel<Date>(question, "date"),
		"MM/dd/yy");
	dateTextField.setOutputMarkupId(true);
	dateTextField.setOutputMarkupPlaceholderTag(true);
	add(dateTextField);

	dateTextField.add(new IsDateRequiredValidator(question.getName()));

	if (focus){
	    dateTextField.add(new DefaultFocusBehavior());
	}

	if (question.getValidators() != null && question.getValidators().size() > 0){
	    for(AbstractValidator current: question.getValidators()){
		dateTextField.add(current);
	    }
	}

	label = new Label("label", new Model<String>(question.getName()));
	add(label);
    }

}
