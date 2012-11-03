package com.bestbuy.bbym.ise.drp.dashboard;

import org.apache.wicket.Component;

import com.bestbuy.bbym.ise.domain.Line;
import com.bestbuy.bbym.ise.drp.util.MapFormatter;

public class CarrierLineStatus {

    private CarrierLineStatus() {
    }

    public static MapFormatter<String> generateMapFormatter(Component component) {
	MapFormatter<String> statusFmt = new MapFormatter<String>();
	statusFmt.addMapping(Line.LINE_STATUS_ACTIVE, component.getString("carrierStatus." + Line.LINE_STATUS_ACTIVE));
	statusFmt.addMapping(Line.LINE_STATUS_TENTATIVE, component.getString("carrierStatus."
		+ Line.LINE_STATUS_TENTATIVE));
	statusFmt.addMapping(Line.LINE_STATUS_SUSPENDED, component.getString("carrierStatus."
		+ Line.LINE_STATUS_SUSPENDED));
	statusFmt.addMapping(Line.LINE_STATUS_CLOSED, component.getString("carrierStatus." + Line.LINE_STATUS_CLOSED));
	statusFmt
		.addMapping(Line.LINE_STATUS_UNKNOWN, component.getString("carrierStatus." + Line.LINE_STATUS_UNKNOWN));
	statusFmt.addMapping(Line.LINE_STATUS_CANCELLED, component.getString("carrierStatus."
		+ Line.LINE_STATUS_CANCELLED));
	return statusFmt;
    }
}
