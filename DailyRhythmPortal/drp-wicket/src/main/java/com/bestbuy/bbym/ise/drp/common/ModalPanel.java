package com.bestbuy.bbym.ise.drp.common;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ModalPanel extends BasePanel {

    private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(ModalPanel.class);

    public ModalPanel(String id) {
	super(id);
	setVisible(false);
	setMarkupId(id);
	setOutputMarkupId(true);
	setOutputMarkupPlaceholderTag(true);
    }

    public final void open(AjaxRequestTarget target) {
	setVisible(true);
	target.add(this);
	onOpen(target);
	update(target);
	String openModalJS = getOpenModalJS() + getAlignModalJS();
	logger.debug("openModalJS=" + openModalJS);
	target.appendJavaScript(openModalJS);
    }

    public final void refresh(AjaxRequestTarget target) {
	update(target);
	String refreshModalJS = getRefreshModalJS() + getAlignModalJS();
	logger.debug("refreshModalJS=" + refreshModalJS);
	target.appendJavaScript(refreshModalJS);
    }

    public final void close(AjaxRequestTarget target) {
	setVisible(false);
	resetAfterClose();
	target.add(this);
	onClose(target);
    }

    public final boolean isOpen() {
	return isVisible();
    }

    public String getAlignModalJS() {
	return "verticalAlignModal('" + getModalSelector() + "');";
    }

    public String getModalSelector() {
	return "#" + getId() + " .modal";
    }

    // FIXME make abstract
    public String getOpenModalJS() {
	return "";
    }

    // FIXME make abstract
    public String getRefreshModalJS() {
	return "";
    }

    // FIXME make abstract
    public void onOpen(AjaxRequestTarget target) {
    }

    // FIXME make abstract
    public void resetAfterClose() {
    }

    public abstract void update(AjaxRequestTarget target);

    public abstract void onClose(AjaxRequestTarget target);
}
