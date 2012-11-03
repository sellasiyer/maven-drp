package com.bestbuy.bbym.ise.drp.triage2;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.ResourceModel;

import com.bestbuy.bbym.ise.drp.common.ModalPanel;
import com.bestbuy.bbym.ise.drp.domain.Config;
import com.bestbuy.bbym.ise.drp.util.FormatPropertyColumn;

public abstract class DeviceModalPanel extends ModalPanel {

    private static final long serialVersionUID = 1L;

    private boolean yesSelected = false;

    private DeviceDataProvider deviceDataProvider = new DeviceDataProvider();

    public DeviceModalPanel(String id, final String yesLabel, final String noLabel) {
	super(id);

	final String na = getString("notApplicable.label");

	Label messageLabel = new Label("messageLabel", new String(""));
	messageLabel.setVisible(false);
	add(messageLabel);

	final List<IColumn<Config>> columns = new ArrayList<IColumn<Config>>();
	columns.add(new FormatPropertyColumn<Config, String>(new ResourceModel(
		"triageIssues.deviceModalPanel.name.column"), "description", "description", null, na));
	columns.add(new FormatPropertyColumn<Config, String>(new ResourceModel(
		"triageIssues.deviceModalPanel.sku.column"), "paramValue", "paramValue", null, na));

	final AjaxFallbackDefaultDataTable<Config> devicesTable = new AjaxFallbackDefaultDataTable<Config>(
		"devicesTable", columns, deviceDataProvider, 200);
	devicesTable.setMarkupId("devicesTable");
	devicesTable.setOutputMarkupId(true);
	devicesTable.setOutputMarkupPlaceholderTag(true);
	add(devicesTable);

	AjaxLink<Void> yesLink = new AjaxLink<Void>("yesLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		yesSelected = true;
		close(target);
	    }
	};
	yesLink.add(new Label("yesLinkLabel", yesLabel));
	yesLink.setMarkupId("yesLink");
	yesLink.setOutputMarkupId(true);
	yesLink.setOutputMarkupPlaceholderTag(true);
	add(yesLink);

	AjaxLink<Void> noLink = new AjaxLink<Void>("noLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		yesSelected = false;
		close(target);
	    }
	};
	noLink.add(new Label("noLinkLabel", noLabel));
	noLink.setMarkupId("cancelLink");
	noLink.setOutputMarkupId(true);
	noLink.setOutputMarkupPlaceholderTag(true);
	add(noLink);
    }

    public boolean isYesSelected() {
	return yesSelected;
    }

    public void setMessage(String message) {
	addOrReplace(new Label("messageLabel", message));
    }

    @Override
    public String getOpenModalJS() {
	return "setModalPanelFocus('#yesLink');";
    }

    @Override
    public void update(AjaxRequestTarget target) {
    }

    public void setDeviceList(List<Config> deviceList) {
	deviceDataProvider.setDeviceList(deviceList);
    }

    @Override
    public String getModalSelector() {
	return "#" + getId() + " .new-modal";
    }
}
