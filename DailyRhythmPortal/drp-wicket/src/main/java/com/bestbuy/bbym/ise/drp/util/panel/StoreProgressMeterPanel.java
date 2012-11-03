package com.bestbuy.bbym.ise.drp.util.panel;

import java.math.BigDecimal;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardNotionalMargin;
import com.bestbuy.bbym.ise.drp.util.MoneyFormatter;

public abstract class StoreProgressMeterPanel extends Panel {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(StoreProgressMeterPanel.class);

    private static MoneyFormatter<BigDecimal> moneyFmt = new MoneyFormatter<BigDecimal>(true, false);

    private final static ScoreboardNotionalMargin emptyStoreMeterData = new ScoreboardNotionalMargin();
    static{
	emptyStoreMeterData.setCurrentValue(0);
	emptyStoreMeterData.setTargetValue(0);
	emptyStoreMeterData.setNextHour("");
	emptyStoreMeterData.setNextHourPercentage(50);
	emptyStoreMeterData.setProductivity(0);
    }

    public StoreProgressMeterPanel(final String id, final String name) {
	this(id, name, null);
    }

    public StoreProgressMeterPanel(final String id, final String name, final String productivityLabel) {
	super(id);
	setMarkupId(id);
	setOutputMarkupId(true);
	setOutputMarkupPlaceholderTag(true);
	add(new Label("name", name));

	final WebMarkupContainer productivity = new WebMarkupContainer("productivity");
	if (productivityLabel == null){
	    productivity.add(new Label("productivityLabel", "xyz"));
	    productivity.setVisibilityAllowed(false);
	}else{
	    productivity.add(new Label("productivityLabel", productivityLabel));
	}
	add(productivity);

	add(AttributeModifier.append("class", new Model<String>(getCssClasses())));
    }

    public void refresh(final AjaxRequestTarget target, final ScoreboardNotionalMargin storeMeterData) {
	String refreshJS = buildRefreshJS(storeMeterData);
	logger.debug("refreshJS=" + refreshJS);
	target.appendJavaScript(refreshJS);
    }

    private String buildRefreshJS(final ScoreboardNotionalMargin storeMeterData) {
	if (storeMeterData == null){
	    return "";
	}
	StringBuilder sb = new StringBuilder();
	sb.append("setStoreProgressMeter('");
	sb.append(getId());
	sb.append("', ");
	sb.append(storeMeterData.getCurrentValue());
	sb.append(", ");
	sb.append(storeMeterData.getTargetValue());
	sb.append(", '");
	if (storeMeterData.getNextHour() != null){
	    sb.append(storeMeterData.getNextHour());
	}
	sb.append("', ");
	sb.append(((float) storeMeterData.getNextHourPercentage()) / 100.0f);
	sb.append(", ");
	sb.append(storeMeterData.getProductivity());
	sb.append(", '");
	sb.append(moneyFmt.format(new BigDecimal(storeMeterData.getCurrentValue())));
	sb.append("', '");
	sb.append(moneyFmt.format(new BigDecimal(storeMeterData.getTargetValue())));
	sb.append("', '");
	sb.append(moneyFmt.format(new BigDecimal(storeMeterData.getProductivity() < 0?0:storeMeterData
		.getProductivity())));
	sb.append("');");
	return sb.toString();
    }

    public abstract String getCssClasses();

    public String getOnDomReadyJS() {
	return buildRefreshJS(emptyStoreMeterData);
    }
}
