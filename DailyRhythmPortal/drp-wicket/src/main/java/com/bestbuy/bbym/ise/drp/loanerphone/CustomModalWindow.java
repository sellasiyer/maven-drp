package com.bestbuy.bbym.ise.drp.loanerphone;

import org.apache.wicket.Component;
import org.apache.wicket.IClusterable;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;

import com.bestbuy.bbym.ise.drp.common.BasePanel;

/**
 * A simple modal window that supports custom markup, but isn't draggable,
 * resizable, or have any other of the features of
 * org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.
 */
public class CustomModalWindow extends BasePanel {

    private static final long serialVersionUID = 1L;
    private Component content;
    public static final String CONTENT_ID = "content";
    private CustomWindowClosedCallback windowClosedCallback = null;

    /** empty container - used when no component is added */
    private WebMarkupContainer empty;

    public static interface CustomWindowClosedCallback extends IClusterable {

	/**
	 * Called after the window has been closed.
	 * 
	 * @param target
	 *            <code>{@link AjaxRequestTarget}</code> instance bound with
	 *            the ajax request.
	 */
	public void onClose(AjaxRequestTarget target);
    }

    public CustomModalWindow(String id) {
	super(id);
	setOutputMarkupId(true);
	setOutputMarkupPlaceholderTag(true);
	setVisible(false);
	add(empty = new WebMarkupContainer(getContentId()));
    }

    public void setContent(Component content) {
	this.content = content;
	if (content.getId().equals(getContentId()) == false){
	    throw new WicketRuntimeException("Modal window content id is wrong. Component ID:" + content.getId()
		    + "; content ID: " + getContentId());
	}
	replace(content);
    }

    public String getContentId() {
	return CONTENT_ID;
    }

    public void show(AjaxRequestTarget target) {
	this.setVisible(true);
	target.add(this);
    }

    public void hide(AjaxRequestTarget target) {
	this.setVisible(false);
	target.add(this);
    }

    public void close(AjaxRequestTarget target) {
	this.setVisible(false);
	target.add(this);
	if (windowClosedCallback != null){
	    windowClosedCallback.onClose(target);
	}
    }

    public CustomModalWindow setWindowClosedCallback(final CustomWindowClosedCallback callback) {
	windowClosedCallback = callback;
	return this;
    }
}
