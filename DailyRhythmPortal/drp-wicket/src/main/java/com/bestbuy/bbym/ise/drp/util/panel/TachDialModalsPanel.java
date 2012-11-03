package com.bestbuy.bbym.ise.drp.util.panel;

import java.math.BigDecimal;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardWidget;
import com.bestbuy.bbym.ise.drp.util.MoneyFormatter;

public class TachDialModalsPanel extends Panel {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(TachDialModalsPanel.class);

    // Model Data
    private List<ScoreboardWidget> tachDialList;
    private static MoneyFormatter<BigDecimal> moneyFmt = new MoneyFormatter<BigDecimal>(true, false);

    // Wicket Elements
    private ListView<ScoreboardWidget> tachDialModalListView;
    private final String na = getString("notApplicable.label");

    public TachDialModalsPanel(final String id, final List<ScoreboardWidget> tachDialList) {
	super(id);
	setMarkupId(id);
	setOutputMarkupId(true);
	setOutputMarkupPlaceholderTag(true);
	this.tachDialList = tachDialList;

	tachDialModalListView = new ListView<ScoreboardWidget>("tachDialModalListView",
		new PropertyModel<List<ScoreboardWidget>>(this, "tachDialList")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void populateItem(final ListItem<ScoreboardWidget> listItem) {
		final ScoreboardWidget sbWidget = listItem.getModelObject();
		logger.trace("sbWidget=" + sbWidget);

		final WebMarkupContainer tachDial = new WebMarkupContainer("tachDial");
		tachDial.setMarkupId(id + "-tach" + listItem.getIndex());
		tachDial.setOutputMarkupId(true);
		listItem.add(tachDial);

		final AjaxLink<Object> closeLink = new AjaxLink<Object>("closeLink") {

		    private static final long serialVersionUID = 1L;

		    @Override
		    public void onClick(AjaxRequestTarget target) {
			logger.debug("closeLink onClick idx=" + listItem.getIndex());
			target.appendJavaScript("$('#" + id + "-" + listItem.getIndex() + "').hide();");
		    }
		};
		closeLink.setOutputMarkupPlaceholderTag(true);
		listItem.add(closeLink);

		listItem.setMarkupId(id + "-" + listItem.getIndex());
		listItem.setOutputMarkupId(true);
	    }

	};
	tachDialModalListView.setOutputMarkupPlaceholderTag(true);
	add(tachDialModalListView);

    }

    public List<ScoreboardWidget> getTachDialList() {
	return tachDialList;
    }

    public void refresh(AjaxRequestTarget target, List<ScoreboardWidget> inTachDialList) {
	if (inTachDialList == null || inTachDialList.isEmpty()){
	    return;
	}
	StringBuilder sb = new StringBuilder();
	for(int i = 0; i < tachDialList.size(); i++){
	    ScoreboardWidget inSbw = inTachDialList.get(i);
	    ScoreboardWidget sbw = tachDialList.get(i);
	    if (inSbw != null && sbw != null){
		sb.append(buildLabelChangeJS(inSbw, getId() + "-tach" + i));
		sb.append(buildAttributesJS(inSbw.getWidgetAttributes(), getId() + "-" + i));
	    }
	}
	for(int i = 0; i < tachDialList.size(); i++){
	    ScoreboardWidget inSbw = inTachDialList.get(i);
	    ScoreboardWidget sbw = tachDialList.get(i);
	    if (inSbw != null && sbw != null){
		sb.append(TachDialsPanel.buildTachDialJS(inSbw, getId() + "-tach" + i));
		sb.append(buildProgressBarsJS(inSbw.getWidgetAttributes(), getId() + "-" + i));
	    }
	}
	logger.debug("refreshJS=" + sb.toString());
	target.appendJavaScript(sb.toString());
    }

    private String buildLabelChangeJS(final ScoreboardWidget scoreboardWidget, final String dialId) {
	StringBuilder sb = new StringBuilder();
	sb.append("$('#");
	sb.append(dialId);
	sb.append(" .label').html('");
	if (scoreboardWidget != null && scoreboardWidget.getWidgetName() != null){
	    sb.append(scoreboardWidget.getWidgetName());
	}else{
	    sb.append(na);
	}
	sb.append("');");
	return sb.toString();
    }

    private String buildAttributesJS(final List<ScoreboardWidget> sbAttributes, final String modalId) {
	if (sbAttributes == null || modalId == null){
	    return "";
	}
	StringBuilder sb = new StringBuilder();
	sb.append("$('#");
	sb.append(modalId);
	sb.append(" .sub-metrics').html(\"");
	int i = 0;
	for(ScoreboardWidget sbAttribute: sbAttributes){
	    sb.append(buildAttributeHTML(sbAttribute, modalId, i++));
	}
	sb.append("\");");
	return sb.toString();
    }

    private String buildAttributeHTML(final ScoreboardWidget sbAttribute, final String modalId, final int idx) {
	if (sbAttribute == null || modalId == null){
	    return "";
	}
	String suffix = "";
	if (sbAttribute.getWidgetType() == ScoreboardWidget.Type.PERCENTAGE){
	    suffix = "%";
	}
	StringBuilder sb = new StringBuilder();
	sb.append("<li>");
	sb.append("<h5>");
	if (sbAttribute.getWidgetName() != null){
	    sb.append(sbAttribute.getWidgetName());
	}else{
	    sb.append(na);
	}
	sb.append("</h5>");
	sb.append("<div class='measure'>");
	sb.append("<span class='amt'>");
	if (sbAttribute.getWidgetType() == ScoreboardWidget.Type.DOLLARVALUE){
	    sb.append(moneyFmt.format(new BigDecimal(sbAttribute.getCurrentVal())));
	}else{
	    sb.append(sbAttribute.getCurrentVal());
	}
	sb.append(suffix);
	sb.append("</span>");
	sb.append("<span class='goal'> / ");
	if (sbAttribute.getWidgetType() == ScoreboardWidget.Type.DOLLARVALUE){
	    sb.append(moneyFmt.format(new BigDecimal(sbAttribute.getTargetVal())));
	}else{
	    sb.append(sbAttribute.getTargetVal());
	}
	sb.append(suffix);
	sb.append(" Goal</span>");
	sb.append("</div>");
	sb.append("<div class='bar' id='");
	sb.append(modalId);
	sb.append("-bar");
	sb.append(idx);
	sb.append("'>");
	sb.append("<div class='fill'></div>");
	sb.append("<div class='shadow'></div>");
	sb.append("</div>");
	sb.append("</li>");
	return sb.toString();
    }

    private String buildProgressBarsJS(final List<ScoreboardWidget> sbAttributes, final String modalId) {
	if (sbAttributes == null || modalId == null){
	    return "";
	}
	StringBuilder sb = new StringBuilder();
	for(int i = 0; i < sbAttributes.size(); i++){
	    ScoreboardWidget sba = sbAttributes.get(i);
	    if (sba != null){
		sb.append("setOtherProgressMeter('");
		sb.append(modalId);
		sb.append("-bar");
		sb.append(i);
		sb.append("', ");
		sb.append(sba.getCurrentVal());
		sb.append(", ");
		sb.append(sba.getTargetVal());
		sb.append(", -1, '");
		sb.append(moneyFmt.format(new BigDecimal(sba.getCurrentVal())));
		sb.append("', '");
		sb.append(moneyFmt.format(new BigDecimal(sba.getTargetVal())));
		sb.append("', '');");
	    }
	}
	return sb.toString();
    }
}
