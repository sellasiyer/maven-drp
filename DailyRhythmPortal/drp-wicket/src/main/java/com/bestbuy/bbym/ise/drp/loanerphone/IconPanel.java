package com.bestbuy.bbym.ise.drp.loanerphone;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.TransparentWebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import com.bestbuy.bbym.ise.domain.Carrier;
import com.bestbuy.bbym.ise.drp.domain.Phone;
import com.bestbuy.bbym.ise.drp.domain.PhoneModel;

public class IconPanel<T> extends Panel {

    private static final long serialVersionUID = 1L;

    public IconPanel(String id, IModel<T> model) {
	super(id, model);
	StringBuilder iconClass = new StringBuilder("carrier");
	if (model != null && model.getObject() != null){
	    if (model.getObject() instanceof PhoneModel){
		PhoneModel pm = (PhoneModel) model.getObject();
		if (pm != null && pm.getCarrier() != null && pm.getCarrier().getCarrier() != null){
		    iconClass.append(" ");
		    iconClass.append(getStyleClass(pm.getCarrier().getCarrier()));
		}
	    }else if (model.getObject() instanceof Phone){
		Phone ph = (Phone) model.getObject();
		if (ph != null && ph.getCarrier() != null && ph.getCarrier().getCarrier() != null){
		    iconClass.append(" ");
		    iconClass.append(getStyleClass(ph.getCarrier().getCarrier()));
		}
	    }
	}
	final String carrierIconClass = iconClass.toString();
	TransparentWebMarkupContainer carrierIcon = new TransparentWebMarkupContainer("carrierIcon");
	carrierIcon.add(new Behavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onComponentTag(Component component, ComponentTag tag) {
		tag.put("class", carrierIconClass.toString());
	    }
	});
	add(carrierIcon);
    }

    private String getStyleClass(String carrierLabel) {
	Carrier carrier = Carrier.fromLabel(carrierLabel);
	return (carrier == null)?"":carrier.getStyleClass();
    }
}
