package com.bestbuy.bbym.ise.drp.scoreboard;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.Store;
import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.BaseWebPage;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardDailyGoal;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardEmployeeDistribution;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardEmployeeNotionalMargin;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardOperationType;
import com.bestbuy.bbym.ise.drp.service.ScoreboardService;
import com.bestbuy.bbym.ise.drp.util.MoneyFormatter;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

public class ScoreboardEmpSchedulePage extends BaseWebPage {

    private static final long serialVersionUID = -7486399250536096809L;
    private static Logger logger = LoggerFactory.getLogger(ScoreboardEmpSchedulePage.class);
    private static final String DATE_PATTERN = "EEEE, MMMM dd, yyyy";
    private static MoneyFormatter<BigDecimal> moneyFmt = new MoneyFormatter<BigDecimal>(true, false);

    @SpringBean(name = "scoreboardService")
    private ScoreboardService scoreboardService;

    ScoreboardEmployeeDistribution scoreboardEmployeeDistribution = new ScoreboardEmployeeDistribution();
    float totalHours = 0;

    public ScoreboardEmpSchedulePage(Date localdate, final ScoreboardOperationType scoreboardOperationType) {

	final DailyRhythmPortalSession session = getDailyRhythmPortalSession();

	Store store = new Store();

	if (session.getDrpUser() != null && session.getDrpUser().getStore() != null
		&& session.getDrpUser().getStore().getId() != null
		&& session.getDrpUser().getStore().getStoreType() == null){
	    try{
		store = scoreboardService.getStoreInfo(session.getDrpUser().getStore().getId());
		if (store == null){
		    logger.warn("No store found by scoreboardService for storeId="
			    + session.getDrpUser().getStore().getId());
		}else{
		    session.getDrpUser().setStore(store);
		    logger.debug("store=" + store);
		}
	    }catch(ServiceException se){
		logger.warn("ServiceException when calling service to get store info", se);
	    }catch(IseBusinessException be){
		logger.warn("IseBusinessException when calling service to get store info", be);
	    }
	}else
	    store = session.getDrpUser().getStore();

	try{
	    scoreboardEmployeeDistribution = scoreboardService.getScoreboardEmployeeDistribution(session.getDrpUser()
		    .getStoreId(), session.getDrpUser().getStore().getStoreType(), scoreboardOperationType, localdate);
	}catch(Exception e){
	    logger.warn("Exception when calling service to get employee schedule", e);
	}

	final WebMarkupContainer storeGoalsContainer = new WebMarkupContainer("storeGoalsContainer");
	storeGoalsContainer.setOutputMarkupPlaceholderTag(true);
	add(storeGoalsContainer);

	final WebMarkupContainer storeEmployeesContainer = new WebMarkupContainer("storeEmployeesContainer");
	storeEmployeesContainer.setOutputMarkupPlaceholderTag(true);
	add(storeEmployeesContainer);

	//heading
	add(new Label("heading", new Model<String>() {

	    @Override
	    public String getObject() {

		return scoreboardOperationType.toString() + " DAILY SCHEDULE";

	    }

	}));

	//current date label
	add(DateLabel.forDatePattern("currentDate", Model.of(new Date()), DATE_PATTERN));

	//Store id label
	storeGoalsContainer.add(new Label("storeId", ("STORE " + (store.getId() == null?"N/A":store.getId())
		.replaceAll("^0*", ""))));

	//Store name label
	storeGoalsContainer.add(new Label("storeName", (store.getStoreName() == null?"N/A":store.getStoreName())));

	final ListView<ScoreboardDailyGoal> storeGoalsView = new ListView<ScoreboardDailyGoal>("storeGoalsView",
		scoreboardEmployeeDistribution.getScoreboardDailyGoal()) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void populateItem(ListItem<ScoreboardDailyGoal> listItem) {
		final ScoreboardDailyGoal rowScoreboardDailyGoal = listItem.getModelObject();
		listItem.add(new Label("goalLabel", rowScoreboardDailyGoal.getName()));
		listItem.add(new Label("goalValue", "Goal: "
			+ (rowScoreboardDailyGoal.getName().equalsIgnoreCase("Notional Margin")?moneyFmt
				.format(new BigDecimal(rowScoreboardDailyGoal.getGoalValue().intValue()))
				:rowScoreboardDailyGoal.getGoalValue().toString())));
	    }
	};

	storeGoalsView.setOutputMarkupId(true);
	storeGoalsContainer.add(storeGoalsView);

	final ListView<ScoreboardEmployeeNotionalMargin> storeEmployeesView = new ListView<ScoreboardEmployeeNotionalMargin>(
		"storeEmployeesView", scoreboardEmployeeDistribution.getScoreboardEmployeeScheduleList()) {

	    private static final long serialVersionUID = 1L;
	    private Long duration;

	    @Override
	    protected void populateItem(ListItem<ScoreboardEmployeeNotionalMargin> listItem) {
		final ScoreboardEmployeeNotionalMargin rowScoreboardEmployeeNotionalMargin = listItem.getModelObject();
		listItem
			.add(new Label("employeeNameNotionalMargin", rowScoreboardEmployeeNotionalMargin
				.getEmployeeShift().getEmpFullName()
				+ " - "
				+ moneyFmt.format(new BigDecimal(rowScoreboardEmployeeNotionalMargin.getTargetValue()))));
		listItem.add(new Label("employeeZone", rowScoreboardEmployeeNotionalMargin.getEmployeeShift()
			.getPrimaryZone().getParamValue()
			+ "<span class=\"secondary\"> / "
			+ rowScoreboardEmployeeNotionalMargin.getEmployeeShift().getSecondaryZone().getParamValue()
			+ "</span>").setEscapeModelStrings(false));

		duration = new Long(rowScoreboardEmployeeNotionalMargin.getEmployeeShift().getShiftEndTime().getTime()
			/ 60000 - rowScoreboardEmployeeNotionalMargin.getEmployeeShift().getShiftStartTime().getTime()
			/ 60000);

		long roundedDurationMin = (long) (duration / 15) * 15;

		float hours = (float) (roundedDurationMin / 60);
		float durationHrsMin = ((roundedDurationMin - hours * 60)) / 60;

		Label employeeShiftDuration = new Label("employeeShiftDuration", String.valueOf(hours + durationHrsMin)
			+ " Hrs");

		incrementTotalHours(hours + durationHrsMin);

		employeeShiftDuration.add(AttributeModifier.append("class", new Model<String>() {

		    private static final long serialVersionUID = 1L;

		    private String getamPMvalue() {

			return((rowScoreboardEmployeeNotionalMargin.getEmployeeShift().getShiftStartTime().getHours()) > 12?"p"
				:"a");
		    }

		    private String getStartTime() {

			int shiftStartHours = rowScoreboardEmployeeNotionalMargin.getEmployeeShift()
				.getShiftStartTime().getHours();

			if (shiftStartHours < 8 && getamPMvalue().equalsIgnoreCase("a"))
			    return "8";
			else
			    return String.valueOf(shiftStartHours);

		    }

		    private String getDuration() {

			float timediff = (rowScoreboardEmployeeNotionalMargin.getEmployeeShift().getShiftEndTime()
				.getTime() - rowScoreboardEmployeeNotionalMargin.getEmployeeShift().getShiftStartTime()
				.getTime());
			timediff = timediff / (1000 * 60 * 60);

			Long durationHours = new Long((int) timediff);
			String durationMinutes = new String();
			int minDiff = (int) ((timediff - durationHours) * 100);

			switch (minDiff) {

			    case 0:
				durationMinutes = "00";
				break;
			    case 25:
				durationMinutes = "15";
				break;
			    case 50:
				durationMinutes = "30";
				break;
			    case 75:
				durationMinutes = "45";
				break;
			    default:
				durationMinutes = "00";
			}

			String timeHrsMins = durationHours.toString() + durationMinutes;

			return timeHrsMins;

		    }

		    private String getMinutes() {

			int minutes = (rowScoreboardEmployeeNotionalMargin.getEmployeeShift().getShiftStartTime()
				.getMinutes() / 15) * 15;

			if (minutes == 0)
			    return "00";
			else
			    return String.valueOf(minutes);

		    }

		    @Override
		    public String getObject() {
			String str = "bar start-" + getStartTime() + getMinutes() + getamPMvalue() + " length-"
				+ getDuration();
			return str;
		    }
		}));

		listItem.add(employeeShiftDuration);
	    }
	};

	storeEmployeesView.setOutputMarkupId(true);
	storeEmployeesContainer.add(storeEmployeesView);

	String totalHours = String.valueOf(scoreboardEmployeeDistribution.getTotalHours());

	add(new Label("totalScheduledHours", String.valueOf(totalHours) + " Total Hours Scheduled"));

    }

    protected void incrementTotalHours(float f) {

	totalHours = totalHours + f;

    }

}
