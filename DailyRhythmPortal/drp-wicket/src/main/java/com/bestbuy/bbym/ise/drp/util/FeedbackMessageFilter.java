package com.bestbuy.bbym.ise.drp.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.util.lang.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeedbackMessageFilter implements IFeedbackMessageFilter {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(FeedbackMessageFilter.class);

    private String name;
    private boolean acceptAllUnspecifiedComponents = true;
    private List<Component> filterInComponents = new ArrayList<Component>();
    private List<Component> filterOutComponents = new ArrayList<Component>();

    public FeedbackMessageFilter(String name) {
	this.name = name;
    }

    public void addFilterInComponent(Component component) {
	filterInComponents.add(component);
    }

    public void addFilterOutComponent(Component component) {
	filterOutComponents.add(component);
    }

    public boolean isAcceptAllUnspecifiedComponents() {
	return acceptAllUnspecifiedComponents;
    }

    public void setAcceptAllUnspecifiedComponents(boolean acceptAllUnspecifiedComponents) {
	this.acceptAllUnspecifiedComponents = acceptAllUnspecifiedComponents;
    }

    @Override
    public boolean accept(FeedbackMessage message) {
	if (acceptAllUnspecifiedComponents){
	    for(Component c: filterOutComponents){
		if (Objects.equal(c, message.getReporter())){
		    return false;
		}
	    }
	    return true;
	}
	logger.trace(name + " checking comp " + message.getReporter().getId());
	for(Component c: filterInComponents){
	    logger.debug("good comp " + c.getId());
	    if (Objects.equal(c, message.getReporter())){
		logger.debug("GOT MATCH");
		return true;
	    }
	}
	logger.debug("NO MATCH");
	return false;
    }
}
