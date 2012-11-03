package com.bestbuy.bbym.ise.drp.msw;

import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.panel.Panel;

public class NoContractCoveragePanel extends Panel {

    private static final long serialVersionUID = 1L;

    public NoContractCoveragePanel(String id, final ModalWindow modalWindow) {
	super(id);

	modalWindow.setTitle("No-Contract Coverage Maps");
	modalWindow.setInitialHeight(300);
	modalWindow.setInitialWidth(350);

	add(new ExternalLink("link1",
		"http://www.tracfone.com/cellular_coverage.jsp?nextPage=cellular_coverage.jsp&task=cellcov"));
	add(new ExternalLink("link2", "http://www.boostmobile.com/coverage/"));
	add(new ExternalLink("link3", "http://prepaid-phones.t-mobile.com/prepaid-coverage"));
	add(new ExternalLink("link4", "http://www.wireless.att.com/coverageviewer/#?type=gophone&opt=payg"));
	add(new ExternalLink("link5", "http://www.virginmobileusa.com/check-cell-phone-coverage"));

	add(new ExternalLink("link6", "http://www.mycricket.com/coverage/maps/wireless"));
	add(new ExternalLink(
		"link7",
		"http://vzwmap.verizonwireless.com/dotcom/coveragelocator/Default.aspx?requesttype=NEWREQUEST?requesttype=newsearch&coveragetype=prepay"));
	add(new ExternalLink("link8", "http://www.net10.com/jsplib/verify_mapcov.jsp"));
	add(new ExternalLink("link9", "http://www.greatcall.com/CheckCoverage/"));
	add(new ExternalLink("link10", "http://www.metropcs.com/metro/maps/coverage-map.jsp#coverage-map-tab"));

    }
}
