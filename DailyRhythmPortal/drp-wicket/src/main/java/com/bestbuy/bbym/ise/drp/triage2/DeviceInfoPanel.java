/**
 * Best Buy Inc (c) 2012
 */
package com.bestbuy.bbym.ise.drp.triage2;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.Device;
import com.bestbuy.bbym.ise.domain.ProtectionPlan;
import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.BasePanel;

/**
 * @author Yernar Khuangan (a1003810)
 * @version 1.0
 * @since iteration 22
 */
public class DeviceInfoPanel extends BasePanel {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(DeviceInfoPanel.class);
    private boolean isHistoryPanelShown = false;
    private String histBtnLabel = "";

    public DeviceInfoPanel(String id) {
	super(id);

	final Device device;
	ProtectionPlan protectionPlan;
	DailyRhythmPortalSession session = getDailyRhythmPortalSession();
	if (session.getSelectedLine() != null && session.getSelectedLine().getDevice() != null){
	    device = session.getSelectedLine().getDevice();
	    if (device.getProtectionPlan() != null){
		protectionPlan = device.getProtectionPlan();
	    }else{
		protectionPlan = new ProtectionPlan();
	    }
	}else{
	    device = new Device();
	    protectionPlan = new ProtectionPlan();
	}

	add(new Label("deviceDesc", device.getDescription()));
	add(new Label("serialNumber", device.getSerialNumber()));
	Label gspLabel = new Label("gspLabel", Model.of(getString("triageDeviceInfoPanel.gsp.label")));
	if (protectionPlan.getDescription() == null || protectionPlan.getDescription().isEmpty()){
	    gspLabel.setVisible(false);
	}
	add(gspLabel);
	add(new Label("protectionPlan", protectionPlan.getDescription()));

	final TriageHistoryPanel historyPanel = new TriageHistoryPanel("triageHistoryPanel", device.getSerialNumber());
	historyPanel.setOutputMarkupId(true);

	final Label labelHistoryButton = new Label("labelHistoryButton",
		new PropertyModel<String>(this, "histBtnLabel"));
	histBtnLabel = getString("triageDeviceInfoPanel.historyButton.label");
	labelHistoryButton.setOutputMarkupId(true);
	labelHistoryButton.setOutputMarkupPlaceholderTag(true);

	AjaxLink<Void> triageHistoryButton = new AjaxLink<Void>("triageHistoryButton") {

	    private static final long serialVersionUID = 4509013294413267326L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		if (!isHistoryPanelShown){
		    target.add(historyPanel);
		    target.appendJavaScript("$('#device-history li:odd').addClass('odd'); "
			    + "$('#device-history, #device-history-container .shadow').slideDown(500);");
		    histBtnLabel = getString("triageDeviceInfoPanel.historyButtonClose.label");
		}else{

		    target
			    .appendJavaScript("$('#device-history, #device-history-container .shadow').slideToggle(500);");
		    histBtnLabel = getString("triageDeviceInfoPanel.historyButton.label");
		}
		isHistoryPanelShown = !isHistoryPanelShown;
		target.add(labelHistoryButton);
	    }
	};
	triageHistoryButton.setOutputMarkupId(true);
	triageHistoryButton.setOutputMarkupPlaceholderTag(true);

	if (historyPanel.isTriageHistoryEmpty()){
	    triageHistoryButton.setVisible(false);
	}

	triageHistoryButton.add(labelHistoryButton);

	add(triageHistoryButton);
	add(historyPanel);
    }

    public String getHistBtnLabel() {
	return histBtnLabel;
    }

}
