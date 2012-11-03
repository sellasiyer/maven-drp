package com.bestbuy.bbym.ise.drp.util.panel;

import java.math.BigDecimal;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardEmployeeNotionalMargin;
import com.bestbuy.bbym.ise.drp.util.MoneyFormatter;

public abstract class ProgressMetersPanel extends Panel {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(ProgressMetersPanel.class);

    // Model Data
    private List<ScoreboardEmployeeNotionalMargin> employeeDataList;
    private String productivityLabel;
    private static MoneyFormatter<BigDecimal> moneyFmt = new MoneyFormatter<BigDecimal>(true, false);

    // Wicket Elements
    private final String na = getString("notApplicable.label");

    public ProgressMetersPanel(final String id, final String title, final String productivityLabel) {
	super(id);
	setMarkupId(id);
	setOutputMarkupId(true);
	setOutputMarkupPlaceholderTag(true);
	this.productivityLabel = productivityLabel;
	add(new Label("title", title));
	add(new Label("topName", na));
	add(new Label("topProductivity", ""));
	add(new Label("topProductivityLabel", productivityLabel));
	add(createEmptyPanel("progressMeterListView"));
    }

    public void refresh(AjaxRequestTarget target, List<ScoreboardEmployeeNotionalMargin> employeeDataList,
	    ScoreboardEmployeeNotionalMargin topEmployeeData) {
	this.employeeDataList = employeeDataList;
	if (employeeDataList == null || employeeDataList.isEmpty()){
	    replace(createEmptyPanel("progressMeterListView"));
	    return;
	}
	logger.debug("refresh with employee list size=" + employeeDataList.size());
	final String id = getId();

	if (topEmployeeData == null || topEmployeeData.getEmployeeShift() == null){
	    replace(new Label("topName", na));
	    replace(new Label("topProductivity", ""));
	}else{
	    replace(new Label("topName", topEmployeeData.getEmployeeShift().getEmpFullName()));
	    replace(new Label("topProductivity", moneyFmt.format(new BigDecimal(topEmployeeData.getProductivity()))));
	}

	ListView<ScoreboardEmployeeNotionalMargin> employeeListView = new ListView<ScoreboardEmployeeNotionalMargin>(
		"progressMeterListView", new PropertyModel<List<ScoreboardEmployeeNotionalMargin>>(this,
			"employeeDataList")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void populateItem(final ListItem<ScoreboardEmployeeNotionalMargin> listItem) {
		final ScoreboardEmployeeNotionalMargin employeeData = listItem.getModelObject();
		logger.trace("employeeData=" + employeeData);
		listItem.add(new Label("productivityLabel", productivityLabel));
		if (employeeData == null || employeeData.getEmployeeShift() == null){
		    listItem.add(new Label("name", na));
		}else{
		    listItem.add(new Label("name", employeeData.getEmployeeShift().getEmpFullName()));
		}

		final AjaxLink<Object> expandLink = new AjaxLink<Object>("expandLink") {

		    private static final long serialVersionUID = 1L;

		    @Override
		    public void onClick(AjaxRequestTarget target) {
			onClickExpandLink(target, employeeData);
		    }
		};
		expandLink.setOutputMarkupPlaceholderTag(true);
		listItem.add(expandLink);

		final WebMarkupContainer bar = new WebMarkupContainer("bar");
		bar.setMarkupId(id + "-bar" + listItem.getIndex());
		bar.setOutputMarkupId(true);
		listItem.add(bar);
	    }

	};
	employeeListView.setOutputMarkupPlaceholderTag(true);
	replace(employeeListView);

	StringBuilder sb = new StringBuilder();
	for(int i = 0; i < employeeDataList.size(); i++){
	    ScoreboardEmployeeNotionalMargin employeeData = employeeDataList.get(i);
	    if (employeeData != null){
		sb.append(buildProgressMeterJS(employeeData, id + "-bar" + i));
	    }
	}
	target.add(this);
	logger.debug("refreshJS=" + sb.toString());
	target.appendJavaScript(sb.toString());
    }

    public static String buildProgressMeterJS(final ScoreboardEmployeeNotionalMargin employeeData, final String barId) {
	if (employeeData == null){
	    return "";
	}
	StringBuilder sb = new StringBuilder();
	sb.append("setOtherProgressMeter('");
	sb.append(barId);
	sb.append("', ");
	sb.append(employeeData.getCurrentValue());
	sb.append(", ");
	sb.append(employeeData.getTargetValue());
	sb.append(", ");
	sb.append(employeeData.getProductivity());
	sb.append(", '");
	sb.append(moneyFmt.format(new BigDecimal(employeeData.getCurrentValue())));
	sb.append("', '");
	sb.append(moneyFmt.format(new BigDecimal(employeeData.getTargetValue())));
	sb.append("', '");
	sb.append(moneyFmt.format(new BigDecimal(employeeData.getProductivity() < 0?0:employeeData.getProductivity())));
	sb.append("');");
	return sb.toString();
    }

    public List<ScoreboardEmployeeNotionalMargin> getEmployeeDataList() {
	return employeeDataList;
    }

    private Panel createEmptyPanel(final String id) {
	final Panel emptyPanel = new EmptyPanel(id);
	emptyPanel.add(new Label("productivityLabel", productivityLabel));
	emptyPanel.add(new Label("name", ""));

	final AjaxLink<Object> expandLink = new AjaxLink<Object>("expandLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
	    }
	};
	expandLink.setOutputMarkupPlaceholderTag(true);
	emptyPanel.add(expandLink);

	final WebMarkupContainer bar = new WebMarkupContainer("bar");
	emptyPanel.add(bar);
	emptyPanel.setVisibilityAllowed(false);
	return emptyPanel;
    }

    public abstract void onClickExpandLink(AjaxRequestTarget target, final ScoreboardEmployeeNotionalMargin employeeData);
}
