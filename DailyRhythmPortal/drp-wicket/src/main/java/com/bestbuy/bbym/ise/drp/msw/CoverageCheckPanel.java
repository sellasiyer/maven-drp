package com.bestbuy.bbym.ise.drp.msw;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.panel.Panel;

public class CoverageCheckPanel extends Panel {

    class CustomLink extends AjaxLink {

	private static final long serialVersionUID = 1L;

	private String url;

	public CustomLink(String id, String url) {
	    super(id);
	    this.url = url;
	}

	@Override
	public void onClick(AjaxRequestTarget target) {
	    String js = "window.open('" + url + "')";
	    target.appendJavaScript(js);
	}
    }

    private static final long serialVersionUID = 1L;

    public CoverageCheckPanel(String id, final ModalWindow modalWindow) {
	super(id);

	modalWindow.setTitle("Coverage Check");
	modalWindow.setInitialHeight(200);
	modalWindow.setInitialWidth(350);

	add(new CustomLink("coverageLink1", "https://www.verizonwireless.com/coveragelocator"));
	add(new CustomLink("coverageLink2", "http://coverage.sprint.com/"));
	add(new CustomLink("coverageLink3", "http://www.wireless.att.com/coverageviewer/#?type=voice"));
	add(new CustomLink("coverageLink4", "http://www.t-mobile.com/coverage/pcc.aspx"));

    }
}
