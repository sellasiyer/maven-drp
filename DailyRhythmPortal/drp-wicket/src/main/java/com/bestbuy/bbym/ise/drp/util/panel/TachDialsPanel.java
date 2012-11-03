package com.bestbuy.bbym.ise.drp.util.panel;

import java.math.BigDecimal;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardWidget;
import com.bestbuy.bbym.ise.drp.util.MoneyFormatter;

public class TachDialsPanel extends Panel {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(TachDialsPanel.class);

    // Model Data
    private List<ScoreboardWidget> tachDialList;
    private static MoneyFormatter<BigDecimal> moneyFmt = new MoneyFormatter<BigDecimal>(true, false);

    // Wicket Elements
    private ListView<ScoreboardWidget> tachDialListView;
    private final String na = getString("notApplicable.label");

    public TachDialsPanel(final String id, final List<ScoreboardWidget> tachDialList, final String modalsId) {
	super(id);
	setMarkupId(id);
	setOutputMarkupId(true);
	setOutputMarkupPlaceholderTag(true);
	this.tachDialList = tachDialList;

	tachDialListView = new ListView<ScoreboardWidget>("tachDialListView",
		new PropertyModel<List<ScoreboardWidget>>(this, "tachDialList")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void populateItem(final ListItem<ScoreboardWidget> listItem) {
		final ScoreboardWidget sbWidget = listItem.getModelObject();
		logger.trace("sbWidget=" + sbWidget);

		final Label label = new Label("label", new Model<String>() {

		    private static final long serialVersionUID = 1L;

		    @Override
		    public String getObject() {
			if (sbWidget == null || sbWidget.getWidgetName() == null){
			    return na;
			}
			return sbWidget.getWidgetName();
		    }
		});
		label.setEscapeModelStrings(false);
		listItem.add(label);

		final AjaxLink<Object> expandLink = new AjaxLink<Object>("expandLink") {

		    private static final long serialVersionUID = 1L;

		    @Override
		    public void onClick(AjaxRequestTarget target) {
			logger.debug("expandLink onClick idx=" + listItem.getIndex());
			target.appendJavaScript("$('#" + modalsId + "-" + listItem.getIndex() + "').show();");
		    }

		    @Override
		    public boolean isVisible() {
			return sbWidget.getWidgetAttributes() != null;
		    }
		};
		expandLink.setOutputMarkupPlaceholderTag(true);
		listItem.add(expandLink);

		listItem.setMarkupId(id + "-tach" + listItem.getIndex());
		listItem.setOutputMarkupId(true);
	    }

	};
	tachDialListView.setOutputMarkupPlaceholderTag(true);
	add(tachDialListView);

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
		sbw.setWidgetName(inSbw.getWidgetName());
		sbw.setWidgetAttributes(inSbw.getWidgetAttributes());
		sb.append(buildTachDialJS(inSbw, getId() + "-tach" + i));
	    }
	}
	target.add(this);
	logger.debug("refreshJS=" + sb.toString());
	target.appendJavaScript(sb.toString());
    }

    public static String buildTachDialJS(final ScoreboardWidget scoreboardWidget, final String dialId) {
	if (scoreboardWidget == null || scoreboardWidget.getWidgetType() == null){
	    return "";
	}
	String style = "fill";
	String suffix = "";
	if (scoreboardWidget.getWidgetType() == ScoreboardWidget.Type.PERCENTAGE){
	    suffix = "%";
	    style = "line";
	}
	StringBuilder sb = new StringBuilder();
	sb.append("getTachDial('");
	sb.append(dialId);
	sb.append("', ");
	sb.append(scoreboardWidget.getCurrentVal());
	sb.append(", ");
	if (scoreboardWidget.getWidgetType() == ScoreboardWidget.Type.PERCENTAGE){
	    sb.append(100);
	}else{
	    sb.append(scoreboardWidget.getTargetVal());
	}
	sb.append(", '");
	if (scoreboardWidget.getWidgetType() == ScoreboardWidget.Type.DOLLARVALUE){
	    sb.append(moneyFmt.format(new BigDecimal(scoreboardWidget.getCurrentVal())));
	}else{
	    sb.append(scoreboardWidget.getCurrentVal());
	}
	sb.append(suffix);
	sb.append("', '");
	if (scoreboardWidget.getWidgetType() == ScoreboardWidget.Type.DOLLARVALUE){
	    sb.append(moneyFmt.format(new BigDecimal(scoreboardWidget.getTargetVal())));
	}else{
	    sb.append(scoreboardWidget.getTargetVal());
	}
	sb.append(suffix);
	sb.append("', '");
	sb.append(style);
	sb.append("');");
	return sb.toString();
    }
}
