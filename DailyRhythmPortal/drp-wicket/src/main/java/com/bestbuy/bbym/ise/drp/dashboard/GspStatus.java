package com.bestbuy.bbym.ise.drp.dashboard;

import org.apache.wicket.Component;

import com.bestbuy.bbym.ise.domain.ServicePlan;
import com.bestbuy.bbym.ise.drp.util.MapFormatter;

public class GspStatus {

    private GspStatus() {
    }

    public static MapFormatter<String> generateMapFormatter(Component component) {
	MapFormatter<String> statusFmt = new MapFormatter<String>();
	statusFmt.addMapping(ServicePlan.PLAN_STATUS_ACTIVE, component.getString("gspCancel.status."
		+ ServicePlan.PLAN_STATUS_ACTIVE));
	statusFmt.addMapping(ServicePlan.PLAN_STATUS_INACTIVE, component.getString("gspCancel.status."
		+ ServicePlan.PLAN_STATUS_INACTIVE));
	statusFmt.addMapping(ServicePlan.PLAN_STATUS_ON_HOLD, component.getString("gspCancel.status."
		+ ServicePlan.PLAN_STATUS_ON_HOLD));
	statusFmt.addMapping(ServicePlan.PLAN_STATUS_CANCELLED, component.getString("gspCancel.status."
		+ ServicePlan.PLAN_STATUS_CANCELLED));
	statusFmt.addMapping(ServicePlan.PLAN_STATUS_UNKNOWN, component.getString("gspCancel.status."
		+ ServicePlan.PLAN_STATUS_UNKNOWN));
	statusFmt.addMapping(ServicePlan.PLAN_STATUS_MARKEDFORCANCEL, component.getString("gspCancel.status."
		+ ServicePlan.PLAN_STATUS_MARKEDFORCANCEL));
	return statusFmt;
    }

}
